package android.credentials;

/* loaded from: classes.dex */
public final class CredentialProviderInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.CredentialProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.credentials.CredentialProviderInfo>() { // from class: android.credentials.CredentialProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialProviderInfo[] newArray(int i) {
            return new android.credentials.CredentialProviderInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialProviderInfo createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.CredentialProviderInfo(parcel);
        }
    };
    private final java.util.List<java.lang.String> mCapabilities;
    private final boolean mIsEnabled;
    private final boolean mIsPrimary;
    private final boolean mIsSystemProvider;
    private final java.lang.CharSequence mOverrideLabel;
    private final android.content.pm.ServiceInfo mServiceInfo;
    private java.lang.CharSequence mSettingsActivity;
    private java.lang.CharSequence mSettingsSubtitle;

    private CredentialProviderInfo(android.credentials.CredentialProviderInfo.Builder builder) {
        this.mCapabilities = new java.util.ArrayList();
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = builder.mServiceInfo;
        this.mCapabilities.addAll(builder.mCapabilities);
        this.mIsSystemProvider = builder.mIsSystemProvider;
        this.mSettingsSubtitle = builder.mSettingsSubtitle;
        this.mIsEnabled = builder.mIsEnabled;
        this.mIsPrimary = builder.mIsPrimary;
        this.mOverrideLabel = builder.mOverrideLabel;
        this.mSettingsActivity = builder.mSettingsActivity;
    }

    public boolean hasCapability(java.lang.String str) {
        return this.mCapabilities.contains(str);
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public boolean isSystemProvider() {
        return this.mIsSystemProvider;
    }

    public android.graphics.drawable.Drawable getServiceIcon(android.content.Context context) {
        return this.mServiceInfo.loadIcon(context.getPackageManager());
    }

    public java.lang.CharSequence getLabel(android.content.Context context) {
        if (this.mOverrideLabel != null) {
            return this.mOverrideLabel;
        }
        return this.mServiceInfo.loadSafeLabel(context.getPackageManager());
    }

    public java.util.List<java.lang.String> getCapabilities() {
        return java.util.Collections.unmodifiableList(this.mCapabilities);
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }

    public java.lang.CharSequence getSettingsSubtitle() {
        return this.mSettingsSubtitle;
    }

    public java.lang.CharSequence getSettingsActivity() {
        if (!android.credentials.flags.Flags.settingsActivityEnabled()) {
            return null;
        }
        return this.mSettingsActivity;
    }

    public android.content.ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mServiceInfo, i);
        parcel.writeBoolean(this.mIsSystemProvider);
        parcel.writeStringList(this.mCapabilities);
        parcel.writeBoolean(this.mIsEnabled);
        parcel.writeBoolean(this.mIsPrimary);
        android.text.TextUtils.writeToParcel(this.mOverrideLabel, parcel, i);
        android.text.TextUtils.writeToParcel(this.mSettingsSubtitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mSettingsActivity, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "CredentialProviderInfo {serviceInfo=" + this.mServiceInfo + ", isSystemProvider=" + this.mIsSystemProvider + ", isEnabled=" + this.mIsEnabled + ", isPrimary=" + this.mIsPrimary + ", overrideLabel=" + ((java.lang.Object) this.mOverrideLabel) + ", settingsSubtitle=" + ((java.lang.Object) this.mSettingsSubtitle) + ", settingsActivity=" + ((java.lang.Object) this.mSettingsActivity) + ", capabilities=" + java.lang.String.join(",", this.mCapabilities) + "}";
    }

    private CredentialProviderInfo(android.os.Parcel parcel) {
        this.mCapabilities = new java.util.ArrayList();
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = (android.content.pm.ServiceInfo) parcel.readTypedObject(android.content.pm.ServiceInfo.CREATOR);
        this.mIsSystemProvider = parcel.readBoolean();
        parcel.readStringList(this.mCapabilities);
        this.mIsEnabled = parcel.readBoolean();
        this.mIsPrimary = parcel.readBoolean();
        this.mOverrideLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSettingsSubtitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSettingsActivity = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public static final class Builder {
        private android.content.pm.ServiceInfo mServiceInfo;
        private java.util.List<java.lang.String> mCapabilities = new java.util.ArrayList();
        private boolean mIsSystemProvider = false;
        private java.lang.CharSequence mSettingsSubtitle = null;
        private java.lang.CharSequence mSettingsActivity = null;
        private boolean mIsEnabled = false;
        private boolean mIsPrimary = false;
        private java.lang.CharSequence mOverrideLabel = null;

        public Builder(android.content.pm.ServiceInfo serviceInfo) {
            this.mServiceInfo = serviceInfo;
        }

        public android.credentials.CredentialProviderInfo.Builder setSystemProvider(boolean z) {
            this.mIsSystemProvider = z;
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder setOverrideLabel(java.lang.CharSequence charSequence) {
            this.mOverrideLabel = charSequence;
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder setSettingsSubtitle(java.lang.CharSequence charSequence) {
            this.mSettingsSubtitle = charSequence;
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder setSettingsActivity(java.lang.CharSequence charSequence) {
            this.mSettingsActivity = charSequence;
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder addCapabilities(java.util.List<java.lang.String> list) {
            this.mCapabilities.addAll(list);
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder setEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public android.credentials.CredentialProviderInfo.Builder setPrimary(boolean z) {
            this.mIsPrimary = z;
            return this;
        }

        public android.credentials.CredentialProviderInfo build() {
            return new android.credentials.CredentialProviderInfo(this);
        }
    }
}
