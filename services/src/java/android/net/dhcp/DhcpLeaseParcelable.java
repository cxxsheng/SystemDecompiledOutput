package android.net.dhcp;

/* loaded from: classes.dex */
public class DhcpLeaseParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.dhcp.DhcpLeaseParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.dhcp.DhcpLeaseParcelable>() { // from class: android.net.dhcp.DhcpLeaseParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.dhcp.DhcpLeaseParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.dhcp.DhcpLeaseParcelable dhcpLeaseParcelable = new android.net.dhcp.DhcpLeaseParcelable();
            dhcpLeaseParcelable.readFromParcel(parcel);
            return dhcpLeaseParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.dhcp.DhcpLeaseParcelable[] newArray(int i) {
            return new android.net.dhcp.DhcpLeaseParcelable[i];
        }
    };
    public byte[] clientId;
    public java.lang.String hostname;
    public byte[] hwAddr;
    public int netAddr = 0;
    public int prefixLength = 0;
    public long expTime = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.clientId);
        parcel.writeByteArray(this.hwAddr);
        parcel.writeInt(this.netAddr);
        parcel.writeInt(this.prefixLength);
        parcel.writeLong(this.expTime);
        parcel.writeString(this.hostname);
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
            this.clientId = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hwAddr = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.netAddr = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.prefixLength = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.expTime = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.hostname = parcel.readString();
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
        stringJoiner.add("clientId: " + java.util.Arrays.toString(this.clientId));
        stringJoiner.add("hwAddr: " + java.util.Arrays.toString(this.hwAddr));
        stringJoiner.add("netAddr: " + this.netAddr);
        stringJoiner.add("prefixLength: " + this.prefixLength);
        stringJoiner.add("expTime: " + this.expTime);
        stringJoiner.add("hostname: " + java.util.Objects.toString(this.hostname));
        return "DhcpLeaseParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
