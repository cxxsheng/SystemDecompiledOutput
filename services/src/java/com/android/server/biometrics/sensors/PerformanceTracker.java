package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class PerformanceTracker {
    private static final java.lang.String TAG = "PerformanceTracker";
    private static android.util.SparseArray<com.android.server.biometrics.sensors.PerformanceTracker> sTrackers;
    private final android.util.SparseArray<com.android.server.biometrics.sensors.PerformanceTracker.Info> mAllUsersInfo = new android.util.SparseArray<>();
    private int mHALDeathCount;

    private static class Info {
        int mAccept;
        int mAcceptCrypto;
        int mAcquire;
        int mAcquireCrypto;
        int mPermanentLockout;
        int mReject;
        int mRejectCrypto;
        int mTimedLockout;

        private Info() {
        }
    }

    public static com.android.server.biometrics.sensors.PerformanceTracker getInstanceForSensorId(int i) {
        if (sTrackers == null) {
            sTrackers = new android.util.SparseArray<>();
        }
        if (!sTrackers.contains(i)) {
            sTrackers.put(i, new com.android.server.biometrics.sensors.PerformanceTracker());
        }
        return sTrackers.get(i);
    }

    private PerformanceTracker() {
    }

    private void createUserEntryIfNecessary(int i) {
        if (!this.mAllUsersInfo.contains(i)) {
            this.mAllUsersInfo.put(i, new com.android.server.biometrics.sensors.PerformanceTracker.Info());
        }
    }

    public void incrementAuthForUser(int i, boolean z) {
        createUserEntryIfNecessary(i);
        if (z) {
            this.mAllUsersInfo.get(i).mAccept++;
        } else {
            this.mAllUsersInfo.get(i).mReject++;
        }
    }

    void incrementCryptoAuthForUser(int i, boolean z) {
        createUserEntryIfNecessary(i);
        if (z) {
            this.mAllUsersInfo.get(i).mAcceptCrypto++;
        } else {
            this.mAllUsersInfo.get(i).mRejectCrypto++;
        }
    }

    public void incrementAcquireForUser(int i, boolean z) {
        createUserEntryIfNecessary(i);
        if (z) {
            this.mAllUsersInfo.get(i).mAcquireCrypto++;
        } else {
            this.mAllUsersInfo.get(i).mAcquire++;
        }
    }

    public void incrementTimedLockoutForUser(int i) {
        createUserEntryIfNecessary(i);
        this.mAllUsersInfo.get(i).mTimedLockout++;
    }

    public void incrementPermanentLockoutForUser(int i) {
        createUserEntryIfNecessary(i);
        this.mAllUsersInfo.get(i).mPermanentLockout++;
    }

    public void incrementHALDeathCount() {
        this.mHALDeathCount++;
    }

    public void clear() {
        this.mAllUsersInfo.clear();
        this.mHALDeathCount = 0;
    }

    public int getAcceptForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mAccept;
        }
        return 0;
    }

    public int getRejectForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mReject;
        }
        return 0;
    }

    public int getAcquireForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mAcquire;
        }
        return 0;
    }

    public int getAcceptCryptoForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mAcceptCrypto;
        }
        return 0;
    }

    public int getRejectCryptoForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mRejectCrypto;
        }
        return 0;
    }

    public int getAcquireCryptoForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mAcquireCrypto;
        }
        return 0;
    }

    public int getTimedLockoutForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mTimedLockout;
        }
        return 0;
    }

    public int getPermanentLockoutForUser(int i) {
        if (this.mAllUsersInfo.contains(i)) {
            return this.mAllUsersInfo.get(i).mPermanentLockout;
        }
        return 0;
    }

    public int getHALDeathCount() {
        return this.mHALDeathCount;
    }
}
