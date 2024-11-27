package com.android.server.contentprotection;

/* loaded from: classes.dex */
public class ContentProtectionConsentManager {
    private static final java.lang.String KEY_CONTENT_PROTECTION_USER_CONSENT = "content_protection_user_consent";
    private static final java.lang.String KEY_PACKAGE_VERIFIER_USER_CONSENT = "package_verifier_user_consent";
    private static final java.lang.String TAG = "ContentProtectionConsentManager";
    private volatile boolean mCachedContentProtectionUserConsent;
    private volatile boolean mCachedPackageVerifierConsent;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public final android.database.ContentObserver mContentObserver;

    @android.annotation.NonNull
    private final android.content.ContentResolver mContentResolver;

    @android.annotation.NonNull
    private final android.app.admin.DevicePolicyCache mDevicePolicyCache;

    @android.annotation.NonNull
    private final android.app.admin.DevicePolicyManagerInternal mDevicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);

    public ContentProtectionConsentManager(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.app.admin.DevicePolicyCache devicePolicyCache) {
        this.mContentResolver = contentResolver;
        this.mDevicePolicyCache = devicePolicyCache;
        this.mContentObserver = new com.android.server.contentprotection.ContentProtectionConsentManager.SettingsObserver(handler);
        registerSettingsGlobalObserver(KEY_PACKAGE_VERIFIER_USER_CONSENT);
        registerSettingsGlobalObserver(KEY_CONTENT_PROTECTION_USER_CONSENT);
        readPackageVerifierConsentGranted();
        readContentProtectionUserConsentGranted();
    }

    public boolean isConsentGranted(int i) {
        return this.mCachedPackageVerifierConsent && isContentProtectionConsentGranted(i);
    }

    private boolean isPackageVerifierConsentGranted() {
        return android.provider.Settings.Global.getInt(this.mContentResolver, KEY_PACKAGE_VERIFIER_USER_CONSENT, 0) >= 1;
    }

    private boolean isContentProtectionUserConsentGranted() {
        return android.provider.Settings.Global.getInt(this.mContentResolver, KEY_CONTENT_PROTECTION_USER_CONSENT, 0) >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readPackageVerifierConsentGranted() {
        this.mCachedPackageVerifierConsent = isPackageVerifierConsentGranted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readContentProtectionUserConsentGranted() {
        this.mCachedContentProtectionUserConsent = isContentProtectionUserConsentGranted();
    }

    private boolean isUserOrganizationManaged(int i) {
        return this.mDevicePolicyManagerInternal.isUserOrganizationManaged(i);
    }

    private boolean isContentProtectionPolicyGranted(int i) {
        if (!android.view.contentprotection.flags.Flags.manageDevicePolicyEnabled()) {
            return false;
        }
        switch (this.mDevicePolicyCache.getContentProtectionPolicy(i)) {
            case 0:
                return this.mCachedContentProtectionUserConsent;
            case 1:
            default:
                return false;
            case 2:
                return true;
        }
    }

    private boolean isContentProtectionConsentGranted(int i) {
        if (!android.view.contentprotection.flags.Flags.manageDevicePolicyEnabled()) {
            return this.mCachedContentProtectionUserConsent && !isUserOrganizationManaged(i);
        }
        if (isUserOrganizationManaged(i)) {
            return isContentProtectionPolicyGranted(i);
        }
        return this.mCachedContentProtectionUserConsent;
    }

    private void registerSettingsGlobalObserver(@android.annotation.NonNull java.lang.String str) {
        registerSettingsObserver(android.provider.Settings.Global.getUriFor(str));
    }

    private void registerSettingsObserver(@android.annotation.NonNull android.net.Uri uri) {
        this.mContentResolver.registerContentObserver(uri, false, this.mContentObserver, -1);
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.database.ContentObserver
        public void onChange(boolean z, @android.annotation.Nullable android.net.Uri uri, int i) {
            java.lang.String lastPathSegment;
            char c;
            if (uri == null || (lastPathSegment = uri.getLastPathSegment()) == null) {
                return;
            }
            switch (lastPathSegment.hashCode()) {
                case 480463670:
                    if (lastPathSegment.equals(com.android.server.contentprotection.ContentProtectionConsentManager.KEY_PACKAGE_VERIFIER_USER_CONSENT)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 802188678:
                    if (lastPathSegment.equals(com.android.server.contentprotection.ContentProtectionConsentManager.KEY_CONTENT_PROTECTION_USER_CONSENT)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.contentprotection.ContentProtectionConsentManager.this.readPackageVerifierConsentGranted();
                    break;
                case 1:
                    com.android.server.contentprotection.ContentProtectionConsentManager.this.readContentProtectionUserConsentGranted();
                    break;
                default:
                    android.util.Slog.w(com.android.server.contentprotection.ContentProtectionConsentManager.TAG, "Ignoring unexpected property: " + lastPathSegment);
                    break;
            }
        }
    }
}
