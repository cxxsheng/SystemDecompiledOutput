package android.companion.virtual.camera;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public interface VirtualCameraCallback {
    void onStreamClosed(int i);

    void onStreamConfigured(int i, android.view.Surface surface, int i2, int i3, int i4);

    default void onProcessCaptureRequest(int i, long j) {
    }
}
