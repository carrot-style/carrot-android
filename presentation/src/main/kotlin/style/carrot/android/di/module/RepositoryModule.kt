/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [RepoModule.kt] created by Ji Sungbin on 22. 1. 23. 오후 1:25
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import style.carrot.android.data.repository.CarrotRepositoryImpl
import style.carrot.android.di.qualifier.SignedRetrofit
import style.carrot.android.domain.repository.CarrotRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideCarrotRepository(@SignedRetrofit signedRetrofit: Retrofit): CarrotRepository =
        CarrotRepositoryImpl(signedRetrofit)
}
