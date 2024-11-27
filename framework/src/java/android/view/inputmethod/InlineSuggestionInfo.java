package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InlineSuggestionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionInfo>() { // from class: android.view.inputmethod.InlineSuggestionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionInfo[] newArray(int i) {
            return new android.view.inputmethod.InlineSuggestionInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InlineSuggestionInfo(parcel);
        }
    };
    public static final java.lang.String SOURCE_AUTOFILL = "android:autofill";
    public static final java.lang.String SOURCE_PLATFORM = "android:platform";
    public static final java.lang.String TYPE_ACTION = "android:autofill:action";
    public static final java.lang.String TYPE_SUGGESTION = "android:autofill:suggestion";
    private final java.lang.String[] mAutofillHints;
    private final android.widget.inline.InlinePresentationSpec mInlinePresentationSpec;
    private final boolean mPinned;
    private final java.lang.String mSource;
    private final android.view.inputmethod.InlineSuggestion mTooltip;
    private final java.lang.String mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static android.view.inputmethod.InlineSuggestionInfo newInlineSuggestionInfo(android.widget.inline.InlinePresentationSpec inlinePresentationSpec, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, boolean z) {
        return new android.view.inputmethod.InlineSuggestionInfo(inlinePresentationSpec, str, strArr, str2, z, null);
    }

    public static android.view.inputmethod.InlineSuggestionInfo newInlineSuggestionInfo(android.widget.inline.InlinePresentationSpec inlinePresentationSpec, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, boolean z, android.view.inputmethod.InlineSuggestion inlineSuggestion) {
        return new android.view.inputmethod.InlineSuggestionInfo(inlinePresentationSpec, str, strArr, str2, z, inlineSuggestion);
    }

    public InlineSuggestionInfo(android.widget.inline.InlinePresentationSpec inlinePresentationSpec, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, boolean z, android.view.inputmethod.InlineSuggestion inlineSuggestion) {
        this.mInlinePresentationSpec = inlinePresentationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpec);
        this.mSource = str;
        if (!java.util.Objects.equals(this.mSource, SOURCE_AUTOFILL) && !java.util.Objects.equals(this.mSource, SOURCE_PLATFORM)) {
            throw new java.lang.IllegalArgumentException("source was " + this.mSource + " but must be one of: SOURCE_AUTOFILL(" + SOURCE_AUTOFILL + "), SOURCE_PLATFORM(" + SOURCE_PLATFORM + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSource);
        this.mAutofillHints = strArr;
        this.mType = str2;
        if (!java.util.Objects.equals(this.mType, TYPE_SUGGESTION) && !java.util.Objects.equals(this.mType, TYPE_ACTION)) {
            throw new java.lang.IllegalArgumentException("type was " + this.mType + " but must be one of: TYPE_SUGGESTION(" + TYPE_SUGGESTION + "), TYPE_ACTION(" + TYPE_ACTION + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mPinned = z;
        this.mTooltip = inlineSuggestion;
    }

    public android.widget.inline.InlinePresentationSpec getInlinePresentationSpec() {
        return this.mInlinePresentationSpec;
    }

    public java.lang.String getSource() {
        return this.mSource;
    }

    public java.lang.String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public boolean isPinned() {
        return this.mPinned;
    }

    public android.view.inputmethod.InlineSuggestion getTooltip() {
        return this.mTooltip;
    }

    public java.lang.String toString() {
        return "InlineSuggestionInfo { inlinePresentationSpec = " + this.mInlinePresentationSpec + ", source = " + this.mSource + ", autofillHints = " + java.util.Arrays.toString(this.mAutofillHints) + ", type = " + this.mType + ", pinned = " + this.mPinned + ", tooltip = " + this.mTooltip + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.inputmethod.InlineSuggestionInfo inlineSuggestionInfo = (android.view.inputmethod.InlineSuggestionInfo) obj;
        if (java.util.Objects.equals(this.mInlinePresentationSpec, inlineSuggestionInfo.mInlinePresentationSpec) && java.util.Objects.equals(this.mSource, inlineSuggestionInfo.mSource) && java.util.Arrays.equals(this.mAutofillHints, inlineSuggestionInfo.mAutofillHints) && java.util.Objects.equals(this.mType, inlineSuggestionInfo.mType) && this.mPinned == inlineSuggestionInfo.mPinned && java.util.Objects.equals(this.mTooltip, inlineSuggestionInfo.mTooltip)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((java.util.Objects.hashCode(this.mInlinePresentationSpec) + 31) * 31) + java.util.Objects.hashCode(this.mSource)) * 31) + java.util.Arrays.hashCode(this.mAutofillHints)) * 31) + java.util.Objects.hashCode(this.mType)) * 31) + java.lang.Boolean.hashCode(this.mPinned)) * 31) + java.util.Objects.hashCode(this.mTooltip);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mPinned ? (byte) 16 : (byte) 0;
        if (this.mAutofillHints != null) {
            b = (byte) (b | 4);
        }
        if (this.mTooltip != null) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mInlinePresentationSpec, i);
        parcel.writeString(this.mSource);
        if (this.mAutofillHints != null) {
            parcel.writeStringArray(this.mAutofillHints);
        }
        parcel.writeString(this.mType);
        if (this.mTooltip != null) {
            parcel.writeTypedObject(this.mTooltip, i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionInfo(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        boolean z = (readByte & 16) != 0;
        android.widget.inline.InlinePresentationSpec inlinePresentationSpec = (android.widget.inline.InlinePresentationSpec) parcel.readTypedObject(android.widget.inline.InlinePresentationSpec.CREATOR);
        java.lang.String readString = parcel.readString();
        java.lang.String[] createStringArray = (readByte & 4) == 0 ? null : parcel.createStringArray();
        java.lang.String readString2 = parcel.readString();
        android.view.inputmethod.InlineSuggestion inlineSuggestion = (readByte & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 0 ? null : (android.view.inputmethod.InlineSuggestion) parcel.readTypedObject(android.view.inputmethod.InlineSuggestion.CREATOR);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpec);
        this.mSource = readString;
        if (!java.util.Objects.equals(this.mSource, SOURCE_AUTOFILL) && !java.util.Objects.equals(this.mSource, SOURCE_PLATFORM)) {
            throw new java.lang.IllegalArgumentException("source was " + this.mSource + " but must be one of: SOURCE_AUTOFILL(" + SOURCE_AUTOFILL + "), SOURCE_PLATFORM(" + SOURCE_PLATFORM + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSource);
        this.mAutofillHints = createStringArray;
        this.mType = readString2;
        if (!java.util.Objects.equals(this.mType, TYPE_SUGGESTION) && !java.util.Objects.equals(this.mType, TYPE_ACTION)) {
            throw new java.lang.IllegalArgumentException("type was " + this.mType + " but must be one of: TYPE_SUGGESTION(" + TYPE_SUGGESTION + "), TYPE_ACTION(" + TYPE_ACTION + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mPinned = z;
        this.mTooltip = inlineSuggestion;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
