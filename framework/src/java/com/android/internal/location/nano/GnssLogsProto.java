package com.android.internal.location.nano;

/* loaded from: classes4.dex */
public interface GnssLogsProto {

    public static final class GnssLog extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.internal.location.nano.GnssLogsProto.GnssLog[] _emptyArray;
        public java.lang.String hardwareRevision;
        public double meanL5TopFourAverageCn0DbHz;
        public int meanPositionAccuracyMeters;
        public int meanTimeToFirstFixSecs;
        public double meanTopFourAverageCn0DbHz;
        public int numL5SvStatusProcessed;
        public int numL5SvStatusUsedInFix;
        public int numL5TopFourAverageCn0Processed;
        public int numLocationReportProcessed;
        public int numPositionAccuracyProcessed;
        public int numSvStatusProcessed;
        public int numSvStatusUsedInFix;
        public int numTimeToFirstFixProcessed;
        public int numTopFourAverageCn0Processed;
        public int percentageLocationFailure;
        public com.android.internal.location.nano.GnssLogsProto.PowerMetrics powerMetrics;
        public double standardDeviationL5TopFourAverageCn0DbHz;
        public int standardDeviationPositionAccuracyMeters;
        public int standardDeviationTimeToFirstFixSecs;
        public double standardDeviationTopFourAverageCn0DbHz;

        public static com.android.internal.location.nano.GnssLogsProto.GnssLog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.internal.location.nano.GnssLogsProto.GnssLog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public GnssLog() {
            clear();
        }

