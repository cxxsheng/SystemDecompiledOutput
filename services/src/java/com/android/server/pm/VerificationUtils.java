package com.android.server.pm;

/* loaded from: classes2.dex */
final class VerificationUtils {
    private static final long DEFAULT_STREAMING_VERIFICATION_TIMEOUT = 3000;
    private static final long DEFAULT_VERIFICATION_TIMEOUT = 10000;

    VerificationUtils() {
    }

    public static long getVerificationTimeout(android.content.Context context, boolean z) {
        if (z) {
            return getDefaultStreamingVerificationTimeout(context);
        }
        return getDefaultVerificationTimeout(context);
    }

    public static long getDefaultVerificationTimeout(android.content.Context context) {
        return java.lang.Math.max(android.provider.Settings.Global.getLong(context.getContentResolver(), "verifier_timeout", 10000L), 10000L);
    }

    public static long getDefaultStreamingVerificationTimeout(android.content.Context context) {
        return java.lang.Math.max(android.provider.Settings.Global.getLong(context.getContentResolver(), "streaming_verifier_timeout", 3000L), 3000L);
    }

    public static void broadcastPackageVerified(int i, android.net.Uri uri, int i2, @android.annotation.Nullable java.lang.String str, int i3, android.os.UserHandle userHandle, android.content.Context context) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.PACKAGE_VERIFIED");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(1);
        intent.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
        intent.putExtra("android.content.pm.extra.VERIFICATION_RESULT", i2);
        if (str != null) {
            intent.putExtra("android.content.pm.extra.VERIFICATION_ROOT_HASH", str);
        }
        intent.putExtra("android.content.pm.extra.DATA_LOADER_TYPE", i3);
        context.sendBroadcastAsUser(intent, userHandle, "android.permission.PACKAGE_VERIFICATION_AGENT");
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    static void processVerificationResponseOnTimeout(int i, com.android.server.pm.PackageVerificationState packageVerificationState, com.android.server.pm.PackageVerificationResponse packageVerificationResponse, com.android.server.pm.PackageManagerService packageManagerService) {
        packageVerificationState.setVerifierResponseOnTimeout(packageVerificationResponse.callerUid, packageVerificationResponse.code);
        processVerificationResponse(i, packageVerificationState, packageVerificationResponse.code, "Verification timed out", packageManagerService);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    static void processVerificationResponse(int i, com.android.server.pm.PackageVerificationState packageVerificationState, com.android.server.pm.PackageVerificationResponse packageVerificationResponse, com.android.server.pm.PackageManagerService packageManagerService) {
        packageVerificationState.setVerifierResponse(packageVerificationResponse.callerUid, packageVerificationResponse.code);
        processVerificationResponse(i, packageVerificationState, packageVerificationResponse.code, "Install not allowed", packageManagerService);
    }

    private static void processVerificationResponse(int i, com.android.server.pm.PackageVerificationState packageVerificationState, int i2, java.lang.String str, com.android.server.pm.PackageManagerService packageManagerService) {
        if (!packageVerificationState.isVerificationComplete()) {
            return;
        }
        com.android.server.pm.VerifyingSession verifyingSession = packageVerificationState.getVerifyingSession();
        android.net.Uri fromFile = verifyingSession != null ? android.net.Uri.fromFile(verifyingSession.mOriginInfo.mResolvedFile) : null;
        if (!packageVerificationState.isInstallAllowed()) {
            i2 = -1;
        }
        int i3 = i2;
        if (packageManagerService != null && verifyingSession != null) {
            broadcastPackageVerified(i, fromFile, i3, null, verifyingSession.getDataLoaderType(), verifyingSession.getUser(), packageManagerService.mContext);
        }
        if (packageVerificationState.isInstallAllowed()) {
            android.util.Slog.i("PackageManager", "Continuing with installation of " + fromFile);
        } else {
            java.lang.String str2 = str + " for " + fromFile;
            android.util.Slog.i("PackageManager", str2);
            if (verifyingSession != null) {
                verifyingSession.setReturnCode(-22, str2);
            }
        }
        if (packageManagerService != null && packageVerificationState.areAllVerificationsComplete()) {
            packageManagerService.mPendingVerification.remove(i);
        }
        android.os.Trace.asyncTraceEnd(262144L, "verification", i);
        if (verifyingSession != null) {
            verifyingSession.handleVerificationFinished();
        }
    }
}
