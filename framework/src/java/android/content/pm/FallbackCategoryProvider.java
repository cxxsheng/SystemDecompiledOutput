package android.content.pm;

/* loaded from: classes.dex */
public class FallbackCategoryProvider {
    private static final java.lang.String TAG = "FallbackCategoryProvider";
    private static final android.util.ArrayMap<java.lang.String, java.lang.Integer> sFallbacks = new android.util.ArrayMap<>();

    public static void loadFallbacks() {
        sFallbacks.clear();
        if (android.os.SystemProperties.getBoolean("fw.ignore_fb_categories", false)) {
            android.util.Log.d(TAG, "Ignoring fallback categories");
            return;
        }
        android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
        assetManager.addAssetPath("/system/framework/framework-res.apk");
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(new android.content.res.Resources(assetManager, null, null).openRawResource(com.android.internal.R.raw.fallback_categories)));
            while (true) {
                try {
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.charAt(0) != '#') {
                            java.lang.String[] split = readLine.split(",");
                            if (split.length == 2) {
                                sFallbacks.put(split[0], java.lang.Integer.valueOf(java.lang.Integer.parseInt(split[1])));
                            }
                        }
                    } else {
                        android.util.Log.d(TAG, "Found " + sFallbacks.size() + " fallback categories");
                        bufferedReader.close();
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (java.io.IOException | java.lang.NumberFormatException e) {
            android.util.Log.w(TAG, "Failed to read fallback categories", e);
        }
    }

    public static int getFallbackCategory(java.lang.String str) {
        return sFallbacks.getOrDefault(str, -1).intValue();
    }
}
