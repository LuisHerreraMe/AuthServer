package com.kuby.routes.empresa

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
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
import java.time.LocalDateTime

fun Route.saveEmpresaRoute(
    empresaDataSource: EmpresaDataSource
) {
    authenticate("another-auth"){
        post() {

            try {
                val empresaRequest = call.receive<Empresa>()
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    saveEmpresaToDatabase(
                        empresaRequest,
                        empresaDataSource
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

private fun Empresa.toModel(): Empresa = Empresa(
    nombre = this.nombre,
    nit = this.nit,
    logo = this.logo,
    contacto = this.contacto,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now()
)


private suspend fun PipelineContext<Unit, ApplicationCall>.saveEmpresaToDatabase(
    empresaRequest: Empresa,
    empresaDataSource: EmpresaDataSource
){
    val empresa = empresaRequest.toModel()
    val response = empresaDataSource.saveEmpresa(empresa)
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