package android.app.admin;

/* loaded from: classes.dex */
public abstract class PolicyUpdateReceiver extends android.content.BroadcastReceiver {
    public static final java.lang.String ACTION_DEVICE_POLICY_CHANGED = "android.app.admin.action.DEVICE_POLICY_CHANGED";
    public static final java.lang.String ACTION_DEVICE_POLICY_SET_RESULT = "android.app.admin.action.DEVICE_POLICY_SET_RESULT";
    public static final java.lang.String EXTRA_ACCOUNT_TYPE = "android.app.admin.extra.ACCOUNT_TYPE";
    public static final java.lang.String EXTRA_INTENT_FILTER = "android.app.admin.extra.INTENT_FILTER";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.app.admin.extra.PACKAGE_NAME";
    public static final java.lang.String EXTRA_PERMISSION_NAME = "android.app.admin.extra.PERMISSION_NAME";
    public static final java.lang.String EXTRA_POLICY_BUNDLE_KEY = "android.app.admin.extra.POLICY_BUNDLE_KEY";
    public static final java.lang.String EXTRA_POLICY_KEY = "android.app.admin.extra.POLICY_KEY";
    public static final java.lang.String EXTRA_POLICY_TARGET_USER_ID = "android.app.admin.extra.POLICY_TARGET_USER_ID";
    public static final java.lang.String EXTRA_POLICY_UPDATE_RESULT_KEY = "android.app.admin.extra.POLICY_UPDATE_RESULT_KEY";
    private static java.lang.String TAG = "PolicyUpdateReceiver";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(android.content.Context context, android.content.Intent intent) {
        char c;
        java.util.Objects.requireNonNull(intent.getAction());
        java.lang.String action = intent.getAction();
        switch (action.hashCode()) {
            case -1756222069:
                if (action.equals(ACTION_DEVICE_POLICY_SET_RESULT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1497094813:
                if (action.equals(ACTION_DEVICE_POLICY_CHANGED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                android.util.Log.i(TAG, "Received ACTION_DEVICE_POLICY_SET_RESULT");
                onPolicySetResult(context, getPolicyKey(intent), getPolicyExtraBundle(intent), getTargetUser(intent), getPolicyChangedReason(intent));
                break;
            case 1:
                android.util.Log.i(TAG, "Received ACTION_DEVICE_POLICY_CHANGED");
                onPolicyChanged(context, getPolicyKey(intent), getPolicyExtraBundle(intent), getTargetUser(intent), getPolicyChangedReason(intent));
                break;
            default:
                android.util.Log.e(TAG, "Unknown action received: " + intent.getAction());
                break;
        }
    }

    static java.lang.String getPolicyKey(android.content.Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_KEY)) {
            throw new java.lang.IllegalArgumentException("PolicyKey has to be provided.");
        }
        return intent.getStringExtra(EXTRA_POLICY_KEY);
    }

    static android.os.Bundle getPolicyExtraBundle(android.content.Intent intent) {
        android.os.Bundle bundleExtra = intent.getBundleExtra(EXTRA_POLICY_BUNDLE_KEY);
        return bundleExtra == null ? new android.os.Bundle() : bundleExtra;
    }

    static android.app.admin.PolicyUpdateResult getPolicyChangedReason(android.content.Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_UPDATE_RESULT_KEY)) {
            throw new java.lang.IllegalArgumentException("PolicyUpdateResult has to be provided.");
        }
        return new android.app.admin.PolicyUpdateResult(intent.getIntExtra(EXTRA_POLICY_UPDATE_RESULT_KEY, -1));
    }

    static android.app.admin.TargetUser getTargetUser(android.content.Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_TARGET_USER_ID)) {
            throw new java.lang.IllegalArgumentException("TargetUser has to be provided.");
        }
        return new android.app.admin.TargetUser(intent.getIntExtra(EXTRA_POLICY_TARGET_USER_ID, -1));
    }

    public void onPolicySetResult(android.content.Context context, java.lang.String str, android.os.Bundle bundle, android.app.admin.TargetUser targetUser, android.app.admin.PolicyUpdateResult policyUpdateResult) {
    }

    public void onPolicyChanged(android.content.Context context, java.lang.String str, android.os.Bundle bundle, android.app.admin.TargetUser targetUser, android.app.admin.PolicyUpdateResult policyUpdateResult) {
    }
}
