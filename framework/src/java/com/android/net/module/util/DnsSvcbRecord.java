package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class DnsSvcbRecord extends com.android.net.module.util.DnsPacket.DnsRecord {
    private static final int KEY_ALPN = 1;
    private static final int KEY_DOHPATH = 7;
    private static final int KEY_ECH = 5;
    private static final int KEY_IPV4HINT = 4;
    private static final int KEY_IPV6HINT = 6;
    private static final int KEY_MANDATORY = 0;
    private static final int KEY_NO_DEFAULT_ALPN = 2;
    private static final int KEY_PORT = 3;
    private static final int MINSVCPARAMSIZE = 4;
    private static final java.lang.String TAG = com.android.net.module.util.DnsSvcbRecord.class.getSimpleName();
    private final android.util.SparseArray<com.android.net.module.util.DnsSvcbRecord.SvcParam> mAllSvcParams;
    private final int mSvcPriority;
    private final java.lang.String mTargetName;

    public DnsSvcbRecord(int i, java.nio.ByteBuffer byteBuffer) throws java.lang.IllegalStateException, com.android.net.module.util.DnsPacket.ParseException {
        super(i, byteBuffer);
        this.mAllSvcParams = new android.util.SparseArray<>();
        if (this.nsType != 64) {
            throw new java.lang.IllegalStateException("incorrect nsType: " + this.nsType);
        }
        if (this.nsClass != 1) {
            throw new com.android.net.module.util.DnsPacket.ParseException("incorrect nsClass: " + this.nsClass);
        }
        if (i == 0) {
            this.mSvcPriority = 0;
            this.mTargetName = "";
            return;
        }
        byte[] rr = getRR();
        if (rr == null) {
            throw new com.android.net.module.util.DnsPacket.ParseException("SVCB rdata is empty");
        }
        java.nio.ByteBuffer asReadOnlyBuffer = java.nio.ByteBuffer.wrap(rr).asReadOnlyBuffer();
        this.mSvcPriority = java.lang.Short.toUnsignedInt(asReadOnlyBuffer.getShort());
        this.mTargetName = com.android.net.module.util.DnsPacketUtils.DnsRecordParser.parseName(asReadOnlyBuffer, 0, false);
        if (this.mTargetName.length() > 255) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Failed to parse SVCB target name, name size is too long: " + this.mTargetName.length());
        }
        while (asReadOnlyBuffer.remaining() >= 4) {
            com.android.net.module.util.DnsSvcbRecord.SvcParam parseSvcParam = parseSvcParam(asReadOnlyBuffer);
            int key = parseSvcParam.getKey();
            if (this.mAllSvcParams.get(key) != null) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Invalid DnsSvcbRecord, key " + key + " is repeated");
            }
            this.mAllSvcParams.put(key, parseSvcParam);
        }
        if (asReadOnlyBuffer.hasRemaining()) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Invalid DnsSvcbRecord. Got " + asReadOnlyBuffer.remaining() + " remaining bytes after parsing");
        }
    }

    public java.lang.String getTargetName() {
        return this.mTargetName;
    }

    public java.util.List<java.lang.String> getAlpns() {
        com.android.net.module.util.DnsSvcbRecord.SvcParamAlpn svcParamAlpn = (com.android.net.module.util.DnsSvcbRecord.SvcParamAlpn) this.mAllSvcParams.get(1);
        return java.util.Collections.unmodifiableList(svcParamAlpn != null ? svcParamAlpn.getValue() : java.util.Collections.EMPTY_LIST);
    }

    public int getPort() {
        com.android.net.module.util.DnsSvcbRecord.SvcParamPort svcParamPort = (com.android.net.module.util.DnsSvcbRecord.SvcParamPort) this.mAllSvcParams.get(3);
        if (svcParamPort != null) {
            return svcParamPort.getValue().intValue();
        }
        return -1;
    }

    public java.util.List<java.net.InetAddress> getAddresses() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint svcParamIpHint = (com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint) this.mAllSvcParams.get(4);
        if (svcParamIpHint != null) {
            arrayList.addAll(svcParamIpHint.getValue());
        }
        com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint svcParamIpHint2 = (com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint) this.mAllSvcParams.get(6);
        if (svcParamIpHint2 != null) {
            arrayList.addAll(svcParamIpHint2.getValue());
        }
        return arrayList;
    }

    public java.lang.String getDohPath() {
        com.android.net.module.util.DnsSvcbRecord.SvcParamDohPath svcParamDohPath = (com.android.net.module.util.DnsSvcbRecord.SvcParamDohPath) this.mAllSvcParams.get(7);
        return svcParamDohPath != null ? svcParamDohPath.getValue() : "";
    }

    @Override // com.android.net.module.util.DnsPacket.DnsRecord
    public java.lang.String toString() {
        if (this.rType == 0) {
            return this.dName + " IN SVCB";
        }
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(" ");
        for (int i = 0; i < this.mAllSvcParams.size(); i++) {
            stringJoiner.add(this.mAllSvcParams.valueAt(i).toString());
        }
        return this.dName + " " + this.ttl + " IN SVCB " + this.mSvcPriority + " " + this.mTargetName + " " + stringJoiner.toString();
    }

    private static com.android.net.module.util.DnsSvcbRecord.SvcParam parseSvcParam(java.nio.ByteBuffer byteBuffer) throws com.android.net.module.util.DnsPacket.ParseException {
        try {
            int unsignedInt = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            switch (unsignedInt) {
                case 0:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamMandatory(byteBuffer);
                case 1:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamAlpn(byteBuffer);
                case 2:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamNoDefaultAlpn(byteBuffer);
                case 3:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamPort(byteBuffer);
                case 4:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamIpv4Hint(byteBuffer);
                case 5:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamEch(byteBuffer);
                case 6:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamIpv6Hint(byteBuffer);
                case 7:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamDohPath(byteBuffer);
                default:
                    return new com.android.net.module.util.DnsSvcbRecord.SvcParamGeneric(unsignedInt, byteBuffer);
            }
        } catch (java.nio.BufferUnderflowException e) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Malformed packet", e);
        }
    }

    private static abstract class SvcParam<T> {
        private final int mKey;

        abstract T getValue();

        SvcParam(int i) {
            this.mKey = i;
        }

        int getKey() {
            return this.mKey;
        }
    }

    private static class SvcParamMandatory extends com.android.net.module.util.DnsSvcbRecord.SvcParam<short[]> {
        private final short[] mValue;

        private SvcParamMandatory(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(0);
            this.mValue = com.android.net.module.util.DnsSvcbRecord.SvcParamValueUtil.toShortArray(com.android.net.module.util.DnsSvcbRecord.sliceAndAdvance(byteBuffer, java.lang.Short.toUnsignedInt(byteBuffer.getShort())));
            if (this.mValue.length == 0) {
                throw new com.android.net.module.util.DnsPacket.ParseException("mandatory value must be non-empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public short[] getValue() {
            return null;
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
            for (short s : this.mValue) {
                stringJoiner.add(com.android.net.module.util.DnsSvcbRecord.toKeyName(s));
            }
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + "=" + stringJoiner.toString();
        }
    }

    private static class SvcParamAlpn extends com.android.net.module.util.DnsSvcbRecord.SvcParam<java.util.List<java.lang.String>> {
        private final java.util.List<java.lang.String> mValue;

        SvcParamAlpn(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(1);
            this.mValue = com.android.net.module.util.DnsSvcbRecord.SvcParamValueUtil.toStringList(com.android.net.module.util.DnsSvcbRecord.sliceAndAdvance(byteBuffer, java.lang.Short.toUnsignedInt(byteBuffer.getShort())));
            if (this.mValue.isEmpty()) {
                throw new com.android.net.module.util.DnsPacket.ParseException("alpn value must be non-empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public java.util.List<java.lang.String> getValue() {
            return java.util.Collections.unmodifiableList(this.mValue);
        }

        public java.lang.String toString() {
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + "=" + android.text.TextUtils.join(",", this.mValue);
        }
    }

    private static class SvcParamNoDefaultAlpn extends com.android.net.module.util.DnsSvcbRecord.SvcParam<java.lang.Void> {
        SvcParamNoDefaultAlpn(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(2);
            if (byteBuffer.getShort() != 0) {
                throw new com.android.net.module.util.DnsPacket.ParseException("no-default-alpn value must be empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public java.lang.Void getValue() {
            return null;
        }

        public java.lang.String toString() {
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey());
        }
    }

    private static class SvcParamPort extends com.android.net.module.util.DnsSvcbRecord.SvcParam<java.lang.Integer> {
        private final int mValue;

        SvcParamPort(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(3);
            short s = byteBuffer.getShort();
            if (s != 2) {
                throw new com.android.net.module.util.DnsPacket.ParseException("key port len is not 2 but " + ((int) s));
            }
            this.mValue = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public java.lang.Integer getValue() {
            return java.lang.Integer.valueOf(this.mValue);
        }

        public java.lang.String toString() {
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + "=" + this.mValue;
        }
    }

    private static class SvcParamIpHint extends com.android.net.module.util.DnsSvcbRecord.SvcParam<java.util.List<java.net.InetAddress>> {
        private final java.util.List<java.net.InetAddress> mValue;

        private SvcParamIpHint(int i, java.nio.ByteBuffer byteBuffer, int i2) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(i);
            this.mValue = com.android.net.module.util.DnsSvcbRecord.SvcParamValueUtil.toInetAddressList(com.android.net.module.util.DnsSvcbRecord.sliceAndAdvance(byteBuffer, java.lang.Short.toUnsignedInt(byteBuffer.getShort())), i2);
            if (this.mValue.isEmpty()) {
                throw new com.android.net.module.util.DnsPacket.ParseException(com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + " value must be non-empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public java.util.List<java.net.InetAddress> getValue() {
            return java.util.Collections.unmodifiableList(this.mValue);
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
            java.util.Iterator<java.net.InetAddress> it = this.mValue.iterator();
            while (it.hasNext()) {
                stringJoiner.add(it.next().getHostAddress());
            }
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + "=" + stringJoiner.toString();
        }
    }

    private static class SvcParamIpv4Hint extends com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        SvcParamIpv4Hint(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(r0, byteBuffer, r0);
            int i = 4;
        }
    }

    private static class SvcParamIpv6Hint extends com.android.net.module.util.DnsSvcbRecord.SvcParamIpHint {
        SvcParamIpv6Hint(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(6, byteBuffer, 16);
        }
    }

    private static class SvcParamEch extends com.android.net.module.util.DnsSvcbRecord.SvcParamGeneric {
        SvcParamEch(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(5, byteBuffer);
        }
    }

    private static class SvcParamDohPath extends com.android.net.module.util.DnsSvcbRecord.SvcParam<java.lang.String> {
        private final java.lang.String mValue;

        SvcParamDohPath(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(7);
            byte[] bArr = new byte[java.lang.Short.toUnsignedInt(byteBuffer.getShort())];
            byteBuffer.get(bArr);
            this.mValue = new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public java.lang.String getValue() {
            return this.mValue;
        }

        public java.lang.String toString() {
            return com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()) + "=" + this.mValue;
        }
    }

    private static class SvcParamGeneric extends com.android.net.module.util.DnsSvcbRecord.SvcParam<byte[]> {
        private final byte[] mValue;

        SvcParamGeneric(int i, java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            super(i);
            this.mValue = new byte[java.lang.Short.toUnsignedInt(byteBuffer.getShort())];
            byteBuffer.get(this.mValue);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public byte[] getValue() {
            return null;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.net.module.util.DnsSvcbRecord.toKeyName(getKey()));
            if (this.mValue != null && this.mValue.length > 0) {
                sb.append("=");
                sb.append(com.android.net.module.util.HexDump.toHexString(this.mValue));
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String toKeyName(int i) {
        switch (i) {
            case 0:
                return "mandatory";
            case 1:
                return "alpn";
            case 2:
                return "no-default-alpn";
            case 3:
                return "port";
            case 4:
                return "ipv4hint";
            case 5:
                return "ech";
            case 6:
                return "ipv6hint";
            case 7:
                return "dohpath";
            default:
                return "key" + i;
        }
    }

    public static java.nio.ByteBuffer sliceAndAdvance(java.nio.ByteBuffer byteBuffer, int i) throws java.nio.BufferUnderflowException {
        if (byteBuffer.remaining() < i) {
            throw new java.nio.BufferUnderflowException();
        }
        int position = byteBuffer.position();
        java.nio.ByteBuffer slice = ((java.nio.ByteBuffer) byteBuffer.slice().limit(i)).slice();
        byteBuffer.position(position + i);
        return slice.asReadOnlyBuffer();
    }

    private static class SvcParamValueUtil {
        private SvcParamValueUtil() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.util.List<java.lang.String> toStringList(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (byteBuffer.hasRemaining()) {
                int unsignedInt = java.lang.Byte.toUnsignedInt(byteBuffer.get());
                if (unsignedInt == 0) {
                    throw new com.android.net.module.util.DnsPacket.ParseException("alpn should not be an empty string");
                }
                byte[] bArr = new byte[unsignedInt];
                byteBuffer.get(bArr);
                arrayList.add(new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8));
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static short[] toShortArray(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            if (byteBuffer.remaining() % 2 != 0) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Can't parse whole byte array");
            }
            java.nio.ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            short[] sArr = new short[asShortBuffer.remaining()];
            asShortBuffer.get(sArr);
            return sArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.util.List<java.net.InetAddress> toInetAddressList(java.nio.ByteBuffer byteBuffer, int i) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            if (byteBuffer.remaining() % i != 0) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Can't parse whole byte array");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            byte[] bArr = new byte[i];
            while (byteBuffer.remaining() >= i) {
                byteBuffer.get(bArr);
                try {
                    arrayList.add(java.net.InetAddress.getByAddress(bArr));
                } catch (java.net.UnknownHostException e) {
                    throw new com.android.net.module.util.DnsPacket.ParseException("Can't parse byte array as an IP address");
                }
            }
            return arrayList;
        }
    }
}
