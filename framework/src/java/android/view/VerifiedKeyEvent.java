package android.view;

/* loaded from: classes4.dex */
public final class VerifiedKeyEvent extends android.view.VerifiedInputEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.VerifiedKeyEvent> CREATOR = new android.os.Parcelable.Creator<android.view.VerifiedKeyEvent>() { // from class: android.view.VerifiedKeyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedKeyEvent[] newArray(int i) {
            return new android.view.VerifiedKeyEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.VerifiedKeyEvent createFromParcel(android.os.Parcel parcel) {
            return new android.view.VerifiedKeyEvent(parcel);
        }
    };
    private static final java.lang.String TAG = "VerifiedKeyEvent";
    private int mAction;
    private long mDownTimeNanos;
    private int mFlags;
    private int mKeyCode;
    private int mMetaState;
    private int mRepeatCount;
    private int mScanCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KeyEventAction {
    }

    public java.lang.Boolean getFlag(int i) {
        switch (i) {
            case 32:
            case 2048:
                return java.lang.Boolean.valueOf((i & this.mFlags) != 0);
            default:
                return null;
        }
    }

    public VerifiedKeyEvent(int i, long j, int i2, int i3, int i4, long j2, int i5, int i6, int i7, int i8, int i9) {
        super(1, i, j, i2, i3);
        this.mAction = i4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.VerifiedKeyEvent.KeyEventAction.class, (java.lang.annotation.Annotation) null, this.mAction);
        this.mDownTimeNanos = j2;
        this.mFlags = i5;
        this.mKeyCode = i6;
        this.mScanCode = i7;
        this.mMetaState = i8;
        this.mRepeatCount = i9;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getDownTimeNanos() {
        return this.mDownTimeNanos;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getKeyCode() {
        return this.mKeyCode;
    }

    public int getScanCode() {
        return this.mScanCode;
    }

    public int getMetaState() {
        return this.mMetaState;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    @Override // android.view.VerifiedInputEvent
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.VerifiedKeyEvent verifiedKeyEvent = (android.view.VerifiedKeyEvent) obj;
        if (super.equals(verifiedKeyEvent) && this.mAction == verifiedKeyEvent.mAction && this.mDownTimeNanos == verifiedKeyEvent.mDownTimeNanos && this.mFlags == verifiedKeyEvent.mFlags && this.mKeyCode == verifiedKeyEvent.mKeyCode && this.mScanCode == verifiedKeyEvent.mScanCode && this.mMetaState == verifiedKeyEvent.mMetaState && this.mRepeatCount == verifiedKeyEvent.mRepeatCount) {
            return true;
        }
        return false;
    }

    @Override // android.view.VerifiedInputEvent
    public int hashCode() {
        return ((((((((((((((super.hashCode() + 31) * 31) + this.mAction) * 31) + java.lang.Long.hashCode(this.mDownTimeNanos)) * 31) + this.mFlags) * 31) + this.mKeyCode) * 31) + this.mScanCode) * 31) + this.mMetaState) * 31) + this.mRepeatCount;
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAction);
        parcel.writeLong(this.mDownTimeNanos);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mScanCode);
        parcel.writeInt(this.mMetaState);
        parcel.writeInt(this.mRepeatCount);
    }

    @Override // android.view.VerifiedInputEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VerifiedKeyEvent(android.os.Parcel parcel) {
        super(parcel, 1);
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        this.mAction = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.VerifiedKeyEvent.KeyEventAction.class, (java.lang.annotation.Annotation) null, this.mAction);
        this.mDownTimeNanos = readLong;
        this.mFlags = readInt2;
        this.mKeyCode = readInt3;
        this.mScanCode = readInt4;
        this.mMetaState = readInt5;
        this.mRepeatCount = readInt6;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
