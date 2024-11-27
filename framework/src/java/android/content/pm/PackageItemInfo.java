package android.content.pm;

/* loaded from: classes.dex */
public class PackageItemInfo {
    public static final float DEFAULT_MAX_LABEL_SIZE_PX = 1000.0f;
    public static final int DUMP_FLAG_ALL = 3;
    public static final int DUMP_FLAG_APPLICATION = 2;
    public static final int DUMP_FLAG_DETAILS = 1;
    public static final int MAX_SAFE_LABEL_LENGTH = 1000;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int SAFE_LABEL_FLAG_FIRST_LINE = 4;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int SAFE_LABEL_FLAG_SINGLE_LINE = 2;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int SAFE_LABEL_FLAG_TRIM = 1;
    private static volatile boolean sForceSafeLabels = false;
    public int banner;
    public int icon;
    public boolean isArchived;
    public int labelRes;
    public int logo;
    public android.os.Bundle metaData;
    public java.lang.String name;
    public java.lang.CharSequence nonLocalizedLabel;
    public java.lang.String packageName;
    public int showUserIcon;

    @android.annotation.SystemApi
    public static void forceSafeLabels() {
        sForceSafeLabels = true;
    }

    public PackageItemInfo() {
        this.showUserIcon = -10000;
    }

    public PackageItemInfo(android.content.pm.PackageItemInfo packageItemInfo) {
        this.name = packageItemInfo.name;
        if (this.name != null) {
            this.name = this.name.trim();
        }
        this.packageName = packageItemInfo.packageName;
        this.labelRes = packageItemInfo.labelRes;
        this.nonLocalizedLabel = packageItemInfo.nonLocalizedLabel;
        if (this.nonLocalizedLabel != null) {
            this.nonLocalizedLabel = this.nonLocalizedLabel.toString().trim();
        }
        this.icon = packageItemInfo.icon;
        this.banner = packageItemInfo.banner;
        this.logo = packageItemInfo.logo;
        this.metaData = packageItemInfo.metaData;
        this.showUserIcon = packageItemInfo.showUserIcon;
        this.isArchived = packageItemInfo.isArchived;
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        if (sForceSafeLabels && !java.util.Objects.equals(this.packageName, android.app.ActivityThread.currentPackageName())) {
            return loadSafeLabel(packageManager, 1000.0f, 5);
        }
        return android.text.TextUtils.trimToSize(loadUnsafeLabel(packageManager), 1000);
    }

