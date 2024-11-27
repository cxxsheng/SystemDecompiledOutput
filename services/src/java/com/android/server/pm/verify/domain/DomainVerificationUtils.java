package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public final class DomainVerificationUtils {
    private static final java.lang.ThreadLocal<java.util.regex.Matcher> sCachedMatcher = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.server.pm.verify.domain.DomainVerificationUtils$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            java.util.regex.Matcher lambda$static$0;
            lambda$static$0 = com.android.server.pm.verify.domain.DomainVerificationUtils.lambda$static$0();
            return lambda$static$0;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.regex.Matcher lambda$static$0() {
        return android.util.Patterns.DOMAIN_NAME.matcher("");
    }

    static android.content.pm.PackageManager.NameNotFoundException throwPackageUnavailable(@android.annotation.NonNull java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new android.content.pm.PackageManager.NameNotFoundException("Package " + str + " unavailable");
    }

    public static boolean isDomainVerificationIntent(android.content.Intent intent, long j) {
        int size;
        if (!intent.isWebIntent()) {
            return false;
        }
        java.lang.String host = intent.getData().getHost();
        if (android.text.TextUtils.isEmpty(host) || !sCachedMatcher.get().reset(host).matches() || (size = com.android.internal.util.CollectionUtils.size(intent.getCategories())) > 2) {
            return false;
        }
        if (size == 2) {
            return intent.hasCategory("android.intent.category.DEFAULT") && intent.hasCategory("android.intent.category.BROWSABLE");
        }
        boolean z = (j & 65536) != 0;
        if (size == 0) {
            return z;
        }
        if (intent.hasCategory("android.intent.category.BROWSABLE")) {
            return z;
        }
        return intent.hasCategory("android.intent.category.DEFAULT");
    }

    static boolean isChangeEnabled(com.android.server.compat.PlatformCompat platformCompat, com.android.server.pm.pkg.AndroidPackage androidPackage, long j) {
        return platformCompat.isChangeEnabledInternalNoLogging(j, buildMockAppInfo(androidPackage));
    }

    @android.annotation.NonNull
    private static android.content.pm.ApplicationInfo buildMockAppInfo(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.packageName = androidPackage.getPackageName();
        applicationInfo.targetSdkVersion = androidPackage.getTargetSdkVersion();
        return applicationInfo;
    }
}
