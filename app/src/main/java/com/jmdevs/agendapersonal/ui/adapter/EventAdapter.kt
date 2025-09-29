package com.jmdevs.agendapersonal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.data.local.entity.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventAdapter(
    private val onItemClicked: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    private val gradients = listOf(
        R.drawable.card_gradient,
        R.drawable.card_gradient2,
        R.drawable.card_gradient3,
        R.drawable.card_gradient4,
        R.drawable.card_gradient5,
        R.drawable.card_gradient6
    )

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardEvent)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val date: TextView = itemView.findViewById(R.id.tvDate) // Declaración de date

        fun bind(event: Event) {
            title.text = event.title
            description.text = event.description

            // Formatear y establecer la fecha en español
            try {
                val sdf = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES"))
                date.text = sdf.format(Date(event.dateMillis)) // Corregido a event.dateMillis
            } catch (e: Exception) {
                date.text = "Fecha no disponible"
            }

            val randomGradient = gradients.random()
            cardView.setBackgroundResource(randomGradient)

            itemView.setOnClickListener {
                onItemClicked(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}
