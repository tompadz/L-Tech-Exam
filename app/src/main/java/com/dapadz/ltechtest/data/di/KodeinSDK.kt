package com.dapadz.ltechtest.data.di

import android.content.Context
import com.dapadz.ltechtest.data.di.modules.*
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct

object KodeinSDK {

    val di: DirectDI
        get() = requireNotNull(_di)

    private var _di: DirectDI? = null

    fun init(context : Context) {
        if (_di != null) {
            _di = null
        }
        val direct = DI {
            importAll(
                clientModule,
                apiModule,
                repositoryModule,
                getSharedPrefsModule(context)
            )
        }.direct
        _di = direct
    }
}