package android.util;

/* loaded from: classes3.dex */
public final class Size {
    private final int mHeight;
    private final int mWidth;

    public Size(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.util.Size)) {
            return false;
        }
        android.util.Size size = (android.util.Size) obj;
        if (this.mWidth != size.mWidth || this.mHeight != size.mHeight) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    private static java.lang.NumberFormatException invalidSize(java.lang.String str) {
        throw new java.lang.NumberFormatException("Invalid Size: \"" + str + "\"");
    }

    public static android.util.Size parseSize(java.lang.String str) throws java.lang.NumberFormatException {
        com.android.internal.util.Preconditions.checkNotNull(str, "string must not be null");
        int indexOf = str.indexOf(42);
        if (indexOf < 0) {
            indexOf = str.indexOf(120);
        }
        if (indexOf < 0) {
            throw invalidSize(str);
        }
        try {
            return new android.util.Size(java.lang.Integer.parseInt(str.substring(0, indexOf)), java.lang.Integer.parseInt(str.substring(indexOf + 1)));
        } catch (java.lang.NumberFormatException e) {
            throw invalidSize(str);
        }
    }

    public int hashCode() {
        return this.mHeight ^ ((this.mWidth << 16) | (this.mWidth >>> 16));
    }
}
