package com.coshx.chocolatine.helpers;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * @class DeviceHelper
 * @brief
 */
public class DeviceHelper {
    public static void lockOrientation(Activity activity) {
        int requestedOrientation;

        if (activity.getResources().getConfiguration().orientation == Configuration
            .ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT;
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE;
        }

        activity.setRequestedOrientation(requestedOrientation);
    }

    public static void unlockOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
