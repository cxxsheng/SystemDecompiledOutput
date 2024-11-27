package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class PduComposer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int END_STRING_FLAG = 0;
    private static final int LENGTH_QUOTE = 31;
    private static final int LONG_INTEGER_LENGTH_MAX = 8;
    private static final int PDU_COMPOSER_BLOCK_SIZE = 1024;
    private static final int PDU_COMPOSE_CONTENT_ERROR = 1;
    private static final int PDU_COMPOSE_FIELD_NOT_SET = 2;
    private static final int PDU_COMPOSE_FIELD_NOT_SUPPORTED = 3;
    private static final int PDU_COMPOSE_SUCCESS = 0;
    private static final int PDU_EMAIL_ADDRESS_TYPE = 2;
    private static final int PDU_IPV4_ADDRESS_TYPE = 3;
    private static final int PDU_IPV6_ADDRESS_TYPE = 4;
    private static final int PDU_PHONE_NUMBER_ADDRESS_TYPE = 1;
    private static final int PDU_UNKNOWN_ADDRESS_TYPE = 5;
    private static final int QUOTED_STRING_FLAG = 34;
    static final java.lang.String REGEXP_EMAIL_ADDRESS_TYPE = "[a-zA-Z| ]*\\<{0,1}[a-zA-Z| ]+@{1}[a-zA-Z| ]+\\.{1}[a-zA-Z| ]+\\>{0,1}";
    static final java.lang.String REGEXP_IPV4_ADDRESS_TYPE = "[0-9]{1,3}\\.{1}[0-9]{1,3}\\.{1}[0-9]{1,3}\\.{1}[0-9]{1,3}";
    static final java.lang.String REGEXP_IPV6_ADDRESS_TYPE = "[a-fA-F]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}";
    static final java.lang.String REGEXP_PHONE_NUMBER_ADDRESS_TYPE = "\\+?[0-9|\\.|\\-]+";
    private static final int SHORT_INTEGER_MAX = 127;
    static final java.lang.String STRING_IPV4_ADDRESS_TYPE = "/TYPE=IPV4";
    static final java.lang.String STRING_IPV6_ADDRESS_TYPE = "/TYPE=IPV6";
    static final java.lang.String STRING_PHONE_NUMBER_ADDRESS_TYPE = "/TYPE=PLMN";
    private static final int TEXT_MAX = 127;
    private static java.util.HashMap<java.lang.String, java.lang.Integer> mContentTypeMap;
    protected java.io.ByteArrayOutputStream mMessage;
    private com.google.android.mms.pdu.GenericPdu mPdu;
    private com.google.android.mms.pdu.PduHeaders mPduHeader;
    protected int mPosition;
    private final android.content.ContentResolver mResolver;
    private com.google.android.mms.pdu.PduComposer.BufferStack mStack;

    static {
        mContentTypeMap = null;
        mContentTypeMap = new java.util.HashMap<>();
        for (int i = 0; i < com.google.android.mms.pdu.PduContentTypes.contentTypes.length; i++) {
            mContentTypeMap.put(com.google.android.mms.pdu.PduContentTypes.contentTypes[i], java.lang.Integer.valueOf(i));
        }
    }

    public PduComposer(android.content.Context context, com.google.android.mms.pdu.GenericPdu genericPdu) {
        this.mMessage = null;
        this.mPdu = null;
        this.mPosition = 0;
        this.mStack = null;
        this.mPduHeader = null;
        this.mPdu = genericPdu;
        this.mResolver = context.getContentResolver();
        this.mPduHeader = genericPdu.getPduHeaders();
        this.mStack = new com.google.android.mms.pdu.PduComposer.BufferStack();
        this.mMessage = new java.io.ByteArrayOutputStream();
        this.mPosition = 0;
    }

    public byte[] make() {
        int messageType = this.mPdu.getMessageType();
        switch (messageType) {
            case 128:
            case 132:
                if (makeSendRetrievePdu(messageType) != 0) {
                    return null;
                }
                break;
            case 129:
            case 130:
            case 134:
            default:
                return null;
            case 131:
                if (makeNotifyResp() != 0) {
                    return null;
                }
                break;
            case 133:
                if (makeAckInd() != 0) {
                    return null;
                }
                break;
            case 135:
                if (makeReadRecInd() != 0) {
                    return null;
                }
                break;
        }
        return this.mMessage.toByteArray();
    }

    protected void arraycopy(byte[] bArr, int i, int i2) {
        this.mMessage.write(bArr, i, i2);
        this.mPosition += i2;
    }

    protected void append(int i) {
        this.mMessage.write(i);
        this.mPosition++;
    }

    protected void appendShortInteger(int i) {
        append((i | 128) & 255);
    }

    protected void appendOctet(int i) {
        append(i);
    }

    protected void appendShortLength(int i) {
        append(i);
    }

    protected void appendLongInteger(long j) {
        long j2 = j;
        int i = 0;
        while (j2 != 0 && i < 8) {
            j2 >>>= 8;
            i++;
        }
        appendShortLength(i);
        int i2 = (i - 1) * 8;
        for (int i3 = 0; i3 < i; i3++) {
            append((int) ((j >>> i2) & 255));
            i2 -= 8;
        }
    }

    protected void appendTextString(byte[] bArr) {
        if ((bArr[0] & 255) > 127) {
            append(127);
        }
        arraycopy(bArr, 0, bArr.length);
        append(0);
    }

    protected void appendTextString(java.lang.String str) {
        appendTextString(str.getBytes());
    }

    protected void appendEncodedString(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        int characterSet = encodedStringValue.getCharacterSet();
        byte[] textString = encodedStringValue.getTextString();
        if (textString == null) {
            return;
        }
        this.mStack.newbuf();
        com.google.android.mms.pdu.PduComposer.PositionMarker mark = this.mStack.mark();
        appendShortInteger(characterSet);
        appendTextString(textString);
        int length = mark.getLength();
        this.mStack.pop();
        appendValueLength(length);
        this.mStack.copy();
    }

    protected void appendUintvarInteger(long j) {
        int i = 0;
        long j2 = 127;
        while (i < 5 && j >= j2) {
            j2 = (j2 << 7) | 127;
            i++;
        }
        while (i > 0) {
            append((int) ((((j >>> (i * 7)) & 127) | 128) & 255));
            i--;
        }
        append((int) (j & 127));
    }

    protected void appendDateValue(long j) {
        appendLongInteger(j);
    }

    protected void appendValueLength(long j) {
        if (j < 31) {
            appendShortLength((int) j);
        } else {
            append(31);
            appendUintvarInteger(j);
        }
    }

    protected void appendQuotedString(byte[] bArr) {
        append(34);
        arraycopy(bArr, 0, bArr.length);
        append(0);
    }

    protected void appendQuotedString(java.lang.String str) {
        appendQuotedString(str.getBytes());
    }

    private com.google.android.mms.pdu.EncodedStringValue appendAddressType(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        try {
            int checkAddressType = checkAddressType(encodedStringValue.getString());
            com.google.android.mms.pdu.EncodedStringValue copy = com.google.android.mms.pdu.EncodedStringValue.copy(encodedStringValue);
            if (1 == checkAddressType) {
                copy.appendTextString(STRING_PHONE_NUMBER_ADDRESS_TYPE.getBytes());
            } else if (3 == checkAddressType) {
                copy.appendTextString(STRING_IPV4_ADDRESS_TYPE.getBytes());
            } else if (4 == checkAddressType) {
                copy.appendTextString(STRING_IPV6_ADDRESS_TYPE.getBytes());
            }
            return copy;
        } catch (java.lang.NullPointerException e) {
            return null;
        }
    }

    private int appendHeader(int i) {
        switch (i) {
            case 129:
            case 130:
            case 151:
                com.google.android.mms.pdu.EncodedStringValue[] encodedStringValues = this.mPduHeader.getEncodedStringValues(i);
                if (encodedStringValues == null) {
                    return 2;
                }
                for (com.google.android.mms.pdu.EncodedStringValue encodedStringValue : encodedStringValues) {
                    com.google.android.mms.pdu.EncodedStringValue appendAddressType = appendAddressType(encodedStringValue);
                    if (appendAddressType == null) {
                        return 1;
                    }
                    appendOctet(i);
                    appendEncodedString(appendAddressType);
                }
                return 0;
            case 131:
            case 132:
            case 135:
            case 140:
            case 142:
            case 146:
            case 147:
            case 148:
            default:
                return 3;
            case 133:
                long longInteger = this.mPduHeader.getLongInteger(i);
                if (-1 == longInteger) {
                    return 2;
                }
                appendOctet(i);
                appendDateValue(longInteger);
                return 0;
            case 134:
            case 143:
            case 144:
            case 145:
            case 149:
            case 153:
            case 155:
                int octet = this.mPduHeader.getOctet(i);
                if (octet == 0) {
                    return 2;
                }
                appendOctet(i);
                appendOctet(octet);
                return 0;
            case 136:
                long longInteger2 = this.mPduHeader.getLongInteger(i);
                if (-1 == longInteger2) {
                    return 2;
                }
                appendOctet(i);
                this.mStack.newbuf();
                com.google.android.mms.pdu.PduComposer.PositionMarker mark = this.mStack.mark();
                append(129);
                appendLongInteger(longInteger2);
                int length = mark.getLength();
                this.mStack.pop();
                appendValueLength(length);
                this.mStack.copy();
                return 0;
            case 137:
                appendOctet(i);
                com.google.android.mms.pdu.EncodedStringValue encodedStringValue2 = this.mPduHeader.getEncodedStringValue(i);
                if (encodedStringValue2 == null || android.text.TextUtils.isEmpty(encodedStringValue2.getString()) || new java.lang.String(encodedStringValue2.getTextString()).equals(com.google.android.mms.pdu.PduHeaders.FROM_INSERT_ADDRESS_TOKEN_STR)) {
                    append(1);
                    append(129);
                } else {
                    this.mStack.newbuf();
                    com.google.android.mms.pdu.PduComposer.PositionMarker mark2 = this.mStack.mark();
                    append(128);
                    com.google.android.mms.pdu.EncodedStringValue appendAddressType2 = appendAddressType(encodedStringValue2);
                    if (appendAddressType2 == null) {
                        return 1;
                    }
                    appendEncodedString(appendAddressType2);
                    int length2 = mark2.getLength();
                    this.mStack.pop();
                    appendValueLength(length2);
                    this.mStack.copy();
                }
                return 0;
            case 138:
                byte[] textString = this.mPduHeader.getTextString(i);
                if (textString == null) {
                    return 2;
                }
                appendOctet(i);
                if (java.util.Arrays.equals(textString, com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_ADVERTISEMENT_STR.getBytes())) {
                    appendOctet(129);
                } else if (java.util.Arrays.equals(textString, "auto".getBytes())) {
                    appendOctet(131);
                } else if (java.util.Arrays.equals(textString, com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_PERSONAL_STR.getBytes())) {
                    appendOctet(128);
                } else if (java.util.Arrays.equals(textString, com.google.android.mms.pdu.PduHeaders.MESSAGE_CLASS_INFORMATIONAL_STR.getBytes())) {
                    appendOctet(130);
                } else {
                    appendTextString(textString);
                }
                return 0;
            case 139:
            case 152:
                byte[] textString2 = this.mPduHeader.getTextString(i);
                if (textString2 == null) {
                    return 2;
                }
                appendOctet(i);
                appendTextString(textString2);
                return 0;
            case 141:
                appendOctet(i);
                int octet2 = this.mPduHeader.getOctet(i);
                if (octet2 == 0) {
                    appendShortInteger(18);
                } else {
                    appendShortInteger(octet2);
                }
                return 0;
            case 150:
            case 154:
                com.google.android.mms.pdu.EncodedStringValue encodedStringValue3 = this.mPduHeader.getEncodedStringValue(i);
                if (encodedStringValue3 == null) {
                    return 2;
                }
                appendOctet(i);
                appendEncodedString(encodedStringValue3);
                return 0;
        }
    }

    private int makeReadRecInd() {
        if (this.mMessage == null) {
            this.mMessage = new java.io.ByteArrayOutputStream();
            this.mPosition = 0;
        }
        appendOctet(140);
        appendOctet(135);
        if (appendHeader(141) != 0 || appendHeader(139) != 0 || appendHeader(151) != 0 || appendHeader(137) != 0) {
            return 1;
        }
        appendHeader(133);
        return appendHeader(155) != 0 ? 1 : 0;
    }

    private int makeNotifyResp() {
        if (this.mMessage == null) {
            this.mMessage = new java.io.ByteArrayOutputStream();
            this.mPosition = 0;
        }
        appendOctet(140);
        appendOctet(131);
        if (appendHeader(152) != 0 || appendHeader(141) != 0 || appendHeader(149) != 0) {
            return 1;
        }
        appendHeader(145);
        return 0;
    }

    private int makeAckInd() {
        if (this.mMessage == null) {
            this.mMessage = new java.io.ByteArrayOutputStream();
            this.mPosition = 0;
        }
        appendOctet(140);
        appendOctet(133);
        if (appendHeader(152) != 0 || appendHeader(141) != 0) {
            return 1;
        }
        appendHeader(145);
        return 0;
    }

    private int makeSendRetrievePdu(int i) {
        if (this.mMessage == null) {
            this.mMessage = new java.io.ByteArrayOutputStream();
            this.mPosition = 0;
        }
        appendOctet(140);
        appendOctet(i);
        appendOctet(152);
        byte[] textString = this.mPduHeader.getTextString(152);
        if (textString == null) {
            throw new java.lang.IllegalArgumentException("Transaction-ID is null.");
        }
        appendTextString(textString);
        if (appendHeader(141) != 0) {
            return 1;
        }
        appendHeader(133);
        if (appendHeader(137) != 0) {
            return 1;
        }
        boolean z = appendHeader(151) != 1;
        if (appendHeader(130) != 1) {
            z = true;
        }
        if (appendHeader(129) != 1) {
            z = true;
        }
        if (!z) {
            return 1;
        }
        appendHeader(150);
        appendHeader(138);
        appendHeader(136);
        appendHeader(143);
        appendHeader(134);
        appendHeader(144);
        if (i == 132) {
            appendHeader(153);
            appendHeader(154);
        }
        appendOctet(132);
        return makeMessageBody(i);
    }

    private int makeMessageBody(int i) {
        int i2;
        this.mStack.newbuf();
        com.google.android.mms.pdu.PduComposer.PositionMarker mark = this.mStack.mark();
        java.lang.Integer num = mContentTypeMap.get(new java.lang.String(this.mPduHeader.getTextString(132)));
        if (num == null) {
            return 1;
        }
        appendShortInteger(num.intValue());
        com.google.android.mms.pdu.PduBody body = i == 132 ? ((com.google.android.mms.pdu.RetrieveConf) this.mPdu).getBody() : ((com.google.android.mms.pdu.SendReq) this.mPdu).getBody();
        if (body == null || body.getPartsNum() == 0) {
            appendUintvarInteger(0L);
            this.mStack.pop();
            this.mStack.copy();
            return 0;
        }
        byte b = 62;
        try {
            com.google.android.mms.pdu.PduPart part = body.getPart(0);
            byte[] contentId = part.getContentId();
            if (contentId != null) {
                appendOctet(138);
                if (60 == contentId[0] && 62 == contentId[contentId.length - 1]) {
                    appendTextString(contentId);
                } else {
                    appendTextString("<" + new java.lang.String(contentId) + ">");
                }
            }
            appendOctet(137);
            appendTextString(part.getContentType());
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        int length = mark.getLength();
        this.mStack.pop();
        appendValueLength(length);
        this.mStack.copy();
        int partsNum = body.getPartsNum();
        appendUintvarInteger(partsNum);
        int i3 = 0;
        while (i3 < partsNum) {
            com.google.android.mms.pdu.PduPart part2 = body.getPart(i3);
            this.mStack.newbuf();
            com.google.android.mms.pdu.PduComposer.PositionMarker mark2 = this.mStack.mark();
            this.mStack.newbuf();
            com.google.android.mms.pdu.PduComposer.PositionMarker mark3 = this.mStack.mark();
            byte[] contentType = part2.getContentType();
            if (contentType == null) {
                return 1;
            }
            java.lang.Integer num2 = mContentTypeMap.get(new java.lang.String(contentType));
            if (num2 == null) {
                appendTextString(contentType);
            } else {
                appendShortInteger(num2.intValue());
            }
            byte[] name = part2.getName();
            if (name == null && (name = part2.getFilename()) == null && (name = part2.getContentLocation()) == null) {
                return 1;
            }
            appendOctet(133);
            appendTextString(name);
            int charset = part2.getCharset();
            if (charset != 0) {
                appendOctet(129);
                appendShortInteger(charset);
            }
            int length2 = mark3.getLength();
            this.mStack.pop();
            appendValueLength(length2);
            this.mStack.copy();
            byte[] contentId2 = part2.getContentId();
            if (contentId2 != null) {
                appendOctet(192);
                if (60 == contentId2[0] && b == contentId2[contentId2.length - 1]) {
                    appendQuotedString(contentId2);
                } else {
                    appendQuotedString("<" + new java.lang.String(contentId2) + ">");
                }
            }
            byte[] contentLocation = part2.getContentLocation();
            if (contentLocation != null) {
                appendOctet(142);
                appendTextString(contentLocation);
            }
            int length3 = mark2.getLength();
            byte[] data = part2.getData();
            if (data != null) {
                arraycopy(data, 0, data.length);
                i2 = data.length;
            } else {
                java.io.InputStream inputStream = null;
                try {
                    byte[] bArr = new byte[1024];
                    inputStream = this.mResolver.openInputStream(part2.getDataUri());
                    int i4 = 0;
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        this.mMessage.write(bArr, 0, read);
                        this.mPosition += read;
                        i4 += read;
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e2) {
                        }
                    }
                    i2 = i4;
                } catch (java.io.FileNotFoundException e3) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    return 1;
                } catch (java.io.IOException e5) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e6) {
                        }
                    }
                    return 1;
                } catch (java.lang.RuntimeException e7) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e8) {
                        }
                    }
                    return 1;
                } catch (java.lang.Throwable th) {
                    if (inputStream == null) {
                        throw th;
                    }
                    try {
                        inputStream.close();
                        throw th;
                    } catch (java.io.IOException e9) {
                        throw th;
                    }
                }
            }
            if (i2 != mark2.getLength() - length3) {
                throw new java.lang.RuntimeException("BUG: Length correctness check failed");
            }
            this.mStack.pop();
            appendUintvarInteger(length3);
            appendUintvarInteger(i2);
            this.mStack.copy();
            i3++;
            b = 62;
        }
        return 0;
    }

    private static class LengthRecordNode {
        java.io.ByteArrayOutputStream currentMessage;
        public int currentPosition;
        public com.google.android.mms.pdu.PduComposer.LengthRecordNode next;

        private LengthRecordNode() {
            this.currentMessage = null;
            this.currentPosition = 0;
            this.next = null;
        }
    }

    private class PositionMarker {
        private int c_pos;
        private int currentStackSize;

        private PositionMarker() {
        }

        int getLength() {
            if (this.currentStackSize != com.google.android.mms.pdu.PduComposer.this.mStack.stackSize) {
                throw new java.lang.RuntimeException("BUG: Invalid call to getLength()");
            }
            return com.google.android.mms.pdu.PduComposer.this.mPosition - this.c_pos;
        }
    }

    private class BufferStack {
        private com.google.android.mms.pdu.PduComposer.LengthRecordNode stack;
        int stackSize;
        private com.google.android.mms.pdu.PduComposer.LengthRecordNode toCopy;

        private BufferStack() {
            this.stack = null;
            this.toCopy = null;
            this.stackSize = 0;
        }

        void newbuf() {
            if (this.toCopy != null) {
                throw new java.lang.RuntimeException("BUG: Invalid newbuf() before copy()");
            }
            com.google.android.mms.pdu.PduComposer.LengthRecordNode lengthRecordNode = new com.google.android.mms.pdu.PduComposer.LengthRecordNode();
            lengthRecordNode.currentMessage = com.google.android.mms.pdu.PduComposer.this.mMessage;
            lengthRecordNode.currentPosition = com.google.android.mms.pdu.PduComposer.this.mPosition;
            lengthRecordNode.next = this.stack;
            this.stack = lengthRecordNode;
            this.stackSize++;
            com.google.android.mms.pdu.PduComposer.this.mMessage = new java.io.ByteArrayOutputStream();
            com.google.android.mms.pdu.PduComposer.this.mPosition = 0;
        }

        void pop() {
            java.io.ByteArrayOutputStream byteArrayOutputStream = com.google.android.mms.pdu.PduComposer.this.mMessage;
            int i = com.google.android.mms.pdu.PduComposer.this.mPosition;
            com.google.android.mms.pdu.PduComposer.this.mMessage = this.stack.currentMessage;
            com.google.android.mms.pdu.PduComposer.this.mPosition = this.stack.currentPosition;
            this.toCopy = this.stack;
            this.stack = this.stack.next;
            this.stackSize--;
            this.toCopy.currentMessage = byteArrayOutputStream;
            this.toCopy.currentPosition = i;
        }

        void copy() {
            com.google.android.mms.pdu.PduComposer.this.arraycopy(this.toCopy.currentMessage.toByteArray(), 0, this.toCopy.currentPosition);
            this.toCopy = null;
        }

        com.google.android.mms.pdu.PduComposer.PositionMarker mark() {
            com.google.android.mms.pdu.PduComposer.PositionMarker positionMarker = new com.google.android.mms.pdu.PduComposer.PositionMarker();
            positionMarker.c_pos = com.google.android.mms.pdu.PduComposer.this.mPosition;
            positionMarker.currentStackSize = this.stackSize;
            return positionMarker;
        }
    }

    protected static int checkAddressType(java.lang.String str) {
        if (str == null) {
            return 5;
        }
        if (str.matches(REGEXP_IPV4_ADDRESS_TYPE)) {
            return 3;
        }
        if (str.matches(REGEXP_PHONE_NUMBER_ADDRESS_TYPE)) {
            return 1;
        }
        if (str.matches(REGEXP_EMAIL_ADDRESS_TYPE)) {
            return 2;
        }
        if (!str.matches(REGEXP_IPV6_ADDRESS_TYPE)) {
            return 5;
        }
        return 4;
    }
}
