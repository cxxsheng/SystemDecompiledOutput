package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseScrollEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualMouseScrollEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualMouseScrollEvent>() { // from class: android.hardware.input.VirtualMouseScrollEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseScrollEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualMouseScrollEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseScrollEvent[] newArray(int i) {
            return new android.hardware.input.VirtualMouseScrollEvent[i];
        }
    };
    private final long mEventTimeNanos;
    private final float mXAxisMovement;
    private final float mYAxisMovement;

    private VirtualMouseScrollEvent(float f, float f2, long j) {
        this.mXAxisMovement = f;
        this.mYAxisMovement = f2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseScrollEvent(android.os.Parcel parcel) {
        this.mXAxisMovement = parcel.readFloat();
        this.mYAxisMovement = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mXAxisMovement);
        parcel.writeFloat(this.mYAxisMovement);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "VirtualMouseScrollEvent( x=" + this.mXAxisMovement + " y=" + this.mYAxisMovement + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public float getXAxisMovement() {
        return this.mXAxisMovement;
    }

    public float getYAxisMovement() {
        return this.mYAxisMovement;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private long mEventTimeNanos = 0;
        private float mXAxisMovement;
        private float mYAxisMovement;

        public android.hardware.input.VirtualMouseScrollEvent build() {
            return new android.hardware.input.VirtualMouseScrollEvent(this.mXAxisMovement, this.mYAxisMovement, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualMouseScrollEvent.Builder setXAxisMovement(float f) {
            com.android.internal.util.Preconditions.checkArgumentInRange(f, -1.0f, 1.0f, "xAxisMovement");
            this.mXAxisMovement = f;
            return this;
        }

        public android.hardware.input.VirtualMouseScrollEvent.Builder setYAxisMovement(float f) {
            com.android.internal.util.Preconditions.checkArgumentInRange(f, -1.0f, 1.0f, "yAxisMovement");
            this.mYAxisMovement = f;
            return this;
        }

        public android.hardware.input.VirtualMouseScrollEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
