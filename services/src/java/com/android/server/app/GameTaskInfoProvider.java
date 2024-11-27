package com.android.server.app;

/* loaded from: classes.dex */
final class GameTaskInfoProvider {
    private static final java.lang.String TAG = "GameTaskInfoProvider";
    private static final int TASK_INFO_CACHE_MAX_SIZE = 50;
    private final android.app.IActivityTaskManager mActivityTaskManager;
    private final com.android.server.app.GameClassifier mGameClassifier;
    private final android.os.UserHandle mUserHandle;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.LruCache<java.lang.Integer, com.android.server.app.GameTaskInfo> mGameTaskInfoCache = new android.util.LruCache<>(50);

    GameTaskInfoProvider(@android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull android.app.IActivityTaskManager iActivityTaskManager, @android.annotation.NonNull com.android.server.app.GameClassifier gameClassifier) {
        this.mUserHandle = userHandle;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mGameClassifier = gameClassifier;
    }

    @android.annotation.Nullable
    com.android.server.app.GameTaskInfo get(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.app.GameTaskInfo gameTaskInfo = this.mGameTaskInfoCache.get(java.lang.Integer.valueOf(i));
                if (gameTaskInfo != null) {
                    return gameTaskInfo;
                }
                android.app.ActivityManager.RunningTaskInfo runningTaskInfo = getRunningTaskInfo(i);
                if (runningTaskInfo == null || runningTaskInfo.baseActivity == null) {
                    return null;
                }
                return generateGameInfo(i, runningTaskInfo.baseActivity);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    com.android.server.app.GameTaskInfo get(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                com.android.server.app.GameTaskInfo gameTaskInfo = this.mGameTaskInfoCache.get(java.lang.Integer.valueOf(i));
                if (gameTaskInfo != null) {
                    if (!gameTaskInfo.mComponentName.equals(componentName)) {
                        return gameTaskInfo;
                    }
                    android.util.Slog.w(TAG, "Found cached task info for taskId " + i + " but cached component name " + gameTaskInfo.mComponentName + " does not match " + componentName);
                }
                return generateGameInfo(i, componentName);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.app.ActivityManager.RunningTaskInfo getRunningTaskInfo(int i) {
        try {
            for (android.app.ActivityManager.RunningTaskInfo runningTaskInfo : this.mActivityTaskManager.getTasks(Integer.MAX_VALUE, false, false, -1)) {
                if (runningTaskInfo.taskId == i) {
                    return runningTaskInfo;
                }
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to fetch running tasks");
            return null;
        }
    }

    private com.android.server.app.GameTaskInfo generateGameInfo(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.server.app.GameTaskInfo gameTaskInfo = new com.android.server.app.GameTaskInfo(i, this.mGameClassifier.isGame(componentName.getPackageName(), this.mUserHandle), componentName);
        synchronized (this.mLock) {
            this.mGameTaskInfoCache.put(java.lang.Integer.valueOf(i), gameTaskInfo);
        }
        return gameTaskInfo;
    }
}
