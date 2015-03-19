package com.coshx.chocolatine.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @class ViewHelper
 * @brief
 */
public class ViewHelper {
    public static <T extends View> T findById(View source, int id) {
        return (T) source.findViewById(id);
    }

    public static <T extends View> T findById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T inflate(LayoutInflater inflater, int layoutId,
                                             ViewGroup parent, boolean attachToRoot) {
        return (T) inflater.inflate(layoutId, parent, attachToRoot);
    }

    public static <T extends View> T inflate(LayoutInflater inflater, int layoutId,
                                             ViewGroup parent) {
        return inflate(inflater, layoutId, parent, false);
    }
}
