package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class SmsHeader {
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_16_BIT = 5;
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_8_BIT = 4;
    public static final int ELT_ID_CHARACTER_SIZE_WVG_OBJECT = 25;
    public static final int ELT_ID_COMPRESSION_CONTROL = 22;
    public static final int ELT_ID_CONCATENATED_16_BIT_REFERENCE = 8;
    public static final int ELT_ID_CONCATENATED_8_BIT_REFERENCE = 0;
    public static final int ELT_ID_ENHANCED_VOICE_MAIL_INFORMATION = 35;
    public static final int ELT_ID_EXTENDED_OBJECT = 20;
    public static final int ELT_ID_EXTENDED_OBJECT_DATA_REQUEST_CMD = 26;
    public static final int ELT_ID_HYPERLINK_FORMAT_ELEMENT = 33;
    public static final int ELT_ID_LARGE_ANIMATION = 14;
    public static final int ELT_ID_LARGE_PICTURE = 16;
    public static final int ELT_ID_NATIONAL_LANGUAGE_LOCKING_SHIFT = 37;
    public static final int ELT_ID_NATIONAL_LANGUAGE_SINGLE_SHIFT = 36;
    public static final int ELT_ID_OBJECT_DISTR_INDICATOR = 23;
    public static final int ELT_ID_PREDEFINED_ANIMATION = 13;
    public static final int ELT_ID_PREDEFINED_SOUND = 11;
    public static final int ELT_ID_REPLY_ADDRESS_ELEMENT = 34;
    public static final int ELT_ID_REUSED_EXTENDED_OBJECT = 21;
    public static final int ELT_ID_RFC_822_EMAIL_HEADER = 32;
    public static final int ELT_ID_SMALL_ANIMATION = 15;
    public static final int ELT_ID_SMALL_PICTURE = 17;
    public static final int ELT_ID_SMSC_CONTROL_PARAMS = 6;
    public static final int ELT_ID_SPECIAL_SMS_MESSAGE_INDICATION = 1;
    public static final int ELT_ID_STANDARD_WVG_OBJECT = 24;
    public static final int ELT_ID_TEXT_FORMATTING = 10;
    public static final int ELT_ID_UDH_SOURCE_INDICATION = 7;
    public static final int ELT_ID_USER_DEFINED_SOUND = 12;
    public static final int ELT_ID_USER_PROMPT_INDICATOR = 19;
    public static final int ELT_ID_VARIABLE_PICTURE = 18;
    public static final int ELT_ID_WIRELESS_CTRL_MSG_PROTOCOL = 9;
    public static final int PORT_WAP_PUSH = 2948;
    public static final int PORT_WAP_WSP = 9200;
    public com.android.internal.telephony.SmsHeader.ConcatRef concatRef;
    public int languageShiftTable;
    public int languageTable;
    public com.android.internal.telephony.SmsHeader.PortAddrs portAddrs;
    public java.util.ArrayList<com.android.internal.telephony.SmsHeader.SpecialSmsMsg> specialSmsMsgList = new java.util.ArrayList<>();
    public java.util.ArrayList<com.android.internal.telephony.SmsHeader.MiscElt> miscEltList = new java.util.ArrayList<>();

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.internal.telephony.SmsHeader smsHeader = (com.android.internal.telephony.SmsHeader) obj;
        if (this.languageTable == smsHeader.languageTable && this.languageShiftTable == smsHeader.languageShiftTable && java.util.Objects.equals(this.portAddrs, smsHeader.portAddrs) && java.util.Objects.equals(this.concatRef, smsHeader.concatRef) && java.util.Objects.equals(this.specialSmsMsgList, smsHeader.specialSmsMsgList) && java.util.Objects.equals(this.miscEltList, smsHeader.miscEltList)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.portAddrs, this.concatRef, this.specialSmsMsgList, this.miscEltList, java.lang.Integer.valueOf(this.languageTable), java.lang.Integer.valueOf(this.languageShiftTable));
    }

    public static class PortAddrs {
        public boolean areEightBits;
        public int destPort;
        public int origPort;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.telephony.SmsHeader.PortAddrs portAddrs = (com.android.internal.telephony.SmsHeader.PortAddrs) obj;
            if (this.destPort == portAddrs.destPort && this.origPort == portAddrs.origPort && this.areEightBits == portAddrs.areEightBits) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.destPort), java.lang.Integer.valueOf(this.origPort), java.lang.Boolean.valueOf(this.areEightBits));
        }
    }

    public static class ConcatRef {
        public boolean isEightBits;
        public int msgCount;
        public int refNumber;
        public int seqNumber;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.telephony.SmsHeader.ConcatRef concatRef = (com.android.internal.telephony.SmsHeader.ConcatRef) obj;
            if (this.refNumber == concatRef.refNumber && this.seqNumber == concatRef.seqNumber && this.msgCount == concatRef.msgCount && this.isEightBits == concatRef.isEightBits) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.refNumber), java.lang.Integer.valueOf(this.seqNumber), java.lang.Integer.valueOf(this.msgCount), java.lang.Boolean.valueOf(this.isEightBits));
        }
    }

    public static class SpecialSmsMsg {
        public int msgCount;
        public int msgIndType;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.telephony.SmsHeader.SpecialSmsMsg specialSmsMsg = (com.android.internal.telephony.SmsHeader.SpecialSmsMsg) obj;
            if (this.msgIndType == specialSmsMsg.msgIndType && this.msgCount == specialSmsMsg.msgCount) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.msgIndType), java.lang.Integer.valueOf(this.msgCount));
        }
    }

    public static class MiscElt {
        public byte[] data;
        public int id;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.telephony.SmsHeader.MiscElt miscElt = (com.android.internal.telephony.SmsHeader.MiscElt) obj;
            if (this.id == miscElt.id && java.util.Arrays.equals(this.data, miscElt.data)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (java.util.Objects.hash(java.lang.Integer.valueOf(this.id)) * 31) + java.util.Arrays.hashCode(this.data);
        }
    }

    public static com.android.internal.telephony.SmsHeader fromByteArray(byte[] bArr) {
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
        while (byteArrayInputStream.available() > 0) {
            int read = byteArrayInputStream.read();
            int read2 = byteArrayInputStream.read();
            switch (read) {
                case 0:
                    com.android.internal.telephony.SmsHeader.ConcatRef concatRef = new com.android.internal.telephony.SmsHeader.ConcatRef();
                    concatRef.refNumber = byteArrayInputStream.read();
                    concatRef.msgCount = byteArrayInputStream.read();
                    concatRef.seqNumber = byteArrayInputStream.read();
                    concatRef.isEightBits = true;
                    if (concatRef.msgCount != 0 && concatRef.seqNumber != 0 && concatRef.seqNumber <= concatRef.msgCount) {
                        smsHeader.concatRef = concatRef;
                        break;
                    }
                    break;
                case 1:
                    com.android.internal.telephony.SmsHeader.SpecialSmsMsg specialSmsMsg = new com.android.internal.telephony.SmsHeader.SpecialSmsMsg();
                    specialSmsMsg.msgIndType = byteArrayInputStream.read();
                    specialSmsMsg.msgCount = byteArrayInputStream.read();
                    smsHeader.specialSmsMsgList.add(specialSmsMsg);
                    break;
                case 4:
                    com.android.internal.telephony.SmsHeader.PortAddrs portAddrs = new com.android.internal.telephony.SmsHeader.PortAddrs();
                    portAddrs.destPort = byteArrayInputStream.read();
                    portAddrs.origPort = byteArrayInputStream.read();
                    portAddrs.areEightBits = true;
                    smsHeader.portAddrs = portAddrs;
                    break;
                case 5:
                    com.android.internal.telephony.SmsHeader.PortAddrs portAddrs2 = new com.android.internal.telephony.SmsHeader.PortAddrs();
                    portAddrs2.destPort = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    portAddrs2.origPort = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    portAddrs2.areEightBits = false;
                    smsHeader.portAddrs = portAddrs2;
                    break;
                case 8:
                    com.android.internal.telephony.SmsHeader.ConcatRef concatRef2 = new com.android.internal.telephony.SmsHeader.ConcatRef();
                    concatRef2.refNumber = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    concatRef2.msgCount = byteArrayInputStream.read();
                    concatRef2.seqNumber = byteArrayInputStream.read();
                    concatRef2.isEightBits = false;
                    if (concatRef2.msgCount != 0 && concatRef2.seqNumber != 0 && concatRef2.seqNumber <= concatRef2.msgCount) {
                        smsHeader.concatRef = concatRef2;
                        break;
                    }
                    break;
                case 36:
                    smsHeader.languageShiftTable = byteArrayInputStream.read();
                    break;
                case 37:
                    smsHeader.languageTable = byteArrayInputStream.read();
                    break;
                default:
                    com.android.internal.telephony.SmsHeader.MiscElt miscElt = new com.android.internal.telephony.SmsHeader.MiscElt();
                    miscElt.id = read;
                    miscElt.data = new byte[read2];
                    byteArrayInputStream.read(miscElt.data, 0, read2);
                    smsHeader.miscEltList.add(miscElt);
                    break;
            }
        }
        return smsHeader;
    }

    public static byte[] toByteArray(com.android.internal.telephony.SmsHeader smsHeader) {
        if (smsHeader.portAddrs == null && smsHeader.concatRef == null && smsHeader.specialSmsMsgList.isEmpty() && smsHeader.miscEltList.isEmpty() && smsHeader.languageShiftTable == 0 && smsHeader.languageTable == 0) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(140);
        com.android.internal.telephony.SmsHeader.ConcatRef concatRef = smsHeader.concatRef;
        if (concatRef != null) {
            if (concatRef.isEightBits) {
                byteArrayOutputStream.write(0);
                byteArrayOutputStream.write(3);
                byteArrayOutputStream.write(concatRef.refNumber);
            } else {
                byteArrayOutputStream.write(8);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(concatRef.refNumber >>> 8);
                byteArrayOutputStream.write(concatRef.refNumber & 255);
            }
            byteArrayOutputStream.write(concatRef.msgCount);
            byteArrayOutputStream.write(concatRef.seqNumber);
        }
        com.android.internal.telephony.SmsHeader.PortAddrs portAddrs = smsHeader.portAddrs;
        if (portAddrs != null) {
            if (portAddrs.areEightBits) {
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(2);
                byteArrayOutputStream.write(portAddrs.destPort);
                byteArrayOutputStream.write(portAddrs.origPort);
            } else {
                byteArrayOutputStream.write(5);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(portAddrs.destPort >>> 8);
                byteArrayOutputStream.write(portAddrs.destPort & 255);
                byteArrayOutputStream.write(portAddrs.origPort >>> 8);
                byteArrayOutputStream.write(portAddrs.origPort & 255);
            }
        }
        if (smsHeader.languageShiftTable != 0) {
            byteArrayOutputStream.write(36);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(smsHeader.languageShiftTable);
        }
        if (smsHeader.languageTable != 0) {
            byteArrayOutputStream.write(37);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(smsHeader.languageTable);
        }
        java.util.Iterator<com.android.internal.telephony.SmsHeader.SpecialSmsMsg> it = smsHeader.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            com.android.internal.telephony.SmsHeader.SpecialSmsMsg next = it.next();
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(2);
            byteArrayOutputStream.write(next.msgIndType & 255);
            byteArrayOutputStream.write(next.msgCount & 255);
        }
        java.util.Iterator<com.android.internal.telephony.SmsHeader.MiscElt> it2 = smsHeader.miscEltList.iterator();
        while (it2.hasNext()) {
            com.android.internal.telephony.SmsHeader.MiscElt next2 = it2.next();
            byteArrayOutputStream.write(next2.id);
            byteArrayOutputStream.write(next2.data.length);
            byteArrayOutputStream.write(next2.data, 0, next2.data.length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UserDataHeader ");
        sb.append("{ ConcatRef ");
        if (this.concatRef == null) {
            sb.append("unset");
        } else {
            sb.append("{ refNumber=" + this.concatRef.refNumber);
            sb.append(", msgCount=" + this.concatRef.msgCount);
            sb.append(", seqNumber=" + this.concatRef.seqNumber);
            sb.append(", isEightBits=" + this.concatRef.isEightBits);
            sb.append(" }");
        }
        sb.append(", PortAddrs ");
        if (this.portAddrs == null) {
            sb.append("unset");
        } else {
            sb.append("{ destPort=" + this.portAddrs.destPort);
            sb.append(", origPort=" + this.portAddrs.origPort);
            sb.append(", areEightBits=" + this.portAddrs.areEightBits);
            sb.append(" }");
        }
        if (this.languageShiftTable != 0) {
            sb.append(", languageShiftTable=" + this.languageShiftTable);
        }
        if (this.languageTable != 0) {
            sb.append(", languageTable=" + this.languageTable);
        }
        java.util.Iterator<com.android.internal.telephony.SmsHeader.SpecialSmsMsg> it = this.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            com.android.internal.telephony.SmsHeader.SpecialSmsMsg next = it.next();
            sb.append(", SpecialSmsMsg ");
            sb.append("{ msgIndType=" + next.msgIndType);
            sb.append(", msgCount=" + next.msgCount);
            sb.append(" }");
        }
        java.util.Iterator<com.android.internal.telephony.SmsHeader.MiscElt> it2 = this.miscEltList.iterator();
        while (it2.hasNext()) {
            com.android.internal.telephony.SmsHeader.MiscElt next2 = it2.next();
            sb.append(", MiscElt ");
            sb.append("{ id=" + next2.id);
            sb.append(", length=" + next2.data.length);
            sb.append(", data=" + com.android.internal.util.HexDump.toHexString(next2.data));
            sb.append(" }");
        }
        sb.append(" }");
        return sb.toString();
    }
}
