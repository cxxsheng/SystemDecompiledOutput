package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class PackageBackwardCompatibility extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    private static final com.android.server.pm.parsing.library.PackageBackwardCompatibility INSTANCE;
    private static final java.lang.String TAG = com.android.server.pm.parsing.library.PackageBackwardCompatibility.class.getSimpleName();
    private final boolean mBootClassPathContainsATB;
    private final com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[] mPackageUpdaters;

    static {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new com.android.server.pm.parsing.library.AndroidNetIpSecIkeUpdater());
        arrayList.add(new com.android.server.pm.parsing.library.ComGoogleAndroidMapsUpdater());
        arrayList.add(new com.android.server.pm.parsing.library.OrgApacheHttpLegacyUpdater());
        arrayList.add(new com.android.server.pm.parsing.library.AndroidHidlUpdater());
        arrayList.add(new com.android.server.pm.parsing.library.PackageBackwardCompatibility.AndroidTestRunnerSplitUpdater());
        boolean z = !addUpdaterForAndroidTestBase(arrayList);
        arrayList.add(new com.android.server.pm.parsing.library.ApexSharedLibraryUpdater(com.android.server.SystemConfig.getInstance().getSharedLibraries()));
        INSTANCE = new com.android.server.pm.parsing.library.PackageBackwardCompatibility(z, (com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[]) arrayList.toArray(new com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[0]));
    }

    private static boolean addUpdaterForAndroidTestBase(java.util.List<com.android.server.pm.parsing.library.PackageSharedLibraryUpdater> list) {
        try {
            r1 = com.android.internal.pm.pkg.parsing.ParsingPackage.class.getClassLoader().loadClass("android.content.pm.AndroidTestBaseUpdater") != null;
            android.util.Log.i(TAG, "Loaded android.content.pm.AndroidTestBaseUpdater");
        } catch (java.lang.ClassNotFoundException e) {
            android.util.Log.i(TAG, "Could not find android.content.pm.AndroidTestBaseUpdater, ignoring");
        }
        if (r1) {
            list.add(new com.android.server.pm.parsing.library.AndroidTestBaseUpdater());
        } else {
            list.add(new com.android.server.pm.parsing.library.PackageBackwardCompatibility.RemoveUnnecessaryAndroidTestBaseLibrary());
        }
        return r1;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.pm.parsing.library.PackageSharedLibraryUpdater getInstance() {
        return INSTANCE;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[] getPackageUpdaters() {
        return this.mPackageUpdaters;
    }

    private PackageBackwardCompatibility(boolean z, com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[] packageSharedLibraryUpdaterArr) {
        this.mBootClassPathContainsATB = z;
        this.mPackageUpdaters = packageSharedLibraryUpdaterArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void modifySharedLibraries(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        INSTANCE.updatePackage(parsedPackage, z, z2);
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        for (com.android.server.pm.parsing.library.PackageSharedLibraryUpdater packageSharedLibraryUpdater : this.mPackageUpdaters) {
            packageSharedLibraryUpdater.updatePackage(parsedPackage, z, z2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static boolean bootClassPathContainsATB() {
        return INSTANCE.mBootClassPathContainsATB;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class AndroidTestRunnerSplitUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
            prefixImplicitDependency(parsedPackage, "android.test.runner", "android.test.mock");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class RemoveUnnecessaryOrgApacheHttpLegacyLibrary extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, com.android.server.pm.parsing.library.SharedLibraryNames.ORG_APACHE_HTTP_LEGACY);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class RemoveUnnecessaryAndroidTestBaseLibrary extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "android.test.base");
        }
    }
}
