package com.kuby

import com.kuby.plugins.configureCors
import com.kuby.application.initializeDefaultRolesAndPermissions
import com.kuby.application.initializeRegionsAndCities
import com.kuby.plugins.*
import com.kuby.service.JwtService
import io.ktor.server.application.*
import com.kuby.plugins.configureRouting


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    val jwtService = JwtService(this)
    configureCors()
    configureKoin()
    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(jwtService)
    initializeDefaultRolesAndPermissions()
    initializeRegionsAndCities()
}


