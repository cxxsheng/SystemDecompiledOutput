package com.android.internal.colorextraction.types;

/* loaded from: classes4.dex */
public class Tonal implements com.android.internal.colorextraction.types.ExtractionType {
    private static final boolean DEBUG = true;
    private static final float FIT_WEIGHT_H = 1.0f;
    private static final float FIT_WEIGHT_L = 10.0f;
    private static final float FIT_WEIGHT_S = 1.0f;
    public static final int MAIN_COLOR_DARK = -14671580;
    public static final int MAIN_COLOR_LIGHT = -2433824;
    public static final int MAIN_COLOR_REGULAR = -16777216;
    private static final java.lang.String TAG = "Tonal";
    private final android.content.Context mContext;
    private final com.android.internal.colorextraction.types.Tonal.TonalPalette mGreyPalette;
    private float[] mTmpHSL = new float[3];
    private final java.util.ArrayList<com.android.internal.colorextraction.types.Tonal.TonalPalette> mTonalPalettes;

    public Tonal(android.content.Context context) {
        this.mTonalPalettes = new com.android.internal.colorextraction.types.Tonal.ConfigParser(context).getTonalPalettes();
        this.mContext = context;
        this.mGreyPalette = this.mTonalPalettes.get(0);
        this.mTonalPalettes.remove(0);
    }

