package io.github.aleksandersh.mysocialnetworkphotos

import android.app.Application
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeDependencies()
    }

    private fun initializeDependencies() {
        Tree.init(applicationContext)
    }
}