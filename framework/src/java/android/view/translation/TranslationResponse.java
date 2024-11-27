package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationResponse> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationResponse>() { // from class: android.view.translation.TranslationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationResponse[] newArray(int i) {
            return new android.view.translation.TranslationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationResponse createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationResponse(parcel);
        }
    };
    public static final int TRANSLATION_STATUS_CONTEXT_UNSUPPORTED = 2;
    public static final int TRANSLATION_STATUS_SUCCESS = 0;
    public static final int TRANSLATION_STATUS_UNKNOWN_ERROR = 1;
    private final boolean mFinalResponse;
    private final android.util.SparseArray<android.view.translation.TranslationResponseValue> mTranslationResponseValues;
    private final int mTranslationStatus;
    private final android.util.SparseArray<android.view.translation.ViewTranslationResponse> mViewTranslationResponses;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TranslationStatus {
    }

    static abstract class BaseBuilder {
        @java.lang.Deprecated
        public abstract android.view.translation.TranslationResponse.Builder setTranslationStatus(int i);

        BaseBuilder() {
        }

        public android.view.translation.TranslationResponse.Builder setTranslationResponseValue(int i, android.view.translation.TranslationResponseValue translationResponseValue) {
            java.util.Objects.requireNonNull(translationResponseValue, "value should not be null");
            android.view.translation.TranslationResponse.Builder builder = (android.view.translation.TranslationResponse.Builder) this;
            if (builder.mTranslationResponseValues == null) {
                builder.setTranslationResponseValues(new android.util.SparseArray<>());
            }
            builder.mTranslationResponseValues.put(i, translationResponseValue);
            return builder;
        }

        public android.view.translation.TranslationResponse.Builder setViewTranslationResponse(int i, android.view.translation.ViewTranslationResponse viewTranslationResponse) {
            java.util.Objects.requireNonNull(viewTranslationResponse, "value should not be null");
            android.view.translation.TranslationResponse.Builder builder = (android.view.translation.TranslationResponse.Builder) this;
            if (builder.mViewTranslationResponses == null) {
                builder.setViewTranslationResponses(new android.util.SparseArray<>());
            }
            builder.mViewTranslationResponses.put(i, viewTranslationResponse);
            return builder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.SparseArray<android.view.translation.TranslationResponseValue> defaultTranslationResponseValues() {
        return new android.util.SparseArray<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.SparseArray<android.view.translation.ViewTranslationResponse> defaultViewTranslationResponses() {
        return new android.util.SparseArray<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean defaultFinalResponse() {
        return true;
    }

    public static java.lang.String translationStatusToString(int i) {
        switch (i) {
            case 0:
                return "TRANSLATION_STATUS_SUCCESS";
            case 1:
                return "TRANSLATION_STATUS_UNKNOWN_ERROR";
            case 2:
                return "TRANSLATION_STATUS_CONTEXT_UNSUPPORTED";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    TranslationResponse(int i, android.util.SparseArray<android.view.translation.TranslationResponseValue> sparseArray, android.util.SparseArray<android.view.translation.ViewTranslationResponse> sparseArray2, boolean z) {
        this.mTranslationStatus = i;
        if (this.mTranslationStatus != 0 && this.mTranslationStatus != 1 && this.mTranslationStatus != 2) {
            throw new java.lang.IllegalArgumentException("translationStatus was " + this.mTranslationStatus + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mTranslationResponseValues = sparseArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationResponseValues);
        this.mViewTranslationResponses = sparseArray2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mViewTranslationResponses);
        this.mFinalResponse = z;
    }

    public int getTranslationStatus() {
        return this.mTranslationStatus;
    }

    public android.util.SparseArray<android.view.translation.TranslationResponseValue> getTranslationResponseValues() {
        return this.mTranslationResponseValues;
    }

    public android.util.SparseArray<android.view.translation.ViewTranslationResponse> getViewTranslationResponses() {
        return this.mViewTranslationResponses;
    }

    public boolean isFinalResponse() {
        return this.mFinalResponse;
    }

    public java.lang.String toString() {
        return "TranslationResponse { translationStatus = " + translationStatusToString(this.mTranslationStatus) + ", translationResponseValues = " + this.mTranslationResponseValues + ", viewTranslationResponses = " + this.mViewTranslationResponses + ", finalResponse = " + this.mFinalResponse + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mFinalResponse ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mTranslationStatus);
        parcel.writeSparseArray(this.mTranslationResponseValues);
        parcel.writeSparseArray(this.mViewTranslationResponses);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationResponse(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        int readInt = parcel.readInt();
        android.util.SparseArray<android.view.translation.TranslationResponseValue> readSparseArray = parcel.readSparseArray(android.view.translation.TranslationResponseValue.class.getClassLoader());
        android.util.SparseArray<android.view.translation.ViewTranslationResponse> readSparseArray2 = parcel.readSparseArray(android.view.translation.ViewTranslationResponse.class.getClassLoader());
        this.mTranslationStatus = readInt;
        if (this.mTranslationStatus != 0 && this.mTranslationStatus != 1 && this.mTranslationStatus != 2) {
            throw new java.lang.IllegalArgumentException("translationStatus was " + this.mTranslationStatus + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mTranslationResponseValues = readSparseArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationResponseValues);
        this.mViewTranslationResponses = readSparseArray2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mViewTranslationResponses);
        this.mFinalResponse = z;
    }

    public static final class Builder extends android.view.translation.TranslationResponse.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private boolean mFinalResponse;
        private android.util.SparseArray<android.view.translation.TranslationResponseValue> mTranslationResponseValues;
        private int mTranslationStatus;
        private android.util.SparseArray<android.view.translation.ViewTranslationResponse> mViewTranslationResponses;

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ android.view.translation.TranslationResponse.Builder setTranslationResponseValue(int i, android.view.translation.TranslationResponseValue translationResponseValue) {
            return super.setTranslationResponseValue(i, translationResponseValue);
        }

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ android.view.translation.TranslationResponse.Builder setViewTranslationResponse(int i, android.view.translation.ViewTranslationResponse viewTranslationResponse) {
            return super.setViewTranslationResponse(i, viewTranslationResponse);
        }

        public Builder(int i) {
            this.mTranslationStatus = i;
            if (this.mTranslationStatus != 0 && this.mTranslationStatus != 1 && this.mTranslationStatus != 2) {
                throw new java.lang.IllegalArgumentException("translationStatus was " + this.mTranslationStatus + " but must be one of: TRANSLATION_STATUS_SUCCESS(0), TRANSLATION_STATUS_UNKNOWN_ERROR(1), TRANSLATION_STATUS_CONTEXT_UNSUPPORTED(2" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        @Override // android.view.translation.TranslationResponse.BaseBuilder
        @java.lang.Deprecated
        public android.view.translation.TranslationResponse.Builder setTranslationStatus(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mTranslationStatus = i;
            return this;
        }

        public android.view.translation.TranslationResponse.Builder setTranslationResponseValues(android.util.SparseArray<android.view.translation.TranslationResponseValue> sparseArray) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationResponseValues = sparseArray;
            return this;
        }

        public android.view.translation.TranslationResponse.Builder setViewTranslationResponses(android.util.SparseArray<android.view.translation.ViewTranslationResponse> sparseArray) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mViewTranslationResponses = sparseArray;
            return this;
        }

        public android.view.translation.TranslationResponse.Builder setFinalResponse(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mFinalResponse = z;
            return this;
        }

        public android.view.translation.TranslationResponse build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mTranslationResponseValues = android.view.translation.TranslationResponse.defaultTranslationResponseValues();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mViewTranslationResponses = android.view.translation.TranslationResponse.defaultViewTranslationResponses();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mFinalResponse = android.view.translation.TranslationResponse.defaultFinalResponse();
            }
            return new android.view.translation.TranslationResponse(this.mTranslationStatus, this.mTranslationResponseValues, this.mViewTranslationResponses, this.mFinalResponse);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
