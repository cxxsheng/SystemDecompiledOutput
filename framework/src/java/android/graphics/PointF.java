package android.graphics;

/* loaded from: classes.dex */
public class PointF implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.PointF> CREATOR = new android.os.Parcelable.Creator<android.graphics.PointF>() { // from class: android.graphics.PointF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.PointF createFromParcel(android.os.Parcel parcel) {
            android.graphics.PointF pointF = new android.graphics.PointF();
            pointF.readFromParcel(parcel);
            return pointF;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.PointF[] newArray(int i) {
            return new android.graphics.PointF[i];
        }
    };
    public float x;
    public float y;

    public PointF() {
    }

    public PointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public PointF(android.graphics.Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public PointF(android.graphics.PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public final void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public final void set(android.graphics.PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public final boolean equals(float f, float f2) {
        return this.x == f && this.y == f2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.PointF pointF = (android.graphics.PointF) obj;
        if (java.lang.Float.compare(pointF.x, this.x) == 0 && java.lang.Float.compare(pointF.y, this.y) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.x != 0.0f ? java.lang.Float.floatToIntBits(this.x) : 0) * 31) + (this.y != 0.0f ? java.lang.Float.floatToIntBits(this.y) : 0);
    }

    public java.lang.String toString() {
        return "PointF(" + this.x + ", " + this.y + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public final float length() {
        return length(this.x, this.y);
    }

    public static float length(float f, float f2) {
        return (float) java.lang.Math.hypot(f, f2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
    }
}
