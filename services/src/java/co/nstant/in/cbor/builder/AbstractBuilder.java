package co.nstant.in.cbor.builder;

/* loaded from: classes.dex */
public abstract class AbstractBuilder<T> {
    private final T parent;

    public AbstractBuilder(T t) {
        this.parent = t;
    }

    protected T getParent() {
        return this.parent;
    }

    protected void addChunk(co.nstant.in.cbor.model.DataItem dataItem) {
        throw new java.lang.IllegalStateException();
    }

    protected co.nstant.in.cbor.model.DataItem convert(long j) {
        if (j >= 0) {
            return new co.nstant.in.cbor.model.UnsignedInteger(j);
        }
        return new co.nstant.in.cbor.model.NegativeInteger(j);
    }

    protected co.nstant.in.cbor.model.DataItem convert(java.math.BigInteger bigInteger) {
        if (bigInteger.signum() == -1) {
            return new co.nstant.in.cbor.model.NegativeInteger(bigInteger);
        }
        return new co.nstant.in.cbor.model.UnsignedInteger(bigInteger);
    }

    protected co.nstant.in.cbor.model.DataItem convert(boolean z) {
        if (z) {
            return co.nstant.in.cbor.model.SimpleValue.TRUE;
        }
        return co.nstant.in.cbor.model.SimpleValue.FALSE;
    }

    protected co.nstant.in.cbor.model.DataItem convert(byte[] bArr) {
        return new co.nstant.in.cbor.model.ByteString(bArr);
    }

    protected co.nstant.in.cbor.model.DataItem convert(java.lang.String str) {
        return new co.nstant.in.cbor.model.UnicodeString(str);
    }

    protected co.nstant.in.cbor.model.DataItem convert(float f) {
        if (isHalfPrecisionEnough(f)) {
            return new co.nstant.in.cbor.model.HalfPrecisionFloat(f);
        }
        return new co.nstant.in.cbor.model.SinglePrecisionFloat(f);
    }

    protected co.nstant.in.cbor.model.DataItem convert(double d) {
        return new co.nstant.in.cbor.model.DoublePrecisionFloat(d);
    }

    protected co.nstant.in.cbor.model.Tag tag(long j) {
        return new co.nstant.in.cbor.model.Tag(j);
    }

    private boolean isHalfPrecisionEnough(float f) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            getHalfPrecisionFloatEncoder(byteArrayOutputStream).encode(new co.nstant.in.cbor.model.HalfPrecisionFloat(f));
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            co.nstant.in.cbor.decoder.HalfPrecisionFloatDecoder halfPrecisionFloatDecoder = getHalfPrecisionFloatDecoder(byteArrayInputStream);
            if (byteArrayInputStream.read() != -1) {
                return f == halfPrecisionFloatDecoder.decode(0).getValue();
            }
            throw new co.nstant.in.cbor.CborException("unexpected end of stream");
        } catch (co.nstant.in.cbor.CborException e) {
            return false;
        }
    }

    protected co.nstant.in.cbor.encoder.HalfPrecisionFloatEncoder getHalfPrecisionFloatEncoder(java.io.OutputStream outputStream) {
        return new co.nstant.in.cbor.encoder.HalfPrecisionFloatEncoder(null, outputStream);
    }

    protected co.nstant.in.cbor.decoder.HalfPrecisionFloatDecoder getHalfPrecisionFloatDecoder(java.io.InputStream inputStream) {
        return new co.nstant.in.cbor.decoder.HalfPrecisionFloatDecoder(null, inputStream);
    }
}
