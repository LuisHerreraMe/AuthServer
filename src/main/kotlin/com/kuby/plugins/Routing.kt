package com.kuby.plugins

import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.EndPoint
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.domain.user.repository.UserDataSource
import com.kuby.routes.empresa.*
import com.kuby.routes.rol.getRolByIdRoute
import com.kuby.routes.sucursal.getSucursalByIdEmpresaRoute
import com.kuby.routes.sucursal.saveSucursalRoute
import com.kuby.routes.user.*
import com.kuby.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

fun Application.configureRouting(
    jwtService: JwtService,
) {
    routing {
        val userDataSource: UserDataSource by KoinJavaComponent.inject(UserDataSource::class.java)
        val empresaDataSource: EmpresaDataSource by KoinJavaComponent.inject(EmpresaDataSource::class.java)
        val sucursalDataSource: SucursalDataSource by KoinJavaComponent.inject(SucursalDataSource::class.java)
        val rolDataSource: RolDataSource by KoinJavaComponent.inject(RolDataSource::class.java)

        route(EndPoint.Auth.path) {
            signUpRoute(jwtService, userDataSource)
            signInRoute(jwtService, userDataSource)
        }

        route(EndPoint.DataUser.path) {
            getUserRoute(application,userDataSource)
            updateUserRoute(application,userDataSource)
            deleteUserRoute(application,userDataSource)
        }

        route(EndPoint.DataEmpresa.path) {
            getEmpresaByNameRoute(empresaDataSource)
            getEmpresaRoute(empresaDataSource)
            saveEmpresaRoute(empresaDataSource)
            deleteEmpresaRoute(empresaDataSource)
            updateEmpresaRoute(empresaDataSource)
            getEmpresaByIdRoute(empresaDataSource)
        }

        route(EndPoint.DataSucursal.path){
            saveSucursalRoute(sucursalDataSource)
            getSucursalByIdEmpresaRoute(sucursalDataSource)
        }

        route(EndPoint.DataRol.path){
            getRolByIdRoute(rolDataSource)
        }


    }
}