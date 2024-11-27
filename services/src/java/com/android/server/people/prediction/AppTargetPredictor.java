package com.android.server.people.prediction;

/* loaded from: classes2.dex */
public class AppTargetPredictor {
    private static final java.lang.String UI_SURFACE_SHARE = "share";
    private final java.util.concurrent.ExecutorService mCallbackExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
    final int mCallingUserId;
    private final com.android.server.people.data.DataManager mDataManager;
    private final android.app.prediction.AppPredictionContext mPredictionContext;
    private final java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> mUpdatePredictionsMethod;

    public static com.android.server.people.prediction.AppTargetPredictor create(@android.annotation.NonNull android.app.prediction.AppPredictionContext appPredictionContext, @android.annotation.NonNull java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i, android.content.Context context) {
        if (UI_SURFACE_SHARE.equals(appPredictionContext.getUiSurface())) {
            return new com.android.server.people.prediction.ShareTargetPredictor(appPredictionContext, consumer, dataManager, i, context);
        }
        return new com.android.server.people.prediction.AppTargetPredictor(appPredictionContext, consumer, dataManager, i);
    }

    AppTargetPredictor(@android.annotation.NonNull android.app.prediction.AppPredictionContext appPredictionContext, @android.annotation.NonNull java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i) {
        this.mPredictionContext = appPredictionContext;
        this.mUpdatePredictionsMethod = consumer;
        this.mDataManager = dataManager;
        this.mCallingUserId = i;
    }

    public void onAppTargetEvent(final android.app.prediction.AppTargetEvent appTargetEvent) {
        this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.prediction.AppTargetPredictor.this.lambda$onAppTargetEvent$0(appTargetEvent);
            }
        });
    }

    public void onLaunchLocationShown(java.lang.String str, java.util.List<android.app.prediction.AppTargetId> list) {
    }

    public void onSortAppTargets(final java.util.List<android.app.prediction.AppTarget> list, final java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
        this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.prediction.AppTargetPredictor.this.lambda$onSortAppTargets$1(list, consumer);
            }
        });
    }

    public void onRequestPredictionUpdate() {
        this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.prediction.AppTargetPredictor.this.predictTargets();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> getUpdatePredictionsMethod() {
        return this.mUpdatePredictionsMethod;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: reportAppTargetEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$onAppTargetEvent$0(android.app.prediction.AppTargetEvent appTargetEvent) {
    }

    void predictTargets() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: sortTargets, reason: merged with bridge method [inline-methods] */
    public void lambda$onSortAppTargets$1(java.util.List<android.app.prediction.AppTarget> list, java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
        consumer.accept(list);
    }

    void destroy() {
    }

    android.app.prediction.AppPredictionContext getPredictionContext() {
        return this.mPredictionContext;
    }

    com.android.server.people.data.DataManager getDataManager() {
        return this.mDataManager;
    }

    void updatePredictions(java.util.List<android.app.prediction.AppTarget> list) {
        this.mUpdatePredictionsMethod.accept(list);
    }
}
