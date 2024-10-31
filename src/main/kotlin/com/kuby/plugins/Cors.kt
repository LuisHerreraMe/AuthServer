package com.kuby.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.* // Para HttpMethod y HttpHeaders


fun Application.configureCors() {
    install(CORS) {
        anyHost() // Solo en desarrollo, en producción usa allowHost("https://frontend.com")
        allowMethod(HttpMethod.Options) // Permitir preflight requests
        allowMethod(HttpMethod.Get) // Permitir metodo GET
        allowMethod(HttpMethod.Post) // Permitir metodo POST
        allowMethod(HttpMethod.Put) // Permitir metodo PUT
        allowMethod(HttpMethod.Delete) // Permitir metodo DELETE
        allowMethod(HttpMethod.Patch) // Permitir metodo PATCH
        allowHeader(HttpHeaders.ContentType) // Permitir header para JSON
        allowHeader(HttpHeaders.Authorization) // Si usas autenticación por token
        allowCredentials = true // Si envías cookies o credenciales
    }
}
