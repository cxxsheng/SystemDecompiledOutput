package android.ota.nano;

/* loaded from: classes3.dex */
public interface OtaPackageMetadata {

    public static final class PartitionState extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile android.ota.nano.OtaPackageMetadata.PartitionState[] _emptyArray;
        public java.lang.String[] build;
        public java.lang.String[] device;
        public java.lang.String partitionName;
        public java.lang.String version;

        public static android.ota.nano.OtaPackageMetadata.PartitionState[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.ota.nano.OtaPackageMetadata.PartitionState[0];
                    }
                }
            }
            return _emptyArray;
        }

        public PartitionState() {
            clear();
        }

        public android.ota.nano.OtaPackageMetadata.PartitionState clear() {
            this.partitionName = "";
            this.device = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
            this.build = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
            this.version = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.partitionName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.partitionName);
            }
            if (this.device != null && this.device.length > 0) {
                for (int i = 0; i < this.device.length; i++) {
                    java.lang.String str = this.device[i];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(2, str);
                    }
                }
            }
            if (this.build != null && this.build.length > 0) {
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    java.lang.String str2 = this.build[i2];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(3, str2);
                    }
                }
            }
            if (!this.version.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.version);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.partitionName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.partitionName);
            }
            if (this.device != null && this.device.length > 0) {
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < this.device.length; i3++) {
                    java.lang.String str = this.device[i3];
                    if (str != null) {
                        i2++;
                        i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i + (i2 * 1);
            }
            if (this.build != null && this.build.length > 0) {
                int i4 = 0;
                int i5 = 0;
                for (int i6 = 0; i6 < this.build.length; i6++) {
                    java.lang.String str2 = this.build[i6];
                    if (str2 != null) {
                        i5++;
                        i4 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                }
                computeSerializedSize = computeSerializedSize + i4 + (i5 * 1);
            }
            if (!this.version.equals("")) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(4, this.version);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.ota.nano.OtaPackageMetadata.PartitionState mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.partitionName = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                        int length = this.device == null ? 0 : this.device.length;
                        int i = repeatedFieldArrayLength + length;
                        java.lang.String[] strArr = new java.lang.String[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.device, 0, strArr, 0, length);
                        }
                        while (length < i - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.device = strArr;
                        break;
                    case 26:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        int length2 = this.build == null ? 0 : this.build.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        java.lang.String[] strArr2 = new java.lang.String[i2];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.build, 0, strArr2, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            strArr2[length2] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        strArr2[length2] = codedInputByteBufferNano.readString();
                        this.build = strArr2;
                        break;
                    case 34:
                        this.version = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.ota.nano.OtaPackageMetadata.PartitionState parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.ota.nano.OtaPackageMetadata.PartitionState) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.ota.nano.OtaPackageMetadata.PartitionState(), bArr);
        }

        public static android.ota.nano.OtaPackageMetadata.PartitionState parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.ota.nano.OtaPackageMetadata.PartitionState().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DeviceState extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile android.ota.nano.OtaPackageMetadata.DeviceState[] _emptyArray;
        public java.lang.String[] build;
        public java.lang.String buildIncremental;
        public java.lang.String[] device;
        public android.ota.nano.OtaPackageMetadata.PartitionState[] partitionState;
        public java.lang.String sdkLevel;
        public java.lang.String securityPatchLevel;
        public long timestamp;

        public static android.ota.nano.OtaPackageMetadata.DeviceState[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.ota.nano.OtaPackageMetadata.DeviceState[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DeviceState() {
            clear();
        }

        public android.ota.nano.OtaPackageMetadata.DeviceState clear() {
            this.device = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
            this.build = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
            this.buildIncremental = "";
            this.timestamp = 0L;
            this.sdkLevel = "";
            this.securityPatchLevel = "";
            this.partitionState = android.ota.nano.OtaPackageMetadata.PartitionState.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.device != null && this.device.length > 0) {
                for (int i = 0; i < this.device.length; i++) {
                    java.lang.String str = this.device[i];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(1, str);
                    }
                }
            }
            if (this.build != null && this.build.length > 0) {
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    java.lang.String str2 = this.build[i2];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(2, str2);
                    }
                }
            }
            if (!this.buildIncremental.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.buildIncremental);
            }
            if (this.timestamp != 0) {
                codedOutputByteBufferNano.writeInt64(4, this.timestamp);
            }
            if (!this.sdkLevel.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.securityPatchLevel);
            }
            if (this.partitionState != null && this.partitionState.length > 0) {
                for (int i3 = 0; i3 < this.partitionState.length; i3++) {
                    android.ota.nano.OtaPackageMetadata.PartitionState partitionState = this.partitionState[i3];
                    if (partitionState != null) {
                        codedOutputByteBufferNano.writeMessage(7, partitionState);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.device != null && this.device.length > 0) {
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < this.device.length; i3++) {
                    java.lang.String str = this.device[i3];
                    if (str != null) {
                        i2++;
                        i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i + (i2 * 1);
            }
            if (this.build != null && this.build.length > 0) {
                int i4 = 0;
                int i5 = 0;
                for (int i6 = 0; i6 < this.build.length; i6++) {
                    java.lang.String str2 = this.build[i6];
                    if (str2 != null) {
                        i5++;
                        i4 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                }
                computeSerializedSize = computeSerializedSize + i4 + (i5 * 1);
            }
            if (!this.buildIncremental.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(3, this.buildIncremental);
            }
            if (this.timestamp != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(4, this.timestamp);
            }
            if (!this.sdkLevel.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(6, this.securityPatchLevel);
            }
            if (this.partitionState != null && this.partitionState.length > 0) {
                for (int i7 = 0; i7 < this.partitionState.length; i7++) {
                    android.ota.nano.OtaPackageMetadata.PartitionState partitionState = this.partitionState[i7];
                    if (partitionState != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(7, partitionState);
                    }
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.ota.nano.OtaPackageMetadata.DeviceState mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        int length = this.device == null ? 0 : this.device.length;
                        int i = repeatedFieldArrayLength + length;
                        java.lang.String[] strArr = new java.lang.String[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.device, 0, strArr, 0, length);
                        }
                        while (length < i - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.device = strArr;
                        break;
                    case 18:
                        int repeatedFieldArrayLength2 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                        int length2 = this.build == null ? 0 : this.build.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        java.lang.String[] strArr2 = new java.lang.String[i2];
                        if (length2 != 0) {
                            java.lang.System.arraycopy(this.build, 0, strArr2, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            strArr2[length2] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        strArr2[length2] = codedInputByteBufferNano.readString();
                        this.build = strArr2;
                        break;
                    case 26:
                        this.buildIncremental = codedInputByteBufferNano.readString();
                        break;
                    case 32:
                        this.timestamp = codedInputByteBufferNano.readInt64();
                        break;
                    case 42:
                        this.sdkLevel = codedInputByteBufferNano.readString();
                        break;
                    case 50:
                        this.securityPatchLevel = codedInputByteBufferNano.readString();
                        break;
                    case 58:
                        int repeatedFieldArrayLength3 = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                        int length3 = this.partitionState == null ? 0 : this.partitionState.length;
                        int i3 = repeatedFieldArrayLength3 + length3;
                        android.ota.nano.OtaPackageMetadata.PartitionState[] partitionStateArr = new android.ota.nano.OtaPackageMetadata.PartitionState[i3];
                        if (length3 != 0) {
                            java.lang.System.arraycopy(this.partitionState, 0, partitionStateArr, 0, length3);
                        }
                        while (length3 < i3 - 1) {
                            partitionStateArr[length3] = new android.ota.nano.OtaPackageMetadata.PartitionState();
                            codedInputByteBufferNano.readMessage(partitionStateArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        partitionStateArr[length3] = new android.ota.nano.OtaPackageMetadata.PartitionState();
                        codedInputByteBufferNano.readMessage(partitionStateArr[length3]);
                        this.partitionState = partitionStateArr;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.ota.nano.OtaPackageMetadata.DeviceState parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.ota.nano.OtaPackageMetadata.DeviceState) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.ota.nano.OtaPackageMetadata.DeviceState(), bArr);
        }

        public static android.ota.nano.OtaPackageMetadata.DeviceState parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.ota.nano.OtaPackageMetadata.DeviceState().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApexInfo extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile android.ota.nano.OtaPackageMetadata.ApexInfo[] _emptyArray;
        public long decompressedSize;
        public boolean isCompressed;
        public java.lang.String packageName;
        public long sourceVersion;
        public long version;

        public static android.ota.nano.OtaPackageMetadata.ApexInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.ota.nano.OtaPackageMetadata.ApexInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApexInfo() {
            clear();
        }

        public android.ota.nano.OtaPackageMetadata.ApexInfo clear() {
            this.packageName = "";
            this.version = 0L;
            this.isCompressed = false;
            this.decompressedSize = 0L;
            this.sourceVersion = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.packageName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.packageName);
            }
            if (this.version != 0) {
                codedOutputByteBufferNano.writeInt64(2, this.version);
            }
            if (this.isCompressed) {
                codedOutputByteBufferNano.writeBool(3, this.isCompressed);
            }
            if (this.decompressedSize != 0) {
                codedOutputByteBufferNano.writeInt64(4, this.decompressedSize);
            }
            if (this.sourceVersion != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.sourceVersion);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.packageName.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
            }
            if (this.version != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(2, this.version);
            }
            if (this.isCompressed) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(3, this.isCompressed);
            }
            if (this.decompressedSize != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(4, this.decompressedSize);
            }
            if (this.sourceVersion != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(5, this.sourceVersion);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.ota.nano.OtaPackageMetadata.ApexInfo mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.packageName = codedInputByteBufferNano.readString();
                        break;
                    case 16:
                        this.version = codedInputByteBufferNano.readInt64();
                        break;
                    case 24:
                        this.isCompressed = codedInputByteBufferNano.readBool();
                        break;
                    case 32:
                        this.decompressedSize = codedInputByteBufferNano.readInt64();
                        break;
                    case 40:
                        this.sourceVersion = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.ota.nano.OtaPackageMetadata.ApexInfo parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.ota.nano.OtaPackageMetadata.ApexInfo) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.ota.nano.OtaPackageMetadata.ApexInfo(), bArr);
        }

        public static android.ota.nano.OtaPackageMetadata.ApexInfo parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.ota.nano.OtaPackageMetadata.ApexInfo().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApexMetadata extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile android.ota.nano.OtaPackageMetadata.ApexMetadata[] _emptyArray;
        public android.ota.nano.OtaPackageMetadata.ApexInfo[] apexInfo;

        public static android.ota.nano.OtaPackageMetadata.ApexMetadata[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.ota.nano.OtaPackageMetadata.ApexMetadata[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApexMetadata() {
            clear();
        }

        public android.ota.nano.OtaPackageMetadata.ApexMetadata clear() {
            this.apexInfo = android.ota.nano.OtaPackageMetadata.ApexInfo.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.apexInfo != null && this.apexInfo.length > 0) {
                for (int i = 0; i < this.apexInfo.length; i++) {
                    android.ota.nano.OtaPackageMetadata.ApexInfo apexInfo = this.apexInfo[i];
                    if (apexInfo != null) {
                        codedOutputByteBufferNano.writeMessage(1, apexInfo);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.apexInfo != null && this.apexInfo.length > 0) {
                for (int i = 0; i < this.apexInfo.length; i++) {
                    android.ota.nano.OtaPackageMetadata.ApexInfo apexInfo = this.apexInfo[i];
                    if (apexInfo != null) {
                        computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, apexInfo);
                    }
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.ota.nano.OtaPackageMetadata.ApexMetadata mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        int length = this.apexInfo == null ? 0 : this.apexInfo.length;
                        int i = repeatedFieldArrayLength + length;
                        android.ota.nano.OtaPackageMetadata.ApexInfo[] apexInfoArr = new android.ota.nano.OtaPackageMetadata.ApexInfo[i];
                        if (length != 0) {
                            java.lang.System.arraycopy(this.apexInfo, 0, apexInfoArr, 0, length);
                        }
                        while (length < i - 1) {
                            apexInfoArr[length] = new android.ota.nano.OtaPackageMetadata.ApexInfo();
                            codedInputByteBufferNano.readMessage(apexInfoArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        apexInfoArr[length] = new android.ota.nano.OtaPackageMetadata.ApexInfo();
                        codedInputByteBufferNano.readMessage(apexInfoArr[length]);
                        this.apexInfo = apexInfoArr;
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.ota.nano.OtaPackageMetadata.ApexMetadata parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.ota.nano.OtaPackageMetadata.ApexMetadata) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.ota.nano.OtaPackageMetadata.ApexMetadata(), bArr);
        }

        public static android.ota.nano.OtaPackageMetadata.ApexMetadata parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.ota.nano.OtaPackageMetadata.ApexMetadata().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class OtaMetadata extends com.android.framework.protobuf.nano.MessageNano {
        public static final int AB = 1;
        public static final int BLOCK = 2;
        public static final int BRICK = 3;
        public static final int UNKNOWN = 0;
        private static volatile android.ota.nano.OtaPackageMetadata.OtaMetadata[] _emptyArray;
        public boolean downgrade;
        public android.ota.nano.OtaPackageMetadata.DeviceState postcondition;
        public android.ota.nano.OtaPackageMetadata.DeviceState precondition;
        public java.util.Map<java.lang.String, java.lang.String> propertyFiles;
        public long requiredCache;
        public boolean retrofitDynamicPartitions;
        public boolean splDowngrade;
        public int type;
        public boolean wipe;

        public static android.ota.nano.OtaPackageMetadata.OtaMetadata[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new android.ota.nano.OtaPackageMetadata.OtaMetadata[0];
                    }
                }
            }
            return _emptyArray;
        }

        public OtaMetadata() {
            clear();
        }

        public android.ota.nano.OtaPackageMetadata.OtaMetadata clear() {
            this.type = 0;
            this.wipe = false;
            this.downgrade = false;
            this.propertyFiles = null;
            this.precondition = null;
            this.postcondition = null;
            this.retrofitDynamicPartitions = false;
            this.requiredCache = 0L;
            this.splDowngrade = false;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.type != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.type);
            }
            if (this.wipe) {
                codedOutputByteBufferNano.writeBool(2, this.wipe);
            }
            if (this.downgrade) {
                codedOutputByteBufferNano.writeBool(3, this.downgrade);
            }
            if (this.propertyFiles != null) {
                com.android.framework.protobuf.nano.InternalNano.serializeMapField(codedOutputByteBufferNano, this.propertyFiles, 4, 9, 9);
            }
            if (this.precondition != null) {
                codedOutputByteBufferNano.writeMessage(5, this.precondition);
            }
            if (this.postcondition != null) {
                codedOutputByteBufferNano.writeMessage(6, this.postcondition);
            }
            if (this.retrofitDynamicPartitions) {
                codedOutputByteBufferNano.writeBool(7, this.retrofitDynamicPartitions);
            }
            if (this.requiredCache != 0) {
                codedOutputByteBufferNano.writeInt64(8, this.requiredCache);
            }
            if (this.splDowngrade) {
                codedOutputByteBufferNano.writeBool(9, this.splDowngrade);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.type != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.type);
            }
            if (this.wipe) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(2, this.wipe);
            }
            if (this.downgrade) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(3, this.downgrade);
            }
            if (this.propertyFiles != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.InternalNano.computeMapFieldSize(this.propertyFiles, 4, 9, 9);
            }
            if (this.precondition != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(5, this.precondition);
            }
            if (this.postcondition != null) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(6, this.postcondition);
            }
            if (this.retrofitDynamicPartitions) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(7, this.retrofitDynamicPartitions);
            }
            if (this.requiredCache != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(8, this.requiredCache);
            }
            if (this.splDowngrade) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(9, this.splDowngrade);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public android.ota.nano.OtaPackageMetadata.OtaMetadata mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            com.android.framework.protobuf.nano.MapFactories.MapFactory mapFactory = com.android.framework.protobuf.nano.MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.type = readInt32;
                                break;
                        }
                    case 16:
                        this.wipe = codedInputByteBufferNano.readBool();
                        break;
                    case 24:
                        this.downgrade = codedInputByteBufferNano.readBool();
                        break;
                    case 34:
                        this.propertyFiles = com.android.framework.protobuf.nano.InternalNano.mergeMapEntry(codedInputByteBufferNano, this.propertyFiles, mapFactory, 9, 9, null, 10, 18);
                        break;
                    case 42:
                        if (this.precondition == null) {
                            this.precondition = new android.ota.nano.OtaPackageMetadata.DeviceState();
                        }
                        codedInputByteBufferNano.readMessage(this.precondition);
                        break;
                    case 50:
                        if (this.postcondition == null) {
                            this.postcondition = new android.ota.nano.OtaPackageMetadata.DeviceState();
                        }
                        codedInputByteBufferNano.readMessage(this.postcondition);
                        break;
                    case 56:
                        this.retrofitDynamicPartitions = codedInputByteBufferNano.readBool();
                        break;
                    case 64:
                        this.requiredCache = codedInputByteBufferNano.readInt64();
                        break;
                    case 72:
                        this.splDowngrade = codedInputByteBufferNano.readBool();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static android.ota.nano.OtaPackageMetadata.OtaMetadata parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (android.ota.nano.OtaPackageMetadata.OtaMetadata) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.ota.nano.OtaPackageMetadata.OtaMetadata(), bArr);
        }

        public static android.ota.nano.OtaPackageMetadata.OtaMetadata parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new android.ota.nano.OtaPackageMetadata.OtaMetadata().mergeFrom(codedInputByteBufferNano);
        }
    }
}
