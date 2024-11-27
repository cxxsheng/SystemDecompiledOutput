package android.util;

/* loaded from: classes3.dex */
public final class SizeF implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.util.SizeF> CREATOR = new android.os.Parcelable.Creator<android.util.SizeF>() { // from class: android.util.SizeF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.SizeF createFromParcel(android.os.Parcel parcel) {
            return new android.util.SizeF(parcel.readFloat(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.SizeF[] newArray(int i) {
            return new android.util.SizeF[i];
        }
    };
    private final float mHeight;
    private final float mWidth;

    public SizeF(float f, float f2) {
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentFinite(f, "width");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentFinite(f2, "height");
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.util.SizeF)) {
            return false;
        }
        android.util.SizeF sizeF = (android.util.SizeF) obj;
        if (this.mWidth != sizeF.mWidth || this.mHeight != sizeF.mHeight) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    private static java.lang.NumberFormatException invalidSizeF(java.lang.String str) {
        throw new java.lang.NumberFormatException("Invalid SizeF: \"" + str + "\"");
    }

    public static android.util.SizeF parseSizeF(java.lang.String str) throws java.lang.NumberFormatException {
        com.android.internal.util.Preconditions.checkNotNull(str, "string must not be null");
        int indexOf = str.indexOf(42);
        if (indexOf < 0) {
            indexOf = str.indexOf(120);
        }
        if (indexOf < 0) {
            throw invalidSizeF(str);
        }
        try {
            return new android.util.SizeF(java.lang.Float.parseFloat(str.substring(0, indexOf)), java.lang.Float.parseFloat(str.substring(indexOf + 1)));
        } catch (java.lang.NumberFormatException e) {
            throw invalidSizeF(str);
        } catch (java.lang.IllegalArgumentException e2) {
            throw invalidSizeF(str);
        }
    }

    public int hashCode() {
        return java.lang.Float.floatToIntBits(this.mWidth) ^ java.lang.Float.floatToIntBits(this.mHeight);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mWidth);
        parcel.writeFloat(this.mHeight);
    }
}
