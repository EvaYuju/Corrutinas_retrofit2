package com.ddi.corrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddi.corrutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    // Preparamos el viewBinding para enlazar las vistas al código
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    // Vble para el listado que es "mutable"
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enlaza este código con la vista principal (activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }//onCreate
    private fun initRecyclerView() {
        // Llamamos al binding del recyclerView
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter

    }

/* A continuación, la instancia de Retrofit que vamos a crear será la que tenga el resto de la url
del endpoint, se encargará de convertir el JSON a DogResponse y tendrá toda la configuración
para hacer la llamada del API.*/
    private fun getRetrofit(): Retrofit {
    // Nuestra función getRetrofit() retornará una instancia de Retrofit.
        return Retrofit.Builder() // llamamos a la función .Builder()
            // le añadiremos la baseUrl/ (es la parte fija de nuestra API)
            .baseUrl("https://dog.ceo/api/breed/")
            // esta línea implementará la librería del principio,
            // que hará tod el trabajo de recuperar el JSON y pasarlo a DogsResponse.
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Para que tod esto se aplique terminamos con .build()
    }//getRetrofit


    private fun searchByName(query:String){ // (query = raza que buscamos)
        // Creamos una corrutina para que tod lo que ejecutemos dentro se haga un hilo secundario
        // que seria en este caso la llamada a internet
        CoroutineScope(Dispatchers.IO).launch {
            // Vble call=dameelRetrofit.create.(Le pasamos la interfaz).llamada a la funcion("$deVble+query/url")
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            // el body es donde está la respuesta
            val puppies = call.body()
            // volver al hilo principal
            runOnUiThread {
                if (call.isSuccessful) {
                    // Primero almacenamos las imagenes en una vble
                    val images = puppies?.images ?: emptyList() // si es nulo recibimos una listaVacia
                    // Primero borra lo que tengas
                    dogImages.clear()
                    // Añade lo que tengas
                    dogImages.addAll(images)
                    // Avisar al adapter de lo que ha cambiado
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }//runOnUiThread
        }//Coroutine
    }//searchByName

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
    }//showError

    // Estos 2 métodos nos van a avisar cuando el usuario escriba cada letra o borre
    override fun onQueryTextSubmit(query: String?): Boolean {
    // Cuando le das a buscar se llama a este método
        // Sí el texto que ha escrito el usuario no es vacío ni nulo:
        if (!query.isNullOrBlank()){
            // Llamamos a la función de busqueda.toLowerCawe pasando tod a minúscula
            searchByName(query.toLowerCase())
        }
        return true
    }//onQueryTextSubmit

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }//onQueryTextChange


}//MainActivity

