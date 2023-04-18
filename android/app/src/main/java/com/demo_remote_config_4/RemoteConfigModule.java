package com.demo_remote_config_4;

// Import necessary libraries and classes
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.facebook.react.bridge.Callback;

public class RemoteConfigModule extends ReactContextBaseJavaModule {
    // Define a constant for logging purposes
    private static final String TAG = "RemoteConfigModule";
    // Declare a variable to hold the FirebaseRemoteConfig instance
    private final FirebaseRemoteConfig mFirebaseRemoteConfig;

    // Constructor for the RemoteConfigModule class
    public RemoteConfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // Get the FirebaseRemoteConfig instance
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Configure Firebase Remote Config settings
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .build();
        // Set the Firebase Remote Config settings asynchronously
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        // Set the default values for the Firebase Remote Config asynchronously
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    // This method returns the name of the Native Module to be used in JavaScript
    @NonNull
    @Override
    public String getName() {
        return "RemoteConfigModule";
    }

    // This method retrieves a value for a given key and valueType from the Firebase Remote Config
    // and passes the result to the provided Callback
    @ReactMethod
    public void getValueForKey(String key, String valueType, Callback callback) {
        if (valueType.equals("Boolean")) {
            boolean value = mFirebaseRemoteConfig.getBoolean(key);
            callback.invoke(null, value);
        } else if (valueType.equals("Double")) {
            double value = mFirebaseRemoteConfig.getDouble(key);
            callback.invoke(null, value);
        } else if (valueType.equals("Long")) {
            long value = mFirebaseRemoteConfig.getLong(key);
            callback.invoke(null, value);
        } else if (valueType.equals("String")) {
            String value = mFirebaseRemoteConfig.getString(key);
            callback.invoke(null, value);
        } else {
            callback.invoke("Invalid value type specified", null);
        }
    }

    // This method fetches and activates the Firebase Remote Config values,
    // resolving or rejecting the Promise based on the success of the operation
    @ReactMethod
    public void fetchAndActivateRemoteConfig(Promise promise) {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(getCurrentActivity(), task -> {
                    if (task.isSuccessful()) {
                        boolean updated = task.getResult();
                        Log.d(TAG, "Config params updated: " + updated);
                        promise.resolve(updated);
                    } else {
                        Log.d(TAG, "Fetch failed");
                        promise.reject("FETCH_FAILED", "Fetch failed");
                    }
                });
    }
}
