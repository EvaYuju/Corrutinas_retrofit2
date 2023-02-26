package com.ddi.corrutinas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ddi.corrutinas.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {
    // añadimos el binding
    private val binding = ItemDogBinding.bind(view)

    fun bind(image:String){
        // Libreria para convertir una url en una imagen
        // y las carga dentro del imageView
        Picasso.get().load(image).into(binding.ivDog)
    }
}