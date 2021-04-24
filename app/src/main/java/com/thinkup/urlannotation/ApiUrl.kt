package com.thinkup.urlannotation

@Target(AnnotationTarget.CLASS)
annotation class ApiUrl(
    val url: String
)