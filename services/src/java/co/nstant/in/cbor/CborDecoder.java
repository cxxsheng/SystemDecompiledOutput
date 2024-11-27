package co.nstant.in.cbor;

/* loaded from: classes.dex */
public class CborDecoder {
    private final co.nstant.in.cbor.decoder.ArrayDecoder arrayDecoder;
    private final co.nstant.in.cbor.decoder.ByteStringDecoder byteStringDecoder;
    private final java.io.InputStream inputStream;
    private final co.nstant.in.cbor.decoder.MapDecoder mapDecoder;
    private final co.nstant.in.cbor.decoder.NegativeIntegerDecoder negativeIntegerDecoder;
    private final co.nstant.in.cbor.decoder.SpecialDecoder specialDecoder;
    private final co.nstant.in.cbor.decoder.TagDecoder tagDecoder;
    private final co.nstant.in.cbor.decoder.UnicodeStringDecoder unicodeStringDecoder;
    private final co.nstant.in.cbor.decoder.UnsignedIntegerDecoder unsignedIntegerDecoder;
    private boolean autoDecodeInfinitiveArrays = true;
    private boolean autoDecodeInfinitiveMaps = true;
    private boolean autoDecodeInfinitiveByteStrings = true;
    private boolean autoDecodeInfinitiveUnicodeStrings = true;
    private boolean autoDecodeRationalNumbers = true;
    private boolean autoDecodeLanguageTaggedStrings = true;
    private boolean rejectDuplicateKeys = false;

    public CborDecoder(java.io.InputStream inputStream) {
        java.util.Objects.requireNonNull(inputStream);
        this.inputStream = inputStream;
        this.unsignedIntegerDecoder = new co.nstant.in.cbor.decoder.UnsignedIntegerDecoder(this, inputStream);
        this.negativeIntegerDecoder = new co.nstant.in.cbor.decoder.NegativeIntegerDecoder(this, inputStream);
        this.byteStringDecoder = new co.nstant.in.cbor.decoder.ByteStringDecoder(this, inputStream);
        this.unicodeStringDecoder = new co.nstant.in.cbor.decoder.UnicodeStringDecoder(this, inputStream);
        this.arrayDecoder = new co.nstant.in.cbor.decoder.ArrayDecoder(this, inputStream);
        this.mapDecoder = new co.nstant.in.cbor.decoder.MapDecoder(this, inputStream);
        this.tagDecoder = new co.nstant.in.cbor.decoder.TagDecoder(this, inputStream);
        this.specialDecoder = new co.nstant.in.cbor.decoder.SpecialDecoder(this, inputStream);
    }

