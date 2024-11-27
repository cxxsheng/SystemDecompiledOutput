package com.android.server.wm;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public class ActivityInterceptorCallbackRegistry {
    private static final com.android.server.wm.ActivityInterceptorCallbackRegistry sInstance = new com.android.server.wm.ActivityInterceptorCallbackRegistry();

    private ActivityInterceptorCallbackRegistry() {
    }

    @android.annotation.NonNull
    public static com.android.server.wm.ActivityInterceptorCallbackRegistry getInstance() {
        return sInstance;
    }

    public void registerActivityInterceptorCallback(int i, @android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback activityInterceptorCallback) {
        if (getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only system server can register ActivityInterceptorCallback");
        }
        if (!com.android.server.wm.ActivityInterceptorCallback.isValidMainlineOrderId(i)) {
            throw new java.lang.IllegalArgumentException("id is not in the mainline modules range, please useActivityTaskManagerInternal.registerActivityStartInterceptor(OrderedId, ActivityInterceptorCallback) instead.");
        }
        if (activityInterceptorCallback == null) {
            throw new java.lang.IllegalArgumentException("The passed ActivityInterceptorCallback can not be null");
        }
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerActivityStartInterceptor(i, activityInterceptorCallback);
    }

    public void unregisterActivityInterceptorCallback(int i) {
        if (getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only system server can register ActivityInterceptorCallback");
        }
        if (!com.android.server.wm.ActivityInterceptorCallback.isValidMainlineOrderId(i)) {
            throw new java.lang.IllegalArgumentException("id is not in the mainline modules range, please useActivityTaskManagerInternal.unregisterActivityStartInterceptor(OrderedId) instead.");
        }
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).unregisterActivityStartInterceptor(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getCallingUid() {
        return android.os.Binder.getCallingUid();
    }
}
