/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [EventType.kt] created by Ji Sungbin on 22. 1. 24. 오후 7:37
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.mvi

/**
 * @property None 기본 상태
 * @property CheckStyled 사용하려는 커스텀 링크가 이미 사용 됐는지 확인
 * @property Styled 스타일링 결과
 * @property StyleUpdateSha 스타일링 업데이트에 필요한 Sha 값 요청
 * @property LoadCarrotUrls 내가 스타일링한 링크들 [style.carrot.android.domain.model.CarrotUrl] 리스트로 불러옴
 */
enum class EventType { None, CheckStyled, Styled, StyleUpdateSha, LoadCarrotUrls }
