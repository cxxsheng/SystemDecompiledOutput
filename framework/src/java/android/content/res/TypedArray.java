package android.content.res;

/* loaded from: classes.dex */
public class TypedArray implements java.lang.AutoCloseable {
    static final int STYLE_ASSET_COOKIE = 2;
    static final int STYLE_CHANGING_CONFIGURATIONS = 4;
    static final int STYLE_DATA = 1;
    static final int STYLE_DENSITY = 5;
    static final int STYLE_NUM_ENTRIES = 7;
    static final int STYLE_RESOURCE_ID = 3;
    static final int STYLE_SOURCE_RESOURCE_ID = 6;
    static final int STYLE_TYPE = 0;
    private android.content.res.AssetManager mAssets;
    int[] mData;
    long mDataAddress;
    int[] mIndices;
    long mIndicesAddress;
    int mLength;
    private android.util.DisplayMetrics mMetrics;
    private boolean mRecycled;
    private final android.content.res.Resources mResources;
    android.content.res.Resources.Theme mTheme;
    android.util.TypedValue mValue = new android.util.TypedValue();
    android.content.res.XmlBlock.Parser mXml;

    static android.content.res.TypedArray obtain(android.content.res.Resources resources, int i) {
        android.content.res.TypedArray acquire = resources.mTypedArrayPool.acquire();
        if (acquire == null) {
            acquire = new android.content.res.TypedArray(resources);
        }
        acquire.mRecycled = false;
        acquire.mAssets = resources.getAssets();
        acquire.mMetrics = resources.getDisplayMetrics();
        acquire.resize(i);
        return acquire;
    }

    private void resize(int i) {
        this.mLength = i;
        int i2 = i * 7;
        int i3 = i + 1;
        dalvik.system.VMRuntime runtime = dalvik.system.VMRuntime.getRuntime();
        if (this.mDataAddress == 0 || this.mData.length < i2) {
            this.mData = (int[]) runtime.newNonMovableArray(java.lang.Integer.TYPE, i2);
            this.mDataAddress = runtime.addressOf(this.mData);
            this.mIndices = (int[]) runtime.newNonMovableArray(java.lang.Integer.TYPE, i3);
            this.mIndicesAddress = runtime.addressOf(this.mIndices);
        }
    }

