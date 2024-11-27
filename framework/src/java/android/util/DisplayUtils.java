package android.util;

/* loaded from: classes3.dex */
public class DisplayUtils {
    public static int getDisplayUniqueIdConfigIndex(android.content.res.Resources resources, java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.config_displayUniqueIdArray);
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(stringArray[i])) {
                return i;
            }
        }
        return -1;
    }

    public static android.view.Display.Mode getMaximumResolutionDisplayMode(android.view.Display.Mode[] modeArr) {
        android.view.Display.Mode mode = null;
        if (modeArr == null || modeArr.length == 0) {
            return null;
        }
        int i = 0;
        for (android.view.Display.Mode mode2 : modeArr) {
            if (mode2.getPhysicalWidth() > i) {
                i = mode2.getPhysicalWidth();
                mode = mode2;
            }
        }
        return mode;
    }

    public static float getPhysicalPixelDisplaySizeRatio(int i, int i2, int i3, int i4) {
        if (i == i3 && i2 == i4) {
            return 1.0f;
        }
        return java.lang.Math.min(i3 / i, i4 / i2);
    }
}
