package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public final class SELinuxUtil {
    public static final java.lang.String COMPLETE_STR = ":complete";
    private static final java.lang.String INSTANT_APP_STR = ":ephemeralapp";

    public static java.lang.String getSeinfoUser(com.android.server.pm.pkg.PackageUserState packageUserState) {
        if (packageUserState.isInstantApp()) {
            return ":ephemeralapp:complete";
        }
        return COMPLETE_STR;
    }
}
