package com.android.internal.widget;

/* loaded from: classes5.dex */
final class ColoredIconHelper {
    static final int COLOR_INVALID = 1;

    private ColoredIconHelper() {
    }

    static void applyGrayTint(android.content.Context context, android.graphics.drawable.Drawable drawable, boolean z, int i) {
        if (i == 1) {
            return;
        }
        if (z) {
            drawable.mutate().setColorFilter(com.android.internal.util.ContrastColorUtil.resolveColor(context, 0, (context.getResources().getConfiguration().uiMode & 48) == 32), android.graphics.PorterDuff.Mode.SRC_ATOP);
        } else {
            drawable.mutate().setColorFilter(i, android.graphics.PorterDuff.Mode.SRC_ATOP);
        }
    }
}
