package android.hardware.radio;

/* loaded from: classes2.dex */
public class RadioConst implements android.os.Parcelable {
    public static final int CARD_MAX_APPS = 8;
    public static final android.os.Parcelable.Creator<android.hardware.radio.RadioConst> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioConst>() { // from class: android.hardware.radio.RadioConst.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.RadioConst createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.RadioConst radioConst = new android.hardware.radio.RadioConst();
            radioConst.readFromParcel(parcel);
            return radioConst;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.RadioConst[] newArray(int i) {
            return new android.hardware.radio.RadioConst[i];
        }
    };
    public static final int MAX_RILDS = 3;
    public static final int MAX_UUID_LENGTH = 64;
    public static final int P2_CONSTANT_NO_P2 = -1;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt < 4) {
            try {
                throw new android.os.BadParcelableException("Parcelable too small");
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
        if (dataPosition > Integer.MAX_VALUE - readInt) {
            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
    }

    public java.lang.String toString() {
        return "RadioConst" + new java.util.StringJoiner(", ", "{", "}").toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
