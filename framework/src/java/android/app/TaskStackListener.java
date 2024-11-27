package android.app;

/* loaded from: classes.dex */
public abstract class TaskStackListener extends android.app.ITaskStackListener.Stub {
    private boolean mIsRemote = true;

    public void setIsLocal() {
        this.mIsRemote = false;
    }

    @Override // android.app.ITaskStackListener
    public void onTaskStackChanged() throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityPinned(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityUnpinned() throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityRestartAttempt(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityForcedResizable(java.lang.String str, int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityDismissingDockedTask() throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityLaunchOnSecondaryDisplayFailed(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
        onActivityLaunchOnSecondaryDisplayFailed();
    }

    @java.lang.Deprecated
    public void onActivityLaunchOnSecondaryDisplayFailed() throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityLaunchOnSecondaryDisplayRerouted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskCreated(int i, android.content.ComponentName componentName) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskRemoved(int i) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskMovedToFront(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        onTaskMovedToFront(runningTaskInfo.taskId);
    }

    @java.lang.Deprecated
    public void onTaskMovedToFront(int i) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskRemovalStarted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        onTaskRemovalStarted(runningTaskInfo.taskId);
    }

    @java.lang.Deprecated
    public void onTaskRemovalStarted(int i) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskDescriptionChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        onTaskDescriptionChanged(runningTaskInfo.taskId, runningTaskInfo.taskDescription);
    }

    @java.lang.Deprecated
    public void onTaskDescriptionChanged(int i, android.app.ActivityManager.TaskDescription taskDescription) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
        onTaskProfileLocked(runningTaskInfo);
    }

    @java.lang.Deprecated
    public void onTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskSnapshotChanged(int i, android.window.TaskSnapshot taskSnapshot) throws android.os.RemoteException {
        if (this.mIsRemote && taskSnapshot != null && taskSnapshot.getHardwareBuffer() != null) {
            taskSnapshot.getHardwareBuffer().close();
        }
    }

    @Override // android.app.ITaskStackListener
    public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskDisplayChanged(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskListUpdated() throws android.os.RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskListFrozenChanged(boolean z) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskFocusChanged(int i, boolean z) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskRequestedOrientationChanged(int i, int i2) {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityRotation(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskMovedToBack(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    @Override // android.app.ITaskStackListener
    public void onLockTaskModeChanged(int i) {
    }
}
