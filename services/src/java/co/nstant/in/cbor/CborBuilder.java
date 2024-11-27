package co.nstant.in.cbor;

/* loaded from: classes.dex */
public class CborBuilder extends co.nstant.in.cbor.builder.AbstractBuilder<co.nstant.in.cbor.CborBuilder> {
    private final java.util.List<co.nstant.in.cbor.model.DataItem> dataItems;

    public CborBuilder() {
        super(null);
        this.dataItems = new java.util.LinkedList();
    }

    public co.nstant.in.cbor.CborBuilder reset() {
        this.dataItems.clear();
        return this;
    }

    public java.util.List<co.nstant.in.cbor.model.DataItem> build() {
        return this.dataItems;
    }

    public co.nstant.in.cbor.CborBuilder add(co.nstant.in.cbor.model.DataItem dataItem) {
        this.dataItems.add(dataItem);
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(long j) {
        add(convert(j));
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(java.math.BigInteger bigInteger) {
        add(convert(bigInteger));
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(boolean z) {
        add(convert(z));
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(float f) {
        add(convert(f));
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(double d) {
        add(convert(d));
        return this;
    }

    public co.nstant.in.cbor.CborBuilder add(byte[] bArr) {
        add(convert(bArr));
        return this;
    }

    public co.nstant.in.cbor.builder.ByteStringBuilder<co.nstant.in.cbor.CborBuilder> startByteString() {
        return startByteString(null);
    }

    public co.nstant.in.cbor.builder.ByteStringBuilder<co.nstant.in.cbor.CborBuilder> startByteString(byte[] bArr) {
        add(new co.nstant.in.cbor.model.ByteString(bArr).setChunked(true));
        return new co.nstant.in.cbor.builder.ByteStringBuilder<>(this);
    }

    public co.nstant.in.cbor.CborBuilder add(java.lang.String str) {
        add(convert(str));
        return this;
    }

    public co.nstant.in.cbor.builder.UnicodeStringBuilder<co.nstant.in.cbor.CborBuilder> startString() {
        return startString(null);
    }

    public co.nstant.in.cbor.builder.UnicodeStringBuilder<co.nstant.in.cbor.CborBuilder> startString(java.lang.String str) {
        add(new co.nstant.in.cbor.model.UnicodeString(str).setChunked(true));
        return new co.nstant.in.cbor.builder.UnicodeStringBuilder<>(this);
    }

    public co.nstant.in.cbor.CborBuilder addTag(long j) {
        add(tag(j));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.CborBuilder> startArray() {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        array.setChunked(true);
        add(array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.CborBuilder> addArray() {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        add(array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.CborBuilder> addMap() {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        add(map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.CborBuilder> startMap() {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        map.setChunked(true);
        add(map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    @Override // co.nstant.in.cbor.builder.AbstractBuilder
    protected void addChunk(co.nstant.in.cbor.model.DataItem dataItem) {
        add(dataItem);
    }
}
