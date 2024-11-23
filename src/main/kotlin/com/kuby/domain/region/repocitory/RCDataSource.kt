package com.kuby.domain.region.repocitory

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.region.model.Ciudad
import com.kuby.domain.region.model.Region

interface RCDataSource {
    suspend fun getRegion(): List<Region>
    suspend fun getRegionById(id: String): Region?
    suspend fun getRegionByName(nombre: String): List<Region>
    suspend fun saveRegion(region: Region): Boolean

    suspend fun getCiudad(): List<Ciudad>
    suspend fun getCiudadByIdRegion(idRegion: String): List<Ciudad>
    suspend fun getCiudadByName(nombre: String): List<Ciudad>
    suspend fun saveCiudad(ciudad: Ciudad): Boolean
}
