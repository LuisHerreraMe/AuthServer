package com.kuby.routes.servicio

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.servicio.model.Servicio
import com.kuby.domain.servicio.repocitory.ServicioDataSource
import com.kuby.util.Permissions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.time.LocalDateTime

fun Route.UpdataServicioRoute(
    servicioDataSource: ServicioDataSource
) {
    authenticate("another-auth"){
        post() {

            try {
                val servicioRequest = call.receive<Servicio>()
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    saveServicioToDatabase(
                        servicioRequest,
                        servicioDataSource
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

private fun Servicio.toModel(): Servicio = Servicio(
    id = this.id,
    codigo = this.codigo,
    descripcion = this.descripcion,
    detalle = this.detalle,
    estado = "0",
    idSede = this.idSede,
    fechaSolicitud = LocalDateTime.now().toString()

)

private suspend fun PipelineContext<Unit, ApplicationCall>.saveServicioToDatabase(
    servicioRequest: Servicio,
    servicioDataSource: ServicioDataSource
){
    val servicio = servicioRequest.toModel()
    println(servicio)
    val response = servicioDataSource.saveServicio(servicio)
    return if (response) {
        call.respond(
            status = HttpStatusCode.OK,
            message = ApiResponseError(
                statusCode = HttpStatusCode.OK.value,
                message = "ok"
            )
        )
    } else {
        call.respond(
            status = HttpStatusCode.Forbidden,
            message = ApiResponseError(
                statusCode = 403,
                message = "Numero NIT ya esta en uso."
            )
        )
    }
}


private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("UserId")
        ?.asString()