package android.content.pm;

/* loaded from: classes.dex */
public abstract class CrossProfileAppsInternal {
    public abstract java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str, int i);

    public abstract void setInteractAcrossProfilesAppOp(java.lang.String str, int i, int i2);

    public abstract boolean verifyPackageHasInteractAcrossProfilePermission(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract boolean verifyUidHasInteractAcrossProfilePermission(java.lang.String str, int i);
}
