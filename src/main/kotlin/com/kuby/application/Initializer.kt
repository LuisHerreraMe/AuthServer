package com.kuby.application


import com.kuby.domain.model.Permiso
import com.kuby.domain.model.Rol
import com.kuby.domain.repository.PermisoDataSource
import com.kuby.domain.repository.RolDataSource
import com.kuby.domain.repository.UserDataSource
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject
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
            permisos = permisos.map { it.id }
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
}