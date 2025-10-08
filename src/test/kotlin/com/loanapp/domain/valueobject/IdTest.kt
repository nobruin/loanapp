package com.loanapp.domain.valueobject

import com.loanapp.domain.shared.valueobject.Id
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class IdTest {
    @Test
    fun `should create an Id with new`() {
        val id1 = Id.new<Any>()
        val id2 = Id.new<Any>()

        assertNotEquals(id1,id2)
        assertTrue {
            id1.toString().isNotBlank() && id2.toString().isNotBlank()
        }
    }

    @Test
    fun `should create an Id with valid UUID`(){
        val uuid = UUID.randomUUID()
        val id = Id.fromString<Any>(uuid.toString())

        assertTrue { id.toString() == uuid.toString() }
    }

    @Test
    fun `should throw exception for blank Id`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Id.fromString<Any>("")
        }

        assertEquals("Id cannot be blank", exception.message)
    }

    @Test
    fun `should throw exception for invalid UUID format`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Id.fromString<Any>("not-a-uuid")
        }

        assertTrue(exception.message!!.contains("Invalid UUID format"))
    }

    @Test
    fun `should compare Ids by value`() {
        val uuid = UUID.randomUUID()
        val id1 = Id.fromString<Any>(uuid.toString())
        val id2 = Id.fromString<Any>(uuid.toString())

        assertEquals(id1, id2)
        assertEquals(id1.hashCode(), id2.hashCode())
    }
}