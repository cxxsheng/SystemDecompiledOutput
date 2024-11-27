package android.view.autofill;

/* loaded from: classes4.dex */
public final class AutofillManager {
    public static final int ACTION_RESPONSE_EXPIRED = 5;
    public static final int ACTION_START_SESSION = 1;
    public static final int ACTION_VALUE_CHANGED = 4;
    public static final int ACTION_VIEW_ENTERED = 2;
    public static final int ACTION_VIEW_EXITED = 3;
    public static final java.lang.String ANY_HINT = "any";
    private static final int AUTHENTICATION_ID_DATASET_ID_MASK = 65535;
    private static final int AUTHENTICATION_ID_DATASET_ID_SHIFT = 16;
    public static final int AUTHENTICATION_ID_DATASET_ID_UNDEFINED = 65535;
    public static final int COMMIT_REASON_ACTIVITY_FINISHED = 1;
    public static final int COMMIT_REASON_SESSION_DESTROYED = 5;
    public static final int COMMIT_REASON_UNKNOWN = 0;
    public static final int COMMIT_REASON_VIEW_CHANGED = 4;
    public static final int COMMIT_REASON_VIEW_CLICKED = 3;
    public static final int COMMIT_REASON_VIEW_COMMITTED = 2;
    public static final int DEFAULT_LOGGING_LEVEL;
    public static final int DEFAULT_MAX_PARTITIONS_SIZE = 10;
    public static final java.lang.String EXTRA_ASSIST_STRUCTURE = "android.view.autofill.extra.ASSIST_STRUCTURE";
    public static final java.lang.String EXTRA_AUGMENTED_AUTOFILL_CLIENT = "android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT";
    public static final java.lang.String EXTRA_AUTHENTICATION_RESULT = "android.view.autofill.extra.AUTHENTICATION_RESULT";
    public static final java.lang.String EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET = "android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET";
    public static final java.lang.String EXTRA_AUTH_STATE = "android.view.autofill.extra.AUTH_STATE";
    public static final java.lang.String EXTRA_AUTOFILL_REQUEST_ID = "android.view.autofill.extra.AUTOFILL_REQUEST_ID";
    public static final java.lang.String EXTRA_CLIENT_STATE = "android.view.autofill.extra.CLIENT_STATE";
    public static final java.lang.String EXTRA_INLINE_SUGGESTIONS_REQUEST = "android.view.autofill.extra.INLINE_SUGGESTIONS_REQUEST";
    public static final java.lang.String EXTRA_RESTORE_CROSS_ACTIVITY = "android.view.autofill.extra.RESTORE_CROSS_ACTIVITY";
    public static final java.lang.String EXTRA_RESTORE_SESSION_TOKEN = "android.view.autofill.extra.RESTORE_SESSION_TOKEN";
    public static final int FC_SERVICE_TIMEOUT = 5000;
    public static final int FLAG_ADD_CLIENT_DEBUG = 2;
    public static final int FLAG_ADD_CLIENT_ENABLED = 1;
    public static final int FLAG_ADD_CLIENT_ENABLED_FOR_AUGMENTED_AUTOFILL_ONLY = 8;
    public static final int FLAG_ADD_CLIENT_VERBOSE = 4;
    public static final int FLAG_SMART_SUGGESTION_OFF = 0;
    public static final int FLAG_SMART_SUGGESTION_SYSTEM = 1;
    private static final java.lang.String LAST_AUTOFILLED_DATA_TAG = "android:lastAutoFilledData";
    public static final int MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS = 120000;
    public static final int NO_LOGGING = 0;
    public static final int NO_SESSION = Integer.MAX_VALUE;
    public static final int PENDING_UI_OPERATION_CANCEL = 1;
    public static final int PENDING_UI_OPERATION_RESTORE = 2;
    public static final int RECEIVER_FLAG_SESSION_FOR_AUGMENTED_AUTOFILL_ONLY = 1;
    public static final int RESULT_CODE_NOT_SERVICE = -1;
    public static final int RESULT_OK = 0;
    private static final java.lang.String SESSION_ID_TAG = "android:sessionId";
    public static final int SET_STATE_FLAG_DEBUG = 8;
    public static final int SET_STATE_FLAG_ENABLED = 1;
    public static final int SET_STATE_FLAG_FOR_AUTOFILL_ONLY = 32;
    public static final int SET_STATE_FLAG_RESET_CLIENT = 4;
    public static final int SET_STATE_FLAG_RESET_SESSION = 2;
    public static final int SET_STATE_FLAG_VERBOSE = 16;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED_BY_SERVICE = 4;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_PENDING_AUTHENTICATION = 7;
    public static final int STATE_SHOWING_SAVE_UI = 3;
    private static final java.lang.String STATE_TAG = "android:state";
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_UNKNOWN_COMPAT_MODE = 5;
    public static final int STATE_UNKNOWN_FAILED = 6;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final java.lang.String TAG = "AutofillManager";
    private java.util.Set<java.lang.String> mAllowedActivitySet;
    private android.view.autofill.IAugmentedAutofillManagerClient mAugmentedAutofillServiceClient;
    private android.view.autofill.AutofillManager.AutofillCallback mCallback;
    private android.view.autofill.AutofillManager.CompatibilityBridge mCompatibilityBridge;
    private final android.content.Context mContext;
    private java.util.Set<java.lang.String> mDeniedActivitySet;
    private boolean mEnabled;
    private boolean mEnabledForAugmentedAutofillOnly;
    private java.util.Set<android.view.autofill.AutofillId> mEnteredForAugmentedAutofillIds;
    private android.util.ArraySet<android.view.autofill.AutofillId> mEnteredIds;
    private java.util.List<android.view.autofill.AutofillId> mFillDialogTriggerIds;
    private android.util.ArraySet<android.view.autofill.AutofillId> mFillableIds;
    private boolean mForAugmentedAutofillOnly;
    private android.view.autofill.AutofillId mIdShownFillUi;
    private final boolean mIsCredmanIntegrationEnabled;
    private boolean mIsPackageFullyAllowedForAutofill;
    private boolean mIsPackageFullyDeniedForAutofill;
    private boolean mIsPackagePartiallyAllowedForAutofill;
    private boolean mIsPackagePartiallyDeniedForAutofill;
    private boolean mIsTriggerFillRequestOnFilteredImportantViewsEnabled;
    private boolean mIsTriggerFillRequestOnUnimportantViewEnabled;
    private android.view.autofill.ParcelableMap mLastAutofilledData;
    private java.util.Set<java.lang.String> mNonAutofillableImeActionIdSet;
    private boolean mOnInvisibleCalled;
    private final android.content.AutofillOptions mOptions;
    private boolean mRelayoutFix;
    private boolean mSaveOnFinish;
    private android.view.autofill.AutofillId mSaveTriggerId;
    private boolean mScreenHasCredmanField;
    private final android.view.autofill.IAutoFillManager mService;
    private android.view.autofill.IAutoFillManagerClient mServiceClient;
    private sun.misc.Cleaner mServiceClientCleaner;
    private boolean mShouldAlwaysIncludeWebviewInAssistStructure;
    private boolean mShouldEnableAutofillOnAllViewTypes;
    private boolean mShouldEnableMultilineFilter;
    private boolean mShouldIncludeAllChildrenViewInAssistStructure;
    private boolean mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure;
    private android.view.autofill.AutofillManager.TrackedViews mTrackedViews;
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private final java.lang.Object mLock = new java.lang.Object();
    private int mSessionId = Integer.MAX_VALUE;
    private int mState = 0;
    private boolean mShowAutofillDialogCalled = false;
    private boolean mShouldIgnoreCredentialViews = false;
    private final android.util.ArraySet<android.view.autofill.AutofillId> mAllTrackedViews = new android.util.ArraySet<>();
    private java.util.concurrent.atomic.AtomicBoolean mIsFillRequested = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final boolean mIsFillDialogEnabled = android.view.autofill.AutofillFeatureFlags.isFillDialogEnabled();
    private final java.lang.String[] mFillDialogEnabledHints = android.view.autofill.AutofillFeatureFlags.getFillDialogEnabledHints();
    private final boolean mIsFillAndSaveDialogDisabledForCredentialManager = android.view.autofill.AutofillFeatureFlags.isFillAndSaveDialogDisabledForCredentialManager();

    public interface AutofillClient {
        void autofillClientAuthenticate(int i, android.content.IntentSender intentSender, android.content.Intent intent, boolean z);

        void autofillClientDispatchUnhandledKey(android.view.View view, android.view.KeyEvent keyEvent);

        android.view.View autofillClientFindViewByAccessibilityIdTraversal(int i, int i2);

        android.view.View autofillClientFindViewByAutofillIdTraversal(android.view.autofill.AutofillId autofillId);

        android.view.View[] autofillClientFindViewsByAutofillIdTraversal(android.view.autofill.AutofillId[] autofillIdArr);

        android.os.IBinder autofillClientGetActivityToken();

        android.content.ComponentName autofillClientGetComponentName();

        android.view.autofill.AutofillId autofillClientGetNextAutofillId();

        boolean[] autofillClientGetViewVisibility(android.view.autofill.AutofillId[] autofillIdArr);

        boolean autofillClientIsCompatibilityModeEnabled();

        boolean autofillClientIsFillUiShowing();

        boolean autofillClientIsVisibleForAutofill();

        boolean autofillClientRequestHideFillUi();

        boolean autofillClientRequestShowFillUi(android.view.View view, int i, int i2, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter);

        void autofillClientResetableStateAvailable();

        void autofillClientRunOnUiThread(java.lang.Runnable runnable);

        boolean isDisablingEnterExitEventForAutofill();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutofillCommitReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SmartSuggestionMode {
    }

    static {
        int i;
        if (android.os.Build.IS_ENG) {
            i = 2;
        } else {
            i = 0;
        }
        DEFAULT_LOGGING_LEVEL = i;
    }

    public static int makeAuthenticationId(int i, int i2) {
        return (i << 16) | (i2 & 65535);
    }

    public static int getRequestIdFromAuthenticationId(int i) {
        return i >> 16;
    }

    public static int getDatasetIdFromAuthenticationId(int i) {
        return i & 65535;
    }

