package android.content.res;

/* loaded from: classes.dex */
public final class StringBlock implements java.io.Closeable {
    private static final java.lang.String TAG = "AssetManager";
    private static final boolean localLOGV = false;
    private long mNative;
    private android.util.SparseArray<java.lang.CharSequence> mSparseStrings;
    private java.lang.CharSequence[] mStrings;
    private final boolean mUseSparse;
    private boolean mOpen = true;
    android.content.res.StringBlock.StyleIDs mStyleIDs = null;
    private final boolean mOwnsNative = false;

    private static native long nativeCreate(byte[] bArr, int i, int i2);

    private static native void nativeDestroy(long j);

    private static native int nativeGetSize(long j);

    private static native java.lang.String nativeGetString(long j, int i);

    private static native int[] nativeGetStyle(long j, int i);

    public StringBlock(byte[] bArr, boolean z) {
        this.mNative = nativeCreate(bArr, 0, bArr.length);
        this.mUseSparse = z;
    }

    public StringBlock(byte[] bArr, int i, int i2, boolean z) {
        this.mNative = nativeCreate(bArr, i, i2);
        this.mUseSparse = z;
    }

    @java.lang.Deprecated
    public java.lang.CharSequence get(int i) {
        java.lang.CharSequence sequence = getSequence(i);
        return sequence == null ? "" : sequence;
    }

