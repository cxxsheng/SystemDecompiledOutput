package com.android.server.wm;

/* loaded from: classes3.dex */
public class BackgroundActivityStartController {
    public static final android.app.ActivityOptions ACTIVITY_OPTIONS_SYSTEM_DEFINED = android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(0).setPendingIntentCreatorBackgroundActivityStartMode(0);
    private static final int ASM_GRACEPERIOD_MAX_REPEATS = 5;
    private static final long ASM_GRACEPERIOD_TIMEOUT_MS = 3000;
    static final java.lang.String AUTO_OPT_IN_CALL_FOR_RESULT = "callForResult";
    static final java.lang.String AUTO_OPT_IN_NOT_PENDING_INTENT = "notPendingIntent";
    static final java.lang.String AUTO_OPT_IN_SAME_UID = "sameUid";
    static final int BAL_ALLOW_ALLOWLISTED_COMPONENT = 3;
    static final int BAL_ALLOW_ALLOWLISTED_UID = 2;
    static final int BAL_ALLOW_DEFAULT = 1;
    static final int BAL_ALLOW_FOREGROUND = 9;
    static final int BAL_ALLOW_GRACE_PERIOD = 8;
    static final int BAL_ALLOW_NON_APP_VISIBLE_WINDOW = 11;
    static final int BAL_ALLOW_PENDING_INTENT = 5;
    static final int BAL_ALLOW_PERMISSION = 6;
    static final int BAL_ALLOW_SAW_PERMISSION = 7;
    static final int BAL_ALLOW_SDK_SANDBOX = 10;
    static final int BAL_ALLOW_VISIBLE_WINDOW = 4;
    static final int BAL_BLOCK = 0;
    private static final long DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_CREATOR = 296478951;
    private static final int NO_PROCESS_UID = -1;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;

    @com.android.internal.annotations.GuardedBy({"mService.mGlobalLock"})
    private final java.util.HashMap<java.lang.Integer, com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry> mTaskIdToFinishedActivity = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mService.mGlobalLock"})
    private com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry mTopFinishedActivity = null;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BalCode {
    }

    static java.lang.String balCodeToString(int i) {
        switch (i) {
            case 0:
                return "BAL_BLOCK";
            case 1:
                return "BAL_ALLOW_DEFAULT";
            case 2:
                return "BAL_ALLOW_ALLOWLISTED_UID";
            case 3:
                return "BAL_ALLOW_ALLOWLISTED_COMPONENT";
            case 4:
                return "BAL_ALLOW_VISIBLE_WINDOW";
            case 5:
                return "BAL_ALLOW_PENDING_INTENT";
            case 6:
                return "BAL_ALLOW_PERMISSION";
            case 7:
                return "BAL_ALLOW_SAW_PERMISSION";
            case 8:
                return "BAL_ALLOW_GRACE_PERIOD";
            case 9:
                return "BAL_ALLOW_FOREGROUND";
            case 10:
                return "BAL_ALLOW_SDK_SANDBOX";
            case 11:
                return "BAL_ALLOW_NON_APP_VISIBLE_WINDOW";
            default:
                throw new java.lang.IllegalArgumentException("Unexpected value: " + i);
        }
    }

