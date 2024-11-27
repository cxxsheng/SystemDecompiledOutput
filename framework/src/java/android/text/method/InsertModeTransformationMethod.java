package android.text.method;

/* loaded from: classes3.dex */
public class InsertModeTransformationMethod implements android.text.method.TransformationMethod, android.text.TextWatcher {
    private int mEnd;
    private final android.text.method.TransformationMethod mOldTransformationMethod;
    private final boolean mSingleLine;
    private int mStart;

    public InsertModeTransformationMethod(int i, boolean z, android.text.method.TransformationMethod transformationMethod) {
        this(i, i, z, transformationMethod);
    }

    private InsertModeTransformationMethod(int i, int i2, boolean z, android.text.method.TransformationMethod transformationMethod) {
        this.mStart = i;
        this.mEnd = i2;
        this.mSingleLine = z;
        this.mOldTransformationMethod = transformationMethod;
    }

    public android.text.method.InsertModeTransformationMethod update(android.text.method.TransformationMethod transformationMethod, boolean z) {
        return new android.text.method.InsertModeTransformationMethod(this.mStart, this.mEnd, z, transformationMethod);
    }

    public android.text.method.TransformationMethod getOldTransformationMethod() {
        return this.mOldTransformationMethod;
    }

    private java.lang.CharSequence getPlaceholderText(android.view.View view) {
        if (!this.mSingleLine) {
            return "\n\n";
        }
        android.text.SpannableString spannableString = new android.text.SpannableString("�");
        spannableString.setSpan(new android.text.method.InsertModeTransformationMethod.SingleLinePlaceholderSpan((int) java.lang.Math.ceil(android.util.TypedValue.applyDimension(1, 108.0f, view.getResources().getDisplayMetrics()))), 0, 1, 33);
        return spannableString;
    }

