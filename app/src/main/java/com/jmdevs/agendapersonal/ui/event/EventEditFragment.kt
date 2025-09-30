package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.databinding.FragmentEventEditBinding
import com.jmdevs.agendapersonal.util.UuidGenerator
import com.jmdevs.agendapersonal.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EventEditFragment : Fragment() {

    private var _binding: FragmentEventEditBinding? = null
    private val binding get() = _binding!!

    private val args: EventEditFragmentArgs by navArgs()

    private val eventViewModel: EventViewModel by viewModels()

    private var currentEvent: Event? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = args.eventId
        val selectedDateMillis = args.selectedDate

        if (eventId != null && eventId != "@null" && eventId.isNotBlank()) {
            loadEventData(eventId)
        } else {
            val dateToShow = if (selectedDateMillis != -1L) {
                Date(selectedDateMillis)
            } else {
                Date()
            }
            prefillDateTime(dateToShow)
        }

        binding.btnSaveEvent.setOnClickListener {
            saveEvent()
        }
    }

    private fun prefillDateTime(date: Date) {
        val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.etEventDate.setText(sdfDate.format(date))
        binding.etEventTime.setText(sdfTime.format(date))
    }

    private fun loadEventData(eventId: String) {
        lifecycleScope.launch {
            eventViewModel.getEventByIdFlow(eventId).collectLatest { event ->
                event?.let {
                    currentEvent = it
                    binding.etEventTitle.setText(it.title)
                    binding.etEventDescription.setText(it.description ?: "")
                    prefillDateTime(Date(it.dateMillis))
                }
            }
        }
    }

    private fun saveEvent() {
        val title = binding.etEventTitle.text.toString().trim()
        val description = binding.etEventDescription.text.toString().trim()
        val dateStr = binding.etEventDate.text.toString().trim()
        val timeStr = binding.etEventTime.text.toString().trim()

        if (title.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
            Toast.makeText(requireContext(), "Título, fecha y hora son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val dateTimeStr = "$dateStr $timeStr"
        val sdfDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).apply { isLenient = false }
        val eventMillis: Long
        try {
            eventMillis = sdfDateTime.parse(dateTimeStr)?.time ?: run {
                Toast.makeText(requireContext(), "Formato de fecha (dd/MM/yyyy) u hora (HH:mm) inválido", Toast.LENGTH_SHORT).show()
                return
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Formato de fecha (dd/MM/yyyy) u hora (HH:mm) inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedEvent = if (currentEvent != null) {
            currentEvent!!.copy(
                title = title,
                description = description.ifEmpty { null },
                dateMillis = eventMillis,
                updatedAt = System.currentTimeMillis()
            )
        } else {
            Event(
                id = UuidGenerator.generate(),
                title = title,
                description = description.ifEmpty { null },
                dateMillis = eventMillis,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        }

        lifecycleScope.launch {
            if (currentEvent != null) {
                eventViewModel.update(updatedEvent)
                Toast.makeText(requireContext(), "Evento actualizado", Toast.LENGTH_SHORT).show()
            } else {
                eventViewModel.insert(updatedEvent)
                Toast.makeText(requireContext(), "Evento guardado", Toast.LENGTH_SHORT).show()
            }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
