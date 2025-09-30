package com.jmdevs.agendapersonal.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentCalendarBinding
import com.jmdevs.agendapersonal.ui.adapter.EventAdapter
import com.jmdevs.agendapersonal.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private var selectedDate: Long = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeAllEventsForDots()

        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
                selectedDate = clickedDayCalendar.timeInMillis
                loadEventsForSelectedDate(selectedDate)
                binding.btnCreateEvent.visibility = View.VISIBLE
            }
        })

        binding.btnCreateEvent.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToEventEditFragment(
                eventId = null,
                selectedDate = selectedDate
            )
            findNavController().navigate(action)
        }

        binding.btnCreateEvent.visibility = View.GONE
        loadEventsForSelectedDate(System.currentTimeMillis())
        binding.calendarView.setDate(Calendar.getInstance())
    }

    private fun observeAllEventsForDots() {
        viewLifecycleOwner.lifecycleScope.launch {
            eventViewModel.events.collectLatest { events ->
                val eventDays = events.map { event ->
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = event.dateMillis
                    EventDay(calendar, R.drawable.ic_dot)
                }
                binding.calendarView.setEvents(eventDays)
            }
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter(
            onItemClicked = { event ->
                val action = CalendarFragmentDirections.actionCalendarFragmentToEventEditFragment(
                    eventId = event.id,
                    selectedDate = event.dateMillis
                )
                findNavController().navigate(action)
            },
            onEventStateChanged = { event ->
                eventViewModel.update(event)
            }
        )
        binding.rvEventsForDate.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadEventsForSelectedDate(dateInMillis: Long) {
        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }

        val startOfDay = (calendar.clone() as Calendar).apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val endOfDay = (calendar.clone() as Calendar).apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            eventViewModel.getEventsForDateRange(startOfDay.timeInMillis, endOfDay.timeInMillis).collectLatest { events ->
                eventAdapter.submitList(events)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
