package android.content.pm;

/* loaded from: classes.dex */
public class LauncherActivityInfo {
    private static final android.icu.text.UnicodeSet TRIMMABLE_CHARACTERS = new android.icu.text.UnicodeSet("[[:White_Space:][:Default_Ignorable_Code_Point:][:gc=Cc:]]", false).freeze();
    private final android.content.pm.LauncherActivityInfoInternal mInternal;
    private final android.content.pm.PackageManager mPm;

    LauncherActivityInfo(android.content.Context context, android.content.pm.LauncherActivityInfoInternal launcherActivityInfoInternal) {
        this.mPm = context.getPackageManager();
        this.mInternal = launcherActivityInfoInternal;
    }

    public android.content.ComponentName getComponentName() {
        return this.mInternal.getComponentName();
    }

    public android.os.UserHandle getUser() {
        return this.mInternal.getUser();
    }

    public java.lang.CharSequence getLabel() {
        if (!android.content.pm.Flags.lightweightInvisibleLabelDetection()) {
            return getActivityInfo().loadLabel(this.mPm);
        }
        java.lang.CharSequence trim = trim(getActivityInfo().loadLabel(this.mPm));
        if (android.text.TextUtils.isEmpty(trim)) {
            java.lang.CharSequence trim2 = trim(getApplicationInfo().loadLabel(this.mPm));
            if (android.text.TextUtils.isEmpty(trim2)) {
                return getComponentName().getPackageName();
            }
            return trim2;
        }
        return trim;
    }

    public float getLoadingProgress() {
        return this.mInternal.getIncrementalStatesInfo().getProgress();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.graphics.drawable.Drawable getIcon(int i) {
        android.graphics.drawable.Drawable drawable;
        int iconResource = getActivityInfo().getIconResource();
        if (i != 0 && iconResource != 0) {
            try {
                drawable = this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo).getDrawableForDensity(iconResource, i);
            } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
            }
            if (drawable != null) {
                return getActivityInfo().loadIcon(this.mPm);
            }
            return drawable;
        }
        drawable = null;
        if (drawable != null) {
        }
    }

    public int getApplicationFlags() {
        return getActivityInfo().flags;
    }

    public android.content.pm.ActivityInfo getActivityInfo() {
        return this.mInternal.getActivityInfo();
    }

    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return getActivityInfo().applicationInfo;
    }

    public long getFirstInstallTime() {
        try {
            return this.mPm.getPackageInfo(getActivityInfo().packageName, 8192).firstInstallTime;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 0L;
        }
    }

    public java.lang.String getName() {
        return getActivityInfo().name;
    }

    public android.graphics.drawable.Drawable getBadgedIcon(int i) {
        return this.mPm.getUserBadgedIcon(getIcon(i), this.mInternal.getUser());
    }

    private static boolean isTrimmable(android.graphics.Paint paint, java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(paint);
        java.util.Objects.requireNonNull(charSequence);
        if (android.text.TextUtils.isEmpty(charSequence) || java.lang.Character.codePointCount(charSequence, 0, charSequence.length()) != 1) {
            return false;
        }
        return TRIMMABLE_CHARACTERS.contains(charSequence) || !paint.hasGlyph(charSequence.toString());
    }

    public static java.lang.CharSequence trimStart(java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(charSequence);
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        android.graphics.Paint paint = new android.graphics.Paint();
        int i = 0;
        for (int i2 : charSequence.codePoints().toArray()) {
            java.lang.String str = new java.lang.String(new int[]{i2}, 0, 1);
            if (!isTrimmable(paint, str)) {
                break;
            }
            i += str.length();
        }
        if (i == 0) {
            return charSequence;
        }
        return charSequence.subSequence(i, charSequence.length());
    }

    public static java.lang.CharSequence trimEnd(java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(charSequence);
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        android.graphics.Paint paint = new android.graphics.Paint();
        int[] array = charSequence.codePoints().toArray();
        int i = 0;
        for (int length = array.length - 1; length >= 0; length--) {
            java.lang.String str = new java.lang.String(new int[]{array[length]}, 0, 1);
            if (!isTrimmable(paint, str)) {
                break;
            }
            i += str.length();
        }
        if (i == 0) {
            return charSequence;
        }
        return charSequence.subSequence(0, charSequence.length() - i);
    }

    public static java.lang.CharSequence trim(java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(charSequence);
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        java.lang.CharSequence trimStart = trimStart(charSequence);
        if (android.text.TextUtils.isEmpty(trimStart)) {
            return trimStart;
        }
        return trimEnd(trimStart);
    }
}
