package android.timezone;

/* loaded from: classes3.dex */
public final class TelephonyLookup {
    private static android.timezone.TelephonyLookup sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private final com.android.i18n.timezone.TelephonyLookup mDelegate;

    public static android.timezone.TelephonyLookup getInstance() {
        android.timezone.TelephonyLookup telephonyLookup;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.timezone.TelephonyLookup(com.android.i18n.timezone.TelephonyLookup.getInstance());
            }
            telephonyLookup = sInstance;
        }
        return telephonyLookup;
    }

    private TelephonyLookup(com.android.i18n.timezone.TelephonyLookup telephonyLookup) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyLookup) java.util.Objects.requireNonNull(telephonyLookup);
    }

    public android.timezone.TelephonyNetworkFinder getTelephonyNetworkFinder() {
        com.android.i18n.timezone.TelephonyNetworkFinder telephonyNetworkFinder = this.mDelegate.getTelephonyNetworkFinder();
        if (telephonyNetworkFinder != null) {
            return new android.timezone.TelephonyNetworkFinder(telephonyNetworkFinder);
        }
        return null;
    }
}
