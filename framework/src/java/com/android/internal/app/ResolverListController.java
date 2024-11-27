package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverListController {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ResolverListController";
    private boolean isComputed;
    private final android.content.Context mContext;
    private final int mLaunchedFromUid;
    private final android.os.UserHandle mQueryIntentsAsUser;
    private final java.lang.String mReferrerPackage;
    private com.android.internal.app.AbstractResolverComparator mResolverComparator;
    private final android.content.Intent mTargetIntent;
    private final android.os.UserHandle mUserHandle;
    private final android.content.pm.PackageManager mpm;

    public ResolverListController(android.content.Context context, android.content.pm.PackageManager packageManager, android.content.Intent intent, java.lang.String str, int i, android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        this(context, packageManager, intent, str, i, userHandle, new com.android.internal.app.ResolverRankerServiceResolverComparator(context, intent, str, (com.android.internal.app.AbstractResolverComparator.AfterCompute) null, (com.android.internal.app.ChooserActivityLogger) null, userHandle), userHandle2);
    }

    public ResolverListController(android.content.Context context, android.content.pm.PackageManager packageManager, android.content.Intent intent, java.lang.String str, int i, android.os.UserHandle userHandle, com.android.internal.app.AbstractResolverComparator abstractResolverComparator, android.os.UserHandle userHandle2) {
        this.isComputed = false;
        this.mContext = context;
        this.mpm = packageManager;
        this.mLaunchedFromUid = i;
        this.mTargetIntent = intent;
        this.mReferrerPackage = str;
        this.mUserHandle = userHandle;
        this.mResolverComparator = abstractResolverComparator;
        this.mQueryIntentsAsUser = userHandle2;
    }

    public android.content.pm.ResolveInfo getLastChosen() throws android.os.RemoteException {
        return android.app.AppGlobals.getPackageManager().getLastChosenActivity(this.mTargetIntent, this.mTargetIntent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 65536);
    }

    public void setLastChosen(android.content.Intent intent, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
        android.app.AppGlobals.getPackageManager().setLastChosenActivity(intent, intent.resolveType(this.mContext.getContentResolver()), 65536, intentFilter, i, intent.getComponent());
    }

    public java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getResolversForIntent(boolean z, boolean z2, boolean z3, java.util.List<android.content.Intent> list) {
        return getResolversForIntentAsUser(z, z2, z3, list, this.mQueryIntentsAsUser);
    }

    public java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getResolversForIntentAsUser(boolean z, boolean z2, boolean z3, java.util.List<android.content.Intent> list, android.os.UserHandle userHandle) {
        return getResolversForIntentAsUserInternal(list, userHandle, (z ? 64 : 0) | (z3 ? 65536 : 0) | 524288 | 262144 | (z2 ? 128 : 0) | 536870912);
    }

    private java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getResolversForIntentAsUserInternal(java.util.List<android.content.Intent> list, android.os.UserHandle userHandle, int i) {
        int size = list.size();
        java.util.ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            android.content.Intent intent = list.get(i2);
            int i3 = (intent.isWebIntent() || (intent.getFlags() & 2048) != 0) ? 8388608 | i : i;
            if (intent.getClass() != android.content.Intent.class) {
                intent = new android.content.Intent(intent);
            }
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = this.mpm.queryIntentActivitiesAsUser(intent, i3, userHandle);
            if (queryIntentActivitiesAsUser != null) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                addResolveListDedupe(arrayList, intent, queryIntentActivitiesAsUser);
            }
        }
        return arrayList;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public void addResolveListDedupe(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, android.content.Intent intent, java.util.List<android.content.pm.ResolveInfo> list2) {
        boolean z;
        int size = list2.size();
        int size2 = list.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = list2.get(i);
            if (resolveInfo.userHandle == null) {
                android.util.Log.w(TAG, "Skipping ResolveInfo with no userHandle: " + resolveInfo);
            } else {
                int i2 = 0;
                while (true) {
                    if (i2 >= size2) {
                        z = false;
                        break;
                    }
                    com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = list.get(i2);
                    if (!isSameResolvedComponent(resolveInfo, resolvedComponentInfo)) {
                        i2++;
                    } else {
                        resolvedComponentInfo.add(intent, resolveInfo);
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    android.content.ComponentName componentName = new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2 = new com.android.internal.app.ResolverActivity.ResolvedComponentInfo(componentName, intent, resolveInfo);
                    resolvedComponentInfo2.setPinned(isComponentPinned(componentName));
                    resolvedComponentInfo2.setFixedAtTop(isFixedAtTop(componentName));
                    list.add(resolvedComponentInfo2);
                }
            }
        }
    }

    public boolean isComponentPinned(android.content.ComponentName componentName) {
        return false;
    }

    public boolean isFixedAtTop(android.content.ComponentName componentName) {
        return false;
    }

    public java.util.ArrayList<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> filterIneligibleActivities(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        java.util.ArrayList<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> arrayList = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ActivityInfo activityInfo = list.get(size).getResolveInfoAt(0).activityInfo;
            if (android.app.ActivityManager.checkComponentPermission(activityInfo.permission, this.mLaunchedFromUid, activityInfo.applicationInfo.uid, activityInfo.exported) != 0 || isComponentFiltered(activityInfo.getComponentName())) {
                if (z && arrayList == null) {
                    arrayList = new java.util.ArrayList<>(list);
                }
                list.remove(size);
            }
        }
        return arrayList;
    }

    public java.util.ArrayList<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> filterLowPriority(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        android.content.pm.ResolveInfo resolveInfoAt = list.get(0).getResolveInfoAt(0);
        int size = list.size();
        java.util.ArrayList<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> arrayList = null;
        for (int i = 1; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfoAt2 = list.get(i).getResolveInfoAt(0);
            if (resolveInfoAt.priority != resolveInfoAt2.priority || resolveInfoAt.isDefault != resolveInfoAt2.isDefault) {
                while (i < size) {
                    if (z && arrayList == null) {
                        arrayList = new java.util.ArrayList<>(list);
                    }
                    list.remove(i);
                    size--;
                }
            }
        }
        return arrayList;
    }

    private class ComputeCallback implements com.android.internal.app.AbstractResolverComparator.AfterCompute {
        private java.util.concurrent.CountDownLatch mFinishComputeSignal;

        public ComputeCallback(java.util.concurrent.CountDownLatch countDownLatch) {
            this.mFinishComputeSignal = countDownLatch;
        }

        @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
        public void afterCompute() {
            this.mFinishComputeSignal.countDown();
        }
    }

    private void compute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) throws java.lang.InterruptedException {
        if (this.mResolverComparator == null) {
            android.util.Log.d(TAG, "Comparator has already been destroyed; skipped.");
            return;
        }
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mResolverComparator.setCallBack(new com.android.internal.app.ResolverListController.ComputeCallback(countDownLatch));
        this.mResolverComparator.compute(list);
        countDownLatch.await();
        this.isComputed = true;
    }

    public void sort(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        try {
            java.lang.System.currentTimeMillis();
            if (!this.isComputed) {
                compute(list);
            }
            java.util.Collections.sort(list, this.mResolverComparator);
            java.lang.System.currentTimeMillis();
        } catch (java.lang.InterruptedException e) {
            android.util.Log.e(TAG, "Compute & Sort was interrupted: " + e);
        }
    }

    public void topK(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, int i) {
        if (list == null || list.isEmpty() || i <= 0) {
            return;
        }
        if (list.size() <= i) {
            sort(list);
            return;
        }
        try {
            java.lang.System.currentTimeMillis();
            if (!this.isComputed) {
                compute(list);
            }
            java.util.PriorityQueue priorityQueue = new java.util.PriorityQueue(i, new java.util.Comparator() { // from class: com.android.internal.app.ResolverListController$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$topK$0;
                    lambda$topK$0 = com.android.internal.app.ResolverListController.this.lambda$topK$0((com.android.internal.app.ResolverActivity.ResolvedComponentInfo) obj, (com.android.internal.app.ResolverActivity.ResolvedComponentInfo) obj2);
                    return lambda$topK$0;
                }
            });
            int size = list.size();
            int i2 = size - 1;
            int i3 = size - i;
            priorityQueue.addAll(list.subList(i3, size));
            for (int i4 = i3 - 1; i4 >= 0; i4--) {
                com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = list.get(i4);
                if ((-this.mResolverComparator.compare(resolvedComponentInfo, (com.android.internal.app.ResolverActivity.ResolvedComponentInfo) priorityQueue.peek())) > 0) {
                    list.set(i2, (com.android.internal.app.ResolverActivity.ResolvedComponentInfo) priorityQueue.poll());
                    priorityQueue.add(resolvedComponentInfo);
                    i2--;
                } else {
                    list.set(i2, resolvedComponentInfo);
                    i2--;
                }
            }
            while (!priorityQueue.isEmpty()) {
                list.set(i2, (com.android.internal.app.ResolverActivity.ResolvedComponentInfo) priorityQueue.poll());
                i2--;
            }
            java.lang.System.currentTimeMillis();
        } catch (java.lang.InterruptedException e) {
            android.util.Log.e(TAG, "Compute & greatestOf was interrupted: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$topK$0(com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2) {
        return -this.mResolverComparator.compare(resolvedComponentInfo, resolvedComponentInfo2);
    }

    private static boolean isSameResolvedComponent(android.content.pm.ResolveInfo resolveInfo, com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
        return activityInfo.packageName.equals(resolvedComponentInfo.name.getPackageName()) && activityInfo.name.equals(resolvedComponentInfo.name.getClassName());
    }

    boolean isComponentFiltered(android.content.ComponentName componentName) {
        return false;
    }

    public float getScore(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return this.mResolverComparator.getScore(displayResolveInfo);
    }

    public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
        return this.mResolverComparator.getScore(targetInfo);
    }

    public void updateModel(com.android.internal.app.chooser.TargetInfo targetInfo) {
        this.mResolverComparator.updateModel(targetInfo);
    }

    public void updateChooserCounts(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2) {
        this.mResolverComparator.updateChooserCounts(str, userHandle, str2);
    }

    public void destroy() {
        this.mResolverComparator.destroy();
    }
}
