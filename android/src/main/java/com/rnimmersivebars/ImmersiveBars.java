package com.rnimmersivebars;

import android.os.Build;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.app.Activity;

public class ImmersiveBars {
    /**
     * Allowing usage of lambda for Promise replacement within the file
     */
    interface ChangeColorsCallback
    {
        void finished(boolean worked);
    }

    /**
     * For usage in the onCreate method
     */
    public static void changeBarColors(final Activity activity, final Boolean isDarkMode) {
        changeBarColors(activity, isDarkMode, "", "");
    }

    /**
     * For usage in the React Module
     */
    public static void changeBarColors(final Activity activity, final Boolean isDarkMode, final String translucentLightStr, final String translucentDarkStr) {
        if (activity == null) {
            return;
        }
        final Window window = activity.getWindow();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * Handle the color setting
                 */
                int translucentLightColor;
                if (translucentLightStr.isEmpty()) {
                    translucentLightColor = Color.parseColor("#50000000");
                } else if (translucentLightStr.equals("transparent")) {
                    translucentLightColor = Color.TRANSPARENT;
                } else {
                    translucentLightColor = Color.parseColor(translucentLightStr);
                }

                int translucentDarkColor;
                if (translucentDarkStr.isEmpty() || translucentDarkStr.equals("transparent")) {
                    translucentDarkColor = Color.TRANSPARENT;
                } else {
                    translucentDarkColor = Color.parseColor(translucentDarkStr);
                }

                // Set the navbar to be drawn over
                // Both flags were added in Level 16
                int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

                // M was the first version that supported light mode status bar
                boolean shouldUseTransparentStatusBar = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
                // O was the first version that supported light mode nav bar
                boolean shouldUseTransparentNavBar = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;

                if (shouldUseTransparentStatusBar) {
                    window.setStatusBarColor(Color.TRANSPARENT);
                    if (!isDarkMode) {
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
                } else {
                    window.setStatusBarColor(isDarkMode ? translucentDarkColor : translucentLightColor);
                }

                if (shouldUseTransparentNavBar) {
                    window.setNavigationBarColor(Color.TRANSPARENT);
                    if (!isDarkMode) {
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    }
                } else {
                    window.setNavigationBarColor(isDarkMode ? translucentDarkColor: translucentLightColor);
                }

                window.getDecorView().setSystemUiVisibility(flags);
            }
        });
    }
}
