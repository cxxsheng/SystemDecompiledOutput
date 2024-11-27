package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public interface CallerIdentityInjector {
    public static final com.android.server.timezonedetector.CallerIdentityInjector REAL = new com.android.server.timezonedetector.CallerIdentityInjector.Real();

    long clearCallingIdentity();

    int getCallingUserId();

    int resolveUserId(int i, java.lang.String str);

    void restoreCallingIdentity(long j);

    public static class Real implements com.android.server.timezonedetector.CallerIdentityInjector {
        protected Real() {
        }

        @Override // com.android.server.timezonedetector.CallerIdentityInjector
        public int resolveUserId(int i, java.lang.String str) {
            return android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, str, null);
        }

        @Override // com.android.server.timezonedetector.CallerIdentityInjector
        public int getCallingUserId() {
            return android.os.UserHandle.getCallingUserId();
        }

        @Override // com.android.server.timezonedetector.CallerIdentityInjector
        public long clearCallingIdentity() {
            return android.os.Binder.clearCallingIdentity();
        }

        @Override // com.android.server.timezonedetector.CallerIdentityInjector
        public void restoreCallingIdentity(long j) {
            android.os.Binder.restoreCallingIdentity(j);
        }
    }
}
