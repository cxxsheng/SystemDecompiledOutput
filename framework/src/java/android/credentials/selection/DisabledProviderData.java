package android.credentials.selection;

/* loaded from: classes.dex */
public final class DisabledProviderData extends android.credentials.selection.ProviderData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.DisabledProviderData> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.DisabledProviderData>() { // from class: android.credentials.selection.DisabledProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.DisabledProviderData createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.DisabledProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.DisabledProviderData[] newArray(int i) {
            return new android.credentials.selection.DisabledProviderData[i];
        }
    };

    public DisabledProviderData(java.lang.String str) {
        super(str);
    }

    public android.credentials.selection.DisabledProviderInfo toDisabledProviderInfo() {
        return new android.credentials.selection.DisabledProviderInfo(getProviderFlattenedComponentName());
    }

    private DisabledProviderData(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
