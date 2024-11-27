package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class LocalActivityManager {
    static final int CREATED = 2;
    static final int DESTROYED = 5;
    static final int INITIALIZING = 1;
    static final int RESTORED = 0;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    private static final java.lang.String TAG = "LocalActivityManager";
    private static final boolean localLOGV = false;
    private boolean mFinishing;
    private final android.app.Activity mParent;
    private android.app.LocalActivityManager.LocalActivityRecord mResumed;
    private boolean mSingleMode;
    private final java.util.Map<java.lang.String, android.app.LocalActivityManager.LocalActivityRecord> mActivities = new java.util.HashMap();
    private final java.util.ArrayList<android.app.LocalActivityManager.LocalActivityRecord> mActivityArray = new java.util.ArrayList<>();
    private int mCurState = 1;
    private final android.app.ActivityThread mActivityThread = android.app.ActivityThread.currentActivityThread();

    private static class LocalActivityRecord extends android.os.Binder {
        android.app.Activity activity;
        android.content.pm.ActivityInfo activityInfo;
        int curState = 0;
        final java.lang.String id;
        android.os.Bundle instanceState;
        android.content.Intent intent;
        android.view.Window window;

        LocalActivityRecord(java.lang.String str, android.content.Intent intent) {
            this.id = str;
            this.intent = intent;
        }
    }

    public LocalActivityManager(android.app.Activity activity, boolean z) {
        this.mParent = activity;
        this.mSingleMode = z;
    }

    private void moveToState(android.app.LocalActivityManager.LocalActivityRecord localActivityRecord, int i) {
        java.lang.Object obj;
        android.app.Activity.NonConfigurationInstances nonConfigurationInstances;
        android.app.servertransaction.PendingTransactionActions pendingTransactionActions;
        if (localActivityRecord.curState == 0 || localActivityRecord.curState == 5) {
            return;
        }
        if (localActivityRecord.curState != 1) {
            android.app.ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(localActivityRecord);
            if (activityClient == null) {
                android.util.Log.w(TAG, "Can't get activity record for " + localActivityRecord.id);
                return;
            }
            switch (localActivityRecord.curState) {
                case 2:
                    if (i == 3) {
                        this.mActivityThread.performRestartActivity(activityClient, true);
                        localActivityRecord.curState = 3;
                    }
                    if (i == 4) {
                        this.mActivityThread.performRestartActivity(activityClient, true);
                        this.mActivityThread.performResumeActivity(activityClient, true, "moveToState-CREATED");
                        localActivityRecord.curState = 4;
                        break;
                    }
                    break;
                case 3:
                    if (i == 4) {
                        this.mActivityThread.performResumeActivity(activityClient, true, "moveToState-STARTED");
                        localActivityRecord.instanceState = null;
                        localActivityRecord.curState = 4;
                    }
                    if (i == 2) {
                        this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-STARTED");
                        localActivityRecord.curState = 2;
                        break;
                    }
                    break;
                case 4:
                    if (i == 3) {
                        performPause(localActivityRecord, this.mFinishing);
                        localActivityRecord.curState = 3;
                    }
                    if (i == 2) {
                        performPause(localActivityRecord, this.mFinishing);
                        this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-RESUMED");
                        localActivityRecord.curState = 2;
                        break;
                    }
                    break;
            }
            return;
        }
        java.util.HashMap<java.lang.String, java.lang.Object> lastNonConfigurationChildInstances = this.mParent.getLastNonConfigurationChildInstances();
        if (lastNonConfigurationChildInstances == null) {
            obj = null;
        } else {
            obj = lastNonConfigurationChildInstances.get(localActivityRecord.id);
        }
        if (obj == null) {
            nonConfigurationInstances = null;
        } else {
            android.app.Activity.NonConfigurationInstances nonConfigurationInstances2 = new android.app.Activity.NonConfigurationInstances();
            nonConfigurationInstances2.activity = obj;
            nonConfigurationInstances = nonConfigurationInstances2;
        }
        if (localActivityRecord.activityInfo == null) {
            localActivityRecord.activityInfo = this.mActivityThread.resolveActivityInfo(localActivityRecord.intent);
        }
        localActivityRecord.activity = this.mActivityThread.startActivityNow(this.mParent, localActivityRecord.id, localActivityRecord.intent, localActivityRecord.activityInfo, localActivityRecord, localActivityRecord.instanceState, nonConfigurationInstances, localActivityRecord, localActivityRecord);
        if (localActivityRecord.activity == null) {
            return;
        }
        localActivityRecord.window = localActivityRecord.activity.getWindow();
        localActivityRecord.instanceState = null;
        android.app.ActivityThread.ActivityClientRecord activityClient2 = this.mActivityThread.getActivityClient(localActivityRecord);
        if (!localActivityRecord.activity.mFinished) {
            pendingTransactionActions = new android.app.servertransaction.PendingTransactionActions();
            pendingTransactionActions.setOldState(activityClient2.state);
            pendingTransactionActions.setRestoreInstanceState(true);
            pendingTransactionActions.setCallOnPostCreate(true);
        } else {
            pendingTransactionActions = null;
        }
        this.mActivityThread.handleStartActivity(activityClient2, pendingTransactionActions, null);
        localActivityRecord.curState = 3;
        if (i == 4) {
            this.mActivityThread.performResumeActivity(activityClient2, true, "moveToState-INITIALIZING");
            localActivityRecord.curState = 4;
        }
    }

    private void performPause(android.app.LocalActivityManager.LocalActivityRecord localActivityRecord, boolean z) {
        boolean z2 = localActivityRecord.instanceState == null;
        android.os.Bundle performPauseActivity = this.mActivityThread.performPauseActivity(localActivityRecord, z, "performPause", (android.app.servertransaction.PendingTransactionActions) null);
        if (z2) {
            localActivityRecord.instanceState = performPauseActivity;
        }
    }

    public android.view.Window startActivity(java.lang.String str, android.content.Intent intent) {
        boolean z;
        android.app.LocalActivityManager.LocalActivityRecord localActivityRecord;
        if (this.mCurState == 1) {
            throw new java.lang.IllegalStateException("Activities can't be added until the containing group has been created.");
        }
        android.app.LocalActivityManager.LocalActivityRecord localActivityRecord2 = this.mActivities.get(str);
        boolean z2 = false;
        android.content.pm.ActivityInfo activityInfo = null;
        if (localActivityRecord2 == null) {
            localActivityRecord2 = new android.app.LocalActivityManager.LocalActivityRecord(str, intent);
            z = false;
            z2 = true;
        } else if (localActivityRecord2.intent == null) {
            z = false;
        } else {
            z = localActivityRecord2.intent.filterEquals(intent);
            if (z) {
                activityInfo = localActivityRecord2.activityInfo;
            }
        }
        if (activityInfo == null) {
            activityInfo = this.mActivityThread.resolveActivityInfo(intent);
        }
        if (this.mSingleMode && (localActivityRecord = this.mResumed) != null && localActivityRecord != localActivityRecord2 && this.mCurState == 4) {
            moveToState(localActivityRecord, 3);
        }
        if (z2) {
            this.mActivities.put(str, localActivityRecord2);
            this.mActivityArray.add(localActivityRecord2);
        } else if (localActivityRecord2.activityInfo != null) {
            if (activityInfo == localActivityRecord2.activityInfo || (activityInfo.name.equals(localActivityRecord2.activityInfo.name) && activityInfo.packageName.equals(localActivityRecord2.activityInfo.packageName))) {
                if (activityInfo.launchMode != 0 || (intent.getFlags() & 536870912) != 0) {
                    java.util.ArrayList arrayList = new java.util.ArrayList(1);
                    arrayList.add(new com.android.internal.content.ReferrerIntent(intent, this.mParent.getPackageName()));
                    this.mActivityThread.handleNewIntent(this.mActivityThread.getActivityClient(localActivityRecord2), arrayList);
                    localActivityRecord2.intent = intent;
                    moveToState(localActivityRecord2, this.mCurState);
                    if (this.mSingleMode) {
                        this.mResumed = localActivityRecord2;
                    }
                    return localActivityRecord2.window;
                }
                if (z && (intent.getFlags() & 67108864) == 0) {
                    localActivityRecord2.intent = intent;
                    moveToState(localActivityRecord2, this.mCurState);
                    if (this.mSingleMode) {
                        this.mResumed = localActivityRecord2;
                    }
                    return localActivityRecord2.window;
                }
            }
            performDestroy(localActivityRecord2, true);
        }
        localActivityRecord2.intent = intent;
        localActivityRecord2.curState = 1;
        localActivityRecord2.activityInfo = activityInfo;
        moveToState(localActivityRecord2, this.mCurState);
        if (this.mSingleMode) {
            this.mResumed = localActivityRecord2;
        }
        return localActivityRecord2.window;
    }

    private android.view.Window performDestroy(android.app.LocalActivityManager.LocalActivityRecord localActivityRecord, boolean z) {
        android.view.Window window = localActivityRecord.window;
        if (localActivityRecord.curState == 4 && !z) {
            performPause(localActivityRecord, z);
        }
        android.app.ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(localActivityRecord);
        if (activityClient != null) {
            this.mActivityThread.performDestroyActivity(activityClient, z, 0, false, "LocalActivityManager::performDestroy");
        }
        localActivityRecord.activity = null;
        localActivityRecord.window = null;
        if (z) {
            localActivityRecord.instanceState = null;
        }
        localActivityRecord.curState = 5;
        return window;
    }

    public android.view.Window destroyActivity(java.lang.String str, boolean z) {
        android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivities.get(str);
        if (localActivityRecord == null) {
            return null;
        }
        android.view.Window performDestroy = performDestroy(localActivityRecord, z);
        if (z) {
            this.mActivities.remove(str);
            this.mActivityArray.remove(localActivityRecord);
            return performDestroy;
        }
        return performDestroy;
    }

    public android.app.Activity getCurrentActivity() {
        if (this.mResumed != null) {
            return this.mResumed.activity;
        }
        return null;
    }

    public java.lang.String getCurrentId() {
        if (this.mResumed != null) {
            return this.mResumed.id;
        }
        return null;
    }

    public android.app.Activity getActivity(java.lang.String str) {
        android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivities.get(str);
        if (localActivityRecord != null) {
            return localActivityRecord.activity;
        }
        return null;
    }

    public void dispatchCreate(android.os.Bundle bundle) {
        if (bundle != null) {
            for (java.lang.String str : bundle.keySet()) {
                try {
                    android.os.Bundle bundle2 = bundle.getBundle(str);
                    android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivities.get(str);
                    if (localActivityRecord != null) {
                        localActivityRecord.instanceState = bundle2;
                    } else {
                        android.app.LocalActivityManager.LocalActivityRecord localActivityRecord2 = new android.app.LocalActivityManager.LocalActivityRecord(str, null);
                        localActivityRecord2.instanceState = bundle2;
                        this.mActivities.put(str, localActivityRecord2);
                        this.mActivityArray.add(localActivityRecord2);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Exception thrown when restoring LocalActivityManager state", e);
                }
            }
        }
        this.mCurState = 2;
    }

    public android.os.Bundle saveInstanceState() {
        int size = this.mActivityArray.size();
        android.os.Bundle bundle = null;
        for (int i = 0; i < size; i++) {
            android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            if ((localActivityRecord.instanceState != null || localActivityRecord.curState == 4) && localActivityRecord.activity != null) {
                android.os.Bundle bundle2 = new android.os.Bundle();
                localActivityRecord.activity.performSaveInstanceState(bundle2);
                localActivityRecord.instanceState = bundle2;
            }
            if (localActivityRecord.instanceState != null) {
                bundle.putBundle(localActivityRecord.id, localActivityRecord.instanceState);
            }
        }
        return bundle;
    }

    public void dispatchResume() {
        this.mCurState = 4;
        if (this.mSingleMode) {
            if (this.mResumed != null) {
                moveToState(this.mResumed, 4);
            }
        } else {
            int size = this.mActivityArray.size();
            for (int i = 0; i < size; i++) {
                moveToState(this.mActivityArray.get(i), 4);
            }
        }
    }

    public void dispatchPause(boolean z) {
        if (z) {
            this.mFinishing = true;
        }
        this.mCurState = 3;
        if (this.mSingleMode) {
            if (this.mResumed != null) {
                moveToState(this.mResumed, 3);
                return;
            }
            return;
        }
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            if (localActivityRecord.curState == 4) {
                moveToState(localActivityRecord, 3);
            }
        }
    }

    public void dispatchStop() {
        this.mCurState = 2;
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            moveToState(this.mActivityArray.get(i), 2);
        }
    }

    public java.util.HashMap<java.lang.String, java.lang.Object> dispatchRetainNonConfigurationInstance() {
        java.lang.Object onRetainNonConfigurationInstance;
        int size = this.mActivityArray.size();
        java.util.HashMap<java.lang.String, java.lang.Object> hashMap = null;
        for (int i = 0; i < size; i++) {
            android.app.LocalActivityManager.LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            if (localActivityRecord != null && localActivityRecord.activity != null && (onRetainNonConfigurationInstance = localActivityRecord.activity.onRetainNonConfigurationInstance()) != null) {
                if (hashMap == null) {
                    hashMap = new java.util.HashMap<>();
                }
                hashMap.put(localActivityRecord.id, onRetainNonConfigurationInstance);
            }
        }
        return hashMap;
    }

    public void removeAllActivities() {
        dispatchDestroy(true);
    }

    public void dispatchDestroy(boolean z) {
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            android.app.ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(this.mActivityArray.get(i));
            if (activityClient != null) {
                this.mActivityThread.performDestroyActivity(activityClient, z, 0, false, "LocalActivityManager::dispatchDestroy");
            }
        }
        this.mActivities.clear();
        this.mActivityArray.clear();
    }
}
