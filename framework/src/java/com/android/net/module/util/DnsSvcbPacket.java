package com.android.net.module.util;

/* loaded from: classes5.dex */
public class DnsSvcbPacket extends com.android.net.module.util.DnsPacket {
    private static final java.lang.String TAG = com.android.net.module.util.DnsSvcbPacket.class.getSimpleName();
    public static final int TYPE_SVCB = 64;

    private DnsSvcbPacket(byte[] bArr) throws com.android.net.module.util.DnsPacket.ParseException {
        super(bArr);
        int recordCount = this.mHeader.getRecordCount(0);
        if (recordCount != 1) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Unexpected question count " + recordCount);
        }
        int i = this.mRecords[0].get(0).nsType;
        if (i != 64) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Unexpected query type " + i);
        }
    }

    public boolean isResponse() {
        return this.mHeader.isResponse();
    }

    public boolean isSupported(java.lang.String str) {
        return findSvcbRecord(str) != null;
    }

    public java.lang.String getTargetName(java.lang.String str) {
        com.android.net.module.util.DnsSvcbRecord findSvcbRecord = findSvcbRecord(str);
        if (findSvcbRecord != null) {
            return findSvcbRecord.getTargetName();
        }
        return null;
    }

    public int getPort(java.lang.String str) {
        com.android.net.module.util.DnsSvcbRecord findSvcbRecord = findSvcbRecord(str);
        if (findSvcbRecord != null) {
            return findSvcbRecord.getPort();
        }
        return -1;
    }

    public java.util.List<java.net.InetAddress> getAddresses(java.lang.String str) {
        com.android.net.module.util.DnsSvcbRecord findSvcbRecord = findSvcbRecord(str);
        if (findSvcbRecord == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        java.util.List<java.net.InetAddress> addressesFromAdditionalSection = getAddressesFromAdditionalSection();
        return addressesFromAdditionalSection.size() > 0 ? addressesFromAdditionalSection : findSvcbRecord.getAddresses();
    }

    public java.lang.String getDohPath(java.lang.String str) {
        com.android.net.module.util.DnsSvcbRecord findSvcbRecord = findSvcbRecord(str);
        if (findSvcbRecord != null) {
            return findSvcbRecord.getDohPath();
        }
        return null;
    }

    private com.android.net.module.util.DnsSvcbRecord findSvcbRecord(java.lang.String str) {
        for (com.android.net.module.util.DnsPacket.DnsRecord dnsRecord : this.mRecords[1]) {
            if (dnsRecord instanceof com.android.net.module.util.DnsSvcbRecord) {
                com.android.net.module.util.DnsSvcbRecord dnsSvcbRecord = (com.android.net.module.util.DnsSvcbRecord) dnsRecord;
                if (dnsSvcbRecord.getAlpns().contains(str)) {
                    return dnsSvcbRecord;
                }
            }
        }
        return null;
    }

    private java.util.List<java.net.InetAddress> getAddressesFromAdditionalSection() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.mHeader.getRecordCount(3) == 0) {
            return arrayList;
        }
        for (com.android.net.module.util.DnsPacket.DnsRecord dnsRecord : this.mRecords[3]) {
            if (dnsRecord.nsType != 1 && dnsRecord.nsType != 28) {
                android.util.Log.d(TAG, "Found type other than A/AAAA in Additional section: " + dnsRecord.nsType);
            } else {
                try {
                    arrayList.add(java.net.InetAddress.getByAddress(dnsRecord.getRR()));
                } catch (java.net.UnknownHostException e) {
                    android.util.Log.w(TAG, "Failed to parse address");
                }
            }
        }
        return arrayList;
    }

    public static com.android.net.module.util.DnsSvcbPacket fromResponse(byte[] bArr) throws com.android.net.module.util.DnsPacket.ParseException {
        com.android.net.module.util.DnsSvcbPacket dnsSvcbPacket = new com.android.net.module.util.DnsSvcbPacket(bArr);
        if (!dnsSvcbPacket.isResponse()) {
            throw new com.android.net.module.util.DnsPacket.ParseException("Not an answer packet");
        }
        return dnsSvcbPacket;
    }
}
