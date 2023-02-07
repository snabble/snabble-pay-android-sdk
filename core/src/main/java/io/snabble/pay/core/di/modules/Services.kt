package io.snabble.pay.core.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.snabble.pay.core.SnabblePay
import io.snabble.pay.network.retrofit.ApiResultCallAdapterFactory
import io.snabble.pay.network.service.register.AppRegistrationService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val services = module {
    factory(named("snabblePayUrl")) { SnabblePay.baseUrl } bind String::class

    single {
        OkHttpClient.Builder()
            .build()
    } bind OkHttpClient::class

    @OptIn(ExperimentalSerializationApi::class)
    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResultCallAdapterFactory.create())
            .baseUrl(get<String>(named("snabblePayUrl")))
            .build()
    } bind Retrofit::class

    single { get<Retrofit>().create(AppRegistrationService::class.java) } bind AppRegistrationService::class
}
