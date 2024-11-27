package com.android.server.pm;

/* loaded from: classes2.dex */
final class MultiPackageVerifyingSession {
    private final java.util.List<com.android.server.pm.VerifyingSession> mChildVerifyingSessions;
    private final android.content.pm.IPackageInstallObserver2 mObserver;
    private final android.os.UserHandle mUser;
    private final java.util.Set<com.android.server.pm.VerifyingSession> mVerificationState;

    MultiPackageVerifyingSession(com.android.server.pm.VerifyingSession verifyingSession, java.util.List<com.android.server.pm.VerifyingSession> list) throws com.android.server.pm.PackageManagerException {
        this.mUser = verifyingSession.getUser();
        if (list.size() == 0) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("No child sessions found!", -21);
        }
        this.mChildVerifyingSessions = list;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).mParentVerifyingSession = this;
        }
        this.mVerificationState = new android.util.ArraySet(this.mChildVerifyingSessions.size());
        this.mObserver = verifyingSession.mObserver;
    }

    public void start() {
        android.os.Trace.asyncTraceEnd(262144L, "queueVerify", java.lang.System.identityHashCode(this));
        android.os.Trace.traceBegin(262144L, "startVerify");
        java.util.Iterator<com.android.server.pm.VerifyingSession> it = this.mChildVerifyingSessions.iterator();
        while (it.hasNext()) {
            it.next().handleStartVerify();
        }
        java.util.Iterator<com.android.server.pm.VerifyingSession> it2 = this.mChildVerifyingSessions.iterator();
        while (it2.hasNext()) {
            it2.next().handleReturnCode();
        }
        android.os.Trace.traceEnd(262144L);
    }

    public void trySendVerificationCompleteNotification(com.android.server.pm.VerifyingSession verifyingSession) {
        int i;
        java.lang.String str;
        this.mVerificationState.add(verifyingSession);
        if (this.mVerificationState.size() != this.mChildVerifyingSessions.size()) {
            return;
        }
        java.util.Iterator<com.android.server.pm.VerifyingSession> it = this.mVerificationState.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                str = null;
                break;
            }
            com.android.server.pm.VerifyingSession next = it.next();
            int ret = next.getRet();
            if (ret != 1) {
                str = next.getErrorMessage();
                i = ret;
                break;
            }
        }
        try {
            this.mObserver.onPackageInstalled((java.lang.String) null, i, str, new android.os.Bundle());
        } catch (android.os.RemoteException e) {
            android.util.Slog.i("PackageManager", "Observer no longer exists.");
        }
    }

    public java.lang.String toString() {
        return "MultiPackageVerifyingSession{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "}";
    }
}
