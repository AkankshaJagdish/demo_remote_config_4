// Import necessary libraries and components
import React, { useState, useEffect } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Text,
  NativeModules,
  TouchableOpacity,
  Modal,
  TextInput,
  Button,
} from 'react-native';

// Import the RemoteConfigModule from NativeModules
const { RemoteConfigModule } = NativeModules;

const App = () => {
  // Define state variables for feedback, loading status, and modal visibility
  const [feedbackEnabled, setFeedbackEnabled] = useState(false);
  const [loading, setLoading] = useState(true);
  const [showFeedbackModal, setShowFeedbackModal] = useState(false);
  const [feedbackText, setFeedbackText] = useState('');

  // Use useEffect to fetch remote config values when the component mounts
  useEffect(() => {
    const fetchRemoteConfig = async () => {
      try {
        // Fetch and activate remote config values
        await RemoteConfigModule.fetchAndActivateRemoteConfig();
        // Get the 'enable_feedback' value from remote config and update the state
        RemoteConfigModule.getValueForKey('enable_feedback', 'Boolean', (error, value) => {
          if (error) {
            console.error(error);
            return;
          }
  
          console.log('enable_feedback value:', value);
          setFeedbackEnabled(value);
        });
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
  
    fetchRemoteConfig();
  }, []);
  
  // Function to handle feedback submission
  const submitFeedback = () => {
    // TODO: Send feedback to server
    setShowFeedbackModal(false);
    setFeedbackText('');
  };

  // Render a loading screen while fetching remote config values
  if (loading) {
    return (
      <SafeAreaView style={styles.container}>
        <Text>Loading...</Text>
      </SafeAreaView>
    );
  }

  // Render the main app screen with feedback button and modal
  return (
    <SafeAreaView style={styles.container}>
      {feedbackEnabled ? (
        <>
          <DeliveryExecutiveFeedback
            onPress={() => setShowFeedbackModal(true)}
          />
          <Modal
            visible={showFeedbackModal}
            onRequestClose={() => setShowFeedbackModal(false)}
            animationType="slide"
          >
            <SafeAreaView style={styles.modalContainer}>
              <Text style={styles.modalTitle}>Delivery Executive Feedback</Text>
              <TextInput
                style={styles.feedbackInput}
                value={feedbackText}
                onChangeText={setFeedbackText}
                placeholder="Enter your feedback"
                multiline
              />
              <Button title="Submit" onPress={submitFeedback} />
            </SafeAreaView>
          </Modal>
        </>
      ) : (
        <Text>Feedback Survey not enabled</Text>
      )}
    </SafeAreaView>
  );
};

// Component for rendering the Delivery Executive Feedback button
const DeliveryExecutiveFeedback = ({ onPress }) => {
  return (
    <TouchableOpacity style={styles.feedbackButton} onPress={onPress}>
      <Text style={styles.feedbackButtonText}>Delivery Executive Feedback</Text>
    </TouchableOpacity>
  );
};

// Define styles for the app components
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  feedbackButton: {
    backgroundColor: '#007AFF',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
  },
  feedbackButtonText: {
    color: '#FFF',
    fontWeight: 'bold',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  feedbackInput: {
    height: 100,
    width: '80%',
    borderWidth: 1,
    borderColor: '#DDD',
    borderRadius: 5,
    padding: 10,
    marginBottom: 20,
  },
});

export default App;
