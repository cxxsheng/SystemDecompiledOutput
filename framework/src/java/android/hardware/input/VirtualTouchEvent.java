package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualTouchEvent implements android.os.Parcelable {
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_UP = 1;
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualTouchEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualTouchEvent>() { // from class: android.hardware.input.VirtualTouchEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualTouchEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualTouchEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualTouchEvent[] newArray(int i) {
            return new android.hardware.input.VirtualTouchEvent[i];
        }
    };
    private static final int MAX_POINTERS = 16;
    public static final int TOOL_TYPE_FINGER = 1;
    public static final int TOOL_TYPE_PALM = 5;
    public static final int TOOL_TYPE_UNKNOWN = 0;
    private final int mAction;
    private final long mEventTimeNanos;
    private final float mMajorAxisSize;
    private final int mPointerId;
    private final float mPressure;
    private final int mToolType;
    private final float mX;
    private final float mY;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ToolType {
    }

    private VirtualTouchEvent(int i, int i2, int i3, float f, float f2, float f3, float f4, long j) {
        this.mPointerId = i;
        this.mToolType = i2;
        this.mAction = i3;
        this.mX = f;
        this.mY = f2;
        this.mPressure = f3;
        this.mMajorAxisSize = f4;
        this.mEventTimeNanos = j;
    }

    private VirtualTouchEvent(android.os.Parcel parcel) {
        this.mPointerId = parcel.readInt();
        this.mToolType = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mX = parcel.readFloat();
        this.mY = parcel.readFloat();
        this.mPressure = parcel.readFloat();
        this.mMajorAxisSize = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPointerId);
        parcel.writeInt(this.mToolType);
        parcel.writeInt(this.mAction);
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeFloat(this.mPressure);
        parcel.writeFloat(this.mMajorAxisSize);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "VirtualTouchEvent( pointerId=" + this.mPointerId + " toolType=" + android.view.MotionEvent.toolTypeToString(this.mToolType) + " action=" + android.view.MotionEvent.actionToString(this.mAction) + " x=" + this.mX + " y=" + this.mY + " pressure=" + this.mPressure + " majorAxisSize=" + this.mMajorAxisSize + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public int getPointerId() {
        return this.mPointerId;
    }

    public int getToolType() {
        return this.mToolType;
    }

    public int getAction() {
        return this.mAction;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getPressure() {
        return this.mPressure;
    }

    public float getMajorAxisSize() {
        return this.mMajorAxisSize;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mToolType = 0;
        private int mPointerId = -1;
        private int mAction = -1;
        private float mX = Float.NaN;
        private float mY = Float.NaN;
        private float mPressure = Float.NaN;
        private float mMajorAxisSize = Float.NaN;
        private long mEventTimeNanos = 0;

        public android.hardware.input.VirtualTouchEvent build() {
            if (this.mToolType == 0 || this.mPointerId == -1 || this.mAction == -1 || java.lang.Float.isNaN(this.mX) || java.lang.Float.isNaN(this.mY)) {
                throw new java.lang.IllegalArgumentException("Cannot build virtual touch event with unset required fields");
            }
            if ((this.mToolType == 5 && this.mAction != 3) || (this.mAction == 3 && this.mToolType != 5)) {
                throw new java.lang.IllegalArgumentException("ACTION_CANCEL and TOOL_TYPE_PALM must always appear together");
            }
            return new android.hardware.input.VirtualTouchEvent(this.mPointerId, this.mToolType, this.mAction, this.mX, this.mY, this.mPressure, this.mMajorAxisSize, this.mEventTimeNanos);
        }

        public android.hardware.input.VirtualTouchEvent.Builder setPointerId(int i) {
            if (i < 0 || i > 15) {
                throw new java.lang.IllegalArgumentException("The pointer id must be in the range 0 - 15inclusive, but was: " + i);
            }
            this.mPointerId = i;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setToolType(int i) {
            if (i != 1 && i != 5) {
                throw new java.lang.IllegalArgumentException("Unsupported touch event tool type");
            }
            this.mToolType = i;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setAction(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                throw new java.lang.IllegalArgumentException("Unsupported touch event action type: " + i);
            }
            this.mAction = i;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setX(float f) {
            this.mX = f;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setY(float f) {
            this.mY = f;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setPressure(float f) {
            if (f < 0.0f) {
                throw new java.lang.IllegalArgumentException("Touch event pressure cannot be negative");
            }
            this.mPressure = f;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setMajorAxisSize(float f) {
            if (f < 0.0f) {
                throw new java.lang.IllegalArgumentException("Touch event major axis size cannot be negative");
            }
            this.mMajorAxisSize = f;
            return this;
        }

        public android.hardware.input.VirtualTouchEvent.Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
