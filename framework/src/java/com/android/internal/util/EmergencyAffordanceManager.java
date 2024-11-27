package com.android.internal.util;

/* loaded from: classes5.dex */
public class EmergencyAffordanceManager {
    private static final java.lang.String EMERGENCY_CALL_NUMBER_SETTING = "emergency_affordance_number";
    public static final boolean ENABLED = true;
    private static final java.lang.String FORCE_EMERGENCY_AFFORDANCE_SETTING = "force_emergency_affordance";
    private final android.content.Context mContext;

    public EmergencyAffordanceManager(android.content.Context context) {
        this.mContext = context;
    }

    public final void performEmergencyCall() {
        performEmergencyCall(this.mContext);
    }

    private static android.net.Uri getPhoneUri(android.content.Context context) {
        java.lang.String string;
        java.lang.String string2 = context.getResources().getString(com.android.internal.R.string.config_emergency_call_number);
        if (android.os.Build.IS_DEBUGGABLE && (string = android.provider.Settings.Global.getString(context.getContentResolver(), EMERGENCY_CALL_NUMBER_SETTING)) != null) {
            string2 = string;
        }
        return android.net.Uri.fromParts(android.telecom.PhoneAccount.SCHEME_TEL, string2, null);
    }

    private static void performEmergencyCall(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_CALL_EMERGENCY);
        intent.setData(getPhoneUri(context));
        intent.setFlags(268435456);
        context.startActivityAsUser(intent, android.os.UserHandle.CURRENT);
    }

    public boolean needsEmergencyAffordance() {
        if (forceShowing()) {
            return true;
        }
        return isEmergencyAffordanceNeeded();
    }

    private boolean isEmergencyAffordanceNeeded() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), android.provider.Settings.Global.EMERGENCY_AFFORDANCE_NEEDED, 0) != 0;
    }

    private boolean forceShowing() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), FORCE_EMERGENCY_AFFORDANCE_SETTING, 0) != 0;
    }
}
