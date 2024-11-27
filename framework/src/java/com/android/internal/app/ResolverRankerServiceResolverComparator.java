package com.android.internal.app;

/* loaded from: classes4.dex */
class ResolverRankerServiceResolverComparator extends com.android.internal.app.AbstractResolverComparator {
    private static final int CONNECTION_COST_TIMEOUT_MILLIS = 200;
    private static final boolean DEBUG = false;
    private static final float RECENCY_MULTIPLIER = 2.0f;
    private static final long RECENCY_TIME_PERIOD = 43200000;
    private static final java.lang.String TAG = "RRSResolverComparator";
    private static final long USAGE_STATS_PERIOD = 604800000;
    private java.lang.String mAction;
    private final java.text.Collator mCollator;
    private com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceComparatorModel mComparatorModel;
    private java.util.concurrent.CountDownLatch mConnectSignal;
    private com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceConnection mConnection;
    private android.content.Context mContext;
    private final long mCurrentTime;
    private final java.lang.Object mLock;
    private android.service.resolver.IResolverRankerService mRanker;
    private android.content.ComponentName mRankerServiceName;
    private final java.lang.String mReferrerPackage;
    private android.content.ComponentName mResolvedRankerName;
    private final long mSinceTime;
    private final java.util.Map<android.os.UserHandle, java.util.Map<java.lang.String, android.app.usage.UsageStats>> mStatsPerUser;
    private java.util.ArrayList<android.service.resolver.ResolverTarget> mTargets;
    private final java.util.Map<android.os.UserHandle, java.util.LinkedHashMap<android.content.ComponentName, android.service.resolver.ResolverTarget>> mTargetsDictPerUser;

    public ResolverRankerServiceResolverComparator(android.content.Context context, android.content.Intent intent, java.lang.String str, com.android.internal.app.AbstractResolverComparator.AfterCompute afterCompute, com.android.internal.app.ChooserActivityLogger chooserActivityLogger, android.os.UserHandle userHandle) {
        this(context, intent, str, afterCompute, chooserActivityLogger, com.google.android.collect.Lists.newArrayList(userHandle));
    }

