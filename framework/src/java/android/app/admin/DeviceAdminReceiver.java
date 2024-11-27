package android.app.admin;

/* loaded from: classes.dex */
public class DeviceAdminReceiver extends android.content.BroadcastReceiver {
    public static final java.lang.String ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE";
    public static final java.lang.String ACTION_BUGREPORT_FAILED = "android.app.action.BUGREPORT_FAILED";
    public static final java.lang.String ACTION_BUGREPORT_SHARE = "android.app.action.BUGREPORT_SHARE";
    public static final java.lang.String ACTION_BUGREPORT_SHARING_DECLINED = "android.app.action.BUGREPORT_SHARING_DECLINED";
    public static final java.lang.String ACTION_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.action.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final java.lang.String ACTION_COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED = "android.app.action.COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED";
    public static final java.lang.String ACTION_DEVICE_ADMIN_DISABLED = "android.app.action.DEVICE_ADMIN_DISABLED";
    public static final java.lang.String ACTION_DEVICE_ADMIN_DISABLE_REQUESTED = "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED";
    public static final java.lang.String ACTION_DEVICE_ADMIN_ENABLED = "android.app.action.DEVICE_ADMIN_ENABLED";
    public static final java.lang.String ACTION_LOCK_TASK_ENTERING = "android.app.action.LOCK_TASK_ENTERING";
    public static final java.lang.String ACTION_LOCK_TASK_EXITING = "android.app.action.LOCK_TASK_EXITING";
    public static final java.lang.String ACTION_NETWORK_LOGS_AVAILABLE = "android.app.action.NETWORK_LOGS_AVAILABLE";
    public static final java.lang.String ACTION_NOTIFY_PENDING_SYSTEM_UPDATE = "android.app.action.NOTIFY_PENDING_SYSTEM_UPDATE";
    public static final java.lang.String ACTION_OPERATION_SAFETY_STATE_CHANGED = "android.app.action.OPERATION_SAFETY_STATE_CHANGED";
    public static final java.lang.String ACTION_PASSWORD_CHANGED = "android.app.action.ACTION_PASSWORD_CHANGED";
    public static final java.lang.String ACTION_PASSWORD_EXPIRING = "android.app.action.ACTION_PASSWORD_EXPIRING";
    public static final java.lang.String ACTION_PASSWORD_FAILED = "android.app.action.ACTION_PASSWORD_FAILED";
    public static final java.lang.String ACTION_PASSWORD_SUCCEEDED = "android.app.action.ACTION_PASSWORD_SUCCEEDED";
    public static final java.lang.String ACTION_PROFILE_PROVISIONING_COMPLETE = "android.app.action.PROFILE_PROVISIONING_COMPLETE";
    public static final java.lang.String ACTION_SECURITY_LOGS_AVAILABLE = "android.app.action.SECURITY_LOGS_AVAILABLE";
    public static final java.lang.String ACTION_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.TRANSFER_OWNERSHIP_COMPLETE";
    public static final java.lang.String ACTION_USER_ADDED = "android.app.action.USER_ADDED";
    public static final java.lang.String ACTION_USER_REMOVED = "android.app.action.USER_REMOVED";
    public static final java.lang.String ACTION_USER_STARTED = "android.app.action.USER_STARTED";
    public static final java.lang.String ACTION_USER_STOPPED = "android.app.action.USER_STOPPED";
    public static final java.lang.String ACTION_USER_SWITCHED = "android.app.action.USER_SWITCHED";
    public static final int BUGREPORT_FAILURE_FAILED_COMPLETING = 0;
    public static final int BUGREPORT_FAILURE_FILE_NO_LONGER_AVAILABLE = 1;
    public static final java.lang.String DEVICE_ADMIN_META_DATA = "android.app.device_admin";
    public static final java.lang.String EXTRA_BUGREPORT_FAILURE_REASON = "android.app.extra.BUGREPORT_FAILURE_REASON";
    public static final java.lang.String EXTRA_BUGREPORT_HASH = "android.app.extra.BUGREPORT_HASH";
    public static final java.lang.String EXTRA_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.extra.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final java.lang.String EXTRA_CHOOSE_PRIVATE_KEY_RESPONSE = "android.app.extra.CHOOSE_PRIVATE_KEY_RESPONSE";
    public static final java.lang.String EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID = "android.app.extra.CHOOSE_PRIVATE_KEY_SENDER_UID";
    public static final java.lang.String EXTRA_CHOOSE_PRIVATE_KEY_URI = "android.app.extra.CHOOSE_PRIVATE_KEY_URI";
    public static final java.lang.String EXTRA_DISABLE_WARNING = "android.app.extra.DISABLE_WARNING";
    public static final java.lang.String EXTRA_LOCK_TASK_PACKAGE = "android.app.extra.LOCK_TASK_PACKAGE";
    public static final java.lang.String EXTRA_NETWORK_LOGS_COUNT = "android.app.extra.EXTRA_NETWORK_LOGS_COUNT";
    public static final java.lang.String EXTRA_NETWORK_LOGS_TOKEN = "android.app.extra.EXTRA_NETWORK_LOGS_TOKEN";
    public static final java.lang.String EXTRA_OPERATION_SAFETY_REASON = "android.app.extra.OPERATION_SAFETY_REASON";
    public static final java.lang.String EXTRA_OPERATION_SAFETY_STATE = "android.app.extra.OPERATION_SAFETY_STATE";
    public static final java.lang.String EXTRA_SYSTEM_UPDATE_RECEIVED_TIME = "android.app.extra.SYSTEM_UPDATE_RECEIVED_TIME";
    public static final java.lang.String EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE = "android.app.extra.TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE";
    private static final boolean LOCAL_LOGV = false;
    private static final java.lang.String TAG = "DevicePolicy";
    private android.app.admin.DevicePolicyManager mManager;
    private android.content.ComponentName mWho;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BugreportFailureCode {
    }

