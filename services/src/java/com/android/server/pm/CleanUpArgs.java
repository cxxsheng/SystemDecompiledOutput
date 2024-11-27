package com.android.server.pm;

/* loaded from: classes2.dex */
final class CleanUpArgs {

    @android.annotation.NonNull
    private final java.io.File mCodeFile;

    @android.annotation.NonNull
    private final java.lang.String[] mInstructionSets;

    @android.annotation.NonNull
    private final java.lang.String mPackageName;

    CleanUpArgs(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String[] strArr) {
        this.mPackageName = str;
        this.mCodeFile = new java.io.File(str2);
        this.mInstructionSets = strArr;
    }

    @android.annotation.NonNull
    java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @android.annotation.NonNull
    java.io.File getCodeFile() {
        return this.mCodeFile;
    }

    @android.annotation.NonNull
    java.lang.String getCodePath() {
        return this.mCodeFile.getAbsolutePath();
    }

    @android.annotation.NonNull
    java.lang.String[] getInstructionSets() {
        return this.mInstructionSets;
    }
}
