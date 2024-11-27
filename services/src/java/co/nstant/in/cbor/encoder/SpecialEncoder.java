package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class SpecialEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.Special> {
    private final co.nstant.in.cbor.encoder.DoublePrecisionFloatEncoder doublePrecisionFloatEncoder;
    private final co.nstant.in.cbor.encoder.HalfPrecisionFloatEncoder halfPrecisionFloatEncoder;
    private final co.nstant.in.cbor.encoder.SinglePrecisionFloatEncoder singlePrecisionFloatEncoder;

    public SpecialEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
        this.halfPrecisionFloatEncoder = new co.nstant.in.cbor.encoder.HalfPrecisionFloatEncoder(cborEncoder, outputStream);
        this.singlePrecisionFloatEncoder = new co.nstant.in.cbor.encoder.SinglePrecisionFloatEncoder(cborEncoder, outputStream);
        this.doublePrecisionFloatEncoder = new co.nstant.in.cbor.encoder.DoublePrecisionFloatEncoder(cborEncoder, outputStream);
    }

    /* renamed from: co.nstant.in.cbor.encoder.SpecialEncoder$1, reason: invalid class name */
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
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.UNALLOCATED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_HALF_PRECISION_FLOAT.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_SINGLE_PRECISION_FLOAT.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.IEEE_754_DOUBLE_PRECISION_FLOAT.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SpecialType[co.nstant.in.cbor.model.SpecialType.SIMPLE_VALUE_NEXT_BYTE.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            $SwitchMap$co$nstant$in$cbor$model$SimpleValueType = new int[co.nstant.in.cbor.model.SimpleValueType.values().length];
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.FALSE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.NULL.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$SimpleValueType[co.nstant.in.cbor.model.SimpleValueType.TRUE.ordinal()] = 3;
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

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.Special special) throws co.nstant.in.cbor.CborException {
        switch (co.nstant.in.cbor.encoder.SpecialEncoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$SpecialType[special.getSpecialType().ordinal()]) {
            case 1:
                write(255);
                return;
            case 2:
                co.nstant.in.cbor.model.SimpleValue simpleValue = (co.nstant.in.cbor.model.SimpleValue) special;
                switch (co.nstant.in.cbor.encoder.SpecialEncoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$SimpleValueType[simpleValue.getSimpleValueType().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        write(simpleValue.getSimpleValueType().getValue() | com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS);
                        return;
                    case 5:
                        write(simpleValue.getValue() | com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS);
                        return;
                    default:
                        return;
                }
            case 3:
                throw new co.nstant.in.cbor.CborException("Unallocated special type");
            case 4:
                if (!(special instanceof co.nstant.in.cbor.model.HalfPrecisionFloat)) {
                    throw new co.nstant.in.cbor.CborException("Wrong data item type");
                }
                this.halfPrecisionFloatEncoder.encode((co.nstant.in.cbor.model.HalfPrecisionFloat) special);
                return;
            case 5:
                if (!(special instanceof co.nstant.in.cbor.model.SinglePrecisionFloat)) {
                    throw new co.nstant.in.cbor.CborException("Wrong data item type");
                }
                this.singlePrecisionFloatEncoder.encode((co.nstant.in.cbor.model.SinglePrecisionFloat) special);
                return;
            case 6:
                if (!(special instanceof co.nstant.in.cbor.model.DoublePrecisionFloat)) {
                    throw new co.nstant.in.cbor.CborException("Wrong data item type");
                }
                this.doublePrecisionFloatEncoder.encode((co.nstant.in.cbor.model.DoublePrecisionFloat) special);
                return;
            case 7:
                if (!(special instanceof co.nstant.in.cbor.model.SimpleValue)) {
                    throw new co.nstant.in.cbor.CborException("Wrong data item type");
                }
                write(com.android.internal.util.FrameworkStatsLog.INTEGRITY_RULES_PUSHED);
                write(((co.nstant.in.cbor.model.SimpleValue) special).getValue());
                return;
            default:
                return;
        }
    }
}
