package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class AndroidNetIpSecIkeUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    private static final java.lang.String LIBRARY_NAME = "android.net.ipsec.ike";

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, LIBRARY_NAME);
    }
}
