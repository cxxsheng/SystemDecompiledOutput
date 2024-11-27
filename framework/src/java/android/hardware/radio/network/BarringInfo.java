package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class BarringInfo implements android.os.Parcelable {
    public static final int BARRING_TYPE_CONDITIONAL = 1;
    public static final int BARRING_TYPE_NONE = 0;
    public static final int BARRING_TYPE_UNCONDITIONAL = 2;
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.BarringInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.BarringInfo>() { // from class: android.hardware.radio.network.BarringInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.BarringInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.BarringInfo barringInfo = new android.hardware.radio.network.BarringInfo();
            barringInfo.readFromParcel(parcel);
            return barringInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.BarringInfo[] newArray(int i) {
            return new android.hardware.radio.network.BarringInfo[i];
        }
    };
    public static final int SERVICE_TYPE_CS_FALLBACK = 5;
    public static final int SERVICE_TYPE_CS_SERVICE = 0;
    public static final int SERVICE_TYPE_CS_VOICE = 2;
    public static final int SERVICE_TYPE_EMERGENCY = 8;
    public static final int SERVICE_TYPE_MMTEL_VIDEO = 7;
    public static final int SERVICE_TYPE_MMTEL_VOICE = 6;
    public static final int SERVICE_TYPE_MO_DATA = 4;
    public static final int SERVICE_TYPE_MO_SIGNALLING = 3;
    public static final int SERVICE_TYPE_OPERATOR_1 = 1001;
    public static final int SERVICE_TYPE_OPERATOR_10 = 1010;
    public static final int SERVICE_TYPE_OPERATOR_11 = 1011;
    public static final int SERVICE_TYPE_OPERATOR_12 = 1012;
    public static final int SERVICE_TYPE_OPERATOR_13 = 1013;
    public static final int SERVICE_TYPE_OPERATOR_14 = 1014;
    public static final int SERVICE_TYPE_OPERATOR_15 = 1015;
    public static final int SERVICE_TYPE_OPERATOR_16 = 1016;
    public static final int SERVICE_TYPE_OPERATOR_17 = 1017;
    public static final int SERVICE_TYPE_OPERATOR_18 = 1018;
    public static final int SERVICE_TYPE_OPERATOR_19 = 1019;
    public static final int SERVICE_TYPE_OPERATOR_2 = 1002;
    public static final int SERVICE_TYPE_OPERATOR_20 = 1020;
    public static final int SERVICE_TYPE_OPERATOR_21 = 1021;
    public static final int SERVICE_TYPE_OPERATOR_22 = 1022;
    public static final int SERVICE_TYPE_OPERATOR_23 = 1023;
    public static final int SERVICE_TYPE_OPERATOR_24 = 1024;
    public static final int SERVICE_TYPE_OPERATOR_25 = 1025;
    public static final int SERVICE_TYPE_OPERATOR_26 = 1026;
    public static final int SERVICE_TYPE_OPERATOR_27 = 1027;
    public static final int SERVICE_TYPE_OPERATOR_28 = 1028;
    public static final int SERVICE_TYPE_OPERATOR_29 = 1029;
    public static final int SERVICE_TYPE_OPERATOR_3 = 1003;
    public static final int SERVICE_TYPE_OPERATOR_30 = 1030;
    public static final int SERVICE_TYPE_OPERATOR_31 = 1031;
    public static final int SERVICE_TYPE_OPERATOR_32 = 1032;
    public static final int SERVICE_TYPE_OPERATOR_4 = 1004;
    public static final int SERVICE_TYPE_OPERATOR_5 = 1005;
    public static final int SERVICE_TYPE_OPERATOR_6 = 1006;
    public static final int SERVICE_TYPE_OPERATOR_7 = 1007;
    public static final int SERVICE_TYPE_OPERATOR_8 = 1008;
    public static final int SERVICE_TYPE_OPERATOR_9 = 1009;
    public static final int SERVICE_TYPE_PS_SERVICE = 1;
    public static final int SERVICE_TYPE_SMS = 9;
    public android.hardware.radio.network.BarringTypeSpecificInfo barringTypeSpecificInfo;
    public int serviceType = 0;
    public int barringType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serviceType);
        parcel.writeInt(this.barringType);
        parcel.writeTypedObject(this.barringTypeSpecificInfo, i);
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
            this.serviceType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.barringType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.barringTypeSpecificInfo = (android.hardware.radio.network.BarringTypeSpecificInfo) parcel.readTypedObject(android.hardware.radio.network.BarringTypeSpecificInfo.CREATOR);
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
        stringJoiner.add("serviceType: " + this.serviceType);
        stringJoiner.add("barringType: " + this.barringType);
        stringJoiner.add("barringTypeSpecificInfo: " + java.util.Objects.toString(this.barringTypeSpecificInfo));
        return "BarringInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.barringTypeSpecificInfo) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
