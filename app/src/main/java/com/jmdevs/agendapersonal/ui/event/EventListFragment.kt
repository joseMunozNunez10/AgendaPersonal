package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentEventListBinding
import com.jmdevs.agendapersonal.ui.adapter.EventAdapter
import com.jmdevs.agendapersonal.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventViewModel by viewModels()

    private lateinit var adapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)

        adapter = EventAdapter(
            onItemClicked = { event ->
                val action = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(event.id)
                findNavController().navigate(action)
            },
            onEventStateChanged = { event ->
                viewModel.update(event)
            }
        )

        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
