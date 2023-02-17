package io.snabble.pay.core.appcredentials.data

import io.snabble.pay.core.appcredentials.domain.model.toAppCredentialsDto
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.network.provider.AppCredentialsProvider
import io.snabble.pay.network.service.register.dto.AppCredentialsDto

class AppCredentialsProviderImpl(
    private val appCredentialsRepository: AppCredentialsRepository,
) : AppCredentialsProvider {

    override suspend fun getAppCredentials(): AppCredentialsDto? =
        appCredentialsRepository.getAppCredentials()?.toAppCredentialsDto()
}
