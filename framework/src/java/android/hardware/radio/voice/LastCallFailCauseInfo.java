package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class LastCallFailCauseInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.LastCallFailCauseInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.LastCallFailCauseInfo>() { // from class: android.hardware.radio.voice.LastCallFailCauseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.LastCallFailCauseInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.LastCallFailCauseInfo lastCallFailCauseInfo = new android.hardware.radio.voice.LastCallFailCauseInfo();
            lastCallFailCauseInfo.readFromParcel(parcel);
            return lastCallFailCauseInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.LastCallFailCauseInfo[] newArray(int i) {
            return new android.hardware.radio.voice.LastCallFailCauseInfo[i];
        }
    };
    public int causeCode;
    public java.lang.String vendorCause;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.causeCode);
        parcel.writeString(this.vendorCause);
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
            this.causeCode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.vendorCause = parcel.readString();
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
        stringJoiner.add("causeCode: " + android.hardware.radio.voice.LastCallFailCause$$.toString(this.causeCode));
        stringJoiner.add("vendorCause: " + java.util.Objects.toString(this.vendorCause));
        return "LastCallFailCauseInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
