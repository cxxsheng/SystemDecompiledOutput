package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class SecurityAlgorithmUpdate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.SecurityAlgorithmUpdate> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.SecurityAlgorithmUpdate>() { // from class: android.hardware.radio.network.SecurityAlgorithmUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.SecurityAlgorithmUpdate createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.SecurityAlgorithmUpdate securityAlgorithmUpdate = new android.hardware.radio.network.SecurityAlgorithmUpdate();
            securityAlgorithmUpdate.readFromParcel(parcel);
            return securityAlgorithmUpdate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.SecurityAlgorithmUpdate[] newArray(int i) {
            return new android.hardware.radio.network.SecurityAlgorithmUpdate[i];
        }
    };
    public int connectionEvent;
    public int encryption;
    public int integrity;
    public boolean isUnprotectedEmergency = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.connectionEvent);
        parcel.writeInt(this.encryption);
        parcel.writeInt(this.integrity);
        parcel.writeBoolean(this.isUnprotectedEmergency);
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
            this.connectionEvent = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.encryption = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.integrity = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.isUnprotectedEmergency = parcel.readBoolean();
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
        stringJoiner.add("connectionEvent: " + android.hardware.radio.network.ConnectionEvent$$.toString(this.connectionEvent));
        stringJoiner.add("encryption: " + android.hardware.radio.network.SecurityAlgorithm$$.toString(this.encryption));
        stringJoiner.add("integrity: " + android.hardware.radio.network.SecurityAlgorithm$$.toString(this.integrity));
        stringJoiner.add("isUnprotectedEmergency: " + this.isUnprotectedEmergency);
        return "SecurityAlgorithmUpdate" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
