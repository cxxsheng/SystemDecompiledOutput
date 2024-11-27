package com.android.server.connectivity.metrics.nano;

/* loaded from: classes5.dex */
public interface IpConnectivityLogClass {
    public static final int BLUETOOTH = 1;
    public static final int CELLULAR = 2;
    public static final int ETHERNET = 3;
    public static final int LOWPAN = 9;
    public static final int MULTIPLE = 6;
    public static final int NONE = 5;
    public static final int UNKNOWN = 0;
    public static final int WIFI = 4;
    public static final int WIFI_NAN = 8;
    public static final int WIFI_P2P = 7;

    public static final class NetworkId extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId[] _emptyArray;
        public int networkId;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkId() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId clear() {
            this.networkId = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.networkId != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.networkId);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.networkId != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.networkId);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.networkId = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class Pair extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] _emptyArray;
        public int key;
        public int value;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[0];
                    }
                }
            }
            return _emptyArray;
        }

        public Pair() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair clear() {
            this.key = 0;
            this.value = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.key != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.key);
            }
            if (this.value != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.value);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.key != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.key);
            }
            if (this.value != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.value);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.key = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.value = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DefaultNetworkEvent extends com.android.framework.protobuf.nano.MessageNano {
        public static final int DISCONNECT = 3;
        public static final int DUAL = 3;
        public static final int INVALIDATION = 2;
        public static final int IPV4 = 1;
        public static final int IPV6 = 2;
        public static final int NONE = 0;
        public static final int OUTSCORED = 1;
        public static final int UNKNOWN = 0;
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent[] _emptyArray;
        public long defaultNetworkDurationMs;
        public long finalScore;
        public long initialScore;
        public int ipSupport;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId networkId;
        public long noDefaultNetworkDurationMs;
        public int previousDefaultNetworkLinkLayer;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId previousNetworkId;
        public int previousNetworkIpSupport;
        public int[] transportTypes;
        public long validationDurationMs;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DefaultNetworkEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent clear() {
            this.defaultNetworkDurationMs = 0L;
            this.validationDurationMs = 0L;
            this.initialScore = 0L;
            this.finalScore = 0L;
            this.ipSupport = 0;
            this.previousDefaultNetworkLinkLayer = 0;
            this.networkId = null;
            this.previousNetworkId = null;
            this.previousNetworkIpSupport = 0;
            this.transportTypes = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.noDefaultNetworkDurationMs = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, this.networkId);
            }
            if (this.previousNetworkId != null) {
                codedOutputByteBufferNano.writeMessage(2, this.previousNetworkId);
            }
            if (this.previousNetworkIpSupport != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.previousNetworkIpSupport);
            }
            if (this.transportTypes != null && this.transportTypes.length > 0) {
                for (int i = 0; i < this.transportTypes.length; i++) {
                    codedOutputByteBufferNano.writeInt32(4, this.transportTypes[i]);
                }
            }
            if (this.defaultNetworkDurationMs != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.defaultNetworkDurationMs);
            }
            if (this.noDefaultNetworkDurationMs != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.noDefaultNetworkDurationMs);
            }
            if (this.initialScore != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.initialScore);
            }
            if (this.finalScore != 0) {
                codedOutputByteBufferNano.writeInt64(8, this.finalScore);
            }
            if (this.ipSupport != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.ipSupport);
            }
            if (this.previousDefaultNetworkLinkLayer != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.previousDefaultNetworkLinkLayer);
            }
            if (this.validationDurationMs != 0) {
                codedOutputByteBufferNano.writeInt64(11, this.validationDurationMs);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.networkId != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, this.networkId);
            }
            if (this.previousNetworkId != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(2, this.previousNetworkId);
            }
            if (this.previousNetworkIpSupport != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.previousNetworkIpSupport);
            }
            if (this.transportTypes != null && this.transportTypes.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.transportTypes.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.transportTypes[i2]);
                }
                computeSerializedSize = computeSerializedSize + i + (this.transportTypes.length * 1);
            }
            if (this.defaultNetworkDurationMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.defaultNetworkDurationMs);
            }
            if (this.noDefaultNetworkDurationMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(6, this.noDefaultNetworkDurationMs);
            }
            if (this.initialScore != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(7, this.initialScore);
            }
            if (this.finalScore != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(8, this.finalScore);
            }
            if (this.ipSupport != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(9, this.ipSupport);
            }
            if (this.previousDefaultNetworkLinkLayer != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(10, this.previousDefaultNetworkLinkLayer);
            }
            if (this.validationDurationMs != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(11, this.validationDurationMs);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 18:
                        if (this.previousNetworkId == null) {
                            this.previousNetworkId = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.previousNetworkId);
                        break;
                    case 24:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.previousNetworkIpSupport = readInt32;
                                break;
                        }
                    case 32:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        int length = this.transportTypes == null ? 0 : this.transportTypes.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr = new int[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.transportTypes, 0, iArr, 0, length);
                        }
                        while (length < i - 1) {
                            iArr[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr[length] = codedInputByteBufferNano.readInt32();
                        this.transportTypes = iArr;
                        break;
                    case 34:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length2 = this.transportTypes == null ? 0 : this.transportTypes.length;
                        int i3 = i2 + length2;
                        int[] iArr2 = new int[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.transportTypes, 0, iArr2, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr2[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.transportTypes = iArr2;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 40:
                        this.defaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.noDefaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.initialScore = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.finalScore = codedInputByteBufferNano.readInt64();
                        break;
                    case 72:
                        int readInt322 = codedInputByteBufferNano.readInt32();
                        switch (readInt322) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.ipSupport = readInt322;
                                break;
                        }
                    case 80:
                        int readInt323 = codedInputByteBufferNano.readInt32();
                        switch (readInt323) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                                this.previousDefaultNetworkLinkLayer = readInt323;
                                break;
                        }
                    case 88:
                        this.validationDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpReachabilityEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent[] _emptyArray;
        public int eventType;
        public java.lang.String ifName;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpReachabilityEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if (this.eventType != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.eventType);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            if (this.eventType != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.eventType);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    case 16:
                        this.eventType = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NetworkEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent[] _emptyArray;
        public int eventType;
        public int latencyMs;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId networkId;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent clear() {
            this.networkId = null;
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, this.networkId);
            }
            if (this.eventType != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.eventType);
            }
            if (this.latencyMs != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.latencyMs);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.networkId != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, this.networkId);
            }
            if (this.eventType != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.eventType);
            }
            if (this.latencyMs != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.latencyMs);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 16:
                        this.eventType = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.latencyMs = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ValidationProbeEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent[] _emptyArray;
        public int latencyMs;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId networkId;
        public int probeResult;
        public int probeType;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ValidationProbeEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent clear() {
            this.networkId = null;
            this.latencyMs = 0;
            this.probeType = 0;
            this.probeResult = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, this.networkId);
            }
            if (this.latencyMs != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.latencyMs);
            }
            if (this.probeType != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.probeType);
            }
            if (this.probeResult != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.probeResult);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.networkId != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, this.networkId);
            }
            if (this.latencyMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.latencyMs);
            }
            if (this.probeType != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.probeType);
            }
            if (this.probeResult != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.probeResult);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 16:
                        this.latencyMs = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.probeType = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.probeResult = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DNSLookupBatch extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch[] _emptyArray;
        public int[] eventTypes;
        public long getaddrinfoErrorCount;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] getaddrinfoErrors;
        public long getaddrinfoQueryCount;
        public long gethostbynameErrorCount;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] gethostbynameErrors;
        public long gethostbynameQueryCount;
        public int[] latenciesMs;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId networkId;
        public int[] returnCodes;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DNSLookupBatch() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch clear() {
            this.latenciesMs = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.getaddrinfoQueryCount = 0L;
            this.gethostbynameQueryCount = 0L;
            this.getaddrinfoErrorCount = 0L;
            this.gethostbynameErrorCount = 0L;
            this.getaddrinfoErrors = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.gethostbynameErrors = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.networkId = null;
            this.eventTypes = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.returnCodes = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, this.networkId);
            }
            if (this.eventTypes != null && this.eventTypes.length > 0) {
                for (int i = 0; i < this.eventTypes.length; i++) {
                    codedOutputByteBufferNano.writeInt32(2, this.eventTypes[i]);
                }
            }
            if (this.returnCodes != null && this.returnCodes.length > 0) {
                for (int i2 = 0; i2 < this.returnCodes.length; i2++) {
                    codedOutputByteBufferNano.writeInt32(3, this.returnCodes[i2]);
                }
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                for (int i3 = 0; i3 < this.latenciesMs.length; i3++) {
                    codedOutputByteBufferNano.writeInt32(4, this.latenciesMs[i3]);
                }
            }
            if (this.getaddrinfoQueryCount != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.getaddrinfoQueryCount);
            }
            if (this.gethostbynameQueryCount != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.gethostbynameQueryCount);
            }
            if (this.getaddrinfoErrorCount != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.getaddrinfoErrorCount);
            }
            if (this.gethostbynameErrorCount != 0) {
                codedOutputByteBufferNano.writeInt64(8, this.gethostbynameErrorCount);
            }
            if (this.getaddrinfoErrors != null && this.getaddrinfoErrors.length > 0) {
                for (int i4 = 0; i4 < this.getaddrinfoErrors.length; i4++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.getaddrinfoErrors[i4];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(9, pair);
                    }
                }
            }
            if (this.gethostbynameErrors != null && this.gethostbynameErrors.length > 0) {
                for (int i5 = 0; i5 < this.gethostbynameErrors.length; i5++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.gethostbynameErrors[i5];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(10, pair2);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.networkId != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, this.networkId);
            }
            if (this.eventTypes != null && this.eventTypes.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.eventTypes.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.eventTypes[i2]);
                }
                computeSerializedSize = computeSerializedSize + i + (this.eventTypes.length * 1);
            }
            if (this.returnCodes != null && this.returnCodes.length > 0) {
                int i3 = 0;
                for (int i4 = 0; i4 < this.returnCodes.length; i4++) {
                    i3 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.returnCodes[i4]);
                }
                computeSerializedSize = computeSerializedSize + i3 + (this.returnCodes.length * 1);
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                int i5 = 0;
                for (int i6 = 0; i6 < this.latenciesMs.length; i6++) {
                    i5 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.latenciesMs[i6]);
                }
                computeSerializedSize = computeSerializedSize + i5 + (this.latenciesMs.length * 1);
            }
            if (this.getaddrinfoQueryCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.getaddrinfoQueryCount);
            }
            if (this.gethostbynameQueryCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(6, this.gethostbynameQueryCount);
            }
            if (this.getaddrinfoErrorCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(7, this.getaddrinfoErrorCount);
            }
            if (this.gethostbynameErrorCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(8, this.gethostbynameErrorCount);
            }
            if (this.getaddrinfoErrors != null && this.getaddrinfoErrors.length > 0) {
                for (int i7 = 0; i7 < this.getaddrinfoErrors.length; i7++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.getaddrinfoErrors[i7];
                    if (pair != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(9, pair);
                    }
                }
            }
            if (this.gethostbynameErrors != null && this.gethostbynameErrors.length > 0) {
                for (int i8 = 0; i8 < this.gethostbynameErrors.length; i8++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.gethostbynameErrors[i8];
                    if (pair2 != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(10, pair2);
                    }
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 16:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 16);
                        int length = this.eventTypes == null ? 0 : this.eventTypes.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr = new int[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.eventTypes, 0, iArr, 0, length);
                        }
                        while (length < i - 1) {
                            iArr[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr[length] = codedInputByteBufferNano.readInt32();
                        this.eventTypes = iArr;
                        break;
                    case 18:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length2 = this.eventTypes == null ? 0 : this.eventTypes.length;
                        int i3 = i2 + length2;
                        int[] iArr2 = new int[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.eventTypes, 0, iArr2, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr2[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.eventTypes = iArr2;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 24:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        int length3 = this.returnCodes == null ? 0 : this.returnCodes.length;
                        int i4 = repeatedFieldArrayLength2 + length3;
                        int[] iArr3 = new int[i4];
                        if (length3 != 0) {
                            java.lang.System.arraycopy(this.returnCodes, 0, iArr3, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            iArr3[length3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        iArr3[length3] = codedInputByteBufferNano.readInt32();
                        this.returnCodes = iArr3;
                        break;
                    case 26:
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position2 = codedInputByteBufferNano.getPosition();
                        int i5 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i5++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position2);
                        int length4 = this.returnCodes == null ? 0 : this.returnCodes.length;
                        int i6 = i5 + length4;
                        int[] iArr4 = new int[i6];
                        if (length4 != 0) {
                            java.lang.System.arraycopy(this.returnCodes, 0, iArr4, 0, length4);
                        }
                        while (length4 < i6) {
                            iArr4[length4] = codedInputByteBufferNano.readInt32();
                            length4++;
                        }
                        this.returnCodes = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                        break;
                    case 32:
                        int repeatedFieldArrayLength3 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        int length5 = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i7 = repeatedFieldArrayLength3 + length5;
                        int[] iArr5 = new int[i7];
                        if (length5 != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr5, 0, length5);
                        }
                        while (length5 < i7 - 1) {
                            iArr5[length5] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        iArr5[length5] = codedInputByteBufferNano.readInt32();
                        this.latenciesMs = iArr5;
                        break;
                    case 34:
                        int pushLimit3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position3 = codedInputByteBufferNano.getPosition();
                        int i8 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i8++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position3);
                        int length6 = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i9 = i8 + length6;
                        int[] iArr6 = new int[i9];
                        if (length6 != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr6, 0, length6);
                        }
                        while (length6 < i9) {
                            iArr6[length6] = codedInputByteBufferNano.readInt32();
                            length6++;
                        }
                        this.latenciesMs = iArr6;
                        codedInputByteBufferNano.popLimit(pushLimit3);
                        break;
                    case 40:
                        this.getaddrinfoQueryCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.gethostbynameQueryCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.getaddrinfoErrorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.gethostbynameErrorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 74:
                        int repeatedFieldArrayLength4 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        int length7 = this.getaddrinfoErrors == null ? 0 : this.getaddrinfoErrors.length;
                        int i10 = repeatedFieldArrayLength4 + length7;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i10];
                        if (length7 != 0) {
                            java.lang.System.arraycopy(this.getaddrinfoErrors, 0, pairArr, 0, length7);
                        }
                        while (length7 < i10 - 1) {
                            pairArr[length7] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr[length7]);
                            codedInputByteBufferNano.readTag();
                            length7++;
                        }
                        pairArr[length7] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr[length7]);
                        this.getaddrinfoErrors = pairArr;
                        break;
                    case 82:
                        int repeatedFieldArrayLength5 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        int length8 = this.gethostbynameErrors == null ? 0 : this.gethostbynameErrors.length;
                        int i11 = repeatedFieldArrayLength5 + length8;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i11];
                        if (length8 != 0) {
                            java.lang.System.arraycopy(this.gethostbynameErrors, 0, pairArr2, 0, length8);
                        }
                        while (length8 < i11 - 1) {
                            pairArr2[length8] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr2[length8]);
                            codedInputByteBufferNano.readTag();
                            length8++;
                        }
                        pairArr2[length8] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr2[length8]);
                        this.gethostbynameErrors = pairArr2;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DNSLatencies extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies[] _emptyArray;
        public int aCount;
        public int aaaaCount;
        public int[] latenciesMs;
        public int queryCount;
        public int returnCode;
        public int type;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DNSLatencies() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies clear() {
            this.type = 0;
            this.returnCode = 0;
            this.queryCount = 0;
            this.aCount = 0;
            this.aaaaCount = 0;
            this.latenciesMs = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.type != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.type);
            }
            if (this.returnCode != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.returnCode);
            }
            if (this.queryCount != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.queryCount);
            }
            if (this.aCount != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.aCount);
            }
            if (this.aaaaCount != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.aaaaCount);
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                for (int i = 0; i < this.latenciesMs.length; i++) {
                    codedOutputByteBufferNano.writeInt32(6, this.latenciesMs[i]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.type != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.type);
            }
            if (this.returnCode != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.returnCode);
            }
            if (this.queryCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.queryCount);
            }
            if (this.aCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.aCount);
            }
            if (this.aaaaCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.aaaaCount);
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.latenciesMs.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.latenciesMs[i2]);
                }
                return computeSerializedSize + i + (this.latenciesMs.length * 1);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.type = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.returnCode = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.queryCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.aCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.aaaaCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                        int length = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr = new int[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr, 0, length);
                        }
                        while (length < i - 1) {
                            iArr[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr[length] = codedInputByteBufferNano.readInt32();
                        this.latenciesMs = iArr;
                        break;
                    case 50:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length2 = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i3 = i2 + length2;
                        int[] iArr2 = new int[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr2, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr2[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.latenciesMs = iArr2;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ConnectStatistics extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics[] _emptyArray;
        public int connectBlockingCount;
        public int connectCount;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] errnosCounters;
        public int ipv6AddrCount;
        public int[] latenciesMs;
        public int[] nonBlockingLatenciesMs;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ConnectStatistics() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics clear() {
            this.connectCount = 0;
            this.connectBlockingCount = 0;
            this.ipv6AddrCount = 0;
            this.latenciesMs = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.nonBlockingLatenciesMs = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_INT_ARRAY;
            this.errnosCounters = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.connectCount != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.connectCount);
            }
            if (this.ipv6AddrCount != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.ipv6AddrCount);
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                for (int i = 0; i < this.latenciesMs.length; i++) {
                    codedOutputByteBufferNano.writeInt32(3, this.latenciesMs[i]);
                }
            }
            if (this.errnosCounters != null && this.errnosCounters.length > 0) {
                for (int i2 = 0; i2 < this.errnosCounters.length; i2++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.errnosCounters[i2];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(4, pair);
                    }
                }
            }
            if (this.connectBlockingCount != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.connectBlockingCount);
            }
            if (this.nonBlockingLatenciesMs != null && this.nonBlockingLatenciesMs.length > 0) {
                for (int i3 = 0; i3 < this.nonBlockingLatenciesMs.length; i3++) {
                    codedOutputByteBufferNano.writeInt32(6, this.nonBlockingLatenciesMs[i3]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.connectCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.connectCount);
            }
            if (this.ipv6AddrCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.ipv6AddrCount);
            }
            if (this.latenciesMs != null && this.latenciesMs.length > 0) {
                int i = 0;
                for (int i2 = 0; i2 < this.latenciesMs.length; i2++) {
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.latenciesMs[i2]);
                }
                computeSerializedSize = computeSerializedSize + i + (this.latenciesMs.length * 1);
            }
            if (this.errnosCounters != null && this.errnosCounters.length > 0) {
                for (int i3 = 0; i3 < this.errnosCounters.length; i3++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.errnosCounters[i3];
                    if (pair != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(4, pair);
                    }
                }
            }
            if (this.connectBlockingCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.connectBlockingCount);
            }
            if (this.nonBlockingLatenciesMs != null && this.nonBlockingLatenciesMs.length > 0) {
                int i4 = 0;
                for (int i5 = 0; i5 < this.nonBlockingLatenciesMs.length; i5++) {
                    i4 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(this.nonBlockingLatenciesMs[i5]);
                }
                return computeSerializedSize + i4 + (this.nonBlockingLatenciesMs.length * 1);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.connectCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.ipv6AddrCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        int length = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr = new int[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr, 0, length);
                        }
                        while (length < i - 1) {
                            iArr[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr[length] = codedInputByteBufferNano.readInt32();
                        this.latenciesMs = iArr;
                        break;
                    case 26:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int length2 = this.latenciesMs == null ? 0 : this.latenciesMs.length;
                        int i3 = i2 + length2;
                        int[] iArr2 = new int[i3];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.latenciesMs, 0, iArr2, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr2[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.latenciesMs = iArr2;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 34:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        int length3 = this.errnosCounters == null ? 0 : this.errnosCounters.length;
                        int i4 = repeatedFieldArrayLength2 + length3;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i4];
                        if (length3 != 0) {
                            java.lang.System.arraycopy(this.errnosCounters, 0, pairArr, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            pairArr[length3] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        pairArr[length3] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr[length3]);
                        this.errnosCounters = pairArr;
                        break;
                    case 40:
                        this.connectBlockingCount = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        int repeatedFieldArrayLength3 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                        int length4 = this.nonBlockingLatenciesMs == null ? 0 : this.nonBlockingLatenciesMs.length;
                        int i5 = repeatedFieldArrayLength3 + length4;
                        int[] iArr3 = new int[i5];
                        if (length4 != 0) {
                            java.lang.System.arraycopy(this.nonBlockingLatenciesMs, 0, iArr3, 0, length4);
                        }
                        while (length4 < i5 - 1) {
                            iArr3[length4] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        iArr3[length4] = codedInputByteBufferNano.readInt32();
                        this.nonBlockingLatenciesMs = iArr3;
                        break;
                    case 50:
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position2 = codedInputByteBufferNano.getPosition();
                        int i6 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i6++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position2);
                        int length5 = this.nonBlockingLatenciesMs == null ? 0 : this.nonBlockingLatenciesMs.length;
                        int i7 = i6 + length5;
                        int[] iArr4 = new int[i7];
                        if (length5 != 0) {
                            java.lang.System.arraycopy(this.nonBlockingLatenciesMs, 0, iArr4, 0, length5);
                        }
                        while (length5 < i7) {
                            iArr4[length5] = codedInputByteBufferNano.readInt32();
                            length5++;
                        }
                        this.nonBlockingLatenciesMs = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DHCPEvent extends com.android.framework.protobuf.nano.MessageNano {
        public static final int ERROR_CODE_FIELD_NUMBER = 3;
        public static final int STATE_TRANSITION_FIELD_NUMBER = 2;
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent[] _emptyArray;
        public int durationMs;
        public java.lang.String ifName;
        private int valueCase_ = 0;
        private java.lang.Object value_;

        public int getValueCase() {
            return this.valueCase_;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent clearValue() {
            this.valueCase_ = 0;
            this.value_ = null;
            return this;
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasStateTransition() {
            return this.valueCase_ == 2;
        }

        public java.lang.String getStateTransition() {
            if (this.valueCase_ == 2) {
                return (java.lang.String) this.value_;
            }
            return "";
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent setStateTransition(java.lang.String str) {
            this.valueCase_ = 2;
            this.value_ = str;
            return this;
        }

        public boolean hasErrorCode() {
            return this.valueCase_ == 3;
        }

        public int getErrorCode() {
            if (this.valueCase_ == 3) {
                return ((java.lang.Integer) this.value_).intValue();
            }
            return 0;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent setErrorCode(int i) {
            this.valueCase_ = 3;
            this.value_ = java.lang.Integer.valueOf(i);
            return this;
        }

        public DHCPEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent clear() {
            this.ifName = "";
            this.durationMs = 0;
            clearValue();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if (this.valueCase_ == 2) {
                codedOutputByteBufferNano.writeString(2, (java.lang.String) this.value_);
            }
            if (this.valueCase_ == 3) {
                codedOutputByteBufferNano.writeInt32(3, ((java.lang.Integer) this.value_).intValue());
            }
            if (this.durationMs != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.durationMs);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            if (this.valueCase_ == 2) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(2, (java.lang.String) this.value_);
            }
            if (this.valueCase_ == 3) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, ((java.lang.Integer) this.value_).intValue());
            }
            if (this.durationMs != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.durationMs);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.value_ = codedInputByteBufferNano.readString();
                        this.valueCase_ = 2;
                        break;
                    case 24:
                        this.value_ = java.lang.Integer.valueOf(codedInputByteBufferNano.readInt32());
                        this.valueCase_ = 3;
                        break;
                    case 32:
                        this.durationMs = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApfProgramEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent[] _emptyArray;
        public int currentRas;
        public boolean dropMulticast;
        public long effectiveLifetime;
        public int filteredRas;
        public boolean hasIpv4Addr;
        public long lifetime;
        public int programLength;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApfProgramEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent clear() {
            this.lifetime = 0L;
            this.effectiveLifetime = 0L;
            this.filteredRas = 0;
            this.currentRas = 0;
            this.programLength = 0;
            this.dropMulticast = false;
            this.hasIpv4Addr = false;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.lifetime != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.lifetime);
            }
            if (this.filteredRas != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.filteredRas);
            }
            if (this.currentRas != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.currentRas);
            }
            if (this.programLength != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.programLength);
            }
            if (this.dropMulticast) {
                codedOutputByteBufferNano.writeBool(5, this.dropMulticast);
            }
            if (this.hasIpv4Addr) {
                codedOutputByteBufferNano.writeBool(6, this.hasIpv4Addr);
            }
            if (this.effectiveLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.effectiveLifetime);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.lifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.lifetime);
            }
            if (this.filteredRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.filteredRas);
            }
            if (this.currentRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.currentRas);
            }
            if (this.programLength != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.programLength);
            }
            if (this.dropMulticast) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(5, this.dropMulticast);
            }
            if (this.hasIpv4Addr) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(6, this.hasIpv4Addr);
            }
            if (this.effectiveLifetime != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(7, this.effectiveLifetime);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.lifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.filteredRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.currentRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.programLength = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.dropMulticast = codedInputByteBufferNano.readBool();
                        break;
                    case 48:
                        this.hasIpv4Addr = codedInputByteBufferNano.readBool();
                        break;
                    case 56:
                        this.effectiveLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApfStatistics extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics[] _emptyArray;
        public int droppedRas;
        public long durationMs;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] hardwareCounters;
        public int matchingRas;
        public int maxProgramSize;
        public int parseErrors;
        public int programUpdates;
        public int programUpdatesAll;
        public int programUpdatesAllowingMulticast;
        public int receivedRas;
        public int totalPacketDropped;
        public int totalPacketProcessed;
        public int zeroLifetimeRas;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApfStatistics() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics clear() {
            this.durationMs = 0L;
            this.receivedRas = 0;
            this.matchingRas = 0;
            this.droppedRas = 0;
            this.zeroLifetimeRas = 0;
            this.parseErrors = 0;
            this.programUpdates = 0;
            this.maxProgramSize = 0;
            this.programUpdatesAll = 0;
            this.programUpdatesAllowingMulticast = 0;
            this.totalPacketProcessed = 0;
            this.totalPacketDropped = 0;
            this.hardwareCounters = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.durationMs != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.durationMs);
            }
            if (this.receivedRas != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.receivedRas);
            }
            if (this.matchingRas != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.matchingRas);
            }
            if (this.droppedRas != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.droppedRas);
            }
            if (this.zeroLifetimeRas != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.zeroLifetimeRas);
            }
            if (this.parseErrors != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.parseErrors);
            }
            if (this.programUpdates != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.programUpdates);
            }
            if (this.maxProgramSize != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.maxProgramSize);
            }
            if (this.programUpdatesAll != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.programUpdatesAll);
            }
            if (this.programUpdatesAllowingMulticast != 0) {
                codedOutputByteBufferNano.writeInt32(11, this.programUpdatesAllowingMulticast);
            }
            if (this.totalPacketProcessed != 0) {
                codedOutputByteBufferNano.writeInt32(12, this.totalPacketProcessed);
            }
            if (this.totalPacketDropped != 0) {
                codedOutputByteBufferNano.writeInt32(13, this.totalPacketDropped);
            }
            if (this.hardwareCounters != null && this.hardwareCounters.length > 0) {
                for (int i = 0; i < this.hardwareCounters.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.hardwareCounters[i];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(14, pair);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.durationMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.durationMs);
            }
            if (this.receivedRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.receivedRas);
            }
            if (this.matchingRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.matchingRas);
            }
            if (this.droppedRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.droppedRas);
            }
            if (this.zeroLifetimeRas != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(6, this.zeroLifetimeRas);
            }
            if (this.parseErrors != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(7, this.parseErrors);
            }
            if (this.programUpdates != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(8, this.programUpdates);
            }
            if (this.maxProgramSize != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(9, this.maxProgramSize);
            }
            if (this.programUpdatesAll != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(10, this.programUpdatesAll);
            }
            if (this.programUpdatesAllowingMulticast != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(11, this.programUpdatesAllowingMulticast);
            }
            if (this.totalPacketProcessed != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(12, this.totalPacketProcessed);
            }
            if (this.totalPacketDropped != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(13, this.totalPacketDropped);
            }
            if (this.hardwareCounters != null && this.hardwareCounters.length > 0) {
                for (int i = 0; i < this.hardwareCounters.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.hardwareCounters[i];
                    if (pair != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(14, pair);
                    }
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.durationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.receivedRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.matchingRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.droppedRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.zeroLifetimeRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 56:
                        this.parseErrors = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.programUpdates = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.maxProgramSize = codedInputByteBufferNano.readInt32();
                        break;
                    case 80:
                        this.programUpdatesAll = codedInputByteBufferNano.readInt32();
                        break;
                    case 88:
                        this.programUpdatesAllowingMulticast = codedInputByteBufferNano.readInt32();
                        break;
                    case 96:
                        this.totalPacketProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 104:
                        this.totalPacketDropped = codedInputByteBufferNano.readInt32();
                        break;
                    case 114:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        int length = this.hardwareCounters == null ? 0 : this.hardwareCounters.length;
                        int i = repeatedFieldArrayLength + length;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.hardwareCounters, 0, pairArr, 0, length);
                        }
                        while (length < i - 1) {
                            pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr[length]);
                        this.hardwareCounters = pairArr;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class RaEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent[] _emptyArray;
        public long dnsslLifetime;
        public long prefixPreferredLifetime;
        public long prefixValidLifetime;
        public long rdnssLifetime;
        public long routeInfoLifetime;
        public long routerLifetime;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public RaEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent clear() {
            this.routerLifetime = 0L;
            this.prefixValidLifetime = 0L;
            this.prefixPreferredLifetime = 0L;
            this.routeInfoLifetime = 0L;
            this.rdnssLifetime = 0L;
            this.dnsslLifetime = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.routerLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.routerLifetime);
            }
            if (this.prefixValidLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(2, this.prefixValidLifetime);
            }
            if (this.prefixPreferredLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(3, this.prefixPreferredLifetime);
            }
            if (this.routeInfoLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(4, this.routeInfoLifetime);
            }
            if (this.rdnssLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.rdnssLifetime);
            }
            if (this.dnsslLifetime != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.dnsslLifetime);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.routerLifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.routerLifetime);
            }
            if (this.prefixValidLifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(2, this.prefixValidLifetime);
            }
            if (this.prefixPreferredLifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(3, this.prefixPreferredLifetime);
            }
            if (this.routeInfoLifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(4, this.routeInfoLifetime);
            }
            if (this.rdnssLifetime != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.rdnssLifetime);
            }
            if (this.dnsslLifetime != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(6, this.dnsslLifetime);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.routerLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.prefixValidLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 24:
                        this.prefixPreferredLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 32:
                        this.routeInfoLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 40:
                        this.rdnssLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.dnsslLifetime = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpProvisioningEvent extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent[] _emptyArray;
        public int eventType;
        public java.lang.String ifName;
        public int latencyMs;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpProvisioningEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if (this.eventType != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.eventType);
            }
            if (this.latencyMs != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.latencyMs);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            if (this.eventType != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.eventType);
            }
            if (this.latencyMs != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.latencyMs);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    case 16:
                        this.eventType = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.latencyMs = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NetworkStats extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats[] _emptyArray;
        public long durationMs;
        public boolean everValidated;
        public int ipSupport;
        public int noConnectivityReports;
        public boolean portalFound;
        public int validationAttempts;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] validationEvents;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] validationStates;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkStats() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats clear() {
            this.durationMs = 0L;
            this.ipSupport = 0;
            this.everValidated = false;
            this.portalFound = false;
            this.noConnectivityReports = 0;
            this.validationAttempts = 0;
            this.validationEvents = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.validationStates = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.durationMs != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.durationMs);
            }
            if (this.ipSupport != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.ipSupport);
            }
            if (this.everValidated) {
                codedOutputByteBufferNano.writeBool(3, this.everValidated);
            }
            if (this.portalFound) {
                codedOutputByteBufferNano.writeBool(4, this.portalFound);
            }
            if (this.noConnectivityReports != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.noConnectivityReports);
            }
            if (this.validationAttempts != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.validationAttempts);
            }
            if (this.validationEvents != null && this.validationEvents.length > 0) {
                for (int i = 0; i < this.validationEvents.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.validationEvents[i];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(7, pair);
                    }
                }
            }
            if (this.validationStates != null && this.validationStates.length > 0) {
                for (int i2 = 0; i2 < this.validationStates.length; i2++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.validationStates[i2];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(8, pair2);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.durationMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.durationMs);
            }
            if (this.ipSupport != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.ipSupport);
            }
            if (this.everValidated) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(3, this.everValidated);
            }
            if (this.portalFound) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(4, this.portalFound);
            }
            if (this.noConnectivityReports != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.noConnectivityReports);
            }
            if (this.validationAttempts != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(6, this.validationAttempts);
            }
            if (this.validationEvents != null && this.validationEvents.length > 0) {
                for (int i = 0; i < this.validationEvents.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.validationEvents[i];
                    if (pair != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(7, pair);
                    }
                }
            }
            if (this.validationStates != null && this.validationStates.length > 0) {
                for (int i2 = 0; i2 < this.validationStates.length; i2++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.validationStates[i2];
                    if (pair2 != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(8, pair2);
                    }
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.durationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.ipSupport = readInt32;
                                break;
                        }
                    case 24:
                        this.everValidated = codedInputByteBufferNano.readBool();
                        break;
                    case 32:
                        this.portalFound = codedInputByteBufferNano.readBool();
                        break;
                    case 40:
                        this.noConnectivityReports = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.validationAttempts = codedInputByteBufferNano.readInt32();
                        break;
                    case 58:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                        int length = this.validationEvents == null ? 0 : this.validationEvents.length;
                        int i = repeatedFieldArrayLength + length;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.validationEvents, 0, pairArr, 0, length);
                        }
                        while (length < i - 1) {
                            pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr[length]);
                        this.validationEvents = pairArr;
                        break;
                    case 66:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        int length2 = this.validationStates == null ? 0 : this.validationStates.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i2];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.validationStates, 0, pairArr2, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            pairArr2[length2] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr2[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        pairArr2[length2] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr2[length2]);
                        this.validationStates = pairArr2;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class WakeupStats extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats[] _emptyArray;
        public long applicationWakeups;
        public long durationSec;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] ethertypeCounts;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] ipNextHeaderCounts;
        public long l2BroadcastCount;
        public long l2MulticastCount;
        public long l2UnicastCount;
        public long noUidWakeups;
        public long nonApplicationWakeups;
        public long rootWakeups;
        public long systemWakeups;
        public long totalWakeups;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats[0];
                    }
                }
            }
            return _emptyArray;
        }

        public WakeupStats() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats clear() {
            this.durationSec = 0L;
            this.totalWakeups = 0L;
            this.rootWakeups = 0L;
            this.systemWakeups = 0L;
            this.applicationWakeups = 0L;
            this.nonApplicationWakeups = 0L;
            this.noUidWakeups = 0L;
            this.ethertypeCounts = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.ipNextHeaderCounts = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair.emptyArray();
            this.l2UnicastCount = 0L;
            this.l2MulticastCount = 0L;
            this.l2BroadcastCount = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.durationSec != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.durationSec);
            }
            if (this.totalWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(2, this.totalWakeups);
            }
            if (this.rootWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(3, this.rootWakeups);
            }
            if (this.systemWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(4, this.systemWakeups);
            }
            if (this.applicationWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.applicationWakeups);
            }
            if (this.nonApplicationWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.nonApplicationWakeups);
            }
            if (this.noUidWakeups != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.noUidWakeups);
            }
            if (this.ethertypeCounts != null && this.ethertypeCounts.length > 0) {
                for (int i = 0; i < this.ethertypeCounts.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.ethertypeCounts[i];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(8, pair);
                    }
                }
            }
            if (this.ipNextHeaderCounts != null && this.ipNextHeaderCounts.length > 0) {
                for (int i2 = 0; i2 < this.ipNextHeaderCounts.length; i2++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.ipNextHeaderCounts[i2];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(9, pair2);
                    }
                }
            }
            if (this.l2UnicastCount != 0) {
                codedOutputByteBufferNano.writeInt64(10, this.l2UnicastCount);
            }
            if (this.l2MulticastCount != 0) {
                codedOutputByteBufferNano.writeInt64(11, this.l2MulticastCount);
            }
            if (this.l2BroadcastCount != 0) {
                codedOutputByteBufferNano.writeInt64(12, this.l2BroadcastCount);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.durationSec != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.durationSec);
            }
            if (this.totalWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(2, this.totalWakeups);
            }
            if (this.rootWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(3, this.rootWakeups);
            }
            if (this.systemWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(4, this.systemWakeups);
            }
            if (this.applicationWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.applicationWakeups);
            }
            if (this.nonApplicationWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(6, this.nonApplicationWakeups);
            }
            if (this.noUidWakeups != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(7, this.noUidWakeups);
            }
            if (this.ethertypeCounts != null && this.ethertypeCounts.length > 0) {
                for (int i = 0; i < this.ethertypeCounts.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = this.ethertypeCounts[i];
                    if (pair != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(8, pair);
                    }
                }
            }
            if (this.ipNextHeaderCounts != null && this.ipNextHeaderCounts.length > 0) {
                for (int i2 = 0; i2 < this.ipNextHeaderCounts.length; i2++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair2 = this.ipNextHeaderCounts[i2];
                    if (pair2 != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(9, pair2);
                    }
                }
            }
            if (this.l2UnicastCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(10, this.l2UnicastCount);
            }
            if (this.l2MulticastCount != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(11, this.l2MulticastCount);
            }
            if (this.l2BroadcastCount != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(12, this.l2BroadcastCount);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.durationSec = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.totalWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 24:
                        this.rootWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 32:
                        this.systemWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 40:
                        this.applicationWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.nonApplicationWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.noUidWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 66:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        int length = this.ethertypeCounts == null ? 0 : this.ethertypeCounts.length;
                        int i = repeatedFieldArrayLength + length;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.ethertypeCounts, 0, pairArr, 0, length);
                        }
                        while (length < i - 1) {
                            pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        pairArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr[length]);
                        this.ethertypeCounts = pairArr;
                        break;
                    case 74:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        int length2 = this.ipNextHeaderCounts == null ? 0 : this.ipNextHeaderCounts.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[i2];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.ipNextHeaderCounts, 0, pairArr2, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            pairArr2[length2] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                            codedInputByteBufferNano.readMessage(pairArr2[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        pairArr2[length2] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
                        codedInputByteBufferNano.readMessage(pairArr2[length2]);
                        this.ipNextHeaderCounts = pairArr2;
                        break;
                    case 80:
                        this.l2UnicastCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 88:
                        this.l2MulticastCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 96:
                        this.l2BroadcastCount = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpConnectivityEvent extends com.android.framework.protobuf.nano.MessageNano {
        public static final int APF_PROGRAM_EVENT_FIELD_NUMBER = 9;
        public static final int APF_STATISTICS_FIELD_NUMBER = 10;
        public static final int CONNECT_STATISTICS_FIELD_NUMBER = 14;
        public static final int DEFAULT_NETWORK_EVENT_FIELD_NUMBER = 2;
        public static final int DHCP_EVENT_FIELD_NUMBER = 6;
        public static final int DNS_LATENCIES_FIELD_NUMBER = 13;
        public static final int DNS_LOOKUP_BATCH_FIELD_NUMBER = 5;
        public static final int IP_PROVISIONING_EVENT_FIELD_NUMBER = 7;
        public static final int IP_REACHABILITY_EVENT_FIELD_NUMBER = 3;
        public static final int NETWORK_EVENT_FIELD_NUMBER = 4;
        public static final int NETWORK_STATS_FIELD_NUMBER = 19;
        public static final int RA_EVENT_FIELD_NUMBER = 11;
        public static final int VALIDATION_PROBE_EVENT_FIELD_NUMBER = 8;
        public static final int WAKEUP_STATS_FIELD_NUMBER = 20;
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[] _emptyArray;
        private int eventCase_ = 0;
        private java.lang.Object event_;
        public java.lang.String ifName;
        public int linkLayer;
        public int networkId;
        public long timeMs;
        public long transports;

        public int getEventCase() {
            return this.eventCase_;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent clearEvent() {
            this.eventCase_ = 0;
            this.event_ = null;
            return this;
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasDefaultNetworkEvent() {
            return this.eventCase_ == 2;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent getDefaultNetworkEvent() {
            if (this.eventCase_ == 2) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setDefaultNetworkEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent defaultNetworkEvent) {
            if (defaultNetworkEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 2;
            this.event_ = defaultNetworkEvent;
            return this;
        }

        public boolean hasIpReachabilityEvent() {
            return this.eventCase_ == 3;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent getIpReachabilityEvent() {
            if (this.eventCase_ == 3) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setIpReachabilityEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent ipReachabilityEvent) {
            if (ipReachabilityEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 3;
            this.event_ = ipReachabilityEvent;
            return this;
        }

        public boolean hasNetworkEvent() {
            return this.eventCase_ == 4;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent getNetworkEvent() {
            if (this.eventCase_ == 4) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setNetworkEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent networkEvent) {
            if (networkEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 4;
            this.event_ = networkEvent;
            return this;
        }

        public boolean hasDnsLookupBatch() {
            return this.eventCase_ == 5;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch getDnsLookupBatch() {
            if (this.eventCase_ == 5) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setDnsLookupBatch(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch dNSLookupBatch) {
            if (dNSLookupBatch == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 5;
            this.event_ = dNSLookupBatch;
            return this;
        }

        public boolean hasDnsLatencies() {
            return this.eventCase_ == 13;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies getDnsLatencies() {
            if (this.eventCase_ == 13) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setDnsLatencies(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies dNSLatencies) {
            if (dNSLatencies == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 13;
            this.event_ = dNSLatencies;
            return this;
        }

        public boolean hasConnectStatistics() {
            return this.eventCase_ == 14;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics getConnectStatistics() {
            if (this.eventCase_ == 14) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setConnectStatistics(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics connectStatistics) {
            if (connectStatistics == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 14;
            this.event_ = connectStatistics;
            return this;
        }

        public boolean hasDhcpEvent() {
            return this.eventCase_ == 6;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent getDhcpEvent() {
            if (this.eventCase_ == 6) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setDhcpEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent dHCPEvent) {
            if (dHCPEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 6;
            this.event_ = dHCPEvent;
            return this;
        }

        public boolean hasIpProvisioningEvent() {
            return this.eventCase_ == 7;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent getIpProvisioningEvent() {
            if (this.eventCase_ == 7) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setIpProvisioningEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent ipProvisioningEvent) {
            if (ipProvisioningEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 7;
            this.event_ = ipProvisioningEvent;
            return this;
        }

        public boolean hasValidationProbeEvent() {
            return this.eventCase_ == 8;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent getValidationProbeEvent() {
            if (this.eventCase_ == 8) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setValidationProbeEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent validationProbeEvent) {
            if (validationProbeEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 8;
            this.event_ = validationProbeEvent;
            return this;
        }

        public boolean hasApfProgramEvent() {
            return this.eventCase_ == 9;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent getApfProgramEvent() {
            if (this.eventCase_ == 9) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setApfProgramEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent apfProgramEvent) {
            if (apfProgramEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 9;
            this.event_ = apfProgramEvent;
            return this;
        }

        public boolean hasApfStatistics() {
            return this.eventCase_ == 10;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics getApfStatistics() {
            if (this.eventCase_ == 10) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setApfStatistics(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics apfStatistics) {
            if (apfStatistics == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 10;
            this.event_ = apfStatistics;
            return this;
        }

        public boolean hasRaEvent() {
            return this.eventCase_ == 11;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent getRaEvent() {
            if (this.eventCase_ == 11) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setRaEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent raEvent) {
            if (raEvent == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 11;
            this.event_ = raEvent;
            return this;
        }

        public boolean hasNetworkStats() {
            return this.eventCase_ == 19;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats getNetworkStats() {
            if (this.eventCase_ == 19) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setNetworkStats(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats networkStats) {
            if (networkStats == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 19;
            this.event_ = networkStats;
            return this;
        }

        public boolean hasWakeupStats() {
            return this.eventCase_ == 20;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats getWakeupStats() {
            if (this.eventCase_ == 20) {
                return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats) this.event_;
            }
            return null;
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent setWakeupStats(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats wakeupStats) {
            if (wakeupStats == null) {
                throw new java.lang.NullPointerException();
            }
            this.eventCase_ = 20;
            this.event_ = wakeupStats;
            return this;
        }

        public IpConnectivityEvent() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent clear() {
            this.timeMs = 0L;
            this.linkLayer = 0;
            this.networkId = 0;
            this.ifName = "";
            this.transports = 0L;
            clearEvent();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.timeMs != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.timeMs);
            }
            if (this.eventCase_ == 2) {
                codedOutputByteBufferNano.writeMessage(2, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 3) {
                codedOutputByteBufferNano.writeMessage(3, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 4) {
                codedOutputByteBufferNano.writeMessage(4, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 5) {
                codedOutputByteBufferNano.writeMessage(5, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 6) {
                codedOutputByteBufferNano.writeMessage(6, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 7) {
                codedOutputByteBufferNano.writeMessage(7, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 8) {
                codedOutputByteBufferNano.writeMessage(8, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 9) {
                codedOutputByteBufferNano.writeMessage(9, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 10) {
                codedOutputByteBufferNano.writeMessage(10, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 11) {
                codedOutputByteBufferNano.writeMessage(11, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 13) {
                codedOutputByteBufferNano.writeMessage(13, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 14) {
                codedOutputByteBufferNano.writeMessage(14, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.linkLayer != 0) {
                codedOutputByteBufferNano.writeInt32(15, this.linkLayer);
            }
            if (this.networkId != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.networkId);
            }
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.ifName);
            }
            if (this.transports != 0) {
                codedOutputByteBufferNano.writeInt64(18, this.transports);
            }
            if (this.eventCase_ == 19) {
                codedOutputByteBufferNano.writeMessage(19, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 20) {
                codedOutputByteBufferNano.writeMessage(20, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.timeMs != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.timeMs);
            }
            if (this.eventCase_ == 2) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(2, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 3) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(3, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 4) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(4, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 5) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(5, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 6) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(6, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 7) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(7, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 8) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(8, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 9) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(9, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 10) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(10, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 11) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(11, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 13) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(13, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 14) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(14, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.linkLayer != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(15, this.linkLayer);
            }
            if (this.networkId != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(16, this.networkId);
            }
            if (!this.ifName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(17, this.ifName);
            }
            if (this.transports != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(18, this.transports);
            }
            if (this.eventCase_ == 19) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(19, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            if (this.eventCase_ == 20) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(20, (com.android.framework.protobuf.nano.MessageNano) this.event_);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.timeMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 18:
                        if (this.eventCase_ != 2) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 2;
                        break;
                    case 26:
                        if (this.eventCase_ != 3) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 3;
                        break;
                    case 34:
                        if (this.eventCase_ != 4) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 4;
                        break;
                    case 42:
                        if (this.eventCase_ != 5) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 5;
                        break;
                    case 50:
                        if (this.eventCase_ != 6) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 6;
                        break;
                    case 58:
                        if (this.eventCase_ != 7) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 7;
                        break;
                    case 66:
                        if (this.eventCase_ != 8) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 8;
                        break;
                    case 74:
                        if (this.eventCase_ != 9) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 9;
                        break;
                    case 82:
                        if (this.eventCase_ != 10) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 10;
                        break;
                    case 90:
                        if (this.eventCase_ != 11) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 11;
                        break;
                    case 106:
                        if (this.eventCase_ != 13) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLatencies();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 13;
                        break;
                    case 114:
                        if (this.eventCase_ != 14) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 14;
                        break;
                    case 120:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                                this.linkLayer = readInt32;
                                break;
                        }
                    case 128:
                        this.networkId = codedInputByteBufferNano.readInt32();
                        break;
                    case 138:
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    case 144:
                        this.transports = codedInputByteBufferNano.readInt64();
                        break;
                    case 154:
                        if (this.eventCase_ != 19) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkStats();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 19;
                        break;
                    case 162:
                        if (this.eventCase_ != 20) {
                            this.event_ = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats();
                        }
                        codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                        this.eventCase_ = 20;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpConnectivityLog extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog[] _emptyArray;
        public int droppedEvents;
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[] events;
        public int version;

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpConnectivityLog() {
            clear();
        }

        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog clear() {
            this.events = com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent.emptyArray();
            this.droppedEvents = 0;
            this.version = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.events != null && this.events.length > 0) {
                for (int i = 0; i < this.events.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent = this.events[i];
                    if (ipConnectivityEvent != null) {
                        codedOutputByteBufferNano.writeMessage(1, ipConnectivityEvent);
                    }
                }
            }
            if (this.droppedEvents != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.droppedEvents);
            }
            if (this.version != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.version);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.events != null && this.events.length > 0) {
                for (int i = 0; i < this.events.length; i++) {
                    com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent = this.events[i];
                    if (ipConnectivityEvent != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, ipConnectivityEvent);
                    }
                }
            }
            if (this.droppedEvents != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.droppedEvents);
            }
            if (this.version != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.version);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        int length = this.events == null ? 0 : this.events.length;
                        int i = repeatedFieldArrayLength + length;
                        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[] ipConnectivityEventArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.events, 0, ipConnectivityEventArr, 0, length);
                        }
                        while (length < i - 1) {
                            ipConnectivityEventArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent();
                            codedInputByteBufferNano.readMessage(ipConnectivityEventArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        ipConnectivityEventArr[length] = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent();
                        codedInputByteBufferNano.readMessage(ipConnectivityEventArr[length]);
                        this.events = ipConnectivityEventArr;
                        break;
                    case 16:
                        this.droppedEvents = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.version = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog(), bArr);
        }

        public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog().mergeFrom(codedInputByteBufferNano);
        }
    }
}
