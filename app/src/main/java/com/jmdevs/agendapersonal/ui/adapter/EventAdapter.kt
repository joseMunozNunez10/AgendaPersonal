package com.jmdevs.agendapersonal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.data.local.entity.Event

class EventAdapter(
    private val events: List<Event>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // Lista de drawables de gradientes
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false) // <-- CORREGIDO AQUÍ
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        // Asignar datos
        holder.title.text = event.title
        holder.description.text = event.description

        // Asignar un gradiente random
        val randomGradient = gradients.random()
        holder.cardView.setBackgroundResource(randomGradient)
    }

    override fun getItemCount(): Int = events.size
}