package android.telephony;

/* loaded from: classes3.dex */
public class TelephonyLocalConnection {
    private static android.telephony.TelephonyLocalConnection.ConnectionImpl sInstance;

    public interface ConnectionImpl {
        java.lang.String getCallComposerServerUrlForHandle(int i, java.util.UUID uuid);
    }

    public static java.lang.String getCallComposerServerUrlForHandle(int i, java.util.UUID uuid) {
        checkInstance();
        return sInstance.getCallComposerServerUrlForHandle(i, uuid);
    }

    private static void checkInstance() {
        if (sInstance == null) {
            throw new java.lang.IllegalStateException("Connection impl is null!");
        }
    }

    public static void setInstance(android.telephony.TelephonyLocalConnection.ConnectionImpl connectionImpl) {
        sInstance = connectionImpl;
    }
}
