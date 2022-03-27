package com.example.servicesamplekotlin

import android.app.Application

class Time: Application() {

    var time: Int? = null

    companion object {
        private var instance : Time? = null

        fun  getInstance(): Time {
            if (instance == null)
                instance = Time()

            return instance!!
        }
    }



}
