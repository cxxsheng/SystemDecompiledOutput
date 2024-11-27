package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaCallWaiting implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaCallWaiting> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaCallWaiting>() { // from class: android.hardware.radio.voice.CdmaCallWaiting.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaCallWaiting createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaCallWaiting cdmaCallWaiting = new android.hardware.radio.voice.CdmaCallWaiting();
            cdmaCallWaiting.readFromParcel(parcel);
            return cdmaCallWaiting;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaCallWaiting[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaCallWaiting[i];
        }
    };
    public static final int NUMBER_PLAN_DATA = 3;
    public static final int NUMBER_PLAN_ISDN = 1;
    public static final int NUMBER_PLAN_NATIONAL = 8;
    public static final int NUMBER_PLAN_PRIVATE = 9;
    public static final int NUMBER_PLAN_TELEX = 4;
    public static final int NUMBER_PLAN_UNKNOWN = 0;
    public static final int NUMBER_PRESENTATION_ALLOWED = 0;
    public static final int NUMBER_PRESENTATION_RESTRICTED = 1;
    public static final int NUMBER_PRESENTATION_UNKNOWN = 2;
    public static final int NUMBER_TYPE_INTERNATIONAL = 1;
    public static final int NUMBER_TYPE_NATIONAL = 2;
    public static final int NUMBER_TYPE_NETWORK_SPECIFIC = 3;
    public static final int NUMBER_TYPE_SUBSCRIBER = 4;
    public static final int NUMBER_TYPE_UNKNOWN = 0;
    public java.lang.String name;
    public java.lang.String number;
    public android.hardware.radio.voice.CdmaSignalInfoRecord signalInfoRecord;
    public int numberPresentation = 0;
    public int numberType = 0;
    public int numberPlan = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.number);
        parcel.writeInt(this.numberPresentation);
        parcel.writeString(this.name);
        parcel.writeTypedObject(this.signalInfoRecord, i);
        parcel.writeInt(this.numberType);
        parcel.writeInt(this.numberPlan);
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
            this.number = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.numberPresentation = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.name = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signalInfoRecord = (android.hardware.radio.voice.CdmaSignalInfoRecord) parcel.readTypedObject(android.hardware.radio.voice.CdmaSignalInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.numberType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.numberPlan = parcel.readInt();
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
        stringJoiner.add("number: " + java.util.Objects.toString(this.number));
        stringJoiner.add("numberPresentation: " + this.numberPresentation);
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("signalInfoRecord: " + java.util.Objects.toString(this.signalInfoRecord));
        stringJoiner.add("numberType: " + this.numberType);
        stringJoiner.add("numberPlan: " + this.numberPlan);
        return "CdmaCallWaiting" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalInfoRecord) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
