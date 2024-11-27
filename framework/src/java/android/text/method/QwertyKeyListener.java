package android.text.method;

/* loaded from: classes3.dex */
public class QwertyKeyListener extends android.text.method.BaseKeyListener {
    private static android.text.method.QwertyKeyListener sFullKeyboardInstance;
    private android.text.method.TextKeyListener.Capitalize mAutoCap;
    private boolean mAutoText;
    private boolean mFullKeyboard;
    private static android.text.method.QwertyKeyListener[] sInstance = new android.text.method.QwertyKeyListener[android.text.method.TextKeyListener.Capitalize.values().length * 2];
    private static android.util.SparseArray<java.lang.String> PICKER_SETS = new android.util.SparseArray<>();

    static {
        PICKER_SETS.put(65, "ÀÁÂÄÆÃÅĄĀ");
        PICKER_SETS.put(67, "ÇĆČ");
        PICKER_SETS.put(68, "Ď");
        PICKER_SETS.put(69, "ÈÉÊËĘĚĒ");
        PICKER_SETS.put(71, "Ğ");
        PICKER_SETS.put(76, "Ł");
        PICKER_SETS.put(73, "ÌÍÎÏĪİ");
        PICKER_SETS.put(78, "ÑŃŇ");
        PICKER_SETS.put(79, "ØŒÕÒÓÔÖŌ");
        PICKER_SETS.put(82, "Ř");
        PICKER_SETS.put(83, "ŚŠŞ");
        PICKER_SETS.put(84, "Ť");
        PICKER_SETS.put(85, "ÙÚÛÜŮŪ");
        PICKER_SETS.put(89, "ÝŸ");
        PICKER_SETS.put(90, "ŹŻŽ");
        PICKER_SETS.put(97, "àáâäæãåąā");
        PICKER_SETS.put(99, "çćč");
        PICKER_SETS.put(100, "ď");
        PICKER_SETS.put(101, "èéêëęěē");
        PICKER_SETS.put(103, "ğ");
        PICKER_SETS.put(105, "ìíîïīı");
        PICKER_SETS.put(108, "ł");
        PICKER_SETS.put(110, "ñńň");
        PICKER_SETS.put(111, "øœõòóôöō");
        PICKER_SETS.put(114, "ř");
        PICKER_SETS.put(115, "§ßśšş");
        PICKER_SETS.put(116, "ť");
        PICKER_SETS.put(117, "ùúûüůū");
        PICKER_SETS.put(121, "ýÿ");
        PICKER_SETS.put(122, "źżž");
        PICKER_SETS.put(61185, "…¥•®©±[]{}\\|");
        PICKER_SETS.put(47, "\\");
        PICKER_SETS.put(49, "¹½⅓¼⅛");
        PICKER_SETS.put(50, "²⅔");
        PICKER_SETS.put(51, "³¾⅜");
        PICKER_SETS.put(52, "⁴");
        PICKER_SETS.put(53, "⅝");
        PICKER_SETS.put(55, "⅞");
        PICKER_SETS.put(48, "ⁿ∅");
        PICKER_SETS.put(36, "¢£€¥₣₤₱");
        PICKER_SETS.put(37, "‰");
        PICKER_SETS.put(42, "†‡");
        PICKER_SETS.put(45, "–—");
        PICKER_SETS.put(43, "±");
        PICKER_SETS.put(40, "[{<");
        PICKER_SETS.put(41, "]}>");
        PICKER_SETS.put(33, "¡");
        PICKER_SETS.put(34, "“”«»˝");
        PICKER_SETS.put(63, "¿");
        PICKER_SETS.put(44, "‚„");
        PICKER_SETS.put(61, "≠≈∞");
        PICKER_SETS.put(60, "≤«‹");
        PICKER_SETS.put(62, "≥»›");
    }

    private QwertyKeyListener(android.text.method.TextKeyListener.Capitalize capitalize, boolean z, boolean z2) {
        this.mAutoCap = capitalize;
        this.mAutoText = z;
        this.mFullKeyboard = z2;
    }

