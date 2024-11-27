package android.internal.modules.utils.build;

/* loaded from: classes2.dex */
public final class SdkLevel {
    private SdkLevel() {
    }

    public static boolean isAtLeastR() {
        return true;
    }

    public static boolean isAtLeastS() {
        return true;
    }

    public static boolean isAtLeastSv2() {
        return true;
    }

    public static boolean isAtLeastT() {
        return true;
    }

    public static boolean isAtLeastU() {
        return true;
    }

    public static boolean isAtLeastV() {
        return isAtLeastPreReleaseCodename("VanillaIceCream");
    }

    private static boolean isAtLeastPreReleaseCodename(java.lang.String str) {
        return !"REL".equals(android.os.Build.VERSION.CODENAME) && android.os.Build.VERSION.CODENAME.compareTo(str) >= 0;
    }
}
