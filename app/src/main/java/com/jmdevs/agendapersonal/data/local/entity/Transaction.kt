package com.jmdevs.agendapersonal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val type: String, // "INGRESO" o "GASTO"
    val category: String,
    val description: String,
    val date: Long,
    val paymentMethod: String,
    val currency: String = "Clp",
    val balance: Double
)