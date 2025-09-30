package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentEventDetailBinding
import com.jmdevs.agendapersonal.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class EventDetailFragment : Fragment(R.layout.fragment_event_detail) {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    private val args: EventDetailFragmentArgs by navArgs()
    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventDetailBinding.bind(view)

        lifecycleScope.launch {
            viewModel.getEventByIdFlow(args.eventId).collectLatest { event ->
                event?.let {
                    binding.tvTitleDetail.text = it.title
                    binding.tvDescriptionDetail.text = it.description ?: ""
                    binding.tvDateDetail.text = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(java.util.Date(it.dateMillis))
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val action = EventDetailFragmentDirections.actionEventDetailFragmentToEventEditFragment(args.eventId)
            findNavController().navigate(action)
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getEventByIdFlow(args.eventId).collectLatest { event ->
                    event?.let {
                        viewModel.delete(it)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
