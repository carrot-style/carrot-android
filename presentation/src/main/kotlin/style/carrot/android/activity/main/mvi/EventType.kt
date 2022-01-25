/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [EventType.kt] created by Ji Sungbin on 22. 1. 24. 오후 7:37
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.mvi

import style.carrot.android.activity.main.MainViewModel
import style.carrot.android.activity.main.mvi.EventType.DeleteStyledUrl
import style.carrot.android.activity.main.mvi.EventType.LoadStyledUrls
import style.carrot.android.activity.main.mvi.EventType.None
import style.carrot.android.activity.main.mvi.EventType.StyeldSha
import style.carrot.android.activity.main.mvi.EventType.Styling

/**
 * @property None 기본 상태
 * [MainState.type]에서 기본값으로 사용함
 * @property StyeldSha 스타일링 파일의 sha 값을 조회함
 * [MainViewModel.getStyeldSha]에서 사용됨
 * @property Styling 스타일링 결과
 * [MainViewModel.styling]에서 사용함
 * @property LoadStyledUrls 스타일링 링크들 firestore 에서 가져오기
 * [MainViewModel.loadStyledUrls]에서 사용함
 * @property DeleteStyledUrl 스타일링된 링크 삭제
 * [MainViewModel.deleteStyledUrl]에서 사용함
 */
enum class EventType {
    None,
    StyeldSha,
    Styling,
    LoadStyledUrls,
    DeleteStyledUrl,
}
