package org.cloud99p.maroon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform