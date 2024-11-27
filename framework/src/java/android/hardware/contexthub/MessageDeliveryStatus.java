package android.hardware.contexthub;

/* loaded from: classes.dex */
public class MessageDeliveryStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.contexthub.MessageDeliveryStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.contexthub.MessageDeliveryStatus>() { // from class: android.hardware.contexthub.MessageDeliveryStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.contexthub.MessageDeliveryStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus = new android.hardware.contexthub.MessageDeliveryStatus();
            messageDeliveryStatus.readFromParcel(parcel);
            return messageDeliveryStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.contexthub.MessageDeliveryStatus[] newArray(int i) {
            return new android.hardware.contexthub.MessageDeliveryStatus[i];
        }
    };
    public byte errorCode;
    public int messageSequenceNumber = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.messageSequenceNumber);
        parcel.writeByte(this.errorCode);
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
            this.messageSequenceNumber = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.errorCode = parcel.readByte();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
