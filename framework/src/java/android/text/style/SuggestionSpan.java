package android.text.style;

/* loaded from: classes3.dex */
public class SuggestionSpan extends android.text.style.CharacterStyle implements android.text.ParcelableSpan {

    @java.lang.Deprecated
    public static final java.lang.String ACTION_SUGGESTION_PICKED = "android.text.style.SUGGESTION_PICKED";
    public static final android.os.Parcelable.Creator<android.text.style.SuggestionSpan> CREATOR = new android.os.Parcelable.Creator<android.text.style.SuggestionSpan>() { // from class: android.text.style.SuggestionSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.SuggestionSpan createFromParcel(android.os.Parcel parcel) {
            return new android.text.style.SuggestionSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.SuggestionSpan[] newArray(int i) {
            return new android.text.style.SuggestionSpan[i];
        }
    };
    public static final int FLAG_AUTO_CORRECTION = 4;
    public static final int FLAG_EASY_CORRECT = 1;
    public static final int FLAG_GRAMMAR_ERROR = 8;
    public static final int FLAG_MISSPELLED = 2;
    public static final int SUGGESTIONS_MAX_SIZE = 5;

    @java.lang.Deprecated
    public static final java.lang.String SUGGESTION_SPAN_PICKED_AFTER = "after";

    @java.lang.Deprecated
    public static final java.lang.String SUGGESTION_SPAN_PICKED_BEFORE = "before";

    @java.lang.Deprecated
    public static final java.lang.String SUGGESTION_SPAN_PICKED_HASHCODE = "hashcode";
    private static final java.lang.String TAG = "SuggestionSpan";
    private int mAutoCorrectionUnderlineColor;
    private float mAutoCorrectionUnderlineThickness;
    private int mEasyCorrectUnderlineColor;
    private float mEasyCorrectUnderlineThickness;
    private int mFlags;
    private int mGrammarErrorUnderlineColor;
    private float mGrammarErrorUnderlineThickness;
    private final int mHashCode;
    private final java.lang.String mLanguageTag;
    private final java.lang.String mLocaleStringForCompatibility;
    private int mMisspelledUnderlineColor;
    private float mMisspelledUnderlineThickness;
    private final java.lang.String[] mSuggestions;

    public SuggestionSpan(android.content.Context context, java.lang.String[] strArr, int i) {
        this(context, null, strArr, i, null);
    }

    public SuggestionSpan(java.util.Locale locale, java.lang.String[] strArr, int i) {
        this(null, locale, strArr, i, null);
    }

    public SuggestionSpan(android.content.Context context, java.util.Locale locale, java.lang.String[] strArr, int i, java.lang.Class<?> cls) {
        this.mSuggestions = (java.lang.String[]) java.util.Arrays.copyOf(strArr, java.lang.Math.min(5, strArr.length));
        this.mFlags = i;
        if (locale == null) {
            if (context != null) {
                locale = context.getResources().getConfiguration().locale;
            } else {
                android.util.Log.e(TAG, "No locale or context specified in SuggestionSpan constructor");
                locale = null;
            }
        }
        this.mLocaleStringForCompatibility = locale == null ? "" : locale.toString();
        this.mLanguageTag = locale != null ? locale.toLanguageTag() : "";
        this.mHashCode = hashCodeInternal(this.mSuggestions, this.mLanguageTag, this.mLocaleStringForCompatibility);
        initStyle(context);
    }

    private void initStyle(android.content.Context context) {
        if (context == null) {
            this.mMisspelledUnderlineThickness = 0.0f;
            this.mGrammarErrorUnderlineThickness = 0.0f;
            this.mEasyCorrectUnderlineThickness = 0.0f;
            this.mAutoCorrectionUnderlineThickness = 0.0f;
            this.mMisspelledUnderlineColor = -16777216;
            this.mGrammarErrorUnderlineColor = -16777216;
            this.mEasyCorrectUnderlineColor = -16777216;
            this.mAutoCorrectionUnderlineColor = -16777216;
            return;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.SuggestionSpan, com.android.internal.R.attr.textAppearanceMisspelledSuggestion, 0);
        this.mMisspelledUnderlineThickness = obtainStyledAttributes.getDimension(1, 0.0f);
        this.mMisspelledUnderlineColor = obtainStyledAttributes.getColor(0, -16777216);
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(null, com.android.internal.R.styleable.SuggestionSpan, com.android.internal.R.attr.textAppearanceGrammarErrorSuggestion, 0);
        this.mGrammarErrorUnderlineThickness = obtainStyledAttributes2.getDimension(1, 0.0f);
        this.mGrammarErrorUnderlineColor = obtainStyledAttributes2.getColor(0, -16777216);
        obtainStyledAttributes2.recycle();
        android.content.res.TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(null, com.android.internal.R.styleable.SuggestionSpan, com.android.internal.R.attr.textAppearanceEasyCorrectSuggestion, 0);
        this.mEasyCorrectUnderlineThickness = obtainStyledAttributes3.getDimension(1, 0.0f);
        this.mEasyCorrectUnderlineColor = obtainStyledAttributes3.getColor(0, -16777216);
        obtainStyledAttributes3.recycle();
        android.content.res.TypedArray obtainStyledAttributes4 = context.obtainStyledAttributes(null, com.android.internal.R.styleable.SuggestionSpan, com.android.internal.R.attr.textAppearanceAutoCorrectionSuggestion, 0);
        this.mAutoCorrectionUnderlineThickness = obtainStyledAttributes4.getDimension(1, 0.0f);
        this.mAutoCorrectionUnderlineColor = obtainStyledAttributes4.getColor(0, -16777216);
        obtainStyledAttributes4.recycle();
    }

