package android.graphics;

/* loaded from: classes.dex */
public final class Insets implements android.os.Parcelable {
    public final int bottom;
    public final int left;
    public final int right;
    public final int top;
    public static final android.graphics.Insets NONE = new android.graphics.Insets(0, 0, 0, 0);
    public static final android.os.Parcelable.Creator<android.graphics.Insets> CREATOR = new android.os.Parcelable.Creator<android.graphics.Insets>() { // from class: android.graphics.Insets.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Insets createFromParcel(android.os.Parcel parcel) {
            return new android.graphics.Insets(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Insets[] newArray(int i) {
            return new android.graphics.Insets[i];
        }
    };

    private Insets(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public static android.graphics.Insets of(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return NONE;
        }
        return new android.graphics.Insets(i, i2, i3, i4);
    }

    public static android.graphics.Insets of(android.graphics.Rect rect) {
        return rect == null ? NONE : of(rect.left, rect.top, rect.right, rect.bottom);
    }

    public android.graphics.Rect toRect() {
        return new android.graphics.Rect(this.left, this.top, this.right, this.bottom);
    }

    public static android.graphics.Insets add(android.graphics.Insets insets, android.graphics.Insets insets2) {
        return of(insets.left + insets2.left, insets.top + insets2.top, insets.right + insets2.right, insets.bottom + insets2.bottom);
    }

    public static android.graphics.Insets subtract(android.graphics.Insets insets, android.graphics.Insets insets2) {
        return of(insets.left - insets2.left, insets.top - insets2.top, insets.right - insets2.right, insets.bottom - insets2.bottom);
    }

    public static android.graphics.Insets max(android.graphics.Insets insets, android.graphics.Insets insets2) {
        return of(java.lang.Math.max(insets.left, insets2.left), java.lang.Math.max(insets.top, insets2.top), java.lang.Math.max(insets.right, insets2.right), java.lang.Math.max(insets.bottom, insets2.bottom));
    }

    public static android.graphics.Insets min(android.graphics.Insets insets, android.graphics.Insets insets2) {
        return of(java.lang.Math.min(insets.left, insets2.left), java.lang.Math.min(insets.top, insets2.top), java.lang.Math.min(insets.right, insets2.right), java.lang.Math.min(insets.bottom, insets2.bottom));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.Insets insets = (android.graphics.Insets) obj;
        if (this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.top == insets.top) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public java.lang.String toString() {
        return "Insets{left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }
}
