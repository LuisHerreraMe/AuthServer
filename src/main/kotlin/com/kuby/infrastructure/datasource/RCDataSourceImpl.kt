import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.region.model.Ciudad
import com.kuby.domain.region.model.Region
import com.kuby.domain.region.repocitory.RCDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.regex

class RCDataSourceImpl(
    private val database: CoroutineDatabase
) : RCDataSource {

    private val regiones = database.getCollection<Region>()
    private val ciudades = database.getCollection<Ciudad>()


    //////   REGION /////

    override suspend fun getRegion(): List<Region> {
        return regiones.find().toList()
    }

    override suspend fun getRegionById(id: String): Region? {
        return regiones.findOne(Region::id eq id)
    }

    override suspend fun getRegionByName(nombre: String): List<Region> {
        return regiones.find(Region::nombre regex ".*$nombre.*").toList()
    }

    override suspend fun saveRegion(region: Region): Boolean {
        val existingRegion = regiones.findOne(Region::id eq region.id)
        return if (existingRegion == null) {
            val result = regiones.insertOne(region)
            result.wasAcknowledged()
        } else {
            false
        }
    }

    //////   CIUDAD /////

    override suspend fun getCiudad(): List<Ciudad> {
        return ciudades.find().toList()
    }

    override suspend fun getCiudadByIdRegion(idRegion: String): List<Ciudad> {
        return ciudades.find(Ciudad::idRegion eq idRegion).toList()
    }

    override suspend fun getCiudadByName(nombre: String): List<Ciudad> {
        return ciudades.find(Ciudad::nombre regex ".*$nombre.*").toList()
    }

    override suspend fun saveCiudad(ciudad: Ciudad): Boolean {
        val existingCiudad = ciudades.findOne(Ciudad::id eq ciudad.id)
        return if (existingCiudad == null) {
            val result = ciudades.insertOne(ciudad)
            result.wasAcknowledged()
        } else {
            false
        }
    }
}
