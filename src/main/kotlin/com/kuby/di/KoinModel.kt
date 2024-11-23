package com.kuby.di


import EmpresaDataSourceImpl
import RCDataSourceImpl
import SucursalDataSourceImpl
import com.kuby.infrastructure.datasource.PermisoDataSourceImpl
import com.kuby.infrastructure.datasource.RolDataSourceImpl
import com.kuby.infrastructure.datasource.UserDataSourceImpl
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.region.repocitory.RCDataSource
import com.kuby.domain.rol_permiso.repository.PermisoDataSource
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.kuby.domain.servicio.repocitory.ServicioDataSource
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.domain.user.repository.UserDataSource
import com.kuby.infrastructure.datasource.ServicioDataSourceImpl
import com.kuby.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val KoinModule = module {
    single {
        KMongo.createClient(System.getenv("MONGODB_URI"))
            .coroutine
            .getDatabase(DATABASE_NAME)
    }
    single<RolDataSource> { RolDataSourceImpl(get()) }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<PermisoDataSource> { PermisoDataSourceImpl(get()) }
    single<EmpresaDataSource> { EmpresaDataSourceImpl(get()) }
    single<SucursalDataSource> { SucursalDataSourceImpl(get()) }
    single<ServicioDataSource> { ServicioDataSourceImpl(get()) }
    single<RCDataSource> { RCDataSourceImpl(get()) }
}