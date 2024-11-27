package android.os;

/* loaded from: classes3.dex */
public final class ParcelUuid implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ParcelUuid> CREATOR = new android.os.Parcelable.Creator<android.os.ParcelUuid>() { // from class: android.os.ParcelUuid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelUuid createFromParcel(android.os.Parcel parcel) {
            return new android.os.ParcelUuid(new java.util.UUID(parcel.readLong(), parcel.readLong()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelUuid[] newArray(int i) {
            return new android.os.ParcelUuid[i];
        }
    };
    private final java.util.UUID mUuid;

    public ParcelUuid(java.util.UUID uuid) {
        this.mUuid = uuid;
    }

    public static android.os.ParcelUuid fromString(java.lang.String str) {
        return new android.os.ParcelUuid(java.util.UUID.fromString(str));
    }

    public java.util.UUID getUuid() {
        return this.mUuid;
    }

    public java.lang.String toString() {
        return this.mUuid.toString();
    }

    public int hashCode() {
        return this.mUuid.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.os.ParcelUuid)) {
            return false;
        }
        return this.mUuid.equals(((android.os.ParcelUuid) obj).mUuid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mUuid.getMostSignificantBits());
        parcel.writeLong(this.mUuid.getLeastSignificantBits());
    }
}
