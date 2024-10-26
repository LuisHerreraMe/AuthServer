package com.kuby.routes.empresa

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
import com.kuby.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.saveEmpresaRoute(
    empresaDataSource: EmpresaDataSource
) {
    authenticate("another-auth"){
        post() {
            try {
                val empresaRequest = call.receive<Empresa>()

                saveEmpresaToDatabase(
                    empresaRequest,
                    empresaDataSource
                )

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

private suspend fun Empresa.toModel(): Empresa = Empresa(
    nombre = this.nombre,
    nit = this.nit,
    logo = this.logo,
    contacto = this.contacto
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