package com.android.server.input;

/* loaded from: classes2.dex */
final class StickyModifierStateController {

    @com.android.internal.annotations.GuardedBy({"mStickyModifierStateListenerRecords"})
    private final android.util.SparseArray<com.android.server.input.StickyModifierStateController.StickyModifierStateListenerRecord> mStickyModifierStateListenerRecords = new android.util.SparseArray<>();
    private static final java.lang.String TAG = "ModifierStateController";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    StickyModifierStateController() {
    }

    public void notifyStickyModifierStateChanged(int i, int i2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Sticky modifier state changed, modifierState = " + i + ", lockedModifierState = " + i2);
        }
        synchronized (this.mStickyModifierStateListenerRecords) {
            for (int i3 = 0; i3 < this.mStickyModifierStateListenerRecords.size(); i3++) {
                try {
                    this.mStickyModifierStateListenerRecords.valueAt(i3).notifyStickyModifierStateChanged(i, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void registerStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener, int i) {
        synchronized (this.mStickyModifierStateListenerRecords) {
            try {
                if (this.mStickyModifierStateListenerRecords.get(i) != null) {
                    throw new java.lang.IllegalStateException("The calling process has already registered a StickyModifierStateListener.");
                }
                com.android.server.input.StickyModifierStateController.StickyModifierStateListenerRecord stickyModifierStateListenerRecord = new com.android.server.input.StickyModifierStateController.StickyModifierStateListenerRecord(i, iStickyModifierStateListener);
                try {
                    iStickyModifierStateListener.asBinder().linkToDeath(stickyModifierStateListenerRecord, 0);
                    this.mStickyModifierStateListenerRecords.put(i, stickyModifierStateListenerRecord);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener, int i) {
        synchronized (this.mStickyModifierStateListenerRecords) {
            try {
                com.android.server.input.StickyModifierStateController.StickyModifierStateListenerRecord stickyModifierStateListenerRecord = this.mStickyModifierStateListenerRecords.get(i);
                if (stickyModifierStateListenerRecord == null) {
                    throw new java.lang.IllegalStateException("The calling process has no registered StickyModifierStateListener.");
                }
                if (stickyModifierStateListenerRecord.mListener.asBinder() != iStickyModifierStateListener.asBinder()) {
                    throw new java.lang.IllegalStateException("The calling process has a different registered StickyModifierStateListener.");
                }
                stickyModifierStateListenerRecord.mListener.asBinder().unlinkToDeath(stickyModifierStateListenerRecord, 0);
                this.mStickyModifierStateListenerRecords.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStickyModifierStateListenerDied(int i) {
        synchronized (this.mStickyModifierStateListenerRecords) {
            this.mStickyModifierStateListenerRecords.remove(i);
        }
    }

    private class StickyModifierStateListenerRecord implements android.os.IBinder.DeathRecipient {
        public final android.hardware.input.IStickyModifierStateListener mListener;
        public final int mPid;

        StickyModifierStateListenerRecord(int i, android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) {
            this.mPid = i;
            this.mListener = iStickyModifierStateListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.StickyModifierStateController.DEBUG) {
                android.util.Slog.d(com.android.server.input.StickyModifierStateController.TAG, "Sticky modifier state listener for pid " + this.mPid + " died.");
            }
            com.android.server.input.StickyModifierStateController.this.onStickyModifierStateListenerDied(this.mPid);
        }

        public void notifyStickyModifierStateChanged(int i, int i2) {
            try {
                this.mListener.onStickyModifierStateChanged(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.StickyModifierStateController.TAG, "Failed to notify process " + this.mPid + " that sticky modifier state changed, assuming it died.", e);
                binderDied();
            }
        }
    }
}
