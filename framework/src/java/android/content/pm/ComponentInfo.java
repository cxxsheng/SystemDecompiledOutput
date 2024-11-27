package android.content.pm;

/* loaded from: classes.dex */
public class ComponentInfo extends android.content.pm.PackageItemInfo {
    public android.content.pm.ApplicationInfo applicationInfo;
    public java.lang.String[] attributionTags;
    public int descriptionRes;
    public boolean directBootAware;
    public boolean enabled;
    public boolean exported;
    public java.lang.String processName;
    public java.lang.String splitName;

    public ComponentInfo() {
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
    }

    public ComponentInfo(android.content.pm.ComponentInfo componentInfo) {
        super(componentInfo);
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
        this.applicationInfo = componentInfo.applicationInfo;
        this.processName = componentInfo.processName;
        this.splitName = componentInfo.splitName;
        this.attributionTags = componentInfo.attributionTags;
        this.descriptionRes = componentInfo.descriptionRes;
        this.enabled = componentInfo.enabled;
        this.exported = componentInfo.exported;
        this.directBootAware = componentInfo.directBootAware;
    }

    @Override // android.content.pm.PackageItemInfo
    public java.lang.CharSequence loadUnsafeLabel(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        java.lang.CharSequence text2;
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        android.content.pm.ApplicationInfo applicationInfo = this.applicationInfo;
        if (this.labelRes != 0 && (text2 = packageManager.getText(this.packageName, this.labelRes, applicationInfo)) != null) {
            return text2;
        }
        if (applicationInfo.nonLocalizedLabel != null) {
            return applicationInfo.nonLocalizedLabel;
        }
        if (applicationInfo.labelRes != 0 && (text = packageManager.getText(this.packageName, applicationInfo.labelRes, applicationInfo)) != null) {
            return text;
        }
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled && this.applicationInfo.enabled;
    }

    public final int getIconResource() {
        return this.icon != 0 ? this.icon : this.applicationInfo.icon;
    }

    public final int getLogoResource() {
        return this.logo != 0 ? this.logo : this.applicationInfo.logo;
    }

    public final int getBannerResource() {
        return this.banner != 0 ? this.banner : this.applicationInfo.banner;
    }

    public android.content.ComponentName getComponentName() {
        return new android.content.ComponentName(this.packageName, this.name);
    }

    @Override // android.content.pm.PackageItemInfo
    protected void dumpFront(android.util.Printer printer, java.lang.String str) {
        super.dumpFront(printer, str);
        if (this.processName != null && !this.packageName.equals(this.processName)) {
            printer.println(str + "processName=" + this.processName);
        }
        if (this.splitName != null) {
            printer.println(str + "splitName=" + this.splitName);
        }
        if (this.attributionTags != null && this.attributionTags.length > 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.attributionTags[0]);
            for (int i = 1; i < this.attributionTags.length; i++) {
                sb.append(", ");
                sb.append(this.attributionTags[i]);
            }
            printer.println(str + "attributionTags=[" + ((java.lang.Object) sb) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        printer.println(str + "enabled=" + this.enabled + " exported=" + this.exported + " directBootAware=" + this.directBootAware);
        if (this.descriptionRes != 0) {
            printer.println(str + "description=" + this.descriptionRes);
        }
    }

    @Override // android.content.pm.PackageItemInfo
    protected void dumpBack(android.util.Printer printer, java.lang.String str) {
        dumpBack(printer, str, 3);
    }

    void dumpBack(android.util.Printer printer, java.lang.String str, int i) {
        if ((i & 2) != 0) {
            if (this.applicationInfo != null) {
                printer.println(str + "ApplicationInfo:");
                this.applicationInfo.dump(printer, str + "  ", i);
            } else {
                printer.println(str + "ApplicationInfo: null");
            }
        }
        super.dumpBack(printer, str);
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.applicationInfo.writeToParcel(parcel, i);
        parcel.writeString8(this.processName);
        parcel.writeString8(this.splitName);
        parcel.writeString8Array(this.attributionTags);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.enabled ? 1 : 0);
        parcel.writeInt(this.exported ? 1 : 0);
        parcel.writeInt(this.directBootAware ? 1 : 0);
    }

    protected ComponentInfo(android.os.Parcel parcel) {
        super(parcel);
        boolean z;
        boolean z2;
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
        this.applicationInfo = android.content.pm.ApplicationInfo.CREATOR.createFromParcel(parcel);
        this.processName = parcel.readString8();
        this.splitName = parcel.readString8();
        this.attributionTags = parcel.createString8Array();
        this.descriptionRes = parcel.readInt();
        if (parcel.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.enabled = z;
        if (parcel.readInt() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.exported = z2;
        this.directBootAware = parcel.readInt() != 0;
    }

    @Override // android.content.pm.PackageItemInfo
    public android.graphics.drawable.Drawable loadDefaultIcon(android.content.pm.PackageManager packageManager) {
        return this.applicationInfo.loadIcon(packageManager);
    }

    @Override // android.content.pm.PackageItemInfo
    protected android.graphics.drawable.Drawable loadDefaultBanner(android.content.pm.PackageManager packageManager) {
        return this.applicationInfo.loadBanner(packageManager);
    }

    @Override // android.content.pm.PackageItemInfo
    protected android.graphics.drawable.Drawable loadDefaultLogo(android.content.pm.PackageManager packageManager) {
        return this.applicationInfo.loadLogo(packageManager);
    }

    @Override // android.content.pm.PackageItemInfo
    protected android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.applicationInfo;
    }
}
