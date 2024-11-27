package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceAuthenticateOptions implements android.hardware.biometrics.AuthenticateOptions, android.os.Parcelable {
    public static final int AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN = 4;
    public static final int AUTHENTICATE_REASON_ASSISTANT_VISIBLE = 3;
    public static final int AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED = 5;
    public static final int AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED = 6;
    public static final int AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED = 7;
    public static final int AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN = 2;
    public static final int AUTHENTICATE_REASON_QS_EXPANDED = 8;
    public static final int AUTHENTICATE_REASON_STARTED_WAKING_UP = 1;
    public static final int AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER = 9;
    public static final int AUTHENTICATE_REASON_UDFPS_POINTER_DOWN = 10;
    public static final int AUTHENTICATE_REASON_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceAuthenticateOptions> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceAuthenticateOptions>() { // from class: android.hardware.face.FaceAuthenticateOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceAuthenticateOptions[] newArray(int i) {
            return new android.hardware.face.FaceAuthenticateOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceAuthenticateOptions createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceAuthenticateOptions(parcel);
        }
    };
    private java.lang.String mAttributionTag;
    private final int mAuthenticateReason;
    private final int mDisplayState;
    private java.lang.String mOpPackageName;
    private int mSensorId;
    private final int mUserId;
    private final int mWakeReason;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticateReason {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultUserId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSensorId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultAuthenticateReason() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultWakeReason() {
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

    public static java.lang.String authenticateReasonToString(int i) {
        switch (i) {
            case 0:
                return "AUTHENTICATE_REASON_UNKNOWN";
            case 1:
                return "AUTHENTICATE_REASON_STARTED_WAKING_UP";
            case 2:
                return "AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN";
            case 3:
                return "AUTHENTICATE_REASON_ASSISTANT_VISIBLE";
            case 4:
                return "AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN";
            case 5:
                return "AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED";
            case 6:
                return "AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED";
            case 7:
                return "AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED";
            case 8:
                return "AUTHENTICATE_REASON_QS_EXPANDED";
            case 9:
                return "AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER";
            case 10:
                return "AUTHENTICATE_REASON_UDFPS_POINTER_DOWN";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    FaceAuthenticateOptions(int i, int i2, int i3, int i4, int i5, java.lang.String str, java.lang.String str2) {
        this.mUserId = i;
        this.mSensorId = i2;
        this.mDisplayState = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.hardware.biometrics.AuthenticateOptions.DisplayState.class, (java.lang.annotation.Annotation) null, this.mDisplayState);
        this.mAuthenticateReason = i4;
        if (this.mAuthenticateReason != 0 && this.mAuthenticateReason != 1 && this.mAuthenticateReason != 2 && this.mAuthenticateReason != 3 && this.mAuthenticateReason != 4 && this.mAuthenticateReason != 5 && this.mAuthenticateReason != 6 && this.mAuthenticateReason != 7 && this.mAuthenticateReason != 8 && this.mAuthenticateReason != 9 && this.mAuthenticateReason != 10) {
            throw new java.lang.IllegalArgumentException("authenticateReason was " + this.mAuthenticateReason + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mWakeReason = i5;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.os.PowerManager.WakeReason.class, (java.lang.annotation.Annotation) null, this.mWakeReason);
        this.mOpPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        this.mAttributionTag = str2;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getUserId() {
        return this.mUserId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getSensorId() {
        return this.mSensorId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getDisplayState() {
        return this.mDisplayState;
    }

    public int getAuthenticateReason() {
        return this.mAuthenticateReason;
    }

    public int getWakeReason() {
        return this.mWakeReason;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public java.lang.String getOpPackageName() {
        return this.mOpPackageName;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    public android.hardware.face.FaceAuthenticateOptions setSensorId(int i) {
        this.mSensorId = i;
        return this;
    }

    public android.hardware.face.FaceAuthenticateOptions setOpPackageName(java.lang.String str) {
        this.mOpPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        return this;
    }

    public android.hardware.face.FaceAuthenticateOptions setAttributionTag(java.lang.String str) {
        this.mAttributionTag = str;
        return this;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions = (android.hardware.face.FaceAuthenticateOptions) obj;
        if (this.mUserId == faceAuthenticateOptions.mUserId && this.mSensorId == faceAuthenticateOptions.mSensorId && this.mDisplayState == faceAuthenticateOptions.mDisplayState && this.mAuthenticateReason == faceAuthenticateOptions.mAuthenticateReason && this.mWakeReason == faceAuthenticateOptions.mWakeReason && java.util.Objects.equals(this.mOpPackageName, faceAuthenticateOptions.mOpPackageName) && java.util.Objects.equals(this.mAttributionTag, faceAuthenticateOptions.mAttributionTag)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((this.mUserId + 31) * 31) + this.mSensorId) * 31) + this.mDisplayState) * 31) + this.mAuthenticateReason) * 31) + this.mWakeReason) * 31) + java.util.Objects.hashCode(this.mOpPackageName)) * 31) + java.util.Objects.hashCode(this.mAttributionTag);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mAttributionTag != null ? (byte) 64 : (byte) 0);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mSensorId);
        parcel.writeInt(this.mDisplayState);
        parcel.writeInt(this.mAuthenticateReason);
        parcel.writeInt(this.mWakeReason);
        parcel.writeString(this.mOpPackageName);
        if (this.mAttributionTag != null) {
            parcel.writeString(this.mAttributionTag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected FaceAuthenticateOptions(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = (readByte & 64) == 0 ? null : parcel.readString();
        this.mUserId = readInt;
        this.mSensorId = readInt2;
        this.mDisplayState = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.hardware.biometrics.AuthenticateOptions.DisplayState.class, (java.lang.annotation.Annotation) null, this.mDisplayState);
        this.mAuthenticateReason = readInt4;
        if (this.mAuthenticateReason != 0 && this.mAuthenticateReason != 1 && this.mAuthenticateReason != 2 && this.mAuthenticateReason != 3 && this.mAuthenticateReason != 4 && this.mAuthenticateReason != 5 && this.mAuthenticateReason != 6 && this.mAuthenticateReason != 7 && this.mAuthenticateReason != 8 && this.mAuthenticateReason != 9 && this.mAuthenticateReason != 10) {
            throw new java.lang.IllegalArgumentException("authenticateReason was " + this.mAuthenticateReason + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mWakeReason = readInt5;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.os.PowerManager.WakeReason.class, (java.lang.annotation.Annotation) null, this.mWakeReason);
        this.mOpPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOpPackageName);
        this.mAttributionTag = readString2;
    }

    public static class Builder {
        private java.lang.String mAttributionTag;
        private int mAuthenticateReason;
        private long mBuilderFieldsSet = 0;
        private int mDisplayState;
        private java.lang.String mOpPackageName;
        private int mSensorId;
        private int mUserId;
        private int mWakeReason;

        public android.hardware.face.FaceAuthenticateOptions.Builder setUserId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mUserId = i;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setSensorId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSensorId = i;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setDisplayState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDisplayState = i;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setAuthenticateReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mAuthenticateReason = i;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setWakeReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWakeReason = i;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setOpPackageName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mOpPackageName = str;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions.Builder setAttributionTag(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mAttributionTag = str;
            return this;
        }

        public android.hardware.face.FaceAuthenticateOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mUserId = android.hardware.face.FaceAuthenticateOptions.defaultUserId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSensorId = android.hardware.face.FaceAuthenticateOptions.defaultSensorId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDisplayState = android.hardware.face.FaceAuthenticateOptions.defaultDisplayState();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mAuthenticateReason = android.hardware.face.FaceAuthenticateOptions.defaultAuthenticateReason();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mWakeReason = android.hardware.face.FaceAuthenticateOptions.defaultWakeReason();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mOpPackageName = android.hardware.face.FaceAuthenticateOptions.defaultOpPackageName();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mAttributionTag = android.hardware.face.FaceAuthenticateOptions.defaultAttributionTag();
            }
            return new android.hardware.face.FaceAuthenticateOptions(this.mUserId, this.mSensorId, this.mDisplayState, this.mAuthenticateReason, this.mWakeReason, this.mOpPackageName, this.mAttributionTag);
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
