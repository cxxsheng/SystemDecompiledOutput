package com.android.server.wm;

/* loaded from: classes3.dex */
class BackgroundLaunchProcessController {
    private static final long DEFAULT_RESCIND_BAL_FG_PRIVILEGES_BOUND_SERVICE = 261072174;
    private static final java.lang.String TAG = "ActivityTaskManager";

    @android.annotation.Nullable
    private final com.android.server.wm.BackgroundActivityStartCallback mBackgroundActivityStartCallback;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.util.ArrayMap<android.os.Binder, android.app.BackgroundStartPrivileges> mBackgroundStartPrivileges;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.util.IntArray mBalOptInBoundClientUids;
    private final java.util.function.IntPredicate mUidHasActiveVisibleWindowPredicate;

    BackgroundLaunchProcessController(@android.annotation.NonNull java.util.function.IntPredicate intPredicate, @android.annotation.Nullable com.android.server.wm.BackgroundActivityStartCallback backgroundActivityStartCallback) {
        this.mUidHasActiveVisibleWindowPredicate = intPredicate;
        this.mBackgroundActivityStartCallback = backgroundActivityStartCallback;
    }

    com.android.server.wm.BackgroundActivityStartController.BalVerdict areBackgroundActivityStartsAllowed(int i, int i2, java.lang.String str, int i3, boolean z, boolean z2, boolean z3, long j, long j2, long j3) {
        boolean isBoundByForegroundUid;
        if (z3) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(6, true, "process instrumenting with background activity starts privileges");
        }
        if (isBackgroundStartAllowedByToken(i2, str, z)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(6, true, "process allowed by token");
        }
        if (com.android.window.flags.Flags.balRespectAppSwitchStateWhenCheckBoundByForegroundUid()) {
            isBoundByForegroundUid = i3 != 0 && isBoundByForegroundUid();
        } else {
            isBoundByForegroundUid = isBoundByForegroundUid();
        }
        if (isBoundByForegroundUid) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(4, false, "process bound by foreground uid");
        }
        if (z2 && i3 != 0) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(9, false, "process has activity in foreground task");
        }
        if (i3 == 2) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if ((uptimeMillis - j2 < com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY || uptimeMillis - j3 < com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY) && (j2 > j || j3 > j)) {
                return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(8, true, "within 10000ms grace period");
            }
        }
        return com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK;
    }

    private boolean isBackgroundStartAllowedByToken(int i, java.lang.String str, boolean z) {
        synchronized (this) {
            try {
                if (this.mBackgroundStartPrivileges != null && !this.mBackgroundStartPrivileges.isEmpty()) {
                    if (z) {
                        int size = this.mBackgroundStartPrivileges.size();
                        while (true) {
                            int i2 = size - 1;
                            if (size <= 0) {
                                return false;
                            }
                            if (this.mBackgroundStartPrivileges.valueAt(i2).allowsBackgroundFgsStarts()) {
                                return true;
                            }
                            size = i2;
                        }
                    } else if (this.mBackgroundActivityStartCallback == null) {
                        int size2 = this.mBackgroundStartPrivileges.size();
                        while (true) {
                            int i3 = size2 - 1;
                            if (size2 <= 0) {
                                return false;
                            }
                            if (this.mBackgroundStartPrivileges.valueAt(i3).allowsBackgroundActivityStarts()) {
                                return true;
                            }
                            size2 = i3;
                        }
                    } else {
                        java.util.List<android.os.IBinder> originatingTokensThatAllowBal = getOriginatingTokensThatAllowBal();
                        if (originatingTokensThatAllowBal.isEmpty()) {
                            return false;
                        }
                        return this.mBackgroundActivityStartCallback.isActivityStartAllowed(originatingTokensThatAllowBal, i, str);
                    }
                }
                return false;
            } finally {
            }
        }
    }

    private java.util.List<android.os.IBinder> getOriginatingTokensThatAllowBal() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mBackgroundStartPrivileges.size();
        while (true) {
            int i = size - 1;
            if (size > 0) {
                android.app.BackgroundStartPrivileges valueAt = this.mBackgroundStartPrivileges.valueAt(i);
                if (valueAt.allowsBackgroundActivityStarts()) {
                    arrayList.add(valueAt.getOriginatingToken());
                }
                size = i;
            } else {
                return arrayList;
            }
        }
    }

    private boolean isBoundByForegroundUid() {
        synchronized (this) {
            try {
                if (this.mBalOptInBoundClientUids != null) {
                    for (int size = this.mBalOptInBoundClientUids.size() - 1; size >= 0; size--) {
                        if (this.mUidHasActiveVisibleWindowPredicate.test(this.mBalOptInBoundClientUids.get(size))) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearBalOptInBoundClientUids() {
        synchronized (this) {
            try {
                if (this.mBalOptInBoundClientUids == null) {
                    this.mBalOptInBoundClientUids = new android.util.IntArray();
                } else {
                    this.mBalOptInBoundClientUids.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addBoundClientUid(int i, java.lang.String str, long j) {
        if (!android.app.compat.CompatChanges.isChangeEnabled(DEFAULT_RESCIND_BAL_FG_PRIVILEGES_BOUND_SERVICE, str, android.os.UserHandle.getUserHandleForUid(i)) || (j & 512) != 0) {
            if (this.mBalOptInBoundClientUids == null) {
                this.mBalOptInBoundClientUids = new android.util.IntArray();
            }
            if (this.mBalOptInBoundClientUids.indexOf(i) == -1) {
                this.mBalOptInBoundClientUids.add(i);
            }
        }
    }

    void addOrUpdateAllowBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        java.util.Objects.requireNonNull(binder, "entity");
        java.util.Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
        com.android.internal.util.Preconditions.checkArgument(backgroundStartPrivileges.allowsAny(), "backgroundStartPrivileges does not allow anything");
        synchronized (this) {
            try {
                if (this.mBackgroundStartPrivileges == null) {
                    this.mBackgroundStartPrivileges = new android.util.ArrayMap<>();
                }
                this.mBackgroundStartPrivileges.put(binder, backgroundStartPrivileges);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeAllowBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder) {
        java.util.Objects.requireNonNull(binder, "entity");
        synchronized (this) {
            try {
                if (this.mBackgroundStartPrivileges != null) {
                    this.mBackgroundStartPrivileges.remove(binder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean canCloseSystemDialogsByToken(int i) {
        if (this.mBackgroundActivityStartCallback == null) {
            return false;
        }
        synchronized (this) {
            try {
                if (this.mBackgroundStartPrivileges != null && !this.mBackgroundStartPrivileges.isEmpty()) {
                    return this.mBackgroundActivityStartCallback.canCloseSystemDialogs(getOriginatingTokensThatAllowBal(), i);
                }
                return false;
            } finally {
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this) {
            try {
                if (this.mBackgroundStartPrivileges != null && !this.mBackgroundStartPrivileges.isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("Background activity start tokens (token: originating token):");
                    for (int size = this.mBackgroundStartPrivileges.size() - 1; size >= 0; size--) {
                        printWriter.print(str);
                        printWriter.print("  - ");
                        printWriter.print(this.mBackgroundStartPrivileges.keyAt(size));
                        printWriter.print(": ");
                        printWriter.println(this.mBackgroundStartPrivileges.valueAt(size));
                    }
                }
                if (this.mBalOptInBoundClientUids != null && this.mBalOptInBoundClientUids.size() > 0) {
                    printWriter.print(str);
                    printWriter.print("BoundClientUids:");
                    printWriter.println(java.util.Arrays.toString(this.mBalOptInBoundClientUids.toArray()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
