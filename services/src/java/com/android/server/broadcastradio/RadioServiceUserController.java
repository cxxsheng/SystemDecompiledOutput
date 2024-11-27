package com.android.server.broadcastradio;

/* loaded from: classes.dex */
public final class RadioServiceUserController {
    private RadioServiceUserController() {
        throw new java.lang.UnsupportedOperationException("RadioServiceUserController class is noninstantiable");
    }

    public static boolean isCurrentOrSystemUser() {
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        return identifier == getCurrentUser() || identifier == 0;
    }

    public static int getCurrentUser() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return currentUser;
        } catch (java.lang.RuntimeException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return com.android.server.am.ProcessList.INVALID_ADJ;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
