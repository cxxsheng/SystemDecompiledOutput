package com.android.internal.widget.remotecompose.core.operations;

/* loaded from: classes5.dex */
public class TextData implements com.android.internal.widget.remotecompose.core.Operation {
    public static final com.android.internal.widget.remotecompose.core.operations.TextData.Companion COMPANION = new com.android.internal.widget.remotecompose.core.operations.TextData.Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public java.lang.String mText;
    public int mTextId;

    public TextData(int i, java.lang.String str) {
        this.mTextId = i;
        this.mText = str;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        COMPANION.apply(wireBuffer, this.mTextId, this.mText);
    }

    public java.lang.String toString() {
        return "TEXT DATA " + this.mTextId + "\"" + this.mText + "\"";
    }

    public static class Companion implements com.android.internal.widget.remotecompose.core.CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public java.lang.String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 102;
        }

        public void apply(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, int i, java.lang.String str) {
            wireBuffer.start(102);
            wireBuffer.writeInt(i);
            wireBuffer.writeUTF8(str);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list) {
            list.add(new com.android.internal.widget.remotecompose.core.operations.TextData(wireBuffer.readInt(), wireBuffer.readUTF8(4000)));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        remoteContext.loadText(this.mTextId, this.mText);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public java.lang.String deepToString(java.lang.String str) {
        return str + toString();
    }
}
