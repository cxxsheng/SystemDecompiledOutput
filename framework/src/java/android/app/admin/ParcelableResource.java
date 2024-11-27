package android.app.admin;

/* loaded from: classes.dex */
public final class ParcelableResource implements android.os.Parcelable {
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String ATTR_RESOURCE_ID = "resource-id";
    private static final java.lang.String ATTR_RESOURCE_NAME = "resource-name";
    private static final java.lang.String ATTR_RESOURCE_TYPE = "resource-type";
    public static final int RESOURCE_TYPE_DRAWABLE = 1;
    public static final int RESOURCE_TYPE_STRING = 2;
    private final java.lang.String mPackageName;
    private final int mResourceId;
    private final java.lang.String mResourceName;
    private final int mResourceType;
    private static java.lang.String TAG = "DevicePolicyManager";
    public static final android.os.Parcelable.Creator<android.app.admin.ParcelableResource> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ParcelableResource>() { // from class: android.app.admin.ParcelableResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ParcelableResource createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.ParcelableResource(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ParcelableResource[] newArray(int i) {
            return new android.app.admin.ParcelableResource[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResourceType {
    }

    public ParcelableResource(android.content.Context context, int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        java.util.Objects.requireNonNull(context, "context must be provided");
        verifyResourceExistsInCallingPackage(context, i, i2);
        this.mResourceId = i;
        this.mPackageName = context.getResources().getResourcePackageName(i);
        this.mResourceName = context.getResources().getResourceName(i);
        this.mResourceType = i2;
    }

    private ParcelableResource(int i, java.lang.String str, java.lang.String str2, int i2) {
        this.mResourceId = i;
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mResourceName = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mResourceType = i2;
    }

    private static void verifyResourceExistsInCallingPackage(android.content.Context context, int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        switch (i2) {
            case 1:
                if (!hasDrawableInCallingPackage(context, i)) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("Drawable with id %d doesn't exist in the calling package %s", java.lang.Integer.valueOf(i), context.getPackageName()));
                }
                return;
            case 2:
                if (!hasStringInCallingPackage(context, i)) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("String with id %d doesn't exist in the calling package %s", java.lang.Integer.valueOf(i), context.getPackageName()));
                }
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown ResourceType: " + i2);
        }
    }

    private static boolean hasDrawableInCallingPackage(android.content.Context context, int i) {
        try {
            return "drawable".equals(context.getResources().getResourceTypeName(i));
        } catch (android.content.res.Resources.NotFoundException e) {
            return false;
        }
    }

    private static boolean hasStringInCallingPackage(android.content.Context context, int i) {
        try {
            return "string".equals(context.getResources().getResourceTypeName(i));
        } catch (android.content.res.Resources.NotFoundException e) {
            return false;
        }
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getResourceName() {
        return this.mResourceName;
    }

    public int getResourceType() {
        return this.mResourceType;
    }

    public android.graphics.drawable.Drawable getDrawable(android.content.Context context, int i, java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        try {
            android.content.res.Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return appResourcesWithCallersConfiguration.getDrawableForDensity(this.mResourceId, i, context.getTheme());
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Unable to load drawable resource " + this.mResourceName, e);
            return loadDefaultDrawable(supplier);
        }
    }

    public java.lang.String getString(android.content.Context context, java.util.function.Supplier<java.lang.String> supplier) {
        try {
            android.content.res.Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return appResourcesWithCallersConfiguration.getString(this.mResourceId);
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Unable to load string resource " + this.mResourceName, e);
            return loadDefaultString(supplier);
        }
    }

    public java.lang.String getString(android.content.Context context, java.util.function.Supplier<java.lang.String> supplier, java.lang.Object... objArr) {
        try {
            android.content.res.Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return java.lang.String.format(context.getResources().getConfiguration().getLocales().get(0), appResourcesWithCallersConfiguration.getString(this.mResourceId), objArr);
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Unable to load string resource " + this.mResourceName, e);
            return loadDefaultString(supplier);
        }
    }

    private android.content.res.Resources getAppResourcesWithCallersConfiguration(android.content.Context context) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        return packageManager.getResourcesForApplication(packageManager.getApplicationInfo(this.mPackageName, 9216), context.getResources().getConfiguration());
    }

    private void verifyResourceName(android.content.res.Resources resources) throws java.lang.IllegalStateException {
        java.lang.String resourceName = resources.getResourceName(this.mResourceId);
        if (!this.mResourceName.equals(resourceName)) {
            throw new java.lang.IllegalStateException(java.lang.String.format("Current resource name %s for resource id %d has changed from the previously stored resource name %s.", resourceName, java.lang.Integer.valueOf(this.mResourceId), this.mResourceName));
        }
    }

    public static android.graphics.drawable.Drawable loadDefaultDrawable(java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        java.util.Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        return supplier.get();
    }

    public static java.lang.String loadDefaultString(java.util.function.Supplier<java.lang.String> supplier) {
        java.util.Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        return supplier.get();
    }

    public void writeToXmlFile(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeInt(null, ATTR_RESOURCE_ID, this.mResourceId);
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
        typedXmlSerializer.attribute(null, ATTR_RESOURCE_NAME, this.mResourceName);
        typedXmlSerializer.attributeInt(null, ATTR_RESOURCE_TYPE, this.mResourceType);
    }

    public static android.app.admin.ParcelableResource createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.ParcelableResource(typedXmlPullParser.getAttributeInt(null, ATTR_RESOURCE_ID), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME), typedXmlPullParser.getAttributeValue(null, ATTR_RESOURCE_NAME), typedXmlPullParser.getAttributeInt(null, ATTR_RESOURCE_TYPE));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.ParcelableResource parcelableResource = (android.app.admin.ParcelableResource) obj;
        if (this.mResourceId == parcelableResource.mResourceId && this.mPackageName.equals(parcelableResource.mPackageName) && this.mResourceName.equals(parcelableResource.mResourceName) && this.mResourceType == parcelableResource.mResourceType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mResourceId), this.mPackageName, this.mResourceName, java.lang.Integer.valueOf(this.mResourceType));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResourceId);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mResourceName);
        parcel.writeInt(this.mResourceType);
    }
}
