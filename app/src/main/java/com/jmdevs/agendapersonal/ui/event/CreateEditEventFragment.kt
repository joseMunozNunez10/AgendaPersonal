package com.jmdevs.agendapersonal.ui.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentCreateEditEventBinding
import com.jmdevs.agendapersonal.ui.main.viewmodel.EventViewModel
import com.jmdevs.agendapersonal.util.UuidGenerator

class CreateEditEventFragment : Fragment(R.layout.fragment_create_edit_event) {

    private var _binding: FragmentCreateEditEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreateEditEventBinding.bind(view)

        // Guardar evento
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val dateMillis = System.currentTimeMillis() // TODO: usar DatePicker
            if (title.isNotBlank()) {
                val event = viewModel.createEvent(
                    id = UuidGenerator.generate(),
                    title = title,
                    dateMillis = dateMillis
                )
                findNavController().popBackStack() // vuelve a lista
            } else {
                binding.etTitle.error = "Título obligatorio"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
