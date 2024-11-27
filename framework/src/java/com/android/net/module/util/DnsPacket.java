package com.android.net.module.util;

/* loaded from: classes5.dex */
public abstract class DnsPacket {
    public static final int ANSECTION = 1;
    public static final int ARSECTION = 3;
    public static final int NSSECTION = 2;
    static final int NUM_SECTIONS = 4;
    public static final int QDSECTION = 0;
    private static final java.lang.String TAG = com.android.net.module.util.DnsPacket.class.getSimpleName();
    private static final int TYPE_CNAME = 5;
    public static final int TYPE_SVCB = 64;
    protected final com.android.net.module.util.DnsPacket.DnsHeader mHeader;
    protected final java.util.List<com.android.net.module.util.DnsPacket.DnsRecord>[] mRecords;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecordType {
    }

    public static class ParseException extends java.lang.RuntimeException {
        public java.lang.String reason;

        public ParseException(java.lang.String str) {
            super(str);
            this.reason = str;
        }

        public ParseException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
            this.reason = str;
        }
    }

    public static class DnsHeader {
        private static final int FLAGS_SECTION_QR_BIT = 15;
        private static final int SIZE_IN_BYTES = 12;
        private static final java.lang.String TAG = "DnsHeader";
        private final int mFlags;
        private final int mId;
        private final int[] mRecordCount;

        public DnsHeader(java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException {
            java.util.Objects.requireNonNull(byteBuffer);
            this.mId = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            this.mFlags = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            this.mRecordCount = new int[4];
            for (int i = 0; i < 4; i++) {
                this.mRecordCount[i] = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            }
        }

        public boolean isResponse() {
            return (this.mFlags & 32768) != 0;
        }

        public DnsHeader(int i, int i2, int i3, int i4) {
            this.mId = i;
            this.mFlags = i2;
            this.mRecordCount = new int[4];
            this.mRecordCount[0] = i3;
            this.mRecordCount[1] = i4;
        }

        public int getRecordCount(int i) {
            return this.mRecordCount[i];
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String toString() {
            return "DnsHeader{id=" + this.mId + ", flags=" + this.mFlags + ", recordCounts=" + java.util.Arrays.toString(this.mRecordCount) + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            com.android.net.module.util.DnsPacket.DnsHeader dnsHeader = (com.android.net.module.util.DnsPacket.DnsHeader) obj;
            return this.mId == dnsHeader.mId && this.mFlags == dnsHeader.mFlags && java.util.Arrays.equals(this.mRecordCount, dnsHeader.mRecordCount);
        }

        public int hashCode() {
            return (this.mId * 31) + (this.mFlags * 37) + java.util.Arrays.hashCode(this.mRecordCount);
        }

        public byte[] getBytes() {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(12);
            allocate.putShort((short) this.mId);
            allocate.putShort((short) this.mFlags);
            for (int i = 0; i < 4; i++) {
                allocate.putShort((short) this.mRecordCount[i]);
            }
            return allocate.array();
        }
    }

    public static class DnsRecord {
        public static final int MAXNAMESIZE = 255;
        public static final int NAME_COMPRESSION = 192;
        public static final int NAME_NORMAL = 0;
        private static final java.lang.String TAG = "DnsRecord";
        public final java.lang.String dName;
        private final byte[] mRdata;
        public final int nsClass;
        public final int nsType;
        public final int rType;
        public final long ttl;

        protected DnsRecord(int i, java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            java.util.Objects.requireNonNull(byteBuffer);
            this.rType = i;
            this.dName = com.android.net.module.util.DnsPacketUtils.DnsRecordParser.parseName(byteBuffer, 0, true);
            if (this.dName.length() > 255) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Parse name fail, name size is too long: " + this.dName.length());
            }
            this.nsType = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            this.nsClass = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            if (i != 0) {
                this.ttl = java.lang.Integer.toUnsignedLong(byteBuffer.getInt());
                this.mRdata = new byte[java.lang.Short.toUnsignedInt(byteBuffer.getShort())];
                byteBuffer.get(this.mRdata);
            } else {
                this.ttl = 0L;
                this.mRdata = null;
            }
        }

        public static com.android.net.module.util.DnsPacket.DnsRecord parse(int i, java.nio.ByteBuffer byteBuffer) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            java.util.Objects.requireNonNull(byteBuffer);
            int position = byteBuffer.position();
            com.android.net.module.util.DnsPacketUtils.DnsRecordParser.parseName(byteBuffer, 0, true);
            int unsignedInt = java.lang.Short.toUnsignedInt(byteBuffer.getShort());
            byteBuffer.position(position);
            switch (unsignedInt) {
                case 64:
                    return new com.android.net.module.util.DnsSvcbRecord(i, byteBuffer);
                default:
                    return new com.android.net.module.util.DnsPacket.DnsRecord(i, byteBuffer);
            }
        }

        public static com.android.net.module.util.DnsPacket.DnsRecord makeAOrAAAARecord(int i, java.lang.String str, int i2, long j, java.net.InetAddress inetAddress) throws java.io.IOException {
            return new com.android.net.module.util.DnsPacket.DnsRecord(i, str, inetAddress.getAddress().length == 4 ? 1 : 28, i2, j, inetAddress, null);
        }

        public static com.android.net.module.util.DnsPacket.DnsRecord makeCNameRecord(int i, java.lang.String str, int i2, long j, java.lang.String str2) throws java.io.IOException {
            return new com.android.net.module.util.DnsPacket.DnsRecord(i, str, 5, i2, j, null, str2);
        }

        public static com.android.net.module.util.DnsPacket.DnsRecord makeQuestion(java.lang.String str, int i, int i2) {
            return new com.android.net.module.util.DnsPacket.DnsRecord(str, i, i2);
        }

        private static java.lang.String requireHostName(java.lang.String str) {
            if (!com.android.net.module.util.DnsPacketUtils.DnsRecordParser.isHostName(str)) {
                throw new java.lang.IllegalArgumentException("Expected domain name but got " + str);
            }
            return str;
        }

        private DnsRecord(java.lang.String str, int i, int i2) {
            this.rType = 0;
            this.dName = requireHostName(str);
            this.nsType = i;
            this.nsClass = i2;
            this.mRdata = null;
            this.ttl = 0L;
        }

        private DnsRecord(int i, java.lang.String str, int i2, int i3, long j, java.net.InetAddress inetAddress, java.lang.String str2) throws java.io.IOException {
            this.rType = i;
            this.dName = requireHostName(str);
            this.nsType = i2;
            this.nsClass = i3;
            if (i < 0 || i >= 4 || i == 0) {
                throw new java.lang.IllegalArgumentException("Unexpected record type: " + i);
            }
            this.mRdata = i2 == 5 ? com.android.net.module.util.DnsPacketUtils.DnsRecordParser.domainNameToLabels(str2) : inetAddress.getAddress();
            this.ttl = j;
        }

        public byte[] getRR() {
            if (this.mRdata == null) {
                return null;
            }
            return (byte[]) this.mRdata.clone();
        }

        public byte[] getBytes() throws java.io.IOException {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            dataOutputStream.write(com.android.net.module.util.DnsPacketUtils.DnsRecordParser.domainNameToLabels(this.dName));
            dataOutputStream.writeShort(this.nsType);
            dataOutputStream.writeShort(this.nsClass);
            if (this.rType != 0) {
                dataOutputStream.writeInt((int) this.ttl);
                if (this.mRdata == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(this.mRdata.length);
                    dataOutputStream.write(this.mRdata);
                }
            }
            return byteArrayOutputStream.toByteArray();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            com.android.net.module.util.DnsPacket.DnsRecord dnsRecord = (com.android.net.module.util.DnsPacket.DnsRecord) obj;
            return this.rType == dnsRecord.rType && this.nsType == dnsRecord.nsType && this.nsClass == dnsRecord.nsClass && this.ttl == dnsRecord.ttl && android.text.TextUtils.equals(this.dName, dnsRecord.dName) && java.util.Arrays.equals(this.mRdata, dnsRecord.mRdata);
        }

        public int hashCode() {
            return (java.util.Objects.hash(this.dName) * 31) + (((int) (this.ttl & (-1))) * 37) + (((int) (this.ttl >> 32)) * 41) + (this.nsType * 43) + (this.nsClass * 47) + (this.rType * 53) + java.util.Arrays.hashCode(this.mRdata);
        }

        public java.lang.String toString() {
            return "DnsRecord{rType=" + this.rType + ", dName='" + this.dName + android.text.format.DateFormat.QUOTE + ", nsType=" + this.nsType + ", nsClass=" + this.nsClass + ", ttl=" + this.ttl + ", mRdata=" + java.util.Arrays.toString(this.mRdata) + '}';
        }
    }

    protected DnsPacket(byte[] bArr) throws com.android.net.module.util.DnsPacket.ParseException {
        if (bArr == null) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Parse header failed, null input data");
        }
        try {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
            this.mHeader = new com.android.net.module.util.DnsPacket.DnsHeader(wrap);
            this.mRecords = new java.util.ArrayList[4];
            for (int i = 0; i < 4; i++) {
                int recordCount = this.mHeader.getRecordCount(i);
                this.mRecords[i] = new java.util.ArrayList(recordCount);
                for (int i2 = 0; i2 < recordCount; i2++) {
                    try {
                        this.mRecords[i].add(com.android.net.module.util.DnsPacket.DnsRecord.parse(i, wrap));
                    } catch (java.nio.BufferUnderflowException e) {
                        throw new com.android.net.module.util.DnsPacket.ParseException("Parse record fail", e);
                    }
                }
            }
        } catch (java.nio.BufferUnderflowException e2) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Parse Header fail, bad input data", e2);
        }
    }

    protected DnsPacket(com.android.net.module.util.DnsPacket.DnsHeader dnsHeader, java.util.List<com.android.net.module.util.DnsPacket.DnsRecord> list, java.util.List<com.android.net.module.util.DnsPacket.DnsRecord> list2) {
        this.mHeader = (com.android.net.module.util.DnsPacket.DnsHeader) java.util.Objects.requireNonNull(dnsHeader);
        this.mRecords = new java.util.List[4];
        this.mRecords[0] = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        this.mRecords[1] = java.util.Collections.unmodifiableList(new java.util.ArrayList(list2));
        this.mRecords[2] = new java.util.ArrayList();
        this.mRecords[3] = new java.util.ArrayList();
        for (int i = 0; i < 4; i++) {
            if (this.mHeader.mRecordCount[i] != this.mRecords[i].size()) {
                throw new java.lang.IllegalArgumentException("Record count mismatch: expected " + this.mHeader.mRecordCount[i] + " but was " + this.mRecords[i]);
            }
        }
    }

    public byte[] getBytes() throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        byteArrayOutputStream.write(this.mHeader.getBytes());
        for (int i = 0; i < 4; i++) {
            java.util.Iterator<com.android.net.module.util.DnsPacket.DnsRecord> it = this.mRecords[i].iterator();
            while (it.hasNext()) {
                byteArrayOutputStream.write(it.next().getBytes());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public java.lang.String toString() {
        return "DnsPacket{header=" + this.mHeader + ", records='" + java.util.Arrays.toString(this.mRecords) + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        com.android.net.module.util.DnsPacket dnsPacket = (com.android.net.module.util.DnsPacket) obj;
        return java.util.Objects.equals(this.mHeader, dnsPacket.mHeader) && java.util.Arrays.deepEquals(this.mRecords, dnsPacket.mRecords);
    }

    public int hashCode() {
        return (java.util.Objects.hash(this.mHeader) * 31) + java.util.Arrays.hashCode(this.mRecords);
    }
}
