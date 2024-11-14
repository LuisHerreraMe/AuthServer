package com.kuby.domain.sucursal.repocitory

import com.kuby.domain.sucursal.model.Sucursal
import com.kuby.domain.sucursal.model.UpdateSucursal

interface SucursalDataSource {
    suspend fun getSucursal():List<Sucursal?>
    suspend fun getSucursalById(id: String): Sucursal?
    suspend fun getSucursalByIdEmpresa(idEmpresa: String): List<Sucursal?>
    suspend fun getSucursalByName(nombre: String):List<Sucursal?>
    suspend fun saveSucursal(sucursal: Sucursal): Boolean
    suspend fun deleteSucursal(id: String): Boolean
    suspend fun updateSucursal(id: String, updateSucursal: UpdateSucursal): Boolean
}