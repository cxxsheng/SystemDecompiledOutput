package com.android.server.biometrics;

/* loaded from: classes.dex */
public class Utils {
    private static final java.lang.String TAG = "BiometricUtils";

    public static boolean isDebugEnabled(android.content.Context context, int i) {
        if (i == -10000) {
            return false;
        }
        return (android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_debug_enabled", 0, i) != 0;
    }

    public static boolean isVirtualEnabled(@android.annotation.NonNull android.content.Context context) {
        return android.os.Build.isDebuggable() && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_virtual_enabled", 0, -2) == 1;
    }

    static void combineAuthenticatorBundles(android.hardware.biometrics.PromptInfo promptInfo) {
        int i;
        boolean isDeviceCredentialAllowed = promptInfo.isDeviceCredentialAllowed();
        promptInfo.setDeviceCredentialAllowed(false);
        if (promptInfo.getAuthenticators() != 0) {
            i = promptInfo.getAuthenticators();
        } else if (isDeviceCredentialAllowed) {
            i = 33023;
        } else {
            i = 255;
        }
        promptInfo.setAuthenticators(i);
    }

    static boolean isCredentialRequested(int i) {
        return (i & 32768) != 0;
    }

    static boolean isCredentialRequested(android.hardware.biometrics.PromptInfo promptInfo) {
        return isCredentialRequested(promptInfo.getAuthenticators());
    }

    static int getPublicBiometricStrength(int i) {
        return i & 255;
    }

    static int getPublicBiometricStrength(android.hardware.biometrics.PromptInfo promptInfo) {
        return getPublicBiometricStrength(promptInfo.getAuthenticators());
    }

    static boolean isBiometricRequested(int i) {
        return getPublicBiometricStrength(i) != 0;
    }

    static boolean isBiometricRequested(android.hardware.biometrics.PromptInfo promptInfo) {
        return getPublicBiometricStrength(promptInfo) != 0;
    }

    public static boolean isAtLeastStrength(int i, int i2) {
        int i3 = i & 32767;
        if (((~i2) & i3) != 0) {
            return false;
        }
        for (int i4 = 1; i4 <= i2; i4 = (i4 << 1) | 1) {
            if (i4 == i3) {
                return true;
            }
        }
        android.util.Slog.e("BiometricService", "Unknown sensorStrength: " + i3 + ", requestedStrength: " + i2);
        return false;
    }

    static boolean isValidAuthenticatorConfig(android.hardware.biometrics.PromptInfo promptInfo) {
        return isValidAuthenticatorConfig(promptInfo.getAuthenticators());
    }

    static boolean isValidAuthenticatorConfig(int i) {
        if (i == 0) {
            return true;
        }
        if (((-65536) & i) != 0) {
            android.util.Slog.e("BiometricService", "Non-biometric, non-credential bits found. Authenticators: " + i);
            return false;
        }
        int i2 = i & 32767;
        if ((i2 == 0 && isCredentialRequested(i)) || i2 == 15 || i2 == 255) {
            return true;
        }
        android.util.Slog.e("BiometricService", "Unsupported biometric flags. Authenticators: " + i);
        return false;
    }

    static int biometricConstantsToBiometricManager(int i) {
        switch (i) {
            case 0:
            case 7:
            case 9:
                break;
            case 1:
                break;
            case 11:
            case 14:
                break;
            case 12:
                break;
            case 15:
                break;
            case 18:
                break;
            default:
                android.util.Slog.e("BiometricService", "Unhandled result code: " + i);
                break;
        }
        return 1;
    }

    static int getAuthenticationTypeForResult(int i) {
        switch (i) {
            case 1:
            case 4:
                return 2;
            case 7:
                return 1;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported dismissal reason: " + i);
        }
    }

    static int authenticatorStatusToBiometricConstant(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
            case 4:
                return 12;
            case 3:
            case 6:
            case 8:
            default:
                return 1;
            case 5:
                return 15;
            case 7:
                return 11;
            case 9:
                return 14;
            case 10:
                return 7;
            case 11:
                return 9;
            case 12:
                return 18;
        }
    }

    static boolean isConfirmationSupported(int i) {
        switch (i) {
            case 4:
            case 8:
                return true;
            default:
                return false;
        }
    }

    static int removeBiometricBits(int i) {
        return i & (-32768);
    }

    public static boolean listContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static void checkPermissionOrShell(android.content.Context context, java.lang.String str) {
        if (android.os.Binder.getCallingUid() == 2000) {
            return;
        }
        checkPermission(context, str);
    }

