/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [DocumentSnapshot.kt] created by Ji Sungbin on 22. 1. 24. 오후 8:38
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.util.extension

import com.google.firebase.firestore.DocumentSnapshot

inline fun <reified T> DocumentSnapshot.toObjectNonNull(): T = toObject(T::class.java)!!
