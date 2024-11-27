package android.window;

/* loaded from: classes4.dex */
public final class ActivityWindowInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.ActivityWindowInfo> CREATOR = new android.os.Parcelable.Creator<android.window.ActivityWindowInfo>() { // from class: android.window.ActivityWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ActivityWindowInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.ActivityWindowInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ActivityWindowInfo[] newArray(int i) {
            return new android.window.ActivityWindowInfo[i];
        }
    };
    private boolean mIsEmbedded;
    private final android.graphics.Rect mTaskBounds;
    private final android.graphics.Rect mTaskFragmentBounds;

    public ActivityWindowInfo() {
        this.mTaskBounds = new android.graphics.Rect();
        this.mTaskFragmentBounds = new android.graphics.Rect();
    }

    public ActivityWindowInfo(android.window.ActivityWindowInfo activityWindowInfo) {
        this.mTaskBounds = new android.graphics.Rect();
        this.mTaskFragmentBounds = new android.graphics.Rect();
        set(activityWindowInfo);
    }

    public void set(android.window.ActivityWindowInfo activityWindowInfo) {
        set(activityWindowInfo.mIsEmbedded, activityWindowInfo.mTaskBounds, activityWindowInfo.mTaskFragmentBounds);
    }

    public void set(boolean z, android.graphics.Rect rect, android.graphics.Rect rect2) {
        this.mIsEmbedded = z;
        this.mTaskBounds.set(rect);
        this.mTaskFragmentBounds.set(rect2);
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public android.graphics.Rect getTaskBounds() {
        return this.mTaskBounds;
    }

    public android.graphics.Rect getTaskFragmentBounds() {
        return this.mTaskFragmentBounds;
    }

    private ActivityWindowInfo(android.os.Parcel parcel) {
        this.mTaskBounds = new android.graphics.Rect();
        this.mTaskFragmentBounds = new android.graphics.Rect();
        this.mIsEmbedded = parcel.readBoolean();
        this.mTaskBounds.readFromParcel(parcel);
        this.mTaskFragmentBounds.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEmbedded);
        this.mTaskBounds.writeToParcel(parcel, i);
        this.mTaskFragmentBounds.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.window.ActivityWindowInfo activityWindowInfo = (android.window.ActivityWindowInfo) obj;
        if (this.mIsEmbedded == activityWindowInfo.mIsEmbedded && this.mTaskBounds.equals(activityWindowInfo.mTaskBounds) && this.mTaskFragmentBounds.equals(activityWindowInfo.mTaskFragmentBounds)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + (this.mIsEmbedded ? 1 : 0)) * 31) + this.mTaskBounds.hashCode()) * 31) + this.mTaskFragmentBounds.hashCode();
    }

    public java.lang.String toString() {
        return "ActivityWindowInfo{isEmbedded=" + this.mIsEmbedded + ", taskBounds=" + this.mTaskBounds + ", taskFragmentBounds=" + this.mTaskFragmentBounds + "}";
    }
}
