package com.ddi.corrutinas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

// Lo que hará será definir el tipo de consumo de API y lo que nos va a devolver.
interface APIService {
    // Tipo de llamada (retrofit)
    @GET
    // Función damePerrosPorRaza(url de retrofit2:tipo String devuelve un response<de tipo DogResponse>
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse> // URL=https://dog.ceo/api/breed/ STRING= "raza"/images
     // palabra reservada llamada *suspend, esta será necesaria para trabajar con corrutinas,
     // es decir, siempre que queramos hacer llamadas en segundo plano usando corrutinas
     // tendremos que añadirla para que funcione nuestro código.
}
