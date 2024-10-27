package com.kuby.application


import com.kuby.domain.rol_permiso.model.Permiso
import com.kuby.domain.rol_permiso.model.Rol
import com.kuby.domain.rol_permiso.repository.PermisoDataSource
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.kuby.util.enviarCorreoPersonalizado
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime

fun initializeDefaultRolesAndPermissions() = runBlocking {
    val rolDataSource: RolDataSource by inject(RolDataSource::class.java)
    val permisoDataSource: PermisoDataSource by inject(PermisoDataSource::class.java)

    // Permisos por defecto
    val permisos = listOf(
        Permiso(nombre = "MANAGE_USERS", createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now(), descripcion = "Permission to manage users"),
        Permiso(nombre = "VIEW_DASHBOARD", createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now(), descripcion = "Permission to view the dashboard"),
        Permiso(nombre = "CREATION_SERVICES", createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now(), descripcion = "Permission to create new services"),
        Permiso(nombre = "MODIFY_SERVICES", createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now(), descripcion = "Permission to modify services")
    )

    permisos.forEach { permiso ->
        permisoDataSource.createPermiso(permiso)
    }

    val roles = listOf(
        Rol(
            nombre = "ADMIN",
            descripcion = "Administrator with full access",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            idPermisos = permisos.map { it.id }
        ),
        Rol(
            nombre = "USER",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            descripcion = "Regular user with limited access"
        )
    )
    roles.forEach { rol ->
        rolDataSource.createRol(rol)
    }

    try {
        val destinatario = "herreramedinaluisdavid54@gmail.com"
        val asunto = "Roles y Permisos Inicializados"
        val plantillaRuta = "src/main/kotlin/com/kuby/resources/templates/correo_plantilla.html"

        val variables = mapOf(
            "titulo" to "Notificación de Inicialización",
            "nombreCliente" to "Juan Pérez",
            "mensaje" to "Los roles y permisos por defecto se han inicializado correctamente.",
            "fecha" to LocalDateTime.now().toString()
        )

        enviarCorreoPersonalizado(destinatario, asunto, plantillaRuta, variables)
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error al enviar el correo de inicialización.")
    }

}