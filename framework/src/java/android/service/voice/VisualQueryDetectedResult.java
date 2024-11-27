package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryDetectedResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.VisualQueryDetectedResult> CREATOR = new android.os.Parcelable.Creator<android.service.voice.VisualQueryDetectedResult>() { // from class: android.service.voice.VisualQueryDetectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryDetectedResult[] newArray(int i) {
            return new android.service.voice.VisualQueryDetectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryDetectedResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.VisualQueryDetectedResult(parcel);
        }
    };
    private final byte[] mAccessibilityDetectionData;
    private final java.lang.String mPartialQuery;
    private final int mSpeakerId;

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String defaultPartialQuery() {
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] defaultAccessibilityDetectionData() {
        return null;
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
    }

    public android.service.voice.VisualQueryDetectedResult.Builder buildUpon() {
        return new android.service.voice.VisualQueryDetectedResult.Builder().setPartialQuery(this.mPartialQuery).setSpeakerId(this.mSpeakerId).setAccessibilityDetectionData(this.mAccessibilityDetectionData);
    }

    VisualQueryDetectedResult(java.lang.String str, int i, byte[] bArr) {
        this.mPartialQuery = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPartialQuery);
        this.mSpeakerId = i;
        this.mAccessibilityDetectionData = bArr;
        onConstructed();
    }

    public java.lang.String getPartialQuery() {
        return this.mPartialQuery;
    }

    public int getSpeakerId() {
        return this.mSpeakerId;
    }

    public byte[] getAccessibilityDetectionData() {
        return this.mAccessibilityDetectionData;
    }

    public java.lang.String toString() {
        return "VisualQueryDetectedResult { partialQuery = " + this.mPartialQuery + ", speakerId = " + this.mSpeakerId + ", accessibilityDetectionData = " + java.util.Arrays.toString(this.mAccessibilityDetectionData) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult = (android.service.voice.VisualQueryDetectedResult) obj;
        if (java.util.Objects.equals(this.mPartialQuery, visualQueryDetectedResult.mPartialQuery) && this.mSpeakerId == visualQueryDetectedResult.mSpeakerId && java.util.Arrays.equals(this.mAccessibilityDetectionData, visualQueryDetectedResult.mAccessibilityDetectionData)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mPartialQuery) + 31) * 31) + this.mSpeakerId) * 31) + java.util.Arrays.hashCode(this.mAccessibilityDetectionData);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPartialQuery);
        parcel.writeInt(this.mSpeakerId);
        parcel.writeByteArray(this.mAccessibilityDetectionData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisualQueryDetectedResult(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        byte[] createByteArray = parcel.createByteArray();
        this.mPartialQuery = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPartialQuery);
        this.mSpeakerId = readInt;
        this.mAccessibilityDetectionData = createByteArray;
        onConstructed();
    }

    public static final class Builder {
        private byte[] mAccessibilityDetectionData;
        private long mBuilderFieldsSet = 0;
        private java.lang.String mPartialQuery;
        private int mSpeakerId;

        public android.service.voice.VisualQueryDetectedResult.Builder setPartialQuery(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mPartialQuery = str;
            return this;
        }

        public android.service.voice.VisualQueryDetectedResult.Builder setSpeakerId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSpeakerId = i;
            return this;
        }

        public android.service.voice.VisualQueryDetectedResult.Builder setAccessibilityDetectionData(byte... bArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAccessibilityDetectionData = bArr;
            return this;
        }

        public android.service.voice.VisualQueryDetectedResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mPartialQuery = android.service.voice.VisualQueryDetectedResult.defaultPartialQuery();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSpeakerId = android.service.voice.VisualQueryDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mAccessibilityDetectionData = android.service.voice.VisualQueryDetectedResult.defaultAccessibilityDetectionData();
            }
            return new android.service.voice.VisualQueryDetectedResult(this.mPartialQuery, this.mSpeakerId, this.mAccessibilityDetectionData);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
