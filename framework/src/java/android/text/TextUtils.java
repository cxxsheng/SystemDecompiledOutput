package android.text;

/* loaded from: classes3.dex */
public class TextUtils {
    public static final int ABSOLUTE_SIZE_SPAN = 16;
    public static final int ACCESSIBILITY_CLICKABLE_SPAN = 25;
    public static final int ACCESSIBILITY_REPLACEMENT_SPAN = 29;
    public static final int ACCESSIBILITY_URL_SPAN = 26;
    public static final int ALIGNMENT_SPAN = 1;
    public static final int ANNOTATION = 18;
    public static final int BACKGROUND_COLOR_SPAN = 12;
    public static final int BULLET_SPAN = 8;
    public static final int CAP_MODE_CHARACTERS = 4096;
    public static final int CAP_MODE_SENTENCES = 16384;
    public static final int CAP_MODE_WORDS = 8192;
    public static final int EASY_EDIT_SPAN = 22;
    static final char ELLIPSIS_FILLER = 65279;
    private static final java.lang.String ELLIPSIS_NORMAL = "…";
    private static final java.lang.String ELLIPSIS_TWO_DOTS = "‥";
    public static final int FIRST_SPAN = 1;
    public static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 30;
    public static final int LEADING_MARGIN_SPAN = 10;
    public static final int LINE_BACKGROUND_SPAN = 27;
    public static final int LINE_BREAK_CONFIG_SPAN = 30;
    public static final int LINE_FEED_CODE_POINT = 10;
    public static final int LINE_HEIGHT_SPAN = 28;
    public static final int LOCALE_SPAN = 23;
    private static final int NBSP_CODE_POINT = 160;
    private static final int PARCEL_SAFE_TEXT_LENGTH = 100000;
    public static final int QUOTE_SPAN = 9;
    public static final int RELATIVE_SIZE_SPAN = 3;
    public static final int SAFE_STRING_FLAG_FIRST_LINE = 4;
    public static final int SAFE_STRING_FLAG_SINGLE_LINE = 2;
    public static final int SAFE_STRING_FLAG_TRIM = 1;
    public static final int SCALE_X_SPAN = 4;
    public static final int SPELL_CHECK_SPAN = 20;
    public static final int STRIKETHROUGH_SPAN = 5;
    public static final int STYLE_SPAN = 7;
    public static final int SUBSCRIPT_SPAN = 15;
    public static final int SUGGESTION_RANGE_SPAN = 21;
    public static final int SUGGESTION_SPAN = 19;
    public static final int SUPERSCRIPT_SPAN = 14;
    private static final java.lang.String TAG = "TextUtils";
    public static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    public static final int TYPEFACE_SPAN = 13;
    public static final int UNDERLINE_SPAN = 6;
    public static final int URL_SPAN = 11;
    public static final android.os.Parcelable.Creator<java.lang.CharSequence> CHAR_SEQUENCE_CREATOR = new android.os.Parcelable.Creator<java.lang.CharSequence>() { // from class: android.text.TextUtils.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public java.lang.CharSequence createFromParcel(android.os.Parcel parcel) {
            java.lang.Object standard;
            int readInt = parcel.readInt();
            java.lang.String readString8 = parcel.readString8();
            if (readString8 == null) {
                return null;
            }
            if (readInt == 1) {
                return readString8;
            }
            android.text.SpannableString spannableString = new android.text.SpannableString(readString8);
            while (true) {
                int readInt2 = parcel.readInt();
                if (readInt2 != 0) {
                    switch (readInt2) {
                        case 1:
                            standard = new android.text.style.AlignmentSpan.Standard(parcel);
                            break;
                        case 2:
                            standard = new android.text.style.ForegroundColorSpan(parcel);
                            break;
                        case 3:
                            standard = new android.text.style.RelativeSizeSpan(parcel);
                            break;
                        case 4:
                            standard = new android.text.style.ScaleXSpan(parcel);
                            break;
                        case 5:
                            standard = new android.text.style.StrikethroughSpan(parcel);
                            break;
                        case 6:
                            standard = new android.text.style.UnderlineSpan(parcel);
                            break;
                        case 7:
                            standard = new android.text.style.StyleSpan(parcel);
                            break;
                        case 8:
                            standard = new android.text.style.BulletSpan(parcel);
                            break;
                        case 9:
                            standard = new android.text.style.QuoteSpan(parcel);
                            break;
                        case 10:
                            standard = new android.text.style.LeadingMarginSpan.Standard(parcel);
                            break;
                        case 11:
                            standard = new android.text.style.URLSpan(parcel);
                            break;
                        case 12:
                            standard = new android.text.style.BackgroundColorSpan(parcel);
                            break;
                        case 13:
                            standard = new android.text.style.TypefaceSpan(parcel);
                            break;
                        case 14:
                            standard = new android.text.style.SuperscriptSpan(parcel);
                            break;
                        case 15:
                            standard = new android.text.style.SubscriptSpan(parcel);
                            break;
                        case 16:
                            standard = new android.text.style.AbsoluteSizeSpan(parcel);
                            break;
                        case 17:
                            standard = new android.text.style.TextAppearanceSpan(parcel);
                            break;
                        case 18:
                            standard = new android.text.Annotation(parcel);
                            break;
                        case 19:
                            standard = new android.text.style.SuggestionSpan(parcel);
                            break;
                        case 20:
                            standard = new android.text.style.SpellCheckSpan(parcel);
                            break;
                        case 21:
                            standard = new android.text.style.SuggestionRangeSpan(parcel);
                            break;
                        case 22:
                            standard = new android.text.style.EasyEditSpan(parcel);
                            break;
                        case 23:
                            standard = new android.text.style.LocaleSpan(parcel);
                            break;
                        case 24:
                            standard = new android.text.style.TtsSpan(parcel);
                            break;
                        case 25:
                            standard = new android.text.style.AccessibilityClickableSpan(parcel);
                            break;
                        case 26:
                            standard = new android.text.style.AccessibilityURLSpan(parcel);
                            break;
                        case 27:
                            standard = new android.text.style.LineBackgroundSpan.Standard(parcel);
                            break;
                        case 28:
                            standard = new android.text.style.LineHeightSpan.Standard(parcel);
                            break;
                        case 29:
                            standard = new android.text.style.AccessibilityReplacementSpan(parcel);
                            break;
                        case 30:
                            standard = android.text.style.LineBreakConfigSpan.CREATOR.createFromParcel(parcel);
                            break;
                        default:
                            throw new java.lang.RuntimeException("bogus span encoding " + readInt2);
                    }
                    android.text.TextUtils.readSpan(parcel, spannableString, standard);
                } else {
                    return spannableString;
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public java.lang.CharSequence[] newArray(int i) {
            return new java.lang.CharSequence[i];
        }
    };
    private static java.lang.Object sLock = new java.lang.Object();
    private static char[] sTemp = null;

    public interface EllipsizeCallback {
        void ellipsized(int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SafeStringFlags {
    }

    public interface StringSplitter extends java.lang.Iterable<java.lang.String> {
        void setString(java.lang.String str);
    }

    public enum TruncateAt {
        START,
        MIDDLE,
        END,
        MARQUEE,
        END_SMALL
    }

    public static java.lang.String getEllipsisString(android.text.TextUtils.TruncateAt truncateAt) {
        return truncateAt == android.text.TextUtils.TruncateAt.END_SMALL ? ELLIPSIS_TWO_DOTS : ELLIPSIS_NORMAL;
    }

    private TextUtils() {
    }

    public static void getChars(java.lang.CharSequence charSequence, int i, int i2, char[] cArr, int i3) {
        java.lang.Class<?> cls = charSequence.getClass();
        if (cls == java.lang.String.class) {
            ((java.lang.String) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (cls == java.lang.StringBuffer.class) {
            ((java.lang.StringBuffer) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (cls == java.lang.StringBuilder.class) {
            ((java.lang.StringBuilder) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (charSequence instanceof android.text.GetChars) {
            ((android.text.GetChars) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        while (i < i2) {
            cArr[i3] = charSequence.charAt(i);
            i++;
            i3++;
        }
    }

    public static int indexOf(java.lang.CharSequence charSequence, char c) {
        return indexOf(charSequence, c, 0);
    }

    public static int indexOf(java.lang.CharSequence charSequence, char c, int i) {
        if (charSequence.getClass() == java.lang.String.class) {
            return ((java.lang.String) charSequence).indexOf(c, i);
        }
        return indexOf(charSequence, c, i, charSequence.length());
    }

    public static int indexOf(java.lang.CharSequence charSequence, char c, int i, int i2) {
        java.lang.Class<?> cls = charSequence.getClass();
        if (!(charSequence instanceof android.text.GetChars) && cls != java.lang.StringBuffer.class && cls != java.lang.StringBuilder.class && cls != java.lang.String.class) {
            while (i < i2) {
                if (charSequence.charAt(i) != c) {
                    i++;
                } else {
                    return i;
                }
            }
            return -1;
        }
        char[] obtain = obtain(500);
        while (i < i2) {
            int i3 = i + 500;
            if (i3 > i2) {
                i3 = i2;
            }
            getChars(charSequence, i, i3, obtain, 0);
            int i4 = i3 - i;
            for (int i5 = 0; i5 < i4; i5++) {
                if (obtain[i5] == c) {
                    recycle(obtain);
                    return i5 + i;
                }
            }
            i = i3;
        }
        recycle(obtain);
        return -1;
    }

    public static int lastIndexOf(java.lang.CharSequence charSequence, char c) {
        return lastIndexOf(charSequence, c, charSequence.length() - 1);
    }

    public static int lastIndexOf(java.lang.CharSequence charSequence, char c, int i) {
        if (charSequence.getClass() == java.lang.String.class) {
            return ((java.lang.String) charSequence).lastIndexOf(c, i);
        }
        return lastIndexOf(charSequence, c, 0, i);
    }

    public static int lastIndexOf(java.lang.CharSequence charSequence, char c, int i, int i2) {
        if (i2 < 0) {
            return -1;
        }
        if (i2 >= charSequence.length()) {
            i2 = charSequence.length() - 1;
        }
        int i3 = i2 + 1;
        java.lang.Class<?> cls = charSequence.getClass();
        if ((charSequence instanceof android.text.GetChars) || cls == java.lang.StringBuffer.class || cls == java.lang.StringBuilder.class || cls == java.lang.String.class) {
            char[] obtain = obtain(500);
            while (i < i3) {
                int i4 = i3 - 500;
                if (i4 < i) {
                    i4 = i;
                }
                getChars(charSequence, i4, i3, obtain, 0);
                for (int i5 = (i3 - i4) - 1; i5 >= 0; i5--) {
                    if (obtain[i5] == c) {
                        recycle(obtain);
                        return i5 + i4;
                    }
                }
                i3 = i4;
            }
            recycle(obtain);
            return -1;
        }
        for (int i6 = i3 - 1; i6 >= i; i6--) {
            if (charSequence.charAt(i6) == c) {
                return i6;
            }
        }
        return -1;
    }

    public static int indexOf(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        return indexOf(charSequence, charSequence2, 0, charSequence.length());
    }

    public static int indexOf(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i) {
        return indexOf(charSequence, charSequence2, i, charSequence.length());
    }

    public static int indexOf(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i, int i2) {
        int length = charSequence2.length();
        if (length == 0) {
            return i;
        }
        char charAt = charSequence2.charAt(0);
        while (true) {
            int indexOf = indexOf(charSequence, charAt, i);
            if (indexOf > i2 - length || indexOf < 0) {
                return -1;
            }
            if (regionMatches(charSequence, indexOf, charSequence2, 0, length)) {
                return indexOf;
            }
            i = indexOf + 1;
        }
    }

    public static boolean regionMatches(java.lang.CharSequence charSequence, int i, java.lang.CharSequence charSequence2, int i2, int i3) {
        int i4 = i3 * 2;
        if (i4 < i3) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        char[] obtain = obtain(i4);
        boolean z = false;
        getChars(charSequence, i, i + i3, obtain, 0);
        getChars(charSequence2, i2, i2 + i3, obtain, i3);
        int i5 = 0;
        while (true) {
            if (i5 >= i3) {
                z = true;
                break;
            }
            if (obtain[i5] != obtain[i5 + i3]) {
                break;
            }
            i5++;
        }
        recycle(obtain);
        return z;
    }

    public static java.lang.String substring(java.lang.CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof java.lang.String) {
            return ((java.lang.String) charSequence).substring(i, i2);
        }
        if (charSequence instanceof java.lang.StringBuilder) {
            return ((java.lang.StringBuilder) charSequence).substring(i, i2);
        }
        if (charSequence instanceof java.lang.StringBuffer) {
            return ((java.lang.StringBuffer) charSequence).substring(i, i2);
        }
        int i3 = i2 - i;
        char[] obtain = obtain(i3);
        getChars(charSequence, i, i2, obtain, 0);
        java.lang.String str = new java.lang.String(obtain, 0, i3);
        recycle(obtain);
        return str;
    }

    public static java.lang.String truncateStringForUtf8Storage(java.lang.String str, int i) {
        if (i < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            if (charAt < 128) {
                i3++;
            } else if (charAt < 2048) {
                i3 += 2;
            } else if (charAt < 55296 || charAt > 57343 || str.codePointAt(i2) < 65536) {
                i3 += 3;
            } else {
                i3 += 4;
                i2 += i3 > i ? 0 : 1;
            }
            if (i3 <= i) {
                i2++;
            } else {
                return str.substring(0, i2);
            }
        }
        return str;
    }

    public static java.lang.String join(java.lang.CharSequence charSequence, java.lang.Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(objArr[0]);
        for (int i = 1; i < length; i++) {
            sb.append(charSequence);
            sb.append(objArr[i]);
        }
        return sb.toString();
    }

    public static java.lang.String join(java.lang.CharSequence charSequence, java.lang.Iterable iterable) {
        java.util.Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(charSequence);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static java.lang.String[] split(java.lang.String str, java.lang.String str2) {
        if (str.length() == 0) {
            return android.util.EmptyArray.STRING;
        }
        return str.split(str2, -1);
    }

    public static java.lang.String[] split(java.lang.String str, java.util.regex.Pattern pattern) {
        if (str.length() == 0) {
            return android.util.EmptyArray.STRING;
        }
        return pattern.split(str, -1);
    }

    public static class SimpleStringSplitter implements android.text.TextUtils.StringSplitter, java.util.Iterator<java.lang.String> {
        private char mDelimiter;
        private int mLength;
        private int mPosition;
        private java.lang.String mString;

        public SimpleStringSplitter(char c) {
            this.mDelimiter = c;
        }

        @Override // android.text.TextUtils.StringSplitter
        public void setString(java.lang.String str) {
            this.mString = str;
            this.mPosition = 0;
            this.mLength = this.mString.length();
        }

        @Override // java.lang.Iterable
        public java.util.Iterator<java.lang.String> iterator() {
            return this;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mPosition < this.mLength;
        }

        @Override // java.util.Iterator
        public java.lang.String next() {
            int indexOf = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (indexOf == -1) {
                indexOf = this.mLength;
            }
            java.lang.String substring = this.mString.substring(this.mPosition, indexOf);
            this.mPosition = indexOf + 1;
            return substring;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static java.lang.CharSequence stringOrSpannedString(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence instanceof android.text.SpannedString) {
            return charSequence;
        }
        if (charSequence instanceof android.text.Spanned) {
            return new android.text.SpannedString(charSequence);
        }
        return charSequence.toString();
    }

    public static boolean isEmpty(java.lang.CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static java.lang.String nullIfEmpty(java.lang.String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static java.lang.String emptyIfNull(java.lang.String str) {
        return str == null ? "" : str;
    }

    public static java.lang.String firstNotEmpty(java.lang.String str, java.lang.String str2) {
        return !isEmpty(str) ? str : (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
    }

    public static int length(java.lang.String str) {
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    public static java.lang.String safeIntern(java.lang.String str) {
        if (str != null) {
            return str.intern();
        }
        return null;
    }

    public static int getTrimmedLength(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) <= ' ') {
            i++;
        }
        while (length > i && charSequence.charAt(length - 1) <= ' ') {
            length--;
        }
        return length - i;
    }

    public static boolean equals(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof java.lang.String) && (charSequence2 instanceof java.lang.String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @java.lang.Deprecated
    public static java.lang.CharSequence getReverse(java.lang.CharSequence charSequence, int i, int i2) {
        return new android.text.TextUtils.Reverser(charSequence, i, i2);
    }

    private static class Reverser implements java.lang.CharSequence, android.text.GetChars {
        private int mEnd;
        private java.lang.CharSequence mSource;
        private int mStart;

        public Reverser(java.lang.CharSequence charSequence, int i, int i2) {
            this.mSource = charSequence;
            this.mStart = i;
            this.mEnd = i2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mEnd - this.mStart;
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

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return (char) android.icu.lang.UCharacter.getMirror(this.mSource.charAt((this.mEnd - 1) - i));
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            android.text.TextUtils.getChars(this.mSource, this.mStart + i, this.mStart + i2, cArr, i3);
            int i4 = i2 - i;
            android.text.AndroidCharacter.mirror(cArr, 0, i4);
            int i5 = i4 / 2;
            for (int i6 = 0; i6 < i5; i6++) {
                int i7 = i3 + i6;
                char c = cArr[i7];
                int i8 = ((i3 + i4) - i6) - 1;
                cArr[i7] = cArr[i8];
                cArr[i8] = c;
            }
        }
    }

    public static void writeToParcel(java.lang.CharSequence charSequence, android.os.Parcel parcel, int i) {
        if (charSequence instanceof android.text.Spanned) {
            parcel.writeInt(0);
            parcel.writeString8(charSequence.toString());
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            java.lang.Object[] spans = spanned.getSpans(0, charSequence.length(), java.lang.Object.class);
            for (int i2 = 0; i2 < spans.length; i2++) {
                java.lang.Object obj = spans[i2];
                java.lang.Object obj2 = spans[i2];
                if (obj2 instanceof android.text.style.CharacterStyle) {
                    obj2 = ((android.text.style.CharacterStyle) obj2).getUnderlying();
                }
                if (obj2 instanceof android.text.ParcelableSpan) {
                    android.text.ParcelableSpan parcelableSpan = (android.text.ParcelableSpan) obj2;
                    int spanTypeIdInternal = parcelableSpan.getSpanTypeIdInternal();
                    if (spanTypeIdInternal < 1 || spanTypeIdInternal > 30) {
                        android.util.Log.e(TAG, "External class \"" + parcelableSpan.getClass().getSimpleName() + "\" is attempting to use the frameworks-only ParcelableSpan interface");
                    } else {
                        parcel.writeInt(spanTypeIdInternal);
                        parcelableSpan.writeToParcelInternal(parcel, i);
                        writeWhere(parcel, spanned, obj);
                    }
                }
            }
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        if (charSequence != null) {
            parcel.writeString8(charSequence.toString());
        } else {
            parcel.writeString8(null);
        }
    }

    private static void writeWhere(android.os.Parcel parcel, android.text.Spanned spanned, java.lang.Object obj) {
        parcel.writeInt(spanned.getSpanStart(obj));
        parcel.writeInt(spanned.getSpanEnd(obj));
        parcel.writeInt(spanned.getSpanFlags(obj));
    }

    public static void dumpSpans(java.lang.CharSequence charSequence, android.util.Printer printer, java.lang.String str) {
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            for (java.lang.Object obj : spanned.getSpans(0, charSequence.length(), java.lang.Object.class)) {
                printer.println(str + ((java.lang.Object) charSequence.subSequence(spanned.getSpanStart(obj), spanned.getSpanEnd(obj))) + ": " + java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj)) + " " + obj.getClass().getCanonicalName() + " (" + spanned.getSpanStart(obj) + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + spanned.getSpanEnd(obj) + ") fl=#" + spanned.getSpanFlags(obj));
            }
            return;
        }
        printer.println(str + ((java.lang.Object) charSequence) + ": (no spans)");
    }

    public static java.lang.CharSequence replace(java.lang.CharSequence charSequence, java.lang.String[] strArr, java.lang.CharSequence[] charSequenceArr) {
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        for (int i = 0; i < strArr.length; i++) {
            int indexOf = indexOf(spannableStringBuilder, strArr[i]);
            if (indexOf >= 0) {
                spannableStringBuilder.setSpan(strArr[i], indexOf, strArr[i].length() + indexOf, 33);
            }
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            int spanStart = spannableStringBuilder.getSpanStart(strArr[i2]);
            int spanEnd = spannableStringBuilder.getSpanEnd(strArr[i2]);
            if (spanStart >= 0) {
                spannableStringBuilder.replace(spanStart, spanEnd, charSequenceArr[i2]);
            }
        }
        return spannableStringBuilder;
    }

    public static java.lang.CharSequence expandTemplate(java.lang.CharSequence charSequence, java.lang.CharSequence... charSequenceArr) {
        if (charSequenceArr.length > 9) {
            throw new java.lang.IllegalArgumentException("max of 9 values are supported");
        }
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        int i = 0;
        while (i < spannableStringBuilder.length()) {
            try {
                if (spannableStringBuilder.charAt(i) == '^') {
                    int i2 = i + 1;
                    char charAt = spannableStringBuilder.charAt(i2);
                    if (charAt == '^') {
                        spannableStringBuilder.delete(i2, i + 2);
                        i = i2;
                    } else if (java.lang.Character.isDigit(charAt)) {
                        int numericValue = java.lang.Character.getNumericValue(charAt) - 1;
                        if (numericValue < 0) {
                            throw new java.lang.IllegalArgumentException("template requests value ^" + (numericValue + 1));
                        }
                        if (numericValue >= charSequenceArr.length) {
                            throw new java.lang.IllegalArgumentException("template requests value ^" + (numericValue + 1) + "; only " + charSequenceArr.length + " provided");
                        }
                        spannableStringBuilder.replace(i, i + 2, charSequenceArr[numericValue]);
                        i += charSequenceArr[numericValue].length();
                    }
                }
                i++;
            } catch (java.lang.IndexOutOfBoundsException e) {
            }
        }
        return spannableStringBuilder;
    }

    public static int getOffsetBefore(java.lang.CharSequence charSequence, int i) {
        int i2;
        if (i == 0 || i == 1) {
            return 0;
        }
        char charAt = charSequence.charAt(i - 1);
        if (charAt >= 56320 && charAt <= 57343) {
            char charAt2 = charSequence.charAt(i - 2);
            i2 = (charAt2 >= 55296 && charAt2 <= 56319) ? i - 2 : i - 1;
        } else {
            i2 = i - 1;
        }
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) spanned.getSpans(i2, i2, android.text.style.ReplacementSpan.class);
            for (int i3 = 0; i3 < replacementSpanArr.length; i3++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i3]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i3]);
                if (spanStart < i2 && spanEnd > i2) {
                    i2 = spanStart;
                }
            }
        }
        return i2;
    }

    public static int getOffsetAfter(java.lang.CharSequence charSequence, int i) {
        int i2;
        int length = charSequence.length();
        if (i == length) {
            return length;
        }
        if (i == length - 1) {
            return length;
        }
        char charAt = charSequence.charAt(i);
        if (charAt >= 55296 && charAt <= 56319) {
            i2 = i + 1;
            char charAt2 = charSequence.charAt(i2);
            if (charAt2 >= 56320 && charAt2 <= 57343) {
                i2 = i + 2;
            }
        } else {
            i2 = i + 1;
        }
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) spanned.getSpans(i2, i2, android.text.style.ReplacementSpan.class);
            for (int i3 = 0; i3 < replacementSpanArr.length; i3++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i3]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i3]);
                if (spanStart < i2 && spanEnd > i2) {
                    i2 = spanEnd;
                }
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readSpan(android.os.Parcel parcel, android.text.Spannable spannable, java.lang.Object obj) {
        spannable.setSpan(obj, parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public static void copySpansFrom(android.text.Spanned spanned, int i, int i2, java.lang.Class cls, android.text.Spannable spannable, int i3) {
        if (cls == null) {
            cls = java.lang.Object.class;
        }
        java.lang.Object[] spans = spanned.getSpans(i, i2, cls);
        for (int i4 = 0; i4 < spans.length; i4++) {
            int spanStart = spanned.getSpanStart(spans[i4]);
            int spanEnd = spanned.getSpanEnd(spans[i4]);
            int spanFlags = spanned.getSpanFlags(spans[i4]);
            if (spanStart < i) {
                spanStart = i;
            }
            if (spanEnd > i2) {
                spanEnd = i2;
            }
            spannable.setSpan(spans[i4], (spanStart - i) + i3, (spanEnd - i) + i3, spanFlags);
        }
    }

    public static java.lang.CharSequence toUpperCase(java.util.Locale locale, java.lang.CharSequence charSequence, boolean z) {
        android.icu.text.Edits edits = new android.icu.text.Edits();
        if (!z) {
            return edits.hasChanges() ? (java.lang.StringBuilder) android.icu.text.CaseMap.toUpper().apply(locale, charSequence, new java.lang.StringBuilder(), edits) : charSequence;
        }
        android.text.SpannableStringBuilder spannableStringBuilder = (android.text.SpannableStringBuilder) android.icu.text.CaseMap.toUpper().apply(locale, charSequence, new android.text.SpannableStringBuilder(), edits);
        if (!edits.hasChanges()) {
            return charSequence;
        }
        android.icu.text.Edits.Iterator fineIterator = edits.getFineIterator();
        int length = charSequence.length();
        android.text.Spanned spanned = (android.text.Spanned) charSequence;
        for (java.lang.Object obj : spanned.getSpans(0, length, java.lang.Object.class)) {
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            spannableStringBuilder.setSpan(obj, spanStart == length ? spannableStringBuilder.length() : toUpperMapToDest(fineIterator, spanStart), spanEnd == length ? spannableStringBuilder.length() : toUpperMapToDest(fineIterator, spanEnd), spanned.getSpanFlags(obj));
        }
        return spannableStringBuilder;
    }

    private static int toUpperMapToDest(android.icu.text.Edits.Iterator iterator, int i) {
        iterator.findSourceIndex(i);
        if (i == iterator.sourceIndex()) {
            return iterator.destinationIndex();
        }
        if (iterator.hasChange()) {
            return iterator.destinationIndex() + iterator.newLength();
        }
        return iterator.destinationIndex() + (i - iterator.sourceIndex());
    }

    public static java.lang.CharSequence ellipsize(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, float f, android.text.TextUtils.TruncateAt truncateAt) {
        return ellipsize(charSequence, textPaint, f, truncateAt, false, null);
    }

    public static java.lang.CharSequence ellipsize(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, float f, android.text.TextUtils.TruncateAt truncateAt, boolean z, android.text.TextUtils.EllipsizeCallback ellipsizeCallback) {
        return ellipsize(charSequence, textPaint, f, truncateAt, z, ellipsizeCallback, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, getEllipsisString(truncateAt));
    }

    public static java.lang.CharSequence ellipsize(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, float f, android.text.TextUtils.TruncateAt truncateAt, boolean z, android.text.TextUtils.EllipsizeCallback ellipsizeCallback, android.text.TextDirectionHeuristic textDirectionHeuristic, java.lang.String str) {
        int breakText;
        int i;
        int length = charSequence.length();
        android.text.MeasuredParagraph measuredParagraph = null;
        try {
            android.text.MeasuredParagraph buildForMeasurement = android.text.MeasuredParagraph.buildForMeasurement(textPaint, charSequence, 0, charSequence.length(), textDirectionHeuristic, null);
            if (buildForMeasurement.getWholeWidth() <= f) {
                if (ellipsizeCallback != null) {
                    ellipsizeCallback.ellipsized(0, 0);
                }
                if (buildForMeasurement != null) {
                    buildForMeasurement.recycle();
                }
                return charSequence;
            }
            float measureText = f - textPaint.measureText(str);
            if (measureText < 0.0f) {
                i = length;
                breakText = 0;
            } else if (truncateAt == android.text.TextUtils.TruncateAt.START) {
                i = length - buildForMeasurement.breakText(length, false, measureText);
                breakText = 0;
            } else {
                if (truncateAt != android.text.TextUtils.TruncateAt.END && truncateAt != android.text.TextUtils.TruncateAt.END_SMALL) {
                    i = length - buildForMeasurement.breakText(length, false, measureText / 2.0f);
                    breakText = buildForMeasurement.breakText(i, true, measureText - buildForMeasurement.measure(i, length));
                }
                breakText = buildForMeasurement.breakText(length, true, measureText);
                i = length;
            }
            if (ellipsizeCallback != null) {
                ellipsizeCallback.ellipsized(breakText, i);
            }
            char[] chars = buildForMeasurement.getChars();
            android.text.Spanned spanned = charSequence instanceof android.text.Spanned ? (android.text.Spanned) charSequence : null;
            int i2 = i - breakText;
            int i3 = length - i2;
            if (z) {
                if (i3 > 0 && i2 >= str.length()) {
                    str.getChars(0, str.length(), chars, breakText);
                    breakText += str.length();
                }
                while (breakText < i) {
                    chars[breakText] = ELLIPSIS_FILLER;
                    breakText++;
                }
                java.lang.String str2 = new java.lang.String(chars, 0, length);
                if (spanned == null) {
                    if (buildForMeasurement != null) {
                        buildForMeasurement.recycle();
                    }
                    return str2;
                }
                android.text.SpannableString spannableString = new android.text.SpannableString(str2);
                copySpansFrom(spanned, 0, length, java.lang.Object.class, spannableString, 0);
                if (buildForMeasurement != null) {
                    buildForMeasurement.recycle();
                }
                return spannableString;
            }
            if (i3 == 0) {
                if (buildForMeasurement != null) {
                    buildForMeasurement.recycle();
                }
                return "";
            }
            if (spanned != null) {
                android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
                spannableStringBuilder.append(charSequence, 0, breakText);
                spannableStringBuilder.append((java.lang.CharSequence) str);
                spannableStringBuilder.append(charSequence, i, length);
                if (buildForMeasurement != null) {
                    buildForMeasurement.recycle();
                }
                return spannableStringBuilder;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder(i3 + str.length());
            sb.append(chars, 0, breakText);
            sb.append(str);
            sb.append(chars, i, length - i);
            java.lang.String sb2 = sb.toString();
            if (buildForMeasurement != null) {
                buildForMeasurement.recycle();
            }
            return sb2;
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                measuredParagraph.recycle();
            }
            throw th;
        }
    }

    public static java.lang.CharSequence listEllipsize(android.content.Context context, java.util.List<java.lang.CharSequence> list, java.lang.String str, android.text.TextPaint textPaint, float f, int i) {
        int size;
        android.content.res.Resources resources;
        android.text.BidiFormatter bidiFormatter;
        java.lang.String quantityString;
        if (list == null || (size = list.size()) == 0) {
            return "";
        }
        if (context == null) {
            bidiFormatter = android.text.BidiFormatter.getInstance();
            resources = null;
        } else {
            resources = context.getResources();
            bidiFormatter = android.text.BidiFormatter.getInstance(resources.getConfiguration().getLocales().get(0));
        }
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            spannableStringBuilder.append(bidiFormatter.unicodeWrap(list.get(i2)));
            if (i2 != size - 1) {
                spannableStringBuilder.append((java.lang.CharSequence) str);
            }
            iArr[i2] = spannableStringBuilder.length();
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            spannableStringBuilder.delete(iArr[i3], spannableStringBuilder.length());
            int i4 = (size - i3) - 1;
            if (i4 > 0) {
                if (resources == null) {
                    quantityString = ELLIPSIS_NORMAL;
                } else {
                    quantityString = resources.getQuantityString(i, i4, java.lang.Integer.valueOf(i4));
                }
                spannableStringBuilder.append(bidiFormatter.unicodeWrap((java.lang.CharSequence) quantityString));
            }
            if (textPaint.measureText(spannableStringBuilder, 0, spannableStringBuilder.length()) <= f) {
                return spannableStringBuilder;
            }
        }
        return "";
    }

    @java.lang.Deprecated
    public static java.lang.CharSequence commaEllipsize(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, float f, java.lang.String str, java.lang.String str2) {
        return commaEllipsize(charSequence, textPaint, f, str, str2, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    @java.lang.Deprecated
    public static java.lang.CharSequence commaEllipsize(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, float f, java.lang.String str, java.lang.String str2, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        char c;
        int i;
        int i2;
        android.text.MeasuredParagraph measuredParagraph = null;
        android.text.MeasuredParagraph measuredParagraph2 = null;
        try {
            int length = charSequence.length();
            measuredParagraph = android.text.MeasuredParagraph.buildForMeasurement(textPaint, charSequence, 0, length, textDirectionHeuristic, null);
            if (measuredParagraph.getWholeWidth() > f) {
                char[] chars = measuredParagraph.getChars();
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    c = ',';
                    if (i3 >= length) {
                        break;
                    }
                    if (chars[i3] == ',') {
                        i4++;
                    }
                    i3++;
                }
                int i5 = 1;
                int i6 = i4 + 1;
                java.lang.String str3 = "";
                float[] rawArray = measuredParagraph.getWidths().getRawArray();
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (i9 < length) {
                    int i10 = (int) (i7 + rawArray[i9]);
                    if (chars[i9] == c) {
                        i6--;
                        java.lang.String str4 = i6 == i5 ? " " + str : " " + java.lang.String.format(str2, java.lang.Integer.valueOf(i6));
                        i = i10;
                        int i11 = i8;
                        i2 = i9;
                        measuredParagraph2 = android.text.MeasuredParagraph.buildForMeasurement(textPaint, str4, 0, str4.length(), textDirectionHeuristic, measuredParagraph2);
                        if (i + measuredParagraph2.getWholeWidth() > f) {
                            i8 = i11;
                        } else {
                            i8 = i2 + 1;
                            str3 = str4;
                        }
                    } else {
                        i = i10;
                        i2 = i9;
                    }
                    i9 = i2 + 1;
                    i7 = i;
                    i5 = 1;
                    c = ',';
                }
                android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(str3);
                spannableStringBuilder.insert(0, charSequence, 0, i8);
                return spannableStringBuilder;
            }
            if (measuredParagraph != null) {
                measuredParagraph.recycle();
            }
            return charSequence;
        } finally {
            if (measuredParagraph != null) {
                measuredParagraph.recycle();
            }
            if (measuredParagraph2 != null) {
                measuredParagraph2.recycle();
            }
        }
    }

    static boolean couldAffectRtl(char c) {
        return (1424 <= c && c <= 2303) || c == 8206 || c == 8207 || (8234 <= c && c <= 8238) || ((8294 <= c && c <= 8297) || ((55296 <= c && c <= 57343) || ((64285 <= c && c <= 65023) || (65136 <= c && c <= 65278))));
    }

    static boolean doesNotNeedBidi(char[] cArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            if (!couldAffectRtl(cArr[i])) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    static char[] obtain(int i) {
        char[] cArr;
        synchronized (sLock) {
            cArr = sTemp;
            sTemp = null;
        }
        if (cArr == null || cArr.length < i) {
            return com.android.internal.util.ArrayUtils.newUnpaddedCharArray(i);
        }
        return cArr;
    }

    static char[] obtain$ravenwood(int i) {
        return new char[i];
    }

    static void recycle(char[] cArr) {
        if (cArr.length > 1000) {
            return;
        }
        synchronized (sLock) {
            sTemp = cArr;
        }
    }

    static void recycle$ravenwood(char[] cArr) {
    }

    public static java.lang.String htmlEncode(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(charAt);
                    break;
            }
        }
        return sb.toString();
    }

    public static java.lang.CharSequence concat(java.lang.CharSequence... charSequenceArr) {
        if (charSequenceArr.length == 0) {
            return "";
        }
        int i = 0;
        boolean z = true;
        if (charSequenceArr.length == 1) {
            return charSequenceArr[0];
        }
        int length = charSequenceArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            }
            if (charSequenceArr[i2] instanceof android.text.Spanned) {
                break;
            }
            i2++;
        }
        if (z) {
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
            int length2 = charSequenceArr.length;
            while (i < length2) {
                java.lang.CharSequence charSequence = charSequenceArr[i];
                if (charSequence == null) {
                    charSequence = "null";
                }
                spannableStringBuilder.append(charSequence);
                i++;
            }
            return new android.text.SpannedString(spannableStringBuilder);
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length3 = charSequenceArr.length;
        while (i < length3) {
            sb.append(charSequenceArr[i]);
            i++;
        }
        return sb.toString();
    }

    public static boolean isGraphic(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int codePointAt = java.lang.Character.codePointAt(charSequence, i);
            int type = java.lang.Character.getType(codePointAt);
            if (type == 15 || type == 16 || type == 19 || type == 0 || type == 13 || type == 14 || type == 12) {
                i += java.lang.Character.charCount(codePointAt);
            } else {
                return true;
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public static boolean isGraphic(char c) {
        int type = java.lang.Character.getType(c);
        return (type == 15 || type == 16 || type == 19 || type == 0 || type == 13 || type == 14 || type == 12) ? false : true;
    }

    public static boolean isDigitsOnly(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int codePointAt = java.lang.Character.codePointAt(charSequence, i);
            if (!java.lang.Character.isDigit(codePointAt)) {
                return false;
            }
            i += java.lang.Character.charCount(codePointAt);
        }
        return true;
    }

    public static boolean isPrintableAscii(char c) {
        return (' ' <= c && c <= '~') || c == '\r' || c == '\n';
    }

    public static boolean isPrintableAsciiOnly(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!isPrintableAscii(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getCapsMode(java.lang.CharSequence charSequence, int i, int i2) {
        char charAt;
        char charAt2;
        int i3 = 0;
        if (i < 0) {
            return 0;
        }
        if ((i2 & 4096) != 0) {
            i3 = 4096;
        }
        if ((i2 & 24576) == 0) {
            return i3;
        }
        while (i > 0 && ((charAt2 = charSequence.charAt(i - 1)) == '\"' || charAt2 == '\'' || java.lang.Character.getType(charAt2) == 21)) {
            i--;
        }
        int i4 = i;
        while (i4 > 0) {
            char charAt3 = charSequence.charAt(i4 - 1);
            if (charAt3 != ' ' && charAt3 != '\t') {
                break;
            }
            i4--;
        }
        if (i4 == 0 || charSequence.charAt(i4 - 1) == '\n') {
            return i3 | 8192;
        }
        if ((i2 & 16384) == 0) {
            return i != i4 ? i3 | 8192 : i3;
        }
        if (i == i4) {
            return i3;
        }
        while (i4 > 0) {
            char charAt4 = charSequence.charAt(i4 - 1);
            if (charAt4 != '\"' && charAt4 != '\'' && java.lang.Character.getType(charAt4) != 22) {
                break;
            }
            i4--;
        }
        if (i4 > 0 && ((charAt = charSequence.charAt(i4 - 1)) == '.' || charAt == '?' || charAt == '!')) {
            if (charAt == '.') {
                for (int i5 = i4 - 2; i5 >= 0; i5--) {
                    char charAt5 = charSequence.charAt(i5);
                    if (charAt5 == '.') {
                        return i3;
                    }
                    if (!java.lang.Character.isLetter(charAt5)) {
                        break;
                    }
                }
            }
            return i3 | 16384;
        }
        return i3;
    }

    public static boolean delimitedStringContains(java.lang.String str, char c, java.lang.String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return false;
        }
        int length = str.length();
        int i = -1;
        while (true) {
            i = str.indexOf(str2, i + 1);
            if (i == -1) {
                return false;
            }
            if (i <= 0 || str.charAt(i - 1) == c) {
                int length2 = str2.length() + i;
                if (length2 == length || str.charAt(length2) == c) {
                    return true;
                }
            }
        }
    }

    public static <T> T[] removeEmptySpans(T[] tArr, android.text.Spanned spanned, java.lang.Class<T> cls) {
        java.lang.Object[] objArr = null;
        int i = 0;
        for (int i2 = 0; i2 < tArr.length; i2++) {
            T t = tArr[i2];
            if (spanned.getSpanStart(t) == spanned.getSpanEnd(t)) {
                if (objArr == null) {
                    objArr = (java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, tArr.length - 1);
                    java.lang.System.arraycopy(tArr, 0, objArr, 0, i2);
                    i = i2;
                }
            } else if (objArr != null) {
                objArr[i] = t;
                i++;
            }
        }
        if (objArr != null) {
            T[] tArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i));
            java.lang.System.arraycopy(objArr, 0, tArr2, 0, i);
            return tArr2;
        }
        return tArr;
    }

    public static long packRangeInLong(int i, int i2) {
        return i2 | (i << 32);
    }

    public static int unpackRangeStartFromLong(long j) {
        return (int) (j >>> 32);
    }

    public static int unpackRangeEndFromLong(long j) {
        return (int) (j & 4294967295L);
    }

    public static int getLayoutDirectionFromLocale(java.util.Locale locale) {
        return ((locale == null || locale.equals(java.util.Locale.ROOT) || !android.icu.util.ULocale.forLocale(locale).isRightToLeft()) && !android.sysprop.DisplayProperties.debug_force_rtl().orElse(false).booleanValue()) ? 0 : 1;
    }

    public static java.lang.String formatSimple(java.lang.String str, java.lang.Object... objArr) {
        int i;
        java.lang.String str2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        int i2 = 0;
        int i3 = 0;
        while (i2 < sb.length()) {
            if (sb.charAt(i2) == '%') {
                char charAt = sb.charAt(i2 + 1);
                int i4 = 2;
                char c = 0;
                int i5 = 0;
                while (true) {
                    if ('0' <= charAt && charAt <= '9') {
                        if (c == 0) {
                            c = charAt == '0' ? '0' : ' ';
                        }
                        i5 = (i5 * 10) + java.lang.Character.digit(charAt, 10);
                        i4++;
                        charAt = sb.charAt((i2 + i4) - 1);
                    }
                }
                switch (charAt) {
                    case '%':
                        i = i3;
                        str2 = "%";
                        break;
                    case 'b':
                        if (i3 == objArr.length) {
                            throw new java.lang.IllegalArgumentException("Too few arguments");
                        }
                        i = i3 + 1;
                        java.lang.Object obj = objArr[i3];
                        if (obj instanceof java.lang.Boolean) {
                            str2 = java.lang.Boolean.toString(((java.lang.Boolean) obj).booleanValue());
                            break;
                        } else {
                            str2 = java.lang.Boolean.toString(obj != null);
                            break;
                        }
                    case 'c':
                    case 'd':
                    case 'f':
                    case 's':
                        if (i3 == objArr.length) {
                            throw new java.lang.IllegalArgumentException("Too few arguments");
                        }
                        i = i3 + 1;
                        str2 = java.lang.String.valueOf(objArr[i3]);
                        break;
                    case 'x':
                        if (i3 == objArr.length) {
                            throw new java.lang.IllegalArgumentException("Too few arguments");
                        }
                        i = i3 + 1;
                        java.lang.Object obj2 = objArr[i3];
                        if (obj2 instanceof java.lang.Integer) {
                            str2 = java.lang.Integer.toHexString(((java.lang.Integer) obj2).intValue());
                            break;
                        } else if (obj2 instanceof java.lang.Long) {
                            str2 = java.lang.Long.toHexString(((java.lang.Long) obj2).longValue());
                            break;
                        } else {
                            throw new java.lang.IllegalArgumentException("Unsupported hex type " + obj2.getClass());
                        }
                    default:
                        throw new java.lang.IllegalArgumentException("Unsupported format code " + charAt);
                }
                sb.replace(i2, i4 + i2, str2);
                int i6 = (c == '0' && str2.charAt(0) == '-') ? 1 : 0;
                for (int length = str2.length(); length < i5; length++) {
                    sb.insert(i2 + i6, c);
                }
                i2 += java.lang.Math.max(str2.length(), i5);
                i3 = i;
            } else {
                i2++;
            }
        }
        if (i3 != objArr.length) {
            throw new java.lang.IllegalArgumentException("Too many arguments");
        }
        return sb.toString();
    }

    public static boolean hasStyleSpan(android.text.Spanned spanned) {
        com.android.internal.util.Preconditions.checkArgument(spanned != null);
        java.lang.Class[] clsArr = {android.text.style.CharacterStyle.class, android.text.style.ParagraphStyle.class, android.text.style.UpdateAppearance.class};
        for (int i = 0; i < 3; i++) {
            if (spanned.nextSpanTransition(-1, spanned.length(), clsArr[i]) < spanned.length()) {
                return true;
            }
        }
        return false;
    }

    public static java.lang.CharSequence trimNoCopySpans(java.lang.CharSequence charSequence) {
        if (charSequence != null && (charSequence instanceof android.text.Spanned)) {
            return new android.text.SpannableStringBuilder(charSequence);
        }
        return charSequence;
    }

    public static void wrap(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2) {
        sb.insert(0, str);
        sb.append(str2);
    }

    public static <T extends java.lang.CharSequence> T trimToParcelableSize(T t) {
        return (T) trimToSize(t, 100000);
    }

    public static <T extends java.lang.CharSequence> T trimToSize(T t, int i) {
        com.android.internal.util.Preconditions.checkArgument(i > 0);
        if (isEmpty(t) || t.length() <= i) {
            return t;
        }
        int i2 = i - 1;
        if (java.lang.Character.isHighSurrogate(t.charAt(i2)) && java.lang.Character.isLowSurrogate(t.charAt(i))) {
            i = i2;
        }
        return (T) t.subSequence(0, i);
    }

    public static <T extends java.lang.CharSequence> T trimToLengthWithEllipsis(T t, int i) {
        T t2 = (T) trimToSize(t, i);
        if (t != null && t2.length() < t.length()) {
            return t2.toString() + android.telecom.Logging.Session.TRUNCATE_STRING;
        }
        return t2;
    }

    public static boolean isNewline(int i) {
        int type = java.lang.Character.getType(i);
        return type == 14 || type == 13 || i == 10;
    }

    public static boolean isWhitespace(int i) {
        return java.lang.Character.isWhitespace(i) || i == 160;
    }

    public static boolean isWhitespaceExceptNewline(int i) {
        return isWhitespace(i) && !isNewline(i);
    }

    public static boolean isPunctuation(int i) {
        int type = java.lang.Character.getType(i);
        return type == 23 || type == 20 || type == 22 || type == 30 || type == 29 || type == 24 || type == 21;
    }

    public static java.lang.String withoutPrefix(java.lang.String str, java.lang.String str2) {
        if (str == null || str2 == null) {
            return str2;
        }
        return str2.startsWith(str) ? str2.substring(str.length()) : str2;
    }

    public static java.lang.CharSequence makeSafeForPresentation(java.lang.String str, int i, float f, int i2) {
        boolean z = true;
        boolean z2 = (i2 & 4) != 0;
        boolean z3 = (i2 & 2) != 0;
        boolean z4 = (i2 & 1) != 0;
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(f, "ellipsizeDip");
        com.android.internal.util.Preconditions.checkFlagsArgument(i2, 7);
        if (z2 && z3) {
            z = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "Cannot set SAFE_STRING_FLAG_SINGLE_LINE and SAFE_STRING_FLAG_FIRST_LINE at thesame time");
        if (i > 0) {
            str = str.substring(0, java.lang.Math.min(str.length(), i));
        }
        android.text.TextUtils.StringWithRemovedChars stringWithRemovedChars = new android.text.TextUtils.StringWithRemovedChars(android.text.Html.fromHtml(str).toString());
        int length = stringWithRemovedChars.length();
        int i3 = -1;
        int i4 = -1;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                break;
            }
            int codePointAt = stringWithRemovedChars.codePointAt(i5);
            int type = java.lang.Character.getType(codePointAt);
            int charCount = java.lang.Character.charCount(codePointAt);
            boolean isNewline = isNewline(codePointAt);
            if (z2 && isNewline) {
                stringWithRemovedChars.removeAllCharAfter(i5);
                break;
            }
            if (z3 && isNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + charCount);
            } else if (type == 15 && !isNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + charCount);
            } else if (z4 && !isWhitespace(codePointAt)) {
                if (i3 == -1) {
                    i3 = i5;
                }
                i4 = i5 + charCount;
            }
            i5 += charCount;
        }
        if (z4) {
            if (i3 == -1) {
                stringWithRemovedChars.removeAllCharAfter(0);
            } else {
                if (i3 > 0) {
                    stringWithRemovedChars.removeAllCharBefore(i3);
                }
                if (i4 < length) {
                    stringWithRemovedChars.removeAllCharAfter(i4);
                }
            }
        }
        if (f == 0.0f) {
            return stringWithRemovedChars.toString();
        }
        if (android.graphics.Typeface.getSystemFontMap().isEmpty()) {
            int i6 = (int) ((f + 0.5f) / 23.94f);
            java.lang.String stringWithRemovedChars2 = stringWithRemovedChars.toString();
            if (isEmpty(stringWithRemovedChars2) || stringWithRemovedChars2.length() <= i6) {
                return stringWithRemovedChars2;
            }
            return ((java.lang.String) trimToSize(stringWithRemovedChars2, i6)) + getEllipsisString(android.text.TextUtils.TruncateAt.END);
        }
        android.text.TextPaint textPaint = new android.text.TextPaint();
        textPaint.setTextSize(42.0f);
        return ellipsize(stringWithRemovedChars.toString(), textPaint, f, android.text.TextUtils.TruncateAt.END);
    }

    private static class StringWithRemovedChars {
        private final java.lang.String mOriginal;
        private java.util.BitSet mRemovedChars;

        StringWithRemovedChars(java.lang.String str) {
            this.mOriginal = str;
        }

        void removeRange(int i, int i2) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new java.util.BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(i, i2);
        }

        void removeAllCharBefore(int i) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new java.util.BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(0, i);
        }

        void removeAllCharAfter(int i) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new java.util.BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(i, this.mOriginal.length());
        }

        public java.lang.String toString() {
            if (this.mRemovedChars == null) {
                return this.mOriginal;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mOriginal.length());
            for (int i = 0; i < this.mOriginal.length(); i++) {
                if (!this.mRemovedChars.get(i)) {
                    sb.append(this.mOriginal.charAt(i));
                }
            }
            return sb.toString();
        }

        int length() {
            return this.mOriginal.length();
        }

        int codePointAt(int i) {
            return this.mOriginal.codePointAt(i);
        }
    }
}
