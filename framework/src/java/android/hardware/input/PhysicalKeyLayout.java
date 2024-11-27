package android.hardware.input;

/* loaded from: classes2.dex */
final class PhysicalKeyLayout {
    private static final android.util.SparseIntArray DEFAULT_KEYCODE_FOR_SCANCODE = new android.util.SparseIntArray();
    private static final int SCANCODE_0 = 11;
    private static final int SCANCODE_1 = 2;
    private static final int SCANCODE_2 = 3;
    private static final int SCANCODE_3 = 4;
    private static final int SCANCODE_4 = 5;
    private static final int SCANCODE_5 = 6;
    private static final int SCANCODE_6 = 7;
    private static final int SCANCODE_7 = 8;
    private static final int SCANCODE_8 = 9;
    private static final int SCANCODE_9 = 10;
    private static final int SCANCODE_A = 30;
    private static final int SCANCODE_APOSTROPHE = 40;
    private static final int SCANCODE_B = 48;
    private static final int SCANCODE_BACKSLASH1 = 43;
    private static final int SCANCODE_BACKSLASH2 = 86;
    private static final int SCANCODE_C = 46;
    private static final int SCANCODE_COMMA = 51;
    private static final int SCANCODE_D = 32;
    private static final int SCANCODE_E = 18;
    private static final int SCANCODE_EQUALS = 13;
    private static final int SCANCODE_F = 33;
    private static final int SCANCODE_G = 34;
    private static final int SCANCODE_GRAVE = 41;
    private static final int SCANCODE_H = 35;
    private static final int SCANCODE_I = 23;
    private static final int SCANCODE_J = 36;
    private static final int SCANCODE_K = 37;
    private static final int SCANCODE_L = 38;
    private static final int SCANCODE_LEFT_BRACKET = 26;
    private static final int SCANCODE_M = 50;
    private static final int SCANCODE_MINUS = 12;
    private static final int SCANCODE_N = 49;
    private static final int SCANCODE_O = 24;
    private static final int SCANCODE_P = 25;
    private static final int SCANCODE_PERIOD = 52;
    private static final int SCANCODE_Q = 16;
    private static final int SCANCODE_R = 19;
    private static final int SCANCODE_RIGHT_BRACKET = 27;
    private static final int SCANCODE_S = 31;
    private static final int SCANCODE_SEMICOLON = 39;
    private static final int SCANCODE_SLASH = 53;
    private static final int SCANCODE_T = 20;
    private static final int SCANCODE_U = 22;
    private static final int SCANCODE_V = 47;
    private static final int SCANCODE_W = 17;
    private static final int SCANCODE_X = 45;
    private static final int SCANCODE_Y = 21;
    private static final int SCANCODE_YEN = 124;
    private static final int SCANCODE_Z = 44;
    private static final java.lang.String TAG = "KeyboardLayoutPreview";
    private android.hardware.input.PhysicalKeyLayout.LayoutKey[][] mKeys = null;
    private android.hardware.input.PhysicalKeyLayout.EnterKey mEnterKey = null;

    static {
        DEFAULT_KEYCODE_FOR_SCANCODE.put(2, 8);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(3, 9);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(4, 10);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(5, 11);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(6, 12);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(7, 13);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(8, 14);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(9, 15);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(10, 16);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(11, 7);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(12, 69);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(13, 70);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(16, 45);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(17, 51);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(18, 33);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(19, 46);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(20, 48);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(21, 53);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(22, 49);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(23, 37);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(24, 43);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(25, 44);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(26, 71);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(27, 72);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(30, 29);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(31, 47);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(32, 32);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(33, 34);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(34, 35);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(35, 36);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(36, 38);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(37, 39);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(38, 40);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(39, 74);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(40, 75);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(41, 68);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(43, 73);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(44, 54);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(45, 52);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(46, 31);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(47, 50);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(48, 30);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(49, 42);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(50, 41);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(51, 55);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(52, 56);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(53, 76);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(86, 73);
        DEFAULT_KEYCODE_FOR_SCANCODE.put(124, 216);
    }

