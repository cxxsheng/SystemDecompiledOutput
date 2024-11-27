package android.content.pm;

/* loaded from: classes.dex */
public class LauncherActivityInfoInternal implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.LauncherActivityInfoInternal> CREATOR = new android.os.Parcelable.Creator<android.content.pm.LauncherActivityInfoInternal>() { // from class: android.content.pm.LauncherActivityInfoInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LauncherActivityInfoInternal createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.LauncherActivityInfoInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LauncherActivityInfoInternal[] newArray(int i) {
            return new android.content.pm.LauncherActivityInfoInternal[i];
        }
    };
    private android.content.pm.ActivityInfo mActivityInfo;
    private android.content.ComponentName mComponentName;
    private android.content.pm.IncrementalStatesInfo mIncrementalStatesInfo;
    private android.os.UserHandle mUser;

    public LauncherActivityInfoInternal(android.content.pm.ActivityInfo activityInfo, android.content.pm.IncrementalStatesInfo incrementalStatesInfo, android.os.UserHandle userHandle) {
        this.mActivityInfo = activityInfo;
        this.mComponentName = new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
        this.mIncrementalStatesInfo = incrementalStatesInfo;
        this.mUser = userHandle;
    }

    public LauncherActivityInfoInternal(android.os.Parcel parcel) {
        this.mActivityInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
        this.mComponentName = new android.content.ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
        this.mIncrementalStatesInfo = (android.content.pm.IncrementalStatesInfo) parcel.readTypedObject(android.content.pm.IncrementalStatesInfo.CREATOR);
        this.mUser = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.content.pm.ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public android.content.pm.IncrementalStatesInfo getIncrementalStatesInfo() {
        return this.mIncrementalStatesInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mActivityInfo, i);
        parcel.writeTypedObject(this.mIncrementalStatesInfo, i);
        parcel.writeTypedObject(this.mUser, i);
    }
}
