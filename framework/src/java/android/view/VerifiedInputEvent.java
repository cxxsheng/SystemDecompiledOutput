package android.view;

/* loaded from: classes4.dex */
public abstract class VerifiedInputEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.VerifiedInputEvent> CREATOR = new android.os.Parcelable.Creator<android.view.VerifiedInputEvent>() { // from class: android.view.VerifiedInputEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedInputEvent[] newArray(int i) {
            return new android.view.VerifiedInputEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedInputEvent createFromParcel(android.os.Parcel parcel) {
            int peekInt = android.view.VerifiedInputEvent.peekInt(parcel);
            if (peekInt == 1) {
                return android.view.VerifiedKeyEvent.CREATOR.createFromParcel(parcel);
            }
            if (peekInt == 2) {
                return android.view.VerifiedMotionEvent.CREATOR.createFromParcel(parcel);
            }
            throw new java.lang.IllegalArgumentException("Unexpected input event type in parcel.");
        }
    };
    private static final java.lang.String TAG = "VerifiedInputEvent";
    protected static final int VERIFIED_KEY = 1;
    protected static final int VERIFIED_MOTION = 2;
    private int mDeviceId;
    private int mDisplayId;
    private long mEventTimeNanos;
    private int mSource;
    private int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerifiedInputEventType {
    }

    protected VerifiedInputEvent(int i, int i2, long j, int i3, int i4) {
        this.mType = i;
        this.mDeviceId = i2;
        this.mEventTimeNanos = j;
        this.mSource = i3;
        this.mDisplayId = i4;
    }

    protected VerifiedInputEvent(android.os.Parcel parcel, int i) {
        this.mType = parcel.readInt();
        if (this.mType != i) {
            throw new java.lang.IllegalArgumentException("Unexpected input event type token in parcel.");
        }
        this.mDeviceId = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
        this.mSource = parcel.readInt();
        this.mDisplayId = parcel.readInt();
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mDeviceId);
        parcel.writeLong(this.mEventTimeNanos);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mDisplayId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int peekInt(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        parcel.setDataPosition(dataPosition);
        return readInt;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.VerifiedInputEvent verifiedInputEvent = (android.view.VerifiedInputEvent) obj;
        if (this.mType == verifiedInputEvent.mType && getDeviceId() == verifiedInputEvent.getDeviceId() && getEventTimeNanos() == verifiedInputEvent.getEventTimeNanos() && getSource() == verifiedInputEvent.getSource() && getDisplayId() == verifiedInputEvent.getDisplayId()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((this.mType + 31) * 31) + getDeviceId()) * 31) + java.lang.Long.hashCode(getEventTimeNanos())) * 31) + getSource()) * 31) + getDisplayId();
    }
}
