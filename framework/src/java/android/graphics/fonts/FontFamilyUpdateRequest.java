package android.graphics.fonts;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FontFamilyUpdateRequest {
    private final java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.FontFamily> mFontFamilies;
    private final java.util.List<android.graphics.fonts.FontFileUpdateRequest> mFontFiles;

    public static final class FontFamily {
        private final java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> mFonts;
        private final java.lang.String mName;

        public static final class Builder {
            private final java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> mFonts;
            private final java.lang.String mName;

            public Builder(java.lang.String str, java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> list) {
                java.util.Objects.requireNonNull(str);
                com.android.internal.util.Preconditions.checkStringNotEmpty(str);
                java.util.Objects.requireNonNull(list);
                com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "fonts");
                com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "fonts");
                this.mName = str;
                this.mFonts = new java.util.ArrayList(list);
            }

            public android.graphics.fonts.FontFamilyUpdateRequest.FontFamily.Builder addFont(android.graphics.fonts.FontFamilyUpdateRequest.Font font) {
                this.mFonts.add(font);
                return this;
            }

            public android.graphics.fonts.FontFamilyUpdateRequest.FontFamily build() {
                return new android.graphics.fonts.FontFamilyUpdateRequest.FontFamily(this.mName, this.mFonts);
            }
        }

        private FontFamily(java.lang.String str, java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> list) {
            this.mName = str;
            this.mFonts = list;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> getFonts() {
            return this.mFonts;
        }
    }

    public static final class Font {
        private final java.util.List<android.graphics.fonts.FontVariationAxis> mAxes;
        private final int mIndex;
        private final java.lang.String mPostScriptName;
        private final android.graphics.fonts.FontStyle mStyle;

        public static final class Builder {
            private java.util.List<android.graphics.fonts.FontVariationAxis> mAxes = java.util.Collections.emptyList();
            private int mIndex = 0;
            private final java.lang.String mPostScriptName;
            private final android.graphics.fonts.FontStyle mStyle;

            public Builder(java.lang.String str, android.graphics.fonts.FontStyle fontStyle) {
                java.util.Objects.requireNonNull(str);
                com.android.internal.util.Preconditions.checkStringNotEmpty(str);
                java.util.Objects.requireNonNull(fontStyle);
                this.mPostScriptName = str;
                this.mStyle = fontStyle;
            }

            public android.graphics.fonts.FontFamilyUpdateRequest.Font.Builder setAxes(java.util.List<android.graphics.fonts.FontVariationAxis> list) {
                java.util.Objects.requireNonNull(list);
                com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "axes");
                this.mAxes = list;
                return this;
            }

            public android.graphics.fonts.FontFamilyUpdateRequest.Font.Builder setIndex(int i) {
                com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
                this.mIndex = i;
                return this;
            }

            public android.graphics.fonts.FontFamilyUpdateRequest.Font build() {
                return new android.graphics.fonts.FontFamilyUpdateRequest.Font(this.mPostScriptName, this.mStyle, this.mIndex, this.mAxes);
            }
        }

        private Font(java.lang.String str, android.graphics.fonts.FontStyle fontStyle, int i, java.util.List<android.graphics.fonts.FontVariationAxis> list) {
            this.mPostScriptName = str;
            this.mStyle = fontStyle;
            this.mIndex = i;
            this.mAxes = list;
        }

        public java.lang.String getPostScriptName() {
            return this.mPostScriptName;
        }

        public android.graphics.fonts.FontStyle getStyle() {
            return this.mStyle;
        }

        public java.util.List<android.graphics.fonts.FontVariationAxis> getAxes() {
            return this.mAxes;
        }

        public int getIndex() {
            return this.mIndex;
        }
    }

    public static final class Builder {
        private final java.util.List<android.graphics.fonts.FontFileUpdateRequest> mFontFileUpdateRequests = new java.util.ArrayList();
        private final java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.FontFamily> mFontFamilies = new java.util.ArrayList();

        public android.graphics.fonts.FontFamilyUpdateRequest.Builder addFontFileUpdateRequest(android.graphics.fonts.FontFileUpdateRequest fontFileUpdateRequest) {
            java.util.Objects.requireNonNull(fontFileUpdateRequest);
            this.mFontFileUpdateRequests.add(fontFileUpdateRequest);
            return this;
        }

        public android.graphics.fonts.FontFamilyUpdateRequest.Builder addFontFamily(android.graphics.fonts.FontFamilyUpdateRequest.FontFamily fontFamily) {
            java.util.Objects.requireNonNull(fontFamily);
            this.mFontFamilies.add(fontFamily);
            return this;
        }

        public android.graphics.fonts.FontFamilyUpdateRequest build() {
            return new android.graphics.fonts.FontFamilyUpdateRequest(this.mFontFileUpdateRequests, this.mFontFamilies);
        }
    }

    private FontFamilyUpdateRequest(java.util.List<android.graphics.fonts.FontFileUpdateRequest> list, java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.FontFamily> list2) {
        this.mFontFiles = list;
        this.mFontFamilies = list2;
    }

    public java.util.List<android.graphics.fonts.FontFileUpdateRequest> getFontFileUpdateRequests() {
        return this.mFontFiles;
    }

    public java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.FontFamily> getFontFamilies() {
        return this.mFontFamilies;
    }
}
