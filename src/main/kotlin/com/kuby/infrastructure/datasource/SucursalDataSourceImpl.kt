

import com.kuby.domain.sucursal.model.Sucursal
import com.kuby.domain.sucursal.model.UpdateSucursal
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.mongodb.client.model.Filters
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class SucursalDataSourceImpl(
    private val database: CoroutineDatabase
) : SucursalDataSource {

    private val sucursales = database.getCollection<Sucursal>()


    override suspend fun getSucursal(): List<Sucursal?> {
        return sucursales.find().toList()
    }

    override suspend fun getSucursalById(id: String): Sucursal? {
        return sucursales.findOne(Sucursal::id eq id)
    }

    override suspend fun getSucursalByIdEmpresa(idEmpresa: String): List<Sucursal> {
        val filtro = Filters.eq("idEmpresa", idEmpresa)
        return sucursales.find(filtro).toList()
    }

    override suspend fun getSucursalByName(nombre: String): List<Sucursal?> {
        val filtro = Filters.regex("nombre", ".*$nombre.*", "i")
        return sucursales.find(filtro).toList()
    }

    override suspend fun saveSucursal(sucursal: Sucursal): Boolean {
        val existingEmpresa = sucursales.findOne(Sucursal::nombre eq sucursal.nombre)
        return if (existingEmpresa == null) {
            val result = sucursales.insertOne(sucursal)
            result.wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun deleteSucursal(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateSucursal(id: String, updateSucursal: UpdateSucursal): Boolean {
        TODO("Not yet implemented")
    }


}