    public android.app.admin.DevicePolicyManager getManager(android.content.Context context) {
        if (this.mManager != null) {
            return this.mManager;
        }
        this.mManager = (android.app.admin.DevicePolicyManager) context.getSystemService(android.content.Context.DEVICE_POLICY_SERVICE);
        return this.mManager;
    }

    public android.content.ComponentName getWho(android.content.Context context) {
        if (this.mWho != null) {
            return this.mWho;
        }
        this.mWho = new android.content.ComponentName(context, getClass());
        return this.mWho;
    }

    public void onEnabled(android.content.Context context, android.content.Intent intent) {
    }

    public java.lang.CharSequence onDisableRequested(android.content.Context context, android.content.Intent intent) {
        return null;
    }

    public void onDisabled(android.content.Context context, android.content.Intent intent) {
    }

    @java.lang.Deprecated
    public void onPasswordChanged(android.content.Context context, android.content.Intent intent) {
    }

    public void onPasswordChanged(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        onPasswordChanged(context, intent);
    }

    @java.lang.Deprecated
    public void onPasswordFailed(android.content.Context context, android.content.Intent intent) {
    }

    public void onPasswordFailed(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        onPasswordFailed(context, intent);
    }

    @java.lang.Deprecated
    public void onPasswordSucceeded(android.content.Context context, android.content.Intent intent) {
    }

    public void onPasswordSucceeded(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        onPasswordSucceeded(context, intent);
    }

    @java.lang.Deprecated
    public void onPasswordExpiring(android.content.Context context, android.content.Intent intent) {
    }

    public void onPasswordExpiring(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        onPasswordExpiring(context, intent);
    }

    public void onProfileProvisioningComplete(android.content.Context context, android.content.Intent intent) {
    }

    @java.lang.Deprecated
    public void onReadyForUserInitialization(android.content.Context context, android.content.Intent intent) {
    }

    public void onLockTaskModeEntering(android.content.Context context, android.content.Intent intent, java.lang.String str) {
    }

    public void onLockTaskModeExiting(android.content.Context context, android.content.Intent intent) {
    }

    public java.lang.String onChoosePrivateKeyAlias(android.content.Context context, android.content.Intent intent, int i, android.net.Uri uri, java.lang.String str) {
        return null;
    }

    public void onSystemUpdatePending(android.content.Context context, android.content.Intent intent, long j) {
    }

    public void onBugreportSharingDeclined(android.content.Context context, android.content.Intent intent) {
    }

    public void onBugreportShared(android.content.Context context, android.content.Intent intent, java.lang.String str) {
    }

    public void onBugreportFailed(android.content.Context context, android.content.Intent intent, int i) {
    }

    public void onSecurityLogsAvailable(android.content.Context context, android.content.Intent intent) {
    }

    public void onNetworkLogsAvailable(android.content.Context context, android.content.Intent intent, long j, int i) {
    }

    public void onUserAdded(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
    }

    public void onUserRemoved(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
    }

    public void onUserStarted(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
    }

    public void onUserStopped(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
    }

    public void onUserSwitched(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
    }

    public void onTransferOwnershipComplete(android.content.Context context, android.os.PersistableBundle persistableBundle) {
    }

    public void onTransferAffiliatedProfileOwnershipComplete(android.content.Context context, android.os.UserHandle userHandle) {
    }

    public void onOperationSafetyStateChanged(android.content.Context context, int i, boolean z) {
    }

    private void onOperationSafetyStateChanged(android.content.Context context, android.content.Intent intent) {
        if (hasRequiredExtra(intent, EXTRA_OPERATION_SAFETY_REASON) && hasRequiredExtra(intent, EXTRA_OPERATION_SAFETY_STATE)) {
            int intExtra = intent.getIntExtra(EXTRA_OPERATION_SAFETY_REASON, -1);
            if (!android.app.admin.DevicePolicyManager.isValidOperationSafetyReason(intExtra)) {
                android.util.Log.wtf(TAG, "Received invalid reason on " + intent.getAction() + ": " + intExtra);
                return;
            } else {
                onOperationSafetyStateChanged(context, intExtra, intent.getBooleanExtra(EXTRA_OPERATION_SAFETY_STATE, false));
                return;
            }
        }
        android.util.Log.w(TAG, "Igoring intent that's missing required extras");
    }

