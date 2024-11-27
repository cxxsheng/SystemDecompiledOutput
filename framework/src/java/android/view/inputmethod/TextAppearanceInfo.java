package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class TextAppearanceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.TextAppearanceInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.TextAppearanceInfo>() { // from class: android.view.inputmethod.TextAppearanceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextAppearanceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.TextAppearanceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextAppearanceInfo[] newArray(int i) {
            return new android.view.inputmethod.TextAppearanceInfo[i];
        }
    };
    private final boolean mAllCaps;
    private final boolean mElegantTextHeight;
    private final boolean mFallbackLineSpacing;
    private final java.lang.String mFontFeatureSettings;
    private final java.lang.String mFontVariationSettings;
    private final int mHighlightTextColor;
    private final int mHintTextColor;
    private final float mLetterSpacing;
    private final int mLineBreakStyle;
    private final int mLineBreakWordStyle;
    private final int mLinkTextColor;
    private final int mShadowColor;
    private final float mShadowDx;
    private final float mShadowDy;
    private final float mShadowRadius;
    private final java.lang.String mSystemFontFamilyName;
    private final int mTextColor;
    private final int mTextFontWeight;
    private final android.os.LocaleList mTextLocales;
    private final float mTextScaleX;
    private final float mTextSize;
    private final int mTextStyle;

    private TextAppearanceInfo(android.view.inputmethod.TextAppearanceInfo.Builder builder) {
        this.mTextSize = builder.mTextSize;
        this.mTextLocales = builder.mTextLocales;
        this.mSystemFontFamilyName = builder.mSystemFontFamilyName;
        this.mTextFontWeight = builder.mTextFontWeight;
        this.mTextStyle = builder.mTextStyle;
        this.mAllCaps = builder.mAllCaps;
        this.mShadowDx = builder.mShadowDx;
        this.mShadowDy = builder.mShadowDy;
        this.mShadowRadius = builder.mShadowRadius;
        this.mShadowColor = builder.mShadowColor;
        this.mElegantTextHeight = builder.mElegantTextHeight;
        this.mFallbackLineSpacing = builder.mFallbackLineSpacing;
        this.mLetterSpacing = builder.mLetterSpacing;
        this.mFontFeatureSettings = builder.mFontFeatureSettings;
        this.mFontVariationSettings = builder.mFontVariationSettings;
        this.mLineBreakStyle = builder.mLineBreakStyle;
        this.mLineBreakWordStyle = builder.mLineBreakWordStyle;
        this.mTextScaleX = builder.mTextScaleX;
        this.mHighlightTextColor = builder.mHighlightTextColor;
        this.mTextColor = builder.mTextColor;
        this.mHintTextColor = builder.mHintTextColor;
        this.mLinkTextColor = builder.mLinkTextColor;
    }

    public static android.view.inputmethod.TextAppearanceInfo createFromTextView(android.widget.TextView textView) {
        java.lang.String str;
        int i;
        int i2;
        int selectionStart = textView.getSelectionStart();
        java.lang.CharSequence text = textView.getText();
        android.text.TextPaint textPaint = new android.text.TextPaint();
        textPaint.set(textView.getPaint());
        if ((text instanceof android.text.Spanned) && text.length() > 0 && selectionStart > 0) {
            android.text.Spanned spanned = (android.text.Spanned) text;
            int i3 = selectionStart - 1;
            android.text.style.CharacterStyle[] characterStyleArr = (android.text.style.CharacterStyle[]) spanned.getSpans(i3, i3, android.text.style.CharacterStyle.class);
            if (characterStyleArr != null) {
                for (android.text.style.CharacterStyle characterStyle : characterStyleArr) {
                    if (spanned.getSpanStart(characterStyle) <= i3 && i3 < spanned.getSpanEnd(characterStyle)) {
                        characterStyle.updateDrawState(textPaint);
                    }
                }
            }
        }
        android.graphics.Typeface typeface = textPaint.getTypeface();
        if (typeface == null) {
            str = null;
            i = -1;
            i2 = 0;
        } else {
            str = typeface.getSystemFontFamilyName();
            i = typeface.getWeight();
            i2 = typeface.getStyle();
        }
        android.view.inputmethod.TextAppearanceInfo.Builder builder = new android.view.inputmethod.TextAppearanceInfo.Builder();
        builder.setTextSize(textPaint.getTextSize()).setTextLocales(textPaint.getTextLocales()).setSystemFontFamilyName(str).setTextFontWeight(i).setTextStyle(i2).setShadowDx(textPaint.getShadowLayerDx()).setShadowDy(textPaint.getShadowLayerDy()).setShadowRadius(textPaint.getShadowLayerRadius()).setShadowColor(textPaint.getShadowLayerColor()).setElegantTextHeight(textPaint.isElegantTextHeight()).setLetterSpacing(textPaint.getLetterSpacing()).setFontFeatureSettings(textPaint.getFontFeatureSettings()).setFontVariationSettings(textPaint.getFontVariationSettings()).setTextScaleX(textPaint.getTextScaleX()).setTextColor(text.length() == 0 ? textView.getCurrentTextColor() : textPaint.getColor()).setLinkTextColor(textPaint.linkColor).setAllCaps(textView.isAllCaps()).setFallbackLineSpacing(textView.isFallbackLineSpacing()).setLineBreakStyle(textView.getLineBreakStyle()).setLineBreakWordStyle(textView.getLineBreakWordStyle()).setHighlightTextColor(textView.getHighlightColor()).setHintTextColor(textView.getCurrentHintTextColor());
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mTextSize);
        this.mTextLocales.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mAllCaps);
        parcel.writeString8(this.mSystemFontFamilyName);
        parcel.writeInt(this.mTextFontWeight);
        parcel.writeInt(this.mTextStyle);
        parcel.writeFloat(this.mShadowDx);
        parcel.writeFloat(this.mShadowDy);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeInt(this.mShadowColor);
        parcel.writeBoolean(this.mElegantTextHeight);
        parcel.writeBoolean(this.mFallbackLineSpacing);
        parcel.writeFloat(this.mLetterSpacing);
        parcel.writeString8(this.mFontFeatureSettings);
        parcel.writeString8(this.mFontVariationSettings);
        parcel.writeInt(this.mLineBreakStyle);
        parcel.writeInt(this.mLineBreakWordStyle);
        parcel.writeFloat(this.mTextScaleX);
        parcel.writeInt(this.mHighlightTextColor);
        parcel.writeInt(this.mTextColor);
        parcel.writeInt(this.mHintTextColor);
        parcel.writeInt(this.mLinkTextColor);
    }

    TextAppearanceInfo(android.os.Parcel parcel) {
        this.mTextSize = parcel.readFloat();
        this.mTextLocales = android.os.LocaleList.CREATOR.createFromParcel(parcel);
        this.mAllCaps = parcel.readBoolean();
        this.mSystemFontFamilyName = parcel.readString8();
        this.mTextFontWeight = parcel.readInt();
        this.mTextStyle = parcel.readInt();
        this.mShadowDx = parcel.readFloat();
        this.mShadowDy = parcel.readFloat();
        this.mShadowRadius = parcel.readFloat();
        this.mShadowColor = parcel.readInt();
        this.mElegantTextHeight = parcel.readBoolean();
        this.mFallbackLineSpacing = parcel.readBoolean();
        this.mLetterSpacing = parcel.readFloat();
        this.mFontFeatureSettings = parcel.readString8();
        this.mFontVariationSettings = parcel.readString8();
        this.mLineBreakStyle = parcel.readInt();
        this.mLineBreakWordStyle = parcel.readInt();
        this.mTextScaleX = parcel.readFloat();
        this.mHighlightTextColor = parcel.readInt();
        this.mTextColor = parcel.readInt();
        this.mHintTextColor = parcel.readInt();
        this.mLinkTextColor = parcel.readInt();
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public android.os.LocaleList getTextLocales() {
        return this.mTextLocales;
    }

    public java.lang.String getSystemFontFamilyName() {
        return this.mSystemFontFamilyName;
    }

    public int getTextFontWeight() {
        return this.mTextFontWeight;
    }

    public int getTextStyle() {
        return this.mTextStyle;
    }

    public boolean isAllCaps() {
        return this.mAllCaps;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public boolean isElegantTextHeight() {
        return this.mElegantTextHeight;
    }

    public boolean isFallbackLineSpacing() {
        return this.mFallbackLineSpacing;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    public java.lang.String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public java.lang.String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public float getTextScaleX() {
        return this.mTextScaleX;
    }

    public int getHighlightTextColor() {
        return this.mHighlightTextColor;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getHintTextColor() {
        return this.mHintTextColor;
    }

    public int getLinkTextColor() {
        return this.mLinkTextColor;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.inputmethod.TextAppearanceInfo)) {
            return false;
        }
        android.view.inputmethod.TextAppearanceInfo textAppearanceInfo = (android.view.inputmethod.TextAppearanceInfo) obj;
        return java.lang.Float.compare(textAppearanceInfo.mTextSize, this.mTextSize) == 0 && this.mTextFontWeight == textAppearanceInfo.mTextFontWeight && this.mTextStyle == textAppearanceInfo.mTextStyle && this.mAllCaps == textAppearanceInfo.mAllCaps && java.lang.Float.compare(textAppearanceInfo.mShadowDx, this.mShadowDx) == 0 && java.lang.Float.compare(textAppearanceInfo.mShadowDy, this.mShadowDy) == 0 && java.lang.Float.compare(textAppearanceInfo.mShadowRadius, this.mShadowRadius) == 0 && textAppearanceInfo.mShadowColor == this.mShadowColor && this.mElegantTextHeight == textAppearanceInfo.mElegantTextHeight && this.mFallbackLineSpacing == textAppearanceInfo.mFallbackLineSpacing && java.lang.Float.compare(textAppearanceInfo.mLetterSpacing, this.mLetterSpacing) == 0 && this.mLineBreakStyle == textAppearanceInfo.mLineBreakStyle && this.mLineBreakWordStyle == textAppearanceInfo.mLineBreakWordStyle && this.mHighlightTextColor == textAppearanceInfo.mHighlightTextColor && this.mTextColor == textAppearanceInfo.mTextColor && this.mLinkTextColor == textAppearanceInfo.mLinkTextColor && this.mHintTextColor == textAppearanceInfo.mHintTextColor && java.util.Objects.equals(this.mTextLocales, textAppearanceInfo.mTextLocales) && java.util.Objects.equals(this.mSystemFontFamilyName, textAppearanceInfo.mSystemFontFamilyName) && java.util.Objects.equals(this.mFontFeatureSettings, textAppearanceInfo.mFontFeatureSettings) && java.util.Objects.equals(this.mFontVariationSettings, textAppearanceInfo.mFontVariationSettings) && java.lang.Float.compare(textAppearanceInfo.mTextScaleX, this.mTextScaleX) == 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mTextSize), this.mTextLocales, this.mSystemFontFamilyName, java.lang.Integer.valueOf(this.mTextFontWeight), java.lang.Integer.valueOf(this.mTextStyle), java.lang.Boolean.valueOf(this.mAllCaps), java.lang.Float.valueOf(this.mShadowDx), java.lang.Float.valueOf(this.mShadowDy), java.lang.Float.valueOf(this.mShadowRadius), java.lang.Integer.valueOf(this.mShadowColor), java.lang.Boolean.valueOf(this.mElegantTextHeight), java.lang.Boolean.valueOf(this.mFallbackLineSpacing), java.lang.Float.valueOf(this.mLetterSpacing), this.mFontFeatureSettings, this.mFontVariationSettings, java.lang.Integer.valueOf(this.mLineBreakStyle), java.lang.Integer.valueOf(this.mLineBreakWordStyle), java.lang.Float.valueOf(this.mTextScaleX), java.lang.Integer.valueOf(this.mHighlightTextColor), java.lang.Integer.valueOf(this.mTextColor), java.lang.Integer.valueOf(this.mHintTextColor), java.lang.Integer.valueOf(this.mLinkTextColor));
    }

    public java.lang.String toString() {
        return "TextAppearanceInfo{mTextSize=" + this.mTextSize + ", mTextLocales=" + this.mTextLocales + ", mSystemFontFamilyName='" + this.mSystemFontFamilyName + android.text.format.DateFormat.QUOTE + ", mTextFontWeight=" + this.mTextFontWeight + ", mTextStyle=" + this.mTextStyle + ", mAllCaps=" + this.mAllCaps + ", mShadowDx=" + this.mShadowDx + ", mShadowDy=" + this.mShadowDy + ", mShadowRadius=" + this.mShadowRadius + ", mShadowColor=" + this.mShadowColor + ", mElegantTextHeight=" + this.mElegantTextHeight + ", mFallbackLineSpacing=" + this.mFallbackLineSpacing + ", mLetterSpacing=" + this.mLetterSpacing + ", mFontFeatureSettings='" + this.mFontFeatureSettings + android.text.format.DateFormat.QUOTE + ", mFontVariationSettings='" + this.mFontVariationSettings + android.text.format.DateFormat.QUOTE + ", mLineBreakStyle=" + this.mLineBreakStyle + ", mLineBreakWordStyle=" + this.mLineBreakWordStyle + ", mTextScaleX=" + this.mTextScaleX + ", mHighlightTextColor=" + this.mHighlightTextColor + ", mTextColor=" + this.mTextColor + ", mHintTextColor=" + this.mHintTextColor + ", mLinkTextColor=" + this.mLinkTextColor + '}';
    }

    public static final class Builder {
        private float mTextSize = -1.0f;
        private android.os.LocaleList mTextLocales = android.os.LocaleList.getAdjustedDefault();
        private java.lang.String mSystemFontFamilyName = null;
        private int mTextFontWeight = -1;
        private int mTextStyle = 0;
        private boolean mAllCaps = false;
        private float mShadowDx = 0.0f;
        private float mShadowDy = 0.0f;
        private float mShadowRadius = 0.0f;
        private int mShadowColor = 0;
        private boolean mElegantTextHeight = false;
        private boolean mFallbackLineSpacing = false;
        private float mLetterSpacing = 0.0f;
        private java.lang.String mFontFeatureSettings = null;
        private java.lang.String mFontVariationSettings = null;
        private int mLineBreakStyle = 0;
        private int mLineBreakWordStyle = 0;
        private float mTextScaleX = 1.0f;
        private int mHighlightTextColor = 0;
        private int mTextColor = 0;
        private int mHintTextColor = 0;
        private int mLinkTextColor = 0;

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextSize(float f) {
            this.mTextSize = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextLocales(android.os.LocaleList localeList) {
            this.mTextLocales = localeList;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setSystemFontFamilyName(java.lang.String str) {
            this.mSystemFontFamilyName = str;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextFontWeight(int i) {
            this.mTextFontWeight = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextStyle(int i) {
            this.mTextStyle = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setAllCaps(boolean z) {
            this.mAllCaps = z;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setShadowDx(float f) {
            this.mShadowDx = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setShadowDy(float f) {
            this.mShadowDy = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setShadowRadius(float f) {
            this.mShadowRadius = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setShadowColor(int i) {
            this.mShadowColor = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setElegantTextHeight(boolean z) {
            this.mElegantTextHeight = z;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setFallbackLineSpacing(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setLetterSpacing(float f) {
            this.mLetterSpacing = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setFontFeatureSettings(java.lang.String str) {
            this.mFontFeatureSettings = str;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setFontVariationSettings(java.lang.String str) {
            this.mFontVariationSettings = str;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setLineBreakStyle(int i) {
            this.mLineBreakStyle = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setLineBreakWordStyle(int i) {
            this.mLineBreakWordStyle = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextScaleX(float f) {
            this.mTextScaleX = f;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setHighlightTextColor(int i) {
            this.mHighlightTextColor = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setTextColor(int i) {
            this.mTextColor = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setHintTextColor(int i) {
            this.mHintTextColor = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo.Builder setLinkTextColor(int i) {
            this.mLinkTextColor = i;
            return this;
        }

        public android.view.inputmethod.TextAppearanceInfo build() {
            return new android.view.inputmethod.TextAppearanceInfo(this);
        }
    }
}
