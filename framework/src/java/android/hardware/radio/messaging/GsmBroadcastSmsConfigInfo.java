package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class GsmBroadcastSmsConfigInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo>() { // from class: android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo();
            gsmBroadcastSmsConfigInfo.readFromParcel(parcel);
            return gsmBroadcastSmsConfigInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] newArray(int i) {
            return new android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[i];
        }
    };
    public int fromServiceId = 0;
    public int toServiceId = 0;
    public int fromCodeScheme = 0;
    public int toCodeScheme = 0;
    public boolean selected = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.fromServiceId);
        parcel.writeInt(this.toServiceId);
        parcel.writeInt(this.fromCodeScheme);
        parcel.writeInt(this.toCodeScheme);
        parcel.writeBoolean(this.selected);
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
            this.fromServiceId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.toServiceId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.fromCodeScheme = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.toCodeScheme = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.selected = parcel.readBoolean();
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
        stringJoiner.add("fromServiceId: " + this.fromServiceId);
        stringJoiner.add("toServiceId: " + this.toServiceId);
        stringJoiner.add("fromCodeScheme: " + this.fromCodeScheme);
        stringJoiner.add("toCodeScheme: " + this.toCodeScheme);
        stringJoiner.add("selected: " + this.selected);
        return "GsmBroadcastSmsConfigInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
