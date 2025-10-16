package com.jmdevs.agendapersonal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.data.local.entity.Transaction
import com.jmdevs.agendapersonal.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(private val onItemClicked: (Transaction) -> Unit) : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
        holder.itemView.setOnClickListener {
            onItemClicked(transaction)
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())

        fun bind(transaction: Transaction) {
            binding.tvTransactionDescription.text = transaction.description
            binding.tvTransactionDate.text = dateFormat.format(Date(transaction.date))
            binding.tvTransactionCategory.text = transaction.category

            val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            format.maximumFractionDigits = 0
            val formattedAmount = format.format(transaction.amount)

            if (transaction.type == "INGRESO") {
                binding.tvTransactionAmount.text = "+ ${formattedAmount.replace("CLP", "$")}"
                binding.tvTransactionAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.income_color))
            } else {
                binding.tvTransactionAmount.text = "- ${formattedAmount.replace("CLP", "$")}"
                binding.tvTransactionAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.expense_color))
            }
        }
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
}
