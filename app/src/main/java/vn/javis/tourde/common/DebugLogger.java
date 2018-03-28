/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.javis.tourde.common;

import android.util.Log;

/**
 * Created by HoangTrung on 3/27/18.
 */

public class DebugLogger {

    private static final Boolean IS_DEBUG = true; // true if project is developing, false when release

    /*
     * print message under category is verbose
     */
    public static void logVerbose(String tag, String message) {
        if (IS_DEBUG) {
            Log.v(tag, message);
        }
    }

    /*
     * print message under category is info
     */
    public static void logInfo(String tag, String message) {
        if (IS_DEBUG) {
            Log.v(tag, message);
        }
    }

    /*
     * print message under category is debug
     */
    public static void logDebug(String tag, String message) {
        if (IS_DEBUG) {
            Log.d(tag, message);
        }
    }

    /*
     * print message under category is error
     */
    public static void logError(String tag, String message) {
        if (IS_DEBUG) {
            Log.d(tag, message);
        }
    }

}
