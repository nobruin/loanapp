package com.loanapp.domain.valueobject

import com.loanapp.domain.shared.valueobject.Cpf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CpfTest {

    @Test
    fun `should accept a valid cpf`() {
        val cpf = Cpf("12345678901")
        assertTrue {  cpf.value == "12345678901" }
    }

    @Test
    fun `should reject the invalid cpf`() {
        val inValidValue = "123.456.789-01"
        val exception =  assertThrows<IllegalArgumentException> {
             Cpf(inValidValue)
         }
        assertEquals(exception.message,"Invalid CPF: $inValidValue" )
    }
}