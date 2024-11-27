package com.android.internal.telephony.util;

/* loaded from: classes5.dex */
public final class TelephonyUtils {
    public static final java.util.concurrent.Executor DIRECT_EXECUTOR;
    public static boolean IS_DEBUGGABLE = false;
    public static boolean IS_USER = "user".equals(android.os.Build.TYPE);
    private static final java.lang.String LOG_TAG = "TelephonyUtils";

    static {
        IS_DEBUGGABLE = android.os.SystemProperties.getInt("ro.debuggable", 0) == 1;
        DIRECT_EXECUTOR = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    }

    public static boolean checkDumpPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.DUMP) != 0) {
            printWriter.println("Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }

    public static java.lang.String emptyIfNull(java.lang.String str) {
        return str == null ? "" : str;
    }

    public static <T> java.util.List<T> emptyIfNull(java.util.List<T> list) {
        return list == null ? java.util.Collections.emptyList() : list;
    }

    public static android.content.pm.ComponentInfo getComponentInfo(android.content.pm.ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo;
        }
        if (resolveInfo.serviceInfo != null) {
            return resolveInfo.serviceInfo;
        }
        if (resolveInfo.providerInfo != null) {
            return resolveInfo.providerInfo;
        }
        throw new java.lang.IllegalStateException("Missing ComponentInfo!");
    }

    public static void runWithCleanCallingIdentity(java.lang.Runnable runnable) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            runnable.run();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void runWithCleanCallingIdentity(final java.lang.Runnable runnable, java.util.concurrent.Executor executor) {
        if (runnable != null) {
            if (executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: com.android.internal.telephony.util.TelephonyUtils$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                });
            } else {
                runWithCleanCallingIdentity(runnable);
            }
        }
    }

    public static <T> T runWithCleanCallingIdentity(java.util.function.Supplier<T> supplier) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return supplier.get();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.os.Bundle filterValues(android.os.Bundle bundle) {
        android.os.Bundle bundle2 = new android.os.Bundle(bundle);
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            if (!(obj instanceof java.lang.Integer) && !(obj instanceof java.lang.Long) && !(obj instanceof java.lang.Double) && !(obj instanceof java.lang.String) && !(obj instanceof int[]) && !(obj instanceof long[]) && !(obj instanceof double[]) && !(obj instanceof java.lang.String[]) && !(obj instanceof android.os.PersistableBundle) && obj != null && !(obj instanceof java.lang.Boolean) && !(obj instanceof boolean[])) {
                if (obj instanceof android.os.Bundle) {
                    bundle2.putBundle(str, filterValues((android.os.Bundle) obj));
                } else if (!obj.getClass().getName().startsWith("android.")) {
                    bundle2.remove(str);
                }
            }
        }
        return bundle2;
    }

    public static void waitUntilReady(java.util.concurrent.CountDownLatch countDownLatch, long j) {
        try {
            countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException e) {
        }
    }

    public static java.lang.String dataStateToString(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "SUSPENDED";
            case 4:
                return "DISCONNECTING";
            case 5:
                return "HANDOVERINPROGRESS";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String mobileDataPolicyToString(int i) {
        switch (i) {
            case 1:
                return "DATA_ON_NON_DEFAULT_DURING_VOICE_CALL";
            case 2:
                return "MMS_ALWAYS_ALLOWED";
            case 3:
                return "AUTO_DATA_SWITCH";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String apnEditedStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNEDITED";
            case 1:
                return "USER_EDITED";
            case 2:
                return "USER_DELETED";
            case 3:
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "CARRIER_EDITED";
            case 5:
                return "CARRIER_DELETED";
        }
    }

    public static android.os.UserHandle getSubscriptionUserHandle(android.content.Context context, int i) {
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) context.getSystemService(android.telephony.SubscriptionManager.class);
        if (subscriptionManager != null && android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            return subscriptionManager.getSubscriptionUserHandle(i);
        }
        return null;
    }

    public static void showSwitchToManagedProfileDialogIfAppropriate(android.content.Context context, int i, int i2, java.lang.String str) {
        com.android.internal.telephony.ITelephony asInterface;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i2);
            if (isUidForeground(context, i2) && isPackageSMSRoleHolderForUser(context, str, userHandleForUid)) {
                android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) context.getSystemService(android.telephony.SubscriptionManager.class);
                if (!subscriptionManager.isActiveSubscriptionId(i)) {
                    android.util.Log.e(LOG_TAG, "Tried to send message with an inactive subscription " + i);
                    return;
                }
                android.os.UserHandle subscriptionUserHandle = subscriptionManager.getSubscriptionUserHandle(i);
                android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
                if (subscriptionUserHandle != null && userManager.isManagedProfile(subscriptionUserHandle.getIdentifier()) && (asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get())) != null) {
                    try {
                        asInterface.showSwitchToManagedProfileDialog();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(LOG_TAG, "Failed to launch switch to managed profile dialog.");
                    }
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isUidForeground(android.content.Context context, int i) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
        return activityManager != null && activityManager.getUidImportance(i) == 100;
    }

    private static boolean isPackageSMSRoleHolderForUser(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        java.util.List roleHoldersAsUser = ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getRoleHoldersAsUser("android.app.role.SMS", userHandle);
        return !roleHoldersAsUser.isEmpty() && str.equals(roleHoldersAsUser.get(0));
    }
}
