package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class KeepaliveStatus implements android.os.Parcelable {
    public static final int CODE_ACTIVE = 0;
    public static final int CODE_INACTIVE = 1;
    public static final int CODE_PENDING = 2;
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.KeepaliveStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.KeepaliveStatus>() { // from class: android.hardware.radio.data.KeepaliveStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.KeepaliveStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.KeepaliveStatus keepaliveStatus = new android.hardware.radio.data.KeepaliveStatus();
            keepaliveStatus.readFromParcel(parcel);
            return keepaliveStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.KeepaliveStatus[] newArray(int i) {
            return new android.hardware.radio.data.KeepaliveStatus[i];
        }
    };
    public int sessionHandle = 0;
    public int code = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionHandle);
        parcel.writeInt(this.code);
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
            this.sessionHandle = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.code = parcel.readInt();
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
        stringJoiner.add("sessionHandle: " + this.sessionHandle);
        stringJoiner.add("code: " + this.code);
        return "KeepaliveStatus" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
