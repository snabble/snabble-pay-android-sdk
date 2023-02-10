package io.snabble.pay.core.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.snabble.pay.core.SnabblePay
import io.snabble.pay.network.okhttp.authenticator.PayAuthenticator
import io.snabble.pay.network.okhttp.interceptor.AuthorizationHeaderInterceptor
import io.snabble.pay.network.retrofit.ApiResultCallAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit

internal val networkModule = module {
    factory(named(SNABBLE_PAY_URL)) { SnabblePay.baseUrl } bind String::class

    single(named(AUTH_FREE_OK_HTTP_CLIENT)) {
        OkHttpClient.Builder().build()
    } bind OkHttpClient::class

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(AuthorizationHeaderInterceptor(getAccessToken = get()))
            .authenticator(PayAuthenticator(getNewAccessToken = get()))
            .build()
    } bind OkHttpClient::class

    factory { Json { ignoreUnknownKeys = true; isLenient = true } } bind Json::class

    factory(named(RETROFIT_JSON_CONVERTER_FACTORY)) {
        @OptIn(ExperimentalSerializationApi::class)
        get<Json>().asConverterFactory("application/json".toMediaType())
    } bind Converter.Factory::class

    factory { ApiResultCallAdapterFactory.create() } bind ApiResultCallAdapterFactory::class

    single(named(AUTH_FREE_RETROFIT)) {
        Retrofit.Builder()
            .client(get(named(AUTH_FREE_OK_HTTP_CLIENT)))
            .addConverterFactory(get(named(RETROFIT_JSON_CONVERTER_FACTORY)))
            .addCallAdapterFactory(get<ApiResultCallAdapterFactory>())
            .baseUrl(get<String>(named(SNABBLE_PAY_URL)))
            .build()
    } bind Retrofit::class

    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get(named(RETROFIT_JSON_CONVERTER_FACTORY)))
            .addCallAdapterFactory(get<ApiResultCallAdapterFactory>())
            .baseUrl(get<String>(named(SNABBLE_PAY_URL)))
            .build()
    } bind Retrofit::class
}

internal const val AUTH_FREE_OK_HTTP_CLIENT = "Auth-Free-OkHttpClient"
internal const val AUTH_FREE_RETROFIT = "Auth-Free-Retrofit"
internal const val RETROFIT_JSON_CONVERTER_FACTORY = "Retrofit-Json-Converter-Factory"
internal const val SNABBLE_PAY_URL = "Snabble-Pay-Url"
