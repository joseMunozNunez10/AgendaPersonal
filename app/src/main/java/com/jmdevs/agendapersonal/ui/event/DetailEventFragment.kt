package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentDetailEventBinding
import com.jmdevs.agendapersonal.ui.main.viewmodel.EventViewModel

class DetailEventFragment : Fragment(R.layout.fragment_detail_event) {

    private var _binding: FragmentDetailEventBinding? = null
    private val binding get() = _binding!!

    private val args: DetailEventFragmentArgs by navArgs()
    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDetailEventBinding.bind(view)

        val eventId = args.eventId

        viewModel.getEventById(eventId)?.let { event ->
            binding.tvTitle.text = event.title
            binding.tvDescription.text = event.description
        }

        binding.btnEdit.setOnClickListener {
            // TODO: navegar a CreateEditEventFragment con eventId
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteEvent(eventId)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
