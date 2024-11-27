package android.hardware.biometrics;

/* loaded from: classes.dex */
public class BiometricTestSession implements java.lang.AutoCloseable {
    private static final java.lang.String BASE_TAG = "BiometricTestSession";
    private java.util.concurrent.CountDownLatch mCloseLatch;
    private final android.content.Context mContext;
    private final int mSensorId;
    private final android.hardware.biometrics.ITestSession mTestSession;
    private final android.hardware.biometrics.ITestSessionCallback mCallback = new android.hardware.biometrics.ITestSessionCallback.Stub() { // from class: android.hardware.biometrics.BiometricTestSession.1
        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupStarted(int i) {
            android.util.Log.d(android.hardware.biometrics.BiometricTestSession.this.getTag(), "onCleanupStarted, sensor: " + android.hardware.biometrics.BiometricTestSession.this.mSensorId + ", userId: " + i);
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupFinished(int i) {
            android.util.Log.d(android.hardware.biometrics.BiometricTestSession.this.getTag(), "onCleanupFinished, sensor: " + android.hardware.biometrics.BiometricTestSession.this.mSensorId + ", userId: " + i + ", remaining users: " + android.hardware.biometrics.BiometricTestSession.this.mUsersCleaningUp.size());
            android.hardware.biometrics.BiometricTestSession.this.mUsersCleaningUp.remove(java.lang.Integer.valueOf(i));
            if (android.hardware.biometrics.BiometricTestSession.this.mUsersCleaningUp.isEmpty() && android.hardware.biometrics.BiometricTestSession.this.mCloseLatch != null) {
                android.hardware.biometrics.BiometricTestSession.this.mCloseLatch.countDown();
            }
        }
    };
    private final android.util.ArraySet<java.lang.Integer> mTestedUsers = new android.util.ArraySet<>();
    private final android.util.ArraySet<java.lang.Integer> mUsersCleaningUp = new android.util.ArraySet<>();

    public interface TestSessionProvider {
        android.hardware.biometrics.ITestSession createTestSession(android.content.Context context, int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) throws android.os.RemoteException;
    }

    public BiometricTestSession(android.content.Context context, int i, android.hardware.biometrics.BiometricTestSession.TestSessionProvider testSessionProvider) throws android.os.RemoteException {
        this.mContext = context;
        this.mSensorId = i;
        this.mTestSession = testSessionProvider.createTestSession(context, i, this.mCallback);
        setTestHalEnabled(true);
    }

    private void setTestHalEnabled(boolean z) {
        try {
            android.util.Log.w(getTag(), "setTestHalEnabled, sensor: " + this.mSensorId + " enabled: " + z);
            this.mTestSession.setTestHalEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startEnroll(int i) {
        try {
            this.mTestedUsers.add(java.lang.Integer.valueOf(i));
            this.mTestSession.startEnroll(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishEnroll(int i) {
        try {
            this.mTestedUsers.add(java.lang.Integer.valueOf(i));
            this.mTestSession.finishEnroll(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void acceptAuthentication(int i) {
        try {
            this.mTestSession.acceptAuthentication(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rejectAuthentication(int i) {
        try {
            this.mTestSession.rejectAuthentication(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAcquired(int i, int i2) {
        try {
            this.mTestSession.notifyAcquired(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyError(int i, int i2) {
        try {
            this.mTestSession.notifyError(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cleanupInternalState(int i) {
        try {
            if (this.mUsersCleaningUp.contains(java.lang.Integer.valueOf(i))) {
                android.util.Log.w(getTag(), "Cleanup already in progress for user: " + i);
            }
            this.mUsersCleaningUp.add(java.lang.Integer.valueOf(i));
            this.mTestSession.cleanupInternalState(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        android.util.Log.d(getTag(), "Close, mTestedUsers size; " + this.mTestedUsers.size());
        if (!this.mTestedUsers.isEmpty()) {
            this.mCloseLatch = new java.util.concurrent.CountDownLatch(1);
            java.util.Iterator<java.lang.Integer> it = this.mTestedUsers.iterator();
            while (it.hasNext()) {
                cleanupInternalState(it.next().intValue());
            }
            try {
                android.util.Log.d(getTag(), "Awaiting latch...");
                this.mCloseLatch.await(3L, java.util.concurrent.TimeUnit.SECONDS);
                android.util.Log.d(getTag(), "Finished awaiting");
            } catch (java.lang.InterruptedException e) {
                android.util.Log.e(getTag(), "Latch interrupted", e);
            }
        }
        if (!this.mUsersCleaningUp.isEmpty()) {
            android.util.Log.e(getTag(), "Cleanup not finished before shutdown - pending: " + this.mUsersCleaningUp.size());
        }
        setTestHalEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getTag() {
        return "BiometricTestSession_" + this.mSensorId;
    }
}
