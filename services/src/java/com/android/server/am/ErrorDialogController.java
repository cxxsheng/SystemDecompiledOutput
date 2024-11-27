package com.android.server.am;

/* loaded from: classes.dex */
final class ErrorDialogController {

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    @android.annotation.Nullable
    private android.app.AnrController mAnrController;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private java.util.List<com.android.server.am.AppNotRespondingDialog> mAnrDialogs;
    private final com.android.server.am.ProcessRecord mApp;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private java.util.List<com.android.server.am.AppErrorDialog> mCrashDialogs;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private java.util.List<com.android.server.am.StrictModeViolationDialog> mViolationDialogs;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private com.android.server.am.AppWaitingForDebuggerDialog mWaitDialog;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasCrashDialogs() {
        return this.mCrashDialogs != null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    java.util.List<com.android.server.am.AppErrorDialog> getCrashDialogs() {
        return this.mCrashDialogs;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasAnrDialogs() {
        return this.mAnrDialogs != null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    java.util.List<com.android.server.am.AppNotRespondingDialog> getAnrDialogs() {
        return this.mAnrDialogs;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasViolationDialogs() {
        return this.mViolationDialogs != null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasDebugWaitingDialog() {
        return this.mWaitDialog != null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearAllErrorDialogs() {
        clearCrashDialogs();
        clearAnrDialogs();
        clearViolationDialogs();
        clearWaitingDialog();
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearCrashDialogs() {
        clearCrashDialogs(true);
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearCrashDialogs(boolean z) {
        if (this.mCrashDialogs == null) {
            return;
        }
        if (z) {
            scheduleForAllDialogs(this.mCrashDialogs, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda0());
        }
        this.mCrashDialogs = null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearAnrDialogs() {
        if (this.mAnrDialogs == null) {
            return;
        }
        scheduleForAllDialogs(this.mAnrDialogs, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda0());
        this.mAnrDialogs = null;
        this.mAnrController = null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearViolationDialogs() {
        if (this.mViolationDialogs == null) {
            return;
        }
        scheduleForAllDialogs(this.mViolationDialogs, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda0());
        this.mViolationDialogs = null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void clearWaitingDialog() {
        if (this.mWaitDialog == null) {
            return;
        }
        final com.android.server.am.AppWaitingForDebuggerDialog appWaitingForDebuggerDialog = this.mWaitDialog;
        android.os.Handler handler = this.mService.mUiHandler;
        java.util.Objects.requireNonNull(appWaitingForDebuggerDialog);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.BaseErrorDialog.this.dismiss();
            }
        });
        this.mWaitDialog = null;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void scheduleForAllDialogs(final java.util.List<? extends com.android.server.am.BaseErrorDialog> list, final java.util.function.Consumer<com.android.server.am.BaseErrorDialog> consumer) {
        this.mService.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ErrorDialogController.this.lambda$scheduleForAllDialogs$0(list, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleForAllDialogs$0(java.util.List list, java.util.function.Consumer consumer) {
        if (list != null) {
            forAllDialogs(list, consumer);
        }
    }

    void forAllDialogs(java.util.List<? extends com.android.server.am.BaseErrorDialog> list, java.util.function.Consumer<com.android.server.am.BaseErrorDialog> consumer) {
        for (int size = list.size() - 1; size >= 0; size--) {
            consumer.accept(list.get(size));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void showCrashDialogs(com.android.server.am.AppErrorDialog.Data data) {
        java.util.List<android.content.Context> displayContexts = getDisplayContexts(false);
        this.mCrashDialogs = new java.util.ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mCrashDialogs.add(new com.android.server.am.AppErrorDialog(displayContexts.get(size), this.mService, data));
        }
        this.mService.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ErrorDialogController.this.lambda$showCrashDialogs$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCrashDialogs$1() {
        java.util.List<com.android.server.am.AppErrorDialog> list;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                list = this.mCrashDialogs;
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (list != null) {
            forAllDialogs(list, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda4());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void showAnrDialogs(com.android.server.am.AppNotRespondingDialog.Data data) {
        java.util.List<android.content.Context> displayContexts = getDisplayContexts(this.mApp.mErrorState.isSilentAnr());
        this.mAnrDialogs = new java.util.ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mAnrDialogs.add(new com.android.server.am.AppNotRespondingDialog(this.mService, displayContexts.get(size), data));
        }
        scheduleForAllDialogs(this.mAnrDialogs, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda4());
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void showViolationDialogs(com.android.server.am.AppErrorResult appErrorResult) {
        java.util.List<android.content.Context> displayContexts = getDisplayContexts(false);
        this.mViolationDialogs = new java.util.ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mViolationDialogs.add(new com.android.server.am.StrictModeViolationDialog(displayContexts.get(size), this.mService, appErrorResult, this.mApp));
        }
        scheduleForAllDialogs(this.mViolationDialogs, new com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda4());
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void showDebugWaitingDialogs() {
        this.mWaitDialog = new com.android.server.am.AppWaitingForDebuggerDialog(this.mService, getDisplayContexts(true).get(0), this.mApp);
        this.mService.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ErrorDialogController.this.lambda$showDebugWaitingDialogs$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDebugWaitingDialogs$2() {
        com.android.server.am.AppWaitingForDebuggerDialog appWaitingForDebuggerDialog;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                appWaitingForDebuggerDialog = this.mWaitDialog;
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (appWaitingForDebuggerDialog != null) {
            appWaitingForDebuggerDialog.show();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    @android.annotation.Nullable
    android.app.AnrController getAnrController() {
        return this.mAnrController;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setAnrController(android.app.AnrController anrController) {
        this.mAnrController = anrController;
    }

    private java.util.List<android.content.Context> getDisplayContexts(boolean z) {
        android.content.Context context;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (!z) {
            this.mApp.getWindowProcessController().getDisplayContextsWithErrorDialogs(arrayList);
        }
        if (arrayList.isEmpty() || z) {
            if (this.mService.mWmInternal != null) {
                context = this.mService.mWmInternal.getTopFocusedDisplayUiContext();
            } else {
                context = this.mService.mUiContext;
            }
            arrayList.add(context);
        }
        return arrayList;
    }

    ErrorDialogController(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
        this.mProcLock = this.mService.mProcLock;
    }
}
