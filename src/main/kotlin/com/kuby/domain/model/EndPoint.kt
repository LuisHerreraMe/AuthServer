package com.kuby.domain.model

sealed class EndPoint (val path: String) {

    data object Auth: EndPoint(path = "/api/v1/auth")
    data object DataUser: EndPoint(path = "/api/v1/user")
    data object DataEmpresa: EndPoint(path = "/api/v1/empresa")
    data object DataSucursal: EndPoint(path = "/api/v1/sucursal")

    data object DataServicio: EndPoint(path = "/api/v1/servicios")
    data object DataRegion: EndPoint(path = "/api/v1/regiones")
    data object DataCiudad: EndPoint(path = "/api/v1/ciudades")
    data object DataRol: EndPoint(path = "/api/v1/roles")
    data object SignUp: EndPoint(path = "/sign_up")
    data object SignIn: EndPoint(path = "/sign_in")
    data object Unauthorized: EndPoint(path = "/unauthorized")
}