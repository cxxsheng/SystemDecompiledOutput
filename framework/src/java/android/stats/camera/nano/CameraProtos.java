package android.stats.camera.nano;

/* loaded from: classes3.dex */
public interface CameraProtos {

    public static final class CameraStreamProto extends com.android.framework.protobuf.nano.MessageNano {
        public static final int CAPTURE_LATENCY = 1;
        public static final int UNKNOWN = 0;
        private static volatile android.stats.camera.nano.CameraProtos.CameraStreamProto[] _emptyArray;
        public int colorSpace;
        public int dataSpace;
        public long dynamicRangeProfile;
        public long errorCount;
        public int firstCaptureLatencyMillis;
        public int format;
        public int height;
        public float[] histogramBins;
        public long[] histogramCounts;
        public int histogramType;
        public int maxAppBuffers;
        public int maxHalBuffers;
        public long requestCount;
        public long streamUseCase;
        public long usage;
        public int width;

        public static android.stats.camera.nano.CameraProtos.CameraStreamProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.stats.camera.nano.CameraProtos.CameraStreamProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public CameraStreamProto() {
            clear();
        }

        public android.stats.camera.nano.CameraProtos.CameraStreamProto clear() {
            this.width = 0;
            this.height = 0;
            this.format = 0;
            this.dataSpace = 0;
            this.usage = 0L;
            this.requestCount = 0L;
            this.errorCount = 0L;
            this.firstCaptureLatencyMillis = 0;
            this.maxHalBuffers = 0;
            this.maxAppBuffers = 0;
            this.histogramType = 0;
            this.histogramBins = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_FLOAT_ARRAY;
            this.histogramCounts = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_LONG_ARRAY;
            this.dynamicRangeProfile = 0L;
            this.streamUseCase = 0L;
            this.colorSpace = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.width != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.width);
            }
            if (this.height != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.height);
            }
            if (this.format != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.format);
            }
            if (this.dataSpace != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.dataSpace);
            }
            if (this.usage != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.usage);
            }
            if (this.requestCount != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.requestCount);
            }
            if (this.errorCount != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.errorCount);
            }
            if (this.firstCaptureLatencyMillis != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.firstCaptureLatencyMillis);
            }
            if (this.maxHalBuffers != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.maxHalBuffers);
            }
            if (this.maxAppBuffers != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.maxAppBuffers);
            }
            if (this.histogramType != 0) {
                codedOutputByteBufferNano.writeInt32(11, this.histogramType);
            }
            if (this.histogramBins != null && this.histogramBins.length > 0) {
                for (int i = 0; i < this.histogramBins.length; i++) {
                    codedOutputByteBufferNano.writeFloat(12, this.histogramBins[i]);
                }
            }
            if (this.histogramCounts != null && this.histogramCounts.length > 0) {
                for (int i2 = 0; i2 < this.histogramCounts.length; i2++) {
                    codedOutputByteBufferNano.writeInt64(13, this.histogramCounts[i2]);
                }
            }
            if (this.dynamicRangeProfile != 0) {
                codedOutputByteBufferNano.writeInt64(14, this.dynamicRangeProfile);
            }
            if (this.streamUseCase != 0) {
                codedOutputByteBufferNano.writeInt64(15, this.streamUseCase);
            }
            if (this.colorSpace != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.colorSpace);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.width != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.width);
            }
            if (this.height != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.height);
            }
            if (this.format != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.format);
            }
            if (this.dataSpace != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.dataSpace);
            }
            if (this.usage != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.usage);
            }
            if (this.requestCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(6, this.requestCount);
            }
            if (this.errorCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(7, this.errorCount);
            }
            if (this.firstCaptureLatencyMillis != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(8, this.firstCaptureLatencyMillis);
            }
            if (this.maxHalBuffers != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(9, this.maxHalBuffers);
            }
            if (this.maxAppBuffers != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(10, this.maxAppBuffers);
            }
            if (this.histogramType != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(11, this.histogramType);
            }
            if (this.histogramBins != null && this.histogramBins.length > 0) {
                computeSerializedSize = computeSerializedSize + (this.histogramBins.length * 4) + (this.histogramBins.length * 1);
            }
            if (this.histogramCounts != null && this.histogramCounts.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.histogramCounts.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64SizeNoTag(this.histogramCounts[i2]);
                }
                computeSerializedSize = computeSerializedSize + i + (this.histogramCounts.length * 1);
            }
            if (this.dynamicRangeProfile != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(14, this.dynamicRangeProfile);
            }
            if (this.streamUseCase != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(15, this.streamUseCase);
            }
            if (this.colorSpace != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(16, this.colorSpace);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.stats.camera.nano.CameraProtos.CameraStreamProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.width = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.height = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.format = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.dataSpace = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.usage = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.requestCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.errorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.firstCaptureLatencyMillis = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.maxHalBuffers = codedInputByteBufferNano.readInt32();
                        break;
                    case 80:
                        this.maxAppBuffers = codedInputByteBufferNano.readInt32();
                        break;
                    case 88:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                                this.histogramType = readInt32;
                                break;
                        }
                    case 98:
                        int readRawVarint32 = codedInputByteBufferNano.readRawVarint32();
                        int pushLimit = codedInputByteBufferNano.pushLimit(readRawVarint32);
                        int i = readRawVarint32 / 4;
                        int length = this.histogramBins == null ? 0 : this.histogramBins.length;
                        int i2 = i + length;
                        float[] fArr = new float[i2];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.histogramBins, 0, fArr, 0, length);
                        }
                        while (length < i2) {
                            fArr[length] = codedInputByteBufferNano.readFloat();
                            length++;
                        }
                        this.histogramBins = fArr;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 101:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 101);
                        int length2 = this.histogramBins == null ? 0 : this.histogramBins.length;
                        int i3 = repeatedFieldArrayLength + length2;
                        float[] fArr2 = new float[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.histogramBins, 0, fArr2, 0, length2);
                        }
                        while (length2 < i3 - 1) {
                            fArr2[length2] = codedInputByteBufferNano.readFloat();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        fArr2[length2] = codedInputByteBufferNano.readFloat();
                        this.histogramBins = fArr2;
                        break;
                    case 104:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 104);
                        int length3 = this.histogramCounts == null ? 0 : this.histogramCounts.length;
                        int i4 = repeatedFieldArrayLength2 + length3;
                        long[] jArr = new long[i4];
                        if (length3 != 0) {
                            java.lang.System.arraycopy(this.histogramCounts, 0, jArr, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            jArr[length3] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        jArr[length3] = codedInputByteBufferNano.readInt64();
                        this.histogramCounts = jArr;
                        break;
                    case 106:
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i5 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            i5++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length4 = this.histogramCounts == null ? 0 : this.histogramCounts.length;
                        int i6 = i5 + length4;
                        long[] jArr2 = new long[i6];
                        if (length4 != 0) {
                            java.lang.System.arraycopy(this.histogramCounts, 0, jArr2, 0, length4);
                        }
                        while (length4 < i6) {
                            jArr2[length4] = codedInputByteBufferNano.readInt64();
                            length4++;
                        }
                        this.histogramCounts = jArr2;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                        break;
                    case 112:
                        this.dynamicRangeProfile = codedInputByteBufferNano.readInt64();
                        break;
                    case 120:
                        this.streamUseCase = codedInputByteBufferNano.readInt64();
                        break;
                    case 128:
                        this.colorSpace = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.stats.camera.nano.CameraProtos.CameraStreamProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.stats.camera.nano.CameraProtos.CameraStreamProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.stats.camera.nano.CameraProtos.CameraStreamProto(), bArr);
        }

        public static android.stats.camera.nano.CameraProtos.CameraStreamProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.stats.camera.nano.CameraProtos.CameraStreamProto().mergeFrom(codedInputByteBufferNano);
        }
    }
}
