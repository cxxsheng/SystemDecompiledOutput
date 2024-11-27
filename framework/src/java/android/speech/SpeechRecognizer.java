package android.speech;

/* loaded from: classes3.dex */
public class SpeechRecognizer {
    public static final java.lang.String CONFIDENCE_SCORES = "confidence_scores";
    public static final java.lang.String DETECTED_LANGUAGE = "detected_language";
    public static final int ERROR_AUDIO = 3;
    public static final int ERROR_CANNOT_CHECK_SUPPORT = 14;
    public static final int ERROR_CANNOT_LISTEN_TO_DOWNLOAD_EVENTS = 15;
    public static final int ERROR_CLIENT = 5;
    public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
    public static final int ERROR_LANGUAGE_NOT_SUPPORTED = 12;
    public static final int ERROR_LANGUAGE_UNAVAILABLE = 13;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_NETWORK_TIMEOUT = 1;
    public static final int ERROR_NO_MATCH = 7;
    public static final int ERROR_RECOGNIZER_BUSY = 8;
    public static final int ERROR_SERVER = 4;
    public static final int ERROR_SERVER_DISCONNECTED = 11;
    public static final int ERROR_SPEECH_TIMEOUT = 6;
    public static final int ERROR_TOO_MANY_REQUESTS = 10;
    public static final java.lang.String LANGUAGE_DETECTION_CONFIDENCE_LEVEL = "language_detection_confidence_level";
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_CONFIDENT = 2;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_HIGHLY_CONFIDENT = 3;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_NOT_CONFIDENT = 1;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_UNKNOWN = 0;
    public static final java.lang.String LANGUAGE_SWITCH_RESULT = "language_switch_result";
    public static final int LANGUAGE_SWITCH_RESULT_FAILED = 2;
    public static final int LANGUAGE_SWITCH_RESULT_NOT_ATTEMPTED = 0;
    public static final int LANGUAGE_SWITCH_RESULT_SKIPPED_NO_MODEL = 3;
    public static final int LANGUAGE_SWITCH_RESULT_SUCCEEDED = 1;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHANGE_LISTENER = 4;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 6;
    private static final int MSG_SET_TEMPORARY_ON_DEVICE_COMPONENT = 5;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 7;
    public static final java.lang.String RECOGNITION_PARTS = "recognition_parts";
    public static final java.lang.String RESULTS_ALTERNATIVES = "results_alternatives";
    public static final java.lang.String RESULTS_RECOGNITION = "results_recognition";
    public static final java.lang.String TOP_LOCALE_ALTERNATIVES = "top_locale_alternatives";

    @java.lang.annotation.Documented
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LanguageDetectionConfidenceLevel {
    }

    @java.lang.annotation.Documented
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LanguageSwitchResult {
    }

    @java.lang.annotation.Documented
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionError {
    }

    SpeechRecognizer() {
    }

    public static boolean isRecognitionAvailable(android.content.Context context) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new android.content.Intent(android.speech.RecognitionService.SERVICE_INTERFACE), 0);
        return (queryIntentServices == null || queryIntentServices.size() == 0) ? false : true;
    }

    public static boolean isOnDeviceRecognitionAvailable(android.content.Context context) {
        return android.content.ComponentName.unflattenFromString(context.getString(com.android.internal.R.string.config_defaultOnDeviceSpeechRecognitionService)) != null;
    }

    public static android.speech.SpeechRecognizer createSpeechRecognizer(android.content.Context context) {
        return createSpeechRecognizer(context, null);
    }

    public static android.speech.SpeechRecognizer createSpeechRecognizer(android.content.Context context, android.content.ComponentName componentName) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Context cannot be null");
        }
        android.speech.SpeechRecognizerImpl.checkIsCalledFromMainThread();
        return wrapWithProxy(new android.speech.SpeechRecognizerImpl(context, componentName));
    }

    public static android.speech.SpeechRecognizer createOnDeviceSpeechRecognizer(android.content.Context context) {
        if (!isOnDeviceRecognitionAvailable(context)) {
            throw new java.lang.UnsupportedOperationException("On-device recognition is not available");
        }
        return wrapWithProxy(android.speech.SpeechRecognizerImpl.lenientlyCreateOnDeviceSpeechRecognizer(context));
    }

    private static android.speech.SpeechRecognizer wrapWithProxy(android.speech.SpeechRecognizer speechRecognizer) {
        return new android.speech.SpeechRecognizerProxy(speechRecognizer);
    }

    public static android.speech.SpeechRecognizer createOnDeviceTestingSpeechRecognizer(android.content.Context context) {
        return wrapWithProxy(android.speech.SpeechRecognizerImpl.lenientlyCreateOnDeviceSpeechRecognizer(context));
    }

    public void setRecognitionListener(android.speech.RecognitionListener recognitionListener) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void startListening(android.content.Intent intent) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void stopListening() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void cancel() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void checkRecognitionSupport(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.RecognitionSupportCallback recognitionSupportCallback) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void triggerModelDownload(android.content.Intent intent) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void triggerModelDownload(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.ModelDownloadListener modelDownloadListener) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void setTemporaryOnDeviceRecognizer(android.content.ComponentName componentName) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void destroy() {
        throw new java.lang.UnsupportedOperationException();
    }
}
