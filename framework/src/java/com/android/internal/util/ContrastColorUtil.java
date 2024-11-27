package com.android.internal.util;

/* loaded from: classes5.dex */
public class ContrastColorUtil {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ContrastColorUtil";
    private static com.android.internal.util.ContrastColorUtil sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private final int mGrayscaleIconMaxSize;
    private final com.android.internal.util.ImageUtils mImageUtils = new com.android.internal.util.ImageUtils();
    private final java.util.WeakHashMap<android.graphics.Bitmap, android.util.Pair<java.lang.Boolean, java.lang.Integer>> mGrayscaleBitmapCache = new java.util.WeakHashMap<>();

    public static com.android.internal.util.ContrastColorUtil getInstance(android.content.Context context) {
        com.android.internal.util.ContrastColorUtil contrastColorUtil;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new com.android.internal.util.ContrastColorUtil(context);
            }
            contrastColorUtil = sInstance;
        }
        return contrastColorUtil;
    }

    private ContrastColorUtil(android.content.Context context) {
        this.mGrayscaleIconMaxSize = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_grayscale_icon_max_size);
    }

    public boolean isGrayscaleIcon(android.graphics.Bitmap bitmap) {
        boolean isGrayscale;
        int generationId;
        if (bitmap.getWidth() > this.mGrayscaleIconMaxSize || bitmap.getHeight() > this.mGrayscaleIconMaxSize) {
            return false;
        }
        synchronized (sLock) {
            android.util.Pair<java.lang.Boolean, java.lang.Integer> pair = this.mGrayscaleBitmapCache.get(bitmap);
            if (pair != null && pair.second.intValue() == bitmap.getGenerationId()) {
                return pair.first.booleanValue();
            }
            synchronized (this.mImageUtils) {
                isGrayscale = this.mImageUtils.isGrayscale(bitmap);
                generationId = bitmap.getGenerationId();
            }
            synchronized (sLock) {
                this.mGrayscaleBitmapCache.put(bitmap, android.util.Pair.create(java.lang.Boolean.valueOf(isGrayscale), java.lang.Integer.valueOf(generationId)));
            }
            return isGrayscale;
        }
    }

    public boolean isGrayscaleIcon(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            android.graphics.drawable.BitmapDrawable bitmapDrawable = (android.graphics.drawable.BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap() != null && isGrayscaleIcon(bitmapDrawable.getBitmap());
        }
        if (!(drawable instanceof android.graphics.drawable.AnimationDrawable)) {
            return drawable instanceof android.graphics.drawable.VectorDrawable;
        }
        android.graphics.drawable.AnimationDrawable animationDrawable = (android.graphics.drawable.AnimationDrawable) drawable;
        return animationDrawable.getNumberOfFrames() > 0 && isGrayscaleIcon(animationDrawable.getFrame(0));
    }

    public boolean isGrayscaleIcon(android.content.Context context, android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return false;
        }
        switch (icon.getType()) {
        }
        return false;
    }

    public boolean isGrayscaleIcon(android.content.Context context, int i) {
        if (i == 0) {
            return false;
        }
        try {
            return isGrayscaleIcon(context.getDrawable(i));
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e(TAG, "Drawable not found: " + i);
            return false;
        }
    }

    public java.lang.CharSequence invertCharSequenceColors(java.lang.CharSequence charSequence) {
        java.lang.Object obj;
        java.lang.Object obj2;
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), java.lang.Object.class);
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(spanned.toString());
            for (java.lang.Object obj3 : spans) {
                if (!(obj3 instanceof android.text.style.CharacterStyle)) {
                    obj = obj3;
                } else {
                    obj = ((android.text.style.CharacterStyle) obj3).getUnderlying();
                }
                if (obj instanceof android.text.style.TextAppearanceSpan) {
                    obj2 = processTextAppearanceSpan((android.text.style.TextAppearanceSpan) obj3);
                    if (obj2 == obj) {
                        obj2 = obj3;
                    }
                } else if (obj instanceof android.text.style.ForegroundColorSpan) {
                    obj2 = new android.text.style.ForegroundColorSpan(processColor(((android.text.style.ForegroundColorSpan) obj).getForegroundColor()));
                } else {
                    obj2 = obj3;
                }
                spannableStringBuilder.setSpan(obj2, spanned.getSpanStart(obj3), spanned.getSpanEnd(obj3), spanned.getSpanFlags(obj3));
            }
            return spannableStringBuilder;
        }
        return charSequence;
    }

    private android.text.style.TextAppearanceSpan processTextAppearanceSpan(android.text.style.TextAppearanceSpan textAppearanceSpan) {
        android.content.res.ColorStateList textColor = textAppearanceSpan.getTextColor();
        if (textColor != null) {
            int[] colors = textColor.getColors();
            boolean z = false;
            for (int i = 0; i < colors.length; i++) {
                if (com.android.internal.util.ImageUtils.isGrayscale(colors[i])) {
                    if (!z) {
                        colors = java.util.Arrays.copyOf(colors, colors.length);
                    }
                    colors[i] = processColor(colors[i]);
                    z = true;
                }
            }
            if (z) {
                return new android.text.style.TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), new android.content.res.ColorStateList(textColor.getStates(), colors), textAppearanceSpan.getLinkTextColor());
            }
        }
        return textAppearanceSpan;
    }

    public static java.lang.CharSequence clearColorSpans(java.lang.CharSequence charSequence) {
        java.lang.Object obj;
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), java.lang.Object.class);
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(spanned.toString());
            for (java.lang.Object obj2 : spans) {
                if (!(obj2 instanceof android.text.style.CharacterStyle)) {
                    obj = obj2;
                } else {
                    obj = ((android.text.style.CharacterStyle) obj2).getUnderlying();
                }
                if (!(obj instanceof android.text.style.TextAppearanceSpan)) {
                    if (!(obj instanceof android.text.style.ForegroundColorSpan) && !(obj instanceof android.text.style.BackgroundColorSpan)) {
                        obj = obj2;
                    }
                } else {
                    android.text.style.TextAppearanceSpan textAppearanceSpan = (android.text.style.TextAppearanceSpan) obj;
                    if (textAppearanceSpan.getTextColor() != null) {
                        obj = new android.text.style.TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), null, textAppearanceSpan.getLinkTextColor());
                    }
                }
                spannableStringBuilder.setSpan(obj, spanned.getSpanStart(obj2), spanned.getSpanEnd(obj2), spanned.getSpanFlags(obj2));
            }
            return spannableStringBuilder;
        }
        return charSequence;
    }

    public static java.lang.CharSequence ensureColorSpanContrast(java.lang.CharSequence charSequence, int i) {
        java.lang.Object obj;
        java.lang.Object[] objArr;
        int i2;
        android.content.res.ColorStateList colorStateList;
        if (charSequence == null || !(charSequence instanceof android.text.Spanned)) {
            return charSequence;
        }
        android.text.Spanned spanned = (android.text.Spanned) charSequence;
        int i3 = 0;
        java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), java.lang.Object.class);
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(spanned.toString());
        int length = spans.length;
        int i4 = 0;
        while (i4 < length) {
            java.lang.Object obj2 = spans[i4];
            int spanStart = spanned.getSpanStart(obj2);
            int spanEnd = spanned.getSpanEnd(obj2);
            int i5 = spanEnd - spanStart == charSequence.length() ? 1 : i3;
            if (!(obj2 instanceof android.text.style.CharacterStyle)) {
                obj = obj2;
            } else {
                obj = ((android.text.style.CharacterStyle) obj2).getUnderlying();
            }
            java.lang.Object obj3 = null;
            if (obj instanceof android.text.style.TextAppearanceSpan) {
                android.text.style.TextAppearanceSpan textAppearanceSpan = (android.text.style.TextAppearanceSpan) obj;
                android.content.res.ColorStateList textColor = textAppearanceSpan.getTextColor();
                if (textColor == null) {
                    objArr = spans;
                    i2 = length;
                } else {
                    if (i5 != 0) {
                        objArr = spans;
                        i2 = length;
                        colorStateList = null;
                    } else {
                        int[] colors = textColor.getColors();
                        int length2 = colors.length;
                        int[] iArr = new int[length2];
                        while (i3 < length2) {
                            iArr[i3] = ensureLargeTextContrast(colors[i3], i, isColorDark(i));
                            i3++;
                            spans = spans;
                            length = length;
                        }
                        objArr = spans;
                        i2 = length;
                        colorStateList = new android.content.res.ColorStateList((int[][]) textColor.getStates().clone(), iArr);
                    }
                    obj = new android.text.style.TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), colorStateList, textAppearanceSpan.getLinkTextColor());
                }
                obj3 = obj;
            } else {
                objArr = spans;
                i2 = length;
                if (obj instanceof android.text.style.ForegroundColorSpan) {
                    if (i5 == 0) {
                        obj3 = new android.text.style.ForegroundColorSpan(ensureLargeTextContrast(((android.text.style.ForegroundColorSpan) obj).getForegroundColor(), i, isColorDark(i)));
                    }
                } else {
                    obj3 = obj2;
                }
            }
            if (obj3 != null) {
                spannableStringBuilder.setSpan(obj3, spanStart, spanEnd, spanned.getSpanFlags(obj2));
            }
            i4++;
            spans = objArr;
            length = i2;
            i3 = 0;
        }
        return spannableStringBuilder;
    }

    public static boolean isColorDark(int i) {
        return calculateLuminance(i) <= 0.17912878474d;
    }

    private int processColor(int i) {
        return android.graphics.Color.argb(android.graphics.Color.alpha(i), 255 - android.graphics.Color.red(i), 255 - android.graphics.Color.green(i), 255 - android.graphics.Color.blue(i));
    }

    public static int findContrastColor(int i, int i2, boolean z, double d) {
        int i3 = z ? i : i2;
        int i4 = z ? i2 : i;
        if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i3, i4) >= d) {
            return i;
        }
        double[] dArr = new double[3];
        com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.colorToLAB(z ? i3 : i4, dArr);
        double d2 = dArr[0];
        double d3 = dArr[1];
        double d4 = dArr[2];
        double d5 = 0.0d;
        for (int i5 = 0; i5 < 15 && d2 - d5 > 1.0E-5d; i5++) {
            double d6 = (d5 + d2) / 2.0d;
            if (z) {
                i3 = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.LABToColor(d6, d3, d4);
            } else {
                i4 = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.LABToColor(d6, d3, d4);
            }
            if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i3, i4) > d) {
                d5 = d6;
            } else {
                d2 = d6;
            }
        }
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.LABToColor(d5, d3, d4);
    }

    public static int findAlphaToMeetContrast(int i, int i2, double d) {
        if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i, i2) >= d) {
            return i;
        }
        int alpha = android.graphics.Color.alpha(i);
        int red = android.graphics.Color.red(i);
        int green = android.graphics.Color.green(i);
        int blue = android.graphics.Color.blue(i);
        int i3 = 255;
        for (int i4 = 0; i4 < 15 && i3 - alpha > 0; i4++) {
            int i5 = (alpha + i3) / 2;
            if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(android.graphics.Color.argb(i5, red, green, blue), i2) > d) {
                i3 = i5;
            } else {
                alpha = i5;
            }
        }
        return android.graphics.Color.argb(i3, red, green, blue);
    }

    public static int findContrastColorAgainstDark(int i, int i2, boolean z, double d) {
        int i3 = z ? i : i2;
        if (!z) {
            i2 = i;
        }
        if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i3, i2) >= d) {
            return i;
        }
        float[] fArr = new float[3];
        com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.colorToHSL(z ? i3 : i2, fArr);
        float f = fArr[2];
        float f2 = 1.0f;
        for (int i4 = 0; i4 < 15 && f2 - f > 1.0E-5d; i4++) {
            float f3 = (f + f2) / 2.0f;
            fArr[2] = f3;
            if (z) {
                i3 = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.HSLToColor(fArr);
            } else {
                i2 = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.HSLToColor(fArr);
            }
            if (com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i3, i2) > d) {
                f2 = f3;
            } else {
                f = f3;
            }
        }
        fArr[2] = f2;
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.HSLToColor(fArr);
    }

    public static int ensureTextContrastOnBlack(int i) {
        return findContrastColorAgainstDark(i, -16777216, true, 12.0d);
    }

    public static int ensureLargeTextContrast(int i, int i2, boolean z) {
        if (z) {
            return findContrastColorAgainstDark(i, i2, true, 3.0d);
        }
        return findContrastColor(i, i2, true, 3.0d);
    }

    public static int ensureTextContrast(int i, int i2, boolean z) {
        return ensureContrast(i, i2, z, 4.5d);
    }

    public static int ensureContrast(int i, int i2, boolean z, double d) {
        if (z) {
            return findContrastColorAgainstDark(i, i2, true, d);
        }
        return findContrastColor(i, i2, true, d);
    }

    public static int ensureTextBackgroundColor(int i, int i2, int i3) {
        return findContrastColor(findContrastColor(i, i3, false, 3.0d), i2, false, 4.5d);
    }

    private static java.lang.String contrastChange(int i, int i2, int i3) {
        return java.lang.String.format("from %.2f:1 to %.2f:1", java.lang.Double.valueOf(com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i, i3)), java.lang.Double.valueOf(com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i2, i3)));
    }

    public static int resolveColor(android.content.Context context, int i, boolean z) {
        int i2;
        if (i == 0) {
            if (z) {
                i2 = com.android.internal.R.color.notification_default_color_dark;
            } else {
                i2 = com.android.internal.R.color.notification_default_color_light;
            }
            return context.getColor(i2);
        }
        return i;
    }

    public static int resolveContrastColor(android.content.Context context, int i, int i2) {
        return resolveContrastColor(context, i, i2, false);
    }

    public static int resolveContrastColor(android.content.Context context, int i, int i2, boolean z) {
        return ensureTextContrast(resolveColor(context, i, z), i2, z);
    }

    public static int changeColorLightness(int i, int i2) {
        double[] tempDouble3Array = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.getTempDouble3Array();
        com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.colorToLAB(i, tempDouble3Array);
        tempDouble3Array[0] = java.lang.Math.max(java.lang.Math.min(100.0d, tempDouble3Array[0] + i2), 0.0d);
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.LABToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
    }

    public static int resolvePrimaryColor(android.content.Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(com.android.internal.R.color.notification_primary_text_color_light);
        }
        return context.getColor(com.android.internal.R.color.notification_primary_text_color_dark);
    }

    public static int resolveSecondaryColor(android.content.Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(com.android.internal.R.color.notification_secondary_text_color_light);
        }
        return context.getColor(com.android.internal.R.color.notification_secondary_text_color_dark);
    }

    public static int resolveDefaultColor(android.content.Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(com.android.internal.R.color.notification_default_color_light);
        }
        return context.getColor(com.android.internal.R.color.notification_default_color_dark);
    }

    public static int getShiftedColor(int i, int i2) {
        double[] tempDouble3Array = com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.getTempDouble3Array();
        com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.colorToLAB(i, tempDouble3Array);
        if (tempDouble3Array[0] >= 4.0d) {
            tempDouble3Array[0] = java.lang.Math.max(0.0d, tempDouble3Array[0] - i2);
        } else {
            tempDouble3Array[0] = java.lang.Math.min(100.0d, tempDouble3Array[0] + i2);
        }
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.LABToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
    }

    public static int getMutedColor(int i, float f) {
        return compositeColors(com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.setAlphaComponent(-1, (int) (f * 255.0f)), i);
    }

    private static boolean shouldUseDark(int i, boolean z) {
        if (i == 0) {
            return !z;
        }
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateLuminance(i) > 0.17912878474d;
    }

    public static double calculateLuminance(int i) {
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateLuminance(i);
    }

    public static double calculateContrast(int i, int i2) {
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.calculateContrast(i, i2);
    }

    public static boolean satisfiesTextContrast(int i, int i2) {
        return calculateContrast(i2, i) >= 4.5d;
    }

    public static int compositeColors(int i, int i2) {
        return com.android.internal.util.ContrastColorUtil.ColorUtilsFromCompat.compositeColors(i, i2);
    }

    public static boolean isColorLight(int i) {
        return calculateLuminance(i) > 0.5d;
    }

    private static class ColorUtilsFromCompat {
        private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
        private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
        private static final java.lang.ThreadLocal<double[]> TEMP_ARRAY = new java.lang.ThreadLocal<>();
        private static final double XYZ_EPSILON = 0.008856d;
        private static final double XYZ_KAPPA = 903.3d;
        private static final double XYZ_WHITE_REFERENCE_X = 95.047d;
        private static final double XYZ_WHITE_REFERENCE_Y = 100.0d;
        private static final double XYZ_WHITE_REFERENCE_Z = 108.883d;

        private ColorUtilsFromCompat() {
        }

        public static int compositeColors(int i, int i2) {
            int alpha = android.graphics.Color.alpha(i2);
            int alpha2 = android.graphics.Color.alpha(i);
            int compositeAlpha = compositeAlpha(alpha2, alpha);
            return android.graphics.Color.argb(compositeAlpha, compositeComponent(android.graphics.Color.red(i), alpha2, android.graphics.Color.red(i2), alpha, compositeAlpha), compositeComponent(android.graphics.Color.green(i), alpha2, android.graphics.Color.green(i2), alpha, compositeAlpha), compositeComponent(android.graphics.Color.blue(i), alpha2, android.graphics.Color.blue(i2), alpha, compositeAlpha));
        }

        private static int compositeAlpha(int i, int i2) {
            return 255 - (((255 - i2) * (255 - i)) / 255);
        }

        private static int compositeComponent(int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                return 0;
            }
            return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
        }

        public static int setAlphaComponent(int i, int i2) {
            if (i2 < 0 || i2 > 255) {
                throw new java.lang.IllegalArgumentException("alpha must be between 0 and 255.");
            }
            return (i & 16777215) | (i2 << 24);
        }

        public static double calculateLuminance(int i) {
            double[] tempDouble3Array = getTempDouble3Array();
            colorToXYZ(i, tempDouble3Array);
            return tempDouble3Array[1] / XYZ_WHITE_REFERENCE_Y;
        }

        public static double calculateContrast(int i, int i2) {
            if (android.graphics.Color.alpha(i2) != 255) {
                android.util.Log.wtf(com.android.internal.util.ContrastColorUtil.TAG, "background can not be translucent: #" + java.lang.Integer.toHexString(i2));
            }
            if (android.graphics.Color.alpha(i) < 255) {
                i = compositeColors(i, i2);
            }
            double calculateLuminance = calculateLuminance(i) + 0.05d;
            double calculateLuminance2 = calculateLuminance(i2) + 0.05d;
            return java.lang.Math.max(calculateLuminance, calculateLuminance2) / java.lang.Math.min(calculateLuminance, calculateLuminance2);
        }

        public static void colorToLAB(int i, double[] dArr) {
            RGBToLAB(android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i), dArr);
        }

        public static void RGBToLAB(int i, int i2, int i3, double[] dArr) {
            RGBToXYZ(i, i2, i3, dArr);
            XYZToLAB(dArr[0], dArr[1], dArr[2], dArr);
        }

        public static void colorToXYZ(int i, double[] dArr) {
            RGBToXYZ(android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i), dArr);
        }

        public static void RGBToXYZ(int i, int i2, int i3, double[] dArr) {
            if (dArr.length != 3) {
                throw new java.lang.IllegalArgumentException("outXyz must have a length of 3.");
            }
            double d = i / 255.0d;
            double pow = d < 0.04045d ? d / 12.92d : java.lang.Math.pow((d + 0.055d) / 1.055d, 2.4d);
            double d2 = i2 / 255.0d;
            double pow2 = d2 < 0.04045d ? d2 / 12.92d : java.lang.Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
            double d3 = i3 / 255.0d;
            double pow3 = d3 < 0.04045d ? d3 / 12.92d : java.lang.Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
            dArr[0] = ((0.4124d * pow) + (0.3576d * pow2) + (0.1805d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[1] = ((0.2126d * pow) + (0.7152d * pow2) + (0.0722d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[2] = ((pow * 0.0193d) + (pow2 * 0.1192d) + (pow3 * 0.9505d)) * XYZ_WHITE_REFERENCE_Y;
        }

        public static void XYZToLAB(double d, double d2, double d3, double[] dArr) {
            if (dArr.length != 3) {
                throw new java.lang.IllegalArgumentException("outLab must have a length of 3.");
            }
            double pivotXyzComponent = pivotXyzComponent(d / XYZ_WHITE_REFERENCE_X);
            double pivotXyzComponent2 = pivotXyzComponent(d2 / XYZ_WHITE_REFERENCE_Y);
            double pivotXyzComponent3 = pivotXyzComponent(d3 / XYZ_WHITE_REFERENCE_Z);
            dArr[0] = java.lang.Math.max(0.0d, (116.0d * pivotXyzComponent2) - 16.0d);
            dArr[1] = (pivotXyzComponent - pivotXyzComponent2) * 500.0d;
            dArr[2] = (pivotXyzComponent2 - pivotXyzComponent3) * 200.0d;
        }

        public static void LABToXYZ(double d, double d2, double d3, double[] dArr) {
            double d4 = (d + 16.0d) / 116.0d;
            double d5 = (d2 / 500.0d) + d4;
            double d6 = d4 - (d3 / 200.0d);
            double pow = java.lang.Math.pow(d5, 3.0d);
            if (pow <= XYZ_EPSILON) {
                pow = ((d5 * 116.0d) - 16.0d) / XYZ_KAPPA;
            }
            double pow2 = d > 7.9996247999999985d ? java.lang.Math.pow(d4, 3.0d) : d / XYZ_KAPPA;
            double pow3 = java.lang.Math.pow(d6, 3.0d);
            if (pow3 <= XYZ_EPSILON) {
                pow3 = ((d6 * 116.0d) - 16.0d) / XYZ_KAPPA;
            }
            dArr[0] = pow * XYZ_WHITE_REFERENCE_X;
            dArr[1] = pow2 * XYZ_WHITE_REFERENCE_Y;
            dArr[2] = pow3 * XYZ_WHITE_REFERENCE_Z;
        }

        public static int XYZToColor(double d, double d2, double d3) {
            double d4 = (((3.2406d * d) + ((-1.5372d) * d2)) + ((-0.4986d) * d3)) / XYZ_WHITE_REFERENCE_Y;
            double d5 = ((((-0.9689d) * d) + (1.8758d * d2)) + (0.0415d * d3)) / XYZ_WHITE_REFERENCE_Y;
            double d6 = (((0.0557d * d) + ((-0.204d) * d2)) + (1.057d * d3)) / XYZ_WHITE_REFERENCE_Y;
            return android.graphics.Color.rgb(constrain((int) java.lang.Math.round((d4 > 0.0031308d ? (java.lang.Math.pow(d4, 0.4166666666666667d) * 1.055d) - 0.055d : d4 * 12.92d) * 255.0d), 0, 255), constrain((int) java.lang.Math.round((d5 > 0.0031308d ? (java.lang.Math.pow(d5, 0.4166666666666667d) * 1.055d) - 0.055d : d5 * 12.92d) * 255.0d), 0, 255), constrain((int) java.lang.Math.round((d6 > 0.0031308d ? (java.lang.Math.pow(d6, 0.4166666666666667d) * 1.055d) - 0.055d : d6 * 12.92d) * 255.0d), 0, 255));
        }

        public static int LABToColor(double d, double d2, double d3) {
            double[] tempDouble3Array = getTempDouble3Array();
            LABToXYZ(d, d2, d3, tempDouble3Array);
            return XYZToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
        }

        private static int constrain(int i, int i2, int i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }

        private static float constrain(float f, float f2, float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }

        private static double pivotXyzComponent(double d) {
            if (d > XYZ_EPSILON) {
                return java.lang.Math.pow(d, 0.3333333333333333d);
            }
            return ((d * XYZ_KAPPA) + 16.0d) / 116.0d;
        }

        public static double[] getTempDouble3Array() {
            double[] dArr = TEMP_ARRAY.get();
            if (dArr == null) {
                double[] dArr2 = new double[3];
                TEMP_ARRAY.set(dArr2);
                return dArr2;
            }
            return dArr;
        }

        public static int HSLToColor(float[] fArr) {
            int round;
            int round2;
            int round3;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float abs = (1.0f - java.lang.Math.abs((f3 * 2.0f) - 1.0f)) * f2;
            float f4 = f3 - (0.5f * abs);
            float abs2 = (1.0f - java.lang.Math.abs(((f / 60.0f) % 2.0f) - 1.0f)) * abs;
            switch (((int) f) / 60) {
                case 0:
                    round = java.lang.Math.round((abs + f4) * 255.0f);
                    round2 = java.lang.Math.round((abs2 + f4) * 255.0f);
                    round3 = java.lang.Math.round(f4 * 255.0f);
                    break;
                case 1:
                    round = java.lang.Math.round((abs2 + f4) * 255.0f);
                    round2 = java.lang.Math.round((abs + f4) * 255.0f);
                    round3 = java.lang.Math.round(f4 * 255.0f);
                    break;
                case 2:
                    round = java.lang.Math.round(f4 * 255.0f);
                    round2 = java.lang.Math.round((abs + f4) * 255.0f);
                    round3 = java.lang.Math.round((abs2 + f4) * 255.0f);
                    break;
                case 3:
                    round = java.lang.Math.round(f4 * 255.0f);
                    round2 = java.lang.Math.round((abs2 + f4) * 255.0f);
                    round3 = java.lang.Math.round((abs + f4) * 255.0f);
                    break;
                case 4:
                    round = java.lang.Math.round((abs2 + f4) * 255.0f);
                    round2 = java.lang.Math.round(f4 * 255.0f);
                    round3 = java.lang.Math.round((abs + f4) * 255.0f);
                    break;
                case 5:
                case 6:
                    round = java.lang.Math.round((abs + f4) * 255.0f);
                    round2 = java.lang.Math.round(f4 * 255.0f);
                    round3 = java.lang.Math.round((abs2 + f4) * 255.0f);
                    break;
                default:
                    round3 = 0;
                    round = 0;
                    round2 = 0;
                    break;
            }
            return android.graphics.Color.rgb(constrain(round, 0, 255), constrain(round2, 0, 255), constrain(round3, 0, 255));
        }

        public static void colorToHSL(int i, float[] fArr) {
            RGBToHSL(android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i), fArr);
        }

        public static void RGBToHSL(int i, int i2, int i3, float[] fArr) {
            float f;
            float abs;
            float f2 = i / 255.0f;
            float f3 = i2 / 255.0f;
            float f4 = i3 / 255.0f;
            float max = java.lang.Math.max(f2, java.lang.Math.max(f3, f4));
            float min = java.lang.Math.min(f2, java.lang.Math.min(f3, f4));
            float f5 = max - min;
            float f6 = (max + min) / 2.0f;
            if (max == min) {
                f = 0.0f;
                abs = 0.0f;
            } else {
                if (max == f2) {
                    f = ((f3 - f4) / f5) % 6.0f;
                } else if (max == f3) {
                    f = ((f4 - f2) / f5) + 2.0f;
                } else {
                    f = 4.0f + ((f2 - f3) / f5);
                }
                abs = f5 / (1.0f - java.lang.Math.abs((2.0f * f6) - 1.0f));
            }
            float f7 = (f * 60.0f) % 360.0f;
            if (f7 < 0.0f) {
                f7 += 360.0f;
            }
            fArr[0] = constrain(f7, 0.0f, 360.0f);
            fArr[1] = constrain(abs, 0.0f, 1.0f);
            fArr[2] = constrain(f6, 0.0f, 1.0f);
        }
    }
}