    public int length() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mLength;
    }

    public int getIndexCount() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mIndices[0];
    }

    public int getIndex(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mIndices[i + 1];
    }

    public android.content.res.Resources getResources() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mResources;
    }

    public java.lang.CharSequence getText(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int i3 = this.mData[i2 + 0];
        if (i3 == 0) {
            return null;
        }
        if (i3 == 3) {
            return loadStringValueAt(i2);
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i2, typedValue)) {
            return typedValue.coerceToString();
        }
        throw new java.lang.RuntimeException("getText of bad type: 0x" + java.lang.Integer.toHexString(i3));
    }

    public java.lang.String getString(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int i3 = this.mData[i2 + 0];
        if (i3 == 0) {
            return null;
        }
        if (i3 == 3) {
            return loadStringValueAt(i2).toString();
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i2, typedValue)) {
            java.lang.CharSequence coerceToString = typedValue.coerceToString();
            if (coerceToString != null) {
                return coerceToString.toString();
            }
            return null;
        }
        throw new java.lang.RuntimeException("getString of bad type: 0x" + java.lang.Integer.toHexString(i3));
    }

    public java.lang.String getNonResourceString(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        if (iArr[i2 + 0] == 3 && iArr[i2 + 2] < 0) {
            java.lang.String charSequence = this.mXml.getPooledString(iArr[i2 + 1]).toString();
            if (charSequence != null && this.mXml != null && this.mXml.mValidator != null) {
                this.mXml.mValidator.validateResStrAttr(this.mXml, i2, charSequence);
            }
            return charSequence;
        }
        return null;
    }

    public java.lang.String getNonConfigurationString(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (((~i2) & android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(iArr[i3 + 4])) != 0 || i4 == 0) {
            return null;
        }
        if (i4 == 3) {
            return loadStringValueAt(i3).toString();
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i3, typedValue)) {
            java.lang.CharSequence coerceToString = typedValue.coerceToString();
            if (coerceToString != null) {
                return coerceToString.toString();
            }
            return null;
        }
        throw new java.lang.RuntimeException("getNonConfigurationString of bad type: 0x" + java.lang.Integer.toHexString(i4));
    }

    public boolean getBoolean(int i, boolean z) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        int i3 = iArr[i2 + 0];
        if (i3 == 0) {
            return z;
        }
        if (i3 >= 16 && i3 <= 31) {
            return iArr[i2 + 1] != 0;
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i2, typedValue)) {
            android.os.StrictMode.noteResourceMismatch(typedValue);
            return com.android.internal.util.XmlUtils.convertValueToBoolean(typedValue.coerceToString(), z);
        }
        throw new java.lang.RuntimeException("getBoolean of bad type: 0x" + java.lang.Integer.toHexString(i3));
    }

    public int getInt(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 == 0) {
            return i2;
        }
        if (i4 >= 16 && i4 <= 31) {
            return iArr[i3 + 1];
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i3, typedValue)) {
            android.os.StrictMode.noteResourceMismatch(typedValue);
            return com.android.internal.util.XmlUtils.convertValueToInt(typedValue.coerceToString(), i2);
        }
        throw new java.lang.RuntimeException("getInt of bad type: 0x" + java.lang.Integer.toHexString(i4));
    }

    public float getFloat(int i, float f) {
        java.lang.CharSequence coerceToString;
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        int i3 = iArr[i2 + 0];
        if (i3 == 0) {
            return f;
        }
        if (i3 == 4) {
            return java.lang.Float.intBitsToFloat(iArr[i2 + 1]);
        }
        if (i3 >= 16 && i3 <= 31) {
            return iArr[i2 + 1];
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i2, typedValue) && (coerceToString = typedValue.coerceToString()) != null) {
            android.os.StrictMode.noteResourceMismatch(typedValue);
            return java.lang.Float.parseFloat(coerceToString.toString());
        }
        throw new java.lang.RuntimeException("getFloat of bad type: 0x" + java.lang.Integer.toHexString(i3));
    }

    public int getColor(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 == 0) {
            return i2;
        }
        if (i4 >= 16 && i4 <= 31) {
            return iArr[i3 + 1];
        }
        if (i4 == 3) {
            android.util.TypedValue typedValue = this.mValue;
            if (getValueAt(i3, typedValue)) {
                return this.mResources.loadColorStateList(typedValue, typedValue.resourceId, this.mTheme).getDefaultColor();
            }
            return i2;
        }
        if (i4 == 2) {
            android.util.TypedValue typedValue2 = this.mValue;
            getValueAt(i3, typedValue2);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue2 + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to color: type=0x" + java.lang.Integer.toHexString(i4) + ", theme=" + this.mTheme);
    }

    public android.content.res.ComplexColor getComplexColor(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            if (typedValue.type == 2) {
                throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
            }
            return this.mResources.loadComplexColor(typedValue, typedValue.resourceId, this.mTheme);
        }
        return null;
    }

    public android.content.res.ColorStateList getColorStateList(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            if (typedValue.type == 2) {
                throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
            }
            return this.mResources.loadColorStateList(typedValue, typedValue.resourceId, this.mTheme);
        }
        return null;
    }

    public int getInteger(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 == 0) {
            return i2;
        }
        if (i4 >= 16 && i4 <= 31) {
            return iArr[i3 + 1];
        }
        if (i4 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i3, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to integer: type=0x" + java.lang.Integer.toHexString(i4) + ", theme=" + this.mTheme);
    }

    public float getDimension(int i, float f) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        int i3 = iArr[i2 + 0];
        if (i3 == 0) {
            return f;
        }
        if (i3 == 5) {
            return android.util.TypedValue.complexToDimension(iArr[i2 + 1], this.mMetrics);
        }
        if (i3 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i2, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to dimension: type=0x" + java.lang.Integer.toHexString(i3) + ", theme=" + this.mTheme);
    }

    public int getDimensionPixelOffset(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 == 0) {
            return i2;
        }
        if (i4 == 5) {
            return android.util.TypedValue.complexToDimensionPixelOffset(iArr[i3 + 1], this.mMetrics);
        }
        if (i4 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i3, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to dimension: type=0x" + java.lang.Integer.toHexString(i4) + ", theme=" + this.mTheme);
    }

    public int getDimensionPixelSize(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 == 0) {
            return i2;
        }
        if (i4 == 5) {
            return android.util.TypedValue.complexToDimensionPixelSize(iArr[i3 + 1], this.mMetrics);
        }
        if (i4 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i3, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to dimension: type=0x" + java.lang.Integer.toHexString(i4) + ", theme=" + this.mTheme);
    }

    public int getLayoutDimension(int i, java.lang.String str) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        int i3 = iArr[i2 + 0];
        if (i3 >= 16 && i3 <= 31) {
            return iArr[i2 + 1];
        }
        if (i3 == 5) {
            return android.util.TypedValue.complexToDimensionPixelSize(iArr[i2 + 1], this.mMetrics);
        }
        if (i3 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i2, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException(getPositionDescription() + ": You must supply a " + str + " attribute., theme=" + this.mTheme);
    }

    public int getLayoutDimension(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        int i4 = iArr[i3 + 0];
        if (i4 >= 16 && i4 <= 31) {
            return iArr[i3 + 1];
        }
        if (i4 == 5) {
            return android.util.TypedValue.complexToDimensionPixelSize(iArr[i3 + 1], this.mMetrics);
        }
        return i2;
    }

    public float getFraction(int i, int i2, int i3, float f) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i4 = i * 7;
        int[] iArr = this.mData;
        int i5 = iArr[i4 + 0];
        if (i5 == 0) {
            return f;
        }
        if (i5 == 6) {
            return android.util.TypedValue.complexToFraction(iArr[i4 + 1], i2, i3);
        }
        if (i5 == 2) {
            android.util.TypedValue typedValue = this.mValue;
            getValueAt(i4, typedValue);
            throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
        }
        throw new java.lang.UnsupportedOperationException("Can't convert value at index " + i + " to fraction: type=0x" + java.lang.Integer.toHexString(i5) + ", theme=" + this.mTheme);
    }

    public int getResourceId(int i, int i2) {
        int i3;
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i4 = i * 7;
        int[] iArr = this.mData;
        if (iArr[i4 + 0] != 0 && (i3 = iArr[i4 + 3]) != 0) {
            return i3;
        }
        return i2;
    }

    public int getThemeAttributeId(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = i * 7;
        int[] iArr = this.mData;
        if (iArr[i3 + 0] == 2) {
            return iArr[i3 + 1];
        }
        return i2;
    }

    public android.graphics.drawable.Drawable getDrawable(int i) {
        return getDrawableForDensity(i, 0);
    }

    public android.graphics.drawable.Drawable getDrawableForDensity(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            if (typedValue.type == 2) {
                throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
            }
            if (i2 > 0) {
                this.mResources.getValueForDensity(typedValue.resourceId, i2, typedValue, true);
            }
            return this.mResources.loadDrawable(typedValue, typedValue.resourceId, i2, this.mTheme);
        }
        return null;
    }

    public android.graphics.Typeface getFont(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            if (typedValue.type == 2) {
                throw new java.lang.UnsupportedOperationException("Failed to resolve attribute at index " + i + ": " + typedValue + ", theme=" + this.mTheme);
            }
            return this.mResources.getFont(typedValue, typedValue.resourceId);
        }
        return null;
    }

    public java.lang.CharSequence[] getTextArray(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            return this.mResources.getTextArray(typedValue.resourceId);
        }
        return null;
    }

    public boolean getValue(int i, android.util.TypedValue typedValue) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return getValueAt(i * 7, typedValue);
    }

    public int getType(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mData[(i * 7) + 0];
    }

    public int getSourceResourceId(int i, int i2) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i3 = this.mData[(i * 7) + 6];
        if (i3 != 0) {
            return i3;
        }
        return i2;
    }

    public boolean hasValue(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mData[(i * 7) + 0] != 0;
    }

    public boolean hasValueOrEmpty(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int i2 = i * 7;
        int[] iArr = this.mData;
        return iArr[i2 + 0] != 0 || iArr[i2 + 1] == 1;
    }

    public android.util.TypedValue peekValue(int i) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        android.util.TypedValue typedValue = this.mValue;
        if (getValueAt(i * 7, typedValue)) {
            return typedValue;
        }
        return null;
    }

    public java.lang.String getPositionDescription() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        return this.mXml != null ? this.mXml.getPositionDescription() : "<internal>";
    }

    public void recycle() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException(toString() + " recycled twice!");
        }
        this.mRecycled = true;
        this.mXml = null;
        this.mTheme = null;
        this.mAssets = null;
        this.mResources.mTypedArrayPool.release(this);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        recycle();
    }

    public int[] extractThemeAttrs() {
        return extractThemeAttrs(null);
    }

    public int[] extractThemeAttrs(int[] iArr) {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int[] iArr2 = this.mData;
        int length = length();
        int[] iArr3 = null;
        for (int i = 0; i < length; i++) {
            int i2 = i * 7;
            int i3 = i2 + 0;
            if (iArr2[i3] == 2) {
                iArr2[i3] = 0;
                int i4 = iArr2[i2 + 1];
                if (i4 != 0) {
                    if (iArr3 == null) {
                        if (iArr != null && iArr.length == length) {
                            java.util.Arrays.fill(iArr, 0);
                            iArr3 = iArr;
                        } else {
                            iArr3 = new int[length];
                        }
                    }
                    iArr3[i] = i4;
                }
            }
        }
        return iArr3;
    }

    public int getChangingConfigurations() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException("Cannot make calls to a recycled instance!");
        }
        int[] iArr = this.mData;
        int length = length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 7;
            if (iArr[i3 + 0] != 0) {
                i |= android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(iArr[i3 + 4]);
            }
        }
        return i;
    }

    private boolean getValueAt(int i, android.util.TypedValue typedValue) {
        int[] iArr = this.mData;
        int i2 = iArr[i + 0];
        if (i2 == 0) {
            return false;
        }
        typedValue.type = i2;
        typedValue.data = iArr[i + 1];
        typedValue.assetCookie = iArr[i + 2];
        typedValue.resourceId = iArr[i + 3];
        typedValue.changingConfigurations = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(iArr[i + 4]);
        typedValue.density = iArr[i + 5];
        typedValue.string = i2 == 3 ? loadStringValueAt(i) : null;
        typedValue.sourceResourceId = iArr[i + 6];
        return true;
    }

    private java.lang.CharSequence loadStringValueAt(int i) {
        java.lang.CharSequence pooledStringForCookie;
        int[] iArr = this.mData;
        int i2 = iArr[i + 2];
        if (i2 < 0) {
            if (this.mXml == null) {
                pooledStringForCookie = null;
            } else {
                pooledStringForCookie = this.mXml.getPooledString(iArr[i + 1]);
            }
        } else {
            pooledStringForCookie = this.mAssets.getPooledStringForCookie(i2, iArr[i + 1]);
        }
        if (pooledStringForCookie != null && this.mXml != null && this.mXml.mValidator != null) {
            this.mXml.mValidator.validateResStrAttr(this.mXml, i / 7, pooledStringForCookie);
        }
        return pooledStringForCookie;
    }

    protected TypedArray(android.content.res.Resources resources) {
        this.mResources = resources;
        this.mMetrics = this.mResources.getDisplayMetrics();
        this.mAssets = this.mResources.getAssets();
    }

    public java.lang.String toString() {
        return java.util.Arrays.toString(this.mData);
    }
}
