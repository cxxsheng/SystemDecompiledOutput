package android.timezone;

/* loaded from: classes3.dex */
public final class TelephonyNetworkFinder {
    private final com.android.i18n.timezone.TelephonyNetworkFinder mDelegate;

    TelephonyNetworkFinder(com.android.i18n.timezone.TelephonyNetworkFinder telephonyNetworkFinder) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyNetworkFinder) java.util.Objects.requireNonNull(telephonyNetworkFinder);
    }

    public android.timezone.TelephonyNetwork findNetworkByMccMnc(java.lang.String str, java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        com.android.i18n.timezone.TelephonyNetwork findNetworkByMccMnc = this.mDelegate.findNetworkByMccMnc(str, str2);
        if (findNetworkByMccMnc != null) {
            return new android.timezone.TelephonyNetwork(findNetworkByMccMnc);
        }
        return null;
    }
}
