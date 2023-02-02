package io.snabble.pay.network.provider

import io.snabble.pay.network.service.register.dto.AppCredentialsDto

interface AppCredentialsProvider {

    suspend fun getAppCredentials(): AppCredentialsDto?
}
