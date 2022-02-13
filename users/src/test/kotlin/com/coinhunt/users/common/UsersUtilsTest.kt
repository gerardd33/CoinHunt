package com.coinhunt.users.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class UsersUtilsTest {

    @Test
    fun `generates the same hash for the same password consistently`() {
        val input = "jafn32nkkg4"

        val hashedPassword1 = UsersUtils.hashPassword(input)
        val hashedPassword2 = UsersUtils.hashPassword(input)

        assertEquals(hashedPassword1, hashedPassword2)
    }

    @Test
    fun `generates different hashes for different passwords`() {
        val input1 = "jafn32nkkg4"
        val input2 = "jafn22nkkg4"

        val hashedPassword1 = UsersUtils.hashPassword(input1)
        val hashedPassword2 = UsersUtils.hashPassword(input2)

        assertNotEquals(hashedPassword1, hashedPassword2)
    }
}