    public PhysicalKeyLayout(android.view.KeyCharacterMap keyCharacterMap, android.hardware.input.KeyboardLayout keyboardLayout) {
        initLayoutKeys(keyCharacterMap, keyboardLayout);
    }

    private void initLayoutKeys(android.view.KeyCharacterMap keyCharacterMap, android.hardware.input.KeyboardLayout keyboardLayout) {
        if (keyboardLayout == null) {
            createIsoLayout(keyCharacterMap);
            return;
        }
        if (keyboardLayout.isAnsiLayout()) {
            createAnsiLayout(keyCharacterMap);
        } else if (keyboardLayout.isJisLayout()) {
            createJisLayout(keyCharacterMap);
        } else {
            createIsoLayout(keyCharacterMap);
        }
    }

    public android.hardware.input.PhysicalKeyLayout.LayoutKey[][] getKeys() {
        return this.mKeys;
    }

    public android.hardware.input.PhysicalKeyLayout.EnterKey getEnterKey() {
        return this.mEnterKey;
    }

    private void createAnsiLayout(android.view.KeyCharacterMap keyCharacterMap) {
        this.mKeys = new android.hardware.input.PhysicalKeyLayout.LayoutKey[][]{new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12), getKey(keyCharacterMap, 13), getKey(67, 1.5f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(keyCharacterMap, 43)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(115, 1.75f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(66, 1.75f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(59, 2.5f), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(60, 2.5f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
    }

    private void createIsoLayout(android.view.KeyCharacterMap keyCharacterMap) {
        this.mKeys = new android.hardware.input.PhysicalKeyLayout.LayoutKey[][]{new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12), getKey(keyCharacterMap, 13), getKey(67, 1.5f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(61, 1.15f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(66, 1.35f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(keyCharacterMap, 43), getKey(66, 1.0f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(59, 1.15f), getKey(keyCharacterMap, 86), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(60, 2.35f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(62, 6.5f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new android.hardware.input.PhysicalKeyLayout.EnterKey(1, 13, 1.35f, 1.0f);
    }

    private void createJisLayout(android.view.KeyCharacterMap keyCharacterMap) {
        this.mKeys = new android.hardware.input.PhysicalKeyLayout.LayoutKey[][]{new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(keyCharacterMap, 41), getKey(keyCharacterMap, 2), getKey(keyCharacterMap, 3), getKey(keyCharacterMap, 4), getKey(keyCharacterMap, 5), getKey(keyCharacterMap, 6), getKey(keyCharacterMap, 7), getKey(keyCharacterMap, 8), getKey(keyCharacterMap, 9), getKey(keyCharacterMap, 10), getKey(keyCharacterMap, 11), getKey(keyCharacterMap, 12, 0.8f), getKey(keyCharacterMap, 13, 0.8f), getKey(keyCharacterMap, 124, 0.8f), getKey(67, 1.1f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(61, 1.15f), getKey(keyCharacterMap, 16), getKey(keyCharacterMap, 17), getKey(keyCharacterMap, 18), getKey(keyCharacterMap, 19), getKey(keyCharacterMap, 20), getKey(keyCharacterMap, 21), getKey(keyCharacterMap, 22), getKey(keyCharacterMap, 23), getKey(keyCharacterMap, 24), getKey(keyCharacterMap, 25), getKey(keyCharacterMap, 26), getKey(keyCharacterMap, 27), getKey(66, 1.35f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(61, 1.5f), getKey(keyCharacterMap, 30), getKey(keyCharacterMap, 31), getKey(keyCharacterMap, 32), getKey(keyCharacterMap, 33), getKey(keyCharacterMap, 34), getKey(keyCharacterMap, 35), getKey(keyCharacterMap, 36), getKey(keyCharacterMap, 37), getKey(keyCharacterMap, 38), getKey(keyCharacterMap, 39), getKey(keyCharacterMap, 40), getKey(keyCharacterMap, 86), getKey(66, 1.0f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(59, 1.15f), getKey(keyCharacterMap, 44), getKey(keyCharacterMap, 45), getKey(keyCharacterMap, 46), getKey(keyCharacterMap, 47), getKey(keyCharacterMap, 48), getKey(keyCharacterMap, 49), getKey(keyCharacterMap, 50), getKey(keyCharacterMap, 51), getKey(keyCharacterMap, 52), getKey(keyCharacterMap, 53), getKey(keyCharacterMap, 43), getKey(60, 2.35f)}, new android.hardware.input.PhysicalKeyLayout.LayoutKey[]{getKey(113, 1.0f), getKey(119, 1.0f), getKey(117, 1.0f), getKey(57, 1.0f), getKey(0, 1.0f), getKey(62, 3.5f), getKey(0, 1.0f), getKey(0, 1.0f), getKey(58, 1.0f), getKey(118, 1.0f), getKey(82, 1.0f), getKey(114, 1.0f)}};
        this.mEnterKey = new android.hardware.input.PhysicalKeyLayout.EnterKey(1, 13, 1.35f, 1.0f);
    }

    private static android.hardware.input.PhysicalKeyLayout.LayoutKey getKey(android.view.KeyCharacterMap keyCharacterMap, int i, float f) {
        int mappedKeyOrDefault = keyCharacterMap.getMappedKeyOrDefault(i, DEFAULT_KEYCODE_FOR_SCANCODE.get(i, 0));
        return new android.hardware.input.PhysicalKeyLayout.LayoutKey(mappedKeyOrDefault, i, f, new android.hardware.input.PhysicalKeyLayout.KeyGlyph(keyCharacterMap, mappedKeyOrDefault));
    }

    private static android.hardware.input.PhysicalKeyLayout.LayoutKey getKey(android.view.KeyCharacterMap keyCharacterMap, int i) {
        return getKey(keyCharacterMap, i, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getKeyText(android.view.KeyCharacterMap keyCharacterMap, int i, int i2) {
        int i3;
        if (isSpecialKey(i) || (i3 = keyCharacterMap.get(i, i2) & Integer.MAX_VALUE) == 0) {
            return "";
        }
        if (java.lang.Character.isValidCodePoint(i3)) {
            return java.lang.String.valueOf(java.lang.Character.toChars(i3));
        }
        return "□";
    }

    private static android.hardware.input.PhysicalKeyLayout.LayoutKey getKey(int i, float f) {
        return new android.hardware.input.PhysicalKeyLayout.LayoutKey(i, i, f, null);
    }

    private static boolean isSpecialKey(int i) {
        switch (i) {
            case 0:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 66:
            case 67:
            case 82:
            case 113:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSpecialKey(android.hardware.input.PhysicalKeyLayout.LayoutKey layoutKey) {
        return isSpecialKey(layoutKey.keyCode);
    }

    public static boolean isKeyPositionUnsure(android.hardware.input.PhysicalKeyLayout.LayoutKey layoutKey) {
        switch (layoutKey.scanCode) {
            case 41:
            case 43:
            case 86:
                return true;
            default:
                return false;
        }
    }

    public static final class LayoutKey extends java.lang.Record {
        private final android.hardware.input.PhysicalKeyLayout.KeyGlyph glyph;
        private final int keyCode;
        private final float keyWeight;
        private final int scanCode;

        public LayoutKey(int keyCode, int scanCode, float keyWeight, android.hardware.input.PhysicalKeyLayout.KeyGlyph glyph) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.keyWeight = keyWeight;
            this.glyph = glyph;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, android.hardware.input.PhysicalKeyLayout.LayoutKey.class, java.lang.Object.class), android.hardware.input.PhysicalKeyLayout.LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        public android.hardware.input.PhysicalKeyLayout.KeyGlyph glyph() {
            return this.glyph;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, android.hardware.input.PhysicalKeyLayout.LayoutKey.class), android.hardware.input.PhysicalKeyLayout.LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public int keyCode() {
            return this.keyCode;
        }

        public float keyWeight() {
            return this.keyWeight;
        }

        public int scanCode() {
            return this.scanCode;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, android.hardware.input.PhysicalKeyLayout.LayoutKey.class), android.hardware.input.PhysicalKeyLayout.LayoutKey.class, "keyCode;scanCode;keyWeight;glyph", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->scanCode:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->keyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$LayoutKey;->glyph:Landroid/hardware/input/PhysicalKeyLayout$KeyGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    public static final class EnterKey extends java.lang.Record {
        private final float bottomKeyWeight;
        private final int column;
        private final int row;
        private final float topKeyWeight;

        public EnterKey(int row, int column, float topKeyWeight, float bottomKeyWeight) {
            this.row = row;
            this.column = column;
            this.topKeyWeight = topKeyWeight;
            this.bottomKeyWeight = bottomKeyWeight;
        }

        public float bottomKeyWeight() {
            return this.bottomKeyWeight;
        }

        public int column() {
            return this.column;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, android.hardware.input.PhysicalKeyLayout.EnterKey.class, java.lang.Object.class), android.hardware.input.PhysicalKeyLayout.EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, android.hardware.input.PhysicalKeyLayout.EnterKey.class), android.hardware.input.PhysicalKeyLayout.EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public int row() {
            return this.row;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, android.hardware.input.PhysicalKeyLayout.EnterKey.class), android.hardware.input.PhysicalKeyLayout.EnterKey.class, "row;column;topKeyWeight;bottomKeyWeight", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->row:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->column:I", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->topKeyWeight:F", "FIELD:Landroid/hardware/input/PhysicalKeyLayout$EnterKey;->bottomKeyWeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public float topKeyWeight() {
            return this.topKeyWeight;
        }
    }

    public static class KeyGlyph {
        private final java.lang.String mAltGrShiftText;
        private final java.lang.String mAltGrText;
        private final java.lang.String mBaseText;
        private final java.lang.String mShiftText;

        public KeyGlyph(android.view.KeyCharacterMap keyCharacterMap, int i) {
            this.mBaseText = android.hardware.input.PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 1048576);
            this.mShiftText = android.hardware.input.PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 65);
            this.mAltGrText = android.hardware.input.PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 1048610);
            this.mAltGrShiftText = android.hardware.input.PhysicalKeyLayout.getKeyText(keyCharacterMap, i, 99);
        }

        public java.lang.String getBaseText() {
            return this.mBaseText;
        }

        public java.lang.String getShiftText() {
            return this.mShiftText;
        }

        public java.lang.String getAltGrText() {
            return this.mAltGrText;
        }

        public java.lang.String getAltGrShiftText() {
            return this.mAltGrShiftText;
        }

        public boolean hasBaseText() {
            return !android.text.TextUtils.isEmpty(this.mBaseText);
        }

        public boolean hasValidShiftText() {
            return (android.text.TextUtils.isEmpty(this.mShiftText) || android.text.TextUtils.equals(this.mBaseText, this.mShiftText)) ? false : true;
        }

        public boolean hasValidAltGrText() {
            return (android.text.TextUtils.isEmpty(this.mAltGrText) || android.text.TextUtils.equals(this.mBaseText, this.mAltGrText) || android.text.TextUtils.equals(this.mShiftText, this.mAltGrText)) ? false : true;
        }

        public boolean hasValidAltGrShiftText() {
            return (android.text.TextUtils.isEmpty(this.mAltGrShiftText) || android.text.TextUtils.equals(this.mBaseText, this.mAltGrShiftText) || android.text.TextUtils.equals(this.mAltGrText, this.mAltGrShiftText) || android.text.TextUtils.equals(this.mShiftText, this.mAltGrShiftText)) ? false : true;
        }
    }
}
