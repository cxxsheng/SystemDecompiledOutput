package com.android.server.am;

/* loaded from: classes.dex */
public class ForegroundServiceTypeLoggerModule {
    public static final int FGS_API_BEGIN_WITH_FGS = 1;
    public static final int FGS_API_END_WITHOUT_FGS = 3;
    public static final int FGS_API_END_WITH_FGS = 2;
    public static final int FGS_API_PAUSE = 4;
    public static final int FGS_API_RESUME = 5;
    public static final int FGS_STATE_CHANGED_API_CALL = 4;
    private static final java.lang.String TAG = "ForegroundServiceTypeLoggerModule";
    private final android.util.SparseArray<com.android.server.am.ForegroundServiceTypeLoggerModule.UidState> mUids = new android.util.SparseArray<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FgsApiState {
    }

    private static class UidState {
        final android.util.SparseArray<com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord> mApiClosedCalls;
        final android.util.SparseArray<com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord> mApiOpenCalls;
        final android.util.SparseArray<java.lang.Long> mFirstFgsTimeStamp;
        final android.util.SparseArray<java.lang.Long> mLastFgsTimeStamp;
        final android.util.SparseIntArray mOpenWithFgsCount;
        final android.util.SparseIntArray mOpenedWithoutFgsCount;
        final android.util.SparseArray<android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord>> mRunningFgs;

        private UidState() {
            this.mApiOpenCalls = new android.util.SparseArray<>();
            this.mApiClosedCalls = new android.util.SparseArray<>();
            this.mOpenedWithoutFgsCount = new android.util.SparseIntArray();
            this.mOpenWithFgsCount = new android.util.SparseIntArray();
            this.mRunningFgs = new android.util.SparseArray<>();
            this.mLastFgsTimeStamp = new android.util.SparseArray<>();
            this.mFirstFgsTimeStamp = new android.util.SparseArray<>();
        }
    }

