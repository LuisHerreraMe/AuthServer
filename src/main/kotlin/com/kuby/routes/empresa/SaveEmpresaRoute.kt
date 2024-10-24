package com.kuby.routes.empresa

import com.kuby.domain.repository.EmpresaDataSource
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.saveEmpresaRoute(
    app: Application,
    empresaDataSource: EmpresaDataSource
) {
    authenticate("another-auth"){
        get("/{id}") {

        }
    }
}