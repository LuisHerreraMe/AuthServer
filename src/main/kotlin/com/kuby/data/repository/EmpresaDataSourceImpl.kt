package com.kuby.data.repository

import com.kuby.domain.model.Empresa
import com.kuby.domain.model.Rol
import com.kuby.domain.repository.EmpresaDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class EmpresaDataSourceImpl(
    private val database: CoroutineDatabase
): EmpresaDataSource {

    private  val empresas = database.getCollection<Empresa>()

    override suspend fun getEmpresaById(id: String): Empresa? {
        return empresas.findOne(Rol::id eq id)
    }

    override suspend fun saveEmpresaById(empresa: Empresa): Boolean {
        val existingUser = empresas.findOne(filter = Empresa::nit eq empresa.nit)
        return if (existingUser == null){
            val result = empresas.insertOne(document = empresa)
            result.wasAcknowledged()
        } else{
            false
        }
    }

    override suspend fun deleteEmpresa(id: String): Boolean {
        return empresas.deleteOne(filter = Empresa::id eq id).wasAcknowledged()
    }
}