    public ResolverRankerServiceResolverComparator(android.content.Context context, android.content.Intent intent, java.lang.String str, com.android.internal.app.AbstractResolverComparator.AfterCompute afterCompute, com.android.internal.app.ChooserActivityLogger chooserActivityLogger, java.util.List<android.os.UserHandle> list) {
        super(context, intent, list);
        this.mLock = new java.lang.Object();
        this.mCollator = java.text.Collator.getInstance(context.getResources().getConfiguration().locale);
        this.mReferrerPackage = str;
        this.mContext = context;
        this.mCurrentTime = java.lang.System.currentTimeMillis();
        this.mSinceTime = this.mCurrentTime - 604800000;
        this.mStatsPerUser = new java.util.HashMap();
        this.mTargetsDictPerUser = new java.util.HashMap();
        for (android.os.UserHandle userHandle : list) {
            this.mStatsPerUser.put(userHandle, this.mUsmMap.get(userHandle).queryAndAggregateUsageStats(this.mSinceTime, this.mCurrentTime));
            this.mTargetsDictPerUser.put(userHandle, new java.util.LinkedHashMap<>());
        }
        this.mAction = intent.getAction();
        this.mRankerServiceName = new android.content.ComponentName(this.mContext, getClass());
        setCallBack(afterCompute);
        setChooserActivityLogger(chooserActivityLogger);
        this.mComparatorModel = buildUpdatedModel();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void handleResultMessage(android.os.Message message) {
        if (message.what != 0) {
            return;
        }
        if (message.obj == null) {
            android.util.Log.e(TAG, "Receiving null prediction results.");
            return;
        }
        java.util.List list = (java.util.List) message.obj;
        if (list != null && this.mTargets != null && list.size() == this.mTargets.size()) {
            int size = this.mTargets.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                float selectProbability = ((android.service.resolver.ResolverTarget) list.get(i)).getSelectProbability();
                if (selectProbability != this.mTargets.get(i).getSelectProbability()) {
                    this.mTargets.get(i).setSelectProbability(selectProbability);
                    z = true;
                }
            }
            if (z) {
                this.mRankerServiceName = this.mResolvedRankerName;
                this.mComparatorModel = buildUpdatedModel();
                return;
            }
            return;
        }
        android.util.Log.e(TAG, "Sizes of sent and received ResolverTargets diff.");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0113 A[SYNTHETIC] */
    @Override // com.android.internal.app.AbstractResolverComparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doCompute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        long j;
        float f;
        long j2 = this.mCurrentTime - 43200000;
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = 1.0f;
        float f5 = 1.0f;
        for (com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            android.service.resolver.ResolverTarget resolverTarget = new android.service.resolver.ResolverTarget();
            java.util.LinkedHashMap<android.content.ComponentName, android.service.resolver.ResolverTarget> linkedHashMap = this.mTargetsDictPerUser.get(resolvedComponentInfo.getResolveInfoAt(0).userHandle);
            java.util.Map<java.lang.String, android.app.usage.UsageStats> map = this.mStatsPerUser.get(resolvedComponentInfo.getResolveInfoAt(0).userHandle);
            if (linkedHashMap == null || map == null) {
                j = j2;
            } else {
                linkedHashMap.put(resolvedComponentInfo.name, resolverTarget);
                android.app.usage.UsageStats usageStats = map.get(resolvedComponentInfo.name.getPackageName());
                if (usageStats != null) {
                    if (!resolvedComponentInfo.name.getPackageName().equals(this.mReferrerPackage) && !isPersistentProcess(resolvedComponentInfo)) {
                        float max = java.lang.Math.max(usageStats.getLastTimeUsed() - j2, 0L);
                        resolverTarget.setRecencyScore(max);
                        if (max > f2) {
                            f2 = max;
                        }
                    }
                    float totalTimeInForeground = usageStats.getTotalTimeInForeground();
                    resolverTarget.setTimeSpentScore(totalTimeInForeground);
                    if (totalTimeInForeground > f3) {
                        f3 = totalTimeInForeground;
                    }
                    float f6 = usageStats.mLaunchCount;
                    resolverTarget.setLaunchScore(f6);
                    if (f6 > f4) {
                        f4 = f6;
                    }
                    if (usageStats.mChooserCounts == null || this.mAction == null) {
                        j = j2;
                    } else if (usageStats.mChooserCounts.get(this.mAction) == null) {
                        j = j2;
                    } else {
                        f = usageStats.mChooserCounts.get(this.mAction).getOrDefault(this.mContentType, 0).intValue();
                        if (this.mAnnotations == null) {
                            j = j2;
                        } else {
                            int i = 0;
                            while (i < this.mAnnotations.length) {
                                f += usageStats.mChooserCounts.get(this.mAction).getOrDefault(this.mAnnotations[i], 0).intValue();
                                i++;
                                j2 = j2;
                            }
                            j = j2;
                        }
                        resolverTarget.setChooserScore(f);
                        if (f <= f5) {
                            f5 = f;
                        }
                    }
                    f = 0.0f;
                    resolverTarget.setChooserScore(f);
                    if (f <= f5) {
                    }
                } else {
                    j = j2;
                }
            }
            j2 = j;
        }
        this.mTargets = new java.util.ArrayList<>();
        java.util.Iterator<android.os.UserHandle> it = this.mTargetsDictPerUser.keySet().iterator();
        while (it.hasNext()) {
            this.mTargets.addAll(this.mTargetsDictPerUser.get(it.next()).values());
        }
        java.util.Iterator<android.service.resolver.ResolverTarget> it2 = this.mTargets.iterator();
        while (it2.hasNext()) {
            android.service.resolver.ResolverTarget next = it2.next();
            float recencyScore = next.getRecencyScore() / f2;
            setFeatures(next, recencyScore * recencyScore * RECENCY_MULTIPLIER, next.getLaunchScore() / f4, next.getTimeSpentScore() / f3, next.getChooserScore() / f5);
            addDefaultSelectProbability(next);
        }
        predictSelectProbabilities(this.mTargets);
        this.mComparatorModel = buildUpdatedModel();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
        return this.mComparatorModel.getComparator().compare(resolveInfo, resolveInfo2);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
        return this.mComparatorModel.getScore(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void updateModel(com.android.internal.app.chooser.TargetInfo targetInfo) {
        synchronized (this.mLock) {
            this.mComparatorModel.notifyOnTargetSelected(targetInfo);
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        if (this.mConnection != null) {
            this.mContext.unbindService(this.mConnection);
            this.mConnection.destroy();
        }
        afterCompute();
    }

    private void initRanker(android.content.Context context) {
        synchronized (this.mLock) {
            if (this.mConnection == null || this.mRanker == null) {
                android.content.Intent resolveRankerService = resolveRankerService();
                if (resolveRankerService == null) {
                    return;
                }
                this.mConnectSignal = new java.util.concurrent.CountDownLatch(1);
                this.mConnection = new com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceConnection(this.mConnectSignal);
                context.bindServiceAsUser(resolveRankerService, this.mConnection, 1, android.os.UserHandle.SYSTEM);
            }
        }
    }

    private android.content.Intent resolveRankerService() {
        android.content.Intent intent = new android.content.Intent(android.service.resolver.ResolverRankerService.SERVICE_INTERFACE);
        for (android.content.pm.ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServices(intent, 0)) {
            if (resolveInfo != null && resolveInfo.serviceInfo != null && resolveInfo.serviceInfo.applicationInfo != null) {
                android.content.ComponentName componentName = new android.content.ComponentName(resolveInfo.serviceInfo.applicationInfo.packageName, resolveInfo.serviceInfo.name);
                try {
                    if (!"android.permission.BIND_RESOLVER_RANKER_SERVICE".equals(this.mContext.getPackageManager().getServiceInfo(componentName, 0).permission)) {
                        android.util.Log.w(TAG, "ResolverRankerService " + componentName + " does not require permission android.permission.BIND_RESOLVER_RANKER_SERVICE - this service will not be queried for ResolverRankerServiceResolverComparator. add android:permission=\"android.permission.BIND_RESOLVER_RANKER_SERVICE\" to the <service> tag for " + componentName + " in the manifest.");
                    } else if (this.mContext.getPackageManager().checkPermission("android.permission.PROVIDE_RESOLVER_RANKER_SERVICE", resolveInfo.serviceInfo.packageName) != 0) {
                        android.util.Log.w(TAG, "ResolverRankerService " + componentName + " does not hold permission android.permission.PROVIDE_RESOLVER_RANKER_SERVICE - this service will not be queried for ResolverRankerServiceResolverComparator.");
                    } else {
                        this.mResolvedRankerName = componentName;
                        intent.setComponent(componentName);
                        return intent;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.e(TAG, "Could not look up service " + componentName + "; component name not found");
                }
            }
        }
        return null;
    }

    private class ResolverRankerServiceConnection implements android.content.ServiceConnection {
        private final java.util.concurrent.CountDownLatch mConnectSignal;
        public final android.service.resolver.IResolverRankerResult resolverRankerResult = new android.service.resolver.IResolverRankerResult.Stub() { // from class: com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceConnection.1
            @Override // android.service.resolver.IResolverRankerResult
            public void sendResult(java.util.List<android.service.resolver.ResolverTarget> list) throws android.os.RemoteException {
                synchronized (com.android.internal.app.ResolverRankerServiceResolverComparator.this.mLock) {
                    android.os.Message obtain = android.os.Message.obtain();
                    obtain.what = 0;
                    obtain.obj = list;
                    com.android.internal.app.ResolverRankerServiceResolverComparator.this.mHandler.sendMessage(obtain);
                }
            }
        };

        public ResolverRankerServiceConnection(java.util.concurrent.CountDownLatch countDownLatch) {
            this.mConnectSignal = countDownLatch;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.internal.app.ResolverRankerServiceResolverComparator.this.mLock) {
                com.android.internal.app.ResolverRankerServiceResolverComparator.this.mRanker = android.service.resolver.IResolverRankerService.Stub.asInterface(iBinder);
                com.android.internal.app.ResolverRankerServiceResolverComparator.this.mComparatorModel = com.android.internal.app.ResolverRankerServiceResolverComparator.this.buildUpdatedModel();
                this.mConnectSignal.countDown();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.internal.app.ResolverRankerServiceResolverComparator.this.mLock) {
                destroy();
            }
        }

        public void destroy() {
            synchronized (com.android.internal.app.ResolverRankerServiceResolverComparator.this.mLock) {
                com.android.internal.app.ResolverRankerServiceResolverComparator.this.mRanker = null;
                com.android.internal.app.ResolverRankerServiceResolverComparator.this.mComparatorModel = com.android.internal.app.ResolverRankerServiceResolverComparator.this.buildUpdatedModel();
            }
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void beforeCompute() {
        super.beforeCompute();
        java.util.Iterator<android.os.UserHandle> it = this.mTargetsDictPerUser.keySet().iterator();
        while (it.hasNext()) {
            this.mTargetsDictPerUser.get(it.next()).clear();
        }
        this.mTargets = null;
        this.mRankerServiceName = new android.content.ComponentName(this.mContext, getClass());
        this.mComparatorModel = buildUpdatedModel();
        this.mResolvedRankerName = null;
        initRanker(this.mContext);
    }

    private void predictSelectProbabilities(java.util.List<android.service.resolver.ResolverTarget> list) {
        if (this.mConnection != null) {
            try {
                this.mConnectSignal.await(200L, java.util.concurrent.TimeUnit.MILLISECONDS);
                synchronized (this.mLock) {
                    if (this.mRanker != null) {
                        this.mRanker.predict(list, this.mConnection.resolverRankerResult);
                        return;
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in Predict: " + e);
            } catch (java.lang.InterruptedException e2) {
                android.util.Log.e(TAG, "Error in Wait for Service Connection.");
            }
        }
        afterCompute();
    }

    private void addDefaultSelectProbability(android.service.resolver.ResolverTarget resolverTarget) {
        resolverTarget.setSelectProbability((float) (1.0d / (java.lang.Math.exp(1.6568f - ((((resolverTarget.getLaunchScore() * 2.5543f) + (resolverTarget.getTimeSpentScore() * 2.8412f)) + (resolverTarget.getRecencyScore() * 0.269f)) + (resolverTarget.getChooserScore() * 4.2222f))) + 1.0d)));
    }

    private void setFeatures(android.service.resolver.ResolverTarget resolverTarget, float f, float f2, float f3, float f4) {
        resolverTarget.setRecencyScore(f);
        resolverTarget.setLaunchScore(f2);
        resolverTarget.setTimeSpentScore(f3);
        resolverTarget.setChooserScore(f4);
    }

    static boolean isPersistentProcess(com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        return (resolvedComponentInfo == null || resolvedComponentInfo.getCount() <= 0 || (resolvedComponentInfo.getResolveInfoAt(0).activityInfo.applicationInfo.flags & 8) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceComparatorModel buildUpdatedModel() {
        return new com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceComparatorModel(this.mStatsPerUser, this.mTargetsDictPerUser, this.mTargets, this.mCollator, this.mRanker, this.mRankerServiceName, this.mAnnotations != null, this.mPmMap);
    }

    static class ResolverRankerServiceComparatorModel implements com.android.internal.app.ResolverComparatorModel {
        private final boolean mAnnotationsUsed;
        private final java.text.Collator mCollator;
        private final java.util.Map<android.os.UserHandle, android.content.pm.PackageManager> mPmMap;
        private final android.service.resolver.IResolverRankerService mRanker;
        private final android.content.ComponentName mRankerServiceName;
        private final java.util.Map<android.os.UserHandle, java.util.Map<java.lang.String, android.app.usage.UsageStats>> mStatsPerUser;
        private final java.util.List<android.service.resolver.ResolverTarget> mTargets;
        private final java.util.Map<android.os.UserHandle, java.util.LinkedHashMap<android.content.ComponentName, android.service.resolver.ResolverTarget>> mTargetsDictPerUser;

        ResolverRankerServiceComparatorModel(java.util.Map<android.os.UserHandle, java.util.Map<java.lang.String, android.app.usage.UsageStats>> map, java.util.Map<android.os.UserHandle, java.util.LinkedHashMap<android.content.ComponentName, android.service.resolver.ResolverTarget>> map2, java.util.List<android.service.resolver.ResolverTarget> list, java.text.Collator collator, android.service.resolver.IResolverRankerService iResolverRankerService, android.content.ComponentName componentName, boolean z, java.util.Map<android.os.UserHandle, android.content.pm.PackageManager> map3) {
            this.mStatsPerUser = map;
            this.mTargetsDictPerUser = map2;
            this.mTargets = list;
            this.mCollator = collator;
            this.mRanker = iResolverRankerService;
            this.mRankerServiceName = componentName;
            this.mAnnotationsUsed = z;
            this.mPmMap = map3;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public java.util.Comparator<android.content.pm.ResolveInfo> getComparator() {
            return new java.util.Comparator() { // from class: com.android.internal.app.ResolverRankerServiceResolverComparator$ResolverRankerServiceComparatorModel$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$getComparator$0;
                    lambda$getComparator$0 = com.android.internal.app.ResolverRankerServiceResolverComparator.ResolverRankerServiceComparatorModel.this.lambda$getComparator$0((android.content.pm.ResolveInfo) obj, (android.content.pm.ResolveInfo) obj2);
                    return lambda$getComparator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getComparator$0(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
            java.lang.CharSequence charSequence;
            int compare;
            android.service.resolver.ResolverTarget activityResolverTargetForUser = getActivityResolverTargetForUser(resolveInfo.activityInfo, resolveInfo.userHandle);
            android.service.resolver.ResolverTarget activityResolverTargetForUser2 = getActivityResolverTargetForUser(resolveInfo2.activityInfo, resolveInfo2.userHandle);
            if (activityResolverTargetForUser != null && activityResolverTargetForUser2 != null && (compare = java.lang.Float.compare(activityResolverTargetForUser2.getSelectProbability(), activityResolverTargetForUser.getSelectProbability())) != 0) {
                return compare > 0 ? 1 : -1;
            }
            if (!this.mPmMap.containsKey(resolveInfo.userHandle)) {
                charSequence = null;
            } else {
                charSequence = resolveInfo.loadLabel(this.mPmMap.get(resolveInfo.userHandle));
            }
            if (charSequence == null) {
                charSequence = resolveInfo.activityInfo.name;
            }
            java.lang.CharSequence loadLabel = this.mPmMap.containsKey(resolveInfo2.userHandle) ? resolveInfo2.loadLabel(this.mPmMap.get(resolveInfo2.userHandle)) : null;
            if (loadLabel == null) {
                loadLabel = resolveInfo2.activityInfo.name;
            }
            return this.mCollator.compare(charSequence.toString().trim(), loadLabel.toString().trim());
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
            if (this.mTargetsDictPerUser.containsKey(targetInfo.getResolveInfo().userHandle) && this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).get(targetInfo.getResolvedComponentName()) != null) {
                return this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).get(targetInfo.getResolvedComponentName()).getSelectProbability();
            }
            return 0.0f;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public void notifyOnTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo) {
            int i;
            if (this.mRanker != null) {
                try {
                    if (!this.mTargetsDictPerUser.containsKey(targetInfo.getResolveInfo().userHandle)) {
                        i = -1;
                    } else {
                        i = new java.util.ArrayList(this.mTargetsDictPerUser.get(targetInfo.getResolveInfo().userHandle).keySet()).indexOf(targetInfo.getResolvedComponentName());
                    }
                    if (i >= 0 && this.mTargets != null) {
                        float score = getScore(targetInfo);
                        java.util.Iterator<android.service.resolver.ResolverTarget> it = this.mTargets.iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            if (it.next().getSelectProbability() > score) {
                                i2++;
                            }
                        }
                        logMetrics(i2);
                        this.mRanker.train(this.mTargets, i);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.internal.app.ResolverRankerServiceResolverComparator.TAG, "Error in Train: " + e);
                }
            }
        }

        private void logMetrics(int i) {
            if (this.mRankerServiceName != null) {
                com.android.internal.logging.MetricsLogger metricsLogger = new com.android.internal.logging.MetricsLogger();
                android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1085);
                logMaker.setComponentName(this.mRankerServiceName);
                logMaker.addTaggedData(1086, java.lang.Integer.valueOf(this.mAnnotationsUsed ? 1 : 0));
                logMaker.addTaggedData(1087, java.lang.Integer.valueOf(i));
                metricsLogger.write(logMaker);
            }
        }

        private android.service.resolver.ResolverTarget getActivityResolverTargetForUser(android.content.pm.ActivityInfo activityInfo, android.os.UserHandle userHandle) {
            if (this.mStatsPerUser == null || !this.mTargetsDictPerUser.containsKey(userHandle)) {
                return null;
            }
            return this.mTargetsDictPerUser.get(userHandle).get(new android.content.ComponentName(activityInfo.packageName, activityInfo.name));
        }
    }
}
