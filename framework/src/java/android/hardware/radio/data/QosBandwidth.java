package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class QosBandwidth implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.QosBandwidth> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.QosBandwidth>() { // from class: android.hardware.radio.data.QosBandwidth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosBandwidth createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.QosBandwidth qosBandwidth = new android.hardware.radio.data.QosBandwidth();
            qosBandwidth.readFromParcel(parcel);
            return qosBandwidth;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosBandwidth[] newArray(int i) {
            return new android.hardware.radio.data.QosBandwidth[i];
        }
    };
    public int maxBitrateKbps = 0;
    public int guaranteedBitrateKbps = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.maxBitrateKbps);
        parcel.writeInt(this.guaranteedBitrateKbps);
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
            this.maxBitrateKbps = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.guaranteedBitrateKbps = parcel.readInt();
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
        stringJoiner.add("maxBitrateKbps: " + this.maxBitrateKbps);
        stringJoiner.add("guaranteedBitrateKbps: " + this.guaranteedBitrateKbps);
        return "QosBandwidth" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
