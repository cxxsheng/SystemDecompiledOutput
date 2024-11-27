package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class Theme implements com.android.internal.widget.remotecompose.core.RemoteComposeOperation {
    public static final com.android.internal.widget.remotecompose.core.operations.Theme.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.Theme.Companion();
    public static final int DARK = -2;
    public static final int LIGHT = -3;
    public static final int UNSPECIFIED = -1;
    int mTheme;

    public Theme(int i) {
        this.mTheme = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mTheme);
    }

    public java.lang.String toString() {
        return "SET_THEME " + this.mTheme;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.setTheme(this.mTheme);
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
            return "SetTheme";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 63;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i) {
            wireBuffer.start(63);
            wireBuffer.writeInt(i);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.Theme(wireBuffer.readInt()));
        }
    }
}
