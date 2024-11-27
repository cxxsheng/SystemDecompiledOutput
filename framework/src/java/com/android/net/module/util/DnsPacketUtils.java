package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class DnsPacketUtils {

    public static class DnsRecordParser {
        private static final int MAXLABELCOUNT = 128;
        private static final int MAXLABELSIZE = 63;
        private static final int MAXNAMESIZE = 255;
        private static final java.text.DecimalFormat sByteFormat = new java.text.DecimalFormat();
        private static final java.text.FieldPosition sPos = new java.text.FieldPosition(0);

        static java.lang.String labelToString(byte[] bArr) {
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
            for (byte b : bArr) {
                int unsignedInt = java.lang.Byte.toUnsignedInt(b);
                if (unsignedInt <= 32 || unsignedInt >= 127) {
                    stringBuffer.append('\\');
                    sByteFormat.format(unsignedInt, stringBuffer, sPos);
                } else if (unsignedInt == 34 || unsignedInt == 46 || unsignedInt == 59 || unsignedInt == 92 || unsignedInt == 40 || unsignedInt == 41 || unsignedInt == 64 || unsignedInt == 36) {
                    stringBuffer.append('\\');
                    stringBuffer.append((char) unsignedInt);
                } else {
                    stringBuffer.append((char) unsignedInt);
                }
            }
            return stringBuffer.toString();
        }

        public static byte[] domainNameToLabels(java.lang.String str) throws java.io.IOException, android.net.ParseException {
            if (str.length() > 255) {
                throw new android.net.ParseException("Domain name exceeds max length: " + str.length());
            }
            if (!isHostName(str)) {
                throw new android.net.ParseException("Failed to parse domain name: " + str);
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            for (java.lang.String str2 : str.split("\\.")) {
                if (str2.length() > 63) {
                    throw new android.net.ParseException("label is too long: " + str2);
                }
                byteArrayOutputStream.write(str2.length());
                byteArrayOutputStream.write(str2.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            byteArrayOutputStream.write(0);
            return byteArrayOutputStream.toByteArray();
        }

        public static boolean isHostName(java.lang.String str) {
            return (str == null || !android.util.Patterns.DOMAIN_NAME.matcher(str).matches() || android.net.InetAddresses.isNumericAddress(str)) ? false : true;
        }

        public static java.lang.String parseName(java.nio.ByteBuffer byteBuffer, int i, boolean z) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            return parseName(byteBuffer, i, 128, z);
        }

        public static java.lang.String parseName(java.nio.ByteBuffer byteBuffer, int i, int i2, boolean z) throws java.nio.BufferUnderflowException, com.android.net.module.util.DnsPacket.ParseException {
            if (i > i2) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Failed to parse name, too many labels");
            }
            int unsignedInt = java.lang.Byte.toUnsignedInt(byteBuffer.get());
            int i3 = unsignedInt & 192;
            if (unsignedInt == 0) {
                return "";
            }
            if ((i3 != 0 && i3 != 192) || (!z && i3 == 192)) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Parse name fail, bad label type: " + i3);
            }
            if (i3 == 192) {
                int unsignedInt2 = ((unsignedInt & (-193)) << 8) + java.lang.Byte.toUnsignedInt(byteBuffer.get());
                int position = byteBuffer.position();
                if (unsignedInt2 >= position - 2) {
                    throw new com.android.net.module.util.DnsPacket.ParseException("Parse compression name fail, invalid compression");
                }
                byteBuffer.position(unsignedInt2);
                java.lang.String parseName = parseName(byteBuffer, i + 1, i2, z);
                byteBuffer.position(position);
                return parseName;
            }
            byte[] bArr = new byte[unsignedInt];
            byteBuffer.get(bArr);
            java.lang.String labelToString = labelToString(bArr);
            if (labelToString.length() > 63) {
                throw new com.android.net.module.util.DnsPacket.ParseException("Parse name fail, invalid label length");
            }
            java.lang.String parseName2 = parseName(byteBuffer, i + 1, i2, z);
            return android.text.TextUtils.isEmpty(parseName2) ? labelToString : labelToString + android.media.MediaMetrics.SEPARATOR + parseName2;
        }

        private DnsRecordParser() {
        }
    }

    private DnsPacketUtils() {
    }
}
