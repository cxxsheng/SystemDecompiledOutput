package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationCapability implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationCapability> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationCapability>() { // from class: android.view.translation.TranslationCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationCapability[] newArray(int i) {
            return new android.view.translation.TranslationCapability[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationCapability createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationCapability(parcel);
        }
    };
    public static final int STATE_AVAILABLE_TO_DOWNLOAD = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_NOT_AVAILABLE = 4;
    public static final int STATE_ON_DEVICE = 3;
    public static final int STATE_REMOVED_AND_AVAILABLE = 1000;
    private final android.view.translation.TranslationSpec mSourceSpec;
    private final int mState;
    private final int mSupportedTranslationFlags;
    private final android.view.translation.TranslationSpec mTargetSpec;
    private final boolean mUiTranslationEnabled;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModelState {
    }

    @android.annotation.SystemApi
    public TranslationCapability(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, boolean z, int i2) {
        java.util.Objects.requireNonNull(translationSpec, "sourceSpec should not be null");
        java.util.Objects.requireNonNull(translationSpec2, "targetSpec should not be null");
        this.mState = i;
        this.mSourceSpec = translationSpec;
        this.mTargetSpec = translationSpec2;
        this.mUiTranslationEnabled = z;
        this.mSupportedTranslationFlags = i2;
    }

    public static java.lang.String modelStateToString(int i) {
        switch (i) {
            case 1:
                return "STATE_AVAILABLE_TO_DOWNLOAD";
            case 2:
                return "STATE_DOWNLOADING";
            case 3:
                return "STATE_ON_DEVICE";
            case 4:
                return "STATE_NOT_AVAILABLE";
            case 1000:
                return "STATE_REMOVED_AND_AVAILABLE";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public int getState() {
        return this.mState;
    }

    public android.view.translation.TranslationSpec getSourceSpec() {
        return this.mSourceSpec;
    }

    public android.view.translation.TranslationSpec getTargetSpec() {
        return this.mTargetSpec;
    }

    public boolean isUiTranslationEnabled() {
        return this.mUiTranslationEnabled;
    }

    public int getSupportedTranslationFlags() {
        return this.mSupportedTranslationFlags;
    }

    public java.lang.String toString() {
        return "TranslationCapability { state = " + modelStateToString(this.mState) + ", sourceSpec = " + this.mSourceSpec + ", targetSpec = " + this.mTargetSpec + ", uiTranslationEnabled = " + this.mUiTranslationEnabled + ", supportedTranslationFlags = " + this.mSupportedTranslationFlags + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mUiTranslationEnabled ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mState);
        parcel.writeTypedObject(this.mSourceSpec, i);
        parcel.writeTypedObject(this.mTargetSpec, i);
        parcel.writeInt(this.mSupportedTranslationFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationCapability(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        int readInt = parcel.readInt();
        android.view.translation.TranslationSpec translationSpec = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
        android.view.translation.TranslationSpec translationSpec2 = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
        int readInt2 = parcel.readInt();
        this.mState = readInt;
        if (this.mState != 1 && this.mState != 2 && this.mState != 3 && this.mState != 4 && this.mState != 1000) {
            throw new java.lang.IllegalArgumentException("state was " + this.mState + " but must be one of: STATE_AVAILABLE_TO_DOWNLOAD(1), STATE_DOWNLOADING(2), STATE_ON_DEVICE(3), STATE_NOT_AVAILABLE(4), STATE_REMOVED_AND_AVAILABLE(1000" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mSourceSpec = translationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSourceSpec);
        this.mTargetSpec = translationSpec2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTargetSpec);
        this.mUiTranslationEnabled = z;
        this.mSupportedTranslationFlags = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.translation.TranslationContext.TranslationFlag.class, (java.lang.annotation.Annotation) null, this.mSupportedTranslationFlags);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
