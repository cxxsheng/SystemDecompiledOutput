package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class GsmSignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.GsmSignalStrength> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.GsmSignalStrength>() { // from class: android.hardware.radio.network.GsmSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.GsmSignalStrength createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.GsmSignalStrength gsmSignalStrength = new android.hardware.radio.network.GsmSignalStrength();
            gsmSignalStrength.readFromParcel(parcel);
            return gsmSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.GsmSignalStrength[] newArray(int i) {
            return new android.hardware.radio.network.GsmSignalStrength[i];
        }
    };
    public int signalStrength = 0;
    public int bitErrorRate = 0;
    public int timingAdvance = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.signalStrength);
        parcel.writeInt(this.bitErrorRate);
        parcel.writeInt(this.timingAdvance);
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
            this.signalStrength = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bitErrorRate = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.timingAdvance = parcel.readInt();
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
        stringJoiner.add("signalStrength: " + this.signalStrength);
        stringJoiner.add("bitErrorRate: " + this.bitErrorRate);
        stringJoiner.add("timingAdvance: " + this.timingAdvance);
        return "GsmSignalStrength" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}