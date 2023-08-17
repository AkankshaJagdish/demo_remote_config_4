# demo_remote_config_4

Here is how to create a react-native app with integrated Firebase Remote Config.

A basic runthrough:
1. Initialize React Native App and add Firebase: Start by initializing a new React Native app and create a corresponding project on Firebase. Enable Google Analytics for the Firebase Project, and add the React Native app to the Firebase project. 
2. Create an Android Native Module: Create a new Java class (RemoteConfigModule.java) to serve as a native module for interacting with Firebase Remote Config. This includes methods for fetching remote configuration values and handling the logic for different value types. 
3. Register the Android Module: The native module needs to be registered with the React Native app. Implement a ReactPackage interface (MyAppPackage) for the app and add RemoteConfigModule to the list of NativeModules to register. 
4. Create parameters in Firebase Remote Config: Add the desired parameters to Firebase Remote Config for controlling the behavior of your app. In this case, a parameter 'enable_feedback' is used to control whether a feedback modal is displayed in the app or not. 
5. Develop a React Native App to use the Native Module: Develop an app that fetches the remote configuration value and uses it to control the display of a feedback modal. The feedback form button will only be displayed if Firebase Remote Config has enabled it, otherwise, a message is displayed stating the form is not enabled. 
6. Run the App: Finally, start the development server for your app and run it. Depending on the value of 'enable_feedback' in Firebase Remote Config, the app will either display a feedback form or a message indicating that the form is not enabled. 
