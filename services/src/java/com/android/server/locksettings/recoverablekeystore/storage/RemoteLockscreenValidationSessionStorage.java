package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class RemoteLockscreenValidationSessionStorage {
    private static final long SESSION_TIMEOUT_MILLIS = 600000;
    private static final java.lang.String TAG = "RemoteLockscreenValidation";

    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession> mSessionsByUserId = new android.util.SparseArray<>(0);

    @android.annotation.Nullable
    public com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession get(int i) {
        com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession lockscreenVerificationSession;
        synchronized (this.mSessionsByUserId) {
            lockscreenVerificationSession = this.mSessionsByUserId.get(i);
        }
        return lockscreenVerificationSession;
    }

    public com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession startSession(int i) {
        com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession lockscreenVerificationSession;
        synchronized (this.mSessionsByUserId) {
            if (this.mSessionsByUserId.get(i) != null) {
                this.mSessionsByUserId.delete(i);
            }
            try {
                lockscreenVerificationSession = new com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession(com.android.security.SecureBox.genKeyPair(), android.os.SystemClock.elapsedRealtime());
                this.mSessionsByUserId.put(i, lockscreenVerificationSession);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        return lockscreenVerificationSession;
    }

    public void finishSession(int i) {
        synchronized (this.mSessionsByUserId) {
            this.mSessionsByUserId.delete(i);
        }
    }

    public java.lang.Runnable getLockscreenValidationCleanupTask() {
        return new com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenValidationCleanupTask();
    }

    public class LockscreenVerificationSession {
        private final long mElapsedStartTime;
        private final java.security.KeyPair mKeyPair;

        LockscreenVerificationSession(java.security.KeyPair keyPair, long j) {
            this.mKeyPair = keyPair;
            this.mElapsedStartTime = j;
        }

        public java.security.KeyPair getKeyPair() {
            return this.mKeyPair;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getElapsedStartTimeMillis() {
            return this.mElapsedStartTime;
        }
    }

    private class LockscreenValidationCleanupTask implements java.lang.Runnable {
        private LockscreenValidationCleanupTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                synchronized (com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.this.mSessionsByUserId) {
                    try {
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        for (int i = 0; i < com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.this.mSessionsByUserId.size(); i++) {
                            if (android.os.SystemClock.elapsedRealtime() - com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.this.mSessionsByUserId.valueAt(i).getElapsedStartTimeMillis() > 600000) {
                                arrayList.add(java.lang.Integer.valueOf(com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.this.mSessionsByUserId.keyAt(i)));
                            }
                        }
                        java.util.Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.this.mSessionsByUserId.delete(((java.lang.Integer) it.next()).intValue());
                        }
                    } finally {
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.TAG, "Unexpected exception thrown during LockscreenValidationCleanupTask", e);
            }
        }
    }
}
