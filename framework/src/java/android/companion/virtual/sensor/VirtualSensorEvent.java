package android.companion.virtual.sensor;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensorEvent> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensorEvent>() { // from class: android.companion.virtual.sensor.VirtualSensorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensorEvent createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.sensor.VirtualSensorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensorEvent[] newArray(int i) {
            return new android.companion.virtual.sensor.VirtualSensorEvent[i];
        }
    };
    private long mTimestampNanos;
    private float[] mValues;

    private VirtualSensorEvent(float[] fArr, long j) {
        this.mValues = fArr;
        this.mTimestampNanos = j;
    }

    private VirtualSensorEvent(android.os.Parcel parcel) {
        this.mValues = new float[parcel.readInt()];
        parcel.readFloatArray(this.mValues);
        this.mTimestampNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mValues.length);
        parcel.writeFloatArray(this.mValues);
        parcel.writeLong(this.mTimestampNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float[] getValues() {
        return this.mValues;
    }

    public long getTimestampNanos() {
        return this.mTimestampNanos;
    }

    public static final class Builder {
        private long mTimestampNanos = 0;
        private float[] mValues;

        public Builder(float[] fArr) {
            this.mValues = fArr;
        }

        public android.companion.virtual.sensor.VirtualSensorEvent build() {
            if (this.mValues == null || this.mValues.length == 0) {
                throw new java.lang.IllegalArgumentException("Cannot build virtual sensor event with no values.");
            }
            if (this.mTimestampNanos <= 0) {
                this.mTimestampNanos = android.os.SystemClock.elapsedRealtimeNanos();
            }
            return new android.companion.virtual.sensor.VirtualSensorEvent(this.mValues, this.mTimestampNanos);
        }

        public android.companion.virtual.sensor.VirtualSensorEvent.Builder setTimestampNanos(long j) {
            this.mTimestampNanos = j;
            return this;
        }
    }
}
