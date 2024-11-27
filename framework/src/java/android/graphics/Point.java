package android.graphics;

/* loaded from: classes.dex */
public class Point implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.Point> CREATOR = new android.os.Parcelable.Creator<android.graphics.Point>() { // from class: android.graphics.Point.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Point createFromParcel(android.os.Parcel parcel) {
            android.graphics.Point point = new android.graphics.Point();
            point.readFromParcel(parcel);
            return point;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Point[] newArray(int i) {
            return new android.graphics.Point[i];
        }
    };
    public int x;
    public int y;

    public Point() {
    }

    public Point(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public Point(android.graphics.Point point) {
        set(point);
    }

    public void set(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public void set(android.graphics.Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(int i, int i2) {
        this.x += i;
        this.y += i2;
    }

    public final boolean equals(int i, int i2) {
        return this.x == i && this.y == i2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.Point point = (android.graphics.Point) obj;
        if (this.x == point.x && this.y == point.y) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.x * 31) + this.y;
    }

    public java.lang.String toString() {
        return "Point(" + this.x + ", " + this.y + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public java.lang.String flattenToString() {
        return this.x + "x" + this.y;
    }

    public static android.graphics.Point unflattenFromString(java.lang.String str) throws java.lang.NumberFormatException {
        int indexOf = str.indexOf("x");
        return new android.graphics.Point(java.lang.Integer.parseInt(str.substring(0, indexOf)), java.lang.Integer.parseInt(str.substring(indexOf + 1)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.x);
        parcel.writeInt(this.y);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.x = parcel.readInt();
        this.y = parcel.readInt();
    }
}
