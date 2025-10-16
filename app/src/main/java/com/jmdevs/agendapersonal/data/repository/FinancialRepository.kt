package com.jmdevs.agendapersonal.data.repository

import com.jmdevs.agendapersonal.data.local.dao.FinancialCategoryDao
import com.jmdevs.agendapersonal.data.local.dao.TransactionDao
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FinancialRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: FinancialCategoryDao
) {

    // Transaction operations
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()

    fun getTransactionById(id: Long): Flow<Transaction> = transactionDao.getTransactionById(id)

    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insertTransaction(transaction)

    suspend fun updateTransaction(transaction: Transaction) = transactionDao.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.deleteTransaction(transaction)

    // Category operations
    fun getAllCategories(): Flow<List<FinancialCategory>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: FinancialCategory) = categoryDao.insertCategory(category)
}