    public java.lang.CharSequence loadUnsafeLabel(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        if (this.labelRes != 0 && (text = packageManager.getText(this.packageName, this.labelRes, getApplicationInfo())) != null) {
            return text.toString().trim();
        }
        if (this.name != null) {
            return this.name;
        }
        return this.packageName;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.CharSequence loadSafeLabel(android.content.pm.PackageManager packageManager) {
        return loadSafeLabel(packageManager, 1000.0f, 5);
    }

    @android.annotation.SystemApi
    public java.lang.CharSequence loadSafeLabel(android.content.pm.PackageManager packageManager, float f, int i) {
        java.util.Objects.requireNonNull(packageManager);
        return android.text.TextUtils.makeSafeForPresentation(loadUnsafeLabel(packageManager).toString(), 1000, f, i);
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return packageManager.loadItemIcon(this, getApplicationInfo());
    }

    public android.graphics.drawable.Drawable loadUnbadgedIcon(android.content.pm.PackageManager packageManager) {
        return packageManager.loadUnbadgedItemIcon(this, getApplicationInfo());
    }

    public android.graphics.drawable.Drawable loadBanner(android.content.pm.PackageManager packageManager) {
        android.graphics.drawable.Drawable drawable;
        if (this.banner != 0 && (drawable = packageManager.getDrawable(this.packageName, this.banner, getApplicationInfo())) != null) {
            return drawable;
        }
        return loadDefaultBanner(packageManager);
    }

    public android.graphics.drawable.Drawable loadDefaultIcon(android.content.pm.PackageManager packageManager) {
        return packageManager.getDefaultActivityIcon();
    }

    protected android.graphics.drawable.Drawable loadDefaultBanner(android.content.pm.PackageManager packageManager) {
        return null;
    }

    public android.graphics.drawable.Drawable loadLogo(android.content.pm.PackageManager packageManager) {
        android.graphics.drawable.Drawable drawable;
        if (this.logo != 0 && (drawable = packageManager.getDrawable(this.packageName, this.logo, getApplicationInfo())) != null) {
            return drawable;
        }
        return loadDefaultLogo(packageManager);
    }

    protected android.graphics.drawable.Drawable loadDefaultLogo(android.content.pm.PackageManager packageManager) {
        return null;
    }

    public android.content.res.XmlResourceParser loadXmlMetaData(android.content.pm.PackageManager packageManager, java.lang.String str) {
        int i;
        if (this.metaData != null && (i = this.metaData.getInt(str)) != 0) {
            return packageManager.getXml(this.packageName, i, getApplicationInfo());
        }
        return null;
    }

    protected void dumpFront(android.util.Printer printer, java.lang.String str) {
        if (this.name != null) {
            printer.println(str + "name=" + this.name);
        }
        printer.println(str + "packageName=" + this.packageName);
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0 || this.banner != 0) {
            printer.println(str + "labelRes=0x" + java.lang.Integer.toHexString(this.labelRes) + " nonLocalizedLabel=" + ((java.lang.Object) this.nonLocalizedLabel) + " icon=0x" + java.lang.Integer.toHexString(this.icon) + " banner=0x" + java.lang.Integer.toHexString(this.banner));
        }
    }

    protected void dumpBack(android.util.Printer printer, java.lang.String str) {
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.name);
        parcel.writeString8(this.packageName);
        parcel.writeInt(this.labelRes);
        android.text.TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, i);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.logo);
        parcel.writeBundle(this.metaData);
        parcel.writeInt(this.banner);
        parcel.writeInt(this.showUserIcon);
        parcel.writeBoolean(this.isArchived);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        if (this.name != null) {
            protoOutputStream.write(1138166333441L, this.name);
        }
        protoOutputStream.write(1138166333442L, this.packageName);
        protoOutputStream.write(1120986464259L, this.labelRes);
        if (this.nonLocalizedLabel != null) {
            protoOutputStream.write(1138166333444L, this.nonLocalizedLabel.toString());
        }
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1120986464262L, this.banner);
        protoOutputStream.write(1133871366151L, this.isArchived);
        protoOutputStream.end(start);
    }

    protected PackageItemInfo(android.os.Parcel parcel) {
        this.name = parcel.readString8();
        this.packageName = parcel.readString8();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.logo = parcel.readInt();
        this.metaData = parcel.readBundle();
        this.banner = parcel.readInt();
        this.showUserIcon = parcel.readInt();
        this.isArchived = parcel.readBoolean();
    }

    protected android.content.pm.ApplicationInfo getApplicationInfo() {
        return null;
    }

    public static class DisplayNameComparator implements java.util.Comparator<android.content.pm.PackageItemInfo> {
        private final android.content.pm.PackageManager mPM;
        private final java.text.Collator sCollator = java.text.Collator.getInstance();

        public DisplayNameComparator(android.content.pm.PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override // java.util.Comparator
        public final int compare(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.PackageItemInfo packageItemInfo2) {
            java.lang.CharSequence loadLabel = packageItemInfo.loadLabel(this.mPM);
            if (loadLabel == null) {
                loadLabel = packageItemInfo.name;
            }
            java.lang.CharSequence loadLabel2 = packageItemInfo2.loadLabel(this.mPM);
            if (loadLabel2 == null) {
                loadLabel2 = packageItemInfo2.name;
            }
            return this.sCollator.compare(loadLabel.toString(), loadLabel2.toString());
        }
    }
}
