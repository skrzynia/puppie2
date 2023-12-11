package org.wit.puppie2.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.puppie2.R
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainActivity : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Start Me Up")
    }
}