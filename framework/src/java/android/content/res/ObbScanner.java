package android.content.res;

/* loaded from: classes.dex */
public class ObbScanner {
    private static native void getObbInfo_native(java.lang.String str, android.content.res.ObbInfo obbInfo) throws java.io.IOException;

    private ObbScanner() {
    }

    public static android.content.res.ObbInfo getObbInfo(java.lang.String str) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("file path cannot be null");
        }
        java.io.File file = new java.io.File(str);
        if (!file.exists()) {
            throw new java.lang.IllegalArgumentException("OBB file does not exist: " + str);
        }
        java.lang.String canonicalPath = file.getCanonicalPath();
        android.content.res.ObbInfo obbInfo = new android.content.res.ObbInfo();
        obbInfo.filename = canonicalPath;
        getObbInfo_native(canonicalPath, obbInfo);
        return obbInfo;
    }
}
