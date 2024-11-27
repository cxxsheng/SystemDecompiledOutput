package android.graphics;

/* loaded from: classes.dex */
public class RectF implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.RectF> CREATOR = new android.os.Parcelable.Creator<android.graphics.RectF>() { // from class: android.graphics.RectF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.RectF createFromParcel(android.os.Parcel parcel) {
            android.graphics.RectF rectF = new android.graphics.RectF();
            rectF.readFromParcel(parcel);
            return rectF;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.RectF[] newArray(int i) {
            return new android.graphics.RectF[i];
        }
    };
    public float bottom;
    public float left;
    public float right;
    public float top;

    public RectF() {
    }

    public RectF(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public RectF(android.graphics.RectF rectF) {
        if (rectF == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = rectF.left;
        this.top = rectF.top;
        this.right = rectF.right;
        this.bottom = rectF.bottom;
    }

    public RectF(android.graphics.Rect rect) {
        if (rect == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.RectF rectF = (android.graphics.RectF) obj;
        if (this.left == rectF.left && this.top == rectF.top && this.right == rectF.right && this.bottom == rectF.bottom) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.left != 0.0f ? java.lang.Float.floatToIntBits(this.left) : 0) * 31) + (this.top != 0.0f ? java.lang.Float.floatToIntBits(this.top) : 0)) * 31) + (this.right != 0.0f ? java.lang.Float.floatToIntBits(this.right) : 0)) * 31) + (this.bottom != 0.0f ? java.lang.Float.floatToIntBits(this.bottom) : 0);
    }

    public java.lang.String toString() {
        return "RectF(" + this.left + ", " + this.top + ", " + this.right + ", " + this.bottom + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public java.lang.String toShortString() {
        return toShortString(new java.lang.StringBuilder(32));
    }

    public java.lang.String toShortString(java.lang.StringBuilder sb) {
        sb.setLength(0);
        sb.append('[');
        sb.append(this.left);
        sb.append(',');
        sb.append(this.top);
        sb.append("][");
        sb.append(this.right);
        sb.append(',');
        sb.append(this.bottom);
        sb.append(']');
        return sb.toString();
    }

    public void printShortString(java.io.PrintWriter printWriter) {
        printWriter.print('[');
        printWriter.print(this.left);
        printWriter.print(',');
        printWriter.print(this.top);
        printWriter.print("][");
        printWriter.print(this.right);
        printWriter.print(',');
        printWriter.print(this.bottom);
        printWriter.print(']');
    }

    public final boolean isEmpty() {
        return this.left >= this.right || this.top >= this.bottom;
    }

    public final float width() {
        return this.right - this.left;
    }

    public final float height() {
        return this.bottom - this.top;
    }

    public final float centerX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float centerY() {
        return (this.top + this.bottom) * 0.5f;
    }

    public void setEmpty() {
        this.bottom = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.left = 0.0f;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void set(android.graphics.RectF rectF) {
        this.left = rectF.left;
        this.top = rectF.top;
        this.right = rectF.right;
        this.bottom = rectF.bottom;
    }

    public void set(android.graphics.Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void offset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right += f;
        this.bottom += f2;
    }

    public void offsetTo(float f, float f2) {
        this.right += f - this.left;
        this.bottom += f2 - this.top;
        this.left = f;
        this.top = f2;
    }

    public void inset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right -= f;
        this.bottom -= f2;
    }

    public boolean contains(float f, float f2) {
        return this.left < this.right && this.top < this.bottom && f >= this.left && f < this.right && f2 >= this.top && f2 < this.bottom;
    }

    public boolean contains(float f, float f2, float f3, float f4) {
        return this.left < this.right && this.top < this.bottom && this.left <= f && this.top <= f2 && this.right >= f3 && this.bottom >= f4;
    }

    public boolean contains(android.graphics.RectF rectF) {
        return this.left < this.right && this.top < this.bottom && this.left <= rectF.left && this.top <= rectF.top && this.right >= rectF.right && this.bottom >= rectF.bottom;
    }

    public boolean intersect(float f, float f2, float f3, float f4) {
        if (this.left >= f3 || f >= this.right || this.top >= f4 || f2 >= this.bottom) {
            return false;
        }
        if (this.left < f) {
            this.left = f;
        }
        if (this.top < f2) {
            this.top = f2;
        }
        if (this.right > f3) {
            this.right = f3;
        }
        if (this.bottom > f4) {
            this.bottom = f4;
            return true;
        }
        return true;
    }

    public boolean intersect(android.graphics.RectF rectF) {
        return intersect(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public boolean setIntersect(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
        if (rectF.left < rectF2.right && rectF2.left < rectF.right && rectF.top < rectF2.bottom && rectF2.top < rectF.bottom) {
            this.left = java.lang.Math.max(rectF.left, rectF2.left);
            this.top = java.lang.Math.max(rectF.top, rectF2.top);
            this.right = java.lang.Math.min(rectF.right, rectF2.right);
            this.bottom = java.lang.Math.min(rectF.bottom, rectF2.bottom);
            return true;
        }
        return false;
    }

    public boolean intersects(float f, float f2, float f3, float f4) {
        return this.left < f3 && f < this.right && this.top < f4 && f2 < this.bottom;
    }

    public static boolean intersects(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
        return rectF.left < rectF2.right && rectF2.left < rectF.right && rectF.top < rectF2.bottom && rectF2.top < rectF.bottom;
    }

    public void round(android.graphics.Rect rect) {
        rect.set(com.android.internal.util.FastMath.round(this.left), com.android.internal.util.FastMath.round(this.top), com.android.internal.util.FastMath.round(this.right), com.android.internal.util.FastMath.round(this.bottom));
    }

    public void roundOut(android.graphics.Rect rect) {
        rect.set((int) java.lang.Math.floor(this.left), (int) java.lang.Math.floor(this.top), (int) java.lang.Math.ceil(this.right), (int) java.lang.Math.ceil(this.bottom));
    }

    public void union(float f, float f2, float f3, float f4) {
        if (f >= f3 || f2 >= f4) {
            return;
        }
        if (this.left >= this.right || this.top >= this.bottom) {
            this.left = f;
            this.top = f2;
            this.right = f3;
            this.bottom = f4;
            return;
        }
        if (this.left > f) {
            this.left = f;
        }
        if (this.top > f2) {
            this.top = f2;
        }
        if (this.right < f3) {
            this.right = f3;
        }
        if (this.bottom < f4) {
            this.bottom = f4;
        }
    }

    public void union(android.graphics.RectF rectF) {
        union(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public void union(float f, float f2) {
        if (f < this.left) {
            this.left = f;
        } else if (f > this.right) {
            this.right = f;
        }
        if (f2 < this.top) {
            this.top = f2;
        } else if (f2 > this.bottom) {
            this.bottom = f2;
        }
    }

    public void sort() {
        if (this.left > this.right) {
            float f = this.left;
            this.left = this.right;
            this.right = f;
        }
        if (this.top > this.bottom) {
            float f2 = this.top;
            this.top = this.bottom;
            this.bottom = f2;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.left);
        parcel.writeFloat(this.top);
        parcel.writeFloat(this.right);
        parcel.writeFloat(this.bottom);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.left = parcel.readFloat();
        this.top = parcel.readFloat();
        this.right = parcel.readFloat();
        this.bottom = parcel.readFloat();
    }

    public void scale(float f) {
        if (f != 1.0f) {
            this.left *= f;
            this.top *= f;
            this.right *= f;
            this.bottom *= f;
        }
    }
}
