package android.service.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AmbientContextDetectionServiceStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.ambientcontext.AmbientContextDetectionServiceStatus> CREATOR = new android.os.Parcelable.Creator<android.service.ambientcontext.AmbientContextDetectionServiceStatus>() { // from class: android.service.ambientcontext.AmbientContextDetectionServiceStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.ambientcontext.AmbientContextDetectionServiceStatus[] newArray(int i) {
            return new android.service.ambientcontext.AmbientContextDetectionServiceStatus[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.ambientcontext.AmbientContextDetectionServiceStatus createFromParcel(android.os.Parcel parcel) {
            return new android.service.ambientcontext.AmbientContextDetectionServiceStatus(parcel);
        }
    };
    public static final java.lang.String STATUS_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextServiceStatusBundleKey";
    private final java.lang.String mPackageName;
    private final int mStatusCode;

    AmbientContextDetectionServiceStatus(int i, java.lang.String str) {
        this.mStatusCode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextManager.StatusCode.class, (java.lang.annotation.Annotation) null, this.mStatusCode);
        this.mPackageName = str;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String toString() {
        return "AmbientContextDetectionServiceStatus { statusCode = " + this.mStatusCode + ", packageName = " + this.mPackageName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte((byte) 0);
        parcel.writeInt(this.mStatusCode);
        parcel.writeString(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextDetectionServiceStatus(android.os.Parcel parcel) {
        parcel.readByte();
        int readInt = parcel.readInt();
        java.lang.String readString = parcel.readString();
        this.mStatusCode = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextManager.StatusCode.class, (java.lang.annotation.Annotation) null, this.mStatusCode);
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private java.lang.String mPackageName;
        private int mStatusCode;

        public Builder(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mPackageName = str;
        }

        public android.service.ambientcontext.AmbientContextDetectionServiceStatus.Builder setStatusCode(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mStatusCode = i;
            return this;
        }

        public android.service.ambientcontext.AmbientContextDetectionServiceStatus build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mStatusCode = 0;
            }
            return new android.service.ambientcontext.AmbientContextDetectionServiceStatus(this.mStatusCode, this.mPackageName);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
