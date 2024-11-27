package android.app;

/* loaded from: classes.dex */
public final class WallpaperColors implements android.os.Parcelable {
    private static final float DARK_PIXEL_CONTRAST = 5.5f;
    private static final float DARK_THEME_MEAN_LUMINANCE = 0.3f;
    private static final boolean DEBUG_DARK_PIXELS = false;
    public static final int HINT_FROM_BITMAP = 4;
    public static final int HINT_SUPPORTS_DARK_TEXT = 1;
    public static final int HINT_SUPPORTS_DARK_THEME = 2;
    private static final int MAX_BITMAP_SIZE = 112;
    private static final int MAX_WALLPAPER_EXTRACTION_AREA = 12544;
    private static final float MIN_COLOR_OCCURRENCE = 0.05f;
    private final java.util.Map<java.lang.Integer, java.lang.Integer> mAllColors;
    private int mColorHints;
    private final java.util.List<android.graphics.Color> mMainColors;
    private static final float BRIGHT_IMAGE_MEAN_LUMINANCE = android.os.SystemProperties.getInt("persist.wallpapercolors.threshold", 70) / 100.0f;
    private static final float MAX_DARK_AREA = android.os.SystemProperties.getInt("persist.wallpapercolors.max_dark_area", 5) / 100.0f;
    public static final android.os.Parcelable.Creator<android.app.WallpaperColors> CREATOR = new android.os.Parcelable.Creator<android.app.WallpaperColors>() { // from class: android.app.WallpaperColors.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WallpaperColors createFromParcel(android.os.Parcel parcel) {
            return new android.app.WallpaperColors(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WallpaperColors[] newArray(int i) {
            return new android.app.WallpaperColors[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ColorsHints {
    }

    public WallpaperColors(android.os.Parcel parcel) {
        this.mMainColors = new java.util.ArrayList();
        this.mAllColors = new java.util.HashMap();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mMainColors.add(android.graphics.Color.valueOf(parcel.readInt()));
        }
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mAllColors.put(java.lang.Integer.valueOf(parcel.readInt()), java.lang.Integer.valueOf(parcel.readInt()));
        }
        this.mColorHints = parcel.readInt();
    }

    public static android.app.WallpaperColors fromDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            throw new java.lang.IllegalArgumentException("Drawable cannot be null");
        }
        android.os.Trace.beginSection("WallpaperColors#fromDrawable");
        android.graphics.Rect copyBounds = drawable.copyBounds();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            intrinsicWidth = 112;
            intrinsicHeight = 112;
        }
        android.util.Size calculateOptimalSize = calculateOptimalSize(intrinsicWidth, intrinsicHeight);
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(calculateOptimalSize.getWidth(), calculateOptimalSize.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        drawable.setBounds(0, 0, createBitmap.getWidth(), createBitmap.getHeight());
        drawable.draw(canvas);
        android.app.WallpaperColors fromBitmap = fromBitmap(createBitmap);
        createBitmap.recycle();
        drawable.setBounds(copyBounds);
        android.os.Trace.endSection();
        return fromBitmap;
    }

