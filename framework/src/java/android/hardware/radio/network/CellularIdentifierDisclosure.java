package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellularIdentifierDisclosure implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellularIdentifierDisclosure> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellularIdentifierDisclosure>() { // from class: android.hardware.radio.network.CellularIdentifierDisclosure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellularIdentifierDisclosure createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellularIdentifierDisclosure cellularIdentifierDisclosure = new android.hardware.radio.network.CellularIdentifierDisclosure();
            cellularIdentifierDisclosure.readFromParcel(parcel);
            return cellularIdentifierDisclosure;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellularIdentifierDisclosure[] newArray(int i) {
            return new android.hardware.radio.network.CellularIdentifierDisclosure[i];
        }
    };
    public int identifier;
    public boolean isEmergency = false;
    public java.lang.String plmn;
    public int protocolMessage;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.plmn);
        parcel.writeInt(this.identifier);
        parcel.writeInt(this.protocolMessage);
        parcel.writeBoolean(this.isEmergency);
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
            this.plmn = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.identifier = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.protocolMessage = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.isEmergency = parcel.readBoolean();
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
        stringJoiner.add("plmn: " + java.util.Objects.toString(this.plmn));
        stringJoiner.add("identifier: " + android.hardware.radio.network.CellularIdentifier$$.toString(this.identifier));
        stringJoiner.add("protocolMessage: " + android.hardware.radio.network.NasProtocolMessage$$.toString(this.protocolMessage));
        stringJoiner.add("isEmergency: " + this.isEmergency);
        return "CellularIdentifierDisclosure" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
