package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class SpecialDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.Special> {
    private final co.nstant.in.cbor.decoder.DoublePrecisionFloatDecoder doublePrecisionFloatDecoder;
    private final co.nstant.in.cbor.decoder.HalfPrecisionFloatDecoder halfPrecisionFloatDecoder;
    private final co.nstant.in.cbor.decoder.SinglePrecisionFloatDecoder singlePrecisionFloatDecoder;

    public SpecialDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
        this.halfPrecisionFloatDecoder = new co.nstant.in.cbor.decoder.HalfPrecisionFloatDecoder(cborDecoder, inputStream);
        this.singlePrecisionFloatDecoder = new co.nstant.in.cbor.decoder.SinglePrecisionFloatDecoder(cborDecoder, inputStream);
        this.doublePrecisionFloatDecoder = new co.nstant.in.cbor.decoder.DoublePrecisionFloatDecoder(cborDecoder, inputStream);
    }

    /* renamed from: co.nstant.in.cbor.decoder.SpecialDecoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$co$nstant$in$cbor$model$SimpleValueType;
        static final /* synthetic */ int[] $SwitchMap$co$nstant$in$cbor$model$SpecialType = new int[co.nstant.in.cbor.model.SpecialType.values().length];

        static {
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.BREAK.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.SIMPLE_VALUE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_HALF_PRECISION_FLOAT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_SINGLE_PRECISION_FLOAT.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_DOUBLE_PRECISION_FLOAT.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.SIMPLE_VALUE_NEXT_BYTE.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.UNALLOCATED.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            $SwitchMap$co$nstant$in$cbor$model$SimpleValueType = new int[co.nstant.in.cbor.model.SimpleValueType.values().length];
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.FALSE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.TRUE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.NULL.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.UNDEFINED.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.UNALLOCATED.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.RESERVED.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e13) {
            }
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.Special decode(int i) throws co.nstant.in.cbor.CborException {
        switch (co.nstant.in.cbor.decoder.SpecialDecoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.ofByte(i).ordinal()]) {
            case 1:
                return co.nstant.in.cbor.model.Special.BREAK;
            case 2:
                switch (co.nstant.in.cbor.decoder.SpecialDecoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.ofByte(i).ordinal()]) {
                    case 1:
                        return co.nstant.in.cbor.model.SimpleValue.FALSE;
                    case 2:
                        return co.nstant.in.cbor.model.SimpleValue.TRUE;
                    case 3:
                        return co.nstant.in.cbor.model.SimpleValue.NULL;
                    case 4:
                        return co.nstant.in.cbor.model.SimpleValue.UNDEFINED;
                    case 5:
                        return new co.nstant.in.cbor.model.SimpleValue(i & 31);
                    default:
                        throw new co.nstant.in.cbor.CborException("Not implemented");
                }
            case 3:
                return this.halfPrecisionFloatDecoder.decode(i);
            case 4:
                return this.singlePrecisionFloatDecoder.decode(i);
            case 5:
                return this.doublePrecisionFloatDecoder.decode(i);
            case 6:
                return new co.nstant.in.cbor.model.SimpleValue(nextSymbol());
            default:
                throw new co.nstant.in.cbor.CborException("Not implemented");
        }
    }
}
