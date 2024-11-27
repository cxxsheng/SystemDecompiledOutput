package android.speech;

/* loaded from: classes3.dex */
public interface RecognitionListener {
    void onBeginningOfSpeech();

    void onBufferReceived(byte[] bArr);

    void onEndOfSpeech();

    void onError(int i);

    void onEvent(int i, android.os.Bundle bundle);

    void onPartialResults(android.os.Bundle bundle);

    void onReadyForSpeech(android.os.Bundle bundle);

    void onResults(android.os.Bundle bundle);

    void onRmsChanged(float f);

    default void onSegmentResults(android.os.Bundle bundle) {
    }

    default void onEndOfSegmentedSession() {
    }

    default void onLanguageDetection(android.os.Bundle bundle) {
    }
}
