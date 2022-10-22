package com.example.gsi.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContactoMedico(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("descripcion")
    @Expose
    val descripcion: String
)
