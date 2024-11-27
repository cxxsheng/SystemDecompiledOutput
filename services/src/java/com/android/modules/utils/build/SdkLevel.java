package com.android.modules.utils.build;

/* loaded from: classes.dex */
public final class SdkLevel {
    private SdkLevel() {
    }

    @androidx.annotation.ChecksSdkIntAtLeast(api = 30)
    public static boolean isAtLeastR() {
        return true;
    }

    @androidx.annotation.ChecksSdkIntAtLeast(api = 31)
    public static boolean isAtLeastS() {
        return true;
    }

    @androidx.annotation.ChecksSdkIntAtLeast(api = 32)
    public static boolean isAtLeastSv2() {
        return true;
    }

    @androidx.annotation.ChecksSdkIntAtLeast(api = 33)
    public static boolean isAtLeastT() {
        return true;
    }

    @androidx.annotation.ChecksSdkIntAtLeast(api = 34, codename = "UpsideDownCake")
    public static boolean isAtLeastU() {
        return true;
    }

    @androidx.annotation.ChecksSdkIntAtLeast(codename = "VanillaIceCream")
    public static boolean isAtLeastV() {
        return isAtLeastPreReleaseCodename("VanillaIceCream");
    }

    private static boolean isAtLeastPreReleaseCodename(@androidx.annotation.NonNull java.lang.String str) {
        return !"REL".equals(android.os.Build.VERSION.CODENAME) && android.os.Build.VERSION.CODENAME.compareTo(str) >= 0;
    }
}
