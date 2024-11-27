package android.view;

/* loaded from: classes4.dex */
public class MagnificationSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.MagnificationSpec> CREATOR = new android.os.Parcelable.Creator<android.view.MagnificationSpec>() { // from class: android.view.MagnificationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.MagnificationSpec[] newArray(int i) {
            return new android.view.MagnificationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.MagnificationSpec createFromParcel(android.os.Parcel parcel) {
            android.view.MagnificationSpec magnificationSpec = new android.view.MagnificationSpec();
            magnificationSpec.initFromParcel(parcel);
            return magnificationSpec;
        }
    };
    public float offsetX;
    public float offsetY;
    public float scale = 1.0f;

    public void initialize(float f, float f2, float f3) {
        if (f < 1.0f) {
            throw new java.lang.IllegalArgumentException("Scale must be greater than or equal to one!");
        }
        this.scale = f;
        this.offsetX = f2;
        this.offsetY = f3;
    }

    public boolean isNop() {
        return this.scale == 1.0f && this.offsetX == 0.0f && this.offsetY == 0.0f;
    }

    public void clear() {
        this.scale = 1.0f;
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
    }

    public void setTo(android.view.MagnificationSpec magnificationSpec) {
        this.scale = magnificationSpec.scale;
        this.offsetX = magnificationSpec.offsetX;
        this.offsetY = magnificationSpec.offsetY;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.offsetX);
        parcel.writeFloat(this.offsetY);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) obj;
        if (this.scale == magnificationSpec.scale && this.offsetX == magnificationSpec.offsetX && this.offsetY == magnificationSpec.offsetY) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.scale != 0.0f ? java.lang.Float.floatToIntBits(this.scale) : 0) * 31) + (this.offsetX != 0.0f ? java.lang.Float.floatToIntBits(this.offsetX) : 0)) * 31) + (this.offsetY != 0.0f ? java.lang.Float.floatToIntBits(this.offsetY) : 0);
    }

    public java.lang.String toString() {
        return "<scale:" + java.lang.Float.toString(this.scale) + ",offsetX:" + java.lang.Float.toString(this.offsetX) + ",offsetY:" + java.lang.Float.toString(this.offsetY) + ">";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(android.os.Parcel parcel) {
        this.scale = parcel.readFloat();
        this.offsetX = parcel.readFloat();
        this.offsetY = parcel.readFloat();
    }
}
