package com.kuby.domain.servicio.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Servicio (
    val id: String = ObjectId().toString(),
    val codigo: String?=null,
    val descripcion: String?=null,
    val detalle: String?=null,
    val estado: String?=null,
    val valor: String?=null,
    val tipoPago: String?=null,
    val fechaSolicitud: String?=null,
    val fechaInicio: String?=null,
    val fechaFin: String?=null,
    val idSede: String?=null,
    val idUser: String ?=null,
)