package com.example.gsi.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class createContactoEmergencia(
    @SerializedName("descripcion")
    @Expose
    val descripcion: String

)
data class putContactoEmergencia(
    @SerializedName("id")
    @Expose
    val id: Long)
data class ContactoEmergencia(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("descripcion")
    @Expose
    val descripcion: String

)