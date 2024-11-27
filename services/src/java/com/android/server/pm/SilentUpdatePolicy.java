package com.android.server.pm;

/* loaded from: classes2.dex */
public class SilentUpdatePolicy {
    private static final long SILENT_UPDATE_THROTTLE_TIME_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(30);

    @com.android.internal.annotations.GuardedBy({"mSilentUpdateInfos"})
    private java.lang.String mAllowUnlimitedSilentUpdatesInstaller;

    @com.android.internal.annotations.GuardedBy({"mSilentUpdateInfos"})
    private final android.util.ArrayMap<android.util.Pair<java.lang.String, java.lang.String>, java.lang.Long> mSilentUpdateInfos = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mSilentUpdateInfos"})
    private long mSilentUpdateThrottleTimeMs = SILENT_UPDATE_THROTTLE_TIME_MS;

    public boolean isSilentUpdateAllowed(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        long j;
        if (str == null) {
            return true;
        }
        long timestampMs = getTimestampMs(str, str2);
        synchronized (this.mSilentUpdateInfos) {
            j = this.mSilentUpdateThrottleTimeMs;
        }
        return android.os.SystemClock.uptimeMillis() - timestampMs > j;
    }

    public void track(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (str == null) {
            return;
        }
        synchronized (this.mSilentUpdateInfos) {
            try {
                if (this.mAllowUnlimitedSilentUpdatesInstaller == null || !this.mAllowUnlimitedSilentUpdatesInstaller.equals(str)) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    pruneLocked(uptimeMillis);
                    this.mSilentUpdateInfos.put(android.util.Pair.create(str, str2), java.lang.Long.valueOf(uptimeMillis));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAllowUnlimitedSilentUpdates(@android.annotation.Nullable java.lang.String str) {
        synchronized (this.mSilentUpdateInfos) {
            if (str == null) {
                try {
                    this.mSilentUpdateInfos.clear();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mAllowUnlimitedSilentUpdatesInstaller = str;
        }
    }

    void setSilentUpdatesThrottleTime(long j) {
        long j2;
        synchronized (this.mSilentUpdateInfos) {
            try {
                if (j >= 0) {
                    j2 = java.util.concurrent.TimeUnit.SECONDS.toMillis(j);
                } else {
                    j2 = SILENT_UPDATE_THROTTLE_TIME_MS;
                }
                this.mSilentUpdateThrottleTimeMs = j2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void pruneLocked(long j) {
        for (int size = this.mSilentUpdateInfos.size() - 1; size >= 0; size--) {
            if (j - this.mSilentUpdateInfos.valueAt(size).longValue() > this.mSilentUpdateThrottleTimeMs) {
                this.mSilentUpdateInfos.removeAt(size);
            }
        }
    }

    private long getTimestampMs(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        java.lang.Long l;
        android.util.Pair create = android.util.Pair.create(str, str2);
        synchronized (this.mSilentUpdateInfos) {
            l = this.mSilentUpdateInfos.get(create);
        }
        if (l != null) {
            return l.longValue();
        }
        return -1L;
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mSilentUpdateInfos) {
            try {
                if (this.mSilentUpdateInfos.isEmpty()) {
                    return;
                }
                indentingPrintWriter.println("Last silent updated Infos:");
                indentingPrintWriter.increaseIndent();
                int size = this.mSilentUpdateInfos.size();
                for (int i = 0; i < size; i++) {
                    android.util.Pair<java.lang.String, java.lang.String> keyAt = this.mSilentUpdateInfos.keyAt(i);
                    if (keyAt != null) {
                        indentingPrintWriter.printPair("installerPackageName", keyAt.first);
                        indentingPrintWriter.printPair(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, keyAt.second);
                        indentingPrintWriter.printPair("silentUpdatedMillis", this.mSilentUpdateInfos.valueAt(i));
                        indentingPrintWriter.println();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
