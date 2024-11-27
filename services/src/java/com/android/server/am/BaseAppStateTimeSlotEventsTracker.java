package com.android.server.am;

/* loaded from: classes.dex */
abstract class BaseAppStateTimeSlotEventsTracker<T extends com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy, U extends com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents> extends com.android.server.am.BaseAppStateEventsTracker<T, U> {
    static final boolean DEBUG_APP_STATE_TIME_SLOT_EVENT_TRACKER = false;
    static final java.lang.String TAG = "BaseAppStateTimeSlotEventsTracker";
    private com.android.server.am.BaseAppStateTimeSlotEventsTracker.H mHandler;
    private final android.util.ArrayMap<U, java.lang.Integer> mTmpPkgs;

    BaseAppStateTimeSlotEventsTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<T>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mTmpPkgs = new android.util.ArrayMap<>();
        this.mHandler = new com.android.server.am.BaseAppStateTimeSlotEventsTracker.H(this);
    }

    void onNewEvent(java.lang.String str, int i) {
        this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void handleNewEvent(java.lang.String str, int i) {
        int totalEvents;
        boolean z;
        if (((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).shouldExempt(str, i) != -1) {
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents = (com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents) this.mPkgEvents.get(i, str);
                if (simpleAppStateTimeslotEvents == null) {
                    simpleAppStateTimeslotEvents = (com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents) createAppStateEvents(i, str);
                    this.mPkgEvents.put(i, str, simpleAppStateTimeslotEvents);
                }
                simpleAppStateTimeslotEvents.addEvent(elapsedRealtime, 0);
                totalEvents = simpleAppStateTimeslotEvents.getTotalEvents(elapsedRealtime, 0);
                z = totalEvents >= ((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).getNumOfEventsThreshold();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            ((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).onExcessiveEvents(str, i, totalEvents, elapsedRealtime);
        }
    }

    void onMonitorEnabled(boolean z) {
        if (!z) {
            synchronized (this.mLock) {
                this.mPkgEvents.clear();
            }
        }
    }

    void onNumOfEventsThresholdChanged(int i) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                android.util.SparseArray map = this.mPkgEvents.getMap();
                for (int size = map.size() - 1; size >= 0; size--) {
                    android.util.ArrayMap arrayMap = (android.util.ArrayMap) map.valueAt(size);
                    for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                        com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents = (com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents) arrayMap.valueAt(size2);
                        int totalEvents = simpleAppStateTimeslotEvents.getTotalEvents(elapsedRealtime, 0);
                        if (totalEvents >= i) {
                            this.mTmpPkgs.put(simpleAppStateTimeslotEvents, java.lang.Integer.valueOf(totalEvents));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int size3 = this.mTmpPkgs.size() - 1; size3 >= 0; size3--) {
            U keyAt = this.mTmpPkgs.keyAt(size3);
            ((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).onExcessiveEvents(keyAt.mPackageName, keyAt.mUid, this.mTmpPkgs.valueAt(size3).intValue(), elapsedRealtime);
        }
        this.mTmpPkgs.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    int getTotalEventsLocked(int i, long j) {
        com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents = (com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents) getUidEventsLocked(i);
        if (simpleAppStateTimeslotEvents == null) {
            return 0;
        }
        return simpleAppStateTimeslotEvents.getTotalEvents(j, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trimEvents() {
        trim(java.lang.Math.max(0L, android.os.SystemClock.elapsedRealtime() - ((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).getMaxTrackingDuration()));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserInteractionStarted(java.lang.String str, int i) {
        ((com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy) this.mInjector.getPolicy()).onUserInteractionStarted(str, i);
    }

    static class H extends android.os.Handler {
        static final int MSG_NEW_EVENT = 0;
        final com.android.server.am.BaseAppStateTimeSlotEventsTracker mTracker;

        H(com.android.server.am.BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker) {
            super(baseAppStateTimeSlotEventsTracker.mBgHandler.getLooper());
            this.mTracker = baseAppStateTimeSlotEventsTracker;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    this.mTracker.handleNewEvent((java.lang.String) message.obj, message.arg1);
                    break;
            }
        }
    }

    static class BaseAppStateTimeSlotEventsPolicy<E extends com.android.server.am.BaseAppStateTimeSlotEventsTracker> extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy<E> {
        final int mDefaultNumOfEventsThreshold;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final com.android.internal.app.ProcessMap<java.lang.Long> mExcessiveEventPkgs;
        final java.lang.String mKeyNumOfEventsThreshold;

        @android.annotation.NonNull
        private final java.lang.Object mLock;
        volatile int mNumOfEventsThreshold;
        long mTimeSlotSize;

        BaseAppStateTimeSlotEventsPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull E e, @android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull java.lang.String str2, long j, @android.annotation.NonNull java.lang.String str3, int i) {
            super(injector, e, str, z, str2, j);
            this.mExcessiveEventPkgs = new com.android.internal.app.ProcessMap<>();
            this.mTimeSlotSize = 900000L;
            this.mKeyNumOfEventsThreshold = str3;
            this.mDefaultNumOfEventsThreshold = i;
            this.mLock = e.mLock;
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            super.onSystemReady();
            updateNumOfEventsThreshold();
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(java.lang.String str) {
            if (this.mKeyNumOfEventsThreshold.equals(str)) {
                updateNumOfEventsThreshold();
            } else {
                super.onPropertiesChanged(str);
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).onMonitorEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public void onMaxTrackingDurationChanged(long j) {
            android.os.Handler handler = ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).mBgHandler;
            final com.android.server.am.BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker = (com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker;
            java.util.Objects.requireNonNull(baseAppStateTimeSlotEventsTracker);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.am.BaseAppStateTimeSlotEventsTracker$BaseAppStateTimeSlotEventsPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BaseAppStateTimeSlotEventsTracker.this.trimEvents();
                }
            });
        }

        private void updateNumOfEventsThreshold() {
            int i = android.provider.DeviceConfig.getInt("activity_manager", this.mKeyNumOfEventsThreshold, this.mDefaultNumOfEventsThreshold);
            if (i != this.mNumOfEventsThreshold) {
                this.mNumOfEventsThreshold = i;
                ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).onNumOfEventsThresholdChanged(i);
            }
        }

        int getNumOfEventsThreshold() {
            return this.mNumOfEventsThreshold;
        }

        long getTimeSlotSize() {
            return this.mTimeSlotSize;
        }

        @com.android.internal.annotations.VisibleForTesting
        void setTimeSlotSize(long j) {
            this.mTimeSlotSize = j;
        }

        java.lang.String getEventName() {
            return "event";
        }

        void onExcessiveEvents(java.lang.String str, int i, int i2, long j) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    if (((java.lang.Long) this.mExcessiveEventPkgs.get(str, i)) != null) {
                        z = false;
                    } else {
                        this.mExcessiveEventPkgs.put(str, i, java.lang.Long.valueOf(j));
                        z = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, 1536, 2, true);
            }
        }

        int shouldExempt(java.lang.String str, int i) {
            if (((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).isUidOnTop(i)) {
                return 12;
            }
            if (((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).mAppRestrictionController.hasForegroundServices(str, i)) {
                return 14;
            }
            int shouldExemptUid = shouldExemptUid(i);
            if (shouldExemptUid == -1) {
                return -1;
            }
            return shouldExemptUid;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0023 A[Catch: all -> 0x001e, DONT_GENERATE, TryCatch #0 {all -> 0x001e, blocks: (B:4:0x0003, B:6:0x000f, B:11:0x0023, B:15:0x0027, B:17:0x0029), top: B:3:0x0003 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
        @Override // com.android.server.am.BaseAppStatePolicy
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int getProposedRestrictionLevel(java.lang.String str, int i, int i2) {
            int i3;
            synchronized (this.mLock) {
                try {
                    if (this.mExcessiveEventPkgs.get(str, i) != null && ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).mAppRestrictionController.isAutoRestrictAbusiveAppEnabled()) {
                        i3 = 40;
                        return i2 <= 40 ? i3 : i2 == 40 ? 30 : 0;
                    }
                    i3 = 30;
                    if (i2 <= 40) {
                    }
                } finally {
                }
            }
        }

        void onUserInteractionStarted(java.lang.String str, int i) {
            synchronized (this.mLock) {
                this.mExcessiveEventPkgs.remove(str, i);
            }
            ((com.android.server.am.BaseAppStateTimeSlotEventsTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, 768, 3, true);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            super.dump(printWriter, str);
            if (isEnabled()) {
                printWriter.print(str);
                printWriter.print(this.mKeyNumOfEventsThreshold);
                printWriter.print('=');
                printWriter.println(this.mNumOfEventsThreshold);
            }
            printWriter.print(str);
            printWriter.print("event_time_slot_size=");
            printWriter.println(getTimeSlotSize());
        }
    }

    static class SimpleAppStateTimeslotEvents extends com.android.server.am.BaseAppStateTimeSlotEvents {
        static final int DEFAULT_INDEX = 0;
        static final long DEFAULT_TIME_SLOT_SIZE = 900000;
        static final long DEFAULT_TIME_SLOT_SIZE_DEBUG = 60000;

        SimpleAppStateTimeslotEvents(int i, @android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
            super(i, str, 1, j, str2, maxTrackingDurationConfig);
        }

        SimpleAppStateTimeslotEvents(com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents) {
            super(simpleAppStateTimeslotEvents);
        }

        @Override // com.android.server.am.BaseAppStateEvents
        java.lang.String formatEventTypeLabel(int i) {
            return "";
        }

        @Override // com.android.server.am.BaseAppStateEvents
        java.lang.String formatEventSummary(long j, int i) {
            if (this.mEvents[0] == null || this.mEvents[0].size() == 0) {
                return "(none)";
            }
            return "total=" + getTotalEvents(j, 0) + ", latest=" + getTotalEventsSince(this.mCurSlotStartTime[0], j, 0) + "(slot=" + android.util.TimeUtils.formatTime(this.mCurSlotStartTime[0], j) + ")";
        }
    }
}
