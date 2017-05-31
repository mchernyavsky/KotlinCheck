package io.kotlincheck

import org.apache.commons.lang3.SerializationException
import org.apache.commons.lang3.SerializationUtils
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Serializable
import javax.sql.rowset.serial.SerialBlob


object CounterexampleStorage {

    init {
        Database.connect("jdbc:h2:./build/counterexamples", driver = "org.h2.Driver")
        transaction { SchemaUtils.create(Counterexamples) }
    }

    fun <P> loadCounterexamples(propositionFullName: String): List<P> = transaction {
        Counterexample
                .find { Counterexamples.propositionName eq propositionFullName }
                .map { it.arguments }
                .map { it.binaryStream }
                .map { SerializationUtils.deserialize<P>(it) }
    }

    fun saveCounterexample(propositionFullName: String, counterexample: Serializable) {
        try {
            val serialized = SerializationUtils.serialize(counterexample)
            transaction {
                Counterexamples.insert {
                    it[propositionName] = propositionFullName
                    it[arguments] = SerialBlob(serialized)
                }
            }
        } catch (e: SerializationException) {
            // ignore
        }
    }
}

object Counterexamples : IntIdTable() {
    val propositionName = varchar("propositionName", 128).index()
    val arguments = blob("arguments")
}

class Counterexample(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Counterexample>(Counterexamples)

    var propositionName by Counterexamples.propositionName
    var arguments by Counterexamples.arguments
}
