package com.android.modules.utils.build;

/* loaded from: classes.dex */
public final class UnboundedSdkLevel {
    private static final android.util.SparseArray<java.util.Set<java.lang.String>> PREVIOUS_CODENAMES = new android.util.SparseArray<>(4);
    private static final com.android.modules.utils.build.UnboundedSdkLevel sInstance;
    private final java.lang.String mCodename;
    private final boolean mIsReleaseBuild;
    private final java.util.Set<java.lang.String> mKnownCodenames;
    private final int mSdkInt;

    public static boolean isAtLeast(@androidx.annotation.NonNull java.lang.String str) {
        return sInstance.isAtLeastInternal(str);
    }

    public static boolean isAtMost(@androidx.annotation.NonNull java.lang.String str) {
        return sInstance.isAtMostInternal(str);
    }

    static {
        java.util.Set<java.lang.String> set;
        PREVIOUS_CODENAMES.put(29, setOf("Q"));
        PREVIOUS_CODENAMES.put(30, setOf("Q", "R"));
        PREVIOUS_CODENAMES.put(31, setOf("Q", "R", "S"));
        PREVIOUS_CODENAMES.put(32, setOf("Q", "R", "S", "Sv2"));
        int i = android.os.Build.VERSION.SDK_INT;
        java.lang.String str = android.os.Build.VERSION.CODENAME;
        if (com.android.modules.utils.build.SdkLevel.isAtLeastT()) {
            set = android.os.Build.VERSION.KNOWN_CODENAMES;
        } else {
            set = PREVIOUS_CODENAMES.get(android.os.Build.VERSION.SDK_INT);
        }
        sInstance = new com.android.modules.utils.build.UnboundedSdkLevel(i, str, set);
    }

    private static java.util.Set<java.lang.String> setOf(java.lang.String... strArr) {
        if (com.android.modules.utils.build.SdkLevel.isAtLeastR()) {
            return java.util.Set.of((java.lang.Object[]) strArr);
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length);
        for (java.lang.String str : strArr) {
            arraySet.add(str);
        }
        return arraySet;
    }

    @com.android.internal.annotations.VisibleForTesting
    UnboundedSdkLevel(int i, java.lang.String str, java.util.Set<java.lang.String> set) {
        this.mSdkInt = i;
        this.mCodename = str;
        this.mIsReleaseBuild = "REL".equals(str);
        this.mKnownCodenames = set;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isAtLeastInternal(@androidx.annotation.NonNull java.lang.String str) {
        java.lang.String removeFingerprint = removeFingerprint(str);
        if (this.mIsReleaseBuild) {
            if (!isCodename(removeFingerprint)) {
                return this.mSdkInt >= java.lang.Integer.parseInt(removeFingerprint);
            }
            if (!this.mKnownCodenames.contains(removeFingerprint)) {
                return false;
            }
            throw new java.lang.IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
        }
        if (isCodename(removeFingerprint)) {
            return this.mKnownCodenames.contains(removeFingerprint);
        }
        return this.mSdkInt >= java.lang.Integer.parseInt(removeFingerprint);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isAtMostInternal(@androidx.annotation.NonNull java.lang.String str) {
        java.lang.String removeFingerprint = removeFingerprint(str);
        if (!this.mIsReleaseBuild) {
            return isCodename(removeFingerprint) ? !this.mKnownCodenames.contains(removeFingerprint) || this.mCodename.equals(removeFingerprint) : this.mSdkInt < java.lang.Integer.parseInt(removeFingerprint);
        }
        if (!isCodename(removeFingerprint)) {
            return this.mSdkInt <= java.lang.Integer.parseInt(removeFingerprint);
        }
        if (!this.mKnownCodenames.contains(removeFingerprint)) {
            return true;
        }
        throw new java.lang.IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String removeFingerprint(@androidx.annotation.NonNull java.lang.String str) {
        int indexOf;
        if (isCodename(str) && (indexOf = str.indexOf(46)) != -1) {
            return str.substring(0, indexOf);
        }
        return str;
    }

    private boolean isCodename(java.lang.String str) {
        if (str.length() == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        return java.lang.Character.isUpperCase(str.charAt(0));
    }
}
