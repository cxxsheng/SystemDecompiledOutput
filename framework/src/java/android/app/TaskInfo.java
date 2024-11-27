package android.app;

/* loaded from: classes.dex */
public class TaskInfo {
    public static final int PROPERTY_VALUE_UNSET = -1;
    private static final java.lang.String TAG = "TaskInfo";
    public android.content.ComponentName baseActivity;
    public android.content.Intent baseIntent;
    public int defaultMinSize;
    public android.graphics.Rect displayCutoutInsets;
    public int displayId;
    public boolean isFocused;
    public boolean isResizeable;
    public boolean isRunning;
    public boolean isSleeping;
    public boolean isTopActivityTransparent;
    public boolean isVisible;
    public boolean isVisibleRequested;
    public long lastActiveTime;
    public int lastParentTaskIdBeforePip;
    public int launchIntoPipHostTaskId;
    public android.content.LocusId mTopActivityLocusId;
    public int minHeight;
    public int minWidth;
    public int numActivities;
    public android.content.ComponentName origActivity;
    public int parentTaskId;
    public android.app.PictureInPictureParams pictureInPictureParams;
    public android.graphics.Point positionInParent;
    public android.content.ComponentName realActivity;
    public int resizeMode;
    public boolean shouldDockBigOverlays;
    public boolean supportsMultiWindow;
    public android.app.ActivityManager.TaskDescription taskDescription;
    public int taskId;
    public android.window.WindowContainerToken token;
    public android.content.ComponentName topActivity;
    public android.content.pm.ActivityInfo topActivityInfo;
    public int topActivityType;
    public int userId;
    public int displayAreaFeatureId = -1;
    public final android.content.res.Configuration configuration = new android.content.res.Configuration();
    public java.util.ArrayList<android.os.IBinder> launchCookies = new java.util.ArrayList<>();
    public android.app.AppCompatTaskInfo appCompatTaskInfo = android.app.AppCompatTaskInfo.create();

    TaskInfo() {
    }

    private TaskInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public android.window.WindowContainerToken getToken() {
        return this.token;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.configuration;
    }

    public android.app.PictureInPictureParams getPictureInPictureParams() {
        return this.pictureInPictureParams;
    }

    public boolean shouldDockBigOverlays() {
        return this.shouldDockBigOverlays;
    }

    public int getWindowingMode() {
        return this.configuration.windowConfiguration.getWindowingMode();
    }

    public int getActivityType() {
        return this.configuration.windowConfiguration.getActivityType();
    }

    public void addLaunchCookie(android.os.IBinder iBinder) {
        if (iBinder == null || this.launchCookies.contains(iBinder)) {
            return;
        }
        this.launchCookies.add(iBinder);
    }

    public boolean containsLaunchCookie(android.os.IBinder iBinder) {
        return this.launchCookies.contains(iBinder);
    }

    public int getParentTaskId() {
        return this.parentTaskId;
    }

    public boolean hasParentTask() {
        return this.parentTaskId != -1;
    }

    public int getDisplayId() {
        return this.displayId;
    }