    public SuggestionSpan(android.os.Parcel parcel) {
        this.mSuggestions = parcel.readStringArray();
        this.mFlags = parcel.readInt();
        this.mLocaleStringForCompatibility = parcel.readString();
        this.mLanguageTag = parcel.readString();
        this.mHashCode = parcel.readInt();
        this.mEasyCorrectUnderlineColor = parcel.readInt();
        this.mEasyCorrectUnderlineThickness = parcel.readFloat();
        this.mMisspelledUnderlineColor = parcel.readInt();
        this.mMisspelledUnderlineThickness = parcel.readFloat();
        this.mAutoCorrectionUnderlineColor = parcel.readInt();
        this.mAutoCorrectionUnderlineThickness = parcel.readFloat();
        this.mGrammarErrorUnderlineColor = parcel.readInt();
        this.mGrammarErrorUnderlineThickness = parcel.readFloat();
    }

    public java.lang.String[] getSuggestions() {
        return this.mSuggestions;
    }

    @java.lang.Deprecated
    public java.lang.String getLocale() {
        return this.mLocaleStringForCompatibility;
    }

    public java.util.Locale getLocaleObject() {
        if (this.mLanguageTag.isEmpty()) {
            return null;
        }
        return java.util.Locale.forLanguageTag(this.mLanguageTag);
    }

    @java.lang.Deprecated
    public java.lang.String getNotificationTargetClassName() {
        return null;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mFlags);
        parcel.writeString(this.mLocaleStringForCompatibility);
        parcel.writeString(this.mLanguageTag);
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mEasyCorrectUnderlineColor);
        parcel.writeFloat(this.mEasyCorrectUnderlineThickness);
        parcel.writeInt(this.mMisspelledUnderlineColor);
        parcel.writeFloat(this.mMisspelledUnderlineThickness);
        parcel.writeInt(this.mAutoCorrectionUnderlineColor);
        parcel.writeFloat(this.mAutoCorrectionUnderlineThickness);
        parcel.writeInt(this.mGrammarErrorUnderlineColor);
        parcel.writeFloat(this.mGrammarErrorUnderlineThickness);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 19;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.text.style.SuggestionSpan) && ((android.text.style.SuggestionSpan) obj).hashCode() == this.mHashCode;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    private static int hashCodeInternal(java.lang.String[] strArr, java.lang.String str, java.lang.String str2) {
        return java.util.Arrays.hashCode(new java.lang.Object[]{java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis()), strArr, str, str2});
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        boolean z = (this.mFlags & 2) != 0;
        boolean z2 = (this.mFlags & 1) != 0;
        boolean z3 = (this.mFlags & 4) != 0;
        boolean z4 = (this.mFlags & 8) != 0;
        if (z2) {
            if (!z && !z4) {
                textPaint.setUnderlineText(this.mEasyCorrectUnderlineColor, this.mEasyCorrectUnderlineThickness);
                return;
            } else {
                if (textPaint.underlineColor == 0) {
                    if (z4) {
                        textPaint.setUnderlineText(this.mGrammarErrorUnderlineColor, this.mGrammarErrorUnderlineThickness);
                        return;
                    } else {
                        textPaint.setUnderlineText(this.mMisspelledUnderlineColor, this.mMisspelledUnderlineThickness);
                        return;
                    }
                }
                return;
            }
        }
        if (z3) {
            textPaint.setUnderlineText(this.mAutoCorrectionUnderlineColor, this.mAutoCorrectionUnderlineThickness);
        } else if (z) {
            textPaint.setUnderlineText(this.mMisspelledUnderlineColor, this.mMisspelledUnderlineThickness);
        } else if (z4) {
            textPaint.setUnderlineText(this.mGrammarErrorUnderlineColor, this.mGrammarErrorUnderlineThickness);
        }
    }

    public int getUnderlineColor() {
        boolean z = (this.mFlags & 2) != 0;
        boolean z2 = (this.mFlags & 1) != 0;
        boolean z3 = (this.mFlags & 4) != 0;
        boolean z4 = (this.mFlags & 8) != 0;
        if (z2) {
            if (z4) {
                return this.mGrammarErrorUnderlineColor;
            }
            if (z) {
                return this.mMisspelledUnderlineColor;
            }
            return this.mEasyCorrectUnderlineColor;
        }
        if (z3) {
            return this.mAutoCorrectionUnderlineColor;
        }
        if (z) {
            return this.mMisspelledUnderlineColor;
        }
        if (z4) {
            return this.mGrammarErrorUnderlineColor;
        }
        return 0;
    }

    @java.lang.Deprecated
    public void notifySelection(android.content.Context context, java.lang.String str, int i) {
        android.util.Log.w(TAG, "notifySelection() is deprecated.  Does nothing.");
    }
}
