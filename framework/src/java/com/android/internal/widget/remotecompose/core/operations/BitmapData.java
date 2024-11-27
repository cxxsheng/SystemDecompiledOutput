package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class BitmapData implements com.android.internal.widget.remotecompose.core.Operation {
    public static final com.android.internal.widget.remotecompose.core.operations.BitmapData.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.BitmapData.Companion();
    public static final int MAX_IMAGE_DIMENSION = 8000;
    byte[] mBitmap;
    int mImageHeight;
    int mImageId;
    int mImageWidth;

    public BitmapData(int i, int i2, int i3, byte[] bArr) {
        this.mImageId = i;
        this.mImageWidth = i2;
        this.mImageHeight = i3;
        this.mBitmap = bArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mImageId, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    public java.lang.String toString() {
        return "BITMAP DATA $imageId";
    }

    public static class Companion implements com.android.internal.widget.remotecompose.core.CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public java.lang.String name() {
            return "BitmapData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 101;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, int i2, int i3, byte[] bArr) {
            wireBuffer.start(101);
            wireBuffer.writeInt(i);
            wireBuffer.writeInt(i2);
            wireBuffer.writeInt(i3);
            wireBuffer.writeBuffer(bArr);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            int readInt = wireBuffer.readInt();
            int readInt2 = wireBuffer.readInt();
            int readInt3 = wireBuffer.readInt();
            if (readInt2 < 1 || readInt3 < 1 || readInt3 > 8000 || readInt2 > 8000) {
                throw new java.lang.RuntimeException("Dimension of image is invalid " + readInt2 + "x" + readInt3);
            }
            list.add(new com.android.internal.widget.remotecompose.core.operations.BitmapData(readInt, readInt2, readInt3, wireBuffer.readBuffer()));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.loadBitmap(this.mImageId, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public java.lang.String deepToString(java.lang.String str) {
        return str + toString();
    }
}
