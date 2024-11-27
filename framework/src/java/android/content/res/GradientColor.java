package android.content.res;

/* loaded from: classes.dex */
public class GradientColor extends android.content.res.ComplexColor {
    private static final boolean DBG_GRADIENT = false;
    private static final java.lang.String TAG = "GradientColor";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private int mCenterColor;
    private float mCenterX;
    private float mCenterY;
    private int mChangingConfigurations;
    private int mDefaultColor;
    private int mEndColor;
    private float mEndX;
    private float mEndY;
    private android.content.res.GradientColor.GradientColorFactory mFactory;
    private float mGradientRadius;
    private int mGradientType;
    private boolean mHasCenterColor;
    private int[] mItemColors;
    private float[] mItemOffsets;
    private int[][] mItemsThemeAttrs;
    private android.graphics.Shader mShader;
    private int mStartColor;
    private float mStartX;
    private float mStartY;
    private int[] mThemeAttrs;
    private int mTileMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface GradientTileMode {
    }

    private GradientColor() {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
    }

    private GradientColor(android.content.res.GradientColor gradientColor) {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
        if (gradientColor != null) {
            this.mChangingConfigurations = gradientColor.mChangingConfigurations;
            this.mDefaultColor = gradientColor.mDefaultColor;
            this.mShader = gradientColor.mShader;
            this.mGradientType = gradientColor.mGradientType;
            this.mCenterX = gradientColor.mCenterX;
            this.mCenterY = gradientColor.mCenterY;
            this.mStartX = gradientColor.mStartX;
            this.mStartY = gradientColor.mStartY;
            this.mEndX = gradientColor.mEndX;
            this.mEndY = gradientColor.mEndY;
            this.mStartColor = gradientColor.mStartColor;
            this.mCenterColor = gradientColor.mCenterColor;
            this.mEndColor = gradientColor.mEndColor;
            this.mHasCenterColor = gradientColor.mHasCenterColor;
            this.mGradientRadius = gradientColor.mGradientRadius;
            this.mTileMode = gradientColor.mTileMode;
            if (gradientColor.mItemColors != null) {
                this.mItemColors = (int[]) gradientColor.mItemColors.clone();
            }
            if (gradientColor.mItemOffsets != null) {
                this.mItemOffsets = (float[]) gradientColor.mItemOffsets.clone();
            }
            if (gradientColor.mThemeAttrs != null) {
                this.mThemeAttrs = (int[]) gradientColor.mThemeAttrs.clone();
            }
            if (gradientColor.mItemsThemeAttrs != null) {
                this.mItemsThemeAttrs = (int[][]) gradientColor.mItemsThemeAttrs.clone();
            }
        }
    }

    private static android.graphics.Shader.TileMode parseTileMode(int i) {
        switch (i) {
            case 0:
                return android.graphics.Shader.TileMode.CLAMP;
            case 1:
                return android.graphics.Shader.TileMode.REPEAT;
            case 2:
                return android.graphics.Shader.TileMode.MIRROR;
            default:
                return android.graphics.Shader.TileMode.CLAMP;
        }
    }

    private void updateRootElementState(android.content.res.TypedArray typedArray) {
        this.mThemeAttrs = typedArray.extractThemeAttrs();
        this.mStartX = typedArray.getFloat(8, this.mStartX);
        this.mStartY = typedArray.getFloat(9, this.mStartY);
        this.mEndX = typedArray.getFloat(10, this.mEndX);
        this.mEndY = typedArray.getFloat(11, this.mEndY);
        this.mCenterX = typedArray.getFloat(3, this.mCenterX);
        this.mCenterY = typedArray.getFloat(4, this.mCenterY);
        this.mGradientType = typedArray.getInt(2, this.mGradientType);
        this.mStartColor = typedArray.getColor(0, this.mStartColor);
        this.mHasCenterColor |= typedArray.hasValue(7);
        this.mCenterColor = typedArray.getColor(7, this.mCenterColor);
        this.mEndColor = typedArray.getColor(1, this.mEndColor);
        this.mTileMode = typedArray.getInt(6, this.mTileMode);
        this.mGradientRadius = typedArray.getFloat(5, this.mGradientRadius);
    }

    private void validateXmlContent() throws org.xmlpull.v1.XmlPullParserException {
        if (this.mGradientRadius <= 0.0f && this.mGradientType == 1) {
            throw new org.xmlpull.v1.XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }
    }

    public android.graphics.Shader getShader() {
        return this.mShader;
    }

