package org.wit.puppie2.main

import android.app.Application
import org.wit.puppie2.dao.FStorage
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainActivity : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Start Me Up")
        i("Database started")


    }
}