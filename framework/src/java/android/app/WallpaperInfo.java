package android.app;

/* loaded from: classes.dex */
public final class WallpaperInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.WallpaperInfo> CREATOR = new android.os.Parcelable.Creator<android.app.WallpaperInfo>() { // from class: android.app.WallpaperInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WallpaperInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.WallpaperInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WallpaperInfo[] newArray(int i) {
            return new android.app.WallpaperInfo[i];
        }
    };
    static final java.lang.String TAG = "WallpaperInfo";
    final int mAuthorResource;
    final int mContextDescriptionResource;
    final int mContextUriResource;
    final int mDescriptionResource;
    final android.content.pm.ResolveInfo mService;
    final java.lang.String mSettingsActivityName;
    final java.lang.String mSettingsSliceUri;
    final boolean mShouldUseDefaultUnfoldTransition;
    final boolean mShowMetadataInPreview;
    final boolean mSupportMultipleDisplays;
    final boolean mSupportsAmbientMode;
    final int mThumbnailResource;

    public WallpaperInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        this.mService = resolveInfo;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.service.wallpaper.WallpaperService.SERVICE_META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("No android.service.wallpaper meta-data");
                }
                android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!android.content.Context.WALLPAPER_SERVICE.equals(loadXmlMetaData.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with wallpaper tag");
                }
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.Wallpaper);
                this.mSettingsActivityName = obtainAttributes.getString(1);
                this.mThumbnailResource = obtainAttributes.getResourceId(2, -1);
                this.mAuthorResource = obtainAttributes.getResourceId(3, -1);
                this.mDescriptionResource = obtainAttributes.getResourceId(0, -1);
                this.mContextUriResource = obtainAttributes.getResourceId(4, -1);
                this.mContextDescriptionResource = obtainAttributes.getResourceId(5, -1);
                this.mShowMetadataInPreview = obtainAttributes.getBoolean(6, false);
                this.mSupportsAmbientMode = obtainAttributes.getBoolean(7, packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH));
                this.mShouldUseDefaultUnfoldTransition = obtainAttributes.getBoolean(10, true);
                this.mSettingsSliceUri = obtainAttributes.getString(8);
                this.mSupportMultipleDisplays = obtainAttributes.getBoolean(9, false);
                obtainAttributes.recycle();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } finally {
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
        }
    }

    WallpaperInfo(android.os.Parcel parcel) {
        this.mSettingsActivityName = parcel.readString();
        this.mThumbnailResource = parcel.readInt();
        this.mAuthorResource = parcel.readInt();
        this.mDescriptionResource = parcel.readInt();
        this.mContextUriResource = parcel.readInt();
        this.mContextDescriptionResource = parcel.readInt();
        this.mShowMetadataInPreview = parcel.readInt() != 0;
        this.mSupportsAmbientMode = parcel.readInt() != 0;
        this.mSettingsSliceUri = parcel.readString();
        this.mSupportMultipleDisplays = parcel.readInt() != 0;
        this.mShouldUseDefaultUnfoldTransition = parcel.readInt() != 0;
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
    }

    public java.lang.String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public java.lang.String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public android.graphics.drawable.Drawable loadThumbnail(android.content.pm.PackageManager packageManager) {
        if (this.mThumbnailResource < 0) {
            return null;
        }
        return packageManager.getDrawable(this.mService.serviceInfo.packageName, this.mThumbnailResource, this.mService.serviceInfo.applicationInfo);
    }

    public java.lang.CharSequence loadAuthor(android.content.pm.PackageManager packageManager) throws android.content.res.Resources.NotFoundException {
        android.content.pm.ApplicationInfo applicationInfo;
        if (this.mAuthorResource <= 0) {
            throw new android.content.res.Resources.NotFoundException();
        }
        java.lang.String str = this.mService.resolvePackageName;
        if (str != null) {
            applicationInfo = null;
        } else {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        }
        return packageManager.getText(str, this.mAuthorResource, applicationInfo);
    }

    public java.lang.CharSequence loadDescription(android.content.pm.PackageManager packageManager) throws android.content.res.Resources.NotFoundException {
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.String str = this.mService.resolvePackageName;
        if (str != null) {
            applicationInfo = null;
        } else {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        }
        if (this.mService.serviceInfo.descriptionRes != 0) {
            return packageManager.getText(str, this.mService.serviceInfo.descriptionRes, applicationInfo);
        }
        if (this.mDescriptionResource <= 0) {
            throw new android.content.res.Resources.NotFoundException();
        }
        return packageManager.getText(str, this.mDescriptionResource, this.mService.serviceInfo.applicationInfo);
    }

    public android.net.Uri loadContextUri(android.content.pm.PackageManager packageManager) throws android.content.res.Resources.NotFoundException {
        android.content.pm.ApplicationInfo applicationInfo;
        if (this.mContextUriResource <= 0) {
            throw new android.content.res.Resources.NotFoundException();
        }
        java.lang.String str = this.mService.resolvePackageName;
        if (str != null) {
            applicationInfo = null;
        } else {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        }
        java.lang.CharSequence text = packageManager.getText(str, this.mContextUriResource, applicationInfo);
        if (text == null) {
            return null;
        }
        return android.net.Uri.parse(text.toString());
    }

    public java.lang.CharSequence loadContextDescription(android.content.pm.PackageManager packageManager) throws android.content.res.Resources.NotFoundException {
        android.content.pm.ApplicationInfo applicationInfo;
        if (this.mContextDescriptionResource <= 0) {
            throw new android.content.res.Resources.NotFoundException();
        }
        java.lang.String str = this.mService.resolvePackageName;
        if (str != null) {
            applicationInfo = null;
        } else {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        }
        return packageManager.getText(str, this.mContextDescriptionResource, applicationInfo).toString();
    }

    public boolean getShowMetadataInPreview() {
        return this.mShowMetadataInPreview;
    }

    @android.annotation.SystemApi
    public boolean supportsAmbientMode() {
        return this.mSupportsAmbientMode;
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public android.net.Uri getSettingsSliceUri() {
        if (this.mSettingsSliceUri == null) {
            return null;
        }
        return android.net.Uri.parse(this.mSettingsSliceUri);
    }

    public boolean supportsMultipleDisplays() {
        return this.mSupportMultipleDisplays;
    }

    public boolean shouldUseDefaultUnfoldTransition() {
        return this.mShouldUseDefaultUnfoldTransition;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + "Service:");
        this.mService.dump(printer, str + "  ");
        printer.println(str + "mSettingsActivityName=" + this.mSettingsActivityName);
    }

    public java.lang.String toString() {
        return "WallpaperInfo{" + this.mService.serviceInfo.name + ", settings: " + this.mSettingsActivityName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mThumbnailResource);
        parcel.writeInt(this.mAuthorResource);
        parcel.writeInt(this.mDescriptionResource);
        parcel.writeInt(this.mContextUriResource);
        parcel.writeInt(this.mContextDescriptionResource);
        parcel.writeInt(this.mShowMetadataInPreview ? 1 : 0);
        parcel.writeInt(this.mSupportsAmbientMode ? 1 : 0);
        parcel.writeString(this.mSettingsSliceUri);
        parcel.writeInt(this.mSupportMultipleDisplays ? 1 : 0);
        parcel.writeInt(this.mShouldUseDefaultUnfoldTransition ? 1 : 0);
        this.mService.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
