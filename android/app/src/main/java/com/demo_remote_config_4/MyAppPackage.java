// Import necessary libraries and components
package com.demo_remote_config_4;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Implement a ReactPackage interface for the app
public class MyAppPackage implements ReactPackage {

    // The createViewManagers method is used for adding custom ViewManagers
    // In this case, we don't have any custom ViewManagers, so we return an empty list
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    // The createNativeModules method is used for adding custom NativeModules
    // In this case, we add the RemoteConfigModule to the list of native modules
    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        // Add the RemoteConfigModule to the list of native modules
        modules.add(new RemoteConfigModule(reactContext));

        return modules;
    }

}