    @Override // com.android.internal.colorextraction.types.ExtractionType
    public void extractInto(android.app.WallpaperColors wallpaperColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors2, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors3) {
        if (!runTonalExtraction(wallpaperColors, gradientColors, gradientColors2, gradientColors3)) {
            applyFallback(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
        }
    }

    private boolean runTonalExtraction(android.app.WallpaperColors wallpaperColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors2, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors3) {
        int min;
        if (wallpaperColors == null) {
            return false;
        }
        java.util.List<android.graphics.Color> mainColors = wallpaperColors.getMainColors();
        int size = mainColors.size();
        boolean z = (wallpaperColors.getColorHints() & 1) != 0;
        if (size == 0) {
            return false;
        }
        int argb = mainColors.get(0).toArgb();
        float[] fArr = new float[3];
        com.android.internal.graphics.ColorUtils.RGBToHSL(android.graphics.Color.red(argb), android.graphics.Color.green(argb), android.graphics.Color.blue(argb), fArr);
        fArr[0] = fArr[0] / 360.0f;
        com.android.internal.colorextraction.types.Tonal.TonalPalette findTonalPalette = findTonalPalette(fArr[0], fArr[1]);
        if (findTonalPalette != null) {
            int bestFit = bestFit(findTonalPalette, fArr[0], fArr[1], fArr[2]);
            if (bestFit == -1) {
                android.util.Log.w(TAG, "Could not find best fit!");
                return false;
            }
            float[] fit = fit(findTonalPalette.h, fArr[0], bestFit, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
            float[] fit2 = fit(findTonalPalette.s, fArr[1], bestFit, 0.0f, 1.0f);
            float[] fit3 = fit(findTonalPalette.l, fArr[2], bestFit, 0.0f, 1.0f);
            int[] colorPalette = getColorPalette(fit, fit2, fit3);
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Tonal Palette - index: " + bestFit + ". Main color: " + java.lang.Integer.toHexString(getColorInt(bestFit, fit, fit2, fit3)) + "\nColors: ");
            for (int i = 0; i < fit.length; i++) {
                sb.append(java.lang.Integer.toHexString(getColorInt(i, fit, fit2, fit3)));
                if (i < fit.length - 1) {
                    sb.append(", ");
                }
            }
            android.util.Log.d(TAG, sb.toString());
            int colorInt = getColorInt(bestFit, fit, fit2, fit3);
            com.android.internal.graphics.ColorUtils.colorToHSL(colorInt, this.mTmpHSL);
            float f = this.mTmpHSL[2];
            com.android.internal.graphics.ColorUtils.colorToHSL(MAIN_COLOR_LIGHT, this.mTmpHSL);
            if (f > this.mTmpHSL[2]) {
                return false;
            }
            com.android.internal.graphics.ColorUtils.colorToHSL(MAIN_COLOR_DARK, this.mTmpHSL);
            if (f < this.mTmpHSL[2]) {
                return false;
            }
            int i2 = 0;
            gradientColors.setMainColor(colorInt);
            gradientColors.setSecondaryColor(colorInt);
            gradientColors.setColorPalette(colorPalette);
            if (z) {
                min = fit.length - 1;
            } else if (bestFit < 2) {
                min = 0;
            } else {
                min = java.lang.Math.min(bestFit, 3);
            }
            int colorInt2 = getColorInt(min, fit, fit2, fit3);
            gradientColors2.setMainColor(colorInt2);
            gradientColors2.setSecondaryColor(colorInt2);
            gradientColors2.setColorPalette(colorPalette);
            if (z) {
                i2 = fit.length - 1;
            } else if (bestFit >= 2) {
                i2 = 2;
            }
            int colorInt3 = getColorInt(i2, fit, fit2, fit3);
            gradientColors3.setMainColor(colorInt3);
            gradientColors3.setSecondaryColor(colorInt3);
            gradientColors3.setColorPalette(colorPalette);
            gradientColors.setSupportsDarkText(z);
            gradientColors2.setSupportsDarkText(z);
            gradientColors3.setSupportsDarkText(z);
            android.util.Log.d(TAG, "Gradients: \n\tNormal " + gradientColors + "\n\tDark " + gradientColors2 + "\n\tExtra dark: " + gradientColors3);
            return true;
        }
        android.util.Log.w(TAG, "Could not find a tonal palette!");
        return false;
    }

    private void applyFallback(android.app.WallpaperColors wallpaperColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors2, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors3) {
        applyFallback(wallpaperColors, gradientColors);
        applyFallback(wallpaperColors, gradientColors2);
        applyFallback(wallpaperColors, gradientColors3);
    }

    public void applyFallback(android.app.WallpaperColors wallpaperColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors) {
        int i;
        boolean z = (wallpaperColors == null || (wallpaperColors.getColorHints() & 1) == 0) ? false : true;
        boolean z2 = (wallpaperColors == null || (wallpaperColors.getColorHints() & 2) == 0) ? false : true;
        boolean z3 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        if (z) {
            i = MAIN_COLOR_LIGHT;
        } else if (z2 || z3) {
            i = MAIN_COLOR_DARK;
        } else {
            i = -16777216;
        }
        float[] fArr = new float[3];
        com.android.internal.graphics.ColorUtils.colorToHSL(i, fArr);
        gradientColors.setMainColor(i);
        gradientColors.setSecondaryColor(i);
        gradientColors.setSupportsDarkText(z);
        gradientColors.setColorPalette(getColorPalette(findTonalPalette(fArr[0], fArr[1])));
    }

    private int getColorInt(int i, float[] fArr, float[] fArr2, float[] fArr3) {
        this.mTmpHSL[0] = fract(fArr[i]) * 360.0f;
        this.mTmpHSL[1] = fArr2[i];
        this.mTmpHSL[2] = fArr3[i];
        return com.android.internal.graphics.ColorUtils.HSLToColor(this.mTmpHSL);
    }

    private int[] getColorPalette(float[] fArr, float[] fArr2, float[] fArr3) {
        int length = fArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = getColorInt(i, fArr, fArr2, fArr3);
        }
        return iArr;
    }

    private int[] getColorPalette(com.android.internal.colorextraction.types.Tonal.TonalPalette tonalPalette) {
        return getColorPalette(tonalPalette.h, tonalPalette.s, tonalPalette.l);
    }

    private static float[] fit(float[] fArr, float f, int i, float f2, float f3) {
        float[] fArr2 = new float[fArr.length];
        float f4 = f - fArr[i];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr2[i2] = android.util.MathUtils.constrain(fArr[i2] + f4, f2, f3);
        }
        return fArr2;
    }

