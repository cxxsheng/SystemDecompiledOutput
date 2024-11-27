package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class DrawBitmapInt extends com.android.internal.widget.remotecompose.core.PaintOperation {
    public static final com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt.Companion();
    int mContentDescId;
    int mDstBottom;
    int mDstLeft;
    int mDstRight;
    int mDstTop;
    int mImageId;
    int mSrcBottom;
    int mSrcLeft;
    int mSrcRight;
    int mSrcTop;

    public DrawBitmapInt(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mContentDescId = 0;
        this.mImageId = i;
        this.mSrcLeft = i2;
        this.mSrcTop = i3;
        this.mSrcRight = i4;
        this.mSrcBottom = i5;
        this.mDstLeft = i6;
        this.mDstTop = i7;
        this.mDstRight = i8;
        this.mDstBottom = i9;
        this.mContentDescId = i10;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }

    public java.lang.String toString() {
        return "DRAW_BITMAP_INT " + this.mImageId + " on " + this.mSrcLeft + " " + this.mSrcTop + " " + this.mSrcRight + " " + this.mSrcBottom + " - " + this.mDstLeft + " " + this.mDstTop + " " + this.mDstRight + " " + this.mDstBottom + android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static class Companion implements com.android.internal.widget.remotecompose.core.CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public java.lang.String name() {
            return "DrawBitmapInt";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 44;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            wireBuffer.start(66);
            wireBuffer.writeInt(i);
            wireBuffer.writeInt(i2);
            wireBuffer.writeInt(i3);
            wireBuffer.writeInt(i4);
            wireBuffer.writeInt(i5);
            wireBuffer.writeInt(i6);
            wireBuffer.writeInt(i7);
            wireBuffer.writeInt(i8);
            wireBuffer.writeInt(i9);
            wireBuffer.writeInt(i10);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(com.android.internal.widget.remotecompose.core.PaintContext paintContext) {
        paintContext.drawBitmap(this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }
}
