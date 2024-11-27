package android.net.networkstack.aidl.quirks;

/* loaded from: classes.dex */
public class IPv6ProvisioningLossQuirkParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable>() { // from class: android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable iPv6ProvisioningLossQuirkParcelable = new android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable();
            iPv6ProvisioningLossQuirkParcelable.readFromParcel(parcel);
            return iPv6ProvisioningLossQuirkParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable[] newArray(int i) {
            return new android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable[i];
        }
    };
    public int detectionCount = 0;
    public long quirkExpiry = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.detectionCount);
        parcel.writeLong(this.quirkExpiry);
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
                return;
            }
            this.detectionCount = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.quirkExpiry = parcel.readLong();
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("detectionCount: " + this.detectionCount);
        stringJoiner.add("quirkExpiry: " + this.quirkExpiry);
        return "IPv6ProvisioningLossQuirkParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
