package com.android.internal.policy;

/* loaded from: classes5.dex */
public class ScreenDecorationsUtils {
    public static float getWindowCornerRadius(android.content.Context context) {
        android.content.res.Resources resources = context.getResources();
        if (!supportsRoundedCornersOnWindows(resources)) {
            return 0.0f;
        }
        java.lang.String uniqueId = context.getDisplayNoVerify().getUniqueId();
        float roundedCornerRadius = android.view.RoundedCorners.getRoundedCornerRadius(resources, uniqueId) - android.view.RoundedCorners.getRoundedCornerRadiusAdjustment(resources, uniqueId);
        float roundedCornerTopRadius = android.view.RoundedCorners.getRoundedCornerTopRadius(resources, uniqueId) - android.view.RoundedCorners.getRoundedCornerRadiusTopAdjustment(resources, uniqueId);
        if (roundedCornerTopRadius == 0.0f) {
            roundedCornerTopRadius = roundedCornerRadius;
        }
        float roundedCornerBottomRadius = android.view.RoundedCorners.getRoundedCornerBottomRadius(resources, uniqueId) - android.view.RoundedCorners.getRoundedCornerRadiusBottomAdjustment(resources, uniqueId);
        if (roundedCornerBottomRadius != 0.0f) {
            roundedCornerRadius = roundedCornerBottomRadius;
        }
        return java.lang.Math.min(roundedCornerTopRadius, roundedCornerRadius);
    }

    public static boolean supportsRoundedCornersOnWindows(android.content.res.Resources resources) {
        return resources.getBoolean(com.android.internal.R.bool.config_supportsRoundedCornersOnWindows);
    }
}
