package com.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.routes.*

fun Application.configureRouting() {
    routing {
        route("/counters"){
            addCounter()
            getCounter()
            getCounters()
            incrementCounter()
        }
    }
}
