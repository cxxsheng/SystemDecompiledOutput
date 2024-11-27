package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public final class FingerprintAuthenticateOptions implements android.hardware.biometrics.AuthenticateOptions, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintAuthenticateOptions> CREATOR = new android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintAuthenticateOptions>() { // from class: android.hardware.fingerprint.FingerprintAuthenticateOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintAuthenticateOptions[] newArray(int i) {
            return new android.hardware.fingerprint.FingerprintAuthenticateOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintAuthenticateOptions createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.fingerprint.FingerprintAuthenticateOptions(parcel);
        }
    };
    private java.lang.String mAttributionTag;
    private final int mDisplayState;
    private final boolean mIgnoreEnrollmentState;
    private java.lang.String mOpPackageName;
    private int mSensorId;
    private final int mUserId;
    private android.hardware.biometrics.common.AuthenticateReason.Vendor mVendorReason;

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultUserId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSensorId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean defaultIgnoreEnrollmentState() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String defaultOpPackageName() {
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String defaultAttributionTag() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.biometrics.common.AuthenticateReason.Vendor defaultVendorReason() {
        return null;
    }

    FingerprintAuthenticateOptions(int i, int i2, boolean z, int i3, java.lang.String str, java.lang.String str2, android.hardware.biometrics.common.AuthenticateReason.Vendor vendor) {
        this.mUserId = i;
        this.mSensorId = i2;
        this.mIgnoreEnrollmentState = z;
        this.mDisplayState = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.hardware.biometrics.AuthenticateOptions.DisplayState.class, (java.lang.annotation.Annotation) null, this.mDisplayState);
        this.mOpPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        this.mAttributionTag = str2;
        this.mVendorReason = vendor;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getUserId() {
        return this.mUserId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getSensorId() {
        return this.mSensorId;
    }

    public boolean isIgnoreEnrollmentState() {
        return this.mIgnoreEnrollmentState;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getDisplayState() {
        return this.mDisplayState;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public java.lang.String getOpPackageName() {
        return this.mOpPackageName;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    public android.hardware.biometrics.common.AuthenticateReason.Vendor getVendorReason() {
        return this.mVendorReason;
    }

    public android.hardware.fingerprint.FingerprintAuthenticateOptions setSensorId(int i) {
        this.mSensorId = i;
        return this;
    }

    public android.hardware.fingerprint.FingerprintAuthenticateOptions setOpPackageName(java.lang.String str) {
        this.mOpPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        return this;
    }

    public android.hardware.fingerprint.FingerprintAuthenticateOptions setAttributionTag(java.lang.String str) {
        this.mAttributionTag = str;
        return this;
    }

    public android.hardware.fingerprint.FingerprintAuthenticateOptions setVendorReason(android.hardware.biometrics.common.AuthenticateReason.Vendor vendor) {
        this.mVendorReason = vendor;
        return this;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions = (android.hardware.fingerprint.FingerprintAuthenticateOptions) obj;
        if (this.mUserId == fingerprintAuthenticateOptions.mUserId && this.mSensorId == fingerprintAuthenticateOptions.mSensorId && this.mIgnoreEnrollmentState == fingerprintAuthenticateOptions.mIgnoreEnrollmentState && this.mDisplayState == fingerprintAuthenticateOptions.mDisplayState && java.util.Objects.equals(this.mOpPackageName, fingerprintAuthenticateOptions.mOpPackageName) && java.util.Objects.equals(this.mAttributionTag, fingerprintAuthenticateOptions.mAttributionTag) && java.util.Objects.equals(this.mVendorReason, fingerprintAuthenticateOptions.mVendorReason)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((this.mUserId + 31) * 31) + this.mSensorId) * 31) + java.lang.Boolean.hashCode(this.mIgnoreEnrollmentState)) * 31) + this.mDisplayState) * 31) + java.util.Objects.hashCode(this.mOpPackageName)) * 31) + java.util.Objects.hashCode(this.mAttributionTag)) * 31) + java.util.Objects.hashCode(this.mVendorReason);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mIgnoreEnrollmentState ? (byte) 4 : (byte) 0;
        if (this.mAttributionTag != null) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        if (this.mVendorReason != null) {
            b = (byte) (b | 64);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mSensorId);
        parcel.writeInt(this.mDisplayState);
        parcel.writeString(this.mOpPackageName);
        if (this.mAttributionTag != null) {
            parcel.writeString(this.mAttributionTag);
        }
        if (this.mVendorReason != null) {
            parcel.writeTypedObject(this.mVendorReason, i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FingerprintAuthenticateOptions(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        boolean z = (readByte & 4) != 0;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = (readByte & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 0 ? null : parcel.readString();
        android.hardware.biometrics.common.AuthenticateReason.Vendor vendor = (readByte & 64) == 0 ? null : (android.hardware.biometrics.common.AuthenticateReason.Vendor) parcel.readTypedObject(android.hardware.biometrics.common.AuthenticateReason.Vendor.CREATOR);
        this.mUserId = readInt;
        this.mSensorId = readInt2;
        this.mIgnoreEnrollmentState = z;
        this.mDisplayState = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.hardware.biometrics.AuthenticateOptions.DisplayState.class, (java.lang.annotation.Annotation) null, this.mDisplayState);
        this.mOpPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        this.mAttributionTag = readString2;
        this.mVendorReason = vendor;
    }

    public static final class Builder {
        private java.lang.String mAttributionTag;
        private long mBuilderFieldsSet = 0;
        private int mDisplayState;
        private boolean mIgnoreEnrollmentState;
        private java.lang.String mOpPackageName;
        private int mSensorId;
        private int mUserId;
        private android.hardware.biometrics.common.AuthenticateReason.Vendor mVendorReason;

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setUserId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mUserId = i;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setSensorId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSensorId = i;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setIgnoreEnrollmentState(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mIgnoreEnrollmentState = z;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setDisplayState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mDisplayState = i;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setOpPackageName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mOpPackageName = str;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setAttributionTag(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionTag = str;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder setVendorReason(android.hardware.biometrics.common.AuthenticateReason.Vendor vendor) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mVendorReason = vendor;
            return this;
        }

        public android.hardware.fingerprint.FingerprintAuthenticateOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mUserId = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultUserId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSensorId = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultSensorId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mIgnoreEnrollmentState = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultIgnoreEnrollmentState();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mDisplayState = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultDisplayState();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mOpPackageName = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultOpPackageName();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAttributionTag = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultAttributionTag();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mVendorReason = android.hardware.fingerprint.FingerprintAuthenticateOptions.defaultVendorReason();
            }
            return new android.hardware.fingerprint.FingerprintAuthenticateOptions(this.mUserId, this.mSensorId, this.mIgnoreEnrollmentState, this.mDisplayState, this.mOpPackageName, this.mAttributionTag, this.mVendorReason);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
