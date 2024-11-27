package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseButtonEvent implements android.os.Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_UNKNOWN = -1;
    public static final int BUTTON_BACK = 8;
    public static final int BUTTON_FORWARD = 16;
    public static final int BUTTON_PRIMARY = 1;
    public static final int BUTTON_SECONDARY = 2;
    public static final int BUTTON_TERTIARY = 4;
    public static final int BUTTON_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualMouseButtonEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualMouseButtonEvent>() { // from class: android.hardware.input.VirtualMouseButtonEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseButtonEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualMouseButtonEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseButtonEvent[] newArray(int i) {
            return new android.hardware.input.VirtualMouseButtonEvent[i];
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

    private VirtualMouseButtonEvent(int i, int i2, long j) {
        this.mAction = i;
        this.mButtonCode = i2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseButtonEvent(android.os.Parcel parcel) {
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

    public java.lang.String toString() {
        return "VirtualMouseButtonEvent( action=" + android.view.MotionEvent.actionToString(this.mAction) + " button=" + android.view.MotionEvent.buttonStateToString(this.mButtonCode) + " eventTime(ns)=" + this.mEventTimeNanos;
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

        public android.hardware.input.VirtualMouseButtonEvent build() {
            if (this.mAction == -1 || this.mButtonCode == -1) {
                throw new java.lang.IllegalArgumentException("Cannot build virtual mouse button event with unset fields");
            }
            return new android.hardware.input.VirtualMouseButtonEvent(this.mAction, this.mButtonCode, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualMouseButtonEvent.Builder setButtonCode(int i) {
            if (i != 1 && i != 4 && i != 2 && i != 8 && i != 16) {
                throw new java.lang.IllegalArgumentException("Unsupported mouse button code");
            }
            this.mButtonCode = i;
            return this;
        }

        public android.hardware.input.VirtualMouseButtonEvent.Builder setAction(int i) {
            if (i != 11 && i != 12) {
                throw new java.lang.IllegalArgumentException("Unsupported mouse button action type");
            }
            this.mAction = i;
            return this;
        }

        public android.hardware.input.VirtualMouseButtonEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
