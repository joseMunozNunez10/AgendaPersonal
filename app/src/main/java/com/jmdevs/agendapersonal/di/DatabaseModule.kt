package com.jmdevs.agendapersonal.di

import android.content.Context
import androidx.room.Room
import com.jmdevs.agendapersonal.data.local.dao.EventDao
import com.jmdevs.agendapersonal.data.local.dao.FinancialCategoryDao
import com.jmdevs.agendapersonal.data.local.dao.TransactionDao
import com.jmdevs.agendapersonal.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "agenda_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }

    @Provides
    fun provideFinancialCategoryDao(appDatabase: AppDatabase): FinancialCategoryDao {
        return appDatabase.financialCategoryDao()
    }
}
