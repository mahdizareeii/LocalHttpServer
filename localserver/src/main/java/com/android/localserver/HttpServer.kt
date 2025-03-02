package com.android.localserver

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.stop
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.slf4j.event.Level
import java.util.concurrent.TimeUnit


class HttpServer {
    private var server: NettyApplicationEngine? = null

    fun initServer() {
        server = embeddedServer(
            factory = Netty,
            port = 8080,
            configure = {
                requestReadTimeoutSeconds = 10
                responseWriteTimeoutSeconds = 10

            },
            module = {
                install(CallLogging) {
                    level = Level.INFO
                }
                install(ContentNegotiation) {
                    gson() {
                        setPrettyPrinting()
                    }
                }

                routing {
                    get("/test") {
                        log.info("test called")
                        call.respond(mapOf("message" to "hello mahdi"))
                    }

                    get("/") {
                        log.info("home called")
                        call.respond(mapOf("message" to "hello mahdi"))
                    }
                }
            }
        )
    }

    fun start() {
        server?.start(true)
    }

    fun stop() {
        server?.stop(0, 0, TimeUnit.MILLISECONDS)
    }
}