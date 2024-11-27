package android.service.assist.classification;

/* loaded from: classes3.dex */
public final class FieldClassification implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.assist.classification.FieldClassification> CREATOR = new android.os.Parcelable.Creator<android.service.assist.classification.FieldClassification>() { // from class: android.service.assist.classification.FieldClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassification[] newArray(int i) {
            return new android.service.assist.classification.FieldClassification[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassification createFromParcel(android.os.Parcel parcel) {
            return new android.service.assist.classification.FieldClassification(parcel);
        }
    };
    private final android.view.autofill.AutofillId mAutofillId;
    private final java.util.Set<java.lang.String> mGroupHints;
    private final java.util.Set<java.lang.String> mHints;

    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public java.util.Set<java.lang.String> getHints() {
        return this.mHints;
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getGroupHints() {
        return this.mGroupHints;
    }

    static java.util.Set<java.lang.String> unparcelHints(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        return new android.util.ArraySet(arrayList);
    }

    void parcelHints(android.os.Parcel parcel, int i) {
        parcel.writeStringList(new java.util.ArrayList(this.mHints));
    }

    static java.util.Set<java.lang.String> unparcelGroupHints(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        return new android.util.ArraySet(arrayList);
    }

    void parcelGroupHints(android.os.Parcel parcel, int i) {
        parcel.writeStringList(new java.util.ArrayList(this.mGroupHints));
    }

    public FieldClassification(android.view.autofill.AutofillId autofillId, java.util.Set<java.lang.String> set) {
        this(autofillId, set, new android.util.ArraySet());
    }

    @android.annotation.SystemApi
    public FieldClassification(android.view.autofill.AutofillId autofillId, java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2) {
        this.mAutofillId = autofillId;
        this.mHints = set;
        this.mGroupHints = set2;
    }

    public java.lang.String toString() {
        return "FieldClassification { autofillId = " + this.mAutofillId + ", hints = " + this.mHints + ", groupHints = " + this.mGroupHints + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcelHints(parcel, i);
        parcelGroupHints(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FieldClassification(android.os.Parcel parcel) {
        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
        java.util.Set<java.lang.String> unparcelHints = unparcelHints(parcel);
        java.util.Set<java.lang.String> unparcelGroupHints = unparcelGroupHints(parcel);
        this.mAutofillId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAutofillId);
        this.mHints = unparcelHints;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHints);
        this.mGroupHints = unparcelGroupHints;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mGroupHints);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
