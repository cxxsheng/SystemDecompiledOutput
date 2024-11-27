package android.window;

/* loaded from: classes4.dex */
public final class StartingWindowRemovalInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.StartingWindowRemovalInfo> CREATOR = new android.os.Parcelable.Creator<android.window.StartingWindowRemovalInfo>() { // from class: android.window.StartingWindowRemovalInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.StartingWindowRemovalInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.StartingWindowRemovalInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.StartingWindowRemovalInfo[] newArray(int i) {
            return new android.window.StartingWindowRemovalInfo[i];
        }
    };
    public static final int DEFER_MODE_NONE = 0;
    public static final int DEFER_MODE_NORMAL = 1;
    public static final int DEFER_MODE_ROTATION = 2;
    public int deferRemoveForImeMode;
    public android.graphics.Rect mainFrame;
    public boolean playRevealAnimation;
    public boolean removeImmediately;
    public float roundedCornerRadius;
    public int taskId;
    public android.view.SurfaceControl windowAnimationLeash;
    public boolean windowlessSurface;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeferMode {
    }

    public StartingWindowRemovalInfo() {
    }

    private StartingWindowRemovalInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    void readFromParcel(android.os.Parcel parcel) {
        this.taskId = parcel.readInt();
        this.windowAnimationLeash = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        this.mainFrame = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.playRevealAnimation = parcel.readBoolean();
        this.deferRemoveForImeMode = parcel.readInt();
        this.roundedCornerRadius = parcel.readFloat();
        this.windowlessSurface = parcel.readBoolean();
        this.removeImmediately = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeTypedObject(this.windowAnimationLeash, i);
        parcel.writeTypedObject(this.mainFrame, i);
        parcel.writeBoolean(this.playRevealAnimation);
        parcel.writeInt(this.deferRemoveForImeMode);
        parcel.writeFloat(this.roundedCornerRadius);
        parcel.writeBoolean(this.windowlessSurface);
        parcel.writeBoolean(this.removeImmediately);
    }

    public java.lang.String toString() {
        return "StartingWindowRemovalInfo{taskId=" + this.taskId + " frame=" + this.mainFrame + " playRevealAnimation=" + this.playRevealAnimation + " roundedCornerRadius=" + this.roundedCornerRadius + " deferRemoveForImeMode=" + this.deferRemoveForImeMode + " windowlessSurface=" + this.windowlessSurface + " removeImmediately=" + this.removeImmediately + "}";
    }
}
