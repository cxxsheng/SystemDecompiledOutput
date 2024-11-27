package android.media.permission;

/* loaded from: classes2.dex */
public class IdentityContext implements android.media.permission.SafeCloseable {
    private static java.lang.ThreadLocal<android.media.permission.Identity> sThreadLocalIdentity = new java.lang.ThreadLocal<>();
    private android.media.permission.Identity mPrior = get();

    public static android.media.permission.SafeCloseable create(android.media.permission.Identity identity) {
        return new android.media.permission.IdentityContext(identity);
    }

    public static android.media.permission.Identity get() {
        return sThreadLocalIdentity.get();
    }

    public static android.media.permission.Identity getNonNull() {
        android.media.permission.Identity identity = get();
        if (identity == null) {
            throw new java.lang.NullPointerException("Identity context is not set");
        }
        return identity;
    }

    private IdentityContext(android.media.permission.Identity identity) {
        set(identity);
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        set(this.mPrior);
    }

    private static void set(android.media.permission.Identity identity) {
        sThreadLocalIdentity.set(identity);
    }
}
