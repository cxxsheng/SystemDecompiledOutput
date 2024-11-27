package android.util.proto;

/* loaded from: classes3.dex */
public final class ProtoOutputStream extends android.util.proto.ProtoStream {
    public static final java.lang.String TAG = "ProtoOutputStream";
    private android.util.proto.EncodedBuffer mBuffer;
    private boolean mCompacted;
    private int mCopyBegin;
    private int mDepth;
    private long mExpectedObjectToken;
    private int mNextObjectId;
    private java.io.OutputStream mStream;

    public ProtoOutputStream() {
        this(0);
    }

    public ProtoOutputStream(int i) {
        this.mNextObjectId = -1;
        this.mBuffer = new android.util.proto.EncodedBuffer(i);
    }

    public ProtoOutputStream(java.io.OutputStream outputStream) {
        this();
        this.mStream = outputStream;
    }

    public ProtoOutputStream(java.io.FileDescriptor fileDescriptor) {
        this(new java.io.FileOutputStream(fileDescriptor));
    }

    public int getRawSize() {
        if (this.mCompacted) {
            return getBytes().length;
        }
        return this.mBuffer.getSize();
    }

    public void write(long j, double d) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 257:
                writeDoubleImpl(i, d);
                return;
            case 258:
                writeFloatImpl(i, (float) d);
                return;
            case 259:
                writeInt64Impl(i, (long) d);
                return;
            case 260:
                writeUInt64Impl(i, (long) d);
                return;
            case 261:
                writeInt32Impl(i, (int) d);
                return;
            case 262:
                writeFixed64Impl(i, (long) d);
                return;
            case 263:
                writeFixed32Impl(i, (int) d);
                return;
            case 264:
                writeBoolImpl(i, d != 0.0d);
                return;
            case 269:
                writeUInt32Impl(i, (int) d);
                return;
            case 270:
                writeEnumImpl(i, (int) d);
                return;
            case 271:
                writeSFixed32Impl(i, (int) d);
                return;
            case 272:
                writeSFixed64Impl(i, (long) d);
                return;
            case 273:
                writeSInt32Impl(i, (int) d);
                return;
            case 274:
                writeSInt64Impl(i, (long) d);
                return;
            case 513:
            case 1281:
                writeRepeatedDoubleImpl(i, d);
                return;
            case 514:
            case 1282:
                writeRepeatedFloatImpl(i, (float) d);
                return;
            case 515:
            case 1283:
                writeRepeatedInt64Impl(i, (long) d);
                return;
            case 516:
            case 1284:
                writeRepeatedUInt64Impl(i, (long) d);
                return;
            case 517:
            case 1285:
                writeRepeatedInt32Impl(i, (int) d);
                return;
            case 518:
            case 1286:
                writeRepeatedFixed64Impl(i, (long) d);
                return;
            case 519:
            case 1287:
                writeRepeatedFixed32Impl(i, (int) d);
                return;
            case 520:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                writeRepeatedBoolImpl(i, d != 0.0d);
                return;
            case 525:
            case 1293:
                writeRepeatedUInt32Impl(i, (int) d);
                return;
            case 526:
            case 1294:
                writeRepeatedEnumImpl(i, (int) d);
                return;
            case 527:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                writeRepeatedSFixed32Impl(i, (int) d);
                return;
            case 528:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                writeRepeatedSFixed64Impl(i, (long) d);
                return;
            case 529:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                writeRepeatedSInt32Impl(i, (int) d);
                return;
            case 530:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                writeRepeatedSInt64Impl(i, (long) d);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, double) with " + getFieldIdString(j));
        }
    }

    public void write(long j, float f) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 257:
                writeDoubleImpl(i, f);
                return;
            case 258:
                writeFloatImpl(i, f);
                return;
            case 259:
                writeInt64Impl(i, (long) f);
                return;
            case 260:
                writeUInt64Impl(i, (long) f);
                return;
            case 261:
                writeInt32Impl(i, (int) f);
                return;
            case 262:
                writeFixed64Impl(i, (long) f);
                return;
            case 263:
                writeFixed32Impl(i, (int) f);
                return;
            case 264:
                writeBoolImpl(i, f != 0.0f);
                return;
            case 269:
                writeUInt32Impl(i, (int) f);
                return;
            case 270:
                writeEnumImpl(i, (int) f);
                return;
            case 271:
                writeSFixed32Impl(i, (int) f);
                return;
            case 272:
                writeSFixed64Impl(i, (long) f);
                return;
            case 273:
                writeSInt32Impl(i, (int) f);
                return;
            case 274:
                writeSInt64Impl(i, (long) f);
                return;
            case 513:
            case 1281:
                writeRepeatedDoubleImpl(i, f);
                return;
            case 514:
            case 1282:
                writeRepeatedFloatImpl(i, f);
                return;
            case 515:
            case 1283:
                writeRepeatedInt64Impl(i, (long) f);
                return;
            case 516:
            case 1284:
                writeRepeatedUInt64Impl(i, (long) f);
                return;
            case 517:
            case 1285:
                writeRepeatedInt32Impl(i, (int) f);
                return;
            case 518:
            case 1286:
                writeRepeatedFixed64Impl(i, (long) f);
                return;
            case 519:
            case 1287:
                writeRepeatedFixed32Impl(i, (int) f);
                return;
            case 520:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                writeRepeatedBoolImpl(i, f != 0.0f);
                return;
            case 525:
            case 1293:
                writeRepeatedUInt32Impl(i, (int) f);
                return;
            case 526:
            case 1294:
                writeRepeatedEnumImpl(i, (int) f);
                return;
            case 527:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                writeRepeatedSFixed32Impl(i, (int) f);
                return;
            case 528:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                writeRepeatedSFixed64Impl(i, (long) f);
                return;
            case 529:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                writeRepeatedSInt32Impl(i, (int) f);
                return;
            case 530:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                writeRepeatedSInt64Impl(i, (long) f);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, float) with " + getFieldIdString(j));
        }
    }

    public void write(long j, int i) {
        assertNotCompacted();
        int i2 = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 257:
                writeDoubleImpl(i2, i);
                return;
            case 258:
                writeFloatImpl(i2, i);
                return;
            case 259:
                writeInt64Impl(i2, i);
                return;
            case 260:
                writeUInt64Impl(i2, i);
                return;
            case 261:
                writeInt32Impl(i2, i);
                return;
            case 262:
                writeFixed64Impl(i2, i);
                return;
            case 263:
                writeFixed32Impl(i2, i);
                return;
            case 264:
                writeBoolImpl(i2, i != 0);
                return;
            case 269:
                writeUInt32Impl(i2, i);
                return;
            case 270:
                writeEnumImpl(i2, i);
                return;
            case 271:
                writeSFixed32Impl(i2, i);
                return;
            case 272:
                writeSFixed64Impl(i2, i);
                return;
            case 273:
                writeSInt32Impl(i2, i);
                return;
            case 274:
                writeSInt64Impl(i2, i);
                return;
            case 513:
            case 1281:
                writeRepeatedDoubleImpl(i2, i);
                return;
            case 514:
            case 1282:
                writeRepeatedFloatImpl(i2, i);
                return;
            case 515:
            case 1283:
                writeRepeatedInt64Impl(i2, i);
                return;
            case 516:
            case 1284:
                writeRepeatedUInt64Impl(i2, i);
                return;
            case 517:
            case 1285:
                writeRepeatedInt32Impl(i2, i);
                return;
            case 518:
            case 1286:
                writeRepeatedFixed64Impl(i2, i);
                return;
            case 519:
            case 1287:
                writeRepeatedFixed32Impl(i2, i);
                return;
            case 520:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                writeRepeatedBoolImpl(i2, i != 0);
                return;
            case 525:
            case 1293:
                writeRepeatedUInt32Impl(i2, i);
                return;
            case 526:
            case 1294:
                writeRepeatedEnumImpl(i2, i);
                return;
            case 527:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                writeRepeatedSFixed32Impl(i2, i);
                return;
            case 528:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                writeRepeatedSFixed64Impl(i2, i);
                return;
            case 529:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                writeRepeatedSInt32Impl(i2, i);
                return;
            case 530:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                writeRepeatedSInt64Impl(i2, i);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, int) with " + getFieldIdString(j));
        }
    }

    public void write(long j, long j2) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 257:
                writeDoubleImpl(i, j2);
                return;
            case 258:
                writeFloatImpl(i, j2);
                return;
            case 259:
                writeInt64Impl(i, j2);
                return;
            case 260:
                writeUInt64Impl(i, j2);
                return;
            case 261:
                writeInt32Impl(i, (int) j2);
                return;
            case 262:
                writeFixed64Impl(i, j2);
                return;
            case 263:
                writeFixed32Impl(i, (int) j2);
                return;
            case 264:
                writeBoolImpl(i, j2 != 0);
                return;
            case 269:
                writeUInt32Impl(i, (int) j2);
                return;
            case 270:
                writeEnumImpl(i, (int) j2);
                return;
            case 271:
                writeSFixed32Impl(i, (int) j2);
                return;
            case 272:
                writeSFixed64Impl(i, j2);
                return;
            case 273:
                writeSInt32Impl(i, (int) j2);
                return;
            case 274:
                writeSInt64Impl(i, j2);
                return;
            case 513:
            case 1281:
                writeRepeatedDoubleImpl(i, j2);
                return;
            case 514:
            case 1282:
                writeRepeatedFloatImpl(i, j2);
                return;
            case 515:
            case 1283:
                writeRepeatedInt64Impl(i, j2);
                return;
            case 516:
            case 1284:
                writeRepeatedUInt64Impl(i, j2);
                return;
            case 517:
            case 1285:
                writeRepeatedInt32Impl(i, (int) j2);
                return;
            case 518:
            case 1286:
                writeRepeatedFixed64Impl(i, j2);
                return;
            case 519:
            case 1287:
                writeRepeatedFixed32Impl(i, (int) j2);
                return;
            case 520:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                writeRepeatedBoolImpl(i, j2 != 0);
                return;
            case 525:
            case 1293:
                writeRepeatedUInt32Impl(i, (int) j2);
                return;
            case 526:
            case 1294:
                writeRepeatedEnumImpl(i, (int) j2);
                return;
            case 527:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                writeRepeatedSFixed32Impl(i, (int) j2);
                return;
            case 528:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                writeRepeatedSFixed64Impl(i, j2);
                return;
            case 529:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                writeRepeatedSInt32Impl(i, (int) j2);
                return;
            case 530:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                writeRepeatedSInt64Impl(i, j2);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, long) with " + getFieldIdString(j));
        }
    }

    public void write(long j, boolean z) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 264:
                writeBoolImpl(i, z);
                return;
            case 520:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                writeRepeatedBoolImpl(i, z);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, boolean) with " + getFieldIdString(j));
        }
    }

    public void write(long j, java.lang.String str) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 265:
                writeStringImpl(i, str);
                return;
            case 521:
            case com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_INVALID_PERMISSION /* 1289 */:
                writeRepeatedStringImpl(i, str);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, String) with " + getFieldIdString(j));
        }
    }

    public void write(long j, byte[] bArr) {
        assertNotCompacted();
        int i = (int) j;
        switch ((int) ((17587891077120L & j) >> 32)) {
            case 267:
                writeObjectImpl(i, bArr);
                return;
            case 268:
                writeBytesImpl(i, bArr);
                return;
            case 523:
            case 1291:
                writeRepeatedObjectImpl(i, bArr);
                return;
            case 524:
            case 1292:
                writeRepeatedBytesImpl(i, bArr);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Attempt to call write(long, byte[]) with " + getFieldIdString(j));
        }
    }

    public long start(long j) {
        assertNotCompacted();
        int i = (int) j;
        if ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) == android.util.proto.ProtoStream.FIELD_TYPE_MESSAGE) {
            long j2 = android.util.proto.ProtoStream.FIELD_COUNT_MASK & j;
            if (j2 == 1099511627776L) {
                return startObjectImpl(i, false);
            }
            if (j2 == 2199023255552L || j2 == android.util.proto.ProtoStream.FIELD_COUNT_PACKED) {
                return startObjectImpl(i, true);
            }
        }
        throw new java.lang.IllegalArgumentException("Attempt to call start(long) with " + getFieldIdString(j));
    }

    public void end(long j) {
        endObjectImpl(j, getRepeatedFromToken(j));
    }

    @java.lang.Deprecated
    public void writeDouble(long j, double d) {
        assertNotCompacted();
        writeDoubleImpl(checkFieldId(j, 1103806595072L), d);
    }

    private void writeDoubleImpl(int i, double d) {
        if (d != 0.0d) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(java.lang.Double.doubleToLongBits(d));
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedDouble(long j, double d) {
        assertNotCompacted();
        writeRepeatedDoubleImpl(checkFieldId(j, 2203318222848L), d);
    }

    private void writeRepeatedDoubleImpl(int i, double d) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(java.lang.Double.doubleToLongBits(d));
    }

    @java.lang.Deprecated
    public void writePackedDouble(long j, double[] dArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5501853106176L);
        int length = dArr != null ? dArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(java.lang.Double.doubleToLongBits(dArr[i]));
            }
        }
    }

    @java.lang.Deprecated
    public void writeFloat(long j, float f) {
        assertNotCompacted();
        writeFloatImpl(checkFieldId(j, 1108101562368L), f);
    }

    private void writeFloatImpl(int i, float f) {
        if (f != 0.0f) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(java.lang.Float.floatToIntBits(f));
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedFloat(long j, float f) {
        assertNotCompacted();
        writeRepeatedFloatImpl(checkFieldId(j, 2207613190144L), f);
    }

    private void writeRepeatedFloatImpl(int i, float f) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(java.lang.Float.floatToIntBits(f));
    }

    @java.lang.Deprecated
    public void writePackedFloat(long j, float[] fArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5506148073472L);
        int length = fArr != null ? fArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(java.lang.Float.floatToIntBits(fArr[i]));
            }
        }
    }

    private void writeUnsignedVarintFromSignedInt(int i) {
        if (i >= 0) {
            this.mBuffer.writeRawVarint32(i);
        } else {
            this.mBuffer.writeRawVarint64(i);
        }
    }

    @java.lang.Deprecated
    public void writeInt32(long j, int i) {
        assertNotCompacted();
        writeInt32Impl(checkFieldId(j, 1120986464256L), i);
    }

    private void writeInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            writeUnsignedVarintFromSignedInt(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedInt32Impl(checkFieldId(j, 2220498092032L), i);
    }

    private void writeRepeatedInt32Impl(int i, int i2) {
        writeTag(i, 0);
        writeUnsignedVarintFromSignedInt(i2);
    }

    @java.lang.Deprecated
    public void writePackedInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5519032975360L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArr[i2];
                i += i3 >= 0 ? android.util.proto.EncodedBuffer.getRawVarint32Size(i3) : 10;
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i4 = 0; i4 < length; i4++) {
                writeUnsignedVarintFromSignedInt(iArr[i4]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeInt64(long j, long j2) {
        assertNotCompacted();
        writeInt64Impl(checkFieldId(j, 1112396529664L), j2);
    }

    private void writeInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint64(j);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedInt64Impl(checkFieldId(j, 2211908157440L), j2);
    }

    private void writeRepeatedInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint64(j);
    }

    @java.lang.Deprecated
    public void writePackedInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5510443040768L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += android.util.proto.EncodedBuffer.getRawVarint64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint64(jArr[i3]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeUInt32(long j, int i) {
        assertNotCompacted();
        writeUInt32Impl(checkFieldId(j, 1155346202624L), i);
    }

    private void writeUInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint32(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedUInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedUInt32Impl(checkFieldId(j, 2254857830400L), i);
    }

    private void writeRepeatedUInt32Impl(int i, int i2) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint32(i2);
    }

    @java.lang.Deprecated
    public void writePackedUInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5553392713728L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += android.util.proto.EncodedBuffer.getRawVarint32Size(iArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint32(iArr[i3]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeUInt64(long j, long j2) {
        assertNotCompacted();
        writeUInt64Impl(checkFieldId(j, 1116691496960L), j2);
    }

    private void writeUInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint64(j);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedUInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedUInt64Impl(checkFieldId(j, 2216203124736L), j2);
    }

    private void writeRepeatedUInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint64(j);
    }

    @java.lang.Deprecated
    public void writePackedUInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5514738008064L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += android.util.proto.EncodedBuffer.getRawVarint64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint64(jArr[i3]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeSInt32(long j, int i) {
        assertNotCompacted();
        writeSInt32Impl(checkFieldId(j, 1172526071808L), i);
    }

    private void writeSInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawZigZag32(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedSInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedSInt32Impl(checkFieldId(j, 2272037699584L), i);
    }

    private void writeRepeatedSInt32Impl(int i, int i2) {
        writeTag(i, 0);
        this.mBuffer.writeRawZigZag32(i2);
    }

    @java.lang.Deprecated
    public void writePackedSInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5570572582912L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += android.util.proto.EncodedBuffer.getRawZigZag32Size(iArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawZigZag32(iArr[i3]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeSInt64(long j, long j2) {
        assertNotCompacted();
        writeSInt64Impl(checkFieldId(j, 1176821039104L), j2);
    }

    private void writeSInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawZigZag64(j);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedSInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedSInt64Impl(checkFieldId(j, 2276332666880L), j2);
    }

    private void writeRepeatedSInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawZigZag64(j);
    }

    @java.lang.Deprecated
    public void writePackedSInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5574867550208L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += android.util.proto.EncodedBuffer.getRawZigZag64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawZigZag64(jArr[i3]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeFixed32(long j, int i) {
        assertNotCompacted();
        writeFixed32Impl(checkFieldId(j, 1129576398848L), i);
    }

    private void writeFixed32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedFixed32(long j, int i) {
        assertNotCompacted();
        writeRepeatedFixed32Impl(checkFieldId(j, 2229088026624L), i);
    }

    private void writeRepeatedFixed32Impl(int i, int i2) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(i2);
    }

    @java.lang.Deprecated
    public void writePackedFixed32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5527622909952L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(iArr[i]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeFixed64(long j, long j2) {
        assertNotCompacted();
        writeFixed64Impl(checkFieldId(j, 1125281431552L), j2);
    }

    private void writeFixed64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(j);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedFixed64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedFixed64Impl(checkFieldId(j, 2224793059328L), j2);
    }

    private void writeRepeatedFixed64Impl(int i, long j) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(j);
    }

    @java.lang.Deprecated
    public void writePackedFixed64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5523327942656L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(jArr[i]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeSFixed32(long j, int i) {
        assertNotCompacted();
        writeSFixed32Impl(checkFieldId(j, 1163936137216L), i);
    }

    private void writeSFixed32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedSFixed32(long j, int i) {
        assertNotCompacted();
        writeRepeatedSFixed32Impl(checkFieldId(j, 2263447764992L), i);
    }

    private void writeRepeatedSFixed32Impl(int i, int i2) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(i2);
    }

    @java.lang.Deprecated
    public void writePackedSFixed32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5561982648320L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(iArr[i]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeSFixed64(long j, long j2) {
        assertNotCompacted();
        writeSFixed64Impl(checkFieldId(j, 1168231104512L), j2);
    }

    private void writeSFixed64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(j);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedSFixed64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedSFixed64Impl(checkFieldId(j, 2267742732288L), j2);
    }

    private void writeRepeatedSFixed64Impl(int i, long j) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(j);
    }

    @java.lang.Deprecated
    public void writePackedSFixed64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5566277615616L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(jArr[i]);
            }
        }
    }

    @java.lang.Deprecated
    public void writeBool(long j, boolean z) {
        assertNotCompacted();
        writeBoolImpl(checkFieldId(j, 1133871366144L), z);
    }

    private void writeBoolImpl(int i, boolean z) {
        if (z) {
            writeTag(i, 0);
            this.mBuffer.writeRawByte((byte) 1);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedBool(long j, boolean z) {
        assertNotCompacted();
        writeRepeatedBoolImpl(checkFieldId(j, 2233382993920L), z);
    }

    private void writeRepeatedBoolImpl(int i, boolean z) {
        writeTag(i, 0);
        this.mBuffer.writeRawByte(z ? (byte) 1 : (byte) 0);
    }

    @java.lang.Deprecated
    public void writePackedBool(long j, boolean[] zArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5531917877248L);
        int length = zArr != null ? zArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawByte(zArr[i] ? (byte) 1 : (byte) 0);
            }
        }
    }

    @java.lang.Deprecated
    public void writeString(long j, java.lang.String str) {
        assertNotCompacted();
        writeStringImpl(checkFieldId(j, 1138166333440L), str);
    }

    private void writeStringImpl(int i, java.lang.String str) {
        if (str != null && str.length() > 0) {
            writeUtf8String(i, str);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedString(long j, java.lang.String str) {
        assertNotCompacted();
        writeRepeatedStringImpl(checkFieldId(j, 2237677961216L), str);
    }

    private void writeRepeatedStringImpl(int i, java.lang.String str) {
        if (str == null || str.length() == 0) {
            writeKnownLengthHeader(i, 0);
        } else {
            writeUtf8String(i, str);
        }
    }

    private void writeUtf8String(int i, java.lang.String str) {
        try {
            byte[] bytes = str.getBytes(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
            writeKnownLengthHeader(i, bytes.length);
            this.mBuffer.writeRawBuffer(bytes);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.RuntimeException("not possible");
        }
    }

    @java.lang.Deprecated
    public void writeBytes(long j, byte[] bArr) {
        assertNotCompacted();
        writeBytesImpl(checkFieldId(j, 1151051235328L), bArr);
    }

    private void writeBytesImpl(int i, byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            writeKnownLengthHeader(i, bArr.length);
            this.mBuffer.writeRawBuffer(bArr);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedBytes(long j, byte[] bArr) {
        assertNotCompacted();
        writeRepeatedBytesImpl(checkFieldId(j, 2250562863104L), bArr);
    }

    private void writeRepeatedBytesImpl(int i, byte[] bArr) {
        writeKnownLengthHeader(i, bArr == null ? 0 : bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    @java.lang.Deprecated
    public void writeEnum(long j, int i) {
        assertNotCompacted();
        writeEnumImpl(checkFieldId(j, 1159641169920L), i);
    }

    private void writeEnumImpl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            writeUnsignedVarintFromSignedInt(i2);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedEnum(long j, int i) {
        assertNotCompacted();
        writeRepeatedEnumImpl(checkFieldId(j, 2259152797696L), i);
    }

    private void writeRepeatedEnumImpl(int i, int i2) {
        writeTag(i, 0);
        writeUnsignedVarintFromSignedInt(i2);
    }

    @java.lang.Deprecated
    public void writePackedEnum(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5557687681024L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArr[i2];
                i += i3 >= 0 ? android.util.proto.EncodedBuffer.getRawVarint32Size(i3) : 10;
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i4 = 0; i4 < length; i4++) {
                writeUnsignedVarintFromSignedInt(iArr[i4]);
            }
        }
    }

    @java.lang.Deprecated
    public long startObject(long j) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(j, 1146756268032L), false);
    }

    @java.lang.Deprecated
    public void endObject(long j) {
        assertNotCompacted();
        endObjectImpl(j, false);
    }

    @java.lang.Deprecated
    public long startRepeatedObject(long j) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(j, 2246267895808L), true);
    }

    @java.lang.Deprecated
    public void endRepeatedObject(long j) {
        assertNotCompacted();
        endObjectImpl(j, true);
    }

    private long startObjectImpl(int i, boolean z) {
        writeTag(i, 2);
        int writePos = this.mBuffer.getWritePos();
        this.mDepth++;
        this.mNextObjectId--;
        this.mBuffer.writeRawFixed32((int) (this.mExpectedObjectToken >> 32));
        this.mBuffer.writeRawFixed32((int) this.mExpectedObjectToken);
        this.mExpectedObjectToken = makeToken(getTagSize(i), z, this.mDepth, this.mNextObjectId, writePos);
        return this.mExpectedObjectToken;
    }

    private void endObjectImpl(long j, boolean z) {
        int depthFromToken = getDepthFromToken(j);
        boolean repeatedFromToken = getRepeatedFromToken(j);
        int offsetFromToken = getOffsetFromToken(j);
        int writePos = (this.mBuffer.getWritePos() - offsetFromToken) - 8;
        if (z != repeatedFromToken) {
            if (z) {
                throw new java.lang.IllegalArgumentException("endRepeatedObject called where endObject should have been");
            }
            throw new java.lang.IllegalArgumentException("endObject called where endRepeatedObject should have been");
        }
        if ((this.mDepth & 511) != depthFromToken || this.mExpectedObjectToken != j) {
            throw new java.lang.IllegalArgumentException("Mismatched startObject/endObject calls. Current depth " + this.mDepth + " token=" + token2String(j) + " expectedToken=" + token2String(this.mExpectedObjectToken));
        }
        int i = offsetFromToken + 4;
        this.mExpectedObjectToken = (this.mBuffer.getRawFixed32At(offsetFromToken) << 32) | (this.mBuffer.getRawFixed32At(i) & 4294967295L);
        this.mDepth--;
        if (writePos > 0) {
            this.mBuffer.editRawFixed32(offsetFromToken, -writePos);
            this.mBuffer.editRawFixed32(i, -1);
        } else if (z) {
            this.mBuffer.editRawFixed32(offsetFromToken, 0);
            this.mBuffer.editRawFixed32(i, 0);
        } else {
            this.mBuffer.rewindWriteTo(offsetFromToken - getTagSizeFromToken(j));
        }
    }

    @java.lang.Deprecated
    public void writeObject(long j, byte[] bArr) {
        assertNotCompacted();
        writeObjectImpl(checkFieldId(j, 1146756268032L), bArr);
    }

    void writeObjectImpl(int i, byte[] bArr) {
        if (bArr != null && bArr.length != 0) {
            writeKnownLengthHeader(i, bArr.length);
            this.mBuffer.writeRawBuffer(bArr);
        }
    }

    @java.lang.Deprecated
    public void writeRepeatedObject(long j, byte[] bArr) {
        assertNotCompacted();
        writeRepeatedObjectImpl(checkFieldId(j, 2246267895808L), bArr);
    }

    void writeRepeatedObjectImpl(int i, byte[] bArr) {
        writeKnownLengthHeader(i, bArr == null ? 0 : bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    public static long makeFieldId(int i, long j) {
        return j | (i & 4294967295L);
    }

    public static int checkFieldId(long j, long j2) {
        long j3 = j & android.util.proto.ProtoStream.FIELD_COUNT_MASK;
        long j4 = j & android.util.proto.ProtoStream.FIELD_TYPE_MASK;
        long j5 = j2 & android.util.proto.ProtoStream.FIELD_COUNT_MASK;
        long j6 = j2 & android.util.proto.ProtoStream.FIELD_TYPE_MASK;
        int i = (int) j;
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Invalid proto field " + i + " fieldId=" + java.lang.Long.toHexString(j));
        }
        if (j4 != j6 || (j3 != j5 && (j3 != android.util.proto.ProtoStream.FIELD_COUNT_PACKED || j5 != 2199023255552L))) {
            java.lang.String fieldCountString = getFieldCountString(j3);
            java.lang.String fieldTypeString = getFieldTypeString(j4);
            if (fieldTypeString != null && fieldCountString != null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                if (j6 == android.util.proto.ProtoStream.FIELD_TYPE_MESSAGE) {
                    sb.append("start");
                } else {
                    sb.append("write");
                }
                sb.append(getFieldCountString(j5));
                sb.append(getFieldTypeString(j6));
                sb.append(" called for field ");
                sb.append(i);
                sb.append(" which should be used with ");
                if (j4 == android.util.proto.ProtoStream.FIELD_TYPE_MESSAGE) {
                    sb.append("start");
                } else {
                    sb.append("write");
                }
                sb.append(fieldCountString);
                sb.append(fieldTypeString);
                if (j3 == android.util.proto.ProtoStream.FIELD_COUNT_PACKED) {
                    sb.append(" or writeRepeated");
                    sb.append(fieldTypeString);
                }
                sb.append('.');
                throw new java.lang.IllegalArgumentException(sb.toString());
            }
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            if (j6 == android.util.proto.ProtoStream.FIELD_TYPE_MESSAGE) {
                sb2.append("start");
            } else {
                sb2.append("write");
            }
            sb2.append(getFieldCountString(j5));
            sb2.append(getFieldTypeString(j6));
            sb2.append(" called with an invalid fieldId: 0x");
            sb2.append(java.lang.Long.toHexString(j));
            sb2.append(". The proto field ID might be ");
            sb2.append(i);
            sb2.append('.');
            throw new java.lang.IllegalArgumentException(sb2.toString());
        }
        return i;
    }

    private static int getTagSize(int i) {
        return android.util.proto.EncodedBuffer.getRawVarint32Size(i << 3);
    }

    public void writeTag(int i, int i2) {
        this.mBuffer.writeRawVarint32((i << 3) | i2);
    }

    private void writeKnownLengthHeader(int i, int i2) {
        writeTag(i, 2);
        this.mBuffer.writeRawFixed32(i2);
        this.mBuffer.writeRawFixed32(i2);
    }

    private void assertNotCompacted() {
        if (this.mCompacted) {
            throw new java.lang.IllegalArgumentException("write called after compact");
        }
    }

    public byte[] getBytes() {
        compactIfNecessary();
        return this.mBuffer.getBytes(this.mBuffer.getReadableSize());
    }

    private void compactIfNecessary() {
        if (!this.mCompacted) {
            if (this.mDepth != 0) {
                throw new java.lang.IllegalArgumentException("Trying to compact with " + this.mDepth + " missing calls to endObject");
            }
            this.mBuffer.startEditing();
            int readableSize = this.mBuffer.getReadableSize();
            editEncodedSize(readableSize);
            this.mBuffer.rewindRead();
            compactSizes(readableSize);
            if (this.mCopyBegin < readableSize) {
                this.mBuffer.writeFromThisBuffer(this.mCopyBegin, readableSize - this.mCopyBegin);
            }
            this.mBuffer.startEditing();
            this.mCompacted = true;
        }
    }

    private int editEncodedSize(int i) {
        int readPos = this.mBuffer.getReadPos() + i;
        int i2 = 0;
        while (true) {
            int readPos2 = this.mBuffer.getReadPos();
            if (readPos2 < readPos) {
                int readRawTag = readRawTag();
                i2 += android.util.proto.EncodedBuffer.getRawVarint32Size(readRawTag);
                int i3 = readRawTag & 7;
                switch (i3) {
                    case 0:
                        do {
                            i2++;
                        } while ((this.mBuffer.readRawByte() & 128) != 0);
                    case 1:
                        i2 += 8;
                        this.mBuffer.skipRead(8);
                        break;
                    case 2:
                        int readRawFixed32 = this.mBuffer.readRawFixed32();
                        int readPos3 = this.mBuffer.getReadPos();
                        int readRawFixed322 = this.mBuffer.readRawFixed32();
                        if (readRawFixed32 >= 0) {
                            if (readRawFixed322 != readRawFixed32) {
                                throw new java.lang.RuntimeException("Pre-computed size where the precomputed size and the raw size in the buffer don't match! childRawSize=" + readRawFixed32 + " childEncodedSize=" + readRawFixed322 + " childEncodedSizePos=" + readPos3);
                            }
                            this.mBuffer.skipRead(readRawFixed32);
                        } else {
                            readRawFixed322 = editEncodedSize(-readRawFixed32);
                            this.mBuffer.editRawFixed32(readPos3, readRawFixed322);
                        }
                        i2 += android.util.proto.EncodedBuffer.getRawVarint32Size(readRawFixed322) + readRawFixed322;
                        break;
                    case 3:
                    case 4:
                        throw new java.lang.RuntimeException("groups not supported at index " + readPos2);
                    case 5:
                        i2 += 4;
                        this.mBuffer.skipRead(4);
                        break;
                    default:
                        throw new android.util.proto.ProtoParseException("editEncodedSize Bad tag tag=0x" + java.lang.Integer.toHexString(readRawTag) + " wireType=" + i3 + " -- " + this.mBuffer.getDebugString());
                }
            } else {
                return i2;
            }
        }
    }

    private void compactSizes(int i) {
        int readPos = this.mBuffer.getReadPos() + i;
        while (true) {
            int readPos2 = this.mBuffer.getReadPos();
            if (readPos2 < readPos) {
                int readRawTag = readRawTag();
                int i2 = readRawTag & 7;
                switch (i2) {
                    case 0:
                        while ((this.mBuffer.readRawByte() & 128) != 0) {
                        }
                        break;
                    case 1:
                        this.mBuffer.skipRead(8);
                        break;
                    case 2:
                        this.mBuffer.writeFromThisBuffer(this.mCopyBegin, this.mBuffer.getReadPos() - this.mCopyBegin);
                        int readRawFixed32 = this.mBuffer.readRawFixed32();
                        int readRawFixed322 = this.mBuffer.readRawFixed32();
                        this.mBuffer.writeRawVarint32(readRawFixed322);
                        this.mCopyBegin = this.mBuffer.getReadPos();
                        if (readRawFixed32 >= 0) {
                            this.mBuffer.skipRead(readRawFixed322);
                            break;
                        } else {
                            compactSizes(-readRawFixed32);
                            break;
                        }
                    case 3:
                    case 4:
                        throw new java.lang.RuntimeException("groups not supported at index " + readPos2);
                    case 5:
                        this.mBuffer.skipRead(4);
                        break;
                    default:
                        throw new android.util.proto.ProtoParseException("compactSizes Bad tag tag=0x" + java.lang.Integer.toHexString(readRawTag) + " wireType=" + i2 + " -- " + this.mBuffer.getDebugString());
                }
            } else {
                return;
            }
        }
    }

    public void flush() {
        if (this.mStream == null || this.mDepth != 0 || this.mCompacted) {
            return;
        }
        compactIfNecessary();
        try {
            this.mStream.write(this.mBuffer.getBytes(this.mBuffer.getReadableSize()));
            this.mStream.flush();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Error flushing proto to stream", e);
        }
    }

    private int readRawTag() {
        if (this.mBuffer.getReadPos() == this.mBuffer.getReadableSize()) {
            return 0;
        }
        return (int) this.mBuffer.readRawUnsigned();
    }

    public void dump(java.lang.String str) {
        android.util.Log.d(str, this.mBuffer.getDebugString());
        this.mBuffer.dumpBuffers(str);
    }
}
