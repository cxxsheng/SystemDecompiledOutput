package android.internal.modules.utils.build;

/* loaded from: classes2.dex */
public final class UnboundedSdkLevel {
    private static final android.util.SparseArray<java.util.Set<java.lang.String>> PREVIOUS_CODENAMES = new android.util.SparseArray<>(4);
    private static final android.internal.modules.utils.build.UnboundedSdkLevel sInstance;
    private final java.lang.String mCodename;
    private final boolean mIsReleaseBuild;
    private final java.util.Set<java.lang.String> mKnownCodenames;
    private final int mSdkInt;

    public static boolean isAtLeast(java.lang.String str) {
        return sInstance.isAtLeastInternal(str);
    }

    public static boolean isAtMost(java.lang.String str) {
        return sInstance.isAtMostInternal(str);
    }

    static {
        java.util.Set<java.lang.String> set;
        PREVIOUS_CODENAMES.put(29, setOf(android.hardware.gnss.GnssSignalType.CODE_TYPE_Q));
        PREVIOUS_CODENAMES.put(30, setOf(android.hardware.gnss.GnssSignalType.CODE_TYPE_Q, "R"));
        PREVIOUS_CODENAMES.put(31, setOf(android.hardware.gnss.GnssSignalType.CODE_TYPE_Q, "R", android.hardware.gnss.GnssSignalType.CODE_TYPE_S));
        PREVIOUS_CODENAMES.put(32, setOf(android.hardware.gnss.GnssSignalType.CODE_TYPE_Q, "R", android.hardware.gnss.GnssSignalType.CODE_TYPE_S, "Sv2"));
        int i = android.os.Build.VERSION.SDK_INT;
        java.lang.String str = android.os.Build.VERSION.CODENAME;
        if (android.internal.modules.utils.build.SdkLevel.isAtLeastT()) {
            set = android.os.Build.VERSION.KNOWN_CODENAMES;
        } else {
            set = PREVIOUS_CODENAMES.get(android.os.Build.VERSION.SDK_INT);
        }
        sInstance = new android.internal.modules.utils.build.UnboundedSdkLevel(i, str, set);
    }

    private static java.util.Set<java.lang.String> setOf(java.lang.String... strArr) {
        if (android.internal.modules.utils.build.SdkLevel.isAtLeastR()) {
            return java.util.Set.of((java.lang.Object[]) strArr);
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length);
        for (java.lang.String str : strArr) {
            arraySet.add(str);
        }
        return arraySet;
    }

    UnboundedSdkLevel(int i, java.lang.String str, java.util.Set<java.lang.String> set) {
        this.mSdkInt = i;
        this.mCodename = str;
        this.mIsReleaseBuild = "REL".equals(str);
        this.mKnownCodenames = set;
    }

    boolean isAtLeastInternal(java.lang.String str) {
        java.lang.String removeFingerprint = removeFingerprint(str);
        if (this.mIsReleaseBuild) {
            if (!isCodename(removeFingerprint)) {
                return this.mSdkInt >= java.lang.Integer.parseInt(removeFingerprint);
            }
            if (this.mKnownCodenames.contains(removeFingerprint)) {
                throw new java.lang.IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
            }
            return false;
        }
        if (isCodename(removeFingerprint)) {
            return this.mKnownCodenames.contains(removeFingerprint);
        }
        return this.mSdkInt >= java.lang.Integer.parseInt(removeFingerprint);
    }

    boolean isAtMostInternal(java.lang.String str) {
        java.lang.String removeFingerprint = removeFingerprint(str);
        if (!this.mIsReleaseBuild) {
            return isCodename(removeFingerprint) ? !this.mKnownCodenames.contains(removeFingerprint) || this.mCodename.equals(removeFingerprint) : this.mSdkInt < java.lang.Integer.parseInt(removeFingerprint);
        }
        if (!isCodename(removeFingerprint)) {
            return this.mSdkInt <= java.lang.Integer.parseInt(removeFingerprint);
        }
        if (this.mKnownCodenames.contains(removeFingerprint)) {
            throw new java.lang.IllegalArgumentException("Artifact with a known codename " + removeFingerprint + " must be recompiled with a finalized integer version.");
        }
        return true;
    }

    java.lang.String removeFingerprint(java.lang.String str) {
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
