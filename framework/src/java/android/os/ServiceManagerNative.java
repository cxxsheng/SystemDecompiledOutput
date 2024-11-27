package android.os;

/* loaded from: classes3.dex */
public final class ServiceManagerNative {
    private ServiceManagerNative() {
    }

    public static android.os.IServiceManager asInterface(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        return new android.os.ServiceManagerProxy(iBinder);
    }
}
