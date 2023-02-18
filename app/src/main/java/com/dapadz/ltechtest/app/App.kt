package com.dapadz.ltechtest.app

import android.app.Application
import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.utils.AndroidUtils
import org.kodein.di.DI
import org.kodein.di.DIAware


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KodeinSDK.init(applicationContext)
        AndroidUtils.checkDisplaySize(applicationContext)
    }
}