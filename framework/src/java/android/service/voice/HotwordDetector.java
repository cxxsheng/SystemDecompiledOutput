package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface HotwordDetector {
    public static final int DETECTOR_TYPE_NORMAL = 0;
    public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_DSP = 1;
    public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_SOFTWARE = 2;
    public static final int DETECTOR_TYPE_VISUAL_QUERY_DETECTOR = 3;

    boolean startRecognition();

    boolean startRecognition(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle);

    boolean stopRecognition();

    void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory);

    default void destroy() {
        throw new java.lang.UnsupportedOperationException("Not implemented. Must override in a subclass.");
    }

    default boolean isUsingSandboxedDetectionService() {
        throw new java.lang.UnsupportedOperationException("Not implemented. Must override in a subclass.");
    }

    static java.lang.String detectorTypeToString(int i) {
        switch (i) {
            case 0:
                return android.graphics.FontListParser.STYLE_NORMAL;
            case 1:
                return "trusted_hotword_dsp";
            case 2:
                return "trusted_hotword_software";
            case 3:
                return "visual_query_detector";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    default void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        throw new java.lang.UnsupportedOperationException("Not implemented. Must override in a subclass.");
    }

    public interface Callback {
        void onDetected(android.service.voice.AlwaysOnHotwordDetector.EventPayload eventPayload);

        @java.lang.Deprecated
        void onError();

        void onHotwordDetectionServiceInitialized(int i);

        void onHotwordDetectionServiceRestarted();

        void onRecognitionPaused();

        void onRecognitionResumed();

        void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult);

        default void onFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            onError();
        }

        default void onUnknownFailure(java.lang.String str) {
            onError();
        }
    }
}
