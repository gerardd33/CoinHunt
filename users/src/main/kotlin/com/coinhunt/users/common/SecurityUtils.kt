package com.coinhunt.users.common

import com.coinhunt.users.api.errors.UnauthorizedException
import com.google.common.hash.Hashing
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

object SecurityUtils {

    private const val JWT_SECRET_KEY = "2pj7312"
    private const val JWT_ISSUER = "Coin Hunt: Users"
    private val JWT_TOKEN_EXPIRATION_MILLIS = TimeUnit.MINUTES.toMillis(20)

    fun hashPassword(password: String): String {
        return Hashing.sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString()
    }

    fun encodeJwtToken(subject: String): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setSubject(subject)
            .claim("roles", "user")
            .setIssuedAt(Date(now))
            .setIssuer(JWT_ISSUER)
            .setExpiration(Date(now + JWT_TOKEN_EXPIRATION_MILLIS))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
            .compact()
    }

    fun decodeJwtToken(jwtToken: String): String {
        try {
            val claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(jwtToken).body

            if (claims.issuer != JWT_ISSUER) {
                throw UnauthorizedException("The issuer is incorrect")
            }

            return claims.subject
        } catch (_: Exception) {
            throw UnauthorizedException("Received JWT token is invalid")
        }
    }
}
