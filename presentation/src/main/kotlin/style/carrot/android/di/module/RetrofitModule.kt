/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 22. 1. 23. 오후 1:26
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.di.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jisungbin.logeukes.logeukes
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import style.carrot.android.di.qualifier.SignedRetrofit
import style.carrot.android.secret.SecretConfig

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {
    private const val BaseApiUrl = "https://api.github.com"

    private val mapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .registerKotlinModule()

    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            builder = builder
                .addHeader("Authorization", "token ${SecretConfig.GithubToken}")
                .addHeader("Accept", "application/json")
            return chain.proceed(builder.build())
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor { message -> logeukes("OkHttp") { message } }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @SignedRetrofit
    @ViewModelScoped
    fun provideSignedRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BaseApiUrl)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .client(getInterceptor(provideHttpLoggingInterceptor(), AuthInterceptor()))
        .build()
}
