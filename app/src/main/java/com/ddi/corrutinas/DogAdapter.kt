package com.ddi.corrutinas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// clase que recibe(una lista de images:Lista de strings)<viewHolder>
class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {
    // Devolverle al view holder , que es el encargado de coger los atributos y pintarlos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        // le vamos a pasar el item
        val layoutInflater = LayoutInflater.from(parent.context)
        // devuelve por parametros (accedemos al R.layout. )le pasamos el parent, false
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }
    // Método devuelve el tamaño de la lista
    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // Creamos un val item = images[posicion]
        val item = images[position]
        holder.bind(item)
    }
}
