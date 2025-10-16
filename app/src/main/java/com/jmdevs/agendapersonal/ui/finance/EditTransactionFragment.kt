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
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.databinding.FragmentEditTransactionBinding
import com.jmdevs.agendapersonal.viewmodel.FinanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTransactionFragment : Fragment() {

    private var _binding: FragmentEditTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinanceViewModel by viewModels()
    private val args: EditTransactionFragmentArgs by navArgs()

    private var currentTransaction: Transaction? = null
    private var categories: List<FinancialCategory> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeTransaction()
        observeCategories()

        binding.buttonSave.setOnClickListener {
            updateTransaction()
        }

        binding.buttonDelete.setOnClickListener {
            deleteTransaction()
        }
    }

    private fun observeTransaction(){
        lifecycleScope.launch {
            viewModel.getTransactionById(args.transactionId).collect { transaction ->
                transaction?.let {
                    currentTransaction = it
                    populateUI(it)
                }
            }
        }
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCategories.collectLatest { categoryList ->
                categories = categoryList
                currentTransaction?.let { setupCategoryChips(categoryList, it.category) }
            }
        }
    }

    private fun setupCategoryChips(categories: List<FinancialCategory>, currentCategory: String) {
        val chipGroup = binding.chipGroupCategory
        chipGroup.removeAllViews()
        categories.forEach { category ->
            val chip = Chip(context).apply {
                text = category.name
                isCheckable = true
                isChecked = category.name == currentCategory
                tag = category.type // Store type in chip's tag
            }
            chipGroup.addView(chip)
        }
    }

    private fun populateUI(transaction: Transaction) {
        binding.tietDescription.setText(transaction.description)
        binding.tietAmount.setText(transaction.amount.toString())
        setupCategoryChips(categories, transaction.category)
    }

    private fun updateTransaction() {
        val description = binding.tietDescription.text.toString().trim()
        val amount = binding.tietAmount.text.toString().toLongOrNull()

        if (description.isEmpty() || amount == null) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
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

        currentTransaction?.let {
            val updatedTransaction = it.copy(
                description = description,
                amount = amount,
                type = transactionType,
                category = categoryName
            )
            viewModel.updateTransaction(updatedTransaction)
            findNavController().popBackStack()
        }
    }

    private fun deleteTransaction() {
        currentTransaction?.let {
            viewModel.deleteTransaction(it)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}