package org.rnimmersivebars;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

public class ImmersiveBarsModule extends ReactContextBaseJavaModule {
    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    private static final String ERROR_NO_ACTIVITY_MESSAGE = "Tried to change the navigation bar while not attached to an Activity";

    public ImmersiveBarsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ImmersiveBars";
    }

    @ReactMethod
    public void changeBarColors(final Boolean isDarkMode, Promise promise) {
        try {
            ImmersiveBars.changeBarColors(
                    isDarkMode,
                    "",
                    "",
                    worked -> {
                        if (worked) {
                            promise.resolve(worked)
                        } else {
                            promise.reject(ERROR_NO_ACTIVITY, new Throwable(ERROR_NO_ACTIVITY_MESSAGE));
                        }
                    }
            );
        } catch (IllegalViewOperationException e) {
            WritableMap map = Arguments.createMap();
            map.putBoolean("success", false);
            promise.reject("error", e);
        }
    }

    @ReactMethod
    public void changeBarColors(final Boolean isDarkMode, final String translucentLightStr, final String translucentDarkStr, Promise promise) {
        try {
            ImmersiveBars.changeBarColors(
                    isDarkMode,
                    translucentLightStr,
                    translucentDarkStr,
                    worked -> {
                        if (worked) {
                            promise.resolve(worked)
                        } else {
                            promise.reject(ERROR_NO_ACTIVITY, new Throwable(ERROR_NO_ACTIVITY_MESSAGE));
                        }
                    }
            );
        } catch (IllegalViewOperationException e) {
            WritableMap map = Arguments.createMap();
            map.putBoolean("success", false);
            promise.reject("error", e);
        }
    }
}
