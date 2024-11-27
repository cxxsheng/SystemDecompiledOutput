package android.appwidget;

/* loaded from: classes.dex */
public class AppWidgetProviderInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.appwidget.AppWidgetProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.appwidget.AppWidgetProviderInfo>() { // from class: android.appwidget.AppWidgetProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.appwidget.AppWidgetProviderInfo createFromParcel(android.os.Parcel parcel) {
            return new android.appwidget.AppWidgetProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.appwidget.AppWidgetProviderInfo[] newArray(int i) {
            return new android.appwidget.AppWidgetProviderInfo[i];
        }
    };
    public static final int RESIZE_BOTH = 3;
    public static final int RESIZE_HORIZONTAL = 1;
    public static final int RESIZE_NONE = 0;
    public static final int RESIZE_VERTICAL = 2;
    public static final int WIDGET_CATEGORY_HOME_SCREEN = 1;
    public static final int WIDGET_CATEGORY_KEYGUARD = 2;
    public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
    public static final int WIDGET_CATEGORY_UNKNOWN = -1;
    public static final int WIDGET_FEATURE_CONFIGURATION_OPTIONAL = 4;
    public static final int WIDGET_FEATURE_HIDE_FROM_PICKER = 2;
    public static final int WIDGET_FEATURE_RECONFIGURABLE = 1;
    public int autoAdvanceViewId;
    public android.content.ComponentName configure;
    public int descriptionRes;
    public int generatedPreviewCategories;
    public int icon;
    public int initialKeyguardLayout;
    public int initialLayout;
    public boolean isExtendedFromAppWidgetProvider;

    @java.lang.Deprecated
    public java.lang.String label;
    public int maxResizeHeight;
    public int maxResizeWidth;
    public int minHeight;
    public int minResizeHeight;
    public int minResizeWidth;
    public int minWidth;
    public int previewImage;
    public int previewLayout;
    public android.content.ComponentName provider;
    public android.content.pm.ActivityInfo providerInfo;
    public int resizeMode;
    public int targetCellHeight;
    public int targetCellWidth;
    public int updatePeriodMillis;
    public int widgetCategory;
    public int widgetFeatures;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CategoryFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FeatureFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResizeModeFlags {
    }

    public AppWidgetProviderInfo() {
    }

    public AppWidgetProviderInfo(android.os.Parcel parcel) {
        this.provider = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        this.minWidth = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.minResizeWidth = parcel.readInt();
        this.minResizeHeight = parcel.readInt();
        this.maxResizeWidth = parcel.readInt();
        this.maxResizeHeight = parcel.readInt();
        this.targetCellWidth = parcel.readInt();
        this.targetCellHeight = parcel.readInt();
        this.updatePeriodMillis = parcel.readInt();
        this.initialLayout = parcel.readInt();
        this.initialKeyguardLayout = parcel.readInt();
        this.configure = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        this.label = parcel.readString();
        this.icon = parcel.readInt();
        this.previewImage = parcel.readInt();
        this.previewLayout = parcel.readInt();
        this.autoAdvanceViewId = parcel.readInt();
        this.resizeMode = parcel.readInt();
        this.widgetCategory = parcel.readInt();
        this.providerInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
        this.widgetFeatures = parcel.readInt();
        this.descriptionRes = parcel.readInt();
        this.isExtendedFromAppWidgetProvider = parcel.readBoolean();
        if (android.appwidget.flags.Flags.generatedPreviews()) {
            this.generatedPreviewCategories = parcel.readInt();
        }
    }

    public final java.lang.String loadLabel(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence loadLabel = this.providerInfo.loadLabel(packageManager);
        if (loadLabel != null) {
            return loadLabel.toString().trim();
        }
        return null;
    }

    public final android.graphics.drawable.Drawable loadIcon(android.content.Context context, int i) {
        return loadDrawable(context, i, this.providerInfo.getIconResource(), true);
    }

    public final android.graphics.drawable.Drawable loadPreviewImage(android.content.Context context, int i) {
        return loadDrawable(context, i, this.previewImage, false);
    }

    public final java.lang.CharSequence loadDescription(android.content.Context context) {
        java.lang.CharSequence text;
        if (android.content.res.ResourceId.isValid(this.descriptionRes) && (text = context.getPackageManager().getText(this.providerInfo.packageName, this.descriptionRes, this.providerInfo.applicationInfo)) != null) {
            return text.toString().trim();
        }
        return null;
    }

    public final android.os.UserHandle getProfile() {
        return new android.os.UserHandle(android.os.UserHandle.getUserId(this.providerInfo.applicationInfo.uid));
    }

    public android.content.pm.ActivityInfo getActivityInfo() {
        return this.providerInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.provider, i);
        parcel.writeInt(this.minWidth);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.minResizeWidth);
        parcel.writeInt(this.minResizeHeight);
        parcel.writeInt(this.maxResizeWidth);
        parcel.writeInt(this.maxResizeHeight);
        parcel.writeInt(this.targetCellWidth);
        parcel.writeInt(this.targetCellHeight);
        parcel.writeInt(this.updatePeriodMillis);
        parcel.writeInt(this.initialLayout);
        parcel.writeInt(this.initialKeyguardLayout);
        parcel.writeTypedObject(this.configure, i);
        parcel.writeString(this.label);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.previewImage);
        parcel.writeInt(this.previewLayout);
        parcel.writeInt(this.autoAdvanceViewId);
        parcel.writeInt(this.resizeMode);
        parcel.writeInt(this.widgetCategory);
        parcel.writeTypedObject(this.providerInfo, i);
        parcel.writeInt(this.widgetFeatures);
        parcel.writeInt(this.descriptionRes);
        parcel.writeBoolean(this.isExtendedFromAppWidgetProvider);
        if (android.appwidget.flags.Flags.generatedPreviews()) {
            parcel.writeInt(this.generatedPreviewCategories);
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.appwidget.AppWidgetProviderInfo m723clone() {
        android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
        appWidgetProviderInfo.provider = this.provider == null ? null : this.provider.m778clone();
        appWidgetProviderInfo.minWidth = this.minWidth;
        appWidgetProviderInfo.minHeight = this.minHeight;
        appWidgetProviderInfo.minResizeWidth = this.minResizeWidth;
        appWidgetProviderInfo.minResizeHeight = this.minResizeHeight;
        appWidgetProviderInfo.maxResizeWidth = this.maxResizeWidth;
        appWidgetProviderInfo.maxResizeHeight = this.maxResizeHeight;
        appWidgetProviderInfo.targetCellWidth = this.targetCellWidth;
        appWidgetProviderInfo.targetCellHeight = this.targetCellHeight;
        appWidgetProviderInfo.updatePeriodMillis = this.updatePeriodMillis;
        appWidgetProviderInfo.initialLayout = this.initialLayout;
        appWidgetProviderInfo.initialKeyguardLayout = this.initialKeyguardLayout;
        appWidgetProviderInfo.configure = this.configure != null ? this.configure.m778clone() : null;
        appWidgetProviderInfo.label = this.label;
        appWidgetProviderInfo.icon = this.icon;
        appWidgetProviderInfo.previewImage = this.previewImage;
        appWidgetProviderInfo.previewLayout = this.previewLayout;
        appWidgetProviderInfo.autoAdvanceViewId = this.autoAdvanceViewId;
        appWidgetProviderInfo.resizeMode = this.resizeMode;
        appWidgetProviderInfo.widgetCategory = this.widgetCategory;
        appWidgetProviderInfo.providerInfo = this.providerInfo;
        appWidgetProviderInfo.widgetFeatures = this.widgetFeatures;
        appWidgetProviderInfo.descriptionRes = this.descriptionRes;
        appWidgetProviderInfo.isExtendedFromAppWidgetProvider = this.isExtendedFromAppWidgetProvider;
        if (android.appwidget.flags.Flags.generatedPreviews()) {
            appWidgetProviderInfo.generatedPreviewCategories = this.generatedPreviewCategories;
        }
        return appWidgetProviderInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private android.graphics.drawable.Drawable loadDrawable(android.content.Context context, int i, int i2, boolean z) {
        try {
            android.content.res.Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(this.providerInfo.applicationInfo);
            if (android.content.res.ResourceId.isValid(i2)) {
                if (i < 0) {
                    i = 0;
                }
                return resourcesForApplication.getDrawableForDensity(i2, i, null);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
        }
        if (z) {
            return this.providerInfo.loadIcon(context.getPackageManager());
        }
        return null;
    }

    public void updateDimensions(android.util.DisplayMetrics displayMetrics) {
        this.minWidth = android.util.TypedValue.complexToDimensionPixelSize(this.minWidth, displayMetrics);
        this.minHeight = android.util.TypedValue.complexToDimensionPixelSize(this.minHeight, displayMetrics);
        this.minResizeWidth = android.util.TypedValue.complexToDimensionPixelSize(this.minResizeWidth, displayMetrics);
        this.minResizeHeight = android.util.TypedValue.complexToDimensionPixelSize(this.minResizeHeight, displayMetrics);
        this.maxResizeWidth = android.util.TypedValue.complexToDimensionPixelSize(this.maxResizeWidth, displayMetrics);
        this.maxResizeHeight = android.util.TypedValue.complexToDimensionPixelSize(this.maxResizeHeight, displayMetrics);
    }

    public java.lang.String toString() {
        return "AppWidgetProviderInfo(" + getProfile() + '/' + this.provider + ')';
    }
}
