package com.android.server.pm;

/* loaded from: classes2.dex */
public class UpdateOwnershipHelper {
    private static final int MAX_DENYLIST_SIZE = 500;
    private static final java.lang.String TAG_OWNERSHIP_OPT_OUT = "deny-ownership";
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mUpdateOwnerOptOutsToOwners = new android.util.ArrayMap<>(200);
    private final java.lang.Object mLock = new java.lang.Object();

    static boolean hasValidOwnershipDenyList(com.android.server.pm.PackageSetting packageSetting) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageSetting.getPkg();
        return pkg != null && (packageSetting.isSystem() || packageSetting.isUpdatedSystemApp()) && pkg.getProperties().containsKey("android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST") && usesAnyPermission(pkg, "android.permission.INSTALL_PACKAGES", "android.permission.INSTALL_PACKAGE_UPDATES");
    }

    private static boolean usesAnyPermission(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String... strArr) {
        java.util.List usesPermissions = androidPackage.getUsesPermissions();
        for (int i = 0; i < usesPermissions.size(); i++) {
            for (java.lang.String str : strArr) {
                if (str.equals(((com.android.internal.pm.pkg.component.ParsedUsesPermission) usesPermissions.get(i)).getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
    
        android.util.Slog.w("PackageManager", "Deny list defined by " + r0.getPackageName() + " was trucated to maximum size of 500");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.util.ArraySet<java.lang.String> readUpdateOwnerDenyList(com.android.server.pm.PackageSetting packageSetting) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        if (!hasValidOwnershipDenyList(packageSetting) || (pkg = packageSetting.getPkg()) == null) {
            return null;
        }
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(500);
        try {
            int resourceId = ((android.content.pm.PackageManager.Property) pkg.getProperties().get("android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST")).getResourceId();
            android.content.pm.ApplicationInfo generateAppInfoWithoutState = com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(pkg);
            android.content.res.XmlResourceParser xml = android.app.ResourcesManager.getInstance().getResources((android.os.IBinder) null, generateAppInfoWithoutState.sourceDir, generateAppInfoWithoutState.splitSourceDirs, generateAppInfoWithoutState.resourceDirs, generateAppInfoWithoutState.overlayPaths, generateAppInfoWithoutState.sharedLibraryFiles, (java.lang.Integer) null, android.content.res.Configuration.EMPTY, (android.content.res.CompatibilityInfo) null, (java.lang.ClassLoader) null, (java.util.List) null).getXml(resourceId);
            while (true) {
                try {
                    if (xml.getEventType() == 1) {
                        break;
                    }
                    if (xml.next() == 2 && TAG_OWNERSHIP_OPT_OUT.equals(xml.getName())) {
                        xml.next();
                        java.lang.String text = xml.getText();
                        if (text != null && !text.isBlank()) {
                            arraySet.add(text);
                            if (arraySet.size() > 500) {
                                break;
                            }
                        }
                    }
                } finally {
                }
            }
            xml.close();
            return arraySet;
        } catch (java.lang.Exception e) {
            android.util.Slog.e("PackageManager", "Failed to parse update owner list for " + packageSetting.getPackageName(), e);
            return null;
        }
    }

    public void addToUpdateOwnerDenyList(java.lang.String str, android.util.ArraySet<java.lang.String> arraySet) {
        synchronized (this.mLock) {
            for (int i = 0; i < arraySet.size(); i++) {
                try {
                    android.util.ArraySet<java.lang.String> putIfAbsent = this.mUpdateOwnerOptOutsToOwners.putIfAbsent(arraySet.valueAt(i), new android.util.ArraySet<>(new java.lang.String[]{str}));
                    if (putIfAbsent != null) {
                        putIfAbsent.add(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void removeUpdateOwnerDenyList(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mUpdateOwnerOptOutsToOwners.size() - 1; size >= 0; size--) {
                    android.util.ArraySet<java.lang.String> arraySet = this.mUpdateOwnerOptOutsToOwners.get(this.mUpdateOwnerOptOutsToOwners.keyAt(size));
                    if (arraySet.remove(str) && arraySet.isEmpty()) {
                        this.mUpdateOwnerOptOutsToOwners.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUpdateOwnershipDenylisted(java.lang.String str) {
        return this.mUpdateOwnerOptOutsToOwners.containsKey(str);
    }

    public boolean isUpdateOwnershipDenyListProvider(java.lang.String str) {
        if (str == null) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                for (int size = this.mUpdateOwnerOptOutsToOwners.size() - 1; size >= 0; size--) {
                    if (this.mUpdateOwnerOptOutsToOwners.valueAt(size).contains(str)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
