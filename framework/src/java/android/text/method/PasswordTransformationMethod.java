package android.text.method;

/* loaded from: classes3.dex */
public class PasswordTransformationMethod implements android.text.method.TransformationMethod, android.text.TextWatcher {
    private static char DOT = 8226;
    private static android.text.method.PasswordTransformationMethod sInstance;

    @Override // android.text.method.TransformationMethod
    public java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view) {
        if (charSequence instanceof android.text.Spannable) {
            android.text.Spannable spannable = (android.text.Spannable) charSequence;
            for (android.text.method.PasswordTransformationMethod.ViewReference viewReference : (android.text.method.PasswordTransformationMethod.ViewReference[]) spannable.getSpans(0, spannable.length(), android.text.method.PasswordTransformationMethod.ViewReference.class)) {
                spannable.removeSpan(viewReference);
            }
            removeVisibleSpans(spannable);
            spannable.setSpan(new android.text.method.PasswordTransformationMethod.ViewReference(view), 0, 0, 34);
        }
        return new android.text.method.PasswordTransformationMethod.PasswordCharSequence(charSequence);
    }

    public static android.text.method.PasswordTransformationMethod getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new android.text.method.PasswordTransformationMethod();
        return sInstance;
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence instanceof android.text.Spannable) {
            android.text.Spannable spannable = (android.text.Spannable) charSequence;
            android.text.method.PasswordTransformationMethod.ViewReference[] viewReferenceArr = (android.text.method.PasswordTransformationMethod.ViewReference[]) spannable.getSpans(0, charSequence.length(), android.text.method.PasswordTransformationMethod.ViewReference.class);
            if (viewReferenceArr.length == 0) {
                return;
            }
            android.view.View view = null;
            for (int i4 = 0; view == null && i4 < viewReferenceArr.length; i4++) {
                view = (android.view.View) viewReferenceArr[i4].get();
            }
            if (view != null && (android.text.method.TextKeyListener.getInstance().getPrefs(view.getContext()) & 8) != 0 && i3 > 0) {
                removeVisibleSpans(spannable);
                if (i3 == 1) {
                    spannable.setSpan(new android.text.method.PasswordTransformationMethod.Visible(spannable, this), i, i3 + i, 33);
                }
            }
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(android.text.Editable editable) {
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect) {
        if (!z && (charSequence instanceof android.text.Spannable)) {
            removeVisibleSpans((android.text.Spannable) charSequence);
        }
    }

    private static void removeVisibleSpans(android.text.Spannable spannable) {
        for (android.text.method.PasswordTransformationMethod.Visible visible : (android.text.method.PasswordTransformationMethod.Visible[]) spannable.getSpans(0, spannable.length(), android.text.method.PasswordTransformationMethod.Visible.class)) {
            spannable.removeSpan(visible);
        }
    }

    private static class PasswordCharSequence implements java.lang.CharSequence, android.text.GetChars {
        private java.lang.CharSequence mSource;

        public PasswordCharSequence(java.lang.CharSequence charSequence) {
            this.mSource = charSequence;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mSource.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            if (this.mSource instanceof android.text.Spanned) {
                android.text.Spanned spanned = (android.text.Spanned) this.mSource;
                int spanStart = spanned.getSpanStart(android.text.method.TextKeyListener.ACTIVE);
                int spanEnd = spanned.getSpanEnd(android.text.method.TextKeyListener.ACTIVE);
                if (i >= spanStart && i < spanEnd) {
                    return this.mSource.charAt(i);
                }
                android.text.method.PasswordTransformationMethod.Visible[] visibleArr = (android.text.method.PasswordTransformationMethod.Visible[]) spanned.getSpans(0, spanned.length(), android.text.method.PasswordTransformationMethod.Visible.class);
                for (int i2 = 0; i2 < visibleArr.length; i2++) {
                    if (spanned.getSpanStart(visibleArr[i2].mTransformer) >= 0) {
                        int spanStart2 = spanned.getSpanStart(visibleArr[i2]);
                        int spanEnd2 = spanned.getSpanEnd(visibleArr[i2]);
                        if (i >= spanStart2 && i < spanEnd2) {
                            return this.mSource.charAt(i);
                        }
                    }
                }
            }
            return android.text.method.PasswordTransformationMethod.DOT;
        }

        @Override // java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new java.lang.String(cArr);
        }

        @Override // java.lang.CharSequence
        public java.lang.String toString() {
            return subSequence(0, length()).toString();
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            int i4;
            int[] iArr;
            int i5;
            int i6;
            int[] iArr2;
            boolean z;
            android.text.TextUtils.getChars(this.mSource, i, i2, cArr, i3);
            if (!(this.mSource instanceof android.text.Spanned)) {
                i4 = -1;
                iArr = null;
                i5 = 0;
                i6 = -1;
                iArr2 = null;
            } else {
                android.text.Spanned spanned = (android.text.Spanned) this.mSource;
                i4 = spanned.getSpanStart(android.text.method.TextKeyListener.ACTIVE);
                i6 = spanned.getSpanEnd(android.text.method.TextKeyListener.ACTIVE);
                android.text.method.PasswordTransformationMethod.Visible[] visibleArr = (android.text.method.PasswordTransformationMethod.Visible[]) spanned.getSpans(0, spanned.length(), android.text.method.PasswordTransformationMethod.Visible.class);
                i5 = visibleArr.length;
                iArr = new int[i5];
                iArr2 = new int[i5];
                for (int i7 = 0; i7 < i5; i7++) {
                    if (spanned.getSpanStart(visibleArr[i7].mTransformer) >= 0) {
                        iArr[i7] = spanned.getSpanStart(visibleArr[i7]);
                        iArr2[i7] = spanned.getSpanEnd(visibleArr[i7]);
                    }
                }
            }
            for (int i8 = i; i8 < i2; i8++) {
                if (i8 < i4 || i8 >= i6) {
                    int i9 = 0;
                    while (true) {
                        if (i9 < i5) {
                            if (i8 < iArr[i9] || i8 >= iArr2[i9]) {
                                i9++;
                            } else {
                                z = true;
                                break;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                    if (!z) {
                        cArr[(i8 - i) + i3] = android.text.method.PasswordTransformationMethod.DOT;
                    }
                }
            }
        }
    }

    private static class Visible extends android.os.Handler implements android.text.style.UpdateLayout, java.lang.Runnable {
        private android.text.Spannable mText;
        private android.text.method.PasswordTransformationMethod mTransformer;

        public Visible(android.text.Spannable spannable, android.text.method.PasswordTransformationMethod passwordTransformationMethod) {
            this.mText = spannable;
            this.mTransformer = passwordTransformationMethod;
            postAtTime(this, android.os.SystemClock.uptimeMillis() + 1500);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mText.removeSpan(this);
        }
    }

    private static class ViewReference extends java.lang.ref.WeakReference<android.view.View> implements android.text.NoCopySpan {
        public ViewReference(android.view.View view) {
            super(view);
        }
    }
}
