package com.android.internal.policy;

/* loaded from: classes5.dex */
public final class SystemBarUtils {
    public static int getStatusBarHeight(android.content.Context context) {
        return getStatusBarHeight(context.getResources(), context.getDisplay().getCutout());
    }

    public static int getStatusBarHeight(android.content.res.Resources resources, android.view.DisplayCutout displayCutout) {
        return java.lang.Math.max(displayCutout == null ? 0 : displayCutout.getSafeInsetTop(), resources.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height_default) + (displayCutout != null ? displayCutout.getWaterfallInsets().top : 0));
    }

    public static int getStatusBarHeightForRotation(android.content.Context context, int i) {
        android.graphics.Insets waterfallInsets;
        android.graphics.Insets insets;
        android.view.Display display = context.getDisplay();
        int rotation = display.getRotation();
        android.view.DisplayCutout cutout = display.getCutout();
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (cutout == null) {
            insets = android.graphics.Insets.NONE;
            waterfallInsets = android.graphics.Insets.NONE;
        } else {
            android.view.DisplayCutout rotated = cutout.getRotated(displayInfo.logicalWidth, displayInfo.logicalHeight, rotation, i);
            android.graphics.Insets of = android.graphics.Insets.of(rotated.getSafeInsets());
            waterfallInsets = rotated.getWaterfallInsets();
            insets = of;
        }
        return java.lang.Math.max(insets.top, context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height_default) + waterfallInsets.top);
    }

    public static int getQuickQsOffsetHeight(android.content.Context context) {
        return java.lang.Math.max(context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.quick_qs_offset_height), getStatusBarHeight(context));
    }
}
