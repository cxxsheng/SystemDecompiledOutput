package android.content;

/* loaded from: classes.dex */
public abstract class BroadcastReceiver {
    private boolean mDebugUnregister;
    private android.content.BroadcastReceiver.PendingResult mPendingResult;

    public abstract void onReceive(android.content.Context context, android.content.Intent intent);

    public static class PendingResult {
        public static final int TYPE_COMPONENT = 0;
        public static final int TYPE_REGISTERED = 1;
        public static final int TYPE_UNREGISTERED = 2;
        boolean mAbortBroadcast;
        final boolean mAssumeDeliveredHint;
        boolean mFinished;
        final int mFlags;
        final boolean mInitialStickyHint;
        final boolean mOrderedHint;
        java.lang.String mReceiverClassName;
        int mResultCode;
        java.lang.String mResultData;
        android.os.Bundle mResultExtras;
        final int mSendingUser;
        final java.lang.String mSentFromPackage;
        final int mSentFromUid;
        final android.os.IBinder mToken;
        final int mType;

        public PendingResult(int i, java.lang.String str, android.os.Bundle bundle, int i2, boolean z, boolean z2, android.os.IBinder iBinder, int i3, int i4) {
            this(i, str, bundle, i2, z, z2, guessAssumeDelivered(i2, z), iBinder, i3, i4, -1, null);
        }

        public PendingResult(int i, java.lang.String str, android.os.Bundle bundle, int i2, boolean z, boolean z2, boolean z3, android.os.IBinder iBinder, int i3, int i4, int i5, java.lang.String str2) {
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            this.mType = i2;
            this.mOrderedHint = z;
            this.mInitialStickyHint = z2;
            this.mAssumeDeliveredHint = z3;
            this.mToken = iBinder;
            this.mSendingUser = i3;
            this.mFlags = i4;
            this.mSentFromUid = i5;
            this.mSentFromPackage = str2;
        }

        public static boolean guessAssumeDelivered(int i, boolean z) {
            if (i == 0) {
                return false;
            }
            if (z && i != 2) {
                return false;
            }
            return true;
        }

        public final void setResultCode(int i) {
            checkSynchronousHint();
            this.mResultCode = i;
        }

        public final int getResultCode() {
            return this.mResultCode;
        }

        public final void setResultData(java.lang.String str) {
            checkSynchronousHint();
            this.mResultData = str;
        }

        public final java.lang.String getResultData() {
            return this.mResultData;
        }

        public final void setResultExtras(android.os.Bundle bundle) {
            checkSynchronousHint();
            this.mResultExtras = bundle;
        }

        public final android.os.Bundle getResultExtras(boolean z) {
            android.os.Bundle bundle = this.mResultExtras;
            if (!z) {
                return bundle;
            }
            if (bundle == null) {
                android.os.Bundle bundle2 = new android.os.Bundle();
                this.mResultExtras = bundle2;
                return bundle2;
            }
            return bundle;
        }

        public final void setResult(int i, java.lang.String str, android.os.Bundle bundle) {
            checkSynchronousHint();
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
        }

        public final boolean getAbortBroadcast() {
            return this.mAbortBroadcast;
        }

        public final void abortBroadcast() {
            checkSynchronousHint();
            this.mAbortBroadcast = true;
        }

        public final void clearAbortBroadcast() {
            this.mAbortBroadcast = false;
        }

        public final void finish() {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.traceCounter(64L, "PendingResult#finish#ClassName:" + this.mReceiverClassName, 1);
            }
            if (this.mType == 0) {
                final android.app.IActivityManager service = android.app.ActivityManager.getService();
                if (android.app.QueuedWork.hasPendingWork()) {
                    android.app.QueuedWork.queue(new java.lang.Runnable() { // from class: android.content.BroadcastReceiver.PendingResult.1
                        @Override // java.lang.Runnable
                        public void run() {
                            android.content.BroadcastReceiver.PendingResult.this.sendFinished(service);
                        }
                    }, false);
                    return;
                } else {
                    sendFinished(service);
                    return;
                }
            }
            sendFinished(android.app.ActivityManager.getService());
        }

        public void setExtrasClassLoader(java.lang.ClassLoader classLoader) {
            if (this.mResultExtras != null) {
                this.mResultExtras.setClassLoader(classLoader);
            }
        }

        public void sendFinished(android.app.IActivityManager iActivityManager) {
            synchronized (this) {
                if (this.mFinished) {
                    throw new java.lang.IllegalStateException("Broadcast already finished");
                }
                this.mFinished = true;
                try {
                    if (this.mResultExtras != null) {
                        this.mResultExtras.setAllowFds(false);
                    }
                    if (!this.mAssumeDeliveredHint) {
                        if (this.mOrderedHint) {
                            iActivityManager.finishReceiver(this.mToken, this.mResultCode, this.mResultData, this.mResultExtras, this.mAbortBroadcast, this.mFlags);
                        } else {
                            iActivityManager.finishReceiver(this.mToken, 0, null, null, false, this.mFlags);
                        }
                    }
                } catch (android.os.RemoteException e) {
                }
            }
        }

