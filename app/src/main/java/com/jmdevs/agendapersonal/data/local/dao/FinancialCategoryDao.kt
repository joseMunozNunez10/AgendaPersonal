package com.jmdevs.agendapersonal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmdevs.agendapersonal.data.local.entity.FinancialCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface FinancialCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: FinancialCategory)

    @Query("SELECT * FROM financial_categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<FinancialCategory>>

}