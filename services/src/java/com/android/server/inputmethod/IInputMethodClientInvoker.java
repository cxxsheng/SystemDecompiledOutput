package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class IInputMethodClientInvoker {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputMethodManagerService";

    @android.annotation.Nullable
    private final android.os.Handler mHandler;
    private final boolean mIsProxy;

    @android.annotation.NonNull
    private final com.android.internal.inputmethod.IInputMethodClient mTarget;

    @android.annotation.Nullable
    static com.android.server.inputmethod.IInputMethodClientInvoker create(@android.annotation.Nullable com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, @android.annotation.NonNull android.os.Handler handler) {
        if (iInputMethodClient == null) {
            return null;
        }
        boolean isProxy = android.os.Binder.isProxy(iInputMethodClient);
        if (isProxy) {
            handler = null;
        }
        return new com.android.server.inputmethod.IInputMethodClientInvoker(iInputMethodClient, isProxy, handler);
    }

    @android.annotation.Nullable
    static com.android.server.inputmethod.IInputMethodClientInvoker create$ravenwood(@android.annotation.Nullable com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, @android.annotation.NonNull android.os.Handler handler) {
        if (iInputMethodClient == null) {
            return null;
        }
        return new com.android.server.inputmethod.IInputMethodClientInvoker(iInputMethodClient, true, null);
    }

    private IInputMethodClientInvoker(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, boolean z, @android.annotation.Nullable android.os.Handler handler) {
        this.mTarget = iInputMethodClient;
        this.mIsProxy = z;
        this.mHandler = handler;
    }

    private static java.lang.String getCallerMethodName() {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        if (stackTrace.length <= 4) {
            return "<bottom of call stack>";
        }
        return stackTrace[4].getMethodName();
    }

    private static void logRemoteException(@android.annotation.NonNull android.os.RemoteException remoteException) {
        if (!(remoteException instanceof android.os.DeadObjectException)) {
            android.util.Slog.w(TAG, "IPC failed at IInputMethodClientInvoker#" + getCallerMethodName(), remoteException);
        }
    }

    void onBindMethod(@android.annotation.NonNull final com.android.internal.inputmethod.InputBindResult inputBindResult) {
        if (this.mIsProxy) {
            lambda$onBindMethod$0(inputBindResult);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$onBindMethod$0(inputBindResult);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onBindMethodInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onBindMethod$0(@android.annotation.NonNull com.android.internal.inputmethod.InputBindResult inputBindResult) {
        try {
            try {
                this.mTarget.onBindMethod(inputBindResult);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            } catch (android.os.RemoteException e) {
                logRemoteException(e);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            }
            inputBindResult.channel.dispose();
        } catch (java.lang.Throwable th) {
            if (inputBindResult.channel != null && this.mIsProxy) {
                inputBindResult.channel.dispose();
            }
            throw th;
        }
    }

    void onStartInputResult(@android.annotation.NonNull final com.android.internal.inputmethod.InputBindResult inputBindResult, final int i) {
        if (this.mIsProxy) {
            lambda$onStartInputResult$1(inputBindResult, i);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$onStartInputResult$1(inputBindResult, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onStartInputResultInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onStartInputResult$1(@android.annotation.NonNull com.android.internal.inputmethod.InputBindResult inputBindResult, int i) {
        try {
            try {
                this.mTarget.onStartInputResult(inputBindResult, i);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            } catch (android.os.RemoteException e) {
                logRemoteException(e);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            }
            inputBindResult.channel.dispose();
        } catch (java.lang.Throwable th) {
            if (inputBindResult.channel != null && this.mIsProxy) {
                inputBindResult.channel.dispose();
            }
            throw th;
        }
    }

    void onBindAccessibilityService(@android.annotation.NonNull final com.android.internal.inputmethod.InputBindResult inputBindResult, final int i) {
        if (this.mIsProxy) {
            lambda$onBindAccessibilityService$2(inputBindResult, i);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$onBindAccessibilityService$2(inputBindResult, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onBindAccessibilityServiceInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onBindAccessibilityService$2(@android.annotation.NonNull com.android.internal.inputmethod.InputBindResult inputBindResult, int i) {
        try {
            try {
                this.mTarget.onBindAccessibilityService(inputBindResult, i);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            } catch (android.os.RemoteException e) {
                logRemoteException(e);
                if (inputBindResult.channel == null || !this.mIsProxy) {
                    return;
                }
            }
            inputBindResult.channel.dispose();
        } catch (java.lang.Throwable th) {
            if (inputBindResult.channel != null && this.mIsProxy) {
                inputBindResult.channel.dispose();
            }
            throw th;
        }
    }

    void onUnbindMethod(final int i, final int i2) {
        if (this.mIsProxy) {
            lambda$onUnbindMethod$3(i, i2);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$onUnbindMethod$3(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onUnbindMethodInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onUnbindMethod$3(int i, int i2) {
        try {
            this.mTarget.onUnbindMethod(i, i2);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void onUnbindAccessibilityService(final int i, final int i2) {
        if (this.mIsProxy) {
            lambda$onUnbindAccessibilityService$4(i, i2);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$onUnbindAccessibilityService$4(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onUnbindAccessibilityServiceInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onUnbindAccessibilityService$4(int i, int i2) {
        try {
            this.mTarget.onUnbindAccessibilityService(i, i2);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void setActive(final boolean z, final boolean z2) {
        if (this.mIsProxy) {
            lambda$setActive$5(z, z2);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$setActive$5(z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setActiveInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$setActive$5(boolean z, boolean z2) {
        try {
            this.mTarget.setActive(z, z2);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void setInteractive(final boolean z, final boolean z2) {
        if (this.mIsProxy) {
            lambda$setInteractive$6(z, z2);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$setInteractive$6(z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setInteractiveInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$setInteractive$6(boolean z, boolean z2) {
        try {
            this.mTarget.setInteractive(z, z2);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void scheduleStartInputIfNecessary(final boolean z) {
        if (this.mIsProxy) {
            lambda$scheduleStartInputIfNecessary$7(z);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$scheduleStartInputIfNecessary$7(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: scheduleStartInputIfNecessaryInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleStartInputIfNecessary$7(boolean z) {
        try {
            this.mTarget.scheduleStartInputIfNecessary(z);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void reportFullscreenMode(final boolean z) {
        if (this.mIsProxy) {
            lambda$reportFullscreenMode$8(z);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$reportFullscreenMode$8(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportFullscreenModeInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$reportFullscreenMode$8(boolean z) {
        try {
            this.mTarget.reportFullscreenMode(z);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void setImeTraceEnabled(final boolean z) {
        if (this.mIsProxy) {
            lambda$setImeTraceEnabled$9(z);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$setImeTraceEnabled$9(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setImeTraceEnabledInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$setImeTraceEnabled$9(boolean z) {
        try {
            this.mTarget.setImeTraceEnabled(z);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void throwExceptionFromSystem(final java.lang.String str) {
        if (this.mIsProxy) {
            lambda$throwExceptionFromSystem$10(str);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.IInputMethodClientInvoker.this.lambda$throwExceptionFromSystem$10(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: throwExceptionFromSystemInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$throwExceptionFromSystem$10(java.lang.String str) {
        try {
            this.mTarget.throwExceptionFromSystem(str);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    android.os.IBinder asBinder() {
        return this.mTarget.asBinder();
    }
}
