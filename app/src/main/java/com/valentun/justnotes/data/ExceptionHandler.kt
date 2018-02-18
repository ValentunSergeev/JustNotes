package com.valentun.justnotes.data

fun getErrorMessage(error: Throwable) = error.message ?: "Unknown Error"