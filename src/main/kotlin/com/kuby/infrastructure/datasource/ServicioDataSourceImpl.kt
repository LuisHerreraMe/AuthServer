package com.kuby.infrastructure.datasource

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.servicio.model.Servicio
import com.kuby.domain.servicio.repocitory.ServicioDataSource
import com.mongodb.client.model.Filters
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ServicioDataSourceImpl(
    private val database: CoroutineDatabase
): ServicioDataSource {

    private val servicios = database.getCollection<Servicio>()

    override suspend fun getServicio(): List<Servicio?> {
        return servicios.find().toList()
    }

    override suspend fun getNumeroServicio(idSede: String): Int {
        return servicios.countDocuments(Filters.eq("idSede", idSede)).toInt()
    }

    override suspend fun getServicioById(id: String): Servicio? {
        return servicios.findOne(Servicio::id eq id)
    }

    override suspend fun getServicioByIdSucursal(idSede: String): List<Servicio> {
        val filtro = Filters.regex("idSede", ".*$idSede.*", "i")
        return servicios.find(filtro).toList()
    }

    override suspend fun getServicioByName(nombre: String): List<Servicio> {
        val filtro = Filters.regex("nombre", ".*$nombre.*", "i")
        return servicios.find(filtro).toList()
    }

    override suspend fun saveServicio(servicio: Servicio): Boolean {
        val existingEmpresa = servicios.findOne(Servicio::codigo eq servicio.codigo)
        return if (existingEmpresa == null) {
            val result = servicios.insertOne(servicio)
            result.wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun deleteServicio(id: String): Boolean {
        val deleteResult = servicios.deleteOne(Empresa::id eq id)
        return deleteResult.deletedCount > 0
    }
}