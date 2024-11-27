package android.view;

/* loaded from: classes4.dex */
public final class VerifiedMotionEvent extends android.view.VerifiedInputEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.VerifiedMotionEvent> CREATOR = new android.os.Parcelable.Creator<android.view.VerifiedMotionEvent>() { // from class: android.view.VerifiedMotionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedMotionEvent[] newArray(int i) {
            return new android.view.VerifiedMotionEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedMotionEvent createFromParcel(android.os.Parcel parcel) {
            return new android.view.VerifiedMotionEvent(parcel);
        }
    };
    private static final java.lang.String TAG = "VerifiedMotionEvent";
    private int mActionMasked;
    private int mButtonState;
    private long mDownTimeNanos;
    private int mFlags;
    private int mMetaState;
    private float mRawX;
    private float mRawY;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MotionEventAction {
    }

    public java.lang.Boolean getFlag(int i) {
        switch (i) {
            case 1:
            case 2:
            case 2048:
                return java.lang.Boolean.valueOf((i & this.mFlags) != 0);
            default:
                return null;
        }
    }

    public VerifiedMotionEvent(int i, long j, int i2, int i3, float f, float f2, int i4, long j2, int i5, int i6, int i7) {
        super(2, i, j, i2, i3);
        this.mRawX = f;
        this.mRawY = f2;
        this.mActionMasked = i4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.VerifiedMotionEvent.MotionEventAction.class, (java.lang.annotation.Annotation) null, this.mActionMasked);
        this.mDownTimeNanos = j2;
        this.mFlags = i5;
        this.mMetaState = i6;
        this.mButtonState = i7;
    }

    public float getRawX() {
        return this.mRawX;
    }

    public float getRawY() {
        return this.mRawY;
    }

    public int getActionMasked() {
        return this.mActionMasked;
    }

    public long getDownTimeNanos() {
        return this.mDownTimeNanos;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getMetaState() {
        return this.mMetaState;
    }

    public int getButtonState() {
        return this.mButtonState;
    }

    @Override // android.view.VerifiedInputEvent
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.VerifiedMotionEvent verifiedMotionEvent = (android.view.VerifiedMotionEvent) obj;
        if (super.equals(verifiedMotionEvent) && this.mRawX == verifiedMotionEvent.mRawX && this.mRawY == verifiedMotionEvent.mRawY && this.mActionMasked == verifiedMotionEvent.mActionMasked && this.mDownTimeNanos == verifiedMotionEvent.mDownTimeNanos && this.mFlags == verifiedMotionEvent.mFlags && this.mMetaState == verifiedMotionEvent.mMetaState && this.mButtonState == verifiedMotionEvent.mButtonState) {
            return true;
        }
        return false;
    }

    @Override // android.view.VerifiedInputEvent
    public int hashCode() {
        return ((((((((((((((super.hashCode() + 31) * 31) + java.lang.Float.hashCode(this.mRawX)) * 31) + java.lang.Float.hashCode(this.mRawY)) * 31) + this.mActionMasked) * 31) + java.lang.Long.hashCode(this.mDownTimeNanos)) * 31) + this.mFlags) * 31) + this.mMetaState) * 31) + this.mButtonState;
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.mRawX);
        parcel.writeFloat(this.mRawY);
        parcel.writeInt(this.mActionMasked);
        parcel.writeLong(this.mDownTimeNanos);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mMetaState);
        parcel.writeInt(this.mButtonState);
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VerifiedMotionEvent(android.os.Parcel parcel) {
        super(parcel, 2);
        float readFloat = parcel.readFloat();
        float readFloat2 = parcel.readFloat();
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        this.mRawX = readFloat;
        this.mRawY = readFloat2;
        this.mActionMasked = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.VerifiedMotionEvent.MotionEventAction.class, (java.lang.annotation.Annotation) null, this.mActionMasked);
        this.mDownTimeNanos = readLong;
        this.mFlags = readInt2;
        this.mMetaState = readInt3;
        this.mButtonState = readInt4;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
