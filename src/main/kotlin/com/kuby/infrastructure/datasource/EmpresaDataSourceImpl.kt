import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import org.bson.conversions.Bson
import org.litote.kmongo.combine
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class EmpresaDataSourceImpl(
    private val database: CoroutineDatabase
) : EmpresaDataSource {

    private val empresas = database.getCollection<Empresa>()

    override suspend fun getEmpresaById(id: String): Empresa? {
        return empresas.findOne(Empresa::id eq id)
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
        return empresas.deleteOne(Empresa::id eq id).wasAcknowledged()
    }

    override suspend fun updateEmpresa(id: String, updateEmpresa: UpdateEmpresa): Boolean {
        val updates = mutableListOf<Bson>()

        updateEmpresa.nombre.let { updates.add(setValue(Empresa::nombre, it)) }
        updateEmpresa.nit.let { updates.add(setValue(Empresa::nit, it)) }
        updateEmpresa.logo.let { updates.add(setValue(Empresa::logo, it)) }
        updateEmpresa.contacto.let { updates.add(setValue(Empresa::contacto, it)) }

        return if (updates.isNotEmpty()) {
            empresas.updateOne(Empresa::id eq id, combine(updates)).wasAcknowledged()
        } else {
            false // No fields to update
        }
    }
}