package com.loanapp.domain.valueobject

import com.loanapp.domain.shared.valueobject.Phone
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class PhoneTest {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "+99999999999",
            "+12345678901234",
            "99999999999",
            "12345678901234",
        ],
    )
    fun `should accept valid phone numbers`(number: String) {
        assertDoesNotThrow { Phone(number) }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "+(99)999999999",
            "+123-456-789-01234",
            "(99)9999-99999",
            "123-456-789-01234",
        ],
    )
    fun `should reject a invalid Email`(number: String) {
        val exception =
            assertThrows<IllegalArgumentException> {
                Phone(number)
            }
        assertEquals(exception.message, "Invalid phone number: $number")
    }
}
