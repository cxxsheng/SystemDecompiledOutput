package android.window;

/* loaded from: classes4.dex */
public final class TaskAppearedInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskAppearedInfo> CREATOR = new android.os.Parcelable.Creator<android.window.TaskAppearedInfo>() { // from class: android.window.TaskAppearedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskAppearedInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskAppearedInfo((android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR), (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskAppearedInfo[] newArray(int i) {
            return new android.window.TaskAppearedInfo[i];
        }
    };
    private final android.view.SurfaceControl mLeash;
    private final android.app.ActivityManager.RunningTaskInfo mTaskInfo;

    public TaskAppearedInfo(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) {
        this.mTaskInfo = runningTaskInfo;
        this.mLeash = surfaceControl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mTaskInfo, i);
        parcel.writeTypedObject(this.mLeash, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.app.ActivityManager.RunningTaskInfo getTaskInfo() {
        return this.mTaskInfo;
    }

    public android.view.SurfaceControl getLeash() {
        return this.mLeash;
    }
}
