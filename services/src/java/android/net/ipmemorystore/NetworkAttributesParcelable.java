package android.net.ipmemorystore;

/* loaded from: classes.dex */
public class NetworkAttributesParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.ipmemorystore.NetworkAttributesParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.ipmemorystore.NetworkAttributesParcelable>() { // from class: android.net.ipmemorystore.NetworkAttributesParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ipmemorystore.NetworkAttributesParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable = new android.net.ipmemorystore.NetworkAttributesParcelable();
            networkAttributesParcelable.readFromParcel(parcel);
            return networkAttributesParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ipmemorystore.NetworkAttributesParcelable[] newArray(int i) {
            return new android.net.ipmemorystore.NetworkAttributesParcelable[i];
        }
    };
    public byte[] assignedV4Address;
    public java.lang.String cluster;
    public android.net.ipmemorystore.Blob[] dnsAddresses;
    public android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable ipv6ProvisioningLossQuirk;
    public long assignedV4AddressExpiry = 0;
    public int mtu = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.assignedV4Address);
        parcel.writeLong(this.assignedV4AddressExpiry);
        parcel.writeString(this.cluster);
        parcel.writeTypedArray(this.dnsAddresses, i);
        parcel.writeInt(this.mtu);
        parcel.writeTypedObject(this.ipv6ProvisioningLossQuirk, i);
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
            this.assignedV4Address = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.assignedV4AddressExpiry = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cluster = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dnsAddresses = (android.net.ipmemorystore.Blob[]) parcel.createTypedArray(android.net.ipmemorystore.Blob.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mtu = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.ipv6ProvisioningLossQuirk = (android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable) parcel.readTypedObject(android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable.CREATOR);
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
        stringJoiner.add("assignedV4Address: " + java.util.Arrays.toString(this.assignedV4Address));
        stringJoiner.add("assignedV4AddressExpiry: " + this.assignedV4AddressExpiry);
        stringJoiner.add("cluster: " + java.util.Objects.toString(this.cluster));
        stringJoiner.add("dnsAddresses: " + java.util.Arrays.toString(this.dnsAddresses));
        stringJoiner.add("mtu: " + this.mtu);
        stringJoiner.add("ipv6ProvisioningLossQuirk: " + java.util.Objects.toString(this.ipv6ProvisioningLossQuirk));
        return "NetworkAttributesParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.dnsAddresses) | 0 | describeContents(this.ipv6ProvisioningLossQuirk);
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
