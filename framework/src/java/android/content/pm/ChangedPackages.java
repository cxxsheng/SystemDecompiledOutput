package android.content.pm;

/* loaded from: classes.dex */
public final class ChangedPackages implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ChangedPackages> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ChangedPackages>() { // from class: android.content.pm.ChangedPackages.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ChangedPackages createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ChangedPackages(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ChangedPackages[] newArray(int i) {
            return new android.content.pm.ChangedPackages[i];
        }
    };
    private final java.util.List<java.lang.String> mPackageNames;
    private final int mSequenceNumber;

    public ChangedPackages(int i, java.util.List<java.lang.String> list) {
        this.mSequenceNumber = i;
        this.mPackageNames = list;
    }

    protected ChangedPackages(android.os.Parcel parcel) {
        this.mSequenceNumber = parcel.readInt();
        this.mPackageNames = parcel.createStringArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSequenceNumber);
        parcel.writeStringList(this.mPackageNames);
    }

    public int getSequenceNumber() {
        return this.mSequenceNumber;
    }

    public java.util.List<java.lang.String> getPackageNames() {
        return this.mPackageNames;
    }
}
