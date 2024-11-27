package android.text.method;

/* loaded from: classes3.dex */
public abstract class NumberKeyListener extends android.text.method.BaseKeyListener implements android.text.InputFilter {
    private static final java.lang.String DATE_TIME_FORMAT_SYMBOLS = "GyYuUrQqMLlwWdDFgEecabBhHKkjJCmsSAzZOvVXx";
    private static final char SINGLE_QUOTE = '\'';

    protected abstract char[] getAcceptedChars();

    protected int lookup(android.view.KeyEvent keyEvent, android.text.Spannable spannable) {
        return keyEvent.getMatch(getAcceptedChars(), getMetaState(spannable, keyEvent));
    }

    public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
        char[] acceptedChars = getAcceptedChars();
        int i5 = i;
        while (i5 < i2 && ok(acceptedChars, charSequence.charAt(i5))) {
            i5++;
        }
        if (i5 == i2) {
            return null;
        }
        int i6 = i2 - i;
        if (i6 == 1) {
            return "";
        }
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence, i, i2);
        int i7 = i5 - i;
        for (int i8 = i6 - 1; i8 >= i7; i8--) {
            if (!ok(acceptedChars, charSequence.charAt(i8))) {
                spannableStringBuilder.delete(i8, i8 + 1);
            }
        }
        return spannableStringBuilder;
    }

    protected static boolean ok(char[] cArr, char c) {
        for (int length = cArr.length - 1; length >= 0; length--) {
            if (cArr[length] == c) {
                return true;
            }
        }
        return false;
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        int min = java.lang.Math.min(selectionStart, selectionEnd);
        int max = java.lang.Math.max(selectionStart, selectionEnd);
        if (min < 0 || max < 0) {
            android.text.Selection.setSelection(editable, 0);
            max = 0;
            min = 0;
        }
        int lookup = keyEvent != null ? lookup(keyEvent, editable) : 0;
        int repeatCount = keyEvent != null ? keyEvent.getRepeatCount() : 0;
        if (repeatCount == 0) {
            if (lookup != 0) {
                if (min != max) {
                    android.text.Selection.setSelection(editable, max);
                }
                editable.replace(min, max, java.lang.String.valueOf((char) lookup));
                adjustMetaAfterKeypress(editable);
                return true;
            }
        } else if (lookup == 48 && repeatCount == 1 && min == max && max > 0) {
            int i2 = min - 1;
            if (editable.charAt(i2) == '0') {
                editable.replace(i2, max, java.lang.String.valueOf('+'));
                adjustMetaAfterKeypress(editable);
                return true;
            }
        }
        adjustMetaAfterKeypress(editable);
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    static boolean addDigits(java.util.Collection<java.lang.Character> collection, java.util.Locale locale) {
        if (locale == null) {
            return false;
        }
        java.lang.String[] digitStrings = android.icu.text.DecimalFormatSymbols.getInstance(locale).getDigitStrings();
        for (int i = 0; i < 10; i++) {
            if (digitStrings[i].length() > 1) {
                return false;
            }
            collection.add(java.lang.Character.valueOf(digitStrings[i].charAt(0)));
        }
        return true;
    }

    static boolean addFormatCharsFromSkeleton(java.util.Collection<java.lang.Character> collection, java.util.Locale locale, java.lang.String str, java.lang.String str2) {
        if (locale == null) {
            return false;
        }
        java.lang.String bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(locale, str);
        boolean z = true;
        for (int i = 0; i < bestDateTimePattern.length(); i++) {
            char charAt = bestDateTimePattern.charAt(i);
            if (java.lang.Character.isSurrogate(charAt)) {
                return false;
            }
            if (charAt == '\'') {
                z = !z;
                if (i == 0) {
                    continue;
                } else if (bestDateTimePattern.charAt(i - 1) != '\'') {
                    continue;
                }
            }
            if (z) {
                if (str2.indexOf(charAt) != -1) {
                    continue;
                } else if (DATE_TIME_FORMAT_SYMBOLS.indexOf(charAt) != -1) {
                    return false;
                }
            }
            collection.add(java.lang.Character.valueOf(charAt));
        }
        return true;
    }

    static boolean addFormatCharsFromSkeletons(java.util.Collection<java.lang.Character> collection, java.util.Locale locale, java.lang.String[] strArr, java.lang.String str) {
        for (java.lang.String str2 : strArr) {
            if (!addFormatCharsFromSkeleton(collection, locale, str2, str)) {
                return false;
            }
        }
        return true;
    }

    static boolean addAmPmChars(java.util.Collection<java.lang.Character> collection, java.util.Locale locale) {
        if (locale == null) {
            return false;
        }
        java.lang.String[] amPmStrings = android.text.format.DateFormat.getIcuDateFormatSymbols(locale).getAmPmStrings();
        for (int i = 0; i < amPmStrings.length; i++) {
            for (int i2 = 0; i2 < amPmStrings[i].length(); i2++) {
                char charAt = amPmStrings[i].charAt(i2);
                if (!java.lang.Character.isBmpCodePoint(charAt)) {
                    return false;
                }
                collection.add(java.lang.Character.valueOf(charAt));
            }
        }
        return true;
    }

    static char[] collectionToArray(java.util.Collection<java.lang.Character> collection) {
        char[] cArr = new char[collection.size()];
        java.util.Iterator<java.lang.Character> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            cArr[i] = it.next().charValue();
            i++;
        }
        return cArr;
    }
}
