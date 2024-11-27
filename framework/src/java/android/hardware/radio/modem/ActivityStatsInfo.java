package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class ActivityStatsInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.ActivityStatsInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.ActivityStatsInfo>() { // from class: android.hardware.radio.modem.ActivityStatsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.ActivityStatsInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.ActivityStatsInfo activityStatsInfo = new android.hardware.radio.modem.ActivityStatsInfo();
            activityStatsInfo.readFromParcel(parcel);
            return activityStatsInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.ActivityStatsInfo[] newArray(int i) {
            return new android.hardware.radio.modem.ActivityStatsInfo[i];
        }
    };
    public android.hardware.radio.modem.ActivityStatsTechSpecificInfo[] techSpecificInfo;
    public int sleepModeTimeMs = 0;
    public int idleModeTimeMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sleepModeTimeMs);
        parcel.writeInt(this.idleModeTimeMs);
        parcel.writeTypedArray(this.techSpecificInfo, i);
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
            this.sleepModeTimeMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.idleModeTimeMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.techSpecificInfo = (android.hardware.radio.modem.ActivityStatsTechSpecificInfo[]) parcel.createTypedArray(android.hardware.radio.modem.ActivityStatsTechSpecificInfo.CREATOR);
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
        stringJoiner.add("sleepModeTimeMs: " + this.sleepModeTimeMs);
        stringJoiner.add("idleModeTimeMs: " + this.idleModeTimeMs);
        stringJoiner.add("techSpecificInfo: " + java.util.Arrays.toString(this.techSpecificInfo));
        return "ActivityStatsInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.techSpecificInfo) | 0;
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
