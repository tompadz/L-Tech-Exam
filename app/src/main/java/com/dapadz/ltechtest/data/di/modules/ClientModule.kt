package com.dapadz.ltechtest.data.di.modules

import android.util.Log
import com.dapadz.ltechtest.data.cli.httpClient
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import java.util.concurrent.TimeUnit

const val API_URL = "http://dev-exam.l-tech.ru"

val clientModule = DI.Module(
    name = "ClientModule",
    init = {
        bind<HttpClient>() with singleton {
            httpClient {
                install(ContentNegotiation) {
                    gson()
                }
                install(Logging) {
                    logger = object: Logger {
                        override fun log(message: String) {
                            Log.i("HTTP Client", message)
                        }
                    }
                    level = LogLevel.BODY
                }
                defaultRequest {
                    url(API_URL)
                }
            }
        }
    })