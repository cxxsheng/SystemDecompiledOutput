package android.util;

/* loaded from: classes3.dex */
public class SafetyProtectionUtils {
    private static final java.lang.String SAFETY_PROTECTION_RESOURCES_ENABLED = "safety_protection_enabled";

    public static boolean shouldShowSafetyProtectionResources(android.content.Context context) {
        try {
            if (android.provider.DeviceConfig.getBoolean("privacy", SAFETY_PROTECTION_RESOURCES_ENABLED, false) && context.getResources().getBoolean(android.content.res.Resources.getSystem().getIdentifier("config_safetyProtectionEnabled", "bool", "android")) && context.getDrawable(17301685) != null) {
                return !context.getString(17039425).isEmpty();
            }
            return false;
        } catch (android.content.res.Resources.NotFoundException e) {
            return false;
        }
    }
}