    public static void checkPermission(android.content.Context context, java.lang.String str) {
        context.enforceCallingOrSelfPermission(str, "Must have " + str + " permission.");
    }

    public static boolean isCurrentUserOrProfile(android.content.Context context, int i) {
        android.os.UserManager userManager = android.os.UserManager.get(context);
        if (userManager == null) {
            android.util.Slog.e(TAG, "Unable to get UserManager");
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (int i2 : userManager.getEnabledProfileIds(android.app.ActivityManager.getCurrentUser())) {
                if (i2 == i) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isStrongBiometric(int i) {
        try {
            return isAtLeastStrength(android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric")).getCurrentStrength(i), 15);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
            return false;
        }
    }

    public static int getCurrentStrength(int i) {
        try {
            return android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric")).getCurrentStrength(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
            return 0;
        }
    }

    public static boolean isKeyguard(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable java.lang.String str) {
        boolean hasInternalPermission = hasInternalPermission(context);
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.config_hdmiCecSetMenuLanguageActivity));
        java.lang.String packageName = unflattenFromString != null ? unflattenFromString.getPackageName() : null;
        return hasInternalPermission && packageName != null && packageName.equals(str);
    }

    public static boolean isSystem(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable java.lang.String str) {
        return hasInternalPermission(context) && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str);
    }

    public static boolean isSettings(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable java.lang.String str) {
        return hasInternalPermission(context) && "com.android.settings".equals(str);
    }

    private static boolean hasInternalPermission(@android.annotation.NonNull android.content.Context context) {
        return context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0;
    }

    public static java.lang.String getClientName(@android.annotation.Nullable com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        return baseClientMonitor != null ? baseClientMonitor.getClass().getSimpleName() : "null";
    }

    private static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static boolean isUserEncryptedOrLockdown(@android.annotation.NonNull com.android.internal.widget.LockPatternUtils lockPatternUtils, int i) {
        int strongAuthForUser = lockPatternUtils.getStrongAuthForUser(i);
        boolean containsFlag = containsFlag(strongAuthForUser, 1);
        boolean z = containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32);
        android.util.Slog.d(TAG, "isEncrypted: " + containsFlag + " isLockdown: " + z);
        return containsFlag || z;
    }

    public static boolean isForeground(int i, int i2) {
        java.util.List runningAppProcesses;
        try {
            runningAppProcesses = android.app.ActivityManager.getService().getRunningAppProcesses();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "am.getRunningAppProcesses() failed");
        }
        if (runningAppProcesses == null) {
            android.util.Slog.e(TAG, "No running app processes found, defaulting to true");
            return true;
        }
        for (int i3 = 0; i3 < runningAppProcesses.size(); i3++) {
            android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (android.app.ActivityManager.RunningAppProcessInfo) runningAppProcesses.get(i3);
            if (runningAppProcessInfo.pid == i2 && runningAppProcessInfo.uid == i && runningAppProcessInfo.importance <= 125) {
                return true;
            }
        }
        return false;
    }

    public static int authenticatorStrengthToPropertyStrength(int i) {
        switch (i) {
            case 15:
                return 2;
            case 255:
                return 1;
            case 4095:
                return 0;
            default:
                throw new java.lang.IllegalArgumentException("Unknown strength: " + i);
        }
    }

    public static int propertyStrengthToAuthenticatorStrength(int i) {
        switch (i) {
            case 0:
                return 4095;
            case 1:
                return 255;
            case 2:
                return 15;
            default:
                throw new java.lang.IllegalArgumentException("Unknown strength: " + i);
        }
    }

    public static boolean isBackground(java.lang.String str) {
        android.util.Slog.v(TAG, "Checking if the authenticating is in background, clientPackage:" + str);
        java.util.List<android.app.ActivityManager.RunningTaskInfo> tasks = android.app.ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE);
        if (tasks == null || tasks.isEmpty()) {
            android.util.Slog.d(TAG, "No running tasks reported");
            return true;
        }
        for (android.app.ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            android.content.ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                java.lang.String packageName = componentName.getPackageName();
                if (packageName.contentEquals(str) && runningTaskInfo.isVisible()) {
                    return false;
                }
                android.util.Slog.i(TAG, "Running task, top: " + packageName + ", isVisible: " + runningTaskInfo.isVisible());
            }
        }
        return true;
    }
}
