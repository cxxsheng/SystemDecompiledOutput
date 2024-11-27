package android.hardware.power;

/* loaded from: classes2.dex */
public class WorkDurationFixedV1 implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.power.WorkDurationFixedV1> CREATOR = new android.os.Parcelable.Creator<android.hardware.power.WorkDurationFixedV1>() { // from class: android.hardware.power.WorkDurationFixedV1.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.WorkDurationFixedV1 createFromParcel(android.os.Parcel parcel) {
            android.hardware.power.WorkDurationFixedV1 workDurationFixedV1 = new android.hardware.power.WorkDurationFixedV1();
            workDurationFixedV1.readFromParcel(parcel);
            return workDurationFixedV1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.WorkDurationFixedV1[] newArray(int i) {
            return new android.hardware.power.WorkDurationFixedV1[i];
        }
    };
    public long durationNanos = 0;
    public long workPeriodStartTimestampNanos = 0;
    public long cpuDurationNanos = 0;
    public long gpuDurationNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.durationNanos);
        parcel.writeLong(this.workPeriodStartTimestampNanos);
        parcel.writeLong(this.cpuDurationNanos);
        parcel.writeLong(this.gpuDurationNanos);
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
            this.durationNanos = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.workPeriodStartTimestampNanos = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cpuDurationNanos = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.gpuDurationNanos = parcel.readLong();
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
        return 0;
    }
}