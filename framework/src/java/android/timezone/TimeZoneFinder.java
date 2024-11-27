package android.timezone;

/* loaded from: classes3.dex */
public final class TimeZoneFinder {
    private static android.timezone.TimeZoneFinder sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private final com.android.i18n.timezone.TimeZoneFinder mDelegate;

    public static android.timezone.TimeZoneFinder getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.timezone.TimeZoneFinder(com.android.i18n.timezone.TimeZoneFinder.getInstance());
            }
        }
        return sInstance;
    }

    private TimeZoneFinder(com.android.i18n.timezone.TimeZoneFinder timeZoneFinder) {
        this.mDelegate = (com.android.i18n.timezone.TimeZoneFinder) java.util.Objects.requireNonNull(timeZoneFinder);
    }

    public java.lang.String getIanaVersion() {
        return this.mDelegate.getIanaVersion();
    }

    public android.timezone.CountryTimeZones lookupCountryTimeZones(java.lang.String str) {
        com.android.i18n.timezone.CountryTimeZones lookupCountryTimeZones = this.mDelegate.lookupCountryTimeZones(str);
        if (lookupCountryTimeZones == null) {
            return null;
        }
        return new android.timezone.CountryTimeZones(lookupCountryTimeZones);
    }
}