    public static java.util.List<co.nstant.in.cbor.model.DataItem> decode(byte[] bArr) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.CborDecoder(new java.io.ByteArrayInputStream(bArr)).decode();
    }

    public java.util.List<co.nstant.in.cbor.model.DataItem> decode() throws co.nstant.in.cbor.CborException {
        java.util.LinkedList linkedList = new java.util.LinkedList();
        while (true) {
            co.nstant.in.cbor.model.DataItem decodeNext = decodeNext();
            if (decodeNext != null) {
                linkedList.add(decodeNext);
            } else {
                return linkedList;
            }
        }
    }

    public void decode(co.nstant.in.cbor.DataItemListener dataItemListener) throws co.nstant.in.cbor.CborException {
        java.util.Objects.requireNonNull(dataItemListener);
        co.nstant.in.cbor.model.DataItem decodeNext = decodeNext();
        while (decodeNext != null) {
            dataItemListener.onDataItem(decodeNext);
            decodeNext = decodeNext();
        }
    }

    public co.nstant.in.cbor.model.DataItem decodeNext() throws co.nstant.in.cbor.CborException {
        try {
            int read = this.inputStream.read();
            if (read == -1) {
                return null;
            }
            switch (co.nstant.in.cbor.CborDecoder.AnonymousClass1.$SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.ofByte(read).ordinal()]) {
                case 1:
                    return this.arrayDecoder.decode(read);
                case 2:
                    return this.byteStringDecoder.decode(read);
                case 3:
                    return this.mapDecoder.decode(read);
                case 4:
                    return this.negativeIntegerDecoder.decode(read);
                case 5:
                    return this.unicodeStringDecoder.decode(read);
                case 6:
                    return this.unsignedIntegerDecoder.decode(read);
                case 7:
                    return this.specialDecoder.decode(read);
                case 8:
                    co.nstant.in.cbor.model.Tag decode = this.tagDecoder.decode(read);
                    co.nstant.in.cbor.model.DataItem decodeNext = decodeNext();
                    if (decodeNext == null) {
                        throw new co.nstant.in.cbor.CborException("Unexpected end of stream: tag without following data item.");
                    }
                    if (this.autoDecodeRationalNumbers && decode.getValue() == 30) {
                        return decodeRationalNumber(decodeNext);
                    }
                    if (this.autoDecodeLanguageTaggedStrings && decode.getValue() == 38) {
                        return decodeLanguageTaggedString(decodeNext);
                    }
                    co.nstant.in.cbor.model.DataItem dataItem = decodeNext;
                    while (dataItem.hasTag()) {
                        dataItem = dataItem.getTag();
                    }
                    dataItem.setTag(decode);
                    return decodeNext;
                default:
                    throw new co.nstant.in.cbor.CborException("Not implemented major type " + read);
            }
        } catch (java.io.IOException e) {
            throw new co.nstant.in.cbor.CborException(e);
        }
    }

    /* renamed from: co.nstant.in.cbor.CborDecoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$co$nstant$in$cbor$model$MajorType = new int[co.nstant.in.cbor.model.MajorType.values().length];

        static {
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.ARRAY.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.BYTE_STRING.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.MAP.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.NEGATIVE_INTEGER.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.UNICODE_STRING.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.UNSIGNED_INTEGER.ordinal()] = 6;
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
            try {
                $SwitchMap$co$nstant$in$cbor$model$MajorType[co.nstant.in.cbor.model.MajorType.INVALID.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
        }
    }

    private co.nstant.in.cbor.model.DataItem decodeLanguageTaggedString(co.nstant.in.cbor.model.DataItem dataItem) throws co.nstant.in.cbor.CborException {
        if (!(dataItem instanceof co.nstant.in.cbor.model.Array)) {
            throw new co.nstant.in.cbor.CborException("Error decoding LanguageTaggedString: not an array");
        }
        co.nstant.in.cbor.model.Array array = (co.nstant.in.cbor.model.Array) dataItem;
        if (array.getDataItems().size() != 2) {
            throw new co.nstant.in.cbor.CborException("Error decoding LanguageTaggedString: array size is not 2");
        }
        co.nstant.in.cbor.model.DataItem dataItem2 = array.getDataItems().get(0);
        if (!(dataItem2 instanceof co.nstant.in.cbor.model.UnicodeString)) {
            throw new co.nstant.in.cbor.CborException("Error decoding LanguageTaggedString: first data item is not an UnicodeString");
        }
        co.nstant.in.cbor.model.DataItem dataItem3 = array.getDataItems().get(1);
        if (!(dataItem3 instanceof co.nstant.in.cbor.model.UnicodeString)) {
            throw new co.nstant.in.cbor.CborException("Error decoding LanguageTaggedString: second data item is not an UnicodeString");
        }
        return new co.nstant.in.cbor.model.LanguageTaggedString((co.nstant.in.cbor.model.UnicodeString) dataItem2, (co.nstant.in.cbor.model.UnicodeString) dataItem3);
    }

    private co.nstant.in.cbor.model.DataItem decodeRationalNumber(co.nstant.in.cbor.model.DataItem dataItem) throws co.nstant.in.cbor.CborException {
        if (!(dataItem instanceof co.nstant.in.cbor.model.Array)) {
            throw new co.nstant.in.cbor.CborException("Error decoding RationalNumber: not an array");
        }
        co.nstant.in.cbor.model.Array array = (co.nstant.in.cbor.model.Array) dataItem;
        if (array.getDataItems().size() != 2) {
            throw new co.nstant.in.cbor.CborException("Error decoding RationalNumber: array size is not 2");
        }
        co.nstant.in.cbor.model.DataItem dataItem2 = array.getDataItems().get(0);
        if (!(dataItem2 instanceof co.nstant.in.cbor.model.Number)) {
            throw new co.nstant.in.cbor.CborException("Error decoding RationalNumber: first data item is not a number");
        }
        co.nstant.in.cbor.model.DataItem dataItem3 = array.getDataItems().get(1);
        if (!(dataItem3 instanceof co.nstant.in.cbor.model.Number)) {
            throw new co.nstant.in.cbor.CborException("Error decoding RationalNumber: second data item is not a number");
        }
        return new co.nstant.in.cbor.model.RationalNumber((co.nstant.in.cbor.model.Number) dataItem2, (co.nstant.in.cbor.model.Number) dataItem3);
    }

    public boolean isAutoDecodeInfinitiveArrays() {
        return this.autoDecodeInfinitiveArrays;
    }

    public void setAutoDecodeInfinitiveArrays(boolean z) {
        this.autoDecodeInfinitiveArrays = z;
    }

    public boolean isAutoDecodeInfinitiveMaps() {
        return this.autoDecodeInfinitiveMaps;
    }

    public void setAutoDecodeInfinitiveMaps(boolean z) {
        this.autoDecodeInfinitiveMaps = z;
    }

    public boolean isAutoDecodeInfinitiveByteStrings() {
        return this.autoDecodeInfinitiveByteStrings;
    }

    public void setAutoDecodeInfinitiveByteStrings(boolean z) {
        this.autoDecodeInfinitiveByteStrings = z;
    }

    public boolean isAutoDecodeInfinitiveUnicodeStrings() {
        return this.autoDecodeInfinitiveUnicodeStrings;
    }

    public void setAutoDecodeInfinitiveUnicodeStrings(boolean z) {
        this.autoDecodeInfinitiveUnicodeStrings = z;
    }

    public boolean isAutoDecodeRationalNumbers() {
        return this.autoDecodeRationalNumbers;
    }

    public void setAutoDecodeRationalNumbers(boolean z) {
        this.autoDecodeRationalNumbers = z;
    }

    public boolean isAutoDecodeLanguageTaggedStrings() {
        return this.autoDecodeLanguageTaggedStrings;
    }

    public void setAutoDecodeLanguageTaggedStrings(boolean z) {
        this.autoDecodeLanguageTaggedStrings = z;
    }

    public boolean isRejectDuplicateKeys() {
        return this.rejectDuplicateKeys;
    }

    public void setRejectDuplicateKeys(boolean z) {
        this.rejectDuplicateKeys = z;
    }
}
