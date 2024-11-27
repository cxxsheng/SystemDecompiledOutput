package android.accessibilityservice.util;

/* loaded from: classes.dex */
public final class AccessibilityUtils {
    private static final java.lang.String ANCHOR_TAG = "a";
    private static final java.lang.String IMG_PREFIX = "R.drawable.";
    private static final java.util.List<java.lang.String> UNSUPPORTED_TAG_LIST = new java.util.ArrayList(java.util.Collections.singletonList("a"));

    private AccessibilityUtils() {
    }

    public static java.lang.String getFilteredHtmlText(java.lang.String str) {
        for (java.lang.String str2 : UNSUPPORTED_TAG_LIST) {
            java.lang.String str3 = "(?i)<" + str2 + "(\\s+|>)";
            str = java.util.regex.Pattern.compile("(?i)</" + str2 + "\\s*>").matcher(java.util.regex.Pattern.compile(str3).matcher(str).replaceAll("<invalidtag ")).replaceAll("</invalidtag>");
        }
        return java.util.regex.Pattern.compile("(?i)<img\\s+(?!src\\s*=\\s*\"(?-i)R.drawable.)").matcher(str).replaceAll("<invalidtag ");
    }

    public static android.graphics.drawable.Drawable loadSafeAnimatedImage(android.content.Context context, android.content.pm.ApplicationInfo applicationInfo, int i) {
        android.graphics.drawable.Drawable drawable;
        if (i == 0 || (drawable = context.getPackageManager().getDrawable(applicationInfo.packageName, i, applicationInfo)) == null) {
            return null;
        }
        boolean z = drawable.getIntrinsicWidth() > getScreenWidthPixels(context);
        boolean z2 = drawable.getIntrinsicHeight() > getScreenHeightPixels(context);
        if (z || z2) {
            return null;
        }
        return drawable;
    }

    private static int getScreenWidthPixels(android.content.Context context) {
        return java.lang.Math.round(android.util.TypedValue.applyDimension(1, r2.getConfiguration().screenWidthDp, context.getResources().getDisplayMetrics()));
    }

    private static int getScreenHeightPixels(android.content.Context context) {
        return java.lang.Math.round(android.util.TypedValue.applyDimension(1, r2.getConfiguration().screenHeightDp, context.getResources().getDisplayMetrics()));
    }
}
