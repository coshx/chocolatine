package com.coshx.chocolatine.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @class AdapterHelper
 * @brief
 */
public class AdapterHelper {
    public static <T extends View> T recycle(
        View convertView,
        LayoutInflater inflater,
        int layoutId,
        ViewGroup parent) {

        if (convertView == null) {
            return ViewHelper.inflate(inflater, layoutId, parent);
        } else {
            return (T) convertView;
        }
    }
}
