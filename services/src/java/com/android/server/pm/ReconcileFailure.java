package com.android.server.pm;

/* loaded from: classes2.dex */
final class ReconcileFailure extends com.android.server.pm.PackageManagerException {
    public static com.android.server.pm.ReconcileFailure ofInternalError(java.lang.String str, int i) {
        return new com.android.server.pm.ReconcileFailure(str, i);
    }

    private ReconcileFailure(java.lang.String str, int i) {
        super(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Reconcile failed: " + str, i);
    }

    ReconcileFailure(int i, java.lang.String str) {
        super(i, "Reconcile failed: " + str);
    }

    ReconcileFailure(com.android.server.pm.PackageManagerException packageManagerException) {
        this(packageManagerException.error, packageManagerException.getMessage());
    }
}
