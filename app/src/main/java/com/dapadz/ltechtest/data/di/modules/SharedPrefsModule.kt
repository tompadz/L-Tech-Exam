package com.dapadz.ltechtest.data.di.modules

import android.content.Context
import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.data.shared_prefs.SharedPrefUtils
import org.kodein.di.*

fun getSharedPrefsModule(context:Context) = DI.Module(
    name = "SharedPrefsModule",
    init = {
        bind<SharedPrefUtils>() with singleton {
            SharedPrefUtils(context)
        }
    }
)

object SharedPrefModule {
    val prefs: SharedPrefUtils
        get() = KodeinSDK.di.instance()
}

val KodeinSDK.sharedPrefs: SharedPrefModule
    get() = SharedPrefModule