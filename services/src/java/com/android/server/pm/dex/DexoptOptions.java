package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public final class DexoptOptions {
    public static final java.lang.String COMPILER_FILTER_NOOP = "skip";
    public static final int DEXOPT_AS_SHARED_LIBRARY = 64;
    public static final int DEXOPT_BOOT_COMPLETE = 4;
    public static final int DEXOPT_CHECK_FOR_PROFILES_UPDATES = 1;
    public static final int DEXOPT_DOWNGRADE = 32;
    public static final int DEXOPT_FORCE = 2;
    public static final int DEXOPT_FOR_RESTORE = 2048;
    public static final int DEXOPT_IDLE_BACKGROUND_JOB = 512;
    public static final int DEXOPT_INSTALL_WITH_DEX_METADATA_FILE = 1024;
    public static final int DEXOPT_ONLY_SECONDARY_DEX = 8;
    private static final java.lang.String TAG = "DexoptOptions";
    private final int mCompilationReason;
    private final java.lang.String mCompilerFilter;
    private final int mFlags;
    private final java.lang.String mPackageName;
    private final java.lang.String mSplitName;

    public DexoptOptions(java.lang.String str, java.lang.String str2, int i) {
        this(str, -1, str2, null, i);
    }

    public DexoptOptions(java.lang.String str, int i, int i2) {
        this(str, i, com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i), null, i2);
    }

    public DexoptOptions(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2) {
        if ((i2 & (-3696)) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid flags : " + java.lang.Integer.toHexString(i2));
        }
        this.mPackageName = str;
        this.mCompilerFilter = str2;
        this.mFlags = i2;
        this.mSplitName = str3;
        this.mCompilationReason = i;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public boolean isCheckForProfileUpdates() {
        return (this.mFlags & 1) != 0;
    }

    public java.lang.String getCompilerFilter() {
        return this.mCompilerFilter;
    }

    public boolean isForce() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isBootComplete() {
        return (this.mFlags & 4) != 0;
    }

    public boolean isDexoptOnlySecondaryDex() {
        return (this.mFlags & 8) != 0;
    }

    public boolean isDowngrade() {
        return (this.mFlags & 32) != 0;
    }

    public boolean isDexoptAsSharedLibrary() {
        return (this.mFlags & 64) != 0;
    }

    public boolean isDexoptIdleBackgroundJob() {
        return (this.mFlags & 512) != 0;
    }

    public boolean isDexoptInstallWithDexMetadata() {
        return (this.mFlags & 1024) != 0;
    }

    public boolean isDexoptInstallForRestore() {
        return (this.mFlags & 2048) != 0;
    }

    public java.lang.String getSplitName() {
        return this.mSplitName;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getCompilationReason() {
        return this.mCompilationReason;
    }

    public boolean isCompilationEnabled() {
        return !this.mCompilerFilter.equals(COMPILER_FILTER_NOOP);
    }

    public com.android.server.pm.dex.DexoptOptions overrideCompilerFilter(java.lang.String str) {
        return new com.android.server.pm.dex.DexoptOptions(this.mPackageName, this.mCompilationReason, str, this.mSplitName, this.mFlags);
    }

    @android.annotation.NonNull
    public static java.lang.String convertToArtServiceDexoptReason(int i) {
        switch (i) {
            case 0:
                return "first-boot";
            case 1:
                return "boot-after-ota";
            case 2:
            case 10:
            case 14:
                throw new java.lang.UnsupportedOperationException("ART Service unsupported compilation reason " + i);
            case 3:
                return "install";
            case 4:
                return "install-fast";
            case 5:
                return "install-bulk";
            case 6:
                return "install-bulk-secondary";
            case 7:
                return "install-bulk-downgraded";
            case 8:
                return "install-bulk-secondary-downgraded";
            case 9:
                return "bg-dexopt";
            case 11:
                return "inactive";
            case 12:
                return "cmdline";
            case 13:
                return "boot-after-mainline-update";
            default:
                throw new java.lang.IllegalArgumentException("Invalid compilation reason " + i);
        }
    }

    @android.annotation.NonNull
    public com.android.server.art.model.DexoptParams convertToDexoptParams(int i) {
        int i2;
        int i3;
        if (this.mSplitName != null) {
            throw new java.lang.UnsupportedOperationException("Request to optimize only split " + this.mSplitName + " for " + this.mPackageName);
        }
        if ((this.mFlags & 1) == 0 && dalvik.system.DexFile.isProfileGuidedCompilerFilter(this.mCompilerFilter)) {
            throw new java.lang.IllegalArgumentException("DEXOPT_CHECK_FOR_PROFILES_UPDATES must be set with profile guided filter");
        }
        if ((this.mFlags & 2) != 0) {
            i |= 16;
        }
        if ((this.mFlags & 8) != 0) {
            i2 = i | 2;
        } else {
            i2 = i | 1;
        }
        if ((this.mFlags & 32) != 0) {
            i2 |= 8;
        }
        if ((this.mFlags & 1024) == 0) {
            android.util.Log.w(TAG, "DEXOPT_INSTALL_WITH_DEX_METADATA_FILE not set in request to optimise " + this.mPackageName + " - ART Service will unconditionally use a DM file if present.");
        }
        if ((this.mFlags & 4) != 0) {
            if ((this.mFlags & 2048) != 0) {
                i3 = 80;
            } else if ((this.mFlags & 512) != 0) {
                i3 = 40;
            } else {
                i3 = 60;
            }
        } else {
            i3 = 100;
        }
        return new com.android.server.art.model.DexoptParams.Builder(convertToArtServiceDexoptReason(this.mCompilationReason), i2).setCompilerFilter(this.mCompilerFilter).setPriorityClass(i3).build();
    }
}
