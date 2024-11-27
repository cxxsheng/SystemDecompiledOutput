package com.android.server.autofill;

/* loaded from: classes.dex */
final class Session implements com.android.server.autofill.RemoteFillService.FillServiceCallbacks, com.android.server.autofill.ViewState.Listener, com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback, android.service.autofill.ValueFinder, com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks {
    private static final java.lang.String ACTION_DELAYED_FILL = "android.service.autofill.action.DELAYED_FILL";
    static final int AUGMENTED_AUTOFILL_REQUEST_ID = 1;
    private static final boolean DBG = false;
    private static final int DEFAULT__FIELD_CLASSIFICATION_REQUEST_ID_SNAPSHOT = -2;
    private static final int DEFAULT__FILL_REQUEST_ID_SNAPSHOT = -2;
    public static final java.lang.String EXTRA_KEY_DETECTIONS = "detections";
    private static final java.lang.String EXTRA_REQUEST_ID = "android.service.autofill.extra.REQUEST_ID";
    private static final java.lang.String PCC_HINTS_DELIMITER = ",";
    static final java.lang.String REQUEST_ID_KEY = "autofill_request_id";
    static final java.lang.String SESSION_ID_KEY = "autofill_session_id";
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_REMOVED = 3;
    public static final int STATE_UNKNOWN = 0;
    private static final java.lang.String TAG = "AutofillSession";
    public final int id;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder mActivityToken;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.Runnable mAugmentedAutofillDestroyer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.view.autofill.AutofillId> mAugmentedAutofillableIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.metrics.LogMaker> mAugmentedRequestsLogs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.view.autofill.IAutoFillManagerClient mClient;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.Bundle mClientState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder.DeathRecipient mClientVulture;
    private final boolean mCompatMode;

    @android.annotation.NonNull
    private final android.content.ComponentName mComponentName;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.service.autofill.FillContext> mContexts;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.autofill.AutofillId mCurrentViewId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDelayedFillBroadcastReceiverRegistered;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.PendingIntent mDelayedFillPendingIntent;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mDestroyed;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.FillRequestEventLogger mFillRequestEventLogger;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.FillResponseEventLogger mFillResponseEventLogger;
    public final int mFlags;
    private final android.os.Handler mHandler;
    private boolean mHasCallback;
    private boolean mIgnoreViewStateResetToEmpty;

    @android.annotation.NonNull
    final com.android.server.autofill.AutofillInlineSessionController mInlineSessionController;
    private boolean mIsPrimaryCredential;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.autofill.AutofillId[] mLastFillDialogTriggerIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.Pair<java.lang.Integer, android.view.inputmethod.InlineSuggestionsRequest> mLastInlineSuggestionsRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLatencyBaseTime;
    final java.lang.Object mLock;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mLogViewEntered;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mLoggedInlineDatasetShown;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.ui.PendingUi mPendingSaveUi;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.PresentationStatsEventLogger mPresentationStatsEventLogger;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPreviouslyFillDialogPotentiallyStarted;

    @android.annotation.Nullable
    private final com.android.server.autofill.RemoteFillService mRemoteFillService;
    private int mRequestCount;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.service.autofill.FillResponse> mResponses;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.SaveEventLogger mSaveEventLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSaveOnAllViewsInvisible;

