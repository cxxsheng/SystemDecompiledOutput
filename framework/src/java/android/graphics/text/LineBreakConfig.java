package android.graphics.text;

/* loaded from: classes.dex */
public final class LineBreakConfig implements android.os.Parcelable {
    public static final int HYPHENATION_DISABLED = 0;
    public static final int HYPHENATION_ENABLED = 1;
    public static final int HYPHENATION_UNSPECIFIED = -1;
    public static final int LINE_BREAK_STYLE_AUTO = 5;
    public static final int LINE_BREAK_STYLE_LOOSE = 1;
    public static final int LINE_BREAK_STYLE_NONE = 0;
    public static final int LINE_BREAK_STYLE_NORMAL = 2;
    public static final int LINE_BREAK_STYLE_NO_BREAK = 4;
    public static final int LINE_BREAK_STYLE_STRICT = 3;
    public static final int LINE_BREAK_STYLE_UNSPECIFIED = -1;
    public static final int LINE_BREAK_WORD_STYLE_AUTO = 2;
    public static final int LINE_BREAK_WORD_STYLE_NONE = 0;
    public static final int LINE_BREAK_WORD_STYLE_PHRASE = 1;
    public static final int LINE_BREAK_WORD_STYLE_UNSPECIFIED = -1;
    private final int mHyphenation;
    private final int mLineBreakStyle;
    private final int mLineBreakWordStyle;
    public static final android.graphics.text.LineBreakConfig NONE = new android.graphics.text.LineBreakConfig.Builder().setLineBreakStyle(0).setLineBreakWordStyle(0).build();
    public static final android.os.Parcelable.Creator<android.graphics.text.LineBreakConfig> CREATOR = new android.os.Parcelable.Creator<android.graphics.text.LineBreakConfig>() { // from class: android.graphics.text.LineBreakConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.text.LineBreakConfig createFromParcel(android.os.Parcel parcel) {
            return new android.graphics.text.LineBreakConfig(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.text.LineBreakConfig[] newArray(int i) {
            return new android.graphics.text.LineBreakConfig[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Hyphenation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LineBreakStyle {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LineBreakWordStyle {
    }

    public static final class Builder {
        private int mLineBreakStyle = -1;
        private int mLineBreakWordStyle = -1;
        private int mHyphenation = -1;

        public Builder() {
            reset(null);
        }

        public android.graphics.text.LineBreakConfig.Builder merge(android.graphics.text.LineBreakConfig lineBreakConfig) {
            if (lineBreakConfig.mLineBreakStyle != -1) {
                this.mLineBreakStyle = lineBreakConfig.mLineBreakStyle;
            }
            if (lineBreakConfig.mLineBreakWordStyle != -1) {
                this.mLineBreakWordStyle = lineBreakConfig.mLineBreakWordStyle;
            }
            if (lineBreakConfig.mHyphenation != -1) {
                this.mHyphenation = lineBreakConfig.mHyphenation;
            }
            return this;
        }

        public android.graphics.text.LineBreakConfig.Builder reset(android.graphics.text.LineBreakConfig lineBreakConfig) {
            if (lineBreakConfig == null) {
                this.mLineBreakStyle = -1;
                this.mLineBreakWordStyle = -1;
                this.mHyphenation = -1;
            } else {
                this.mLineBreakStyle = lineBreakConfig.mLineBreakStyle;
                this.mLineBreakWordStyle = lineBreakConfig.mLineBreakWordStyle;
                this.mHyphenation = lineBreakConfig.mHyphenation;
            }
            return this;
        }

        public android.graphics.text.LineBreakConfig.Builder setLineBreakStyle(int i) {
            this.mLineBreakStyle = i;
            return this;
        }

        public android.graphics.text.LineBreakConfig.Builder setLineBreakWordStyle(int i) {
            this.mLineBreakWordStyle = i;
            return this;
        }

        public android.graphics.text.LineBreakConfig.Builder setHyphenation(int i) {
            this.mHyphenation = i;
            return this;
        }

        public android.graphics.text.LineBreakConfig build() {
            return new android.graphics.text.LineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle, this.mHyphenation);
        }
    }

    public static android.graphics.text.LineBreakConfig getLineBreakConfig(int i, int i2) {
        return new android.graphics.text.LineBreakConfig.Builder().setLineBreakStyle(i).setLineBreakWordStyle(i2).build();
    }

    public LineBreakConfig(int i, int i2, int i3) {
        this.mLineBreakStyle = i;
        this.mLineBreakWordStyle = i2;
        this.mHyphenation = i3;
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public static int getResolvedLineBreakStyle(android.graphics.text.LineBreakConfig lineBreakConfig) {
        int i;
        if (android.app.ActivityThread.currentApplication().getApplicationInfo().targetSdkVersion >= 10000) {
            i = 5;
        } else {
            i = 0;
        }
        if (lineBreakConfig == null) {
            return i;
        }
        return lineBreakConfig.mLineBreakStyle == -1 ? i : lineBreakConfig.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public static int getResolvedLineBreakWordStyle(android.graphics.text.LineBreakConfig lineBreakConfig) {
        int i;
        if (android.app.ActivityThread.currentApplication().getApplicationInfo().targetSdkVersion >= 10000) {
            i = 2;
        } else {
            i = 0;
        }
        if (lineBreakConfig == null) {
            return i;
        }
        return lineBreakConfig.mLineBreakWordStyle == -1 ? i : lineBreakConfig.mLineBreakWordStyle;
    }

    public int getHyphenation() {
        return this.mHyphenation;
    }

    public static int getResolvedHyphenation(android.graphics.text.LineBreakConfig lineBreakConfig) {
        if (lineBreakConfig == null || lineBreakConfig.mHyphenation == -1) {
            return 1;
        }
        return lineBreakConfig.mHyphenation;
    }

    public android.graphics.text.LineBreakConfig merge(android.graphics.text.LineBreakConfig lineBreakConfig) {
        return new android.graphics.text.LineBreakConfig(lineBreakConfig.mLineBreakStyle == -1 ? this.mLineBreakStyle : lineBreakConfig.mLineBreakStyle, lineBreakConfig.mLineBreakWordStyle == -1 ? this.mLineBreakWordStyle : lineBreakConfig.mLineBreakWordStyle, lineBreakConfig.mHyphenation == -1 ? this.mHyphenation : lineBreakConfig.mHyphenation);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.graphics.text.LineBreakConfig)) {
            return false;
        }
        android.graphics.text.LineBreakConfig lineBreakConfig = (android.graphics.text.LineBreakConfig) obj;
        if (this.mLineBreakStyle != lineBreakConfig.mLineBreakStyle || this.mLineBreakWordStyle != lineBreakConfig.mLineBreakWordStyle || this.mHyphenation != lineBreakConfig.mHyphenation) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLineBreakStyle), java.lang.Integer.valueOf(this.mLineBreakWordStyle), java.lang.Integer.valueOf(this.mHyphenation));
    }

    public java.lang.String toString() {
        return "LineBreakConfig{mLineBreakStyle=" + this.mLineBreakStyle + ", mLineBreakWordStyle=" + this.mLineBreakWordStyle + ", mHyphenation= " + this.mHyphenation + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLineBreakStyle);
        parcel.writeInt(this.mLineBreakWordStyle);
        parcel.writeInt(this.mHyphenation);
    }
}
