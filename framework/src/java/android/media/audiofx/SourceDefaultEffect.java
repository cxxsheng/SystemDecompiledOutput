package android.media.audiofx;

/* loaded from: classes2.dex */
public class SourceDefaultEffect extends android.media.audiofx.DefaultEffect {
    private static final java.lang.String TAG = "SourceDefaultEffect-JAVA";

    private final native void native_release(int i);

    private final native int native_setup(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int[] iArr);

    static {
        java.lang.System.loadLibrary("audioeffect_jni");
    }

    public SourceDefaultEffect(java.util.UUID uuid, java.util.UUID uuid2, int i, int i2) {
        int[] iArr = new int[1];
        int native_setup = native_setup(uuid.toString(), uuid2.toString(), i, i2, android.app.ActivityThread.currentOpPackageName(), iArr);
        if (native_setup != 0) {
            android.util.Log.e(TAG, "Error code " + native_setup + " when initializing SourceDefaultEffect");
            switch (native_setup) {
                case -5:
                    throw new java.lang.UnsupportedOperationException("Effect library not loaded");
                case -4:
                    throw new java.lang.IllegalArgumentException("Source, type uuid, or implementation uuid not supported.");
                default:
                    throw new java.lang.RuntimeException("Cannot initialize effect engine for type: " + uuid + " Error: " + native_setup);
            }
        }
        this.mId = iArr[0];
    }

    public void release() {
        native_release(this.mId);
    }

    protected void finalize() {
        release();
    }
}