        public com.android.internal.location.nano.GnssLogsProto.GnssLog clear() {
            this.numLocationReportProcessed = 0;
            this.percentageLocationFailure = 0;
            this.numTimeToFirstFixProcessed = 0;
            this.meanTimeToFirstFixSecs = 0;
            this.standardDeviationTimeToFirstFixSecs = 0;
            this.numPositionAccuracyProcessed = 0;
            this.meanPositionAccuracyMeters = 0;
            this.standardDeviationPositionAccuracyMeters = 0;
            this.numTopFourAverageCn0Processed = 0;
            this.meanTopFourAverageCn0DbHz = 0.0d;
            this.standardDeviationTopFourAverageCn0DbHz = 0.0d;
            this.powerMetrics = null;
            this.hardwareRevision = "";
            this.numSvStatusProcessed = 0;
            this.numL5SvStatusProcessed = 0;
            this.numSvStatusUsedInFix = 0;
            this.numL5SvStatusUsedInFix = 0;
            this.numL5TopFourAverageCn0Processed = 0;
            this.meanL5TopFourAverageCn0DbHz = 0.0d;
            this.standardDeviationL5TopFourAverageCn0DbHz = 0.0d;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.numLocationReportProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.numLocationReportProcessed);
            }
            if (this.percentageLocationFailure != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.percentageLocationFailure);
            }
            if (this.numTimeToFirstFixProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.numTimeToFirstFixProcessed);
            }
            if (this.meanTimeToFirstFixSecs != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.meanTimeToFirstFixSecs);
            }
            if (this.standardDeviationTimeToFirstFixSecs != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.standardDeviationTimeToFirstFixSecs);
            }
            if (this.numPositionAccuracyProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.numPositionAccuracyProcessed);
            }
            if (this.meanPositionAccuracyMeters != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.meanPositionAccuracyMeters);
            }
            if (this.standardDeviationPositionAccuracyMeters != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.standardDeviationPositionAccuracyMeters);
            }
            if (this.numTopFourAverageCn0Processed != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.numTopFourAverageCn0Processed);
            }
            if (java.lang.Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(10, this.meanTopFourAverageCn0DbHz);
            }
            if (java.lang.Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            if (this.powerMetrics != null) {
                codedOutputByteBufferNano.writeMessage(12, this.powerMetrics);
            }
            if (!this.hardwareRevision.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.hardwareRevision);
            }
            if (this.numSvStatusProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(14, this.numSvStatusProcessed);
            }
            if (this.numL5SvStatusProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(15, this.numL5SvStatusProcessed);
            }
            if (this.numSvStatusUsedInFix != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.numSvStatusUsedInFix);
            }
            if (this.numL5SvStatusUsedInFix != 0) {
                codedOutputByteBufferNano.writeInt32(17, this.numL5SvStatusUsedInFix);
            }
            if (this.numL5TopFourAverageCn0Processed != 0) {
                codedOutputByteBufferNano.writeInt32(18, this.numL5TopFourAverageCn0Processed);
            }
            if (java.lang.Double.doubleToLongBits(this.meanL5TopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(19, this.meanL5TopFourAverageCn0DbHz);
            }
            if (java.lang.Double.doubleToLongBits(this.standardDeviationL5TopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(20, this.standardDeviationL5TopFourAverageCn0DbHz);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.numLocationReportProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.numLocationReportProcessed);
            }
            if (this.percentageLocationFailure != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.percentageLocationFailure);
            }
            if (this.numTimeToFirstFixProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.numTimeToFirstFixProcessed);
            }
            if (this.meanTimeToFirstFixSecs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.meanTimeToFirstFixSecs);
            }
            if (this.standardDeviationTimeToFirstFixSecs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.standardDeviationTimeToFirstFixSecs);
            }
            if (this.numPositionAccuracyProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(6, this.numPositionAccuracyProcessed);
            }
            if (this.meanPositionAccuracyMeters != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(7, this.meanPositionAccuracyMeters);
            }
            if (this.standardDeviationPositionAccuracyMeters != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(8, this.standardDeviationPositionAccuracyMeters);
            }
            if (this.numTopFourAverageCn0Processed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(9, this.numTopFourAverageCn0Processed);
            }
            if (java.lang.Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(10, this.meanTopFourAverageCn0DbHz);
            }
            if (java.lang.Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            if (this.powerMetrics != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(12, this.powerMetrics);
            }
            if (!this.hardwareRevision.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(13, this.hardwareRevision);
            }
            if (this.numSvStatusProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(14, this.numSvStatusProcessed);
            }
            if (this.numL5SvStatusProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(15, this.numL5SvStatusProcessed);
            }
            if (this.numSvStatusUsedInFix != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(16, this.numSvStatusUsedInFix);
            }
            if (this.numL5SvStatusUsedInFix != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(17, this.numL5SvStatusUsedInFix);
            }
            if (this.numL5TopFourAverageCn0Processed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(18, this.numL5TopFourAverageCn0Processed);
            }
            if (java.lang.Double.doubleToLongBits(this.meanL5TopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(19, this.meanL5TopFourAverageCn0DbHz);
            }
            if (java.lang.Double.doubleToLongBits(this.standardDeviationL5TopFourAverageCn0DbHz) != java.lang.Double.doubleToLongBits(0.0d)) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(20, this.standardDeviationL5TopFourAverageCn0DbHz);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.internal.location.nano.GnssLogsProto.GnssLog mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.numLocationReportProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.percentageLocationFailure = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.numTimeToFirstFixProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.meanTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.standardDeviationTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.numPositionAccuracyProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 56:
                        this.meanPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.standardDeviationPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.numTopFourAverageCn0Processed = codedInputByteBufferNano.readInt32();
                        break;
                    case 81:
                        this.meanTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 89:
                        this.standardDeviationTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 98:
                        if (this.powerMetrics == null) {
                            this.powerMetrics = new com.android.internal.location.nano.GnssLogsProto.PowerMetrics();
                        }
                        codedInputByteBufferNano.readMessage(this.powerMetrics);
                        break;
                    case 106:
                        this.hardwareRevision = codedInputByteBufferNano.readString();
                        break;
                    case 112:
                        this.numSvStatusProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 120:
                        this.numL5SvStatusProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 128:
                        this.numSvStatusUsedInFix = codedInputByteBufferNano.readInt32();
                        break;
                    case 136:
                        this.numL5SvStatusUsedInFix = codedInputByteBufferNano.readInt32();
                        break;
                    case 144:
                        this.numL5TopFourAverageCn0Processed = codedInputByteBufferNano.readInt32();
                        break;
                    case 153:
                        this.meanL5TopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 161:
                        this.standardDeviationL5TopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.internal.location.nano.GnssLogsProto.GnssLog parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.internal.location.nano.GnssLogsProto.GnssLog) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.internal.location.nano.GnssLogsProto.GnssLog(), bArr);
        }

        public static com.android.internal.location.nano.GnssLogsProto.GnssLog parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.internal.location.nano.GnssLogsProto.GnssLog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class PowerMetrics extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.internal.location.nano.GnssLogsProto.PowerMetrics[] _emptyArray;
        public double energyConsumedMah;
        public long loggingDurationMs;
        public long[] timeInSignalQualityLevelMs;

        public static com.android.internal.location.nano.GnssLogsProto.PowerMetrics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.internal.location.nano.GnssLogsProto.PowerMetrics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public PowerMetrics() {
            clear();
        }

        public com.android.internal.location.nano.GnssLogsProto.PowerMetrics clear() {
            this.loggingDurationMs = 0L;
            this.energyConsumedMah = 0.0d;
            this.timeInSignalQualityLevelMs = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_LONG_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.loggingDurationMs != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.loggingDurationMs);
            }
            if (java.lang.Double.doubleToLongBits(this.energyConsumedMah) != java.lang.Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(2, this.energyConsumedMah);
            }
            if (this.timeInSignalQualityLevelMs != null && this.timeInSignalQualityLevelMs.length > 0) {
                for (int i = 0; i < this.timeInSignalQualityLevelMs.length; i++) {
                    codedOutputByteBufferNano.writeInt64(3, this.timeInSignalQualityLevelMs[i]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.loggingDurationMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.loggingDurationMs);
            }
            if (java.lang.Double.doubleToLongBits(this.energyConsumedMah) != java.lang.Double.doubleToLongBits(0.0d)) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(2, this.energyConsumedMah);
            }
            if (this.timeInSignalQualityLevelMs != null && this.timeInSignalQualityLevelMs.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.timeInSignalQualityLevelMs.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64SizeNoTag(this.timeInSignalQualityLevelMs[i2]);
                }
                return computeSerializedSize + i + (this.timeInSignalQualityLevelMs.length * 1);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.internal.location.nano.GnssLogsProto.PowerMetrics mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.loggingDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 17:
                        this.energyConsumedMah = codedInputByteBufferNano.readDouble();
                        break;
                    case 24:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        int length = this.timeInSignalQualityLevelMs == null ? 0 : this.timeInSignalQualityLevelMs.length;
                        int i = repeatedFieldArrayLength + length;
                        long[] jArr = new long[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.timeInSignalQualityLevelMs, 0, jArr, 0, length);
                        }
                        while (length < i - 1) {
                            jArr[length] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        jArr[length] = codedInputByteBufferNano.readInt64();
                        this.timeInSignalQualityLevelMs = jArr;
                        break;
                    case 26:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length2 = this.timeInSignalQualityLevelMs == null ? 0 : this.timeInSignalQualityLevelMs.length;
                        int i3 = i2 + length2;
                        long[] jArr2 = new long[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.timeInSignalQualityLevelMs, 0, jArr2, 0, length2);
                        }
                        while (length2 < i3) {
                            jArr2[length2] = codedInputByteBufferNano.readInt64();
                            length2++;
                        }
                        this.timeInSignalQualityLevelMs = jArr2;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.internal.location.nano.GnssLogsProto.PowerMetrics parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.internal.location.nano.GnssLogsProto.PowerMetrics) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.internal.location.nano.GnssLogsProto.PowerMetrics(), bArr);
        }

        public static com.android.internal.location.nano.GnssLogsProto.PowerMetrics parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.internal.location.nano.GnssLogsProto.PowerMetrics().mergeFrom(codedInputByteBufferNano);
        }
    }
}
