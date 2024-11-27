package com.android.server.am;

/* loaded from: classes.dex */
public final class ContentProviderConnection extends android.os.Binder {
    public com.android.internal.app.procstats.AssociationState.SourceState association;
    public final com.android.server.am.ProcessRecord client;
    public final java.lang.String clientPackage;
    public boolean dead;
    final int mExpectedUserId;
    private int mNumStableIncs;
    private int mNumUnstableIncs;
    private java.lang.Object mProcStatsLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mStableCount;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mUnstableCount;
    public final com.android.server.am.ContentProviderRecord provider;
    public boolean waiting;
    private final java.lang.Object mLock = new java.lang.Object();
    public final long createTime = android.os.SystemClock.elapsedRealtime();

    public ContentProviderConnection(com.android.server.am.ContentProviderRecord contentProviderRecord, com.android.server.am.ProcessRecord processRecord, java.lang.String str, int i) {
        this.provider = contentProviderRecord;
        this.client = processRecord;
        this.clientPackage = str;
        this.mExpectedUserId = i;
    }

    public void startAssociationIfNeeded() {
        if (this.association == null && this.provider.proc != null) {
            if (this.provider.appInfo.uid != this.client.uid || !this.provider.info.processName.equals(this.client.processName)) {
                com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder = this.provider.proc.getPkgList().get(this.provider.name.getPackageName());
                if (processStateHolder == null) {
                    android.util.Slog.wtf("ActivityManager", "No package in referenced provider " + this.provider.name.toShortString() + ": proc=" + this.provider.proc);
                    return;
                }
                if (processStateHolder.pkg == null) {
                    android.util.Slog.wtf("ActivityManager", "Inactive holder in referenced provider " + this.provider.name.toShortString() + ": proc=" + this.provider.proc);
                    return;
                }
                this.mProcStatsLock = this.provider.proc.mService.mProcessStats.mLock;
                synchronized (this.mProcStatsLock) {
                    this.association = processStateHolder.pkg.getAssociationStateLocked(processStateHolder.state, this.provider.name.getClassName()).startSource(this.client.uid, this.client.processName, this.clientPackage);
                }
            }
        }
    }

    public void trackProcState(int i, int i2) {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.trackProcState(i, i2, android.os.SystemClock.uptimeMillis());
            }
        }
    }

    public void stopAssociation() {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.stop();
            }
            this.association = null;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ContentProviderConnection{");
        toShortString(sb);
        sb.append('}');
        return sb.toString();
    }

    public java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        toShortString(sb);
        return sb.toString();
    }

    public java.lang.String toClientString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        toClientString(sb);
        return sb.toString();
    }

    public void toShortString(java.lang.StringBuilder sb) {
        sb.append(this.provider.toShortString());
        sb.append("->");
        toClientString(sb);
    }

    public void toClientString(java.lang.StringBuilder sb) {
        sb.append(this.client.toShortString());
        synchronized (this.mLock) {
            sb.append(" s");
            sb.append(this.mStableCount);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.mNumStableIncs);
            sb.append(" u");
            sb.append(this.mUnstableCount);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.mNumUnstableIncs);
        }
        if (this.waiting) {
            sb.append(" WAITING");
        }
        if (this.dead) {
            sb.append(" DEAD");
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        sb.append(" ");
        android.util.TimeUtils.formatDuration(elapsedRealtime - this.createTime, sb);
    }

    public void initializeCount(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mStableCount = 1;
                    this.mNumStableIncs = 1;
                    this.mUnstableCount = 0;
                    this.mNumUnstableIncs = 0;
                } else {
                    this.mStableCount = 0;
                    this.mNumStableIncs = 0;
                    this.mUnstableCount = 1;
                    this.mNumUnstableIncs = 1;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int incrementCount(boolean z) {
        int i;
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mStableCount++;
                    this.mNumStableIncs++;
                } else {
                    this.mUnstableCount++;
                    this.mNumUnstableIncs++;
                }
                i = this.mStableCount + this.mUnstableCount;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int decrementCount(boolean z) {
        int i;
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mStableCount--;
                } else {
                    this.mUnstableCount--;
                }
                i = this.mStableCount + this.mUnstableCount;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public void adjustCounts(int i, int i2) {
        synchronized (this.mLock) {
            if (i > 0) {
                try {
                    this.mNumStableIncs += i;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            int i3 = this.mStableCount + i;
            if (i3 < 0) {
                throw new java.lang.IllegalStateException("stableCount < 0: " + i3);
            }
            if (i2 > 0) {
                this.mNumUnstableIncs += i2;
            }
            int i4 = this.mUnstableCount + i2;
            if (i4 < 0) {
                throw new java.lang.IllegalStateException("unstableCount < 0: " + i4);
            }
            if (i3 + i4 <= 0) {
                throw new java.lang.IllegalStateException("ref counts can't go to zero here: stable=" + i3 + " unstable=" + i4);
            }
            this.mStableCount = i3;
            this.mUnstableCount = i4;
        }
    }

    public int stableCount() {
        int i;
        synchronized (this.mLock) {
            i = this.mStableCount;
        }
        return i;
    }

    public int unstableCount() {
        int i;
        synchronized (this.mLock) {
            i = this.mUnstableCount;
        }
        return i;
    }

    int totalRefCount() {
        int i;
        synchronized (this.mLock) {
            i = this.mStableCount + this.mUnstableCount;
        }
        return i;
    }
}
