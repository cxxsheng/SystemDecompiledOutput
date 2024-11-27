package android.telecom;

/* loaded from: classes3.dex */
public class DefaultDialerManager {
    private static final java.lang.String TAG = "DefaultDialerManager";

    public static boolean setDefaultDialerApplication(android.content.Context context, java.lang.String str) {
        return setDefaultDialerApplication(context, str, android.app.ActivityManager.getCurrentUser());
    }

    public static boolean setDefaultDialerApplication(android.content.Context context, java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
                ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).addRoleHolderAsUser("android.app.role.DIALER", str, 0, android.os.UserHandle.of(i), android.os.AsyncTask.THREAD_POOL_EXECUTOR, new java.util.function.Consumer() { // from class: android.telecom.DefaultDialerManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.telecom.DefaultDialerManager.lambda$setDefaultDialerApplication$0(completableFuture, (java.lang.Boolean) obj);
                    }
                });
                completableFuture.get(5L, java.util.concurrent.TimeUnit.SECONDS);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(TAG, "Failed to set default dialer to " + str + " for user " + i, e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    static /* synthetic */ void lambda$setDefaultDialerApplication$0(java.util.concurrent.CompletableFuture completableFuture, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new java.lang.RuntimeException());
        }
    }

    public static java.lang.String getDefaultDialerApplication(android.content.Context context) {
        return getDefaultDialerApplication(context, context.getUserId());
    }

    public static java.lang.String getDefaultDialerApplication(android.content.Context context, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (java.lang.String) com.android.internal.util.CollectionUtils.firstOrNull(((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getRoleHoldersAsUser("android.app.role.DIALER", android.os.UserHandle.of(i)));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static java.util.List<java.lang.String> getInstalledDialerApplications(android.content.Context context, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(new android.content.Intent(android.content.Intent.ACTION_DIAL), 0, i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.ResolveInfo resolveInfo : queryIntentActivitiesAsUser) {
            android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && !arrayList.contains(activityInfo.packageName) && resolveInfo.targetUserId == -2) {
                arrayList.add(activityInfo.packageName);
            }
        }
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_DIAL);
        intent.setData(android.net.Uri.fromParts(android.telecom.PhoneAccount.SCHEME_TEL, "", null));
        return filterByIntent(context, arrayList, intent, i);
    }

    public static java.util.List<java.lang.String> getInstalledDialerApplications(android.content.Context context) {
        return getInstalledDialerApplications(context, android.os.Process.myUserHandle().getIdentifier());
    }

    public static boolean isDefaultOrSystemDialer(android.content.Context context, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        android.telecom.TelecomManager telecomManager = getTelecomManager(context);
        return str.equals(telecomManager.getDefaultDialerPackage()) || str.equals(telecomManager.getSystemDialerPackage());
    }

    private static java.util.List<java.lang.String> filterByIntent(android.content.Context context, java.util.List<java.lang.String> list, android.content.Intent intent, int i) {
        if (list == null || list.isEmpty()) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, i);
        int size = queryIntentActivitiesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ActivityInfo activityInfo = queryIntentActivitiesAsUser.get(i2).activityInfo;
            if (activityInfo != null && list.contains(activityInfo.packageName) && !arrayList.contains(activityInfo.packageName)) {
                arrayList.add(activityInfo.packageName);
            }
        }
        return arrayList;
    }

    private static android.telecom.TelecomManager getTelecomManager(android.content.Context context) {
        return (android.telecom.TelecomManager) context.getSystemService(android.content.Context.TELECOM_SERVICE);
    }
}
