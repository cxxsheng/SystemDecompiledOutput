package android.os;

/* loaded from: classes3.dex */
public class VintfObject {
    private static final java.lang.String LOG_TAG = "VintfObject";

    public static native java.lang.String[] getHalNamesAndVersions();

    public static native java.lang.String getPlatformSepolicyVersion();

    public static native java.lang.String getSepolicyVersion();

    public static native java.lang.Long getTargetFrameworkCompatibilityMatrixVersion();

    public static native java.util.Map<java.lang.String, java.lang.String[]> getVndkSnapshots();

    public static native java.lang.String[] report();

    public static native int verifyBuildAtBoot();

    static {
        java.lang.System.loadLibrary("vintf_jni");
    }

    private VintfObject() {
    }
}
