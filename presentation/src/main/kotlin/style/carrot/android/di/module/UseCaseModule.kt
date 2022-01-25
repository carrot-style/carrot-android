/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 1. 23. 오후 1:31
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import style.carrot.android.domain.repository.CarrotRepository
import style.carrot.android.domain.usecase.AddStyledUrlUseCase
import style.carrot.android.domain.usecase.DeleteStyledUrlUseCase
import style.carrot.android.domain.usecase.GetStyledShaUseCase
import style.carrot.android.domain.usecase.LoadStyledUrlsUseCase
import style.carrot.android.domain.usecase.StylingUrlUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideStylingCarrotUrlUseCase(repository: CarrotRepository): StylingUrlUseCase =
        StylingUrlUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideLoadCarrotUrlsUseCase(repository: CarrotRepository): LoadStyledUrlsUseCase =
        LoadStyledUrlsUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetStyledShaUseCase(repository: CarrotRepository): GetStyledShaUseCase =
        GetStyledShaUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteStyledUrlUseCase(repository: CarrotRepository): DeleteStyledUrlUseCase =
        DeleteStyledUrlUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideAddMyStyledUrl(repository: CarrotRepository): AddStyledUrlUseCase =
        AddStyledUrlUseCase(repository)
}
