package android.graphics.fonts;

/* loaded from: classes.dex */
public final class FontVariationAxis {
    private final float mStyleValue;
    private final int mTag;
    private final java.lang.String mTagString;
    private static final java.util.regex.Pattern TAG_PATTERN = java.util.regex.Pattern.compile("[ -~]{4}");
    private static final java.util.regex.Pattern STYLE_VALUE_PATTERN = java.util.regex.Pattern.compile("-?(([0-9]+(\\.[0-9]+)?)|(\\.[0-9]+))");

    public FontVariationAxis(java.lang.String str, float f) {
        if (!isValidTag(str)) {
            throw new java.lang.IllegalArgumentException("Illegal tag pattern: " + str);
        }
        this.mTag = makeTag(str);
        this.mTagString = str;
        this.mStyleValue = f;
    }

    public int getOpenTypeTagValue() {
        return this.mTag;
    }

    public java.lang.String getTag() {
        return this.mTagString;
    }

    public float getStyleValue() {
        return this.mStyleValue;
    }

    public java.lang.String toString() {
        return "'" + this.mTagString + "' " + java.lang.Float.toString(this.mStyleValue);
    }

    private static boolean isValidTag(java.lang.String str) {
        return str != null && TAG_PATTERN.matcher(str).matches();
    }

    private static boolean isValidValueFormat(java.lang.String str) {
        return str != null && STYLE_VALUE_PATTERN.matcher(str).matches();
    }

    public static int makeTag(java.lang.String str) {
        return str.charAt(3) | (str.charAt(0) << 24) | (str.charAt(1) << 16) | (str.charAt(2) << '\b');
    }

    public static android.graphics.fonts.FontVariationAxis[] fromFontVariationSettings(java.lang.String str) {
        int i;
        if (str == null || str.isEmpty()) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            if (!java.lang.Character.isWhitespace(charAt)) {
                if ((charAt == '\'' || charAt == '\"') && length >= (i = i2 + 6)) {
                    int i3 = i2 + 5;
                    if (str.charAt(i3) == charAt) {
                        java.lang.String substring = str.substring(i2 + 1, i3);
                        int indexOf = str.indexOf(44, i);
                        if (indexOf == -1) {
                            indexOf = length;
                        }
                        try {
                            arrayList.add(new android.graphics.fonts.FontVariationAxis(substring, java.lang.Float.parseFloat(str.substring(i, indexOf))));
                            i2 = indexOf;
                        } catch (java.lang.NumberFormatException e) {
                            throw new java.lang.IllegalArgumentException("Failed to parse float string: " + e.getMessage());
                        }
                    }
                }
                throw new java.lang.IllegalArgumentException("Tag should be wrapped with double or single quote: " + str);
            }
            i2++;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (android.graphics.fonts.FontVariationAxis[]) arrayList.toArray(new android.graphics.fonts.FontVariationAxis[0]);
    }

    public static java.lang.String toFontVariationSettings(android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr) {
        if (fontVariationAxisArr == null) {
            return "";
        }
        return android.text.TextUtils.join(",", fontVariationAxisArr);
    }

    public static java.lang.String toFontVariationSettings(java.util.List<android.graphics.fonts.FontVariationAxis> list) {
        if (list == null) {
            return "";
        }
        return android.text.TextUtils.join(",", list);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof android.graphics.fonts.FontVariationAxis)) {
            return false;
        }
        android.graphics.fonts.FontVariationAxis fontVariationAxis = (android.graphics.fonts.FontVariationAxis) obj;
        if (fontVariationAxis.mTag == this.mTag && fontVariationAxis.mStyleValue == this.mStyleValue) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTag), java.lang.Float.valueOf(this.mStyleValue));
    }
}
