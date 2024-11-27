package android.hardware.radio.config;

/* loaded from: classes2.dex */
public class PhoneCapability implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.config.PhoneCapability> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.config.PhoneCapability>() { // from class: android.hardware.radio.config.PhoneCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.config.PhoneCapability createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.config.PhoneCapability phoneCapability = new android.hardware.radio.config.PhoneCapability();
            phoneCapability.readFromParcel(parcel);
            return phoneCapability;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.config.PhoneCapability[] newArray(int i) {
            return new android.hardware.radio.config.PhoneCapability[i];
        }
    };
    public static final byte UNKNOWN = -1;
    public byte[] logicalModemIds;
    public byte maxActiveData = 0;
    public byte maxActiveInternetData = 0;
    public boolean isInternetLingeringSupported = false;
    public byte maxActiveVoice = -1;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.maxActiveData);
        parcel.writeByte(this.maxActiveInternetData);
        parcel.writeBoolean(this.isInternetLingeringSupported);
        parcel.writeByteArray(this.logicalModemIds);
        parcel.writeByte(this.maxActiveVoice);
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
            this.maxActiveData = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxActiveInternetData = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isInternetLingeringSupported = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.logicalModemIds = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxActiveVoice = parcel.readByte();
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
        stringJoiner.add("maxActiveData: " + ((int) this.maxActiveData));
        stringJoiner.add("maxActiveInternetData: " + ((int) this.maxActiveInternetData));
        stringJoiner.add("isInternetLingeringSupported: " + this.isInternetLingeringSupported);
        stringJoiner.add("logicalModemIds: " + java.util.Arrays.toString(this.logicalModemIds));
        stringJoiner.add("maxActiveVoice: " + ((int) this.maxActiveVoice));
        return "PhoneCapability" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
