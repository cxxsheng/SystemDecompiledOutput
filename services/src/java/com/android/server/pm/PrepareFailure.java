package com.android.server.pm;

/* loaded from: classes2.dex */
final class PrepareFailure extends com.android.server.pm.PackageManagerException {
    public java.lang.String mConflictingPackage;
    public java.lang.String mConflictingPermission;

    PrepareFailure(int i) {
        super(i, "Failed to prepare for install.");
    }

    PrepareFailure(int i, java.lang.String str) {
        super(i, str);
    }

    public static com.android.server.pm.PrepareFailure ofInternalError(java.lang.String str, int i) {
        return new com.android.server.pm.PrepareFailure(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, str, i);
    }

    private PrepareFailure(int i, java.lang.String str, int i2) {
        super(i, str, i2);
    }

    PrepareFailure(java.lang.String str, java.lang.Exception exc) {
        super(exc instanceof com.android.server.pm.PackageManagerException ? ((com.android.server.pm.PackageManagerException) exc).error : android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, android.util.ExceptionUtils.getCompleteMessage(str, exc));
    }

    com.android.server.pm.PrepareFailure conflictsWithExistingPermission(java.lang.String str, java.lang.String str2) {
        this.mConflictingPermission = str;
        this.mConflictingPackage = str2;
        return this;
    }
}
