package com.android.internal.pm.parsing.pkg;

/* loaded from: classes5.dex */
public interface AndroidPackageInternal extends com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package {
    java.lang.String[] getUsesLibrariesSorted();

    java.lang.String[] getUsesOptionalLibrariesSorted();

    java.lang.String[] getUsesSdkLibrariesSorted();

    java.lang.String[] getUsesStaticLibrariesSorted();
}
