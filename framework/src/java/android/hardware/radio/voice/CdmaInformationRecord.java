package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaInformationRecord implements android.os.Parcelable {
    public static final int CDMA_MAX_NUMBER_OF_INFO_RECS = 10;
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaInformationRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaInformationRecord>() { // from class: android.hardware.radio.voice.CdmaInformationRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaInformationRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaInformationRecord cdmaInformationRecord = new android.hardware.radio.voice.CdmaInformationRecord();
            cdmaInformationRecord.readFromParcel(parcel);
            return cdmaInformationRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaInformationRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaInformationRecord[i];
        }
    };
    public static final int NAME_CALLED_PARTY_NUMBER = 1;
    public static final int NAME_CALLING_PARTY_NUMBER = 2;
    public static final int NAME_CONNECTED_NUMBER = 3;
    public static final int NAME_DISPLAY = 0;
    public static final int NAME_EXTENDED_DISPLAY = 7;
    public static final int NAME_LINE_CONTROL = 6;
    public static final int NAME_REDIRECTING_NUMBER = 5;
    public static final int NAME_SIGNAL = 4;
    public static final int NAME_T53_AUDIO_CONTROL = 10;
    public static final int NAME_T53_CLIR = 8;
    public static final int NAME_T53_RELEASE = 9;
    public android.hardware.radio.voice.CdmaT53AudioControlInfoRecord[] audioCtrl;
    public android.hardware.radio.voice.CdmaT53ClirInfoRecord[] clir;
    public android.hardware.radio.voice.CdmaDisplayInfoRecord[] display;
    public android.hardware.radio.voice.CdmaLineControlInfoRecord[] lineCtrl;
    public int name = 0;
    public android.hardware.radio.voice.CdmaNumberInfoRecord[] number;
    public android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord[] redir;
    public android.hardware.radio.voice.CdmaSignalInfoRecord[] signal;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.name);
        parcel.writeTypedArray(this.display, i);
        parcel.writeTypedArray(this.number, i);
        parcel.writeTypedArray(this.signal, i);
        parcel.writeTypedArray(this.redir, i);
        parcel.writeTypedArray(this.lineCtrl, i);
        parcel.writeTypedArray(this.clir, i);
        parcel.writeTypedArray(this.audioCtrl, i);
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
            this.name = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.display = (android.hardware.radio.voice.CdmaDisplayInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaDisplayInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.number = (android.hardware.radio.voice.CdmaNumberInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaNumberInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signal = (android.hardware.radio.voice.CdmaSignalInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaSignalInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.redir = (android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.lineCtrl = (android.hardware.radio.voice.CdmaLineControlInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaLineControlInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.clir = (android.hardware.radio.voice.CdmaT53ClirInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaT53ClirInfoRecord.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.audioCtrl = (android.hardware.radio.voice.CdmaT53AudioControlInfoRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaT53AudioControlInfoRecord.CREATOR);
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
        stringJoiner.add("name: " + this.name);
        stringJoiner.add("display: " + java.util.Arrays.toString(this.display));
        stringJoiner.add("number: " + java.util.Arrays.toString(this.number));
        stringJoiner.add("signal: " + java.util.Arrays.toString(this.signal));
        stringJoiner.add("redir: " + java.util.Arrays.toString(this.redir));
        stringJoiner.add("lineCtrl: " + java.util.Arrays.toString(this.lineCtrl));
        stringJoiner.add("clir: " + java.util.Arrays.toString(this.clir));
        stringJoiner.add("audioCtrl: " + java.util.Arrays.toString(this.audioCtrl));
        return "CdmaInformationRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.display) | 0 | describeContents(this.number) | describeContents(this.signal) | describeContents(this.redir) | describeContents(this.lineCtrl) | describeContents(this.clir) | describeContents(this.audioCtrl);
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
