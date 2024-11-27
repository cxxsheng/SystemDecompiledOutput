package com.android.internal.util;

/* loaded from: classes5.dex */
public class UserIcons {
    private static final int[] USER_ICON_COLORS = {com.android.internal.R.color.user_icon_1, com.android.internal.R.color.user_icon_2, com.android.internal.R.color.user_icon_3, com.android.internal.R.color.user_icon_4, com.android.internal.R.color.user_icon_5, com.android.internal.R.color.user_icon_6, com.android.internal.R.color.user_icon_7, com.android.internal.R.color.user_icon_8};

    public static android.graphics.Bitmap convertToBitmap(android.graphics.drawable.Drawable drawable) {
        return convertToBitmapAtSize(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    public static android.graphics.Bitmap convertToBitmapAtUserIconSize(android.content.res.Resources resources, android.graphics.drawable.Drawable drawable) {
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.user_icon_size);
        return convertToBitmapAtSize(drawable, dimensionPixelSize, dimensionPixelSize);
    }

    private static android.graphics.Bitmap convertToBitmapAtSize(android.graphics.drawable.Drawable drawable, int i, int i2) {
        if (drawable == null) {
            return null;
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        return createBitmap;
    }

    public static android.graphics.drawable.Drawable getDefaultUserIcon(android.content.res.Resources resources, int i, boolean z) {
        int i2 = z ? com.android.internal.R.color.user_icon_default_white : com.android.internal.R.color.user_icon_default_gray;
        if (i != -10000) {
            i2 = USER_ICON_COLORS[i % USER_ICON_COLORS.length];
        }
        return getDefaultUserIconInColor(resources, resources.getColor(i2, null));
    }

    public static android.graphics.drawable.Drawable getDefaultUserIconInColor(android.content.res.Resources resources, int i) {
        android.graphics.drawable.Drawable mutate = resources.getDrawable(com.android.internal.R.drawable.ic_account_circle, null).mutate();
        mutate.setColorFilter(i, android.graphics.PorterDuff.Mode.SRC_IN);
        mutate.setBounds(0, 0, mutate.getIntrinsicWidth(), mutate.getIntrinsicHeight());
        return mutate;
    }

    public static int[] getUserIconColors(android.content.res.Resources resources) {
        int length = USER_ICON_COLORS.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = resources.getColor(USER_ICON_COLORS[i], null);
        }
        return iArr;
    }
}
