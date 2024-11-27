package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeConfiguration> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeConfiguration>() { // from class: android.app.time.TimeConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeConfiguration createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeConfiguration.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeConfiguration[] newArray(int i) {
            return new android.app.time.TimeConfiguration[i];
        }
    };
    private static final java.lang.String SETTING_AUTO_DETECTION_ENABLED = "autoDetectionEnabled";
    private final android.os.Bundle mBundle;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Setting {
    }

    private TimeConfiguration(android.app.time.TimeConfiguration.Builder builder) {
        this.mBundle = builder.mBundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeConfiguration readFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeConfiguration.Builder().setPropertyBundleInternal(parcel.readBundle()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public boolean isComplete() {
        return hasIsAutoDetectionEnabled();
    }

    public boolean isAutoDetectionEnabled() {
        enforceSettingPresent(SETTING_AUTO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean hasIsAutoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_AUTO_DETECTION_ENABLED);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mBundle.kindofEquals(((android.app.time.TimeConfiguration) obj).mBundle);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mBundle);
    }

    public java.lang.String toString() {
        return "TimeConfiguration{mBundle=" + this.mBundle + '}';
    }

    private void enforceSettingPresent(java.lang.String str) {
        if (!this.mBundle.containsKey(str)) {
            throw new java.lang.IllegalStateException(str + " is not set");
        }
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private final android.os.Bundle mBundle = new android.os.Bundle();

        public Builder() {
        }

        public Builder(android.app.time.TimeConfiguration timeConfiguration) {
            mergeProperties(timeConfiguration);
        }

        public android.app.time.TimeConfiguration.Builder mergeProperties(android.app.time.TimeConfiguration timeConfiguration) {
            this.mBundle.putAll(timeConfiguration.mBundle);
            return this;
        }

        android.app.time.TimeConfiguration.Builder setPropertyBundleInternal(android.os.Bundle bundle) {
            this.mBundle.putAll(bundle);
            return this;
        }

        public android.app.time.TimeConfiguration.Builder setAutoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(android.app.time.TimeConfiguration.SETTING_AUTO_DETECTION_ENABLED, z);
            return this;
        }

        public android.app.time.TimeConfiguration build() {
            return new android.app.time.TimeConfiguration(this);
        }
    }
}
