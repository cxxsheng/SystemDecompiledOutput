package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class NrQos implements android.os.Parcelable {
    public static final int AVERAGING_WINDOW_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.NrQos> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.NrQos>() { // from class: android.hardware.radio.data.NrQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.NrQos createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.NrQos nrQos = new android.hardware.radio.data.NrQos();
            nrQos.readFromParcel(parcel);
            return nrQos;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.NrQos[] newArray(int i) {
            return new android.hardware.radio.data.NrQos[i];
        }
    };
    public static final byte FLOW_ID_RANGE_MAX = 63;
    public static final byte FLOW_ID_RANGE_MIN = 1;
    public android.hardware.radio.data.QosBandwidth downlink;
    public android.hardware.radio.data.QosBandwidth uplink;
    public int fiveQi = 0;
    public byte qfi = 0;

    @java.lang.Deprecated
    public char averagingWindowMs = 0;
    public int averagingWindowMillis = -1;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.fiveQi);
        parcel.writeTypedObject(this.downlink, i);
        parcel.writeTypedObject(this.uplink, i);
        parcel.writeByte(this.qfi);
        parcel.writeInt(this.averagingWindowMs);
        parcel.writeInt(this.averagingWindowMillis);
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
            this.fiveQi = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.downlink = (android.hardware.radio.data.QosBandwidth) parcel.readTypedObject(android.hardware.radio.data.QosBandwidth.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uplink = (android.hardware.radio.data.QosBandwidth) parcel.readTypedObject(android.hardware.radio.data.QosBandwidth.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.qfi = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.averagingWindowMs = (char) parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.averagingWindowMillis = parcel.readInt();
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
        stringJoiner.add("fiveQi: " + this.fiveQi);
        stringJoiner.add("downlink: " + java.util.Objects.toString(this.downlink));
        stringJoiner.add("uplink: " + java.util.Objects.toString(this.uplink));
        stringJoiner.add("qfi: " + ((int) this.qfi));
        stringJoiner.add("averagingWindowMs: " + this.averagingWindowMs);
        stringJoiner.add("averagingWindowMillis: " + this.averagingWindowMillis);
        return "NrQos" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.downlink) | 0 | describeContents(this.uplink);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
