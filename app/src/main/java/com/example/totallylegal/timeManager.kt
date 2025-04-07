package com.example.totallylegal

class timeManager {
    private var totalTime: Int = 0

    constructor(parameter: String){
        val hours: Int = parameter.slice(IntRange(0, 2)).toInt()
        val minutes: Int = parameter.slice(IntRange(3, 4)).toInt()

        val totalTime = (hours * 60) + minutes

        this.totalTime = totalTime
    }

    fun getTime(): Int {
        return totalTime;
    }

    // TODO: pass timezone object into this method

    fun setTime(parameter: String) {
        val hours: Int = parameter.slice(IntRange(0, 2)).toInt()
        val minutes: Int = parameter.slice(IntRange(3, 4)).toInt()

        this.totalTime -= (hours * 60) + minutes
    }

}