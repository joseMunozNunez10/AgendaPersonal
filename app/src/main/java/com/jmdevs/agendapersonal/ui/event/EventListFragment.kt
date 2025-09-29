package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.data.local.database.AppDatabase
import com.jmdevs.agendapersonal.data.repository.EventRepository
import com.jmdevs.agendapersonal.databinding.FragmentEventListBinding
import com.jmdevs.agendapersonal.ui.adapter.EventAdapter
import com.jmdevs.agendapersonal.viewmodel.EventViewModel
import com.jmdevs.agendapersonal.viewmodel.EventViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private val dao by lazy { AppDatabase.getDatabase(requireContext()).eventDao() }
    private val repo by lazy { EventRepository(dao) }
    private val viewModel: EventViewModel by viewModels { EventViewModelFactory(repo) }

    private lateinit var adapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEventListBinding.bind(view)

        adapter = EventAdapter { event ->

            val action = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(event.id)
            findNavController().navigate(action)
        }

        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collectLatest { list ->
                adapter.submitList(list)
            }
        }

        binding.fabAddEvent.setOnClickListener {

            findNavController().navigate(R.id.action_eventListFragment_to_eventEditFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
