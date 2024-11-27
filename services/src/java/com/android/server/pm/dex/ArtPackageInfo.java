package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class ArtPackageInfo {
    private final java.util.List<java.lang.String> mCodePaths;
    private final java.util.List<java.lang.String> mInstructionSets;
    private final java.lang.String mOatDir;
    private final java.lang.String mPackageName;

    public ArtPackageInfo(java.lang.String str, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.lang.String str2) {
        this.mPackageName = str;
        this.mInstructionSets = list;
        this.mCodePaths = list2;
        this.mOatDir = str2;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.List<java.lang.String> getInstructionSets() {
        return this.mInstructionSets;
    }

    public java.util.List<java.lang.String> getCodePaths() {
        return this.mCodePaths;
    }

    public java.lang.String getOatDir() {
        return this.mOatDir;
    }
}
