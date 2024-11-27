package android.hardware.security.secureclock;

/* loaded from: classes2.dex */
public class TimeStampToken implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.secureclock.TimeStampToken> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.secureclock.TimeStampToken>() { // from class: android.hardware.security.secureclock.TimeStampToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.secureclock.TimeStampToken createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.secureclock.TimeStampToken timeStampToken = new android.hardware.security.secureclock.TimeStampToken();
            timeStampToken.readFromParcel(parcel);
            return timeStampToken;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.secureclock.TimeStampToken[] newArray(int i) {
            return new android.hardware.security.secureclock.TimeStampToken[i];
        }
    };
    public long challenge = 0;
    public byte[] mac;
    public android.hardware.security.secureclock.Timestamp timestamp;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.challenge);
        parcel.writeTypedObject(this.timestamp, i);
        parcel.writeByteArray(this.mac);
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
            this.challenge = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timestamp = (android.hardware.security.secureclock.Timestamp) parcel.readTypedObject(android.hardware.security.secureclock.Timestamp.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.mac = parcel.createByteArray();
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
        return describeContents(this.timestamp) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}