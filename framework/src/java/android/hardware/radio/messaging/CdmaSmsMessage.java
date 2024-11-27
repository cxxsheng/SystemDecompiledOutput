package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class CdmaSmsMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaSmsMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaSmsMessage>() { // from class: android.hardware.radio.messaging.CdmaSmsMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaSmsMessage createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage = new android.hardware.radio.messaging.CdmaSmsMessage();
            cdmaSmsMessage.readFromParcel(parcel);
            return cdmaSmsMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaSmsMessage[] newArray(int i) {
            return new android.hardware.radio.messaging.CdmaSmsMessage[i];
        }
    };
    public android.hardware.radio.messaging.CdmaSmsAddress address;
    public byte[] bearerData;
    public android.hardware.radio.messaging.CdmaSmsSubaddress subAddress;
    public int teleserviceId = 0;
    public boolean isServicePresent = false;
    public int serviceCategory = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.teleserviceId);
        parcel.writeBoolean(this.isServicePresent);
        parcel.writeInt(this.serviceCategory);
        parcel.writeTypedObject(this.address, i);
        parcel.writeTypedObject(this.subAddress, i);
        parcel.writeByteArray(this.bearerData);
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
            this.teleserviceId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isServicePresent = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serviceCategory = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.address = (android.hardware.radio.messaging.CdmaSmsAddress) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.subAddress = (android.hardware.radio.messaging.CdmaSmsSubaddress) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsSubaddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.bearerData = parcel.createByteArray();
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
        stringJoiner.add("teleserviceId: " + this.teleserviceId);
        stringJoiner.add("isServicePresent: " + this.isServicePresent);
        stringJoiner.add("serviceCategory: " + this.serviceCategory);
        stringJoiner.add("address: " + java.util.Objects.toString(this.address));
        stringJoiner.add("subAddress: " + java.util.Objects.toString(this.subAddress));
        stringJoiner.add("bearerData: " + java.util.Arrays.toString(this.bearerData));
        return "CdmaSmsMessage" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.address) | 0 | describeContents(this.subAddress);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
