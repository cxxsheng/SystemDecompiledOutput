package android.app.blob;

/* loaded from: classes.dex */
public final class LeaseInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.blob.LeaseInfo> CREATOR = new android.os.Parcelable.Creator<android.app.blob.LeaseInfo>() { // from class: android.app.blob.LeaseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.LeaseInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.blob.LeaseInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.LeaseInfo[] newArray(int i) {
            return new android.app.blob.LeaseInfo[i];
        }
    };
    private final java.lang.CharSequence mDescription;
    private final int mDescriptionResId;
    private final long mExpiryTimeMillis;
    private final java.lang.String mPackageName;

    public LeaseInfo(java.lang.String str, long j, int i, java.lang.CharSequence charSequence) {
        this.mPackageName = str;
        this.mExpiryTimeMillis = j;
        this.mDescriptionResId = i;
        this.mDescription = charSequence;
    }

    private LeaseInfo(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mExpiryTimeMillis = parcel.readLong();
        this.mDescriptionResId = parcel.readInt();
        this.mDescription = parcel.readCharSequence();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public long getExpiryTimeMillis() {
        return this.mExpiryTimeMillis;
    }

    public int getDescriptionResId() {
        return this.mDescriptionResId;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mExpiryTimeMillis);
        parcel.writeInt(this.mDescriptionResId);
        parcel.writeCharSequence(this.mDescription);
    }

    public java.lang.String toString() {
        return "LeaseInfo {package: " + this.mPackageName + ",expiryMs: " + this.mExpiryTimeMillis + ",descriptionResId: " + this.mDescriptionResId + ",description: " + ((java.lang.Object) this.mDescription) + ",}";
    }

    private java.lang.String toShortString() {
        return this.mPackageName;
    }

    static java.lang.String toShortString(java.util.List<android.app.blob.LeaseInfo> list) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i).toShortString());
            sb.append(",");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
