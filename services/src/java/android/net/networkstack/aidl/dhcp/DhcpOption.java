package android.net.networkstack.aidl.dhcp;

/* loaded from: classes.dex */
public class DhcpOption implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.networkstack.aidl.dhcp.DhcpOption> CREATOR = new android.os.Parcelable.Creator<android.net.networkstack.aidl.dhcp.DhcpOption>() { // from class: android.net.networkstack.aidl.dhcp.DhcpOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.dhcp.DhcpOption createFromParcel(android.os.Parcel parcel) {
            android.net.networkstack.aidl.dhcp.DhcpOption dhcpOption = new android.net.networkstack.aidl.dhcp.DhcpOption();
            dhcpOption.readFromParcel(parcel);
            return dhcpOption;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.dhcp.DhcpOption[] newArray(int i) {
            return new android.net.networkstack.aidl.dhcp.DhcpOption[i];
        }
    };
    public byte type = 0;
    public byte[] value;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.type);
        parcel.writeByteArray(this.value);
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
            this.type = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.value = parcel.createByteArray();
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
        stringJoiner.add("type: " + ((int) this.type));
        stringJoiner.add("value: " + java.util.Arrays.toString(this.value));
        return "DhcpOption" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
