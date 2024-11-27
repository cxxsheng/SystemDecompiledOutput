package android.service.autofill;

/* loaded from: classes3.dex */
public final class SaveRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.SaveRequest> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.SaveRequest>() { // from class: android.service.autofill.SaveRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.SaveRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.SaveRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.SaveRequest[] newArray(int i) {
            return new android.service.autofill.SaveRequest[i];
        }
    };
    private final android.os.Bundle mClientState;
    private final java.util.ArrayList<java.lang.String> mDatasetIds;
    private final java.util.ArrayList<android.service.autofill.FillContext> mFillContexts;

    public SaveRequest(java.util.ArrayList<android.service.autofill.FillContext> arrayList, android.os.Bundle bundle, java.util.ArrayList<java.lang.String> arrayList2) {
        this.mFillContexts = (java.util.ArrayList) java.util.Objects.requireNonNull(arrayList, "fillContexts");
        this.mClientState = bundle;
        this.mDatasetIds = arrayList2;
    }

    private SaveRequest(android.os.Parcel parcel) {
        this(parcel.createTypedArrayList(android.service.autofill.FillContext.CREATOR), parcel.readBundle(), parcel.createStringArrayList());
    }

    public java.util.List<android.service.autofill.FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public java.util.List<java.lang.String> getDatasetIds() {
        return this.mDatasetIds;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mFillContexts, i);
        parcel.writeBundle(this.mClientState);
        parcel.writeStringList(this.mDatasetIds);
    }
}
