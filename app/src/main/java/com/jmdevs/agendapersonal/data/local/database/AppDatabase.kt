package com.jmdevs.agendapersonal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jmdevs.agendapersonal.data.local.dao.EventDao
import com.jmdevs.agendapersonal.data.local.dao.FinancialCategoryDao
import com.jmdevs.agendapersonal.data.local.dao.TransactionDao
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.util.Converters

@Database(
    entities = [
        Event::class,
        Transaction::class,
        FinancialCategory::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun transactionDao(): TransactionDao
    abstract fun financialCategoryDao(): FinancialCategoryDao

}