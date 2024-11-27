package android.security.keystore;

/* loaded from: classes.dex */
public class KeyAttestationApplicationId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keystore.KeyAttestationApplicationId> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.KeyAttestationApplicationId>() { // from class: android.security.keystore.KeyAttestationApplicationId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.KeyAttestationApplicationId createFromParcel(android.os.Parcel parcel) {
            android.security.keystore.KeyAttestationApplicationId keyAttestationApplicationId = new android.security.keystore.KeyAttestationApplicationId();
            keyAttestationApplicationId.readFromParcel(parcel);
            return keyAttestationApplicationId;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.KeyAttestationApplicationId[] newArray(int i) {
            return new android.security.keystore.KeyAttestationApplicationId[i];
        }
    };
    public android.security.keystore.KeyAttestationPackageInfo[] packageInfos;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.packageInfos, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.packageInfos = (android.security.keystore.KeyAttestationPackageInfo[]) parcel.createTypedArray(android.security.keystore.KeyAttestationPackageInfo.CREATOR);
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.packageInfos) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
