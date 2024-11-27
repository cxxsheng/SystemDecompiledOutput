package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class RootContentDescription implements com.android.internal.widget.remotecompose.core.RemoteComposeOperation {
    public static final com.android.internal.widget.remotecompose.core.operations.RootContentDescription.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.RootContentDescription.Companion();
    int mContentDescription;

    public RootContentDescription(int i) {
        this.mContentDescription = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mContentDescription);
    }

    public java.lang.String toString() {
        return "ROOT_CONTENT_DESCRIPTION " + this.mContentDescription;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.setDocumentContentDescription(this.mContentDescription);
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
            return "RootContentDescription";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 103;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i) {
            wireBuffer.start(103);
            wireBuffer.writeInt(i);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.RootContentDescription(wireBuffer.readInt()));
        }
    }
}
