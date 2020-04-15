package org.rnimmersivebars;

import android.os.Build;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class ImmersiveBars {
    interface ChangeColorsCallback
    {
        void finished(boolean worked);
    }

    public static void changeBarColors(final Boolean isDarkMode) {
        changeBarColors(isDarkMode, "", "", (boolean worked) -> {});
    }

    public static void changeBarColors(final Boolean isDarkMode, final String translucentLightStr, final String translucentDarkStr) {
        changeBarColors(isDarkMode, translucentDarkStr, translucentDarkStr, (boolean worked) -> {});
    }

    public static void changeBarColors(final Boolean isDarkMode, final String translucentLightStr, final String translucentDarkStr, ChangeColorsCallback cb) {
        if (getCurrentActivity() != null) {
            cb.finished(false);
        }
        final Window window = getCurrentActivity().getWindow();
        runOnUiThread(new Runnable() {
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
                boolean shouldTranslucizeStatusBar = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M;
                // O was the first version that supported light mode nav bar
                boolean shouldTranslucizeNavBar = Build.VERSION.SDK_INT <= Build.VERSION_CODES.O;

                if (shouldTranslucizeStatusBar) {
                    window.setStatusBarColor(isDarkMode ? translucentLightColor : translucentDarkColor);
                } else {
                    window.setStatusBarColor(Color.TRANSPARENT);
                    if (!isDarkMode) {
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
                }

                if (shouldTranslucizeNavBar) {
                    window.setNavigationBarColor(isDarkMode ? translucentLightColor : translucentDarkColor);
                } else {
                    window.setNavigationBarColor(Color.TRANSPARENT);
                    if (!isDarkMode) {
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    }
                }

                window.getDecorView().setSystemUiVisibility(flags);

                cb.finished(true);
            }
        });
    }
}
