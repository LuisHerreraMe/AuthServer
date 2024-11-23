package com.kuby.domain.servicio.repocitory

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.region.model.Ciudad
import com.kuby.domain.servicio.model.Servicio

interface ServicioDataSource {
    suspend fun getServicio():List<Servicio?>
    suspend fun getNumeroServicio(idSede: String): Int?
    suspend fun getServicioById(id: String): Servicio?
    suspend fun getServicioByIdSucursal(idSede: String): List<Servicio>?
    suspend fun getServicioByName(nombre: String): List<Servicio>?
    suspend fun saveServicio(servicio: Servicio): Boolean
    suspend fun deleteServicio(id: String): Boolean
}