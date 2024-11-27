package android.filterfw.core;

/* compiled from: GLFrame.java */
/* loaded from: classes.dex */
class GLFrameTimer {
    private static android.filterfw.core.StopWatchMap mTimer = null;

    GLFrameTimer() {
    }

    public static android.filterfw.core.StopWatchMap get() {
        if (mTimer == null) {
            mTimer = new android.filterfw.core.StopWatchMap();
        }
        return mTimer;
    }
}
