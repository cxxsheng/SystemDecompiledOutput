package com.android.internal.pm.parsing;

/* loaded from: classes5.dex */
public interface IPackageCacher {
    void cacheResult(java.io.File file, int i, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage);

    com.android.internal.pm.parsing.pkg.ParsedPackage getCachedResult(java.io.File file, int i);
}
