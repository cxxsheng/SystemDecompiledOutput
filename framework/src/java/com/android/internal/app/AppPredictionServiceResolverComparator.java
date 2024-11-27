package com.android.internal.app;

/* loaded from: classes4.dex */
class AppPredictionServiceResolverComparator extends com.android.internal.app.AbstractResolverComparator {
    private static final java.lang.String TAG = "APSResolverComparator";
    private final android.app.prediction.AppPredictor mAppPredictor;
    private com.android.internal.app.ResolverComparatorModel mComparatorModel;
    private final android.content.Context mContext;
    private final android.content.Intent mIntent;
    private final com.android.internal.app.AppPredictionServiceResolverComparator.ModelBuilder mModelBuilder;
    private final java.lang.String mReferrerPackage;
    private com.android.internal.app.ResolverRankerServiceResolverComparator mResolverRankerService;
    private com.android.internal.app.ResolverAppPredictorCallback mSortingCallback;
    private final android.os.UserHandle mUser;

    AppPredictionServiceResolverComparator(android.content.Context context, android.content.Intent intent, java.lang.String str, android.app.prediction.AppPredictor appPredictor, android.os.UserHandle userHandle, com.android.internal.app.ChooserActivityLogger chooserActivityLogger) {
        super(context, intent, com.google.android.collect.Lists.newArrayList(userHandle));
        this.mContext = context;
        this.mIntent = intent;
        this.mAppPredictor = appPredictor;
        this.mUser = userHandle;
        this.mReferrerPackage = str;
        setChooserActivityLogger(chooserActivityLogger);
        this.mModelBuilder = new com.android.internal.app.AppPredictionServiceResolverComparator.ModelBuilder(appPredictor, userHandle);
        this.mComparatorModel = this.mModelBuilder.buildFromRankedList(java.util.Collections.emptyList());
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void destroy() {
        if (this.mResolverRankerService != null) {
            this.mResolverRankerService.destroy();
            this.mResolverRankerService = null;
            this.mComparatorModel = this.mModelBuilder.buildFallbackModel(this.mResolverRankerService);
        }
        if (this.mSortingCallback != null) {
            this.mSortingCallback.destroy();
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
        return this.mComparatorModel.getComparator().compare(resolveInfo, resolveInfo2);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
        return this.mComparatorModel.getScore(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void updateModel(com.android.internal.app.chooser.TargetInfo targetInfo) {
        this.mComparatorModel.notifyOnTargetSelected(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void handleResultMessage(android.os.Message message) {
        if (message.what == 0 && message.obj != null) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList((java.util.List) message.obj);
        } else if (message.obj == null && this.mResolverRankerService == null) {
            android.util.Log.e(TAG, "Unexpected null result");
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void doCompute(final java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        if (list.isEmpty()) {
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            arrayList.add(new android.app.prediction.AppTarget.Builder(new android.app.prediction.AppTargetId(resolvedComponentInfo.name.flattenToString()), resolvedComponentInfo.name.getPackageName(), this.mUser).setClassName(resolvedComponentInfo.name.getClassName()).build());
        }
        if (this.mSortingCallback != null) {
            this.mSortingCallback.destroy();
        }
        this.mSortingCallback = new com.android.internal.app.ResolverAppPredictorCallback(new java.util.function.Consumer() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.app.AppPredictionServiceResolverComparator.this.lambda$doCompute$0(list, (java.util.List) obj);
            }
        });
        this.mAppPredictor.sortTargets(arrayList, java.util.concurrent.Executors.newSingleThreadExecutor(), this.mSortingCallback.asConsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doCompute$0(java.util.List list, java.util.List list2) {
        if (list2.isEmpty()) {
            android.util.Log.i(TAG, "AppPredictionService disabled. Using resolver.");
            setupFallbackModel(list);
        } else {
            android.util.Log.i(TAG, "AppPredictionService response received");
            handleResult(list2);
        }
    }

    private void setupFallbackModel(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        this.mResolverRankerService = new com.android.internal.app.ResolverRankerServiceResolverComparator(this.mContext, this.mIntent, this.mReferrerPackage, new com.android.internal.app.AbstractResolverComparator.AfterCompute() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
            public final void afterCompute() {
                com.android.internal.app.AppPredictionServiceResolverComparator.this.lambda$setupFallbackModel$1();
            }
        }, getChooserActivityLogger(), this.mUser);
        this.mComparatorModel = this.mModelBuilder.buildFallbackModel(this.mResolverRankerService);
        this.mResolverRankerService.compute(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupFallbackModel$1() {
        this.mHandler.sendEmptyMessage(0);
    }

    private void handleResult(java.util.List<android.app.prediction.AppTarget> list) {
        if (this.mHandler.hasMessages(1)) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList(list);
            this.mHandler.removeMessages(1);
            afterCompute();
        }
    }

    static class ModelBuilder {
        private final android.app.prediction.AppPredictor mAppPredictor;
        private final android.os.UserHandle mUser;

        ModelBuilder(android.app.prediction.AppPredictor appPredictor, android.os.UserHandle userHandle) {
            this.mAppPredictor = appPredictor;
            this.mUser = userHandle;
        }

        com.android.internal.app.ResolverComparatorModel buildFromRankedList(java.util.List<android.app.prediction.AppTarget> list) {
            return new com.android.internal.app.AppPredictionServiceResolverComparator.AppPredictionServiceComparatorModel(this.mAppPredictor, this.mUser, buildTargetRanksMapFromSortedTargets(list));
        }

        com.android.internal.app.ResolverComparatorModel buildFallbackModel(com.android.internal.app.ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator) {
            return adaptLegacyResolverComparatorToComparatorModel(resolverRankerServiceResolverComparator);
        }

        private java.util.Map<android.content.ComponentName, java.lang.Integer> buildTargetRanksMapFromSortedTargets(java.util.List<android.app.prediction.AppTarget> list) {
            java.util.HashMap hashMap = new java.util.HashMap();
            for (int i = 0; i < list.size(); i++) {
                android.content.ComponentName componentName = new android.content.ComponentName(list.get(i).getPackageName(), list.get(i).getClassName());
                hashMap.put(componentName, java.lang.Integer.valueOf(i));
                android.util.Log.i(com.android.internal.app.AppPredictionServiceResolverComparator.TAG, "handleSortedAppTargets, sortedAppTargets #" + i + ": " + componentName);
            }
            return hashMap;
        }

        /* renamed from: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1, reason: invalid class name */
        class AnonymousClass1 implements com.android.internal.app.ResolverComparatorModel {
            final /* synthetic */ com.android.internal.app.AbstractResolverComparator val$comparator;

            AnonymousClass1(com.android.internal.app.AbstractResolverComparator abstractResolverComparator) {
                this.val$comparator = abstractResolverComparator;
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public java.util.Comparator<android.content.pm.ResolveInfo> getComparator() {
                final com.android.internal.app.AbstractResolverComparator abstractResolverComparator = this.val$comparator;
                return new java.util.Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        int compare;
                        compare = com.android.internal.app.AbstractResolverComparator.this.compare((android.content.pm.ResolveInfo) obj, (android.content.pm.ResolveInfo) obj2);
                        return compare;
                    }
                };
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
                return this.val$comparator.getScore(targetInfo);
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public void notifyOnTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo) {
                this.val$comparator.updateModel(targetInfo);
            }
        }

        private com.android.internal.app.ResolverComparatorModel adaptLegacyResolverComparatorToComparatorModel(com.android.internal.app.AbstractResolverComparator abstractResolverComparator) {
            return new com.android.internal.app.AppPredictionServiceResolverComparator.ModelBuilder.AnonymousClass1(abstractResolverComparator);
        }
    }

    static class AppPredictionServiceComparatorModel implements com.android.internal.app.ResolverComparatorModel {
        private final android.app.prediction.AppPredictor mAppPredictor;
        private final java.util.Map<android.content.ComponentName, java.lang.Integer> mTargetRanks;
        private final android.os.UserHandle mUser;

        AppPredictionServiceComparatorModel(android.app.prediction.AppPredictor appPredictor, android.os.UserHandle userHandle, java.util.Map<android.content.ComponentName, java.lang.Integer> map) {
            this.mAppPredictor = appPredictor;
            this.mUser = userHandle;
            this.mTargetRanks = map;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public java.util.Comparator<android.content.pm.ResolveInfo> getComparator() {
            return new java.util.Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$AppPredictionServiceComparatorModel$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$getComparator$0;
                    lambda$getComparator$0 = com.android.internal.app.AppPredictionServiceResolverComparator.AppPredictionServiceComparatorModel.this.lambda$getComparator$0((android.content.pm.ResolveInfo) obj, (android.content.pm.ResolveInfo) obj2);
                    return lambda$getComparator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getComparator$0(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
            java.lang.Integer num = this.mTargetRanks.get(new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            java.lang.Integer num2 = this.mTargetRanks.get(new android.content.ComponentName(resolveInfo2.activityInfo.packageName, resolveInfo2.activityInfo.name));
            if (num == null && num2 == null) {
                return 0;
            }
            if (num == null) {
                return -1;
            }
            if (num2 == null) {
                return 1;
            }
            return num.intValue() - num2.intValue();
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
            if (this.mTargetRanks.get(targetInfo.getResolvedComponentName()) == null) {
                android.util.Log.w(com.android.internal.app.AppPredictionServiceResolverComparator.TAG, "Score requested for unknown component. Did you call compute yet?");
                return 0.0f;
            }
            return 1.0f - (r3.intValue() / (((this.mTargetRanks.size() - 1) * this.mTargetRanks.size()) / 2));
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public void notifyOnTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo) {
            this.mAppPredictor.notifyAppTargetEvent(new android.app.prediction.AppTargetEvent.Builder(new android.app.prediction.AppTarget.Builder(new android.app.prediction.AppTargetId(targetInfo.getResolvedComponentName().toString()), targetInfo.getResolvedComponentName().getPackageName(), this.mUser).setClassName(targetInfo.getResolvedComponentName().getClassName()).build(), 1).build());
        }
    }
}
