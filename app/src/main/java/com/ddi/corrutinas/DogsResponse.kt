package com.ddi.corrutinas

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    // Propiedad serializedName ("como lo llamamos=exactamente igual que en el JSON")
    @SerializedName("status") var status: String,
    @SerializedName("message") var images: List<String>
)