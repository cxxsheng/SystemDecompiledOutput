package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class Dial implements android.os.Parcelable {
    public static final int CLIR_DEFAULT = 0;
    public static final int CLIR_INVOCATION = 1;
    public static final int CLIR_SUPPRESSION = 2;
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.Dial> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.Dial>() { // from class: android.hardware.radio.voice.Dial.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.Dial createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.Dial dial = new android.hardware.radio.voice.Dial();
            dial.readFromParcel(parcel);
            return dial;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.Dial[] newArray(int i) {
            return new android.hardware.radio.voice.Dial[i];
        }
    };
    public java.lang.String address;
    public int clir = 0;
    public android.hardware.radio.voice.UusInfo[] uusInfo;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.address);
        parcel.writeInt(this.clir);
        parcel.writeTypedArray(this.uusInfo, i);
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
            this.address = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.clir = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.uusInfo = (android.hardware.radio.voice.UusInfo[]) parcel.createTypedArray(android.hardware.radio.voice.UusInfo.CREATOR);
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
        stringJoiner.add("address: " + java.util.Objects.toString(this.address));
        stringJoiner.add("clir: " + this.clir);
        stringJoiner.add("uusInfo: " + java.util.Arrays.toString(this.uusInfo));
        return "Dial" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.uusInfo) | 0;
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
