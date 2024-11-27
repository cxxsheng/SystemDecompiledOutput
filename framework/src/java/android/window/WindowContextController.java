package android.window;

/* loaded from: classes4.dex */
public class WindowContextController {
    private static final boolean DEBUG_ATTACH = false;
    private static final java.lang.String TAG = "WindowContextController";
    public int mAttachedToDisplayArea = 0;
    private final android.window.WindowTokenClient mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttachStatus {
        public static final int STATUS_ATTACHED = 1;
        public static final int STATUS_DETACHED = 2;
        public static final int STATUS_FAILED = 3;
        public static final int STATUS_INITIALIZED = 0;
    }

    public WindowContextController(android.window.WindowTokenClient windowTokenClient) {
        this.mToken = windowTokenClient;
    }

    public void attachToDisplayArea(int i, int i2, android.os.Bundle bundle) {
        if (this.mAttachedToDisplayArea == 1) {
            throw new java.lang.IllegalStateException("A Window Context can be only attached to a DisplayArea once.");
        }
        this.mAttachedToDisplayArea = getWindowTokenClientController().attachToDisplayArea(this.mToken, i, i2, bundle) ? 1 : 3;
        if (this.mAttachedToDisplayArea == 3) {
            android.util.Log.w(TAG, "attachToDisplayArea fail, type:" + i + ", displayId:" + i2);
        }
    }

    public void attachToWindowToken(android.os.IBinder iBinder) {
        if (this.mAttachedToDisplayArea != 1) {
            throw new java.lang.IllegalStateException("The Window Context should have been attached to a DisplayArea. AttachToDisplayArea:" + this.mAttachedToDisplayArea);
        }
        if (!getWindowTokenClientController().attachToWindowToken(this.mToken, iBinder)) {
            android.util.Log.e(TAG, "attachToWindowToken fail");
        }
    }

    public void detachIfNeeded() {
        if (this.mAttachedToDisplayArea == 1) {
            getWindowTokenClientController().detachIfNeeded(this.mToken);
            this.mAttachedToDisplayArea = 2;
        }
    }

    public android.window.WindowTokenClientController getWindowTokenClientController() {
        return android.window.WindowTokenClientController.getInstance();
    }
}
