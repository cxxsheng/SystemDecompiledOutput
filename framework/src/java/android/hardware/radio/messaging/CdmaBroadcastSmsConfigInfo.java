package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class CdmaBroadcastSmsConfigInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo>() { // from class: android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = new android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo();
            cdmaBroadcastSmsConfigInfo.readFromParcel(parcel);
            return cdmaBroadcastSmsConfigInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] newArray(int i) {
            return new android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[i];
        }
    };
    public int serviceCategory = 0;
    public int language = 0;
    public boolean selected = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serviceCategory);
        parcel.writeInt(this.language);
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
            this.serviceCategory = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.language = parcel.readInt();
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
        stringJoiner.add("serviceCategory: " + this.serviceCategory);
        stringJoiner.add("language: " + this.language);
        stringJoiner.add("selected: " + this.selected);
        return "CdmaBroadcastSmsConfigInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