    @android.annotation.Nullable
    private final com.android.server.autofill.SecondaryProviderHandler mSecondaryProviderHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.service.autofill.FillResponse> mSecondaryResponses;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<java.lang.String> mSelectedDatasetIds;
    private final com.android.server.autofill.AutofillManagerServiceImpl mService;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.SessionCommittedEventLogger mSessionCommittedEventLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.autofill.Session.SessionFlags mSessionFlags;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSessionState;
    private final long mStartTime;
    private final com.android.server.autofill.ui.AutoFillUI mUi;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.LocalLog mUiLatencyHistory;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mUiShownTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.assist.AssistStructure.ViewNode mUrlBar;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.LocalLog mWtfHistory;
    public final int taskId;
    public final int uid;
    public final int userId;
    private static final android.content.ComponentName CREDMAN_SERVICE_COMPONENT_NAME = new android.content.ComponentName("com.android.credentialmanager", "com.android.credentialmanager.autofill.CredentialAutofillService");
    private static java.util.concurrent.atomic.AtomicInteger sIdCounter = new java.util.concurrent.atomic.AtomicInteger(2);
    private static java.util.concurrent.atomic.AtomicInteger sIdCounterForPcc = new java.util.concurrent.atomic.AtomicInteger(2);
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.view.autofill.AutofillId, com.android.server.autofill.ViewState> mViewStates = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mFillRequestIdSnapshot = -2;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mFieldClassificationIdSnapshot = -2;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.metrics.LogMaker> mRequestLogs = new android.util.SparseArray<>(1);
    private final com.android.server.autofill.Session.AssistDataReceiverImpl mAssistReceiver = new com.android.server.autofill.Session.AssistDataReceiverImpl();
    private final com.android.server.autofill.Session.PccAssistDataReceiverImpl mPccAssistReceiver = new com.android.server.autofill.Session.PccAssistDataReceiverImpl();
    private final com.android.server.autofill.Session.ClassificationState mClassificationState = new com.android.server.autofill.Session.ClassificationState();
    private final android.content.BroadcastReceiver mDelayedFillBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.autofill.Session.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (!intent.getAction().equals(com.android.server.autofill.Session.ACTION_DELAYED_FILL)) {
                android.util.Slog.wtf(com.android.server.autofill.Session.TAG, "Unexpected action is received.");
                return;
            }
            if (!intent.hasExtra(com.android.server.autofill.Session.EXTRA_REQUEST_ID)) {
                android.util.Slog.e(com.android.server.autofill.Session.TAG, "Delay fill action is missing request id extra.");
                return;
            }
            android.util.Slog.v(com.android.server.autofill.Session.TAG, "mDelayedFillBroadcastReceiver delayed fill action received");
            synchronized (com.android.server.autofill.Session.this.mLock) {
                com.android.server.autofill.Session.this.mAssistReceiver.processDelayedFillLocked(intent.getIntExtra(com.android.server.autofill.Session.EXTRA_REQUEST_ID, 0), (android.service.autofill.FillResponse) intent.getParcelableExtra("android.service.autofill.extra.FILL_RESPONSE", android.service.autofill.FillResponse.class));
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SessionState {
    }

    void onSwitchInputMethodLocked() {
        if (!this.mSessionFlags.mExpiredResponse && shouldResetSessionStateOnInputMethodSwitch()) {
            this.mSessionFlags.mExpiredResponse = true;
            this.mAugmentedAutofillableIds = null;
            if (this.mSessionFlags.mAugmentedAutofillOnly) {
                this.mCurrentViewId = null;
            }
        }
    }

    private boolean shouldResetSessionStateOnInputMethodSwitch() {
        if (this.mService.getRemoteInlineSuggestionRenderServiceLocked() == null) {
            return false;
        }
        if (this.mSessionFlags.mInlineSupportedByService) {
            return true;
        }
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(this.mCurrentViewId);
        return (viewState == null || (viewState.getState() & 4096) == 0) ? false : true;
    }

    private final class SessionFlags {
        private boolean mAugmentedAutofillOnly;
        private boolean mAutofillDisabled;
        private boolean mExpiredResponse;
        private boolean mFillDialogDisabled;
        private boolean mInlineSupportedByService;
        private boolean mScreenHasCredmanField;
        private boolean mShowingSaveUi;

        private SessionFlags() {
        }
    }

    final class AssistDataReceiverImpl extends android.app.IAssistDataReceiver.Stub {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.service.autofill.FillRequest mLastFillRequest;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.service.autofill.FillRequest mPendingFillRequest;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.view.inputmethod.InlineSuggestionsRequest mPendingInlineSuggestionsRequest;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mWaitForInlineRequest;

        AssistDataReceiverImpl() {
        }

        @android.annotation.Nullable
        java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> newAutofillRequestLocked(com.android.server.autofill.ViewState viewState, boolean z) {
            this.mPendingFillRequest = null;
            this.mWaitForInlineRequest = z;
            this.mPendingInlineSuggestionsRequest = null;
            if (!z) {
                return null;
            }
            return new com.android.server.autofill.InlineSuggestionRequestConsumer(new java.lang.ref.WeakReference(this), new java.lang.ref.WeakReference(viewState));
        }

        void handleInlineSuggestionRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.server.autofill.ViewState viewState) {
            synchronized (com.android.server.autofill.Session.this.mLock) {
                try {
                    if (this.mWaitForInlineRequest && this.mPendingInlineSuggestionsRequest == null) {
                        this.mWaitForInlineRequest = inlineSuggestionsRequest != null;
                        this.mPendingInlineSuggestionsRequest = inlineSuggestionsRequest;
                        maybeRequestFillLocked();
                        viewState.resetState(65536);
                    }
                } finally {
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void maybeRequestFillLocked() {
            if (this.mPendingFillRequest == null) {
                return;
            }
            com.android.server.autofill.Session.this.mFieldClassificationIdSnapshot = com.android.server.autofill.Session.sIdCounterForPcc.get();
            if (this.mWaitForInlineRequest) {
                if (this.mPendingInlineSuggestionsRequest == null) {
                    return;
                } else {
                    this.mPendingFillRequest = new android.service.autofill.FillRequest(this.mPendingFillRequest.getId(), this.mPendingFillRequest.getFillContexts(), this.mPendingFillRequest.getHints(), this.mPendingFillRequest.getClientState(), this.mPendingFillRequest.getFlags(), this.mPendingInlineSuggestionsRequest, this.mPendingFillRequest.getDelayedFillIntentSender());
                }
            }
            this.mLastFillRequest = this.mPendingFillRequest;
            if (com.android.server.autofill.Session.this.shouldRequestSecondaryProvider(this.mPendingFillRequest.getFlags()) && com.android.server.autofill.Session.this.mSecondaryProviderHandler != null) {
                android.util.Slog.v(com.android.server.autofill.Session.TAG, "Requesting fill response to secondary provider.");
                com.android.server.autofill.Session.this.mSecondaryProviderHandler.onFillRequest(this.mPendingFillRequest, this.mPendingInlineSuggestionsRequest, this.mPendingFillRequest.getFlags(), com.android.server.autofill.Session.this.id, com.android.server.autofill.Session.this.mClient);
            } else if (com.android.server.autofill.Session.this.mRemoteFillService != null) {
                if (com.android.server.autofill.Session.this.mIsPrimaryCredential) {
                    this.mPendingFillRequest = com.android.server.autofill.Session.this.addSessionIdAndRequestIdToClientState(this.mPendingFillRequest, this.mPendingInlineSuggestionsRequest, com.android.server.autofill.Session.this.id);
                    com.android.server.autofill.Session.this.mRemoteFillService.onFillCredentialRequest(this.mPendingFillRequest, com.android.server.autofill.Session.this.mClient);
                } else {
                    com.android.server.autofill.Session.this.mRemoteFillService.onFillRequest(this.mPendingFillRequest);
                }
            }
            this.mPendingInlineSuggestionsRequest = null;
            this.mWaitForInlineRequest = false;
            this.mPendingFillRequest = null;
            int elapsedRealtime = (int) (android.os.SystemClock.elapsedRealtime() - com.android.server.autofill.Session.this.mLatencyBaseTime);
            com.android.server.autofill.Session.this.mPresentationStatsEventLogger.maybeSetFillRequestSentTimestampMs(elapsedRealtime);
            com.android.server.autofill.Session.this.mFillRequestEventLogger.maybeSetLatencyFillRequestSentMillis(elapsedRealtime);
            com.android.server.autofill.Session.this.mFillRequestEventLogger.logAndEndEvent();
        }

        public void onHandleAssistData(android.os.Bundle bundle) throws android.os.RemoteException {
            int i;
            android.content.IntentSender intentSender;
            if (com.android.server.autofill.Session.this.mRemoteFillService == null) {
                com.android.server.autofill.Session.this.wtf(null, "onHandleAssistData() called without a remote service. mForAugmentedAutofillOnly: %s", java.lang.Boolean.valueOf(com.android.server.autofill.Session.this.mSessionFlags.mAugmentedAutofillOnly));
                return;
            }
            android.view.autofill.AutofillId autofillId = com.android.server.autofill.Session.this.mCurrentViewId;
            if (autofillId == null) {
                android.util.Slog.w(com.android.server.autofill.Session.TAG, "No current view id - session might have finished");
                return;
            }
            android.app.assist.AssistStructure assistStructure = (android.app.assist.AssistStructure) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_STRUCTURE, android.app.assist.AssistStructure.class);
            if (assistStructure == null) {
                android.util.Slog.e(com.android.server.autofill.Session.TAG, "No assist structure - app might have crashed providing it");
                return;
            }
            android.os.Bundle bundle2 = bundle.getBundle(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_RECEIVER_EXTRAS);
            if (bundle2 == null) {
                android.util.Slog.e(com.android.server.autofill.Session.TAG, "No receiver extras - app might have crashed providing it");
                return;
            }
            int i2 = bundle2.getInt(com.android.server.autofill.Session.EXTRA_REQUEST_ID);
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.Session.TAG, "New structure for requestId " + i2 + ": " + assistStructure);
            }
            synchronized (com.android.server.autofill.Session.this.mLock) {
                try {
                    try {
                        assistStructure.ensureDataForAutofill();
                        java.util.ArrayList<android.view.autofill.AutofillId> autofillIds = com.android.server.autofill.Helper.getAutofillIds(assistStructure, false);
                        for (int i3 = 0; i3 < autofillIds.size(); i3++) {
                            autofillIds.get(i3).setSessionId(com.android.server.autofill.Session.this.id);
                        }
                        int flags = assistStructure.getFlags();
                        if (!com.android.server.autofill.Session.this.mCompatMode) {
                            i = flags;
                        } else {
                            java.lang.String[] urlBarResourceIdsForCompatMode = com.android.server.autofill.Session.this.mService.getUrlBarResourceIdsForCompatMode(com.android.server.autofill.Session.this.mComponentName.getPackageName());
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(com.android.server.autofill.Session.TAG, "url_bars in compat mode: " + java.util.Arrays.toString(urlBarResourceIdsForCompatMode));
                            }
                            if (urlBarResourceIdsForCompatMode != null) {
                                com.android.server.autofill.Session.this.mUrlBar = com.android.server.autofill.Helper.sanitizeUrlBar(assistStructure, urlBarResourceIdsForCompatMode);
                                if (com.android.server.autofill.Session.this.mUrlBar != null) {
                                    android.view.autofill.AutofillId autofillId2 = com.android.server.autofill.Session.this.mUrlBar.getAutofillId();
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(com.android.server.autofill.Session.TAG, "Setting urlBar as id=" + autofillId2 + " and domain " + com.android.server.autofill.Session.this.mUrlBar.getWebDomain());
                                    }
                                    com.android.server.autofill.Session.this.mViewStates.put(autofillId2, new com.android.server.autofill.ViewState(autofillId2, com.android.server.autofill.Session.this, 512, com.android.server.autofill.Session.this.mIsPrimaryCredential));
                                }
                            }
                            i = flags | 2;
                        }
                        assistStructure.sanitizeForParceling(true);
                        if (com.android.server.autofill.Session.this.mContexts == null) {
                            com.android.server.autofill.Session.this.mContexts = new java.util.ArrayList(1);
                        }
                        com.android.server.autofill.Session.this.mContexts.add(new android.service.autofill.FillContext(i2, assistStructure, autofillId));
                        com.android.server.autofill.Session.this.cancelCurrentRequestLocked();
                        int size = com.android.server.autofill.Session.this.mContexts.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            com.android.server.autofill.Session.this.fillContextWithAllowedValuesLocked((android.service.autofill.FillContext) com.android.server.autofill.Session.this.mContexts.get(i4), i);
                        }
                        java.util.ArrayList mergePreviousSessionLocked = com.android.server.autofill.Session.this.mergePreviousSessionLocked(false);
                        java.util.List typeHintsForProvider = com.android.server.autofill.Session.this.getTypeHintsForProvider();
                        com.android.server.autofill.Session.this.mDelayedFillPendingIntent = com.android.server.autofill.Session.this.createPendingIntent(i2);
                        android.os.Bundle bundle3 = com.android.server.autofill.Session.this.mClientState;
                        if (com.android.server.autofill.Session.this.mDelayedFillPendingIntent == null) {
                            intentSender = null;
                        } else {
                            intentSender = com.android.server.autofill.Session.this.mDelayedFillPendingIntent.getIntentSender();
                        }
                        this.mPendingFillRequest = new android.service.autofill.FillRequest(i2, mergePreviousSessionLocked, typeHintsForProvider, bundle3, i, null, intentSender);
                        maybeRequestFillLocked();
                    } catch (java.lang.RuntimeException e) {
                        com.android.server.autofill.Session.this.wtf(e, "Exception lazy loading assist structure for %s: %s", assistStructure.getActivityComponent(), e);
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (com.android.server.autofill.Session.this.mActivityToken != null) {
                com.android.server.autofill.Session.this.mService.sendActivityAssistDataToContentCapture(com.android.server.autofill.Session.this.mActivityToken, bundle);
            }
        }

        public void onHandleAssistScreenshot(android.graphics.Bitmap bitmap) {
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void processDelayedFillLocked(int i, android.service.autofill.FillResponse fillResponse) {
            if (this.mLastFillRequest != null && i == this.mLastFillRequest.getId()) {
                android.util.Slog.v(com.android.server.autofill.Session.TAG, "processDelayedFillLocked: calling onFillRequestSuccess with new response");
                com.android.server.autofill.Session.this.onFillRequestSuccess(i, fillResponse, com.android.server.autofill.Session.this.mService.getServicePackageName(), this.mLastFillRequest.getFlags());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.service.autofill.FillRequest addSessionIdAndRequestIdToClientState(android.service.autofill.FillRequest fillRequest, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, int i) {
        if (fillRequest.getClientState() == null) {
            fillRequest = new android.service.autofill.FillRequest(fillRequest.getId(), fillRequest.getFillContexts(), fillRequest.getHints(), new android.os.Bundle(), fillRequest.getFlags(), inlineSuggestionsRequest, fillRequest.getDelayedFillIntentSender());
        }
        fillRequest.getClientState().putInt(SESSION_ID_KEY, i);
        fillRequest.getClientState().putInt(REQUEST_ID_KEY, fillRequest.getId());
        return fillRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<java.lang.String> getTypeHintsForProvider() {
        if (!this.mService.isPccClassificationEnabled()) {
            return java.util.Collections.EMPTY_LIST;
        }
        java.lang.String pccProviderHints = this.mService.getMaster().getPccProviderHints();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "TypeHints flag:" + pccProviderHints);
        }
        if (android.text.TextUtils.isEmpty(pccProviderHints)) {
            return new java.util.ArrayList();
        }
        return java.util.List.of((java.lang.Object[]) pccProviderHints.split(PCC_HINTS_DELIMITER));
    }

    private final class PccAssistDataReceiverImpl extends android.app.IAssistDataReceiver.Stub {
        private PccAssistDataReceiverImpl() {
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void maybeRequestFieldClassificationFromServiceLocked() {
            if (com.android.server.autofill.Session.this.mClassificationState.mPendingFieldClassificationRequest == null) {
                android.util.Slog.w(com.android.server.autofill.Session.TAG, "Received AssistData without pending classification request");
                return;
            }
            com.android.server.autofill.RemoteFieldClassificationService remoteFieldClassificationServiceLocked = com.android.server.autofill.Session.this.mService.getRemoteFieldClassificationServiceLocked();
            if (remoteFieldClassificationServiceLocked != null) {
                remoteFieldClassificationServiceLocked.onFieldClassificationRequest(com.android.server.autofill.Session.this.mClassificationState.mPendingFieldClassificationRequest, new java.lang.ref.WeakReference<>(com.android.server.autofill.Session.this));
            }
            com.android.server.autofill.Session.this.mClassificationState.onFieldClassificationRequestSent();
        }

        public void onHandleAssistData(android.os.Bundle bundle) throws android.os.RemoteException {
            android.app.assist.AssistStructure assistStructure = (android.app.assist.AssistStructure) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_STRUCTURE, android.app.assist.AssistStructure.class);
            if (assistStructure == null) {
                android.util.Slog.e(com.android.server.autofill.Session.TAG, "No assist structure for pcc detection - app might have crashed providing it");
                return;
            }
            android.os.Bundle bundle2 = bundle.getBundle(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_RECEIVER_EXTRAS);
            if (bundle2 == null) {
                android.util.Slog.e(com.android.server.autofill.Session.TAG, "No receiver extras for pcc detection - app might have crashed providing it");
                return;
            }
            int i = bundle2.getInt(com.android.server.autofill.Session.EXTRA_REQUEST_ID);
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.Session.TAG, "New structure for PCC Detection: requestId " + i + ": " + assistStructure);
            }
            synchronized (com.android.server.autofill.Session.this.mLock) {
                try {
                    try {
                        assistStructure.ensureDataForAutofill();
                        java.util.ArrayList<android.view.autofill.AutofillId> autofillIds = com.android.server.autofill.Helper.getAutofillIds(assistStructure, false);
                        for (int i2 = 0; i2 < autofillIds.size(); i2++) {
                            autofillIds.get(i2).setSessionId(com.android.server.autofill.Session.this.id);
                        }
                        com.android.server.autofill.Session.this.mClassificationState.onAssistStructureReceived(assistStructure);
                        maybeRequestFieldClassificationFromServiceLocked();
                    } catch (java.lang.RuntimeException e) {
                        com.android.server.autofill.Session.this.wtf(e, "Exception lazy loading assist structure for %s: %s", assistStructure.getActivityComponent(), e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onHandleAssistScreenshot(android.graphics.Bitmap bitmap) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.PendingIntent createPendingIntent(int i) {
        android.util.Slog.d(TAG, "createPendingIntent for request " + i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.app.PendingIntent.getBroadcast(this.mContext, this.id, new android.content.Intent(ACTION_DELAYED_FILL).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).putExtra(EXTRA_REQUEST_ID, i), 1375731712);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void clearPendingIntentLocked() {
        android.util.Slog.d(TAG, "clearPendingIntentLocked");
        if (this.mDelayedFillPendingIntent == null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDelayedFillPendingIntent.cancel();
            this.mDelayedFillPendingIntent = null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerDelayedFillBroadcastLocked() {
        if (!this.mDelayedFillBroadcastReceiverRegistered) {
            android.util.Slog.v(TAG, "registerDelayedFillBroadcastLocked()");
            this.mContext.registerReceiver(this.mDelayedFillBroadcastReceiver, new android.content.IntentFilter(ACTION_DELAYED_FILL));
            this.mDelayedFillBroadcastReceiverRegistered = true;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unregisterDelayedFillBroadcastLocked() {
        if (this.mDelayedFillBroadcastReceiverRegistered) {
            android.util.Slog.v(TAG, "unregisterDelayedFillBroadcastLocked()");
            this.mContext.unregisterReceiver(this.mDelayedFillBroadcastReceiver);
            this.mDelayedFillBroadcastReceiverRegistered = false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.view.autofill.AutofillId[] getIdsOfAllViewStatesLocked() {
        int size = this.mViewStates.size();
        android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[size];
        for (int i = 0; i < size; i++) {
            autofillIdArr[i] = this.mViewStates.valueAt(i).id;
        }
        return autofillIdArr;
    }

    @android.annotation.Nullable
    public java.lang.String findByAutofillId(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            try {
                android.view.autofill.AutofillValue findValueLocked = findValueLocked(autofillId);
                if (findValueLocked != null) {
                    if (findValueLocked.isText()) {
                        return findValueLocked.getTextValue().toString();
                    }
                    if (findValueLocked.isList()) {
                        java.lang.CharSequence[] autofillOptionsFromContextsLocked = getAutofillOptionsFromContextsLocked(autofillId);
                        if (autofillOptionsFromContextsLocked != null) {
                            java.lang.CharSequence charSequence = autofillOptionsFromContextsLocked[findValueLocked.getListValue()];
                            return charSequence != null ? charSequence.toString() : null;
                        }
                        android.util.Slog.w(TAG, "findByAutofillId(): no autofill options for id " + autofillId);
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.view.autofill.AutofillValue findRawValueByAutofillId(android.view.autofill.AutofillId autofillId) {
        android.view.autofill.AutofillValue findValueLocked;
        synchronized (this.mLock) {
            findValueLocked = findValueLocked(autofillId);
        }
        return findValueLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.autofill.AutofillValue findValueLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        android.view.autofill.AutofillValue findValueFromThisSessionOnlyLocked = findValueFromThisSessionOnlyLocked(autofillId);
        if (findValueFromThisSessionOnlyLocked != null) {
            return getSanitizedValue(com.android.server.autofill.Helper.createSanitizers(getSaveInfoLocked()), autofillId, findValueFromThisSessionOnlyLocked);
        }
        java.util.ArrayList<com.android.server.autofill.Session> previousSessionsLocked = this.mService.getPreviousSessionsLocked(this);
        if (previousSessionsLocked != null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "findValueLocked(): looking on " + previousSessionsLocked.size() + " previous sessions for autofillId " + autofillId);
            }
            for (int i = 0; i < previousSessionsLocked.size(); i++) {
                com.android.server.autofill.Session session = previousSessionsLocked.get(i);
                android.view.autofill.AutofillValue findValueFromThisSessionOnlyLocked2 = session.findValueFromThisSessionOnlyLocked(autofillId);
                if (findValueFromThisSessionOnlyLocked2 != null) {
                    return getSanitizedValue(com.android.server.autofill.Helper.createSanitizers(session.getSaveInfoLocked()), autofillId, findValueFromThisSessionOnlyLocked2);
                }
            }
            return null;
        }
        return null;
    }

    @android.annotation.Nullable
    private android.view.autofill.AutofillValue findValueFromThisSessionOnlyLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
        if (viewState == null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "findValueLocked(): no view state for " + autofillId);
                return null;
            }
            return null;
        }
        android.view.autofill.AutofillValue currentValue = viewState.getCurrentValue();
        if (currentValue == null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "findValueLocked(): no current value for " + autofillId);
            }
            return getValueFromContextsLocked(autofillId);
        }
        return currentValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void fillContextWithAllowedValuesLocked(@android.annotation.NonNull android.service.autofill.FillContext fillContext, int i) {
        android.app.assist.AssistStructure.ViewNode[] findViewNodesByAutofillIds = fillContext.findViewNodesByAutofillIds(getIdsOfAllViewStatesLocked());
        int size = this.mViewStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.autofill.ViewState valueAt = this.mViewStates.valueAt(i2);
            android.app.assist.AssistStructure.ViewNode viewNode = findViewNodesByAutofillIds[i2];
            if (viewNode == null) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "fillContextWithAllowedValuesLocked(): no node for " + valueAt.id);
                }
            } else {
                android.view.autofill.AutofillValue currentValue = valueAt.getCurrentValue();
                android.view.autofill.AutofillValue autofilledValue = valueAt.getAutofilledValue();
                android.app.assist.AssistStructure.AutofillOverlay autofillOverlay = new android.app.assist.AssistStructure.AutofillOverlay();
                if (autofilledValue != null && autofilledValue.equals(currentValue)) {
                    autofillOverlay.value = currentValue;
                }
                if (this.mCurrentViewId != null) {
                    autofillOverlay.focused = this.mCurrentViewId.equals(valueAt.id);
                    if (autofillOverlay.focused && (i & 1) != 0) {
                        autofillOverlay.value = currentValue;
                    }
                }
                viewNode.setAutofillOverlay(autofillOverlay);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cancelCurrentRequestLocked() {
        if (this.mRemoteFillService == null) {
            wtf(null, "cancelCurrentRequestLocked() called without a remote service. mForAugmentedAutofillOnly: %s", java.lang.Boolean.valueOf(this.mSessionFlags.mAugmentedAutofillOnly));
            return;
        }
        int cancelCurrentRequest = this.mRemoteFillService.cancelCurrentRequest();
        if (cancelCurrentRequest != Integer.MIN_VALUE && this.mContexts != null) {
            for (int size = this.mContexts.size() - 1; size >= 0; size--) {
                if (this.mContexts.get(size).getRequestId() == cancelCurrentRequest) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "cancelCurrentRequest(): id = " + cancelCurrentRequest);
                    }
                    this.mContexts.remove(size);
                    return;
                }
            }
        }
    }

    private boolean isViewFocusedLocked(int i) {
        return (i & 16) == 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestNewFillResponseLocked(@android.annotation.NonNull com.android.server.autofill.ViewState viewState, int i, int i2) {
        int andIncrement;
        android.service.autofill.FillResponse secondaryResponse = shouldRequestSecondaryProvider(i2) ? viewState.getSecondaryResponse() : viewState.getResponse();
        this.mFillRequestEventLogger.startLogForNewRequest();
        this.mRequestCount++;
        this.mFillRequestEventLogger.maybeSetAppPackageUid(this.uid);
        this.mFillRequestEventLogger.maybeSetFlags(this.mFlags);
        if (this.mPreviouslyFillDialogPotentiallyStarted) {
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(3);
        } else {
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(4);
        }
        if (secondaryResponse != null) {
            setViewStatesLocked(secondaryResponse, 1, true, true);
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(5);
        }
        this.mSessionFlags.mExpiredResponse = false;
        this.mSessionState = 1;
        if (this.mSessionFlags.mAugmentedAutofillOnly || this.mRemoteFillService == null) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "requestNewFillResponse(): triggering augmented autofill instead (mForAugmentedAutofillOnly=" + this.mSessionFlags.mAugmentedAutofillOnly + ", flags=" + i2 + ")");
            }
            this.mSessionFlags.mAugmentedAutofillOnly = true;
            this.mFillRequestEventLogger.maybeSetRequestId(1);
            this.mFillRequestEventLogger.maybeSetIsAugmented(true);
            this.mFillRequestEventLogger.logAndEndEvent();
            triggerAugmentedAutofillLocked(i2);
            return;
        }
        viewState.setState(i);
        do {
            andIncrement = sIdCounter.getAndIncrement();
        } while (andIncrement == Integer.MIN_VALUE);
        int size = this.mRequestLogs.size() + 1;
        android.metrics.LogMaker addTaggedData = newLogMaker(907).addTaggedData(1454, java.lang.Integer.valueOf(size));
        if (i2 != 0) {
            addTaggedData.addTaggedData(1452, java.lang.Integer.valueOf(i2));
        }
        this.mRequestLogs.put(andIncrement, addTaggedData);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Requesting structure for request #" + size + " ,requestId=" + andIncrement + ", flags=" + i2);
        }
        boolean z = (i2 & 2048) != 0;
        this.mPresentationStatsEventLogger.maybeSetRequestId(andIncrement);
        this.mPresentationStatsEventLogger.maybeSetIsCredentialRequest(z);
        this.mPresentationStatsEventLogger.maybeSetFieldClassificationRequestId(this.mFieldClassificationIdSnapshot);
        this.mFillRequestEventLogger.maybeSetRequestId(andIncrement);
        this.mFillRequestEventLogger.maybeSetAutofillServiceUid(getAutofillServiceUid());
        if (this.mSessionFlags.mInlineSupportedByService) {
            this.mFillRequestEventLogger.maybeSetInlineSuggestionHostUid(this.mContext, this.userId);
        }
        this.mFillRequestEventLogger.maybeSetIsFillDialogEligible(!this.mSessionFlags.mFillDialogDisabled);
        cancelCurrentRequestLocked();
        if (this.mService.isPccClassificationEnabled() && this.mClassificationState.mHintsToAutofillIdMap == null) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "triggering field classification");
            }
            requestAssistStructureForPccLocked(i2 | 512);
        }
        com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked = this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (this.mSessionFlags.mInlineSupportedByService && remoteInlineSuggestionRenderServiceLocked != null && (isViewFocusedLocked(i2) || isRequestSupportFillDialog(i2))) {
            java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> newAutofillRequestLocked = this.mAssistReceiver.newAutofillRequestLocked(viewState, true);
            if (newAutofillRequestLocked != null) {
                remoteInlineSuggestionRenderServiceLocked.getInlineSuggestionsRendererInfo(new android.os.RemoteCallback(new com.android.server.autofill.InlineSuggestionRendorInfoCallbackOnResultListener(new java.lang.ref.WeakReference(this), andIncrement, newAutofillRequestLocked, this.mCurrentViewId), this.mHandler));
                viewState.setState(65536);
            }
        } else {
            this.mAssistReceiver.newAutofillRequestLocked(viewState, false);
        }
        requestAssistStructureLocked(andIncrement, i2);
    }

    private boolean isRequestSupportFillDialog(int i) {
        return (i & 64) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestAssistStructureForPccLocked(int i) {
        int andIncrement;
        if (this.mClassificationState.shouldTriggerRequest()) {
            this.mFillRequestIdSnapshot = sIdCounter.get();
            this.mClassificationState.updatePendingRequest();
            do {
                andIncrement = sIdCounterForPcc.getAndIncrement();
            } while (andIncrement == Integer.MIN_VALUE);
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "request id is " + andIncrement + ", requesting assist structure for pcc");
            }
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt(EXTRA_REQUEST_ID, andIncrement);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!android.app.ActivityTaskManager.getService().requestAutofillData(this.mPccAssistReceiver, bundle, this.mActivityToken, i)) {
                        android.util.Slog.w(TAG, "failed to request autofill data for " + this.mActivityToken);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestAssistStructureLocked(int i, int i2) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(EXTRA_REQUEST_ID, i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!android.app.ActivityTaskManager.getService().requestAutofillData(this.mAssistReceiver, bundle, this.mActivityToken, i2)) {
                    android.util.Slog.w(TAG, "failed to request autofill data for " + this.mActivityToken);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (android.os.RemoteException e) {
        }
    }

    Session(@android.annotation.NonNull com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl, @android.annotation.NonNull com.android.server.autofill.ui.AutoFillUI autoFillUI, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.NonNull java.lang.Object obj, int i2, int i3, int i4, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2, boolean z, @android.annotation.NonNull android.util.LocalLog localLog, @android.annotation.NonNull android.util.LocalLog localLog2, @android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.NonNull android.content.ComponentName componentName2, boolean z2, boolean z3, boolean z4, int i5, @android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal, boolean z5) {
        android.content.ComponentName componentName3;
        android.content.ComponentName componentName4;
        com.android.server.autofill.RemoteFillService remoteFillService;
        android.content.ComponentName componentName5;
        this.mSessionState = 0;
        byte b = 0;
        if (i2 < 0) {
            wtf(null, "Non-positive sessionId: %s", java.lang.Integer.valueOf(i2));
        }
        this.id = i2;
        this.mFlags = i5;
        this.userId = i;
        this.taskId = i3;
        this.uid = i4;
        this.mService = autofillManagerServiceImpl;
        this.mLock = obj;
        this.mUi = autoFillUI;
        this.mHandler = handler;
        if (z5) {
            componentName4 = componentName;
            componentName3 = CREDMAN_SERVICE_COMPONENT_NAME;
        } else {
            componentName3 = componentName;
            componentName4 = CREDMAN_SERVICE_COMPONENT_NAME;
        }
        android.util.Slog.v(TAG, "Primary service component name: " + componentName3 + ", secondary service component name: " + componentName4);
        if (componentName3 == null) {
            componentName5 = componentName4;
            remoteFillService = null;
        } else {
            componentName5 = componentName4;
            remoteFillService = new com.android.server.autofill.RemoteFillService(context, componentName3, i, this, z3);
        }
        this.mRemoteFillService = remoteFillService;
        this.mSecondaryProviderHandler = componentName5 == null ? null : new com.android.server.autofill.SecondaryProviderHandler(context, i, z3, new com.android.server.autofill.SecondaryProviderHandler.SecondaryProviderCallback() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda1
            @Override // com.android.server.autofill.SecondaryProviderHandler.SecondaryProviderCallback
            public final void onSecondaryFillResponse(android.service.autofill.FillResponse fillResponse, int i6) {
                com.android.server.autofill.Session.this.onSecondaryFillResponse(fillResponse, i6);
            }
        }, componentName5);
        this.mActivityToken = iBinder;
        this.mHasCallback = z;
        this.mUiLatencyHistory = localLog;
        this.mWtfHistory = localLog2;
        this.mContext = com.android.server.autofill.Helper.getDisplayContext(context, ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getDisplayId(iBinder));
        this.mComponentName = componentName2;
        this.mCompatMode = z2;
        this.mSessionState = 1;
        this.mStartTime = android.os.SystemClock.elapsedRealtime();
        this.mLatencyBaseTime = this.mStartTime;
        this.mRequestCount = 0;
        this.mPresentationStatsEventLogger = com.android.server.autofill.PresentationStatsEventLogger.createPresentationLog(i2, i4);
        this.mFillRequestEventLogger = com.android.server.autofill.FillRequestEventLogger.forSessionId(i2);
        this.mFillResponseEventLogger = com.android.server.autofill.FillResponseEventLogger.forSessionId(i2);
        this.mSessionCommittedEventLogger = com.android.server.autofill.SessionCommittedEventLogger.forSessionId(i2);
        this.mSessionCommittedEventLogger.maybeSetComponentPackageUid(i4);
        this.mSaveEventLogger = com.android.server.autofill.SaveEventLogger.forSessionId(i2);
        this.mIsPrimaryCredential = z5;
        this.mIgnoreViewStateResetToEmpty = android.service.autofill.Flags.ignoreViewStateResetToEmpty();
        synchronized (this.mLock) {
            this.mSessionFlags = new com.android.server.autofill.Session.SessionFlags();
            this.mSessionFlags.mAugmentedAutofillOnly = z4;
            this.mSessionFlags.mInlineSupportedByService = this.mService.isInlineSuggestionsEnabledLocked();
            setClientLocked(iBinder2);
        }
        this.mInlineSessionController = new com.android.server.autofill.AutofillInlineSessionController(inputMethodManagerInternal, i, componentName2, handler, this.mLock, new com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback() { // from class: com.android.server.autofill.Session.2
            @Override // com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback
            public void notifyInlineUiShown(android.view.autofill.AutofillId autofillId) {
                com.android.server.autofill.Session.this.notifyFillUiShown(autofillId);
                synchronized (com.android.server.autofill.Session.this.mLock) {
                    com.android.server.autofill.Session.this.mPresentationStatsEventLogger.maybeSetSuggestionPresentedTimestampMs((int) (android.os.SystemClock.elapsedRealtime() - com.android.server.autofill.Session.this.mLatencyBaseTime));
                }
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback
            public void notifyInlineUiHidden(android.view.autofill.AutofillId autofillId) {
                com.android.server.autofill.Session.this.notifyFillUiHidden(autofillId);
            }
        });
        this.mMetricsLogger.write(newLogMaker(906).addTaggedData(1452, java.lang.Integer.valueOf(i5)));
        this.mLogViewEntered = false;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.os.IBinder getActivityTokenLocked() {
        return this.mActivityToken;
    }

    void switchActivity(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#switchActivity() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mActivityToken = iBinder;
                setClientLocked(iBinder2);
                updateTrackedIdsLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setClientLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        unlinkClientVultureLocked();
        this.mClient = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(iBinder);
        this.mClientVulture = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda9
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.autofill.Session.this.lambda$setClientLocked$0();
            }
        };
        try {
            this.mClient.asBinder().linkToDeath(this.mClientVulture, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "could not set binder death listener on autofill client: " + e);
            this.mClientVulture = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setClientLocked$0() {
        synchronized (this.mLock) {
            try {
                android.util.Slog.d(TAG, "handling death of " + this.mActivityToken + " when saving=" + this.mSessionFlags.mShowingSaveUi);
                if (this.mSessionFlags.mShowingSaveUi) {
                    this.mUi.hideFillUi(this);
                } else {
                    this.mUi.destroyAll(this.mPendingSaveUi, this, false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unlinkClientVultureLocked() {
        if (this.mClient != null && this.mClientVulture != null) {
            if (!this.mClient.asBinder().unlinkToDeath(this.mClientVulture, 0)) {
                android.util.Slog.w(TAG, "unlinking vulture from death failed for " + this.mActivityToken);
            }
            this.mClientVulture = null;
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestSuccess(int i, @android.annotation.Nullable android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull java.lang.String str, int i2) {
        long j;
        this.mFillResponseEventLogger.startLogForNewResponse();
        this.mFillResponseEventLogger.maybeSetRequestId(i);
        this.mFillResponseEventLogger.maybeSetAppPackageUid(this.uid);
        this.mFillResponseEventLogger.maybeSetResponseStatus(2);
        this.mFillResponseEventLogger.startResponseProcessingTime();
        int elapsedRealtime = (int) (android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
        this.mPresentationStatsEventLogger.maybeSetFillResponseReceivedTimestampMs(elapsedRealtime);
        this.mFillResponseEventLogger.maybeSetLatencyFillResponseReceivedMillis(elapsedRealtime);
        this.mFillResponseEventLogger.maybeSetDetectionPreference(getDetectionPreferenceForLogging());
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onFillRequestSuccess() rejected - session: " + this.id + " destroyed");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                if (this.mSessionFlags.mShowingSaveUi) {
                    android.util.Slog.w(TAG, "Call to Session#onFillRequestSuccess() rejected - session: " + this.id + " is showing saveUi");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                android.metrics.LogMaker logMaker = this.mRequestLogs.get(i);
                if (logMaker != null) {
                    logMaker.setType(10);
                } else {
                    android.util.Slog.w(TAG, "onFillRequestSuccess(): no request log for id " + i);
                }
                if (fillResponse == null) {
                    this.mFillResponseEventLogger.maybeSetTotalDatasetsProvided(0);
                    if (logMaker != null) {
                        logMaker.addTaggedData(909, -1);
                    }
                    processNullResponseLocked(i, i2);
                    return;
                }
                android.view.autofill.AutofillId[] fieldClassificationIds = fillResponse.getFieldClassificationIds();
                if (fieldClassificationIds == null || this.mService.isFieldClassificationEnabledLocked()) {
                    this.mLastFillDialogTriggerIds = fillResponse.getFillDialogTriggerIds();
                    if ((fillResponse.getFlags() & 4) != 0) {
                        android.util.Slog.v(TAG, "Service requested to wait for delayed fill response.");
                        registerDelayedFillBroadcastLocked();
                    }
                    this.mService.setLastResponse(this.id, fillResponse);
                    synchronized (this.mLock) {
                        try {
                            if (this.mLogViewEntered) {
                                this.mLogViewEntered = false;
                                this.mService.logViewEntered(this.id, null);
                            }
                        } finally {
                        }
                    }
                    long disableDuration = fillResponse.getDisableDuration();
                    boolean z = disableDuration > 0;
                    if (z) {
                        int flags = fillResponse.getFlags();
                        boolean z2 = (flags & 2) != 0;
                        notifyDisableAutofillToClient(disableDuration, z2 ? this.mComponentName : null);
                        if (z2) {
                            j = disableDuration;
                            this.mService.disableAutofillForActivity(this.mComponentName, disableDuration, this.id, this.mCompatMode);
                        } else {
                            j = disableDuration;
                            this.mService.disableAutofillForApp(this.mComponentName.getPackageName(), disableDuration, this.id, this.mCompatMode);
                        }
                        synchronized (this.mLock) {
                            try {
                                this.mSessionFlags.mAutofillDisabled = true;
                                if (triggerAugmentedAutofillLocked(i2) != null) {
                                    this.mSessionFlags.mAugmentedAutofillOnly = true;
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(TAG, "Service disabled autofill for " + this.mComponentName + ", but session is kept for augmented autofill only");
                                    }
                                    return;
                                }
                                if (com.android.server.autofill.Helper.sDebug) {
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder("Service disabled autofill for ");
                                    sb.append(this.mComponentName);
                                    sb.append(": flags=");
                                    sb.append(flags);
                                    sb.append(", duration=");
                                    android.util.TimeUtils.formatDuration(j, sb);
                                    android.util.Slog.d(TAG, sb.toString());
                                }
                            } finally {
                            }
                        }
                    }
                    java.util.List datasets = fillResponse.getDatasets();
                    if (((datasets == null || datasets.isEmpty()) && fillResponse.getAuthentication() == null) || z) {
                        notifyUnavailableToClient(z ? 4 : 0, null);
                        synchronized (this.mLock) {
                            this.mInlineSessionController.setInlineFillUiLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(this.mCurrentViewId));
                        }
                    }
                    if (logMaker != null) {
                        logMaker.addTaggedData(909, java.lang.Integer.valueOf(fillResponse.getDatasets() == null ? 0 : fillResponse.getDatasets().size()));
                        if (fieldClassificationIds != null) {
                            logMaker.addTaggedData(1271, java.lang.Integer.valueOf(fieldClassificationIds.length));
                        }
                    }
                    int size = datasets == null ? 0 : datasets.size();
                    this.mFillResponseEventLogger.maybeSetTotalDatasetsProvided(size);
                    this.mFillResponseEventLogger.maybeSetAvailableCount(size);
                    processResponseLockedForPcc(fillResponse, fillResponse.getClientState(), i2);
                    this.mFillResponseEventLogger.maybeSetLatencyResponseProcessingMillis();
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                android.util.Slog.w(TAG, "Ignoring " + fillResponse + " because field detection is disabled");
                processNullResponseLocked(i, i2);
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void processResponseLockedForPcc(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.Nullable android.os.Bundle bundle, int i) {
        synchronized (this.mLock) {
            try {
                android.service.autofill.FillResponse effectiveFillResponse = getEffectiveFillResponse(fillResponse);
                if (isEmptyResponse(effectiveFillResponse)) {
                    processNullResponseLocked(effectiveFillResponse != null ? effectiveFillResponse.getRequestId() : 0, i);
                } else {
                    processResponseLocked(effectiveFillResponse, bundle, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isEmptyResponse(android.service.autofill.FillResponse fillResponse) {
        boolean z = true;
        if (fillResponse == null) {
            return true;
        }
        android.service.autofill.SaveInfo saveInfo = fillResponse.getSaveInfo();
        synchronized (this.mLock) {
            try {
                if ((fillResponse.getDatasets() != null && !fillResponse.getDatasets().isEmpty()) || fillResponse.getAuthentication() != null || ((saveInfo != null && (!com.android.internal.util.ArrayUtils.isEmpty(saveInfo.getOptionalIds()) || !com.android.internal.util.ArrayUtils.isEmpty(saveInfo.getRequiredIds()) || (saveInfo.getFlags() & 4) != 0)) || !com.android.internal.util.ArrayUtils.isEmpty(fillResponse.getFieldClassificationIds()))) {
                    z = false;
                }
            } finally {
            }
        }
        return z;
    }

    private android.service.autofill.FillResponse getEffectiveFillResponse(android.service.autofill.FillResponse fillResponse) {
        com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer = new com.android.server.autofill.Session.DatasetComputationContainer();
        computeDatasetsForProviderAndUpdateContainer(fillResponse, datasetComputationContainer);
        if (!this.mService.isPccClassificationEnabled()) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "PCC classification is disabled");
            }
            return createShallowCopy(fillResponse, datasetComputationContainer);
        }
        synchronized (this.mLock) {
            try {
                if (this.mClassificationState.mState != 4 || this.mClassificationState.mLastFieldClassificationResponse == null) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("PCC classification no last response:");
                        sb.append(this.mClassificationState.mLastFieldClassificationResponse == null);
                        sb.append(" ,ineligible state=");
                        sb.append(this.mClassificationState.mState != 4);
                        android.util.Slog.v(TAG, sb.toString());
                    }
                    return createShallowCopy(fillResponse, datasetComputationContainer);
                }
                if (!this.mClassificationState.processResponse()) {
                    return fillResponse;
                }
                boolean preferProviderOverPcc = this.mService.getMaster().preferProviderOverPcc();
                boolean shouldUsePccFallback = this.mService.getMaster().shouldUsePccFallback();
                if (preferProviderOverPcc && !shouldUsePccFallback) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "preferAutofillProvider but no fallback");
                    }
                    return createShallowCopy(fillResponse, datasetComputationContainer);
                }
                com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer2 = new com.android.server.autofill.Session.DatasetComputationContainer();
                computeDatasetsForPccAndUpdateContainer(fillResponse, datasetComputationContainer2);
                if (preferProviderOverPcc) {
                    if (shouldUsePccFallback) {
                        addFallbackDatasets(datasetComputationContainer, datasetComputationContainer2);
                    }
                } else {
                    if (shouldUsePccFallback) {
                        addFallbackDatasets(datasetComputationContainer2, datasetComputationContainer);
                    }
                    datasetComputationContainer = datasetComputationContainer2;
                }
                return createShallowCopy(fillResponse, datasetComputationContainer);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSecondaryFillResponse(@android.annotation.Nullable android.service.autofill.FillResponse fillResponse, int i) {
        if (fillResponse == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mFillResponseEventLogger.startLogForNewResponse();
                this.mFillResponseEventLogger.maybeSetRequestId(fillResponse.getRequestId());
                this.mFillResponseEventLogger.maybeSetAppPackageUid(this.uid);
                this.mFillResponseEventLogger.maybeSetResponseStatus(2);
                this.mFillResponseEventLogger.startResponseProcessingTime();
                int elapsedRealtime = (int) (android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
                this.mPresentationStatsEventLogger.maybeSetFillResponseReceivedTimestampMs(elapsedRealtime);
                this.mFillResponseEventLogger.maybeSetLatencyFillResponseReceivedMillis(elapsedRealtime);
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onSecondaryFillResponse() rejected - session: " + this.id + " destroyed");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                java.util.List datasets = fillResponse.getDatasets();
                int size = datasets == null ? 0 : datasets.size();
                this.mFillResponseEventLogger.maybeSetTotalDatasetsProvided(size);
                this.mFillResponseEventLogger.maybeSetAvailableCount(size);
                if (this.mSecondaryResponses == null) {
                    this.mSecondaryResponses = new android.util.SparseArray<>(2);
                }
                this.mSecondaryResponses.put(fillResponse.getRequestId(), fillResponse);
                setViewStatesLocked(fillResponse, 2, false, false);
                com.android.server.autofill.ViewState viewState = this.mViewStates.get(this.mCurrentViewId);
                if (viewState != null) {
                    viewState.maybeCallOnFillReady(i);
                }
                this.mFillResponseEventLogger.maybeSetLatencyResponseProcessingMillis();
                this.mFillResponseEventLogger.logAndEndEvent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.service.autofill.FillResponse createShallowCopy(android.service.autofill.FillResponse fillResponse, com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer) {
        return android.service.autofill.FillResponse.shallowCopy(fillResponse, new java.util.ArrayList(datasetComputationContainer.mDatasets), getEligibleSaveInfo(fillResponse));
    }

    private android.service.autofill.SaveInfo getEligibleSaveInfo(android.service.autofill.FillResponse fillResponse) {
        android.service.autofill.SaveInfo saveInfo = fillResponse.getSaveInfo();
        if (saveInfo == null || !com.android.internal.util.ArrayUtils.isEmpty(saveInfo.getOptionalIds()) || !com.android.internal.util.ArrayUtils.isEmpty(saveInfo.getRequiredIds()) || (saveInfo.getFlags() & 4) != 0) {
            return saveInfo;
        }
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap arrayMap = this.mClassificationState.mHintsToAutofillIdMap;
                if (arrayMap == null || arrayMap.isEmpty()) {
                    return saveInfo;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                int type = saveInfo.getType();
                if (type == 0) {
                    java.util.Iterator it = arrayMap.values().iterator();
                    while (it.hasNext()) {
                        arraySet.addAll((java.util.Set) it.next());
                    }
                } else {
                    java.util.Set<java.lang.String> hintsForSaveType = com.android.server.autofill.HintsHelper.getHintsForSaveType(type);
                    for (java.util.Map.Entry entry : arrayMap.entrySet()) {
                        if (hintsForSaveType.contains((java.lang.String) entry.getKey())) {
                            arraySet.addAll((java.util.Collection) entry.getValue());
                        }
                    }
                }
                if (arraySet.isEmpty()) {
                    return saveInfo;
                }
                android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[arraySet.size()];
                this.mSaveEventLogger.maybeSetIsFrameworkCreatedSaveInfo(true);
                arraySet.toArray(autofillIdArr);
                return android.service.autofill.SaveInfo.copy(saveInfo, autofillIdArr);
            } finally {
            }
        }
    }

    private static class DatasetComputationContainer {
        java.util.Map<android.view.autofill.AutofillId, java.util.Set<android.service.autofill.Dataset>> mAutofillIdToDatasetMap;
        java.util.Set<android.view.autofill.AutofillId> mAutofillIds;
        java.util.Set<android.service.autofill.Dataset> mDatasets;

        private DatasetComputationContainer() {
            this.mAutofillIds = new java.util.LinkedHashSet();
            this.mDatasets = new java.util.LinkedHashSet();
            this.mAutofillIdToDatasetMap = new java.util.LinkedHashMap();
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("DatasetComputationContainer[");
            if (this.mAutofillIds != null) {
                sb.append(", autofillIds=");
                sb.append(this.mAutofillIds);
            }
            if (this.mDatasets != null) {
                sb.append(", mDatasets=");
                sb.append(this.mDatasets);
            }
            if (this.mAutofillIdToDatasetMap != null) {
                sb.append(", mAutofillIdToDatasetMap=");
                sb.append(this.mAutofillIdToDatasetMap);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    private void addFallbackDatasets(com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer, com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer2) {
        for (android.view.autofill.AutofillId autofillId : datasetComputationContainer2.mAutofillIds) {
            if (!datasetComputationContainer.mAutofillIds.contains(autofillId)) {
                if (datasetComputationContainer2.mAutofillIdToDatasetMap.get(autofillId).isEmpty()) {
                    return;
                }
                java.util.Set<android.service.autofill.Dataset> set = datasetComputationContainer2.mAutofillIdToDatasetMap.get(autofillId);
                java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet(set);
                datasetComputationContainer.mAutofillIds.add(autofillId);
                datasetComputationContainer.mAutofillIdToDatasetMap.put(autofillId, linkedHashSet);
                datasetComputationContainer.mDatasets.addAll(linkedHashSet);
                for (android.service.autofill.Dataset dataset : set) {
                    java.util.Iterator it = dataset.getFieldIds().iterator();
                    while (it.hasNext()) {
                        android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) it.next();
                        if (!autofillId2.equals(autofillId)) {
                            datasetComputationContainer2.mAutofillIdToDatasetMap.get(autofillId2).remove(dataset);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void computeDatasetsForProviderAndUpdateContainer(android.service.autofill.FillResponse fillResponse, com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer) {
        int i;
        int i2;
        java.util.Iterator it;
        if (this.mService.isPccClassificationEnabled()) {
            i = 2;
        } else {
            i = 1;
        }
        if (fillResponse.getDatasets() == null) {
            return;
        }
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        java.util.LinkedHashSet linkedHashSet2 = new java.util.LinkedHashSet();
        for (android.service.autofill.Dataset dataset : fillResponse.getDatasets()) {
            if (dataset.getFieldIds() != null && !dataset.getFieldIds().isEmpty()) {
                if (dataset.getAutofillDatatypes() != null && !dataset.getAutofillDatatypes().isEmpty()) {
                    int size = dataset.getFieldIds().size();
                    java.util.Iterator it2 = dataset.getFieldIds().iterator();
                    boolean z = false;
                    while (it2.hasNext()) {
                        if (((android.view.autofill.AutofillId) it2.next()) == null) {
                            size--;
                            z = true;
                        }
                    }
                    if (size != 0) {
                        if (z) {
                            java.util.ArrayList arrayList = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList3 = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList4 = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList5 = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList6 = new java.util.ArrayList(size);
                            java.util.ArrayList arrayList7 = new java.util.ArrayList(size);
                            for (int i3 = 0; i3 < dataset.getFieldIds().size(); i3++) {
                                android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(i3);
                                if (autofillId != null) {
                                    arrayList.add(autofillId);
                                    arrayList2.add((android.view.autofill.AutofillValue) dataset.getFieldValues().get(i3));
                                    arrayList3.add(dataset.getFieldPresentation(i3));
                                    arrayList4.add(dataset.getFieldDialogPresentation(i3));
                                    arrayList5.add(dataset.getFieldInlinePresentation(i3));
                                    arrayList6.add(dataset.getFieldInlineTooltipPresentation(i3));
                                    arrayList7.add(dataset.getFilter(i3));
                                }
                            }
                            android.service.autofill.Dataset dataset2 = new android.service.autofill.Dataset(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7, new java.util.ArrayList(), dataset.getFieldContent(), null, null, null, null, dataset.getId(), dataset.getAuthentication());
                            i2 = 3;
                            dataset = dataset2;
                            dataset.setEligibleReasonReason(i2);
                            linkedHashSet.add(dataset);
                            it = dataset.getFieldIds().iterator();
                            while (it.hasNext()) {
                                android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) it.next();
                                linkedHashSet2.add(autofillId2);
                                java.util.Set set = (java.util.Set) linkedHashMap.get(autofillId2);
                                if (set == null) {
                                    set = new java.util.LinkedHashSet();
                                }
                                set.add(dataset);
                                linkedHashMap.put(autofillId2, set);
                            }
                        }
                    }
                }
                i2 = i;
                dataset.setEligibleReasonReason(i2);
                linkedHashSet.add(dataset);
                it = dataset.getFieldIds().iterator();
                while (it.hasNext()) {
                }
            }
        }
        datasetComputationContainer.mAutofillIdToDatasetMap = linkedHashMap;
        datasetComputationContainer.mDatasets = linkedHashSet;
        datasetComputationContainer.mAutofillIds = linkedHashSet2;
    }

    private void computeDatasetsForPccAndUpdateContainer(android.service.autofill.FillResponse fillResponse, com.android.server.autofill.Session.DatasetComputationContainer datasetComputationContainer) {
        int i;
        java.util.LinkedHashSet linkedHashSet;
        java.util.LinkedHashSet linkedHashSet2;
        java.util.LinkedHashMap linkedHashMap;
        java.util.List list;
        android.util.ArrayMap arrayMap;
        java.util.Set linkedHashSet3;
        java.util.LinkedHashSet linkedHashSet4;
        java.util.LinkedHashSet linkedHashSet5;
        int i2;
        java.util.LinkedHashSet linkedHashSet6;
        java.util.LinkedHashSet linkedHashSet7;
        java.util.LinkedHashMap linkedHashMap2;
        java.util.List list2;
        java.util.LinkedHashSet linkedHashSet8;
        int i3;
        android.util.ArrayMap arrayMap2;
        int i4;
        int i5;
        int i6;
        com.android.server.autofill.Session session = this;
        java.util.List datasets = fillResponse.getDatasets();
        if (datasets == null) {
            return;
        }
        synchronized (session.mLock) {
            try {
                android.util.ArrayMap arrayMap3 = session.mClassificationState.mHintsToAutofillIdMap;
                android.util.ArrayMap unused = session.mClassificationState.mGroupHintsToAutofillIdMap;
                java.util.LinkedHashMap linkedHashMap3 = new java.util.LinkedHashMap();
                java.util.LinkedHashSet linkedHashSet9 = new java.util.LinkedHashSet();
                java.util.LinkedHashSet linkedHashSet10 = new java.util.LinkedHashSet();
                int i7 = 0;
                while (i7 < datasets.size()) {
                    android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) datasets.get(i7);
                    if (dataset.getAutofillDatatypes() == null) {
                        i = i7;
                        linkedHashSet = linkedHashSet10;
                        linkedHashSet2 = linkedHashSet9;
                        linkedHashMap = linkedHashMap3;
                        list = datasets;
                        arrayMap = arrayMap3;
                    } else if (dataset.getAutofillDatatypes().isEmpty()) {
                        i = i7;
                        linkedHashSet = linkedHashSet10;
                        linkedHashSet2 = linkedHashSet9;
                        linkedHashMap = linkedHashMap3;
                        list = datasets;
                        arrayMap = arrayMap3;
                    } else {
                        java.util.ArrayList<android.view.autofill.AutofillId> arrayList = new java.util.ArrayList<>();
                        java.util.ArrayList<android.view.autofill.AutofillValue> arrayList2 = new java.util.ArrayList<>();
                        java.util.ArrayList<android.widget.RemoteViews> arrayList3 = new java.util.ArrayList<>();
                        java.util.ArrayList<android.widget.RemoteViews> arrayList4 = new java.util.ArrayList<>();
                        java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList5 = new java.util.ArrayList<>();
                        java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList6 = new java.util.ArrayList<>();
                        java.util.ArrayList<android.service.autofill.Dataset.DatasetFieldFilter> arrayList7 = new java.util.ArrayList<>();
                        java.util.LinkedHashSet linkedHashSet11 = new java.util.LinkedHashSet();
                        java.util.LinkedHashSet linkedHashSet12 = new java.util.LinkedHashSet();
                        java.util.LinkedHashSet linkedHashSet13 = new java.util.LinkedHashSet();
                        int i8 = 4;
                        int i9 = 0;
                        boolean z = false;
                        while (i9 < dataset.getAutofillDatatypes().size()) {
                            if (dataset.getAutofillDatatypes().get(i9) == null) {
                                if (dataset.getFieldIds() != null && dataset.getFieldIds().get(i9) != null) {
                                    i5 = 5;
                                } else {
                                    i5 = i8;
                                }
                                android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(i9);
                                if (session.mClassificationState.mClassificationCombinedHintsMap.containsKey(autofillId)) {
                                    i6 = i9;
                                    linkedHashSet4 = linkedHashSet13;
                                    linkedHashSet5 = linkedHashSet11;
                                    i2 = i7;
                                    linkedHashSet6 = linkedHashSet10;
                                    linkedHashSet7 = linkedHashSet9;
                                    linkedHashMap2 = linkedHashMap3;
                                    list2 = datasets;
                                    linkedHashSet8 = linkedHashSet12;
                                } else {
                                    linkedHashSet13.add(autofillId);
                                    linkedHashSet12.add(autofillId);
                                    i6 = i9;
                                    list2 = datasets;
                                    linkedHashSet8 = linkedHashSet12;
                                    linkedHashSet5 = linkedHashSet11;
                                    i2 = i7;
                                    linkedHashSet4 = linkedHashSet13;
                                    linkedHashSet6 = linkedHashSet10;
                                    linkedHashSet7 = linkedHashSet9;
                                    linkedHashMap2 = linkedHashMap3;
                                    copyFieldsFromDataset(dataset, i9, autofillId, arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7);
                                }
                                i8 = i5;
                                i3 = i6;
                                arrayMap2 = arrayMap3;
                            } else {
                                linkedHashSet4 = linkedHashSet13;
                                linkedHashSet5 = linkedHashSet11;
                                i2 = i7;
                                linkedHashSet6 = linkedHashSet10;
                                linkedHashSet7 = linkedHashSet9;
                                linkedHashMap2 = linkedHashMap3;
                                list2 = datasets;
                                linkedHashSet8 = linkedHashSet12;
                                int i10 = i9;
                                java.lang.String str = (java.lang.String) dataset.getAutofillDatatypes().get(i10);
                                if (!arrayMap3.containsKey(str)) {
                                    i3 = i10;
                                    arrayMap2 = arrayMap3;
                                    i4 = i8;
                                } else {
                                    java.util.ArrayList arrayList8 = new java.util.ArrayList((java.util.Collection) arrayMap3.get(str));
                                    if (arrayList8.isEmpty()) {
                                        i3 = i10;
                                        arrayMap2 = arrayMap3;
                                        i4 = i8;
                                    } else {
                                        java.util.Iterator it = arrayList8.iterator();
                                        while (it.hasNext()) {
                                            android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) it.next();
                                            linkedHashSet6.add(autofillId2);
                                            linkedHashSet5.add(autofillId2);
                                            copyFieldsFromDataset(dataset, i10, autofillId2, arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7);
                                            i8 = i8;
                                            i10 = i10;
                                            arrayMap3 = arrayMap3;
                                        }
                                        i3 = i10;
                                        arrayMap2 = arrayMap3;
                                        z = true;
                                    }
                                }
                                i8 = i4;
                            }
                            i9 = i3 + 1;
                            arrayMap3 = arrayMap2;
                            linkedHashSet10 = linkedHashSet6;
                            linkedHashSet11 = linkedHashSet5;
                            linkedHashSet12 = linkedHashSet8;
                            linkedHashSet13 = linkedHashSet4;
                            datasets = list2;
                            i7 = i2;
                            linkedHashSet9 = linkedHashSet7;
                            linkedHashMap3 = linkedHashMap2;
                            session = this;
                        }
                        java.util.LinkedHashSet linkedHashSet14 = linkedHashSet13;
                        java.util.LinkedHashSet<android.view.autofill.AutofillId> linkedHashSet15 = linkedHashSet11;
                        i = i7;
                        linkedHashSet = linkedHashSet10;
                        java.util.LinkedHashSet linkedHashSet16 = linkedHashSet9;
                        java.util.LinkedHashMap linkedHashMap4 = linkedHashMap3;
                        list = datasets;
                        arrayMap = arrayMap3;
                        int i11 = i8;
                        java.util.LinkedHashSet linkedHashSet17 = linkedHashSet12;
                        if (!z) {
                            linkedHashSet2 = linkedHashSet16;
                            linkedHashMap = linkedHashMap4;
                        } else {
                            linkedHashSet15.addAll(linkedHashSet17);
                            linkedHashSet.addAll(linkedHashSet14);
                            android.service.autofill.Dataset dataset2 = new android.service.autofill.Dataset(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7, new java.util.ArrayList(), dataset.getFieldContent(), null, null, null, null, dataset.getId(), dataset.getAuthentication());
                            dataset2.setEligibleReasonReason(i11);
                            linkedHashSet2 = linkedHashSet16;
                            linkedHashSet2.add(dataset2);
                            for (android.view.autofill.AutofillId autofillId3 : linkedHashSet15) {
                                java.util.LinkedHashMap linkedHashMap5 = linkedHashMap4;
                                if (linkedHashMap5.containsKey(autofillId3)) {
                                    linkedHashSet3 = (java.util.Set) linkedHashMap5.get(autofillId3);
                                } else {
                                    linkedHashSet3 = new java.util.LinkedHashSet();
                                }
                                linkedHashSet3.add(dataset2);
                                linkedHashMap5.put(autofillId3, linkedHashSet3);
                                linkedHashMap4 = linkedHashMap5;
                            }
                            linkedHashMap = linkedHashMap4;
                        }
                    }
                    i7 = i + 1;
                    arrayMap3 = arrayMap;
                    linkedHashSet10 = linkedHashSet;
                    linkedHashSet9 = linkedHashSet2;
                    linkedHashMap3 = linkedHashMap;
                    datasets = list;
                    session = this;
                }
                datasetComputationContainer.mAutofillIds = linkedHashSet10;
                datasetComputationContainer.mDatasets = linkedHashSet9;
                datasetComputationContainer.mAutofillIdToDatasetMap = linkedHashMap3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void copyFieldsFromDataset(android.service.autofill.Dataset dataset, int i, android.view.autofill.AutofillId autofillId, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<android.view.autofill.AutofillValue> arrayList2, java.util.ArrayList<android.widget.RemoteViews> arrayList3, java.util.ArrayList<android.widget.RemoteViews> arrayList4, java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList5, java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList6, java.util.ArrayList<android.service.autofill.Dataset.DatasetFieldFilter> arrayList7) {
        arrayList.add(autofillId);
        arrayList2.add((android.view.autofill.AutofillValue) dataset.getFieldValues().get(i));
        arrayList3.add(dataset.getFieldPresentation(i));
        arrayList4.add(dataset.getFieldDialogPresentation(i));
        arrayList5.add(dataset.getFieldInlinePresentation(i));
        arrayList6.add(dataset.getFieldInlineTooltipPresentation(i));
        arrayList7.add(dataset.getFilter(i));
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestFailure(int i, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        onFillRequestFailureOrTimeout(i, false, charSequence);
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestTimeout(int i) {
        onFillRequestFailureOrTimeout(i, true, null);
    }

    private void onFillRequestFailureOrTimeout(int i, boolean z, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        boolean z2 = !android.text.TextUtils.isEmpty(charSequence);
        this.mFillResponseEventLogger.startLogForNewResponse();
        this.mFillResponseEventLogger.maybeSetRequestId(i);
        this.mFillResponseEventLogger.maybeSetAppPackageUid(this.uid);
        this.mFillResponseEventLogger.maybeSetAvailableCount(-1);
        this.mFillResponseEventLogger.maybeSetTotalDatasetsProvided(-1);
        this.mFillResponseEventLogger.maybeSetDetectionPreference(getDetectionPreferenceForLogging());
        this.mFillResponseEventLogger.maybeSetLatencyFillResponseReceivedMillis((int) (android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime));
        synchronized (this.mLock) {
            try {
                unregisterDelayedFillBroadcastLocked();
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onFillRequestFailureOrTimeout(req=" + i + ") rejected - session: " + this.id + " destroyed");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                if (com.android.server.autofill.Helper.sDebug) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("finishing session due to service ");
                    sb.append(z ? "timeout" : "failure");
                    android.util.Slog.d(TAG, sb.toString());
                }
                this.mService.resetLastResponse();
                this.mLastFillDialogTriggerIds = null;
                android.metrics.LogMaker logMaker = this.mRequestLogs.get(i);
                if (logMaker == null) {
                    android.util.Slog.w(TAG, "onFillRequestFailureOrTimeout(): no log for id " + i);
                } else {
                    logMaker.setType(z ? 2 : 11);
                }
                if (z2) {
                    int targedSdkLocked = this.mService.getTargedSdkLocked();
                    if (targedSdkLocked >= 29) {
                        android.util.Slog.w(TAG, "onFillRequestFailureOrTimeout(): not showing '" + ((java.lang.Object) charSequence) + "' because service's targetting API " + targedSdkLocked);
                        z2 = false;
                    }
                    if (charSequence != null) {
                        logMaker.addTaggedData(1572, java.lang.Integer.valueOf(charSequence.length()));
                    }
                }
                if (z) {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(5);
                    this.mFillResponseEventLogger.maybeSetResponseStatus(4);
                } else {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(7);
                    this.mFillResponseEventLogger.maybeSetResponseStatus(1);
                }
                this.mPresentationStatsEventLogger.logAndEndEvent();
                this.mFillResponseEventLogger.logAndEndEvent();
                notifyUnavailableToClient(6, null);
                if (z2) {
                    getUiForShowing().showError(charSequence, this);
                }
                removeFromService();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onSaveRequestSuccess(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.IntentSender intentSender) {
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                this.mSaveEventLogger.maybeSetIsSaved(true);
                this.mSaveEventLogger.maybeSetLatencySaveFinishMillis(android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
                this.mSaveEventLogger.logAndEndEvent();
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onSaveRequestSuccess() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mMetricsLogger.write(newLogMaker(918, str).setType(intentSender == null ? 10 : 1));
                if (intentSender != null) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Starting intent sender on save()");
                    }
                    startIntentSenderAndFinishSession(intentSender);
                }
                removeFromService();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onSaveRequestFailure(@android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.NonNull java.lang.String str) {
        int targedSdkLocked;
        boolean z = !android.text.TextUtils.isEmpty(charSequence);
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                this.mSaveEventLogger.maybeSetLatencySaveFinishMillis(android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
                this.mSaveEventLogger.logAndEndEvent();
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onSaveRequestFailure() rejected - session: " + this.id + " destroyed");
                    return;
                }
                if (z && (targedSdkLocked = this.mService.getTargedSdkLocked()) >= 29) {
                    android.util.Slog.w(TAG, "onSaveRequestFailure(): not showing '" + ((java.lang.Object) charSequence) + "' because service's targetting API " + targedSdkLocked);
                    z = false;
                }
                android.metrics.LogMaker type = newLogMaker(918, str).setType(11);
                if (charSequence != null) {
                    type.addTaggedData(1572, java.lang.Integer.valueOf(charSequence.length()));
                }
                this.mMetricsLogger.write(type);
                if (z) {
                    getUiForShowing().showError(charSequence, this);
                }
                removeFromService();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onConvertCredentialRequestSuccess(@android.annotation.NonNull android.service.autofill.ConvertCredentialResponse convertCredentialResponse) {
        int i;
        android.service.autofill.Dataset dataset = convertCredentialResponse.getDataset();
        android.os.Bundle clientState = convertCredentialResponse.getClientState();
        if (dataset == null) {
            android.util.Slog.e(TAG, "onConvertCredentialRequestSuccess(): dataset inside response is null");
            return;
        }
        if (clientState == null) {
            android.util.Slog.e(TAG, "onConvertCredentialRequestSuccess(): client state is null, this would cause loss in logging.");
            i = -1;
        } else {
            i = clientState.getInt("android.view.autofill.extra.AUTOFILL_REQUEST_ID");
        }
        fill(i, -1, dataset, 4);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.service.autofill.FillContext getFillContextByRequestIdLocked(int i) {
        if (this.mContexts == null) {
            return null;
        }
        int size = this.mContexts.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.FillContext fillContext = this.mContexts.get(i2);
            if (fillContext.getRequestId() == i) {
                return fillContext;
            }
        }
        return null;
    }

    public void onServiceDied(@android.annotation.NonNull com.android.server.autofill.RemoteFillService remoteFillService) {
        android.util.Slog.w(TAG, "removing session because service died");
        synchronized (this.mLock) {
            forceRemoveFromServiceLocked();
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void authenticate(int i, int i2, android.content.IntentSender intentSender, android.os.Bundle bundle, int i3) {
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "authenticate(): requestId=" + i + "; datasetIdx=" + i2 + "; intentSender=" + intentSender);
        }
        synchronized (this.mLock) {
            try {
                this.mPresentationStatsEventLogger.maybeSetAuthenticationType(2);
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#authenticate() rejected - session: " + this.id + " destroyed");
                    return;
                }
                android.content.Intent createAuthFillInIntentLocked = createAuthFillInIntentLocked(i, bundle);
                if (createAuthFillInIntentLocked == null) {
                    forceRemoveFromServiceLocked();
                    return;
                }
                this.mService.setAuthenticationSelected(this.id, this.mClientState, i3);
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda10
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                        ((com.android.server.autofill.Session) obj).startAuthentication(((java.lang.Integer) obj2).intValue(), (android.content.IntentSender) obj3, (android.content.Intent) obj4, ((java.lang.Boolean) obj5).booleanValue());
                    }
                }, this, java.lang.Integer.valueOf(android.view.autofill.AutofillManager.makeAuthenticationId(i, i2)), intentSender, createAuthFillInIntentLocked, java.lang.Boolean.valueOf(i3 == 2)));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void fill(int i, int i2, android.service.autofill.Dataset dataset, int i3) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#fill() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda6
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                        ((com.android.server.autofill.Session) obj).autoFill(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (android.service.autofill.Dataset) obj4, ((java.lang.Boolean) obj5).booleanValue(), ((java.lang.Integer) obj6).intValue());
                    }
                }, this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), dataset, true, java.lang.Integer.valueOf(i3)));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void save() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#save() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mSaveEventLogger.maybeSetLatencySaveRequestMillis(android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.autofill.AutofillManagerServiceImpl) obj).handleSessionSave((com.android.server.autofill.Session) obj2);
                    }
                }, this.mService, this));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void cancelSave() {
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#cancelSave() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.autofill.Session) obj).removeFromService();
                    }
                }, this));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void onShown(int i) {
        synchronized (this.mLock) {
            if (i == 2) {
                try {
                    if (this.mLoggedInlineDatasetShown) {
                        return;
                    } else {
                        this.mLoggedInlineDatasetShown = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mService.logDatasetShown(this.id, this.mClientState, i);
            android.util.Slog.d(TAG, "onShown(): " + i);
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void requestShowFillUi(android.view.autofill.AutofillId autofillId, int i, int i2, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#requestShowFillUi() rejected - session: " + autofillId + " destroyed");
                    return;
                }
                if (autofillId.equals(this.mCurrentViewId)) {
                    try {
                        this.mClient.requestShowFillUi(this.id, autofillId, i, i2, this.mViewStates.get(autofillId).getVirtualBounds(), iAutofillWindowPresenter);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Error requesting to show fill UI", e);
                    }
                } else if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "Do not show full UI on " + autofillId + " as it is not the current view (" + this.mCurrentViewId + ") anymore");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void dispatchUnhandledKey(android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#dispatchUnhandledKey() rejected - session: " + autofillId + " destroyed");
                    return;
                }
                if (autofillId.equals(this.mCurrentViewId)) {
                    try {
                        this.mClient.dispatchUnhandledKey(this.id, autofillId, keyEvent);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Error requesting to dispatch unhandled key", e);
                    }
                } else {
                    android.util.Slog.w(TAG, "Do not dispatch unhandled key on " + autofillId + " as it is not the current view (" + this.mCurrentViewId + ") anymore");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void requestHideFillUi(android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            try {
                this.mClient.requestHideFillUi(this.id, autofillId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error requesting to hide fill UI", e);
            }
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(autofillId);
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void requestHideFillUiWhenDestroyed(android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            try {
                this.mClient.requestHideFillUiWhenDestroyed(this.id, autofillId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error requesting to hide fill UI", e);
            }
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(autofillId);
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void cancelSession() {
        synchronized (this.mLock) {
            removeFromServiceLocked();
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void startIntentSenderAndFinishSession(android.content.IntentSender intentSender) {
        startIntentSender(intentSender, null);
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#startIntentSender() rejected - session: " + this.id + " destroyed");
                    return;
                }
                if (intent == null) {
                    removeFromServiceLocked();
                }
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda5
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.autofill.Session) obj).doStartIntentSender((android.content.IntentSender) obj2, (android.content.Intent) obj3);
                    }
                }, this, intentSender, intent));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void requestShowSoftInput(android.view.autofill.AutofillId autofillId) {
        android.view.autofill.IAutoFillManagerClient client = getClient();
        if (client != null) {
            try {
                client.requestShowSoftInput(autofillId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error sending input show up notification", e);
            }
        }
    }

    @Override // com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback
    public void requestFallbackFromFillDialog() {
        setFillDialogDisabled();
        synchronized (this.mLock) {
            try {
                if (this.mCurrentViewId == null) {
                    return;
                }
                this.mViewStates.get(this.mCurrentViewId).maybeCallOnFillReady(this.mFlags);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFillUiHidden(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            try {
                this.mClient.notifyFillUiHidden(this.id, autofillId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error sending fill UI hidden notification", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFillUiShown(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            try {
                this.mClient.notifyFillUiShown(this.id, autofillId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error sending fill UI shown notification", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doStartIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) {
        try {
            synchronized (this.mLock) {
                this.mClient.startIntentSender(intentSender, intent);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error launching auth intent", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setAuthenticationResultLocked(android.os.Bundle bundle, int i) {
        android.service.autofill.Dataset dataset;
        android.service.autofill.Dataset datasetFromCredentialResponse;
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#setAuthenticationResultLocked() rejected - session: " + this.id + " destroyed");
            return;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "setAuthenticationResultLocked(): id= " + i + ", data=" + bundle);
        }
        int requestIdFromAuthenticationId = android.view.autofill.AutofillManager.getRequestIdFromAuthenticationId(i);
        if (requestIdFromAuthenticationId == 1) {
            setAuthenticationResultForAugmentedAutofillLocked(bundle, i);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            return;
        }
        if (this.mResponses == null) {
            android.util.Slog.w(TAG, "setAuthenticationResultLocked(" + i + "): no responses");
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            removeFromService();
            return;
        }
        android.service.autofill.FillResponse fillResponse = this.mResponses.get(requestIdFromAuthenticationId);
        if (fillResponse == null || bundle == null) {
            android.util.Slog.w(TAG, "no authenticated response");
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            removeFromService();
            return;
        }
        int datasetIdFromAuthenticationId = android.view.autofill.AutofillManager.getDatasetIdFromAuthenticationId(i);
        if (datasetIdFromAuthenticationId == 65535) {
            dataset = null;
        } else {
            dataset = (android.service.autofill.Dataset) fillResponse.getDatasets().get(datasetIdFromAuthenticationId);
            if (dataset == null) {
                android.util.Slog.w(TAG, "no dataset with index " + datasetIdFromAuthenticationId + " on fill response");
                this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
                this.mPresentationStatsEventLogger.logAndEndEvent();
                removeFromService();
                return;
            }
        }
        this.mSessionFlags.mExpiredResponse = false;
        android.os.Parcelable parcelable = bundle.getParcelable("android.view.autofill.extra.AUTHENTICATION_RESULT");
        android.credentials.GetCredentialException getCredentialException = (android.credentials.GetCredentialException) bundle.getSerializable("android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION", android.credentials.GetCredentialException.class);
        android.os.Bundle bundle2 = bundle.getBundle("android.view.autofill.extra.CLIENT_STATE");
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "setAuthenticationResultLocked(): result=" + parcelable + ", clientState=" + bundle2 + ", authenticationId=" + i);
        }
        if (android.service.autofill.Flags.autofillCredmanDevIntegration() && getCredentialException != null) {
            if (dataset != null && dataset.getFieldIds().size() == 1) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "setAuthenticationResultLocked(): result returns withCredential Manager Exception");
                }
                sendCredentialManagerResponseToApp(null, getCredentialException, (android.view.autofill.AutofillId) dataset.getFieldIds().get(0));
                return;
            }
            return;
        }
        if (parcelable instanceof android.service.autofill.FillResponse) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "setAuthenticationResultLocked(): received FillResponse from authentication flow");
            }
            logAuthenticationStatusLocked(requestIdFromAuthenticationId, com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_720P_HD_ALMOST);
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(1);
            replaceResponseLocked(fillResponse, (android.service.autofill.FillResponse) parcelable, bundle2);
            return;
        }
        if (parcelable instanceof android.credentials.GetCredentialResponse) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Received GetCredentialResponse from authentication flow");
            }
            if (android.service.autofill.Flags.autofillCredmanDevIntegration()) {
                android.credentials.GetCredentialResponse getCredentialResponse = (android.credentials.GetCredentialResponse) parcelable;
                if (dataset != null && dataset.getFieldIds().size() == 1) {
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(0);
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Received GetCredentialResponse from authentication flow,for autofillId: " + autofillId);
                    }
                    sendCredentialManagerResponseToApp(getCredentialResponse, null, autofillId);
                    return;
                }
                return;
            }
            if (android.service.autofill.Flags.autofillCredmanIntegration() && (datasetFromCredentialResponse = getDatasetFromCredentialResponse((android.credentials.GetCredentialResponse) parcelable)) != null) {
                autoFill(requestIdFromAuthenticationId, datasetIdFromAuthenticationId, datasetFromCredentialResponse, false, 0);
                return;
            }
            return;
        }
        if (parcelable instanceof android.service.autofill.Dataset) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "setAuthenticationResultLocked(): received Dataset from authentication flow");
            }
            if (datasetIdFromAuthenticationId != 65535) {
                logAuthenticationStatusLocked(requestIdFromAuthenticationId, 1126);
                this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(1);
                if (bundle2 != null) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Updating client state from auth dataset");
                    }
                    this.mClientState = bundle2;
                }
                android.service.autofill.Dataset effectiveDatasetForAuthentication = getEffectiveDatasetForAuthentication((android.service.autofill.Dataset) parcelable);
                if (!isAuthResultDatasetEphemeral((android.service.autofill.Dataset) fillResponse.getDatasets().get(datasetIdFromAuthenticationId), bundle)) {
                    fillResponse.getDatasets().set(datasetIdFromAuthenticationId, effectiveDatasetForAuthentication);
                }
                autoFill(requestIdFromAuthenticationId, datasetIdFromAuthenticationId, effectiveDatasetForAuthentication, false, 0);
                return;
            }
            android.util.Slog.w(TAG, "invalid index (" + datasetIdFromAuthenticationId + ") for authentication id " + i);
            logAuthenticationStatusLocked(requestIdFromAuthenticationId, 1127);
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            return;
        }
        if (parcelable != null) {
            android.util.Slog.w(TAG, "service returned invalid auth type: " + parcelable);
        }
        logAuthenticationStatusLocked(requestIdFromAuthenticationId, 1128);
        this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
        processNullResponseLocked(requestIdFromAuthenticationId, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.service.autofill.Dataset getDatasetFromCredentialResponse(android.credentials.GetCredentialResponse getCredentialResponse) {
        android.os.Bundle data;
        if (getCredentialResponse == null || (data = getCredentialResponse.getCredential().getData()) == null) {
            return null;
        }
        return (android.service.autofill.Dataset) data.getParcelable("android.view.autofill.extra.AUTHENTICATION_RESULT", android.service.autofill.Dataset.class);
    }

    android.service.autofill.Dataset getEffectiveDatasetForAuthentication(android.service.autofill.Dataset dataset) {
        android.service.autofill.FillResponse effectiveFillResponse = getEffectiveFillResponse(new android.service.autofill.FillResponse.Builder().addDataset(dataset).build());
        if (effectiveFillResponse == null || effectiveFillResponse.getDatasets().size() == 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("No datasets in fill response on authentication. response = ");
            sb.append(effectiveFillResponse == null ? "null" : effectiveFillResponse.toString());
            android.util.Log.wtf(TAG, sb.toString());
            return dataset;
        }
        java.util.List<android.service.autofill.Dataset> datasets = effectiveFillResponse.getDatasets();
        android.service.autofill.Dataset dataset2 = (android.service.autofill.Dataset) effectiveFillResponse.getDatasets().get(0);
        if (datasets.size() > 1) {
            android.service.autofill.Dataset.Builder builder = new android.service.autofill.Dataset.Builder();
            for (android.service.autofill.Dataset dataset3 : datasets) {
                if (!dataset3.getFieldIds().isEmpty()) {
                    for (int i = 0; i < dataset3.getFieldIds().size(); i++) {
                        builder.setField((android.view.autofill.AutofillId) dataset3.getFieldIds().get(i), new android.service.autofill.Field.Builder().setValue((android.view.autofill.AutofillValue) dataset3.getFieldValues().get(i)).build());
                    }
                }
            }
            return builder.setId(dataset.getId()).build();
        }
        return dataset2;
    }

    private static boolean isAuthResultDatasetEphemeral(@android.annotation.Nullable android.service.autofill.Dataset dataset, @android.annotation.NonNull android.os.Bundle bundle) {
        if (bundle.containsKey("android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET")) {
            return bundle.getBoolean("android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET");
        }
        return isPinnedDataset(dataset);
    }

    private static boolean isPinnedDataset(@android.annotation.Nullable android.service.autofill.Dataset dataset) {
        if (dataset != null && dataset.getFieldIds() != null) {
            int size = dataset.getFieldIds().size();
            for (int i = 0; i < size; i++) {
                android.service.autofill.InlinePresentation fieldInlinePresentation = dataset.getFieldInlinePresentation(i);
                if (fieldInlinePresentation != null && fieldInlinePresentation.isPinned()) {
                    return true;
                }
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setAuthenticationResultForAugmentedAutofillLocked(android.os.Bundle bundle, int i) {
        android.service.autofill.Dataset dataset;
        android.view.autofill.AutofillId autofillId;
        android.view.autofill.AutofillValue autofillValue;
        if (bundle == null) {
            dataset = null;
        } else {
            dataset = (android.service.autofill.Dataset) bundle.getParcelable("android.view.autofill.extra.AUTHENTICATION_RESULT", android.service.autofill.Dataset.class);
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "Auth result for augmented autofill: sessionId=" + this.id + ", authId=" + i + ", dataset=" + dataset);
        }
        if (dataset == null || dataset.getFieldIds().size() != 1) {
            autofillId = null;
        } else {
            autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(0);
        }
        if (dataset == null || dataset.getFieldValues().size() != 1) {
            autofillValue = null;
        } else {
            autofillValue = (android.view.autofill.AutofillValue) dataset.getFieldValues().get(0);
        }
        android.content.ClipData fieldContent = dataset != null ? dataset.getFieldContent() : null;
        if (autofillId == null || (autofillValue == null && fieldContent == null)) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Rejecting empty/invalid auth result");
            }
            this.mService.resetLastAugmentedAutofillResponse();
            removeFromServiceLocked();
            return;
        }
        com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillServiceIfCreatedLocked = this.mService.getRemoteAugmentedAutofillServiceIfCreatedLocked();
        if (remoteAugmentedAutofillServiceIfCreatedLocked == null) {
            android.util.Slog.e(TAG, "Can't fill after auth: RemoteAugmentedAutofillService is null");
            this.mService.resetLastAugmentedAutofillResponse();
            removeFromServiceLocked();
            return;
        }
        autofillId.setSessionId(this.id);
        this.mCurrentViewId = autofillId;
        this.mService.logAugmentedAutofillSelected(this.id, dataset.getId(), bundle.getBundle("android.view.autofill.extra.CLIENT_STATE"));
        if (fieldContent != null) {
            remoteAugmentedAutofillServiceIfCreatedLocked.getAutofillUriGrantsManager().grantUriPermissions(this.mComponentName, this.mActivityToken, this.userId, fieldContent);
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "Filling after auth: fieldId=" + autofillId + ", value=" + autofillValue + ", content=" + fieldContent);
        }
        try {
            if (fieldContent != null) {
                this.mClient.autofillContent(this.id, autofillId, fieldContent);
            } else {
                this.mClient.autofill(this.id, dataset.getFieldIds(), dataset.getFieldValues(), true);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error filling after auth: fieldId=" + autofillId + ", value=" + autofillValue + ", content=" + fieldContent, e);
        }
        this.mInlineSessionController.setInlineFillUiLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setHasCallbackLocked(boolean z) {
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#setHasCallbackLocked() rejected - session: " + this.id + " destroyed");
            return;
        }
        this.mHasCallback = z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.service.autofill.FillResponse getLastResponseLocked(@android.annotation.Nullable java.lang.String str) {
        java.lang.String str2;
        if (com.android.server.autofill.Helper.sDebug && str != null) {
            str2 = java.lang.String.format(str, java.lang.Integer.valueOf(this.id));
        } else {
            str2 = null;
        }
        if (this.mContexts == null) {
            if (str2 != null) {
                android.util.Slog.d(TAG, str2 + ": no contexts");
            }
            return null;
        }
        if (this.mResponses == null) {
            if (com.android.server.autofill.Helper.sVerbose && str2 != null) {
                android.util.Slog.v(TAG, str2 + ": no responses on session");
            }
            return null;
        }
        int lastResponseIndexLocked = getLastResponseIndexLocked();
        if (lastResponseIndexLocked < 0) {
            if (str2 != null) {
                android.util.Slog.w(TAG, str2 + ": did not get last response. mResponses=" + this.mResponses + ", mViewStates=" + this.mViewStates);
            }
            return null;
        }
        android.service.autofill.FillResponse valueAt = this.mResponses.valueAt(lastResponseIndexLocked);
        if (com.android.server.autofill.Helper.sVerbose && str2 != null) {
            android.util.Slog.v(TAG, str2 + ": mResponses=" + this.mResponses + ", mContexts=" + this.mContexts + ", mViewStates=" + this.mViewStates);
        }
        return valueAt;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.service.autofill.SaveInfo getSaveInfoLocked() {
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return null;
        }
        return lastResponseLocked.getSaveInfo();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int getSaveInfoFlagsLocked() {
        android.service.autofill.SaveInfo saveInfoLocked = getSaveInfoLocked();
        if (saveInfoLocked == null) {
            return 0;
        }
        return saveInfoLocked.getFlags();
    }

    public void logContextCommitted() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "logContextCommitted (" + this.id + "): commit_reason:0 no_save_reason:0");
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.Session$$ExternalSyntheticLambda4(), this, 0, 0));
        logAllEvents(0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void logContextCommittedLocked(int i, int i2) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "logContextCommittedLocked (" + this.id + "): commit_reason:" + i2 + " no_save_reason:" + i);
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.Session$$ExternalSyntheticLambda4(), this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        this.mSessionCommittedEventLogger.maybeSetCommitReason(i2);
        this.mSessionCommittedEventLogger.maybeSetRequestCount(this.mRequestCount);
        this.mSaveEventLogger.maybeSetSaveUiNotShownReason(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogContextCommitted(int i, int i2) {
        android.service.autofill.FillResponse lastResponseLocked;
        synchronized (this.mLock) {
            lastResponseLocked = getLastResponseLocked("logContextCommited(%s)");
        }
        if (lastResponseLocked == null) {
            android.util.Slog.w(TAG, "handleLogContextCommitted(): last response is null");
            return;
        }
        android.service.autofill.UserData userData = this.mService.getUserData();
        android.service.autofill.FieldClassificationUserData userData2 = lastResponseLocked.getUserData();
        if (userData2 == null && userData == null) {
            userData2 = null;
        } else if (userData2 != null && userData != null) {
            userData2 = new android.service.autofill.CompositeUserData(userData, userData2);
        } else if (userData2 == null) {
            userData2 = this.mService.getUserData();
        }
        com.android.server.autofill.FieldClassificationStrategy fieldClassificationStrategy = this.mService.getFieldClassificationStrategy();
        if (userData2 != null && fieldClassificationStrategy != null) {
            logFieldClassificationScore(fieldClassificationStrategy, userData2, i, i2);
        } else {
            logContextCommitted(null, null, i, i2);
        }
    }

    private void logContextCommitted(@android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList, @android.annotation.Nullable java.util.ArrayList<android.service.autofill.FieldClassification> arrayList2, int i, int i2) {
        synchronized (this.mLock) {
            logContextCommittedLocked(arrayList, arrayList2, i, i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logContextCommittedLocked(@android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList, @android.annotation.Nullable java.util.ArrayList<android.service.autofill.FieldClassification> arrayList2, int i, int i2) {
        java.lang.String str;
        java.util.ArrayList<android.view.autofill.AutofillId> arrayList3;
        java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList4;
        int i3;
        boolean z;
        java.lang.String str2;
        android.view.autofill.AutofillValue autofillValue;
        java.lang.String str3;
        java.util.List list;
        android.view.autofill.AutofillValue autofillValue2;
        java.util.ArrayList arrayList5;
        android.view.autofill.AutofillValue autofillValue3;
        android.util.ArrayMap arrayMap;
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "logContextCommittedLocked (" + this.id + "): commit_reason:" + i2 + " no_save_reason:" + i);
        }
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked("logContextCommited(%s)");
        if (lastResponseLocked == null) {
            return;
        }
        this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(com.android.server.autofill.PresentationStatsEventLogger.getNoPresentationEventReason(i2));
        this.mPresentationStatsEventLogger.logAndEndEvent();
        int flags = lastResponseLocked.getFlags();
        if ((flags & 1) == 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "logContextCommittedLocked(): ignored by flags " + flags);
                return;
            }
            return;
        }
        int size = this.mResponses.size();
        int i4 = 0;
        android.util.ArraySet<java.lang.String> arraySet = null;
        boolean z2 = false;
        while (true) {
            str = "logContextCommitted() skipping idless dataset ";
            if (i4 >= size) {
                break;
            }
            java.util.List datasets = this.mResponses.valueAt(i4).getDatasets();
            if (datasets == null || datasets.isEmpty()) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "logContextCommitted() no datasets at " + i4);
                }
            } else {
                for (int i5 = 0; i5 < datasets.size(); i5++) {
                    android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) datasets.get(i5);
                    java.lang.String id = dataset.getId();
                    if (id == null) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "logContextCommitted() skipping idless dataset " + dataset);
                        }
                    } else if (this.mSelectedDatasetIds == null || !this.mSelectedDatasetIds.contains(id)) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "adding ignored dataset " + id);
                        }
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet<>();
                        }
                        arraySet.add(id);
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
            }
            i4++;
        }
        android.util.ArraySet<java.lang.String> arraySet2 = arraySet;
        int i6 = 0;
        java.util.ArrayList<android.view.autofill.AutofillId> arrayList6 = null;
        java.util.ArrayList<java.lang.String> arrayList7 = null;
        android.util.ArrayMap arrayMap2 = null;
        while (i6 < this.mViewStates.size()) {
            com.android.server.autofill.ViewState valueAt = this.mViewStates.valueAt(i6);
            int state = valueAt.getState();
            if ((state & 8) != 0) {
                if ((state & 2048) != 0) {
                    java.lang.String datasetId = valueAt.getDatasetId();
                    if (datasetId == null) {
                        android.util.Slog.w(TAG, "logContextCommitted(): no dataset id on " + valueAt);
                    } else {
                        android.view.autofill.AutofillValue autofilledValue = valueAt.getAutofilledValue();
                        android.view.autofill.AutofillValue currentValue = valueAt.getCurrentValue();
                        if (autofilledValue != null && autofilledValue.equals(currentValue)) {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "logContextCommitted(): ignoring changed " + valueAt + " because it has same value that was autofilled");
                            }
                        } else {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "logContextCommitted() found changed state: " + valueAt);
                            }
                            if (arrayList6 == null) {
                                arrayList6 = new java.util.ArrayList<>();
                                arrayList7 = new java.util.ArrayList<>();
                            }
                            arrayList6.add(valueAt.id);
                            arrayList7.add(datasetId);
                        }
                    }
                } else {
                    android.view.autofill.AutofillValue currentValue2 = valueAt.getCurrentValue();
                    if (currentValue2 == null) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "logContextCommitted(): skipping view without current value ( " + valueAt + ")");
                        }
                    } else if (z2) {
                        int i7 = 0;
                        while (i7 < size) {
                            java.util.List datasets2 = this.mResponses.valueAt(i7).getDatasets();
                            if (datasets2 == null) {
                                i3 = size;
                                z = z2;
                                str2 = str;
                                autofillValue = currentValue2;
                            } else if (datasets2.isEmpty()) {
                                i3 = size;
                                z = z2;
                                str2 = str;
                                autofillValue = currentValue2;
                            } else {
                                i3 = size;
                                int i8 = 0;
                                while (true) {
                                    z = z2;
                                    if (i8 >= datasets2.size()) {
                                        break;
                                    }
                                    android.service.autofill.Dataset dataset2 = (android.service.autofill.Dataset) datasets2.get(i8);
                                    android.util.ArrayMap arrayMap3 = arrayMap2;
                                    java.lang.String id2 = dataset2.getId();
                                    if (id2 == null) {
                                        if (com.android.server.autofill.Helper.sVerbose) {
                                            android.util.Slog.v(TAG, str + dataset2);
                                        }
                                        str3 = str;
                                        autofillValue2 = currentValue2;
                                        list = datasets2;
                                    } else {
                                        java.util.ArrayList fieldValues = dataset2.getFieldValues();
                                        str3 = str;
                                        int i9 = 0;
                                        while (true) {
                                            list = datasets2;
                                            if (i9 >= fieldValues.size()) {
                                                break;
                                            }
                                            if (!currentValue2.equals((android.view.autofill.AutofillValue) fieldValues.get(i9))) {
                                                arrayList5 = fieldValues;
                                                autofillValue3 = currentValue2;
                                            } else {
                                                if (!com.android.server.autofill.Helper.sDebug) {
                                                    arrayList5 = fieldValues;
                                                } else {
                                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                                    arrayList5 = fieldValues;
                                                    sb.append("field ");
                                                    sb.append(valueAt.id);
                                                    sb.append(" was manually filled with value set by dataset ");
                                                    sb.append(id2);
                                                    android.util.Slog.d(TAG, sb.toString());
                                                }
                                                if (arrayMap3 != null) {
                                                    arrayMap = arrayMap3;
                                                } else {
                                                    arrayMap = new android.util.ArrayMap();
                                                }
                                                android.util.ArraySet arraySet3 = (android.util.ArraySet) arrayMap.get(valueAt.id);
                                                if (arraySet3 != null) {
                                                    autofillValue3 = currentValue2;
                                                } else {
                                                    autofillValue3 = currentValue2;
                                                    arraySet3 = new android.util.ArraySet(1);
                                                    arrayMap.put(valueAt.id, arraySet3);
                                                }
                                                arraySet3.add(id2);
                                                arrayMap3 = arrayMap;
                                            }
                                            i9++;
                                            datasets2 = list;
                                            fieldValues = arrayList5;
                                            currentValue2 = autofillValue3;
                                        }
                                        autofillValue2 = currentValue2;
                                        if (this.mSelectedDatasetIds == null || !this.mSelectedDatasetIds.contains(id2)) {
                                            if (com.android.server.autofill.Helper.sVerbose) {
                                                android.util.Slog.v(TAG, "adding ignored dataset " + id2);
                                            }
                                            if (arraySet2 == null) {
                                                arraySet2 = new android.util.ArraySet<>();
                                            }
                                            arraySet2.add(id2);
                                        }
                                    }
                                    arrayMap2 = arrayMap3;
                                    i8++;
                                    z2 = z;
                                    str = str3;
                                    datasets2 = list;
                                    currentValue2 = autofillValue2;
                                }
                                str2 = str;
                                autofillValue = currentValue2;
                                i7++;
                                size = i3;
                                z2 = z;
                                str = str2;
                                currentValue2 = autofillValue;
                            }
                            if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(TAG, "logContextCommitted() no datasets at " + i7);
                            }
                            i7++;
                            size = i3;
                            z2 = z;
                            str = str2;
                            currentValue2 = autofillValue;
                        }
                    }
                }
            }
            i6++;
            size = size;
            z2 = z2;
            str = str;
        }
        if (arrayMap2 == null) {
            arrayList3 = null;
            arrayList4 = null;
        } else {
            int size2 = arrayMap2.size();
            java.util.ArrayList<android.view.autofill.AutofillId> arrayList8 = new java.util.ArrayList<>(size2);
            java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList9 = new java.util.ArrayList<>(size2);
            for (int i10 = 0; i10 < size2; i10++) {
                android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) arrayMap2.keyAt(i10);
                android.util.ArraySet arraySet4 = (android.util.ArraySet) arrayMap2.valueAt(i10);
                arrayList8.add(autofillId);
                arrayList9.add(new java.util.ArrayList<>(arraySet4));
            }
            arrayList3 = arrayList8;
            arrayList4 = arrayList9;
        }
        this.mService.logContextCommittedLocked(this.id, this.mClientState, this.mSelectedDatasetIds, arraySet2, arrayList6, arrayList7, arrayList3, arrayList4, arrayList, arrayList2, this.mComponentName, this.mCompatMode, i);
        this.mSessionCommittedEventLogger.maybeSetCommitReason(i2);
        this.mSessionCommittedEventLogger.maybeSetRequestCount(this.mRequestCount);
        this.mSaveEventLogger.maybeSetSaveUiNotShownReason(i);
    }

    private void logFieldClassificationScore(@android.annotation.NonNull com.android.server.autofill.FieldClassificationStrategy fieldClassificationStrategy, @android.annotation.NonNull android.service.autofill.FieldClassificationUserData fieldClassificationUserData, int i, int i2) {
        java.util.Collection<com.android.server.autofill.ViewState> values;
        java.lang.String[] values2 = fieldClassificationUserData.getValues();
        java.lang.String[] categoryIds = fieldClassificationUserData.getCategoryIds();
        java.lang.String fieldClassificationAlgorithm = fieldClassificationUserData.getFieldClassificationAlgorithm();
        android.os.Bundle defaultFieldClassificationArgs = fieldClassificationUserData.getDefaultFieldClassificationArgs();
        android.util.ArrayMap<java.lang.String, java.lang.String> fieldClassificationAlgorithms = fieldClassificationUserData.getFieldClassificationAlgorithms();
        android.util.ArrayMap<java.lang.String, android.os.Bundle> fieldClassificationArgs = fieldClassificationUserData.getFieldClassificationArgs();
        if (values2 == null || categoryIds == null || values2.length != categoryIds.length) {
            android.util.Slog.w(TAG, "setScores(): user data mismatch: values.length = " + (values2 == null ? -1 : values2.length) + ", ids.length = " + (categoryIds != null ? categoryIds.length : -1));
            return;
        }
        int maxFieldClassificationIdsSize = android.service.autofill.UserData.getMaxFieldClassificationIdsSize();
        java.util.ArrayList arrayList = new java.util.ArrayList(maxFieldClassificationIdsSize);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(maxFieldClassificationIdsSize);
        synchronized (this.mLock) {
            values = this.mViewStates.values();
        }
        int size = values.size();
        android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[size];
        java.util.ArrayList arrayList3 = new java.util.ArrayList(size);
        int i3 = 0;
        for (com.android.server.autofill.ViewState viewState : values) {
            arrayList3.add(viewState.getCurrentValue());
            autofillIdArr[i3] = viewState.id;
            i3++;
        }
        fieldClassificationStrategy.calculateScores(new android.os.RemoteCallback(new com.android.server.autofill.LogFieldClassificationScoreOnResultListener(this, i, i2, size, autofillIdArr, values2, categoryIds, arrayList, arrayList2)), arrayList3, values2, categoryIds, fieldClassificationAlgorithm, defaultFieldClassificationArgs, fieldClassificationAlgorithms, fieldClassificationArgs);
    }

    void handleLogFieldClassificationScore(@android.annotation.Nullable android.os.Bundle bundle, int i, int i2, int i3, android.view.autofill.AutofillId[] autofillIdArr, java.lang.String[] strArr, java.lang.String[] strArr2, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<android.service.autofill.FieldClassification> arrayList2) {
        java.lang.String[] strArr3 = strArr;
        android.util.ArrayMap arrayMap = null;
        if (bundle == null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "setFieldClassificationScore(): no results");
            }
            logContextCommitted(null, null, i, i2);
            return;
        }
        android.service.autofill.AutofillFieldClassificationService.Scores scores = (android.service.autofill.AutofillFieldClassificationService.Scores) bundle.getParcelable("scores", android.service.autofill.AutofillFieldClassificationService.Scores.class);
        if (scores == null) {
            android.util.Slog.w(TAG, "No field classification score on " + bundle);
            return;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            try {
                android.view.autofill.AutofillId autofillId = autofillIdArr[i4];
                android.util.ArrayMap arrayMap2 = arrayMap;
                int i6 = 0;
                while (i6 < strArr3.length) {
                    try {
                        java.lang.String str = strArr2[i6];
                        float f = scores.scores[i4][i6];
                        if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                            if (arrayMap2 == null) {
                                arrayMap2 = new android.util.ArrayMap(strArr3.length);
                            }
                            java.lang.Float f2 = (java.lang.Float) arrayMap2.get(str);
                            if (f2 != null && f2.floatValue() > f) {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "skipping score " + f + " because it's less than " + f2);
                                }
                            } else {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "adding score " + f + " at index " + i6 + " and id " + autofillId);
                                }
                                arrayMap2.put(str, java.lang.Float.valueOf(f));
                            }
                        } else if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "skipping score 0 at index " + i6 + " and id " + autofillId);
                        }
                        i6++;
                        strArr3 = strArr;
                    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        e = e;
                        i5 = i6;
                    }
                }
                if (arrayMap2 == null) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "no score for autofillId=" + autofillId);
                    }
                    i5 = i6;
                } else {
                    java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayMap2.size());
                    int i7 = 0;
                    while (i7 < arrayMap2.size()) {
                        try {
                            arrayList3.add(new android.service.autofill.FieldClassification.Match((java.lang.String) arrayMap2.keyAt(i7), ((java.lang.Float) arrayMap2.valueAt(i7)).floatValue()));
                            i7++;
                        } catch (java.lang.ArrayIndexOutOfBoundsException e2) {
                            e = e2;
                            i5 = i7;
                            wtf(e, "Error accessing FC score at [%d, %d] (%s): %s", java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), scores, e);
                            return;
                        }
                    }
                    arrayList.add(autofillId);
                    arrayList2.add(new android.service.autofill.FieldClassification(arrayList3));
                    i5 = i7;
                }
                i4++;
                strArr3 = strArr;
                arrayMap = null;
            } catch (java.lang.ArrayIndexOutOfBoundsException e3) {
                e = e3;
            }
        }
        logContextCommitted(arrayList, arrayList2, i, i2);
    }

    public void logSaveUiShown() {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.Session) obj).logSaveShown();
            }
        }, this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:209:0x05b5  */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [boolean, int] */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.autofill.Session.SaveResult showSaveLocked() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        android.graphics.drawable.Drawable serviceIcon;
        java.lang.CharSequence serviceLabel;
        ?? r2;
        int i;
        boolean z5;
        int i2;
        int i3;
        boolean z6;
        boolean z7 = false;
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#showSaveLocked() rejected - session: " + this.id + " destroyed");
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(9);
            this.mSaveEventLogger.logAndEndEvent();
            return new com.android.server.autofill.Session.SaveResult(false, false, 0);
        }
        this.mSessionState = 2;
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked("showSaveLocked(%s)");
        android.service.autofill.SaveInfo saveInfo = lastResponseLocked == null ? null : lastResponseLocked.getSaveInfo();
        if (this.mSessionFlags.mScreenHasCredmanField) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "Call to Session#showSaveLocked() rejected - there is credman field in screen");
            }
            return new com.android.server.autofill.Session.SaveResult(false, true, 0);
        }
        if (saveInfo == null) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "showSaveLocked(" + this.id + "): no saveInfo from service");
            }
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(2);
            this.mSaveEventLogger.logAndEndEvent();
            return new com.android.server.autofill.Session.SaveResult(false, true, 1);
        }
        if ((saveInfo.getFlags() & 4) != 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.v(TAG, "showSaveLocked(" + this.id + "): service asked to delay save");
            }
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(3);
            this.mSaveEventLogger.logAndEndEvent();
            return new com.android.server.autofill.Session.SaveResult(false, false, 2);
        }
        android.util.ArrayMap<android.view.autofill.AutofillId, android.service.autofill.InternalSanitizer> createSanitizers = com.android.server.autofill.Helper.createSanitizers(saveInfo);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.view.autofill.AutofillId[] requiredIds = saveInfo.getRequiredIds();
        if (requiredIds == null) {
            z = false;
            z2 = false;
            z7 = true;
        } else {
            int i4 = 0;
            z = false;
            z2 = false;
            while (true) {
                if (i4 >= requiredIds.length) {
                    z7 = true;
                    break;
                }
                android.view.autofill.AutofillId autofillId = requiredIds[i4];
                if (autofillId == null) {
                    android.util.Slog.w(TAG, "null autofill id on " + java.util.Arrays.toString(requiredIds));
                } else {
                    arraySet.add(autofillId);
                    com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
                    if (viewState == null) {
                        android.util.Slog.w(TAG, "showSaveLocked(): no ViewState for required " + autofillId);
                        break;
                    }
                    android.view.autofill.AutofillValue currentValue = viewState.getCurrentValue();
                    if (currentValue == null || currentValue.isEmpty()) {
                        currentValue = getValueFromContextsLocked(autofillId);
                        if (currentValue != null) {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "Value of required field " + autofillId + " didn't change; using initial value (" + currentValue + ") instead");
                            }
                        } else {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "empty value for required " + autofillId);
                            }
                            z7 = false;
                        }
                    }
                    android.view.autofill.AutofillValue sanitizedValue = getSanitizedValue(createSanitizers, autofillId, currentValue);
                    if (sanitizedValue == null) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "value of required field " + autofillId + " failed sanitization");
                        }
                        z7 = false;
                    } else {
                        viewState.setSanitizedValue(sanitizedValue);
                        arrayMap.put(autofillId, sanitizedValue);
                        android.view.autofill.AutofillValue autofilledValue = viewState.getAutofilledValue();
                        if (!sanitizedValue.equals(autofilledValue)) {
                            if (autofilledValue == null) {
                                android.view.autofill.AutofillValue valueFromContextsLocked = getValueFromContextsLocked(autofillId);
                                if (valueFromContextsLocked != null && valueFromContextsLocked.equals(sanitizedValue)) {
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(TAG, "id " + autofillId + " is part of dataset but initial value didn't change: " + sanitizedValue);
                                    }
                                    z6 = false;
                                } else {
                                    this.mSaveEventLogger.maybeSetIsNewField(true);
                                    z6 = true;
                                }
                            } else {
                                z6 = true;
                                z2 = true;
                            }
                            if (z6) {
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.d(TAG, "found a change on required " + autofillId + ": " + autofilledValue + " => " + sanitizedValue);
                                }
                                z = true;
                            }
                        }
                    }
                }
                i4++;
                z7 = false;
            }
        }
        android.view.autofill.AutofillId[] optionalIds = saveInfo.getOptionalIds();
        if (com.android.server.autofill.Helper.sVerbose) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("allRequiredAreNotEmpty: ");
            sb.append(z7);
            sb.append(" hasOptional: ");
            sb.append(optionalIds != null);
            android.util.Slog.v(TAG, sb.toString());
        }
        if (z7) {
            if (optionalIds != null && (!z || !z2)) {
                for (android.view.autofill.AutofillId autofillId2 : optionalIds) {
                    arraySet.add(autofillId2);
                    com.android.server.autofill.ViewState viewState2 = this.mViewStates.get(autofillId2);
                    if (viewState2 == null) {
                        android.util.Slog.w(TAG, "no ViewState for optional " + autofillId2);
                    } else if ((viewState2.getState() & 8) != 0) {
                        android.view.autofill.AutofillValue sanitizedValue2 = getSanitizedValue(createSanitizers, autofillId2, viewState2.getCurrentValue());
                        if (sanitizedValue2 == null) {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "value of opt. field " + autofillId2 + " failed sanitization");
                            }
                        } else {
                            arrayMap.put(autofillId2, sanitizedValue2);
                            android.view.autofill.AutofillValue autofilledValue2 = viewState2.getAutofilledValue();
                            if (!sanitizedValue2.equals(autofilledValue2)) {
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.d(TAG, "found a change on optional " + autofillId2 + ": " + autofilledValue2 + " => " + sanitizedValue2);
                                }
                                if (autofilledValue2 != null) {
                                    z2 = true;
                                } else {
                                    this.mSaveEventLogger.maybeSetIsNewField(true);
                                }
                                z = true;
                            }
                        }
                    } else {
                        android.view.autofill.AutofillValue valueFromContextsLocked2 = getValueFromContextsLocked(autofillId2);
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "no current value for " + autofillId2 + "; initial value is " + valueFromContextsLocked2);
                        }
                        if (valueFromContextsLocked2 != null) {
                            arrayMap.put(autofillId2, valueFromContextsLocked2);
                        }
                    }
                }
            }
            if (!z) {
                this.mSaveEventLogger.maybeSetSaveUiNotShownReason(5);
                this.mSaveEventLogger.logAndEndEvent();
                i3 = 4;
            } else {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "at least one field changed, validate fields for save UI");
                }
                android.service.autofill.InternalValidator validator = saveInfo.getValidator();
                if (validator != null) {
                    android.metrics.LogMaker newLogMaker = newLogMaker(1133);
                    try {
                        boolean isValid = validator.isValid(this);
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, validator + " returned " + isValid);
                        }
                        if (isValid) {
                            i2 = 10;
                        } else {
                            i2 = 5;
                        }
                        newLogMaker.setType(i2);
                        this.mMetricsLogger.write(newLogMaker);
                        if (!isValid) {
                            android.util.Slog.i(TAG, "not showing save UI because fields failed validation");
                            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(6);
                            this.mSaveEventLogger.logAndEndEvent();
                            return new com.android.server.autofill.Session.SaveResult(false, true, 5);
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Not showing save UI because validation failed:", e);
                        newLogMaker.setType(11);
                        this.mMetricsLogger.write(newLogMaker);
                        this.mSaveEventLogger.maybeSetSaveUiNotShownReason(6);
                        this.mSaveEventLogger.logAndEndEvent();
                        return new com.android.server.autofill.Session.SaveResult(false, true, 5);
                    }
                }
                java.util.List datasets = lastResponseLocked.getDatasets();
                if (datasets == null) {
                    z3 = true;
                    z4 = false;
                } else {
                    for (int i5 = 0; i5 < datasets.size(); i5++) {
                        android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) datasets.get(i5);
                        android.util.ArrayMap<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> fields = com.android.server.autofill.Helper.getFields(dataset);
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "Checking if saved fields match contents of dataset #" + i5 + ": " + dataset + "; savableIds=" + arraySet);
                        }
                        for (int i6 = 0; i6 < arraySet.size(); i6++) {
                            android.view.autofill.AutofillId autofillId3 = (android.view.autofill.AutofillId) arraySet.valueAt(i6);
                            android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) arrayMap.get(autofillId3);
                            if (autofillValue == null) {
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.d(TAG, "dataset has value for field that is null: " + autofillId3);
                                }
                            } else {
                                android.view.autofill.AutofillValue autofillValue2 = fields.get(autofillId3);
                                if (!autofillValue.equals(autofillValue2)) {
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(TAG, "found a dataset change on id " + autofillId3 + ": from " + autofillValue2 + " to " + autofillValue);
                                    }
                                } else if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "no dataset changes for id " + autofillId3);
                                }
                            }
                        }
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "ignoring Save UI because all fields match contents of dataset #" + i5 + ": " + dataset);
                        }
                        this.mSaveEventLogger.maybeSetSaveUiNotShownReason(7);
                        this.mSaveEventLogger.logAndEndEvent();
                        return new com.android.server.autofill.Session.SaveResult(false, true, 6);
                    }
                    z3 = true;
                    z4 = false;
                }
                android.view.autofill.IAutoFillManagerClient client = getClient();
                this.mPendingSaveUi = new com.android.server.autofill.ui.PendingUi(new android.os.Binder(), this.id, client);
                synchronized (this.mLock) {
                    serviceIcon = getServiceIcon(lastResponseLocked);
                    serviceLabel = getServiceLabel(lastResponseLocked);
                }
                if (serviceLabel == null) {
                    r2 = z3;
                    i = z4;
                } else {
                    if (serviceIcon != null) {
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        getUiForShowing().showSaveUi(serviceLabel, serviceIcon, this.mService.getServicePackageName(), saveInfo, this, this.mComponentName, this, this.mContext, this.mPendingSaveUi, z2, this.mCompatMode, lastResponseLocked.getShowSaveDialogIcon(), this.mSaveEventLogger);
                        this.mSaveEventLogger.maybeSetLatencySaveUiDisplayMillis(android.os.SystemClock.elapsedRealtime() - elapsedRealtime);
                        if (client == null) {
                            z5 = true;
                        } else {
                            try {
                                z5 = true;
                            } catch (android.os.RemoteException e2) {
                                e = e2;
                                z5 = true;
                            }
                            try {
                                client.setSaveUiState(this.id, true);
                            } catch (android.os.RemoteException e3) {
                                e = e3;
                                android.util.Slog.e(TAG, "Error notifying client to set save UI state to shown: " + e);
                                this.mSessionFlags.mShowingSaveUi = z5;
                                if (com.android.server.autofill.Helper.sDebug) {
                                }
                                return new com.android.server.autofill.Session.SaveResult(z5, false, 0);
                            }
                        }
                        this.mSessionFlags.mShowingSaveUi = z5;
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "Good news, everyone! All checks passed, show save UI for " + this.id + "!");
                        }
                        return new com.android.server.autofill.Session.SaveResult(z5, false, 0);
                    }
                    r2 = z3;
                    i = z4;
                }
                wtf(null, "showSaveLocked(): no service label or icon", new java.lang.Object[i]);
                this.mSaveEventLogger.maybeSetSaveUiNotShownReason(r2);
                this.mSaveEventLogger.logAndEndEvent();
                return new com.android.server.autofill.Session.SaveResult(i, r2, i);
            }
        } else {
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(4);
            this.mSaveEventLogger.logAndEndEvent();
            i3 = 3;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "showSaveLocked(" + this.id + "): with no changes, comes no responsibilities.allRequiredAreNotNull=" + z7 + ", atLeastOneChanged=" + z);
        }
        return new com.android.server.autofill.Session.SaveResult(false, true, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logSaveShown() {
        this.mService.logSaveShown(this.id, this.mClientState);
    }

    @android.annotation.Nullable
    private android.view.autofill.AutofillValue getSanitizedValue(@android.annotation.Nullable android.util.ArrayMap<android.view.autofill.AutofillId, android.service.autofill.InternalSanitizer> arrayMap, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue) {
        if (arrayMap == null || autofillValue == null) {
            return autofillValue;
        }
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
        android.view.autofill.AutofillValue sanitizedValue = viewState == null ? null : viewState.getSanitizedValue();
        if (sanitizedValue == null) {
            android.service.autofill.InternalSanitizer internalSanitizer = arrayMap.get(autofillId);
            if (internalSanitizer == null) {
                return autofillValue;
            }
            sanitizedValue = internalSanitizer.sanitize(autofillValue);
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Value for " + autofillId + "(" + autofillValue + ") sanitized to " + sanitizedValue);
            }
            if (viewState != null) {
                viewState.setSanitizedValue(sanitizedValue);
            }
        }
        return sanitizedValue;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isSaveUiShowingLocked() {
        return this.mSessionFlags.mShowingSaveUi;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.autofill.AutofillValue getValueFromContextsLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        for (int size = this.mContexts.size() - 1; size >= 0; size--) {
            android.app.assist.AssistStructure.ViewNode findViewNodeByAutofillId = com.android.server.autofill.Helper.findViewNodeByAutofillId(this.mContexts.get(size).getStructure(), autofillId);
            if (findViewNodeByAutofillId != null) {
                android.view.autofill.AutofillValue autofillValue = findViewNodeByAutofillId.getAutofillValue();
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "getValueFromContexts(" + this.id + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + autofillId + ") at " + size + ": " + autofillValue);
                }
                if (autofillValue != null && !autofillValue.isEmpty()) {
                    return autofillValue;
                }
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.CharSequence[] getAutofillOptionsFromContextsLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        for (int size = this.mContexts.size() - 1; size >= 0; size--) {
            android.app.assist.AssistStructure.ViewNode findViewNodeByAutofillId = com.android.server.autofill.Helper.findViewNodeByAutofillId(this.mContexts.get(size).getStructure(), autofillId);
            if (findViewNodeByAutofillId != null && findViewNodeByAutofillId.getAutofillOptions() != null) {
                return findViewNodeByAutofillId.getAutofillOptions();
            }
        }
        return null;
    }

    private void updateValuesForSaveLocked() {
        android.util.ArrayMap<android.view.autofill.AutofillId, android.service.autofill.InternalSanitizer> createSanitizers = com.android.server.autofill.Helper.createSanitizers(getSaveInfoLocked());
        int size = this.mContexts.size();
        for (int i = 0; i < size; i++) {
            android.service.autofill.FillContext fillContext = this.mContexts.get(i);
            android.app.assist.AssistStructure.ViewNode[] findViewNodesByAutofillIds = fillContext.findViewNodesByAutofillIds(getIdsOfAllViewStatesLocked());
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "updateValuesForSaveLocked(): updating " + fillContext);
            }
            for (int i2 = 0; i2 < this.mViewStates.size(); i2++) {
                com.android.server.autofill.ViewState valueAt = this.mViewStates.valueAt(i2);
                android.view.autofill.AutofillId autofillId = valueAt.id;
                android.view.autofill.AutofillValue currentValue = valueAt.getCurrentValue();
                if (currentValue == null) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "updateValuesForSaveLocked(): skipping " + autofillId);
                    }
                } else {
                    android.app.assist.AssistStructure.ViewNode viewNode = findViewNodesByAutofillIds[i2];
                    if (viewNode == null) {
                        android.util.Slog.w(TAG, "callSaveLocked(): did not find node with id " + autofillId);
                    } else {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "updateValuesForSaveLocked(): updating " + autofillId + " to " + currentValue);
                        }
                        android.view.autofill.AutofillValue sanitizedValue = valueAt.getSanitizedValue();
                        if (sanitizedValue == null) {
                            sanitizedValue = getSanitizedValue(createSanitizers, autofillId, currentValue);
                        }
                        if (sanitizedValue != null) {
                            viewNode.updateAutofillValue(sanitizedValue);
                        } else if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "updateValuesForSaveLocked(): not updating field " + autofillId + " because it failed sanitization");
                        }
                    }
                }
            }
            fillContext.getStructure().sanitizeForParceling(false);
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "updateValuesForSaveLocked(): dumping structure of " + fillContext + " before calling service.save()");
                fillContext.getStructure().dump(false);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void callSaveLocked() {
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#callSaveLocked() rejected - session: " + this.id + " destroyed");
            this.mSaveEventLogger.maybeSetIsSaved(false);
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        if (this.mRemoteFillService == null) {
            wtf(null, "callSaveLocked() called without a remote service. mForAugmentedAutofillOnly: %s", java.lang.Boolean.valueOf(this.mSessionFlags.mAugmentedAutofillOnly));
            this.mSaveEventLogger.maybeSetIsSaved(false);
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "callSaveLocked(" + this.id + "): mViewStates=" + this.mViewStates);
        }
        if (this.mContexts == null) {
            android.util.Slog.w(TAG, "callSaveLocked(): no contexts");
            this.mSaveEventLogger.maybeSetIsSaved(false);
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        updateValuesForSaveLocked();
        cancelCurrentRequestLocked();
        java.util.ArrayList<android.service.autofill.FillContext> mergePreviousSessionLocked = mergePreviousSessionLocked(true);
        android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse = this.mClassificationState.mLastFieldClassificationResponse;
        if (this.mService.isPccClassificationEnabled() && fieldClassificationResponse != null && !fieldClassificationResponse.getClassifications().isEmpty()) {
            if (this.mClientState == null) {
                this.mClientState = new android.os.Bundle();
            }
            this.mClientState.putParcelableArrayList(EXTRA_KEY_DETECTIONS, new java.util.ArrayList<>(fieldClassificationResponse.getClassifications()));
        }
        this.mRemoteFillService.onSaveRequest(new android.service.autofill.SaveRequest(mergePreviousSessionLocked, this.mClientState, this.mSelectedDatasetIds));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.ArrayList<android.service.autofill.FillContext> mergePreviousSessionLocked(boolean z) {
        java.util.ArrayList<com.android.server.autofill.Session> previousSessionsLocked = this.mService.getPreviousSessionsLocked(this);
        if (previousSessionsLocked != null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "mergeSessions(" + this.id + "): Merging the content of " + previousSessionsLocked.size() + " sessions for task " + this.taskId);
            }
            java.util.ArrayList<android.service.autofill.FillContext> arrayList = new java.util.ArrayList<>();
            for (int i = 0; i < previousSessionsLocked.size(); i++) {
                com.android.server.autofill.Session session = previousSessionsLocked.get(i);
                java.util.ArrayList<android.service.autofill.FillContext> arrayList2 = session.mContexts;
                if (arrayList2 == null) {
                    android.util.Slog.w(TAG, "mergeSessions(" + this.id + "): Not merging null contexts from " + session.id);
                } else {
                    if (z) {
                        session.updateValuesForSaveLocked();
                    }
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "mergeSessions(" + this.id + "): adding " + arrayList2.size() + " context from previous session #" + session.id);
                    }
                    arrayList.addAll(arrayList2);
                    if (this.mClientState == null && session.mClientState != null) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "mergeSessions(" + this.id + "): setting client state from previous session" + session.id);
                        }
                        this.mClientState = session.mClientState;
                    }
                }
            }
            arrayList.addAll(this.mContexts);
            return arrayList;
        }
        return new java.util.ArrayList<>(this.mContexts);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean requestNewFillResponseOnViewEnteredIfNecessaryLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.NonNull com.android.server.autofill.ViewState viewState, int i) {
        if ((i & 1) != 0) {
            this.mSessionFlags.mAugmentedAutofillOnly = false;
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Re-starting session on view " + autofillId + " and flags " + i);
            }
            requestNewFillResponseLocked(viewState, 256, i);
            return true;
        }
        if (shouldStartNewPartitionLocked(autofillId, i)) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Starting partition or augmented request for view id " + autofillId + ": " + viewState.getStateAsString());
            }
            this.mSessionFlags.mAugmentedAutofillOnly = false;
            requestNewFillResponseLocked(viewState, 32, i);
            return true;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Not starting new partition for view " + autofillId + ": " + viewState.getStateAsString());
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldStartNewPartitionLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId, int i) {
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
        android.util.SparseArray<android.service.autofill.FillResponse> sparseArray = shouldRequestSecondaryProvider(i) ? this.mSecondaryResponses : this.mResponses;
        if (sparseArray == null) {
            return viewState != null && (viewState.getState() & 65536) == 0;
        }
        if (this.mSessionFlags.mExpiredResponse) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Starting a new partition because the response has expired.");
            }
            return true;
        }
        int size = sparseArray.size();
        if (size >= com.android.server.autofill.AutofillManagerService.getPartitionMaxCount()) {
            android.util.Slog.e(TAG, "Not starting a new partition on " + autofillId + " because session " + this.id + " reached maximum of " + com.android.server.autofill.AutofillManagerService.getPartitionMaxCount());
            return false;
        }
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.FillResponse valueAt = sparseArray.valueAt(i2);
            if (com.android.internal.util.ArrayUtils.contains(valueAt.getIgnoredIds(), autofillId)) {
                return false;
            }
            android.service.autofill.SaveInfo saveInfo = valueAt.getSaveInfo();
            if (saveInfo != null && (com.android.internal.util.ArrayUtils.contains(saveInfo.getOptionalIds(), autofillId) || com.android.internal.util.ArrayUtils.contains(saveInfo.getRequiredIds(), autofillId))) {
                return false;
            }
            java.util.List datasets = valueAt.getDatasets();
            if (datasets != null) {
                int size2 = datasets.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    java.util.ArrayList fieldIds = ((android.service.autofill.Dataset) datasets.get(i3)).getFieldIds();
                    if (fieldIds != null && fieldIds.contains(autofillId)) {
                        return false;
                    }
                }
            }
            if (com.android.internal.util.ArrayUtils.contains(valueAt.getAuthenticationIds(), autofillId)) {
                return false;
            }
        }
        return true;
    }

    boolean shouldRequestSecondaryProvider(int i) {
        if (!this.mService.isAutofillCredmanIntegrationEnabled() || this.mSecondaryProviderHandler == null) {
            return false;
        }
        return this.mIsPrimaryCredential ? (i & 2048) == 0 : (i & 2048) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateLocked(android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, int i2) {
        java.lang.String trim;
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#updateLocked() rejected - session: " + autofillId + " destroyed");
            return;
        }
        if (i == 5) {
            this.mSessionFlags.mExpiredResponse = true;
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Set the response has expired.");
            }
            this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReasonIfNoReasonExists(3);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            return;
        }
        autofillId.setSessionId(this.id);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "updateLocked(" + this.id + "): id=" + autofillId + ", action=" + actionAsString(i) + ", flags=" + i2);
        }
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "updateLocked(" + this.id + "): mCurrentViewId=" + this.mCurrentViewId + ", mExpiredResponse=" + this.mSessionFlags.mExpiredResponse + ", viewState=" + viewState);
        }
        if (viewState == null) {
            if (i == 1 || i == 4 || i == 2) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Creating viewState for " + autofillId);
                }
                boolean isIgnoredLocked = isIgnoredLocked(autofillId);
                com.android.server.autofill.ViewState viewState2 = new com.android.server.autofill.ViewState(autofillId, this, isIgnoredLocked ? 128 : 1, this.mIsPrimaryCredential);
                this.mViewStates.put(autofillId, viewState2);
                if (isIgnoredLocked) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "updateLocked(): ignoring view " + viewState2);
                        return;
                    }
                    return;
                }
                viewState = viewState2;
            } else {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Ignoring specific action when viewState=null");
                    return;
                }
                return;
            }
        }
        if ((i2 & 256) != 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "force to reset fill dialog state");
            }
            this.mSessionFlags.mFillDialogDisabled = false;
        }
        if ((i2 & 512) != 0) {
            requestAssistStructureForPccLocked(i2);
            return;
        }
        if ((i2 & 1024) != 0) {
            this.mSessionFlags.mScreenHasCredmanField = true;
        }
        switch (i) {
            case 1:
                this.mCurrentViewId = viewState.id;
                viewState.update(autofillValue, rect, i2);
                startNewEventForPresentationStatsEventLogger();
                this.mPresentationStatsEventLogger.maybeSetIsNewRequest(true);
                if (!isRequestSupportFillDialog(i2)) {
                    this.mSessionFlags.mFillDialogDisabled = true;
                    this.mPreviouslyFillDialogPotentiallyStarted = false;
                } else {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(8);
                    this.mPreviouslyFillDialogPotentiallyStarted = true;
                }
                requestNewFillResponseLocked(viewState, 16, i2);
                return;
            case 2:
                this.mLatencyBaseTime = android.os.SystemClock.elapsedRealtime();
                boolean z = this.mPreviouslyFillDialogPotentiallyStarted;
                this.mPreviouslyFillDialogPotentiallyStarted = false;
                if (com.android.server.autofill.Helper.sVerbose && rect != null) {
                    android.util.Slog.v(TAG, "entered on virtual child " + autofillId + ": " + rect);
                }
                boolean equals = java.util.Objects.equals(this.mCurrentViewId, viewState.id);
                this.mCurrentViewId = viewState.id;
                if (autofillValue != null) {
                    viewState.setCurrentValue(autofillValue);
                }
                boolean z2 = (i2 & 2048) != 0;
                if (shouldRequestSecondaryProvider(i2)) {
                    if (requestNewFillResponseOnViewEnteredIfNecessaryLocked(autofillId, viewState, i2)) {
                        android.util.Slog.v(TAG, "Started a new fill request for secondary provider.");
                        return;
                    }
                    android.service.autofill.FillResponse secondaryResponse = viewState.getSecondaryResponse();
                    if (secondaryResponse != null) {
                        logPresentationStatsOnViewEntered(secondaryResponse, z2);
                    }
                    viewState.update(autofillValue, rect, i2);
                    return;
                }
                if (this.mCompatMode && (viewState.getState() & 512) != 0) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Ignoring VIEW_ENTERED on URL BAR (id=" + autofillId + ")");
                        return;
                    }
                    return;
                }
                synchronized (this.mLock) {
                    try {
                        if (!this.mLogViewEntered) {
                            this.mService.logViewEntered(this.id, null);
                        }
                        this.mLogViewEntered = true;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!z) {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(2);
                    this.mPresentationStatsEventLogger.logAndEndEvent();
                }
                if ((i2 & 1) == 0) {
                    if (this.mAugmentedAutofillableIds != null && this.mAugmentedAutofillableIds.contains(autofillId)) {
                        if (!equals) {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "trigger augmented autofill.");
                            }
                            triggerAugmentedAutofillLocked(i2);
                            return;
                        } else {
                            if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(TAG, "skip augmented autofill for same view: same view entered");
                                return;
                            }
                            return;
                        }
                    }
                    if (this.mSessionFlags.mAugmentedAutofillOnly && equals) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "skip augmented autofill for same view: standard autofill disabled.");
                            return;
                        }
                        return;
                    }
                }
                if (!z) {
                    startNewEventForPresentationStatsEventLogger();
                }
                if (requestNewFillResponseOnViewEnteredIfNecessaryLocked(autofillId, viewState, i2)) {
                    if (z) {
                        this.mPresentationStatsEventLogger.logAndEndEvent();
                        startNewEventForPresentationStatsEventLogger();
                        return;
                    }
                    return;
                }
                android.service.autofill.FillResponse response = viewState.getResponse();
                if (response != null) {
                    logPresentationStatsOnViewEntered(response, z2);
                }
                if (equals) {
                    setFillDialogDisabledAndStartInput();
                    return;
                } else {
                    viewState.update(autofillValue, rect, i2);
                    return;
                }
            case 3:
                if (java.util.Objects.equals(this.mCurrentViewId, viewState.id)) {
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "Exiting view " + autofillId);
                    }
                    this.mUi.hideFillUi(this);
                    this.mUi.hideFillDialog(this);
                    hideAugmentedAutofillLocked(viewState);
                    this.mInlineSessionController.resetInlineFillUiLocked();
                    if ((viewState.getState() & 65536) == 0) {
                        this.mCurrentViewId = null;
                    }
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(2);
                    this.mPresentationStatsEventLogger.logAndEndEvent();
                    return;
                }
                return;
            case 4:
                if (this.mCompatMode && (viewState.getState() & 512) != 0) {
                    if (this.mUrlBar == null) {
                        trim = null;
                    } else {
                        trim = this.mUrlBar.getText().toString().trim();
                    }
                    if (trim == null) {
                        wtf(null, "URL bar value changed, but current value is null", new java.lang.Object[0]);
                        return;
                    }
                    if (autofillValue == null || !autofillValue.isText()) {
                        wtf(null, "URL bar value changed to null or non-text: %s", autofillValue);
                        return;
                    }
                    if (autofillValue.getTextValue().toString().equals(trim)) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "Ignoring change on URL bar as it's the same");
                            return;
                        }
                        return;
                    } else if (this.mSaveOnAllViewsInvisible) {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "Ignoring change on URL because session will finish when views are gone");
                            return;
                        }
                        return;
                    } else {
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "Finishing session because URL bar changed");
                        }
                        forceRemoveFromServiceLocked(5);
                        return;
                    }
                }
                if (!java.util.Objects.equals(autofillValue, viewState.getCurrentValue())) {
                    logIfViewClearedLocked(autofillId, autofillValue, viewState);
                    updateViewStateAndUiOnValueChangedLocked(autofillId, autofillValue, viewState, i2);
                    return;
                }
                return;
            default:
                android.util.Slog.w(TAG, "updateLocked(): unknown action: " + i);
                return;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logPresentationStatsOnViewEntered(android.service.autofill.FillResponse fillResponse, boolean z) {
        this.mPresentationStatsEventLogger.maybeSetRequestId(fillResponse.getRequestId());
        this.mPresentationStatsEventLogger.maybeSetIsCredentialRequest(z);
        this.mPresentationStatsEventLogger.maybeSetFieldClassificationRequestId(this.mFieldClassificationIdSnapshot);
        this.mPresentationStatsEventLogger.maybeSetAvailableCount(fillResponse.getDatasets(), this.mCurrentViewId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void hideAugmentedAutofillLocked(@android.annotation.NonNull com.android.server.autofill.ViewState viewState) {
        if ((viewState.getState() & 4096) != 0) {
            viewState.resetState(4096);
            cancelAugmentedAutofillLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isIgnoredLocked(android.view.autofill.AutofillId autofillId) {
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return false;
        }
        return com.android.internal.util.ArrayUtils.contains(lastResponseLocked.getIgnoredIds(), autofillId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logIfViewClearedLocked(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, com.android.server.autofill.ViewState viewState) {
        if ((autofillValue == null || autofillValue.isEmpty()) && viewState.getCurrentValue() != null && viewState.getCurrentValue().isText() && viewState.getCurrentValue().getTextValue() != null && getSaveInfoLocked() != null) {
            int length = viewState.getCurrentValue().getTextValue().length();
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "updateLocked(" + autofillId + "): resetting value that was " + length + " chars long");
            }
            this.mMetricsLogger.write(newLogMaker(1124).addTaggedData(1125, java.lang.Integer.valueOf(length)));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateViewStateAndUiOnValueChangedLocked(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, com.android.server.autofill.ViewState viewState, int i) {
        java.lang.CharSequence textValue;
        if (this.mIgnoreViewStateResetToEmpty && ((autofillValue == null || autofillValue.isEmpty()) && viewState.getCurrentValue() != null && viewState.getCurrentValue().isText() && viewState.getCurrentValue().getTextValue() != null && viewState.getCurrentValue().getTextValue().length() > 1)) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "Ignoring view state reset to empty on id " + autofillId);
                return;
            }
            return;
        }
        java.lang.String str = null;
        if (autofillValue != null && autofillValue.isText() && (textValue = autofillValue.getTextValue()) != null) {
            str = textValue.toString();
        }
        updateFilteringStateOnValueChangedLocked(str, viewState);
        viewState.setCurrentValue(autofillValue);
        android.view.autofill.AutofillValue autofilledValue = viewState.getAutofilledValue();
        if (autofilledValue != null) {
            if (autofilledValue.equals(autofillValue)) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "ignoring autofilled change on id " + autofillId);
                }
                this.mInlineSessionController.hideInlineSuggestionsUiLocked(viewState.id);
                viewState.resetState(8);
                return;
            }
            if (viewState.id.equals(this.mCurrentViewId) && (viewState.getState() & 4) != 0) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "field changed after autofill on id " + autofillId);
                }
                viewState.resetState(4);
                this.mViewStates.get(this.mCurrentViewId).maybeCallOnFillReady(i);
            }
        }
        if (viewState.id.equals(this.mCurrentViewId) && (viewState.getState() & 8192) != 0) {
            if ((viewState.getState() & 32768) != 0) {
                this.mInlineSessionController.disableFilterMatching(viewState.id);
            }
            this.mInlineSessionController.filterInlineFillUiLocked(this.mCurrentViewId, str);
        } else if (viewState.id.equals(this.mCurrentViewId) && (viewState.getState() & 4096) != 0 && !android.text.TextUtils.isEmpty(str)) {
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(this.mCurrentViewId);
        }
        viewState.setState(8);
        getUiForShowing().filterFillUi(str, this);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateFilteringStateOnValueChangedLocked(@android.annotation.Nullable java.lang.String str, com.android.server.autofill.ViewState viewState) {
        java.lang.String str2 = "";
        if (str == null) {
            str = "";
        }
        android.view.autofill.AutofillValue currentValue = viewState.getCurrentValue();
        if (currentValue != null && currentValue.isText()) {
            str2 = currentValue.getTextValue().toString();
        }
        if ((viewState.getState() & 16384) == 0) {
            if (!com.android.server.autofill.Helper.containsCharsInOrder(str, str2)) {
                viewState.setState(16384);
            }
        } else if (!com.android.server.autofill.Helper.containsCharsInOrder(str2, str)) {
            viewState.setState(32768);
        }
    }

    @Override // com.android.server.autofill.ViewState.Listener
    public void onFillReady(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue, int i) {
        java.lang.String str;
        java.lang.CharSequence serviceLabelLocked;
        android.graphics.drawable.Drawable serviceIconLocked;
        synchronized (this.mLock) {
            try {
                this.mPresentationStatsEventLogger.maybeSetFieldClassificationRequestId(this.mFieldClassificationIdSnapshot);
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#onFillReady() rejected - session: " + this.id + " destroyed");
                    this.mSaveEventLogger.maybeSetSaveUiNotShownReason(9);
                    this.mSaveEventLogger.logAndEndEvent();
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(6);
                    this.mPresentationStatsEventLogger.logAndEndEvent();
                    return;
                }
                if (autofillValue != null && autofillValue.isText()) {
                    str = autofillValue.getTextValue().toString();
                } else {
                    str = null;
                }
                synchronized (this.mService.mLock) {
                    serviceLabelLocked = this.mService.getServiceLabelLocked();
                    serviceIconLocked = this.mService.getServiceIconLocked();
                }
                if (serviceLabelLocked == null || serviceIconLocked == null) {
                    wtf(null, "onFillReady(): no service label or icon", new java.lang.Object[0]);
                    return;
                }
                synchronized (this.mLock) {
                    this.mPresentationStatsEventLogger.maybeSetSuggestionSentTimestampMs((int) (android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime));
                }
                android.view.autofill.AutofillId[] fillDialogTriggerIds = fillResponse.getFillDialogTriggerIds();
                if (fillDialogTriggerIds != null && com.android.internal.util.ArrayUtils.contains(fillDialogTriggerIds, autofillId)) {
                    if (requestShowFillDialog(fillResponse, autofillId, str, i)) {
                        synchronized (this.mLock) {
                            this.mViewStates.get(this.mCurrentViewId).setState(131072);
                            this.mPresentationStatsEventLogger.maybeSetCountShown(fillResponse.getDatasets(), this.mCurrentViewId);
                            this.mPresentationStatsEventLogger.maybeSetDisplayPresentationType(3);
                        }
                        setFillDialogDisabled();
                        synchronized (this.mLock) {
                            this.mPresentationStatsEventLogger.maybeSetSuggestionPresentedTimestampMs((int) (android.os.SystemClock.elapsedRealtime() - this.mLatencyBaseTime));
                        }
                        return;
                    }
                    setFillDialogDisabled();
                }
                if (isCredmanIntegrationActive(fillResponse)) {
                    addCredentialManagerCallback(fillResponse);
                }
                if (fillResponse.supportsInlineSuggestions()) {
                    synchronized (this.mLock) {
                        try {
                            if (requestShowInlineSuggestionsLocked(fillResponse, str)) {
                                this.mViewStates.get(this.mCurrentViewId).setState(8192);
                                this.mPresentationStatsEventLogger.maybeSetCountShown(fillResponse.getDatasets(), this.mCurrentViewId);
                                this.mPresentationStatsEventLogger.maybeSetInlinePresentationAndSuggestionHostUid(this.mContext, this.userId);
                                return;
                            }
                        } finally {
                        }
                    }
                }
                getUiForShowing().showFillUi(autofillId, fillResponse, str, this.mService.getServicePackageName(), this.mComponentName, serviceLabelLocked, serviceIconLocked, this, this.mContext, this.id, this.mCompatMode, this.mService.getMaster().getMaxInputLengthForAutofill());
                synchronized (this.mLock) {
                    this.mPresentationStatsEventLogger.maybeSetCountShown(fillResponse.getDatasets(), this.mCurrentViewId);
                    this.mPresentationStatsEventLogger.maybeSetDisplayPresentationType(1);
                }
                synchronized (this.mLock) {
                    try {
                        if (this.mUiShownTime == 0) {
                            this.mUiShownTime = android.os.SystemClock.elapsedRealtime();
                            long j = this.mUiShownTime - this.mStartTime;
                            this.mPresentationStatsEventLogger.maybeSetSuggestionPresentedTimestampMs((int) (this.mUiShownTime - this.mLatencyBaseTime));
                            if (com.android.server.autofill.Helper.sDebug) {
                                java.lang.StringBuilder sb = new java.lang.StringBuilder("1st UI for ");
                                sb.append(this.mActivityToken);
                                sb.append(" shown in ");
                                android.util.TimeUtils.formatDuration(j, sb);
                                android.util.Slog.d(TAG, sb.toString());
                            }
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder("id=");
                            sb2.append(this.id);
                            sb2.append(" app=");
                            sb2.append(this.mActivityToken);
                            sb2.append(" svc=");
                            sb2.append(this.mService.getServicePackageName());
                            sb2.append(" latency=");
                            android.util.TimeUtils.formatDuration(j, sb2);
                            this.mUiLatencyHistory.log(sb2.toString());
                            addTaggedDataToRequestLogLocked(fillResponse.getRequestId(), 1145, java.lang.Long.valueOf(j));
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    private boolean isCredmanIntegrationActive(android.service.autofill.FillResponse fillResponse) {
        return android.service.autofill.Flags.autofillCredmanIntegration() && (fillResponse.getFlags() & 8) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateFillDialogTriggerIdsLocked() {
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return;
        }
        android.view.autofill.AutofillId[] fillDialogTriggerIds = lastResponseLocked.getFillDialogTriggerIds();
        notifyClientFillDialogTriggerIds(fillDialogTriggerIds != null ? java.util.Arrays.asList(fillDialogTriggerIds) : null);
    }

    private void notifyClientFillDialogTriggerIds(java.util.List<android.view.autofill.AutofillId> list) {
        try {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "notifyFillDialogTriggerIds(): " + list);
            }
            getClient().notifyFillDialogTriggerIds(list);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Cannot set trigger ids for fill dialog", e);
        }
    }

    private boolean isFillDialogUiEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mSessionFlags.mFillDialogDisabled || this.mSessionFlags.mScreenHasCredmanField) ? false : true;
            } finally {
            }
        }
        return z;
    }

    private void setFillDialogDisabled() {
        synchronized (this.mLock) {
            this.mSessionFlags.mFillDialogDisabled = true;
        }
        notifyClientFillDialogTriggerIds(null);
    }

    private void setFillDialogDisabledAndStartInput() {
        android.view.autofill.AutofillId autofillId;
        if (getUiForShowing().isFillDialogShowing()) {
            setFillDialogDisabled();
            synchronized (this.mLock) {
                autofillId = this.mCurrentViewId;
            }
            requestShowSoftInput(autofillId);
        }
    }

    private boolean requestShowFillDialog(android.service.autofill.FillResponse fillResponse, android.view.autofill.AutofillId autofillId, java.lang.String str, int i) {
        android.graphics.drawable.Drawable serviceIcon;
        if (!isFillDialogUiEnabled()) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Log.w(TAG, "requestShowFillDialog: fill dialog is disabled");
            }
            return false;
        }
        if ((i & 128) != 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Log.w(TAG, "requestShowFillDialog: IME is showing");
            }
            return false;
        }
        if (this.mInlineSessionController.isImeShowing()) {
            return false;
        }
        synchronized (this.mLock) {
            if (this.mLastFillDialogTriggerIds != null && com.android.internal.util.ArrayUtils.contains(this.mLastFillDialogTriggerIds, autofillId)) {
                synchronized (this.mLock) {
                    serviceIcon = getServiceIcon(fillResponse);
                }
                getUiForShowing().showFillDialog(autofillId, fillResponse, str, this.mService.getServicePackageName(), this.mComponentName, serviceIcon, this, this.id, this.mCompatMode, this.mPresentationStatsEventLogger);
                return true;
            }
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Log.w(TAG, "Last fill dialog triggered ids are changed.");
            }
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.graphics.drawable.Drawable getServiceIcon(android.service.autofill.FillResponse fillResponse) {
        int iconResourceId = fillResponse.getIconResourceId();
        android.graphics.drawable.Drawable drawable = null;
        if (iconResourceId != 0) {
            drawable = this.mService.getMaster().getContext().getPackageManager().getDrawable(this.mService.getServicePackageName(), iconResourceId, null);
        }
        if (drawable == null) {
            return this.mService.getServiceIconLocked();
        }
        return drawable;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.CharSequence getServiceLabel(android.service.autofill.FillResponse fillResponse) {
        int serviceDisplayNameResourceId = fillResponse.getServiceDisplayNameResourceId();
        java.lang.CharSequence charSequence = null;
        if (serviceDisplayNameResourceId != 0) {
            charSequence = this.mService.getMaster().getContext().getPackageManager().getText(this.mService.getServicePackageName(), serviceDisplayNameResourceId, null);
        }
        if (charSequence == null) {
            return this.mService.getServiceLabelLocked();
        }
        return charSequence;
    }

    private boolean requestShowInlineSuggestionsLocked(@android.annotation.NonNull final android.service.autofill.FillResponse fillResponse, @android.annotation.Nullable java.lang.String str) {
        if (this.mCurrentViewId == null) {
            android.util.Log.w(TAG, "requestShowInlineSuggestionsLocked(): no view currently focused");
            return false;
        }
        final android.view.autofill.AutofillId autofillId = this.mCurrentViewId;
        java.util.Optional<android.view.inputmethod.InlineSuggestionsRequest> inlineSuggestionsRequestLocked = this.mInlineSessionController.getInlineSuggestionsRequestLocked();
        if (!inlineSuggestionsRequestLocked.isPresent()) {
            android.util.Log.w(TAG, "InlineSuggestionsRequest unavailable");
            return false;
        }
        com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked = this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (remoteInlineSuggestionRenderServiceLocked == null) {
            android.util.Log.w(TAG, "RemoteInlineSuggestionRenderService not found");
            return false;
        }
        synchronized (this.mLock) {
            this.mLoggedInlineDatasetShown = false;
        }
        return this.mInlineSessionController.setInlineFillUiLocked(com.android.server.autofill.ui.InlineFillUi.forAutofill(new com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo(inlineSuggestionsRequestLocked.get(), autofillId, str, remoteInlineSuggestionRenderServiceLocked, this.userId, this.id), fillResponse, new com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback() { // from class: com.android.server.autofill.Session.3
            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void autofill(@android.annotation.NonNull android.service.autofill.Dataset dataset, int i) {
                com.android.server.autofill.Session.this.fill(fillResponse.getRequestId(), i, dataset, 2);
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void authenticate(int i, int i2) {
                com.android.server.autofill.Session.this.authenticate(fillResponse.getRequestId(), i2, fillResponse.getAuthentication(), fillResponse.getClientState(), 2);
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void startIntentSender(@android.annotation.NonNull android.content.IntentSender intentSender) {
                com.android.server.autofill.Session.this.startIntentSender(intentSender, new android.content.Intent());
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onError() {
                synchronized (com.android.server.autofill.Session.this.mLock) {
                    com.android.server.autofill.Session.this.mInlineSessionController.setInlineFillUiLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
                }
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onInflate() {
                com.android.server.autofill.Session.this.onShown(2);
            }
        }, this.mService.getMaster().getMaxInputLengthForAutofill()));
    }

    private void addCredentialManagerCallback(android.service.autofill.FillResponse fillResponse) {
        if (fillResponse.getDatasets() == null) {
            return;
        }
        for (android.service.autofill.Dataset dataset : fillResponse.getDatasets()) {
            if (isPinnedDataset(dataset)) {
                android.util.Slog.d(TAG, "Adding Credential Manager callback to a pinned entry");
                addCredentialManagerCallbackForDataset(dataset, fillResponse.getRequestId());
            }
        }
    }

    private void addCredentialManagerCallbackForDataset(android.service.autofill.Dataset dataset, final int i) {
        final android.view.autofill.AutofillId autofillId;
        if (dataset != null && dataset.getFieldIds().size() == 1) {
            autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(0);
        } else {
            autofillId = null;
        }
        android.os.ResultReceiver ipcFriendlyResultReceiver = toIpcFriendlyResultReceiver(new android.os.ResultReceiver(this.mHandler) { // from class: com.android.server.autofill.Session.4
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i2, android.os.Bundle bundle) {
                if (i2 == 0) {
                    android.util.Slog.d(com.android.server.autofill.Session.TAG, "onReceiveResult from Credential Manager bottom sheet");
                    android.credentials.GetCredentialResponse getCredentialResponse = (android.credentials.GetCredentialResponse) bundle.getParcelable("android.service.credentials.extra.GET_CREDENTIAL_RESPONSE", android.credentials.GetCredentialResponse.class);
                    if (android.service.autofill.Flags.autofillCredmanDevIntegration()) {
                        com.android.server.autofill.Session.this.sendCredentialManagerResponseToApp(getCredentialResponse, null, autofillId);
                        return;
                    }
                    android.service.autofill.Dataset datasetFromCredentialResponse = com.android.server.autofill.Session.this.getDatasetFromCredentialResponse(getCredentialResponse);
                    if (datasetFromCredentialResponse != null) {
                        com.android.server.autofill.Session.this.autoFill(i, -1, datasetFromCredentialResponse, false, 4);
                        return;
                    }
                    return;
                }
                if (i2 == -1) {
                    java.lang.String[] stringArray = bundle.getStringArray("android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION");
                    if (stringArray != null && stringArray.length >= 2) {
                        android.util.Slog.w(com.android.server.autofill.Session.TAG, "Credman bottom sheet from pinned entry failed with: + " + stringArray[0] + " , " + stringArray[1]);
                        com.android.server.autofill.Session.this.sendCredentialManagerResponseToApp(null, new android.credentials.GetCredentialException(stringArray[0], stringArray[1]), autofillId);
                        return;
                    }
                    return;
                }
                android.util.Slog.d(com.android.server.autofill.Session.TAG, "Unknown resultCode from credential manager bottom sheet: " + i2);
            }
        });
        android.content.Intent credentialFillInIntent = dataset.getCredentialFillInIntent();
        if (credentialFillInIntent == null) {
            credentialFillInIntent = new android.content.Intent();
        }
        credentialFillInIntent.putExtra("android.credentials.selection.extra.FINAL_RESPONSE_RECEIVER", ipcFriendlyResultReceiver);
        dataset.setCredentialFillInIntent(credentialFillInIntent);
    }

    private android.os.ResultReceiver toIpcFriendlyResultReceiver(android.os.ResultReceiver resultReceiver) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        resultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) android.os.ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return resultReceiver2;
    }

    boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    android.view.autofill.IAutoFillManagerClient getClient() {
        android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient;
        synchronized (this.mLock) {
            iAutoFillManagerClient = this.mClient;
        }
        return iAutoFillManagerClient;
    }

    private void notifyUnavailableToClient(int i, @android.annotation.Nullable java.util.ArrayList<android.view.autofill.AutofillId> arrayList) {
        synchronized (this.mLock) {
            if (this.mCurrentViewId == null) {
                return;
            }
            try {
                if (this.mHasCallback) {
                    this.mClient.notifyNoFillUi(this.id, this.mCurrentViewId, i);
                } else if (i != 0) {
                    this.mClient.setSessionFinished(i, arrayList);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error notifying client no fill UI: id=" + this.mCurrentViewId, e);
            }
        }
    }

    private void notifyDisableAutofillToClient(long j, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            if (this.mCurrentViewId == null) {
                return;
            }
            try {
                this.mClient.notifyDisableAutofill(j, componentName);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error notifying client disable autofill: id=" + this.mCurrentViewId, e);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateTrackedIdsLocked() {
        android.util.ArraySet arraySet;
        android.view.autofill.AutofillId autofillId;
        int i;
        boolean z;
        android.util.ArraySet arraySet2 = null;
        android.service.autofill.FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return;
        }
        this.mSaveOnAllViewsInvisible = false;
        android.service.autofill.SaveInfo saveInfo = lastResponseLocked.getSaveInfo();
        if (saveInfo == null) {
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(2);
            arraySet = null;
            autofillId = null;
            i = 0;
            z = true;
        } else {
            android.view.autofill.AutofillId triggerId = saveInfo.getTriggerId();
            if (triggerId != null) {
                writeLog(1228);
                this.mSaveEventLogger.maybeSetSaveUiShownReason(3);
            }
            i = saveInfo.getFlags();
            this.mSaveOnAllViewsInvisible = (i & 1) != 0;
            this.mFillResponseEventLogger.maybeSetSaveUiTriggerIds(1);
            this.mSaveEventLogger.maybeSetRequestId(lastResponseLocked.getRequestId());
            this.mSaveEventLogger.maybeSetAppPackageUid(this.uid);
            this.mSaveEventLogger.maybeSetSaveUiTriggerIds(1);
            this.mSaveEventLogger.maybeSetFlag(i);
            if (!this.mSaveOnAllViewsInvisible) {
                arraySet = null;
            } else {
                arraySet = new android.util.ArraySet();
                if (saveInfo.getRequiredIds() != null) {
                    java.util.Collections.addAll(arraySet, saveInfo.getRequiredIds());
                    this.mSaveEventLogger.maybeSetSaveUiShownReason(1);
                }
                if (saveInfo.getOptionalIds() != null) {
                    java.util.Collections.addAll(arraySet, saveInfo.getOptionalIds());
                    this.mSaveEventLogger.maybeSetSaveUiShownReason(2);
                }
            }
            if ((i & 2) != 0) {
                this.mSaveEventLogger.maybeSetSaveUiShownReason(0);
                this.mSaveEventLogger.maybeSetSaveUiNotShownReason(8);
                z = false;
                autofillId = triggerId;
            } else {
                z = true;
                autofillId = triggerId;
            }
        }
        java.util.List datasets = lastResponseLocked.getDatasets();
        if (datasets != null) {
            for (int i2 = 0; i2 < datasets.size(); i2++) {
                java.util.ArrayList fieldIds = ((android.service.autofill.Dataset) datasets.get(i2)).getFieldIds();
                if (fieldIds != null) {
                    for (int i3 = 0; i3 < fieldIds.size(); i3++) {
                        android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) fieldIds.get(i3);
                        if (autofillId2 != null && (arraySet == null || !arraySet.contains(autofillId2))) {
                            arraySet2 = com.android.internal.util.ArrayUtils.add(arraySet2, autofillId2);
                        }
                    }
                }
            }
        }
        try {
            if (com.android.server.autofill.Helper.sVerbose) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("updateTrackedIdsLocked(): ");
                sb.append(arraySet);
                sb.append(" => ");
                sb.append(arraySet2);
                sb.append(" triggerId: ");
                sb.append(autofillId);
                sb.append(" saveOnFinish:");
                sb.append(z);
                sb.append(" flags: ");
                sb.append(i);
                sb.append(" hasSaveInfo: ");
                sb.append(saveInfo != null);
                android.util.Slog.v(TAG, sb.toString());
            }
            this.mClient.setTrackedViews(this.id, com.android.server.autofill.Helper.toArray(arraySet), this.mSaveOnAllViewsInvisible, z, com.android.server.autofill.Helper.toArray(arraySet2), autofillId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Cannot set tracked ids", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setAutofillFailureLocked(@android.annotation.NonNull java.util.List<android.view.autofill.AutofillId> list) {
        for (int i = 0; i < list.size(); i++) {
            android.view.autofill.AutofillId autofillId = list.get(i);
            com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
            if (viewState == null) {
                android.util.Slog.w(TAG, "setAutofillFailure(): no view for id " + autofillId);
            } else {
                viewState.resetState(4);
                viewState.setState(viewState.getState() | 1024);
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Changed state of " + autofillId + " to " + viewState.getStateAsString());
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void replaceResponseLocked(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.service.autofill.FillResponse fillResponse2, @android.annotation.Nullable android.os.Bundle bundle) {
        setViewStatesLocked(fillResponse, 1, true, true);
        fillResponse2.setRequestId(fillResponse.getRequestId());
        processResponseLockedForPcc(fillResponse2, bundle, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void processNullResponseLocked(int i, int i2) {
        java.util.ArrayList<android.view.autofill.AutofillId> arrayList;
        unregisterDelayedFillBroadcastLocked();
        if ((i2 & 1) != 0) {
            getUiForShowing().showError(android.R.string.app_suspended_unsuspend_message, this);
        }
        android.service.autofill.FillContext fillContextByRequestIdLocked = getFillContextByRequestIdLocked(i);
        if (fillContextByRequestIdLocked != null) {
            arrayList = com.android.server.autofill.Helper.getAutofillIds(fillContextByRequestIdLocked.getStructure(), true);
        } else {
            android.util.Slog.w(TAG, "processNullResponseLocked(): no context for req " + i);
            arrayList = null;
        }
        this.mFillResponseEventLogger.maybeSetAvailableCount(0);
        this.mFillResponseEventLogger.logAndEndEvent();
        this.mService.resetLastResponse();
        this.mAugmentedAutofillDestroyer = triggerAugmentedAutofillLocked(i2);
        if (this.mAugmentedAutofillDestroyer == null && (i2 & 4) == 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "canceling session " + this.id + " when service returned null and it cannot be augmented. AutofillableIds: " + arrayList);
            }
            notifyUnavailableToClient(2, arrayList);
            removeFromService();
            return;
        }
        if ((i2 & 4) != 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "keeping session " + this.id + " when service returned null and augmented service is disabled for password fields. AutofillableIds: " + arrayList);
            }
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(this.mCurrentViewId);
        } else if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "keeping session " + this.id + " when service returned null but it can be augmented. AutofillableIds: " + arrayList);
        }
        this.mAugmentedAutofillableIds = arrayList;
        try {
            this.mClient.setState(32);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error setting client to autofill-only", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Runnable triggerAugmentedAutofillLocked(int i) {
        if ((i & 4) != 0) {
            return null;
        }
        int supportedSmartSuggestionModesLocked = this.mService.getSupportedSmartSuggestionModesLocked();
        if (supportedSmartSuggestionModesLocked == 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "triggerAugmentedAutofillLocked(): no supported modes");
            }
            return null;
        }
        final com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = this.mService.getRemoteAugmentedAutofillServiceLocked();
        if (remoteAugmentedAutofillServiceLocked == null) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "triggerAugmentedAutofillLocked(): no service for user");
            }
            return null;
        }
        if ((supportedSmartSuggestionModesLocked & 1) == 0) {
            android.util.Slog.w(TAG, "Unsupported Smart Suggestion mode: " + supportedSmartSuggestionModesLocked);
            return null;
        }
        if (this.mCurrentViewId == null) {
            android.util.Slog.w(TAG, "triggerAugmentedAutofillLocked(): no view currently focused");
            return null;
        }
        boolean isWhitelistedForAugmentedAutofillLocked = this.mService.isWhitelistedForAugmentedAutofillLocked(this.mComponentName);
        if (!isWhitelistedForAugmentedAutofillLocked) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "triggerAugmentedAutofillLocked(): " + android.content.ComponentName.flattenToShortString(this.mComponentName) + " not whitelisted ");
            }
            logAugmentedAutofillRequestLocked(1, remoteAugmentedAutofillServiceLocked.getComponentName(), this.mCurrentViewId, isWhitelistedForAugmentedAutofillLocked, null);
            return null;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling Augmented Autofill Service (" + android.content.ComponentName.flattenToShortString(remoteAugmentedAutofillServiceLocked.getComponentName()) + ") on view " + this.mCurrentViewId + " using suggestion mode " + android.view.autofill.AutofillManager.getSmartSuggestionModeToString(1) + " when server returned null for session " + this.id);
        }
        this.mFillRequestEventLogger.startLogForNewRequest();
        this.mRequestCount++;
        this.mFillRequestEventLogger.maybeSetAppPackageUid(this.uid);
        this.mFillRequestEventLogger.maybeSetFlags(this.mFlags);
        this.mFillRequestEventLogger.maybeSetRequestId(1);
        this.mFillRequestEventLogger.maybeSetIsAugmented(true);
        this.mFillRequestEventLogger.logAndEndEvent();
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(this.mCurrentViewId);
        viewState.setState(4096);
        android.view.autofill.AutofillValue currentValue = viewState.getCurrentValue();
        if (this.mAugmentedRequestsLogs == null) {
            this.mAugmentedRequestsLogs = new java.util.ArrayList<>();
        }
        this.mAugmentedRequestsLogs.add(newLogMaker(1630, remoteAugmentedAutofillServiceLocked.getComponentName().getPackageName()));
        android.view.autofill.AutofillId autofillId = this.mCurrentViewId;
        com.android.server.autofill.Session.AugmentedAutofillInlineSuggestionRequestConsumer augmentedAutofillInlineSuggestionRequestConsumer = new com.android.server.autofill.Session.AugmentedAutofillInlineSuggestionRequestConsumer(this, autofillId, isWhitelistedForAugmentedAutofillLocked, 1, currentValue);
        com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked = this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (remoteInlineSuggestionRenderServiceLocked != null && ((this.mSessionFlags.mAugmentedAutofillOnly || !this.mSessionFlags.mInlineSupportedByService || this.mSessionFlags.mExpiredResponse) && (isViewFocusedLocked(i) || isRequestSupportFillDialog(i)))) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Create inline request for augmented autofill");
            }
            remoteInlineSuggestionRenderServiceLocked.getInlineSuggestionsRendererInfo(new android.os.RemoteCallback(new com.android.server.autofill.Session.AugmentedAutofillInlineSuggestionRendererOnResultListener(this, autofillId, augmentedAutofillInlineSuggestionRequestConsumer), this.mHandler));
        } else {
            augmentedAutofillInlineSuggestionRequestConsumer.accept((com.android.server.autofill.Session.AugmentedAutofillInlineSuggestionRequestConsumer) this.mInlineSessionController.getInlineSuggestionsRequestLocked().orElse(null));
        }
        if (this.mAugmentedAutofillDestroyer == null) {
            java.util.Objects.requireNonNull(remoteAugmentedAutofillServiceLocked);
            this.mAugmentedAutofillDestroyer = new java.lang.Runnable() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.RemoteAugmentedAutofillService.this.onDestroyAutofillWindowsRequest();
                }
            };
        }
        return this.mAugmentedAutofillDestroyer;
    }

    private static class AugmentedAutofillInlineSuggestionRendererOnResultListener implements android.os.RemoteCallback.OnResultListener {
        final android.view.autofill.AutofillId mFocusedId;
        java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> mRequestAugmentedAutofill;
        java.lang.ref.WeakReference<com.android.server.autofill.Session> mSessionWeakRef;

        AugmentedAutofillInlineSuggestionRendererOnResultListener(com.android.server.autofill.Session session, android.view.autofill.AutofillId autofillId, java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> consumer) {
            this.mSessionWeakRef = new java.lang.ref.WeakReference<>(session);
            this.mFocusedId = autofillId;
            this.mRequestAugmentedAutofill = consumer;
        }

        public void onResult(@android.annotation.Nullable android.os.Bundle bundle) {
            com.android.server.autofill.Session session = this.mSessionWeakRef.get();
            if (com.android.server.autofill.Session.logIfSessionNull(session, "AugmentedAutofillInlineSuggestionRendererOnResultListener:")) {
                return;
            }
            synchronized (session.mLock) {
                session.mInlineSessionController.onCreateInlineSuggestionsRequestLocked(this.mFocusedId, this.mRequestAugmentedAutofill, bundle);
            }
        }
    }

    private static class AugmentedAutofillInlineSuggestionRequestConsumer implements java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> {
        final android.view.autofill.AutofillValue mCurrentValue;
        final android.view.autofill.AutofillId mFocusedId;
        final boolean mIsAllowlisted;
        final int mMode;
        java.lang.ref.WeakReference<com.android.server.autofill.Session> mSessionWeakRef;

        AugmentedAutofillInlineSuggestionRequestConsumer(com.android.server.autofill.Session session, android.view.autofill.AutofillId autofillId, boolean z, int i, android.view.autofill.AutofillValue autofillValue) {
            this.mSessionWeakRef = new java.lang.ref.WeakReference<>(session);
            this.mFocusedId = autofillId;
            this.mIsAllowlisted = z;
            this.mMode = i;
            this.mCurrentValue = autofillValue;
        }

        @Override // java.util.function.Consumer
        public void accept(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest) {
            com.android.server.autofill.Session session = this.mSessionWeakRef.get();
            if (com.android.server.autofill.Session.logIfSessionNull(session, "AugmentedAutofillInlineSuggestionRequestConsumer:")) {
                return;
            }
            session.onAugmentedAutofillInlineSuggestionAccept(inlineSuggestionsRequest, this.mFocusedId, this.mIsAllowlisted, this.mMode, this.mCurrentValue);
        }
    }

    private static class AugmentedAutofillInlineSuggestionsResponseCallback implements java.util.function.Function<com.android.server.autofill.ui.InlineFillUi, java.lang.Boolean> {
        java.lang.ref.WeakReference<com.android.server.autofill.Session> mSessionWeakRef;

        AugmentedAutofillInlineSuggestionsResponseCallback(com.android.server.autofill.Session session) {
            this.mSessionWeakRef = new java.lang.ref.WeakReference<>(session);
        }

        @Override // java.util.function.Function
        public java.lang.Boolean apply(com.android.server.autofill.ui.InlineFillUi inlineFillUi) {
            java.lang.Boolean valueOf;
            com.android.server.autofill.Session session = this.mSessionWeakRef.get();
            if (com.android.server.autofill.Session.logIfSessionNull(session, "AugmentedAutofillInlineSuggestionsResponseCallback:")) {
                return false;
            }
            synchronized (session.mLock) {
                valueOf = java.lang.Boolean.valueOf(session.mInlineSessionController.setInlineFillUiLocked(inlineFillUi));
            }
            return valueOf;
        }
    }

    private static class AugmentedAutofillErrorCallback implements java.lang.Runnable {
        java.lang.ref.WeakReference<com.android.server.autofill.Session> mSessionWeakRef;

        AugmentedAutofillErrorCallback(com.android.server.autofill.Session session) {
            this.mSessionWeakRef = new java.lang.ref.WeakReference<>(session);
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.autofill.Session session = this.mSessionWeakRef.get();
            if (com.android.server.autofill.Session.logIfSessionNull(session, "AugmentedAutofillErrorCallback:")) {
                return;
            }
            session.onAugmentedAutofillErrorCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean logIfSessionNull(com.android.server.autofill.Session session, java.lang.String str) {
        if (session == null) {
            android.util.Slog.wtf(TAG, str + " Session null");
            return true;
        }
        if (session.mDestroyed) {
            android.util.Slog.w(TAG, str + " Session destroyed, but following through");
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAugmentedAutofillInlineSuggestionAccept(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.view.autofill.AutofillId autofillId, boolean z, int i, android.view.autofill.AutofillValue autofillValue) {
        synchronized (this.mLock) {
            com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = this.mService.getRemoteAugmentedAutofillServiceLocked();
            logAugmentedAutofillRequestLocked(i, remoteAugmentedAutofillServiceLocked.getComponentName(), autofillId, z, java.lang.Boolean.valueOf(inlineSuggestionsRequest != null));
            remoteAugmentedAutofillServiceLocked.onRequestAutofillLocked(this.id, this.mClient, this.taskId, this.mComponentName, this.mActivityToken, android.view.autofill.AutofillId.withoutSession(autofillId), autofillValue, inlineSuggestionsRequest, new com.android.server.autofill.Session.AugmentedAutofillInlineSuggestionsResponseCallback(this), new com.android.server.autofill.Session.AugmentedAutofillErrorCallback(this), this.mService.getRemoteInlineSuggestionRenderServiceLocked(), this.userId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAugmentedAutofillErrorCallback() {
        synchronized (this.mLock) {
            cancelAugmentedAutofillLocked();
            this.mInlineSessionController.setInlineFillUiLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(this.mCurrentViewId));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cancelAugmentedAutofillLocked() {
        com.android.server.autofill.RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = this.mService.getRemoteAugmentedAutofillServiceLocked();
        if (remoteAugmentedAutofillServiceLocked == null) {
            android.util.Slog.w(TAG, "cancelAugmentedAutofillLocked(): no service for user");
            return;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "cancelAugmentedAutofillLocked() on " + this.mCurrentViewId);
        }
        remoteAugmentedAutofillServiceLocked.onDestroyAutofillWindowsRequest();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void processResponseLocked(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.Nullable android.os.Bundle bundle, int i) {
        this.mUi.hideAll(this);
        if ((fillResponse.getFlags() & 4) == 0) {
            android.util.Slog.d(TAG, "Service did not request to wait for delayed fill response.");
            unregisterDelayedFillBroadcastLocked();
        }
        int requestId = fillResponse.getRequestId();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "processResponseLocked(): mCurrentViewId=" + this.mCurrentViewId + ",flags=" + i + ", reqId=" + requestId + ", resp=" + fillResponse + ",newClientState=" + bundle);
        }
        if (this.mResponses == null) {
            this.mResponses = new android.util.SparseArray<>(2);
        }
        this.mResponses.put(requestId, fillResponse);
        this.mClientState = bundle != null ? bundle : fillResponse.getClientState();
        boolean z = bundle != null && bundle.getBoolean("webview_requested_credential", false);
        java.util.List<android.service.autofill.Dataset> datasets = fillResponse.getDatasets();
        this.mPresentationStatsEventLogger.maybeSetWebviewRequestedCredential(z);
        this.mPresentationStatsEventLogger.maybeSetFieldClassificationRequestId(sIdCounterForPcc.get());
        this.mPresentationStatsEventLogger.maybeSetAvailableCount(datasets, this.mCurrentViewId);
        this.mFillResponseEventLogger.maybeSetDatasetsCountAfterPotentialPccFiltering(datasets);
        setViewStatesLocked(fillResponse, 2, false, true);
        updateFillDialogTriggerIdsLocked();
        updateTrackedIdsLocked();
        if (this.mCurrentViewId == null) {
            return;
        }
        this.mViewStates.get(this.mCurrentViewId).maybeCallOnFillReady(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setViewStatesLocked(android.service.autofill.FillResponse fillResponse, int i, boolean z, boolean z2) {
        java.util.List datasets = fillResponse.getDatasets();
        if (datasets != null && !datasets.isEmpty()) {
            for (int i2 = 0; i2 < datasets.size(); i2++) {
                android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) datasets.get(i2);
                if (dataset == null) {
                    android.util.Slog.w(TAG, "Ignoring null dataset on " + datasets);
                } else {
                    setViewStatesLocked(fillResponse, dataset, i, z, z2);
                }
            }
        } else if (fillResponse.getAuthentication() != null) {
            for (android.view.autofill.AutofillId autofillId : fillResponse.getAuthenticationIds()) {
                com.android.server.autofill.ViewState createOrUpdateViewStateLocked = createOrUpdateViewStateLocked(autofillId, i, null);
                if (!z) {
                    createOrUpdateViewStateLocked.setResponse(fillResponse, z2);
                } else {
                    createOrUpdateViewStateLocked.setResponse(null, z2);
                }
            }
        }
        android.service.autofill.SaveInfo saveInfo = fillResponse.getSaveInfo();
        if (saveInfo != null) {
            android.view.autofill.AutofillId[] requiredIds = saveInfo.getRequiredIds();
            if (requiredIds != null) {
                for (android.view.autofill.AutofillId autofillId2 : requiredIds) {
                    createOrUpdateViewStateLocked(autofillId2, i, null);
                }
            }
            android.view.autofill.AutofillId[] optionalIds = saveInfo.getOptionalIds();
            if (optionalIds != null) {
                for (android.view.autofill.AutofillId autofillId3 : optionalIds) {
                    createOrUpdateViewStateLocked(autofillId3, i, null);
                }
            }
        }
        android.view.autofill.AutofillId[] authenticationIds = fillResponse.getAuthenticationIds();
        if (authenticationIds != null) {
            for (android.view.autofill.AutofillId autofillId4 : authenticationIds) {
                createOrUpdateViewStateLocked(autofillId4, i, null);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setViewStatesLocked(@android.annotation.Nullable android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.service.autofill.Dataset dataset, int i, boolean z, boolean z2) {
        java.util.ArrayList fieldIds = dataset.getFieldIds();
        java.util.ArrayList fieldValues = dataset.getFieldValues();
        for (int i2 = 0; i2 < fieldIds.size(); i2++) {
            com.android.server.autofill.ViewState createOrUpdateViewStateLocked = createOrUpdateViewStateLocked((android.view.autofill.AutofillId) fieldIds.get(i2), i, (android.view.autofill.AutofillValue) fieldValues.get(i2));
            java.lang.String id = dataset.getId();
            if (id != null) {
                createOrUpdateViewStateLocked.setDatasetId(id);
            }
            if (z) {
                createOrUpdateViewStateLocked.setResponse(null, z2);
            } else if (fillResponse != null) {
                createOrUpdateViewStateLocked.setResponse(fillResponse, z2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.autofill.ViewState createOrUpdateViewStateLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId, int i, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue) {
        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
        if (viewState != null) {
            viewState.setState(i);
        } else {
            viewState = new com.android.server.autofill.ViewState(autofillId, this, i, this.mIsPrimaryCredential);
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "Adding autofillable view with id " + autofillId + " and state " + i);
            }
            viewState.setCurrentValue(findValueLocked(autofillId));
            this.mViewStates.put(autofillId, viewState);
        }
        if ((i & 4) != 0) {
            viewState.setAutofilledValue(autofillValue);
        }
        return viewState;
    }

    void autoFill(int i, int i2, android.service.autofill.Dataset dataset, boolean z, int i3) {
        android.content.Intent createAuthFillInIntentLocked;
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "autoFill(): requestId=" + i + "; datasetIdx=" + i2 + "; dataset=" + dataset);
        }
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    android.util.Slog.w(TAG, "Call to Session#autoFill() rejected - session: " + this.id + " destroyed");
                    return;
                }
                this.mPresentationStatsEventLogger.maybeSetSelectedDatasetId(i2);
                this.mPresentationStatsEventLogger.maybeSetSelectedDatasetPickReason(dataset.getEligibleReason());
                if (dataset.getAuthentication() == null) {
                    if (z) {
                        this.mService.logDatasetSelected(dataset.getId(), this.id, this.mClientState, i3);
                    }
                    if (this.mCurrentViewId != null) {
                        this.mInlineSessionController.hideInlineSuggestionsUiLocked(this.mCurrentViewId);
                    }
                    autoFillApp(dataset);
                    return;
                }
                this.mService.logDatasetAuthenticationSelected(dataset.getId(), this.id, this.mClientState, i3);
                this.mPresentationStatsEventLogger.maybeSetAuthenticationType(1);
                setViewStatesLocked(null, dataset, 64, false, true);
                if (dataset.getCredentialFillInIntent() != null && android.service.autofill.Flags.autofillCredmanIntegration()) {
                    android.util.Slog.d(TAG, "Setting credential fill intent");
                    createAuthFillInIntentLocked = dataset.getCredentialFillInIntent();
                } else {
                    createAuthFillInIntentLocked = createAuthFillInIntentLocked(i, this.mClientState);
                }
                if (createAuthFillInIntentLocked == null) {
                    forceRemoveFromServiceLocked();
                } else {
                    startAuthentication(android.view.autofill.AutofillManager.makeAuthenticationId(i, i2), dataset.getAuthentication(), createAuthFillInIntentLocked, false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.Intent createAuthFillInIntentLocked(int i, android.os.Bundle bundle) {
        android.content.Intent intent = new android.content.Intent();
        android.service.autofill.FillContext fillContextByRequestIdLocked = getFillContextByRequestIdLocked(i);
        if (fillContextByRequestIdLocked == null) {
            wtf(null, "createAuthFillInIntentLocked(): no FillContext. requestId=%d; mContexts=%s", java.lang.Integer.valueOf(i), this.mContexts);
            return null;
        }
        if (this.mLastInlineSuggestionsRequest != null && ((java.lang.Integer) this.mLastInlineSuggestionsRequest.first).intValue() == i) {
            intent.putExtra("android.view.autofill.extra.INLINE_SUGGESTIONS_REQUEST", (android.os.Parcelable) this.mLastInlineSuggestionsRequest.second);
        }
        intent.putExtra("android.view.autofill.extra.ASSIST_STRUCTURE", fillContextByRequestIdLocked.getStructure());
        intent.putExtra("android.view.autofill.extra.CLIENT_STATE", bundle);
        return intent;
    }

    @android.annotation.NonNull
    java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> inlineSuggestionsRequestCacheDecorator(@android.annotation.NonNull final java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> consumer, final int i) {
        return new java.util.function.Consumer() { // from class: com.android.server.autofill.Session$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.Session.this.lambda$inlineSuggestionsRequestCacheDecorator$1(consumer, i, (android.view.inputmethod.InlineSuggestionsRequest) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inlineSuggestionsRequestCacheDecorator$1(java.util.function.Consumer consumer, int i, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest) {
        consumer.accept(inlineSuggestionsRequest);
        synchronized (this.mLock) {
            this.mLastInlineSuggestionsRequest = android.util.Pair.create(java.lang.Integer.valueOf(i), inlineSuggestionsRequest);
        }
    }

    private int getDetectionPreferenceForLogging() {
        if (this.mService.isPccClassificationEnabled()) {
            if (this.mService.getMaster().preferProviderOverPcc()) {
                return 1;
            }
            return 2;
        }
        return 0;
    }

    private void startNewEventForPresentationStatsEventLogger() {
        synchronized (this.mLock) {
            this.mPresentationStatsEventLogger.startNewEvent();
            this.mPresentationStatsEventLogger.maybeSetDetectionPreference(getDetectionPreferenceForLogging());
            this.mPresentationStatsEventLogger.maybeSetAutofillServiceUid(getAutofillServiceUid());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAuthentication(int i, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) {
        try {
            synchronized (this.mLock) {
                this.mClient.authenticate(this.id, i, intentSender, intent, z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error launching auth intent", e);
        }
    }

    static final class SaveResult {
        private boolean mLogSaveShown;
        private boolean mRemoveSession;
        private int mSaveDialogNotShowReason;

        SaveResult(boolean z, boolean z2, int i) {
            this.mLogSaveShown = z;
            this.mRemoveSession = z2;
            this.mSaveDialogNotShowReason = i;
        }

        public boolean isLogSaveShown() {
            return this.mLogSaveShown;
        }

        public void setLogSaveShown(boolean z) {
            this.mLogSaveShown = z;
        }

        public boolean isRemoveSession() {
            return this.mRemoveSession;
        }

        public void setRemoveSession(boolean z) {
            this.mRemoveSession = z;
        }

        public int getNoSaveUiReason() {
            return this.mSaveDialogNotShowReason;
        }

        public void setSaveDialogNotShowReason(int i) {
            this.mSaveDialogNotShowReason = i;
        }

        public java.lang.String toString() {
            return "SaveResult: [logSaveShown=" + this.mLogSaveShown + ", removeSession=" + this.mRemoveSession + ", saveDialogNotShowReason=" + this.mSaveDialogNotShowReason + "]";
        }
    }

    private static final class ClassificationState {
        private static final int STATE_INITIAL = 1;
        private static final int STATE_INVALIDATED = 5;
        private static final int STATE_PENDING_ASSIST_REQUEST = 2;
        private static final int STATE_PENDING_REQUEST = 3;
        private static final int STATE_RESPONSE = 4;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<android.view.autofill.AutofillId, java.util.Set<java.lang.String>> mClassificationCombinedHintsMap;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<android.view.autofill.AutofillId, java.util.Set<java.lang.String>> mClassificationGroupHintsMap;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<android.view.autofill.AutofillId, java.util.Set<java.lang.String>> mClassificationHintsMap;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<java.lang.String, java.util.Set<android.view.autofill.AutofillId>> mGroupHintsToAutofillIdMap;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<java.lang.String, java.util.Set<android.view.autofill.AutofillId>> mHintsToAutofillIdMap;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.service.assist.classification.FieldClassificationResponse mLastFieldClassificationResponse;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.service.assist.classification.FieldClassificationRequest mPendingFieldClassificationRequest;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private int mState;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface ClassificationRequestState {
        }

        private ClassificationState() {
            this.mState = 1;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.lang.String stateToString() {
            switch (this.mState) {
                case 1:
                    return "STATE_INITIAL";
                case 2:
                    return "STATE_PENDING_ASSIST_REQUEST";
                case 3:
                    return "STATE_PENDING_REQUEST";
                case 4:
                    return "STATE_RESPONSE";
                case 5:
                    return "STATE_INVALIDATED";
                default:
                    return "UNKNOWN_CLASSIFICATION_STATE_" + this.mState;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean processResponse() {
            if (this.mClassificationHintsMap != null && !this.mClassificationHintsMap.isEmpty()) {
                return true;
            }
            android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse = this.mLastFieldClassificationResponse;
            if (fieldClassificationResponse == null) {
                return false;
            }
            this.mClassificationHintsMap = new android.util.ArrayMap<>();
            this.mClassificationGroupHintsMap = new android.util.ArrayMap<>();
            this.mHintsToAutofillIdMap = new android.util.ArrayMap<>();
            this.mGroupHintsToAutofillIdMap = new android.util.ArrayMap<>();
            this.mClassificationCombinedHintsMap = new android.util.ArrayMap<>();
            for (android.service.assist.classification.FieldClassification fieldClassification : fieldClassificationResponse.getClassifications()) {
                android.view.autofill.AutofillId autofillId = fieldClassification.getAutofillId();
                java.util.Set<java.lang.String> hints = fieldClassification.getHints();
                java.util.Set<java.lang.String> groupHints = fieldClassification.getGroupHints();
                android.util.ArraySet arraySet = new android.util.ArraySet(hints);
                this.mClassificationHintsMap.put(autofillId, hints);
                if (groupHints != null) {
                    this.mClassificationGroupHintsMap.put(autofillId, groupHints);
                    arraySet.addAll(groupHints);
                }
                this.mClassificationCombinedHintsMap.put(autofillId, arraySet);
                processDetections(hints, autofillId, this.mHintsToAutofillIdMap);
                processDetections(groupHints, autofillId, this.mGroupHintsToAutofillIdMap);
            }
            return true;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private static void processDetections(java.util.Set<java.lang.String> set, android.view.autofill.AutofillId autofillId, android.util.ArrayMap<java.lang.String, java.util.Set<android.view.autofill.AutofillId>> arrayMap) {
            java.util.Set<android.view.autofill.AutofillId> arraySet;
            for (java.lang.String str : set) {
                if (arrayMap.containsKey(str)) {
                    arraySet = arrayMap.get(str);
                } else {
                    arraySet = new android.util.ArraySet<>();
                }
                arraySet.add(autofillId);
                arrayMap.put(str, arraySet);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void invalidateState() {
            this.mState = 5;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void updatePendingAssistData() {
            this.mState = 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updatePendingRequest() {
            this.mState = 3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateResponseReceived(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) {
            this.mState = 4;
            this.mLastFieldClassificationResponse = fieldClassificationResponse;
            this.mPendingFieldClassificationRequest = null;
            processResponse();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onAssistStructureReceived(android.app.assist.AssistStructure assistStructure) {
            this.mState = 3;
            this.mPendingFieldClassificationRequest = new android.service.assist.classification.FieldClassificationRequest(assistStructure);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onFieldClassificationRequestSent() {
            this.mState = 3;
            this.mPendingFieldClassificationRequest = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean shouldTriggerRequest() {
            return this.mState == 1 || this.mState == 5;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public java.lang.String toString() {
            return "ClassificationState: [state=" + stateToString() + ", mPendingFieldClassificationRequest=" + this.mPendingFieldClassificationRequest + ", mLastFieldClassificationResponse=" + this.mLastFieldClassificationResponse + ", mClassificationHintsMap=" + this.mClassificationHintsMap + ", mClassificationGroupHintsMap=" + this.mClassificationGroupHintsMap + ", mHintsToAutofillIdMap=" + this.mHintsToAutofillIdMap + ", mGroupHintsToAutofillIdMap=" + this.mGroupHintsToAutofillIdMap + "]";
        }
    }

    public java.lang.String toString() {
        return "Session: [id=" + this.id + ", component=" + this.mComponentName + ", state=" + sessionStateAsString(this.mSessionState) + "]";
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("id: ");
        printWriter.println(this.id);
        printWriter.print(str);
        printWriter.print("uid: ");
        printWriter.println(this.uid);
        printWriter.print(str);
        printWriter.print("taskId: ");
        printWriter.println(this.taskId);
        printWriter.print(str);
        printWriter.print("flags: ");
        printWriter.println(this.mFlags);
        printWriter.print(str);
        printWriter.print("displayId: ");
        printWriter.println(this.mContext.getDisplayId());
        printWriter.print(str);
        printWriter.print("state: ");
        printWriter.println(sessionStateAsString(this.mSessionState));
        printWriter.print(str);
        printWriter.print("mComponentName: ");
        printWriter.println(this.mComponentName);
        printWriter.print(str);
        printWriter.print("mActivityToken: ");
        printWriter.println(this.mActivityToken);
        printWriter.print(str);
        printWriter.print("mStartTime: ");
        printWriter.println(this.mStartTime);
        printWriter.print(str);
        printWriter.print("Time to show UI: ");
        if (this.mUiShownTime == 0) {
            printWriter.println("N/A");
        } else {
            android.util.TimeUtils.formatDuration(this.mUiShownTime - this.mStartTime, printWriter);
            printWriter.println();
        }
        int size = this.mRequestLogs.size();
        printWriter.print(str);
        printWriter.print("mSessionLogs: ");
        printWriter.println(size);
        for (int i = 0; i < size; i++) {
            int keyAt = this.mRequestLogs.keyAt(i);
            android.metrics.LogMaker valueAt = this.mRequestLogs.valueAt(i);
            printWriter.print(str2);
            printWriter.print('#');
            printWriter.print(i);
            printWriter.print(": req=");
            printWriter.print(keyAt);
            printWriter.print(", log=");
            dumpRequestLog(printWriter, valueAt);
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("mResponses: ");
        if (this.mResponses == null) {
            printWriter.println("null");
        } else {
            printWriter.println(this.mResponses.size());
            for (int i2 = 0; i2 < this.mResponses.size(); i2++) {
                printWriter.print(str2);
                printWriter.print('#');
                printWriter.print(i2);
                printWriter.print(' ');
                printWriter.println(this.mResponses.valueAt(i2));
            }
        }
        printWriter.print(str);
        printWriter.print("mCurrentViewId: ");
        printWriter.println(this.mCurrentViewId);
        printWriter.print(str);
        printWriter.print("mDestroyed: ");
        printWriter.println(this.mDestroyed);
        printWriter.print(str);
        printWriter.print("mShowingSaveUi: ");
        printWriter.println(this.mSessionFlags.mShowingSaveUi);
        printWriter.print(str);
        printWriter.print("mPendingSaveUi: ");
        printWriter.println(this.mPendingSaveUi);
        int size2 = this.mViewStates.size();
        printWriter.print(str);
        printWriter.print("mViewStates size: ");
        printWriter.println(this.mViewStates.size());
        for (int i3 = 0; i3 < size2; i3++) {
            printWriter.print(str);
            printWriter.print("ViewState at #");
            printWriter.println(i3);
            this.mViewStates.valueAt(i3).dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.print("mContexts: ");
        if (this.mContexts != null) {
            int size3 = this.mContexts.size();
            for (int i4 = 0; i4 < size3; i4++) {
                android.service.autofill.FillContext fillContext = this.mContexts.get(i4);
                printWriter.print(str2);
                printWriter.print(fillContext);
                if (com.android.server.autofill.Helper.sVerbose) {
                    printWriter.println("AssistStructure dumped at logcat)");
                    fillContext.getStructure().dump(false);
                }
            }
        } else {
            printWriter.println("null");
        }
        printWriter.print(str);
        printWriter.print("mHasCallback: ");
        printWriter.println(this.mHasCallback);
        if (this.mClientState != null) {
            printWriter.print(str);
            printWriter.print("mClientState: ");
            printWriter.print(this.mClientState.getSize());
            printWriter.println(" bytes");
        }
        printWriter.print(str);
        printWriter.print("mCompatMode: ");
        printWriter.println(this.mCompatMode);
        printWriter.print(str);
        printWriter.print("mUrlBar: ");
        if (this.mUrlBar == null) {
            printWriter.println("N/A");
        } else {
            printWriter.print("id=");
            printWriter.print(this.mUrlBar.getAutofillId());
            printWriter.print(" domain=");
            printWriter.print(this.mUrlBar.getWebDomain());
            printWriter.print(" text=");
            com.android.server.autofill.Helper.printlnRedactedText(printWriter, this.mUrlBar.getText());
        }
        printWriter.print(str);
        printWriter.print("mSaveOnAllViewsInvisible: ");
        printWriter.println(this.mSaveOnAllViewsInvisible);
        printWriter.print(str);
        printWriter.print("mSelectedDatasetIds: ");
        printWriter.println(this.mSelectedDatasetIds);
        if (this.mSessionFlags.mAugmentedAutofillOnly) {
            printWriter.print(str);
            printWriter.println("For Augmented Autofill Only");
        }
        if (this.mSessionFlags.mFillDialogDisabled) {
            printWriter.print(str);
            printWriter.println("Fill Dialog disabled");
        }
        if (this.mLastFillDialogTriggerIds != null) {
            printWriter.print(str);
            printWriter.println("Last Fill Dialog trigger ids: ");
            printWriter.println(this.mSelectedDatasetIds);
        }
        if (this.mAugmentedAutofillDestroyer != null) {
            printWriter.print(str);
            printWriter.println("has mAugmentedAutofillDestroyer");
        }
        if (this.mAugmentedRequestsLogs != null) {
            printWriter.print(str);
            printWriter.print("number augmented requests: ");
            printWriter.println(this.mAugmentedRequestsLogs.size());
        }
        if (this.mAugmentedAutofillableIds != null) {
            printWriter.print(str);
            printWriter.print("mAugmentedAutofillableIds: ");
            printWriter.println(this.mAugmentedAutofillableIds);
        }
        if (this.mRemoteFillService != null) {
            this.mRemoteFillService.dump(str, printWriter);
        }
    }

    private static void dumpRequestLog(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull android.metrics.LogMaker logMaker) {
        printWriter.print("CAT=");
        printWriter.print(logMaker.getCategory());
        printWriter.print(", TYPE=");
        int type = logMaker.getType();
        switch (type) {
            case 2:
                printWriter.print("CLOSE");
                break;
            case 10:
                printWriter.print("SUCCESS");
                break;
            case 11:
                printWriter.print("FAILURE");
                break;
            default:
                printWriter.print("UNSUPPORTED");
                break;
        }
        printWriter.print('(');
        printWriter.print(type);
        printWriter.print(')');
        printWriter.print(", PKG=");
        printWriter.print(logMaker.getPackageName());
        printWriter.print(", SERVICE=");
        printWriter.print(logMaker.getTaggedData(908));
        printWriter.print(", ORDINAL=");
        printWriter.print(logMaker.getTaggedData(1454));
        dumpNumericValue(printWriter, logMaker, "FLAGS", 1452);
        dumpNumericValue(printWriter, logMaker, "NUM_DATASETS", 909);
        dumpNumericValue(printWriter, logMaker, "UI_LATENCY", 1145);
        int numericValue = com.android.server.autofill.Helper.getNumericValue(logMaker, 1453);
        if (numericValue != 0) {
            printWriter.print(", AUTH_STATUS=");
            switch (numericValue) {
                case com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_720P_HD_ALMOST /* 912 */:
                    printWriter.print("AUTHENTICATED");
                    break;
                case 1126:
                    printWriter.print("DATASET_AUTHENTICATED");
                    break;
                case 1127:
                    printWriter.print("INVALID_DATASET_AUTHENTICATION");
                    break;
                case 1128:
                    printWriter.print("INVALID_AUTHENTICATION");
                    break;
                default:
                    printWriter.print("UNSUPPORTED");
                    break;
            }
            printWriter.print('(');
            printWriter.print(numericValue);
            printWriter.print(')');
        }
        dumpNumericValue(printWriter, logMaker, "FC_IDS", 1271);
        dumpNumericValue(printWriter, logMaker, "COMPAT_MODE", 1414);
    }

    private static void dumpNumericValue(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull android.metrics.LogMaker logMaker, @android.annotation.NonNull java.lang.String str, int i) {
        int numericValue = com.android.server.autofill.Helper.getNumericValue(logMaker, i);
        if (numericValue != 0) {
            printWriter.print(", ");
            printWriter.print(str);
            printWriter.print('=');
            printWriter.print(numericValue);
        }
    }

    void sendCredentialManagerResponseToApp(@android.annotation.Nullable android.credentials.GetCredentialResponse getCredentialResponse, @android.annotation.Nullable android.credentials.GetCredentialException getCredentialException, @android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                android.util.Slog.w(TAG, "Call to Session#sendCredentialManagerResponseToApp() rejected - session: " + this.id + " destroyed");
                return;
            }
            try {
                com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
                if (this.mService.getMaster().getIsFillFieldsFromCurrentSessionOnly() && viewState != null && viewState.id.getSessionId() != this.id && com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Skipping sending credential response to view: " + autofillId + " as it isn't part of the current session: " + this.id);
                }
                if (getCredentialException != null) {
                    this.mClient.onGetCredentialException(this.id, autofillId, getCredentialException.getType(), getCredentialException.getMessage());
                } else if (getCredentialResponse != null) {
                    this.mClient.onGetCredentialResponse(this.id, autofillId, getCredentialResponse);
                } else {
                    android.util.Slog.w(TAG, "sendCredentialManagerResponseToApp called with null responseand exception");
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Error sending credential response to activity: " + e);
            }
        }
    }

    void autoFillApp(android.service.autofill.Dataset dataset) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                android.util.Slog.w(TAG, "Call to Session#autoFillApp() rejected - session: " + this.id + " destroyed");
                return;
            }
            try {
                int size = dataset.getFieldIds().size();
                java.util.ArrayList arrayList = new java.util.ArrayList(size);
                java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
                if (size == 1 && ((android.view.autofill.AutofillId) dataset.getFieldIds().get(0)).equals(this.mCurrentViewId)) {
                    z = true;
                } else {
                    z = false;
                }
                boolean z2 = false;
                for (int i = 0; i < size; i++) {
                    if (dataset.getFieldValues().get(i) != null) {
                        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) dataset.getFieldIds().get(i);
                        com.android.server.autofill.ViewState viewState = this.mViewStates.get(autofillId);
                        if (this.mService.getMaster().getIsFillFieldsFromCurrentSessionOnly() && viewState != null && viewState.id.getSessionId() != this.id) {
                            if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(TAG, "Skipping filling view: " + autofillId + " as it isn't part of the current session: " + this.id);
                            }
                        } else {
                            arrayList.add(autofillId);
                            arrayList2.add((android.view.autofill.AutofillValue) dataset.getFieldValues().get(i));
                            if (viewState != null && (viewState.getState() & 64) != 0) {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "autofillApp(): view " + autofillId + " waiting auth");
                                }
                                viewState.resetState(64);
                                z2 = true;
                            }
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    if (z2) {
                        this.mUi.hideFillUi(this);
                    }
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "autoFillApp(): the buck is on the app: " + dataset);
                    }
                    this.mClient.autofill(this.id, arrayList, arrayList2, z);
                    if (dataset.getId() != null) {
                        if (this.mSelectedDatasetIds == null) {
                            this.mSelectedDatasetIds = new java.util.ArrayList<>();
                        }
                        this.mSelectedDatasetIds.add(dataset.getId());
                    }
                    setViewStatesLocked(null, dataset, 4, false, true);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Error autofilling activity: " + e);
            }
        }
    }

    private com.android.server.autofill.ui.AutoFillUI getUiForShowing() {
        com.android.server.autofill.ui.AutoFillUI autoFillUI;
        synchronized (this.mLock) {
            this.mUi.setCallback(this);
            autoFillUI = this.mUi;
        }
        return autoFillUI;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logAllEvents(int i) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "logAllEvents(" + this.id + "): commitReason: " + i);
        }
        this.mSessionCommittedEventLogger.maybeSetCommitReason(i);
        this.mSessionCommittedEventLogger.maybeSetRequestCount(this.mRequestCount);
        this.mSessionCommittedEventLogger.maybeSetSessionDurationMillis(android.os.SystemClock.elapsedRealtime() - this.mStartTime);
        this.mFillRequestEventLogger.logAndEndEvent();
        this.mFillResponseEventLogger.logAndEndEvent();
        this.mPresentationStatsEventLogger.logAndEndEvent();
        this.mSaveEventLogger.logAndEndEvent();
        this.mSessionCommittedEventLogger.logAndEndEvent();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.autofill.RemoteFillService destroyLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "destroyLocked for session: " + this.id);
        }
        logAllEvents(5);
        if (this.mDestroyed) {
            return null;
        }
        clearPendingIntentLocked();
        unregisterDelayedFillBroadcastLocked();
        unlinkClientVultureLocked();
        this.mUi.destroyAll(this.mPendingSaveUi, this, true);
        this.mUi.clearCallback(this);
        if (this.mCurrentViewId != null) {
            this.mInlineSessionController.destroyLocked(this.mCurrentViewId);
        }
        com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked = this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (remoteInlineSuggestionRenderServiceLocked != null) {
            remoteInlineSuggestionRenderServiceLocked.destroySuggestionViews(this.userId, this.id);
        }
        this.mDestroyed = true;
        int size = this.mRequestLogs.size();
        if (size > 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "destroyLocked(): logging " + size + " requests");
            }
            for (int i = 0; i < size; i++) {
                this.mMetricsLogger.write(this.mRequestLogs.valueAt(i));
            }
        }
        int size2 = this.mAugmentedRequestsLogs == null ? 0 : this.mAugmentedRequestsLogs.size();
        if (size2 > 0) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "destroyLocked(): logging " + size + " augmented requests");
            }
            for (int i2 = 0; i2 < size2; i2++) {
                this.mMetricsLogger.write(this.mAugmentedRequestsLogs.get(i2));
            }
        }
        android.metrics.LogMaker addTaggedData = newLogMaker(919).addTaggedData(1455, java.lang.Integer.valueOf(size));
        if (size2 > 0) {
            addTaggedData.addTaggedData(1631, java.lang.Integer.valueOf(size2));
        }
        if (this.mSessionFlags.mAugmentedAutofillOnly) {
            addTaggedData.addTaggedData(1720, 1);
        }
        this.mMetricsLogger.write(addTaggedData);
        return this.mRemoteFillService;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void forceRemoveFromServiceLocked() {
        forceRemoveFromServiceLocked(0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void forceRemoveFromServiceIfForAugmentedOnlyLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "forceRemoveFromServiceIfForAugmentedOnlyLocked(" + this.id + "): " + this.mSessionFlags.mAugmentedAutofillOnly);
        }
        if (this.mSessionFlags.mAugmentedAutofillOnly) {
            forceRemoveFromServiceLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void forceRemoveFromServiceLocked(int i) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "forceRemoveFromServiceLocked(): " + this.mPendingSaveUi);
        }
        boolean isSaveUiPendingLocked = isSaveUiPendingLocked();
        this.mPendingSaveUi = null;
        removeFromServiceLocked();
        this.mUi.destroyAll(this.mPendingSaveUi, this, false);
        if (!isSaveUiPendingLocked) {
            try {
                this.mClient.setSessionFinished(i, (java.util.List) null);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error notifying client to finish session", e);
            }
        }
        destroyAugmentedAutofillWindowsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void destroyAugmentedAutofillWindowsLocked() {
        if (this.mAugmentedAutofillDestroyer != null) {
            this.mAugmentedAutofillDestroyer.run();
            this.mAugmentedAutofillDestroyer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromService() {
        synchronized (this.mLock) {
            removeFromServiceLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeFromServiceLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "removeFromServiceLocked(" + this.id + "): " + this.mPendingSaveUi);
        }
        if (this.mDestroyed) {
            android.util.Slog.w(TAG, "Call to Session#removeFromServiceLocked() rejected - session: " + this.id + " destroyed");
            return;
        }
        if (isSaveUiPendingLocked()) {
            android.util.Slog.i(TAG, "removeFromServiceLocked() ignored, waiting for pending save ui");
            return;
        }
        com.android.server.autofill.RemoteFillService destroyLocked = destroyLocked();
        this.mService.removeSessionLocked(this.id);
        if (destroyLocked != null) {
            destroyLocked.destroy();
        }
        if (this.mSecondaryProviderHandler != null) {
            this.mSecondaryProviderHandler.destroy();
        }
        this.mSessionState = 3;
    }

    void onPendingSaveUi(int i, @android.annotation.NonNull android.os.IBinder iBinder) {
        getUiForShowing().onPendingSaveUi(i, iBinder);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isSaveUiPendingForTokenLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        return isSaveUiPendingLocked() && iBinder.equals(this.mPendingSaveUi.getToken());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isSaveUiPendingLocked() {
        return this.mPendingSaveUi != null && this.mPendingSaveUi.getState() == 2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getLastResponseIndexLocked() {
        int i = -1;
        if (this.mResponses != null) {
            int size = this.mResponses.size();
            int i2 = -1;
            for (int i3 = 0; i3 < size; i3++) {
                if (this.mResponses.keyAt(i3) > i2) {
                    i2 = this.mResponses.keyAt(i3);
                    i = i3;
                }
            }
        }
        return i;
    }

    private android.metrics.LogMaker newLogMaker(int i) {
        return newLogMaker(i, this.mService.getServicePackageName());
    }

    private android.metrics.LogMaker newLogMaker(int i, java.lang.String str) {
        return com.android.server.autofill.Helper.newLogMaker(i, this.mComponentName, str, this.id, this.mCompatMode);
    }

    private void writeLog(int i) {
        this.mMetricsLogger.write(newLogMaker(i));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logAuthenticationStatusLocked(int i, int i2) {
        addTaggedDataToRequestLogLocked(i, 1453, java.lang.Integer.valueOf(i2));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addTaggedDataToRequestLogLocked(int i, int i2, @android.annotation.Nullable java.lang.Object obj) {
        android.metrics.LogMaker logMaker = this.mRequestLogs.get(i);
        if (logMaker == null) {
            android.util.Slog.w(TAG, "addTaggedDataToRequestLogLocked(tag=" + i2 + "): no log for id " + i);
            return;
        }
        logMaker.addTaggedData(i2, obj);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logAugmentedAutofillRequestLocked(int i, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, boolean z, java.lang.Boolean bool) {
        this.mService.getMaster().logRequestLocked("aug:id=" + this.id + " u=" + this.uid + " m=" + i + " a=" + android.content.ComponentName.flattenToShortString(this.mComponentName) + " f=" + autofillId + " s=" + componentName + " w=" + z + " i=" + bool);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wtf(@android.annotation.Nullable java.lang.Exception exc, java.lang.String str, java.lang.Object... objArr) {
        java.lang.String format = java.lang.String.format(str, objArr);
        synchronized (this.mLock) {
            this.mWtfHistory.log(format);
        }
        if (exc != null) {
            android.util.Slog.wtf(TAG, format, exc);
        } else {
            android.util.Slog.wtf(TAG, format);
        }
    }

    private static java.lang.String actionAsString(int i) {
        switch (i) {
            case 1:
                return "START_SESSION";
            case 2:
                return "VIEW_ENTERED";
            case 3:
                return "VIEW_EXITED";
            case 4:
                return "VALUE_CHANGED";
            case 5:
                return "RESPONSE_EXPIRED";
            default:
                return "UNKNOWN_" + i;
        }
    }

    private static java.lang.String sessionStateAsString(int i) {
        switch (i) {
            case 0:
                return "STATE_UNKNOWN";
            case 1:
                return "STATE_ACTIVE";
            case 2:
                return "STATE_FINISHED";
            case 3:
                return "STATE_REMOVED";
            default:
                return "UNKNOWN_SESSION_STATE_" + i;
        }
    }

    private int getAutofillServiceUid() {
        android.content.pm.ServiceInfo serviceInfo = this.mService.getServiceInfo();
        if (serviceInfo == null) {
            return -1;
        }
        return serviceInfo.applicationInfo.uid;
    }

    @Override // com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks
    public void onClassificationRequestSuccess(@android.annotation.Nullable android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) {
        this.mClassificationState.updateResponseReceived(fieldClassificationResponse);
    }

    @Override // com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks
    public void onClassificationRequestFailure(int i, @android.annotation.Nullable java.lang.CharSequence charSequence) {
    }

    @Override // com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks
    public void onClassificationRequestTimeout(int i) {
    }

    @Override // com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks
    public void onServiceDied(@android.annotation.NonNull com.android.server.autofill.RemoteFieldClassificationService remoteFieldClassificationService) {
        android.util.Slog.w(TAG, "removing session because service died");
        synchronized (this.mLock) {
        }
    }

    @Override // com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks
    public void logFieldClassificationEvent(long j, android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse, int i) {
        int i2;
        com.android.server.autofill.FieldClassificationEventLogger createLogger = com.android.server.autofill.FieldClassificationEventLogger.createLogger();
        createLogger.startNewLogForRequest();
        createLogger.maybeSetLatencyMillis(android.os.SystemClock.elapsedRealtime() - j);
        createLogger.maybeSetAppPackageUid(this.uid);
        createLogger.maybeSetNextFillRequestId(this.mFillRequestIdSnapshot + 1);
        createLogger.maybeSetRequestId(sIdCounterForPcc.get());
        createLogger.maybeSetSessionId(this.id);
        if (fieldClassificationResponse == null) {
            i2 = -1;
        } else {
            i2 = fieldClassificationResponse.getClassifications().size();
        }
        createLogger.maybeSetRequestStatus(i);
        createLogger.maybeSetCountClassifications(i2);
        createLogger.logAndEndEvent();
        this.mFillRequestIdSnapshot = -2;
    }
}
