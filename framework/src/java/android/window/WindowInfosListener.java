package android.window;

/* loaded from: classes4.dex */
public abstract class WindowInfosListener {
    private final long mNativeListener;

    private static native long nativeCreate(android.window.WindowInfosListener windowInfosListener);

    private static native long nativeGetFinalizer();

    private static native android.util.Pair<android.view.InputWindowHandle[], android.window.WindowInfosListener.DisplayInfo[]> nativeRegister(long j);

    private static native void nativeUnregister(long j);

    public abstract void onWindowInfosChanged(android.view.InputWindowHandle[] inputWindowHandleArr, android.window.WindowInfosListener.DisplayInfo[] displayInfoArr);

    public WindowInfosListener() {
        libcore.util.NativeAllocationRegistry createMalloced = libcore.util.NativeAllocationRegistry.createMalloced(android.window.WindowInfosListener.class.getClassLoader(), nativeGetFinalizer());
        this.mNativeListener = nativeCreate(this);
        createMalloced.registerNativeAllocation(this, this.mNativeListener);
    }

    public android.util.Pair<android.view.InputWindowHandle[], android.window.WindowInfosListener.DisplayInfo[]> register() {
        return nativeRegister(this.mNativeListener);
    }

    public void unregister() {
        nativeUnregister(this.mNativeListener);
    }

    public static final class DisplayInfo {
        public final int mDisplayId;
        public final android.util.Size mLogicalSize;
        public final android.graphics.Matrix mTransform;

        private DisplayInfo(int i, int i2, int i3, android.graphics.Matrix matrix) {
            this.mDisplayId = i;
            this.mLogicalSize = new android.util.Size(i2, i3);
            this.mTransform = matrix;
        }

        public java.lang.String toString() {
            return "displayId=" + this.mDisplayId + ", mLogicalSize=" + this.mLogicalSize + ", mTransform=" + this.mTransform;
        }
    }
}
