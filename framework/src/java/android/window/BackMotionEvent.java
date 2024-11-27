package android.window;

/* loaded from: classes4.dex */
public final class BackMotionEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.BackMotionEvent> CREATOR = new android.os.Parcelable.Creator<android.window.BackMotionEvent>() { // from class: android.window.BackMotionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackMotionEvent createFromParcel(android.os.Parcel parcel) {
            return new android.window.BackMotionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackMotionEvent[] newArray(int i) {
            return new android.window.BackMotionEvent[i];
        }
    };
    private final android.view.RemoteAnimationTarget mDepartingAnimationTarget;
    private final float mProgress;
    private final int mSwipeEdge;
    private final float mTouchX;
    private final float mTouchY;
    private final boolean mTriggerBack;
    private final float mVelocityX;
    private final float mVelocityY;

    public BackMotionEvent(float f, float f2, float f3, float f4, float f5, boolean z, int i, android.view.RemoteAnimationTarget remoteAnimationTarget) {
        this.mTouchX = f;
        this.mTouchY = f2;
        this.mProgress = f3;
        this.mVelocityX = f4;
        this.mVelocityY = f5;
        this.mTriggerBack = z;
        this.mSwipeEdge = i;
        this.mDepartingAnimationTarget = remoteAnimationTarget;
    }

    private BackMotionEvent(android.os.Parcel parcel) {
        this.mTouchX = parcel.readFloat();
        this.mTouchY = parcel.readFloat();
        this.mProgress = parcel.readFloat();
        this.mVelocityX = parcel.readFloat();
        this.mVelocityY = parcel.readFloat();
        this.mTriggerBack = parcel.readBoolean();
        this.mSwipeEdge = parcel.readInt();
        this.mDepartingAnimationTarget = (android.view.RemoteAnimationTarget) parcel.readTypedObject(android.view.RemoteAnimationTarget.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mTouchX);
        parcel.writeFloat(this.mTouchY);
        parcel.writeFloat(this.mProgress);
        parcel.writeFloat(this.mVelocityX);
        parcel.writeFloat(this.mVelocityY);
        parcel.writeBoolean(this.mTriggerBack);
        parcel.writeInt(this.mSwipeEdge);
        parcel.writeTypedObject(this.mDepartingAnimationTarget, i);
    }

    public float getTouchX() {
        return this.mTouchX;
    }

    public float getTouchY() {
        return this.mTouchY;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public float getVelocityX() {
        return this.mVelocityX;
    }

    public float getVelocityY() {
        return this.mVelocityY;
    }

    public boolean getTriggerBack() {
        return this.mTriggerBack;
    }

    public int getSwipeEdge() {
        return this.mSwipeEdge;
    }

    public android.view.RemoteAnimationTarget getDepartingAnimationTarget() {
        return this.mDepartingAnimationTarget;
    }

    public java.lang.String toString() {
        return "BackMotionEvent{mTouchX=" + this.mTouchX + ", mTouchY=" + this.mTouchY + ", mProgress=" + this.mProgress + ", mVelocityX=" + this.mVelocityX + ", mVelocityY=" + this.mVelocityY + ", mTriggerBack=" + this.mTriggerBack + ", mSwipeEdge" + this.mSwipeEdge + ", mDepartingAnimationTarget" + this.mDepartingAnimationTarget + "}";
    }
}