    public boolean equalsForTaskOrganizer(android.app.TaskInfo taskInfo) {
        return taskInfo != null && this.topActivityType == taskInfo.topActivityType && this.isResizeable == taskInfo.isResizeable && this.supportsMultiWindow == taskInfo.supportsMultiWindow && this.displayAreaFeatureId == taskInfo.displayAreaFeatureId && java.util.Objects.equals(this.positionInParent, taskInfo.positionInParent) && java.util.Objects.equals(this.pictureInPictureParams, taskInfo.pictureInPictureParams) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.shouldDockBigOverlays), java.lang.Boolean.valueOf(taskInfo.shouldDockBigOverlays)) && java.util.Objects.equals(this.displayCutoutInsets, taskInfo.displayCutoutInsets) && getWindowingMode() == taskInfo.getWindowingMode() && this.configuration.uiMode == taskInfo.configuration.uiMode && java.util.Objects.equals(this.taskDescription, taskInfo.taskDescription) && this.isFocused == taskInfo.isFocused && this.isVisible == taskInfo.isVisible && this.isVisibleRequested == taskInfo.isVisibleRequested && this.isSleeping == taskInfo.isSleeping && java.util.Objects.equals(this.mTopActivityLocusId, taskInfo.mTopActivityLocusId) && this.parentTaskId == taskInfo.parentTaskId && java.util.Objects.equals(this.topActivity, taskInfo.topActivity) && this.isTopActivityTransparent == taskInfo.isTopActivityTransparent && this.appCompatTaskInfo.equalsForTaskOrganizer(taskInfo.appCompatTaskInfo);
    }

    public boolean equalsForCompatUi(android.app.TaskInfo taskInfo) {
        if (taskInfo == null) {
            return false;
        }
        boolean hasCompatUI = this.appCompatTaskInfo.hasCompatUI();
        if (this.displayId != taskInfo.displayId || this.taskId != taskInfo.taskId || this.isFocused != taskInfo.isFocused || this.isTopActivityTransparent != taskInfo.isTopActivityTransparent || !this.appCompatTaskInfo.equalsForCompatUi(taskInfo.appCompatTaskInfo)) {
            return false;
        }
        if (hasCompatUI && !this.configuration.windowConfiguration.getBounds().equals(taskInfo.configuration.windowConfiguration.getBounds())) {
            return false;
        }
        if (hasCompatUI && this.configuration.getLayoutDirection() != taskInfo.configuration.getLayoutDirection()) {
            return false;
        }
        if (!hasCompatUI || this.configuration.uiMode == taskInfo.configuration.uiMode) {
            return !hasCompatUI || this.isVisible == taskInfo.isVisible;
        }
        return false;
    }

    void readFromParcel(android.os.Parcel parcel) {
        this.userId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.displayId = parcel.readInt();
        this.isRunning = parcel.readBoolean();
        this.baseIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        this.baseActivity = android.content.ComponentName.readFromParcel(parcel);
        this.topActivity = android.content.ComponentName.readFromParcel(parcel);
        this.origActivity = android.content.ComponentName.readFromParcel(parcel);
        this.realActivity = android.content.ComponentName.readFromParcel(parcel);
        this.numActivities = parcel.readInt();
        this.lastActiveTime = parcel.readLong();
        this.taskDescription = (android.app.ActivityManager.TaskDescription) parcel.readTypedObject(android.app.ActivityManager.TaskDescription.CREATOR);
        this.supportsMultiWindow = parcel.readBoolean();
        this.resizeMode = parcel.readInt();
        this.configuration.readFromParcel(parcel);
        this.token = android.window.WindowContainerToken.CREATOR.createFromParcel(parcel);
        this.topActivityType = parcel.readInt();
        this.pictureInPictureParams = (android.app.PictureInPictureParams) parcel.readTypedObject(android.app.PictureInPictureParams.CREATOR);
        this.shouldDockBigOverlays = parcel.readBoolean();
        this.launchIntoPipHostTaskId = parcel.readInt();
        this.lastParentTaskIdBeforePip = parcel.readInt();
        this.displayCutoutInsets = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.topActivityInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
        this.isResizeable = parcel.readBoolean();
        this.minWidth = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.defaultMinSize = parcel.readInt();
        parcel.readBinderList(this.launchCookies);
        this.positionInParent = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
        this.parentTaskId = parcel.readInt();
        this.isFocused = parcel.readBoolean();
        this.isVisible = parcel.readBoolean();
        this.isVisibleRequested = parcel.readBoolean();
        this.isSleeping = parcel.readBoolean();
        this.mTopActivityLocusId = (android.content.LocusId) parcel.readTypedObject(android.content.LocusId.CREATOR);
        this.displayAreaFeatureId = parcel.readInt();
        this.isTopActivityTransparent = parcel.readBoolean();
        this.appCompatTaskInfo = (android.app.AppCompatTaskInfo) parcel.readTypedObject(android.app.AppCompatTaskInfo.CREATOR);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.userId);
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.displayId);
        parcel.writeBoolean(this.isRunning);
        parcel.writeTypedObject(this.baseIntent, 0);
        android.content.ComponentName.writeToParcel(this.baseActivity, parcel);
        android.content.ComponentName.writeToParcel(this.topActivity, parcel);
        android.content.ComponentName.writeToParcel(this.origActivity, parcel);
        android.content.ComponentName.writeToParcel(this.realActivity, parcel);
        parcel.writeInt(this.numActivities);
        parcel.writeLong(this.lastActiveTime);
        parcel.writeTypedObject(this.taskDescription, i);
        parcel.writeBoolean(this.supportsMultiWindow);
        parcel.writeInt(this.resizeMode);
        this.configuration.writeToParcel(parcel, i);
        this.token.writeToParcel(parcel, i);
        parcel.writeInt(this.topActivityType);
        parcel.writeTypedObject(this.pictureInPictureParams, i);
        parcel.writeBoolean(this.shouldDockBigOverlays);
        parcel.writeInt(this.launchIntoPipHostTaskId);
        parcel.writeInt(this.lastParentTaskIdBeforePip);
        parcel.writeTypedObject(this.displayCutoutInsets, i);
        parcel.writeTypedObject(this.topActivityInfo, i);
        parcel.writeBoolean(this.isResizeable);
        parcel.writeInt(this.minWidth);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.defaultMinSize);
        parcel.writeBinderList(this.launchCookies);
        parcel.writeTypedObject(this.positionInParent, i);
        parcel.writeInt(this.parentTaskId);
        parcel.writeBoolean(this.isFocused);
        parcel.writeBoolean(this.isVisible);
        parcel.writeBoolean(this.isVisibleRequested);
        parcel.writeBoolean(this.isSleeping);
        parcel.writeTypedObject(this.mTopActivityLocusId, i);
        parcel.writeInt(this.displayAreaFeatureId);
        parcel.writeBoolean(this.isTopActivityTransparent);
        parcel.writeTypedObject(this.appCompatTaskInfo, i);
    }

    public java.lang.String toString() {
        return "TaskInfo{userId=" + this.userId + " taskId=" + this.taskId + " displayId=" + this.displayId + " isRunning=" + this.isRunning + " baseIntent=" + this.baseIntent + " baseActivity=" + this.baseActivity + " topActivity=" + this.topActivity + " origActivity=" + this.origActivity + " realActivity=" + this.realActivity + " numActivities=" + this.numActivities + " lastActiveTime=" + this.lastActiveTime + " supportsMultiWindow=" + this.supportsMultiWindow + " resizeMode=" + this.resizeMode + " isResizeable=" + this.isResizeable + " minWidth=" + this.minWidth + " minHeight=" + this.minHeight + " defaultMinSize=" + this.defaultMinSize + " token=" + this.token + " topActivityType=" + this.topActivityType + " pictureInPictureParams=" + this.pictureInPictureParams + " shouldDockBigOverlays=" + this.shouldDockBigOverlays + " launchIntoPipHostTaskId=" + this.launchIntoPipHostTaskId + " lastParentTaskIdBeforePip=" + this.lastParentTaskIdBeforePip + " displayCutoutSafeInsets=" + this.displayCutoutInsets + " topActivityInfo=" + this.topActivityInfo + " launchCookies=" + this.launchCookies + " positionInParent=" + this.positionInParent + " parentTaskId=" + this.parentTaskId + " isFocused=" + this.isFocused + " isVisible=" + this.isVisible + " isVisibleRequested=" + this.isVisibleRequested + " isSleeping=" + this.isSleeping + " locusId=" + this.mTopActivityLocusId + " displayAreaFeatureId=" + this.displayAreaFeatureId + " isTopActivityTransparent=" + this.isTopActivityTransparent + " appCompatTaskInfo=" + this.appCompatTaskInfo + "}";
    }
}
