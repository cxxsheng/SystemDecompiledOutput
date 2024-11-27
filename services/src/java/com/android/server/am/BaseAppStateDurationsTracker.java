package com.android.server.am;

/* loaded from: classes.dex */
abstract class BaseAppStateDurationsTracker<T extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, U extends com.android.server.am.BaseAppStateDurations> extends com.android.server.am.BaseAppStateEventsTracker<T, U> {
    static final boolean DEBUG_BASE_APP_STATE_DURATION_TRACKER = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations> mUidStateDurations;

    BaseAppStateDurationsTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<T>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mUidStateDurations = new android.util.SparseArray<>();
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void onUidProcStateChanged(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mPkgEvents.getMap().indexOfKey(i) < 0) {
                    return;
                }
                onUidProcStateChangedUncheckedLocked(i, i2);
                com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations = this.mUidStateDurations.get(i);
                if (uidStateDurations == null) {
                    uidStateDurations = new com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations(i, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
                    this.mUidStateDurations.put(i, uidStateDurations);
                }
                uidStateDurations.addEvent(i2 < 4, android.os.SystemClock.elapsedRealtime());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void onUidGone(int i) {
        onUidProcStateChanged(i, 20);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void trimLocked(long j) {
        super.trimLocked(j);
        for (int size = this.mUidStateDurations.size() - 1; size >= 0; size--) {
            com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations valueAt = this.mUidStateDurations.valueAt(size);
            valueAt.trim(j);
            if (valueAt.isEmpty()) {
                this.mUidStateDurations.removeAt(size);
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onUntrackingUidLocked(int i) {
        this.mUidStateDurations.remove(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    long getTotalDurations(java.lang.String str, int i, long j, int i2, boolean z) {
        com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations;
        synchronized (this.mLock) {
            try {
                com.android.server.am.BaseAppStateDurations baseAppStateDurations = (com.android.server.am.BaseAppStateDurations) this.mPkgEvents.get(i, str);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                if (z && (uidStateDurations = this.mUidStateDurations.get(i)) != null && !uidStateDurations.isEmpty()) {
                    com.android.server.am.BaseAppStateDurations baseAppStateDurations2 = (com.android.server.am.BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
                    baseAppStateDurations2.subtract(uidStateDurations, i2, 0);
                    return baseAppStateDurations2.getTotalDurations(j, i2);
                }
                return baseAppStateDurations.getTotalDurations(j, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getTotalDurations(java.lang.String str, int i, long j, int i2) {
        return getTotalDurations(str, i, j, i2, true);
    }

    long getTotalDurations(java.lang.String str, int i, long j) {
        return getTotalDurations(str, i, j, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    long getTotalDurations(int i, long j, int i2, boolean z) {
        com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations;
        synchronized (this.mLock) {
            try {
                com.android.server.am.BaseAppStateDurations baseAppStateDurations = (com.android.server.am.BaseAppStateDurations) getUidEventsLocked(i);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                if (z && (uidStateDurations = this.mUidStateDurations.get(i)) != null && !uidStateDurations.isEmpty()) {
                    baseAppStateDurations.subtract(uidStateDurations, i2, 0);
                }
                return baseAppStateDurations.getTotalDurations(j, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getTotalDurations(int i, long j, int i2) {
        return getTotalDurations(i, j, i2, true);
    }

    long getTotalDurations(int i, long j) {
        return getTotalDurations(i, j, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    long getTotalDurationsSince(java.lang.String str, int i, long j, long j2, int i2, boolean z) {
        com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations;
        synchronized (this.mLock) {
            try {
                com.android.server.am.BaseAppStateDurations baseAppStateDurations = (com.android.server.am.BaseAppStateDurations) this.mPkgEvents.get(i, str);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                if (z && (uidStateDurations = this.mUidStateDurations.get(i)) != null && !uidStateDurations.isEmpty()) {
                    com.android.server.am.BaseAppStateDurations baseAppStateDurations2 = (com.android.server.am.BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
                    baseAppStateDurations2.subtract(uidStateDurations, i2, 0);
                    return baseAppStateDurations2.getTotalDurationsSince(j, j2, i2);
                }
                return baseAppStateDurations.getTotalDurationsSince(j, j2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getTotalDurationsSince(java.lang.String str, int i, long j, long j2, int i2) {
        return getTotalDurationsSince(str, i, j, j2, i2, true);
    }

    long getTotalDurationsSince(java.lang.String str, int i, long j, long j2) {
        return getTotalDurationsSince(str, i, j, j2, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    long getTotalDurationsSince(int i, long j, long j2, int i2, boolean z) {
        com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations;
        synchronized (this.mLock) {
            try {
                com.android.server.am.BaseAppStateDurations baseAppStateDurations = (com.android.server.am.BaseAppStateDurations) getUidEventsLocked(i);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                if (z && (uidStateDurations = this.mUidStateDurations.get(i)) != null && !uidStateDurations.isEmpty()) {
                    baseAppStateDurations.subtract(uidStateDurations, i2, 0);
                }
                return baseAppStateDurations.getTotalDurationsSince(j, j2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getTotalDurationsSince(int i, long j, long j2, int i2) {
        return getTotalDurationsSince(i, j, j2, i2, true);
    }

    long getTotalDurationsSince(int i, long j, long j2) {
        return getTotalDurationsSince(i, j, j2, 0);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        super.reset();
        synchronized (this.mLock) {
            this.mUidStateDurations.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpEventLocked(java.io.PrintWriter printWriter, java.lang.String str, U u, long j) {
        com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations = this.mUidStateDurations.get(u.mUid);
        printWriter.print("  " + str);
        printWriter.println("(bg only)");
        if (uidStateDurations == null || uidStateDurations.isEmpty()) {
            u.dump(printWriter, "    " + str, j);
            return;
        }
        com.android.server.am.BaseAppStateDurations baseAppStateDurations = (com.android.server.am.BaseAppStateDurations) createAppStateEvents(u);
        baseAppStateDurations.subtract(uidStateDurations, 0);
        baseAppStateDurations.dump(printWriter, "    " + str, j);
        printWriter.print("  " + str);
        printWriter.println("(fg + bg)");
        u.dump(printWriter, "    " + str, j);
    }

    static class SimplePackageDurations extends com.android.server.am.BaseAppStateDurations<com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent> {
        static final int DEFAULT_INDEX = 0;

        /* JADX WARN: Multi-variable type inference failed */
        SimplePackageDurations(int i, java.lang.String str, com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
            super(i, str, 1, "ActivityManager", maxTrackingDurationConfig);
            this.mEvents[0] = new java.util.LinkedList();
        }

        SimplePackageDurations(com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations) {
            super(simplePackageDurations);
        }

        void addEvent(boolean z, long j) {
            addEvent(z, (boolean) new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(j), 0);
        }

        long getTotalDurations(long j) {
            return getTotalDurations(j, 0);
        }

        long getTotalDurationsSince(long j, long j2) {
            return getTotalDurationsSince(j, j2, 0);
        }

        boolean isActive() {
            return isActive(0);
        }

        @Override // com.android.server.am.BaseAppStateEvents
        java.lang.String formatEventTypeLabel(int i) {
            return "";
        }
    }

    static class UidStateDurations extends com.android.server.am.BaseAppStateDurationsTracker.SimplePackageDurations {
        UidStateDurations(int i, com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
            super(i, "", maxTrackingDurationConfig);
        }

        UidStateDurations(com.android.server.am.BaseAppStateDurationsTracker.UidStateDurations uidStateDurations) {
            super(uidStateDurations);
        }
    }
}
