/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [DispatcherModule.kt] created by Ji Sungbin on 22. 1. 24. 오후 5:23
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import style.carrot.android.di.qualifier.IoDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object DispatcherModule {
    @Provides
    @IoDispatcher
    @ViewModelScoped
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
