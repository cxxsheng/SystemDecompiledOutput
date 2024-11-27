package android.timezone;

/* loaded from: classes3.dex */
public final class TelephonyNetwork {
    private final com.android.i18n.timezone.TelephonyNetwork mDelegate;

    TelephonyNetwork(com.android.i18n.timezone.TelephonyNetwork telephonyNetwork) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyNetwork) java.util.Objects.requireNonNull(telephonyNetwork);
    }

    public java.lang.String getMcc() {
        return this.mDelegate.getMcc();
    }

    public java.lang.String getMnc() {
        return this.mDelegate.getMnc();
    }

    public java.lang.String getCountryIsoCode() {
        return this.mDelegate.getCountryIsoCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mDelegate.equals(((android.timezone.TelephonyNetwork) obj).mDelegate);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDelegate);
    }

    public java.lang.String toString() {
        return "TelephonyNetwork{mDelegate=" + this.mDelegate + '}';
    }
}
