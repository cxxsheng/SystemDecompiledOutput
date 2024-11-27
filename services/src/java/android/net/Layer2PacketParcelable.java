package android.net;

/* loaded from: classes.dex */
public class Layer2PacketParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.Layer2PacketParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.Layer2PacketParcelable>() { // from class: android.net.Layer2PacketParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.Layer2PacketParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.Layer2PacketParcelable layer2PacketParcelable = new android.net.Layer2PacketParcelable();
            layer2PacketParcelable.readFromParcel(parcel);
            return layer2PacketParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.Layer2PacketParcelable[] newArray(int i) {
            return new android.net.Layer2PacketParcelable[i];
        }
    };
    public android.net.MacAddress dstMacAddress;
    public byte[] payload;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.dstMacAddress, i);
        parcel.writeByteArray(this.payload);
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
            this.dstMacAddress = (android.net.MacAddress) parcel.readTypedObject(android.net.MacAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.payload = parcel.createByteArray();
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
        stringJoiner.add("dstMacAddress: " + java.util.Objects.toString(this.dstMacAddress));
        stringJoiner.add("payload: " + java.util.Arrays.toString(this.payload));
        return "Layer2PacketParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.dstMacAddress) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
