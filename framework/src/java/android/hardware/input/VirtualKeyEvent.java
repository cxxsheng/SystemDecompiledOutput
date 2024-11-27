package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualKeyEvent implements android.os.Parcelable {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_UP = 1;
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualKeyEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualKeyEvent>() { // from class: android.hardware.input.VirtualKeyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualKeyEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualKeyEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualKeyEvent[] newArray(int i) {
            return new android.hardware.input.VirtualKeyEvent[i];
        }
    };
    private final int mAction;
    private final long mEventTimeNanos;
    private final int mKeyCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SupportedKeycode {
    }

    private VirtualKeyEvent(int i, int i2, long j) {
        this.mAction = i;
        this.mKeyCode = i2;
        this.mEventTimeNanos = j;
    }

    private VirtualKeyEvent(android.os.Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mKeyCode = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mKeyCode);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "VirtualKeyEvent( action=" + android.view.KeyEvent.actionToString(this.mAction) + " keyCode=" + android.view.KeyEvent.keyCodeToString(this.mKeyCode) + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public int getKeyCode() {
        return this.mKeyCode;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mAction = -1;
        private int mKeyCode = -1;
        private long mEventTimeNanos = 0;

        public android.hardware.input.VirtualKeyEvent build() {
            if (this.mAction == -1 || this.mKeyCode == -1) {
                throw new java.lang.IllegalArgumentException("Cannot build virtual key event with unset fields");
            }
            return new android.hardware.input.VirtualKeyEvent(this.mAction, this.mKeyCode, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualKeyEvent.Builder setKeyCode(int i) {
            this.mKeyCode = i;
            return this;
        }

        public android.hardware.input.VirtualKeyEvent.Builder setAction(int i) {
            if (i != 0 && i != 1) {
                throw new java.lang.IllegalArgumentException("Unsupported action type");
            }
            this.mAction = i;
            return this;
        }

        public android.hardware.input.VirtualKeyEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
