package android.service.controls.actions;

/* loaded from: classes3.dex */
public abstract class ControlAction {
    public static final android.service.controls.actions.ControlAction ERROR_ACTION = new android.service.controls.actions.ControlAction() { // from class: android.service.controls.actions.ControlAction.1
        @Override // android.service.controls.actions.ControlAction
        public int getActionType() {
            return -1;
        }
    };
    private static final java.lang.String KEY_ACTION_TYPE = "key_action_type";
    private static final java.lang.String KEY_CHALLENGE_VALUE = "key_challenge_value";
    private static final java.lang.String KEY_TEMPLATE_ID = "key_template_id";
    private static final int NUM_RESPONSE_TYPES = 6;
    public static final int RESPONSE_CHALLENGE_ACK = 3;
    public static final int RESPONSE_CHALLENGE_PASSPHRASE = 5;
    public static final int RESPONSE_CHALLENGE_PIN = 4;
    public static final int RESPONSE_FAIL = 2;
    public static final int RESPONSE_OK = 1;
    public static final int RESPONSE_UNKNOWN = 0;
    private static final java.lang.String TAG = "ControlAction";
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_COMMAND = 5;
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_MODE = 4;
    private final java.lang.String mChallengeValue;
    private final java.lang.String mTemplateId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResponseResult {
    }

    public abstract int getActionType();

    public static final boolean isValidResponse(int i) {
        return i >= 0 && i < 6;
    }

    private ControlAction() {
        this.mTemplateId = "";
        this.mChallengeValue = null;
    }

    ControlAction(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        this.mTemplateId = str;
        this.mChallengeValue = str2;
    }

    ControlAction(android.os.Bundle bundle) {
        this.mTemplateId = bundle.getString(KEY_TEMPLATE_ID);
        this.mChallengeValue = bundle.getString(KEY_CHALLENGE_VALUE);
    }

    public java.lang.String getTemplateId() {
        return this.mTemplateId;
    }

    public java.lang.String getChallengeValue() {
        return this.mChallengeValue;
    }

    android.os.Bundle getDataBundle() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt(KEY_ACTION_TYPE, getActionType());
        bundle.putString(KEY_TEMPLATE_ID, this.mTemplateId);
        bundle.putString(KEY_CHALLENGE_VALUE, this.mChallengeValue);
        return bundle;
    }

    static android.service.controls.actions.ControlAction createActionFromBundle(android.os.Bundle bundle) {
        if (bundle == null) {
            android.util.Log.e(TAG, "Null bundle");
            return ERROR_ACTION;
        }
        try {
            switch (bundle.getInt(KEY_ACTION_TYPE, -1)) {
                case 1:
                    return new android.service.controls.actions.BooleanAction(bundle);
                case 2:
                    return new android.service.controls.actions.FloatAction(bundle);
                case 3:
                default:
                    return ERROR_ACTION;
                case 4:
                    return new android.service.controls.actions.ModeAction(bundle);
                case 5:
                    return new android.service.controls.actions.CommandAction(bundle);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error creating action", e);
            return ERROR_ACTION;
        }
    }

    public static android.service.controls.actions.ControlAction getErrorAction() {
        return ERROR_ACTION;
    }
}
