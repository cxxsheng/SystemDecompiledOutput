package android.os;

/* loaded from: classes3.dex */
public final class CarrierAssociatedAppEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.CarrierAssociatedAppEntry> CREATOR = new android.os.Parcelable.Creator<android.os.CarrierAssociatedAppEntry>() { // from class: android.os.CarrierAssociatedAppEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CarrierAssociatedAppEntry createFromParcel(android.os.Parcel parcel) {
            return new android.os.CarrierAssociatedAppEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CarrierAssociatedAppEntry[] newArray(int i) {
            return new android.os.CarrierAssociatedAppEntry[i];
        }
    };
    public static final int SDK_UNSPECIFIED = -1;
    public final int addedInSdk;
    public final java.lang.String packageName;

    public CarrierAssociatedAppEntry(java.lang.String str, int i) {
        this.packageName = str;
        this.addedInSdk = i;
    }

    public CarrierAssociatedAppEntry(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.addedInSdk = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeInt(this.addedInSdk);
    }
}
