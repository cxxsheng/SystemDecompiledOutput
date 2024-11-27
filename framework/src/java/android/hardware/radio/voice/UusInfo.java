package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class UusInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.UusInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.UusInfo>() { // from class: android.hardware.radio.voice.UusInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.UusInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.UusInfo uusInfo = new android.hardware.radio.voice.UusInfo();
            uusInfo.readFromParcel(parcel);
            return uusInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.UusInfo[] newArray(int i) {
            return new android.hardware.radio.voice.UusInfo[i];
        }
    };
    public static final int UUS_DCS_IA5C = 4;
    public static final int UUS_DCS_OSIHLP = 1;
    public static final int UUS_DCS_RMCF = 3;
    public static final int UUS_DCS_USP = 0;
    public static final int UUS_DCS_X244 = 2;
    public static final int UUS_TYPE_TYPE1_IMPLICIT = 0;
    public static final int UUS_TYPE_TYPE1_NOT_REQUIRED = 2;
    public static final int UUS_TYPE_TYPE1_REQUIRED = 1;
    public static final int UUS_TYPE_TYPE2_NOT_REQUIRED = 4;
    public static final int UUS_TYPE_TYPE2_REQUIRED = 3;
    public static final int UUS_TYPE_TYPE3_NOT_REQUIRED = 6;
    public static final int UUS_TYPE_TYPE3_REQUIRED = 5;
    public java.lang.String uusData;
    public int uusType = 0;
    public int uusDcs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uusType);
        parcel.writeInt(this.uusDcs);
        parcel.writeString(this.uusData);
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
            this.uusType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uusDcs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.uusData = parcel.readString();
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
        stringJoiner.add("uusType: " + this.uusType);
        stringJoiner.add("uusDcs: " + this.uusDcs);
        stringJoiner.add("uusData: " + java.util.Objects.toString(this.uusData));
        return "UusInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
