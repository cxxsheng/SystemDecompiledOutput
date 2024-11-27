package android.app.servertransaction;

/* loaded from: classes.dex */
public class TransactionExecutor {
    private static final boolean DEBUG_RESOLVER = false;
    private static final java.lang.String TAG = "TransactionExecutor";
    private final android.app.ClientTransactionHandler mTransactionHandler;
    private final android.app.servertransaction.PendingTransactionActions mPendingActions = new android.app.servertransaction.PendingTransactionActions();
    private final android.app.servertransaction.TransactionExecutorHelper mHelper = new android.app.servertransaction.TransactionExecutorHelper();
    private final android.util.ArrayMap<android.content.Context, android.content.res.Configuration> mContextToPreChangedConfigMap = new android.util.ArrayMap<>();

    public TransactionExecutor(android.app.ClientTransactionHandler clientTransactionHandler) {
        this.mTransactionHandler = clientTransactionHandler;
    }

    public void execute(android.app.servertransaction.ClientTransaction clientTransaction) {
        android.os.Trace.traceBegin(32L, "clientTransactionExecuted");
        try {
            try {
                if (clientTransaction.getTransactionItems() != null) {
                    executeTransactionItems(clientTransaction);
                } else {
                    executeCallbacks(clientTransaction);
                    executeLifecycleState(clientTransaction);
                }
                android.os.Trace.traceEnd(32L);
                if (!this.mContextToPreChangedConfigMap.isEmpty()) {
                    try {
                        android.util.ArraySet arraySet = new android.util.ArraySet();
                        int size = this.mContextToPreChangedConfigMap.size();
                        for (int i = 0; i < size; i++) {
                            android.content.Context keyAt = this.mContextToPreChangedConfigMap.keyAt(i);
                            if (!android.app.WindowConfiguration.areConfigurationsEqualForDisplay(keyAt.getResources().getConfiguration(), this.mContextToPreChangedConfigMap.valueAt(i))) {
                                arraySet.add(java.lang.Integer.valueOf(keyAt.getDisplayId()));
                            }
                        }
                        android.app.servertransaction.ClientTransactionListenerController clientTransactionListenerController = android.app.servertransaction.ClientTransactionListenerController.getInstance();
                        int size2 = arraySet.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            clientTransactionListenerController.onDisplayChanged(((java.lang.Integer) arraySet.valueAt(i2)).intValue());
                        }
                    } finally {
                        this.mContextToPreChangedConfigMap.clear();
                    }
                }
                this.mPendingActions.clear();
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to execute the transaction: " + android.app.servertransaction.TransactionExecutorHelper.transactionToString(clientTransaction, this.mTransactionHandler));
                throw e;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    public void executeTransactionItems(android.app.servertransaction.ClientTransaction clientTransaction) {
        java.util.List<android.app.servertransaction.ClientTransactionItem> transactionItems = clientTransaction.getTransactionItems();
        int size = transactionItems.size();
        for (int i = 0; i < size; i++) {
            android.app.servertransaction.ClientTransactionItem clientTransactionItem = transactionItems.get(i);
            if (clientTransactionItem.isActivityLifecycleItem()) {
                executeLifecycleItem(clientTransaction, (android.app.servertransaction.ActivityLifecycleItem) clientTransactionItem);
            } else {
                executeNonLifecycleItem(clientTransaction, clientTransactionItem, android.app.servertransaction.TransactionExecutorHelper.shouldExcludeLastLifecycleState(transactionItems, i));
            }
        }
    }

    @java.lang.Deprecated
    public void executeCallbacks(android.app.servertransaction.ClientTransaction clientTransaction) {
        java.util.List<android.app.servertransaction.ClientTransactionItem> callbacks = clientTransaction.getCallbacks();
        if (callbacks == null || callbacks.isEmpty()) {
            return;
        }
        android.app.servertransaction.ActivityLifecycleItem lifecycleStateRequest = clientTransaction.getLifecycleStateRequest();
        int targetState = lifecycleStateRequest != null ? lifecycleStateRequest.getTargetState() : -1;
        int lastCallbackRequestingState = android.app.servertransaction.TransactionExecutorHelper.lastCallbackRequestingState(clientTransaction);
        int size = callbacks.size();
        int i = 0;
        while (i < size) {
            android.app.servertransaction.ClientTransactionItem clientTransactionItem = callbacks.get(i);
            int postExecutionState = clientTransactionItem.getPostExecutionState();
            executeNonLifecycleItem(clientTransaction, clientTransactionItem, postExecutionState != -1 && i == lastCallbackRequestingState && targetState == postExecutionState);
            i++;
        }
    }

    private void executeNonLifecycleItem(android.app.servertransaction.ClientTransaction clientTransaction, android.app.servertransaction.ClientTransactionItem clientTransactionItem, boolean z) {
        android.content.Context context;
        int closestPreExecutionState;
        android.os.IBinder activityToken = clientTransactionItem.getActivityToken();
        android.app.ActivityThread.ActivityClientRecord activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        if (activityToken != null && activityClient == null && this.mTransactionHandler.getActivitiesToBeDestroyed().containsKey(activityToken)) {
            android.util.Slog.w(TAG, "Skip pre-destroyed transaction item:\n" + clientTransactionItem);
            return;
        }
        int postExecutionState = clientTransactionItem.getPostExecutionState();
        if (clientTransactionItem.shouldHaveDefinedPreExecutionState() && (closestPreExecutionState = this.mHelper.getClosestPreExecutionState(activityClient, postExecutionState)) != -1) {
            cycleToPath(activityClient, closestPreExecutionState, clientTransaction);
        }
        if (!this.mTransactionHandler.isExecutingLocalTransaction() && com.android.window.flags.Flags.bundleClientTransactionFlag()) {
            context = clientTransactionItem.getContextToUpdate(this.mTransactionHandler);
        } else {
            context = null;
        }
        if (context != null && !this.mContextToPreChangedConfigMap.containsKey(context)) {
            this.mContextToPreChangedConfigMap.put(context, new android.content.res.Configuration(context.getResources().getConfiguration()));
        }
        clientTransactionItem.execute(this.mTransactionHandler, this.mPendingActions);
        clientTransactionItem.postExecute(this.mTransactionHandler, this.mPendingActions);
        if (activityClient == null) {
            activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        }
        if (postExecutionState != -1 && activityClient != null) {
            cycleToPath(activityClient, postExecutionState, z, clientTransaction);
        }
    }

    @java.lang.Deprecated
    private void executeLifecycleState(android.app.servertransaction.ClientTransaction clientTransaction) {
        android.app.servertransaction.ActivityLifecycleItem lifecycleStateRequest = clientTransaction.getLifecycleStateRequest();
        if (lifecycleStateRequest == null) {
            return;
        }
        executeLifecycleItem(clientTransaction, lifecycleStateRequest);
    }

    private void executeLifecycleItem(android.app.servertransaction.ClientTransaction clientTransaction, android.app.servertransaction.ActivityLifecycleItem activityLifecycleItem) {
        android.os.IBinder activityToken = activityLifecycleItem.getActivityToken();
        android.app.ActivityThread.ActivityClientRecord activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        if (activityClient == null) {
            if (this.mTransactionHandler.getActivitiesToBeDestroyed().get(activityToken) == activityLifecycleItem) {
                activityLifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
            }
        } else {
            cycleToPath(activityClient, activityLifecycleItem.getTargetState(), true, clientTransaction);
            activityLifecycleItem.execute(this.mTransactionHandler, this.mPendingActions);
            activityLifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
        }
    }

    public void cycleToPath(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i, android.app.servertransaction.ClientTransaction clientTransaction) {
        cycleToPath(activityClientRecord, i, false, clientTransaction);
    }

    private void cycleToPath(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i, boolean z, android.app.servertransaction.ClientTransaction clientTransaction) {
        performLifecycleSequence(activityClientRecord, this.mHelper.getLifecyclePath(activityClientRecord.getLifecycleState(), i, z), clientTransaction);
    }

    private void performLifecycleSequence(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.util.IntArray intArray, android.app.servertransaction.ClientTransaction clientTransaction) {
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            switch (i2) {
                case 1:
                    this.mTransactionHandler.handleLaunchActivity(activityClientRecord, this.mPendingActions, -1, null);
                    break;
                case 2:
                    this.mTransactionHandler.handleStartActivity(activityClientRecord, this.mPendingActions, null);
                    break;
                case 3:
                    this.mTransactionHandler.handleResumeActivity(activityClientRecord, false, activityClientRecord.isForward, false, "LIFECYCLER_RESUME_ACTIVITY");
                    break;
                case 4:
                    this.mTransactionHandler.handlePauseActivity(activityClientRecord, false, false, 0, false, this.mPendingActions, "LIFECYCLER_PAUSE_ACTIVITY");
                    break;
                case 5:
                    this.mTransactionHandler.handleStopActivity(activityClientRecord, 0, this.mPendingActions, false, "LIFECYCLER_STOP_ACTIVITY");
                    break;
                case 6:
                    this.mTransactionHandler.handleDestroyActivity(activityClientRecord, false, 0, false, "performLifecycleSequence. cycling to:" + intArray.get(size - 1));
                    break;
                case 7:
                    this.mTransactionHandler.performRestartActivity(activityClientRecord, false);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unexpected lifecycle state: " + i2);
            }
        }
    }
}
