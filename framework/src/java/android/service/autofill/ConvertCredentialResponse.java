package android.service.autofill;

/* loaded from: classes3.dex */
public final class ConvertCredentialResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.ConvertCredentialResponse> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.ConvertCredentialResponse>() { // from class: android.service.autofill.ConvertCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ConvertCredentialResponse[] newArray(int i) {
            return new android.service.autofill.ConvertCredentialResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ConvertCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.ConvertCredentialResponse(parcel);
        }
    };
    private final android.os.Bundle mClientState;
    private final android.service.autofill.Dataset mDataset;

    public ConvertCredentialResponse(android.service.autofill.Dataset dataset, android.os.Bundle bundle) {
        this.mDataset = dataset;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDataset);
        this.mClientState = bundle;
    }

    public android.service.autofill.Dataset getDataset() {
        return this.mDataset;
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public java.lang.String toString() {
        return "ConvertCredentialResponse { dataset = " + this.mDataset + ", clientState = " + this.mClientState + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mClientState != null ? (byte) 2 : (byte) 0);
        parcel.writeTypedObject(this.mDataset, i);
        if (this.mClientState != null) {
            parcel.writeBundle(this.mClientState);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ConvertCredentialResponse(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) parcel.readTypedObject(android.service.autofill.Dataset.CREATOR);
        android.os.Bundle readBundle = (readByte & 2) == 0 ? null : parcel.readBundle();
        this.mDataset = dataset;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDataset);
        this.mClientState = readBundle;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
