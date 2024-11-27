package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class PduParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final int END_STRING_FLAG = 0;
    private static final int LENGTH_QUOTE = 31;
    private static final boolean LOCAL_LOGV = false;
    private static final java.lang.String LOG_TAG = "PduParser";
    private static final int LONG_INTEGER_LENGTH_MAX = 8;
    private static final int QUOTE = 127;
    private static final int QUOTED_STRING_FLAG = 34;
    private static final int SHORT_INTEGER_MAX = 127;
    private static final int SHORT_LENGTH_MAX = 30;
    private static final int TEXT_MAX = 127;
    private static final int TEXT_MIN = 32;
    private static final int THE_FIRST_PART = 0;
    private static final int THE_LAST_PART = 1;
    private static final int TYPE_QUOTED_STRING = 1;
    private static final int TYPE_TEXT_STRING = 0;
    private static final int TYPE_TOKEN_STRING = 2;
    private final boolean mParseContentDisposition;
    private java.io.ByteArrayInputStream mPduDataStream;
    private static byte[] mTypeParam = null;
    private static byte[] mStartParam = null;
    private com.google.android.mms.pdu.PduHeaders mHeaders = null;
    private com.google.android.mms.pdu.PduBody mBody = null;

    public PduParser(byte[] bArr, boolean z) {
        this.mPduDataStream = null;
        this.mPduDataStream = new java.io.ByteArrayInputStream(bArr);
        this.mParseContentDisposition = z;
    }

    public com.google.android.mms.pdu.GenericPdu parse() {
        if (this.mPduDataStream == null) {
            return null;
        }
        this.mHeaders = parseHeaders(this.mPduDataStream);
        if (this.mHeaders == null) {
            return null;
        }
        int octet = this.mHeaders.getOctet(140);
        if (!checkMandatoryHeader(this.mHeaders)) {
            log("check mandatory headers failed!");
            return null;
        }
        if (128 == octet || 132 == octet) {
            this.mBody = parseParts(this.mPduDataStream);
            if (this.mBody == null) {
                return null;
            }
        }
        switch (octet) {
            case 128:
                break;
            case 129:
                break;
            case 130:
                break;
            case 131:
                break;
            case 132:
                com.google.android.mms.pdu.RetrieveConf retrieveConf = new com.google.android.mms.pdu.RetrieveConf(this.mHeaders, this.mBody);
                byte[] contentType = retrieveConf.getContentType();
                if (contentType != null) {
                    java.lang.String str = new java.lang.String(contentType);
                    if (!str.equals(com.google.android.mms.ContentType.MULTIPART_MIXED) && !str.equals(com.google.android.mms.ContentType.MULTIPART_RELATED) && !str.equals(com.google.android.mms.ContentType.MULTIPART_ALTERNATIVE)) {
                        if (str.equals(com.google.android.mms.ContentType.MULTIPART_ALTERNATIVE)) {
                            com.google.android.mms.pdu.PduPart part = this.mBody.getPart(0);
                            this.mBody.removeAll();
                            this.mBody.addPart(0, part);
                            break;
                        }
                    }
                }
                break;
            case 133:
                break;
            case 134:
                break;
            case 135:
                break;
            case 136:
                break;
            default:
                log("Parser doesn't support this message type in this version!");
                break;
        }
        return null;
    }

    protected com.google.android.mms.pdu.PduHeaders parseHeaders(java.io.ByteArrayInputStream byteArrayInputStream) {
        com.google.android.mms.pdu.EncodedStringValue encodedStringValue;
        byte[] textString;
        if (byteArrayInputStream == null) {
            return null;
        }
        com.google.android.mms.pdu.PduHeaders pduHeaders = new com.google.android.mms.pdu.PduHeaders();
        boolean z = true;
        while (z && byteArrayInputStream.available() > 0) {
            byteArrayInputStream.mark(1);
            int extractByteValue = extractByteValue(byteArrayInputStream);
            if (extractByteValue >= 32 && extractByteValue <= 127) {
                byteArrayInputStream.reset();
                parseWapString(byteArrayInputStream, 0);
            } else {
                switch (extractByteValue) {
                    case 129:
                    case 130:
                    case 151:
                        com.google.android.mms.pdu.EncodedStringValue parseEncodedStringValue = parseEncodedStringValue(byteArrayInputStream);
                        if (parseEncodedStringValue == null) {
                            continue;
                        } else {
                            byte[] textString2 = parseEncodedStringValue.getTextString();
                            if (textString2 != null) {
                                java.lang.String str = new java.lang.String(textString2);
                                int indexOf = str.indexOf("/");
                                if (indexOf > 0) {
                                    str = str.substring(0, indexOf);
                                }
                                try {
                                    parseEncodedStringValue.setTextString(str.getBytes());
                                } catch (java.lang.NullPointerException e) {
                                    log("null pointer error!");
                                    return null;
                                }
                            }
                            try {
                                pduHeaders.appendEncodedStringValue(parseEncodedStringValue, extractByteValue);
                                break;
                            } catch (java.lang.NullPointerException e2) {
                                log("null pointer error!");
                                break;
                            } catch (java.lang.RuntimeException e3) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                    case 131:
                    case 139:
                    case 152:
                    case 158:
                    case 183:
                    case 184:
                    case 185:
                    case 189:
                    case 190:
                        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setTextString(parseWapString, extractByteValue);
                                break;
                            } catch (java.lang.NullPointerException e4) {
                                log("null pointer error!");
                                break;
                            } catch (java.lang.RuntimeException e5) {
                                log(extractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                    case 132:
                        java.util.HashMap hashMap = new java.util.HashMap();
                        byte[] parseContentType = parseContentType(byteArrayInputStream, hashMap);
                        if (parseContentType != null) {
                            try {
                                pduHeaders.setTextString(parseContentType, 132);
                            } catch (java.lang.NullPointerException e6) {
                                log("null pointer error!");
                            } catch (java.lang.RuntimeException e7) {
                                log(extractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                        mStartParam = (byte[]) hashMap.get(153);
                        mTypeParam = (byte[]) hashMap.get(131);
                        z = false;
                        break;
                    case 133:
                    case 142:
                    case 159:
                        try {
                            pduHeaders.setLongInteger(parseLongInteger(byteArrayInputStream), extractByteValue);
                            break;
                        } catch (java.lang.RuntimeException e8) {
                            log(extractByteValue + "is not Long-Integer header field!");
                            return null;
                        }
                    case 134:
                    case 143:
                    case 144:
                    case 145:
                    case 146:
                    case 148:
                    case 149:
                    case 153:
                    case 155:
                    case 156:
                    case 162:
                    case 163:
                    case 165:
                    case 167:
                    case 169:
                    case 171:
                    case 177:
                    case 180:
                    case 186:
                    case 187:
                    case 188:
                    case 191:
                        int extractByteValue2 = extractByteValue(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(extractByteValue2, extractByteValue);
                            break;
                        } catch (com.google.android.mms.InvalidHeaderValueException e9) {
                            log("Set invalid Octet value: " + extractByteValue2 + " into the header filed: " + extractByteValue);
                            return null;
                        } catch (java.lang.RuntimeException e10) {
                            log(extractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 135:
                    case 136:
                    case 157:
                        parseValueLength(byteArrayInputStream);
                        int extractByteValue3 = extractByteValue(byteArrayInputStream);
                        try {
                            long parseLongInteger = parseLongInteger(byteArrayInputStream);
                            if (129 == extractByteValue3) {
                                parseLongInteger += java.lang.System.currentTimeMillis() / 1000;
                            }
                            try {
                                pduHeaders.setLongInteger(parseLongInteger, extractByteValue);
                                break;
                            } catch (java.lang.RuntimeException e11) {
                                log(extractByteValue + "is not Long-Integer header field!");
                                return null;
                            }
                        } catch (java.lang.RuntimeException e12) {
                            log(extractByteValue + "is not Long-Integer header field!");
                            return null;
                        }
                    case 137:
                        parseValueLength(byteArrayInputStream);
                        if (128 == extractByteValue(byteArrayInputStream)) {
                            encodedStringValue = parseEncodedStringValue(byteArrayInputStream);
                            if (encodedStringValue != null && (textString = encodedStringValue.getTextString()) != null) {
                                java.lang.String str2 = new java.lang.String(textString);
                                int indexOf2 = str2.indexOf("/");
                                if (indexOf2 > 0) {
                                    str2 = str2.substring(0, indexOf2);
                                }
                                try {
                                    encodedStringValue.setTextString(str2.getBytes());
                                } catch (java.lang.NullPointerException e13) {
                                    log("null pointer error!");
                                    return null;
                                }
                            }
                        } else {
                            try {
                                encodedStringValue = new com.google.android.mms.pdu.EncodedStringValue(com.google.android.mms.pdu.PduHeaders.FROM_INSERT_ADDRESS_TOKEN_STR.getBytes());
                            } catch (java.lang.NullPointerException e14) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                        try {
                            pduHeaders.setEncodedStringValue(encodedStringValue, 137);
                            break;
                        } catch (java.lang.NullPointerException e15) {
                            log("null pointer error!");
                            break;
                        } catch (java.lang.RuntimeException e16) {
                            log(extractByteValue + "is not Encoded-String-Value header field!");
                            return null;
                        }
                    case 138:
                        byteArrayInputStream.mark(1);
                        int extractByteValue4 = extractByteValue(byteArrayInputStream);
                        if (extractByteValue4 >= 128) {
                            if (128 == extractByteValue4) {
                                try {
                                    pduHeaders.setTextString(com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_PERSONAL_STR.getBytes(), 138);
                                    break;
                                } catch (java.lang.NullPointerException e17) {
                                    log("null pointer error!");
                                    break;
                                } catch (java.lang.RuntimeException e18) {
                                    log(extractByteValue + "is not Text-String header field!");
                                    return null;
                                }
                            } else if (129 == extractByteValue4) {
                                pduHeaders.setTextString(com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_ADVERTISEMENT_STR.getBytes(), 138);
                                break;
                            } else if (130 == extractByteValue4) {
                                pduHeaders.setTextString(com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_INFORMATIONAL_STR.getBytes(), 138);
                                break;
                            } else if (131 != extractByteValue4) {
                                break;
                            } else {
                                pduHeaders.setTextString("auto".getBytes(), 138);
                                break;
                            }
                        } else {
                            byteArrayInputStream.reset();
                            byte[] parseWapString2 = parseWapString(byteArrayInputStream, 0);
                            if (parseWapString2 == null) {
                                break;
                            } else {
                                try {
                                    pduHeaders.setTextString(parseWapString2, 138);
                                    break;
                                } catch (java.lang.NullPointerException e19) {
                                    log("null pointer error!");
                                    break;
                                } catch (java.lang.RuntimeException e20) {
                                    log(extractByteValue + "is not Text-String header field!");
                                    return null;
                                }
                            }
                        }
                    case 140:
                        int extractByteValue5 = extractByteValue(byteArrayInputStream);
                        switch (extractByteValue5) {
                            case 137:
                            case 138:
                            case 139:
                            case 140:
                            case 141:
                            case 142:
                            case 143:
                            case 144:
                            case 145:
                            case 146:
                            case 147:
                            case 148:
                            case 149:
                            case 150:
                            case 151:
                                return null;
                            default:
                                try {
                                    pduHeaders.setOctet(extractByteValue5, extractByteValue);
                                    break;
                                } catch (com.google.android.mms.InvalidHeaderValueException e21) {
                                    log("Set invalid Octet value: " + extractByteValue5 + " into the header filed: " + extractByteValue);
                                    return null;
                                } catch (java.lang.RuntimeException e22) {
                                    log(extractByteValue + "is not Octet header field!");
                                    return null;
                                }
                        }
                    case 141:
                        int parseShortInteger = parseShortInteger(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(parseShortInteger, 141);
                            break;
                        } catch (com.google.android.mms.InvalidHeaderValueException e23) {
                            log("Set invalid Octet value: " + parseShortInteger + " into the header filed: " + extractByteValue);
                            return null;
                        } catch (java.lang.RuntimeException e24) {
                            log(extractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 147:
                    case 150:
                    case 154:
                    case 166:
                    case 181:
                    case 182:
                        com.google.android.mms.pdu.EncodedStringValue parseEncodedStringValue2 = parseEncodedStringValue(byteArrayInputStream);
                        if (parseEncodedStringValue2 == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setEncodedStringValue(parseEncodedStringValue2, extractByteValue);
                                break;
                            } catch (java.lang.NullPointerException e25) {
                                log("null pointer error!");
                                break;
                            } catch (java.lang.RuntimeException e26) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                    case 160:
                        parseValueLength(byteArrayInputStream);
                        try {
                            parseIntegerValue(byteArrayInputStream);
                            com.google.android.mms.pdu.EncodedStringValue parseEncodedStringValue3 = parseEncodedStringValue(byteArrayInputStream);
                            if (parseEncodedStringValue3 == null) {
                                break;
                            } else {
                                try {
                                    pduHeaders.setEncodedStringValue(parseEncodedStringValue3, 160);
                                    break;
                                } catch (java.lang.NullPointerException e27) {
                                    log("null pointer error!");
                                    break;
                                } catch (java.lang.RuntimeException e28) {
                                    log(extractByteValue + "is not Encoded-String-Value header field!");
                                    return null;
                                }
                            }
                        } catch (java.lang.RuntimeException e29) {
                            log(extractByteValue + " is not Integer-Value");
                            return null;
                        }
                    case 161:
                        parseValueLength(byteArrayInputStream);
                        try {
                            parseIntegerValue(byteArrayInputStream);
                            try {
                                pduHeaders.setLongInteger(parseLongInteger(byteArrayInputStream), 161);
                                break;
                            } catch (java.lang.RuntimeException e30) {
                                log(extractByteValue + "is not Long-Integer header field!");
                                return null;
                            }
                        } catch (java.lang.RuntimeException e31) {
                            log(extractByteValue + " is not Integer-Value");
                            return null;
                        }
                    case 164:
                        parseValueLength(byteArrayInputStream);
                        extractByteValue(byteArrayInputStream);
                        parseEncodedStringValue(byteArrayInputStream);
                        break;
                    case 168:
                    case 174:
                    case 176:
                    default:
                        log("Unknown header");
                        break;
                    case 170:
                    case 172:
                        parseValueLength(byteArrayInputStream);
                        extractByteValue(byteArrayInputStream);
                        try {
                            parseIntegerValue(byteArrayInputStream);
                            break;
                        } catch (java.lang.RuntimeException e32) {
                            log(extractByteValue + " is not Integer-Value");
                            return null;
                        }
                    case 173:
                    case 175:
                    case 179:
                        try {
                            pduHeaders.setLongInteger(parseIntegerValue(byteArrayInputStream), extractByteValue);
                            break;
                        } catch (java.lang.RuntimeException e33) {
                            log(extractByteValue + "is not Long-Integer header field!");
                            return null;
                        }
                    case 178:
                        parseContentType(byteArrayInputStream, null);
                        break;
                }
            }
        }
        return pduHeaders;
    }

    protected com.google.android.mms.pdu.PduBody parseParts(java.io.ByteArrayInputStream byteArrayInputStream) {
        if (byteArrayInputStream == null) {
            return null;
        }
        int parseUnsignedInt = parseUnsignedInt(byteArrayInputStream);
        com.google.android.mms.pdu.PduBody pduBody = new com.google.android.mms.pdu.PduBody();
        for (int i = 0; i < parseUnsignedInt; i++) {
            int parseUnsignedInt2 = parseUnsignedInt(byteArrayInputStream);
            int parseUnsignedInt3 = parseUnsignedInt(byteArrayInputStream);
            com.google.android.mms.pdu.PduPart pduPart = new com.google.android.mms.pdu.PduPart();
            int available = byteArrayInputStream.available();
            if (available <= 0) {
                return null;
            }
            java.util.HashMap hashMap = new java.util.HashMap();
            byte[] parseContentType = parseContentType(byteArrayInputStream, hashMap);
            if (parseContentType == null) {
                pduPart.setContentType(com.google.android.mms.pdu.PduContentTypes.contentTypes[0].getBytes());
            } else {
                pduPart.setContentType(parseContentType);
            }
            byte[] bArr = (byte[]) hashMap.get(151);
            if (bArr != null) {
                pduPart.setName(bArr);
            }
            java.lang.Integer num = (java.lang.Integer) hashMap.get(129);
            if (num != null) {
                pduPart.setCharset(num.intValue());
            }
            int available2 = parseUnsignedInt2 - (available - byteArrayInputStream.available());
            if (available2 > 0) {
                if (!parsePartHeaders(byteArrayInputStream, pduPart, available2)) {
                    return null;
                }
            } else if (available2 < 0) {
                return null;
            }
            if (pduPart.getContentLocation() == null && pduPart.getName() == null && pduPart.getFilename() == null && pduPart.getContentId() == null) {
                pduPart.setContentLocation(java.lang.Long.toOctalString(java.lang.System.currentTimeMillis()).getBytes());
            }
            if (parseUnsignedInt3 > 0) {
                byte[] bArr2 = new byte[parseUnsignedInt3];
                java.lang.String str = new java.lang.String(pduPart.getContentType());
                byteArrayInputStream.read(bArr2, 0, parseUnsignedInt3);
                if (str.equalsIgnoreCase(com.google.android.mms.ContentType.MULTIPART_ALTERNATIVE)) {
                    pduPart = parseParts(new java.io.ByteArrayInputStream(bArr2)).getPart(0);
                } else {
                    byte[] contentTransferEncoding = pduPart.getContentTransferEncoding();
                    if (contentTransferEncoding != null) {
                        java.lang.String str2 = new java.lang.String(contentTransferEncoding);
                        if (str2.equalsIgnoreCase(com.google.android.mms.pdu.PduPart.P_BASE64)) {
                            bArr2 = com.google.android.mms.pdu.Base64.decodeBase64(bArr2);
                        } else if (str2.equalsIgnoreCase(com.google.android.mms.pdu.PduPart.P_QUOTED_PRINTABLE)) {
                            bArr2 = com.google.android.mms.pdu.QuotedPrintable.decodeQuotedPrintable(bArr2);
                        }
                    }
                    if (bArr2 == null) {
                        log("Decode part data error!");
                        return null;
                    }
                    pduPart.setData(bArr2);
                }
            }
            if (checkPartPosition(pduPart) == 0) {
                pduBody.addPart(0, pduPart);
            } else {
                pduBody.addPart(pduPart);
            }
        }
        return pduBody;
    }

    private static void log(java.lang.String str) {
    }

    protected static int parseUnsignedInt(java.io.ByteArrayInputStream byteArrayInputStream) {
        int read = byteArrayInputStream.read();
        if (read != -1) {
            int i = 0;
            while ((read & 128) != 0) {
                i = (i << 7) | (read & 127);
                read = byteArrayInputStream.read();
                if (read == -1) {
                    return read;
                }
            }
            return (i << 7) | (read & 127);
        }
        return read;
    }

    protected static int parseValueLength(java.io.ByteArrayInputStream byteArrayInputStream) {
        int read = byteArrayInputStream.read() & 255;
        if (read <= 30) {
            return read;
        }
        if (read == 31) {
            return parseUnsignedInt(byteArrayInputStream);
        }
        throw new java.lang.RuntimeException("Value length > LENGTH_QUOTE!");
    }

    protected static com.google.android.mms.pdu.EncodedStringValue parseEncodedStringValue(java.io.ByteArrayInputStream byteArrayInputStream) {
        int i;
        com.google.android.mms.pdu.EncodedStringValue encodedStringValue;
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read() & 255;
        if (read == 0) {
            return new com.google.android.mms.pdu.EncodedStringValue("");
        }
        byteArrayInputStream.reset();
        if (read >= 32) {
            i = 0;
        } else {
            parseValueLength(byteArrayInputStream);
            i = parseShortInteger(byteArrayInputStream);
        }
        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
        try {
            if (i != 0) {
                encodedStringValue = new com.google.android.mms.pdu.EncodedStringValue(i, parseWapString);
            } else {
                encodedStringValue = new com.google.android.mms.pdu.EncodedStringValue(parseWapString);
            }
            return encodedStringValue;
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    protected static byte[] parseWapString(java.io.ByteArrayInputStream byteArrayInputStream, int i) {
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        if (1 == i && 34 == read) {
            byteArrayInputStream.mark(1);
        } else if (i == 0 && 127 == read) {
            byteArrayInputStream.mark(1);
        } else {
            byteArrayInputStream.reset();
        }
        return getWapString(byteArrayInputStream, i);
    }

    protected static boolean isTokenCharacter(int i) {
        if (i < 33 || i > 126) {
            return false;
        }
        switch (i) {
            case 34:
            case 40:
            case 41:
            case 44:
            case 47:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 91:
            case 92:
            case 93:
            case 123:
            case 125:
                break;
        }
        return false;
    }

    protected static boolean isText(int i) {
        if ((i >= 32 && i <= 126) || (i >= 128 && i <= 255)) {
            return true;
        }
        switch (i) {
        }
        return true;
    }

    protected static byte[] getWapString(java.io.ByteArrayInputStream byteArrayInputStream, int i) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        int read = byteArrayInputStream.read();
        while (-1 != read && read != 0) {
            if (i == 2) {
                if (isTokenCharacter(read)) {
                    byteArrayOutputStream.write(read);
                }
            } else if (isText(read)) {
                byteArrayOutputStream.write(read);
            }
            read = byteArrayInputStream.read();
        }
        if (byteArrayOutputStream.size() > 0) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    protected static int extractByteValue(java.io.ByteArrayInputStream byteArrayInputStream) {
        return byteArrayInputStream.read() & 255;
    }

    protected static int parseShortInteger(java.io.ByteArrayInputStream byteArrayInputStream) {
        return byteArrayInputStream.read() & 127;
    }

    protected static long parseLongInteger(java.io.ByteArrayInputStream byteArrayInputStream) {
        int read = byteArrayInputStream.read() & 255;
        if (read > 8) {
            throw new java.lang.RuntimeException("Octet count greater than 8 and I can't represent that!");
        }
        long j = 0;
        for (int i = 0; i < read; i++) {
            j = (j << 8) + (byteArrayInputStream.read() & 255);
        }
        return j;
    }

    protected static long parseIntegerValue(java.io.ByteArrayInputStream byteArrayInputStream) {
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        if (read > 127) {
            return parseShortInteger(byteArrayInputStream);
        }
        return parseLongInteger(byteArrayInputStream);
    }

    protected static int skipWapValue(java.io.ByteArrayInputStream byteArrayInputStream, int i) {
        int read = byteArrayInputStream.read(new byte[i], 0, i);
        if (read < i) {
            return -1;
        }
        return read;
    }

    protected static void parseContentTypeParams(java.io.ByteArrayInputStream byteArrayInputStream, java.util.HashMap<java.lang.Integer, java.lang.Object> hashMap, java.lang.Integer num) {
        int available = byteArrayInputStream.available();
        int intValue = num.intValue();
        while (intValue > 0) {
            intValue--;
            switch (byteArrayInputStream.read()) {
                case 129:
                    byteArrayInputStream.mark(1);
                    int extractByteValue = extractByteValue(byteArrayInputStream);
                    byteArrayInputStream.reset();
                    if ((extractByteValue > 32 && extractByteValue < 127) || extractByteValue == 0) {
                        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                        try {
                            hashMap.put(129, java.lang.Integer.valueOf(com.google.android.mms.pdu.CharacterSets.getMibEnumValue(new java.lang.String(parseWapString))));
                        } catch (java.io.UnsupportedEncodingException e) {
                            android.util.Log.e(LOG_TAG, java.util.Arrays.toString(parseWapString), e);
                            hashMap.put(129, 0);
                        }
                    } else {
                        int parseIntegerValue = (int) parseIntegerValue(byteArrayInputStream);
                        if (hashMap != null) {
                            hashMap.put(129, java.lang.Integer.valueOf(parseIntegerValue));
                        }
                    }
                    intValue = num.intValue() - (available - byteArrayInputStream.available());
                    break;
                case 131:
                case 137:
                    byteArrayInputStream.mark(1);
                    int extractByteValue2 = extractByteValue(byteArrayInputStream);
                    byteArrayInputStream.reset();
                    if (extractByteValue2 > 127) {
                        int parseShortInteger = parseShortInteger(byteArrayInputStream);
                        if (parseShortInteger < com.google.android.mms.pdu.PduContentTypes.contentTypes.length) {
                            hashMap.put(131, com.google.android.mms.pdu.PduContentTypes.contentTypes[parseShortInteger].getBytes());
                        }
                    } else {
                        byte[] parseWapString2 = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString2 != null && hashMap != null) {
                            hashMap.put(131, parseWapString2);
                        }
                    }
                    intValue = num.intValue() - (available - byteArrayInputStream.available());
                    break;
                case 133:
                case 151:
                    byte[] parseWapString3 = parseWapString(byteArrayInputStream, 0);
                    if (parseWapString3 != null && hashMap != null) {
                        hashMap.put(151, parseWapString3);
                    }
                    intValue = num.intValue() - (available - byteArrayInputStream.available());
                    break;
                case 138:
                case 153:
                    byte[] parseWapString4 = parseWapString(byteArrayInputStream, 0);
                    if (parseWapString4 != null && hashMap != null) {
                        hashMap.put(153, parseWapString4);
                    }
                    intValue = num.intValue() - (available - byteArrayInputStream.available());
                    break;
                default:
                    if (-1 == skipWapValue(byteArrayInputStream, intValue)) {
                        android.util.Log.e(LOG_TAG, "Corrupt Content-Type");
                        break;
                    } else {
                        intValue = 0;
                        break;
                    }
            }
        }
        if (intValue != 0) {
            android.util.Log.e(LOG_TAG, "Corrupt Content-Type");
        }
    }

    protected static byte[] parseContentType(java.io.ByteArrayInputStream byteArrayInputStream, java.util.HashMap<java.lang.Integer, java.lang.Object> hashMap) {
        byte[] parseWapString;
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        int i = read & 255;
        if (i < 32) {
            int parseValueLength = parseValueLength(byteArrayInputStream);
            int available = byteArrayInputStream.available();
            if (parseValueLength > available) {
                android.util.Log.e(LOG_TAG, "parseContentType: Invalid length " + parseValueLength + " when available bytes are " + available);
                return com.google.android.mms.pdu.PduContentTypes.contentTypes[0].getBytes();
            }
            byteArrayInputStream.mark(1);
            int read2 = byteArrayInputStream.read();
            byteArrayInputStream.reset();
            int i2 = read2 & 255;
            if (i2 >= 32 && i2 <= 127) {
                parseWapString = parseWapString(byteArrayInputStream, 0);
            } else {
                if (i2 <= 127) {
                    android.util.Log.e(LOG_TAG, "Corrupt content-type");
                    return com.google.android.mms.pdu.PduContentTypes.contentTypes[0].getBytes();
                }
                int parseShortInteger = parseShortInteger(byteArrayInputStream);
                if (parseShortInteger < com.google.android.mms.pdu.PduContentTypes.contentTypes.length) {
                    parseWapString = com.google.android.mms.pdu.PduContentTypes.contentTypes[parseShortInteger].getBytes();
                } else {
                    byteArrayInputStream.reset();
                    parseWapString = parseWapString(byteArrayInputStream, 0);
                }
            }
            int available2 = parseValueLength - (available - byteArrayInputStream.available());
            if (available2 > 0) {
                parseContentTypeParams(byteArrayInputStream, hashMap, java.lang.Integer.valueOf(available2));
            }
            if (available2 < 0) {
                android.util.Log.e(LOG_TAG, "Corrupt MMS message");
                return com.google.android.mms.pdu.PduContentTypes.contentTypes[0].getBytes();
            }
            return parseWapString;
        }
        if (i <= 127) {
            return parseWapString(byteArrayInputStream, 0);
        }
        return com.google.android.mms.pdu.PduContentTypes.contentTypes[parseShortInteger(byteArrayInputStream)].getBytes();
    }

    protected boolean parsePartHeaders(java.io.ByteArrayInputStream byteArrayInputStream, com.google.android.mms.pdu.PduPart pduPart, int i) {
        int available = byteArrayInputStream.available();
        int i2 = i;
        while (i2 > 0) {
            int read = byteArrayInputStream.read();
            i2--;
            if (read > 127) {
                switch (read) {
                    case 142:
                        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString != null) {
                            pduPart.setContentLocation(parseWapString);
                        }
                        i2 = i - (available - byteArrayInputStream.available());
                        break;
                    case 174:
                    case 197:
                        if (!this.mParseContentDisposition) {
                            break;
                        } else {
                            int parseValueLength = parseValueLength(byteArrayInputStream);
                            byteArrayInputStream.mark(1);
                            int available2 = byteArrayInputStream.available();
                            int read2 = byteArrayInputStream.read();
                            if (read2 == 128) {
                                pduPart.setContentDisposition(com.google.android.mms.pdu.PduPart.DISPOSITION_FROM_DATA);
                            } else if (read2 == 129) {
                                pduPart.setContentDisposition(com.google.android.mms.pdu.PduPart.DISPOSITION_ATTACHMENT);
                            } else if (read2 == 130) {
                                pduPart.setContentDisposition(com.google.android.mms.pdu.PduPart.DISPOSITION_INLINE);
                            } else {
                                byteArrayInputStream.reset();
                                pduPart.setContentDisposition(parseWapString(byteArrayInputStream, 0));
                            }
                            if (available2 - byteArrayInputStream.available() < parseValueLength) {
                                if (byteArrayInputStream.read() == 152) {
                                    pduPart.setFilename(parseWapString(byteArrayInputStream, 0));
                                }
                                int available3 = available2 - byteArrayInputStream.available();
                                if (available3 < parseValueLength) {
                                    int i3 = parseValueLength - available3;
                                    byteArrayInputStream.read(new byte[i3], 0, i3);
                                }
                            }
                            i2 = i - (available - byteArrayInputStream.available());
                            break;
                        }
                    case 192:
                        byte[] parseWapString2 = parseWapString(byteArrayInputStream, 1);
                        if (parseWapString2 != null) {
                            pduPart.setContentId(parseWapString2);
                        }
                        i2 = i - (available - byteArrayInputStream.available());
                        break;
                    default:
                        if (-1 == skipWapValue(byteArrayInputStream, i2)) {
                            android.util.Log.e(LOG_TAG, "Corrupt Part headers");
                            return false;
                        }
                        i2 = 0;
                        break;
                }
            } else if (read >= 32 && read <= 127) {
                byte[] parseWapString3 = parseWapString(byteArrayInputStream, 0);
                byte[] parseWapString4 = parseWapString(byteArrayInputStream, 0);
                if (true == com.google.android.mms.pdu.PduPart.CONTENT_TRANSFER_ENCODING.equalsIgnoreCase(new java.lang.String(parseWapString3))) {
                    pduPart.setContentTransferEncoding(parseWapString4);
                }
                i2 = i - (available - byteArrayInputStream.available());
            } else {
                if (-1 == skipWapValue(byteArrayInputStream, i2)) {
                    android.util.Log.e(LOG_TAG, "Corrupt Part headers");
                    return false;
                }
                i2 = 0;
            }
        }
        if (i2 == 0) {
            return true;
        }
        android.util.Log.e(LOG_TAG, "Corrupt Part headers");
        return false;
    }

    private static int checkPartPosition(com.google.android.mms.pdu.PduPart pduPart) {
        byte[] contentType;
        if (mTypeParam == null && mStartParam == null) {
            return 1;
        }
        if (mStartParam == null) {
            return (mTypeParam == null || (contentType = pduPart.getContentType()) == null || true != java.util.Arrays.equals(mTypeParam, contentType)) ? 1 : 0;
        }
        byte[] contentId = pduPart.getContentId();
        return (contentId == null || true != java.util.Arrays.equals(mStartParam, contentId)) ? 1 : 0;
    }

    protected static boolean checkMandatoryHeader(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        if (pduHeaders == null) {
            return false;
        }
        int octet = pduHeaders.getOctet(140);
        if (pduHeaders.getOctet(141) == 0) {
            return false;
        }
        switch (octet) {
            case 128:
                if (pduHeaders.getTextString(132) != null && pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 129:
                if (pduHeaders.getOctet(146) != 0 && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 130:
                if (pduHeaders.getTextString(131) != null && -1 != pduHeaders.getLongInteger(136) && pduHeaders.getTextString(138) != null && -1 != pduHeaders.getLongInteger(142) && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 131:
                if (pduHeaders.getOctet(149) != 0 && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 132:
                if (pduHeaders.getTextString(132) != null && -1 != pduHeaders.getLongInteger(133)) {
                }
                break;
            case 133:
                if (pduHeaders.getTextString(152) == null) {
                }
                break;
            case 134:
                if (-1 != pduHeaders.getLongInteger(133) && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(149) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
            case 135:
                if (pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(155) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
            case 136:
                if (-1 != pduHeaders.getLongInteger(133) && pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(155) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
        }
        return false;
    }
}
