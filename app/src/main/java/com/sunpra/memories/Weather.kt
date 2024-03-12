package com.sunpra.memories

class Weather(
    private val city: String,
    private val lowTemperature: Int,
    private val highTemperature: Int,
    private val chancesOfRain: Int,
) {
    fun printWeather() {
        println("City: $city")
        println("Low temperature: $lowTemperature, High temperaure: $highTemperature")
        println("Chances of rain: $chancesOfRain%")
    }
}

class AnotherWeather {
    private val city: String
    private val lowTemperature: Int
    private val highTemperature: Int
    private val chancesOfRain: Int

    constructor(city: String, lowTemperature: Int, highTemperature: Int, chancesOfRain: Int) {
        this.city = city
        this.lowTemperature = lowTemperature
        this.highTemperature = highTemperature
        this.chancesOfRain = chancesOfRain
    }

    fun printWeather(paramOne: String) : String {
        val booleanAnswerQuestion =
            Question<Boolean>("Have you ever visited, Pokhara?", true, Difficulty.Hard)
        println("City: $city")
        println("Low temperature: $lowTemperature, High temperaure: $highTemperature")
        println("Chances of rain: $chancesOfRain%")
        return  ""
    }
}

class Question<T>(
    private val questionText: String,
    private val answer: T,
    private val difficulty: Difficulty
)

enum class Difficulty{
    Hard, Medium, Low
}

fun addList(intigers: List<Int>): Int = intigers.sum()

