package android.view.translation;

/* loaded from: classes4.dex */
public final class ViewTranslationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.ViewTranslationRequest> CREATOR = new android.os.Parcelable.Creator<android.view.translation.ViewTranslationRequest>() { // from class: android.view.translation.ViewTranslationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.ViewTranslationRequest[] newArray(int i) {
            return new android.view.translation.ViewTranslationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.ViewTranslationRequest createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.ViewTranslationRequest(parcel);
        }
    };
    public static final java.lang.String ID_CONTENT_DESCRIPTION = "android:content_description";
    public static final java.lang.String ID_TEXT = "android:text";
    private final android.view.autofill.AutofillId mAutofillId;
    private final java.util.Map<java.lang.String, android.view.translation.TranslationRequestValue> mTranslationRequestValues;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Id {
    }

    public android.view.translation.TranslationRequestValue getValue(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "key should not be null");
        if (!this.mTranslationRequestValues.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("Request does not contain value for key=" + str);
        }
        return this.mTranslationRequestValues.get(str);
    }

    public java.util.Set<java.lang.String> getKeys() {
        return this.mTranslationRequestValues.keySet();
    }

    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Map<java.lang.String, android.view.translation.TranslationRequestValue> defaultTranslationRequestValues() {
        return java.util.Collections.emptyMap();
    }

    public static final class Builder {
        private android.view.autofill.AutofillId mAutofillId;
        private long mBuilderFieldsSet = 0;
        private java.util.Map<java.lang.String, android.view.translation.TranslationRequestValue> mTranslationRequestValues;

        public Builder(android.view.autofill.AutofillId autofillId) {
            this.mAutofillId = autofillId;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        }

        public Builder(android.view.autofill.AutofillId autofillId, long j) {
            this.mAutofillId = new android.view.autofill.AutofillId(autofillId, j, 0);
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        }

        public android.view.translation.ViewTranslationRequest.Builder setValue(java.lang.String str, android.view.translation.TranslationRequestValue translationRequestValue) {
            if (this.mTranslationRequestValues == null) {
                setTranslationRequestValues(new android.util.ArrayMap());
            }
            this.mTranslationRequestValues.put(str, translationRequestValue);
            return this;
        }

        public android.view.translation.ViewTranslationRequest build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mTranslationRequestValues = android.view.translation.ViewTranslationRequest.defaultTranslationRequestValues();
            }
            return new android.view.translation.ViewTranslationRequest(this.mAutofillId, this.mTranslationRequestValues);
        }

        android.view.translation.ViewTranslationRequest.Builder setTranslationRequestValues(java.util.Map<java.lang.String, android.view.translation.TranslationRequestValue> map) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationRequestValues = map;
            return this;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    public ViewTranslationRequest(android.view.autofill.AutofillId autofillId, java.util.Map<java.lang.String, android.view.translation.TranslationRequestValue> map) {
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mTranslationRequestValues = map;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationRequestValues);
    }

    public java.lang.String toString() {
        return "ViewTranslationRequest { autofillId = " + this.mAutofillId + ", translationRequestValues = " + this.mTranslationRequestValues + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.translation.ViewTranslationRequest viewTranslationRequest = (android.view.translation.ViewTranslationRequest) obj;
        if (java.util.Objects.equals(this.mAutofillId, viewTranslationRequest.mAutofillId) && java.util.Objects.equals(this.mTranslationRequestValues, viewTranslationRequest.mTranslationRequestValues)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mAutofillId) + 31) * 31) + java.util.Objects.hashCode(this.mTranslationRequestValues);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeMap(this.mTranslationRequestValues);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ViewTranslationRequest(android.os.Parcel parcel) {
        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        parcel.readMap(linkedHashMap, android.view.translation.TranslationRequestValue.class.getClassLoader());
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mTranslationRequestValues = linkedHashMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationRequestValues);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
