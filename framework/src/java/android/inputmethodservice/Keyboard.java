package android.inputmethodservice;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class Keyboard {
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    private static final int GRID_HEIGHT = 5;
    private static final int GRID_SIZE = 50;
    private static final int GRID_WIDTH = 10;
    public static final int KEYCODE_ALT = -6;
    public static final int KEYCODE_CANCEL = -3;
    public static final int KEYCODE_DELETE = -5;
    public static final int KEYCODE_DONE = -4;
    public static final int KEYCODE_MODE_CHANGE = -2;
    public static final int KEYCODE_SHIFT = -1;
    private static float SEARCH_DISTANCE = 1.8f;
    static final java.lang.String TAG = "Keyboard";
    private static final java.lang.String TAG_KEY = "Key";
    private static final java.lang.String TAG_KEYBOARD = "Keyboard";
    private static final java.lang.String TAG_ROW = "Row";
    private int mCellHeight;
    private int mCellWidth;
    private int mDefaultHeight;
    private int mDefaultHorizontalGap;
    private int mDefaultVerticalGap;
    private int mDefaultWidth;
    private int mDisplayHeight;
    private int mDisplayWidth;
    private int[][] mGridNeighbors;
    private int mKeyHeight;
    private int mKeyWidth;
    private int mKeyboardMode;
    private java.util.List<android.inputmethodservice.Keyboard.Key> mKeys;
    private java.lang.CharSequence mLabel;
    private java.util.List<android.inputmethodservice.Keyboard.Key> mModifierKeys;
    private int mProximityThreshold;
    private int[] mShiftKeyIndices;
    private android.inputmethodservice.Keyboard.Key[] mShiftKeys;
    private boolean mShifted;
    private int mTotalHeight;
    private int mTotalWidth;
    private java.util.ArrayList<android.inputmethodservice.Keyboard.Row> rows;

    public static class Row {
        public int defaultHeight;
        public int defaultHorizontalGap;
        public int defaultWidth;
        java.util.ArrayList<android.inputmethodservice.Keyboard.Key> mKeys = new java.util.ArrayList<>();
        public int mode;
        private android.inputmethodservice.Keyboard parent;
        public int rowEdgeFlags;
        public int verticalGap;

        public Row(android.inputmethodservice.Keyboard keyboard) {
            this.parent = keyboard;
        }

        public Row(android.content.res.Resources resources, android.inputmethodservice.Keyboard keyboard, android.content.res.XmlResourceParser xmlResourceParser) {
            this.parent = keyboard;
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.Keyboard);
            this.defaultWidth = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 0, keyboard.mDisplayWidth, keyboard.mDefaultWidth);
            this.defaultHeight = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 1, keyboard.mDisplayHeight, keyboard.mDefaultHeight);
            this.defaultHorizontalGap = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 2, keyboard.mDisplayWidth, keyboard.mDefaultHorizontalGap);
            this.verticalGap = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 3, keyboard.mDisplayHeight, keyboard.mDefaultVerticalGap);
            obtainAttributes.recycle();
            android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.Keyboard_Row);
            this.rowEdgeFlags = obtainAttributes2.getInt(0, 0);
            this.mode = obtainAttributes2.getResourceId(1, 0);
            obtainAttributes2.recycle();
        }
    }

    public static class Key {
        public int[] codes;
        public int edgeFlags;
        public int gap;
        public int height;
        public android.graphics.drawable.Drawable icon;
        public android.graphics.drawable.Drawable iconPreview;
        private android.inputmethodservice.Keyboard keyboard;
        public java.lang.CharSequence label;
        public boolean modifier;
        public boolean on;
        public java.lang.CharSequence popupCharacters;
        public int popupResId;
        public boolean pressed;
        public boolean repeatable;
        public boolean sticky;
        public java.lang.CharSequence text;
        public int width;
        public int x;
        public int y;
        private static final int[] KEY_STATE_NORMAL_ON = {16842911, 16842912};
        private static final int[] KEY_STATE_PRESSED_ON = {16842919, 16842911, 16842912};
        private static final int[] KEY_STATE_NORMAL_OFF = {16842911};
        private static final int[] KEY_STATE_PRESSED_OFF = {16842919, 16842911};
        private static final int[] KEY_STATE_NORMAL = new int[0];
        private static final int[] KEY_STATE_PRESSED = {16842919};

        public Key(android.inputmethodservice.Keyboard.Row row) {
            this.keyboard = row.parent;
            this.height = row.defaultHeight;
            this.width = row.defaultWidth;
            this.gap = row.defaultHorizontalGap;
            this.edgeFlags = row.rowEdgeFlags;
        }

        public Key(android.content.res.Resources resources, android.inputmethodservice.Keyboard.Row row, int i, int i2, android.content.res.XmlResourceParser xmlResourceParser) {
            this(row);
            this.x = i;
            this.y = i2;
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.Keyboard);
            this.width = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 0, this.keyboard.mDisplayWidth, row.defaultWidth);
            this.height = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 1, this.keyboard.mDisplayHeight, row.defaultHeight);
            this.gap = android.inputmethodservice.Keyboard.getDimensionOrFraction(obtainAttributes, 2, this.keyboard.mDisplayWidth, row.defaultHorizontalGap);
            obtainAttributes.recycle();
            android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.Keyboard_Key);
            this.x += this.gap;
            android.util.TypedValue typedValue = new android.util.TypedValue();
            obtainAttributes2.getValue(0, typedValue);
            if (typedValue.type == 16 || typedValue.type == 17) {
                this.codes = new int[]{typedValue.data};
            } else if (typedValue.type == 3) {
                this.codes = parseCSV(typedValue.string.toString());
            }
            this.iconPreview = obtainAttributes2.getDrawable(7);
            if (this.iconPreview != null) {
                this.iconPreview.setBounds(0, 0, this.iconPreview.getIntrinsicWidth(), this.iconPreview.getIntrinsicHeight());
            }
            this.popupCharacters = obtainAttributes2.getText(2);
            this.popupResId = obtainAttributes2.getResourceId(1, 0);
            this.repeatable = obtainAttributes2.getBoolean(6, false);
            this.modifier = obtainAttributes2.getBoolean(4, false);
            this.sticky = obtainAttributes2.getBoolean(5, false);
            this.edgeFlags = obtainAttributes2.getInt(3, 0);
            this.edgeFlags = row.rowEdgeFlags | this.edgeFlags;
            this.icon = obtainAttributes2.getDrawable(10);
            if (this.icon != null) {
                this.icon.setBounds(0, 0, this.icon.getIntrinsicWidth(), this.icon.getIntrinsicHeight());
            }
            this.label = obtainAttributes2.getText(9);
            this.text = obtainAttributes2.getText(8);
            if (this.codes == null && !android.text.TextUtils.isEmpty(this.label)) {
                this.codes = new int[]{this.label.charAt(0)};
            }
            obtainAttributes2.recycle();
        }

        public void onPressed() {
            this.pressed = !this.pressed;
        }

        public void onReleased(boolean z) {
            this.pressed = !this.pressed;
            if (this.sticky && z) {
                this.on = !this.on;
            }
        }

        int[] parseCSV(java.lang.String str) {
            int i;
            int i2 = 0;
            if (str.length() <= 0) {
                i = 0;
            } else {
                i = 1;
                int i3 = 0;
                while (true) {
                    i3 = str.indexOf(",", i3 + 1);
                    if (i3 <= 0) {
                        break;
                    }
                    i++;
                }
            }
            int[] iArr = new int[i];
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, ",");
            while (stringTokenizer.hasMoreTokens()) {
                int i4 = i2 + 1;
                try {
                    iArr[i2] = java.lang.Integer.parseInt(stringTokenizer.nextToken());
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.e("Keyboard", "Error parsing keycodes " + str);
                }
                i2 = i4;
            }
            return iArr;
        }

        public boolean isInside(int i, int i2) {
            return (i >= this.x || (((this.edgeFlags & 1) > 0) && i <= this.x + this.width)) && (i < this.x + this.width || (((this.edgeFlags & 2) > 0) && i >= this.x)) && ((i2 >= this.y || (((this.edgeFlags & 4) > 0) && i2 <= this.y + this.height)) && (i2 < this.y + this.height || (((this.edgeFlags & 8) > 0) && i2 >= this.y)));
        }

        public int squaredDistanceFrom(int i, int i2) {
            int i3 = (this.x + (this.width / 2)) - i;
            int i4 = (this.y + (this.height / 2)) - i2;
            return (i3 * i3) + (i4 * i4);
        }

        public int[] getCurrentDrawableState() {
            int[] iArr = KEY_STATE_NORMAL;
            if (this.on) {
                if (this.pressed) {
                    return KEY_STATE_PRESSED_ON;
                }
                return KEY_STATE_NORMAL_ON;
            }
            if (this.sticky) {
                if (this.pressed) {
                    return KEY_STATE_PRESSED_OFF;
                }
                return KEY_STATE_NORMAL_OFF;
            }
            if (this.pressed) {
                return KEY_STATE_PRESSED;
            }
            return iArr;
        }
    }

    public Keyboard(android.content.Context context, int i) {
        this(context, i, 0);
    }

    public Keyboard(android.content.Context context, int i, int i2, int i3, int i4) {
        this.mShiftKeys = new android.inputmethodservice.Keyboard.Key[]{null, null};
        this.mShiftKeyIndices = new int[]{-1, -1};
        this.rows = new java.util.ArrayList<>();
        this.mDisplayWidth = i3;
        this.mDisplayHeight = i4;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new java.util.ArrayList();
        this.mModifierKeys = new java.util.ArrayList();
        this.mKeyboardMode = i2;
        loadKeyboard(context, context.getResources().getXml(i));
    }

    public Keyboard(android.content.Context context, int i, int i2) {
        this.mShiftKeys = new android.inputmethodservice.Keyboard.Key[]{null, null};
        this.mShiftKeyIndices = new int[]{-1, -1};
        this.rows = new java.util.ArrayList<>();
        android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisplayWidth = displayMetrics.widthPixels;
        this.mDisplayHeight = displayMetrics.heightPixels;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new java.util.ArrayList();
        this.mModifierKeys = new java.util.ArrayList();
        this.mKeyboardMode = i2;
        loadKeyboard(context, context.getResources().getXml(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Keyboard(android.content.Context context, int i, java.lang.CharSequence charSequence, int i2, int i3) {
        this(context, i);
        this.mTotalWidth = 0;
        android.inputmethodservice.Keyboard.Row row = new android.inputmethodservice.Keyboard.Row(this);
        row.defaultHeight = this.mDefaultHeight;
        row.defaultWidth = this.mDefaultWidth;
        row.defaultHorizontalGap = this.mDefaultHorizontalGap;
        row.verticalGap = this.mDefaultVerticalGap;
        row.rowEdgeFlags = 12;
        i2 = i2 == -1 ? Integer.MAX_VALUE : i2;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < charSequence.length(); i7++) {
            int charAt = charSequence.charAt(i7);
            if (i5 >= i2 || this.mDefaultWidth + i6 + i3 > this.mDisplayWidth) {
                i4 += this.mDefaultVerticalGap + this.mDefaultHeight;
                i5 = 0;
                i6 = 0;
            }
            android.inputmethodservice.Keyboard.Key key = new android.inputmethodservice.Keyboard.Key(row);
            key.x = i6;
            key.y = i4;
            key.label = java.lang.String.valueOf((char) charAt);
            key.codes = new int[]{charAt};
            i5++;
            i6 += key.width + key.gap;
            this.mKeys.add(key);
            row.mKeys.add(key);
            if (i6 > this.mTotalWidth) {
                this.mTotalWidth = i6;
            }
        }
        this.mTotalHeight = i4 + this.mDefaultHeight;
        this.rows.add(row);
    }

    final void resize(int i, int i2) {
        int size = this.rows.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.inputmethodservice.Keyboard.Row row = this.rows.get(i3);
            int size2 = row.mKeys.size();
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < size2; i6++) {
                android.inputmethodservice.Keyboard.Key key = row.mKeys.get(i6);
                if (i6 > 0) {
                    i4 += key.gap;
                }
                i5 += key.width;
            }
            if (i4 + i5 > i) {
                float f = (i - i4) / i5;
                int i7 = 0;
                for (int i8 = 0; i8 < size2; i8++) {
                    android.inputmethodservice.Keyboard.Key key2 = row.mKeys.get(i8);
                    key2.width = (int) (key2.width * f);
                    key2.x = i7;
                    i7 += key2.width + key2.gap;
                }
            }
        }
        this.mTotalWidth = i;
    }

    public java.util.List<android.inputmethodservice.Keyboard.Key> getKeys() {
        return this.mKeys;
    }

    public java.util.List<android.inputmethodservice.Keyboard.Key> getModifierKeys() {
        return this.mModifierKeys;
    }

    protected int getHorizontalGap() {
        return this.mDefaultHorizontalGap;
    }

    protected void setHorizontalGap(int i) {
        this.mDefaultHorizontalGap = i;
    }

    protected int getVerticalGap() {
        return this.mDefaultVerticalGap;
    }

    protected void setVerticalGap(int i) {
        this.mDefaultVerticalGap = i;
    }

    protected int getKeyHeight() {
        return this.mDefaultHeight;
    }

    protected void setKeyHeight(int i) {
        this.mDefaultHeight = i;
    }

    protected int getKeyWidth() {
        return this.mDefaultWidth;
    }

    protected void setKeyWidth(int i) {
        this.mDefaultWidth = i;
    }

    public int getHeight() {
        return this.mTotalHeight;
    }

    public int getMinWidth() {
        return this.mTotalWidth;
    }

    public boolean setShifted(boolean z) {
        for (android.inputmethodservice.Keyboard.Key key : this.mShiftKeys) {
            if (key != null) {
                key.on = z;
            }
        }
        if (this.mShifted == z) {
            return false;
        }
        this.mShifted = z;
        return true;
    }

    public boolean isShifted() {
        return this.mShifted;
    }

    public int[] getShiftKeyIndices() {
        return this.mShiftKeyIndices;
    }

    public int getShiftKeyIndex() {
        return this.mShiftKeyIndices[0];
    }

    private void computeNearestNeighbors() {
        this.mCellWidth = ((getMinWidth() + 10) - 1) / 10;
        this.mCellHeight = ((getHeight() + 5) - 1) / 5;
        this.mGridNeighbors = new int[50][];
        int[] iArr = new int[this.mKeys.size()];
        int i = this.mCellWidth * 10;
        int i2 = this.mCellHeight * 5;
        int i3 = 0;
        while (i3 < i) {
            int i4 = 0;
            while (i4 < i2) {
                int i5 = 0;
                for (int i6 = 0; i6 < this.mKeys.size(); i6++) {
                    android.inputmethodservice.Keyboard.Key key = this.mKeys.get(i6);
                    if (key.squaredDistanceFrom(i3, i4) < this.mProximityThreshold || key.squaredDistanceFrom((this.mCellWidth + i3) - 1, i4) < this.mProximityThreshold || key.squaredDistanceFrom((this.mCellWidth + i3) - 1, (this.mCellHeight + i4) - 1) < this.mProximityThreshold || key.squaredDistanceFrom(i3, (this.mCellHeight + i4) - 1) < this.mProximityThreshold) {
                        iArr[i5] = i6;
                        i5++;
                    }
                }
                int[] iArr2 = new int[i5];
                java.lang.System.arraycopy(iArr, 0, iArr2, 0, i5);
                this.mGridNeighbors[((i4 / this.mCellHeight) * 10) + (i3 / this.mCellWidth)] = iArr2;
                i4 += this.mCellHeight;
            }
            i3 += this.mCellWidth;
        }
    }

    public int[] getNearestKeys(int i, int i2) {
        int i3;
        if (this.mGridNeighbors == null) {
            computeNearestNeighbors();
        }
        if (i >= 0 && i < getMinWidth() && i2 >= 0 && i2 < getHeight() && (i3 = ((i2 / this.mCellHeight) * 10) + (i / this.mCellWidth)) < 50) {
            return this.mGridNeighbors[i3];
        }
        return new int[0];
    }

    protected android.inputmethodservice.Keyboard.Row createRowFromXml(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        return new android.inputmethodservice.Keyboard.Row(resources, this, xmlResourceParser);
    }

    protected android.inputmethodservice.Keyboard.Key createKeyFromXml(android.content.res.Resources resources, android.inputmethodservice.Keyboard.Row row, int i, int i2, android.content.res.XmlResourceParser xmlResourceParser) {
        return new android.inputmethodservice.Keyboard.Key(resources, row, i, i2, xmlResourceParser);
    }

    private void loadKeyboard(android.content.Context context, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.Resources resources = context.getResources();
        android.inputmethodservice.Keyboard.Key key = null;
        android.inputmethodservice.Keyboard.Row row = null;
        boolean z = false;
        int i = 0;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            try {
                int next = xmlResourceParser.next();
                if (next == 1) {
                    break;
                }
                if (next == 2) {
                    java.lang.String name = xmlResourceParser.getName();
                    if (TAG_ROW.equals(name)) {
                        android.inputmethodservice.Keyboard.Row createRowFromXml = createRowFromXml(resources, xmlResourceParser);
                        this.rows.add(createRowFromXml);
                        if (!((createRowFromXml.mode == 0 || createRowFromXml.mode == this.mKeyboardMode) ? false : true)) {
                            row = createRowFromXml;
                            i2 = 0;
                            z2 = true;
                        } else {
                            skipToEndOfRow(xmlResourceParser);
                            row = createRowFromXml;
                            z2 = false;
                            i2 = 0;
                        }
                    } else if (TAG_KEY.equals(name)) {
                        key = createKeyFromXml(resources, row, i2, i, xmlResourceParser);
                        this.mKeys.add(key);
                        if (key.codes[0] != -1) {
                            if (key.codes[0] == -6) {
                                this.mModifierKeys.add(key);
                            }
                        } else {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= this.mShiftKeys.length) {
                                    break;
                                }
                                if (this.mShiftKeys[i3] != null) {
                                    i3++;
                                } else {
                                    this.mShiftKeys[i3] = key;
                                    this.mShiftKeyIndices[i3] = this.mKeys.size() - 1;
                                    break;
                                }
                            }
                            this.mModifierKeys.add(key);
                        }
                        row.mKeys.add(key);
                        z = true;
                    } else if ("Keyboard".equals(name)) {
                        parseKeyboardAttributes(resources, xmlResourceParser);
                    }
                } else if (next == 3) {
                    if (z) {
                        i2 += key.gap + key.width;
                        if (i2 > this.mTotalWidth) {
                            this.mTotalWidth = i2;
                        }
                        z = false;
                    } else if (z2) {
                        i = i + row.verticalGap + row.defaultHeight;
                        z2 = false;
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e("Keyboard", "Parse error:" + e);
                e.printStackTrace();
            }
        }
        this.mTotalHeight = i - this.mDefaultVerticalGap;
    }

    private void skipToEndOfRow(android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (next == 3 && xmlResourceParser.getName().equals(TAG_ROW)) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void parseKeyboardAttributes(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.Keyboard);
        this.mDefaultWidth = getDimensionOrFraction(obtainAttributes, 0, this.mDisplayWidth, this.mDisplayWidth / 10);
        this.mDefaultHeight = getDimensionOrFraction(obtainAttributes, 1, this.mDisplayHeight, 50);
        this.mDefaultHorizontalGap = getDimensionOrFraction(obtainAttributes, 2, this.mDisplayWidth, 0);
        this.mDefaultVerticalGap = getDimensionOrFraction(obtainAttributes, 3, this.mDisplayHeight, 0);
        this.mProximityThreshold = (int) (this.mDefaultWidth * SEARCH_DISTANCE);
        this.mProximityThreshold *= this.mProximityThreshold;
        obtainAttributes.recycle();
    }

    static int getDimensionOrFraction(android.content.res.TypedArray typedArray, int i, int i2, int i3) {
        android.util.TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue == null) {
            return i3;
        }
        if (peekValue.type == 5) {
            return typedArray.getDimensionPixelOffset(i, i3);
        }
        if (peekValue.type == 6) {
            return java.lang.Math.round(typedArray.getFraction(i, i2, i2, i3));
        }
        return i3;
    }
}
