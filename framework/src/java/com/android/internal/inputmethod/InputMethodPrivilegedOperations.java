package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodPrivilegedOperations {
    private static final java.lang.String TAG = "InputMethodPrivilegedOperations";
    private final com.android.internal.inputmethod.InputMethodPrivilegedOperations.OpsHolder mOps = new com.android.internal.inputmethod.InputMethodPrivilegedOperations.OpsHolder();

    private static final class OpsHolder {
        private com.android.internal.inputmethod.IInputMethodPrivilegedOperations mPrivOps;

        private OpsHolder() {
        }

        public synchronized void set(com.android.internal.inputmethod.IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
            if (this.mPrivOps != null) {
                throw new java.lang.IllegalStateException("IInputMethodPrivilegedOperations must be set at most once. privOps=" + iInputMethodPrivilegedOperations);
            }
            this.mPrivOps = iInputMethodPrivilegedOperations;
        }

        private static java.lang.String getCallerMethodName() {
            java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
            if (stackTrace.length <= 4) {
                return "<bottom of call stack>";
            }
            return stackTrace[4].getMethodName();
        }

        public synchronized com.android.internal.inputmethod.IInputMethodPrivilegedOperations getAndWarnIfNull() {
            if (this.mPrivOps == null) {
                android.util.Log.e(com.android.internal.inputmethod.InputMethodPrivilegedOperations.TAG, getCallerMethodName() + " is ignored. Call it within attachToken() and InputMethodService.onDestroy()");
            }
            return this.mPrivOps;
        }
    }

    public void set(com.android.internal.inputmethod.IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
        java.util.Objects.requireNonNull(iInputMethodPrivilegedOperations, "privOps must not be null");
        this.mOps.set(iInputMethodPrivilegedOperations);
    }

    public void setImeWindowStatusAsync(int i, int i2) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.setImeWindowStatusAsync(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportStartInputAsync(android.os.IBinder iBinder) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.reportStartInputAsync(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public com.android.internal.inputmethod.IInputContentUriToken createInputContentUriToken(android.net.Uri uri, java.lang.String str) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return null;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.createInputContentUriToken(uri, str, androidFuture);
            return com.android.internal.inputmethod.IInputContentUriToken.Stub.asInterface((android.os.IBinder) com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture));
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public void reportFullscreenModeAsync(boolean z) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.reportFullscreenModeAsync(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateStatusIconAsync(java.lang.String str, int i) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.updateStatusIconAsync(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInputMethod(java.lang.String str) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.setInputMethod(str, androidFuture);
            com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInputMethodAndSubtype(java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.setInputMethodAndSubtype(str, inputMethodSubtype, androidFuture);
            com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void hideMySoftInput(android.view.inputmethod.ImeTracker.Token token, int i, int i2) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 46);
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.hideMySoftInput(token, i, i2, androidFuture);
            com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void showMySoftInput(android.view.inputmethod.ImeTracker.Token token, int i, int i2) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 46);
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.showMySoftInput(token, i, i2, androidFuture);
            com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchToPreviousInputMethod() {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.switchToPreviousInputMethod(androidFuture);
            return ((java.lang.Boolean) com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchToNextInputMethod(boolean z) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.switchToNextInputMethod(z, androidFuture);
            return ((java.lang.Boolean) com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldOfferSwitchingToNextInputMethod() {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            andWarnIfNull.shouldOfferSwitchingToNextInputMethod(androidFuture);
            return ((java.lang.Boolean) com.android.internal.inputmethod.CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyUserActionAsync() {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.notifyUserActionAsync();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applyImeVisibilityAsync(android.os.IBinder iBinder, boolean z, android.view.inputmethod.ImeTracker.Token token) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 46);
        try {
            andWarnIfNull.applyImeVisibilityAsync(iBinder, z, token);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onStylusHandwritingReady(int i, int i2) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.onStylusHandwritingReady(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetStylusHandwriting(int i) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.resetStylusHandwriting(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void switchKeyboardLayoutAsync(int i) {
        com.android.internal.inputmethod.IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.switchKeyboardLayoutAsync(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