    public void onComplianceAcknowledgementRequired(android.content.Context context, android.content.Intent intent) {
        getManager(context).acknowledgeDeviceCompliant();
    }

    private boolean hasRequiredExtra(android.content.Intent intent, java.lang.String str) {
        if (intent.hasExtra(str)) {
            return true;
        }
        android.util.Log.wtf(TAG, "Missing '" + str + "' on intent " + intent);
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        if (ACTION_PASSWORD_CHANGED.equals(action)) {
            onPasswordChanged(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_PASSWORD_FAILED.equals(action)) {
            onPasswordFailed(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_PASSWORD_SUCCEEDED.equals(action)) {
            onPasswordSucceeded(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_DEVICE_ADMIN_ENABLED.equals(action)) {
            onEnabled(context, intent);
            return;
        }
        if (ACTION_DEVICE_ADMIN_DISABLE_REQUESTED.equals(action)) {
            java.lang.CharSequence onDisableRequested = onDisableRequested(context, intent);
            if (onDisableRequested != null) {
                getResultExtras(true).putCharSequence(EXTRA_DISABLE_WARNING, onDisableRequested);
                return;
            }
            return;
        }
        if (ACTION_DEVICE_ADMIN_DISABLED.equals(action)) {
            onDisabled(context, intent);
            return;
        }
        if (ACTION_PASSWORD_EXPIRING.equals(action)) {
            onPasswordExpiring(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_PROFILE_PROVISIONING_COMPLETE.equals(action)) {
            onProfileProvisioningComplete(context, intent);
            return;
        }
        if (ACTION_CHOOSE_PRIVATE_KEY_ALIAS.equals(action)) {
            setResultData(onChoosePrivateKeyAlias(context, intent, intent.getIntExtra(EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID, -1), (android.net.Uri) intent.getParcelableExtra(EXTRA_CHOOSE_PRIVATE_KEY_URI, android.net.Uri.class), intent.getStringExtra(EXTRA_CHOOSE_PRIVATE_KEY_ALIAS)));
            return;
        }
        if (ACTION_LOCK_TASK_ENTERING.equals(action)) {
            onLockTaskModeEntering(context, intent, intent.getStringExtra(EXTRA_LOCK_TASK_PACKAGE));
            return;
        }
        if (ACTION_LOCK_TASK_EXITING.equals(action)) {
            onLockTaskModeExiting(context, intent);
            return;
        }
        if (ACTION_NOTIFY_PENDING_SYSTEM_UPDATE.equals(action)) {
            onSystemUpdatePending(context, intent, intent.getLongExtra(EXTRA_SYSTEM_UPDATE_RECEIVED_TIME, -1L));
            return;
        }
        if (ACTION_BUGREPORT_SHARING_DECLINED.equals(action)) {
            onBugreportSharingDeclined(context, intent);
            return;
        }
        if (ACTION_BUGREPORT_SHARE.equals(action)) {
            onBugreportShared(context, intent, intent.getStringExtra(EXTRA_BUGREPORT_HASH));
            return;
        }
        if (ACTION_BUGREPORT_FAILED.equals(action)) {
            onBugreportFailed(context, intent, intent.getIntExtra(EXTRA_BUGREPORT_FAILURE_REASON, 0));
            return;
        }
        if (ACTION_SECURITY_LOGS_AVAILABLE.equals(action)) {
            onSecurityLogsAvailable(context, intent);
            return;
        }
        if (ACTION_NETWORK_LOGS_AVAILABLE.equals(action)) {
            onNetworkLogsAvailable(context, intent, intent.getLongExtra(EXTRA_NETWORK_LOGS_TOKEN, -1L), intent.getIntExtra(EXTRA_NETWORK_LOGS_COUNT, 0));
            return;
        }
        if (ACTION_USER_ADDED.equals(action)) {
            onUserAdded(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_USER_REMOVED.equals(action)) {
            onUserRemoved(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_USER_STARTED.equals(action)) {
            onUserStarted(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_USER_STOPPED.equals(action)) {
            onUserStopped(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_USER_SWITCHED.equals(action)) {
            onUserSwitched(context, intent, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
            return;
        }
        if (ACTION_TRANSFER_OWNERSHIP_COMPLETE.equals(action)) {
            onTransferOwnershipComplete(context, (android.os.PersistableBundle) intent.getParcelableExtra(EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE, android.os.PersistableBundle.class));
            return;
        }
        if (ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE.equals(action)) {
            onTransferAffiliatedProfileOwnershipComplete(context, (android.os.UserHandle) intent.getParcelableExtra(android.content.Intent.EXTRA_USER, android.os.UserHandle.class));
        } else if (ACTION_OPERATION_SAFETY_STATE_CHANGED.equals(action)) {
            onOperationSafetyStateChanged(context, intent);
        } else if (ACTION_COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED.equals(action)) {
            onComplianceAcknowledgementRequired(context, intent);
        }
    }
}
