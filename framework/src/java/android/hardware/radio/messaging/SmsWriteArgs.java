package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class SmsWriteArgs implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.SmsWriteArgs> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.SmsWriteArgs>() { // from class: android.hardware.radio.messaging.SmsWriteArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.SmsWriteArgs createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.SmsWriteArgs smsWriteArgs = new android.hardware.radio.messaging.SmsWriteArgs();
            smsWriteArgs.readFromParcel(parcel);
            return smsWriteArgs;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.SmsWriteArgs[] newArray(int i) {
            return new android.hardware.radio.messaging.SmsWriteArgs[i];
        }
    };
    public static final int STATUS_REC_READ = 1;
    public static final int STATUS_REC_UNREAD = 0;
    public static final int STATUS_STO_SENT = 3;
    public static final int STATUS_STO_UNSENT = 2;
    public java.lang.String pdu;
    public java.lang.String smsc;
    public int status = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeString(this.pdu);
        parcel.writeString(this.smsc);
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
            this.status = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pdu = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.smsc = parcel.readString();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("pdu: " + java.util.Objects.toString(this.pdu));
        stringJoiner.add("smsc: " + java.util.Objects.toString(this.smsc));
        return "SmsWriteArgs" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
