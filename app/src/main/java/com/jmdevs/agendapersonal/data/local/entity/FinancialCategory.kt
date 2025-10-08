package com.jmdevs.agendapersonal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "financial_categories")
data class FinancialCategory (
    @PrimaryKey
    val name: String,
    val type: String, // "INGRESO" o "GASTO"



)