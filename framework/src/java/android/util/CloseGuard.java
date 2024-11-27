package android.util;

/* loaded from: classes3.dex */
public final class CloseGuard {
    private final dalvik.system.CloseGuard mImpl = getImpl();

    public static android.util.CloseGuard get() {
        return new android.util.CloseGuard();
    }

    private dalvik.system.CloseGuard getImpl() {
        return dalvik.system.CloseGuard.get();
    }

    private dalvik.system.CloseGuard getImpl$ravenwood() {
        return null;
    }

    public void open(java.lang.String str) {
        if (this.mImpl != null) {
            this.mImpl.open(str);
        }
    }

    public void close() {
        if (this.mImpl != null) {
            this.mImpl.close();
        }
    }

    public void warnIfOpen() {
        if (this.mImpl != null) {
            this.mImpl.warnIfOpen();
        }
    }
}
