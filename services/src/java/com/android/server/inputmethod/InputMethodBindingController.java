package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodBindingController {
    static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int IME_CONNECTION_BIND_FLAGS = 1082654725;

    @com.android.internal.annotations.VisibleForTesting
    static final int IME_VISIBLE_BIND_FLAGS = 738201601;
    private static final java.lang.String TAG = com.android.server.inputmethod.InputMethodBindingController.class.getSimpleName();
    static final long TIME_TO_RECONNECT = 3000;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private java.lang.String mCurId;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private android.content.Intent mCurIntent;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.server.inputmethod.IInputMethodInvoker mCurMethod;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mCurMethodUid;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mCurSeq;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private android.os.IBinder mCurToken;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean mHasMainConnection;
    private final int mImeConnectionBindFlags;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private long mLastBindTime;

    @android.annotation.Nullable
    private java.util.concurrent.CountDownLatch mLatchForTesting;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final android.content.ServiceConnection mMainConnection;

    @android.annotation.NonNull
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private java.lang.String mSelectedMethodId;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.InputMethodManagerService mService;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean mSupportsConnectionlessStylusHw;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean mSupportsStylusHw;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean mVisibleBound;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final android.content.ServiceConnection mVisibleConnection;

    @android.annotation.NonNull
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;

    InputMethodBindingController(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
        this(inputMethodManagerService, IME_CONNECTION_BIND_FLAGS, null);
    }

    InputMethodBindingController(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService, int i, java.util.concurrent.CountDownLatch countDownLatch) {
        this.mCurMethodUid = -1;
        this.mVisibleConnection = new android.content.ServiceConnection() { // from class: com.android.server.inputmethod.InputMethodBindingController.1
            @Override // android.content.ServiceConnection
            public void onBindingDied(android.content.ComponentName componentName) {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        com.android.server.inputmethod.InputMethodBindingController.this.mService.invalidateAutofillSessionLocked();
                        if (com.android.server.inputmethod.InputMethodBindingController.this.isVisibleBound()) {
                            com.android.server.inputmethod.InputMethodBindingController.this.unbindVisibleConnection();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    com.android.server.inputmethod.InputMethodBindingController.this.mService.invalidateAutofillSessionLocked();
                }
            }
        };
        this.mMainConnection = new android.content.ServiceConnection() { // from class: com.android.server.inputmethod.InputMethodBindingController.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.os.Trace.traceBegin(32L, "IMMS.onServiceConnected");
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (com.android.server.inputmethod.InputMethodBindingController.this.mCurIntent != null && componentName.equals(com.android.server.inputmethod.InputMethodBindingController.this.mCurIntent.getComponent())) {
                            com.android.server.inputmethod.InputMethodBindingController.this.mCurMethod = com.android.server.inputmethod.IInputMethodInvoker.create(com.android.internal.inputmethod.IInputMethod.Stub.asInterface(iBinder));
                            updateCurrentMethodUid();
                            if (com.android.server.inputmethod.InputMethodBindingController.this.mCurToken == null) {
                                android.util.Slog.w(com.android.server.inputmethod.InputMethodBindingController.TAG, "Service connected without a token!");
                                com.android.server.inputmethod.InputMethodBindingController.this.unbindCurrentMethod();
                                android.os.Trace.traceEnd(32L);
                                return;
                            }
                            android.view.inputmethod.InputMethodInfo queryInputMethodForCurrentUserLocked = com.android.server.inputmethod.InputMethodBindingController.this.mService.queryInputMethodForCurrentUserLocked(com.android.server.inputmethod.InputMethodBindingController.this.mSelectedMethodId);
                            boolean z = com.android.server.inputmethod.InputMethodBindingController.this.mSupportsStylusHw != queryInputMethodForCurrentUserLocked.supportsStylusHandwriting();
                            com.android.server.inputmethod.InputMethodBindingController.this.mSupportsStylusHw = queryInputMethodForCurrentUserLocked.supportsStylusHandwriting();
                            if (z) {
                                android.view.inputmethod.InputMethodManager.invalidateLocalStylusHandwritingAvailabilityCaches();
                            }
                            if (com.android.server.inputmethod.InputMethodBindingController.this.mSupportsConnectionlessStylusHw != queryInputMethodForCurrentUserLocked.supportsConnectionlessStylusHandwriting()) {
                                com.android.server.inputmethod.InputMethodBindingController.this.mSupportsConnectionlessStylusHw = queryInputMethodForCurrentUserLocked.supportsConnectionlessStylusHandwriting();
                                android.view.inputmethod.InputMethodManager.invalidateLocalConnectionlessStylusHandwritingAvailabilityCaches();
                            }
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.initializeImeLocked(com.android.server.inputmethod.InputMethodBindingController.this.mCurMethod, com.android.server.inputmethod.InputMethodBindingController.this.mCurToken);
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.scheduleNotifyImeUidToAudioService(com.android.server.inputmethod.InputMethodBindingController.this.mCurMethodUid);
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.reRequestCurrentClientSessionLocked();
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.performOnCreateInlineSuggestionsRequestLocked();
                        }
                        com.android.server.inputmethod.InputMethodBindingController.this.mService.scheduleResetStylusHandwriting();
                        android.os.Trace.traceEnd(32L);
                        if (com.android.server.inputmethod.InputMethodBindingController.this.mLatchForTesting != null) {
                            com.android.server.inputmethod.InputMethodBindingController.this.mLatchForTesting.countDown();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
            private void updateCurrentMethodUid() {
                java.lang.String packageName = com.android.server.inputmethod.InputMethodBindingController.this.mCurIntent.getComponent().getPackageName();
                int packageUid = com.android.server.inputmethod.InputMethodBindingController.this.mPackageManagerInternal.getPackageUid(packageName, 0L, com.android.server.inputmethod.InputMethodBindingController.this.mService.getCurrentImeUserIdLocked());
                if (packageUid < 0) {
                    android.util.Slog.e(com.android.server.inputmethod.InputMethodBindingController.TAG, "Failed to get UID for package=" + packageName);
                    com.android.server.inputmethod.InputMethodBindingController.this.mCurMethodUid = -1;
                    return;
                }
                com.android.server.inputmethod.InputMethodBindingController.this.mCurMethodUid = packageUid;
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(@android.annotation.NonNull android.content.ComponentName componentName) {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (com.android.server.inputmethod.InputMethodBindingController.this.mCurMethod != null && com.android.server.inputmethod.InputMethodBindingController.this.mCurIntent != null && componentName.equals(com.android.server.inputmethod.InputMethodBindingController.this.mCurIntent.getComponent())) {
                            com.android.server.inputmethod.InputMethodBindingController.this.mLastBindTime = android.os.SystemClock.uptimeMillis();
                            com.android.server.inputmethod.InputMethodBindingController.this.clearCurMethodAndSessions();
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.clearInputShownLocked();
                            com.android.server.inputmethod.InputMethodBindingController.this.mService.unbindCurrentClientLocked(3);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mService = inputMethodManagerService;
        this.mContext = this.mService.mContext;
        this.mPackageManagerInternal = this.mService.mPackageManagerInternal;
        this.mWindowManagerInternal = this.mService.mWindowManagerInternal;
        this.mImeConnectionBindFlags = i;
        this.mLatchForTesting = countDownLatch;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    long getLastBindTime() {
        return this.mLastBindTime;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean hasMainConnection() {
        return this.mHasMainConnection;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    java.lang.String getCurId() {
        return this.mCurId;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    java.lang.String getSelectedMethodId() {
        return this.mSelectedMethodId;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setSelectedMethodId(@android.annotation.Nullable java.lang.String str) {
        this.mSelectedMethodId = str;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    android.os.IBinder getCurToken() {
        return this.mCurToken;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    android.content.Intent getCurIntent() {
        return this.mCurIntent;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    int getSequenceNumber() {
        return this.mCurSeq;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void advanceSequenceNumber() {
        this.mCurSeq++;
        if (this.mCurSeq <= 0) {
            this.mCurSeq = 1;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    com.android.server.inputmethod.IInputMethodInvoker getCurMethod() {
        return this.mCurMethod;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    int getCurMethodUid() {
        return this.mCurMethodUid;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean isVisibleBound() {
        return this.mVisibleBound;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean supportsStylusHandwriting() {
        return this.mSupportsStylusHw;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean supportsConnectionlessStylusHandwriting() {
        return this.mSupportsConnectionlessStylusHw;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void unbindCurrentMethod() {
        if (isVisibleBound()) {
            unbindVisibleConnection();
        }
        if (hasMainConnection()) {
            unbindMainConnection();
        }
        if (getCurToken() != null) {
            removeCurrentToken();
            this.mService.resetSystemUiLocked();
        }
        this.mCurId = null;
        clearCurMethodAndSessions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void clearCurMethodAndSessions() {
        this.mService.clearClientSessionsLocked();
        this.mCurMethod = null;
        this.mCurMethodUid = -1;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void removeCurrentToken() {
        this.mWindowManagerInternal.removeWindowToken(this.mCurToken, false, false, this.mService.getCurTokenDisplayIdLocked());
        this.mCurToken = null;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    com.android.internal.inputmethod.InputBindResult bindCurrentMethod() {
        if (this.mSelectedMethodId == null) {
            android.util.Slog.e(TAG, "mSelectedMethodId is null!");
            return com.android.internal.inputmethod.InputBindResult.NO_IME;
        }
        android.view.inputmethod.InputMethodInfo queryInputMethodForCurrentUserLocked = this.mService.queryInputMethodForCurrentUserLocked(this.mSelectedMethodId);
        if (queryInputMethodForCurrentUserLocked == null) {
            throw new java.lang.IllegalArgumentException("Unknown id: " + this.mSelectedMethodId);
        }
        this.mCurIntent = createImeBindingIntent(queryInputMethodForCurrentUserLocked.getComponent());
        if (bindCurrentInputMethodServiceMainConnection()) {
            this.mCurId = queryInputMethodForCurrentUserLocked.getId();
            this.mLastBindTime = android.os.SystemClock.uptimeMillis();
            addFreshWindowToken();
            return new com.android.internal.inputmethod.InputBindResult(2, (com.android.internal.inputmethod.IInputMethodSession) null, (android.util.SparseArray) null, (android.view.InputChannel) null, this.mCurId, this.mCurSeq, false);
        }
        android.util.Slog.w("InputMethodManagerService", "Failure connecting to input method service: " + this.mCurIntent);
        this.mCurIntent = null;
        return com.android.internal.inputmethod.InputBindResult.IME_NOT_CONNECTED;
    }

    @android.annotation.NonNull
    private android.content.Intent createImeBindingIntent(android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent("android.view.InputMethod");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.client_label", android.R.string.indeterminate_progress_38);
        intent.putExtra("android.intent.extra.client_intent", android.app.PendingIntent.getActivity(this.mContext, 0, new android.content.Intent("android.settings.INPUT_METHOD_SETTINGS"), 67108864));
        return intent;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void addFreshWindowToken() {
        int displayIdToShowImeLocked = this.mService.getDisplayIdToShowImeLocked();
        this.mCurToken = new android.os.Binder();
        this.mService.setCurTokenDisplayIdLocked(displayIdToShowImeLocked);
        this.mWindowManagerInternal.addWindowToken(this.mCurToken, 2011, displayIdToShowImeLocked, null);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void unbindMainConnection() {
        this.mContext.unbindService(this.mMainConnection);
        this.mHasMainConnection = false;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void unbindVisibleConnection() {
        this.mContext.unbindService(this.mVisibleConnection);
        this.mVisibleBound = false;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean bindCurrentInputMethodService(android.content.ServiceConnection serviceConnection, int i) {
        if (this.mCurIntent == null || serviceConnection == null) {
            android.util.Slog.e(TAG, "--- bind failed: service = " + this.mCurIntent + ", conn = " + serviceConnection);
            return false;
        }
        return this.mContext.bindServiceAsUser(this.mCurIntent, serviceConnection, i, new android.os.UserHandle(this.mService.getCurrentImeUserIdLocked()));
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean bindCurrentInputMethodServiceMainConnection() {
        this.mHasMainConnection = bindCurrentInputMethodService(this.mMainConnection, this.mImeConnectionBindFlags);
        return this.mHasMainConnection;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setCurrentMethodVisible() {
        if (this.mCurMethod != null) {
            if (hasMainConnection() && !isVisibleBound()) {
                this.mVisibleBound = bindCurrentInputMethodService(this.mVisibleConnection, IME_VISIBLE_BIND_FLAGS);
                return;
            }
            return;
        }
        if (!hasMainConnection()) {
            bindCurrentMethod();
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mLastBindTime;
        if (uptimeMillis >= 3000) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.IMF_FORCE_RECONNECT_IME, getSelectedMethodId(), java.lang.Long.valueOf(uptimeMillis), 1);
            android.util.Slog.w(TAG, "Force disconnect/connect to the IME in setCurrentMethodVisible()");
            unbindMainConnection();
            bindCurrentInputMethodServiceMainConnection();
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setCurrentMethodNotVisible() {
        if (isVisibleBound()) {
            unbindVisibleConnection();
        }
    }
}
