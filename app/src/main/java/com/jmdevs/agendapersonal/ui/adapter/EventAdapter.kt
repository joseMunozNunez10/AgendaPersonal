package com.jmdevs.agendapersonal.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
    private val onItemClicked: (Event) -> Unit,
    private val onEventStateChanged: (Event) -> Unit // Para notificar cambios de estado (isCompleted)
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
        val date: TextView = itemView.findViewById(R.id.tvDate)
        // Usamos el ID que confirmaste para el CheckBox
        val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.tvIsCompleted)

        fun bind(event: Event) {
            title.text = event.title
            description.text = event.description

            try {
                val sdf = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES"))
                date.text = sdf.format(Date(event.dateMillis))
            } catch (e: Exception) {
                date.text = "Fecha no disponible"
            }

            val randomGradient = gradients.random()
            cardView.setBackgroundResource(randomGradient)

            itemView.setOnClickListener {
                onItemClicked(event)
            }

            // Lógica para el CheckBox y el tachado del título
            
            // 1. Quitar listeners anteriores para evitar múltiples llamadas si bind se reutiliza.
            checkBoxCompleted.setOnCheckedChangeListener(null)
            
            // 2. Establecer el estado inicial del CheckBox y el tachado del título
            checkBoxCompleted.isChecked = event.isCompleted
            updateTitleStrikeThrough(event.isCompleted)

            // 3. Listener para cuando el CheckBox cambia de estado por interacción del usuario
            checkBoxCompleted.setOnCheckedChangeListener { _, isCheckedByUser ->
                // Actualizamos el estado en la copia local del objeto 'event'
                event.isCompleted = isCheckedByUser
                
                // Aplicamos el cambio visual inmediatamente
                updateTitleStrikeThrough(isCheckedByUser)
                
                // Notificamos hacia afuera (al Fragment/Activity, que luego llamará al ViewModel)
                // para que el cambio se persista en la base de datos.
                onEventStateChanged(event)
            }
        }

        // Función helper para aplicar/quitar el tachado del título
        private fun updateTitleStrikeThrough(isCompleted: Boolean) {
            if (isCompleted) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
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
        // Si 'Event' es una data class, la comparación por defecto 'oldItem == newItem'
        // ya considerará todos los campos, incluido 'isCompleted'.
        return oldItem == newItem
    }
}
