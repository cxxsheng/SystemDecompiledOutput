package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class EpsQos implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.EpsQos> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.EpsQos>() { // from class: android.hardware.radio.data.EpsQos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.EpsQos createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.EpsQos epsQos = new android.hardware.radio.data.EpsQos();
            epsQos.readFromParcel(parcel);
            return epsQos;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.EpsQos[] newArray(int i) {
            return new android.hardware.radio.data.EpsQos[i];
        }
    };
    public android.hardware.radio.data.QosBandwidth downlink;
    public int qci = 0;
    public android.hardware.radio.data.QosBandwidth uplink;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.qci);
        parcel.writeTypedObject(this.downlink, i);
        parcel.writeTypedObject(this.uplink, i);
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
            this.qci = parcel.readInt();
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
            } else {
                this.uplink = (android.hardware.radio.data.QosBandwidth) parcel.readTypedObject(android.hardware.radio.data.QosBandwidth.CREATOR);
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
        stringJoiner.add("qci: " + this.qci);
        stringJoiner.add("downlink: " + java.util.Objects.toString(this.downlink));
        stringJoiner.add("uplink: " + java.util.Objects.toString(this.uplink));
        return "EpsQos" + stringJoiner.toString();
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
