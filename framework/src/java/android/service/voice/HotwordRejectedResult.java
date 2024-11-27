package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class HotwordRejectedResult implements android.os.Parcelable {
    public static final int CONFIDENCE_LEVEL_HIGH = 3;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 2;
    public static final int CONFIDENCE_LEVEL_NONE = 0;
    public static final int CONFIDENCE_LEVEL_VERY_HIGH = 4;
    public static final android.os.Parcelable.Creator<android.service.voice.HotwordRejectedResult> CREATOR = new android.os.Parcelable.Creator<android.service.voice.HotwordRejectedResult>() { // from class: android.service.voice.HotwordRejectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordRejectedResult[] newArray(int i) {
            return new android.service.voice.HotwordRejectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordRejectedResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.HotwordRejectedResult(parcel);
        }
    };
    private final int mConfidenceLevel;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface HotwordConfidenceLevelValue {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    public static java.lang.String confidenceLevelToString(int i) {
        switch (i) {
            case 0:
                return "CONFIDENCE_LEVEL_NONE";
            case 1:
                return "CONFIDENCE_LEVEL_LOW";
            case 2:
                return "CONFIDENCE_LEVEL_MEDIUM";
            case 3:
                return "CONFIDENCE_LEVEL_HIGH";
            case 4:
                return "CONFIDENCE_LEVEL_VERY_HIGH";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    HotwordRejectedResult(int i) {
        this.mConfidenceLevel = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.service.voice.HotwordRejectedResult.HotwordConfidenceLevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public java.lang.String toString() {
        return "HotwordRejectedResult { confidenceLevel = " + this.mConfidenceLevel + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mConfidenceLevel == ((android.service.voice.HotwordRejectedResult) obj).mConfidenceLevel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 + this.mConfidenceLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mConfidenceLevel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HotwordRejectedResult(android.os.Parcel parcel) {
        this.mConfidenceLevel = parcel.readInt();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.service.voice.HotwordRejectedResult.HotwordConfidenceLevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;

        public android.service.voice.HotwordRejectedResult.Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mConfidenceLevel = i;
            return this;
        }

        public android.service.voice.HotwordRejectedResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mConfidenceLevel = android.service.voice.HotwordRejectedResult.defaultConfidenceLevel();
            }
            return new android.service.voice.HotwordRejectedResult(this.mConfidenceLevel);
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
