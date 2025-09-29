package com.loanapp.domain.valueobject

import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EmailTest {

    @Test
    fun `should accept a valid Email`(){
        val email = Email("test@test.com")

        assertTrue { email.value == "test@test.com" }
    }

    @Test
    fun `should reject a invalid Email`(){
        val inValidValue = "ABC11"
        val exception =  assertThrows<IllegalArgumentException> {
            Email(inValidValue)
        }
        assertEquals(exception.message,"Invalid email: $inValidValue" )
    }
}