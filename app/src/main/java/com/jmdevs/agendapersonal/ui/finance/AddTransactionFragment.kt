package com.jmdevs.agendapersonal.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.databinding.FragmentAddTransactionBinding
import com.jmdevs.agendapersonal.viewmodel.FinanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinanceViewModel by viewModels()
    private var categories: List<FinancialCategory> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeCategories()
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCategories.collectLatest { categoryList ->
                categories = categoryList
                setupCategoryChips(categories)
            }
        }
    }

    private fun setupCategoryChips(categories: List<FinancialCategory>) {
        val chipGroup = binding.chipGroupCategory
        chipGroup.removeAllViews()
        categories.forEach { category ->
            val chip = Chip(context).apply {
                text = category.name
                isCheckable = true
                tag = category.type // Store type in chip's tag
            }
            chipGroup.addView(chip)
        }
    }

    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val description = binding.tietDescription.text.toString().trim()
        val amountString = binding.tietAmount.text.toString().trim()

        if (description.isEmpty()) {
            Toast.makeText(requireContext(), "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
            return
        }

        if (amountString.isEmpty()) {
            Toast.makeText(requireContext(), "El monto no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountString.toLongOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "Por favor, introduzca un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedCategoryChipId = binding.chipGroupCategory.checkedChipId
        if (selectedCategoryChipId == View.NO_ID) {
            Toast.makeText(requireContext(), "Por favor seleccione una categoría", Toast.LENGTH_SHORT).show()
            return
        }
        val categoryChip = binding.chipGroupCategory.findViewById<Chip>(selectedCategoryChipId)
        val categoryName = categoryChip.text.toString()
        val transactionType = categoryChip.tag as String

        val transaction = Transaction(
            description = description,
            amount = amount,
            date = Date().time,
            type = transactionType,
            category = categoryName,
            paymentMethod = "Efectivo", // Hardcoded for now
            balance = 0L // This seems to be unused or calculated elsewhere
        )

        viewModel.insertTransaction(transaction)
        Toast.makeText(requireContext(), "Transaccion guardada", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
