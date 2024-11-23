package com.kuby.domain.pagoServicio.repocitory

import com.kuby.domain.pagoServicio.model.PagoServicio
import com.kuby.domain.user.model.User
import java.time.LocalDateTime

interface pagoServicioDataSource {
    suspend fun  getPagoServInfoById(id: String): PagoServicio?
    suspend fun  getPagoServ():List<User>?
    suspend fun  getPagoServInfoByIdSucursal(idSucursal: String): PagoServicio?
    suspend fun savePagoServInfo(pagoServicio: PagoServicio): Boolean
    suspend fun deletePagoServ(id: String): Boolean
    suspend fun  updatePagoServInfo(): Boolean
}