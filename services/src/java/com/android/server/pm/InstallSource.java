package com.android.server.pm;

/* loaded from: classes2.dex */
public final class InstallSource {
    static final com.android.server.pm.InstallSource EMPTY = new com.android.server.pm.InstallSource(null, null, null, -1, null, null, false, false, null, 0);
    private static final com.android.server.pm.InstallSource EMPTY_ORPHANED = new com.android.server.pm.InstallSource(null, null, null, -1, null, null, true, false, null, 0);

    @android.annotation.Nullable
    final java.lang.String mInitiatingPackageName;

    @android.annotation.Nullable
    final com.android.server.pm.PackageSignatures mInitiatingPackageSignatures;

    @android.annotation.Nullable
    final java.lang.String mInstallerAttributionTag;

    @android.annotation.Nullable
    final java.lang.String mInstallerPackageName;
    final int mInstallerPackageUid;
    final boolean mIsInitiatingPackageUninstalled;
    final boolean mIsOrphaned;

    @android.annotation.Nullable
    final java.lang.String mOriginatingPackageName;
    final int mPackageSource;

    @android.annotation.Nullable
    final java.lang.String mUpdateOwnerPackageName;

    static com.android.server.pm.InstallSource create(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5, boolean z, boolean z2) {
        return create(str, str2, str3, i, str4, str5, 0, z, z2);
    }

    static com.android.server.pm.InstallSource create(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5, int i2) {
        return create(str, str2, str3, i, str4, str5, i2, false, false);
    }

    static com.android.server.pm.InstallSource create(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5, int i2, boolean z, boolean z2) {
        return createInternal(intern(str), intern(str2), intern(str3), i, intern(str4), str5, i2, z, z2, null);
    }

    private static com.android.server.pm.InstallSource createInternal(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5, int i2, boolean z, boolean z2, @android.annotation.Nullable com.android.server.pm.PackageSignatures packageSignatures) {
        if (str == null && str2 == null && str3 == null && str4 == null && packageSignatures == null && !z2 && i2 == 0) {
            return z ? EMPTY_ORPHANED : EMPTY;
        }
        return new com.android.server.pm.InstallSource(str, str2, str3, i, str4, str5, z, z2, packageSignatures, i2);
    }

    private InstallSource(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5, boolean z, boolean z2, @android.annotation.Nullable com.android.server.pm.PackageSignatures packageSignatures, int i2) {
        if (str == null) {
            com.android.internal.util.Preconditions.checkArgument(packageSignatures == null);
            com.android.internal.util.Preconditions.checkArgument(!z2);
        }
        this.mInitiatingPackageName = str;
        this.mOriginatingPackageName = str2;
        this.mInstallerPackageName = str3;
        this.mInstallerPackageUid = i;
        this.mUpdateOwnerPackageName = str4;
        this.mInstallerAttributionTag = str5;
        this.mIsOrphaned = z;
        this.mIsInitiatingPackageUninstalled = z2;
        this.mInitiatingPackageSignatures = packageSignatures;
        this.mPackageSource = i2;
    }

    com.android.server.pm.InstallSource setInstallerPackage(@android.annotation.Nullable java.lang.String str, int i) {
        if (java.util.Objects.equals(str, this.mInstallerPackageName)) {
            return this;
        }
        return createInternal(this.mInitiatingPackageName, this.mOriginatingPackageName, intern(str), i, this.mUpdateOwnerPackageName, this.mInstallerAttributionTag, this.mPackageSource, this.mIsOrphaned, this.mIsInitiatingPackageUninstalled, this.mInitiatingPackageSignatures);
    }

    com.android.server.pm.InstallSource setUpdateOwnerPackageName(@android.annotation.Nullable java.lang.String str) {
        if (java.util.Objects.equals(str, this.mUpdateOwnerPackageName)) {
            return this;
        }
        return createInternal(this.mInitiatingPackageName, this.mOriginatingPackageName, this.mInstallerPackageName, this.mInstallerPackageUid, intern(str), this.mInstallerAttributionTag, this.mPackageSource, this.mIsOrphaned, this.mIsInitiatingPackageUninstalled, this.mInitiatingPackageSignatures);
    }

    com.android.server.pm.InstallSource setIsOrphaned(boolean z) {
        if (z == this.mIsOrphaned) {
            return this;
        }
        return createInternal(this.mInitiatingPackageName, this.mOriginatingPackageName, this.mInstallerPackageName, this.mInstallerPackageUid, this.mUpdateOwnerPackageName, this.mInstallerAttributionTag, this.mPackageSource, z, this.mIsInitiatingPackageUninstalled, this.mInitiatingPackageSignatures);
    }

    com.android.server.pm.InstallSource setInitiatingPackageSignatures(@android.annotation.Nullable com.android.server.pm.PackageSignatures packageSignatures) {
        if (packageSignatures == this.mInitiatingPackageSignatures) {
            return this;
        }
        return createInternal(this.mInitiatingPackageName, this.mOriginatingPackageName, this.mInstallerPackageName, this.mInstallerPackageUid, this.mUpdateOwnerPackageName, this.mInstallerAttributionTag, this.mPackageSource, this.mIsOrphaned, this.mIsInitiatingPackageUninstalled, packageSignatures);
    }

    com.android.server.pm.InstallSource removeInstallerPackage(@android.annotation.Nullable java.lang.String str) {
        boolean z;
        boolean z2;
        java.lang.String str2;
        java.lang.String str3;
        int i;
        boolean z3;
        java.lang.String str4;
        if (str == null) {
            return this;
        }
        boolean z4 = this.mIsInitiatingPackageUninstalled;
        java.lang.String str5 = this.mOriginatingPackageName;
        java.lang.String str6 = this.mInstallerPackageName;
        java.lang.String str7 = this.mUpdateOwnerPackageName;
        int i2 = this.mInstallerPackageUid;
        boolean z5 = this.mIsOrphaned;
        boolean z6 = true;
        if (str.equals(this.mInitiatingPackageName) && !z4) {
            z = true;
            z2 = true;
        } else {
            z = false;
            z2 = z4;
        }
        if (!str.equals(str5)) {
            str2 = str5;
        } else {
            z = true;
            str2 = null;
        }
        if (!str.equals(str6)) {
            str3 = str6;
            i = i2;
            z3 = z5;
        } else {
            i = -1;
            z = true;
            z3 = true;
            str3 = null;
        }
        if (!str.equals(str7)) {
            str4 = str7;
            z6 = z;
        } else {
            str4 = null;
        }
        return !z6 ? this : createInternal(this.mInitiatingPackageName, str2, str3, i, str4, null, this.mPackageSource, z3, z2, this.mInitiatingPackageSignatures);
    }

    @android.annotation.Nullable
    private static java.lang.String intern(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.intern();
    }
}
