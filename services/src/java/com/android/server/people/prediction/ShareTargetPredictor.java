package com.android.server.people.prediction;

/* loaded from: classes2.dex */
class ShareTargetPredictor extends com.android.server.people.prediction.AppTargetPredictor {
    private static final java.lang.String REMOTE_APP_PREDICTOR_KEY = "remote_app_predictor";

    @android.annotation.Nullable
    private final java.lang.String mChooserActivity;
    private final android.content.IntentFilter mIntentFilter;
    private final android.app.prediction.AppPredictor mRemoteAppPredictor;
    private static final java.lang.String TAG = "ShareTargetPredictor";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    ShareTargetPredictor(@android.annotation.NonNull android.app.prediction.AppPredictionContext appPredictionContext, @android.annotation.NonNull java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i, @android.annotation.NonNull android.content.Context context) {
        super(appPredictionContext, consumer, dataManager, i);
        this.mIntentFilter = (android.content.IntentFilter) appPredictionContext.getExtras().getParcelable("intent_filter", android.content.IntentFilter.class);
        if (android.provider.DeviceConfig.getBoolean("systemui", "dark_launch_remote_prediction_service_enabled", false)) {
            appPredictionContext.getExtras().putBoolean(REMOTE_APP_PREDICTOR_KEY, true);
            this.mRemoteAppPredictor = ((android.app.prediction.AppPredictionManager) context.createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.app.prediction.AppPredictionManager.class)).createAppPredictionSession(appPredictionContext);
        } else {
            this.mRemoteAppPredictor = null;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.config_bodyFontFamily));
        this.mChooserActivity = unflattenFromString != null ? unflattenFromString.getShortClassName() : null;
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    /* renamed from: reportAppTargetEvent */
    void lambda$onAppTargetEvent$0(android.app.prediction.AppTargetEvent appTargetEvent) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "reportAppTargetEvent");
        }
        if (this.mIntentFilter != null) {
            getDataManager().reportShareTargetEvent(appTargetEvent, this.mIntentFilter);
        }
        if (this.mRemoteAppPredictor != null) {
            this.mRemoteAppPredictor.notifyAppTargetEvent(appTargetEvent);
        }
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    void predictTargets() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "predictTargets");
        }
        if (this.mIntentFilter == null) {
            updatePredictions(java.util.List.of());
            return;
        }
        java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> directShareTargets = getDirectShareTargets();
        com.android.server.people.prediction.SharesheetModelScorer.computeScore(directShareTargets, getShareEventType(this.mIntentFilter), java.lang.System.currentTimeMillis());
        java.util.Collections.sort(directShareTargets, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.people.prediction.ShareTargetPredictor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Float.valueOf(((com.android.server.people.prediction.ShareTargetPredictor.ShareTarget) obj).getScore());
            }
        }, java.util.Collections.reverseOrder()).thenComparing(new java.util.function.Function() { // from class: com.android.server.people.prediction.ShareTargetPredictor$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$predictTargets$0;
                lambda$predictTargets$0 = com.android.server.people.prediction.ShareTargetPredictor.lambda$predictTargets$0((com.android.server.people.prediction.ShareTargetPredictor.ShareTarget) obj);
                return lambda$predictTargets$0;
            }
        }));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < java.lang.Math.min(getPredictionContext().getPredictedTargetCount(), directShareTargets.size()); i++) {
            arrayList.add(directShareTargets.get(i).getAppTarget());
        }
        updatePredictions(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$predictTargets$0(com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget) {
        return java.lang.Integer.valueOf(shareTarget.getAppTarget().getRank());
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    /* renamed from: sortTargets */
    void lambda$onSortAppTargets$1(java.util.List<android.app.prediction.AppTarget> list, java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "sortTargets");
        }
        if (this.mIntentFilter == null) {
            consumer.accept(list);
            return;
        }
        java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> appShareTargets = getAppShareTargets(list);
        com.android.server.people.prediction.SharesheetModelScorer.computeScoreForAppShare(appShareTargets, getShareEventType(this.mIntentFilter), getPredictionContext().getPredictedTargetCount(), java.lang.System.currentTimeMillis(), getDataManager(), this.mCallingUserId, this.mChooserActivity);
        java.util.Collections.sort(appShareTargets, new java.util.Comparator() { // from class: com.android.server.people.prediction.ShareTargetPredictor$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$sortTargets$1;
                lambda$sortTargets$1 = com.android.server.people.prediction.ShareTargetPredictor.lambda$sortTargets$1((com.android.server.people.prediction.ShareTargetPredictor.ShareTarget) obj, (com.android.server.people.prediction.ShareTargetPredictor.ShareTarget) obj2);
                return lambda$sortTargets$1;
            }
        });
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget : appShareTargets) {
            android.app.prediction.AppTarget appTarget = shareTarget.getAppTarget();
            arrayList.add(new android.app.prediction.AppTarget.Builder(appTarget.getId(), appTarget.getPackageName(), appTarget.getUser()).setClassName(appTarget.getClassName()).setRank(shareTarget.getScore() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? (int) (shareTarget.getScore() * 1000.0f) : 0).build());
        }
        consumer.accept(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortTargets$1(com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget, com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget2) {
        return -java.lang.Float.compare(shareTarget.getScore(), shareTarget2.getScore());
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    void destroy() {
        if (this.mRemoteAppPredictor != null) {
            this.mRemoteAppPredictor.destroy();
        }
    }

    private java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> getDirectShareTargets() {
        com.android.server.people.data.ConversationInfo conversationInfo;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.ShortcutManager.ShareShortcutInfo shareShortcutInfo : getDataManager().getShareShortcuts(this.mIntentFilter, this.mCallingUserId)) {
            android.content.pm.ShortcutInfo shortcutInfo = shareShortcutInfo.getShortcutInfo();
            android.app.prediction.AppTarget build = new android.app.prediction.AppTarget.Builder(new android.app.prediction.AppTargetId(shortcutInfo.getId()), shortcutInfo).setClassName(shareShortcutInfo.getTargetComponent().getClassName()).setRank(shortcutInfo.getRank()).build();
            com.android.server.people.data.PackageData packageData = getDataManager().getPackage(shortcutInfo.getPackage(), shortcutInfo.getUserId());
            com.android.server.people.data.EventHistory eventHistory = null;
            if (packageData == null) {
                conversationInfo = null;
            } else {
                java.lang.String id = shortcutInfo.getId();
                conversationInfo = packageData.getConversationInfo(id);
                if (conversationInfo != null) {
                    eventHistory = packageData.getEventHistory(id);
                }
            }
            arrayList.add(new com.android.server.people.prediction.ShareTargetPredictor.ShareTarget(build, eventHistory, conversationInfo));
        }
        return arrayList;
    }

    private java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> getAppShareTargets(java.util.List<android.app.prediction.AppTarget> list) {
        com.android.server.people.data.EventHistory classLevelEventHistory;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.app.prediction.AppTarget appTarget : list) {
            com.android.server.people.data.PackageData packageData = getDataManager().getPackage(appTarget.getPackageName(), appTarget.getUser().getIdentifier());
            if (packageData == null) {
                classLevelEventHistory = null;
            } else {
                classLevelEventHistory = packageData.getClassLevelEventHistory(appTarget.getClassName());
            }
            arrayList.add(new com.android.server.people.prediction.ShareTargetPredictor.ShareTarget(appTarget, classLevelEventHistory, null));
        }
        return arrayList;
    }

    private int getShareEventType(android.content.IntentFilter intentFilter) {
        return getDataManager().mimeTypeToShareEventType(intentFilter != null ? intentFilter.getDataType(0) : null);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ShareTarget {

        @android.annotation.NonNull
        private final android.app.prediction.AppTarget mAppTarget;

        @android.annotation.Nullable
        private final com.android.server.people.data.ConversationInfo mConversationInfo;

        @android.annotation.Nullable
        private final com.android.server.people.data.EventHistory mEventHistory;
        private float mScore = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

        @com.android.internal.annotations.VisibleForTesting
        ShareTarget(@android.annotation.NonNull android.app.prediction.AppTarget appTarget, @android.annotation.Nullable com.android.server.people.data.EventHistory eventHistory, @android.annotation.Nullable com.android.server.people.data.ConversationInfo conversationInfo) {
            this.mAppTarget = appTarget;
            this.mEventHistory = eventHistory;
            this.mConversationInfo = conversationInfo;
        }

        @android.annotation.NonNull
        @com.android.internal.annotations.VisibleForTesting
        android.app.prediction.AppTarget getAppTarget() {
            return this.mAppTarget;
        }

        @com.android.internal.annotations.VisibleForTesting
        @android.annotation.Nullable
        com.android.server.people.data.EventHistory getEventHistory() {
            return this.mEventHistory;
        }

        @com.android.internal.annotations.VisibleForTesting
        @android.annotation.Nullable
        com.android.server.people.data.ConversationInfo getConversationInfo() {
            return this.mConversationInfo;
        }

        @com.android.internal.annotations.VisibleForTesting
        float getScore() {
            return this.mScore;
        }

        @com.android.internal.annotations.VisibleForTesting
        void setScore(float f) {
            this.mScore = f;
        }
    }
}
