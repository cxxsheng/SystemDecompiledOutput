package android.hardware.input;

/* loaded from: classes2.dex */
public class IKeyboardBacklightState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.IKeyboardBacklightState> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.IKeyboardBacklightState>() { // from class: android.hardware.input.IKeyboardBacklightState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.IKeyboardBacklightState createFromParcel(android.os.Parcel parcel) {
            android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState = new android.hardware.input.IKeyboardBacklightState();
            iKeyboardBacklightState.readFromParcel(parcel);
            return iKeyboardBacklightState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.IKeyboardBacklightState[] newArray(int i) {
            return new android.hardware.input.IKeyboardBacklightState[i];
        }
    };
    public int brightnessLevel = 0;
    public int maxBrightnessLevel = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.brightnessLevel);
        parcel.writeInt(this.maxBrightnessLevel);
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
            this.brightnessLevel = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxBrightnessLevel = parcel.readInt();
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

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.input.IKeyboardBacklightState)) {
            return false;
        }
        android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState = (android.hardware.input.IKeyboardBacklightState) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.brightnessLevel), java.lang.Integer.valueOf(iKeyboardBacklightState.brightnessLevel)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxBrightnessLevel), java.lang.Integer.valueOf(iKeyboardBacklightState.maxBrightnessLevel))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.brightnessLevel), java.lang.Integer.valueOf(this.maxBrightnessLevel)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
