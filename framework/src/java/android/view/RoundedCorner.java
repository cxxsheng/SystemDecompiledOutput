package android.view;

/* loaded from: classes4.dex */
public final class RoundedCorner implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.RoundedCorner> CREATOR = new android.os.Parcelable.Creator<android.view.RoundedCorner>() { // from class: android.view.RoundedCorner.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RoundedCorner createFromParcel(android.os.Parcel parcel) {
            return new android.view.RoundedCorner(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RoundedCorner[] newArray(int i) {
            return new android.view.RoundedCorner[i];
        }
    };
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 2;
    public static final int POSITION_TOP_LEFT = 0;
    public static final int POSITION_TOP_RIGHT = 1;
    private final android.graphics.Point mCenter;
    private final int mPosition;
    private final int mRadius;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Position {
    }

    public RoundedCorner(int i) {
        this.mPosition = i;
        this.mRadius = 0;
        this.mCenter = new android.graphics.Point(0, 0);
    }

    public RoundedCorner(int i, int i2, int i3, int i4) {
        this.mPosition = i;
        this.mRadius = i2;
        this.mCenter = new android.graphics.Point(i3, i4);
    }

    RoundedCorner(android.view.RoundedCorner roundedCorner) {
        this.mPosition = roundedCorner.getPosition();
        this.mRadius = roundedCorner.getRadius();
        this.mCenter = new android.graphics.Point(roundedCorner.getCenter());
    }

    public int getPosition() {
        return this.mPosition;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public android.graphics.Point getCenter() {
        return new android.graphics.Point(this.mCenter);
    }

    public boolean isEmpty() {
        return this.mRadius == 0 || this.mCenter.x <= 0 || this.mCenter.y <= 0;
    }

    private java.lang.String getPositionString(int i) {
        switch (i) {
            case 0:
                return "TopLeft";
            case 1:
                return "TopRight";
            case 2:
                return "BottomRight";
            case 3:
                return "BottomLeft";
            default:
                return "Invalid";
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.view.RoundedCorner)) {
            return false;
        }
        android.view.RoundedCorner roundedCorner = (android.view.RoundedCorner) obj;
        return this.mPosition == roundedCorner.mPosition && this.mRadius == roundedCorner.mRadius && this.mCenter.equals(roundedCorner.mCenter);
    }

    public int hashCode() {
        return ((((0 + this.mPosition) * 31) + this.mRadius) * 31) + this.mCenter.hashCode();
    }

    public java.lang.String toString() {
        return "RoundedCorner{position=" + getPositionString(this.mPosition) + ", radius=" + this.mRadius + ", center=" + this.mCenter + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPosition);
        parcel.writeInt(this.mRadius);
        parcel.writeInt(this.mCenter.x);
        parcel.writeInt(this.mCenter.y);
    }
}
