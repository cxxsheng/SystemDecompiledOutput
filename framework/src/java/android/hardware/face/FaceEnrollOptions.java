package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceEnrollOptions implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceEnrollOptions> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceEnrollOptions>() { // from class: android.hardware.face.FaceEnrollOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollOptions[] newArray(int i) {
            return new android.hardware.face.FaceEnrollOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollOptions createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceEnrollOptions(parcel);
        }
    };
    public static final int ENROLL_REASON_RE_ENROLL_NOTIFICATION = 1;
    public static final int ENROLL_REASON_SETTINGS = 2;
    public static final int ENROLL_REASON_SUW = 3;
    public static final int ENROLL_REASON_UNKNOWN = 0;
    private final int mEnrollReason;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnrollReason {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEnrollReason() {
        return 0;
    }

    public static java.lang.String enrollReasonToString(int i) {
        switch (i) {
            case 0:
                return "ENROLL_REASON_UNKNOWN";
            case 1:
                return "ENROLL_REASON_RE_ENROLL_NOTIFICATION";
            case 2:
                return "ENROLL_REASON_SETTINGS";
            case 3:
                return "ENROLL_REASON_SUW";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    FaceEnrollOptions(int i) {
        this.mEnrollReason = i;
        if (this.mEnrollReason != 0 && this.mEnrollReason != 1 && this.mEnrollReason != 2 && this.mEnrollReason != 3) {
            throw new java.lang.IllegalArgumentException("enrollReason was " + this.mEnrollReason + " but must be one of: ENROLL_REASON_UNKNOWN(0), ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2), ENROLL_REASON_SUW(3" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public int getEnrollReason() {
        return this.mEnrollReason;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mEnrollReason == ((android.hardware.face.FaceEnrollOptions) obj).mEnrollReason) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 + this.mEnrollReason;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEnrollReason);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected FaceEnrollOptions(android.os.Parcel parcel) {
        this.mEnrollReason = parcel.readInt();
        if (this.mEnrollReason != 0 && this.mEnrollReason != 1 && this.mEnrollReason != 2 && this.mEnrollReason != 3) {
            throw new java.lang.IllegalArgumentException("enrollReason was " + this.mEnrollReason + " but must be one of: ENROLL_REASON_UNKNOWN(0), ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2), ENROLL_REASON_SUW(3" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public static class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEnrollReason;

        public android.hardware.face.FaceEnrollOptions.Builder setEnrollReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEnrollReason = i;
            return this;
        }

        public android.hardware.face.FaceEnrollOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEnrollReason = android.hardware.face.FaceEnrollOptions.defaultEnrollReason();
            }
            return new android.hardware.face.FaceEnrollOptions(this.mEnrollReason);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}