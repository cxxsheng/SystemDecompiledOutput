package android.speech.tts;

/* loaded from: classes3.dex */
public interface SynthesisCallback {

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SupportedAudioFormat {
    }

    int audioAvailable(byte[] bArr, int i, int i2);

    int done();

    void error();

    void error(int i);

    int getMaxBufferSize();

    boolean hasFinished();

    boolean hasStarted();

    int start(int i, int i2, int i3);

    default void rangeStart(int i, int i2, int i3) {
    }
}
