package com.android.server.wm;

/* loaded from: classes3.dex */
class LaunchObserverRegistryImpl extends com.android.server.wm.ActivityMetricsLaunchObserver implements com.android.server.wm.ActivityMetricsLaunchObserverRegistry {
    private final android.os.Handler mHandler;
    private final java.util.ArrayList<com.android.server.wm.ActivityMetricsLaunchObserver> mList = new java.util.ArrayList<>();

    public LaunchObserverRegistryImpl(android.os.Looper looper) {
        this.mHandler = new android.os.Handler(looper);
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserverRegistry
    public void registerLaunchObserver(com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleRegisterLaunchObserver((com.android.server.wm.ActivityMetricsLaunchObserver) obj2);
            }
        }, this, activityMetricsLaunchObserver));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserverRegistry
    public void unregisterLaunchObserver(com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleUnregisterLaunchObserver((com.android.server.wm.ActivityMetricsLaunchObserver) obj2);
            }
        }, this, activityMetricsLaunchObserver));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onIntentStarted(android.content.Intent intent, long j) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda5
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnIntentStarted((android.content.Intent) obj2, ((java.lang.Long) obj3).longValue());
            }
        }, this, intent, java.lang.Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onIntentFailed(long j) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnIntentFailed(((java.lang.Long) obj2).longValue());
            }
        }, this, java.lang.Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onActivityLaunched(long j, android.content.ComponentName componentName, int i, int i2) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda3
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnActivityLaunched(((java.lang.Long) obj2).longValue(), (android.content.ComponentName) obj3, ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
            }
        }, this, java.lang.Long.valueOf(j), componentName, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onActivityLaunchCancelled(long j) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnActivityLaunchCancelled(((java.lang.Long) obj2).longValue());
            }
        }, this, java.lang.Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onActivityLaunchFinished(long j, android.content.ComponentName componentName, long j2, int i) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda4
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnActivityLaunchFinished(((java.lang.Long) obj2).longValue(), (android.content.ComponentName) obj3, ((java.lang.Long) obj4).longValue(), ((java.lang.Integer) obj5).intValue());
            }
        }, this, java.lang.Long.valueOf(j), componentName, java.lang.Long.valueOf(j2), java.lang.Integer.valueOf(i)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public void onReportFullyDrawn(long j, long j2) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda2
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.wm.LaunchObserverRegistryImpl) obj).handleOnReportFullyDrawn(((java.lang.Long) obj2).longValue(), ((java.lang.Long) obj3).longValue());
            }
        }, this, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRegisterLaunchObserver(com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver) {
        this.mList.add(activityMetricsLaunchObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnregisterLaunchObserver(com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver) {
        this.mList.remove(activityMetricsLaunchObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnIntentStarted(android.content.Intent intent, long j) {
        for (int i = 0; i < this.mList.size(); i++) {
            this.mList.get(i).onIntentStarted(intent, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnIntentFailed(long j) {
        for (int i = 0; i < this.mList.size(); i++) {
            this.mList.get(i).onIntentFailed(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityLaunched(long j, android.content.ComponentName componentName, int i, int i2) {
        for (int i3 = 0; i3 < this.mList.size(); i3++) {
            this.mList.get(i3).onActivityLaunched(j, componentName, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityLaunchCancelled(long j) {
        for (int i = 0; i < this.mList.size(); i++) {
            this.mList.get(i).onActivityLaunchCancelled(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityLaunchFinished(long j, android.content.ComponentName componentName, long j2, int i) {
        for (int i2 = 0; i2 < this.mList.size(); i2++) {
            this.mList.get(i2).onActivityLaunchFinished(j, componentName, j2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReportFullyDrawn(long j, long j2) {
        for (int i = 0; i < this.mList.size(); i++) {
            this.mList.get(i).onReportFullyDrawn(j, j2);
        }
    }
}
