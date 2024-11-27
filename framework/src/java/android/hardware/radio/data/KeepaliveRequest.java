package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class KeepaliveRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.KeepaliveRequest> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.KeepaliveRequest>() { // from class: android.hardware.radio.data.KeepaliveRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.KeepaliveRequest createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.KeepaliveRequest keepaliveRequest = new android.hardware.radio.data.KeepaliveRequest();
            keepaliveRequest.readFromParcel(parcel);
            return keepaliveRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.KeepaliveRequest[] newArray(int i) {
            return new android.hardware.radio.data.KeepaliveRequest[i];
        }
    };
    public static final int TYPE_NATT_IPV4 = 0;
    public static final int TYPE_NATT_IPV6 = 1;
    public byte[] destinationAddress;
    public byte[] sourceAddress;
    public int type = 0;
    public int sourcePort = 0;
    public int destinationPort = 0;
    public int maxKeepaliveIntervalMillis = 0;
    public int cid = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeByteArray(this.sourceAddress);
        parcel.writeInt(this.sourcePort);
        parcel.writeByteArray(this.destinationAddress);
        parcel.writeInt(this.destinationPort);
        parcel.writeInt(this.maxKeepaliveIntervalMillis);
        parcel.writeInt(this.cid);
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
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sourceAddress = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sourcePort = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.destinationAddress = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.destinationPort = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxKeepaliveIntervalMillis = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.cid = parcel.readInt();
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("sourceAddress: " + java.util.Arrays.toString(this.sourceAddress));
        stringJoiner.add("sourcePort: " + this.sourcePort);
        stringJoiner.add("destinationAddress: " + java.util.Arrays.toString(this.destinationAddress));
        stringJoiner.add("destinationPort: " + this.destinationPort);
        stringJoiner.add("maxKeepaliveIntervalMillis: " + this.maxKeepaliveIntervalMillis);
        stringJoiner.add("cid: " + this.cid);
        return "KeepaliveRequest" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
