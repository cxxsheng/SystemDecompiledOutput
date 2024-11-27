package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaRedirectingNumberInfoRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord>() { // from class: android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord cdmaRedirectingNumberInfoRecord = new android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord();
            cdmaRedirectingNumberInfoRecord.readFromParcel(parcel);
            return cdmaRedirectingNumberInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord[i];
        }
    };
    public static final int REDIRECTING_REASON_CALLED_DTE_OUT_OF_ORDER = 9;
    public static final int REDIRECTING_REASON_CALL_FORWARDING_BUSY = 1;
    public static final int REDIRECTING_REASON_CALL_FORWARDING_BY_THE_CALLED_DTE = 10;
    public static final int REDIRECTING_REASON_CALL_FORWARDING_NO_REPLY = 2;
    public static final int REDIRECTING_REASON_CALL_FORWARDING_UNCONDITIONAL = 15;
    public static final int REDIRECTING_REASON_RESERVED = 16;
    public static final int REDIRECTING_REASON_UNKNOWN = 0;
    public android.hardware.radio.voice.CdmaNumberInfoRecord redirectingNumber;
    public int redirectingReason = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.redirectingNumber, i);
        parcel.writeInt(this.redirectingReason);
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
            this.redirectingNumber = (android.hardware.radio.voice.CdmaNumberInfoRecord) parcel.readTypedObject(android.hardware.radio.voice.CdmaNumberInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.redirectingReason = parcel.readInt();
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
        stringJoiner.add("redirectingNumber: " + java.util.Objects.toString(this.redirectingNumber));
        stringJoiner.add("redirectingReason: " + this.redirectingReason);
        return "CdmaRedirectingNumberInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.redirectingNumber) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
