package android.hardware.common.fmq;

/* loaded from: classes.dex */
public class MQDescriptor<T, Flavor> implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.common.fmq.MQDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.common.fmq.MQDescriptor>() { // from class: android.hardware.common.fmq.MQDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.common.fmq.MQDescriptor createFromParcel(android.os.Parcel parcel) {
            android.hardware.common.fmq.MQDescriptor mQDescriptor = new android.hardware.common.fmq.MQDescriptor();
            mQDescriptor.readFromParcel(parcel);
            return mQDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.common.fmq.MQDescriptor[] newArray(int i) {
            return new android.hardware.common.fmq.MQDescriptor[i];
        }
    };
    public android.hardware.common.fmq.GrantorDescriptor[] grantors;
    public android.hardware.common.NativeHandle handle;
    public int quantum = 0;
    public int flags = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.grantors, i);
        parcel.writeTypedObject(this.handle, i);
        parcel.writeInt(this.quantum);
        parcel.writeInt(this.flags);
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
            this.grantors = (android.hardware.common.fmq.GrantorDescriptor[]) parcel.createTypedArray(android.hardware.common.fmq.GrantorDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.handle = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.quantum = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.flags = parcel.readInt();
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
        return describeContents(this.grantors) | 0 | describeContents(this.handle);
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
