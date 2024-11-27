package com.android.server.usage;

/* loaded from: classes2.dex */
class BroadcastResponseStatsTracker {
    static final int NOTIFICATION_EVENT_TYPE_CANCELLED = 2;
    static final int NOTIFICATION_EVENT_TYPE_POSTED = 0;
    static final int NOTIFICATION_EVENT_TYPE_UPDATED = 1;
    static final java.lang.String TAG = "ResponseStatsTracker";
    private com.android.server.usage.AppStandbyInternal mAppStandby;
    private final android.content.Context mContext;
    private android.app.role.RoleManager mRoleManager;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<com.android.server.usage.UserBroadcastEvents> mUserBroadcastEvents = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats>> mUserResponseStats = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>>> mExemptedRoleHoldersCache = new android.util.SparseArray<>();
    private final android.app.role.OnRoleHoldersChangedListener mRoleHoldersChangedListener = new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.usage.BroadcastResponseStatsTracker$$ExternalSyntheticLambda0
        public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
            com.android.server.usage.BroadcastResponseStatsTracker.this.onRoleHoldersChanged(str, userHandle);
        }
    };
    private com.android.server.usage.BroadcastResponseStatsLogger mLogger = new com.android.server.usage.BroadcastResponseStatsLogger();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotificationEventType {
    }

    BroadcastResponseStatsTracker(@android.annotation.NonNull com.android.server.usage.AppStandbyInternal appStandbyInternal, @android.annotation.NonNull android.content.Context context) {
        this.mAppStandby = appStandbyInternal;
        this.mContext = context;
    }

    void onSystemServicesReady(android.content.Context context) {
        this.mRoleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(com.android.internal.os.BackgroundThread.getExecutor(), this.mRoleHoldersChangedListener, android.os.UserHandle.ALL);
    }

    void reportBroadcastDispatchEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j, long j2, int i2) {
        this.mLogger.logBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
        if (i2 <= this.mAppStandby.getBroadcastResponseFgThresholdState() || doesPackageHoldExemptedRole(str, userHandle) || doesPackageHoldExemptedPermission(str, userHandle)) {
            return;
        }
        synchronized (this.mLock) {
            com.android.server.usage.BroadcastEvent orCreateBroadcastEvent = getOrCreateBroadcastEvent(getOrCreateBroadcastEventsLocked(str, userHandle), i, str, userHandle.getIdentifier(), j);
            orCreateBroadcastEvent.addTimestampMs(j2);
            recordAndPruneOldBroadcastDispatchTimestamps(orCreateBroadcastEvent);
        }
    }

    void reportNotificationPosted(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
        reportNotificationEvent(0, str, userHandle, j);
    }

    void reportNotificationUpdated(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
        reportNotificationEvent(1, str, userHandle, j);
    }

    void reportNotificationCancelled(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
        reportNotificationEvent(2, str, userHandle, j);
    }

    private void reportNotificationEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
        this.mLogger.logNotificationEvent(i, str, userHandle, j);
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.usage.BroadcastEvent> broadcastEventsLocked = getBroadcastEventsLocked(str, userHandle);
                if (broadcastEventsLocked == null) {
                    return;
                }
                long broadcastResponseWindowDurationMs = this.mAppStandby.getBroadcastResponseWindowDurationMs();
                long broadcastSessionsWithResponseDurationMs = this.mAppStandby.getBroadcastSessionsWithResponseDurationMs();
                boolean shouldNoteResponseEventForAllBroadcastSessions = this.mAppStandby.shouldNoteResponseEventForAllBroadcastSessions();
                for (int size = broadcastEventsLocked.size() - 1; size >= 0; size--) {
                    com.android.server.usage.BroadcastEvent valueAt = broadcastEventsLocked.valueAt(size);
                    recordAndPruneOldBroadcastDispatchTimestamps(valueAt);
                    android.util.LongArrayQueue timestampsMs = valueAt.getTimestampsMs();
                    long j2 = 0;
                    long j3 = 0;
                    while (timestampsMs.size() > 0 && timestampsMs.peekFirst() < j) {
                        long peekFirst = timestampsMs.peekFirst();
                        if (j - peekFirst <= broadcastResponseWindowDurationMs && peekFirst >= j3) {
                            if (j3 == j2 || shouldNoteResponseEventForAllBroadcastSessions) {
                                android.app.usage.BroadcastResponseStats orCreateBroadcastResponseStats = getOrCreateBroadcastResponseStats(valueAt);
                                orCreateBroadcastResponseStats.incrementBroadcastsDispatchedCount(1);
                                long j4 = peekFirst + broadcastSessionsWithResponseDurationMs;
                                switch (i) {
                                    case 0:
                                        orCreateBroadcastResponseStats.incrementNotificationsPostedCount(1);
                                        break;
                                    case 1:
                                        orCreateBroadcastResponseStats.incrementNotificationsUpdatedCount(1);
                                        break;
                                    case 2:
                                        orCreateBroadcastResponseStats.incrementNotificationsCancelledCount(1);
                                        break;
                                    default:
                                        android.util.Slog.wtf(TAG, "Unknown event: " + i);
                                        break;
                                }
                                j3 = j4;
                            }
                        }
                        timestampsMs.removeFirst();
                        j2 = 0;
                    }
                    if (timestampsMs.size() == 0) {
                        broadcastEventsLocked.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void recordAndPruneOldBroadcastDispatchTimestamps(com.android.server.usage.BroadcastEvent broadcastEvent) {
        android.util.LongArrayQueue timestampsMs = broadcastEvent.getTimestampsMs();
        long broadcastResponseWindowDurationMs = this.mAppStandby.getBroadcastResponseWindowDurationMs();
        long broadcastSessionsDurationMs = this.mAppStandby.getBroadcastSessionsDurationMs();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = 0;
        while (timestampsMs.size() > 0 && timestampsMs.peekFirst() < elapsedRealtime - broadcastResponseWindowDurationMs) {
            long peekFirst = timestampsMs.peekFirst();
            if (peekFirst >= j) {
                getOrCreateBroadcastResponseStats(broadcastEvent).incrementBroadcastsDispatchedCount(1);
                j = peekFirst + broadcastSessionsDurationMs;
            }
            timestampsMs.removeFirst();
        }
    }

    @android.annotation.NonNull
    java.util.List<android.app.usage.BroadcastResponseStats> queryBroadcastResponseStats(int i, @android.annotation.Nullable java.lang.String str, long j, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats> sparseArray = this.mUserResponseStats.get(i);
                if (sparseArray == null) {
                    return arrayList;
                }
                com.android.server.usage.UserBroadcastResponseStats userBroadcastResponseStats = sparseArray.get(i2);
                if (userBroadcastResponseStats == null) {
                    return arrayList;
                }
                userBroadcastResponseStats.populateAllBroadcastResponseStats(arrayList, str, j);
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearBroadcastResponseStats(int i, @android.annotation.Nullable java.lang.String str, long j, int i2) {
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats> sparseArray = this.mUserResponseStats.get(i);
                if (sparseArray == null) {
                    return;
                }
                com.android.server.usage.UserBroadcastResponseStats userBroadcastResponseStats = sparseArray.get(i2);
                if (userBroadcastResponseStats == null) {
                    return;
                }
                userBroadcastResponseStats.clearBroadcastResponseStats(str, j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearBroadcastEvents(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.UserBroadcastEvents userBroadcastEvents = this.mUserBroadcastEvents.get(i2);
                if (userBroadcastEvents == null) {
                    return;
                }
                userBroadcastEvents.clear(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isPackageExemptedFromBroadcastResponseStats(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            try {
                if (doesPackageHoldExemptedPermission(str, userHandle)) {
                    return true;
                }
                return doesPackageHoldExemptedRole(str, userHandle);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean doesPackageHoldExemptedRole(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.List broadcastResponseExemptedRoles = this.mAppStandby.getBroadcastResponseExemptedRoles();
        synchronized (this.mLock) {
            try {
                for (int size = broadcastResponseExemptedRoles.size() - 1; size >= 0; size--) {
                    if (com.android.internal.util.CollectionUtils.contains(getRoleHoldersLocked((java.lang.String) broadcastResponseExemptedRoles.get(size), userHandle), str)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean doesPackageHoldExemptedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        try {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, userHandle.getIdentifier());
            java.util.List broadcastResponseExemptedPermissions = this.mAppStandby.getBroadcastResponseExemptedPermissions();
            for (int size = broadcastResponseExemptedPermissions.size() - 1; size >= 0; size--) {
                if (this.mContext.checkPermission((java.lang.String) broadcastResponseExemptedPermissions.get(size), -1, packageUidAsUser) == 0) {
                    return true;
                }
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.util.List<java.lang.String> getRoleHoldersLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> arrayMap = this.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mExemptedRoleHoldersCache.put(userHandle.getIdentifier(), arrayMap);
        }
        java.util.List<java.lang.String> list = arrayMap.get(str);
        if (list == null && this.mRoleManager != null) {
            java.util.List roleHoldersAsUser = this.mRoleManager.getRoleHoldersAsUser(str, userHandle);
            arrayMap.put(str, roleHoldersAsUser);
            return roleHoldersAsUser;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> arrayMap = this.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
                if (arrayMap == null) {
                    return;
                }
                arrayMap.remove(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                this.mUserBroadcastEvents.remove(i);
                for (int size = this.mUserResponseStats.size() - 1; size >= 0; size--) {
                    this.mUserResponseStats.valueAt(size).remove(i);
                }
                this.mExemptedRoleHoldersCache.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onPackageRemoved(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.UserBroadcastEvents userBroadcastEvents = this.mUserBroadcastEvents.get(i);
                if (userBroadcastEvents != null) {
                    userBroadcastEvents.onPackageRemoved(str);
                }
                for (int size = this.mUserResponseStats.size() - 1; size >= 0; size--) {
                    com.android.server.usage.UserBroadcastResponseStats userBroadcastResponseStats = this.mUserResponseStats.valueAt(size).get(i);
                    if (userBroadcastResponseStats != null) {
                        userBroadcastResponseStats.onPackageRemoved(str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUidRemoved(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mUserBroadcastEvents.size() - 1; size >= 0; size--) {
                    this.mUserBroadcastEvents.valueAt(size).onUidRemoved(i);
                }
                this.mUserResponseStats.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.ArraySet<com.android.server.usage.BroadcastEvent> getBroadcastEventsLocked(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle) {
        com.android.server.usage.UserBroadcastEvents userBroadcastEvents = this.mUserBroadcastEvents.get(userHandle.getIdentifier());
        if (userBroadcastEvents == null) {
            return null;
        }
        return userBroadcastEvents.getBroadcastEvents(str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<com.android.server.usage.BroadcastEvent> getOrCreateBroadcastEventsLocked(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle) {
        com.android.server.usage.UserBroadcastEvents userBroadcastEvents = this.mUserBroadcastEvents.get(userHandle.getIdentifier());
        if (userBroadcastEvents == null) {
            userBroadcastEvents = new com.android.server.usage.UserBroadcastEvents();
            this.mUserBroadcastEvents.put(userHandle.getIdentifier(), userBroadcastEvents);
        }
        return userBroadcastEvents.getOrCreateBroadcastEvents(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.app.usage.BroadcastResponseStats getBroadcastResponseStats(@android.annotation.Nullable android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats> sparseArray, @android.annotation.NonNull com.android.server.usage.BroadcastEvent broadcastEvent) {
        com.android.server.usage.UserBroadcastResponseStats userBroadcastResponseStats;
        if (sparseArray == null || (userBroadcastResponseStats = sparseArray.get(broadcastEvent.getTargetUserId())) == null) {
            return null;
        }
        return userBroadcastResponseStats.getBroadcastResponseStats(broadcastEvent);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.usage.BroadcastResponseStats getOrCreateBroadcastResponseStats(@android.annotation.NonNull com.android.server.usage.BroadcastEvent broadcastEvent) {
        int sourceUid = broadcastEvent.getSourceUid();
        android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats> sparseArray = this.mUserResponseStats.get(sourceUid);
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray<>();
            this.mUserResponseStats.put(sourceUid, sparseArray);
        }
        com.android.server.usage.UserBroadcastResponseStats userBroadcastResponseStats = sparseArray.get(broadcastEvent.getTargetUserId());
        if (userBroadcastResponseStats == null) {
            userBroadcastResponseStats = new com.android.server.usage.UserBroadcastResponseStats();
            sparseArray.put(broadcastEvent.getTargetUserId(), userBroadcastResponseStats);
        }
        return userBroadcastResponseStats.getOrCreateBroadcastResponseStats(broadcastEvent);
    }

    private static com.android.server.usage.BroadcastEvent getOrCreateBroadcastEvent(android.util.ArraySet<com.android.server.usage.BroadcastEvent> arraySet, int i, java.lang.String str, int i2, long j) {
        com.android.server.usage.BroadcastEvent broadcastEvent = new com.android.server.usage.BroadcastEvent(i, str, i2, j);
        int indexOf = arraySet.indexOf(broadcastEvent);
        if (indexOf >= 0) {
            return arraySet.valueAt(indexOf);
        }
        arraySet.add(broadcastEvent);
        return broadcastEvent;
    }

    void dump(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Broadcast response stats:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            dumpBroadcastEventsLocked(indentingPrintWriter);
            indentingPrintWriter.println();
            dumpResponseStatsLocked(indentingPrintWriter);
            indentingPrintWriter.println();
            dumpRoleHoldersLocked(indentingPrintWriter);
            indentingPrintWriter.println();
            this.mLogger.dumpLogs(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpBroadcastEventsLocked(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Broadcast events:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mUserBroadcastEvents.size(); i++) {
            int keyAt = this.mUserBroadcastEvents.keyAt(i);
            com.android.server.usage.UserBroadcastEvents valueAt = this.mUserBroadcastEvents.valueAt(i);
            indentingPrintWriter.println("User " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            valueAt.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpResponseStatsLocked(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Response stats:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mUserResponseStats.size(); i++) {
            int keyAt = this.mUserResponseStats.keyAt(i);
            android.util.SparseArray<com.android.server.usage.UserBroadcastResponseStats> valueAt = this.mUserResponseStats.valueAt(i);
            indentingPrintWriter.println("Uid " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                int keyAt2 = valueAt.keyAt(i2);
                com.android.server.usage.UserBroadcastResponseStats valueAt2 = valueAt.valueAt(i2);
                indentingPrintWriter.println("User " + keyAt2 + ":");
                indentingPrintWriter.increaseIndent();
                valueAt2.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpRoleHoldersLocked(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Role holders:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mExemptedRoleHoldersCache.size(); i++) {
            int keyAt = this.mExemptedRoleHoldersCache.keyAt(i);
            android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> valueAt = this.mExemptedRoleHoldersCache.valueAt(i);
            indentingPrintWriter.println("User " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                java.lang.String keyAt2 = valueAt.keyAt(i2);
                java.util.List<java.lang.String> valueAt2 = valueAt.valueAt(i2);
                indentingPrintWriter.print(keyAt2 + ": ");
                for (int i3 = 0; i3 < valueAt2.size(); i3++) {
                    if (i3 > 0) {
                        indentingPrintWriter.print(", ");
                    }
                    indentingPrintWriter.print(valueAt2.get(i3));
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }
}
