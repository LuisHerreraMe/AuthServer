package com.kuby.routes.sucursal

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.servicio.repocitory.ServicioDataSource
import com.kuby.domain.sucursal.model.SucursalReturn
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.service.JwtService
import com.kuby.util.Permissions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.getSucursalRoute(
    sucursalDataSource: SucursalDataSource,
    servicioDataSource: ServicioDataSource
) {
    authenticate("another-auth"){
        get(){
            try {
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    val sucursales = sucursalDataSource.getSucursal()
                    val sucursalesConNumeroServicio = sucursales.map { sucursal ->
                        val numeroServicios = sucursal?.let { it1 -> servicioDataSource.getNumeroServicio(it1.id) } ?: 0
                        if (sucursal != null) {
                            println("Sucursal ID: ${sucursal.id}, Número de Servicios: $numeroServicios")
                        }

                        sucursal?.let { it1 ->
                            SucursalReturn(
                                id = it1.id,
                                idEmpresa = sucursal.idEmpresa,
                                nombre = sucursal.nombre,
                                phone = sucursal.phone,
                                direccion = sucursal.direccion,
                                municipio = sucursal.municipio,
                                numeroServicio = numeroServicios.toString(),
                                createdAt = sucursal.createdAt,
                                updatedAt = sucursal.updatedAt
                            )
                        }
                    }
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = sucursalesConNumeroServicio
                    )
                } else{
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = ApiResponseError(
                            statusCode = HttpStatusCode.Forbidden.value,
                            message = "Privilegios no validos"
                        )
                    )
                }
            }catch (e: Exception){
                call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = ApiResponseError(
                        statusCode = HttpStatusCode.Forbidden.value,
                        message = "Error al registrar la Empresa"
                    )
                )
            }
        }
    }
}


private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("UserId")
        ?.asString()