    public java.lang.CharSequence getSequence(int i) {
        synchronized (this) {
            if (this.mStrings != null) {
                java.lang.CharSequence charSequence = this.mStrings[i];
                if (charSequence != null) {
                    return charSequence;
                }
            } else if (this.mSparseStrings != null) {
                java.lang.CharSequence charSequence2 = this.mSparseStrings.get(i);
                if (charSequence2 != null) {
                    return charSequence2;
                }
            } else {
                int nativeGetSize = nativeGetSize(this.mNative);
                if (this.mUseSparse && nativeGetSize > 250) {
                    this.mSparseStrings = new android.util.SparseArray<>();
                } else {
                    this.mStrings = new java.lang.CharSequence[nativeGetSize];
                }
            }
            java.lang.String nativeGetString = nativeGetString(this.mNative, i);
            if (nativeGetString == null) {
                return null;
            }
            int[] nativeGetStyle = nativeGetStyle(this.mNative, i);
            java.lang.CharSequence charSequence3 = nativeGetString;
            if (nativeGetStyle != null) {
                if (this.mStyleIDs == null) {
                    this.mStyleIDs = new android.content.res.StringBlock.StyleIDs();
                }
                for (int i2 = 0; i2 < nativeGetStyle.length; i2 += 3) {
                    int i3 = nativeGetStyle[i2];
                    if (i3 != this.mStyleIDs.boldId && i3 != this.mStyleIDs.italicId && i3 != this.mStyleIDs.underlineId && i3 != this.mStyleIDs.ttId && i3 != this.mStyleIDs.bigId && i3 != this.mStyleIDs.smallId && i3 != this.mStyleIDs.subId && i3 != this.mStyleIDs.supId && i3 != this.mStyleIDs.strikeId && i3 != this.mStyleIDs.listItemId && i3 != this.mStyleIDs.marqueeId) {
                        java.lang.String nativeGetString2 = nativeGetString(this.mNative, i3);
                        if (nativeGetString2 == null) {
                            return null;
                        }
                        if (nativeGetString2.equals(android.app.blob.XmlTags.TAG_BLOB)) {
                            this.mStyleIDs.boldId = i3;
                        } else if (nativeGetString2.equals("i")) {
                            this.mStyleIDs.italicId = i3;
                        } else if (nativeGetString2.equals(android.app.blob.XmlTags.ATTR_UID)) {
                            this.mStyleIDs.underlineId = i3;
                        } else if (nativeGetString2.equals(android.media.TtmlUtils.TAG_TT)) {
                            this.mStyleIDs.ttId = i3;
                        } else if (nativeGetString2.equals("big")) {
                            this.mStyleIDs.bigId = i3;
                        } else if (nativeGetString2.equals("small")) {
                            this.mStyleIDs.smallId = i3;
                        } else if (nativeGetString2.equals("sup")) {
                            this.mStyleIDs.supId = i3;
                        } else if (nativeGetString2.equals(android.provider.Telephony.BaseMmsColumns.SUBJECT)) {
                            this.mStyleIDs.subId = i3;
                        } else if (nativeGetString2.equals("strike")) {
                            this.mStyleIDs.strikeId = i3;
                        } else if (nativeGetString2.equals("li")) {
                            this.mStyleIDs.listItemId = i3;
                        } else if (nativeGetString2.equals("marquee")) {
                            this.mStyleIDs.marqueeId = i3;
                        } else if (nativeGetString2.equals("nobreak")) {
                            this.mStyleIDs.mNoBreakId = i3;
                        } else if (nativeGetString2.equals("nohyphen")) {
                            this.mStyleIDs.mNoHyphenId = i3;
                        }
                    }
                }
                charSequence3 = applyStyles(nativeGetString, nativeGetStyle, this.mStyleIDs);
            }
            if (charSequence3 != null) {
                if (this.mStrings != null) {
                    this.mStrings[i] = charSequence3;
                } else {
                    this.mSparseStrings.put(i, charSequence3);
                }
            }
            return charSequence3;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            super.finalize();
        } finally {
            close();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                if (this.mOwnsNative) {
                    nativeDestroy(this.mNative);
                }
                this.mNative = 0L;
            }
        }
    }

    static final class StyleIDs {
        private int boldId = -1;
        private int italicId = -1;
        private int underlineId = -1;
        private int ttId = -1;
        private int bigId = -1;
        private int smallId = -1;
        private int subId = -1;
        private int supId = -1;
        private int strikeId = -1;
        private int listItemId = -1;
        private int marqueeId = -1;
        private int mNoBreakId = -1;
        private int mNoHyphenId = -1;

        StyleIDs() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0311 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.CharSequence applyStyles(java.lang.String str, int[] iArr, android.content.res.StringBlock.StyleIDs styleIDs) {
        java.lang.String subtag;
        int i;
        if (iArr.length == 0) {
            return str;
        }
        android.text.SpannableString spannableString = new android.text.SpannableString(str);
        for (int i2 = 0; i2 < iArr.length; i2 += 3) {
            int i3 = iArr[i2];
            if (i3 == styleIDs.boldId) {
                spannableString.setSpan(new android.text.style.StyleSpan(1, android.app.ActivityThread.currentApplication().getResources().getConfiguration().fontWeightAdjustment), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
            } else {
                int i4 = 2;
                if (i3 == styleIDs.italicId) {
                    spannableString.setSpan(new android.text.style.StyleSpan(2), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.underlineId) {
                    spannableString.setSpan(new android.text.style.UnderlineSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.ttId) {
                    spannableString.setSpan(new android.text.style.TypefaceSpan("monospace"), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.bigId) {
                    spannableString.setSpan(new android.text.style.RelativeSizeSpan(1.25f), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.smallId) {
                    spannableString.setSpan(new android.text.style.RelativeSizeSpan(0.8f), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.subId) {
                    spannableString.setSpan(new android.text.style.SubscriptSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.supId) {
                    spannableString.setSpan(new android.text.style.SuperscriptSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.strikeId) {
                    spannableString.setSpan(new android.text.style.StrikethroughSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                } else if (i3 == styleIDs.listItemId) {
                    addParagraphSpan(spannableString, new android.text.style.BulletSpan(10), iArr[i2 + 1], iArr[i2 + 2] + 1);
                } else if (i3 == styleIDs.marqueeId) {
                    spannableString.setSpan(android.text.TextUtils.TruncateAt.MARQUEE, iArr[i2 + 1], iArr[i2 + 2] + 1, 18);
                } else if (i3 == styleIDs.mNoBreakId) {
                    spannableString.setSpan(android.text.style.LineBreakConfigSpan.createNoBreakSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 17);
                } else if (i3 == styleIDs.mNoHyphenId) {
                    spannableString.setSpan(android.text.style.LineBreakConfigSpan.createNoHyphenationSpan(), iArr[i2 + 1], iArr[i2 + 2] + 1, 17);
                } else {
                    java.lang.String nativeGetString = nativeGetString(this.mNative, i3);
                    if (nativeGetString == null) {
                        return null;
                    }
                    if (nativeGetString.startsWith("font;")) {
                        java.lang.String subtag2 = subtag(nativeGetString, ";height=");
                        if (subtag2 != null) {
                            addParagraphSpan(spannableString, new android.content.res.StringBlock.Height(java.lang.Integer.parseInt(subtag2)), iArr[i2 + 1], iArr[i2 + 2] + 1);
                        }
                        java.lang.String subtag3 = subtag(nativeGetString, ";size=");
                        if (subtag3 != null) {
                            spannableString.setSpan(new android.text.style.AbsoluteSizeSpan(java.lang.Integer.parseInt(subtag3), true), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                        java.lang.String subtag4 = subtag(nativeGetString, ";fgcolor=");
                        if (subtag4 != null) {
                            spannableString.setSpan(getColor(subtag4, true), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                        java.lang.String subtag5 = subtag(nativeGetString, ";color=");
                        if (subtag5 != null) {
                            spannableString.setSpan(getColor(subtag5, true), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                        java.lang.String subtag6 = subtag(nativeGetString, ";bgcolor=");
                        if (subtag6 != null) {
                            spannableString.setSpan(getColor(subtag6, false), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                        java.lang.String subtag7 = subtag(nativeGetString, ";face=");
                        if (subtag7 != null) {
                            spannableString.setSpan(new android.text.style.TypefaceSpan(subtag7), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                    } else if (nativeGetString.startsWith("a;")) {
                        java.lang.String subtag8 = subtag(nativeGetString, ";href=");
                        if (subtag8 != null) {
                            spannableString.setSpan(new android.text.style.URLSpan(subtag8), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                    } else if (nativeGetString.startsWith("annotation;")) {
                        int length = nativeGetString.length();
                        int indexOf = nativeGetString.indexOf(59);
                        while (indexOf < length) {
                            int indexOf2 = nativeGetString.indexOf(61, indexOf);
                            if (indexOf2 < 0) {
                                break;
                            }
                            int indexOf3 = nativeGetString.indexOf(59, indexOf2);
                            if (indexOf3 < 0) {
                                indexOf3 = length;
                            }
                            spannableString.setSpan(new android.text.Annotation(nativeGetString.substring(indexOf + 1, indexOf2), nativeGetString.substring(indexOf2 + 1, indexOf3)), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                            indexOf = indexOf3;
                        }
                    } else if (nativeGetString.startsWith("lineBreakConfig;")) {
                        java.lang.String subtag9 = subtag(nativeGetString, ";style=");
                        if (subtag9 != null) {
                            if (subtag9.equals("none")) {
                                i4 = 0;
                            } else if (!subtag9.equals(android.graphics.FontListParser.STYLE_NORMAL)) {
                                if (subtag9.equals("loose")) {
                                    i4 = 1;
                                } else if (!subtag9.equals("strict")) {
                                    android.util.Log.w(TAG, "Unknown LineBreakConfig style: " + subtag9);
                                } else {
                                    i4 = 3;
                                }
                            }
                            subtag = subtag(nativeGetString, ";wordStyle=");
                            if (subtag != null) {
                                if (subtag.equals("none")) {
                                    i = 0;
                                } else if (!subtag.equals("phrase")) {
                                    android.util.Log.w(TAG, "Unknown LineBreakConfig word style: " + subtag);
                                } else {
                                    i = 1;
                                }
                                if (i4 == -1 || i != -1) {
                                    spannableString.setSpan(new android.text.style.LineBreakConfigSpan(new android.graphics.text.LineBreakConfig(i4, i, -1)), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                                }
                            }
                            i = -1;
                            if (i4 == -1) {
                            }
                            spannableString.setSpan(new android.text.style.LineBreakConfigSpan(new android.graphics.text.LineBreakConfig(i4, i, -1)), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                        }
                        i4 = -1;
                        subtag = subtag(nativeGetString, ";wordStyle=");
                        if (subtag != null) {
                        }
                        i = -1;
                        if (i4 == -1) {
                        }
                        spannableString.setSpan(new android.text.style.LineBreakConfigSpan(new android.graphics.text.LineBreakConfig(i4, i, -1)), iArr[i2 + 1], iArr[i2 + 2] + 1, 33);
                    }
                }
            }
        }
        return new android.text.SpannedString(spannableString);
    }

    private static android.text.style.CharacterStyle getColor(java.lang.String str, boolean z) {
        int i = -16777216;
        if (!android.text.TextUtils.isEmpty(str)) {
            if (str.startsWith("@")) {
                android.content.res.Resources system = android.content.res.Resources.getSystem();
                int identifier = system.getIdentifier(str.substring(1), "color", "android");
                if (identifier != 0) {
                    android.content.res.ColorStateList colorStateList = system.getColorStateList(identifier, null);
                    if (z) {
                        return new android.text.style.TextAppearanceSpan(null, 0, 0, colorStateList, null);
                    }
                    i = colorStateList.getDefaultColor();
                }
            } else {
                try {
                    i = android.graphics.Color.parseColor(str);
                } catch (java.lang.IllegalArgumentException e) {
                }
            }
        }
        if (z) {
            return new android.text.style.ForegroundColorSpan(i);
        }
        return new android.text.style.BackgroundColorSpan(i);
    }

    private static void addParagraphSpan(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
        int length = spannable.length();
        if (i != 0 && i != length && spannable.charAt(i - 1) != '\n') {
            do {
                i--;
                if (i <= 0) {
                    break;
                }
            } while (spannable.charAt(i - 1) != '\n');
        }
        if (i2 != 0 && i2 != length && spannable.charAt(i2 - 1) != '\n') {
            do {
                i2++;
                if (i2 >= length) {
                    break;
                }
            } while (spannable.charAt(i2 - 1) != '\n');
        }
        spannable.setSpan(obj, i, i2, 51);
    }

    private static java.lang.String subtag(java.lang.String str, java.lang.String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return null;
        }
        int length = indexOf + str2.length();
        int indexOf2 = str.indexOf(59, length);
        if (indexOf2 < 0) {
            return str.substring(length);
        }
        return str.substring(length, indexOf2);
    }

    private static class Height implements android.text.style.LineHeightSpan.WithDensity {
        private static float sProportion = 0.0f;
        private int mSize;

        public Height(int i) {
            this.mSize = i;
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
            chooseHeight(charSequence, i, i2, i3, i4, fontMetricsInt, null);
        }

        @Override // android.text.style.LineHeightSpan.WithDensity
        public void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.text.TextPaint textPaint) {
            int i5 = this.mSize;
            if (textPaint != null) {
                i5 = (int) (i5 * textPaint.density);
            }
            if (fontMetricsInt.bottom - fontMetricsInt.top < i5) {
                fontMetricsInt.top = fontMetricsInt.bottom - i5;
                fontMetricsInt.ascent -= i5;
                return;
            }
            if (sProportion == 0.0f) {
                android.graphics.Paint paint = new android.graphics.Paint();
                paint.setTextSize(100.0f);
                paint.getTextBounds("ABCDEFG", 0, 7, new android.graphics.Rect());
                sProportion = r4.top / paint.ascent();
            }
            int ceil = (int) java.lang.Math.ceil((-fontMetricsInt.top) * sProportion);
            if (i5 - fontMetricsInt.descent >= ceil) {
                fontMetricsInt.top = fontMetricsInt.bottom - i5;
                fontMetricsInt.ascent = fontMetricsInt.descent - i5;
                return;
            }
            if (i5 >= ceil) {
                int i6 = -ceil;
                fontMetricsInt.ascent = i6;
                fontMetricsInt.top = i6;
                int i7 = fontMetricsInt.top + i5;
                fontMetricsInt.descent = i7;
                fontMetricsInt.bottom = i7;
                return;
            }
            int i8 = -i5;
            fontMetricsInt.ascent = i8;
            fontMetricsInt.top = i8;
            fontMetricsInt.descent = 0;
            fontMetricsInt.bottom = 0;
        }
    }

    public StringBlock(long j, boolean z) {
        this.mNative = j;
        this.mUseSparse = z;
    }
}
