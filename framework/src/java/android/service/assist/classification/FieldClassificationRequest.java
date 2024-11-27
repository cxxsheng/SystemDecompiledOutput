package android.service.assist.classification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FieldClassificationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.assist.classification.FieldClassificationRequest> CREATOR = new android.os.Parcelable.Creator<android.service.assist.classification.FieldClassificationRequest>() { // from class: android.service.assist.classification.FieldClassificationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassificationRequest[] newArray(int i) {
            return new android.service.assist.classification.FieldClassificationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.assist.classification.FieldClassificationRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.assist.classification.FieldClassificationRequest(parcel);
        }
    };
    private final android.app.assist.AssistStructure mAssistStructure;

    public FieldClassificationRequest(android.app.assist.AssistStructure assistStructure) {
        this.mAssistStructure = assistStructure;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAssistStructure);
    }

    public android.app.assist.AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    public java.lang.String toString() {
        return "FieldClassificationRequest { assistStructure = " + this.mAssistStructure + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAssistStructure, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FieldClassificationRequest(android.os.Parcel parcel) {
        this.mAssistStructure = (android.app.assist.AssistStructure) parcel.readTypedObject(android.app.assist.AssistStructure.CREATOR);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAssistStructure);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
