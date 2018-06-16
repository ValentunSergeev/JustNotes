package com.valentun.justnotes.extensions

fun <T> List<T>.copy() = this.toMutableList()

fun Any?.isNull() = this == null