package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class GsmSmsMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.GsmSmsMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.GsmSmsMessage>() { // from class: android.hardware.radio.messaging.GsmSmsMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.GsmSmsMessage createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage = new android.hardware.radio.messaging.GsmSmsMessage();
            gsmSmsMessage.readFromParcel(parcel);
            return gsmSmsMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.GsmSmsMessage[] newArray(int i) {
            return new android.hardware.radio.messaging.GsmSmsMessage[i];
        }
    };
    public java.lang.String pdu;
    public java.lang.String smscPdu;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.smscPdu);
        parcel.writeString(this.pdu);
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
            this.smscPdu = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.pdu = parcel.readString();
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
        stringJoiner.add("smscPdu: " + java.util.Objects.toString(this.smscPdu));
        stringJoiner.add("pdu: " + java.util.Objects.toString(this.pdu));
        return "GsmSmsMessage" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
