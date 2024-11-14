import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import org.litote.kmongo.combine
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import java.time.LocalDateTime

class EmpresaDataSourceImpl(
    private val database: CoroutineDatabase
) : EmpresaDataSource {

    private val empresas = database.getCollection<Empresa>()

    override suspend fun getEmpresa(): List<Empresa?> {
        return empresas.find().toList()
    }

    override suspend fun getEmpresaById(id: String): Empresa? {
        return empresas.findOne(Empresa::id eq id)
    }


    override suspend fun getEmpresaByName(nombre: String): List<Empresa> {
        val filtro = Filters.regex("nombre", ".*$nombre.*", "i")
        return empresas.find(filtro).toList()
    }


    override suspend fun saveEmpresa(empresa: Empresa): Boolean {
        val existingEmpresa = empresas.findOne(Empresa::nit eq empresa.nit)
        return if (existingEmpresa == null) {
            val result = empresas.insertOne(empresa)
            result.wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun deleteEmpresa(id: String): Boolean {
        val deleteResult = empresas.deleteOne(Empresa::id eq id)
        return deleteResult.deletedCount > 0
    }

    override suspend fun updateEmpresa(id: String, updateEmpresa: UpdateEmpresa): Boolean {
        val updates = mutableListOf<Bson>()

        updateEmpresa.nombre?.let { updates.add(setValue(UpdateEmpresa::nombre, it)) }
        updateEmpresa.nit?.let { updates.add(setValue(UpdateEmpresa::nit, it)) }
        updateEmpresa.logo?.let { updates.add(setValue(UpdateEmpresa::logo, it)) }
        updateEmpresa.emailAddress?.let { updates.add(setValue(UpdateEmpresa::emailAddress, it)) }
        updateEmpresa.phone?.let { updates.add(setValue(UpdateEmpresa::phone, it)) }
        updateEmpresa.updatedAt?.let { updates.add(setValue(UpdateEmpresa::updatedAt, it)) }


        return if (updates.isNotEmpty()) {
            // Ejecuta la actualización y verifica si se encontró y actualizó un documento
            val result = empresas.updateOne(Empresa::id eq id, combine(updates))
            result.matchedCount > 0 && result.wasAcknowledged()
        } else {
            false // No hay campos para actualizar
        }
    }

}