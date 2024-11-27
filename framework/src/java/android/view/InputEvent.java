package android.view;

/* loaded from: classes4.dex */
public abstract class InputEvent implements android.os.Parcelable {
    protected static final int PARCEL_TOKEN_KEY_EVENT = 2;
    protected static final int PARCEL_TOKEN_MOTION_EVENT = 1;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
    protected boolean mRecycled;
    private java.lang.RuntimeException mRecycledLocation;
    protected int mSeq = mNextSeq.getAndIncrement();
    private static final java.util.concurrent.atomic.AtomicInteger mNextSeq = new java.util.concurrent.atomic.AtomicInteger();
    public static final android.os.Parcelable.Creator<android.view.InputEvent> CREATOR = new android.os.Parcelable.Creator<android.view.InputEvent>() { // from class: android.view.InputEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputEvent createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 2) {
                return android.view.KeyEvent.createFromParcelBody(parcel);
            }
            if (readInt == 1) {
                return android.view.MotionEvent.createFromParcelBody(parcel);
            }
            throw new java.lang.IllegalStateException("Unexpected input event type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputEvent[] newArray(int i) {
            return new android.view.InputEvent[i];
        }
    };

    public abstract void cancel();

    public abstract android.view.InputEvent copy();

    public abstract int getDeviceId();

    public abstract int getDisplayId();

    public abstract long getEventTime();

    public abstract long getEventTimeNanos();

    public abstract int getId();

    public abstract int getSource();

    public abstract boolean isTainted();

    public abstract void setDisplayId(int i);

    public abstract void setSource(int i);

    public abstract void setTainted(boolean z);

    InputEvent() {
    }

    public final android.view.InputDevice getDevice() {
        return android.view.InputDevice.getDevice(getDeviceId());
    }

    public boolean isFromSource(int i) {
        return (getSource() & i) == i;
    }

    public void recycle() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException(toString() + " recycled twice!");
        }
        this.mRecycled = true;
    }

    public void recycleIfNeededAfterDispatch() {
        recycle();
    }

    protected void prepareForReuse() {
        this.mRecycled = false;
        this.mRecycledLocation = null;
        this.mSeq = mNextSeq.getAndIncrement();
    }

    public int getSequenceNumber() {
        return this.mSeq;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
