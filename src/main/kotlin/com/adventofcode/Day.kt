package com.adventofcode

fun process(line: String) {
  TODO()
}

fun solution(): Long {
  TODO()
}

fun main() {
  ::main
    .javaClass
    .getResourceAsStream("/input")!!
    .bufferedReader()
    .forEachLine(::process)
  println(solution())
}