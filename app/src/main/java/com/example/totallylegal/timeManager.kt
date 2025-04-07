package com.example.totallylegal

class timeManager {
    private var totalTime: Int = 0

    public constructor(parameter: String){
        val Hours: Int = parameter.slice(IntRange(0, 2)).toInt()
        val Minutes: Int = parameter.slice(IntRange(3, 4)).toInt()

        val totalTime = (Hours * 60) + Minutes;

        this.totalTime = totalTime
    }

    public fun getTime(): Int {
        return totalTime;
    }

    // TODO: pass timezone object into this method

    public fun setTime(parameter: String) {
        val Hours: Int = parameter.slice(IntRange(0, 2)).toInt()
        val Minutes: Int = parameter.slice(IntRange(3, 4)).toInt()

        this.totalTime -= (Hours * 60) + Minutes;
    }

}