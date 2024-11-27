package android.text.style;

/* loaded from: classes3.dex */
public class TextAppearanceSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    private final boolean mElegantTextHeight;
    private final java.lang.String mFamilyName;
    private final java.lang.String mFontFeatureSettings;
    private final java.lang.String mFontVariationSettings;
    private final boolean mHasElegantTextHeight;
    private final boolean mHasLetterSpacing;
    private final float mLetterSpacing;
    private final int mShadowColor;
    private final float mShadowDx;
    private final float mShadowDy;
    private final float mShadowRadius;
    private final int mStyle;
    private final android.content.res.ColorStateList mTextColor;
    private final android.content.res.ColorStateList mTextColorLink;
    private final int mTextFontWeight;
    private final android.os.LocaleList mTextLocales;
    private final int mTextSize;
    private final android.graphics.Typeface mTypeface;

    public TextAppearanceSpan(android.content.Context context, int i) {
        this(context, i, -1);
    }

    public TextAppearanceSpan(android.content.Context context, int i, int i2) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, com.android.internal.R.styleable.TextAppearance);
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        this.mTextColorLink = obtainStyledAttributes.getColorStateList(6);
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.mStyle = obtainStyledAttributes.getInt(2, 0);
        if (!context.isRestricted() && context.canLoadUnsafeResources()) {
            this.mTypeface = obtainStyledAttributes.getFont(12);
        } else {
            this.mTypeface = null;
        }
        if (this.mTypeface != null) {
            this.mFamilyName = null;
        } else {
            java.lang.String string = obtainStyledAttributes.getString(12);
            if (string != null) {
                this.mFamilyName = string;
            } else {
                switch (obtainStyledAttributes.getInt(1, 0)) {
                    case 1:
                        this.mFamilyName = "sans";
                        break;
                    case 2:
                        this.mFamilyName = "serif";
                        break;
                    case 3:
                        this.mFamilyName = "monospace";
                        break;
                    default:
                        this.mFamilyName = null;
                        break;
                }
            }
        }
        this.mTextFontWeight = obtainStyledAttributes.getInt(18, -1);
        java.lang.String string2 = obtainStyledAttributes.getString(19);
        if (string2 != null) {
            android.os.LocaleList forLanguageTags = android.os.LocaleList.forLanguageTags(string2);
            if (!forLanguageTags.isEmpty()) {
                this.mTextLocales = forLanguageTags;
            } else {
                this.mTextLocales = null;
            }
        } else {
            this.mTextLocales = null;
        }
        this.mShadowRadius = obtainStyledAttributes.getFloat(10, 0.0f);
        this.mShadowDx = obtainStyledAttributes.getFloat(8, 0.0f);
        this.mShadowDy = obtainStyledAttributes.getFloat(9, 0.0f);
        this.mShadowColor = obtainStyledAttributes.getInt(7, 0);
        this.mHasElegantTextHeight = obtainStyledAttributes.hasValue(13);
        this.mElegantTextHeight = obtainStyledAttributes.getBoolean(13, false);
        this.mHasLetterSpacing = obtainStyledAttributes.hasValue(14);
        this.mLetterSpacing = obtainStyledAttributes.getFloat(14, 0.0f);
        this.mFontFeatureSettings = obtainStyledAttributes.getString(15);
        this.mFontVariationSettings = obtainStyledAttributes.getString(16);
        obtainStyledAttributes.recycle();
        if (i2 >= 0) {
            android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(16973829, com.android.internal.R.styleable.Theme);
            colorStateList = obtainStyledAttributes2.getColorStateList(i2);
            obtainStyledAttributes2.recycle();
        }
        this.mTextColor = colorStateList;
    }

    public TextAppearanceSpan(java.lang.String str, int i, int i2, android.content.res.ColorStateList colorStateList, android.content.res.ColorStateList colorStateList2) {
        this.mFamilyName = str;
        this.mStyle = i;
        this.mTextSize = i2;
        this.mTextColor = colorStateList;
        this.mTextColorLink = colorStateList2;
        this.mTypeface = null;
        this.mTextFontWeight = -1;
        this.mTextLocales = null;
        this.mShadowRadius = 0.0f;
        this.mShadowDx = 0.0f;
        this.mShadowDy = 0.0f;
        this.mShadowColor = 0;
        this.mHasElegantTextHeight = false;
        this.mElegantTextHeight = false;
        this.mHasLetterSpacing = false;
        this.mLetterSpacing = 0.0f;
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
    }

    public TextAppearanceSpan(android.os.Parcel parcel) {
        this.mFamilyName = parcel.readString();
        this.mStyle = parcel.readInt();
        this.mTextSize = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mTextColor = android.content.res.ColorStateList.CREATOR.createFromParcel(parcel);
        } else {
            this.mTextColor = null;
        }
        if (parcel.readInt() != 0) {
            this.mTextColorLink = android.content.res.ColorStateList.CREATOR.createFromParcel(parcel);
        } else {
            this.mTextColorLink = null;
        }
        this.mTypeface = android.graphics.LeakyTypefaceStorage.readTypefaceFromParcel(parcel);
        this.mTextFontWeight = parcel.readInt();
        this.mTextLocales = (android.os.LocaleList) parcel.readParcelable(android.os.LocaleList.class.getClassLoader(), android.os.LocaleList.class);
        this.mShadowRadius = parcel.readFloat();
        this.mShadowDx = parcel.readFloat();
        this.mShadowDy = parcel.readFloat();
        this.mShadowColor = parcel.readInt();
        this.mHasElegantTextHeight = parcel.readBoolean();
        this.mElegantTextHeight = parcel.readBoolean();
        this.mHasLetterSpacing = parcel.readBoolean();
        this.mLetterSpacing = parcel.readFloat();
        this.mFontFeatureSettings = parcel.readString();
        this.mFontVariationSettings = parcel.readString();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 17;
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
        parcel.writeString(this.mFamilyName);
        parcel.writeInt(this.mStyle);
        parcel.writeInt(this.mTextSize);
        if (this.mTextColor != null) {
            parcel.writeInt(1);
            this.mTextColor.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mTextColorLink != null) {
            parcel.writeInt(1);
            this.mTextColorLink.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        android.graphics.LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
        parcel.writeInt(this.mTextFontWeight);
        parcel.writeParcelable(this.mTextLocales, i);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeFloat(this.mShadowDx);
        parcel.writeFloat(this.mShadowDy);
        parcel.writeInt(this.mShadowColor);
        parcel.writeBoolean(this.mHasElegantTextHeight);
        parcel.writeBoolean(this.mElegantTextHeight);
        parcel.writeBoolean(this.mHasLetterSpacing);
        parcel.writeFloat(this.mLetterSpacing);
        parcel.writeString(this.mFontFeatureSettings);
        parcel.writeString(this.mFontVariationSettings);
    }

    public java.lang.String getFamily() {
        return this.mFamilyName;
    }

    public android.content.res.ColorStateList getTextColor() {
        return this.mTextColor;
    }

    public android.content.res.ColorStateList getLinkTextColor() {
        return this.mTextColorLink;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public int getTextStyle() {
        return this.mStyle;
    }

    public int getTextFontWeight() {
        return this.mTextFontWeight;
    }

    public android.os.LocaleList getTextLocales() {
        return this.mTextLocales;
    }

    public android.graphics.Typeface getTypeface() {
        return this.mTypeface;
    }

    public int getShadowColor() {
        return this.mShadowColor;
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

    public java.lang.String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public java.lang.String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public boolean isElegantTextHeight() {
        return this.mElegantTextHeight;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        updateMeasureState(textPaint);
        if (this.mTextColor != null) {
            textPaint.setColor(this.mTextColor.getColorForState(textPaint.drawableState, 0));
        }
        if (this.mTextColorLink != null) {
            textPaint.linkColor = this.mTextColorLink.getColorForState(textPaint.drawableState, 0);
        }
        if (this.mShadowColor != 0) {
            textPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor);
        }
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        int i;
        android.graphics.Typeface create;
        android.graphics.Typeface typeface;
        int i2;
        if (this.mTypeface != null) {
            i2 = this.mStyle;
            typeface = android.graphics.Typeface.create(this.mTypeface, i2);
        } else if (this.mFamilyName != null || this.mStyle != 0) {
            android.graphics.Typeface typeface2 = textPaint.getTypeface();
            if (typeface2 == null) {
                i = 0;
            } else {
                i = typeface2.getStyle();
            }
            int i3 = i | this.mStyle;
            if (this.mFamilyName != null) {
                create = android.graphics.Typeface.create(this.mFamilyName, i3);
            } else if (typeface2 == null) {
                create = android.graphics.Typeface.defaultFromStyle(i3);
            } else {
                create = android.graphics.Typeface.create(typeface2, i3);
            }
            typeface = create;
            i2 = i3;
        } else {
            typeface = null;
            i2 = 0;
        }
        if (typeface != null) {
            if (this.mTextFontWeight >= 0) {
                typeface = textPaint.setTypeface(android.graphics.Typeface.create(typeface, java.lang.Math.min(1000, this.mTextFontWeight), (i2 & 2) != 0));
            }
            int i4 = i2 & (~typeface.getStyle());
            if ((i4 & 1) != 0) {
                textPaint.setFakeBoldText(true);
            }
            if ((i4 & 2) != 0) {
                textPaint.setTextSkewX(-0.25f);
            }
            textPaint.setTypeface(typeface);
        }
        if (this.mTextSize > 0) {
            textPaint.setTextSize(this.mTextSize);
        }
        if (this.mTextLocales != null) {
            textPaint.setTextLocales(this.mTextLocales);
        }
        if (this.mHasElegantTextHeight) {
            textPaint.setElegantTextHeight(this.mElegantTextHeight);
        }
        if (this.mHasLetterSpacing) {
            textPaint.setLetterSpacing(this.mLetterSpacing);
        }
        if (this.mFontFeatureSettings != null) {
            textPaint.setFontFeatureSettings(this.mFontFeatureSettings);
        }
        if (this.mFontVariationSettings != null) {
            textPaint.setFontVariationSettings(this.mFontVariationSettings);
        }
    }

    public java.lang.String toString() {
        return "TextAppearanceSpan{familyName='" + getFamily() + android.text.format.DateFormat.QUOTE + ", style=" + getTextStyle() + ", textSize=" + getTextSize() + ", textColor=" + getTextColor() + ", textColorLink=" + getLinkTextColor() + ", typeface=" + getTypeface() + ", textFontWeight=" + getTextFontWeight() + ", textLocales=" + getTextLocales() + ", shadowRadius=" + getShadowRadius() + ", shadowDx=" + getShadowDx() + ", shadowDy=" + getShadowDy() + ", shadowColor=" + java.lang.String.format("#%08X", java.lang.Integer.valueOf(getShadowColor())) + ", elegantTextHeight=" + isElegantTextHeight() + ", letterSpacing=" + getLetterSpacing() + ", fontFeatureSettings='" + getFontFeatureSettings() + android.text.format.DateFormat.QUOTE + ", fontVariationSettings='" + getFontVariationSettings() + android.text.format.DateFormat.QUOTE + '}';
    }
}
