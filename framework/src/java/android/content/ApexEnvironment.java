package android.content;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class ApexEnvironment {
    private static final java.lang.String APEX_DATA = "apexdata";
    private final java.lang.String mApexModuleName;

    public static android.content.ApexEnvironment getApexEnvironment(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "apexModuleName cannot be null");
        return new android.content.ApexEnvironment(str);
    }

    private ApexEnvironment(java.lang.String str) {
        this.mApexModuleName = str;
    }

    public java.io.File getDeviceProtectedDataDir() {
        return android.os.Environment.buildPath(android.os.Environment.getDataMiscDirectory(), APEX_DATA, this.mApexModuleName);
    }

    public java.io.File getDeviceProtectedDataDirForUser(android.os.UserHandle userHandle) {
        return android.os.Environment.buildPath(android.os.Environment.getDataMiscDeDirectory(userHandle.getIdentifier()), APEX_DATA, this.mApexModuleName);
    }

    public java.io.File getCredentialProtectedDataDirForUser(android.os.UserHandle userHandle) {
        return android.os.Environment.buildPath(android.os.Environment.getDataMiscCeDirectory(userHandle.getIdentifier()), APEX_DATA, this.mApexModuleName);
    }
}
