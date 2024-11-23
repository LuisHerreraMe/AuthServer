package com.kuby.application


import com.kuby.domain.region.model.Ciudad
import com.kuby.domain.region.model.Region
import com.kuby.domain.region.repocitory.RCDataSource
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
}

fun initializeRegionsAndCities() = runBlocking {
    val rcDataSource: RCDataSource by inject(RCDataSource::class.java)

    // Regiones iniciales
    val initialRegions = listOf(
        Region(id = "1", nombre = "Amazonas"),
        Region(id = "2", nombre = "Antioquia"),
        Region(id = "3", nombre = "Arauca"),
        Region(id = "4", nombre = "Atlántico"),
        Region(id = "5", nombre = "Bolívar"),
        Region(id = "6", nombre = "Boyacá"),
        Region(id = "7", nombre = "Caldas"),
        Region(id = "8", nombre = "Caquetá"),
        Region(id = "9", nombre = "Casanare"),
        Region(id = "10", nombre = "Cauca"),
        Region(id = "11", nombre = "Cesar"),
        Region(id = "12", nombre = "Chocó"),
        Region(id = "13", nombre = "Córdoba"),
        Region(id = "14", nombre = "Cundinamarca"),
        Region(id = "15", nombre = "Guainía"),
        Region(id = "16", nombre = "Guaviare"),
        Region(id = "17", nombre = "Huila"),
        Region(id = "18", nombre = "La Guajira"),
        Region(id = "19", nombre = "Magdalena"),
        Region(id = "20", nombre = "Meta"),
        Region(id = "21", nombre = "Nariño"),
        Region(id = "22", nombre = "Norte de Santander"),
        Region(id = "23", nombre = "Putumayo"),
        Region(id = "24", nombre = "Quindío"),
        Region(id = "25", nombre = "Risaralda"),
        Region(id = "26", nombre = "San Andrés y Providencia"),
        Region(id = "27", nombre = "Santander"),
        Region(id = "28", nombre = "Sucre"),
        Region(id = "29", nombre = "Tolima"),
        Region(id = "30", nombre = "Valle del Cauca"),
        Region(id = "31", nombre = "Vaupés"),
        Region(id = "32", nombre = "Vichada"),
        Region(id = "33", nombre = "Bogotá D.C.")
    )


    // Ciudades iniciales
    val initialCities = listOf(
        // Amazonas
        Ciudad(id = "1", idRegion = "1", nombre = "Leticia"),
        Ciudad(id = "2", idRegion = "1", nombre = "Puerto Nariño"),

        // Antioquia
        Ciudad(id = "3", idRegion = "2", nombre = "Medellín"),
        Ciudad(id = "4", idRegion = "2", nombre = "Bello"),
        Ciudad(id = "5", idRegion = "2", nombre = "Itagüí"),
        Ciudad(id = "6", idRegion = "2", nombre = "Envigado"),
        Ciudad(id = "7", idRegion = "2", nombre = "Rionegro"),

        // Arauca
        Ciudad(id = "8", idRegion = "3", nombre = "Arauca"),
        Ciudad(id = "9", idRegion = "3", nombre = "Arauquita"),
        Ciudad(id = "10", idRegion = "3", nombre = "Saravena"),

        // Atlántico
        Ciudad(id = "11", idRegion = "4", nombre = "Barranquilla"),
        Ciudad(id = "12", idRegion = "4", nombre = "Soledad"),
        Ciudad(id = "13", idRegion = "4", nombre = "Malambo"),
        Ciudad(id = "14", idRegion = "4", nombre = "Sabanalarga"),

        // Bolívar
        Ciudad(id = "15", idRegion = "5", nombre = "Cartagena"),
        Ciudad(id = "16", idRegion = "5", nombre = "Magangué"),
        Ciudad(id = "17", idRegion = "5", nombre = "Turbaco"),
        Ciudad(id = "18", idRegion = "5", nombre = "Arjona"),

        // Boyacá
        Ciudad(id = "19", idRegion = "6", nombre = "Tunja"),
        Ciudad(id = "20", idRegion = "6", nombre = "Duitama"),
        Ciudad(id = "21", idRegion = "6", nombre = "Sogamoso"),
        Ciudad(id = "22", idRegion = "6", nombre = "Chiquinquirá"),

        // Caldas
        Ciudad(id = "23", idRegion = "7", nombre = "Manizales"),
        Ciudad(id = "24", idRegion = "7", nombre = "La Dorada"),
        Ciudad(id = "25", idRegion = "7", nombre = "Villamaría"),

        // Caquetá
        Ciudad(id = "26", idRegion = "8", nombre = "Florencia"),
        Ciudad(id = "27", idRegion = "8", nombre = "Morelia"),

        // Casanare
        Ciudad(id = "28", idRegion = "9", nombre = "Yopal"),
        Ciudad(id = "29", idRegion = "9", nombre = "Aguazul"),

        // Cauca
        Ciudad(id = "30", idRegion = "10", nombre = "Popayán"),
        Ciudad(id = "31", idRegion = "10", nombre = "Santander de Quilichao"),

        // Cesar
        Ciudad(id = "32", idRegion = "11", nombre = "Valledupar"),
        Ciudad(id = "33", idRegion = "11", nombre = "Aguachica"),

        // Chocó
        Ciudad(id = "34", idRegion = "12", nombre = "Quibdó"),
        Ciudad(id = "35", idRegion = "12", nombre = "Istmina"),

        // Córdoba
        Ciudad(id = "36", idRegion = "13", nombre = "Montería"),
        Ciudad(id = "37", idRegion = "13", nombre = "Lorica"),

        // Cundinamarca
        Ciudad(id = "38", idRegion = "14", nombre = "Bogotá D.C."),
        Ciudad(id = "39", idRegion = "14", nombre = "Soacha"),
        Ciudad(id = "40", idRegion = "14", nombre = "Girardot"),

        // Guainía
        Ciudad(id = "41", idRegion = "15", nombre = "Inírida"),

        // Guaviare
        Ciudad(id = "42", idRegion = "16", nombre = "San José del Guaviare"),

        // Huila
        Ciudad(id = "43", idRegion = "17", nombre = "Neiva"),
        Ciudad(id = "44", idRegion = "17", nombre = "Pitalito"),

        // La Guajira
        Ciudad(id = "45", idRegion = "18", nombre = "Riohacha"),
        Ciudad(id = "46", idRegion = "18", nombre = "Maicao"),

        // Magdalena
        Ciudad(id = "47", idRegion = "19", nombre = "Santa Marta"),
        Ciudad(id = "48", idRegion = "19", nombre = "Ciénaga"),

        // Meta
        Ciudad(id = "49", idRegion = "20", nombre = "Villavicencio"),
        Ciudad(id = "50", idRegion = "20", nombre = "Acacías"),

        // Nariño
        Ciudad(id = "51", idRegion = "21", nombre = "Pasto"),
        Ciudad(id = "52", idRegion = "21", nombre = "Ipiales"),

        // Norte de Santander
        Ciudad(id = "53", idRegion = "22", nombre = "Cúcuta"),
        Ciudad(id = "54", idRegion = "22", nombre = "Ocaña"),

        // Putumayo
        Ciudad(id = "55", idRegion = "23", nombre = "Mocoa"),

        // Quindío
        Ciudad(id = "56", idRegion = "24", nombre = "Armenia"),

        // Risaralda
        Ciudad(id = "57", idRegion = "25", nombre = "Pereira"),
        Ciudad(id = "58", idRegion = "25", nombre = "Dosquebradas"),

        // San Andrés y Providencia
        Ciudad(id = "59", idRegion = "26", nombre = "San Andrés"),

        // Santander
        Ciudad(id = "60", idRegion = "27", nombre = "Bucaramanga"),
        Ciudad(id = "61", idRegion = "27", nombre = "Floridablanca"),

        // Sucre
        Ciudad(id = "62", idRegion = "28", nombre = "Sincelejo"),

        // Tolima
        Ciudad(id = "63", idRegion = "29", nombre = "Ibagué"),
        Ciudad(id = "64", idRegion = "29", nombre = "Espinal"),

        // Valle del Cauca
        Ciudad(id = "65", idRegion = "30", nombre = "Cali"),
        Ciudad(id = "66", idRegion = "30", nombre = "Palmira"),

        // Vaupés
        Ciudad(id = "67", idRegion = "31", nombre = "Mitú"),

        // Vichada
        Ciudad(id = "68", idRegion = "32", nombre = "Puerto Carreño")
    )


    // Verificar si ya existen regiones en la base de datos
    val regionsInDb = rcDataSource.getRegion()
    if (regionsInDb.isEmpty()) {
        initialRegions.forEach { rcDataSource.saveRegion(it) }
        println("Regiones iniciales insertadas en la base de datos.")
    } else {
        println("Las regiones ya existen en la base de datos.")
    }

    // Verificar si ya existen ciudades en la base de datos
    val citiesInDb = rcDataSource.getCiudad()
    if (citiesInDb.isEmpty()) {
        initialCities.forEach { rcDataSource.saveCiudad(it) }
        println("Ciudades iniciales insertadas en la base de datos.")
    } else {
        println("Las ciudades ya existen en la base de datos.")
    }
}