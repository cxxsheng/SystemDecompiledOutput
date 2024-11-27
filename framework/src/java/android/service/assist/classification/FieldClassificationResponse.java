package android.service.assist.classification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FieldClassificationResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.assist.classification.FieldClassificationResponse> CREATOR = new android.os.Parcelable.Creator<android.service.assist.classification.FieldClassificationResponse>() { // from class: android.service.assist.classification.FieldClassificationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassificationResponse[] newArray(int i) {
            return new android.service.assist.classification.FieldClassificationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassificationResponse createFromParcel(android.os.Parcel parcel) {
            return new android.service.assist.classification.FieldClassificationResponse(parcel);
        }
    };
    private final java.util.Set<android.service.assist.classification.FieldClassification> mClassifications;

    static java.util.Set<android.service.assist.classification.FieldClassification> unparcelClassifications(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.service.assist.classification.FieldClassification.class.getClassLoader(), android.service.assist.classification.FieldClassification.class);
        return new android.util.ArraySet(arrayList);
    }

    void parcelClassifications(android.os.Parcel parcel, int i) {
        parcel.writeParcelableList(new java.util.ArrayList(this.mClassifications), i);
    }

    public FieldClassificationResponse(java.util.Set<android.service.assist.classification.FieldClassification> set) {
        this.mClassifications = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mClassifications);
    }

    public java.util.Set<android.service.assist.classification.FieldClassification> getClassifications() {
        return this.mClassifications;
    }

    public java.lang.String toString() {
        return "FieldClassificationResponse { classifications = " + this.mClassifications + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcelClassifications(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FieldClassificationResponse(android.os.Parcel parcel) {
        this.mClassifications = unparcelClassifications(parcel);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mClassifications);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
