package com.android.server;

/* loaded from: classes.dex */
public class SecurityStateManagerService extends android.os.ISecurityStateManager.Stub {
    static final java.util.regex.Pattern KERNEL_RELEASE_PATTERN = java.util.regex.Pattern.compile("(\\d+\\.\\d+\\.\\d+)(.*)");
    private static final java.lang.String TAG = "SecurityStateManagerService";
    static final java.lang.String VENDOR_SECURITY_PATCH_PROPERTY_KEY = "ro.vendor.build.security_patch";
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;

    public SecurityStateManagerService(android.content.Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public android.os.Bundle getGlobalSecurityState() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("system_spl", android.os.Build.VERSION.SECURITY_PATCH);
        bundle.putString("vendor_spl", android.os.SystemProperties.get(VENDOR_SECURITY_PATCH_PROPERTY_KEY, ""));
        java.lang.String string = this.mContext.getString(android.R.string.config_defaultCredentialManagerHybridService);
        if (!string.isEmpty()) {
            bundle.putString(string, getSpl(string));
        }
        bundle.putString("kernel_version", getKernelVersion());
        addWebViewPackages(bundle);
        addSecurityStatePackages(bundle);
        return bundle;
    }

    private java.lang.String getSpl(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                return this.mPackageManager.getPackageInfo(str, 0).versionName;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to get SPL for package %s.", new java.lang.Object[]{str}), e);
                return "";
            }
        }
        return "";
    }

    private java.lang.String getKernelVersion() {
        java.util.regex.Matcher matcher = KERNEL_RELEASE_PATTERN.matcher(android.os.VintfRuntimeInfo.getKernelVersion());
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    private void addWebViewPackages(android.os.Bundle bundle) {
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : android.webkit.WebViewUpdateService.getAllWebViewPackages()) {
            java.lang.String str = webViewProviderInfo.packageName;
            bundle.putString(str, getSpl(str));
        }
    }

    private void addSecurityStatePackages(android.os.Bundle bundle) {
        for (java.lang.String str : this.mContext.getResources().getStringArray(android.R.array.config_screenThresholdLevels)) {
            bundle.putString(str, getSpl(str));
        }
    }
}
