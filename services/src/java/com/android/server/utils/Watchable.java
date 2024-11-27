package com.android.server.utils;

/* loaded from: classes2.dex */
public interface Watchable {
    void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable);

    boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher);

    void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher);

    void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher);

    static void verifyWatchedAttributes(java.lang.Object obj, com.android.server.utils.Watcher watcher, boolean z) {
        if (!android.os.Build.IS_ENG && !android.os.Build.IS_USERDEBUG) {
            return;
        }
        for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
            com.android.server.utils.Watched watched = (com.android.server.utils.Watched) field.getAnnotation(com.android.server.utils.Watched.class);
            if (watched != null) {
                java.lang.String str = obj.getClass().getName() + "." + field.getName();
                try {
                    field.setAccessible(true);
                    java.lang.Object obj2 = field.get(obj);
                    if (obj2 instanceof com.android.server.utils.Watchable) {
                        com.android.server.utils.Watchable watchable = (com.android.server.utils.Watchable) obj2;
                        if (watchable != null && !watchable.isRegisteredObserver(watcher)) {
                            handleVerifyError("Watchable " + str + " missing an observer", z);
                        }
                    } else if (!watched.manual()) {
                        handleVerifyError("@Watched annotated field " + str + " is not a watchable type and is not flagged for manual watching.", z);
                    }
                } catch (java.lang.IllegalAccessException e) {
                    handleVerifyError("Watchable " + str + " not visible", z);
                }
            }
        }
    }

    static void handleVerifyError(java.lang.String str, boolean z) {
        if (z) {
            android.util.Log.e("Watchable", str);
            return;
        }
        throw new java.lang.RuntimeException(str);
    }

    static void verifyWatchedAttributes(java.lang.Object obj, com.android.server.utils.Watcher watcher) {
        verifyWatchedAttributes(obj, watcher, false);
    }
}
