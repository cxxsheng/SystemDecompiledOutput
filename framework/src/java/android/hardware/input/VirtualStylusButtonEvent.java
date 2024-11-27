package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualStylusButtonEvent implements android.os.Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_UNKNOWN = -1;
    public static final int BUTTON_PRIMARY = 32;
    public static final int BUTTON_SECONDARY = 64;
    public static final int BUTTON_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualStylusButtonEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualStylusButtonEvent>() { // from class: android.hardware.input.VirtualStylusButtonEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualStylusButtonEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualStylusButtonEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualStylusButtonEvent[] newArray(int i) {
            return new android.hardware.input.VirtualStylusButtonEvent[i];
        }
    };
    private final int mAction;
    private final int mButtonCode;
    private final long mEventTimeNanos;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Button {
    }

    private VirtualStylusButtonEvent(int i, int i2, long j) {
        this.mAction = i;
        this.mButtonCode = i2;
        this.mEventTimeNanos = j;
    }

    private VirtualStylusButtonEvent(android.os.Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mButtonCode = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mButtonCode);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getButtonCode() {
        return this.mButtonCode;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mAction = -1;
        private int mButtonCode = -1;
        private long mEventTimeNanos = 0;

        public android.hardware.input.VirtualStylusButtonEvent build() {
            if (this.mAction == -1) {
                throw new java.lang.IllegalArgumentException("Cannot build stylus button event with unset action");
            }
            if (this.mButtonCode == -1) {
                throw new java.lang.IllegalArgumentException("Cannot build stylus button event with unset button code");
            }
            return new android.hardware.input.VirtualStylusButtonEvent(this.mAction, this.mButtonCode, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualStylusButtonEvent.Builder setButtonCode(int i) {
            if (i != 32 && i != 64) {
                throw new java.lang.IllegalArgumentException("Unsupported stylus button code : " + i);
            }
            this.mButtonCode = i;
            return this;
        }

        public android.hardware.input.VirtualStylusButtonEvent.Builder setAction(int i) {
            if (i != 11 && i != 12) {
                throw new java.lang.IllegalArgumentException("Unsupported stylus button action : " + i);
            }
            this.mAction = i;
            return this;
        }

        public android.hardware.input.VirtualStylusButtonEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