    public AutofillManager(android.content.Context context, android.view.autofill.IAutoFillManager iAutoFillManager) {
        this.mIsTriggerFillRequestOnUnimportantViewEnabled = false;
        this.mNonAutofillableImeActionIdSet = new android.util.ArraySet();
        this.mIsPackageFullyDeniedForAutofill = false;
        this.mIsPackagePartiallyDeniedForAutofill = false;
        this.mDeniedActivitySet = new android.util.ArraySet();
        this.mIsPackageFullyAllowedForAutofill = false;
        this.mIsPackagePartiallyAllowedForAutofill = false;
        this.mAllowedActivitySet = new android.util.ArraySet();
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context, "context cannot be null");
        this.mService = iAutoFillManager;
        this.mOptions = context.getAutofillOptions();
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d("AutofillManager", "Fill dialog is enabled:" + this.mIsFillDialogEnabled + ", hints=" + java.util.Arrays.toString(this.mFillDialogEnabledHints));
        }
        if (this.mOptions != null) {
            android.view.autofill.Helper.sDebug = (this.mOptions.loggingLevel & 2) != 0;
            android.view.autofill.Helper.sVerbose = (this.mOptions.loggingLevel & 4) != 0;
        }
        this.mIsTriggerFillRequestOnUnimportantViewEnabled = android.view.autofill.AutofillFeatureFlags.isTriggerFillRequestOnUnimportantViewEnabled();
        this.mIsTriggerFillRequestOnFilteredImportantViewsEnabled = android.view.autofill.AutofillFeatureFlags.isTriggerFillRequestOnFilteredImportantViewsEnabled();
        this.mShouldEnableAutofillOnAllViewTypes = android.view.autofill.AutofillFeatureFlags.shouldEnableAutofillOnAllViewTypes();
        this.mNonAutofillableImeActionIdSet = android.view.autofill.AutofillFeatureFlags.getNonAutofillableImeActionIdSetFromFlag();
        this.mShouldEnableMultilineFilter = android.view.autofill.AutofillFeatureFlags.shouldEnableMultilineFilter();
        java.lang.String denylistStringFromFlag = android.view.autofill.AutofillFeatureFlags.getDenylistStringFromFlag();
        java.lang.String allowlistStringFromFlag = android.view.autofill.AutofillFeatureFlags.getAllowlistStringFromFlag();
        java.lang.String packageName = this.mContext.getPackageName();
        this.mIsPackageFullyDeniedForAutofill = isPackageFullyAllowedOrDeniedForAutofill(denylistStringFromFlag, packageName);
        this.mIsPackageFullyAllowedForAutofill = isPackageFullyAllowedOrDeniedForAutofill(allowlistStringFromFlag, packageName);
        if (!this.mIsPackageFullyDeniedForAutofill) {
            this.mIsPackagePartiallyDeniedForAutofill = isPackagePartiallyDeniedOrAllowedForAutofill(denylistStringFromFlag, packageName);
        }
        if (!this.mIsPackageFullyAllowedForAutofill) {
            this.mIsPackagePartiallyAllowedForAutofill = isPackagePartiallyDeniedOrAllowedForAutofill(allowlistStringFromFlag, packageName);
        }
        if (this.mIsPackagePartiallyDeniedForAutofill) {
            this.mDeniedActivitySet = getDeniedOrAllowedActivitySetFromString(denylistStringFromFlag, packageName);
        }
        if (this.mIsPackagePartiallyAllowedForAutofill) {
            this.mAllowedActivitySet = getDeniedOrAllowedActivitySetFromString(allowlistStringFromFlag, packageName);
        }
        this.mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure = android.view.autofill.AutofillFeatureFlags.shouldIncludeAllViewsAutofillTypeNotNoneInAssistStructrue();
        this.mShouldIncludeAllChildrenViewInAssistStructure = android.view.autofill.AutofillFeatureFlags.shouldIncludeAllChildrenViewInAssistStructure();
        this.mShouldAlwaysIncludeWebviewInAssistStructure = android.view.autofill.AutofillFeatureFlags.shouldAlwaysIncludeWebviewInAssistStructure();
        this.mRelayoutFix = android.service.autofill.Flags.relayout();
        this.mIsCredmanIntegrationEnabled = android.service.autofill.Flags.autofillCredmanIntegration();
    }

    public boolean isTriggerFillRequestOnFilteredImportantViewsEnabled() {
        return this.mIsTriggerFillRequestOnFilteredImportantViewsEnabled;
    }

    public boolean isTriggerFillRequestOnUnimportantViewEnabled() {
        return this.mIsTriggerFillRequestOnUnimportantViewEnabled;
    }

    private boolean isPassingImeActionCheck(android.widget.EditText editText) {
        if (this.mNonAutofillableImeActionIdSet.contains(java.lang.String.valueOf(editText.getImeOptions()))) {
            android.util.Log.d("AutofillManager", "view not autofillable - not passing ime action check");
            return false;
        }
        return true;
    }

    private boolean isPassingMultilineCheck(android.widget.EditText editText) {
        if (editText.getMinLines() <= 1) {
            return true;
        }
        android.util.Log.d("AutofillManager", "view not autofillable - has multiline input type");
        return false;
    }

    private boolean isPackageFullyAllowedOrDeniedForAutofill(java.lang.String str, java.lang.String str2) {
        return str.indexOf(new java.lang.StringBuilder().append(str2).append(":;").toString()) != -1;
    }

    private boolean isPackagePartiallyDeniedOrAllowedForAutofill(java.lang.String str, java.lang.String str2) {
        return str.indexOf(new java.lang.StringBuilder().append(str2).append(":").toString()) != -1;
    }

    public boolean shouldIncludeAllChildrenViewsWithAutofillTypeNotNoneInAssistStructure() {
        return this.mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure;
    }

    public boolean shouldIncludeAllChildrenViewInAssistStructure() {
        return this.mShouldIncludeAllChildrenViewInAssistStructure;
    }

    public boolean shouldAlwaysIncludeWebviewInAssistStructure() {
        return this.mShouldAlwaysIncludeWebviewInAssistStructure;
    }

    private java.util.Set<java.lang.String> getDeniedOrAllowedActivitySetFromString(java.lang.String str, java.lang.String str2) {
        int indexOf = str.indexOf(str2 + ":");
        int indexOf2 = str.indexOf(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, indexOf);
        int length = indexOf + str2.length() + 1;
        if (length >= indexOf2) {
            android.util.Log.e("AutofillManager", "Failed to get denied activity names from list because it's wrongly formatted");
            return new android.util.ArraySet();
        }
        return new android.util.ArraySet(java.util.Arrays.asList(str.substring(length, indexOf2).split(",")));
    }

    public boolean isActivityDeniedForAutofill() {
        android.view.autofill.AutofillManager.AutofillClient client;
        if (this.mIsPackageFullyDeniedForAutofill) {
            return true;
        }
        if (!this.mIsPackagePartiallyDeniedForAutofill || (client = getClient()) == null) {
            return false;
        }
        return this.mDeniedActivitySet.contains(client.autofillClientGetComponentName().flattenToShortString());
    }

    public boolean isActivityAllowedForAutofill() {
        android.view.autofill.AutofillManager.AutofillClient client;
        if (this.mIsPackageFullyAllowedForAutofill) {
            return true;
        }
        if (!this.mIsPackagePartiallyAllowedForAutofill || (client = getClient()) == null) {
            return false;
        }
        return this.mAllowedActivitySet.contains(client.autofillClientGetComponentName().flattenToShortString());
    }

    public boolean isAutofillable(android.view.View view) {
        if (view.getAutofillType() == 0) {
            return false;
        }
        if (!view.isImportantForAutofill() && isActivityDeniedForAutofill()) {
            android.util.Log.d("AutofillManager", "view is not autofillable - activity denied for autofill");
            return false;
        }
        if (isActivityAllowedForAutofill()) {
            android.util.Log.d("AutofillManager", "view is autofillable - activity allowed for autofill");
            return true;
        }
        if (view instanceof android.widget.EditText) {
            if (!this.mShouldEnableMultilineFilter || isPassingMultilineCheck((android.widget.EditText) view)) {
                return isPassingImeActionCheck((android.widget.EditText) view);
            }
            return false;
        }
        if (view.isImportantForAutofill() || this.mShouldEnableAutofillOnAllViewTypes || (view instanceof android.widget.CheckBox) || (view instanceof android.widget.Spinner) || (view instanceof android.widget.DatePicker) || (view instanceof android.widget.TimePicker) || (view instanceof android.widget.RadioGroup)) {
            return true;
        }
        android.util.Log.d("AutofillManager", "view is not autofillable - not important and filtered by view type check");
        return false;
    }

    public void enableCompatibilityMode() {
        synchronized (this.mLock) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Slog.d("AutofillManager", "creating CompatibilityBridge for " + this.mContext);
            }
            this.mCompatibilityBridge = new android.view.autofill.AutofillManager.CompatibilityBridge();
        }
    }

    public void onCreate(android.os.Bundle bundle) {
        boolean z;
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            this.mLastAutofilledData = (android.view.autofill.ParcelableMap) bundle.getParcelable(LAST_AUTOFILLED_DATA_TAG, android.view.autofill.ParcelableMap.class);
            if (isActiveLocked()) {
                android.util.Log.w("AutofillManager", "New session was started before onCreate()");
                return;
            }
            this.mSessionId = bundle.getInt(SESSION_ID_TAG, Integer.MAX_VALUE);
            this.mState = bundle.getInt(STATE_TAG, 0);
            if (this.mSessionId != Integer.MAX_VALUE) {
                boolean tryAddServiceClientIfNeededLocked = tryAddServiceClientIfNeededLocked();
                android.view.autofill.AutofillManager.AutofillClient client = getClient();
                if (client != null) {
                    com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
                    try {
                        if (tryAddServiceClientIfNeededLocked) {
                            this.mService.restoreSession(this.mSessionId, client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), syncResultReceiver);
                            int intResult = syncResultReceiver.getIntResult();
                            z = true;
                            if (intResult != 1) {
                                z = false;
                            }
                        } else {
                            android.util.Log.w("AutofillManager", "No service client for session " + this.mSessionId);
                            z = false;
                        }
                        if (!z) {
                            android.util.Log.w("AutofillManager", "Session " + this.mSessionId + " could not be restored");
                            this.mSessionId = Integer.MAX_VALUE;
                            this.mState = 0;
                        } else {
                            if (android.view.autofill.Helper.sDebug) {
                                android.util.Log.d("AutofillManager", "session " + this.mSessionId + " was restored");
                            }
                            client.autofillClientResetableStateAvailable();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e("AutofillManager", "Could not figure out if there was an autofill session", e);
                    } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
                        android.util.Log.e("AutofillManager", "Fail to get session restore status: " + e2);
                    }
                }
            }
        }
    }

    public void onVisibleForAutofill() {
        android.view.Choreographer.getInstance().postCallback(4, new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                android.view.autofill.AutofillManager.this.lambda$onVisibleForAutofill$0();
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisibleForAutofill$0() {
        synchronized (this.mLock) {
            if (this.mEnabled && isActiveLocked() && this.mTrackedViews != null) {
                this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }
    }

    public void onInvisibleForAutofill(boolean z) {
        synchronized (this.mLock) {
            this.mOnInvisibleCalled = true;
            if (z) {
                updateSessionLocked(null, null, null, 5, 0);
            }
        }
    }

    public void onSaveInstanceState(android.os.Bundle bundle) {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                bundle.putInt(SESSION_ID_TAG, this.mSessionId);
            }
            if (this.mState != 0) {
                bundle.putInt(STATE_TAG, this.mState);
            }
            if (this.mLastAutofilledData != null) {
                bundle.putParcelable(LAST_AUTOFILLED_DATA_TAG, this.mLastAutofilledData);
            }
        }
    }

    public boolean isCompatibilityModeEnabledLocked() {
        return this.mCompatibilityBridge != null;
    }

    public boolean isEnabled() {
        if (!hasAutofillFeature()) {
            return false;
        }
        synchronized (this.mLock) {
            if (isDisabledByServiceLocked()) {
                return false;
            }
            return tryAddServiceClientIfNeededLocked() ? this.mEnabled : false;
        }
    }

    public android.service.autofill.FillEventHistory getFillEventHistory() {
        try {
            com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
            this.mService.getFillEventHistory(syncResultReceiver);
            return (android.service.autofill.FillEventHistory) syncResultReceiver.getParcelableResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            android.util.Log.e("AutofillManager", "Fail to get fill event history: " + e2);
            return null;
        }
    }

    public void requestAutofill(android.view.View view) {
        int i;
        if (view.isFocused()) {
            i = 1;
        } else {
            i = 17;
        }
        notifyViewEntered(view, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void requestAutofillFromNewSession(android.view.View view) {
        cancel();
        notifyViewEntered(view);
    }

    public void requestAutofill(android.view.View view, int i, android.graphics.Rect rect) {
        int i2;
        if (view.isFocused()) {
            i2 = 1;
        } else {
            i2 = 17;
        }
        notifyViewEntered(view, i, rect, i2);
    }

    public void notifyViewEntered(android.view.View view) {
        notifyViewEntered(view, 0);
    }

    public void notifyVirtualViewsReady(android.view.View view, android.util.SparseArray<android.view.autofill.VirtualViewFillInfo> sparseArray) {
        java.util.Objects.requireNonNull(sparseArray);
        if (sparseArray.size() == 0) {
            throw new java.lang.IllegalArgumentException("No VirtualViewInfo found");
        }
        if (shouldSuppressDialogsForCredman(view) && this.mIsFillAndSaveDialogDisabledForCredentialManager) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d("AutofillManager", "Ignoring Fill Dialog request since important for credMan:" + view.getAutofillId().toString());
            }
            this.mScreenHasCredmanField = true;
        } else {
            for (int i = 0; i < sparseArray.size(); i++) {
                android.view.autofill.VirtualViewFillInfo valueAt = sparseArray.valueAt(i);
                notifyViewReadyInner(getAutofillId(view, sparseArray.keyAt(i)), valueAt == null ? null : valueAt.getAutofillHints());
            }
        }
    }

    public void notifyViewEnteredForFillDialog(android.view.View view) {
        if (shouldSuppressDialogsForCredman(view) && this.mIsFillAndSaveDialogDisabledForCredentialManager) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d("AutofillManager", "Ignoring Fill Dialog request since important for credMan:" + view.getAutofillId());
            }
            this.mScreenHasCredmanField = true;
            return;
        }
        notifyViewReadyInner(view.getAutofillId(), view.getAutofillHints());
    }

    private void notifyViewReadyInner(android.view.autofill.AutofillId autofillId, java.lang.String[] strArr) {
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d("AutofillManager", "notifyViewReadyInner:" + autofillId);
        }
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mAllTrackedViews.contains(autofillId)) {
                return;
            }
            this.mAllTrackedViews.add(autofillId);
            if (this.mTrackedViews != null) {
                this.mTrackedViews.checkViewState(autofillId);
            }
            if (this.mIsFillRequested.get()) {
                return;
            }
            if (android.view.autofill.AutofillFeatureFlags.isAutofillPccClassificationEnabled()) {
                synchronized (this.mLock) {
                    if (!isActiveLocked()) {
                        if (tryAddServiceClientIfNeededLocked()) {
                            startSessionLocked(android.view.autofill.AutofillId.NO_AUTOFILL_ID, null, null, 512);
                        } else if (android.view.autofill.Helper.sVerbose) {
                            android.util.Log.v("AutofillManager", "not starting session: no service client");
                        }
                    }
                }
            }
            boolean z = true;
            if (!this.mIsFillDialogEnabled) {
                if (strArr == null) {
                    z = false;
                } else {
                    int length = strArr.length;
                    int i = 0;
                    boolean z2 = false;
                    while (true) {
                        if (i >= length) {
                            z = z2;
                            break;
                        }
                        java.lang.String str = strArr[i];
                        java.lang.String[] strArr2 = this.mFillDialogEnabledHints;
                        int length2 = strArr2.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length2) {
                                break;
                            }
                            if (!strArr2[i2].equalsIgnoreCase(str)) {
                                i2++;
                            } else {
                                z2 = true;
                                break;
                            }
                        }
                        if (z2) {
                            z = z2;
                            break;
                        }
                        i++;
                    }
                }
            }
            if (z) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Log.d("AutofillManager", "Triggering pre-emptive request for fill dialog.");
                }
                synchronized (this.mLock) {
                    notifyViewEnteredLocked(null, android.view.autofill.AutofillId.NO_AUTOFILL_ID, null, null, 80);
                }
            }
        }
    }

    private boolean hasFillDialogUiFeature() {
        return this.mIsFillDialogEnabled || !com.android.internal.util.ArrayUtils.isEmpty(this.mFillDialogEnabledHints);
    }

    private int getImeStateFlag(android.view.View view) {
        android.view.WindowInsets rootWindowInsets;
        if (view == null || (rootWindowInsets = view.getRootWindowInsets()) == null || !rootWindowInsets.isVisible(android.view.WindowInsets.Type.ime())) {
            return 0;
        }
        return 128;
    }

    private boolean shouldIgnoreViewEnteredLocked(android.view.autofill.AutofillId autofillId, int i) {
        if (isDisabledByServiceLocked()) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "ignoring notifyViewEntered(flags=" + i + ", view=" + autofillId + ") on state " + getStateAsStringLocked() + " because disabled by svc");
            }
            return true;
        }
        if (isFinishedLocked() && (i & 1) == 0 && this.mEnteredIds != null && this.mEnteredIds.contains(autofillId)) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "ignoring notifyViewEntered(flags=" + i + ", view=" + autofillId + ") on state " + getStateAsStringLocked() + " because view was already entered: " + this.mEnteredIds);
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClientVisibleForAutofillLocked() {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        return client != null && client.autofillClientIsVisibleForAutofill();
    }

    private boolean isClientDisablingEnterExitEvent() {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        return client != null && client.isDisablingEnterExitEventForAutofill();
    }

    private void notifyViewEntered(android.view.View view, int i) {
        android.view.autofill.AutofillManager.AutofillCallback notifyViewEnteredLocked;
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            notifyViewEnteredLocked = notifyViewEnteredLocked(view, view.getAutofillId(), null, view.getAutofillValue(), i);
        }
        if (notifyViewEnteredLocked != null) {
            this.mCallback.onAutofillEvent(view, 3);
        }
    }

    public void notifyViewExited(android.view.View view) {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            notifyViewExitedLocked(view);
        }
    }

    void notifyViewExitedLocked(android.view.View view) {
        if (tryAddServiceClientIfNeededLocked()) {
            if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
                updateSessionLocked(view.getAutofillId(), null, null, 3, 0);
            }
        }
    }

    public void notifyViewVisibilityChanged(android.view.View view, boolean z) {
        notifyViewVisibilityChangedInternal(view, 0, z, false);
    }

    public void notifyViewVisibilityChanged(android.view.View view, int i, boolean z) {
        notifyViewVisibilityChangedInternal(view, i, z, true);
    }

    private void notifyViewVisibilityChangedInternal(android.view.View view, int i, boolean z, boolean z2) {
        synchronized (this.mLock) {
            if (this.mForAugmentedAutofillOnly) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "notifyViewVisibilityChanged(): ignoring on augmented only mode");
                }
                return;
            }
            if (this.mRelayoutFix && this.mState == 7) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "notifyViewVisibilityChanged(): ignoring in auth pending mode");
                }
                return;
            }
            if (this.mEnabled && isActiveLocked()) {
                android.view.autofill.AutofillId autofillId = z2 ? getAutofillId(view, i) : view.getAutofillId();
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "visibility changed for " + autofillId + ": " + z);
                }
                if (!z && this.mFillableIds != null && this.mFillableIds.contains(autofillId)) {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d("AutofillManager", "Hidding UI when view " + autofillId + " became invisible");
                    }
                    requestHideFillUi(autofillId, view);
                }
                if (this.mTrackedViews != null) {
                    this.mTrackedViews.notifyViewVisibilityChangedLocked(autofillId, z);
                } else if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "Ignoring visibility change on " + autofillId + ": no tracked views");
                }
            } else if (!z2 && z) {
                startAutofillIfNeededLocked(view);
            }
        }
    }

    public void notifyViewEntered(android.view.View view, int i, android.graphics.Rect rect) {
        notifyViewEntered(view, i, rect, 0);
    }

    private void notifyViewEntered(android.view.View view, int i, android.graphics.Rect rect, int i2) {
        android.view.autofill.AutofillManager.AutofillCallback notifyViewEnteredLocked;
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            notifyViewEnteredLocked = notifyViewEnteredLocked(view, getAutofillId(view, i), rect, null, i2);
        }
        if (notifyViewEnteredLocked != null) {
            notifyViewEnteredLocked.onAutofillEvent(view, i, 3);
        }
    }

    private android.view.autofill.AutofillManager.AutofillCallback notifyViewEnteredLocked(android.view.View view, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i) {
        int i2;
        if (shouldIgnoreViewEnteredLocked(autofillId, i)) {
            return null;
        }
        if (!tryAddServiceClientIfNeededLocked()) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "ignoring notifyViewEntered(" + autofillId + "): no service client");
            }
            return null;
        }
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "ignoring notifyViewEntered(" + autofillId + "): disabled");
            }
            return this.mCallback;
        }
        if (this.mIsCredmanIntegrationEnabled && isCredmanRequested(view)) {
            i |= 2048;
        }
        this.mIsFillRequested.set(true);
        if (!isClientDisablingEnterExitEvent()) {
            if ((view instanceof android.widget.TextView) && ((android.widget.TextView) view).isAnyPasswordInputType()) {
                i |= 4;
            }
            if (android.view.autofill.AutofillFeatureFlags.isFillAndSaveDialogDisabledForCredentialManager() && this.mScreenHasCredmanField) {
                i |= 1024;
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "updating session with flag screen has credman view");
                }
            }
            int imeStateFlag = getImeStateFlag(view) | i;
            if (!isActiveLocked()) {
                startSessionLocked(autofillId, rect, autofillValue, imeStateFlag);
            } else {
                if (this.mForAugmentedAutofillOnly && (imeStateFlag & 1) != 0) {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d("AutofillManager", "notifyViewEntered(" + autofillId + "): resetting mForAugmentedAutofillOnly on manual request");
                    }
                    this.mForAugmentedAutofillOnly = false;
                }
                if ((imeStateFlag & 64) == 0) {
                    i2 = imeStateFlag;
                } else {
                    i2 = imeStateFlag | 256;
                }
                updateSessionLocked(autofillId, rect, autofillValue, 2, i2);
            }
            addEnteredIdLocked(autofillId);
        }
        return null;
    }

    private void addEnteredIdLocked(android.view.autofill.AutofillId autofillId) {
        if (this.mEnteredIds == null) {
            this.mEnteredIds = new android.util.ArraySet<>(1);
        }
        autofillId.resetSessionId();
        this.mEnteredIds.add(autofillId);
    }

    public void notifyViewExited(android.view.View view, int i) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "notifyViewExited(" + view.getAutofillId() + ", " + i);
        }
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            notifyViewExitedLocked(view, i);
        }
    }

    private void notifyViewExitedLocked(android.view.View view, int i) {
        if (tryAddServiceClientIfNeededLocked()) {
            if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
                updateSessionLocked(getAutofillId(view, i), null, null, 3, 0);
            }
        }
    }

    public void notifyValueChanged(android.view.View view) {
        android.view.autofill.AutofillValue autofillValue;
        android.view.autofill.AutofillValue autofillValue2;
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            android.view.autofill.AutofillId autofillId = null;
            boolean z = false;
            if (this.mLastAutofilledData == null) {
                view.setAutofilled(false, false);
                autofillValue = null;
            } else {
                android.view.autofill.AutofillId autofillId2 = view.getAutofillId();
                if (this.mLastAutofilledData.containsKey(autofillId2)) {
                    android.view.autofill.AutofillValue autofillValue3 = view.getAutofillValue();
                    boolean z2 = this.mLastAutofilledData.keySet().size() == 1;
                    if (java.util.Objects.equals(this.mLastAutofilledData.get(autofillId2), autofillValue3)) {
                        view.setAutofilled(true, z2);
                    } else {
                        view.setAutofilled(false, false);
                        this.mLastAutofilledData.remove(autofillId2);
                    }
                    z = true;
                    autofillId = autofillId2;
                    autofillValue = autofillValue3;
                } else {
                    view.setAutofilled(false, false);
                    autofillId = autofillId2;
                    autofillValue = null;
                }
            }
            if (this.mEnabled && isActiveLocked()) {
                if (autofillId == null) {
                    autofillId = view.getAutofillId();
                }
                if (z) {
                    autofillValue2 = autofillValue;
                } else {
                    autofillValue2 = view.getAutofillValue();
                }
                updateSessionLocked(autofillId, null, autofillValue2, 4, getImeStateFlag(view));
                return;
            }
            if (!startAutofillIfNeededLocked(view) && android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "notifyValueChanged(" + view.getAutofillId() + "): ignoring on state " + getStateAsStringLocked());
            }
        }
    }

    public void notifyValueChanged(android.view.View view, int i, android.view.autofill.AutofillValue autofillValue) {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mEnabled && isActiveLocked()) {
                updateSessionLocked(getAutofillId(view, i), null, autofillValue, 4, getImeStateFlag(view));
                return;
            }
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "notifyValueChanged(" + view.getAutofillId() + ":" + i + "): ignoring on state " + getStateAsStringLocked());
            }
        }
    }

    public void notifyViewClicked(android.view.View view) {
        notifyViewClicked(view.getAutofillId());
    }

    public void notifyViewClicked(android.view.View view, int i) {
        notifyViewClicked(getAutofillId(view, i));
    }

    private void notifyViewClicked(android.view.autofill.AutofillId autofillId) {
        if (!hasAutofillFeature()) {
            return;
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "notifyViewClicked(): id=" + autofillId + ", trigger=" + this.mSaveTriggerId);
        }
        synchronized (this.mLock) {
            if (this.mEnabled && isActiveLocked()) {
                if (this.mSaveTriggerId != null && this.mSaveTriggerId.equals(autofillId)) {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d("AutofillManager", "triggering commit by click of " + autofillId);
                    }
                    commitLocked(3);
                    this.mMetricsLogger.write(newLog(com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SAVE_EXPLICITLY_TRIGGERED));
                }
            }
        }
    }

    public void onActivityFinishing() {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSaveOnFinish) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Log.d("AutofillManager", "onActivityFinishing(): calling commitLocked()");
                }
                commitLocked(1);
            } else {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Log.d("AutofillManager", "onActivityFinishing(): calling cancelLocked()");
                }
                cancelLocked();
            }
        }
    }

    public void commit() {
        if (!hasAutofillFeature()) {
            return;
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "commit() called by app");
        }
        synchronized (this.mLock) {
            commitLocked(2);
        }
    }

    private void commitLocked(int i) {
        if (!this.mEnabled && !isActiveLocked()) {
            return;
        }
        finishSessionLocked(i);
    }

    public void cancel() {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "cancel() called by app or augmented autofill service");
        }
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            cancelLocked();
        }
    }

    private void cancelLocked() {
        if (!this.mEnabled && !isActiveLocked()) {
            return;
        }
        cancelSessionLocked();
    }

    public void disableOwnedAutofillServices() {
        disableAutofillServices();
    }

    public void disableAutofillServices() {
        if (!hasAutofillFeature()) {
            return;
        }
        try {
            this.mService.disableOwnedAutofillServices(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasEnabledAutofillServices() {
        if (this.mService == null) {
            return false;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.isServiceEnabled(this.mContext.getUserId(), this.mContext.getPackageName(), syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get enabled autofill services status. " + e2);
        }
    }

    public android.content.ComponentName getAutofillServiceComponentName() {
        if (this.mService == null) {
            return null;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.getAutofillServiceComponentName(syncResultReceiver);
            return (android.content.ComponentName) syncResultReceiver.getParcelableResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get autofill services component name. " + e2);
        }
    }

    public java.lang.String getUserDataId() {
        try {
            com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
            this.mService.getUserDataId(syncResultReceiver);
            return syncResultReceiver.getStringResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get user data id for field classification. " + e2);
        }
    }

    public android.service.autofill.UserData getUserData() {
        try {
            com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
            this.mService.getUserData(syncResultReceiver);
            return (android.service.autofill.UserData) syncResultReceiver.getParcelableResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get user data for field classification. " + e2);
        }
    }

    public void setUserData(android.service.autofill.UserData userData) {
        try {
            this.mService.setUserData(userData);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFieldClassificationEnabled() {
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.isFieldClassificationEnabled(syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get field classification enabled status. " + e2);
        }
    }

    public java.lang.String getDefaultFieldClassificationAlgorithm() {
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.getDefaultFieldClassificationAlgorithm(syncResultReceiver);
            return syncResultReceiver.getStringResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get default field classification algorithm. " + e2);
        }
    }

    public java.util.List<java.lang.String> getAvailableFieldClassificationAlgorithms() {
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.getAvailableFieldClassificationAlgorithms(syncResultReceiver);
            java.lang.String[] stringArrayResult = syncResultReceiver.getStringArrayResult();
            return stringArrayResult != null ? java.util.Arrays.asList(stringArrayResult) : java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get available field classification algorithms. " + e2);
        }
    }

    public boolean isAutofillSupported() {
        if (this.mService == null) {
            return false;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.isServiceSupported(this.mContext.getUserId(), syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get autofill supported status. " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.autofill.AutofillManager.AutofillClient getClient() {
        android.view.autofill.AutofillManager.AutofillClient autofillClient = this.mContext.getAutofillClient();
        if (autofillClient == null && android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "No AutofillClient for " + this.mContext.getPackageName() + " on context " + this.mContext);
        }
        return autofillClient;
    }

    public boolean isAutofillUiShowing() {
        android.view.autofill.AutofillManager.AutofillClient autofillClient = this.mContext.getAutofillClient();
        return autofillClient != null && autofillClient.autofillClientIsFillUiShowing();
    }

    public boolean shouldIgnoreCredentialViews() {
        return this.mShouldIgnoreCredentialViews;
    }

    public void onAuthenticationResult(int i, android.content.Intent intent, android.view.View view) {
        android.os.Parcelable parcelable;
        if (!hasAutofillFeature()) {
            return;
        }
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d("AutofillManager", "onAuthenticationResult(): id= " + i + ", data=" + intent);
        }
        synchronized (this.mLock) {
            if (!isActiveLocked()) {
                android.util.Log.w("AutofillManager", "onAuthenticationResult(): sessionId=" + this.mSessionId + " not active");
                return;
            }
            this.mState = 1;
            if (!this.mOnInvisibleCalled && view != null && view.canNotifyAutofillEnterExitEvent()) {
                notifyViewExitedLocked(view);
                notifyViewEnteredLocked(view, view.getAutofillId(), null, view.getAutofillValue(), 0);
            }
            if (intent == null) {
                android.util.Log.i("AutofillManager", "onAuthenticationResult(): empty intent");
                return;
            }
            if (intent.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT) != null) {
                parcelable = intent.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT);
            } else if (intent.getParcelableExtra(android.service.credentials.CredentialProviderService.EXTRA_GET_CREDENTIAL_RESPONSE) != null && android.service.autofill.Flags.autofillCredmanIntegration()) {
                parcelable = intent.getParcelableExtra(android.service.credentials.CredentialProviderService.EXTRA_GET_CREDENTIAL_RESPONSE);
            } else {
                parcelable = null;
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(EXTRA_AUTHENTICATION_RESULT, parcelable);
            java.io.Serializable serializableExtra = intent.getSerializableExtra(android.service.credentials.CredentialProviderService.EXTRA_GET_CREDENTIAL_EXCEPTION, android.credentials.GetCredentialException.class);
            if (serializableExtra != null && android.service.autofill.Flags.autofillCredmanIntegration()) {
                bundle.putSerializable(android.service.credentials.CredentialProviderService.EXTRA_GET_CREDENTIAL_EXCEPTION, serializableExtra);
            }
            android.os.Bundle bundleExtra = intent.getBundleExtra(EXTRA_CLIENT_STATE);
            if (bundleExtra != null) {
                bundle.putBundle(EXTRA_CLIENT_STATE, bundleExtra);
            }
            if (intent.hasExtra(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET)) {
                bundle.putBoolean(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET, intent.getBooleanExtra(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET, false));
            }
            try {
                this.mService.setAuthenticationResult(bundle, this.mSessionId, i, this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                android.util.Log.e("AutofillManager", "Error delivering authentication result", e);
            }
        }
    }

    public android.view.autofill.AutofillId getNextAutofillId() {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client == null) {
            return null;
        }
        android.view.autofill.AutofillId autofillClientGetNextAutofillId = client.autofillClientGetNextAutofillId();
        if (autofillClientGetNextAutofillId == null && android.view.autofill.Helper.sDebug) {
            android.util.Log.d("AutofillManager", "getNextAutofillId(): client " + client + " returned null");
        }
        return autofillClientGetNextAutofillId;
    }

    private static android.view.autofill.AutofillId getAutofillId(android.view.View view, int i) {
        return new android.view.autofill.AutofillId(view.getAutofillViewId(), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01c7 A[Catch: TimeoutException -> 0x01fb, RemoteException -> 0x0213, TryCatch #2 {RemoteException -> 0x0213, TimeoutException -> 0x01fb, blocks: (B:35:0x0111, B:38:0x0118, B:41:0x012a, B:43:0x012e, B:45:0x0136, B:47:0x0140, B:49:0x0144, B:50:0x018a, B:53:0x01a4, B:55:0x01c7, B:56:0x01c9, B:58:0x01d3, B:60:0x01d7, B:61:0x01f5, B:62:0x01f7, B:65:0x0162, B:67:0x0166, B:68:0x0180), top: B:34:0x0111 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d3 A[Catch: TimeoutException -> 0x01fb, RemoteException -> 0x0213, TryCatch #2 {RemoteException -> 0x0213, TimeoutException -> 0x01fb, blocks: (B:35:0x0111, B:38:0x0118, B:41:0x012a, B:43:0x012e, B:45:0x0136, B:47:0x0140, B:49:0x0144, B:50:0x018a, B:53:0x01a4, B:55:0x01c7, B:56:0x01c9, B:58:0x01d3, B:60:0x01d7, B:61:0x01f5, B:62:0x01f7, B:65:0x0162, B:67:0x0166, B:68:0x0180), top: B:34:0x0111 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void startSessionLocked(android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i) {
        int i2;
        int i3;
        if ((this.mEnteredForAugmentedAutofillIds == null || !this.mEnteredForAugmentedAutofillIds.contains(autofillId)) && !this.mEnabledForAugmentedAutofillOnly) {
            i2 = i;
        } else {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "Starting session for augmented autofill on " + autofillId);
            }
            i2 = i | 8;
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "startSessionLocked(): id=" + autofillId + ", bounds=" + rect + ", value=" + autofillValue + ", flags=" + i2 + ", state=" + getStateAsStringLocked() + ", compatMode=" + isCompatibilityModeEnabledLocked() + ", augmentedOnly=" + this.mForAugmentedAutofillOnly + ", enabledAugmentedOnly=" + this.mEnabledForAugmentedAutofillOnly + ", enteredIds=" + this.mEnteredIds);
        }
        if (this.mForAugmentedAutofillOnly && !this.mEnabledForAugmentedAutofillOnly && (i2 & 1) != 0) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "resetting mForAugmentedAutofillOnly on manual autofill request");
            }
            this.mForAugmentedAutofillOnly = false;
        }
        if (this.mState != 0 && !isFinishedLocked() && (i2 & 1) == 0) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "not automatically starting session for " + autofillId + " on state " + getStateAsStringLocked() + " and flags " + i2);
                return;
            }
            return;
        }
        try {
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
            android.content.ComponentName autofillClientGetComponentName = client.autofillClientGetComponentName();
            if (!this.mEnabledForAugmentedAutofillOnly && this.mOptions != null && this.mOptions.isAutofillDisabledLocked(autofillClientGetComponentName)) {
                if (this.mOptions.isAugmentedAutofillEnabled(this.mContext)) {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d("AutofillManager", "startSession(" + autofillClientGetComponentName + "): disabled by service but allowlisted for augmented autofill");
                        i3 = i2 | 8;
                        this.mService.startSession(client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), autofillId, rect, autofillValue, this.mContext.getUserId(), this.mCallback == null, i3, autofillClientGetComponentName, isCompatibilityModeEnabledLocked(), syncResultReceiver);
                        this.mSessionId = syncResultReceiver.getIntResult();
                        if (this.mSessionId != Integer.MAX_VALUE) {
                            this.mState = 1;
                        }
                        if ((syncResultReceiver.getOptionalExtraIntResult(0) & 1) != 0) {
                            if (android.view.autofill.Helper.sDebug) {
                                android.util.Log.d("AutofillManager", "startSession(" + autofillClientGetComponentName + "): for augmented only");
                            }
                            this.mForAugmentedAutofillOnly = true;
                        }
                        client.autofillClientResetableStateAvailable();
                    }
                } else {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d("AutofillManager", "startSession(" + autofillClientGetComponentName + "): ignored because disabled by service and not allowlisted for augmented autofill");
                    }
                    setSessionFinished(4, null);
                    client.autofillClientResetableStateAvailable();
                    return;
                }
            }
            i3 = i2;
            this.mService.startSession(client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), autofillId, rect, autofillValue, this.mContext.getUserId(), this.mCallback == null, i3, autofillClientGetComponentName, isCompatibilityModeEnabledLocked(), syncResultReceiver);
            this.mSessionId = syncResultReceiver.getIntResult();
            if (this.mSessionId != Integer.MAX_VALUE) {
            }
            if ((syncResultReceiver.getOptionalExtraIntResult(0) & 1) != 0) {
            }
            client.autofillClientResetableStateAvailable();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            android.util.Log.w("AutofillManager", "Exception getting result from SyncResultReceiver: " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishSessionLocked(int i) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "finishSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.finishSession(this.mSessionId, this.mContext.getUserId(), i);
                resetSessionLocked(true);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void cancelSessionLocked() {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "cancelSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.cancelSession(this.mSessionId, this.mContext.getUserId());
                resetSessionLocked(true);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void resetSessionLocked(boolean z) {
        this.mSessionId = Integer.MAX_VALUE;
        this.mState = 0;
        this.mTrackedViews = null;
        this.mFillableIds = null;
        this.mSaveTriggerId = null;
        this.mIdShownFillUi = null;
        this.mIsFillRequested.set(false);
        this.mShowAutofillDialogCalled = false;
        this.mFillDialogTriggerIds = null;
        this.mScreenHasCredmanField = false;
        this.mAllTrackedViews.clear();
        if (z) {
            this.mEnteredIds = null;
        }
    }

    private void updateSessionLocked(android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, int i2) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "updateSessionLocked(): id=" + autofillId + ", bounds=" + rect + ", value=" + autofillValue + ", action=" + i + ", flags=" + i2);
        }
        try {
            this.mService.updateSession(this.mSessionId, autofillId, rect, autofillValue, i, i2, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean tryAddServiceClientIfNeededLocked() {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client == null) {
            return false;
        }
        if (this.mService == null) {
            android.util.Log.w("AutofillManager", "Autofill service is null!");
            return false;
        }
        if (this.mServiceClient == null) {
            this.mServiceClient = new android.view.autofill.AutofillManager.AutofillManagerClient();
            try {
                final int userId = this.mContext.getUserId();
                com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
                this.mService.addClient(this.mServiceClient, client.autofillClientGetComponentName(), userId, syncResultReceiver);
                try {
                    int intResult = syncResultReceiver.getIntResult();
                    this.mEnabled = (intResult & 1) != 0;
                    android.view.autofill.Helper.sDebug = (intResult & 2) != 0;
                    android.view.autofill.Helper.sVerbose = (intResult & 4) != 0;
                    this.mEnabledForAugmentedAutofillOnly = (intResult & 8) != 0;
                    if (android.view.autofill.Helper.sVerbose) {
                        android.util.Log.v("AutofillManager", "receiver results: flags=" + intResult + " enabled=" + this.mEnabled + ", enabledForAugmentedOnly: " + this.mEnabledForAugmentedAutofillOnly);
                    }
                    final android.view.autofill.IAutoFillManager iAutoFillManager = this.mService;
                    final android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient = this.mServiceClient;
                    this.mServiceClientCleaner = sun.misc.Cleaner.create(this, new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.autofill.IAutoFillManager.this.removeClient(iAutoFillManagerClient, userId);
                        }
                    });
                } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e) {
                    android.util.Log.w("AutofillManager", "Failed to initialize autofill: " + e);
                    this.mService.removeClient(this.mServiceClient, userId);
                    this.mServiceClient = null;
                    return false;
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        return true;
    }

    private boolean startAutofillIfNeededLocked(android.view.View view) {
        if (this.mState == 0 && this.mSessionId == Integer.MAX_VALUE && (view instanceof android.widget.EditText) && !android.text.TextUtils.isEmpty(((android.widget.EditText) view).getText()) && !view.isFocused() && view.isImportantForAutofill() && view.isLaidOut() && view.isVisibleToUser()) {
            boolean tryAddServiceClientIfNeededLocked = tryAddServiceClientIfNeededLocked();
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "startAutofillIfNeededLocked(): enabled=" + this.mEnabled + " mServiceClient=" + this.mServiceClient);
            }
            if (tryAddServiceClientIfNeededLocked && this.mEnabled && !isClientDisablingEnterExitEvent()) {
                android.view.autofill.AutofillId autofillId = view.getAutofillId();
                android.view.autofill.AutofillValue autofillValue = view.getAutofillValue();
                startSessionLocked(autofillId, null, null, 0);
                updateSessionLocked(autofillId, null, autofillValue, 4, 0);
                addEnteredIdLocked(autofillId);
                return true;
            }
        }
        return false;
    }

    public void registerCallback(android.view.autofill.AutofillManager.AutofillCallback autofillCallback) {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (autofillCallback == null) {
                return;
            }
            boolean z = this.mCallback != null;
            this.mCallback = autofillCallback;
            if (!z) {
                try {
                    this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), true);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterCallback(android.view.autofill.AutofillManager.AutofillCallback autofillCallback) {
        if (!hasAutofillFeature()) {
            return;
        }
        synchronized (this.mLock) {
            if (autofillCallback != null) {
                if (this.mCallback != null && autofillCallback == this.mCallback) {
                    this.mCallback = null;
                    try {
                        this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), false);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void setAugmentedAutofillWhitelist(java.util.Set<java.lang.String> set, java.util.Set<android.content.ComponentName> set2) {
        if (!hasAutofillFeature()) {
            return;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.setAugmentedAutofillWhitelist(android.view.autofill.Helper.toList(set), android.view.autofill.Helper.toList(set2), syncResultReceiver);
            int intResult = syncResultReceiver.getIntResult();
            switch (intResult) {
                case -1:
                    throw new java.lang.SecurityException("caller is not user's Augmented Autofill Service");
                case 0:
                    return;
                default:
                    android.util.Log.wtf("AutofillManager", "setAugmentedAutofillWhitelist(): received invalid result: " + intResult);
                    return;
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            android.util.Log.e("AutofillManager", "Fail to get the result of set AugmentedAutofill whitelist. " + e2);
        }
    }

    public void notifyViewEnteredForAugmentedAutofill(android.view.View view) {
        android.view.autofill.AutofillId autofillId = view.getAutofillId();
        synchronized (this.mLock) {
            if (this.mEnteredForAugmentedAutofillIds == null) {
                this.mEnteredForAugmentedAutofillIds = new android.util.ArraySet(1);
            }
            this.mEnteredForAugmentedAutofillIds.add(autofillId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
        android.view.autofill.AutofillManager.AutofillCallback autofillCallback;
        android.view.autofill.AutofillManager.AutofillClient client;
        android.view.View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId == i && (client = getClient()) != null && client.autofillClientRequestShowFillUi(findView, i2, i3, rect, iAutofillWindowPresenter)) {
                autofillCallback = this.mCallback;
                this.mIdShownFillUi = autofillId;
            } else {
                autofillCallback = null;
            }
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(findView, autofillId.getVirtualChildIntId(), 1);
            } else {
                autofillCallback.onAutofillEvent(findView, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void authenticate(int i, int i2, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) {
        synchronized (this.mLock) {
            if (i == this.mSessionId) {
                if (this.mRelayoutFix) {
                    this.mState = 7;
                }
                android.view.autofill.AutofillManager.AutofillClient client = getClient();
                if (client != null) {
                    this.mOnInvisibleCalled = false;
                    client.autofillClientAuthenticate(i2, intentSender, intent, z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUnhandledKey(int i, android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent) {
        android.view.autofill.AutofillManager.AutofillClient client;
        android.view.View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId == i && (client = getClient()) != null) {
                client.autofillClientDispatchUnhandledKey(findView, keyEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "setState(" + i + ": " + android.util.DebugUtils.flagsToString(android.view.autofill.AutofillManager.class, "SET_STATE_FLAG_", i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        synchronized (this.mLock) {
            try {
                if ((i & 32) != 0) {
                    this.mForAugmentedAutofillOnly = true;
                    return;
                }
                this.mEnabled = (i & 1) != 0;
                if (!this.mEnabled || (i & 2) != 0) {
                    resetSessionLocked(true);
                }
                if ((i & 4) != 0) {
                    this.mServiceClient = null;
                    this.mAugmentedAutofillServiceClient = null;
                    if (this.mServiceClientCleaner != null) {
                        this.mServiceClientCleaner.clean();
                        this.mServiceClientCleaner = null;
                    }
                    notifyReenableAutofill();
                }
                android.view.autofill.Helper.sDebug = (i & 8) != 0;
                android.view.autofill.Helper.sVerbose = (i & 16) != 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setAutofilledIfValuesIs(android.view.View view, android.view.autofill.AutofillValue autofillValue, boolean z) {
        if (java.util.Objects.equals(view.getAutofillValue(), autofillValue)) {
            synchronized (this.mLock) {
                if (this.mLastAutofilledData == null) {
                    this.mLastAutofilledData = new android.view.autofill.ParcelableMap(1);
                }
                this.mLastAutofilledData.put(view.getAutofillId(), autofillValue);
            }
            view.setAutofilled(true, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetCredentialException(int i, android.view.autofill.AutofillId autofillId, java.lang.String str, java.lang.String str2) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                android.util.Log.w("AutofillManager", "onGetCredentialException afm sessionIds don't match");
                return;
            }
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client == null) {
                android.util.Log.w("AutofillManager", "onGetCredentialException afm client id null");
                return;
            }
            java.util.ArrayList<android.view.autofill.AutofillId> arrayList = new java.util.ArrayList<>();
            android.view.View[] autofillClientFindViewsByAutofillIdTraversal = client.autofillClientFindViewsByAutofillIdTraversal(android.view.autofill.Helper.toArray(new java.util.ArrayList(java.util.Collections.singleton(autofillId))));
            if (autofillClientFindViewsByAutofillIdTraversal != null && autofillClientFindViewsByAutofillIdTraversal.length != 0) {
                android.view.View view = autofillClientFindViewsByAutofillIdTraversal[0];
                if (view == null) {
                    android.util.Log.i("AutofillManager", "onGetCredentialException View is null");
                    android.util.Log.d("AutofillManager", "onGetCredentialException(): no View with id " + autofillId);
                    arrayList.add(autofillId);
                }
                if (autofillId.isVirtualInt()) {
                    android.util.Log.i("AutofillManager", "onGetCredentialException afm client id is virtual");
                } else {
                    android.util.Log.i("AutofillManager", "onGetCredentialException afm client id is NOT virtual");
                    view.onGetCredentialException(str, str2);
                }
                handleFailedIdsLocked(arrayList);
                return;
            }
            android.util.Log.w("AutofillManager", "onGetCredentialException afm client view not found");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetCredentialResponse(int i, android.view.autofill.AutofillId autofillId, android.credentials.GetCredentialResponse getCredentialResponse) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                android.util.Log.w("AutofillManager", "onGetCredentialResponse afm sessionIds don't match");
                return;
            }
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client == null) {
                android.util.Log.w("AutofillManager", "onGetCredentialResponse afm client id null");
                return;
            }
            java.util.ArrayList<android.view.autofill.AutofillId> arrayList = new java.util.ArrayList<>();
            android.view.View[] autofillClientFindViewsByAutofillIdTraversal = client.autofillClientFindViewsByAutofillIdTraversal(android.view.autofill.Helper.toArray(new java.util.ArrayList(java.util.Collections.singleton(autofillId))));
            if (autofillClientFindViewsByAutofillIdTraversal != null && autofillClientFindViewsByAutofillIdTraversal.length != 0) {
                android.view.View view = autofillClientFindViewsByAutofillIdTraversal[0];
                if (view == null) {
                    android.util.Log.i("AutofillManager", "onGetCredentialResponse View is null");
                    android.util.Log.d("AutofillManager", "onGetCredentialResponse(): no View with id " + autofillId);
                    arrayList.add(autofillId);
                }
                if (autofillId.isVirtualInt()) {
                    android.util.Log.i("AutofillManager", "onGetCredentialResponse afm client id is virtual");
                } else {
                    android.util.Log.i("AutofillManager", "onGetCredentialResponse afm client id is NOT virtual");
                    view.onGetCredentialResponse(getCredentialResponse);
                }
                handleFailedIdsLocked(arrayList);
                return;
            }
            android.util.Log.w("AutofillManager", "onGetCredentialResponse afm client view not found");
        }
    }

    private void handleFailedIdsLocked(java.util.ArrayList<android.view.autofill.AutofillId> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "autofill(): total failed views: " + arrayList);
            }
            try {
                this.mService.setAutofillFailure(this.mSessionId, arrayList, this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                return;
            }
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            int size = list.size();
            android.view.View[] autofillClientFindViewsByAutofillIdTraversal = client.autofillClientFindViewsByAutofillIdTraversal(android.view.autofill.Helper.toArray(list));
            java.util.ArrayList<android.view.autofill.AutofillId> arrayList = null;
            android.util.ArrayMap arrayMap = null;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                android.view.autofill.AutofillId autofillId = list.get(i3);
                android.view.autofill.AutofillValue autofillValue = list2.get(i3);
                android.view.View view = autofillClientFindViewsByAutofillIdTraversal[i3];
                if (view == null) {
                    android.util.Log.d("AutofillManager", "autofill(): no View with id " + autofillId);
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList<>();
                    }
                    arrayList.add(autofillId);
                } else if (autofillId.isVirtualInt()) {
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap(1);
                    }
                    android.util.SparseArray sparseArray = (android.util.SparseArray) arrayMap.get(view);
                    if (sparseArray == null) {
                        sparseArray = new android.util.SparseArray(5);
                        arrayMap.put(view, sparseArray);
                    }
                    sparseArray.put(autofillId.getVirtualChildIntId(), autofillValue);
                } else {
                    if (this.mLastAutofilledData == null) {
                        this.mLastAutofilledData = new android.view.autofill.ParcelableMap(size - i3);
                    }
                    this.mLastAutofilledData.put(autofillId, autofillValue);
                    view.autofill(autofillValue);
                    setAutofilledIfValuesIs(view, autofillValue, z);
                    i2++;
                }
            }
            handleFailedIdsLocked(arrayList);
            if (arrayMap != null) {
                for (int i4 = 0; i4 < arrayMap.size(); i4++) {
                    android.view.View view2 = (android.view.View) arrayMap.keyAt(i4);
                    android.util.SparseArray<android.view.autofill.AutofillValue> sparseArray2 = (android.util.SparseArray) arrayMap.valueAt(i4);
                    view2.autofill(sparseArray2);
                    i2 += sparseArray2.size();
                }
            }
            this.mMetricsLogger.write(newLog(com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES, java.lang.Integer.valueOf(size)).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, java.lang.Integer.valueOf(i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autofillContent(int i, android.view.autofill.AutofillId autofillId, android.content.ClipData clipData) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                return;
            }
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            android.view.View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (autofillClientFindViewByAutofillIdTraversal == null) {
                android.util.Log.d("AutofillManager", "autofillContent(): no view with id " + autofillId);
                reportAutofillContentFailure(autofillId);
            } else if (autofillClientFindViewByAutofillIdTraversal.performReceiveContent(new android.view.ContentInfo.Builder(clipData, 4).build()) != null) {
                android.util.Log.w("AutofillManager", "autofillContent(): receiver could not insert content: id=" + autofillId + ", view=" + autofillClientFindViewByAutofillIdTraversal + ", clip=" + clipData);
                reportAutofillContentFailure(autofillId);
            } else {
                this.mMetricsLogger.write(newLog(com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES, 1).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, 1));
            }
        }
    }

    private void reportAutofillContentFailure(android.view.autofill.AutofillId autofillId) {
        try {
            this.mService.setAutofillFailure(this.mSessionId, java.util.Collections.singletonList(autofillId), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.metrics.LogMaker newLog(int i) {
        android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(i).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_SESSION_ID, java.lang.Integer.valueOf(this.mSessionId));
        if (isCompatibilityModeEnabledLocked()) {
            addTaggedData.addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_COMPAT_MODE, 1);
        }
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client == null) {
            addTaggedData.setPackageName(this.mContext.getPackageName());
        } else {
            addTaggedData.setComponentName(new android.content.ComponentName(client.autofillClientGetComponentName().getPackageName(), ""));
        }
        return addTaggedData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTrackedViews(int i, android.view.autofill.AutofillId[] autofillIdArr, boolean z, boolean z2, android.view.autofill.AutofillId[] autofillIdArr2, android.view.autofill.AutofillId autofillId) {
        if (autofillId != null) {
            autofillId.resetSessionId();
        }
        synchronized (this.mLock) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "setTrackedViews(): sessionId=" + i + ", trackedIds=" + java.util.Arrays.toString(autofillIdArr) + ", saveOnAllViewsInvisible=" + z + ", saveOnFinish=" + z2 + ", fillableIds=" + java.util.Arrays.toString(autofillIdArr2) + ", saveTrigerId=" + autofillId + ", mFillableIds=" + this.mFillableIds + ", mEnabled=" + this.mEnabled + ", mSessionId=" + this.mSessionId);
            }
            if (this.mEnabled && this.mSessionId == i) {
                this.mSaveOnFinish = z2;
                if (autofillIdArr2 != null) {
                    if (this.mFillableIds == null) {
                        this.mFillableIds = new android.util.ArraySet<>(autofillIdArr2.length);
                    }
                    for (android.view.autofill.AutofillId autofillId2 : autofillIdArr2) {
                        if (autofillId2 != null) {
                            autofillId2.resetSessionId();
                            this.mFillableIds.add(autofillId2);
                        }
                    }
                }
                if (this.mSaveTriggerId != null && !this.mSaveTriggerId.equals(autofillId)) {
                    setNotifyOnClickLocked(this.mSaveTriggerId, false);
                }
                if (autofillId != null && !autofillId.equals(this.mSaveTriggerId)) {
                    this.mSaveTriggerId = autofillId;
                    setNotifyOnClickLocked(this.mSaveTriggerId, true);
                }
                if (!z) {
                    autofillIdArr = null;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                if (this.mFillableIds != null) {
                    arraySet.addAll((android.util.ArraySet) this.mFillableIds);
                }
                if (autofillIdArr != null) {
                    for (android.view.autofill.AutofillId autofillId3 : autofillIdArr) {
                        if (autofillId3 != null) {
                            autofillId3.resetSessionId();
                            arraySet.add(autofillId3);
                        }
                    }
                }
                if (!arraySet.isEmpty()) {
                    this.mTrackedViews = new android.view.autofill.AutofillManager.TrackedViews(autofillIdArr, android.view.autofill.Helper.toArray(arraySet));
                } else {
                    this.mTrackedViews = null;
                }
            }
        }
    }

    private void setNotifyOnClickLocked(android.view.autofill.AutofillId autofillId, boolean z) {
        android.view.View findView = findView(autofillId);
        if (findView == null) {
            android.util.Log.w("AutofillManager", "setNotifyOnClick(): invalid id: " + autofillId);
        } else {
            findView.setNotifyAutofillManagerOnClick(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSaveUiState(int i, boolean z) {
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d("AutofillManager", "setSaveUiState(" + i + "): " + z);
        }
        synchronized (this.mLock) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                android.util.Log.w("AutofillManager", "setSaveUiState(" + i + ", " + z + ") called on existing session " + this.mSessionId + "; cancelling it");
                cancelSessionLocked();
            }
            if (z) {
                this.mSessionId = i;
                this.mState = 3;
            } else {
                this.mSessionId = Integer.MAX_VALUE;
                this.mState = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionFinished(int i, java.util.List<android.view.autofill.AutofillId> list) {
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                list.get(i2).resetSessionId();
            }
        }
        synchronized (this.mLock) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "setSessionFinished(): from " + getStateAsStringLocked() + " to " + getStateAsString(i) + "; autofillableIds=" + list);
            }
            if (list != null) {
                this.mEnteredIds = new android.util.ArraySet<>(list);
            }
            if (i != 5 && i != 6) {
                resetSessionLocked(false);
                this.mState = i;
            }
            resetSessionLocked(true);
            this.mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAugmentedAutofillClient(com.android.internal.os.IResultReceiver iResultReceiver) {
        synchronized (this.mLock) {
            if (this.mAugmentedAutofillServiceClient == null) {
                this.mAugmentedAutofillServiceClient = new android.view.autofill.AutofillManager.AugmentedAutofillManagerClient();
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBinder(EXTRA_AUGMENTED_AUTOFILL_CLIENT, this.mAugmentedAutofillServiceClient.asBinder());
            try {
                iResultReceiver.send(0, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AutofillManager", "Could not send AugmentedAutofillClient back: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestShowSoftInput(android.view.autofill.AutofillId autofillId) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "requestShowSoftInput(" + autofillId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client == null) {
            return;
        }
        final android.view.View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
        if (autofillClientFindViewByAutofillIdTraversal == null) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "View is not found");
                return;
            }
            return;
        }
        android.os.Handler handler = autofillClientFindViewByAutofillIdTraversal.getHandler();
        if (handler == null) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "Ignoring requestShowSoftInput due to no handler in view");
            }
        } else {
            if (handler.getLooper() != android.os.Looper.myLooper()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "Scheduling showSoftInput() on the view UI thread");
                }
                handler.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.requestShowSoftInputInViewThread(android.view.View.this);
                    }
                });
                return;
            }
            requestShowSoftInputInViewThread(autofillClientFindViewByAutofillIdTraversal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void requestShowSoftInputInViewThread(android.view.View view) {
        if (!view.isFocused()) {
            android.util.Log.w("AutofillManager", "Ignoring requestShowSoftInput() due to non-focused view");
            return;
        }
        boolean showSoftInput = ((android.view.inputmethod.InputMethodManager) view.getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)).showSoftInput(view, 0);
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", " InputMethodManager.showSoftInput returns " + showSoftInput);
        }
    }

    public void requestHideFillUi() {
        requestHideFillUi(this.mIdShownFillUi, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestHideFillUi(android.view.autofill.AutofillId autofillId, boolean z) {
        android.view.autofill.AutofillManager.AutofillClient client;
        android.view.View findView = autofillId == null ? null : findView(autofillId);
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "requestHideFillUi(" + autofillId + "): anchor = " + findView);
        }
        if (findView == null) {
            if (z && (client = getClient()) != null) {
                client.autofillClientRequestHideFillUi();
                return;
            }
            return;
        }
        requestHideFillUi(autofillId, findView);
    }

    private void requestHideFillUi(android.view.autofill.AutofillId autofillId, android.view.View view) {
        android.view.autofill.AutofillManager.AutofillCallback autofillCallback;
        synchronized (this.mLock) {
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            autofillCallback = null;
            if (client != null && client.autofillClientRequestHideFillUi()) {
                this.mIdShownFillUi = null;
                autofillCallback = this.mCallback;
            }
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(view, autofillId.getVirtualChildIntId(), 2);
            } else {
                autofillCallback.onAutofillEvent(view, 2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDisableAutofill(long j, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            if (this.mOptions == null) {
                return;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            if (componentName != null) {
                if (this.mOptions.disabledActivities == null) {
                    this.mOptions.disabledActivities = new android.util.ArrayMap<>();
                }
                this.mOptions.disabledActivities.put(componentName.flattenToString(), java.lang.Long.valueOf(elapsedRealtime));
            } else {
                this.mOptions.appDisabledExpiration = elapsedRealtime;
            }
        }
    }

    void notifyReenableAutofill() {
        synchronized (this.mLock) {
            if (this.mOptions == null) {
                return;
            }
            this.mOptions.appDisabledExpiration = 0L;
            this.mOptions.disabledActivities = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNoFillUi(int i, android.view.autofill.AutofillId autofillId, int i2) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "notifyNoFillUi(): sessionFinishedState=" + i2);
        }
        if (findView(autofillId) == null) {
            return;
        }
        notifyCallback(i, autofillId, 3);
        if (i2 != 0) {
            setSessionFinished(i2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallback(int i, android.view.autofill.AutofillId autofillId, int i2) {
        android.view.autofill.AutofillManager.AutofillCallback autofillCallback;
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "notifyCallback(): sessionId=" + i + ", autofillId=" + autofillId + ", event=" + i2);
        }
        android.view.View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId == i && getClient() != null) {
                autofillCallback = this.mCallback;
            } else {
                autofillCallback = null;
            }
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(findView, autofillId.getVirtualChildIntId(), i2);
            } else {
                autofillCallback.onAutofillEvent(findView, i2);
            }
        }
    }

    private boolean shouldSuppressDialogsForCredman(android.view.View view) {
        if (view == null) {
            return false;
        }
        if (view.isCredential()) {
            return true;
        }
        return containsAutofillHintPrefix(view, "credential");
    }

    private boolean isCredmanRequested(android.view.View view) {
        if (view == null) {
            return false;
        }
        if (view.getViewCredentialHandler() != null) {
            return true;
        }
        if (view.getAutofillHints() == null) {
            return false;
        }
        return containsAutofillHintPrefix(view, "credential=");
    }

    private boolean containsAutofillHintPrefix(android.view.View view, java.lang.String str) {
        java.lang.String[] autofillHints = view.getAutofillHints();
        if (autofillHints == null) {
            return false;
        }
        for (java.lang.String str2 : autofillHints) {
            if (str2 != null && str2.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    private android.view.View findView(android.view.autofill.AutofillId autofillId) {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client != null) {
            return client.autofillClientFindViewByAutofillIdTraversal(autofillId);
        }
        return null;
    }

    public boolean hasAutofillFeature() {
        return this.mService != null;
    }

    public void onPendingSaveUi(int i, android.os.IBinder iBinder) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v("AutofillManager", "onPendingSaveUi(" + i + "): " + iBinder);
        }
        synchronized (this.mLock) {
            try {
                this.mService.onPendingSaveUi(i, iBinder);
            } catch (android.os.RemoteException e) {
                android.util.Log.e("AutofillManager", "Error in onPendingSaveUi: ", e);
            }
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.print(str);
            printWriter.println("AutofillManager:");
            java.lang.String str2 = str + "  ";
            printWriter.print(str2);
            printWriter.print("sessionId: ");
            printWriter.println(this.mSessionId);
            printWriter.print(str2);
            printWriter.print("state: ");
            printWriter.println(getStateAsStringLocked());
            printWriter.print(str2);
            printWriter.print("context: ");
            printWriter.println(this.mContext);
            printWriter.print(str2);
            printWriter.print("service client: ");
            printWriter.println(this.mServiceClient);
            android.view.autofill.AutofillManager.AutofillClient client = getClient();
            if (client != null) {
                printWriter.print(str2);
                printWriter.print("client: ");
                printWriter.print(client);
                printWriter.print(" (");
                printWriter.print(client.autofillClientGetActivityToken());
                printWriter.println(')');
            }
            printWriter.print(str2);
            printWriter.print("enabled: ");
            printWriter.println(this.mEnabled);
            printWriter.print(str2);
            printWriter.print("enabledAugmentedOnly: ");
            printWriter.println(this.mForAugmentedAutofillOnly);
            printWriter.print(str2);
            printWriter.print("hasService: ");
            boolean z = true;
            printWriter.println(this.mService != null);
            printWriter.print(str2);
            printWriter.print("hasCallback: ");
            if (this.mCallback == null) {
                z = false;
            }
            printWriter.println(z);
            printWriter.print(str2);
            printWriter.print("onInvisibleCalled ");
            printWriter.println(this.mOnInvisibleCalled);
            printWriter.print(str2);
            printWriter.print("last autofilled data: ");
            printWriter.println(this.mLastAutofilledData);
            printWriter.print(str2);
            printWriter.print("id of last fill UI shown: ");
            printWriter.println(this.mIdShownFillUi);
            printWriter.print(str2);
            printWriter.print("tracked views: ");
            if (this.mTrackedViews == null) {
                printWriter.println("null");
            } else {
                java.lang.String str3 = str2 + "  ";
                printWriter.println();
                printWriter.print(str3);
                printWriter.print("visible:");
                printWriter.println(this.mTrackedViews.mVisibleTrackedIds);
                printWriter.print(str3);
                printWriter.print("invisible:");
                printWriter.println(this.mTrackedViews.mInvisibleTrackedIds);
            }
            printWriter.print(str2);
            printWriter.print("fillable ids: ");
            printWriter.println(this.mFillableIds);
            printWriter.print(str2);
            printWriter.print("entered ids: ");
            printWriter.println(this.mEnteredIds);
            if (this.mEnteredForAugmentedAutofillIds != null) {
                printWriter.print(str2);
                printWriter.print("entered ids for augmented autofill: ");
                printWriter.println(this.mEnteredForAugmentedAutofillIds);
            }
            if (this.mForAugmentedAutofillOnly) {
                printWriter.print(str2);
                printWriter.println("For Augmented Autofill Only");
            }
            printWriter.print(str2);
            printWriter.print("save trigger id: ");
            printWriter.println(this.mSaveTriggerId);
            printWriter.print(str2);
            printWriter.print("save on finish(): ");
            printWriter.println(this.mSaveOnFinish);
            if (this.mOptions != null) {
                printWriter.print(str2);
                printWriter.print("options: ");
                this.mOptions.dumpShort(printWriter);
                printWriter.println();
            }
            printWriter.print(str2);
            printWriter.print("fill dialog enabled: ");
            printWriter.println(this.mIsFillDialogEnabled);
            printWriter.print(str2);
            printWriter.print("fill dialog enabled hints: ");
            printWriter.println(java.util.Arrays.toString(this.mFillDialogEnabledHints));
            printWriter.print(str2);
            printWriter.print("compat mode enabled: ");
            if (this.mCompatibilityBridge != null) {
                java.lang.String str4 = str2 + "  ";
                printWriter.println("true");
                printWriter.print(str4);
                printWriter.print("windowId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedWindowId);
                printWriter.print(str4);
                printWriter.print("nodeId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedNodeId);
                printWriter.print(str4);
                printWriter.print("virtualId: ");
                printWriter.println(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(this.mCompatibilityBridge.mFocusedNodeId));
                printWriter.print(str4);
                printWriter.print("focusedBounds: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedBounds);
            } else {
                printWriter.println("false");
            }
            printWriter.print(str2);
            printWriter.print("debug: ");
            printWriter.print(android.view.autofill.Helper.sDebug);
            printWriter.print(" verbose: ");
            printWriter.println(android.view.autofill.Helper.sVerbose);
        }
    }

    private java.lang.String getStateAsStringLocked() {
        return getStateAsString(this.mState);
    }

    private static java.lang.String getStateAsString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ACTIVE";
            case 2:
                return "FINISHED";
            case 3:
                return "SHOWING_SAVE_UI";
            case 4:
                return "DISABLED_BY_SERVICE";
            case 5:
                return "UNKNOWN_COMPAT_MODE";
            case 6:
                return "UNKNOWN_FAILED";
            case 7:
                return "PENDING_AUTHENTICATION";
            default:
                return "INVALID:" + i;
        }
    }

    public static java.lang.String getSmartSuggestionModeToString(int i) {
        switch (i) {
            case 0:
                return "OFF";
            case 1:
                return "SYSTEM";
            default:
                return "INVALID:" + i;
        }
    }

    private boolean isActiveLocked() {
        return this.mState == 1 || isPendingAuthenticationLocked();
    }

    private boolean isPendingAuthenticationLocked() {
        return this.mRelayoutFix && this.mState == 7;
    }

    private boolean isDisabledByServiceLocked() {
        return this.mState == 4;
    }

    private boolean isFinishedLocked() {
        return this.mState == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void post(java.lang.Runnable runnable) {
        android.view.autofill.AutofillManager.AutofillClient client = getClient();
        if (client == null) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "ignoring post() because client is null");
                return;
            }
            return;
        }
        client.autofillClientRunOnUiThread(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFillDialogTriggerIds(java.util.List<android.view.autofill.AutofillId> list) {
        this.mFillDialogTriggerIds = list;
    }

    public boolean showAutofillDialog(android.view.View view) {
        java.util.Objects.requireNonNull(view);
        if (shouldShowAutofillDialog(view, view.getAutofillId())) {
            this.mShowAutofillDialogCalled = true;
            final java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(view);
            post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.autofill.AutofillManager.this.lambda$showAutofillDialog$3(weakReference);
                }
            });
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAutofillDialog$3(java.lang.ref.WeakReference weakReference) {
        android.view.View view = (android.view.View) weakReference.get();
        if (view != null) {
            notifyViewEntered(view);
        }
    }

    public boolean showAutofillDialog(android.view.View view, final int i) {
        java.util.Objects.requireNonNull(view);
        if (shouldShowAutofillDialog(view, getAutofillId(view, i))) {
            this.mShowAutofillDialogCalled = true;
            final java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(view);
            post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.autofill.AutofillManager.this.lambda$showAutofillDialog$4(weakReference, i);
                }
            });
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAutofillDialog$4(java.lang.ref.WeakReference weakReference, int i) {
        android.view.View view = (android.view.View) weakReference.get();
        if (view != null) {
            notifyViewEntered(view, i, null, 0);
        }
    }

    private boolean shouldShowAutofillDialog(android.view.View view, android.view.autofill.AutofillId autofillId) {
        if (!hasFillDialogUiFeature() || this.mShowAutofillDialogCalled || this.mFillDialogTriggerIds == null || this.mScreenHasCredmanField || getImeStateFlag(view) == 128) {
            return false;
        }
        int size = this.mFillDialogTriggerIds.size();
        for (int i = 0; i < size; i++) {
            if (this.mFillDialogTriggerIds.get(i).equalsIgnoreSession(autofillId)) {
                return true;
            }
        }
        return false;
    }

    private final class CompatibilityBridge implements android.view.accessibility.AccessibilityManager.AccessibilityPolicy {
        android.accessibilityservice.AccessibilityServiceInfo mCompatServiceInfo;
        private final android.graphics.Rect mFocusedBounds = new android.graphics.Rect();
        private final android.graphics.Rect mTempBounds = new android.graphics.Rect();
        private int mFocusedWindowId = -1;
        private long mFocusedNodeId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;

        CompatibilityBridge() {
            android.view.accessibility.AccessibilityManager.getInstance(android.view.autofill.AutofillManager.this.mContext).setAccessibilityPolicy(this);
        }

        private android.accessibilityservice.AccessibilityServiceInfo getCompatServiceInfo() {
            synchronized (android.view.autofill.AutofillManager.this.mLock) {
                if (this.mCompatServiceInfo != null) {
                    return this.mCompatServiceInfo;
                }
                android.content.Intent intent = new android.content.Intent();
                intent.setComponent(new android.content.ComponentName("android", "com.android.server.autofill.AutofillCompatAccessibilityService"));
                try {
                    this.mCompatServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo(android.view.autofill.AutofillManager.this.mContext.getPackageManager().resolveService(intent, 1048704), android.view.autofill.AutofillManager.this.mContext);
                    return this.mCompatServiceInfo;
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Log.e("AutofillManager", "Cannot find compat autofill service:" + intent);
                    throw new java.lang.IllegalStateException("Cannot find compat autofill service");
                }
            }
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public boolean isEnabled(boolean z) {
            return true;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public int getRelevantEventTypes(int i) {
            return i | 8 | 16 | 1 | 2048;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
            if (list == null) {
                list = new java.util.ArrayList<>();
            }
            list.add(getCompatServiceInfo());
            return list;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
            if (list == null) {
                list = new java.util.ArrayList<>();
            }
            list.add(getCompatServiceInfo());
            return list;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public android.view.accessibility.AccessibilityEvent onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z, int i) {
            int eventType = accessibilityEvent.getEventType();
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "onAccessibilityEvent(" + android.view.accessibility.AccessibilityEvent.eventTypeToString(eventType) + "): virtualId=" + android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId()) + ", client=" + android.view.autofill.AutofillManager.this.getClient());
            }
            switch (eventType) {
                case 1:
                    synchronized (android.view.autofill.AutofillManager.this.mLock) {
                        notifyViewClicked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    }
                    break;
                case 8:
                    synchronized (android.view.autofill.AutofillManager.this.mLock) {
                        if (this.mFocusedWindowId != accessibilityEvent.getWindowId() || this.mFocusedNodeId != accessibilityEvent.getSourceNodeId()) {
                            if (this.mFocusedWindowId != -1 && this.mFocusedNodeId != android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID) {
                                notifyViewExited(this.mFocusedWindowId, this.mFocusedNodeId);
                                this.mFocusedWindowId = -1;
                                this.mFocusedNodeId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
                                this.mFocusedBounds.set(0, 0, 0, 0);
                            }
                            int windowId = accessibilityEvent.getWindowId();
                            long sourceNodeId = accessibilityEvent.getSourceNodeId();
                            if (notifyViewEntered(windowId, sourceNodeId, this.mFocusedBounds)) {
                                this.mFocusedWindowId = windowId;
                                this.mFocusedNodeId = sourceNodeId;
                            }
                            break;
                        } else {
                            return accessibilityEvent;
                        }
                    }
                    break;
                case 16:
                    synchronized (android.view.autofill.AutofillManager.this.mLock) {
                        if (this.mFocusedWindowId == accessibilityEvent.getWindowId() && this.mFocusedNodeId == accessibilityEvent.getSourceNodeId()) {
                            notifyValueChanged(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                        }
                    }
                    break;
                case 2048:
                    android.view.autofill.AutofillManager.AutofillClient client = android.view.autofill.AutofillManager.this.getClient();
                    if (client != null) {
                        synchronized (android.view.autofill.AutofillManager.this.mLock) {
                            if (client.autofillClientIsFillUiShowing()) {
                                notifyViewEntered(this.mFocusedWindowId, this.mFocusedNodeId, this.mFocusedBounds);
                            }
                            updateTrackedViewsLocked();
                        }
                        break;
                    }
                    break;
            }
            if (z) {
                return accessibilityEvent;
            }
            return null;
        }

        private boolean notifyViewEntered(int i, long j, android.graphics.Rect rect) {
            android.view.View findViewByAccessibilityId;
            android.view.accessibility.AccessibilityNodeInfo findVirtualNodeByAccessibilityId;
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || (findVirtualNodeByAccessibilityId = findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId)) == null || !findVirtualNodeByAccessibilityId.isEditable()) {
                return false;
            }
            android.graphics.Rect rect2 = this.mTempBounds;
            findVirtualNodeByAccessibilityId.getBoundsInScreen(rect2);
            if (rect2.equals(rect)) {
                return false;
            }
            rect.set(rect2);
            android.view.autofill.AutofillManager.this.notifyViewEntered(findViewByAccessibilityId, virtualDescendantId, rect2);
            return true;
        }

        private void notifyViewExited(int i, long j) {
            android.view.View findViewByAccessibilityId;
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null) {
                return;
            }
            android.view.autofill.AutofillManager.this.notifyViewExited(findViewByAccessibilityId, virtualDescendantId);
        }

        private void notifyValueChanged(int i, long j) {
            android.view.View findViewByAccessibilityId;
            android.view.accessibility.AccessibilityNodeInfo findVirtualNodeByAccessibilityId;
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || (findVirtualNodeByAccessibilityId = findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId)) == null) {
                return;
            }
            android.view.autofill.AutofillManager.this.notifyValueChanged(findViewByAccessibilityId, virtualDescendantId, android.view.autofill.AutofillValue.forText(findVirtualNodeByAccessibilityId.getText()));
        }

        private void notifyViewClicked(int i, long j) {
            android.view.View findViewByAccessibilityId;
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId) == null) {
                return;
            }
            android.view.autofill.AutofillManager.this.notifyViewClicked(findViewByAccessibilityId, virtualDescendantId);
        }

        private void updateTrackedViewsLocked() {
            if (android.view.autofill.AutofillManager.this.mTrackedViews != null) {
                android.view.autofill.AutofillManager.this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }

        private android.view.View findViewByAccessibilityId(int i, long j) {
            android.view.autofill.AutofillManager.AutofillClient client = android.view.autofill.AutofillManager.this.getClient();
            if (client == null) {
                return null;
            }
            return client.autofillClientFindViewByAccessibilityIdTraversal(android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j), i);
        }

        private android.view.accessibility.AccessibilityNodeInfo findVirtualNodeByAccessibilityId(android.view.View view, int i) {
            android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider == null) {
                return null;
            }
            return accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        }

        private boolean isVirtualNode(int i) {
            return (i == -1 || i == Integer.MAX_VALUE) ? false : true;
        }
    }

    private class TrackedViews {
        boolean mHasNewTrackedView;
        private final android.util.ArraySet<android.view.autofill.AutofillId> mInvisibleDialogTrackedIds;
        boolean mIsTrackedSaveView;
        private final android.util.ArraySet<android.view.autofill.AutofillId> mVisibleDialogTrackedIds;
        private final android.util.ArraySet<android.view.autofill.AutofillId> mVisibleTrackedIds = new android.util.ArraySet<>();
        private final android.util.ArraySet<android.view.autofill.AutofillId> mInvisibleTrackedIds = new android.util.ArraySet<>();

        private <T> boolean isInSet(android.util.ArraySet<T> arraySet, T t) {
            return arraySet != null && arraySet.contains(t);
        }

        private <T> android.util.ArraySet<T> addToSet(android.util.ArraySet<T> arraySet, T t) {
            if (arraySet == null) {
                arraySet = new android.util.ArraySet<>(1);
            }
            arraySet.add(t);
            return arraySet;
        }

        private <T> android.util.ArraySet<T> removeFromSet(android.util.ArraySet<T> arraySet, T t) {
            if (arraySet == null) {
                return null;
            }
            arraySet.remove(t);
            if (arraySet.isEmpty()) {
                return null;
            }
            return arraySet;
        }

        TrackedViews(android.view.autofill.AutofillId[] autofillIdArr, android.view.autofill.AutofillId[] autofillIdArr2) {
            if (!com.android.internal.util.ArrayUtils.isEmpty(autofillIdArr)) {
                this.mIsTrackedSaveView = true;
                initialTrackedViews(autofillIdArr, this.mVisibleTrackedIds, this.mInvisibleTrackedIds);
            }
            this.mVisibleDialogTrackedIds = new android.util.ArraySet<>();
            this.mInvisibleDialogTrackedIds = new android.util.ArraySet<>();
            if (!com.android.internal.util.ArrayUtils.isEmpty(autofillIdArr2)) {
                initialTrackedViews(autofillIdArr2, this.mVisibleDialogTrackedIds, this.mInvisibleDialogTrackedIds);
                android.view.autofill.AutofillManager.this.mAllTrackedViews.addAll(java.util.Arrays.asList(autofillIdArr2));
            }
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "TrackedViews(trackedIds=" + java.util.Arrays.toString(autofillIdArr) + "):  mVisibleTrackedIds=" + this.mVisibleTrackedIds + " mInvisibleTrackedIds=" + this.mInvisibleTrackedIds + " allTrackedIds=" + java.util.Arrays.toString(autofillIdArr2) + " mVisibleDialogTrackedIds=" + this.mVisibleDialogTrackedIds + " mInvisibleDialogTrackedIds=" + this.mInvisibleDialogTrackedIds);
            }
            if (this.mIsTrackedSaveView && this.mVisibleTrackedIds.isEmpty()) {
                android.view.autofill.AutofillManager.this.finishSessionLocked(4);
            }
        }

        private void initialTrackedViews(android.view.autofill.AutofillId[] autofillIdArr, android.util.ArraySet<android.view.autofill.AutofillId> arraySet, android.util.ArraySet<android.view.autofill.AutofillId> arraySet2) {
            boolean[] zArr;
            android.view.autofill.AutofillManager.AutofillClient client = android.view.autofill.AutofillManager.this.getClient();
            if (com.android.internal.util.ArrayUtils.isEmpty(autofillIdArr) || client == null) {
                return;
            }
            if (client.autofillClientIsVisibleForAutofill()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "client is visible, check tracked ids");
                }
                zArr = client.autofillClientGetViewVisibility(autofillIdArr);
            } else {
                zArr = new boolean[autofillIdArr.length];
            }
            int length = autofillIdArr.length;
            for (int i = 0; i < length; i++) {
                android.view.autofill.AutofillId autofillId = autofillIdArr[i];
                autofillId.resetSessionId();
                if (zArr[i]) {
                    addToSet(arraySet, autofillId);
                } else {
                    addToSet(arraySet2, autofillId);
                }
            }
        }

        void notifyViewVisibilityChangedLocked(android.view.autofill.AutofillId autofillId, boolean z) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d("AutofillManager", "notifyViewVisibilityChangedLocked(): id=" + autofillId + " isVisible=" + z);
            }
            if (android.view.autofill.AutofillManager.this.isClientVisibleForAutofillLocked()) {
                if (z) {
                    if (isInSet(this.mInvisibleTrackedIds, autofillId)) {
                        removeFromSet(this.mInvisibleTrackedIds, autofillId);
                        addToSet(this.mVisibleTrackedIds, autofillId);
                    }
                    if (isInSet(this.mInvisibleDialogTrackedIds, autofillId)) {
                        removeFromSet(this.mInvisibleDialogTrackedIds, autofillId);
                        addToSet(this.mVisibleDialogTrackedIds, autofillId);
                    }
                } else {
                    if (isInSet(this.mVisibleTrackedIds, autofillId)) {
                        removeFromSet(this.mVisibleTrackedIds, autofillId);
                        addToSet(this.mInvisibleTrackedIds, autofillId);
                    }
                    if (isInSet(this.mVisibleDialogTrackedIds, autofillId)) {
                        removeFromSet(this.mVisibleDialogTrackedIds, autofillId);
                        addToSet(this.mInvisibleDialogTrackedIds, autofillId);
                    }
                }
            }
            if (this.mIsTrackedSaveView && this.mVisibleTrackedIds.isEmpty()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "No more visible ids. Invisible = " + this.mInvisibleTrackedIds);
                }
                android.view.autofill.AutofillManager.this.finishSessionLocked(4);
            }
            if (this.mVisibleDialogTrackedIds.isEmpty()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "No more visible ids. Invisible = " + this.mInvisibleDialogTrackedIds);
                }
                processNoVisibleTrackedAllViews();
            }
        }

        void onVisibleForAutofillChangedLocked() {
            if (android.view.autofill.AutofillManager.this.getClient() != null) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): inv= " + this.mInvisibleTrackedIds + " vis=" + this.mVisibleTrackedIds);
                }
                onVisibleForAutofillChangedInternalLocked(this.mVisibleTrackedIds, this.mInvisibleTrackedIds);
                onVisibleForAutofillChangedInternalLocked(this.mVisibleDialogTrackedIds, this.mInvisibleDialogTrackedIds);
            }
            if (this.mIsTrackedSaveView && this.mVisibleTrackedIds.isEmpty()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                android.view.autofill.AutofillManager.this.finishSessionLocked(4);
            }
            if (this.mVisibleDialogTrackedIds.isEmpty()) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                processNoVisibleTrackedAllViews();
            }
        }

        void onVisibleForAutofillChangedInternalLocked(android.util.ArraySet<android.view.autofill.AutofillId> arraySet, android.util.ArraySet<android.view.autofill.AutofillId> arraySet2) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): inv= " + arraySet2 + " vis=" + arraySet);
            }
            android.util.ArraySet arraySet3 = new android.util.ArraySet();
            arraySet3.addAll((android.util.ArraySet) arraySet);
            arraySet3.addAll((android.util.ArraySet) arraySet2);
            if (!arraySet3.isEmpty()) {
                arraySet.clear();
                arraySet2.clear();
                initialTrackedViews(android.view.autofill.Helper.toArray(arraySet3), arraySet, arraySet2);
            }
        }

        private void processNoVisibleTrackedAllViews() {
            android.view.autofill.AutofillManager.this.mShowAutofillDialogCalled = false;
        }

        void checkViewState(android.view.autofill.AutofillId autofillId) {
            if (this.mHasNewTrackedView) {
                return;
            }
            android.view.autofill.AutofillManager.this.mIsFillRequested.set(false);
            this.mHasNewTrackedView = true;
        }
    }

    public static abstract class AutofillCallback {
        public static final int EVENT_INPUT_HIDDEN = 2;
        public static final int EVENT_INPUT_SHOWN = 1;
        public static final int EVENT_INPUT_UNAVAILABLE = 3;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface AutofillEventType {
        }

        public void onAutofillEvent(android.view.View view, int i) {
        }

        public void onAutofillEvent(android.view.View view, int i, int i2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AutofillManagerClient extends android.view.autofill.IAutoFillManagerClient.Stub {
        private final java.lang.ref.WeakReference<android.view.autofill.AutofillManager> mAfm;

        private AutofillManagerClient(android.view.autofill.AutofillManager autofillManager) {
            this.mAfm = new java.lang.ref.WeakReference<>(autofillManager);
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setState(final int i) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.setState(i);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofill(final int i, final java.util.List<android.view.autofill.AutofillId> list, final java.util.List<android.view.autofill.AutofillValue> list2, final boolean z) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.autofill(i, list, list2, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialResponse(final int i, final android.view.autofill.AutofillId autofillId, final android.credentials.GetCredentialResponse getCredentialResponse) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.onGetCredentialResponse(i, autofillId, getCredentialResponse);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialException(final int i, final android.view.autofill.AutofillId autofillId, final java.lang.String str, final java.lang.String str2) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.onGetCredentialException(i, autofillId, str, str2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofillContent(final int i, final android.view.autofill.AutofillId autofillId, final android.content.ClipData clipData) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.autofillContent(i, autofillId, clipData);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void authenticate(final int i, final int i2, final android.content.IntentSender intentSender, final android.content.Intent intent, final boolean z) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.authenticate(i, i2, intentSender, intent, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowFillUi(final int i, final android.view.autofill.AutofillId autofillId, final int i2, final int i3, final android.graphics.Rect rect, final android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestShowFillUi(i, autofillId, i2, i3, rect, iAutofillWindowPresenter);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUi(int i, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestHideFillUi(autofillId, false);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUiWhenDestroyed(int i, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestHideFillUi(autofillId, true);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyNoFillUi(final int i, final android.view.autofill.AutofillId autofillId, final int i2) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.notifyNoFillUi(i, autofillId, i2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiShown(final int i, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.notifyCallback(i, autofillId, 1);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiHidden(final int i, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.notifyCallback(i, autofillId, 2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyDisableAutofill(final long j, final android.content.ComponentName componentName) throws android.os.RemoteException {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.notifyDisableAutofill(j, componentName);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void dispatchUnhandledKey(final int i, final android.view.autofill.AutofillId autofillId, final android.view.KeyEvent keyEvent) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.dispatchUnhandledKey(i, autofillId, keyEvent);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void startIntentSender(final android.content.IntentSender intentSender, final android.content.Intent intent) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.AutofillManagerClient.lambda$startIntentSender$14(android.view.autofill.AutofillManager.this, intentSender, intent);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$startIntentSender$14(android.view.autofill.AutofillManager autofillManager, android.content.IntentSender intentSender, android.content.Intent intent) {
            try {
                autofillManager.mContext.startIntentSender(intentSender, intent, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Log.e("AutofillManager", "startIntentSender() failed for intent:" + intentSender, e);
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setTrackedViews(final int i, final android.view.autofill.AutofillId[] autofillIdArr, final boolean z, final boolean z2, final android.view.autofill.AutofillId[] autofillIdArr2, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.setTrackedViews(i, autofillIdArr, z, z2, autofillIdArr2, autofillId);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSaveUiState(final int i, final boolean z) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.setSaveUiState(i, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSessionFinished(final int i, final java.util.List<android.view.autofill.AutofillId> list) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.setSessionFinished(i, list);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void getAugmentedAutofillClient(final com.android.internal.os.IResultReceiver iResultReceiver) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.getAugmentedAutofillClient(iResultReceiver);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowSoftInput(final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestShowSoftInput(autofillId);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillDialogTriggerIds(final java.util.List<android.view.autofill.AutofillId> list) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.setFillDialogTriggerIds(list);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AugmentedAutofillManagerClient extends android.view.autofill.IAugmentedAutofillManagerClient.Stub {
        private final java.lang.ref.WeakReference<android.view.autofill.AutofillManager> mAfm;

        private AugmentedAutofillManagerClient(android.view.autofill.AutofillManager autofillManager) {
            this.mAfm = new java.lang.ref.WeakReference<>(autofillManager);
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public android.app.assist.AssistStructure.ViewNodeParcelable getViewNodeParcelable(android.view.autofill.AutofillId autofillId) {
            android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null) {
                return null;
            }
            android.view.View view = getView(autofillManager, autofillId);
            if (view == null) {
                android.util.Log.w("AutofillManager", "getViewNodeParcelable(" + autofillId + "): could not find view");
                return null;
            }
            android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl != null && (viewRootImpl.getWindowFlags() & 8192) == 0) {
                android.app.assist.AssistStructure.ViewNodeBuilder viewNodeBuilder = new android.app.assist.AssistStructure.ViewNodeBuilder();
                viewNodeBuilder.setAutofillId(view.getAutofillId());
                view.onProvideAutofillStructure(viewNodeBuilder, 0);
                android.app.assist.AssistStructure.ViewNode viewNode = viewNodeBuilder.getViewNode();
                if (viewNode != null && autofillId.equals(viewNode.getAutofillId())) {
                    return new android.app.assist.AssistStructure.ViewNodeParcelable(viewNode);
                }
            }
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public android.graphics.Rect getViewCoordinates(android.view.autofill.AutofillId autofillId) {
            android.view.View view;
            android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null || (view = getView(autofillManager, autofillId)) == null) {
                return null;
            }
            android.graphics.Rect rect = new android.graphics.Rect();
            view.getWindowVisibleDisplayFrame(rect);
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            android.graphics.Rect rect2 = new android.graphics.Rect(iArr[0], iArr[1] - rect.top, iArr[0] + view.getWidth(), (iArr[1] - rect.top) + view.getHeight());
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "Coordinates for " + autofillId + ": " + rect2);
            }
            return rect2;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void autofill(final int i, final java.util.List<android.view.autofill.AutofillId> list, final java.util.List<android.view.autofill.AutofillValue> list2, final boolean z) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.autofill(i, list, list2, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestShowFillUi(final int i, final android.view.autofill.AutofillId autofillId, final int i2, final int i3, final android.graphics.Rect rect, final android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestShowFillUi(i, autofillId, i2, i3, rect, iAutofillWindowPresenter);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestHideFillUi(int i, final android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.autofill.AutofillManager.this.requestHideFillUi(autofillId, false);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public boolean requestAutofill(int i, android.view.autofill.AutofillId autofillId) {
            final android.view.autofill.AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null || autofillManager.mSessionId != i) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Slog.d("AutofillManager", "Autofill not available or sessionId doesn't match");
                }
                return false;
            }
            final android.view.View view = getView(autofillManager, autofillId);
            if (view == null || !view.isFocused()) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Slog.d("AutofillManager", "View not available or is not on focus");
                }
                return false;
            }
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("AutofillManager", "requestAutofill() by AugmentedAutofillService.");
            }
            autofillManager.post(new java.lang.Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.autofill.AutofillManager.this.requestAutofillFromNewSession(view);
                }
            });
            return true;
        }

        private android.view.View getView(android.view.autofill.AutofillManager autofillManager, android.view.autofill.AutofillId autofillId) {
            android.view.autofill.AutofillManager.AutofillClient client = autofillManager.getClient();
            if (client == null) {
                android.util.Log.w("AutofillManager", "getView(" + autofillId + "): no autofill client");
                return null;
            }
            android.view.View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (autofillClientFindViewByAutofillIdTraversal == null) {
                android.util.Log.w("AutofillManager", "getView(" + autofillId + "): could not find view");
            }
            return autofillClientFindViewByAutofillIdTraversal;
        }
    }
}