    BackgroundActivityStartController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
    }

    private boolean isHomeApp(int i, @android.annotation.Nullable java.lang.String str) {
        if (this.mService.mHomeProcess != null) {
            return i == this.mService.mHomeProcess.mUid;
        }
        if (str == null) {
            return false;
        }
        android.content.ComponentName defaultHomeActivity = this.mService.getPackageManagerInternalLocked().getDefaultHomeActivity(android.os.UserHandle.getUserId(i));
        return defaultHomeActivity != null && str.equals(defaultHomeActivity.getPackageName());
    }

    @com.android.internal.annotations.VisibleForTesting
    class BalState {
        private final int mAppSwitchState;
        private final boolean mAutoOptInCaller;
        final java.lang.String mAutoOptInReason;
        final android.app.BackgroundStartPrivileges mBalAllowedByPiCreator;
        final android.app.BackgroundStartPrivileges mBalAllowedByPiCreatorWithHardening;
        final android.app.BackgroundStartPrivileges mBalAllowedByPiSender;
        private final com.android.server.wm.WindowProcessController mCallerApp;
        private final java.lang.String mCallingPackage;
        private final int mCallingPid;
        private final int mCallingUid;
        private final boolean mCallingUidHasAnyVisibleWindow;
        private final int mCallingUidProcState;
        private final android.app.ActivityOptions mCheckedOptions;
        private final android.app.BackgroundStartPrivileges mForcedBalByPiSender;
        private final android.content.Intent mIntent;
        private final boolean mIsCallForResult;
        private final boolean mIsCallingUidPersistentSystemProcess;
        private final boolean mIsRealCallingUidPersistentSystemProcess;
        private final com.android.server.am.PendingIntentRecord mOriginatingPendingIntent;
        private final com.android.server.wm.WindowProcessController mRealCallerApp;
        private final java.lang.String mRealCallingPackage;
        private final int mRealCallingPid;
        private final int mRealCallingUid;
        private final boolean mRealCallingUidHasAnyVisibleWindow;
        private final int mRealCallingUidProcState;
        private com.android.server.wm.BackgroundActivityStartController.BalVerdict mResultForCaller;
        private com.android.server.wm.BackgroundActivityStartController.BalVerdict mResultForRealCaller;

        @com.android.internal.annotations.VisibleForTesting
        BalState(int i, int i2, java.lang.String str, int i3, int i4, com.android.server.wm.WindowProcessController windowProcessController, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges, com.android.server.wm.ActivityRecord activityRecord, android.content.Intent intent, android.app.ActivityOptions activityOptions) {
            android.app.BackgroundStartPrivileges backgroundStartPrivileges2;
            android.app.BackgroundStartPrivileges backgroundStartPrivileges3;
            android.app.BackgroundStartPrivileges backgroundStartPrivileges4;
            this.mCallingPackage = str;
            this.mCallingUid = i;
            this.mCallingPid = i2;
            this.mRealCallingUid = i3;
            this.mRealCallingPid = i4;
            this.mCallerApp = windowProcessController;
            this.mForcedBalByPiSender = backgroundStartPrivileges;
            this.mOriginatingPendingIntent = pendingIntentRecord;
            this.mIntent = intent;
            this.mRealCallingPackage = com.android.server.wm.BackgroundActivityStartController.this.mService.getPackageNameIfUnique(i3, i4);
            this.mIsCallForResult = activityRecord != null;
            this.mCheckedOptions = activityOptions;
            int pendingIntentCreatorBackgroundActivityStartMode = activityOptions.getPendingIntentCreatorBackgroundActivityStartMode();
            int pendingIntentBackgroundActivityStartMode = activityOptions.getPendingIntentBackgroundActivityStartMode();
            if (!com.android.window.flags.Flags.balImproveRealCallerVisibilityCheck()) {
                this.mAutoOptInReason = null;
                this.mAutoOptInCaller = false;
            } else if (pendingIntentRecord == null) {
                this.mAutoOptInReason = com.android.server.wm.BackgroundActivityStartController.AUTO_OPT_IN_NOT_PENDING_INTENT;
                this.mAutoOptInCaller = true;
            } else if (this.mIsCallForResult) {
                this.mAutoOptInReason = com.android.server.wm.BackgroundActivityStartController.AUTO_OPT_IN_CALL_FOR_RESULT;
                this.mAutoOptInCaller = false;
            } else if (i == i3 && !com.android.window.flags.Flags.balRequireOptInSameUid()) {
                this.mAutoOptInReason = com.android.server.wm.BackgroundActivityStartController.AUTO_OPT_IN_SAME_UID;
                this.mAutoOptInCaller = false;
            } else {
                this.mAutoOptInReason = null;
                this.mAutoOptInCaller = false;
            }
            if (this.mAutoOptInCaller) {
                if (pendingIntentCreatorBackgroundActivityStartMode == 2) {
                    backgroundStartPrivileges4 = android.app.BackgroundStartPrivileges.NONE;
                } else {
                    backgroundStartPrivileges4 = android.app.BackgroundStartPrivileges.ALLOW_BAL;
                }
                this.mBalAllowedByPiCreator = backgroundStartPrivileges4;
                this.mBalAllowedByPiCreatorWithHardening = backgroundStartPrivileges4;
            } else {
                this.mBalAllowedByPiCreatorWithHardening = getBackgroundStartPrivilegesAllowedByCreator(i, str, activityOptions);
                if (pendingIntentCreatorBackgroundActivityStartMode == 2) {
                    backgroundStartPrivileges2 = android.app.BackgroundStartPrivileges.NONE;
                } else {
                    backgroundStartPrivileges2 = android.app.BackgroundStartPrivileges.ALLOW_BAL;
                }
                this.mBalAllowedByPiCreator = com.android.window.flags.Flags.balRequireOptInByPendingIntentCreator() ? this.mBalAllowedByPiCreatorWithHardening : backgroundStartPrivileges2;
            }
            if (this.mAutoOptInReason != null) {
                if (pendingIntentBackgroundActivityStartMode == 2) {
                    backgroundStartPrivileges3 = android.app.BackgroundStartPrivileges.NONE;
                } else {
                    backgroundStartPrivileges3 = android.app.BackgroundStartPrivileges.ALLOW_BAL;
                }
                this.mBalAllowedByPiSender = backgroundStartPrivileges3;
            } else {
                this.mBalAllowedByPiSender = com.android.server.am.PendingIntentRecord.getBackgroundStartPrivilegesAllowedByCaller(activityOptions, i3, this.mRealCallingPackage);
            }
            this.mAppSwitchState = com.android.server.wm.BackgroundActivityStartController.this.mService.getBalAppSwitchesState();
            this.mCallingUidProcState = com.android.server.wm.BackgroundActivityStartController.this.mService.mActiveUids.getUidState(i);
            this.mIsCallingUidPersistentSystemProcess = this.mCallingUidProcState <= 1;
            this.mCallingUidHasAnyVisibleWindow = com.android.server.wm.BackgroundActivityStartController.this.mService.hasActiveVisibleWindow(i);
            if (i3 == -1) {
                this.mRealCallingUidProcState = 20;
                this.mRealCallingUidHasAnyVisibleWindow = false;
                this.mRealCallerApp = null;
                this.mIsRealCallingUidPersistentSystemProcess = false;
                return;
            }
            if (i == i3) {
                this.mRealCallingUidProcState = this.mCallingUidProcState;
                this.mRealCallingUidHasAnyVisibleWindow = this.mCallingUidHasAnyVisibleWindow;
                this.mRealCallerApp = windowProcessController == null ? com.android.server.wm.BackgroundActivityStartController.this.mService.getProcessController(i4, i3) : windowProcessController;
                this.mIsRealCallingUidPersistentSystemProcess = this.mIsCallingUidPersistentSystemProcess;
                return;
            }
            this.mRealCallingUidProcState = com.android.server.wm.BackgroundActivityStartController.this.mService.mActiveUids.getUidState(i3);
            this.mRealCallingUidHasAnyVisibleWindow = com.android.server.wm.BackgroundActivityStartController.this.mService.hasActiveVisibleWindow(i3);
            this.mRealCallerApp = com.android.server.wm.BackgroundActivityStartController.this.mService.getProcessController(i4, i3);
            this.mIsRealCallingUidPersistentSystemProcess = this.mRealCallingUidProcState <= 1;
        }

        private android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesAllowedByCreator(int i, java.lang.String str, android.app.ActivityOptions activityOptions) {
            switch (activityOptions.getPendingIntentCreatorBackgroundActivityStartMode()) {
                case 0:
                    return str != null ? android.app.compat.CompatChanges.isChangeEnabled(com.android.server.wm.BackgroundActivityStartController.DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_CREATOR, str, android.os.UserHandle.getUserHandleForUid(i)) ? android.app.BackgroundStartPrivileges.NONE : android.app.BackgroundStartPrivileges.ALLOW_BAL : android.app.compat.CompatChanges.isChangeEnabled(com.android.server.wm.BackgroundActivityStartController.DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_CREATOR, i) ? android.app.BackgroundStartPrivileges.NONE : android.app.BackgroundStartPrivileges.ALLOW_BAL;
                case 1:
                    return android.app.BackgroundStartPrivileges.ALLOW_BAL;
                case 2:
                    return android.app.BackgroundStartPrivileges.NONE;
                default:
                    throw new java.lang.IllegalStateException("unsupported BackgroundActivityStartMode: " + activityOptions.getPendingIntentCreatorBackgroundActivityStartMode());
            }
        }

        private java.lang.String getDebugPackageName(java.lang.String str, int i) {
            if (str != null) {
                return str;
            }
            if (i == 0) {
                return "root[debugOnly]";
            }
            java.lang.String nameForUid = com.android.server.wm.BackgroundActivityStartController.this.mService.getPackageManagerInternalLocked().getNameForUid(i);
            if (nameForUid == null) {
                nameForUid = "uid=" + i;
            }
            return nameForUid + "[debugOnly]";
        }

        private int getTargetSdk(java.lang.String str) {
            if (str == null) {
                return -1;
            }
            try {
                return com.android.server.wm.BackgroundActivityStartController.this.mService.mContext.getPackageManager().getTargetSdkVersion(str);
            } catch (java.lang.Exception e) {
                return -1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasRealCaller() {
            return this.mRealCallingUid != -1;
        }

        boolean isPendingIntent() {
            return this.mOriginatingPendingIntent != null && hasRealCaller();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean callerIsRealCaller() {
            return this.mCallingUid == this.mRealCallingUid;
        }

        public void setResultForCaller(com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict) {
            com.android.internal.util.Preconditions.checkState(this.mResultForCaller == null, "mResultForCaller can only be set once");
            this.mResultForCaller = balVerdict;
        }

        public void setResultForRealCaller(com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict) {
            com.android.internal.util.Preconditions.checkState(this.mResultForRealCaller == null, "mResultForRealCaller can only be set once");
            this.mResultForRealCaller = balVerdict;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String dump() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(2048);
            sb.append("[callingPackage: ");
            sb.append(getDebugPackageName(this.mCallingPackage, this.mCallingUid));
            sb.append("; callingPackageTargetSdk: ");
            sb.append(getTargetSdk(this.mCallingPackage));
            sb.append("; callingUid: ");
            sb.append(this.mCallingUid);
            sb.append("; callingPid: ");
            sb.append(this.mCallingPid);
            sb.append("; appSwitchState: ");
            sb.append(this.mAppSwitchState);
            sb.append("; callingUidHasAnyVisibleWindow: ");
            sb.append(this.mCallingUidHasAnyVisibleWindow);
            sb.append("; callingUidProcState: ");
            sb.append(android.util.DebugUtils.valueToString(android.app.ActivityManager.class, "PROCESS_STATE_", this.mCallingUidProcState));
            sb.append("; isCallingUidPersistentSystemProcess: ");
            sb.append(this.mIsCallingUidPersistentSystemProcess);
            sb.append("; forcedBalByPiSender: ");
            sb.append(this.mForcedBalByPiSender);
            sb.append("; intent: ");
            sb.append(this.mIntent);
            sb.append("; callerApp: ");
            sb.append(this.mCallerApp);
            if (this.mCallerApp != null) {
                sb.append("; inVisibleTask: ");
                sb.append(this.mCallerApp.hasActivityInVisibleTask());
            }
            sb.append("; balAllowedByPiCreator: ");
            sb.append(this.mBalAllowedByPiCreator);
            sb.append("; balAllowedByPiCreatorWithHardening: ");
            sb.append(this.mBalAllowedByPiCreatorWithHardening);
            sb.append("; resultIfPiCreatorAllowsBal: ");
            sb.append(this.mResultForCaller);
            sb.append("; hasRealCaller: ");
            sb.append(hasRealCaller());
            sb.append("; isCallForResult: ");
            sb.append(this.mIsCallForResult);
            sb.append("; isPendingIntent: ");
            sb.append(isPendingIntent());
            sb.append("; autoOptInReason: ");
            sb.append(this.mAutoOptInReason);
            if (hasRealCaller()) {
                sb.append("; realCallingPackage: ");
                sb.append(getDebugPackageName(this.mRealCallingPackage, this.mRealCallingUid));
                sb.append("; realCallingPackageTargetSdk: ");
                sb.append(getTargetSdk(this.mRealCallingPackage));
                sb.append("; realCallingUid: ");
                sb.append(this.mRealCallingUid);
                sb.append("; realCallingPid: ");
                sb.append(this.mRealCallingPid);
                sb.append("; realCallingUidHasAnyVisibleWindow: ");
                sb.append(this.mRealCallingUidHasAnyVisibleWindow);
                sb.append("; realCallingUidProcState: ");
                sb.append(android.util.DebugUtils.valueToString(android.app.ActivityManager.class, "PROCESS_STATE_", this.mRealCallingUidProcState));
                sb.append("; isRealCallingUidPersistentSystemProcess: ");
                sb.append(this.mIsRealCallingUidPersistentSystemProcess);
                sb.append("; originatingPendingIntent: ");
                sb.append(this.mOriginatingPendingIntent);
                sb.append("; realCallerApp: ");
                sb.append(this.mRealCallerApp);
                if (this.mRealCallerApp != null) {
                    sb.append("; realInVisibleTask: ");
                    sb.append(this.mRealCallerApp.hasActivityInVisibleTask());
                }
                sb.append("; balAllowedByPiSender: ");
                sb.append(this.mBalAllowedByPiSender);
                sb.append("; resultIfPiSenderAllowsBal: ");
                sb.append(this.mResultForRealCaller);
            }
            sb.append("]");
            return sb.toString();
        }

        public boolean isPendingIntentBalAllowedByPermission() {
            return com.android.server.am.PendingIntentRecord.isPendingIntentBalAllowedByPermission(this.mCheckedOptions);
        }

        public boolean callerExplicitOptInOrAutoOptIn() {
            if (this.mAutoOptInCaller) {
                return !callerExplicitOptOut();
            }
            return this.mCheckedOptions.getPendingIntentCreatorBackgroundActivityStartMode() == 1;
        }

        public boolean realCallerExplicitOptInOrAutoOptIn() {
            if (this.mAutoOptInReason != null) {
                return !realCallerExplicitOptOut();
            }
            return this.mCheckedOptions.getPendingIntentBackgroundActivityStartMode() == 1;
        }

        public boolean callerExplicitOptOut() {
            return this.mCheckedOptions.getPendingIntentCreatorBackgroundActivityStartMode() == 2;
        }

        public boolean realCallerExplicitOptOut() {
            return this.mCheckedOptions.getPendingIntentBackgroundActivityStartMode() == 2;
        }

        public boolean callerExplicitOptInOrOut() {
            return this.mCheckedOptions.getPendingIntentCreatorBackgroundActivityStartMode() != 0;
        }

        public boolean realCallerExplicitOptInOrOut() {
            return this.mCheckedOptions.getPendingIntentBackgroundActivityStartMode() != 0;
        }

        public java.lang.String toString() {
            return dump();
        }
    }

    static class BalVerdict {
        private final boolean mBackground;
        private boolean mBasedOnRealCaller;
        private final int mCode;
        private final java.lang.String mMessage;
        private boolean mOnlyCreatorAllows;
        private java.lang.String mProcessInfo;
        static final com.android.server.wm.BackgroundActivityStartController.BalVerdict BLOCK = new com.android.server.wm.BackgroundActivityStartController.BalVerdict(0, false, "Blocked");
        static final com.android.server.wm.BackgroundActivityStartController.BalVerdict ALLOW_BY_DEFAULT = new com.android.server.wm.BackgroundActivityStartController.BalVerdict(1, false, "Default");
        static final com.android.server.wm.BackgroundActivityStartController.BalVerdict ALLOW_PRIVILEGED = new com.android.server.wm.BackgroundActivityStartController.BalVerdict(2, false, "PRIVILEGED");

        BalVerdict(int i, boolean z, java.lang.String str) {
            this.mBackground = z;
            this.mCode = i;
            this.mMessage = str;
        }

        public com.android.server.wm.BackgroundActivityStartController.BalVerdict withProcessInfo(java.lang.String str, com.android.server.wm.WindowProcessController windowProcessController) {
            this.mProcessInfo = str + " (uid=" + windowProcessController.mUid + ",pid=" + windowProcessController.getPid() + ")";
            return this;
        }

        boolean blocks() {
            return this.mCode == 0;
        }

        boolean allows() {
            return !blocks();
        }

        void setOnlyCreatorAllows(boolean z) {
            this.mOnlyCreatorAllows = z;
        }

        boolean onlyCreatorAllows() {
            return this.mOnlyCreatorAllows;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.BackgroundActivityStartController.BalVerdict setBasedOnRealCaller() {
            this.mBasedOnRealCaller = true;
            return this;
        }

        public java.lang.String toString() {
            return com.android.server.wm.BackgroundActivityStartController.balCodeToString(this.mCode);
        }

        public int getRawCode() {
            return this.mCode;
        }

        public int getCode() {
            if (this.mBasedOnRealCaller && this.mCode != 0) {
                return 5;
            }
            return this.mCode;
        }
    }

    com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart(int i, int i2, java.lang.String str, int i3, int i4, com.android.server.wm.WindowProcessController windowProcessController, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges, com.android.server.wm.ActivityRecord activityRecord, android.content.Intent intent, android.app.ActivityOptions activityOptions) {
        android.app.ActivityOptions activityOptions2;
        com.android.server.wm.BackgroundActivityStartController.BalVerdict basedOnRealCaller;
        if (activityOptions != null) {
            activityOptions2 = activityOptions;
        } else {
            activityOptions2 = ACTIVITY_OPTIONS_SYSTEM_DEFINED;
        }
        com.android.server.wm.BackgroundActivityStartController.BalState balState = new com.android.server.wm.BackgroundActivityStartController.BalState(i, i2, str, i3, i4, windowProcessController, pendingIntentRecord, backgroundStartPrivileges, activityRecord, intent, activityOptions2);
        boolean z = false;
        if (android.os.Process.isSdkSandboxUid(balState.mRealCallingUid)) {
            if (this.mService.hasActiveVisibleWindow(android.os.Process.getAppUidForSdkSandboxUid(balState.mRealCallingUid))) {
                balState.setResultForRealCaller(new com.android.server.wm.BackgroundActivityStartController.BalVerdict(10, false, "uid in SDK sandbox has visible (non-toast) window"));
                return allowBasedOnRealCaller(balState);
            }
        }
        com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStartAllowedByCaller = checkBackgroundActivityStartAllowedByCaller(balState);
        balState.setResultForCaller(checkBackgroundActivityStartAllowedByCaller);
        if (!balState.hasRealCaller()) {
            if (checkBackgroundActivityStartAllowedByCaller.allows()) {
                return allowBasedOnCaller(balState);
            }
            return abortLaunch(balState);
        }
        if (balState.callerIsRealCaller() && checkBackgroundActivityStartAllowedByCaller.allows()) {
            basedOnRealCaller = checkBackgroundActivityStartAllowedByCaller;
        } else {
            basedOnRealCaller = checkBackgroundActivityStartAllowedBySender(balState).setBasedOnRealCaller();
        }
        balState.setResultForRealCaller(basedOnRealCaller);
        if (balState.isPendingIntent()) {
            checkBackgroundActivityStartAllowedByCaller.setOnlyCreatorAllows(checkBackgroundActivityStartAllowedByCaller.allows() && basedOnRealCaller.blocks());
        }
        if (checkBackgroundActivityStartAllowedByCaller.allows() && balState.callerExplicitOptInOrAutoOptIn()) {
            return allowBasedOnCaller(balState);
        }
        if (basedOnRealCaller.allows() && balState.realCallerExplicitOptInOrAutoOptIn()) {
            return allowBasedOnRealCaller(balState);
        }
        boolean z2 = checkBackgroundActivityStartAllowedByCaller.allows() && !balState.callerExplicitOptOut();
        if (basedOnRealCaller.allows() && !balState.realCallerExplicitOptOut()) {
            z = true;
        }
        if (z2 && balState.mBalAllowedByPiCreator.allowsBackgroundActivityStarts()) {
            android.util.Slog.wtf(TAG, "With Android 15 BAL hardening this activity start may be blocked if the PI creator upgrades target_sdk to 35+!  (missing opt in by PI creator)!" + balState.dump());
            showBalRiskToast();
            return allowBasedOnCaller(balState);
        }
        if (z && balState.mBalAllowedByPiSender.allowsBackgroundActivityStarts()) {
            android.util.Slog.wtf(TAG, "With Android 14 BAL hardening this activity start will be blocked if the PI sender upgrades target_sdk to 34+!  (missing opt in by PI sender)!" + balState.dump());
            showBalRiskToast();
            return allowBasedOnRealCaller(balState);
        }
        if (z2 || z) {
            android.util.Slog.w(TAG, "Without BAL hardening this activity start would be allowed");
        }
        return abortLaunch(balState);
    }

    private com.android.server.wm.BackgroundActivityStartController.BalVerdict allowBasedOnCaller(com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        return statsLog(balState.mResultForCaller, balState);
    }

    private com.android.server.wm.BackgroundActivityStartController.BalVerdict allowBasedOnRealCaller(com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        return statsLog(balState.mResultForRealCaller, balState);
    }

    private com.android.server.wm.BackgroundActivityStartController.BalVerdict abortLaunch(com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        android.util.Slog.wtf(TAG, "Background activity launch blocked! " + balState.dump());
        showBalBlockedToast();
        return statsLog(com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK, balState);
    }

    com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStartAllowedByCaller(com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        int i = balState.mCallingUid;
        int i2 = balState.mCallingPid;
        java.lang.String str = balState.mCallingPackage;
        com.android.server.wm.WindowProcessController windowProcessController = balState.mCallerApp;
        int appId = android.os.UserHandle.getAppId(i);
        if (i == 0 || appId == 1000 || appId == 1027) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(2, false, "Important callingUid");
        }
        if (isHomeApp(i, str)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, false, "Home app");
        }
        com.android.server.wm.WindowState currentInputMethodWindow = this.mService.mRootWindowContainer.getCurrentInputMethodWindow();
        if (currentInputMethodWindow != null && appId == currentInputMethodWindow.mOwnerUid) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, false, "Active ime");
        }
        int balAppSwitchesState = this.mService.getBalAppSwitchesState();
        int uidState = this.mService.mActiveUids.getUidState(i);
        boolean hasActiveVisibleWindow = this.mService.hasActiveVisibleWindow(i);
        boolean z = uidState <= 1;
        if ((balAppSwitchesState == 2 || balAppSwitchesState == 1) && hasActiveVisibleWindow) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(4, false, "callingUid has visible window");
        }
        if (this.mService.mActiveUids.hasNonAppVisibleWindow(i)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(11, false, "callingUid has non-app visible window");
        }
        if (z) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, false, "callingUid is persistent system process");
        }
        if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", i2, i) == 0) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(6, true, "START_ACTIVITIES_FROM_BACKGROUND permission granted");
        }
        if (this.mSupervisor.mRecentTasks.isCallerRecents(i)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, true, "Recents Component");
        }
        if (this.mService.isDeviceOwner(i)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, true, "Device Owner");
        }
        if (this.mService.isAffiliatedProfileOwner(i)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, true, "Affiliated Profile Owner");
        }
        if (this.mService.isAssociatedCompanionApp(android.os.UserHandle.getUserId(i), i)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, true, "Companion App");
        }
        if (this.mService.hasSystemAlertWindowPermission(i, i2, str)) {
            android.util.Slog.w(TAG, "Background activity start for " + str + " allowed because SYSTEM_ALERT_WINDOW permission is granted.");
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(7, true, "SYSTEM_ALERT_WINDOW permission is granted");
        }
        if (isSystemExemptFlagEnabled() && this.mService.getAppOpsManager().checkOpNoThrow(130, i, str) == 0) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(6, true, "OP_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION appop is granted");
        }
        com.android.server.wm.BackgroundActivityStartController.BalVerdict checkProcessAllowsBal = checkProcessAllowsBal(windowProcessController, balState);
        if (checkProcessAllowsBal.allows()) {
            return checkProcessAllowsBal;
        }
        return com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK;
    }

    com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStartAllowedBySender(com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        boolean z = true;
        if (balState.isPendingIntentBalAllowedByPermission() && android.app.ActivityManager.checkComponentPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", balState.mRealCallingUid, -1, true) == 0) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(6, false, "realCallingUid has BAL permission.");
        }
        if (balState.mAppSwitchState != 2 && balState.mAppSwitchState != 1 && !isHomeApp(balState.mRealCallingUid, balState.mRealCallingPackage)) {
            z = false;
        }
        if (com.android.window.flags.Flags.balImproveRealCallerVisibilityCheck()) {
            if (z && balState.mRealCallingUidHasAnyVisibleWindow) {
                return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(4, false, "realCallingUid has visible window");
            }
            if (this.mService.mActiveUids.hasNonAppVisibleWindow(balState.mRealCallingUid)) {
                return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(11, false, "realCallingUid has non-app visible window");
            }
        } else if (balState.mRealCallingUidHasAnyVisibleWindow) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(4, false, "realCallingUid has visible (non-toast) window.");
        }
        if (balState.mForcedBalByPiSender.allowsBackgroundActivityStarts() && balState.mIsRealCallingUidPersistentSystemProcess) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(2, false, "realCallingUid is persistent system process AND intent sender forced to allow.");
        }
        if (this.mService.isAssociatedCompanionApp(android.os.UserHandle.getUserId(balState.mRealCallingUid), balState.mRealCallingUid)) {
            return new com.android.server.wm.BackgroundActivityStartController.BalVerdict(3, false, "realCallingUid is a companion app.");
        }
        com.android.server.wm.BackgroundActivityStartController.BalVerdict checkProcessAllowsBal = checkProcessAllowsBal(balState.mRealCallerApp, balState);
        if (checkProcessAllowsBal.allows()) {
            return checkProcessAllowsBal;
        }
        return com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.BackgroundActivityStartController.BalVerdict checkProcessAllowsBal(com.android.server.wm.WindowProcessController windowProcessController, com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        if (windowProcessController == null) {
            return com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK;
        }
        com.android.server.wm.BackgroundActivityStartController.BalVerdict areBackgroundActivityStartsAllowed = windowProcessController.areBackgroundActivityStartsAllowed(balState.mAppSwitchState);
        if (areBackgroundActivityStartsAllowed.allows()) {
            return areBackgroundActivityStartsAllowed.withProcessInfo("callerApp process", windowProcessController);
        }
        android.util.ArraySet<com.android.server.wm.WindowProcessController> processes = this.mService.mProcessMap.getProcesses(windowProcessController.mUid);
        if (processes != null) {
            for (int size = processes.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowProcessController valueAt = processes.valueAt(size);
                if (valueAt != windowProcessController) {
                    com.android.server.wm.BackgroundActivityStartController.BalVerdict areBackgroundActivityStartsAllowed2 = valueAt.areBackgroundActivityStartsAllowed(balState.mAppSwitchState);
                    if (areBackgroundActivityStartsAllowed2.allows()) {
                        return areBackgroundActivityStartsAllowed2.withProcessInfo("process", valueAt);
                    }
                }
            }
        }
        return com.android.server.wm.BackgroundActivityStartController.BalVerdict.BLOCK;
    }

    boolean checkActivityAllowedToStart(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord2, boolean z, boolean z2, @android.annotation.Nullable com.android.server.wm.Task task, int i, int i2, int i3, int i4, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea2;
        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart blockActivityStart;
        if (i2 == 2) {
            return true;
        }
        r3 = false;
        boolean z3 = false;
        boolean z4 = z || (i & 268435456) == 268435456;
        if (z4 && (i2 == 3 || i2 == 6 || i2 == 5 || i2 == 7 || i2 == 4 || i2 == 11)) {
            return true;
        }
        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart blockActivityStart2 = new com.android.server.wm.BackgroundActivityStartController.BlockActivityStart();
        if (activityRecord != null) {
            com.android.server.wm.Task task2 = activityRecord.getTask();
            com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkTopActivityForAsm = checkTopActivityForAsm(z4 ? task2 : task, activityRecord.getUid(), activityRecord, blockActivityStart2);
            if (z4 && checkTopActivityForAsm.mTopActivityMatchesSource) {
                if (task2 != null && (task2.isVisible() || task2 == task)) {
                    z3 = true;
                }
                checkTopActivityForAsm.mTopActivityMatchesSource = z3;
            }
            blockActivityStart = checkTopActivityForAsm;
        } else if (task != null && (!z4 || z2)) {
            blockActivityStart = checkTopActivityForAsm(task, i3, null, blockActivityStart2);
        } else {
            if (task != null && task.getDisplayArea() != null) {
                taskDisplayArea2 = task.getDisplayArea();
            } else {
                taskDisplayArea2 = taskDisplayArea;
            }
            if (taskDisplayArea2 == null) {
                blockActivityStart = blockActivityStart2;
            } else {
                java.util.ArrayList<com.android.server.wm.Task> visibleTasks = taskDisplayArea2.getVisibleTasks();
                for (int i5 = 0; i5 < visibleTasks.size(); i5++) {
                    com.android.server.wm.Task task3 = visibleTasks.get(i5);
                    if (visibleTasks.size() == 1 && task3.isActivityTypeHomeOrRecents()) {
                        blockActivityStart2.optedIn(task3.getTopMostActivity());
                    } else {
                        blockActivityStart2 = checkTopActivityForAsm(task3, i3, null, blockActivityStart2);
                    }
                }
                blockActivityStart = blockActivityStart2;
            }
        }
        if (blockActivityStart.mTopActivityMatchesSource) {
            return true;
        }
        return logAsmFailureAndCheckFeatureEnabled(activityRecord, i3, i4, z, z2, task, activityRecord2, i2, i, blockActivityStart, z4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0173 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean logAsmFailureAndCheckFeatureEnabled(com.android.server.wm.ActivityRecord activityRecord, int i, int i2, boolean z, boolean z2, com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord2, int i3, int i4, com.android.server.wm.BackgroundActivityStartController.BlockActivityStart blockActivityStart, boolean z3) {
        int i5;
        com.android.server.wm.Task task2;
        boolean z4;
        com.android.server.wm.ActivityRecord activityRecord3;
        com.android.server.wm.ActivityRecord activityRecord4 = activityRecord;
        com.android.server.wm.ActivityRecord activity = task == null ? null : task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$logAsmFailureAndCheckFeatureEnabled$0;
                lambda$logAsmFailureAndCheckFeatureEnabled$0 = com.android.server.wm.BackgroundActivityStartController.lambda$logAsmFailureAndCheckFeatureEnabled$0((com.android.server.wm.ActivityRecord) obj);
                return lambda$logAsmFailureAndCheckFeatureEnabled$0;
            }
        });
        if (z || activityRecord4 == null) {
            i5 = 3;
        } else if (activityRecord.getTask().equals(task)) {
            i5 = 1;
        } else {
            i5 = 2;
        }
        boolean z5 = blockActivityStart.mTopActivityOptedIn && com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i);
        com.android.server.wm.ActivityRecord activityRecord5 = activity;
        java.lang.String debugInfoForActivitySecurity = getDebugInfoForActivitySecurity("Launch", activityRecord, activityRecord2, task, activity, i2, i3, z5, z3, z2, allowedByAsmGracePeriod(i, activityRecord, task, i3, z3, z2), blockActivityStart.mActivityOptedIn);
        int uid = activityRecord4 != null ? activityRecord.getUid() : i;
        java.lang.String str = activityRecord4 != null ? activityRecord4.info.name : null;
        int uid2 = activityRecord5 != null ? activityRecord5.getUid() : -1;
        java.lang.String str2 = activityRecord5 != null ? activityRecord5.info.name : null;
        if (z || activityRecord4 == null) {
            task2 = task;
        } else {
            task2 = task;
            if (task2 != null && task2.equals(activityRecord.getTask())) {
                z4 = false;
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ACTIVITY_ACTION_BLOCKED, uid, str, uid2, str2, z4, activityRecord2.getUid(), activityRecord2.info.name, activityRecord2.intent.getAction(), i4, i5, 10, task2 == null && activityRecord4 != null && !task2.equals(activityRecord.getTask()) && task.isVisible(), i3, debugInfoForActivitySecurity);
                java.lang.String str3 = activityRecord2.launchedFromPackage;
                if (com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
                    activityRecord3 = activityRecord5;
                } else {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("go/android-asm");
                    sb.append(z5 ? " blocked " : " would block ");
                    activityRecord3 = activityRecord5;
                    sb.append((java.lang.Object) com.android.server.wm.ActivityTaskSupervisor.getApplicationLabel(this.mService.mContext.getPackageManager(), str3));
                    showToast(sb.toString());
                    android.util.Slog.i(TAG, debugInfoForActivitySecurity);
                }
                if (z5) {
                    return true;
                }
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("[ASM] Abort Launching r: ");
                sb2.append(activityRecord2);
                sb2.append(" as source: ");
                if (activityRecord4 == null) {
                    activityRecord4 = str3;
                }
                sb2.append(activityRecord4);
                sb2.append(" is in background. New task: ");
                sb2.append(z);
                sb2.append(". Top activity: ");
                sb2.append(activityRecord3);
                sb2.append(". BAL Code: ");
                sb2.append(balCodeToString(i3));
                android.util.Slog.e(TAG, sb2.toString());
                return false;
            }
        }
        z4 = true;
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ACTIVITY_ACTION_BLOCKED, uid, str, uid2, str2, z4, activityRecord2.getUid(), activityRecord2.info.name, activityRecord2.intent.getAction(), i4, i5, 10, task2 == null && activityRecord4 != null && !task2.equals(activityRecord.getTask()) && task.isVisible(), i3, debugInfoForActivitySecurity);
        java.lang.String str32 = activityRecord2.launchedFromPackage;
        if (com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
        }
        if (z5) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$logAsmFailureAndCheckFeatureEnabled$0(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isAlwaysOnTop()) ? false : true;
    }

    private void showBalBlockedToast() {
        if (com.android.window.flags.Flags.balShowToastsBlocked()) {
            showToast("BAL blocked. go/debug-bal");
        }
    }

    private void showBalRiskToast() {
        if (com.android.window.flags.Flags.balShowToasts()) {
            showToast("BAL allowed in compat mode. go/debug-bal");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$1(java.lang.String str) {
        android.widget.Toast.makeText(this.mService.mContext, str, 1).show();
    }

    @com.android.internal.annotations.VisibleForTesting
    void showToast(final java.lang.String str) {
        com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.BackgroundActivityStartController.this.lambda$showToast$1(str);
            }
        });
    }

    void clearTopIfNeeded(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord2, final int i, final int i2, int i3, int i4) {
        java.lang.String str;
        if ((i3 & 268435456) != 268435456 || i4 == 2) {
            return;
        }
        final int uid = activityRecord2.getUid();
        java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate = new java.util.function.Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$clearTopIfNeeded$2;
                lambda$clearTopIfNeeded$2 = com.android.server.wm.BackgroundActivityStartController.lambda$clearTopIfNeeded$2(uid, i, i2, (com.android.server.wm.ActivityRecord) obj);
                return lambda$clearTopIfNeeded$2;
            }
        };
        com.android.server.wm.ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity == null || predicate.test(topMostActivity)) {
            return;
        }
        int[] iArr = new int[1];
        boolean shouldRestrictActivitySwitch = com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i);
        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkCrossUidActivitySwitchFromBelow = checkCrossUidActivitySwitchFromBelow(topMostActivity, i, new com.android.server.wm.BackgroundActivityStartController.BlockActivityStart());
        if (shouldRestrictActivitySwitch && checkCrossUidActivitySwitchFromBelow.mTopActivityOptedIn) {
            com.android.server.wm.ActivityRecord activity = task.getActivity(predicate);
            if (activity == null) {
                activity = activityRecord2;
            }
            task.performClearTop(activity, i3, iArr);
            if (iArr[0] > 0) {
                android.util.Slog.w(TAG, "Cleared top n: " + iArr[0] + " activities from task t: " + task + " not matching top uid: " + i);
            }
        }
        if (com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
            if (!shouldRestrictActivitySwitch || iArr[0] > 0) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                if (shouldRestrictActivitySwitch) {
                    str = "Top activities cleared by ";
                } else {
                    str = "Top activities would be cleared by ";
                }
                sb.append(str);
                sb.append("go/android-asm");
                showToast(sb.toString());
                android.util.Slog.i(TAG, getDebugInfoForActivitySecurity("Clear Top", activityRecord, activityRecord2, task, topMostActivity, i2, i4, shouldRestrictActivitySwitch, true, false, false, checkCrossUidActivitySwitchFromBelow.mActivityOptedIn));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$clearTopIfNeeded$2(int i, int i2, int i3, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.isUid(i) || activityRecord.isUid(i2) || activityRecord.isUid(i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    void checkActivityAllowedToClearTask(@android.annotation.NonNull com.android.server.wm.Task task, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        int i3;
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        java.lang.String applicationLabel;
        java.lang.String str2;
        if (i != 1000 && task.isVisible() && !task.inMultiWindowMode() && (i3 = checkBackgroundActivityStartAllowedByCaller(new com.android.server.wm.BackgroundActivityStartController.BalState(i, i2, this.mService.mContext.getPackageManager().getNameForUid(i), -1, -1, null, null, null, null, null, android.app.ActivityOptions.makeBasic())).mCode) != 2 && i3 != 3 && i3 != 6 && i3 != 7 && i3 != 4 && i3 != 11 && (taskDisplayArea = task.getTaskDisplayArea()) != null) {
            com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkTopActivityForAsm = checkTopActivityForAsm(task, i, null, new com.android.server.wm.BackgroundActivityStartController.BlockActivityStart());
            if (checkTopActivityForAsm.mTopActivityMatchesSource) {
                return;
            }
            com.android.server.wm.ActivityRecord activity = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkActivityAllowedToClearTask$3;
                    lambda$checkActivityAllowedToClearTask$3 = com.android.server.wm.BackgroundActivityStartController.lambda$checkActivityAllowedToClearTask$3((com.android.server.wm.ActivityRecord) obj);
                    return lambda$checkActivityAllowedToClearTask$3;
                }
            });
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ACTIVITY_ACTION_BLOCKED, i, str, activity == null ? -1 : activity.getUid(), activity != null ? activity.info.name : null, false, -1, (java.lang.String) null, (java.lang.String) null, 0, 4, 10, false, -1, (java.lang.String) null);
            boolean z = com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i) && checkTopActivityForAsm.mTopActivityOptedIn;
            android.content.pm.PackageManager packageManager = this.mService.mContext.getPackageManager();
            java.lang.String nameForUid = packageManager.getNameForUid(i);
            if (nameForUid == null) {
                applicationLabel = java.lang.String.valueOf(i);
                str2 = applicationLabel;
            } else {
                applicationLabel = com.android.server.wm.ActivityTaskSupervisor.getApplicationLabel(packageManager, nameForUid);
                str2 = nameForUid;
            }
            if (com.android.server.wm.ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("go/android-asm");
                sb.append(z ? " returned home due to " : " would return home due to ");
                sb.append((java.lang.Object) applicationLabel);
                showToast(sb.toString());
            }
            if (z) {
                android.util.Slog.w(TAG, "[ASM] Return to home as source: " + str2 + " is not on top of task t: " + task);
                taskDisplayArea.moveHomeActivityToTop("taskRemoved");
                return;
            }
            android.util.Slog.i(TAG, "[ASM] Would return to home as source: " + str2 + " is not on top of task t: " + task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkActivityAllowedToClearTask$3(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isAlwaysOnTop()) ? false : true;
    }

    private com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkTopActivityForAsm(@android.annotation.NonNull com.android.server.wm.Task task, final int i, @android.annotation.Nullable final com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.BackgroundActivityStartController.BlockActivityStart blockActivityStart) {
        if (activityRecord != null && activityRecord.isVisibleRequested()) {
            return blockActivityStart.matchesSource();
        }
        com.android.server.wm.ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity == null) {
            android.util.Slog.wtf(TAG, "Activities for task: " + task + " not found.");
            return blockActivityStart.optedIn(topMostActivity);
        }
        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkCrossUidActivitySwitchFromBelow = checkCrossUidActivitySwitchFromBelow(topMostActivity, i, blockActivityStart);
        if (checkCrossUidActivitySwitchFromBelow.mTopActivityMatchesSource) {
            return checkCrossUidActivitySwitchFromBelow;
        }
        if (task.forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$checkTopActivityForAsm$4;
                lambda$checkTopActivityForAsm$4 = com.android.server.wm.BackgroundActivityStartController.lambda$checkTopActivityForAsm$4(i, (com.android.server.wm.ActivityRecord) obj);
                return lambda$checkTopActivityForAsm$4;
            }
        })) {
            return checkCrossUidActivitySwitchFromBelow.matchesSource();
        }
        java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate = new java.util.function.Predicate() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$checkTopActivityForAsm$5;
                lambda$checkTopActivityForAsm$5 = com.android.server.wm.BackgroundActivityStartController.lambda$checkTopActivityForAsm$5(com.android.server.wm.ActivityRecord.this, (com.android.server.wm.ActivityRecord) obj);
                return lambda$checkTopActivityForAsm$5;
            }
        };
        com.android.server.wm.ActivityRecord activity = task.getActivity(predicate);
        if (activity == null) {
            return checkCrossUidActivitySwitchFromBelow;
        }
        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkCrossUidActivitySwitchFromBelow2 = checkCrossUidActivitySwitchFromBelow(activity, i, checkCrossUidActivitySwitchFromBelow);
        if (checkCrossUidActivitySwitchFromBelow2.mTopActivityMatchesSource) {
            return checkCrossUidActivitySwitchFromBelow2;
        }
        com.android.server.wm.TaskFragment taskFragment = activity.getTaskFragment();
        if (taskFragment == null) {
            return checkCrossUidActivitySwitchFromBelow2;
        }
        com.android.server.wm.TaskFragment adjacentTaskFragment = taskFragment.getAdjacentTaskFragment();
        if (adjacentTaskFragment == null) {
            return checkCrossUidActivitySwitchFromBelow2;
        }
        com.android.server.wm.ActivityRecord activity2 = adjacentTaskFragment.getActivity(predicate);
        if (activity2 == null) {
            return checkCrossUidActivitySwitchFromBelow2;
        }
        return checkCrossUidActivitySwitchFromBelow(activity2, i, checkCrossUidActivitySwitchFromBelow2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkTopActivityForAsm$4(int i, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.isUid(i) && activityRecord.isVisibleRequested();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkTopActivityForAsm$5(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return activityRecord2.equals(activityRecord) || !(activityRecord2.finishing || activityRecord2.isAlwaysOnTop());
    }

    private com.android.server.wm.BackgroundActivityStartController.BlockActivityStart checkCrossUidActivitySwitchFromBelow(com.android.server.wm.ActivityRecord activityRecord, int i, com.android.server.wm.BackgroundActivityStartController.BlockActivityStart blockActivityStart) {
        if (activityRecord.isUid(i)) {
            return blockActivityStart.matchesSource();
        }
        if (activityRecord.mAllowCrossUidActivitySwitchFromBelow) {
            blockActivityStart.mTopActivityOptedIn = false;
            return blockActivityStart.matchesSource();
        }
        if (!android.app.compat.CompatChanges.isChangeEnabled(230590090L, activityRecord.getUid())) {
            return blockActivityStart;
        }
        if (activityRecord.isUid(1000)) {
            return blockActivityStart.optedIn(activityRecord);
        }
        java.lang.String str = activityRecord.packageName;
        if (str == null) {
            android.util.Slog.wtf(TAG, "Package name: " + activityRecord + " not found.");
            return blockActivityStart.optedIn(activityRecord);
        }
        try {
            return this.mService.mContext.getPackageManager().getApplicationInfo(str, 0).allowCrossUidActivitySwitchFromBelow ? blockActivityStart : blockActivityStart.optedIn(activityRecord);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.wtf(TAG, "Package name: " + str + " not found.");
            return blockActivityStart.optedIn(activityRecord);
        }
    }

    private java.lang.String getDebugInfoForActivitySecurity(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable final com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull final com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable final com.android.server.wm.ActivityRecord activityRecord3, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, com.android.server.wm.ActivityRecord activityRecord4) {
        com.android.server.wm.Task task2 = task;
        final java.util.function.Function function = new java.util.function.Function() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$getDebugInfoForActivitySecurity$6;
                lambda$getDebugInfoForActivitySecurity$6 = com.android.server.wm.BackgroundActivityStartController.lambda$getDebugInfoForActivitySecurity$6(com.android.server.wm.ActivityRecord.this, activityRecord3, activityRecord2, (com.android.server.wm.ActivityRecord) obj);
                return lambda$getDebugInfoForActivitySecurity$6;
            }
        };
        final java.util.StringJoiner stringJoiner = new java.util.StringJoiner("\n");
        stringJoiner.add("[ASM] ------ Activity Security " + str + " Debug Logging Start ------");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[ASM] Block Enabled: ");
        sb.append(z);
        stringJoiner.add(sb.toString());
        if (!z) {
            stringJoiner.add("[ASM] Feature Flag Enabled: " + android.security.Flags.asmRestrictionsEnabled());
            stringJoiner.add("[ASM] Mendel Override: " + com.android.server.wm.ActivitySecurityModelFeatureFlags.asmRestrictionsEnabledForAll());
        }
        stringJoiner.add("[ASM] ASM Version: 10");
        stringJoiner.add("[ASM] System Time: " + android.os.SystemClock.uptimeMillis());
        stringJoiner.add("[ASM] Activity Opted In: " + ((java.lang.String) function.apply(activityRecord4)));
        boolean z5 = (task2 == null || activityRecord == null || activityRecord.getTask() != task2) ? false : true;
        if (activityRecord == null) {
            stringJoiner.add("[ASM] Source Package: " + activityRecord2.launchedFromPackage);
            stringJoiner.add("[ASM] Real Calling Uid Package: " + this.mService.mContext.getPackageManager().getNameForUid(i));
        } else {
            stringJoiner.add("[ASM] Source Record: " + ((java.lang.String) function.apply(activityRecord)));
            stringJoiner.add("[ASM] Source Launch Package: " + activityRecord.launchedFromPackage);
            stringJoiner.add("[ASM] Source Launch Intent: " + activityRecord.intent);
            if (z5) {
                stringJoiner.add("[ASM] Source/Target Task: " + activityRecord.getTask());
                stringJoiner.add("[ASM] Source/Target Task Stack: ");
            } else {
                stringJoiner.add("[ASM] Source Task: " + activityRecord.getTask());
                stringJoiner.add("[ASM] Source Task Stack: ");
            }
            activityRecord.getTask().forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.BackgroundActivityStartController.lambda$getDebugInfoForActivitySecurity$7(stringJoiner, function, (com.android.server.wm.ActivityRecord) obj);
                }
            });
        }
        stringJoiner.add("[ASM] Target Task Top: " + ((java.lang.String) function.apply(activityRecord3)));
        if (!z5) {
            stringJoiner.add("[ASM] Target Task: " + task2);
            if (task2 != null) {
                stringJoiner.add("[ASM] Target Task Stack: ");
                task2.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.BackgroundActivityStartController.lambda$getDebugInfoForActivitySecurity$8(stringJoiner, function, (com.android.server.wm.ActivityRecord) obj);
                    }
                });
            }
        }
        stringJoiner.add("[ASM] Target Record: " + ((java.lang.String) function.apply(activityRecord2)));
        stringJoiner.add("[ASM] Intent: " + activityRecord2.intent);
        stringJoiner.add("[ASM] TaskToFront: " + z2);
        stringJoiner.add("[ASM] AvoidMoveToFront: " + z3);
        stringJoiner.add("[ASM] BalCode: " + balCodeToString(i2));
        stringJoiner.add("[ASM] Allowed By Grace Period: " + z4);
        stringJoiner.add("[ASM] LastResumedActivity: " + ((java.lang.String) function.apply(this.mService.mLastResumedActivity)));
        if (this.mTopFinishedActivity != null) {
            stringJoiner.add("[ASM] TopFinishedActivity: " + this.mTopFinishedActivity.mDebugInfo);
        }
        if (!this.mTaskIdToFinishedActivity.isEmpty()) {
            stringJoiner.add("[ASM] TaskIdToFinishedActivity: ");
            this.mTaskIdToFinishedActivity.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.BackgroundActivityStartController.lambda$getDebugInfoForActivitySecurity$9(stringJoiner, (com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry) obj);
                }
            });
        }
        if (i2 == 4 || i2 == 11 || i2 == 9) {
            if (activityRecord != null) {
                task2 = activityRecord.getTask();
            }
            if (task2 != null && task2.getDisplayArea() != null) {
                stringJoiner.add("[ASM] Tasks: ");
                task2.getDisplayArea().forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.BackgroundActivityStartController$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.BackgroundActivityStartController.lambda$getDebugInfoForActivitySecurity$10(stringJoiner, (com.android.server.wm.Task) obj);
                    }
                });
            }
        }
        stringJoiner.add("[ASM] ------ Activity Security " + str + " Debug Logging End ------");
        return stringJoiner.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$getDebugInfoForActivitySecurity$6(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, com.android.server.wm.ActivityRecord activityRecord3, com.android.server.wm.ActivityRecord activityRecord4) {
        java.lang.String str;
        if (activityRecord4 == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (activityRecord4 == activityRecord) {
            str = " [source]=> ";
        } else if (activityRecord4 == activityRecord2) {
            str = " [ top  ]=> ";
        } else {
            str = activityRecord4 == activityRecord3 ? " [target]=> " : "         => ";
        }
        sb.append(str);
        sb.append(getDebugStringForActivityRecord(activityRecord4));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDebugInfoForActivitySecurity$7(java.util.StringJoiner stringJoiner, java.util.function.Function function, com.android.server.wm.ActivityRecord activityRecord) {
        stringJoiner.add("[ASM] " + ((java.lang.String) function.apply(activityRecord)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDebugInfoForActivitySecurity$8(java.util.StringJoiner stringJoiner, java.util.function.Function function, com.android.server.wm.ActivityRecord activityRecord) {
        stringJoiner.add("[ASM] " + ((java.lang.String) function.apply(activityRecord)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDebugInfoForActivitySecurity$9(java.util.StringJoiner stringJoiner, com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry) {
        stringJoiner.add("[ASM]   " + finishedActivityEntry.mDebugInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDebugInfoForActivitySecurity$10(java.util.StringJoiner stringJoiner, com.android.server.wm.Task task) {
        stringJoiner.add("[ASM]    T: " + task.toFullString());
    }

    private boolean allowedByAsmGracePeriod(int i, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task, int i2, boolean z, boolean z2) {
        com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry;
        com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry2;
        if (i2 == 8) {
            if (z && this.mTopFinishedActivity != null && this.mTopFinishedActivity.mUid == i) {
                return true;
            }
            if (!z || z2) {
                if (task == null || (finishedActivityEntry2 = this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(task.mTaskId))) == null || finishedActivityEntry2.mUid != i) {
                    return (activityRecord == null || (finishedActivityEntry = this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(activityRecord.getTask().mTaskId))) == null || finishedActivityEntry.mUid != i) ? false : true;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isSystemExemptFlagEnabled() {
        return android.provider.DeviceConfig.getBoolean("window_manager", "system_exempt_from_activity_bg_start_restriction_enabled", true);
    }

    private com.android.server.wm.BackgroundActivityStartController.BalVerdict statsLog(com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict, com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        java.lang.String str;
        if (balVerdict.blocks() && this.mService.isActivityStartsLoggingEnabled()) {
            this.mSupervisor.getActivityMetricsLogger().logAbortedBgActivityStart(balState.mIntent, balState.mCallerApp, balState.mCallingUid, balState.mCallingPackage, balState.mCallingUidProcState, balState.mCallingUidHasAnyVisibleWindow, balState.mRealCallingUid, balState.mRealCallingUidProcState, balState.mRealCallingUidHasAnyVisibleWindow, balState.mOriginatingPendingIntent != null);
        }
        int code = balVerdict.getCode();
        int i = balState.mCallingUid;
        int i2 = balState.mRealCallingUid;
        android.content.Intent intent = balState.mIntent;
        if (code == 5 && (i < 10000 || i2 < 10000)) {
            if (intent == null) {
                str = "";
            } else {
                android.content.ComponentName component = intent.getComponent();
                java.util.Objects.requireNonNull(component);
                str = component.flattenToShortString();
            }
            writeBalAllowedLog(str, 5, balState);
        }
        if (code == 6 || code == 9 || code == 7) {
            writeBalAllowedLog("", code, balState);
        }
        return balVerdict;
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeBalAllowedLog(java.lang.String str, int i, com.android.server.wm.BackgroundActivityStartController.BalState balState) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BAL_ALLOWED, str, i, balState.mCallingUid, balState.mRealCallingUid, balState.mResultForCaller == null ? 0 : balState.mResultForCaller.getRawCode(), balState.mBalAllowedByPiCreator.allowsBackgroundActivityStarts(), balState.callerExplicitOptInOrOut(), balState.mResultForRealCaller == null ? 0 : balState.mResultForRealCaller.getRawCode(), balState.mBalAllowedByPiSender.allowsBackgroundActivityStarts(), balState.realCallerExplicitOptInOrOut());
    }

    void onActivityRequestedFinishing(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry = this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(activityRecord.getTask().mTaskId));
        if (finishedActivityEntry != null && activityRecord.isUid(finishedActivityEntry.mUid) && finishedActivityEntry.mLaunchCount > 5) {
            return;
        }
        if (!activityRecord.isVisibleRequested() && activityRecord != activityRecord.getTask().getTopMostActivity()) {
            return;
        }
        com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry2 = new com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry(activityRecord);
        this.mTaskIdToFinishedActivity.put(java.lang.Integer.valueOf(activityRecord.getTask().mTaskId), finishedActivityEntry2);
        if (activityRecord.getTask().mVisibleRequested) {
            this.mTopFinishedActivity = finishedActivityEntry2;
        }
    }

    void onNewActivityLaunched(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.getTask() == null) {
            return;
        }
        if (activityRecord.getTask().mVisibleRequested) {
            this.mTopFinishedActivity = null;
        }
        com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry = this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(activityRecord.getTask().mTaskId));
        if (finishedActivityEntry != null && activityRecord.getTask().isTaskId(finishedActivityEntry.mTaskId)) {
            this.mTaskIdToFinishedActivity.remove(java.lang.Integer.valueOf(finishedActivityEntry.mTaskId));
        }
    }

    private static class BlockActivityStart {
        private com.android.server.wm.ActivityRecord mActivityOptedIn;
        private boolean mTopActivityMatchesSource;
        private boolean mTopActivityOptedIn;

        private BlockActivityStart() {
        }

        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart optedIn(com.android.server.wm.ActivityRecord activityRecord) {
            this.mTopActivityOptedIn = true;
            if (this.mActivityOptedIn == null) {
                this.mActivityOptedIn = activityRecord;
            }
            return this;
        }

        com.android.server.wm.BackgroundActivityStartController.BlockActivityStart matchesSource() {
            this.mTopActivityMatchesSource = true;
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getDebugStringForActivityRecord(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord + " :: visible=" + activityRecord.isVisible() + ", visibleRequested=" + activityRecord.isVisibleRequested() + ", finishing=" + activityRecord.finishing + ", alwaysOnTop=" + activityRecord.isAlwaysOnTop() + ", lastLaunchTime=" + activityRecord.lastLaunchTime + ", lastVisibleTime=" + activityRecord.lastVisibleTime + ", taskFragment=" + activityRecord.getTaskFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class FinishedActivityEntry {
        java.lang.String mDebugInfo;
        int mLaunchCount;
        int mTaskId;
        int mUid;

        FinishedActivityEntry(com.android.server.wm.ActivityRecord activityRecord) {
            com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry finishedActivityEntry = (com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry) com.android.server.wm.BackgroundActivityStartController.this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(activityRecord.getTask().mTaskId));
            final int i = activityRecord.getTask().mTaskId;
            this.mUid = activityRecord.getUid();
            this.mTaskId = i;
            int i2 = 1;
            if (finishedActivityEntry != null && activityRecord.isUid(finishedActivityEntry.mUid)) {
                i2 = 1 + finishedActivityEntry.mLaunchCount;
            }
            this.mLaunchCount = i2;
            this.mDebugInfo = com.android.server.wm.BackgroundActivityStartController.getDebugStringForActivityRecord(activityRecord);
            com.android.server.wm.BackgroundActivityStartController.this.mService.mH.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.BackgroundActivityStartController$FinishedActivityEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.BackgroundActivityStartController.FinishedActivityEntry.this.lambda$new$0(i);
                }
            }, 3000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BackgroundActivityStartController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.BackgroundActivityStartController.this.mTaskIdToFinishedActivity.get(java.lang.Integer.valueOf(i)) == this) {
                        com.android.server.wm.BackgroundActivityStartController.this.mTaskIdToFinishedActivity.remove(java.lang.Integer.valueOf(i));
                    }
                    if (com.android.server.wm.BackgroundActivityStartController.this.mTopFinishedActivity == this) {
                        com.android.server.wm.BackgroundActivityStartController.this.mTopFinishedActivity = null;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
