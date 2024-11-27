package android.content.res;

/* loaded from: classes.dex */
public class ColorStateList extends android.content.res.ComplexColor implements android.os.Parcelable {
    private static final int DEFAULT_COLOR = -65536;
    private static final java.lang.String TAG = "ColorStateList";
    private int mChangingConfigurations;
    private int[] mColors;
    private int mDefaultColor;
    private android.content.res.ColorStateList.ColorStateListFactory mFactory;
    private boolean mIsOpaque;
    private int[][] mStateSpecs;
    private int[][] mThemeAttrs;
    private static final int[][] EMPTY = {new int[0]};
    private static final android.util.SparseArray<java.lang.ref.WeakReference<android.content.res.ColorStateList>> sCache = new android.util.SparseArray<>();
    public static final android.os.Parcelable.Creator<android.content.res.ColorStateList> CREATOR = new android.os.Parcelable.Creator<android.content.res.ColorStateList>() { // from class: android.content.res.ColorStateList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.ColorStateList[] newArray(int i) {
            return new android.content.res.ColorStateList[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.ColorStateList createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int[][] iArr = new int[readInt][];
            for (int i = 0; i < readInt; i++) {
                iArr[i] = parcel.createIntArray();
            }
            return new android.content.res.ColorStateList(iArr, parcel.createIntArray());
        }
    };

    private ColorStateList() {
    }

    public ColorStateList(int[][] iArr, int[] iArr2) {
        this.mStateSpecs = iArr;
        this.mColors = iArr2;
        onColorsChanged();
    }

    public static android.content.res.ColorStateList valueOf(int i) {
        synchronized (sCache) {
            int indexOfKey = sCache.indexOfKey(i);
            if (indexOfKey >= 0) {
                android.content.res.ColorStateList colorStateList = sCache.valueAt(indexOfKey).get();
                if (colorStateList != null) {
                    return colorStateList;
                }
                sCache.removeAt(indexOfKey);
            }
            for (int size = sCache.size() - 1; size >= 0; size--) {
                if (sCache.valueAt(size).refersTo(null)) {
                    sCache.removeAt(size);
                }
            }
            android.content.res.ColorStateList colorStateList2 = new android.content.res.ColorStateList(EMPTY, new int[]{i});
            sCache.put(i, new java.lang.ref.WeakReference<>(colorStateList2));
            return colorStateList2;
        }
    }

