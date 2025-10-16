package com.jmdevs.agendapersonal.ui.finance

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.databinding.FragmentFinanceBinding
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.ui.adapter.TransactionAdapter
import com.jmdevs.agendapersonal.viewmodel.FinanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class FinanceFragment : Fragment() {

    private var _binding: FragmentFinanceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinanceViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeViewModel()
        setupChart()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter { transaction ->
            val action = FinanceFragmentDirections.actionFinanceFragmentToEditTransactionFragment(transaction.id)
            findNavController().navigate(action)
        }
        binding.rvTransactions.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() {
        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_financeFragment_to_addTransactionFragment)
        }
        binding.btnAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_financeFragment_to_addCategoryFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allTransactions.collect { transactions ->
                    transactionAdapter.submitList(transactions)
                    updateChart(transactions)
                    updateBalance(transactions)
                }
            }
        }
    }

    private fun setupChart() {
        binding.pieChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 60f
            transparentCircleRadius = 65f
            setUsePercentValues(false)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
        }
    }

    private fun updateChart(transactions: List<Transaction>) {
        val income = transactions.filter { it.type == "INGRESO" }.sumOf { it.amount }.toFloat()
        val expenses = transactions.filter { it.type == "GASTO" }.sumOf { it.amount }.toFloat()

        val entries = ArrayList<PieEntry>()
        if (income > 0) entries.add(PieEntry(income, "Ingresos"))
        if (expenses > 0) entries.add(PieEntry(expenses, "Gastos"))

        if (entries.isEmpty()) {
            binding.pieChart.clear()
            binding.pieChart.invalidate()
            return
        }

        val dataSet = PieDataSet(entries, "Transacciones")
        dataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.income_color),
            ContextCompat.getColor(requireContext(), R.color.expense_color)
        )
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        val pieData = PieData(dataSet)
        binding.pieChart.data = pieData
        binding.pieChart.invalidate()
    }

    private fun updateBalance(transactions: List<Transaction>) {
        val income = transactions.filter { it.type == "INGRESO" }.sumOf { it.amount }
        val expenses = transactions.filter { it.type == "GASTO" }.sumOf { it.amount }
        val balance = income - expenses

        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        binding.tvBalance.text = format.format(balance)

        when {
            balance > 0 -> binding.tvBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.income_color))
            balance < 0 -> binding.tvBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.expense_color))
            else -> binding.tvBalance.setTextColor(Color.BLACK)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
