package android.view.translation;

/* loaded from: classes4.dex */
public final class ViewTranslationResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.ViewTranslationResponse> CREATOR = new android.os.Parcelable.Creator<android.view.translation.ViewTranslationResponse>() { // from class: android.view.translation.ViewTranslationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.ViewTranslationResponse[] newArray(int i) {
            return new android.view.translation.ViewTranslationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.ViewTranslationResponse createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.ViewTranslationResponse(parcel);
        }
    };
    private final android.view.autofill.AutofillId mAutofillId;
    private final java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> mTranslationResponseValues;

    public android.view.translation.TranslationResponseValue getValue(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (!this.mTranslationResponseValues.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("Request does not contain value for key=" + str);
        }
        return this.mTranslationResponseValues.get(str);
    }

    public java.util.Set<java.lang.String> getKeys() {
        return this.mTranslationResponseValues.keySet();
    }

    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> defaultTranslationResponseValues() {
        return java.util.Collections.emptyMap();
    }

    static abstract class BaseBuilder {
        abstract android.view.translation.ViewTranslationResponse.Builder setTranslationResponseValues(java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> map);

        BaseBuilder() {
        }

        public android.view.translation.ViewTranslationResponse.Builder setValue(java.lang.String str, android.view.translation.TranslationResponseValue translationResponseValue) {
            android.view.translation.ViewTranslationResponse.Builder builder = (android.view.translation.ViewTranslationResponse.Builder) this;
            if (builder.mTranslationResponseValues == null) {
                setTranslationResponseValues(new android.util.ArrayMap());
            }
            builder.mTranslationResponseValues.put(str, translationResponseValue);
            return builder;
        }
    }

    ViewTranslationResponse(android.view.autofill.AutofillId autofillId, java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> map) {
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mTranslationResponseValues = map;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationResponseValues);
    }

    public java.lang.String toString() {
        return "ViewTranslationResponse { autofillId = " + this.mAutofillId + ", translationResponseValues = " + this.mTranslationResponseValues + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.translation.ViewTranslationResponse viewTranslationResponse = (android.view.translation.ViewTranslationResponse) obj;
        if (java.util.Objects.equals(this.mAutofillId, viewTranslationResponse.mAutofillId) && java.util.Objects.equals(this.mTranslationResponseValues, viewTranslationResponse.mTranslationResponseValues)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mAutofillId) + 31) * 31) + java.util.Objects.hashCode(this.mTranslationResponseValues);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeMap(this.mTranslationResponseValues);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ViewTranslationResponse(android.os.Parcel parcel) {
        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        parcel.readMap(linkedHashMap, android.view.translation.TranslationResponseValue.class.getClassLoader());
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mTranslationResponseValues = linkedHashMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationResponseValues);
    }

    public static final class Builder extends android.view.translation.ViewTranslationResponse.BaseBuilder {
        private android.view.autofill.AutofillId mAutofillId;
        private long mBuilderFieldsSet = 0;
        private java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> mTranslationResponseValues;

        @Override // android.view.translation.ViewTranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ android.view.translation.ViewTranslationResponse.Builder setValue(java.lang.String str, android.view.translation.TranslationResponseValue translationResponseValue) {
            return super.setValue(str, translationResponseValue);
        }

        public Builder(android.view.autofill.AutofillId autofillId) {
            this.mAutofillId = autofillId;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        }

        @Override // android.view.translation.ViewTranslationResponse.BaseBuilder
        android.view.translation.ViewTranslationResponse.Builder setTranslationResponseValues(java.util.Map<java.lang.String, android.view.translation.TranslationResponseValue> map) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationResponseValues = map;
            return this;
        }

        public android.view.translation.ViewTranslationResponse build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mTranslationResponseValues = android.view.translation.ViewTranslationResponse.defaultTranslationResponseValues();
            }
            return new android.view.translation.ViewTranslationResponse(this.mAutofillId, this.mTranslationResponseValues);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
