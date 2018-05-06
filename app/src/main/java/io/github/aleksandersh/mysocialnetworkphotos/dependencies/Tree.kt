package io.github.aleksandersh.mysocialnetworkphotos.dependencies

import android.content.Context
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponentHolder

object Tree {

    lateinit var applicationComponent: ApplicationComponentHolder

    fun init(context: Context) {
        applicationComponent =
                ApplicationComponentHolder(
                    ApplicationComponent(
                        context
                    )
                )
    }
}