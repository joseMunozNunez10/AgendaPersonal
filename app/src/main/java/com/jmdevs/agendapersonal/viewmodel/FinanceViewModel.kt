package com.jmdevs.agendapersonal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.data.repository.FinancialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val repository: FinancialRepository
) : ViewModel() {

    val allTransactions: StateFlow<List<Transaction>> = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allCategories: StateFlow<List<FinancialCategory>> = repository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val balance: StateFlow<Double> = allTransactions.map { transactions ->
        var total = 0.0
        transactions.forEach { transaction ->
            if (transaction.type == "INGRESO") {
                total += transaction.amount
            } else {
                total -= transaction.amount
            }
        }
        total
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun getTransactionById(id: Long): Flow<Transaction> {
        return repository.getTransactionById(id)
    }

    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insertTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
    }

    fun insertCategory(category: FinancialCategory) = viewModelScope.launch {
        repository.insertCategory(category)
    }
}
