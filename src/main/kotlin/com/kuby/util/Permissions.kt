package com.kuby.util

import com.kuby.domain.rol_permiso.repository.PermisoDataSource
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.kuby.domain.user.repository.UserDataSource
import org.koin.java.KoinJavaComponent

suspend fun Permissions(idUsuario: String, nombrePermiso: String): Boolean {

    val userDataSource: UserDataSource by KoinJavaComponent.inject(UserDataSource::class.java)
    val rolDataSource: RolDataSource by KoinJavaComponent.inject(RolDataSource::class.java)
    val permisoDataSource: PermisoDataSource by KoinJavaComponent.inject(PermisoDataSource::class.java)

    val usuario = userDataSource.getUserInfoById(idUsuario) ?: return false

    val rolesUsuario = usuario.idRol.mapNotNull { rolId ->
        rolDataSource.getRolById(rolId)
    }

    val permisosUsuario = rolesUsuario.flatMap { rol ->
        rol.idPermisos.mapNotNull { permisoId ->
            permisoDataSource.getPermisoById(permisoId)?.nombre
        }
    }

    return permisosUsuario.contains(nombrePermiso)
}