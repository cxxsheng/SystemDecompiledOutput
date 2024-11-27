package com.android.server.appwidget;

/* loaded from: classes.dex */
public class AppWidgetXmlUtil {
    private static final java.lang.String ATTR_AUTO_ADVANCED_VIEW_ID = "auto_advance_view_id";
    private static final java.lang.String ATTR_CONFIGURE = "configure";
    private static final java.lang.String ATTR_DESCRIPTION_RES = "description_res";
    private static final java.lang.String ATTR_ICON = "icon";
    private static final java.lang.String ATTR_INITIAL_KEYGUARD_LAYOUT = "initial_keyguard_layout";
    private static final java.lang.String ATTR_INITIAL_LAYOUT = "initial_layout";
    private static final java.lang.String ATTR_LABEL = "label";
    private static final java.lang.String ATTR_MAX_RESIZE_HEIGHT = "max_resize_height";
    private static final java.lang.String ATTR_MAX_RESIZE_WIDTH = "max_resize_width";
    private static final java.lang.String ATTR_MIN_HEIGHT = "min_height";
    private static final java.lang.String ATTR_MIN_RESIZE_HEIGHT = "min_resize_height";
    private static final java.lang.String ATTR_MIN_RESIZE_WIDTH = "min_resize_width";
    private static final java.lang.String ATTR_MIN_WIDTH = "min_width";
    private static final java.lang.String ATTR_OS_FINGERPRINT = "os_fingerprint";
    private static final java.lang.String ATTR_PREVIEW_IMAGE = "preview_image";
    private static final java.lang.String ATTR_PREVIEW_LAYOUT = "preview_layout";
    private static final java.lang.String ATTR_PROVIDER_INHERITANCE = "provider_inheritance";
    private static final java.lang.String ATTR_RESIZE_MODE = "resize_mode";
    private static final java.lang.String ATTR_TARGET_CELL_HEIGHT = "target_cell_height";
    private static final java.lang.String ATTR_TARGET_CELL_WIDTH = "target_cell_width";
    private static final java.lang.String ATTR_UPDATE_PERIOD_MILLIS = "update_period_millis";
    private static final java.lang.String ATTR_WIDGET_CATEGORY = "widget_category";
    private static final java.lang.String ATTR_WIDGET_FEATURES = "widget_features";
    private static final java.lang.String TAG = "AppWidgetXmlUtil";

    public static void writeAppWidgetProviderInfoLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) throws java.io.IOException {
        java.util.Objects.requireNonNull(typedXmlSerializer);
        java.util.Objects.requireNonNull(appWidgetProviderInfo);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_WIDTH, appWidgetProviderInfo.minWidth);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_HEIGHT, appWidgetProviderInfo.minHeight);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_RESIZE_WIDTH, appWidgetProviderInfo.minResizeWidth);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_RESIZE_HEIGHT, appWidgetProviderInfo.minResizeHeight);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MAX_RESIZE_WIDTH, appWidgetProviderInfo.maxResizeWidth);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MAX_RESIZE_HEIGHT, appWidgetProviderInfo.maxResizeHeight);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_TARGET_CELL_WIDTH, appWidgetProviderInfo.targetCellWidth);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_TARGET_CELL_HEIGHT, appWidgetProviderInfo.targetCellHeight);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_UPDATE_PERIOD_MILLIS, appWidgetProviderInfo.updatePeriodMillis);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INITIAL_LAYOUT, appWidgetProviderInfo.initialLayout);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INITIAL_KEYGUARD_LAYOUT, appWidgetProviderInfo.initialKeyguardLayout);
        if (appWidgetProviderInfo.configure != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_CONFIGURE, appWidgetProviderInfo.configure.flattenToShortString());
        }
        if (appWidgetProviderInfo.label != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LABEL, appWidgetProviderInfo.label);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_ICON, appWidgetProviderInfo.icon);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PREVIEW_IMAGE, appWidgetProviderInfo.previewImage);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PREVIEW_LAYOUT, appWidgetProviderInfo.previewLayout);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_AUTO_ADVANCED_VIEW_ID, appWidgetProviderInfo.autoAdvanceViewId);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_RESIZE_MODE, appWidgetProviderInfo.resizeMode);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_WIDGET_CATEGORY, appWidgetProviderInfo.widgetCategory);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_WIDGET_FEATURES, appWidgetProviderInfo.widgetFeatures);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_DESCRIPTION_RES, appWidgetProviderInfo.descriptionRes);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_PROVIDER_INHERITANCE, appWidgetProviderInfo.isExtendedFromAppWidgetProvider);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_OS_FINGERPRINT, android.os.Build.VERSION.INCREMENTAL);
    }

    @android.annotation.Nullable
    public static android.appwidget.AppWidgetProviderInfo readAppWidgetProviderInfoLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.util.Objects.requireNonNull(typedXmlPullParser);
        if (!android.os.Build.VERSION.INCREMENTAL.equals(typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_OS_FINGERPRINT))) {
            return null;
        }
        android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
        appWidgetProviderInfo.minWidth = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MIN_WIDTH, 0);
        appWidgetProviderInfo.minHeight = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MIN_HEIGHT, 0);
        appWidgetProviderInfo.minResizeWidth = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MIN_RESIZE_WIDTH, 0);
        appWidgetProviderInfo.minResizeHeight = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MIN_RESIZE_HEIGHT, 0);
        appWidgetProviderInfo.maxResizeWidth = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MAX_RESIZE_WIDTH, 0);
        appWidgetProviderInfo.maxResizeHeight = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MAX_RESIZE_HEIGHT, 0);
        appWidgetProviderInfo.targetCellWidth = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_TARGET_CELL_WIDTH, 0);
        appWidgetProviderInfo.targetCellHeight = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_TARGET_CELL_HEIGHT, 0);
        appWidgetProviderInfo.updatePeriodMillis = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_UPDATE_PERIOD_MILLIS, 0);
        appWidgetProviderInfo.initialLayout = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INITIAL_LAYOUT, 0);
        appWidgetProviderInfo.initialKeyguardLayout = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INITIAL_KEYGUARD_LAYOUT, 0);
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_CONFIGURE);
        if (!android.text.TextUtils.isEmpty(attributeValue)) {
            appWidgetProviderInfo.configure = android.content.ComponentName.unflattenFromString(attributeValue);
        }
        appWidgetProviderInfo.label = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_LABEL);
        appWidgetProviderInfo.icon = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_ICON, 0);
        appWidgetProviderInfo.previewImage = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PREVIEW_IMAGE, 0);
        appWidgetProviderInfo.previewLayout = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PREVIEW_LAYOUT, 0);
        appWidgetProviderInfo.autoAdvanceViewId = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_AUTO_ADVANCED_VIEW_ID, 0);
        appWidgetProviderInfo.resizeMode = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_RESIZE_MODE, 0);
        appWidgetProviderInfo.widgetCategory = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_WIDGET_CATEGORY, 0);
        appWidgetProviderInfo.widgetFeatures = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_WIDGET_FEATURES, 0);
        appWidgetProviderInfo.descriptionRes = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_DESCRIPTION_RES, 0);
        appWidgetProviderInfo.isExtendedFromAppWidgetProvider = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_PROVIDER_INHERITANCE, false);
        return appWidgetProviderInfo;
    }
}
