package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public abstract class AbstractEncoder<T> {
    protected final co.nstant.in.cbor.CborEncoder encoder;
    private final java.io.OutputStream outputStream;

    public abstract void encode(T t) throws co.nstant.in.cbor.CborException;

    public AbstractEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        this.encoder = cborEncoder;
        this.outputStream = outputStream;
    }

    protected void encodeTypeChunked(co.nstant.in.cbor.model.MajorType majorType) throws co.nstant.in.cbor.CborException {
        try {
            this.outputStream.write((majorType.getValue() << 5) | co.nstant.in.cbor.model.AdditionalInformation.INDEFINITE.getValue());
        } catch (java.io.IOException e) {
            throw new co.nstant.in.cbor.CborException(e);
        }
    }

    protected void encodeTypeAndLength(co.nstant.in.cbor.model.MajorType majorType, long j) throws co.nstant.in.cbor.CborException {
        int value = majorType.getValue() << 5;
        if (j <= 23) {
            write((int) (value | j));
            return;
        }
        if (j <= 255) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.ONE_BYTE.getValue());
            write((int) j);
            return;
        }
        if (j <= 65535) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.TWO_BYTES.getValue());
            write((int) (j >> 8));
            write((int) (j & 255));
            return;
        }
        if (j <= 4294967295L) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.FOUR_BYTES.getValue());
            write((int) ((j >> 24) & 255));
            write((int) ((j >> 16) & 255));
            write((int) ((j >> 8) & 255));
            write((int) (j & 255));
            return;
        }
        write(value | co.nstant.in.cbor.model.AdditionalInformation.EIGHT_BYTES.getValue());
        write((int) ((j >> 56) & 255));
        write((int) ((j >> 48) & 255));
        write((int) ((j >> 40) & 255));
        write((int) ((j >> 32) & 255));
        write((int) ((j >> 24) & 255));
        write((int) ((j >> 16) & 255));
        write((int) ((j >> 8) & 255));
        write((int) (j & 255));
    }

    protected void encodeTypeAndLength(co.nstant.in.cbor.model.MajorType majorType, java.math.BigInteger bigInteger) throws co.nstant.in.cbor.CborException {
        boolean z = majorType == co.nstant.in.cbor.model.MajorType.NEGATIVE_INTEGER;
        int value = majorType.getValue() << 5;
        if (bigInteger.compareTo(java.math.BigInteger.valueOf(24L)) == -1) {
            write(value | bigInteger.intValue());
            return;
        }
        if (bigInteger.compareTo(java.math.BigInteger.valueOf(256L)) == -1) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.ONE_BYTE.getValue());
            write(bigInteger.intValue());
            return;
        }
        if (bigInteger.compareTo(java.math.BigInteger.valueOf(65536L)) == -1) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.TWO_BYTES.getValue());
            long longValue = bigInteger.longValue();
            write((int) (longValue >> 8));
            write((int) (longValue & 255));
            return;
        }
        if (bigInteger.compareTo(java.math.BigInteger.valueOf(4294967296L)) == -1) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.FOUR_BYTES.getValue());
            long longValue2 = bigInteger.longValue();
            write((int) ((longValue2 >> 24) & 255));
            write((int) ((longValue2 >> 16) & 255));
            write((int) ((longValue2 >> 8) & 255));
            write((int) (longValue2 & 255));
            return;
        }
        if (bigInteger.compareTo(new java.math.BigInteger("18446744073709551616")) == -1) {
            write(value | co.nstant.in.cbor.model.AdditionalInformation.EIGHT_BYTES.getValue());
            java.math.BigInteger valueOf = java.math.BigInteger.valueOf(255L);
            write(bigInteger.shiftRight(56).and(valueOf).intValue());
            write(bigInteger.shiftRight(48).and(valueOf).intValue());
            write(bigInteger.shiftRight(40).and(valueOf).intValue());
            write(bigInteger.shiftRight(32).and(valueOf).intValue());
            write(bigInteger.shiftRight(24).and(valueOf).intValue());
            write(bigInteger.shiftRight(16).and(valueOf).intValue());
            write(bigInteger.shiftRight(8).and(valueOf).intValue());
            write(bigInteger.and(valueOf).intValue());
            return;
        }
        if (z) {
            this.encoder.encode(new co.nstant.in.cbor.model.Tag(3L));
        } else {
            this.encoder.encode(new co.nstant.in.cbor.model.Tag(2L));
        }
        this.encoder.encode(new co.nstant.in.cbor.model.ByteString(bigInteger.toByteArray()));
    }

    protected void write(int i) throws co.nstant.in.cbor.CborException {
        try {
            this.outputStream.write(i);
        } catch (java.io.IOException e) {
            throw new co.nstant.in.cbor.CborException(e);
        }
    }

    protected void write(byte[] bArr) throws co.nstant.in.cbor.CborException {
        try {
            this.outputStream.write(bArr);
        } catch (java.io.IOException e) {
            throw new co.nstant.in.cbor.CborException(e);
        }
    }
}
