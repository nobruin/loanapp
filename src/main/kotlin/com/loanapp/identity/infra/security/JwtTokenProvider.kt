package com.loanapp.identity.infra.security

import com.loanapp.identity.domain.TokenProvider
import com.loanapp.identity.domain.UserId
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @field:Value("\${jwt.secret}")
    private val secretWord: String,
    private val expirationMillis: Long = TimeUnit.HOURS.toMillis(1) // 1h
) : TokenProvider {
    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secretWord.toByteArray(Charsets.UTF_8))
    }

    override fun generateToken(userId: UserId): String {
        val now = Date()
        val expiry = Date(now.time + expirationMillis)

        return Jwts.builder()
            .subject(userId.value.toString())
            .issuedAt(now)
            .expiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    override fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
            true
        } catch (ex: ExpiredJwtException) {
            println("Expired Token: ${ex.message}")
            false
        } catch (ex: JwtException) {
            println("Invalid token: ${ex.message}")
            false
        } catch (ex: Exception) {
            println("An error occurred: ${ex.message}")
            false
        }
    }

    override fun extractUserId(token: String): UserId? {
        return try {
            val claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload

            val subject = claims.subject ?: return null
            UserId(UUID.fromString(subject))
        } catch (ex: JwtException) {
            println("An error occurred: ${ex.message}")
            null
        }
    }
}