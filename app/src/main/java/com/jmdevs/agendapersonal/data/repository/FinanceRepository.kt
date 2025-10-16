package com.jmdevs.agendapersonal.data.repository

import com.jmdevs.agendapersonal.data.local.dao.FinancialCategoryDao
import com.jmdevs.agendapersonal.data.local.dao.TransactionDao
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FinanceRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val financialCategoryDao: FinancialCategoryDao
) {
    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    fun getAllCategories(): Flow<List<FinancialCategory>> {
        return financialCategoryDao.getAllCategories()
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun insertCategory(category: FinancialCategory) {
        financialCategoryDao.insertCategory(category)
    }


}