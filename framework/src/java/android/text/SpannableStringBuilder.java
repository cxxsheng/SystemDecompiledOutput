package android.text;

/* loaded from: classes3.dex */
public class SpannableStringBuilder implements java.lang.CharSequence, android.text.GetChars, android.text.Spannable, android.text.Editable, java.lang.Appendable, android.text.GraphicsOperations {
    private static final int END_MASK = 15;
    private static final int MARK = 1;
    private static final int PARAGRAPH = 3;
    private static final int POINT = 2;
    private static final int SPAN_ADDED = 2048;
    private static final int SPAN_END_AT_END = 32768;
    private static final int SPAN_END_AT_START = 16384;
    private static final int SPAN_START_AT_END = 8192;
    private static final int SPAN_START_AT_START = 4096;
    private static final int SPAN_START_END_MASK = 61440;
    private static final int START_MASK = 240;
    private static final int START_SHIFT = 4;
    private static final java.lang.String TAG = "SpannableStringBuilder";
    private android.text.InputFilter[] mFilters;
    private int mGapLength;
    private int mGapStart;
    private java.util.IdentityHashMap<java.lang.Object, java.lang.Integer> mIndexOfSpan;
    private int mLowWaterMark;
    private int mSpanCount;
    private int[] mSpanEnds;
    private int[] mSpanFlags;
    private int mSpanInsertCount;
    private int[] mSpanMax;
    private int[] mSpanOrder;
    private int[] mSpanStarts;
    private java.lang.Object[] mSpans;
    private char[] mText;
    private int mTextWatcherDepth;
    private static final android.text.InputFilter[] NO_FILTERS = new android.text.InputFilter[0];
    private static final int[][] sCachedIntBuffer = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 6, 0);

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(java.lang.CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public SpannableStringBuilder(java.lang.CharSequence charSequence, int i, int i2) {
        int i3;
        int i4;
        this.mFilters = NO_FILTERS;
        int i5 = i2 - i;
        if (i5 >= 0) {
            this.mText = com.android.internal.util.ArrayUtils.newUnpaddedCharArray(com.android.internal.util.GrowingArrayUtils.growSize(i5));
            this.mGapStart = i5;
            this.mGapLength = this.mText.length - i5;
            android.text.TextUtils.getChars(charSequence, i, i2, this.mText, 0);
            this.mSpanCount = 0;
            this.mSpanInsertCount = 0;
            this.mSpans = libcore.util.EmptyArray.OBJECT;
            this.mSpanStarts = libcore.util.EmptyArray.INT;
            this.mSpanEnds = libcore.util.EmptyArray.INT;
            this.mSpanFlags = libcore.util.EmptyArray.INT;
            this.mSpanMax = libcore.util.EmptyArray.INT;
            this.mSpanOrder = libcore.util.EmptyArray.INT;
            if (charSequence instanceof android.text.Spanned) {
                android.text.Spanned spanned = (android.text.Spanned) charSequence;
                java.lang.Object[] spans = spanned.getSpans(i, i2, java.lang.Object.class);
                for (int i6 = 0; i6 < spans.length; i6++) {
                    if (!(spans[i6] instanceof android.text.NoCopySpan)) {
                        int spanStart = spanned.getSpanStart(spans[i6]) - i;
                        int spanEnd = spanned.getSpanEnd(spans[i6]) - i;
                        int spanFlags = spanned.getSpanFlags(spans[i6]);
                        spanStart = spanStart < 0 ? 0 : spanStart;
                        if (spanStart <= i5) {
                            i3 = spanStart;
                        } else {
                            i3 = i5;
                        }
                        spanEnd = spanEnd < 0 ? 0 : spanEnd;
                        if (spanEnd <= i5) {
                            i4 = spanEnd;
                        } else {
                            i4 = i5;
                        }
                        setSpan(false, spans[i6], i3, i4, spanFlags, false);
                    }
                }
                restoreInvariants();
                return;
            }
            return;
        }
        throw new java.lang.StringIndexOutOfBoundsException();
    }

    public static android.text.SpannableStringBuilder valueOf(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.SpannableStringBuilder) {
            return (android.text.SpannableStringBuilder) charSequence;
        }
        return new android.text.SpannableStringBuilder(charSequence);
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        int length = length();
        if (i < 0) {
            throw new java.lang.IndexOutOfBoundsException("charAt: " + i + " < 0");
        }
        if (i >= length) {
            throw new java.lang.IndexOutOfBoundsException("charAt: " + i + " >= length " + length);
        }
        if (i >= this.mGapStart) {
            return this.mText[i + this.mGapLength];
        }
        return this.mText[i];
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length - this.mGapLength;
    }

    private void resizeFor(int i) {
        int length = this.mText.length;
        if (i + 1 <= length) {
            return;
        }
        char[] newUnpaddedCharArray = com.android.internal.util.ArrayUtils.newUnpaddedCharArray(com.android.internal.util.GrowingArrayUtils.growSize(i));
        java.lang.System.arraycopy(this.mText, 0, newUnpaddedCharArray, 0, this.mGapStart);
        int length2 = newUnpaddedCharArray.length;
        int i2 = length2 - length;
        int i3 = length - (this.mGapStart + this.mGapLength);
        java.lang.System.arraycopy(this.mText, length - i3, newUnpaddedCharArray, length2 - i3, i3);
        this.mText = newUnpaddedCharArray;
        this.mGapLength += i2;
        if (this.mGapLength < 1) {
            new java.lang.Exception("mGapLength < 1").printStackTrace();
        }
        if (this.mSpanCount != 0) {
            for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                if (this.mSpanStarts[i4] > this.mGapStart) {
                    int[] iArr = this.mSpanStarts;
                    iArr[i4] = iArr[i4] + i2;
                }
                if (this.mSpanEnds[i4] > this.mGapStart) {
                    int[] iArr2 = this.mSpanEnds;
                    iArr2[i4] = iArr2[i4] + i2;
                }
            }
            calcMax(treeRoot());
        }
    }

    private void moveGapTo(int i) {
        int i2;
        int i3;
        if (i == this.mGapStart) {
            return;
        }
        boolean z = i == length();
        if (i < this.mGapStart) {
            int i4 = this.mGapStart - i;
            java.lang.System.arraycopy(this.mText, i, this.mText, (this.mGapStart + this.mGapLength) - i4, i4);
        } else {
            int i5 = i - this.mGapStart;
            java.lang.System.arraycopy(this.mText, (this.mGapLength + i) - i5, this.mText, this.mGapStart, i5);
        }
        if (this.mSpanCount != 0) {
            for (int i6 = 0; i6 < this.mSpanCount; i6++) {
                int i7 = this.mSpanStarts[i6];
                int i8 = this.mSpanEnds[i6];
                if (i7 > this.mGapStart) {
                    i7 -= this.mGapLength;
                }
                if (i7 > i) {
                    i7 += this.mGapLength;
                } else if (i7 == i && ((i2 = (this.mSpanFlags[i6] & 240) >> 4) == 2 || (z && i2 == 3))) {
                    i7 += this.mGapLength;
                }
                if (i8 > this.mGapStart) {
                    i8 -= this.mGapLength;
                }
                if (i8 > i) {
                    i8 += this.mGapLength;
                } else if (i8 == i && ((i3 = this.mSpanFlags[i6] & 15) == 2 || (z && i3 == 3))) {
                    i8 += this.mGapLength;
                }
                this.mSpanStarts[i6] = i7;
                this.mSpanEnds[i6] = i8;
            }
            calcMax(treeRoot());
        }
        this.mGapStart = i;
    }

    @Override // android.text.Editable
    public android.text.SpannableStringBuilder insert(int i, java.lang.CharSequence charSequence, int i2, int i3) {
        return replace(i, i, charSequence, i2, i3);
    }

    @Override // android.text.Editable
    public android.text.SpannableStringBuilder insert(int i, java.lang.CharSequence charSequence) {
        return replace(i, i, charSequence, 0, charSequence.length());
    }

    @Override // android.text.Editable
    public android.text.SpannableStringBuilder delete(int i, int i2) {
        android.text.SpannableStringBuilder replace = replace(i, i2, "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return replace;
    }

    @Override // android.text.Editable
    public void clear() {
        replace(0, length(), "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable
    public void clearSpans() {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            java.lang.Object obj = this.mSpans[i];
            int i2 = this.mSpanStarts[i];
            int i3 = this.mSpanEnds[i];
            if (i2 > this.mGapStart) {
                i2 -= this.mGapLength;
            }
            if (i3 > this.mGapStart) {
                i3 -= this.mGapLength;
            }
            this.mSpanCount = i;
            this.mSpans[i] = null;
            sendSpanRemoved(obj, i2, i3);
        }
        if (this.mIndexOfSpan != null) {
            this.mIndexOfSpan.clear();
        }
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public android.text.SpannableStringBuilder append(java.lang.CharSequence charSequence) {
        int length = length();
        return replace(length, length, charSequence, 0, charSequence.length());
    }

    public android.text.SpannableStringBuilder append(java.lang.CharSequence charSequence, java.lang.Object obj, int i) {
        int length = length();
        append(charSequence);
        setSpan(obj, length, length(), i);
        return this;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public android.text.SpannableStringBuilder append(java.lang.CharSequence charSequence, int i, int i2) {
        int length = length();
        return replace(length, length, charSequence, i, i2);
    }

    @Override // android.text.Editable, java.lang.Appendable
    public android.text.SpannableStringBuilder append(char c) {
        return append((java.lang.CharSequence) java.lang.String.valueOf(c));
    }

    private boolean removeSpansForChange(int i, int i2, boolean z, int i3) {
        int i4 = i3 & 1;
        if (i4 != 0 && resolveGap(this.mSpanMax[i3]) >= i && removeSpansForChange(i, i2, z, leftChild(i3))) {
            return true;
        }
        if (i3 >= this.mSpanCount) {
            return false;
        }
        if ((this.mSpanFlags[i3] & 33) != 33 || this.mSpanStarts[i3] < i || this.mSpanStarts[i3] >= this.mGapStart + this.mGapLength || this.mSpanEnds[i3] < i || this.mSpanEnds[i3] >= this.mGapStart + this.mGapLength || (!z && this.mSpanStarts[i3] <= i && this.mSpanEnds[i3] >= this.mGapStart)) {
            return resolveGap(this.mSpanStarts[i3]) <= i2 && i4 != 0 && removeSpansForChange(i, i2, z, rightChild(i3));
        }
        this.mIndexOfSpan.remove(this.mSpans[i3]);
        removeSpan(i3, 0);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1 */
    private void change(int i, int i2, java.lang.CharSequence charSequence, int i3, int i4) {
        boolean z;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = i2 - i;
        int i10 = i4 - i3;
        int i11 = i10 - i9;
        boolean z3 = true;
        int i12 = this.mSpanCount - 1;
        boolean z4 = false;
        while (i12 >= 0) {
            int i13 = this.mSpanStarts[i12];
            if (i13 > this.mGapStart) {
                i13 -= this.mGapLength;
            }
            int i14 = this.mSpanEnds[i12];
            if (i14 > this.mGapStart) {
                i14 -= this.mGapLength;
            }
            if ((this.mSpanFlags[i12] & 51) != 51) {
                z = z3;
                z2 = z4;
            } else {
                int length = length();
                if (i13 <= i || i13 > i2) {
                    i6 = i13;
                } else {
                    int i15 = i2;
                    while (i15 < length && (i15 <= i2 || charAt(i15 - 1) != '\n')) {
                        i15++;
                    }
                    i6 = i15;
                }
                if (i14 > i && i14 <= i2) {
                    i7 = i2;
                    while (i7 < length) {
                        if (i7 > i2) {
                            i8 = length;
                            if (charAt(i7 - 1) == '\n') {
                                break;
                            }
                        } else {
                            i8 = length;
                        }
                        i7++;
                        length = i8;
                    }
                } else {
                    i7 = i14;
                }
                if (i6 == i13 && i7 == i14) {
                    i14 = i7;
                    i13 = i6;
                    z = true;
                    z2 = z4;
                } else {
                    z = true;
                    setSpan(false, this.mSpans[i12], i6, i7, this.mSpanFlags[i12], true);
                    z2 = true;
                    i13 = i6;
                    i14 = i7;
                }
            }
            if (i13 == i) {
                i5 = 4096;
            } else {
                i5 = i13 == i2 + i11 ? 8192 : 0;
            }
            if (i14 == i) {
                i5 |= 16384;
            } else if (i14 == i2 + i11) {
                i5 |= 32768;
            }
            int[] iArr = this.mSpanFlags;
            iArr[i12] = i5 | iArr[i12];
            i12--;
            z3 = z;
            z4 = z2;
        }
        ?? r10 = z3;
        if (z4) {
            restoreInvariants();
        }
        moveGapTo(i2);
        if (i11 >= this.mGapLength) {
            resizeFor((this.mText.length + i11) - this.mGapLength);
        }
        boolean z5 = i10 == 0 ? r10 == true ? 1 : 0 : false;
        if (i9 > 0) {
            while (this.mSpanCount > 0 && removeSpansForChange(i, i2, z5, treeRoot())) {
            }
        }
        this.mGapStart += i11;
        this.mGapLength -= i11;
        if (this.mGapLength < r10) {
            new java.lang.Exception("mGapLength < 1").printStackTrace();
        }
        android.text.TextUtils.getChars(charSequence, i3, i4, this.mText, i);
        if (i9 > 0) {
            boolean z6 = this.mGapStart + this.mGapLength == this.mText.length ? r10 == true ? 1 : 0 : false;
            for (int i16 = 0; i16 < this.mSpanCount; i16++) {
                boolean z7 = z6;
                this.mSpanStarts[i16] = updatedIntervalBound(this.mSpanStarts[i16], i, i11, (this.mSpanFlags[i16] & 240) >> 4, z7, z5);
                this.mSpanEnds[i16] = updatedIntervalBound(this.mSpanEnds[i16], i, i11, this.mSpanFlags[i16] & 15, z7, z5);
            }
            restoreInvariants();
        }
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            java.lang.Object[] spans = spanned.getSpans(i3, i4, java.lang.Object.class);
            for (int i17 = 0; i17 < spans.length; i17++) {
                int spanStart = spanned.getSpanStart(spans[i17]);
                int spanEnd = spanned.getSpanEnd(spans[i17]);
                if (spanStart < i3) {
                    spanStart = i3;
                }
                if (spanEnd > i4) {
                    spanEnd = i4;
                }
                if (getSpanStart(spans[i17]) < 0) {
                    setSpan(false, spans[i17], (spanStart - i3) + i, (spanEnd - i3) + i, spanned.getSpanFlags(spans[i17]) | 2048, false);
                }
            }
            restoreInvariants();
        }
    }

    private int updatedIntervalBound(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        if (i >= i2 && i < this.mGapStart + this.mGapLength) {
            if (i4 == 2) {
                if (z2 || i > i2) {
                    return this.mGapStart + this.mGapLength;
                }
            } else if (i4 == 3) {
                if (z) {
                    return this.mGapStart + this.mGapLength;
                }
            } else {
                if (z2 || i < this.mGapStart - i3) {
                    return i2;
                }
                return this.mGapStart;
            }
        }
        return i;
    }

    private void removeSpan(int i, int i2) {
        java.lang.Object obj = this.mSpans[i];
        int i3 = this.mSpanStarts[i];
        int i4 = this.mSpanEnds[i];
        if (i3 > this.mGapStart) {
            i3 -= this.mGapLength;
        }
        if (i4 > this.mGapStart) {
            i4 -= this.mGapLength;
        }
        int i5 = i + 1;
        int i6 = this.mSpanCount - i5;
        java.lang.System.arraycopy(this.mSpans, i5, this.mSpans, i, i6);
        java.lang.System.arraycopy(this.mSpanStarts, i5, this.mSpanStarts, i, i6);
        java.lang.System.arraycopy(this.mSpanEnds, i5, this.mSpanEnds, i, i6);
        java.lang.System.arraycopy(this.mSpanFlags, i5, this.mSpanFlags, i, i6);
        java.lang.System.arraycopy(this.mSpanOrder, i5, this.mSpanOrder, i, i6);
        this.mSpanCount--;
        invalidateIndex(i);
        this.mSpans[this.mSpanCount] = null;
        restoreInvariants();
        if ((i2 & 512) == 0) {
            sendSpanRemoved(obj, i3, i4);
        }
    }

    @Override // android.text.Editable
    public android.text.SpannableStringBuilder replace(int i, int i2, java.lang.CharSequence charSequence) {
        return replace(i, i2, charSequence, 0, charSequence.length());
    }

    @Override // android.text.Editable
    public android.text.SpannableStringBuilder replace(int i, int i2, java.lang.CharSequence charSequence, int i3, int i4) {
        boolean z;
        int i5;
        int i6;
        android.text.TextWatcher[] textWatcherArr;
        checkRange("replace", i, i2);
        int length = this.mFilters.length;
        boolean z2 = false;
        java.lang.CharSequence charSequence2 = charSequence;
        int i7 = i3;
        int i8 = i4;
        for (int i9 = 0; i9 < length; i9++) {
            java.lang.CharSequence filter = this.mFilters[i9].filter(charSequence2, i7, i8, this, i, i2);
            if (filter != null) {
                charSequence2 = filter;
                i8 = filter.length();
                i7 = 0;
            }
        }
        int i10 = i2 - i;
        int i11 = i8 - i7;
        if (i10 != 0 || i11 != 0 || hasNonExclusiveExclusiveSpanAt(charSequence2, i7)) {
            android.text.TextWatcher[] textWatcherArr2 = (android.text.TextWatcher[]) getSpans(i, i + i10, android.text.TextWatcher.class);
            sendBeforeTextChanged(textWatcherArr2, i, i10, i11);
            boolean z3 = true;
            if (i10 == 0 || i11 == 0) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                i5 = 0;
                i6 = 0;
            } else {
                i6 = android.text.Selection.getSelectionStart(this);
                i5 = android.text.Selection.getSelectionEnd(this);
            }
            java.lang.CharSequence charSequence3 = charSequence2;
            int i12 = i5;
            int i13 = i7;
            int i14 = i6;
            change(i, i2, charSequence3, i13, i8);
            if (!z) {
                textWatcherArr = textWatcherArr2;
            } else {
                if (i14 <= i || i14 >= i2) {
                    textWatcherArr = textWatcherArr2;
                } else {
                    int intExact = i + java.lang.Math.toIntExact(((i14 - i) * i11) / i10);
                    textWatcherArr = textWatcherArr2;
                    setSpan(false, android.text.Selection.SELECTION_START, intExact, intExact, 34, true);
                    z2 = true;
                }
                if (i12 > i && i12 < i2) {
                    int intExact2 = i + java.lang.Math.toIntExact(((i12 - i) * i11) / i10);
                    setSpan(false, android.text.Selection.SELECTION_END, intExact2, intExact2, 34, true);
                } else {
                    z3 = z2;
                }
                if (z3) {
                    restoreInvariants();
                }
            }
            sendTextChanged(textWatcherArr, i, i10, i11);
            sendAfterTextChanged(textWatcherArr);
            sendToSpanWatchers(i, i2, i11 - i10);
            return this;
        }
        return this;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(java.lang.CharSequence charSequence, int i) {
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            for (java.lang.Object obj : spanned.getSpans(i, i, java.lang.Object.class)) {
                if (spanned.getSpanFlags(obj) != 33) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendToSpanWatchers(int i, int i2, int i3) {
        boolean z;
        int i4;
        int i5;
        for (int i6 = 0; i6 < this.mSpanCount; i6++) {
            int i7 = this.mSpanFlags[i6];
            if ((i7 & 2048) == 0) {
                int i8 = this.mSpanStarts[i6];
                int i9 = this.mSpanEnds[i6];
                if (i8 > this.mGapStart) {
                    i8 -= this.mGapLength;
                }
                int i10 = i8;
                if (i9 > this.mGapStart) {
                    i9 -= this.mGapLength;
                }
                int i11 = i9;
                int i12 = i2 + i3;
                boolean z2 = true;
                if (i10 > i12) {
                    if (i3 != 0) {
                        i4 = i10 - i3;
                        z = true;
                        if (i11 <= i12) {
                            if (i3 != 0) {
                                i5 = i11 - i3;
                                if (z2) {
                                    sendSpanChanged(this.mSpans[i6], i4, i5, i10, i11);
                                }
                                int[] iArr = this.mSpanFlags;
                                iArr[i6] = iArr[i6] & (-61441);
                            }
                            z2 = z;
                            i5 = i11;
                            if (z2) {
                            }
                            int[] iArr2 = this.mSpanFlags;
                            iArr2[i6] = iArr2[i6] & (-61441);
                        } else {
                            if (i11 >= i && ((i11 != i || (i7 & 16384) != 16384) && (i11 != i12 || (i7 & 32768) != 32768))) {
                                i5 = i11;
                                if (z2) {
                                }
                                int[] iArr22 = this.mSpanFlags;
                                iArr22[i6] = iArr22[i6] & (-61441);
                            }
                            z2 = z;
                            i5 = i11;
                            if (z2) {
                            }
                            int[] iArr222 = this.mSpanFlags;
                            iArr222[i6] = iArr222[i6] & (-61441);
                        }
                    }
                    z = false;
                    i4 = i10;
                    if (i11 <= i12) {
                    }
                } else {
                    if (i10 >= i && ((i10 != i || (i7 & 4096) != 4096) && (i10 != i12 || (i7 & 8192) != 8192))) {
                        z = true;
                        i4 = i10;
                        if (i11 <= i12) {
                        }
                    }
                    z = false;
                    i4 = i10;
                    if (i11 <= i12) {
                    }
                }
            }
        }
        for (int i13 = 0; i13 < this.mSpanCount; i13++) {
            if ((this.mSpanFlags[i13] & 2048) != 0) {
                int[] iArr3 = this.mSpanFlags;
                iArr3[i13] = iArr3[i13] & (-2049);
                int i14 = this.mSpanStarts[i13];
                int i15 = this.mSpanEnds[i13];
                if (i14 > this.mGapStart) {
                    i14 -= this.mGapLength;
                }
                if (i15 > this.mGapStart) {
                    i15 -= this.mGapLength;
                }
                sendSpanAdded(this.mSpans[i13], i14, i15);
            }
        }
    }

    @Override // android.text.Spannable
    public void setSpan(java.lang.Object obj, int i, int i2, int i3) {
        setSpan(true, obj, i, i2, i3, true);
    }

    private void setSpan(boolean z, java.lang.Object obj, int i, int i2, int i3, boolean z2) {
        int i4;
        int i5;
        java.lang.Integer num;
        checkRange("setSpan", i, i2);
        int i6 = (i3 & 240) >> 4;
        if (isInvalidParagraph(i, i6)) {
            if (!z2) {
                return;
            } else {
                throw new java.lang.RuntimeException("PARAGRAPH span must start at paragraph boundary (" + i + " follows " + charAt(i - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        int i7 = i3 & 15;
        if (isInvalidParagraph(i2, i7)) {
            if (!z2) {
                return;
            } else {
                throw new java.lang.RuntimeException("PARAGRAPH span must end at paragraph boundary (" + i2 + " follows " + charAt(i2 - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        if (i6 == 2 && i7 == 1 && i == i2) {
            if (z) {
                android.util.Log.e(TAG, "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length");
                return;
            }
            return;
        }
        if (i > this.mGapStart) {
            i4 = this.mGapLength + i;
        } else if (i == this.mGapStart && (i6 == 2 || (i6 == 3 && i == length()))) {
            i4 = this.mGapLength + i;
        } else {
            i4 = i;
        }
        if (i2 > this.mGapStart) {
            i5 = this.mGapLength + i2;
        } else if (i2 == this.mGapStart && (i7 == 2 || (i7 == 3 && i2 == length()))) {
            i5 = this.mGapLength + i2;
        } else {
            i5 = i2;
        }
        if (this.mIndexOfSpan == null || (num = this.mIndexOfSpan.get(obj)) == null) {
            this.mSpans = com.android.internal.util.GrowingArrayUtils.append(this.mSpans, this.mSpanCount, obj);
            this.mSpanStarts = com.android.internal.util.GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, i4);
            this.mSpanEnds = com.android.internal.util.GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, i5);
            this.mSpanFlags = com.android.internal.util.GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, i3);
            this.mSpanOrder = com.android.internal.util.GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
            invalidateIndex(this.mSpanCount);
            this.mSpanCount++;
            this.mSpanInsertCount++;
            int treeRoot = (treeRoot() * 2) + 1;
            if (this.mSpanMax.length < treeRoot) {
                this.mSpanMax = new int[treeRoot];
            }
            if (z) {
                restoreInvariants();
                sendSpanAdded(obj, i, i2);
                return;
            }
            return;
        }
        int intValue = num.intValue();
        int i8 = this.mSpanStarts[intValue];
        int i9 = this.mSpanEnds[intValue];
        if (i8 > this.mGapStart) {
            i8 -= this.mGapLength;
        }
        if (i9 > this.mGapStart) {
            i9 -= this.mGapLength;
        }
        this.mSpanStarts[intValue] = i4;
        this.mSpanEnds[intValue] = i5;
        this.mSpanFlags[intValue] = i3;
        if (z) {
            restoreInvariants();
            sendSpanChanged(obj, i8, i9, i, i2);
        }
    }

    private boolean isInvalidParagraph(int i, int i2) {
        return (i2 != 3 || i == 0 || i == length() || charAt(i - 1) == '\n') ? false : true;
    }

    @Override // android.text.Spannable
    public void removeSpan(java.lang.Object obj) {
        removeSpan(obj, 0);
    }

    @Override // android.text.Spannable
    public void removeSpan(java.lang.Object obj, int i) {
        java.lang.Integer remove;
        if (this.mIndexOfSpan != null && (remove = this.mIndexOfSpan.remove(obj)) != null) {
            removeSpan(remove.intValue(), i);
        }
    }

    private int resolveGap(int i) {
        return i > this.mGapStart ? i - this.mGapLength : i;
    }

    @Override // android.text.Spanned
    public int getSpanStart(java.lang.Object obj) {
        java.lang.Integer num;
        if (this.mIndexOfSpan == null || (num = this.mIndexOfSpan.get(obj)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanStarts[num.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(java.lang.Object obj) {
        java.lang.Integer num;
        if (this.mIndexOfSpan == null || (num = this.mIndexOfSpan.get(obj)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanEnds[num.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(java.lang.Object obj) {
        java.lang.Integer num;
        if (this.mIndexOfSpan == null || (num = this.mIndexOfSpan.get(obj)) == null) {
            return 0;
        }
        return this.mSpanFlags[num.intValue()];
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
        return (T[]) getSpans(i, i2, cls, true);
    }

    public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls, boolean z) {
        if (cls == null) {
            return (T[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.Object.class);
        }
        if (this.mSpanCount == 0) {
            return (T[]) com.android.internal.util.ArrayUtils.emptyArray(cls);
        }
        int countSpans = countSpans(i, i2, cls, treeRoot());
        if (countSpans == 0) {
            return (T[]) com.android.internal.util.ArrayUtils.emptyArray(cls);
        }
        T[] tArr = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, countSpans));
        int[] obtain = z ? obtain(countSpans) : libcore.util.EmptyArray.INT;
        int[] obtain2 = z ? obtain(countSpans) : libcore.util.EmptyArray.INT;
        getSpansRec(i, i2, cls, treeRoot(), tArr, obtain, obtain2, 0, z);
        if (z) {
            sort(tArr, obtain, obtain2);
            recycle(obtain);
            recycle(obtain2);
        }
        return tArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int countSpans(int i, int i2, java.lang.Class cls, int i3) {
        int i4;
        int i5 = i3 & 1;
        if (i5 != 0) {
            int leftChild = leftChild(i3);
            int i6 = this.mSpanMax[leftChild];
            if (i6 > this.mGapStart) {
                i6 -= this.mGapLength;
            }
            if (i6 >= i) {
                i4 = countSpans(i, i2, cls, leftChild);
                if (i3 >= this.mSpanCount) {
                    int i7 = this.mSpanStarts[i3];
                    if (i7 > this.mGapStart) {
                        i7 -= this.mGapLength;
                    }
                    if (i7 <= i2) {
                        int i8 = this.mSpanEnds[i3];
                        if (i8 > this.mGapStart) {
                            i8 -= this.mGapLength;
                        }
                        if (i8 >= i && ((i7 == i8 || i == i2 || (i7 != i2 && i8 != i)) && (java.lang.Object.class == cls || cls.isInstance(this.mSpans[i3])))) {
                            i4++;
                        }
                        if (i5 != 0) {
                            return i4 + countSpans(i, i2, cls, rightChild(i3));
                        }
                        return i4;
                    }
                    return i4;
                }
                return i4;
            }
        }
        i4 = 0;
        if (i3 >= this.mSpanCount) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private <T> int getSpansRec(int i, int i2, java.lang.Class<T> cls, int i3, T[] tArr, int[] iArr, int[] iArr2, int i4, boolean z) {
        int i5;
        int i6;
        int i7;
        int i8 = i3 & 1;
        if (i8 != 0) {
            int leftChild = leftChild(i3);
            int i9 = this.mSpanMax[leftChild];
            if (i9 > this.mGapStart) {
                i9 -= this.mGapLength;
            }
            if (i9 >= i) {
                i5 = getSpansRec(i, i2, cls, leftChild, tArr, iArr, iArr2, i4, z);
                if (i3 < this.mSpanCount) {
                    return i5;
                }
                int i10 = this.mSpanStarts[i3];
                if (i10 > this.mGapStart) {
                    i10 -= this.mGapLength;
                }
                if (i10 > i2) {
                    return i5;
                }
                int i11 = this.mSpanEnds[i3];
                if (i11 > this.mGapStart) {
                    i11 -= this.mGapLength;
                }
                if (i11 >= i && ((i10 == i11 || i == i2 || (i10 != i2 && i11 != i)) && (java.lang.Object.class == cls || cls.isInstance(this.mSpans[i3])))) {
                    int i12 = this.mSpanFlags[i3] & android.text.Spanned.SPAN_PRIORITY;
                    if (z) {
                        iArr[i5] = i12;
                        iArr2[i5] = this.mSpanOrder[i3];
                    } else if (i12 != 0) {
                        i7 = 0;
                        while (i7 < i5 && i12 <= (getSpanFlags(tArr[i7]) & android.text.Spanned.SPAN_PRIORITY)) {
                            i7++;
                        }
                        java.lang.System.arraycopy(tArr, i7, tArr, i7 + 1, i5 - i7);
                        tArr[i7] = this.mSpans[i3];
                        i6 = i5 + 1;
                    }
                    i7 = i5;
                    tArr[i7] = this.mSpans[i3];
                    i6 = i5 + 1;
                } else {
                    i6 = i5;
                }
                if (i6 < tArr.length && i8 != 0) {
                    return getSpansRec(i, i2, cls, rightChild(i3), tArr, iArr, iArr2, i6, z);
                }
                return i6;
            }
        }
        i5 = i4;
        if (i3 < this.mSpanCount) {
        }
    }

    private static int[] obtain(int i) {
        int[] iArr;
        synchronized (sCachedIntBuffer) {
            int length = sCachedIntBuffer.length - 1;
            int i2 = -1;
            while (true) {
                if (length < 0) {
                    length = i2;
                    break;
                }
                if (sCachedIntBuffer[length] != null) {
                    if (sCachedIntBuffer[length].length >= i) {
                        break;
                    }
                    if (i2 == -1) {
                        i2 = length;
                    }
                }
                length--;
            }
            iArr = null;
            if (length != -1) {
                int[] iArr2 = sCachedIntBuffer[length];
                sCachedIntBuffer[length] = null;
                iArr = iArr2;
            }
        }
        return checkSortBuffer(iArr, i);
    }

    private static void recycle(int[] iArr) {
        synchronized (sCachedIntBuffer) {
            for (int i = 0; i < sCachedIntBuffer.length; i++) {
                if (sCachedIntBuffer[i] != null && iArr.length <= sCachedIntBuffer[i].length) {
                }
                sCachedIntBuffer[i] = iArr;
            }
        }
    }

    private static int[] checkSortBuffer(int[] iArr, int i) {
        if (iArr == null || i > iArr.length) {
            return com.android.internal.util.ArrayUtils.newUnpaddedIntArray(com.android.internal.util.GrowingArrayUtils.growSize(i));
        }
        return iArr;
    }

    private final <T> void sort(T[] tArr, int[] iArr, int[] iArr2) {
        int length = tArr.length;
        for (int i = (length / 2) - 1; i >= 0; i--) {
            siftDown(i, tArr, length, iArr, iArr2);
        }
        for (int i2 = length - 1; i2 > 0; i2--) {
            T t = tArr[0];
            tArr[0] = tArr[i2];
            tArr[i2] = t;
            int i3 = iArr[0];
            iArr[0] = iArr[i2];
            iArr[i2] = i3;
            int i4 = iArr2[0];
            iArr2[0] = iArr2[i2];
            iArr2[i2] = i4;
            siftDown(0, tArr, i2, iArr, iArr2);
        }
    }

    private final <T> void siftDown(int i, T[] tArr, int i2, int[] iArr, int[] iArr2) {
        int i3 = (i * 2) + 1;
        while (i3 < i2) {
            if (i3 < i2 - 1) {
                int i4 = i3 + 1;
                if (compareSpans(i3, i4, iArr, iArr2) < 0) {
                    i3 = i4;
                }
            }
            if (compareSpans(i, i3, iArr, iArr2) < 0) {
                T t = tArr[i];
                tArr[i] = tArr[i3];
                tArr[i3] = t;
                int i5 = iArr[i];
                iArr[i] = iArr[i3];
                iArr[i3] = i5;
                int i6 = iArr2[i];
                iArr2[i] = iArr2[i3];
                iArr2[i3] = i6;
                int i7 = i3;
                i3 = (i3 * 2) + 1;
                i = i7;
            } else {
                return;
            }
        }
    }

    private final int compareSpans(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = iArr[i];
        int i4 = iArr[i2];
        if (i3 == i4) {
            return java.lang.Integer.compare(iArr2[i], iArr2[i2]);
        }
        return java.lang.Integer.compare(i4, i3);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
        if (this.mSpanCount == 0) {
            return i2;
        }
        if (cls == null) {
            cls = java.lang.Object.class;
        }
        return nextSpanTransitionRec(i, i2, cls, treeRoot());
    }

    private int nextSpanTransitionRec(int i, int i2, java.lang.Class cls, int i3) {
        int i4 = i3 & 1;
        if (i4 != 0) {
            int leftChild = leftChild(i3);
            if (resolveGap(this.mSpanMax[leftChild]) > i) {
                i2 = nextSpanTransitionRec(i, i2, cls, leftChild);
            }
        }
        if (i3 < this.mSpanCount) {
            int resolveGap = resolveGap(this.mSpanStarts[i3]);
            int resolveGap2 = resolveGap(this.mSpanEnds[i3]);
            if (resolveGap > i && resolveGap < i2 && cls.isInstance(this.mSpans[i3])) {
                i2 = resolveGap;
            }
            if (resolveGap2 > i && resolveGap2 < i2 && cls.isInstance(this.mSpans[i3])) {
                i2 = resolveGap2;
            }
            if (resolveGap < i2 && i4 != 0) {
                return nextSpanTransitionRec(i, i2, cls, rightChild(i3));
            }
            return i2;
        }
        return i2;
    }

    @Override // java.lang.CharSequence
    public java.lang.CharSequence subSequence(int i, int i2) {
        return new android.text.SpannableStringBuilder(this, i, i2);
    }

    @Override // android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        checkRange("getChars", i, i2);
        if (i2 <= this.mGapStart) {
            java.lang.System.arraycopy(this.mText, i, cArr, i3, i2 - i);
        } else if (i >= this.mGapStart) {
            java.lang.System.arraycopy(this.mText, this.mGapLength + i, cArr, i3, i2 - i);
        } else {
            java.lang.System.arraycopy(this.mText, i, cArr, i3, this.mGapStart - i);
            java.lang.System.arraycopy(this.mText, this.mGapStart + this.mGapLength, cArr, i3 + (this.mGapStart - i), i2 - this.mGapStart);
        }
    }

    @Override // java.lang.CharSequence
    public java.lang.String toString() {
        int length = length();
        char[] cArr = new char[length];
        getChars(0, length, cArr, 0);
        return new java.lang.String(cArr);
    }

    public java.lang.String substring(int i, int i2) {
        char[] cArr = new char[i2 - i];
        getChars(i, i2, cArr, 0);
        return new java.lang.String(cArr);
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(android.text.TextWatcher[] textWatcherArr, int i, int i2, int i3) {
        this.mTextWatcherDepth++;
        for (android.text.TextWatcher textWatcher : textWatcherArr) {
            textWatcher.beforeTextChanged(this, i, i2, i3);
        }
        this.mTextWatcherDepth--;
    }

    private void sendTextChanged(android.text.TextWatcher[] textWatcherArr, int i, int i2, int i3) {
        this.mTextWatcherDepth++;
        for (android.text.TextWatcher textWatcher : textWatcherArr) {
            textWatcher.onTextChanged(this, i, i2, i3);
        }
        this.mTextWatcherDepth--;
    }

    private void sendAfterTextChanged(android.text.TextWatcher[] textWatcherArr) {
        this.mTextWatcherDepth++;
        for (android.text.TextWatcher textWatcher : textWatcherArr) {
            textWatcher.afterTextChanged(this);
        }
        this.mTextWatcherDepth--;
    }

    private void sendSpanAdded(java.lang.Object obj, int i, int i2) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(i, i2, android.text.SpanWatcher.class)) {
            spanWatcher.onSpanAdded(this, obj, i, i2);
        }
    }

    private void sendSpanRemoved(java.lang.Object obj, int i, int i2) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(i, i2, android.text.SpanWatcher.class)) {
            spanWatcher.onSpanRemoved(this, obj, i, i2);
        }
    }

    private void sendSpanChanged(java.lang.Object obj, int i, int i2, int i3, int i4) {
        for (android.text.SpanWatcher spanWatcher : (android.text.SpanWatcher[]) getSpans(java.lang.Math.min(i, i3), java.lang.Math.min(java.lang.Math.max(i2, i4), length()), android.text.SpanWatcher.class)) {
            spanWatcher.onSpanChanged(this, obj, i, i2, i3, i4);
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

    @Override // android.text.GraphicsOperations
    public void drawText(android.graphics.BaseCanvas baseCanvas, int i, int i2, float f, float f2, android.graphics.Paint paint) {
        checkRange("drawText", i, i2);
        if (i2 <= this.mGapStart) {
            baseCanvas.drawText(this.mText, i, i2 - i, f, f2, paint);
            return;
        }
        if (i >= this.mGapStart) {
            baseCanvas.drawText(this.mText, i + this.mGapLength, i2 - i, f, f2, paint);
            return;
        }
        int i3 = i2 - i;
        char[] obtain = android.text.TextUtils.obtain(i3);
        getChars(i, i2, obtain, 0);
        baseCanvas.drawText(obtain, 0, i3, f, f2, paint);
        android.text.TextUtils.recycle(obtain);
    }

    @Override // android.text.GraphicsOperations
    public void drawTextRun(android.graphics.BaseCanvas baseCanvas, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        checkRange("drawTextRun", i, i2);
        int i5 = i4 - i3;
        int i6 = i2 - i;
        if (i4 <= this.mGapStart) {
            baseCanvas.drawTextRun(this.mText, i, i6, i3, i5, f, f2, z, paint);
            return;
        }
        if (i3 >= this.mGapStart) {
            baseCanvas.drawTextRun(this.mText, i + this.mGapLength, i6, i3 + this.mGapLength, i5, f, f2, z, paint);
            return;
        }
        char[] obtain = android.text.TextUtils.obtain(i5);
        getChars(i3, i4, obtain, 0);
        baseCanvas.drawTextRun(obtain, i - i3, i6, 0, i5, f, f2, z, paint);
        android.text.TextUtils.recycle(obtain);
    }

    @Override // android.text.GraphicsOperations
    public float measureText(int i, int i2, android.graphics.Paint paint) {
        checkRange("measureText", i, i2);
        if (i2 <= this.mGapStart) {
            return paint.measureText(this.mText, i, i2 - i);
        }
        if (i >= this.mGapStart) {
            return paint.measureText(this.mText, this.mGapLength + i, i2 - i);
        }
        int i3 = i2 - i;
        char[] obtain = android.text.TextUtils.obtain(i3);
        getChars(i, i2, obtain, 0);
        float measureText = paint.measureText(obtain, 0, i3);
        android.text.TextUtils.recycle(obtain);
        return measureText;
    }

    @Override // android.text.GraphicsOperations
    public int getTextWidths(int i, int i2, float[] fArr, android.graphics.Paint paint) {
        checkRange("getTextWidths", i, i2);
        if (i2 <= this.mGapStart) {
            return paint.getTextWidths(this.mText, i, i2 - i, fArr);
        }
        if (i >= this.mGapStart) {
            return paint.getTextWidths(this.mText, this.mGapLength + i, i2 - i, fArr);
        }
        int i3 = i2 - i;
        char[] obtain = android.text.TextUtils.obtain(i3);
        getChars(i, i2, obtain, 0);
        int textWidths = paint.getTextWidths(obtain, 0, i3, fArr);
        android.text.TextUtils.recycle(obtain);
        return textWidths;
    }

    @Override // android.text.GraphicsOperations
    public float getTextRunAdvances(int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, android.graphics.Paint paint) {
        int i6 = i4 - i3;
        int i7 = i2 - i;
        if (i2 <= this.mGapStart) {
            return paint.getTextRunAdvances(this.mText, i, i7, i3, i6, z, fArr, i5);
        }
        if (i >= this.mGapStart) {
            return paint.getTextRunAdvances(this.mText, i + this.mGapLength, i7, i3 + this.mGapLength, i6, z, fArr, i5);
        }
        char[] obtain = android.text.TextUtils.obtain(i6);
        getChars(i3, i4, obtain, 0);
        float textRunAdvances = paint.getTextRunAdvances(obtain, i - i3, i7, 0, i6, z, fArr, i5);
        android.text.TextUtils.recycle(obtain);
        return textRunAdvances;
    }

    @java.lang.Deprecated
    public int getTextRunCursor(int i, int i2, int i3, int i4, int i5, android.graphics.Paint paint) {
        return getTextRunCursor(i, i2, i3 == 1, i4, i5, paint);
    }

    @Override // android.text.GraphicsOperations
    public int getTextRunCursor(int i, int i2, boolean z, int i3, int i4, android.graphics.Paint paint) {
        int i5 = i2 - i;
        if (i2 <= this.mGapStart) {
            return paint.getTextRunCursor(this.mText, i, i5, z, i3, i4);
        }
        if (i >= this.mGapStart) {
            return paint.getTextRunCursor(this.mText, i + this.mGapLength, i5, z, i3 + this.mGapLength, i4) - this.mGapLength;
        }
        char[] obtain = android.text.TextUtils.obtain(i5);
        getChars(i, i2, obtain, 0);
        int textRunCursor = i + paint.getTextRunCursor(obtain, 0, i5, z, i3 - i, i4);
        android.text.TextUtils.recycle(obtain);
        return textRunCursor;
    }

    @Override // android.text.Editable
    public void setFilters(android.text.InputFilter[] inputFilterArr) {
        if (inputFilterArr == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mFilters = inputFilterArr;
    }

    @Override // android.text.Editable
    public android.text.InputFilter[] getFilters() {
        return this.mFilters;
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

    private int treeRoot() {
        return java.lang.Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private static int leftChild(int i) {
        return i - (((i + 1) & (~i)) >> 1);
    }

    private static int rightChild(int i) {
        return i + (((i + 1) & (~i)) >> 1);
    }

    private int calcMax(int i) {
        int i2;
        int i3 = i & 1;
        if (i3 == 0) {
            i2 = 0;
        } else {
            i2 = calcMax(leftChild(i));
        }
        if (i < this.mSpanCount) {
            i2 = java.lang.Math.max(i2, this.mSpanEnds[i]);
            if (i3 != 0) {
                i2 = java.lang.Math.max(i2, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = i2;
        return i2;
    }

    private void restoreInvariants() {
        if (this.mSpanCount == 0) {
            return;
        }
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpanStarts[i] < this.mSpanStarts[i - 1]) {
                java.lang.Object obj = this.mSpans[i];
                int i2 = this.mSpanStarts[i];
                int i3 = this.mSpanEnds[i];
                int i4 = this.mSpanFlags[i];
                int i5 = this.mSpanOrder[i];
                int i6 = i;
                do {
                    int i7 = i6 - 1;
                    this.mSpans[i6] = this.mSpans[i7];
                    this.mSpanStarts[i6] = this.mSpanStarts[i7];
                    this.mSpanEnds[i6] = this.mSpanEnds[i7];
                    this.mSpanFlags[i6] = this.mSpanFlags[i7];
                    this.mSpanOrder[i6] = this.mSpanOrder[i7];
                    i6--;
                    if (i6 <= 0) {
                        break;
                    }
                } while (i2 < this.mSpanStarts[i6 - 1]);
                this.mSpans[i6] = obj;
                this.mSpanStarts[i6] = i2;
                this.mSpanEnds[i6] = i3;
                this.mSpanFlags[i6] = i4;
                this.mSpanOrder[i6] = i5;
                invalidateIndex(i6);
            }
        }
        calcMax(treeRoot());
        if (this.mIndexOfSpan == null) {
            this.mIndexOfSpan = new java.util.IdentityHashMap<>();
        }
        for (int i8 = this.mLowWaterMark; i8 < this.mSpanCount; i8++) {
            java.lang.Integer num = this.mIndexOfSpan.get(this.mSpans[i8]);
            if (num == null || num.intValue() != i8) {
                this.mIndexOfSpan.put(this.mSpans[i8], java.lang.Integer.valueOf(i8));
            }
        }
        this.mLowWaterMark = Integer.MAX_VALUE;
    }

    private void invalidateIndex(int i) {
        this.mLowWaterMark = java.lang.Math.min(i, this.mLowWaterMark);
    }
}
