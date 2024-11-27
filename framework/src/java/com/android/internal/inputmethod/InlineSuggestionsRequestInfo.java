package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InlineSuggestionsRequestInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.inputmethod.InlineSuggestionsRequestInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.inputmethod.InlineSuggestionsRequestInfo>() { // from class: com.android.internal.inputmethod.InlineSuggestionsRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InlineSuggestionsRequestInfo[] newArray(int i) {
            return new com.android.internal.inputmethod.InlineSuggestionsRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InlineSuggestionsRequestInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.inputmethod.InlineSuggestionsRequestInfo(parcel);
        }
    };
    private final android.view.autofill.AutofillId mAutofillId;
    private final android.content.ComponentName mComponentName;
    private final android.os.Bundle mUiExtras;

    public InlineSuggestionsRequestInfo(android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.os.Bundle bundle) {
        this.mComponentName = componentName;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mComponentName);
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mUiExtras = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUiExtras);
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public android.os.Bundle getUiExtras() {
        return this.mUiExtras;
    }

    public java.lang.String toString() {
        return "InlineSuggestionsRequestInfo { componentName = " + this.mComponentName + ", autofillId = " + this.mAutofillId + ", uiExtras = " + this.mUiExtras + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo = (com.android.internal.inputmethod.InlineSuggestionsRequestInfo) obj;
        if (java.util.Objects.equals(this.mComponentName, inlineSuggestionsRequestInfo.mComponentName) && java.util.Objects.equals(this.mAutofillId, inlineSuggestionsRequestInfo.mAutofillId) && java.util.Objects.equals(this.mUiExtras, inlineSuggestionsRequestInfo.mUiExtras)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mComponentName) + 31) * 31) + java.util.Objects.hashCode(this.mAutofillId)) * 31) + java.util.Objects.hashCode(this.mUiExtras);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mComponentName, i);
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeBundle(this.mUiExtras);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionsRequestInfo(android.os.Parcel parcel) {
        android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
        android.os.Bundle readBundle = parcel.readBundle();
        this.mComponentName = componentName;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mComponentName);
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mUiExtras = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUiExtras);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
