package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class SharedConnectivitySettingsState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState>() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final boolean mInstantTetherEnabled;
    private final android.app.PendingIntent mInstantTetherSettingsPendingIntent;

    public static final class Builder {
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;
        private boolean mInstantTetherEnabled;
        private android.app.PendingIntent mInstantTetherSettingsPendingIntent;

        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.Builder setInstantTetherEnabled(boolean z) {
            this.mInstantTetherEnabled = z;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.Builder setInstantTetherSettingsPendingIntent(android.app.PendingIntent pendingIntent) {
            this.mInstantTetherSettingsPendingIntent = pendingIntent;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState build() {
            return new android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState(this.mInstantTetherEnabled, this.mInstantTetherSettingsPendingIntent, this.mExtras);
        }
    }

    private static void validate(android.app.PendingIntent pendingIntent) {
        if (pendingIntent != null && !pendingIntent.isImmutable()) {
            throw new java.lang.IllegalArgumentException("Pending intent must be immutable");
        }
    }

    private SharedConnectivitySettingsState(boolean z, android.app.PendingIntent pendingIntent, android.os.Bundle bundle) {
        validate(pendingIntent);
        this.mInstantTetherEnabled = z;
        this.mInstantTetherSettingsPendingIntent = pendingIntent;
        this.mExtras = bundle;
    }

    public boolean isInstantTetherEnabled() {
        return this.mInstantTetherEnabled;
    }

    public android.app.PendingIntent getInstantTetherSettingsPendingIntent() {
        return this.mInstantTetherSettingsPendingIntent;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState = (android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState) obj;
        return this.mInstantTetherEnabled == sharedConnectivitySettingsState.isInstantTetherEnabled() && java.util.Objects.equals(this.mInstantTetherSettingsPendingIntent, sharedConnectivitySettingsState.getInstantTetherSettingsPendingIntent());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mInstantTetherEnabled), this.mInstantTetherSettingsPendingIntent);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.app.PendingIntent.writePendingIntentOrNullToParcel(this.mInstantTetherSettingsPendingIntent, parcel);
        parcel.writeBoolean(this.mInstantTetherEnabled);
        parcel.writeBundle(this.mExtras);
    }

    public static android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState readFromParcel(android.os.Parcel parcel) {
        return new android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState(parcel.readBoolean(), android.app.PendingIntent.readPendingIntentOrNullFromParcel(parcel), parcel.readBundle());
    }

    public java.lang.String toString() {
        return "SharedConnectivitySettingsState[instantTetherEnabled=" + this.mInstantTetherEnabled + "PendingIntent=" + this.mInstantTetherSettingsPendingIntent + "extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
