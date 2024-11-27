package android.util;

/* loaded from: classes3.dex */
public class IconDrawableFactory {
    protected final android.content.Context mContext;
    protected final android.app.admin.DevicePolicyManager mDpm;
    protected final boolean mEmbedShadow;
    protected final android.util.LauncherIcons mLauncherIcons;
    protected final android.content.pm.PackageManager mPm;
    protected final android.os.UserManager mUm;

    private IconDrawableFactory(android.content.Context context, boolean z) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mUm = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mDpm = (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
        this.mLauncherIcons = new android.util.LauncherIcons(context);
        this.mEmbedShadow = z;
    }

    protected boolean needsBadging(android.content.pm.ApplicationInfo applicationInfo, int i) {
        return applicationInfo.isInstantApp() || this.mUm.hasBadge(i);
    }

    public android.graphics.drawable.Drawable getBadgedIcon(android.content.pm.ApplicationInfo applicationInfo) {
        return getBadgedIcon(applicationInfo, android.os.UserHandle.getUserId(applicationInfo.uid));
    }

    public android.graphics.drawable.Drawable getBadgedIcon(android.content.pm.ApplicationInfo applicationInfo, int i) {
        return getBadgedIcon(applicationInfo, applicationInfo, i);
    }

    public android.graphics.drawable.Drawable getBadgedIcon(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.ApplicationInfo applicationInfo, final int i) {
        android.graphics.drawable.Drawable loadUnbadgedItemIcon = this.mPm.loadUnbadgedItemIcon(packageItemInfo, applicationInfo);
        if (!this.mEmbedShadow && !needsBadging(applicationInfo, i)) {
            return loadUnbadgedItemIcon;
        }
        android.graphics.drawable.Drawable shadowedIcon = getShadowedIcon(loadUnbadgedItemIcon);
        if (applicationInfo.isInstantApp()) {
            int color = android.content.res.Resources.getSystem().getColor(com.android.internal.R.color.instant_app_badge, null);
            shadowedIcon = this.mLauncherIcons.getBadgedDrawable(shadowedIcon, this.mContext.getDrawable(com.android.internal.R.drawable.ic_instant_icon_badge_bolt), color);
        }
        if (this.mUm.hasBadge(i)) {
            return this.mLauncherIcons.getBadgedDrawable(shadowedIcon, this.mDpm.getResources().getDrawable(getUpdatableUserIconBadgeId(i), android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_COLORED, new java.util.function.Supplier() { // from class: android.util.IconDrawableFactory$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.graphics.drawable.Drawable lambda$getBadgedIcon$0;
                    lambda$getBadgedIcon$0 = android.util.IconDrawableFactory.this.lambda$getBadgedIcon$0(i);
                    return lambda$getBadgedIcon$0;
                }
            }), this.mUm.getUserBadgeColor(i));
        }
        return shadowedIcon;
    }

    private java.lang.String getUpdatableUserIconBadgeId(int i) {
        return this.mUm.isManagedProfile(i) ? android.app.admin.DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE : android.app.admin.DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserIconBadge, reason: merged with bridge method [inline-methods] */
    public android.graphics.drawable.Drawable lambda$getBadgedIcon$0(int i) {
        return this.mContext.getResources().getDrawable(this.mUm.getUserIconBadgeResId(i));
    }

    public android.graphics.drawable.Drawable getShadowedIcon(android.graphics.drawable.Drawable drawable) {
        return this.mLauncherIcons.wrapIconDrawableWithShadow(drawable);
    }

    public static android.util.IconDrawableFactory newInstance(android.content.Context context) {
        return new android.util.IconDrawableFactory(context, true);
    }

    public static android.util.IconDrawableFactory newInstance(android.content.Context context, boolean z) {
        return new android.util.IconDrawableFactory(context, z);
    }
}
