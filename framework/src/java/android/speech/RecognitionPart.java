package android.speech;

/* loaded from: classes3.dex */
public final class RecognitionPart implements android.os.Parcelable {
    public static final int CONFIDENCE_LEVEL_HIGH = 5;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 3;
    public static final int CONFIDENCE_LEVEL_MEDIUM_HIGH = 4;
    public static final int CONFIDENCE_LEVEL_MEDIUM_LOW = 2;
    public static final int CONFIDENCE_LEVEL_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.speech.RecognitionPart> CREATOR = new android.os.Parcelable.Creator<android.speech.RecognitionPart>() { // from class: android.speech.RecognitionPart.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.RecognitionPart[] newArray(int i) {
            return new android.speech.RecognitionPart[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.RecognitionPart createFromParcel(android.os.Parcel parcel) {
            return new android.speech.RecognitionPart(parcel);
        }
    };
    private final int mConfidenceLevel;
    private final java.lang.String mFormattedText;
    private final java.lang.String mRawText;
    private final long mTimestampMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String defaultFormattedText() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long defaultTimestampMillis() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(this.mTimestampMillis, "The timestamp must be non-negative.");
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public android.speech.RecognitionPart.Builder setFormattedText(java.lang.String str) {
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
            android.speech.RecognitionPart.Builder builder = (android.speech.RecognitionPart.Builder) this;
            builder.checkNotUsed();
            builder.mBuilderFieldsSet |= 2;
            builder.mFormattedText = str;
            return builder;
        }
    }

    public static java.lang.String confidenceLevelToString(int i) {
        switch (i) {
            case 0:
                return "CONFIDENCE_LEVEL_UNKNOWN";
            case 1:
                return "CONFIDENCE_LEVEL_LOW";
            case 2:
                return "CONFIDENCE_LEVEL_MEDIUM_LOW";
            case 3:
                return "CONFIDENCE_LEVEL_MEDIUM";
            case 4:
                return "CONFIDENCE_LEVEL_MEDIUM_HIGH";
            case 5:
                return "CONFIDENCE_LEVEL_HIGH";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    RecognitionPart(java.lang.String str, java.lang.String str2, long j, int i) {
        this.mRawText = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRawText);
        this.mFormattedText = str2;
        this.mTimestampMillis = j;
        this.mConfidenceLevel = i;
        if (this.mConfidenceLevel != 0 && this.mConfidenceLevel != 1 && this.mConfidenceLevel != 2 && this.mConfidenceLevel != 3 && this.mConfidenceLevel != 4 && this.mConfidenceLevel != 5) {
            throw new java.lang.IllegalArgumentException("confidenceLevel was " + this.mConfidenceLevel + " but must be one of: CONFIDENCE_LEVEL_UNKNOWN(0), CONFIDENCE_LEVEL_LOW(1), CONFIDENCE_LEVEL_MEDIUM_LOW(2), CONFIDENCE_LEVEL_MEDIUM(3), CONFIDENCE_LEVEL_MEDIUM_HIGH(4), CONFIDENCE_LEVEL_HIGH(5" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        onConstructed();
    }

    public java.lang.String getRawText() {
        return this.mRawText;
    }

    public java.lang.String getFormattedText() {
        return this.mFormattedText;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public java.lang.String toString() {
        return "RecognitionPart { rawText = " + this.mRawText + ", formattedText = " + this.mFormattedText + ", timestampMillis = " + this.mTimestampMillis + ", confidenceLevel = " + confidenceLevelToString(this.mConfidenceLevel) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.speech.RecognitionPart recognitionPart = (android.speech.RecognitionPart) obj;
        if (java.util.Objects.equals(this.mRawText, recognitionPart.mRawText) && java.util.Objects.equals(this.mFormattedText, recognitionPart.mFormattedText) && this.mTimestampMillis == recognitionPart.mTimestampMillis && this.mConfidenceLevel == recognitionPart.mConfidenceLevel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((java.util.Objects.hashCode(this.mRawText) + 31) * 31) + java.util.Objects.hashCode(this.mFormattedText)) * 31) + java.lang.Long.hashCode(this.mTimestampMillis)) * 31) + this.mConfidenceLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mFormattedText != null ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mRawText);
        if (this.mFormattedText != null) {
            parcel.writeString(this.mFormattedText);
        }
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeInt(this.mConfidenceLevel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RecognitionPart(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = (readByte & 2) == 0 ? null : parcel.readString();
        long readLong = parcel.readLong();
        int readInt = parcel.readInt();
        this.mRawText = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRawText);
        this.mFormattedText = readString2;
        this.mTimestampMillis = readLong;
        this.mConfidenceLevel = readInt;
        if (this.mConfidenceLevel != 0 && this.mConfidenceLevel != 1 && this.mConfidenceLevel != 2 && this.mConfidenceLevel != 3 && this.mConfidenceLevel != 4 && this.mConfidenceLevel != 5) {
            throw new java.lang.IllegalArgumentException("confidenceLevel was " + this.mConfidenceLevel + " but must be one of: CONFIDENCE_LEVEL_UNKNOWN(0), CONFIDENCE_LEVEL_LOW(1), CONFIDENCE_LEVEL_MEDIUM_LOW(2), CONFIDENCE_LEVEL_MEDIUM(3), CONFIDENCE_LEVEL_MEDIUM_HIGH(4), CONFIDENCE_LEVEL_HIGH(5" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        onConstructed();
    }

    public static final class Builder extends android.speech.RecognitionPart.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private java.lang.String mFormattedText;
        private java.lang.String mRawText;
        private long mTimestampMillis;

        @Override // android.speech.RecognitionPart.BaseBuilder
        public /* bridge */ /* synthetic */ android.speech.RecognitionPart.Builder setFormattedText(java.lang.String str) {
            return super.setFormattedText(str);
        }

        public Builder(java.lang.String str) {
            this.mRawText = str;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRawText);
        }

        public android.speech.RecognitionPart.Builder setRawText(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mRawText = str;
            return this;
        }

        public android.speech.RecognitionPart.Builder setTimestampMillis(long j) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTimestampMillis = j;
            return this;
        }

        public android.speech.RecognitionPart.Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mConfidenceLevel = i;
            return this;
        }

        public android.speech.RecognitionPart build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mFormattedText = android.speech.RecognitionPart.defaultFormattedText();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mTimestampMillis = android.speech.RecognitionPart.defaultTimestampMillis();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mConfidenceLevel = android.speech.RecognitionPart.defaultConfidenceLevel();
            }
            return new android.speech.RecognitionPart(this.mRawText, this.mFormattedText, this.mTimestampMillis, this.mConfidenceLevel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
