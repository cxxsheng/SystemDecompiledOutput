package android.window;

/* loaded from: classes4.dex */
public class TaskFragmentParentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentParentInfo> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentParentInfo>() { // from class: android.window.TaskFragmentParentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentParentInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentParentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentParentInfo[] newArray(int i) {
            return new android.window.TaskFragmentParentInfo[i];
        }
    };
    private final android.content.res.Configuration mConfiguration;
    private final android.view.SurfaceControl mDecorSurface;
    private final int mDisplayId;
    private final boolean mHasDirectActivity;
    private final boolean mVisible;

    public TaskFragmentParentInfo(android.content.res.Configuration configuration, int i, boolean z, boolean z2, android.view.SurfaceControl surfaceControl) {
        this.mConfiguration = new android.content.res.Configuration();
        this.mConfiguration.setTo(configuration);
        this.mDisplayId = i;
        this.mVisible = z;
        this.mHasDirectActivity = z2;
        this.mDecorSurface = surfaceControl;
    }

    public TaskFragmentParentInfo(android.window.TaskFragmentParentInfo taskFragmentParentInfo) {
        this.mConfiguration = new android.content.res.Configuration();
        this.mConfiguration.setTo(taskFragmentParentInfo.getConfiguration());
        this.mDisplayId = taskFragmentParentInfo.mDisplayId;
        this.mVisible = taskFragmentParentInfo.mVisible;
        this.mHasDirectActivity = taskFragmentParentInfo.mHasDirectActivity;
        this.mDecorSurface = taskFragmentParentInfo.mDecorSurface;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean hasDirectActivity() {
        return this.mHasDirectActivity;
    }

    public boolean equalsForTaskFragmentOrganizer(android.window.TaskFragmentParentInfo taskFragmentParentInfo) {
        return taskFragmentParentInfo != null && getWindowingMode() == taskFragmentParentInfo.getWindowingMode() && this.mDisplayId == taskFragmentParentInfo.mDisplayId && this.mVisible == taskFragmentParentInfo.mVisible && this.mHasDirectActivity == taskFragmentParentInfo.mHasDirectActivity && this.mDecorSurface == taskFragmentParentInfo.mDecorSurface;
    }

    public android.view.SurfaceControl getDecorSurface() {
        return this.mDecorSurface;
    }

    private int getWindowingMode() {
        return this.mConfiguration.windowConfiguration.getWindowingMode();
    }

    public java.lang.String toString() {
        return android.window.TaskFragmentParentInfo.class.getSimpleName() + ":{config=" + this.mConfiguration + ", displayId=" + this.mDisplayId + ", visible=" + this.mVisible + ", hasDirectActivity=" + this.mHasDirectActivity + ", decorSurface=" + this.mDecorSurface + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.window.TaskFragmentParentInfo)) {
            return false;
        }
        android.window.TaskFragmentParentInfo taskFragmentParentInfo = (android.window.TaskFragmentParentInfo) obj;
        return this.mConfiguration.equals(taskFragmentParentInfo.mConfiguration) && this.mDisplayId == taskFragmentParentInfo.mDisplayId && this.mVisible == taskFragmentParentInfo.mVisible && this.mHasDirectActivity == taskFragmentParentInfo.mHasDirectActivity && this.mDecorSurface == taskFragmentParentInfo.mDecorSurface;
    }

    public int hashCode() {
        return (((((((this.mConfiguration.hashCode() * 31) + this.mDisplayId) * 31) + (this.mVisible ? 1 : 0)) * 31) + (this.mHasDirectActivity ? 1 : 0)) * 31) + java.util.Objects.hashCode(this.mDecorSurface);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.mDisplayId);
        parcel.writeBoolean(this.mVisible);
        parcel.writeBoolean(this.mHasDirectActivity);
        parcel.writeTypedObject(this.mDecorSurface, i);
    }

    private TaskFragmentParentInfo(android.os.Parcel parcel) {
        this.mConfiguration = new android.content.res.Configuration();
        this.mConfiguration.readFromParcel(parcel);
        this.mDisplayId = parcel.readInt();
        this.mVisible = parcel.readBoolean();
        this.mHasDirectActivity = parcel.readBoolean();
        this.mDecorSurface = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
