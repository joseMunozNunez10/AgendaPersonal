package com.jmdevs.agendapersonal.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.databinding.FragmentAddCategoryBinding
import com.jmdevs.agendapersonal.viewmodel.FinanceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : Fragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString()
            val type = when (binding.rgCategoryType.checkedRadioButtonId) {
                binding.rbIngreso.id -> "INGRESO"
                binding.rbGasto.id -> "GASTO"
                else -> ""
            }

            if (name.isNotEmpty() && type.isNotEmpty()) {
                viewModel.insertCategory(FinancialCategory(name = name, type = type))
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