    private ColorStateList(android.content.res.ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mChangingConfigurations = colorStateList.mChangingConfigurations;
            this.mStateSpecs = colorStateList.mStateSpecs;
            this.mDefaultColor = colorStateList.mDefaultColor;
            this.mIsOpaque = colorStateList.mIsOpaque;
            this.mThemeAttrs = (int[][]) colorStateList.mThemeAttrs.clone();
            this.mColors = (int[]) colorStateList.mColors.clone();
        }
    }

    @java.lang.Deprecated
    public static android.content.res.ColorStateList createFromXml(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createFromXml(resources, xmlPullParser, null);
    }

    public static android.content.res.ColorStateList createFromXml(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
    }

    static android.content.res.ColorStateList createFromXmlInner(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList();
        colorStateList.inflate(resources, xmlPullParser, attributeSet, theme);
        return colorStateList;
    }

    public android.content.res.ColorStateList withAlpha(int i) {
        int length = this.mColors.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = (this.mColors[i2] & 16777215) | (i << 24);
        }
        return new android.content.res.ColorStateList(this.mStateSpecs, iArr);
    }

    public android.content.res.ColorStateList withLStar(float f) {
        int length = this.mColors.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = modulateColor(this.mColors[i], 1.0f, f);
        }
        return new android.content.res.ColorStateList(this.mStateSpecs, iArr);
    }

    private void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z;
        int i = 1;
        int depth = xmlPullParser.getDepth() + 1;
        int[][] iArr = (int[][]) com.android.internal.util.ArrayUtils.newUnpaddedArray(int[].class, 20);
        int[][] iArr2 = new int[iArr.length][];
        int[] iArr3 = new int[iArr.length];
        int i2 = 0;
        int i3 = -65536;
        int i4 = 0;
        boolean z2 = false;
        int i5 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next != i) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next == 3) {
                    z = z2;
                } else if (next != 2 || depth2 > depth || !xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                    depth = depth;
                    z2 = z2;
                    i = 1;
                    i2 = 0;
                } else {
                    android.content.res.TypedArray obtainAttributes = android.content.res.Resources.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.ColorStateListItem);
                    int[] extractThemeAttrs = obtainAttributes.extractThemeAttrs();
                    int color = obtainAttributes.getColor(i2, android.graphics.Color.MAGENTA);
                    int i6 = depth;
                    float f = obtainAttributes.getFloat(1, 1.0f);
                    float f2 = obtainAttributes.getFloat(2, -1.0f);
                    int changingConfigurations = i4 | obtainAttributes.getChangingConfigurations();
                    obtainAttributes.recycle();
                    int attributeCount = attributeSet.getAttributeCount();
                    int[] iArr4 = new int[attributeCount];
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 < attributeCount) {
                        int i9 = attributeCount;
                        int attributeNameResource = attributeSet.getAttributeNameResource(i7);
                        boolean z3 = z2;
                        if (attributeNameResource != 16844359) {
                            switch (attributeNameResource) {
                                case 16843173:
                                case 16843551:
                                    break;
                                default:
                                    int i10 = i8 + 1;
                                    if (!attributeSet.getAttributeBooleanValue(i7, false)) {
                                        attributeNameResource = -attributeNameResource;
                                    }
                                    iArr4[i8] = attributeNameResource;
                                    i8 = i10;
                                    break;
                            }
                        }
                        i7++;
                        attributeCount = i9;
                        z2 = z3;
                    }
                    boolean z4 = z2;
                    int[] trimStateSet = android.util.StateSet.trimStateSet(iArr4, i8);
                    int modulateColor = modulateColor(color, f, f2);
                    if (i5 == 0 || trimStateSet.length == 0) {
                        i3 = modulateColor;
                    }
                    if (extractThemeAttrs == null) {
                        z2 = z4;
                    } else {
                        z2 = true;
                    }
                    iArr3 = com.android.internal.util.GrowingArrayUtils.append(iArr3, i5, modulateColor);
                    iArr2 = (int[][]) com.android.internal.util.GrowingArrayUtils.append(iArr2, i5, extractThemeAttrs);
                    iArr = (int[][]) com.android.internal.util.GrowingArrayUtils.append(iArr, i5, trimStateSet);
                    i5++;
                    i4 = changingConfigurations;
                    depth = i6;
                    i = 1;
                    i2 = 0;
                }
            } else {
                z = z2;
            }
        }
        this.mChangingConfigurations = i4;
        this.mDefaultColor = i3;
        if (z) {
            this.mThemeAttrs = new int[i5][];
            java.lang.System.arraycopy(iArr2, 0, this.mThemeAttrs, 0, i5);
        } else {
            this.mThemeAttrs = null;
        }
        this.mColors = new int[i5];
        this.mStateSpecs = new int[i5][];
        java.lang.System.arraycopy(iArr3, 0, this.mColors, 0, i5);
        java.lang.System.arraycopy(iArr, 0, this.mStateSpecs, 0, i5);
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return this.mThemeAttrs != null;
    }

    private void applyTheme(android.content.res.Resources.Theme theme) {
        float f;
        if (this.mThemeAttrs == null) {
            return;
        }
        int[][] iArr = this.mThemeAttrs;
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (iArr[i] != null) {
                android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(iArr[i], com.android.internal.R.styleable.ColorStateListItem);
                if (iArr[i][0] != 0) {
                    f = android.graphics.Color.alpha(this.mColors[i]) / 255.0f;
                } else {
                    f = 1.0f;
                }
                iArr[i] = resolveAttributes.extractThemeAttrs(iArr[i]);
                if (iArr[i] != null) {
                    z = true;
                }
                this.mColors[i] = modulateColor(resolveAttributes.getColor(0, this.mColors[i]), resolveAttributes.getFloat(1, f), resolveAttributes.getFloat(2, -1.0f));
                this.mChangingConfigurations |= resolveAttributes.getChangingConfigurations();
                resolveAttributes.recycle();
            }
        }
        if (!z) {
            this.mThemeAttrs = null;
        }
        onColorsChanged();
    }

    @Override // android.content.res.ComplexColor
    public android.content.res.ColorStateList obtainForTheme(android.content.res.Resources.Theme theme) {
        if (theme == null || !canApplyTheme()) {
            return this;
        }
        android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList(this);
        colorStateList.applyTheme(theme);
        return colorStateList;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    private int modulateColor(int i, float f, float f2) {
        boolean z = f2 >= 0.0f && f2 <= 100.0f;
        if (f == 1.0f && !z) {
            return i;
        }
        int constrain = android.util.MathUtils.constrain((int) ((android.graphics.Color.alpha(i) * f) + 0.5f), 0, 255);
        if (z) {
            com.android.internal.graphics.cam.Cam colorToCAM = com.android.internal.graphics.ColorUtils.colorToCAM(i);
            i = com.android.internal.graphics.ColorUtils.CAMToColor(colorToCAM.getHue(), colorToCAM.getChroma(), f2);
        }
        return (i & 16777215) | (constrain << 24);
    }

    @Override // android.content.res.ComplexColor
    public boolean isStateful() {
        return this.mStateSpecs.length >= 1 && this.mStateSpecs[0].length > 0;
    }

    public boolean hasFocusStateSpecified() {
        return android.util.StateSet.containsAttribute(this.mStateSpecs, 16842908);
    }

    public boolean isOpaque() {
        return this.mIsOpaque;
    }

    public int getColorForState(int[] iArr, int i) {
        int length = this.mStateSpecs.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (android.util.StateSet.stateSetMatches(this.mStateSpecs[i2], iArr)) {
                return this.mColors[i2];
            }
        }
        return i;
    }

    @Override // android.content.res.ComplexColor
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    public int[][] getStates() {
        return this.mStateSpecs;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public boolean hasState(int i) {
        for (int[] iArr : this.mStateSpecs) {
            int length = iArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (iArr[i2] == i || iArr[i2] == (~i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public java.lang.String toString() {
        return "ColorStateList{mThemeAttrs=" + java.util.Arrays.deepToString(this.mThemeAttrs) + "mChangingConfigurations=" + this.mChangingConfigurations + "mStateSpecs=" + java.util.Arrays.deepToString(this.mStateSpecs) + "mColors=" + java.util.Arrays.toString(this.mColors) + "mDefaultColor=" + this.mDefaultColor + '}';
    }

    private void onColorsChanged() {
        int i;
        int[][] iArr = this.mStateSpecs;
        int[] iArr2 = this.mColors;
        int length = iArr.length;
        boolean z = true;
        if (length <= 0) {
            i = -65536;
        } else {
            i = iArr2[0];
            int i2 = length - 1;
            while (true) {
                if (i2 <= 0) {
                    break;
                }
                if (iArr[i2].length != 0) {
                    i2--;
                } else {
                    i = iArr2[i2];
                    break;
                }
            }
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (android.graphics.Color.alpha(iArr2[i3]) == 255) {
                    i3++;
                } else {
                    z = false;
                    break;
                }
            }
        }
        this.mDefaultColor = i;
        this.mIsOpaque = z;
    }

    @Override // android.content.res.ComplexColor
    public android.content.res.ConstantState<android.content.res.ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new android.content.res.ColorStateList.ColorStateListFactory(this);
        }
        return this.mFactory;
    }

    private static class ColorStateListFactory extends android.content.res.ConstantState<android.content.res.ComplexColor> {
        private final android.content.res.ColorStateList mSrc;

        public ColorStateListFactory(android.content.res.ColorStateList colorStateList) {
            this.mSrc = colorStateList;
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance, reason: merged with bridge method [inline-methods] */
        public android.content.res.ComplexColor newInstance2() {
            return this.mSrc;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance, reason: merged with bridge method [inline-methods] */
        public android.content.res.ComplexColor newInstance2(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (canApplyTheme()) {
            android.util.Log.w(TAG, "Wrote partially-resolved ColorStateList to parcel!");
        }
        int length = this.mStateSpecs.length;
        parcel.writeInt(length);
        for (int i2 = 0; i2 < length; i2++) {
            parcel.writeIntArray(this.mStateSpecs[i2]);
        }
        parcel.writeIntArray(this.mColors);
    }
}
