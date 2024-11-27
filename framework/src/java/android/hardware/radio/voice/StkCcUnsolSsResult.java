package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class StkCcUnsolSsResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.StkCcUnsolSsResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.StkCcUnsolSsResult>() { // from class: android.hardware.radio.voice.StkCcUnsolSsResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.StkCcUnsolSsResult createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.StkCcUnsolSsResult stkCcUnsolSsResult = new android.hardware.radio.voice.StkCcUnsolSsResult();
            stkCcUnsolSsResult.readFromParcel(parcel);
            return stkCcUnsolSsResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.StkCcUnsolSsResult[] newArray(int i) {
            return new android.hardware.radio.voice.StkCcUnsolSsResult[i];
        }
    };
    public static final int REQUEST_TYPE_ACTIVATION = 0;
    public static final int REQUEST_TYPE_DEACTIVATION = 1;
    public static final int REQUEST_TYPE_ERASURE = 4;
    public static final int REQUEST_TYPE_INTERROGATION = 2;
    public static final int REQUEST_TYPE_REGISTRATION = 3;
    public static final int SERVICE_TYPE_ALL_BARRING = 16;
    public static final int SERVICE_TYPE_BAIC = 14;
    public static final int SERVICE_TYPE_BAIC_ROAMING = 15;
    public static final int SERVICE_TYPE_BAOC = 11;
    public static final int SERVICE_TYPE_BAOIC = 12;
    public static final int SERVICE_TYPE_BAOIC_EXC_HOME = 13;
    public static final int SERVICE_TYPE_CFU = 0;
    public static final int SERVICE_TYPE_CF_ALL = 4;
    public static final int SERVICE_TYPE_CF_ALL_CONDITIONAL = 5;
    public static final int SERVICE_TYPE_CF_BUSY = 1;
    public static final int SERVICE_TYPE_CF_NOT_REACHABLE = 3;
    public static final int SERVICE_TYPE_CF_NO_REPLY = 2;
    public static final int SERVICE_TYPE_CLIP = 6;
    public static final int SERVICE_TYPE_CLIR = 7;
    public static final int SERVICE_TYPE_COLP = 8;
    public static final int SERVICE_TYPE_COLR = 9;
    public static final int SERVICE_TYPE_INCOMING_BARRING = 18;
    public static final int SERVICE_TYPE_OUTGOING_BARRING = 17;
    public static final int SERVICE_TYPE_WAIT = 10;
    public static final int SUPP_SERVICE_CLASS_DATA = 2;
    public static final int SUPP_SERVICE_CLASS_DATA_ASYNC = 32;
    public static final int SUPP_SERVICE_CLASS_DATA_SYNC = 16;
    public static final int SUPP_SERVICE_CLASS_FAX = 4;
    public static final int SUPP_SERVICE_CLASS_MAX = 128;
    public static final int SUPP_SERVICE_CLASS_NONE = 0;
    public static final int SUPP_SERVICE_CLASS_PACKET = 64;
    public static final int SUPP_SERVICE_CLASS_PAD = 128;
    public static final int SUPP_SERVICE_CLASS_SMS = 8;
    public static final int SUPP_SERVICE_CLASS_VOICE = 1;
    public static final int TELESERVICE_TYPE_ALL_DATA_TELESERVICES = 3;
    public static final int TELESERVICE_TYPE_ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int TELESERVICE_TYPE_ALL_TELESEVICES = 1;
    public static final int TELESERVICE_TYPE_ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int TELESERVICE_TYPE_SMS_SERVICES = 4;
    public static final int TELESERVICE_TYPE_TELEPHONY = 2;
    public android.hardware.radio.voice.CfData[] cfData;
    public int result;
    public android.hardware.radio.voice.SsInfoData[] ssInfo;
    public int serviceType = 0;
    public int requestType = 0;
    public int teleserviceType = 0;
    public int serviceClass = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serviceType);
        parcel.writeInt(this.requestType);
        parcel.writeInt(this.teleserviceType);
        parcel.writeInt(this.serviceClass);
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.ssInfo, i);
        parcel.writeTypedArray(this.cfData, i);
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
            this.requestType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.teleserviceType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serviceClass = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.result = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ssInfo = (android.hardware.radio.voice.SsInfoData[]) parcel.createTypedArray(android.hardware.radio.voice.SsInfoData.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.cfData = (android.hardware.radio.voice.CfData[]) parcel.createTypedArray(android.hardware.radio.voice.CfData.CREATOR);
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
        stringJoiner.add("requestType: " + this.requestType);
        stringJoiner.add("teleserviceType: " + this.teleserviceType);
        stringJoiner.add("serviceClass: " + this.serviceClass);
        stringJoiner.add("result: " + android.hardware.radio.RadioError$$.toString(this.result));
        stringJoiner.add("ssInfo: " + java.util.Arrays.toString(this.ssInfo));
        stringJoiner.add("cfData: " + java.util.Arrays.toString(this.cfData));
        return "StkCcUnsolSsResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ssInfo) | 0 | describeContents(this.cfData);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
