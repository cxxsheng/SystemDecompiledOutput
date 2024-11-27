package android.text;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public abstract class SpannableStringInternal {
    private static final int COLUMNS = 3;
    static final java.lang.Object[] EMPTY = new java.lang.Object[0];
    private static final int END = 1;
    private static final int FLAGS = 2;
    private static final int START = 0;
    private int mSpanCount;
    private int[] mSpanData;
    private java.lang.Object[] mSpans;
    private java.lang.String mText;

    /* JADX WARN: Multi-variable type inference failed */
    SpannableStringInternal(java.lang.CharSequence charSequence, int i, int i2, boolean z) {
        if (i == 0 && i2 == charSequence.length()) {
            this.mText = charSequence.toString();
        } else {
            this.mText = charSequence.toString().substring(i, i2);
        }
        this.mSpans = libcore.util.EmptyArray.OBJECT;
        this.mSpanData = libcore.util.EmptyArray.INT;
        if (charSequence instanceof android.text.Spanned) {
            if (charSequence instanceof android.text.SpannableStringInternal) {
                copySpansFromInternal((android.text.SpannableStringInternal) charSequence, i, i2, z);
            } else {
                copySpansFromSpanned((android.text.Spanned) charSequence, i, i2, z);
            }
        }
    }

    SpannableStringInternal(java.lang.CharSequence charSequence, int i, int i2) {
        this(charSequence, i, i2, false);
    }

    private void copySpansFromSpanned(android.text.Spanned spanned, int i, int i2, boolean z) {
        java.lang.Object[] spans = spanned.getSpans(i, i2, java.lang.Object.class);
        for (int i3 = 0; i3 < spans.length; i3++) {
            if (!z || !(spans[i3] instanceof android.text.NoCopySpan)) {
                int spanStart = spanned.getSpanStart(spans[i3]);
                int spanEnd = spanned.getSpanEnd(spans[i3]);
                int spanFlags = spanned.getSpanFlags(spans[i3]);
                if (spanStart < i) {
                    spanStart = i;
                }
                if (spanEnd > i2) {
                    spanEnd = i2;
                }
                setSpan(spans[i3], spanStart - i, spanEnd - i, spanFlags, false);
            }
        }
    }

    private void copySpansFromInternal(android.text.SpannableStringInternal spannableStringInternal, int i, int i2, boolean z) {
        int[] iArr = spannableStringInternal.mSpanData;
        java.lang.Object[] objArr = spannableStringInternal.mSpans;
        int i3 = spannableStringInternal.mSpanCount;
        int i4 = 0;
        boolean z2 = false;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i5 * 3;
            if (!isOutOfCopyRange(i, i2, iArr[i6 + 0], iArr[i6 + 1])) {
                if (objArr[i5] instanceof android.text.NoCopySpan) {
                    if (!z) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                i4++;
            }
        }
        if (i4 == 0) {
            return;
        }
        if (!z2 && i == 0 && i2 == spannableStringInternal.length()) {
            this.mSpans = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(spannableStringInternal.mSpans.length);
            this.mSpanData = new int[spannableStringInternal.mSpanData.length];
            this.mSpanCount = spannableStringInternal.mSpanCount;
            java.lang.System.arraycopy(spannableStringInternal.mSpans, 0, this.mSpans, 0, spannableStringInternal.mSpans.length);
            java.lang.System.arraycopy(spannableStringInternal.mSpanData, 0, this.mSpanData, 0, this.mSpanData.length);
            return;
        }
        this.mSpanCount = i4;
        this.mSpans = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(this.mSpanCount);
        this.mSpanData = new int[this.mSpans.length * 3];
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            int i9 = i8 * 3;
            int i10 = iArr[i9 + 0];
            int i11 = iArr[i9 + 1];
            if (!isOutOfCopyRange(i, i2, i10, i11) && (!z || !(objArr[i8] instanceof android.text.NoCopySpan))) {
                if (i10 < i) {
                    i10 = i;
                }
                if (i11 > i2) {
                    i11 = i2;
                }
                this.mSpans[i7] = objArr[i8];
                int i12 = i7 * 3;
                this.mSpanData[i12 + 0] = i10 - i;
                this.mSpanData[i12 + 1] = i11 - i;
                this.mSpanData[i12 + 2] = iArr[i9 + 2];
                i7++;
            }
        }
    }

    private final boolean isOutOfCopyRange(int i, int i2, int i3, int i4) {
        if (i3 > i2 || i4 < i) {
            return true;
        }
        if (i3 != i4 && i != i2) {
            if (i3 == i2 || i4 == i) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int length() {
        return this.mText.length();
    }

    public final char charAt(int i) {
        return this.mText.charAt(i);
    }

    public final java.lang.String toString() {
        return this.mText;
    }

    public final void getChars(int i, int i2, char[] cArr, int i3) {
        this.mText.getChars(i, i2, cArr, i3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSpan(java.lang.Object obj, int i, int i2, int i3) {
        setSpan(obj, i, i2, i3, true);
    }

    private boolean isIndexFollowsNextLine(int i) {
        return (i == 0 || i == length() || charAt(i - 1) == '\n') ? false : true;
    }

    private void setSpan(java.lang.Object obj, int i, int i2, int i3, boolean z) {
        checkRange("setSpan", i, i2);
        if ((i3 & 51) == 51) {
            if (isIndexFollowsNextLine(i)) {
                if (!z) {
                    return;
                } else {
                    throw new java.lang.RuntimeException("PARAGRAPH span must start at paragraph boundary (" + i + " follows " + charAt(i - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
            } else if (isIndexFollowsNextLine(i2)) {
                if (!z) {
                    return;
                } else {
                    throw new java.lang.RuntimeException("PARAGRAPH span must end at paragraph boundary (" + i2 + " follows " + charAt(i2 - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
            }
        }
        int i4 = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i5 = 0; i5 < i4; i5++) {
            if (objArr[i5] == obj) {
                int i6 = i5 * 3;
                int i7 = i6 + 0;
                int i8 = iArr[i7];
                int i9 = i6 + 1;
                int i10 = iArr[i9];
                iArr[i7] = i;
                iArr[i9] = i2;
                iArr[i6 + 2] = i3;
                sendSpanChanged(obj, i8, i10, i, i2);
                return;
            }
        }
        if (this.mSpanCount + 1 >= this.mSpans.length) {
            java.lang.Object[] newUnpaddedObjectArray = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(com.android.internal.util.GrowingArrayUtils.growSize(this.mSpanCount));
            int[] iArr2 = new int[newUnpaddedObjectArray.length * 3];
            java.lang.System.arraycopy(this.mSpans, 0, newUnpaddedObjectArray, 0, this.mSpanCount);
            java.lang.System.arraycopy(this.mSpanData, 0, iArr2, 0, this.mSpanCount * 3);
            this.mSpans = newUnpaddedObjectArray;
            this.mSpanData = iArr2;
        }
        this.mSpans[this.mSpanCount] = obj;
        this.mSpanData[(this.mSpanCount * 3) + 0] = i;
        this.mSpanData[(this.mSpanCount * 3) + 1] = i2;
        this.mSpanData[(this.mSpanCount * 3) + 2] = i3;
        this.mSpanCount++;
        if (this instanceof android.text.Spannable) {
            sendSpanAdded(obj, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeSpan(java.lang.Object obj) {
        removeSpan(obj, 0);
    }

    public void removeSpan(java.lang.Object obj, int i) {
        int i2 = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (objArr[i3] == obj) {
                int i4 = i3 * 3;
                int i5 = iArr[i4 + 0];
                int i6 = iArr[i4 + 1];
                int i7 = i3 + 1;
                int i8 = i2 - i7;
                java.lang.System.arraycopy(objArr, i7, objArr, i3, i8);
                java.lang.System.arraycopy(iArr, i7 * 3, iArr, i4, i8 * 3);
                this.mSpanCount--;
                if ((i & 512) == 0) {
                    sendSpanRemoved(obj, i5, i6);
                    return;
                }
                return;
            }
        }
    }

    public int getSpanStart(java.lang.Object obj) {
        int i = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[(i2 * 3) + 0];
            }
        }
        return -1;
    }

    public int getSpanEnd(java.lang.Object obj) {
        int i = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[(i2 * 3) + 1];
            }
        }
        return -1;
    }

    public int getSpanFlags(java.lang.Object obj) {
        int i = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[(i2 * 3) + 2];
            }
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
        int i3 = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        java.lang.Object[] objArr2 = (T[]) null;
        int i4 = 0;
        java.lang.Object obj = null;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i5 * 3;
            int i7 = iArr[i6 + 0];
            int i8 = iArr[i6 + 1];
            if (i7 <= i2 && i8 >= i && ((i7 == i8 || i == i2 || (i7 != i2 && i8 != i)) && (cls == null || cls == java.lang.Object.class || cls.isInstance(objArr[i5])))) {
                if (i4 == 0) {
                    obj = objArr[i5];
                    i4++;
                } else {
                    if (i4 == 1) {
                        objArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, (i3 - i5) + 1));
                        objArr2[0] = obj;
                    }
                    int i9 = iArr[i6 + 2] & android.text.Spanned.SPAN_PRIORITY;
                    if (i9 != 0) {
                        int i10 = 0;
                        while (i10 < i4 && i9 <= (getSpanFlags(objArr2[i10]) & android.text.Spanned.SPAN_PRIORITY)) {
                            i10++;
                        }
                        java.lang.System.arraycopy(objArr2, i10, objArr2, i10 + 1, i4 - i10);
                        objArr2[i10] = objArr[i5];
                        i4++;
                    } else {
                        objArr2[i4] = objArr[i5];
                        i4++;
                    }
                }
            }
        }
        if (i4 == 0) {
            return (T[]) com.android.internal.util.ArrayUtils.emptyArray(cls);
        }
        if (i4 == 1) {
            T[] tArr = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, 1));
            tArr[0] = obj;
            return tArr;
        }
        if (i4 == objArr2.length) {
            return (T[]) objArr2;
        }
        T[] tArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i4));
        java.lang.System.arraycopy(objArr2, 0, tArr2, 0, i4);
        return tArr2;
    }

    public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
        int i3 = this.mSpanCount;
        java.lang.Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        if (cls == null) {
            cls = java.lang.Object.class;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * 3;
            int i6 = iArr[i5 + 0];
            int i7 = iArr[i5 + 1];
            if (i6 > i && i6 < i2 && cls.isInstance(objArr[i4])) {
                i2 = i6;
            }
            if (i7 > i && i7 < i2 && cls.isInstance(objArr[i4])) {
                i2 = i7;
            }
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanAdded(java.lang.Object obj, int i, int i2) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(i, i2, android.text.SpanWatcher.class)) {
            spanWatcher.onSpanAdded((android.text.Spannable) this, obj, i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanRemoved(java.lang.Object obj, int i, int i2) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(i, i2, android.text.SpanWatcher.class)) {
            spanWatcher.onSpanRemoved((android.text.Spannable) this, obj, i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanChanged(java.lang.Object obj, int i, int i2, int i3, int i4) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(java.lang.Math.min(i, i3), java.lang.Math.max(i2, i4), android.text.SpanWatcher.class)) {
            spanWatcher.onSpanChanged((android.text.Spannable) this, obj, i, i2, i3, i4);
        }
    }

    private static java.lang.String region(int i, int i2) {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + i + " ... " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    private void checkRange(java.lang.String str, int i, int i2) {
        if (i2 < i) {
            throw new java.lang.IndexOutOfBoundsException(str + " " + region(i, i2) + " has end before start");
        }
        int length = length();
        if (i > length || i2 > length) {
            throw new java.lang.IndexOutOfBoundsException(str + " " + region(i, i2) + " ends beyond length " + length);
        }
        if (i < 0 || i2 < 0) {
            throw new java.lang.IndexOutOfBoundsException(str + " " + region(i, i2) + " starts before 0");
        }
    }

    public boolean equals(java.lang.Object obj) {
        if ((obj instanceof android.text.Spanned) && toString().equals(obj.toString())) {
            android.text.Spanned spanned = (android.text.Spanned) obj;
            java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), java.lang.Object.class);
            java.lang.Object[] spans2 = getSpans(0, length(), java.lang.Object.class);
            if (this.mSpanCount == spans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    java.lang.Object obj2 = spans2[i];
                    java.lang.Object obj3 = spans[i];
                    if (obj2 == this) {
                        if (spanned != obj3 || getSpanStart(obj2) != spanned.getSpanStart(obj3) || getSpanEnd(obj2) != spanned.getSpanEnd(obj3) || getSpanFlags(obj2) != spanned.getSpanFlags(obj3)) {
                            return false;
                        }
                    } else if (!obj2.equals(obj3) || getSpanStart(obj2) != spanned.getSpanStart(obj3) || getSpanEnd(obj2) != spanned.getSpanEnd(obj3) || getSpanFlags(obj2) != spanned.getSpanFlags(obj3)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (toString().hashCode() * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            java.lang.Object obj = this.mSpans[i];
            if (obj != this) {
                hashCode = (hashCode * 31) + obj.hashCode();
            }
            hashCode = (((((hashCode * 31) + getSpanStart(obj)) * 31) + getSpanEnd(obj)) * 31) + getSpanFlags(obj);
        }
        return hashCode;
    }

    private void copySpans(android.text.Spanned spanned, int i, int i2) {
        copySpansFromSpanned(spanned, i, i2, false);
    }

    private void copySpans(android.text.SpannableStringInternal spannableStringInternal, int i, int i2) {
        copySpansFromInternal(spannableStringInternal, i, i2, false);
    }
}
