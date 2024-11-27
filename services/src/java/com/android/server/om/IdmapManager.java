package com.android.server.om;

/* loaded from: classes2.dex */
final class IdmapManager {
    static final int IDMAP_IS_MODIFIED = 2;
    static final int IDMAP_IS_VERIFIED = 1;
    static final int IDMAP_NOT_EXIST = 0;
    private static final boolean VENDOR_IS_Q_OR_LATER;
    private final java.lang.String mConfigSignaturePackage;
    private final com.android.server.om.IdmapDaemon mIdmapDaemon;
    private final com.android.server.om.PackageManagerHelper mPackageManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IdmapStatus {
    }

    static {
        boolean z = true;
        try {
            if (java.lang.Integer.parseInt(android.os.SystemProperties.get("ro.vndk.version", "29")) < 29) {
                z = false;
            }
        } catch (java.lang.NumberFormatException e) {
        }
        VENDOR_IS_Q_OR_LATER = z;
    }

    IdmapManager(com.android.server.om.IdmapDaemon idmapDaemon, com.android.server.om.PackageManagerHelper packageManagerHelper) {
        this.mPackageManager = packageManagerHelper;
        this.mIdmapDaemon = idmapDaemon;
        this.mConfigSignaturePackage = packageManagerHelper.getConfigSignaturePackage();
    }

    int createIdmap(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2, java.lang.String str, java.lang.String str2, int i) {
        java.lang.String path = ((com.android.server.pm.pkg.AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath();
        try {
            int calculateFulfilledPolicies = calculateFulfilledPolicies(androidPackage, packageState, androidPackage2, i);
            boolean enforceOverlayable = enforceOverlayable(packageState, androidPackage2);
            if (this.mIdmapDaemon.verifyIdmap(path, str, str2, calculateFulfilledPolicies, enforceOverlayable, i)) {
                return 1;
            }
            return this.mIdmapDaemon.createIdmap(path, str, str2, calculateFulfilledPolicies, enforceOverlayable, i) != null ? 3 : 0;
        } catch (java.lang.Exception e) {
            android.util.Slog.w("OverlayManager", "failed to generate idmap for " + path + " and " + str, e);
            return 0;
        }
    }

    boolean removeIdmap(@android.annotation.NonNull android.content.om.OverlayInfo overlayInfo, int i) {
        try {
            return this.mIdmapDaemon.removeIdmap(overlayInfo.baseCodePath, i);
        } catch (java.lang.Exception e) {
            android.util.Slog.w("OverlayManager", "failed to remove idmap for " + overlayInfo.baseCodePath, e);
            return false;
        }
    }

    boolean idmapExists(@android.annotation.NonNull android.content.om.OverlayInfo overlayInfo) {
        return this.mIdmapDaemon.idmapExists(overlayInfo.baseCodePath, overlayInfo.userId);
    }

    java.util.List<android.os.FabricatedOverlayInfo> getFabricatedOverlayInfos() {
        return this.mIdmapDaemon.getFabricatedOverlayInfos();
    }

    android.os.FabricatedOverlayInfo createFabricatedOverlay(@android.annotation.NonNull android.os.FabricatedOverlayInternal fabricatedOverlayInternal) {
        return this.mIdmapDaemon.createFabricatedOverlay(fabricatedOverlayInternal);
    }

    boolean deleteFabricatedOverlay(@android.annotation.NonNull java.lang.String str) {
        return this.mIdmapDaemon.deleteFabricatedOverlay(str);
    }

    java.lang.String dumpIdmap(@android.annotation.NonNull java.lang.String str) {
        return this.mIdmapDaemon.dumpIdmap(str);
    }

    private boolean enforceOverlayable(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage.getTargetSdkVersion() >= 29) {
            return true;
        }
        if (packageState.isVendor()) {
            return VENDOR_IS_Q_OR_LATER;
        }
        return (packageState.isSystem() || androidPackage.isSignedWithPlatformKey()) ? false : true;
    }

    private int calculateFulfilledPolicies(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2, int i) {
        int i2;
        if (!this.mPackageManager.signaturesMatching(androidPackage.getPackageName(), androidPackage2.getPackageName(), i)) {
            i2 = 1;
        } else {
            i2 = 17;
        }
        if (matchesActorSignature(androidPackage, androidPackage2, i)) {
            i2 |= 128;
        }
        if (!android.text.TextUtils.isEmpty(this.mConfigSignaturePackage) && this.mPackageManager.signaturesMatching(this.mConfigSignaturePackage, androidPackage2.getPackageName(), i)) {
            i2 |= 256;
        }
        if (packageState.isVendor()) {
            return i2 | 4;
        }
        if (packageState.isProduct()) {
            return i2 | 8;
        }
        if (packageState.isOdm()) {
            return i2 | 32;
        }
        if (packageState.isOem()) {
            return i2 | 64;
        }
        if (packageState.isSystem() || packageState.isSystemExt()) {
            return i2 | 2;
        }
        return i2;
    }

    private boolean matchesActorSignature(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2, int i) {
        java.lang.String overlayTargetOverlayableName = androidPackage2.getOverlayTargetOverlayableName();
        if (overlayTargetOverlayableName != null && !this.mPackageManager.getNamedActors().isEmpty()) {
            try {
                android.content.om.OverlayableInfo overlayableForTarget = this.mPackageManager.getOverlayableForTarget(androidPackage.getPackageName(), overlayTargetOverlayableName, i);
                if (overlayableForTarget != null && overlayableForTarget.actor != null) {
                    if (this.mPackageManager.signaturesMatching((java.lang.String) com.android.server.om.OverlayActorEnforcer.getPackageNameForActor(overlayableForTarget.actor, this.mPackageManager.getNamedActors()).first, androidPackage2.getPackageName(), i)) {
                        return true;
                    }
                    return false;
                }
                return false;
            } catch (java.io.IOException e) {
                return false;
            }
        }
        return false;
    }
}
