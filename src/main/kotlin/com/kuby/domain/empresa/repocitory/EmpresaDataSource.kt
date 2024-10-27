package com.kuby.domain.empresa.repocitory

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa

interface EmpresaDataSource {
    suspend fun getEmpresa():List<Empresa?>
    suspend fun getEmpresaById(id: String): Empresa?
    suspend fun getEmpresaByName(nombre: String):List<Empresa?>
    suspend fun saveEmpresa(empresa: Empresa): Boolean
    suspend fun deleteEmpresa(id: String): Boolean
    suspend fun updateEmpresa(id: String, updateEmpresa: UpdateEmpresa): Boolean

}