package android.timezone;

/* loaded from: classes3.dex */
public final class ZoneInfoDb {
    private static android.timezone.ZoneInfoDb sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private final com.android.i18n.timezone.ZoneInfoDb mDelegate;

    public static android.timezone.ZoneInfoDb getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.timezone.ZoneInfoDb(com.android.i18n.timezone.ZoneInfoDb.getInstance());
            }
        }
        return sInstance;
    }

    private ZoneInfoDb(com.android.i18n.timezone.ZoneInfoDb zoneInfoDb) {
        this.mDelegate = (com.android.i18n.timezone.ZoneInfoDb) java.util.Objects.requireNonNull(zoneInfoDb);
    }

    public java.lang.String getVersion() {
        return this.mDelegate.getVersion();
    }
}
