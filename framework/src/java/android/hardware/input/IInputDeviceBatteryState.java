package android.hardware.input;

/* loaded from: classes2.dex */
public class IInputDeviceBatteryState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.IInputDeviceBatteryState> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.IInputDeviceBatteryState>() { // from class: android.hardware.input.IInputDeviceBatteryState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.IInputDeviceBatteryState createFromParcel(android.os.Parcel parcel) {
            android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState = new android.hardware.input.IInputDeviceBatteryState();
            iInputDeviceBatteryState.readFromParcel(parcel);
            return iInputDeviceBatteryState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.IInputDeviceBatteryState[] newArray(int i) {
            return new android.hardware.input.IInputDeviceBatteryState[i];
        }
    };
    public int deviceId = 0;
    public long updateTime = 0;
    public boolean isPresent = false;
    public int status = 0;
    public float capacity = 0.0f;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.deviceId);
        parcel.writeLong(this.updateTime);
        parcel.writeBoolean(this.isPresent);
        parcel.writeInt(this.status);
        parcel.writeFloat(this.capacity);
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
            this.deviceId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.updateTime = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isPresent = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.status = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.capacity = parcel.readFloat();
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
        if (obj == null || !(obj instanceof android.hardware.input.IInputDeviceBatteryState)) {
            return false;
        }
        android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState = (android.hardware.input.IInputDeviceBatteryState) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.deviceId), java.lang.Integer.valueOf(iInputDeviceBatteryState.deviceId)) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.updateTime), java.lang.Long.valueOf(iInputDeviceBatteryState.updateTime)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.isPresent), java.lang.Boolean.valueOf(iInputDeviceBatteryState.isPresent)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.status), java.lang.Integer.valueOf(iInputDeviceBatteryState.status)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.capacity), java.lang.Float.valueOf(iInputDeviceBatteryState.capacity))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.deviceId), java.lang.Long.valueOf(this.updateTime), java.lang.Boolean.valueOf(this.isPresent), java.lang.Integer.valueOf(this.status), java.lang.Float.valueOf(this.capacity)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
