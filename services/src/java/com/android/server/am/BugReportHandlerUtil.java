package com.android.server.am;

/* loaded from: classes.dex */
public final class BugReportHandlerUtil {
    private static final java.lang.String INTENT_BUGREPORT_REQUESTED = "com.android.internal.intent.action.BUGREPORT_REQUESTED";
    private static final java.lang.String INTENT_GET_BUGREPORT_HANDLER_RESPONSE = "com.android.internal.intent.action.GET_BUGREPORT_HANDLER_RESPONSE";
    private static final java.lang.String SHELL_APP_PACKAGE = "com.android.shell";
    private static final java.lang.String TAG = "ActivityManager";

    static boolean isBugReportHandlerEnabled(android.content.Context context) {
        return context.getResources().getBoolean(android.R.bool.config_bugReportHandlerEnabled);
    }

    static boolean launchBugReportHandlerApp(android.content.Context context) {
        if (!isBugReportHandlerEnabled(context)) {
            return false;
        }
        java.lang.String customBugReportHandlerApp = getCustomBugReportHandlerApp(context);
        if (isShellApp(customBugReportHandlerApp)) {
            return false;
        }
        int customBugReportHandlerUser = getCustomBugReportHandlerUser(context);
        if (!isValidBugReportHandlerApp(customBugReportHandlerApp)) {
            customBugReportHandlerApp = getDefaultBugReportHandlerApp(context);
            customBugReportHandlerUser = context.getUserId();
        } else if (getBugReportHandlerAppReceivers(context, customBugReportHandlerApp, customBugReportHandlerUser).isEmpty()) {
            customBugReportHandlerApp = getDefaultBugReportHandlerApp(context);
            customBugReportHandlerUser = context.getUserId();
            resetCustomBugreportHandlerAppAndUser(context);
        }
        if (isShellApp(customBugReportHandlerApp) || !isValidBugReportHandlerApp(customBugReportHandlerApp) || getBugReportHandlerAppReceivers(context, customBugReportHandlerApp, customBugReportHandlerUser).isEmpty()) {
            return false;
        }
        if (getBugReportHandlerAppResponseReceivers(context, customBugReportHandlerApp, customBugReportHandlerUser).isEmpty()) {
            launchBugReportHandlerApp(context, customBugReportHandlerApp, customBugReportHandlerUser);
            return true;
        }
        android.util.Slog.i(TAG, "Getting response from bug report handler app: " + customBugReportHandlerApp);
        android.content.Intent intent = new android.content.Intent(INTENT_GET_BUGREPORT_HANDLER_RESPONSE);
        intent.setPackage(customBugReportHandlerApp);
        intent.addFlags(268435456);
        intent.addFlags(16777216);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            context.sendOrderedBroadcastAsUser(intent, android.os.UserHandle.of(customBugReportHandlerUser), "android.permission.DUMP", -1, null, new com.android.server.am.BugReportHandlerUtil.BugreportHandlerResponseBroadcastReceiver(customBugReportHandlerApp, customBugReportHandlerUser), null, 0, null, null);
            return true;
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Error while trying to get response from bug report handler app.", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void launchBugReportHandlerApp(android.content.Context context, java.lang.String str, int i) {
        android.util.Slog.i(TAG, "Launching bug report handler app: " + str);
        android.content.Intent intent = new android.content.Intent(INTENT_BUGREPORT_REQUESTED);
        intent.setPackage(str);
        intent.addFlags(268435456);
        intent.addFlags(16777216);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(true);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                context.sendBroadcastAsUser(intent, android.os.UserHandle.of(i), "android.permission.DUMP", makeBasic.toBundle());
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(TAG, "Error while trying to launch bugreport handler app.", e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static java.lang.String getCustomBugReportHandlerApp(android.content.Context context) {
        return android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), "custom_bugreport_handler_app", context.getUserId());
    }

    private static int getCustomBugReportHandlerUser(android.content.Context context) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "custom_bugreport_handler_user", com.android.server.am.ProcessList.INVALID_ADJ, context.getUserId());
    }

    private static boolean isShellApp(java.lang.String str) {
        return "com.android.shell".equals(str);
    }

    private static boolean isValidBugReportHandlerApp(java.lang.String str) {
        return !android.text.TextUtils.isEmpty(str) && isBugreportWhitelistedApp(str);
    }

    private static boolean isBugreportWhitelistedApp(java.lang.String str) {
        return com.android.server.SystemConfig.getInstance().getBugreportWhitelistedPackages().contains(str);
    }

    private static java.util.List<android.content.pm.ResolveInfo> getBugReportHandlerAppReceivers(android.content.Context context, java.lang.String str, int i) {
        android.content.Intent intent = new android.content.Intent(INTENT_BUGREPORT_REQUESTED);
        intent.setPackage(str);
        return context.getPackageManager().queryBroadcastReceiversAsUser(intent, 1048576, i);
    }

    private static java.util.List<android.content.pm.ResolveInfo> getBugReportHandlerAppResponseReceivers(android.content.Context context, java.lang.String str, int i) {
        android.content.Intent intent = new android.content.Intent(INTENT_GET_BUGREPORT_HANDLER_RESPONSE);
        intent.setPackage(str);
        return context.getPackageManager().queryBroadcastReceiversAsUser(intent, 1048576, i);
    }

    private static java.lang.String getDefaultBugReportHandlerApp(android.content.Context context) {
        return context.getResources().getString(android.R.string.config_defaultAmbientContextConsentComponent);
    }

    private static void resetCustomBugreportHandlerAppAndUser(android.content.Context context) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Secure.putString(context.getContentResolver(), "custom_bugreport_handler_app", getDefaultBugReportHandlerApp(context));
            android.provider.Settings.Secure.putInt(context.getContentResolver(), "custom_bugreport_handler_user", context.getUserId());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static class BugreportHandlerResponseBroadcastReceiver extends android.content.BroadcastReceiver {
        private final java.lang.String handlerApp;
        private final int handlerUser;

        BugreportHandlerResponseBroadcastReceiver(java.lang.String str, int i) {
            this.handlerApp = str;
            this.handlerUser = i;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (getResultCode() == -1) {
                com.android.server.am.BugReportHandlerUtil.launchBugReportHandlerApp(context, this.handlerApp, this.handlerUser);
            } else {
                android.util.Slog.w(com.android.server.am.BugReportHandlerUtil.TAG, "Request bug report because no response from handler app.");
                ((android.os.BugreportManager) context.getSystemService(android.os.BugreportManager.class)).requestBugreport(new android.os.BugreportParams(1), null, null);
            }
        }
    }
}
