package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public abstract class AbstractDecoder<T> {
    protected static final int INFINITY = -1;
    protected final co.nstant.in.cbor.CborDecoder decoder;
    protected final java.io.InputStream inputStream;

    public abstract T decode(int i) throws co.nstant.in.cbor.CborException;

    public AbstractDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        this.decoder = cborDecoder;
        this.inputStream = inputStream;
    }

    protected int nextSymbol() throws co.nstant.in.cbor.CborException {
        try {
            int read = this.inputStream.read();
            if (read == -1) {
                throw new java.io.IOException("Unexpected end of stream");
            }
            return read;
        } catch (java.io.IOException e) {
            throw new co.nstant.in.cbor.CborException(e);
        }
    }

    /* renamed from: co.nstant.in.cbor.decoder.AbstractDecoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation = new int[co.nstant.in.cbor.model.AdditionalInformation.values().length];

        static {
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.DIRECT.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.ONE_BYTE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.TWO_BYTES.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.FOUR_BYTES.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.EIGHT_BYTES.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.INDEFINITE.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.RESERVED.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
        }
    }

    protected long getLength(int i) throws co.nstant.in.cbor.CborException {
        switch (co.nstant.in.cbor.decoder.AbstractDecoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.ofByte(i).ordinal()]) {
            case 1:
                return i & 31;
            case 2:
                return nextSymbol();
            case 3:
                return (nextSymbol() << 8) | 0 | (nextSymbol() << 0);
            case 4:
                return (nextSymbol() << 16) | 0 | (nextSymbol() << 24) | (nextSymbol() << 8) | (nextSymbol() << 0);
            case 5:
                return (nextSymbol() << 16) | 0 | (nextSymbol() << 56) | (nextSymbol() << 48) | (nextSymbol() << 40) | (nextSymbol() << 32) | (nextSymbol() << 24) | (nextSymbol() << 8) | (nextSymbol() << 0);
            case 6:
                return -1L;
            default:
                throw new co.nstant.in.cbor.CborException("Reserved additional information");
        }
    }

    protected java.math.BigInteger getLengthAsBigInteger(int i) throws co.nstant.in.cbor.CborException {
        switch (co.nstant.in.cbor.decoder.AbstractDecoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$AdditionalInformation[co.nstant.in.cbor.model.AdditionalInformation.ofByte(i).ordinal()]) {
            case 1:
                return java.math.BigInteger.valueOf(i & 31);
            case 2:
                return java.math.BigInteger.valueOf(nextSymbol());
            case 3:
                return java.math.BigInteger.valueOf((nextSymbol() << 8) | 0 | (nextSymbol() << 0));
            case 4:
                return java.math.BigInteger.valueOf((nextSymbol() << 24) | 0 | (nextSymbol() << 16) | (nextSymbol() << 8) | (nextSymbol() << 0));
            case 5:
                return java.math.BigInteger.ZERO.or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(56)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(48)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(40)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(32)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(24)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(16)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(8)).or(java.math.BigInteger.valueOf(nextSymbol()).shiftLeft(0));
            case 6:
                return java.math.BigInteger.valueOf(-1L);
            default:
                throw new co.nstant.in.cbor.CborException("Reserved additional information");
        }
    }
}
