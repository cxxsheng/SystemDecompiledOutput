package android.app;

/* loaded from: classes.dex */
public class ActivityTaskManager {
    public static final int DEFAULT_MINIMAL_SPLIT_SCREEN_DISPLAY_SIZE_DP = 440;
    public static final java.lang.String EXTRA_IGNORE_TARGET_SECURITY = "android.app.extra.EXTRA_IGNORE_TARGET_SECURITY";
    public static final java.lang.String EXTRA_OPTIONS = "android.app.extra.OPTIONS";
    public static final int INVALID_STACK_ID = -1;
    public static final int INVALID_TASK_ID = -1;
    public static final int INVALID_WINDOWING_MODE = -1;
    public static final int RESIZE_MODE_FORCED = 2;
    public static final int RESIZE_MODE_PRESERVE_WINDOW = 1;
    public static final int RESIZE_MODE_SYSTEM = 0;
    public static final int RESIZE_MODE_SYSTEM_SCREEN_ROTATION = 1;
    public static final int RESIZE_MODE_USER = 1;
    public static final int RESIZE_MODE_USER_FORCED = 3;
    private static int sMaxRecentTasks = -1;
    private static final android.util.Singleton<android.app.ActivityTaskManager> sInstance = new android.util.Singleton<android.app.ActivityTaskManager>() { // from class: android.app.ActivityTaskManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.ActivityTaskManager create() {
            return new android.app.ActivityTaskManager();
        }
    };
    private static final android.util.Singleton<android.app.IActivityTaskManager> IActivityTaskManagerSingleton = new android.util.Singleton<android.app.IActivityTaskManager>() { // from class: android.app.ActivityTaskManager.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.IActivityTaskManager create() {
            return android.app.IActivityTaskManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.ACTIVITY_TASK_SERVICE));
        }
    };

    private ActivityTaskManager() {
    }

    public static android.app.ActivityTaskManager getInstance() {
        return sInstance.get();
    }

    public static android.app.IActivityTaskManager getService() {
        return IActivityTaskManagerSingleton.get();
    }

    public void removeRootTasksInWindowingModes(int[] iArr) {
        try {
            getService().removeRootTasksInWindowingModes(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeRootTasksWithActivityTypes(int[] iArr) {
        try {
            getService().removeRootTasksWithActivityTypes(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllVisibleRecentTasks() {
        try {
            getService().removeAllVisibleRecentTasks();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getMaxRecentTasksStatic() {
        if (sMaxRecentTasks < 0) {
            int i = android.app.ActivityManager.isLowRamDeviceStatic() ? 36 : 48;
            sMaxRecentTasks = i;
            return i;
        }
        return sMaxRecentTasks;
    }

    public void onSplashScreenViewCopyFinished(int i, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
        try {
            getService().onSplashScreenViewCopyFinished(i, splashScreenViewParcelable);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getDefaultAppRecentsLimitStatic() {
        return getMaxRecentTasksStatic() / 6;
    }

    public static int getMaxAppRecentsLimitStatic() {
        return getMaxRecentTasksStatic() / 2;
    }

    public static boolean supportsMultiWindow(android.content.Context context) {
        return (!android.app.ActivityManager.isLowRamDeviceStatic() || context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) && android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_supportsMultiWindow);
    }

    public static boolean supportsSplitScreenMultiWindow(android.content.Context context) {
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        context.getDisplay().getRealMetrics(displayMetrics);
        return java.lang.Math.max((int) (((float) displayMetrics.widthPixels) / displayMetrics.density), (int) (((float) displayMetrics.heightPixels) / displayMetrics.density)) >= 440 && supportsMultiWindow(context) && android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_supportsSplitScreenMultiWindow);
    }

    public void startSystemLockTaskMode(int i) {
        try {
            getService().startSystemLockTaskMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopSystemLockTaskMode() {
        try {
            getService().stopSystemLockTaskMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void moveTaskToRootTask(int i, int i2, boolean z) {
        try {
            getService().moveTaskToRootTask(i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resizeTask(int i, android.graphics.Rect rect) {
        try {
            getService().resizeTask(i, rect, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearLaunchParamsForPackages(java.util.List<java.lang.String> list) {
        try {
            getService().clearLaunchParamsForPackages(list);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static boolean currentUiModeSupportsErrorDialogs(android.content.res.Configuration configuration) {
        int i = configuration.uiMode & 15;
        return (i == 3 || (i == 6 && android.os.Build.IS_USER) || i == 4 || i == 7) ? false : true;
    }

    public static boolean currentUiModeSupportsErrorDialogs(android.content.Context context) {
        return currentUiModeSupportsErrorDialogs(context.getResources().getConfiguration());
    }

    public static int getMaxNumPictureInPictureActions(android.content.Context context) {
        return context.getResources().getInteger(com.android.internal.R.integer.config_pictureInPictureMaxNumberOfActions);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i) {
        return getTasks(i, false, false, -1);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z) {
        return getTasks(i, z, false, -1);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2) {
        return getTasks(i, z, z2, -1);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) {
        try {
            return getService().getTasks(i, z, z2, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) {
        try {
            return getService().getRecentTasks(i, i2, i3).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTaskStackListener(android.app.TaskStackListener taskStackListener) {
        try {
            getService().registerTaskStackListener(taskStackListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTaskStackListener(android.app.TaskStackListener taskStackListener) {
        try {
            getService().unregisterTaskStackListener(taskStackListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.Rect getTaskBounds(int i) {
        try {
            return getService().getTaskBounds(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerRemoteAnimationsForDisplay(int i, android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getService().registerRemoteAnimationsForDisplay(i, remoteAnimationDefinition);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInLockTaskMode() {
        try {
            return getService().isInLockTaskMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeTask(int i) {
        try {
            return getService().removeTask(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void detachNavigationBarFromApp(android.os.IBinder iBinder) {
        try {
            getService().detachNavigationBarFromApp(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateLockTaskPackages(android.content.Context context, java.lang.String[] strArr) {
        try {
            getService().updateLockTaskPackages(context.getUserId(), strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class RootTaskInfo extends android.app.TaskInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityTaskManager.RootTaskInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityTaskManager.RootTaskInfo>() { // from class: android.app.ActivityTaskManager.RootTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityTaskManager.RootTaskInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityTaskManager.RootTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityTaskManager.RootTaskInfo[] newArray(int i) {
                return new android.app.ActivityTaskManager.RootTaskInfo[i];
            }
        };
        public android.graphics.Rect bounds;
        public android.graphics.Rect[] childTaskBounds;
        public int[] childTaskIds;
        public java.lang.String[] childTaskNames;
        public int[] childTaskUserIds;
        public int position;
        public boolean visible;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.app.TaskInfo, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.bounds, i);
            parcel.writeIntArray(this.childTaskIds);
            parcel.writeStringArray(this.childTaskNames);
            parcel.writeTypedArray(this.childTaskBounds, i);
            parcel.writeIntArray(this.childTaskUserIds);
            parcel.writeInt(this.visible ? 1 : 0);
            parcel.writeInt(this.position);
            super.writeToParcel(parcel, i);
        }

        @Override // android.app.TaskInfo
        void readFromParcel(android.os.Parcel parcel) {
            this.bounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
            this.childTaskIds = parcel.createIntArray();
            this.childTaskNames = parcel.createStringArray();
            this.childTaskBounds = (android.graphics.Rect[]) parcel.createTypedArray(android.graphics.Rect.CREATOR);
            this.childTaskUserIds = parcel.createIntArray();
            this.visible = parcel.readInt() > 0;
            this.position = parcel.readInt();
            super.readFromParcel(parcel);
        }

        public RootTaskInfo() {
            this.bounds = new android.graphics.Rect();
        }

        private RootTaskInfo(android.os.Parcel parcel) {
            this.bounds = new android.graphics.Rect();
            readFromParcel(parcel);
        }

        @Override // android.app.TaskInfo
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
            sb.append("RootTask id=");
            sb.append(this.taskId);
            sb.append(" bounds=");
            sb.append(this.bounds.toShortString());
            sb.append(" displayId=");
            sb.append(this.displayId);
            sb.append(" userId=");
            sb.append(this.userId);
            sb.append("\n");
            sb.append(" configuration=");
            sb.append(this.configuration);
            sb.append("\n");
            for (int i = 0; i < this.childTaskIds.length; i++) {
                sb.append("  taskId=");
                sb.append(this.childTaskIds[i]);
                sb.append(": ");
                sb.append(this.childTaskNames[i]);
                if (this.childTaskBounds != null) {
                    sb.append(" bounds=");
                    sb.append(this.childTaskBounds[i].toShortString());
                }
                sb.append(" userId=").append(this.childTaskUserIds[i]);
                sb.append(" visible=").append(this.visible);
                if (this.topActivity != null) {
                    sb.append(" topActivity=").append(this.topActivity);
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
