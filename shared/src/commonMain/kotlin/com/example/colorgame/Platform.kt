package com.example.colorgame

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform