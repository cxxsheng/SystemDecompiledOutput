package com.android.server.am;

/* loaded from: classes.dex */
final class AppMediaSessionTracker extends com.android.server.am.BaseAppStateDurationsTracker<com.android.server.am.AppMediaSessionTracker.AppMediaSessionPolicy, com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations> {
    static final boolean DEBUG_MEDIA_SESSION_TRACKER = false;
    static final java.lang.String TAG = "ActivityManager";
    private final android.os.HandlerExecutor mHandlerExecutor;
    private final android.media.session.MediaSessionManager.OnActiveSessionsChangedListener mSessionsChangedListener;
    private final com.android.internal.app.ProcessMap<java.lang.Boolean> mTmpMediaControllers;

    AppMediaSessionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppMediaSessionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppMediaSessionTracker.AppMediaSessionPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mSessionsChangedListener = new android.media.session.MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.server.am.AppMediaSessionTracker$$ExternalSyntheticLambda0
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(java.util.List list) {
                com.android.server.am.AppMediaSessionTracker.this.handleMediaSessionChanged(list);
            }
        };
        this.mTmpMediaControllers = new com.android.internal.app.ProcessMap<>();
        this.mHandlerExecutor = new android.os.HandlerExecutor(this.mBgHandler);
        this.mInjector.setPolicy(new com.android.server.am.AppMediaSessionTracker.AppMediaSessionPolicy(this.mInjector, this));
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations createAppStateEvents(int i, java.lang.String str) {
        return new com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations(i, str, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations createAppStateEvents(com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations) {
        return new com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations(simplePackageDurations);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBgMediaSessionMonitorEnabled(boolean z) {
        if (z) {
            this.mInjector.getMediaSessionManager().addOnActiveSessionsChangedListener(null, android.os.UserHandle.ALL, this.mHandlerExecutor, this.mSessionsChangedListener);
        } else {
            this.mInjector.getMediaSessionManager().removeOnActiveSessionsChangedListener(this.mSessionsChangedListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMediaSessionChanged(java.util.List<android.media.session.MediaController> list) {
        int i;
        int i2;
        if (list != null) {
            synchronized (this.mLock) {
                try {
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    for (android.media.session.MediaController mediaController : list) {
                        java.lang.String packageName = mediaController.getPackageName();
                        int uid = mediaController.getSessionToken().getUid();
                        com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations = (com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations) this.mPkgEvents.get(uid, packageName);
                        if (simplePackageDurations == null) {
                            simplePackageDurations = createAppStateEvents(uid, packageName);
                            this.mPkgEvents.put(uid, packageName, simplePackageDurations);
                        }
                        if (!simplePackageDurations.isActive()) {
                            simplePackageDurations.addEvent(true, elapsedRealtime);
                            notifyListenersOnStateChange(simplePackageDurations.mUid, simplePackageDurations.mPackageName, true, elapsedRealtime, 1);
                        }
                        this.mTmpMediaControllers.put(packageName, uid, java.lang.Boolean.TRUE);
                    }
                    android.util.SparseArray map = this.mPkgEvents.getMap();
                    for (int size = map.size() - 1; size >= 0; size--) {
                        android.util.ArrayMap arrayMap = (android.util.ArrayMap) map.valueAt(size);
                        int size2 = arrayMap.size() - 1;
                        while (size2 >= 0) {
                            com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations2 = (com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations) arrayMap.valueAt(size2);
                            if (!simplePackageDurations2.isActive()) {
                                i2 = size2;
                            } else if (this.mTmpMediaControllers.get(simplePackageDurations2.mPackageName, simplePackageDurations2.mUid) != null) {
                                i2 = size2;
                            } else {
                                simplePackageDurations2.addEvent(false, elapsedRealtime);
                                i2 = size2;
                                notifyListenersOnStateChange(simplePackageDurations2.mUid, simplePackageDurations2.mPackageName, false, elapsedRealtime, 1);
                            }
                            size2 = i2 - 1;
                        }
                    }
                } finally {
                }
            }
            this.mTmpMediaControllers.clear();
            return;
        }
        synchronized (this.mLock) {
            try {
                android.util.SparseArray map2 = this.mPkgEvents.getMap();
                long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                for (int size3 = map2.size() - 1; size3 >= 0; size3--) {
                    android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) map2.valueAt(size3);
                    int size4 = arrayMap2.size() - 1;
                    while (size4 >= 0) {
                        com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations3 = (com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations) arrayMap2.valueAt(size4);
                        if (!simplePackageDurations3.isActive()) {
                            i = size4;
                        } else {
                            simplePackageDurations3.addEvent(false, elapsedRealtime2);
                            i = size4;
                            notifyListenersOnStateChange(simplePackageDurations3.mUid, simplePackageDurations3.mPackageName, false, elapsedRealtime2, 1);
                        }
                        size4 = i - 1;
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trimDurations() {
        trim(java.lang.Math.max(0L, android.os.SystemClock.elapsedRealtime() - ((com.android.server.am.AppMediaSessionTracker.AppMediaSessionPolicy) this.mInjector.getPolicy()).getMaxTrackingDuration()));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 4;
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APP MEDIA SESSION TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    static final class AppMediaSessionPolicy extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy<com.android.server.am.AppMediaSessionTracker> {
        static final boolean DEFAULT_BG_MEDIA_SESSION_MONITOR_ENABLED = true;
        static final long DEFAULT_BG_MEDIA_SESSION_MONITOR_MAX_TRACKING_DURATION = 345600000;
        static final java.lang.String KEY_BG_MEADIA_SESSION_MONITOR_ENABLED = "bg_media_session_monitor_enabled";
        static final java.lang.String KEY_BG_MEDIA_SESSION_MONITOR_MAX_TRACKING_DURATION = "bg_media_session_monitor_max_tracking_duration";

        AppMediaSessionPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppMediaSessionTracker appMediaSessionTracker) {
            super(injector, appMediaSessionTracker, KEY_BG_MEADIA_SESSION_MONITOR_ENABLED, true, KEY_BG_MEDIA_SESSION_MONITOR_MAX_TRACKING_DURATION, DEFAULT_BG_MEDIA_SESSION_MONITOR_MAX_TRACKING_DURATION);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.AppMediaSessionTracker) this.mTracker).onBgMediaSessionMonitorEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public void onMaxTrackingDurationChanged(long j) {
            android.os.Handler handler = ((com.android.server.am.AppMediaSessionTracker) this.mTracker).mBgHandler;
            final com.android.server.am.AppMediaSessionTracker appMediaSessionTracker = (com.android.server.am.AppMediaSessionTracker) this.mTracker;
            java.util.Objects.requireNonNull(appMediaSessionTracker);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.am.AppMediaSessionTracker$AppMediaSessionPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.AppMediaSessionTracker.this.trimDurations();
                }
            });
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        java.lang.String getExemptionReasonString(java.lang.String str, int i, int i2) {
            return "n/a";
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP MEDIA SESSION TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }
}
