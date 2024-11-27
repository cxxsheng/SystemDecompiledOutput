package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class Header implements com.android.internal.widget.remotecompose.core.RemoteComposeOperation {
    public static final com.android.internal.widget.remotecompose.core.operations.Header.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.Header.Companion();
    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 1;
    public static final int PATCH_VERSION = 0;
    long mCapabilities;
    int mHeight;
    int mMajorVersion;
    int mMinorVersion;
    int mPatchVersion;
    int mWidth;

    public Header(int i, int i2, int i3, int i4, int i5, long j) {
        this.mMajorVersion = i;
        this.mMinorVersion = i2;
        this.mPatchVersion = i3;
        this.mWidth = i4;
        this.mHeight = i5;
        this.mCapabilities = j;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mWidth, this.mHeight, this.mCapabilities);
    }

    public java.lang.String toString() {
        return "HEADER v" + this.mMajorVersion + android.media.MediaMetrics.SEPARATOR + this.mMinorVersion + android.media.MediaMetrics.SEPARATOR + this.mPatchVersion + ", " + this.mWidth + " x " + this.mHeight + " [" + this.mCapabilities + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.header(this.mMajorVersion, this.mMinorVersion, this.mPatchVersion, this.mWidth, this.mHeight, this.mCapabilities);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public java.lang.String deepToString(java.lang.String str) {
        return toString();
    }

    public static class Companion implements com.android.internal.widget.remotecompose.core.CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public java.lang.String name() {
            return "Header";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 0;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, int i2, long j) {
            wireBuffer.start(0);
            wireBuffer.writeInt(0);
            wireBuffer.writeInt(1);
            wireBuffer.writeInt(0);
            wireBuffer.writeInt(i);
            wireBuffer.writeInt(i2);
            wireBuffer.writeLong(j);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.Header(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readLong()));
        }
    }
}
