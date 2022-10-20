package com.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val counterStorage = mutableMapOf<String, Int>()

fun Route.addCounter(){
    route("/create") {
        get("{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )
            counterStorage.put(name, 1)
            call.respondText("Counter stored correctly", status = HttpStatusCode.Created)
        }
    }
}

fun Route.getCounters() {
    get {
        if (counterStorage.isNotEmpty()) {
            val values: MutableList<String> = mutableListOf<String>()
            for ((key, value) in counterStorage) {
                values.add("$key = $value")
            }
            call.respond(values)
        } else {
            call.respondText("No counters found", status = HttpStatusCode.OK)
        }
    }
}

fun Route.getCounter(){
    get("{name}") {
        val name = call.parameters["name"] ?: return@get call.respondText(
            "Missing name",
            status = HttpStatusCode.BadRequest
        )
        val value = counterStorage.get(name)
        if(value != null)
            call.respond(value)
        else
            call.respondText("counter not present", status = HttpStatusCode.NotFound)
    }
}

fun Route.incrementCounter(){
    route("/increment") {
        get("{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )
            val value = counterStorage.get(name)
            if(value != null) {
                counterStorage.merge(name, 1, Int::plus)
                call.respondText("Counter updated correctly", status = HttpStatusCode.Created)
            }
            else
                call.respondText("counter not present", status = HttpStatusCode.NotFound)
        }
    }
}

