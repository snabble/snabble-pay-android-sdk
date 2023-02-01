package io.snabble.pay.network.okhttp

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.network.repository.AccessToken
import okhttp3.Request

class OkHttpExtensionsTest : FreeSpec({

    "newWithAuthorizationHeader(AccessToken) should" - {

        "add the auth header if missing" {
            val accessToken = AccessToken("qwerty")
            val request = Request.Builder().url("https://example.com").build()

            val newRequest = request.newWithAuthorizationHeader(accessToken)

            request.headers.count { it.first == "Authorization" } shouldBe 0
            newRequest.headers.contains("Authorization" to "Bearer qwerty")
        }

        "result in a new request only containing the the header w/ the given AccessToken value" {
            val accessToken = AccessToken("qwerty")
            val request = Request.Builder()
                .url("https://example.com")
                .header("Authorization", "Bearer asdfgh")
                .build()

            val newRequest = request.newWithAuthorizationHeader(accessToken)

            newRequest.headers.count { it.first == "Authorization" } shouldBe 1
            newRequest.headers.contains("Authorization" to "Bearer qwerty")
        }
    }

    "Const" - {

        "for auth header should be correct" {
            AUTH_HEADER shouldBe "Authorization"
        }
    }
})
