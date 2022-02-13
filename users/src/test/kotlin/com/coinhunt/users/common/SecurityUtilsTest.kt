package com.coinhunt.users.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class SecurityUtilsTest {

    @Test
    fun `generates the same hash for the same password consistently`() {
        val input = "jafn32nkkg4"

        val hashedPassword1 = SecurityUtils.hashPassword(input)
        val hashedPassword2 = SecurityUtils.hashPassword(input)

        assertEquals(hashedPassword1, hashedPassword2)
    }

    @Test
    fun `generates different hashes for different passwords`() {
        val input1 = "jafn32nkkg4"
        val input2 = "jafn22nkkg4"

        val hashedPassword1 = SecurityUtils.hashPassword(input1)
        val hashedPassword2 = SecurityUtils.hashPassword(input2)

        assertNotEquals(hashedPassword1, hashedPassword2)
    }

    @Test
    fun `encoding and decoding JWTs are each other's inverse`() {
        val originalSubject = "3i2rkfkeos"

        val encodedToken = SecurityUtils.encodeJwtToken(originalSubject)
        val decodedSubject = SecurityUtils.decodeJwtToken(encodedToken)

        assertEquals(decodedSubject, originalSubject)
    }
}
