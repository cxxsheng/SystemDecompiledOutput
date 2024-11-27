package android.service.autofill;

/* loaded from: classes3.dex */
public final class FillRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.FillRequest> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.FillRequest>() { // from class: android.service.autofill.FillRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillRequest[] newArray(int i) {
            return new android.service.autofill.FillRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.FillRequest(parcel);
        }
    };
    public static final int FLAG_COMPATIBILITY_MODE_REQUEST = 2;
    public static final int FLAG_IME_SHOWING = 128;
    public static final int FLAG_MANUAL_REQUEST = 1;
    public static final int FLAG_PASSWORD_INPUT_TYPE = 4;
    public static final int FLAG_PCC_DETECTION = 512;
    public static final int FLAG_RESET_FILL_DIALOG_STATE = 256;
    public static final int FLAG_SCREEN_HAS_CREDMAN_FIELD = 1024;
    public static final int FLAG_SUPPORTS_FILL_DIALOG = 64;
    public static final int FLAG_VIEW_NOT_FOCUSED = 16;
    public static final int FLAG_VIEW_REQUESTS_CREDMAN_SERVICE = 2048;
    public static final int INVALID_REQUEST_ID = Integer.MIN_VALUE;
    private final android.os.Bundle mClientState;
    private final android.content.IntentSender mDelayedFillIntentSender;
    private final java.util.List<android.service.autofill.FillContext> mFillContexts;
    private final int mFlags;
    private final java.util.List<java.lang.String> mHints;
    private final int mId;
    private final android.view.inputmethod.InlineSuggestionsRequest mInlineSuggestionsRequest;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mFillContexts, "contexts");
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mHints, "hints");
    }

    public static java.lang.String requestFlagsToString(int i) {
        return com.android.internal.util.BitUtils.flagsToString(i, new java.util.function.IntFunction() { // from class: android.service.autofill.FillRequest$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                return android.service.autofill.FillRequest.singleRequestFlagsToString(i2);
            }
        });
    }

    static java.lang.String singleRequestFlagsToString(int i) {
        switch (i) {
            case 1:
                return "FLAG_MANUAL_REQUEST";
            case 2:
                return "FLAG_COMPATIBILITY_MODE_REQUEST";
            case 4:
                return "FLAG_PASSWORD_INPUT_TYPE";
            case 16:
                return "FLAG_VIEW_NOT_FOCUSED";
            case 64:
                return "FLAG_SUPPORTS_FILL_DIALOG";
            case 128:
                return "FLAG_IME_SHOWING";
            case 256:
                return "FLAG_RESET_FILL_DIALOG_STATE";
            case 512:
                return "FLAG_PCC_DETECTION";
            case 1024:
                return "FLAG_SCREEN_HAS_CREDMAN_FIELD";
            case 2048:
                return "FLAG_VIEW_REQUESTS_CREDMAN_SERVICE";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public FillRequest(int i, java.util.List<android.service.autofill.FillContext> list, java.util.List<java.lang.String> list2, android.os.Bundle bundle, int i2, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.content.IntentSender intentSender) {
        this.mId = i;
        this.mFillContexts = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFillContexts);
        this.mHints = list2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHints);
        this.mClientState = bundle;
        this.mFlags = i2;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mFlags, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = intentSender;
        onConstructed();
    }

    public int getId() {
        return this.mId;
    }

    public java.util.List<android.service.autofill.FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    public java.util.List<java.lang.String> getHints() {
        return this.mHints;
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public android.view.inputmethod.InlineSuggestionsRequest getInlineSuggestionsRequest() {
        return this.mInlineSuggestionsRequest;
    }

    public android.content.IntentSender getDelayedFillIntentSender() {
        return this.mDelayedFillIntentSender;
    }

    public java.lang.String toString() {
        return "FillRequest { id = " + this.mId + ", fillContexts = " + this.mFillContexts + ", hints = " + this.mHints + ", clientState = " + this.mClientState + ", flags = " + requestFlagsToString(this.mFlags) + ", inlineSuggestionsRequest = " + this.mInlineSuggestionsRequest + ", delayedFillIntentSender = " + this.mDelayedFillIntentSender + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mClientState != null ? (byte) 8 : (byte) 0;
        if (this.mInlineSuggestionsRequest != null) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        if (this.mDelayedFillIntentSender != null) {
            b = (byte) (b | 64);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mId);
        parcel.writeParcelableList(this.mFillContexts, i);
        parcel.writeStringList(this.mHints);
        if (this.mClientState != null) {
            parcel.writeBundle(this.mClientState);
        }
        parcel.writeInt(this.mFlags);
        if (this.mInlineSuggestionsRequest != null) {
            parcel.writeTypedObject(this.mInlineSuggestionsRequest, i);
        }
        if (this.mDelayedFillIntentSender != null) {
            parcel.writeTypedObject(this.mDelayedFillIntentSender, i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FillRequest(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.service.autofill.FillContext.class.getClassLoader());
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        parcel.readStringList(arrayList2);
        android.os.Bundle readBundle = (readByte & 8) == 0 ? null : parcel.readBundle();
        int readInt2 = parcel.readInt();
        android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest = (readByte & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 0 ? null : (android.view.inputmethod.InlineSuggestionsRequest) parcel.readTypedObject(android.view.inputmethod.InlineSuggestionsRequest.CREATOR);
        android.content.IntentSender intentSender = (readByte & 64) == 0 ? null : (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
        this.mId = readInt;
        this.mFillContexts = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFillContexts);
        this.mHints = arrayList2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHints);
        this.mClientState = readBundle;
        this.mFlags = readInt2;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mFlags, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = intentSender;
        onConstructed();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
