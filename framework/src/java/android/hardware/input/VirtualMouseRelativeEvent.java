package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseRelativeEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualMouseRelativeEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualMouseRelativeEvent>() { // from class: android.hardware.input.VirtualMouseRelativeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseRelativeEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualMouseRelativeEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseRelativeEvent[] newArray(int i) {
            return new android.hardware.input.VirtualMouseRelativeEvent[i];
        }
    };
    private final long mEventTimeNanos;
    private final float mRelativeX;
    private final float mRelativeY;

    private VirtualMouseRelativeEvent(float f, float f2, long j) {
        this.mRelativeX = f;
        this.mRelativeY = f2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseRelativeEvent(android.os.Parcel parcel) {
        this.mRelativeX = parcel.readFloat();
        this.mRelativeY = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mRelativeX);
        parcel.writeFloat(this.mRelativeY);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "VirtualMouseRelativeEvent( x=" + this.mRelativeX + " y=" + this.mRelativeY + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public float getRelativeX() {
        return this.mRelativeX;
    }

    public float getRelativeY() {
        return this.mRelativeY;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private long mEventTimeNanos = 0;
        private float mRelativeX;
        private float mRelativeY;

        public android.hardware.input.VirtualMouseRelativeEvent build() {
            return new android.hardware.input.VirtualMouseRelativeEvent(this.mRelativeX, this.mRelativeY, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualMouseRelativeEvent.Builder setRelativeX(float f) {
            this.mRelativeX = f;
            return this;
        }

        public android.hardware.input.VirtualMouseRelativeEvent.Builder setRelativeY(float f) {
            this.mRelativeY = f;
            return this;
        }

        public android.hardware.input.VirtualMouseRelativeEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