    public static android.content.res.GradientColor createFromXml(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlResourceParser);
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(resources, xmlResourceParser, asAttributeSet, theme);
    }

    static android.content.res.GradientColor createFromXmlInner(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String name = xmlPullParser.getName();
        if (!name.equals("gradient")) {
            throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid gradient color tag " + name);
        }
        android.content.res.GradientColor gradientColor = new android.content.res.GradientColor();
        gradientColor.inflate(resources, xmlPullParser, attributeSet, theme);
        return gradientColor;
    }

    private void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = android.content.res.Resources.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientColor);
        updateRootElementState(obtainAttributes);
        this.mChangingConfigurations |= obtainAttributes.getChangingConfigurations();
        obtainAttributes.recycle();
        validateXmlContent();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onColorsChange();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a7, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException(r19.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth;
        int i = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        float[] fArr = new float[20];
        int[] iArr = new int[20];
        int[][] iArr2 = new int[20][];
        int i2 = 0;
        boolean z = false;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next != 2 || depth > depth2 || !xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                i = 1;
            } else {
                android.content.res.TypedArray obtainAttributes = android.content.res.Resources.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientColorItem);
                boolean hasValue = obtainAttributes.hasValue(0);
                boolean hasValue2 = obtainAttributes.hasValue(i);
                if (!hasValue || !hasValue2) {
                    break;
                }
                int[] extractThemeAttrs = obtainAttributes.extractThemeAttrs();
                int color = obtainAttributes.getColor(0, 0);
                float f = obtainAttributes.getFloat(i, 0.0f);
                this.mChangingConfigurations |= obtainAttributes.getChangingConfigurations();
                obtainAttributes.recycle();
                if (extractThemeAttrs != null) {
                    z = true;
                }
                iArr = com.android.internal.util.GrowingArrayUtils.append(iArr, i2, color);
                fArr = com.android.internal.util.GrowingArrayUtils.append(fArr, i2, f);
                iArr2 = (int[][]) com.android.internal.util.GrowingArrayUtils.append(iArr2, i2, extractThemeAttrs);
                i2++;
                i = 1;
            }
        }
        if (i2 > 0) {
            if (z) {
                this.mItemsThemeAttrs = new int[i2][];
                java.lang.System.arraycopy(iArr2, 0, this.mItemsThemeAttrs, 0, i2);
            } else {
                this.mItemsThemeAttrs = null;
            }
            this.mItemColors = new int[i2];
            this.mItemOffsets = new float[i2];
            java.lang.System.arraycopy(iArr, 0, this.mItemColors, 0, i2);
            java.lang.System.arraycopy(fArr, 0, this.mItemOffsets, 0, i2);
        }
    }

    private void applyItemsAttrsTheme(android.content.res.Resources.Theme theme) {
        if (this.mItemsThemeAttrs == null) {
            return;
        }
        int[][] iArr = this.mItemsThemeAttrs;
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (iArr[i] != null) {
                android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(iArr[i], com.android.internal.R.styleable.GradientColorItem);
                iArr[i] = resolveAttributes.extractThemeAttrs(iArr[i]);
                if (iArr[i] != null) {
                    z = true;
                }
                this.mItemColors[i] = resolveAttributes.getColor(0, this.mItemColors[i]);
                this.mItemOffsets[i] = resolveAttributes.getFloat(1, this.mItemOffsets[i]);
                this.mChangingConfigurations |= resolveAttributes.getChangingConfigurations();
                resolveAttributes.recycle();
            }
        }
        if (!z) {
            this.mItemsThemeAttrs = null;
        }
    }

    private void onColorsChange() {
        int[] iArr;
        float[] fArr;
        if (this.mItemColors != null) {
            int length = this.mItemColors.length;
            iArr = new int[length];
            fArr = new float[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = this.mItemColors[i];
                fArr[i] = this.mItemOffsets[i];
            }
        } else if (this.mHasCenterColor) {
            iArr = new int[]{this.mStartColor, this.mCenterColor, this.mEndColor};
            fArr = new float[]{0.0f, 0.5f, 1.0f};
        } else {
            iArr = new int[]{this.mStartColor, this.mEndColor};
            fArr = null;
        }
        if (iArr.length < 2) {
            android.util.Log.w(TAG, "<gradient> tag requires 2 color values specified!" + iArr.length + " " + java.util.Arrays.toString(iArr));
        }
        if (this.mGradientType == 0) {
            this.mShader = new android.graphics.LinearGradient(this.mStartX, this.mStartY, this.mEndX, this.mEndY, iArr, fArr, parseTileMode(this.mTileMode));
        } else if (this.mGradientType == 1) {
            this.mShader = new android.graphics.RadialGradient(this.mCenterX, this.mCenterY, this.mGradientRadius, iArr, fArr, parseTileMode(this.mTileMode));
        } else {
            this.mShader = new android.graphics.SweepGradient(this.mCenterX, this.mCenterY, iArr, fArr);
        }
        this.mDefaultColor = iArr[0];
    }

    @Override // android.content.res.ComplexColor
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    @Override // android.content.res.ComplexColor
    public android.content.res.ConstantState<android.content.res.ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new android.content.res.GradientColor.GradientColorFactory(this);
        }
        return this.mFactory;
    }

    private static class GradientColorFactory extends android.content.res.ConstantState<android.content.res.ComplexColor> {
        private final android.content.res.GradientColor mSrc;

        public GradientColorFactory(android.content.res.GradientColor gradientColor) {
            this.mSrc = gradientColor;
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public android.content.res.ComplexColor newInstance2() {
            return this.mSrc;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public android.content.res.ComplexColor newInstance2(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Override // android.content.res.ComplexColor
    public android.content.res.GradientColor obtainForTheme(android.content.res.Resources.Theme theme) {
        if (theme == null || !canApplyTheme()) {
            return this;
        }
        android.content.res.GradientColor gradientColor = new android.content.res.GradientColor(this);
        gradientColor.applyTheme(theme);
        return gradientColor;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    private void applyTheme(android.content.res.Resources.Theme theme) {
        if (this.mThemeAttrs != null) {
            applyRootAttrsTheme(theme);
        }
        if (this.mItemsThemeAttrs != null) {
            applyItemsAttrsTheme(theme);
        }
        onColorsChange();
    }

    private void applyRootAttrsTheme(android.content.res.Resources.Theme theme) {
        android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(this.mThemeAttrs, com.android.internal.R.styleable.GradientColor);
        this.mThemeAttrs = resolveAttributes.extractThemeAttrs(this.mThemeAttrs);
        updateRootElementState(resolveAttributes);
        this.mChangingConfigurations |= resolveAttributes.getChangingConfigurations();
        resolveAttributes.recycle();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return (this.mThemeAttrs == null && this.mItemsThemeAttrs == null) ? false : true;
    }
}
