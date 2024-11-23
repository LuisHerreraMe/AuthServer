package com.kuby.plugins

import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.EndPoint
import com.kuby.domain.region.repocitory.RCDataSource
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.kuby.domain.servicio.repocitory.ServicioDataSource
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.domain.user.repository.UserDataSource
import com.kuby.routes.RegionSiudad.getCiudadByIdRegionRoute
import com.kuby.routes.RegionSiudad.getRegionRoute
import com.kuby.routes.empresa.*
import com.kuby.routes.rol.getRolByIdRoute
import com.kuby.routes.servicio.getServicioByIdRoute
import com.kuby.routes.servicio.getServicioByIdSedeRoute
import com.kuby.routes.servicio.getServicioRoute
import com.kuby.routes.servicio.saveServicioRoute
import com.kuby.routes.sucursal.*
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
        val rcDataSource: RCDataSource by KoinJavaComponent.inject(RCDataSource::class.java)
        val servicioDataSource: ServicioDataSource by KoinJavaComponent.inject(ServicioDataSource::class.java)

        route(EndPoint.Auth.path) {
            signUpRoute(jwtService, userDataSource)
            signInRoute(jwtService, userDataSource)
        }

        route(EndPoint.DataUser.path) {
            getUserByIdRoute(userDataSource)
            getUserRoute(userDataSource)
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
            getSucursalRoute(sucursalDataSource, servicioDataSource)
            getSucursalByIdEmpresaRoute(sucursalDataSource)
            deleteSucursalRoute(sucursalDataSource)
            getSucursalByIdRoute(sucursalDataSource)
        }

        route(EndPoint.DataRol.path){
            getRolByIdRoute(rolDataSource)
        }

        route(EndPoint.DataCiudad.path){
            getCiudadByIdRegionRoute(rcDataSource)
        }

        route(EndPoint.DataRegion.path){
            getRegionRoute(rcDataSource)
        }

        route(EndPoint.DataServicio.path){
            getServicioRoute(servicioDataSource)
            saveServicioRoute(servicioDataSource)
            getServicioByIdRoute(servicioDataSource)
            getServicioByIdSedeRoute(servicioDataSource)
        }




    }
}