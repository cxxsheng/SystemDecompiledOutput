package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class RootContentBehavior implements com.android.internal.widget.remotecompose.core.RemoteComposeOperation {
    public static final int ALIGNMENT_BOTTOM = 4;
    public static final int ALIGNMENT_CENTER = 34;
    public static final int ALIGNMENT_END = 64;
    public static final int ALIGNMENT_HORIZONTAL_CENTER = 32;
    public static final int ALIGNMENT_START = 16;
    public static final int ALIGNMENT_TOP = 1;
    public static final int ALIGNMENT_VERTICAL_CENTER = 2;
    public static final com.android.internal.widget.remotecompose.core.operations.RootContentBehavior.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.RootContentBehavior.Companion();
    public static final int LAYOUT_HORIZONTAL_FIXED = 4;
    public static final int LAYOUT_HORIZONTAL_MATCH_PARENT = 1;
    public static final int LAYOUT_HORIZONTAL_WRAP_CONTENT = 2;
    public static final int LAYOUT_MATCH_PARENT = 9;
    public static final int LAYOUT_VERTICAL_FIXED = 32;
    public static final int LAYOUT_VERTICAL_MATCH_PARENT = 8;
    public static final int LAYOUT_VERTICAL_WRAP_CONTENT = 16;
    public static final int LAYOUT_WRAP_CONTENT = 18;
    public static final int NONE = 0;
    public static final int SCALE_CROP = 5;
    public static final int SCALE_FILL_BOUNDS = 6;
    public static final int SCALE_FILL_HEIGHT = 3;
    public static final int SCALE_FILL_WIDTH = 2;
    public static final int SCALE_FIT = 4;
    public static final int SCALE_INSIDE = 1;
    public static final int SCROLL_HORIZONTAL = 1;
    public static final int SCROLL_VERTICAL = 2;
    public static final int SIZING_LAYOUT = 1;
    public static final int SIZING_SCALE = 2;
    protected static final java.lang.String TAG = "RootContentBehavior";
    int mAlignment;
    int mMode;
    int mScroll;
    int mSizing;

    public RootContentBehavior(int i, int i2, int i3, int i4) {
        this.mScroll = 0;
        this.mSizing = 0;
        this.mAlignment = 34;
        this.mMode = 0;
        switch (i) {
            case 0:
            case 1:
            case 2:
                this.mScroll = i;
                break;
            default:
                android.util.Log.e(TAG, "incorrect scroll value " + i);
                break;
        }
        if (i2 == 34) {
            this.mAlignment = i2;
        } else {
            int i5 = i2 & 240;
            int i6 = i2 & 15;
            boolean z = i5 == 16 || i5 == 32 || i5 == 64;
            boolean z2 = i6 == 1 || i6 == 2 || i6 == 4;
            if (!z || !z2) {
                android.util.Log.e(TAG, "incorrect alignment  h: " + i5 + " v: " + i6);
            } else {
                this.mAlignment = i2;
            }
        }
        switch (i3) {
            case 1:
                android.util.Log.e(TAG, "sizing_layout is not yet supported");
                break;
            case 2:
                this.mSizing = i3;
                break;
            default:
                android.util.Log.e(TAG, "incorrect sizing value " + i3);
                break;
        }
        if (this.mSizing != 1) {
            if (this.mSizing == 2) {
                switch (i4) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        this.mMode = i4;
                        break;
                    default:
                        android.util.Log.e(TAG, "incorrect mode for scale sizing, mode: " + i4);
                        break;
                }
                return;
            }
            return;
        }
        if (i4 != 0) {
            android.util.Log.e(TAG, "mode for sizing layout is not yet supported");
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mScroll, this.mAlignment, this.mSizing, this.mMode);
    }

    public java.lang.String toString() {
        return "ROOT_CONTENT_BEHAVIOR scroll: " + this.mScroll + " sizing: " + this.mSizing + " mode: " + this.mMode;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.setRootContentBehavior(this.mScroll, this.mAlignment, this.mSizing, this.mMode);
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
            return com.android.internal.widget.remotecompose.core.operations.RootContentBehavior.TAG;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 65;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, int i2, int i3, int i4) {
            wireBuffer.start(65);
            wireBuffer.writeInt(i);
            wireBuffer.writeInt(i2);
            wireBuffer.writeInt(i3);
            wireBuffer.writeInt(i4);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.RootContentBehavior(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
        }
    }
}
