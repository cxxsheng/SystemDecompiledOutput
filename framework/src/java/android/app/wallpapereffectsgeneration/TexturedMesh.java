package android.app.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TexturedMesh implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.TexturedMesh> CREATOR = new android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.TexturedMesh>() { // from class: android.app.wallpapereffectsgeneration.TexturedMesh.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.TexturedMesh createFromParcel(android.os.Parcel parcel) {
            return new android.app.wallpapereffectsgeneration.TexturedMesh(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.TexturedMesh[] newArray(int i) {
            return new android.app.wallpapereffectsgeneration.TexturedMesh[i];
        }
    };
    public static final int INDICES_LAYOUT_TRIANGLES = 1;
    public static final int INDICES_LAYOUT_UNDEFINED = 0;
    public static final int VERTICES_LAYOUT_POSITION3_UV2 = 1;
    public static final int VERTICES_LAYOUT_UNDEFINED = 0;
    private android.graphics.Bitmap mBitmap;
    private int[] mIndices;
    private int mIndicesLayoutType;
    private float[] mVertices;
    private int mVerticesLayoutType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IndicesLayoutType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerticesLayoutType {
    }

    private TexturedMesh(android.os.Parcel parcel) {
        this.mIndicesLayoutType = parcel.readInt();
        this.mVerticesLayoutType = parcel.readInt();
        this.mBitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            byte[] readBlob = parcel.readBlob();
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            this.mIndices = obtain.createIntArray();
            this.mVertices = obtain.createFloatArray();
        } finally {
            obtain.recycle();
        }
    }

    private TexturedMesh(android.graphics.Bitmap bitmap, int[] iArr, float[] fArr, int i, int i2) {
        this.mBitmap = bitmap;
        this.mIndices = iArr;
        this.mVertices = fArr;
        this.mIndicesLayoutType = i;
        this.mVerticesLayoutType = i2;
    }

    public android.graphics.Bitmap getBitmap() {
        return this.mBitmap;
    }

    public int[] getIndices() {
        return this.mIndices;
    }

    public float[] getVertices() {
        return this.mVertices;
    }

    public int getIndicesLayoutType() {
        return this.mIndicesLayoutType;
    }

    public int getVerticesLayoutType() {
        return this.mVerticesLayoutType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mIndicesLayoutType);
        parcel.writeInt(this.mVerticesLayoutType);
        parcel.writeTypedObject(this.mBitmap, i);
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeIntArray(this.mIndices);
            obtain.writeFloatArray(this.mVertices);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.graphics.Bitmap mBitmap;
        private int[] mIndices;
        private int mIndicesLayoutType;
        private float[] mVertices;
        private int mVerticesLayouttype;

        @android.annotation.SystemApi
        public Builder(android.graphics.Bitmap bitmap) {
            this.mBitmap = bitmap;
        }

        public android.app.wallpapereffectsgeneration.TexturedMesh.Builder setIndices(int[] iArr) {
            this.mIndices = iArr;
            return this;
        }

        public android.app.wallpapereffectsgeneration.TexturedMesh.Builder setVertices(float[] fArr) {
            this.mVertices = fArr;
            return this;
        }

        public android.app.wallpapereffectsgeneration.TexturedMesh.Builder setIndicesLayoutType(int i) {
            this.mIndicesLayoutType = i;
            return this;
        }

        public android.app.wallpapereffectsgeneration.TexturedMesh.Builder setVerticesLayoutType(int i) {
            this.mVerticesLayouttype = i;
            return this;
        }

        public android.app.wallpapereffectsgeneration.TexturedMesh build() {
            return new android.app.wallpapereffectsgeneration.TexturedMesh(this.mBitmap, this.mIndices, this.mVertices, this.mIndicesLayoutType, this.mVerticesLayouttype);
        }
    }
}
