package com.dapadz.ltechtest.data.di.modules

import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.data.repository.AuthRepository
import com.dapadz.ltechtest.data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module(
    name = "ArticleRepository",
    init = {
        bind<PostRepository>() with singleton {
            PostRepository(
                instance()
            )
        }

        bind<AuthRepository>() with singleton {
            AuthRepository(
                instance()
            )
        }
    }
)

object RepositoryModule {
    val postRepository: PostRepository
        get() = KodeinSDK.di.instance()

    val authRepository: AuthRepository
        get() = KodeinSDK.di.instance()
}

val KodeinSDK.repository: RepositoryModule
    get() = RepositoryModule