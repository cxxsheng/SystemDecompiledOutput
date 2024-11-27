package android.os;

/* loaded from: classes3.dex */
public class VintfRuntimeInfo {
    public static native java.lang.String getBootAvbVersion();

    public static native java.lang.String getBootVbmetaAvbVersion();

    public static native java.lang.String getCpuInfo();

    public static native java.lang.String getHardwareId();

    public static native long getKernelSepolicyVersion();

    public static native java.lang.String getKernelVersion();

    public static native java.lang.String getNodeName();

    public static native java.lang.String getOsName();

    public static native java.lang.String getOsRelease();

    public static native java.lang.String getOsVersion();

    private VintfRuntimeInfo() {
    }

    static {
        java.lang.System.loadLibrary("vintf_jni");
    }
}
