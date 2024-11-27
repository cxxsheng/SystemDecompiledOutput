package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeCapabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeCapabilities> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeCapabilities>() { // from class: android.app.time.TimeCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeCapabilities createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeCapabilities.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeCapabilities[] newArray(int i) {
            return new android.app.time.TimeCapabilities[i];
        }
    };
    private final int mConfigureAutoDetectionEnabledCapability;
    private final int mSetManualTimeCapability;
    private final android.os.UserHandle mUserHandle;

    private TimeCapabilities(android.app.time.TimeCapabilities.Builder builder) {
        this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(builder.mUserHandle);
        this.mConfigureAutoDetectionEnabledCapability = builder.mConfigureAutoDetectionEnabledCapability;
        this.mSetManualTimeCapability = builder.mSetManualTimeCapability;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeCapabilities createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeCapabilities.Builder(android.os.UserHandle.readFromParcel(parcel)).setConfigureAutoDetectionEnabledCapability(parcel.readInt()).setSetManualTimeCapability(parcel.readInt()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.os.UserHandle.writeToParcel(this.mUserHandle, parcel);
        parcel.writeInt(this.mConfigureAutoDetectionEnabledCapability);
        parcel.writeInt(this.mSetManualTimeCapability);
    }

    public int getConfigureAutoDetectionEnabledCapability() {
        return this.mConfigureAutoDetectionEnabledCapability;
    }

    public int getSetManualTimeCapability() {
        return this.mSetManualTimeCapability;
    }

    public android.app.time.TimeConfiguration tryApplyConfigChanges(android.app.time.TimeConfiguration timeConfiguration, android.app.time.TimeConfiguration timeConfiguration2) {
        android.app.time.TimeConfiguration.Builder builder = new android.app.time.TimeConfiguration.Builder(timeConfiguration);
        if (timeConfiguration2.hasIsAutoDetectionEnabled()) {
            if (getConfigureAutoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setAutoDetectionEnabled(timeConfiguration2.isAutoDetectionEnabled());
        }
        return builder.build();
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
        android.app.time.TimeCapabilities timeCapabilities = (android.app.time.TimeCapabilities) obj;
        if (this.mConfigureAutoDetectionEnabledCapability == timeCapabilities.mConfigureAutoDetectionEnabledCapability && this.mSetManualTimeCapability == timeCapabilities.mSetManualTimeCapability && this.mUserHandle.equals(timeCapabilities.mUserHandle)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUserHandle, java.lang.Integer.valueOf(this.mConfigureAutoDetectionEnabledCapability), java.lang.Integer.valueOf(this.mSetManualTimeCapability));
    }

    public java.lang.String toString() {
        return "TimeCapabilities{mUserHandle=" + this.mUserHandle + ", mConfigureAutoDetectionEnabledCapability=" + this.mConfigureAutoDetectionEnabledCapability + ", mSetManualTimeCapability=" + this.mSetManualTimeCapability + '}';
    }

    public static class Builder {
        private int mConfigureAutoDetectionEnabledCapability;
        private int mSetManualTimeCapability;
        private final android.os.UserHandle mUserHandle;

        public Builder(android.os.UserHandle userHandle) {
            this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        }

        public Builder(android.app.time.TimeCapabilities timeCapabilities) {
            java.util.Objects.requireNonNull(timeCapabilities);
            this.mUserHandle = timeCapabilities.mUserHandle;
            this.mConfigureAutoDetectionEnabledCapability = timeCapabilities.mConfigureAutoDetectionEnabledCapability;
            this.mSetManualTimeCapability = timeCapabilities.mSetManualTimeCapability;
        }

        public android.app.time.TimeCapabilities.Builder setConfigureAutoDetectionEnabledCapability(int i) {
            this.mConfigureAutoDetectionEnabledCapability = i;
            return this;
        }

        public android.app.time.TimeCapabilities.Builder setSetManualTimeCapability(int i) {
            this.mSetManualTimeCapability = i;
            return this;
        }

        public android.app.time.TimeCapabilities build() {
            verifyCapabilitySet(this.mConfigureAutoDetectionEnabledCapability, "configureAutoDetectionEnabledCapability");
            verifyCapabilitySet(this.mSetManualTimeCapability, "mSetManualTimeCapability");
            return new android.app.time.TimeCapabilities(this);
        }

        private void verifyCapabilitySet(int i, java.lang.String str) {
            if (i == 0) {
                throw new java.lang.IllegalStateException(str + " was not set");
            }
        }
    }
}
