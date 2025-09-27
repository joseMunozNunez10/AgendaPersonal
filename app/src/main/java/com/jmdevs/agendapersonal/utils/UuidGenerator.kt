package com.jmdevs.agendapersonal.util

import java.util.UUID

object UuidGenerator {
    fun generate(): String = UUID.randomUUID().toString()
}
