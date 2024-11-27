package com.android.server.pm.parsing;

/* loaded from: classes2.dex */
public class PackageParserUtils {
    @android.annotation.NonNull
    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public static com.android.internal.pm.parsing.PackageParser2 forParsingFileWithDefaults() {
        final com.android.internal.compat.IPlatformCompat asInterface = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));
        return new com.android.internal.pm.parsing.PackageParser2((java.lang.String[]) null, (android.util.DisplayMetrics) null, (com.android.internal.pm.parsing.IPackageCacher) null, new com.android.internal.pm.parsing.PackageParser2.Callback() { // from class: com.android.server.pm.parsing.PackageParserUtils.1
            @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
            public boolean isChangeEnabled(long j, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo) {
                try {
                    return asInterface.isChangeEnabled(j, applicationInfo);
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf("PackageParsing", "IPlatformCompat query failed", e);
                    return true;
                }
            }

            public boolean hasFeature(java.lang.String str) {
                return false;
            }

            public java.util.Set<java.lang.String> getHiddenApiWhitelistedApps() {
                return com.android.server.SystemConfig.getInstance().getHiddenApiWhitelistedApps();
            }

            public java.util.Set<java.lang.String> getInstallConstraintsAllowlist() {
                return com.android.server.SystemConfig.getInstance().getInstallConstraintsAllowlist();
            }
        });
    }
}
