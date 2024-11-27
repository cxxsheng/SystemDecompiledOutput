package com.android.server.people;

/* loaded from: classes2.dex */
class SessionInfo {
    private static final java.lang.String TAG = "SessionInfo";
    private final com.android.server.people.prediction.AppTargetPredictor mAppTargetPredictor;
    private final android.os.RemoteCallbackList<android.app.prediction.IPredictionCallback> mCallbacks = new android.os.RemoteCallbackList<>();

    SessionInfo(android.app.prediction.AppPredictionContext appPredictionContext, com.android.server.people.data.DataManager dataManager, int i, android.content.Context context) {
        this.mAppTargetPredictor = com.android.server.people.prediction.AppTargetPredictor.create(appPredictionContext, new java.util.function.Consumer() { // from class: com.android.server.people.SessionInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.SessionInfo.this.updatePredictions((java.util.List) obj);
            }
        }, dataManager, i, context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addCallback(android.app.prediction.IPredictionCallback iPredictionCallback) {
        this.mCallbacks.register(iPredictionCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeCallback(android.app.prediction.IPredictionCallback iPredictionCallback) {
        this.mCallbacks.unregister(iPredictionCallback);
    }

    com.android.server.people.prediction.AppTargetPredictor getPredictor() {
        return this.mAppTargetPredictor;
    }

    void onDestroy() {
        this.mCallbacks.kill();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePredictions(java.util.List<android.app.prediction.AppTarget> list) {
        int beginBroadcast = this.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mCallbacks.getBroadcastItem(i).onResult(new android.content.pm.ParceledListSlice(list));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to calling callback" + e);
            }
        }
        this.mCallbacks.finishBroadcast();
    }
}
