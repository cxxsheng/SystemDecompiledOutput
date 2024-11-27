package android.webkit;

/* loaded from: classes4.dex */
public class UserPackage {
    public static final int MINIMUM_SUPPORTED_SDK = 33;
    private final android.content.pm.PackageInfo mPackageInfo;
    private final android.content.pm.UserInfo mUserInfo;

    public UserPackage(android.content.pm.UserInfo userInfo, android.content.pm.PackageInfo packageInfo) {
        this.mUserInfo = userInfo;
        this.mPackageInfo = packageInfo;
    }

    public static java.util.List<android.webkit.UserPackage> getPackageInfosAllUsers(android.content.Context context, java.lang.String str, int i) {
        android.content.pm.PackageInfo packageInfo;
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(context);
        java.util.ArrayList arrayList = new java.util.ArrayList(allUsers.size());
        for (android.content.pm.UserInfo userInfo : allUsers) {
            try {
                packageInfo = context.getPackageManager().getPackageInfoAsUser(str, i, userInfo.id);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                packageInfo = null;
            }
            arrayList.add(new android.webkit.UserPackage(userInfo, packageInfo));
        }
        return arrayList;
    }

    public boolean isEnabledPackage() {
        if (this.mPackageInfo == null) {
            return false;
        }
        return this.mPackageInfo.applicationInfo.enabled;
    }

    public boolean isInstalledPackage() {
        return (this.mPackageInfo == null || (this.mPackageInfo.applicationInfo.flags & 8388608) == 0 || (this.mPackageInfo.applicationInfo.privateFlags & 1) != 0) ? false : true;
    }

    public static boolean hasCorrectTargetSdkVersion(android.content.pm.PackageInfo packageInfo) {
        return packageInfo.applicationInfo.targetSdkVersion >= 33;
    }

    public android.content.pm.UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    public android.content.pm.PackageInfo getPackageInfo() {
        return this.mPackageInfo;
    }

    private static java.util.List<android.content.pm.UserInfo> getAllUsers(android.content.Context context) {
        return ((android.os.UserManager) context.getSystemService("user")).getUsers();
    }
}
