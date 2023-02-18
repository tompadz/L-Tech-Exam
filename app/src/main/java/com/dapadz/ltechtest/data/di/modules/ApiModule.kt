package com.dapadz.ltechtest.data.di.modules

import com.dapadz.ltechtest.data.api.AuthApi
import com.dapadz.ltechtest.data.api.PostApi
import org.kodein.di.*


val apiModule = DI.Module(
    name = "ApiModule",
    init = {
        bind<PostApi>() with provider {
            PostApi(
                instance()
            )
        }

        bind<AuthApi>() with provider {
            AuthApi(
                instance()
            )
        }
    })