    public QwertyKeyListener(android.text.method.TextKeyListener.Capitalize capitalize, boolean z) {
        this(capitalize, z, false);
    }

    public static android.text.method.QwertyKeyListener getInstance(boolean z, android.text.method.TextKeyListener.Capitalize capitalize) {
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        if (sInstance[ordinal] == null) {
            sInstance[ordinal] = new android.text.method.QwertyKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    public static android.text.method.QwertyKeyListener getInstanceForFullKeyboard() {
        if (sFullKeyboardInstance == null) {
            sFullKeyboardInstance = new android.text.method.QwertyKeyListener(android.text.method.TextKeyListener.Capitalize.NONE, false, true);
        }
        return sFullKeyboardInstance;
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SimplifyVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v30 int, still in use, count: 2, list:
          (r0v30 int) from 0x0232: ARITH (r2v6 int) = (r0v30 int) + (-1 int) (LINE:292)
          (r0v30 int) from 0x023b: ARITH (r0v31 int) = (r0v30 int) - (2 int)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.utils.InsnRemover.unbindAllArgs(InsnRemover.java:106)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:90)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:141)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyArgs(SimplifyVisitor.java:116)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyInsn(SimplifyVisitor.java:132)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyBlock(SimplifyVisitor.java:86)
        	at jadx.core.dex.visitors.SimplifyVisitor.visit(SimplifyVisitor.java:71)
        */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012e  */
    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onKeyDown(android.view.View r20, android.text.Editable r21, int r22, android.view.KeyEvent r23) {
        /*
            Method dump skipped, instructions count: 785
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.QwertyKeyListener.onKeyDown(android.view.View, android.text.Editable, int, android.view.KeyEvent):boolean");
    }

    private java.lang.String getReplacement(java.lang.CharSequence charSequence, int i, int i2, android.view.View view) {
        boolean z;
        int i3;
        int i4 = i2 - i;
        java.lang.String str = android.text.AutoText.get(charSequence, i, i2, view);
        if (str != null) {
            z = false;
        } else {
            str = android.text.AutoText.get(android.text.TextUtils.substring(charSequence, i, i2).toLowerCase(), 0, i4, view);
            if (str == null) {
                return null;
            }
            z = true;
        }
        if (!z) {
            i3 = 0;
        } else {
            i3 = 0;
            for (int i5 = i; i5 < i2; i5++) {
                if (java.lang.Character.isUpperCase(charSequence.charAt(i5))) {
                    i3++;
                }
            }
        }
        if (i3 != 0) {
            if (i3 == 1) {
                str = toTitleCase(str);
            } else if (i3 == i4) {
                str = str.toUpperCase();
            } else {
                str = toTitleCase(str);
            }
        }
        if (str.length() == i4 && android.text.TextUtils.regionMatches(charSequence, i, str, 0, i4)) {
            return null;
        }
        return str;
    }

    public static void markAsReplaced(android.text.Spannable spannable, int i, int i2, java.lang.String str) {
        for (android.text.method.QwertyKeyListener.Replaced replaced : (android.text.method.QwertyKeyListener.Replaced[]) spannable.getSpans(0, spannable.length(), android.text.method.QwertyKeyListener.Replaced.class)) {
            spannable.removeSpan(replaced);
        }
        int length = str.length();
        char[] cArr = new char[length];
        str.getChars(0, length, cArr, 0);
        spannable.setSpan(new android.text.method.QwertyKeyListener.Replaced(cArr), i, i2, 33);
    }

    private boolean showCharacterPicker(android.view.View view, android.text.Editable editable, char c, boolean z, int i) {
        java.lang.String str = PICKER_SETS.get(c);
        if (str == null) {
            return false;
        }
        if (i == 1) {
            new android.text.method.CharacterPickerDialog(view.getContext(), view, editable, str, z).show();
        }
        return true;
    }

    private static java.lang.String toTitleCase(java.lang.String str) {
        return java.lang.Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    static class Replaced implements android.text.NoCopySpan {
        private char[] mText;

        public Replaced(char[] cArr) {
            this.mText = cArr;
        }
    }
}
