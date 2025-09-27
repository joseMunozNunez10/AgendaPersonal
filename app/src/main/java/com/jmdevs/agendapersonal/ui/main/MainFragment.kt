package com.jmdevs.agendapersonal.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentMainBinding
import com.jmdevs.agendapersonal.ui.main.viewmodel.EventViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        val adapter = EventAdapter { eventId ->
            val action = MainFragmentDirections.actionMainToDetail(eventId)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observa eventos
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collectLatest { list ->
                adapter.submitList(list)
            }
        }

        // Botón agregar evento
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_createEdit)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