        public int getSendingUserId() {
            return this.mSendingUser;
        }

        public int getSentFromUid() {
            return this.mSentFromUid;
        }

        public java.lang.String getSentFromPackage() {
            return this.mSentFromPackage;
        }

        void checkSynchronousHint() {
            if (this.mOrderedHint || this.mInitialStickyHint) {
                return;
            }
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
            runtimeException.fillInStackTrace();
            android.util.Log.e("BroadcastReceiver", runtimeException.getMessage(), runtimeException);
        }
    }

    public final android.content.BroadcastReceiver.PendingResult goAsync() {
        android.content.BroadcastReceiver.PendingResult pendingResult = this.mPendingResult;
        this.mPendingResult = null;
        if (pendingResult != null && android.os.Trace.isTagEnabled(64L)) {
            pendingResult.mReceiverClassName = getClass().getName();
            android.os.Trace.traceCounter(64L, "BroadcastReceiver#goAsync#ClassName:" + pendingResult.mReceiverClassName, 1);
        }
        return pendingResult;
    }

    public android.os.IBinder peekService(android.content.Context context, android.content.Intent intent) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        try {
            intent.prepareToLeaveProcess(context);
            return service.peekService(intent, intent.resolveTypeIfNeeded(context.getContentResolver()), context.getOpPackageName());
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public final void setResultCode(int i) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = i;
    }

    public final int getResultCode() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mResultCode;
        }
        return 0;
    }

    public final void setResultData(java.lang.String str) {
        checkSynchronousHint();
        this.mPendingResult.mResultData = str;
    }

    public final java.lang.String getResultData() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mResultData;
        }
        return null;
    }

    public final void setResultExtras(android.os.Bundle bundle) {
        checkSynchronousHint();
        this.mPendingResult.mResultExtras = bundle;
    }

    public final android.os.Bundle getResultExtras(boolean z) {
        if (this.mPendingResult == null) {
            return null;
        }
        android.os.Bundle bundle = this.mPendingResult.mResultExtras;
        if (!z) {
            return bundle;
        }
        if (bundle == null) {
            android.content.BroadcastReceiver.PendingResult pendingResult = this.mPendingResult;
            android.os.Bundle bundle2 = new android.os.Bundle();
            pendingResult.mResultExtras = bundle2;
            return bundle2;
        }
        return bundle;
    }

    public final void setResult(int i, java.lang.String str, android.os.Bundle bundle) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = i;
        this.mPendingResult.mResultData = str;
        this.mPendingResult.mResultExtras = bundle;
    }

    public final boolean getAbortBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mAbortBroadcast;
        }
        return false;
    }

    public final void abortBroadcast() {
        checkSynchronousHint();
        this.mPendingResult.mAbortBroadcast = true;
    }

    public final void clearAbortBroadcast() {
        if (this.mPendingResult != null) {
            this.mPendingResult.mAbortBroadcast = false;
        }
    }

    public final boolean isOrderedBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mOrderedHint;
        }
        return false;
    }

    public final boolean isInitialStickyBroadcast() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mInitialStickyHint;
        }
        return false;
    }

    public final void setOrderedHint(boolean z) {
    }

    public final void setPendingResult(android.content.BroadcastReceiver.PendingResult pendingResult) {
        this.mPendingResult = pendingResult;
    }

    public final android.content.BroadcastReceiver.PendingResult getPendingResult() {
        return this.mPendingResult;
    }

    @android.annotation.SystemApi
    public final android.os.UserHandle getSendingUser() {
        return android.os.UserHandle.of(getSendingUserId());
    }

    public int getSendingUserId() {
        return this.mPendingResult.mSendingUser;
    }

    public int getSentFromUid() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mSentFromUid;
        }
        return -1;
    }

    public java.lang.String getSentFromPackage() {
        if (this.mPendingResult != null) {
            return this.mPendingResult.mSentFromPackage;
        }
        return null;
    }

    public final void setDebugUnregister(boolean z) {
        this.mDebugUnregister = z;
    }

    public final boolean getDebugUnregister() {
        return this.mDebugUnregister;
    }

    void checkSynchronousHint() {
        if (this.mPendingResult == null) {
            throw new java.lang.IllegalStateException("Call while result is not pending");
        }
        if (this.mPendingResult.mOrderedHint || this.mPendingResult.mInitialStickyHint) {
            return;
        }
        java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
        runtimeException.fillInStackTrace();
        android.util.Log.e("BroadcastReceiver", runtimeException.getMessage(), runtimeException);
    }
}
