package android.media.permission;

/* loaded from: classes2.dex */
public class ClearCallingIdentityContext implements android.media.permission.SafeCloseable {
    private final long mRestoreKey = android.os.Binder.clearCallingIdentity();

    public static android.media.permission.SafeCloseable create() {
        return new android.media.permission.ClearCallingIdentityContext();
    }

    private ClearCallingIdentityContext() {
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        android.os.Binder.restoreCallingIdentity(this.mRestoreKey);
    }
}
