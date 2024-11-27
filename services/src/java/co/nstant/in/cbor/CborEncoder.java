package co.nstant.in.cbor;

/* loaded from: classes.dex */
public class CborEncoder {
    private final co.nstant.in.cbor.encoder.ArrayEncoder arrayEncoder;
    private final co.nstant.in.cbor.encoder.ByteStringEncoder byteStringEncoder;
    private final co.nstant.in.cbor.encoder.MapEncoder mapEncoder;
    private final co.nstant.in.cbor.encoder.NegativeIntegerEncoder negativeIntegerEncoder;
    private final co.nstant.in.cbor.encoder.SpecialEncoder specialEncoder;
    private final co.nstant.in.cbor.encoder.TagEncoder tagEncoder;
    private final co.nstant.in.cbor.encoder.UnicodeStringEncoder unicodeStringEncoder;
    private final co.nstant.in.cbor.encoder.UnsignedIntegerEncoder unsignedIntegerEncoder;

    public CborEncoder(java.io.OutputStream outputStream) {
        java.util.Objects.requireNonNull(outputStream);
        this.unsignedIntegerEncoder = new co.nstant.in.cbor.encoder.UnsignedIntegerEncoder(this, outputStream);
        this.negativeIntegerEncoder = new co.nstant.in.cbor.encoder.NegativeIntegerEncoder(this, outputStream);
        this.byteStringEncoder = new co.nstant.in.cbor.encoder.ByteStringEncoder(this, outputStream);
        this.unicodeStringEncoder = new co.nstant.in.cbor.encoder.UnicodeStringEncoder(this, outputStream);
        this.arrayEncoder = new co.nstant.in.cbor.encoder.ArrayEncoder(this, outputStream);
        this.mapEncoder = new co.nstant.in.cbor.encoder.MapEncoder(this, outputStream);
        this.tagEncoder = new co.nstant.in.cbor.encoder.TagEncoder(this, outputStream);
        this.specialEncoder = new co.nstant.in.cbor.encoder.SpecialEncoder(this, outputStream);
    }

    public void encode(java.util.List<co.nstant.in.cbor.model.DataItem> list) throws co.nstant.in.cbor.CborException {
        java.util.Iterator<co.nstant.in.cbor.model.DataItem> it = list.iterator();
        while (it.hasNext()) {
            encode(it.next());
        }
    }

    public void encode(co.nstant.in.cbor.model.DataItem dataItem) throws co.nstant.in.cbor.CborException {
        if (dataItem == null) {
            dataItem = co.nstant.in.cbor.model.SimpleValue.NULL;
        }
        if (dataItem.hasTag()) {
            this.tagEncoder.encode(dataItem.getTag());
        }
        switch (co.nstant.in.cbor.CborEncoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$MajorType[dataItem.getMajorType().ordinal()]) {
            case 1:
                this.unsignedIntegerEncoder.encode((co.nstant.in.cbor.model.UnsignedInteger) dataItem);
                return;
            case 2:
                this.negativeIntegerEncoder.encode((co.nstant.in.cbor.model.NegativeInteger) dataItem);
                return;
            case 3:
                this.byteStringEncoder.encode((co.nstant.in.cbor.model.ByteString) dataItem);
                return;
            case 4:
                this.unicodeStringEncoder.encode((co.nstant.in.cbor.model.UnicodeString) dataItem);
                return;
            case 5:
                this.arrayEncoder.encode((co.nstant.in.cbor.model.Array) dataItem);
                return;
            case 6:
                this.mapEncoder.encode((co.nstant.in.cbor.model.Map) dataItem);
                return;
            case 7:
                this.specialEncoder.encode((co.nstant.in.cbor.model.Special) dataItem);
                return;
            case 8:
                this.tagEncoder.encode((co.nstant.in.cbor.model.Tag) dataItem);
                return;
            default:
                throw new co.nstant.in.cbor.CborException("Unknown major type");
        }
    }

    /* renamed from: co.nstant.in.cbor.CborEncoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$co$nstant$in$cbor$model$MajorType = new int[co.nstant.in.cbor.model.MajorType.values().length];

        static {
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.UNSIGNED_INTEGER.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.NEGATIVE_INTEGER.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.BYTE_STRING.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.UNICODE_STRING.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.ARRAY.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.MAP.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.SPECIAL.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.TAG.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
        }
    }
}
