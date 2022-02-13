package com.coinhunt.users.common

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

object UsersUtils {

    fun hashPassword(password: String): String {
        return Hashing.sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString()
    }
}
