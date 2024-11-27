package com.android.internal.location.altitude.nano;

/* loaded from: classes4.dex */
public final class MapParamsProto extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.internal.location.altitude.nano.MapParamsProto[] _emptyArray;
    public int cacheTileS2Level;
    public int diskTileS2Level;
    public int mapS2Level;
    public double modelAMeters;
    public double modelBMeters;
    public double modelRmseMeters;

    public static com.android.internal.location.altitude.nano.MapParamsProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.internal.location.altitude.nano.MapParamsProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public MapParamsProto() {
        clear();
    }

    public com.android.internal.location.altitude.nano.MapParamsProto clear() {
        this.mapS2Level = 0;
        this.cacheTileS2Level = 0;
        this.diskTileS2Level = 0;
        this.modelAMeters = 0.0d;
        this.modelBMeters = 0.0d;
        this.modelRmseMeters = 0.0d;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.mapS2Level != 0) {
            codedOutputByteBufferNano.writeInt32(1, this.mapS2Level);
        }
        if (this.cacheTileS2Level != 0) {
            codedOutputByteBufferNano.writeInt32(2, this.cacheTileS2Level);
        }
        if (this.diskTileS2Level != 0) {
            codedOutputByteBufferNano.writeInt32(3, this.diskTileS2Level);
        }
        if (java.lang.Double.doubleToLongBits(this.modelAMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            codedOutputByteBufferNano.writeDouble(4, this.modelAMeters);
        }
        if (java.lang.Double.doubleToLongBits(this.modelBMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            codedOutputByteBufferNano.writeDouble(5, this.modelBMeters);
        }
        if (java.lang.Double.doubleToLongBits(this.modelRmseMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            codedOutputByteBufferNano.writeDouble(6, this.modelRmseMeters);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.mapS2Level != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.mapS2Level);
        }
        if (this.cacheTileS2Level != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.cacheTileS2Level);
        }
        if (this.diskTileS2Level != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.diskTileS2Level);
        }
        if (java.lang.Double.doubleToLongBits(this.modelAMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(4, this.modelAMeters);
        }
        if (java.lang.Double.doubleToLongBits(this.modelBMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(5, this.modelBMeters);
        }
        if (java.lang.Double.doubleToLongBits(this.modelRmseMeters) != java.lang.Double.doubleToLongBits(0.0d)) {
            return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(6, this.modelRmseMeters);
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.internal.location.altitude.nano.MapParamsProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 8:
                    this.mapS2Level = codedInputByteBufferNano.readInt32();
                    break;
                case 16:
                    this.cacheTileS2Level = codedInputByteBufferNano.readInt32();
                    break;
                case 24:
                    this.diskTileS2Level = codedInputByteBufferNano.readInt32();
                    break;
                case 33:
                    this.modelAMeters = codedInputByteBufferNano.readDouble();
                    break;
                case 41:
                    this.modelBMeters = codedInputByteBufferNano.readDouble();
                    break;
                case 49:
                    this.modelRmseMeters = codedInputByteBufferNano.readDouble();
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.internal.location.altitude.nano.MapParamsProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.internal.location.altitude.nano.MapParamsProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.internal.location.altitude.nano.MapParamsProto(), bArr);
    }

    public static com.android.internal.location.altitude.nano.MapParamsProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.internal.location.altitude.nano.MapParamsProto().mergeFrom(codedInputByteBufferNano);
    }
}
