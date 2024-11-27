package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class ActivityStatsTechSpecificInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.ActivityStatsTechSpecificInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.ActivityStatsTechSpecificInfo>() { // from class: android.hardware.radio.modem.ActivityStatsTechSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.ActivityStatsTechSpecificInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.ActivityStatsTechSpecificInfo activityStatsTechSpecificInfo = new android.hardware.radio.modem.ActivityStatsTechSpecificInfo();
            activityStatsTechSpecificInfo.readFromParcel(parcel);
            return activityStatsTechSpecificInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.ActivityStatsTechSpecificInfo[] newArray(int i) {
            return new android.hardware.radio.modem.ActivityStatsTechSpecificInfo[i];
        }
    };
    public static final int FREQUENCY_RANGE_HIGH = 3;
    public static final int FREQUENCY_RANGE_LOW = 1;
    public static final int FREQUENCY_RANGE_MID = 2;
    public static final int FREQUENCY_RANGE_MMWAVE = 4;
    public static final int FREQUENCY_RANGE_UNKNOWN = 0;
    public int rat;
    public int[] txmModetimeMs;
    public int frequencyRange = 0;
    public int rxModeTimeMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.frequencyRange);
        parcel.writeIntArray(this.txmModetimeMs);
        parcel.writeInt(this.rxModeTimeMs);
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
            this.rat = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.frequencyRange = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.txmModetimeMs = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.rxModeTimeMs = parcel.readInt();
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
        stringJoiner.add("rat: " + android.hardware.radio.AccessNetwork$$.toString(this.rat));
        stringJoiner.add("frequencyRange: " + this.frequencyRange);
        stringJoiner.add("txmModetimeMs: " + java.util.Arrays.toString(this.txmModetimeMs));
        stringJoiner.add("rxModeTimeMs: " + this.rxModeTimeMs);
        return "ActivityStatsTechSpecificInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
