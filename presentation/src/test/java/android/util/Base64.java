/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [Base64.java] created by Ji Sungbin on 22. 1. 28. 오후 10:28
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package android.util;

public class Base64 {
    public static String encodeToString(byte[] input, int flags) {
        return java.util.Base64.getEncoder().encodeToString(input);
    }

    public static byte[] decode(String str, int flags) {
        return java.util.Base64.getDecoder().decode(str);
    }
}