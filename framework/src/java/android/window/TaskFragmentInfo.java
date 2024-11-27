package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentInfo> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentInfo>() { // from class: android.window.TaskFragmentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentInfo[] newArray(int i) {
            return new android.window.TaskFragmentInfo[i];
        }
    };
    private final java.util.List<android.os.IBinder> mActivities;
    private final android.content.res.Configuration mConfiguration;
    private final android.os.IBinder mFragmentToken;
    private final java.util.List<android.os.IBinder> mInRequestedTaskFragmentActivities;
    private final boolean mIsClearedForReorderActivityToFront;
    private final boolean mIsTaskClearedForReuse;
    private final boolean mIsTaskFragmentClearedForPip;
    private final boolean mIsVisible;
    private final android.graphics.Point mMinimumDimensions;
    private final android.graphics.Point mPositionInParent;
    private final int mRunningActivityCount;
    private final android.window.WindowContainerToken mToken;

    public TaskFragmentInfo(android.os.IBinder iBinder, android.window.WindowContainerToken windowContainerToken, android.content.res.Configuration configuration, int i, boolean z, java.util.List<android.os.IBinder> list, java.util.List<android.os.IBinder> list2, android.graphics.Point point, boolean z2, boolean z3, boolean z4, android.graphics.Point point2) {
        this.mConfiguration = new android.content.res.Configuration();
        this.mActivities = new java.util.ArrayList();
        this.mInRequestedTaskFragmentActivities = new java.util.ArrayList();
        this.mPositionInParent = new android.graphics.Point();
        this.mMinimumDimensions = new android.graphics.Point();
        this.mFragmentToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
        this.mToken = (android.window.WindowContainerToken) java.util.Objects.requireNonNull(windowContainerToken);
        this.mConfiguration.setTo(configuration);
        this.mRunningActivityCount = i;
        this.mIsVisible = z;
        this.mActivities.addAll(list);
        this.mInRequestedTaskFragmentActivities.addAll(list2);
        this.mPositionInParent.set(point);
        this.mIsTaskClearedForReuse = z2;
        this.mIsTaskFragmentClearedForPip = z3;
        this.mIsClearedForReorderActivityToFront = z4;
        this.mMinimumDimensions.set(point2);
    }

    public android.os.IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    public android.window.WindowContainerToken getToken() {
        return this.mToken;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean isEmpty() {
        return this.mRunningActivityCount == 0;
    }

    public boolean hasRunningActivity() {
        return this.mRunningActivityCount > 0;
    }

    public int getRunningActivityCount() {
        return this.mRunningActivityCount;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public java.util.List<android.os.IBinder> getActivities() {
        return this.mActivities;
    }

    public java.util.List<android.os.IBinder> getActivitiesRequestedInTaskFragment() {
        return this.mInRequestedTaskFragmentActivities;
    }

    public android.graphics.Point getPositionInParent() {
        return this.mPositionInParent;
    }

    public boolean isTaskClearedForReuse() {
        return this.mIsTaskClearedForReuse;
    }

    public boolean isTaskFragmentClearedForPip() {
        return this.mIsTaskFragmentClearedForPip;
    }

    public boolean isClearedForReorderActivityToFront() {
        return this.mIsClearedForReorderActivityToFront;
    }

    public int getWindowingMode() {
        return this.mConfiguration.windowConfiguration.getWindowingMode();
    }

    public int getMinimumWidth() {
        return this.mMinimumDimensions.x;
    }

    public int getMinimumHeight() {
        return this.mMinimumDimensions.y;
    }

    public boolean equalsForTaskFragmentOrganizer(android.window.TaskFragmentInfo taskFragmentInfo) {
        return taskFragmentInfo != null && this.mFragmentToken.equals(taskFragmentInfo.mFragmentToken) && this.mToken.equals(taskFragmentInfo.mToken) && this.mRunningActivityCount == taskFragmentInfo.mRunningActivityCount && this.mIsVisible == taskFragmentInfo.mIsVisible && getWindowingMode() == taskFragmentInfo.getWindowingMode() && this.mActivities.equals(taskFragmentInfo.mActivities) && this.mInRequestedTaskFragmentActivities.equals(taskFragmentInfo.mInRequestedTaskFragmentActivities) && this.mPositionInParent.equals(taskFragmentInfo.mPositionInParent) && this.mIsTaskClearedForReuse == taskFragmentInfo.mIsTaskClearedForReuse && this.mIsTaskFragmentClearedForPip == taskFragmentInfo.mIsTaskFragmentClearedForPip && this.mIsClearedForReorderActivityToFront == taskFragmentInfo.mIsClearedForReorderActivityToFront && this.mMinimumDimensions.equals(taskFragmentInfo.mMinimumDimensions);
    }

    private TaskFragmentInfo(android.os.Parcel parcel) {
        this.mConfiguration = new android.content.res.Configuration();
        this.mActivities = new java.util.ArrayList();
        this.mInRequestedTaskFragmentActivities = new java.util.ArrayList();
        this.mPositionInParent = new android.graphics.Point();
        this.mMinimumDimensions = new android.graphics.Point();
        this.mFragmentToken = parcel.readStrongBinder();
        this.mToken = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
        this.mConfiguration.readFromParcel(parcel);
        this.mRunningActivityCount = parcel.readInt();
        this.mIsVisible = parcel.readBoolean();
        parcel.readBinderList(this.mActivities);
        parcel.readBinderList(this.mInRequestedTaskFragmentActivities);
        this.mPositionInParent.readFromParcel(parcel);
        this.mIsTaskClearedForReuse = parcel.readBoolean();
        this.mIsTaskFragmentClearedForPip = parcel.readBoolean();
        this.mIsClearedForReorderActivityToFront = parcel.readBoolean();
        this.mMinimumDimensions.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mFragmentToken);
        parcel.writeTypedObject(this.mToken, i);
        this.mConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.mRunningActivityCount);
        parcel.writeBoolean(this.mIsVisible);
        parcel.writeBinderList(this.mActivities);
        parcel.writeBinderList(this.mInRequestedTaskFragmentActivities);
        this.mPositionInParent.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mIsTaskClearedForReuse);
        parcel.writeBoolean(this.mIsTaskFragmentClearedForPip);
        parcel.writeBoolean(this.mIsClearedForReorderActivityToFront);
        this.mMinimumDimensions.writeToParcel(parcel, i);
    }

    public java.lang.String toString() {
        return "TaskFragmentInfo{ fragmentToken=" + this.mFragmentToken + " token=" + this.mToken + " runningActivityCount=" + this.mRunningActivityCount + " isVisible=" + this.mIsVisible + " activities=" + this.mActivities + " inRequestedTaskFragmentActivities" + this.mInRequestedTaskFragmentActivities + " positionInParent=" + this.mPositionInParent + " isTaskClearedForReuse=" + this.mIsTaskClearedForReuse + " isTaskFragmentClearedForPip=" + this.mIsTaskFragmentClearedForPip + " mIsClearedForReorderActivityToFront=" + this.mIsClearedForReorderActivityToFront + " minimumDimensions=" + this.mMinimumDimensions + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
