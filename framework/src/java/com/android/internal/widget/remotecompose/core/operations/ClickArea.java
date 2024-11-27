package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class ClickArea implements com.android.internal.widget.remotecompose.core.RemoteComposeOperation {
    public static final com.android.internal.widget.remotecompose.core.operations.ClickArea.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.ClickArea.Companion();
    float mBottom;
    int mContentDescription;
    int mId;
    float mLeft;
    int mMetadata;
    float mRight;
    float mTop;

    public ClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3) {
        this.mId = i;
        this.mContentDescription = i2;
        this.mLeft = f;
        this.mTop = f2;
        this.mRight = f3;
        this.mBottom = f4;
        this.mMetadata = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mId, this.mContentDescription, this.mLeft, this.mTop, this.mRight, this.mBottom, this.mMetadata);
    }

    public java.lang.String toString() {
        return "CLICK_AREA <" + this.mId + " <" + this.mContentDescription + "> <" + this.mMetadata + ">+" + this.mLeft + " " + this.mTop + " " + this.mRight + " " + this.mBottom + "+ (" + (this.mRight - this.mLeft) + " x " + (this.mBottom - this.mTop) + " }";
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        if (remoteContext.getMode() != com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.DATA) {
            return;
        }
        remoteContext.addClickArea(this.mId, this.mContentDescription, this.mLeft, this.mTop, this.mRight, this.mBottom, this.mMetadata);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public java.lang.String deepToString(java.lang.String str) {
        return str + toString();
    }

    public static class Companion implements com.android.internal.widget.remotecompose.core.CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public java.lang.String name() {
            return "ClickArea";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 64;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, int i2, float f, float f2, float f3, float f4, int i3) {
            wireBuffer.start(64);
            wireBuffer.writeInt(i);
            wireBuffer.writeInt(i2);
            wireBuffer.writeFloat(f);
            wireBuffer.writeFloat(f2);
            wireBuffer.writeFloat(f3);
            wireBuffer.writeFloat(f4);
            wireBuffer.writeInt(i3);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.ClickArea(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
        }
    }
}
