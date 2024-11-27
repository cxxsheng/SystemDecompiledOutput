package com.android.server.inputmethod;

/* loaded from: classes2.dex */
public class ZeroJankProxy extends com.android.internal.view.IInputMethodManager.Stub {
    private final java.util.concurrent.Executor mExecutor;
    private final com.android.internal.view.IInputMethodManager mInner;

    ZeroJankProxy(java.util.concurrent.Executor executor, com.android.internal.view.IInputMethodManager iInputMethodManager) {
        this.mInner = iInputMethodManager;
        this.mExecutor = executor;
    }

    private void offload(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable) {
        offloadInner(throwingRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void offload(java.lang.Runnable runnable) {
        offloadInner(runnable);
    }

    private void offloadInner(final java.lang.Runnable runnable) {
        final boolean z = runnable instanceof com.android.internal.util.FunctionalUtils.ThrowingRunnable;
        final long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.ZeroJankProxy.lambda$offloadInner$0(clearCallingIdentity, z, runnable);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$offloadInner$0(long j, boolean z, java.lang.Runnable runnable) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.os.Binder.restoreCallingIdentity(j);
        try {
            try {
                if (z) {
                    ((com.android.internal.util.FunctionalUtils.ThrowingRunnable) runnable).runOrThrow();
                } else {
                    runnable.run();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Exception e) {
                android.util.Slog.e("InputMethodManagerService", "Error in async call", e);
                throw android.util.ExceptionUtils.propagate(e);
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addClient$1(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws java.lang.Exception {
        this.mInner.addClient(iInputMethodClient, iRemoteInputConnection, i);
    }

    public void addClient(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, final int i) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda10
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$addClient$1(iInputMethodClient, iRemoteInputConnection, i);
            }
        });
    }

    public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws android.os.RemoteException {
        return this.mInner.getCurrentInputMethodInfoAsUser(i);
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) throws android.os.RemoteException {
        return this.mInner.getInputMethodList(i, i2);
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) throws android.os.RemoteException {
        return this.mInner.getEnabledInputMethodList(i);
    }

    public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        return this.mInner.getEnabledInputMethodSubtypeList(str, z, i);
    }

    public android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) throws android.os.RemoteException {
        return this.mInner.getLastInputMethodSubtype(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInput$2(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws java.lang.Exception {
        this.mInner.showSoftInput(iInputMethodClient, iBinder, token, i, i2, resultReceiver, i3);
    }

    public boolean showSoftInput(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final android.os.IBinder iBinder, @android.annotation.Nullable final android.view.inputmethod.ImeTracker.Token token, final int i, final int i2, final android.os.ResultReceiver resultReceiver, final int i3) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$showSoftInput$2(iInputMethodClient, iBinder, token, i, i2, resultReceiver, i3);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideSoftInput$3(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) throws java.lang.Exception {
        this.mInner.hideSoftInput(iInputMethodClient, iBinder, token, i, resultReceiver, i2);
    }

    public boolean hideSoftInput(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final android.os.IBinder iBinder, @android.annotation.Nullable final android.view.inputmethod.ImeTracker.Token token, final int i, final android.os.ResultReceiver resultReceiver, final int i2) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$hideSoftInput$3(iInputMethodClient, iBinder, token, i, resultReceiver, i2);
            }
        });
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void hideSoftInputFromServerForTest() throws android.os.RemoteException {
        super.hideSoftInputFromServerForTest_enforcePermission();
        this.mInner.hideSoftInputFromServerForTest();
    }

    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS_FULL")
    public void startInputOrWindowGainedFocusAsync(final int i, final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final android.os.IBinder iBinder, final int i2, final int i3, final int i4, @android.annotation.Nullable final android.view.inputmethod.EditorInfo editorInfo, final com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final int i5, final int i6, @android.annotation.NonNull final android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, final int i7) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$startInputOrWindowGainedFocusAsync$4(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher, i7);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInputOrWindowGainedFocusAsync$4(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) throws java.lang.Exception {
        sendOnStartInputResult(iInputMethodClient, this.mInner.startInputOrWindowGainedFocus(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher), i7);
    }

    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS_FULL")
    public com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, @android.annotation.Nullable android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) throws android.os.RemoteException {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showInputMethodPickerFromClient$5(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) throws java.lang.Exception {
        this.mInner.showInputMethodPickerFromClient(iInputMethodClient, i);
    }

    public void showInputMethodPickerFromClient(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$showInputMethodPickerFromClient$5(iInputMethodClient, i);
            }
        });
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void showInputMethodPickerFromSystem(int i, int i2) throws android.os.RemoteException {
        this.mInner.showInputMethodPickerFromSystem(i, i2);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public boolean isInputMethodPickerShownForTest() throws android.os.RemoteException {
        super.isInputMethodPickerShownForTest_enforcePermission();
        return this.mInner.isInputMethodPickerShownForTest();
    }

    public android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) throws android.os.RemoteException {
        return this.mInner.getCurrentInputMethodSubtype(i);
    }

    public void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) throws android.os.RemoteException {
        this.mInner.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, i);
    }

    public void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, @android.annotation.NonNull int[] iArr, int i) throws android.os.RemoteException {
        this.mInner.setExplicitlyEnabledInputMethodSubtypes(str, iArr, i);
    }

    public int getInputMethodWindowVisibleHeight(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
        return this.mInner.getInputMethodWindowVisibleHeight(iInputMethodClient);
    }

    public void reportPerceptibleAsync(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        this.mInner.reportPerceptibleAsync(iBinder, z);
    }

    @android.annotation.EnforcePermission("android.permission.INTERNAL_SYSTEM_WINDOW")
    public void removeImeSurface() throws android.os.RemoteException {
        this.mInner.removeImeSurface();
    }

    public void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) throws android.os.RemoteException {
        this.mInner.removeImeSurfaceFromWindowAsync(iBinder);
    }

    public void startProtoDump(byte[] bArr, int i, java.lang.String str) throws android.os.RemoteException {
        this.mInner.startProtoDump(bArr, i, str);
    }

    public boolean isImeTraceEnabled() throws android.os.RemoteException {
        return this.mInner.isImeTraceEnabled();
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_UI_TRACING")
    public void startImeTrace() throws android.os.RemoteException {
        this.mInner.startImeTrace();
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_UI_TRACING")
    public void stopImeTrace() throws android.os.RemoteException {
        this.mInner.stopImeTrace();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startStylusHandwriting$6(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws java.lang.Exception {
        this.mInner.startStylusHandwriting(iInputMethodClient);
    }

    public void startStylusHandwriting(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$startStylusHandwriting$6(iInputMethodClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startConnectionlessStylusHandwriting$7(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws java.lang.Exception {
        this.mInner.startConnectionlessStylusHandwriting(iInputMethodClient, i, cursorAnchorInfo, str, str2, iConnectionlessHandwritingCallback);
    }

    public void startConnectionlessStylusHandwriting(final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i, @android.annotation.Nullable final android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.String str2, @android.annotation.NonNull final com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$startConnectionlessStylusHandwriting$7(iInputMethodClient, i, cursorAnchorInfo, str, str2, iConnectionlessHandwritingCallback);
            }
        });
    }

    public boolean acceptStylusHandwritingDelegation(@android.annotation.NonNull final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, final int i2) {
        try {
            return ((java.lang.Boolean) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Boolean lambda$acceptStylusHandwritingDelegation$8;
                    lambda$acceptStylusHandwritingDelegation$8 = com.android.server.inputmethod.ZeroJankProxy.this.lambda$acceptStylusHandwritingDelegation$8(iInputMethodClient, i, str, str2, i2);
                    return lambda$acceptStylusHandwritingDelegation$8;
                }
            }, new java.util.concurrent.Executor() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda9
                @Override // java.util.concurrent.Executor
                public final void execute(java.lang.Runnable runnable) {
                    com.android.server.inputmethod.ZeroJankProxy.this.offload(runnable);
                }
            }).get()).booleanValue();
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.RuntimeException(e);
        } catch (java.util.concurrent.ExecutionException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$acceptStylusHandwritingDelegation$8(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2) {
        try {
            return java.lang.Boolean.valueOf(this.mInner.acceptStylusHandwritingDelegation(iInputMethodClient, i, str, str2, i2));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$acceptStylusHandwritingDelegationAsync$9(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws java.lang.Exception {
        this.mInner.acceptStylusHandwritingDelegationAsync(iInputMethodClient, i, str, str2, i2, iBooleanListener);
    }

    public void acceptStylusHandwritingDelegationAsync(@android.annotation.NonNull final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, final int i2, final com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws android.os.RemoteException {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$acceptStylusHandwritingDelegationAsync$9(iInputMethodClient, i, str, str2, i2, iBooleanListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareStylusHandwritingDelegation$10(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2) throws java.lang.Exception {
        this.mInner.prepareStylusHandwritingDelegation(iInputMethodClient, i, str, str2);
    }

    public void prepareStylusHandwritingDelegation(@android.annotation.NonNull final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2) {
        offload(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.inputmethod.ZeroJankProxy.this.lambda$prepareStylusHandwritingDelegation$10(iInputMethodClient, i, str, str2);
            }
        });
    }

    public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws android.os.RemoteException {
        return this.mInner.isStylusHandwritingAvailableAsUser(i, z);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
        this.mInner.addVirtualStylusIdForTestSession(iInputMethodClient);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) throws android.os.RemoteException {
        this.mInner.setStylusWindowIdleTimeoutForTest(iInputMethodClient, j);
    }

    public com.android.internal.inputmethod.IImeTracker getImeTrackerService() throws android.os.RemoteException {
        return this.mInner.getImeTrackerService();
    }

    public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        this.mInner.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        this.mInner.dump(fileDescriptor, printWriter, strArr);
    }

    private void sendOnStartInputResult(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.InputBindResult inputBindResult, int i) {
        com.android.server.inputmethod.ClientState clientState = this.mInner.getClientState(iInputMethodClient);
        if (clientState != null && clientState.mClient != null) {
            clientState.mClient.onStartInputResult(inputBindResult, i);
            return;
        }
        android.util.Slog.i("InputMethodManagerService", "Client that requested startInputOrWindowGainedFocus is no longer bound. InputBindResult: " + inputBindResult + " for startInputSeq: " + i);
    }
}
