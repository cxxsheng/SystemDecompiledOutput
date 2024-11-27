package android.app;

/* loaded from: classes.dex */
public abstract class ClientTransactionHandler {
    private boolean mIsExecutingLocalTransaction;

    public abstract void countLaunchingActivities(int i);

    public abstract java.util.Map<android.os.IBinder, android.app.servertransaction.DestroyActivityItem> getActivitiesToBeDestroyed();

    public abstract android.app.Activity getActivity(android.os.IBinder iBinder);

    public abstract android.app.ActivityThread.ActivityClientRecord getActivityClient(android.os.IBinder iBinder);

    public abstract android.app.LoadedApk getPackageInfoNoCheck(android.content.pm.ApplicationInfo applicationInfo);

    abstract android.app.servertransaction.TransactionExecutor getTransactionExecutor();

    public abstract android.content.Context getWindowContext(android.os.IBinder iBinder);

    public abstract void handleActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration, int i, android.window.ActivityWindowInfo activityWindowInfo);

    public abstract void handleAttachSplashScreenView(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, android.view.SurfaceControl surfaceControl);

    public abstract void handleConfigurationChanged(android.content.res.Configuration configuration, int i);

    public abstract void handleDestroyActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, int i, boolean z2, java.lang.String str);

    public abstract android.app.Activity handleLaunchActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, int i, android.content.Intent intent);

    public abstract void handleNewIntent(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<com.android.internal.content.ReferrerIntent> list);

    public abstract void handlePauseActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, boolean z2, int i, boolean z3, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, java.lang.String str);

    public abstract void handlePictureInPictureRequested(android.app.ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void handlePictureInPictureStateChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.PictureInPictureUiState pictureInPictureUiState);

    public abstract void handleRelaunchActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions);

    public abstract void handleResumeActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, boolean z2, boolean z3, java.lang.String str);

    public abstract void handleSendResult(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<android.app.ResultInfo> list, java.lang.String str);

    public abstract void handleStartActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo);

    public abstract void handleStopActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, boolean z, java.lang.String str);

    public abstract void handleTopResumedActivityChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str);

    public abstract void handleWindowContextInfoChanged(android.os.IBinder iBinder, android.window.WindowContextInfo windowContextInfo);

    public abstract void handleWindowContextWindowRemoval(android.os.IBinder iBinder);

    public abstract boolean isHandleSplashScreenExit(android.os.IBinder iBinder);

    public abstract void performRestartActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z);

    public abstract android.app.ActivityThread.ActivityClientRecord prepareRelaunchActivity(android.os.IBinder iBinder, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, int i, android.util.MergedConfiguration mergedConfiguration, boolean z, android.window.ActivityWindowInfo activityWindowInfo);

    public abstract void reportRefresh(android.app.ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void reportRelaunch(android.app.ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void reportStop(android.app.servertransaction.PendingTransactionActions pendingTransactionActions);

    abstract void sendMessage(int i, java.lang.Object obj);

    public abstract void updatePendingActivityConfiguration(android.os.IBinder iBinder, android.content.res.Configuration configuration);

    public abstract void updatePendingConfiguration(android.content.res.Configuration configuration);

    public abstract void updateProcessState(int i, boolean z);

    void scheduleTransaction(android.app.servertransaction.ClientTransaction clientTransaction) {
        clientTransaction.preExecute(this);
        sendMessage(159, clientTransaction);
    }

    public void executeTransaction(android.app.servertransaction.ClientTransaction clientTransaction) {
        this.mIsExecutingLocalTransaction = true;
        try {
            clientTransaction.preExecute(this);
            getTransactionExecutor().execute(clientTransaction);
        } finally {
            this.mIsExecutingLocalTransaction = false;
            clientTransaction.recycle();
        }
    }

    public boolean isExecutingLocalTransaction() {
        return this.mIsExecutingLocalTransaction;
    }
}
