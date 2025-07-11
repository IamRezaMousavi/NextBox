package org.cloud99p.nextbox

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
