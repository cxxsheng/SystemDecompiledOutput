package com.android.server.wm.nano;

/* loaded from: classes5.dex */
public interface WindowManagerProtos {

    public static final class TaskSnapshotProto extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto[] _emptyArray;
        public int appearance;
        public long id;
        public int insetBottom;
        public int insetLeft;
        public int insetRight;
        public int insetTop;
        public boolean isRealSnapshot;
        public boolean isTranslucent;
        public float legacyScale;
        public int letterboxInsetBottom;
        public int letterboxInsetLeft;
        public int letterboxInsetRight;
        public int letterboxInsetTop;
        public int orientation;
        public int rotation;
        public int systemUiVisibility;
        public int taskHeight;
        public int taskWidth;
        public java.lang.String topActivityComponent;
        public int windowingMode;

        public static com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public TaskSnapshotProto() {
            clear();
        }

        public com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto clear() {
            this.orientation = 0;
            this.insetLeft = 0;
            this.insetTop = 0;
            this.insetRight = 0;
            this.insetBottom = 0;
            this.isRealSnapshot = false;
            this.windowingMode = 0;
            this.systemUiVisibility = 0;
            this.isTranslucent = false;
            this.topActivityComponent = "";
            this.legacyScale = 0.0f;
            this.id = 0L;
            this.rotation = 0;
            this.taskWidth = 0;
            this.taskHeight = 0;
            this.appearance = 0;
            this.letterboxInsetLeft = 0;
            this.letterboxInsetTop = 0;
            this.letterboxInsetRight = 0;
            this.letterboxInsetBottom = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.orientation != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                codedOutputByteBufferNano.writeBool(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                codedOutputByteBufferNano.writeBool(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.topActivityComponent);
            }
            if (java.lang.Float.floatToIntBits(this.legacyScale) != java.lang.Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(11, this.legacyScale);
            }
            if (this.id != 0) {
                codedOutputByteBufferNano.writeInt64(12, this.id);
            }
            if (this.rotation != 0) {
                codedOutputByteBufferNano.writeInt32(13, this.rotation);
            }
            if (this.taskWidth != 0) {
                codedOutputByteBufferNano.writeInt32(14, this.taskWidth);
            }
            if (this.taskHeight != 0) {
                codedOutputByteBufferNano.writeInt32(15, this.taskHeight);
            }
            if (this.appearance != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.appearance);
            }
            if (this.letterboxInsetLeft != 0) {
                codedOutputByteBufferNano.writeInt32(17, this.letterboxInsetLeft);
            }
            if (this.letterboxInsetTop != 0) {
                codedOutputByteBufferNano.writeInt32(18, this.letterboxInsetTop);
            }
            if (this.letterboxInsetRight != 0) {
                codedOutputByteBufferNano.writeInt32(19, this.letterboxInsetRight);
            }
            if (this.letterboxInsetBottom != 0) {
                codedOutputByteBufferNano.writeInt32(20, this.letterboxInsetBottom);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.orientation != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(10, this.topActivityComponent);
            }
            if (java.lang.Float.floatToIntBits(this.legacyScale) != java.lang.Float.floatToIntBits(0.0f)) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFloatSize(11, this.legacyScale);
            }
            if (this.id != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(12, this.id);
            }
            if (this.rotation != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(13, this.rotation);
            }
            if (this.taskWidth != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(14, this.taskWidth);
            }
            if (this.taskHeight != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(15, this.taskHeight);
            }
            if (this.appearance != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(16, this.appearance);
            }
            if (this.letterboxInsetLeft != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(17, this.letterboxInsetLeft);
            }
            if (this.letterboxInsetTop != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(18, this.letterboxInsetTop);
            }
            if (this.letterboxInsetRight != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(19, this.letterboxInsetRight);
            }
            if (this.letterboxInsetBottom != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(20, this.letterboxInsetBottom);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.orientation = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.insetLeft = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.insetTop = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.insetRight = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.insetBottom = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.isRealSnapshot = codedInputByteBufferNano.readBool();
                        break;
                    case 56:
                        this.windowingMode = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.systemUiVisibility = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.isTranslucent = codedInputByteBufferNano.readBool();
                        break;
                    case 82:
                        this.topActivityComponent = codedInputByteBufferNano.readString();
                        break;
                    case 93:
                        this.legacyScale = codedInputByteBufferNano.readFloat();
                        break;
                    case 96:
                        this.id = codedInputByteBufferNano.readInt64();
                        break;
                    case 104:
                        this.rotation = codedInputByteBufferNano.readInt32();
                        break;
                    case 112:
                        this.taskWidth = codedInputByteBufferNano.readInt32();
                        break;
                    case 120:
                        this.taskHeight = codedInputByteBufferNano.readInt32();
                        break;
                    case 128:
                        this.appearance = codedInputByteBufferNano.readInt32();
                        break;
                    case 136:
                        this.letterboxInsetLeft = codedInputByteBufferNano.readInt32();
                        break;
                    case 144:
                        this.letterboxInsetTop = codedInputByteBufferNano.readInt32();
                        break;
                    case 152:
                        this.letterboxInsetRight = codedInputByteBufferNano.readInt32();
                        break;
                    case 160:
                        this.letterboxInsetBottom = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto(), bArr);
        }

        public static com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class LetterboxProto extends com.android.framework.protobuf.nano.MessageNano {
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_CENTER = 1;
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_LEFT = 0;
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_RIGHT = 2;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_BOTTOM = 2;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_CENTER = 1;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_TOP = 0;
        private static volatile com.android.server.wm.nano.WindowManagerProtos.LetterboxProto[] _emptyArray;
        public int letterboxPositionForBookModeReachability;
        public int letterboxPositionForHorizontalReachability;
        public int letterboxPositionForTabletopModeReachability;
        public int letterboxPositionForVerticalReachability;

        public static com.android.server.wm.nano.WindowManagerProtos.LetterboxProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.wm.nano.WindowManagerProtos.LetterboxProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public LetterboxProto() {
            clear();
        }

        public com.android.server.wm.nano.WindowManagerProtos.LetterboxProto clear() {
            this.letterboxPositionForHorizontalReachability = 0;
            this.letterboxPositionForVerticalReachability = 0;
            this.letterboxPositionForBookModeReachability = 0;
            this.letterboxPositionForTabletopModeReachability = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.letterboxPositionForHorizontalReachability != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.letterboxPositionForHorizontalReachability);
            }
            if (this.letterboxPositionForVerticalReachability != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.letterboxPositionForVerticalReachability);
            }
            if (this.letterboxPositionForBookModeReachability != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.letterboxPositionForBookModeReachability);
            }
            if (this.letterboxPositionForTabletopModeReachability != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.letterboxPositionForTabletopModeReachability);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.letterboxPositionForHorizontalReachability != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.letterboxPositionForHorizontalReachability);
            }
            if (this.letterboxPositionForVerticalReachability != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.letterboxPositionForVerticalReachability);
            }
            if (this.letterboxPositionForBookModeReachability != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.letterboxPositionForBookModeReachability);
            }
            if (this.letterboxPositionForTabletopModeReachability != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.letterboxPositionForTabletopModeReachability);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.wm.nano.WindowManagerProtos.LetterboxProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForHorizontalReachability = readInt32;
                                break;
                        }
                    case 16:
                        int readInt322 = codedInputByteBufferNano.readInt32();
                        switch (readInt322) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForVerticalReachability = readInt322;
                                break;
                        }
                    case 24:
                        int readInt323 = codedInputByteBufferNano.readInt32();
                        switch (readInt323) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForBookModeReachability = readInt323;
                                break;
                        }
                    case 32:
                        int readInt324 = codedInputByteBufferNano.readInt32();
                        switch (readInt324) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForTabletopModeReachability = readInt324;
                                break;
                        }
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.wm.nano.WindowManagerProtos.LetterboxProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.wm.nano.WindowManagerProtos.LetterboxProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.wm.nano.WindowManagerProtos.LetterboxProto(), bArr);
        }

        public static com.android.server.wm.nano.WindowManagerProtos.LetterboxProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.wm.nano.WindowManagerProtos.LetterboxProto().mergeFrom(codedInputByteBufferNano);
        }
    }
}