    private static int bestFit(com.android.internal.colorextraction.types.Tonal.TonalPalette tonalPalette, float f, float f2, float f3) {
        int i = -1;
        float f4 = Float.POSITIVE_INFINITY;
        for (int i2 = 0; i2 < tonalPalette.h.length; i2++) {
            float abs = (java.lang.Math.abs(f - tonalPalette.h[i2]) * 1.0f) + (java.lang.Math.abs(f2 - tonalPalette.s[i2]) * 1.0f) + (java.lang.Math.abs(f3 - tonalPalette.l[i2]) * FIT_WEIGHT_L);
            if (abs < f4) {
                i = i2;
                f4 = abs;
            }
        }
        return i;
    }

    private com.android.internal.colorextraction.types.Tonal.TonalPalette findTonalPalette(float f, float f2) {
        if (f2 < 0.05f) {
            return this.mGreyPalette;
        }
        int size = this.mTonalPalettes.size();
        com.android.internal.colorextraction.types.Tonal.TonalPalette tonalPalette = null;
        float f3 = Float.POSITIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            com.android.internal.colorextraction.types.Tonal.TonalPalette tonalPalette2 = this.mTonalPalettes.get(i);
            if ((f < tonalPalette2.minHue || f > tonalPalette2.maxHue) && ((tonalPalette2.maxHue <= 1.0f || f < 0.0f || f > fract(tonalPalette2.maxHue)) && (tonalPalette2.minHue >= 0.0f || f < fract(tonalPalette2.minHue) || f > 1.0f))) {
                if (f <= tonalPalette2.minHue && tonalPalette2.minHue - f < f3) {
                    f3 = tonalPalette2.minHue - f;
                    tonalPalette = tonalPalette2;
                } else if (f >= tonalPalette2.maxHue && f - tonalPalette2.maxHue < f3) {
                    f3 = f - tonalPalette2.maxHue;
                    tonalPalette = tonalPalette2;
                } else if (tonalPalette2.maxHue > 1.0f && f >= fract(tonalPalette2.maxHue) && f - fract(tonalPalette2.maxHue) < f3) {
                    f3 = f - fract(tonalPalette2.maxHue);
                    tonalPalette = tonalPalette2;
                } else if (tonalPalette2.minHue < 0.0f && f <= fract(tonalPalette2.minHue) && fract(tonalPalette2.minHue) - f < f3) {
                    f3 = fract(tonalPalette2.minHue) - f;
                    tonalPalette = tonalPalette2;
                }
            } else {
                return tonalPalette2;
            }
        }
        return tonalPalette;
    }

    private static float fract(float f) {
        return f - ((float) java.lang.Math.floor(f));
    }

    public static class TonalPalette {
        public final float[] h;
        public final float[] l;
        public final float maxHue;
        public final float minHue;
        public final float[] s;

        TonalPalette(float[] fArr, float[] fArr2, float[] fArr3) {
            if (fArr.length != fArr2.length || fArr2.length != fArr3.length) {
                throw new java.lang.IllegalArgumentException("All arrays should have the same size. h: " + java.util.Arrays.toString(fArr) + " s: " + java.util.Arrays.toString(fArr2) + " l: " + java.util.Arrays.toString(fArr3));
            }
            this.h = fArr;
            this.s = fArr2;
            this.l = fArr3;
            float f = Float.POSITIVE_INFINITY;
            float f2 = Float.NEGATIVE_INFINITY;
            for (float f3 : fArr) {
                f = java.lang.Math.min(f3, f);
                f2 = java.lang.Math.max(f3, f2);
            }
            this.minHue = f;
            this.maxHue = f2;
        }
    }

    public static class ColorRange {
        private android.util.Range<java.lang.Float> mHue;
        private android.util.Range<java.lang.Float> mLightness;
        private android.util.Range<java.lang.Float> mSaturation;

        public ColorRange(android.util.Range<java.lang.Float> range, android.util.Range<java.lang.Float> range2, android.util.Range<java.lang.Float> range3) {
            this.mHue = range;
            this.mSaturation = range2;
            this.mLightness = range3;
        }

        public boolean containsColor(float f, float f2, float f3) {
            return this.mHue.contains((android.util.Range<java.lang.Float>) java.lang.Float.valueOf(f)) && this.mSaturation.contains((android.util.Range<java.lang.Float>) java.lang.Float.valueOf(f2)) && this.mLightness.contains((android.util.Range<java.lang.Float>) java.lang.Float.valueOf(f3));
        }

        public float[] getCenter() {
            return new float[]{this.mHue.getLower().floatValue() + ((this.mHue.getUpper().floatValue() - this.mHue.getLower().floatValue()) / 2.0f), this.mSaturation.getLower().floatValue() + ((this.mSaturation.getUpper().floatValue() - this.mSaturation.getLower().floatValue()) / 2.0f), this.mLightness.getLower().floatValue() + ((this.mLightness.getUpper().floatValue() - this.mLightness.getLower().floatValue()) / 2.0f)};
        }

        public java.lang.String toString() {
            return java.lang.String.format("H: %s, S: %s, L %s", this.mHue, this.mSaturation, this.mLightness);
        }
    }

    public static class ConfigParser {
        private final java.util.ArrayList<com.android.internal.colorextraction.types.Tonal.TonalPalette> mTonalPalettes = new java.util.ArrayList<>();

        public ConfigParser(android.content.Context context) {
            try {
                android.content.res.XmlResourceParser xml = context.getResources().getXml(com.android.internal.R.xml.color_extraction);
                for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                    if (eventType != 0 && eventType != 3) {
                        if (eventType == 2) {
                            if (xml.getName().equals("palettes")) {
                                parsePalettes(xml);
                            }
                        } else {
                            throw new org.xmlpull.v1.XmlPullParserException("Invalid XML event " + eventType + " - " + xml.getName(), xml, null);
                        }
                    }
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public java.util.ArrayList<com.android.internal.colorextraction.types.Tonal.TonalPalette> getTonalPalettes() {
            return this.mTonalPalettes;
        }

        private com.android.internal.colorextraction.types.Tonal.ColorRange readRange(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            xmlPullParser.require(2, null, "range");
            float[] readFloatArray = readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] readFloatArray2 = readFloatArray(xmlPullParser.getAttributeValue(null, android.app.blob.XmlTags.TAG_SESSION));
            float[] readFloatArray3 = readFloatArray(xmlPullParser.getAttributeValue(null, android.app.blob.XmlTags.TAG_LEASEE));
            if (readFloatArray == null || readFloatArray2 == null || readFloatArray3 == null) {
                throw new org.xmlpull.v1.XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
            }
            return new com.android.internal.colorextraction.types.Tonal.ColorRange(new android.util.Range(java.lang.Float.valueOf(readFloatArray[0]), java.lang.Float.valueOf(readFloatArray[1])), new android.util.Range(java.lang.Float.valueOf(readFloatArray2[0]), java.lang.Float.valueOf(readFloatArray2[1])), new android.util.Range(java.lang.Float.valueOf(readFloatArray3[0]), java.lang.Float.valueOf(readFloatArray3[1])));
        }

        private void parsePalettes(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            xmlPullParser.require(2, null, "palettes");
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("palette")) {
                        this.mTonalPalettes.add(readPalette(xmlPullParser));
                        xmlPullParser.next();
                    } else {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid tag: " + name);
                    }
                }
            }
        }

        private com.android.internal.colorextraction.types.Tonal.TonalPalette readPalette(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            xmlPullParser.require(2, null, "palette");
            float[] readFloatArray = readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] readFloatArray2 = readFloatArray(xmlPullParser.getAttributeValue(null, android.app.blob.XmlTags.TAG_SESSION));
            float[] readFloatArray3 = readFloatArray(xmlPullParser.getAttributeValue(null, android.app.blob.XmlTags.TAG_LEASEE));
            if (readFloatArray == null || readFloatArray2 == null || readFloatArray3 == null) {
                throw new org.xmlpull.v1.XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
            }
            return new com.android.internal.colorextraction.types.Tonal.TonalPalette(readFloatArray, readFloatArray2, readFloatArray3);
        }

        private float[] readFloatArray(java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String[] split = str.replaceAll(" ", "").replaceAll("\n", "").split(",");
            float[] fArr = new float[split.length];
            for (int i = 0; i < split.length; i++) {
                fArr[i] = java.lang.Float.parseFloat(split[i]);
            }
            return fArr;
        }
    }
}