    @Override // android.text.method.TransformationMethod
    public java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view) {
        if (this.mOldTransformationMethod != null) {
            java.lang.CharSequence transformation = this.mOldTransformationMethod.getTransformation(charSequence, view);
            if (charSequence instanceof android.text.Spannable) {
                android.text.Spannable spannable = (android.text.Spannable) charSequence;
                spannable.setSpan(this.mOldTransformationMethod, 0, spannable.length(), 18);
            }
            charSequence = transformation;
        }
        return new android.text.method.InsertModeTransformationMethod.TransformedText(charSequence, getPlaceholderText(view));
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect) {
        if (this.mOldTransformationMethod != null) {
            this.mOldTransformationMethod.onFocusChanged(view, charSequence, z, i, rect);
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (i > this.mEnd) {
            return;
        }
        int i4 = i3 - i2;
        if (i < this.mStart) {
            if (i + i2 <= this.mStart) {
                this.mStart += i4;
            } else {
                this.mStart = i;
            }
        }
        if (i2 + i <= this.mEnd) {
            this.mEnd += i4;
        } else if (i < this.mEnd) {
            this.mEnd = i + i3;
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(android.text.Editable editable) {
    }

    public class TransformedText implements android.text.method.OffsetMapping, android.text.Spanned {
        private final java.lang.CharSequence mOriginal;
        private final java.lang.CharSequence mPlaceholder;
        private final android.text.Spanned mSpannedOriginal;
        private final android.text.Spanned mSpannedPlaceholder;

        TransformedText(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
            this.mOriginal = charSequence;
            if (charSequence instanceof android.text.Spanned) {
                this.mSpannedOriginal = (android.text.Spanned) charSequence;
            } else {
                this.mSpannedOriginal = null;
            }
            this.mPlaceholder = charSequence2;
            if (charSequence2 instanceof android.text.Spanned) {
                this.mSpannedPlaceholder = (android.text.Spanned) charSequence2;
            } else {
                this.mSpannedPlaceholder = null;
            }
        }

        @Override // android.text.method.OffsetMapping
        public int originalToTransformed(int i, int i2) {
            if (i < 0) {
                return i;
            }
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, this.mOriginal.length(), android.provider.CallLog.Calls.OFFSET_PARAM_KEY);
            if (i == android.text.method.InsertModeTransformationMethod.this.mEnd && i2 == 1) {
                return i;
            }
            if (i < android.text.method.InsertModeTransformationMethod.this.mEnd) {
                return i;
            }
            return i + this.mPlaceholder.length();
        }

        @Override // android.text.method.OffsetMapping
        public int transformedToOriginal(int i, int i2) {
            if (i < 0) {
                return i;
            }
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, length(), android.provider.CallLog.Calls.OFFSET_PARAM_KEY);
            if (i < android.text.method.InsertModeTransformationMethod.this.mEnd) {
                return i;
            }
            if (i < android.text.method.InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length()) {
                return android.text.method.InsertModeTransformationMethod.this.mEnd;
            }
            return i - this.mPlaceholder.length();
        }

        @Override // android.text.method.OffsetMapping
        public void originalToTransformed(android.text.method.OffsetMapping.TextUpdate textUpdate) {
            if (textUpdate.where > android.text.method.InsertModeTransformationMethod.this.mEnd) {
                textUpdate.where += this.mPlaceholder.length();
            } else if (textUpdate.where + textUpdate.before > android.text.method.InsertModeTransformationMethod.this.mEnd) {
                textUpdate.before += this.mPlaceholder.length();
                textUpdate.after += this.mPlaceholder.length();
            }
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mOriginal.length() + this.mPlaceholder.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, length() - 1, android.graphics.FontListParser.ATTR_INDEX);
            if (i < android.text.method.InsertModeTransformationMethod.this.mEnd) {
                return this.mOriginal.charAt(i);
            }
            if (i < android.text.method.InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length()) {
                return this.mPlaceholder.charAt(i - android.text.method.InsertModeTransformationMethod.this.mEnd);
            }
            return this.mOriginal.charAt(i - this.mPlaceholder.length());
        }

        @Override // java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            if (i2 < i || i < 0 || i2 > length()) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            if (i == i2) {
                return "";
            }
            int length = this.mPlaceholder.length();
            return android.text.TextUtils.concat(this.mOriginal.subSequence(java.lang.Math.min(i, android.text.method.InsertModeTransformationMethod.this.mEnd), java.lang.Math.min(i2, android.text.method.InsertModeTransformationMethod.this.mEnd)), this.mPlaceholder.subSequence(android.util.MathUtils.constrain(i - android.text.method.InsertModeTransformationMethod.this.mEnd, 0, length), android.util.MathUtils.constrain(i2 - android.text.method.InsertModeTransformationMethod.this.mEnd, 0, length)), this.mOriginal.subSequence(java.lang.Math.max(i - length, android.text.method.InsertModeTransformationMethod.this.mEnd), java.lang.Math.max(i2 - length, android.text.method.InsertModeTransformationMethod.this.mEnd)));
        }

        @Override // java.lang.CharSequence
        public java.lang.String toString() {
            return java.lang.String.valueOf(this.mOriginal.subSequence(0, android.text.method.InsertModeTransformationMethod.this.mEnd)) + ((java.lang.Object) this.mPlaceholder) + ((java.lang.Object) this.mOriginal.subSequence(android.text.method.InsertModeTransformationMethod.this.mEnd, this.mOriginal.length()));
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(final int i, final int i2, final java.lang.Class<T> cls) {
            java.lang.Object[] objArr;
            if (i2 < i) {
                return (T[]) com.android.internal.util.ArrayUtils.emptyArray(cls);
            }
            java.lang.Object[] objArr2 = null;
            if (this.mSpannedOriginal == null) {
                objArr = null;
            } else {
                objArr = com.android.internal.util.ArrayUtils.filter(this.mSpannedOriginal.getSpans(transformedToOriginal(i, 1), transformedToOriginal(i2, 1), cls), new java.util.function.IntFunction() { // from class: android.text.method.InsertModeTransformationMethod$TransformedText$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final java.lang.Object apply(int i3) {
                        return android.text.method.InsertModeTransformationMethod.TransformedText.lambda$getSpans$0(cls, i3);
                    }
                }, new java.util.function.Predicate() { // from class: android.text.method.InsertModeTransformationMethod$TransformedText$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getSpans$1;
                        lambda$getSpans$1 = android.text.method.InsertModeTransformationMethod.TransformedText.this.lambda$getSpans$1(i, i2, obj);
                        return lambda$getSpans$1;
                    }
                });
            }
            if (this.mSpannedPlaceholder != null && android.text.method.InsertModeTransformationMethod.intersect(i, i2, android.text.method.InsertModeTransformationMethod.this.mEnd, android.text.method.InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length())) {
                objArr2 = this.mSpannedPlaceholder.getSpans(java.lang.Math.max(i - android.text.method.InsertModeTransformationMethod.this.mEnd, 0), java.lang.Math.min(i2 - android.text.method.InsertModeTransformationMethod.this.mEnd, this.mPlaceholder.length()), cls);
            }
            return (T[]) com.android.internal.util.ArrayUtils.concat(cls, objArr, objArr2);
        }

        static /* synthetic */ java.lang.Object[] lambda$getSpans$0(java.lang.Class cls, int i) {
            return (java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$getSpans$1(int i, int i2, java.lang.Object obj) {
            return android.text.method.InsertModeTransformationMethod.intersect(getSpanStart(obj), getSpanEnd(obj), i, i2);
        }

        @Override // android.text.Spanned
        public int getSpanStart(java.lang.Object obj) {
            int spanStart;
            int spanStart2;
            if (this.mSpannedOriginal != null && (spanStart2 = this.mSpannedOriginal.getSpanStart(obj)) >= 0) {
                if (spanStart2 < android.text.method.InsertModeTransformationMethod.this.mEnd || (spanStart2 == android.text.method.InsertModeTransformationMethod.this.mEnd && this.mSpannedOriginal.getSpanEnd(obj) == spanStart2)) {
                    return spanStart2;
                }
                return spanStart2 + this.mPlaceholder.length();
            }
            if (this.mSpannedPlaceholder != null && (spanStart = this.mSpannedPlaceholder.getSpanStart(obj)) >= 0) {
                return spanStart + android.text.method.InsertModeTransformationMethod.this.mEnd;
            }
            return -1;
        }

        @Override // android.text.Spanned
        public int getSpanEnd(java.lang.Object obj) {
            int spanEnd;
            int spanEnd2;
            if (this.mSpannedOriginal != null && (spanEnd2 = this.mSpannedOriginal.getSpanEnd(obj)) >= 0) {
                if (spanEnd2 <= android.text.method.InsertModeTransformationMethod.this.mEnd) {
                    return spanEnd2;
                }
                return spanEnd2 + this.mPlaceholder.length();
            }
            if (this.mSpannedPlaceholder != null && (spanEnd = this.mSpannedPlaceholder.getSpanEnd(obj)) >= 0) {
                return spanEnd + android.text.method.InsertModeTransformationMethod.this.mEnd;
            }
            return -1;
        }

        @Override // android.text.Spanned
        public int getSpanFlags(java.lang.Object obj) {
            int spanFlags;
            if (this.mSpannedOriginal != null && (spanFlags = this.mSpannedOriginal.getSpanFlags(obj)) != 0) {
                return spanFlags;
            }
            if (this.mSpannedPlaceholder != null) {
                return this.mSpannedPlaceholder.getSpanFlags(obj);
            }
            return 0;
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
            if (i2 <= i) {
                return i2;
            }
            java.lang.Object[] spans = getSpans(i, i2, cls);
            for (int i3 = 0; i3 < spans.length; i3++) {
                int spanStart = getSpanStart(spans[i3]);
                int spanEnd = getSpanEnd(spans[i3]);
                if (i < spanStart && spanStart < i2) {
                    i2 = spanStart;
                }
                if (i < spanEnd && spanEnd < i2) {
                    i2 = spanEnd;
                }
            }
            return i2;
        }

        public int getHighlightStart() {
            return android.text.method.InsertModeTransformationMethod.this.mStart;
        }

        public int getHighlightEnd() {
            return android.text.method.InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length();
        }
    }

    public static class SingleLinePlaceholderSpan extends android.text.style.ReplacementSpan {
        private final int mWidth;

        SingleLinePlaceholderSpan(int i) {
            this.mWidth = i;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(android.graphics.Paint paint, java.lang.CharSequence charSequence, int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
            return this.mWidth;
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(android.graphics.Canvas canvas, java.lang.CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, android.graphics.Paint paint) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean intersect(int i, int i2, int i3, int i4) {
        if (i > i4 || i2 < i3) {
            return false;
        }
        if (i != i2 && i3 != i4) {
            if (i == i4 || i2 == i3) {
                return false;
            }
            return true;
        }
        return true;
    }
}
