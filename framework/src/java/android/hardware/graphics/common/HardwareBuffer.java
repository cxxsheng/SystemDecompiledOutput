package android.hardware.graphics.common;

/* loaded from: classes2.dex */
public class HardwareBuffer implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.graphics.common.HardwareBuffer> CREATOR = new android.os.Parcelable.Creator<android.hardware.graphics.common.HardwareBuffer>() { // from class: android.hardware.graphics.common.HardwareBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.HardwareBuffer createFromParcel(android.os.Parcel parcel) {
            android.hardware.graphics.common.HardwareBuffer hardwareBuffer = new android.hardware.graphics.common.HardwareBuffer();
            hardwareBuffer.readFromParcel(parcel);
            return hardwareBuffer;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.HardwareBuffer[] newArray(int i) {
            return new android.hardware.graphics.common.HardwareBuffer[i];
        }
    };
    public android.hardware.graphics.common.HardwareBufferDescription description;
    public android.hardware.common.NativeHandle handle;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.description, i);
        parcel.writeTypedObject(this.handle, i);
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
            this.description = (android.hardware.graphics.common.HardwareBufferDescription) parcel.readTypedObject(android.hardware.graphics.common.HardwareBufferDescription.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.handle = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.description) | 0 | describeContents(this.handle);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
