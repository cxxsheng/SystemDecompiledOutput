package com.android.server.am;

/* loaded from: classes.dex */
abstract class BaseAppStateEventsTracker<T extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, U extends com.android.server.am.BaseAppStateEvents> extends com.android.server.am.BaseAppStateTracker<T> implements com.android.server.am.BaseAppStateEvents.Factory<U> {
    static final boolean DEBUG_BASE_APP_STATE_EVENTS_TRACKER = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.server.am.UidProcessMap<U> mPkgEvents;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.ArraySet<java.lang.Integer> mTopUids;

    BaseAppStateEventsTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<T>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mPkgEvents = new com.android.server.am.UidProcessMap<>();
        this.mTopUids = new android.util.ArraySet<>();
    }

    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        synchronized (this.mLock) {
            this.mPkgEvents.clear();
            this.mTopUids.clear();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    U getUidEventsLocked(int i) {
        android.util.ArrayMap<java.lang.String, U> arrayMap = this.mPkgEvents.getMap().get(i);
        U u = null;
        if (arrayMap == null) {
            return null;
        }
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            U valueAt = arrayMap.valueAt(size);
            if (valueAt != null) {
                if (u == null) {
                    u = (U) createAppStateEvents(i, valueAt.mPackageName);
                }
                u.add(valueAt);
            }
        }
        return u;
    }

    void trim(long j) {
        synchronized (this.mLock) {
            trimLocked(j);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void trimLocked(long j) {
        android.util.SparseArray<android.util.ArrayMap<java.lang.String, U>> map = this.mPkgEvents.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            android.util.ArrayMap<java.lang.String, U> valueAt = map.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                U valueAt2 = valueAt.valueAt(size2);
                valueAt2.trim(j);
                if (valueAt2.isEmpty()) {
                    valueAt.removeAt(size2);
                }
            }
            if (valueAt.size() == 0) {
                map.removeAt(size);
            }
        }
    }

    boolean isUidOnTop(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mTopUids.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onUntrackingUidLocked(int i) {
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUidProcStateChanged(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mPkgEvents.getMap().indexOfKey(i) < 0) {
                    return;
                }
                onUidProcStateChangedUncheckedLocked(i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onUidProcStateChangedUncheckedLocked(int i, int i2) {
        if (i2 < 4) {
            this.mTopUids.add(java.lang.Integer.valueOf(i));
        } else {
            this.mTopUids.remove(java.lang.Integer.valueOf(i));
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUidGone(int i) {
        synchronized (this.mLock) {
            this.mTopUids.remove(java.lang.Integer.valueOf(i));
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUidRemoved(int i) {
        synchronized (this.mLock) {
            this.mPkgEvents.getMap().remove(i);
            onUntrackingUidLocked(i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.util.ArrayMap<java.lang.String, U>> map = this.mPkgEvents.getMap();
                for (int size = map.size() - 1; size >= 0; size--) {
                    int keyAt = map.keyAt(size);
                    if (android.os.UserHandle.getUserId(keyAt) == i) {
                        map.removeAt(size);
                        onUntrackingUidLocked(keyAt);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy baseAppStateEventsPolicy = (com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy) this.mInjector.getPolicy();
        synchronized (this.mLock) {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                android.util.SparseArray<android.util.ArrayMap<java.lang.String, U>> map = this.mPkgEvents.getMap();
                for (int size = map.size() - 1; size >= 0; size--) {
                    int keyAt = map.keyAt(size);
                    android.util.ArrayMap<java.lang.String, U> valueAt = map.valueAt(size);
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        java.lang.String keyAt2 = valueAt.keyAt(size2);
                        U valueAt2 = valueAt.valueAt(size2);
                        dumpEventHeaderLocked(printWriter, str, keyAt2, keyAt, valueAt2, baseAppStateEventsPolicy);
                        dumpEventLocked(printWriter, str, valueAt2, elapsedRealtime);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dumpOthers(printWriter, str);
        baseAppStateEventsPolicy.dump(printWriter, str);
    }

    void dumpOthers(java.io.PrintWriter printWriter, java.lang.String str) {
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dumpEventHeaderLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, int i, U u, T t) {
        printWriter.print(str);
        printWriter.print("* ");
        printWriter.print(str2);
        printWriter.print('/');
        printWriter.print(android.os.UserHandle.formatUid(i));
        printWriter.print(" exemption=");
        printWriter.println(t.getExemptionReasonString(str2, i, u.mExemptReason));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dumpEventLocked(java.io.PrintWriter printWriter, java.lang.String str, U u, long j) {
        u.dump(printWriter, "  " + str, j);
    }

    static abstract class BaseAppStateEventsPolicy<V extends com.android.server.am.BaseAppStateEventsTracker> extends com.android.server.am.BaseAppStatePolicy<V> implements com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig {
        final long mDefaultMaxTrackingDuration;

        @android.annotation.NonNull
        final java.lang.String mKeyMaxTrackingDuration;
        volatile long mMaxTrackingDuration;

        public abstract void onMaxTrackingDurationChanged(long j);

        BaseAppStateEventsPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector<?> injector, @android.annotation.NonNull V v, @android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull java.lang.String str2, long j) {
            super(injector, v, str, z);
            this.mKeyMaxTrackingDuration = str2;
            this.mDefaultMaxTrackingDuration = j;
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(java.lang.String str) {
            if (this.mKeyMaxTrackingDuration.equals(str)) {
                updateMaxTrackingDuration();
            } else {
                super.onPropertiesChanged(str);
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            super.onSystemReady();
            updateMaxTrackingDuration();
        }

        void updateMaxTrackingDuration() {
            long j = android.provider.DeviceConfig.getLong("activity_manager", this.mKeyMaxTrackingDuration, this.mDefaultMaxTrackingDuration);
            if (j != this.mMaxTrackingDuration) {
                this.mMaxTrackingDuration = j;
                onMaxTrackingDurationChanged(j);
            }
        }

        @Override // com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig
        public long getMaxTrackingDuration() {
            return this.mMaxTrackingDuration;
        }

        java.lang.String getExemptionReasonString(java.lang.String str, int i, int i2) {
            return android.os.PowerExemptionManager.reasonCodeToString(i2);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            super.dump(printWriter, str);
            if (isEnabled()) {
                printWriter.print(str);
                printWriter.print(this.mKeyMaxTrackingDuration);
                printWriter.print('=');
                printWriter.println(this.mMaxTrackingDuration);
            }
        }
    }

    static class SimplePackageEvents extends com.android.server.am.BaseAppStateTimeEvents {
        static final int DEFAULT_INDEX = 0;

        /* JADX WARN: Multi-variable type inference failed */
        SimplePackageEvents(int i, java.lang.String str, com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
            super(i, str, 1, "ActivityManager", maxTrackingDurationConfig);
            this.mEvents[0] = new java.util.LinkedList();
        }

        long getTotalEvents(long j) {
            return getTotalEvents(j, 0);
        }

        long getTotalEventsSince(long j, long j2) {
            return getTotalEventsSince(j, j2, 0);
        }

        @Override // com.android.server.am.BaseAppStateEvents
        java.lang.String formatEventTypeLabel(int i) {
            return "";
        }
    }
}
