package com.example.gsi.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class putTurno(
    @SerializedName("id")
    @Expose
    val id:Long)
data class Turno(
    @SerializedName("id")
    @Expose
    val id:Long,
    @SerializedName("turno")
    @Expose
    val turno:String
)
