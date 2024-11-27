package com.android.server.app;

/* loaded from: classes.dex */
final class GameTaskInfo {
    final android.content.ComponentName mComponentName;
    final boolean mIsGameTask;
    final int mTaskId;

    GameTaskInfo(int i, boolean z, android.content.ComponentName componentName) {
        this.mTaskId = i;
        this.mIsGameTask = z;
        this.mComponentName = componentName;
    }

    public java.lang.String toString() {
        return "GameTaskInfo{mTaskId=" + this.mTaskId + ", mIsGameTask=" + this.mIsGameTask + ", mComponentName=" + this.mComponentName + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.app.GameTaskInfo)) {
            return false;
        }
        com.android.server.app.GameTaskInfo gameTaskInfo = (com.android.server.app.GameTaskInfo) obj;
        return this.mTaskId == gameTaskInfo.mTaskId && this.mIsGameTask == gameTaskInfo.mIsGameTask && this.mComponentName.equals(gameTaskInfo.mComponentName);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTaskId), java.lang.Boolean.valueOf(this.mIsGameTask), this.mComponentName);
    }
}