    public static android.app.WallpaperColors fromBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Bitmap can't be null");
        }
        return fromBitmap(bitmap, 0.0f);
    }

    public static android.app.WallpaperColors fromBitmap(android.graphics.Bitmap bitmap, float f) {
        boolean z;
        com.android.internal.graphics.palette.Palette generate;
        java.util.Objects.requireNonNull(bitmap, "Bitmap can't be null");
        android.os.Trace.beginSection("WallpaperColors#fromBitmap");
        int width = bitmap.getWidth() * bitmap.getHeight();
        if (width <= MAX_WALLPAPER_EXTRACTION_AREA) {
            z = false;
        } else {
            android.util.Size calculateOptimalSize = calculateOptimalSize(bitmap.getWidth(), bitmap.getHeight());
            bitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, calculateOptimalSize.getWidth(), calculateOptimalSize.getHeight(), false);
            z = true;
        }
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            generate = com.android.internal.graphics.palette.Palette.from(bitmap, new com.android.internal.graphics.palette.VariationalKMeansQuantizer()).maximumColorCount(5).resizeBitmapArea(MAX_WALLPAPER_EXTRACTION_AREA).generate();
        } else {
            generate = com.android.internal.graphics.palette.Palette.from(bitmap, new com.android.internal.graphics.palette.CelebiQuantizer()).maximumColorCount(java.lang.Math.max(5, java.lang.Math.min(128, width / 16))).resizeBitmapArea(MAX_WALLPAPER_EXTRACTION_AREA).generate();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(generate.getSwatches());
        arrayList.sort(new java.util.Comparator() { // from class: android.app.WallpaperColors$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                return android.app.WallpaperColors.lambda$fromBitmap$0((com.android.internal.graphics.palette.Palette.Swatch) obj, (com.android.internal.graphics.palette.Palette.Swatch) obj2);
            }
        });
        int size = arrayList.size();
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i = 0; i < size; i++) {
            com.android.internal.graphics.palette.Palette.Swatch swatch = (com.android.internal.graphics.palette.Palette.Swatch) arrayList.get(i);
            hashMap.put(java.lang.Integer.valueOf(swatch.getInt()), java.lang.Integer.valueOf(swatch.getPopulation()));
        }
        int calculateDarkHints = calculateDarkHints(bitmap, f);
        if (z) {
            bitmap.recycle();
        }
        android.os.Trace.endSection();
        return new android.app.WallpaperColors(hashMap, calculateDarkHints | 4);
    }

    static /* synthetic */ int lambda$fromBitmap$0(com.android.internal.graphics.palette.Palette.Swatch swatch, com.android.internal.graphics.palette.Palette.Swatch swatch2) {
        return swatch2.getPopulation() - swatch.getPopulation();
    }

    public WallpaperColors(android.graphics.Color color, android.graphics.Color color2, android.graphics.Color color3) {
        this(color, color2, color3, 0);
        float[] fArr = new float[3];
        com.android.internal.graphics.ColorUtils.colorToHSL(color.toArgb(), fArr);
        if (fArr[2] < DARK_THEME_MEAN_LUMINANCE) {
            this.mColorHints = 2 | this.mColorHints;
        }
    }

    public WallpaperColors(android.graphics.Color color, android.graphics.Color color2, android.graphics.Color color3, int i) {
        if (color == null) {
            throw new java.lang.IllegalArgumentException("Primary color should never be null.");
        }
        this.mMainColors = new java.util.ArrayList(3);
        this.mAllColors = new java.util.HashMap();
        this.mMainColors.add(color);
        this.mAllColors.put(java.lang.Integer.valueOf(color.toArgb()), 0);
        if (color2 != null) {
            this.mMainColors.add(color2);
            this.mAllColors.put(java.lang.Integer.valueOf(color2.toArgb()), 0);
        }
        if (color3 != null) {
            if (color2 == null) {
                throw new java.lang.IllegalArgumentException("tertiaryColor can't be specified when secondaryColor is null");
            }
            this.mMainColors.add(color3);
            this.mAllColors.put(java.lang.Integer.valueOf(color3.toArgb()), 0);
        }
        this.mColorHints = i;
    }

    public WallpaperColors(java.util.Map<java.lang.Integer, java.lang.Integer> map, int i) {
        this.mAllColors = map;
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator<java.lang.Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            hashMap.put(java.lang.Integer.valueOf(intValue), com.android.internal.graphics.cam.Cam.fromInt(intValue));
        }
        java.util.Map<java.lang.Integer, java.lang.Double> colorToHueProportion = colorToHueProportion(map.keySet(), hashMap, hueProportions(hashMap, map));
        java.util.HashMap hashMap2 = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> entry : colorToHueProportion.entrySet()) {
            int intValue2 = entry.getKey().intValue();
            hashMap2.put(java.lang.Integer.valueOf(intValue2), java.lang.Double.valueOf(score((com.android.internal.graphics.cam.Cam) hashMap.get(java.lang.Integer.valueOf(intValue2)), entry.getValue().doubleValue())));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(hashMap2.entrySet());
        arrayList.sort(new java.util.Comparator() { // from class: android.app.WallpaperColors$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compareTo;
                compareTo = ((java.lang.Double) ((java.util.Map.Entry) obj2).getValue()).compareTo((java.lang.Double) ((java.util.Map.Entry) obj).getValue());
                return compareTo;
            }
        });
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.add((java.lang.Integer) ((java.util.Map.Entry) it2.next()).getKey());
        }
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        java.util.Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            int intValue3 = ((java.lang.Integer) it3.next()).intValue();
            com.android.internal.graphics.cam.Cam cam = (com.android.internal.graphics.cam.Cam) hashMap.get(java.lang.Integer.valueOf(intValue3));
            java.util.Iterator it4 = arrayList3.iterator();
            while (true) {
                if (it4.hasNext()) {
                    if (hueDiff(cam, (com.android.internal.graphics.cam.Cam) hashMap.get(java.lang.Integer.valueOf(((java.lang.Integer) it4.next()).intValue()))) < 15.0d) {
                        break;
                    }
                } else {
                    arrayList3.add(java.lang.Integer.valueOf(intValue3));
                    break;
                }
            }
        }
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        java.util.Iterator it5 = arrayList3.iterator();
        while (it5.hasNext()) {
            arrayList4.add(android.graphics.Color.valueOf(((java.lang.Integer) it5.next()).intValue()));
        }
        this.mMainColors = arrayList4;
        this.mColorHints = i;
    }

    private static double hueDiff(com.android.internal.graphics.cam.Cam cam, com.android.internal.graphics.cam.Cam cam2) {
        return 180.0f - java.lang.Math.abs(java.lang.Math.abs(cam.getHue() - cam2.getHue()) - 180.0f);
    }

    private static double score(com.android.internal.graphics.cam.Cam cam, double d) {
        return cam.getChroma() + (d * 100.0d);
    }

    private static java.util.Map<java.lang.Integer, java.lang.Double> colorToHueProportion(java.util.Set<java.lang.Integer> set, java.util.Map<java.lang.Integer, com.android.internal.graphics.cam.Cam> map, double[] dArr) {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            int wrapDegrees = wrapDegrees(java.lang.Math.round(map.get(java.lang.Integer.valueOf(intValue)).getHue()));
            double d = 0.0d;
            for (int i = wrapDegrees - 15; i < wrapDegrees + 15; i++) {
                d += dArr[wrapDegrees(i)];
            }
            hashMap.put(java.lang.Integer.valueOf(intValue), java.lang.Double.valueOf(d));
        }
        return hashMap;
    }

    private static int wrapDegrees(int i) {
        if (i < 0) {
            return (i % 360) + 360;
        }
        if (i >= 360) {
            return i % 360;
        }
        return i;
    }

    private static double[] hueProportions(java.util.Map<java.lang.Integer, com.android.internal.graphics.cam.Cam> map, java.util.Map<java.lang.Integer, java.lang.Integer> map2) {
        double[] dArr = new double[360];
        double d = 0.0d;
        while (map2.entrySet().iterator().hasNext()) {
            d += r1.next().getValue().intValue();
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> it = map2.entrySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().getKey().intValue();
            int intValue2 = map2.get(java.lang.Integer.valueOf(intValue)).intValue();
            int wrapDegrees = wrapDegrees(java.lang.Math.round(map.get(java.lang.Integer.valueOf(intValue)).getHue()));
            dArr[wrapDegrees] = dArr[wrapDegrees] + (intValue2 / d);
        }
        return dArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        java.util.List<android.graphics.Color> mainColors = getMainColors();
        int size = mainColors.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(mainColors.get(i2).toArgb());
        }
        parcel.writeInt(this.mAllColors.size());
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : this.mAllColors.entrySet()) {
            if (entry.getKey() != null) {
                parcel.writeInt(entry.getKey().intValue());
                java.lang.Integer value = entry.getValue();
                parcel.writeInt(value != null ? value.intValue() : 0);
            }
        }
        parcel.writeInt(this.mColorHints);
    }

    public android.graphics.Color getPrimaryColor() {
        return this.mMainColors.get(0);
    }

    public android.graphics.Color getSecondaryColor() {
        if (this.mMainColors.size() < 2) {
            return null;
        }
        return this.mMainColors.get(1);
    }

    public android.graphics.Color getTertiaryColor() {
        if (this.mMainColors.size() < 3) {
            return null;
        }
        return this.mMainColors.get(2);
    }

    public java.util.List<android.graphics.Color> getMainColors() {
        return java.util.Collections.unmodifiableList(this.mMainColors);
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getAllColors() {
        return java.util.Collections.unmodifiableMap(this.mAllColors);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.WallpaperColors wallpaperColors = (android.app.WallpaperColors) obj;
        return this.mMainColors.equals(wallpaperColors.mMainColors) && this.mAllColors.equals(wallpaperColors.mAllColors) && this.mColorHints == wallpaperColors.mColorHints;
    }

    public int hashCode() {
        return (this.mMainColors.hashCode() * 31 * this.mAllColors.hashCode()) + this.mColorHints;
    }

    public int getColorHints() {
        return this.mColorHints;
    }

    private static int calculateDarkHints(android.graphics.Bitmap bitmap, float f) {
        int i = 0;
        if (bitmap == null) {
            return 0;
        }
        android.os.Trace.beginSection("WallpaperColors#calculateDarkHints");
        float saturate = android.util.MathUtils.saturate(f);
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        int i2 = (int) (width * MAX_DARK_AREA);
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int alphaComponent = com.android.internal.graphics.ColorUtils.setAlphaComponent(-16777216, (int) (saturate * 255.0f));
        float[] fArr = new float[3];
        double d = 0.0d;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= width) {
                break;
            }
            int i5 = iArr[i3];
            com.android.internal.graphics.ColorUtils.colorToHSL(i5, fArr);
            int alpha = android.graphics.Color.alpha(i5);
            double calculateLuminance = com.android.internal.graphics.ColorUtils.calculateLuminance(com.android.internal.graphics.ColorUtils.compositeColors(alphaComponent, i5));
            if (!(com.android.internal.util.ContrastColorUtil.calculateContrast(i5, -16777216) > 5.5d) && alpha != 0) {
                i4++;
            }
            d += calculateLuminance;
            i3++;
        }
        double d2 = d / width;
        if (d2 > BRIGHT_IMAGE_MEAN_LUMINANCE && i4 <= i2) {
            i = 1;
        }
        if (d2 < 0.30000001192092896d) {
            i |= 2;
        }
        android.os.Trace.endSection();
        return i;
    }

    private static android.util.Size calculateOptimalSize(int i, int i2) {
        double d;
        int i3 = i * i2;
        if (i3 <= MAX_WALLPAPER_EXTRACTION_AREA) {
            d = 1.0d;
        } else {
            d = java.lang.Math.sqrt(12544.0d / i3);
        }
        int i4 = (int) (i * d);
        int i5 = (int) (i2 * d);
        if (i4 == 0) {
            i4 = 1;
        }
        if (i5 == 0) {
            i5 = 1;
        }
        return new android.util.Size(i4, i5);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.mMainColors.size(); i++) {
            sb.append(java.lang.Integer.toHexString(this.mMainColors.get(i).toArgb())).append(" ");
        }
        return "[WallpaperColors: " + sb.toString() + "h: " + this.mColorHints + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