    public void logForegroundServiceStart(int i, int i2, com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.getComponentName() != null) {
            android.os.Trace.asyncTraceForTrackBegin(64L, serviceRecord.getComponentName().flattenToString() + ":" + i, "foregroundService", serviceRecord.foregroundServiceType);
        }
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i);
        if (uidState == null) {
            uidState = new com.android.server.am.ForegroundServiceTypeLoggerModule.UidState();
            this.mUids.put(i, uidState);
        }
        android.util.IntArray convertFgsTypeToApiTypes = convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
        if (convertFgsTypeToApiTypes.size() == 0) {
            android.util.Slog.w(TAG, "Foreground service start for UID: " + i + " does not have any types");
        }
        android.util.IntArray intArray = new android.util.IntArray();
        android.util.LongArray longArray = new android.util.LongArray();
        int size = convertFgsTypeToApiTypes.size();
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = convertFgsTypeToApiTypes.get(i3);
            int indexOfKey = uidState.mRunningFgs.indexOfKey(i4);
            if (indexOfKey < 0) {
                uidState.mRunningFgs.put(i4, new android.util.ArrayMap<>());
                indexOfKey = uidState.mRunningFgs.indexOfKey(i4);
                uidState.mFirstFgsTimeStamp.put(i4, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
            }
            uidState.mRunningFgs.valueAt(indexOfKey).put(serviceRecord.getComponentName(), serviceRecord);
            if (uidState.mApiOpenCalls.contains(i4)) {
                uidState.mOpenWithFgsCount.put(i4, uidState.mOpenedWithoutFgsCount.get(i4));
                uidState.mOpenedWithoutFgsCount.put(i4, 0);
                intArray.add(i4);
                com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord fgsApiRecord = uidState.mApiOpenCalls.get(i4);
                longArray.add(fgsApiRecord.mTimeStart);
                fgsApiRecord.mIsAssociatedWithFgs = true;
                fgsApiRecord.mAssociatedFgsRecord = serviceRecord;
                uidState.mApiOpenCalls.remove(i4);
            }
        }
        if (intArray.size() != 0) {
            int size2 = intArray.size();
            for (int i5 = 0; i5 < size2; i5++) {
                logFgsApiEvent(serviceRecord, 4, 1, intArray.get(i5), longArray.get(i5));
            }
        }
    }

    public void logForegroundServiceStop(int i, com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.getComponentName() != null) {
            android.os.Trace.asyncTraceForTrackEnd(64L, serviceRecord.getComponentName().flattenToString() + ":" + i, serviceRecord.hashCode());
        }
        android.util.IntArray convertFgsTypeToApiTypes = convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i);
        if (convertFgsTypeToApiTypes.size() == 0) {
            android.util.Slog.w(TAG, "FGS stop call for: " + i + " has no types!");
        }
        if (uidState == null) {
            android.util.Slog.w(TAG, "FGS stop call being logged with no start call for UID for UID " + i + " in package " + serviceRecord.packageName);
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int size = convertFgsTypeToApiTypes.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = convertFgsTypeToApiTypes.get(i2);
            android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = uidState.mRunningFgs.get(i3);
            if (arrayMap == null) {
                android.util.Slog.w(TAG, "Could not find appropriate running FGS for FGS stop for UID " + i + " in package " + serviceRecord.packageName);
            } else {
                arrayMap.remove(serviceRecord.getComponentName());
                if (arrayMap.size() == 0) {
                    uidState.mRunningFgs.remove(i3);
                    uidState.mLastFgsTimeStamp.put(i3, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
                }
                int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i3);
                if (indexOfKey < 0) {
                    android.util.Slog.w(TAG, "Logger should be tracking FGS types correctly for UID " + i + " in package " + serviceRecord.packageName);
                } else {
                    com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord fgsApiRecord = uidState.mApiClosedCalls.get(i3);
                    if (fgsApiRecord != null && uidState.mOpenWithFgsCount.valueAt(indexOfKey) == 0) {
                        arrayList.add(java.lang.Integer.valueOf(i3));
                        arrayList2.add(java.lang.Long.valueOf(fgsApiRecord.mTimeStart));
                        uidState.mApiClosedCalls.remove(i3);
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                logFgsApiEvent(serviceRecord, 4, 2, ((java.lang.Integer) arrayList.get(i4)).intValue(), ((java.lang.Long) arrayList2.get(i4)).longValue());
            }
        }
    }

    public long logForegroundServiceApiEventBegin(int i, int i2, int i3, java.lang.String str) {
        com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord fgsApiRecord = new com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord(i2, i3, str, i, java.lang.System.currentTimeMillis());
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i2);
        if (uidState == null) {
            uidState = new com.android.server.am.ForegroundServiceTypeLoggerModule.UidState();
            this.mUids.put(i2, uidState);
        }
        if (!hasValidActiveFgs(i2, i)) {
            int indexOfKey = uidState.mOpenedWithoutFgsCount.indexOfKey(i);
            if (indexOfKey < 0) {
                uidState.mOpenedWithoutFgsCount.put(i, 0);
                indexOfKey = uidState.mOpenedWithoutFgsCount.indexOfKey(i);
            }
            if (!uidState.mApiOpenCalls.contains(i) || uidState.mOpenedWithoutFgsCount.valueAt(indexOfKey) == 0) {
                uidState.mApiOpenCalls.put(i, fgsApiRecord);
            }
            uidState.mOpenedWithoutFgsCount.put(i, uidState.mOpenedWithoutFgsCount.get(i) + 1);
            return fgsApiRecord.mTimeStart;
        }
        int indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
        if (indexOfKey2 < 0) {
            uidState.mOpenWithFgsCount.put(i, 0);
            indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
        }
        uidState.mOpenWithFgsCount.put(i, uidState.mOpenWithFgsCount.valueAt(indexOfKey2) + 1);
        android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = uidState.mRunningFgs.get(i);
        long j = fgsApiRecord.mTimeStart;
        if (uidState.mOpenWithFgsCount.valueAt(indexOfKey2) == 1) {
            java.util.Iterator<com.android.server.am.ServiceRecord> it = arrayMap.values().iterator();
            while (it.hasNext()) {
                logFgsApiEvent(it.next(), 4, 1, i, j);
            }
        }
        return fgsApiRecord.mTimeStart;
    }

    public long logForegroundServiceApiEventEnd(int i, int i2, int i3) {
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i2);
        if (uidState == null) {
            android.util.Slog.w(TAG, "API event end called before start!");
            return -1L;
        }
        int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i);
        if (indexOfKey >= 0) {
            if (uidState.mOpenWithFgsCount.get(i) != 0) {
                uidState.mOpenWithFgsCount.put(i, uidState.mOpenWithFgsCount.get(i) - 1);
            }
            if (!hasValidActiveFgs(i2, i) && uidState.mOpenWithFgsCount.get(i) == 0) {
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                logFgsApiEventWithNoFgs(i2, 3, i, currentTimeMillis);
                uidState.mOpenWithFgsCount.removeAt(indexOfKey);
                return currentTimeMillis;
            }
        }
        if (uidState.mOpenedWithoutFgsCount.indexOfKey(i) < 0) {
            uidState.mOpenedWithoutFgsCount.put(i, 0);
        }
        int i4 = uidState.mOpenedWithoutFgsCount.get(i);
        if (i4 != 0) {
            int i5 = i4 - 1;
            if (i5 == 0) {
                uidState.mApiOpenCalls.remove(i);
            }
            uidState.mOpenedWithoutFgsCount.put(i, i5);
            return java.lang.System.currentTimeMillis();
        }
        com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord fgsApiRecord = new com.android.server.am.ForegroundServiceTypeLoggerModule.FgsApiRecord(i2, i3, "", i, java.lang.System.currentTimeMillis());
        uidState.mApiClosedCalls.put(i, fgsApiRecord);
        return fgsApiRecord.mTimeStart;
    }

    public void logForegroundServiceApiStateChanged(int i, int i2, int i3, int i4) {
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i2);
        if (!uidState.mRunningFgs.contains(i)) {
            return;
        }
        android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = uidState.mRunningFgs.get(i);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.Iterator<com.android.server.am.ServiceRecord> it = arrayMap.values().iterator();
        while (it.hasNext()) {
            logFgsApiEvent(it.next(), 4, i4, i, currentTimeMillis);
        }
    }

    private android.util.IntArray convertFgsTypeToApiTypes(int i) {
        android.util.IntArray intArray = new android.util.IntArray();
        if ((i & 64) == 64) {
            intArray.add(1);
        }
        if ((i & 16) == 16) {
            intArray.add(2);
            intArray.add(8);
            intArray.add(9);
        }
        if ((i & 8) == 8) {
            intArray.add(3);
        }
        if ((i & 2) == 2) {
            intArray.add(5);
            intArray.add(4);
        }
        if ((i & 128) == 128) {
            intArray.add(6);
        }
        if ((i & 4) == 4) {
            intArray.add(7);
        }
        return intArray;
    }

    private boolean hasValidActiveFgs(int i, int i2) {
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i);
        if (uidState != null) {
            return uidState.mRunningFgs.contains(i2);
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logFgsApiEvent(com.android.server.am.ServiceRecord serviceRecord, int i, int i2, int i3, long j) {
        long j2;
        long j3;
        int i4;
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(serviceRecord.appInfo.uid);
        if (uidState == null) {
            return;
        }
        if (!uidState.mFirstFgsTimeStamp.contains(i3)) {
            j2 = 0;
        } else {
            j2 = uidState.mFirstFgsTimeStamp.get(i3).longValue() - j;
        }
        if (!uidState.mLastFgsTimeStamp.contains(i3)) {
            j3 = 0;
        } else {
            j3 = j - uidState.mLastFgsTimeStamp.get(i3).longValue();
        }
        int[] iArr = {i3};
        long[] jArr = {j};
        int i5 = serviceRecord.appInfo.uid;
        java.lang.String str = serviceRecord.shortInstanceName;
        boolean isFgsAllowedWiu_forCapabilities = serviceRecord.isFgsAllowedWiu_forCapabilities();
        int fgsAllowStart = serviceRecord.getFgsAllowStart();
        int i6 = serviceRecord.appInfo.targetSdkVersion;
        int i7 = serviceRecord.mRecentCallingUid;
        int i8 = serviceRecord.mInfoTempFgsAllowListReason != null ? serviceRecord.mInfoTempFgsAllowListReason.mCallingUid : -1;
        boolean z = serviceRecord.mFgsNotificationWasDeferred;
        boolean z2 = serviceRecord.mFgsNotificationShown;
        int i9 = serviceRecord.mStartForegroundCount;
        boolean z3 = serviceRecord.mFgsHasNotificationPermission;
        int i10 = serviceRecord.foregroundServiceType;
        boolean z4 = serviceRecord.mIsFgsDelegate;
        int i11 = serviceRecord.mFgsDelegation != null ? serviceRecord.mFgsDelegation.mOptions.mClientUid : -1;
        if (serviceRecord.mFgsDelegation != null) {
            i4 = serviceRecord.mFgsDelegation.mOptions.mDelegationService;
        } else {
            i4 = 0;
        }
        com.android.internal.util.FrameworkStatsLog.write(60, i5, str, i, isFgsAllowedWiu_forCapabilities, fgsAllowStart, i6, i7, 0, i8, z, z2, 0, i9, 0, z3, i10, 0, z4, i11, i4, i2, iArr, jArr, -1, 0, -1, 0, j2, j3, serviceRecord.mAllowWiu_noBinding, serviceRecord.mAllowWiu_inBindService, serviceRecord.mAllowWiu_byBindings, serviceRecord.mAllowStart_noBinding, serviceRecord.mAllowStart_inBindService, serviceRecord.mAllowStart_byBindings, 0, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logFgsApiEventWithNoFgs(int i, int i2, int i3, long j) {
        long j2;
        com.android.server.am.ForegroundServiceTypeLoggerModule.UidState uidState = this.mUids.get(i);
        if (uidState != null) {
            if (uidState.mLastFgsTimeStamp.contains(i3)) {
                j2 = j - uidState.mLastFgsTimeStamp.get(i3).longValue();
            } else {
                j2 = 0;
            }
            com.android.internal.util.FrameworkStatsLog.write(60, i, null, 4, false, 0, 0, i, 0, 0, false, false, 0, 0, 0, false, 0, 0, false, 0, 0, i2, new int[]{i3}, new long[]{j}, -1, 0, -1, 0, 0L, j2, 0, 0, 0, 0, 0, 0, 0, false);
        }
    }

    private static class FgsApiRecord {
        com.android.server.am.ServiceRecord mAssociatedFgsRecord;
        boolean mIsAssociatedWithFgs;
        final java.lang.String mPackageName;
        final int mPid;
        final long mTimeStart;
        int mType;
        final int mUid;

        FgsApiRecord(int i, int i2, java.lang.String str, int i3, long j) {
            this.mUid = i;
            this.mPid = i2;
            this.mPackageName = str;
            this.mType = i3;
            this.mTimeStart = j;
        }
    }
}
