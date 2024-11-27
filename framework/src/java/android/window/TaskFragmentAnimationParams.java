package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentAnimationParams implements android.os.Parcelable {
    public static final int DEFAULT_ANIMATION_BACKGROUND_COLOR = 0;
    private final int mAnimationBackgroundColor;
    public static final android.window.TaskFragmentAnimationParams DEFAULT = new android.window.TaskFragmentAnimationParams.Builder().build();
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentAnimationParams> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentAnimationParams>() { // from class: android.window.TaskFragmentAnimationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentAnimationParams createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentAnimationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentAnimationParams[] newArray(int i) {
            return new android.window.TaskFragmentAnimationParams[i];
        }
    };

    private TaskFragmentAnimationParams(int i) {
        this.mAnimationBackgroundColor = i;
    }

    public int getAnimationBackgroundColor() {
        return this.mAnimationBackgroundColor;
    }

    private TaskFragmentAnimationParams(android.os.Parcel parcel) {
        this.mAnimationBackgroundColor = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAnimationBackgroundColor);
    }

    public java.lang.String toString() {
        return "TaskFragmentAnimationParams{ animationBgColor=" + java.lang.Integer.toHexString(this.mAnimationBackgroundColor) + "}";
    }

    public int hashCode() {
        return this.mAnimationBackgroundColor;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.window.TaskFragmentAnimationParams) && this.mAnimationBackgroundColor == ((android.window.TaskFragmentAnimationParams) obj).mAnimationBackgroundColor;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mAnimationBackgroundColor = 0;

        public android.window.TaskFragmentAnimationParams.Builder setAnimationBackgroundColor(int i) {
            this.mAnimationBackgroundColor = i;
            return this;
        }

        public android.window.TaskFragmentAnimationParams build() {
            return new android.window.TaskFragmentAnimationParams(this.mAnimationBackgroundColor);
        }
    }
}
