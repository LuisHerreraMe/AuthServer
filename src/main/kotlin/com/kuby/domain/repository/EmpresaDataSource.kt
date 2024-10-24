package com.kuby.domain.repository

import com.kuby.domain.model.Empresa

interface EmpresaDataSource {
    suspend fun getEmpresaById(id: String): Empresa?
    suspend fun saveEmpresaById(empresa: Empresa): Boolean
    suspend fun deleteEmpresa(id: String): Boolean

}