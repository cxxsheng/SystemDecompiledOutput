package android.hardware.biometrics;

/* loaded from: classes.dex */
public class ParentalControlsUtilsInternal {
    private static final java.lang.String TEST_ALWAYS_REQUIRE_CONSENT_CLASS = "android.hardware.biometrics.ParentalControlsUtilsInternal.require_consent_class";
    private static final java.lang.String TEST_ALWAYS_REQUIRE_CONSENT_PACKAGE = "android.hardware.biometrics.ParentalControlsUtilsInternal.require_consent_package";

    public static android.content.ComponentName getTestComponentName(android.content.Context context, int i) {
        if (!android.os.Build.IS_USERDEBUG && !android.os.Build.IS_ENG) {
            return null;
        }
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), TEST_ALWAYS_REQUIRE_CONSENT_PACKAGE, i);
        java.lang.String stringForUser2 = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), TEST_ALWAYS_REQUIRE_CONSENT_CLASS, i);
        if (stringForUser == null || stringForUser2 == null) {
            return null;
        }
        return new android.content.ComponentName(stringForUser, stringForUser2);
    }

    public static boolean parentConsentRequired(android.content.Context context, android.app.admin.DevicePolicyManager devicePolicyManager, int i, android.os.UserHandle userHandle) {
        if (getTestComponentName(context, userHandle.getIdentifier()) != null) {
            return true;
        }
        return parentConsentRequired(devicePolicyManager, i, userHandle);
    }

    public static boolean parentConsentRequired(android.app.admin.DevicePolicyManager devicePolicyManager, int i, android.os.UserHandle userHandle) {
        android.content.ComponentName supervisionComponentName = getSupervisionComponentName(devicePolicyManager, userHandle);
        if (supervisionComponentName == null) {
            return false;
        }
        int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(supervisionComponentName);
        boolean containsFlag = containsFlag(keyguardDisabledFeatures, 32);
        boolean containsFlag2 = containsFlag(keyguardDisabledFeatures, 128);
        boolean containsFlag3 = containsFlag(keyguardDisabledFeatures, 256);
        if (containsFlag(i, 2) && containsFlag) {
            return true;
        }
        if (containsFlag(i, 8) && containsFlag2) {
            return true;
        }
        return containsFlag(i, 4) && containsFlag3;
    }

    public static android.content.ComponentName getSupervisionComponentName(android.app.admin.DevicePolicyManager devicePolicyManager, android.os.UserHandle userHandle) {
        return devicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
    }

    private static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }
}
