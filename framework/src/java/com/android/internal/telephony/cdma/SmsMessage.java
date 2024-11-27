package com.android.internal.telephony.cdma;

/* loaded from: classes5.dex */
public class SmsMessage extends com.android.internal.telephony.SmsMessageBase {
    private static final byte BEARER_DATA = 8;
    private static final byte BEARER_REPLY_OPTION = 6;
    private static final byte CAUSE_CODES = 7;
    private static final byte DESTINATION_ADDRESS = 4;
    private static final byte DESTINATION_SUB_ADDRESS = 5;
    private static final java.lang.String LOGGABLE_TAG = "CDMA:SMS";
    static final java.lang.String LOG_TAG = "SmsMessage";
    private static final byte ORIGINATING_ADDRESS = 2;
    private static final byte ORIGINATING_SUB_ADDRESS = 3;
    private static final int PRIORITY_EMERGENCY = 3;
    private static final int PRIORITY_INTERACTIVE = 1;
    private static final int PRIORITY_NORMAL = 0;
    private static final int PRIORITY_URGENT = 2;
    private static final int RETURN_ACK = 1;
    private static final int RETURN_NO_ACK = 0;
    private static final byte SERVICE_CATEGORY = 1;
    private static final byte TELESERVICE_IDENTIFIER = 0;
    private static final boolean VDBG = false;
    private com.android.internal.telephony.cdma.sms.BearerData mBearerData;
    private com.android.internal.telephony.cdma.sms.SmsEnvelope mEnvelope;
    private int status;

    public static class SubmitPdu extends com.android.internal.telephony.SmsMessageBase.SubmitPduBase {
    }

    public SmsMessage(com.android.internal.telephony.SmsAddress smsAddress, com.android.internal.telephony.cdma.sms.SmsEnvelope smsEnvelope) {
        this.mOriginatingAddress = smsAddress;
        this.mEnvelope = smsEnvelope;
        createPdu();
    }

    public SmsMessage() {
    }

    public static com.android.internal.telephony.cdma.SmsMessage createFromPdu(byte[] bArr) {
        com.android.internal.telephony.cdma.SmsMessage smsMessage = new com.android.internal.telephony.cdma.SmsMessage();
        try {
            smsMessage.parsePdu(bArr);
            return smsMessage;
        } catch (java.lang.OutOfMemoryError e) {
            android.util.Log.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (java.lang.RuntimeException e2) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e2);
            return null;
        }
    }

    public static com.android.internal.telephony.cdma.SmsMessage createFromEfRecord(int i, byte[] bArr) {
        try {
            com.android.internal.telephony.cdma.SmsMessage smsMessage = new com.android.internal.telephony.cdma.SmsMessage();
            smsMessage.mIndexOnIcc = i;
            if ((bArr[0] & 1) == 0) {
                com.android.telephony.Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            smsMessage.mStatusOnIcc = bArr[0] & 7;
            int i2 = bArr[1] & 255;
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, 2, bArr2, 0, i2);
            smsMessage.parsePduFromEfRecord(bArr2);
            return smsMessage;
        } catch (java.lang.RuntimeException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, "getTPLayerLengthForPDU: is not supported in CDMA mode.");
        return 0;
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, com.android.internal.telephony.SmsHeader smsHeader) {
        return getSubmitPdu(str, str2, str3, z, smsHeader, -1);
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, com.android.internal.telephony.SmsHeader smsHeader, int i) {
        if (str3 == null || str2 == null) {
            return null;
        }
        com.android.internal.telephony.cdma.sms.UserData userData = new com.android.internal.telephony.cdma.sms.UserData();
        userData.payloadStr = str3;
        userData.userDataHeader = smsHeader;
        return privateGetSubmitPdu(str2, z, userData, i);
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, int i, byte[] bArr, boolean z) {
        com.android.internal.telephony.SmsHeader.PortAddrs portAddrs = new com.android.internal.telephony.SmsHeader.PortAddrs();
        portAddrs.destPort = i;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
        smsHeader.portAddrs = portAddrs;
        com.android.internal.telephony.cdma.sms.UserData userData = new com.android.internal.telephony.cdma.sms.UserData();
        userData.userDataHeader = smsHeader;
        userData.msgEncoding = 0;
        userData.msgEncodingSet = true;
        userData.payload = bArr;
        return privateGetSubmitPdu(str2, z, userData);
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, com.android.internal.telephony.cdma.sms.UserData userData, boolean z) {
        return privateGetSubmitPdu(str, z, userData);
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, com.android.internal.telephony.cdma.sms.UserData userData, boolean z, int i) {
        return privateGetSubmitPdu(str, z, userData, i);
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getProtocolIdentifier() {
        com.android.telephony.Rlog.w(LOG_TAG, "getProtocolIdentifier: is not supported in CDMA mode.");
        return 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplace() {
        com.android.telephony.Rlog.w(LOG_TAG, "isReplace: is not supported in CDMA mode.");
        return false;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isCphsMwiMessage() {
        com.android.telephony.Rlog.w(LOG_TAG, "isCphsMwiMessage: is not supported in CDMA mode.");
        return false;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWIClearMessage() {
        return this.mBearerData != null && this.mBearerData.numberOfMessages == 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWISetMessage() {
        return this.mBearerData != null && this.mBearerData.numberOfMessages > 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMwiDontStore() {
        return this.mBearerData != null && this.mBearerData.numberOfMessages > 0 && this.mBearerData.userData == null;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getStatus() {
        return this.status << 16;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isStatusReportMessage() {
        return this.mBearerData.messageType == 4;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplyPathPresent() {
        com.android.telephony.Rlog.w(LOG_TAG, "isReplyPathPresent: is not supported in CDMA mode.");
        return false;
    }

    public static com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength(java.lang.CharSequence charSequence, boolean z, boolean z2) {
        return com.android.internal.telephony.cdma.sms.BearerData.calcTextEncodingDetails(charSequence, z, z2);
    }

    public int getTeleService() {
        return this.mEnvelope.teleService;
    }

    public int getMessageType() {
        if (this.mEnvelope.serviceCategory != 0) {
            return 1;
        }
        return 0;
    }

    private void parsePdu(byte[] bArr) {
        int readUnsignedByte;
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        com.android.internal.telephony.cdma.sms.SmsEnvelope smsEnvelope = new com.android.internal.telephony.cdma.sms.SmsEnvelope();
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = new com.android.internal.telephony.cdma.sms.CdmaSmsAddress();
        com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress cdmaSmsSubaddress = new com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress();
        try {
            smsEnvelope.messageType = dataInputStream.readInt();
            smsEnvelope.teleService = dataInputStream.readInt();
            smsEnvelope.serviceCategory = dataInputStream.readInt();
            cdmaSmsAddress.digitMode = dataInputStream.readByte();
            cdmaSmsAddress.numberMode = dataInputStream.readByte();
            cdmaSmsAddress.ton = dataInputStream.readByte();
            cdmaSmsAddress.numberPlan = dataInputStream.readByte();
            readUnsignedByte = dataInputStream.readUnsignedByte();
            cdmaSmsAddress.numberOfDigits = readUnsignedByte;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("createFromPdu: conversion from byte array to object failed: " + e, e);
        } catch (java.lang.Exception e2) {
            com.android.telephony.Rlog.e(LOG_TAG, "createFromPdu: conversion from byte array to object failed: " + e2);
        }
        if (readUnsignedByte > bArr.length) {
            throw new java.lang.RuntimeException("createFromPdu: Invalid pdu, addr.numberOfDigits " + readUnsignedByte + " > pdu len " + bArr.length);
        }
        cdmaSmsAddress.origBytes = new byte[readUnsignedByte];
        dataInputStream.read(cdmaSmsAddress.origBytes, 0, readUnsignedByte);
        smsEnvelope.bearerReply = dataInputStream.readInt();
        smsEnvelope.replySeqNo = dataInputStream.readByte();
        smsEnvelope.errorClass = dataInputStream.readByte();
        smsEnvelope.causeCode = dataInputStream.readByte();
        int readInt = dataInputStream.readInt();
        if (readInt > bArr.length) {
            throw new java.lang.RuntimeException("createFromPdu: Invalid pdu, bearerDataLength " + readInt + " > pdu len " + bArr.length);
        }
        smsEnvelope.bearerData = new byte[readInt];
        dataInputStream.read(smsEnvelope.bearerData, 0, readInt);
        dataInputStream.close();
        this.mOriginatingAddress = cdmaSmsAddress;
        smsEnvelope.origAddress = cdmaSmsAddress;
        smsEnvelope.origSubaddress = cdmaSmsSubaddress;
        this.mEnvelope = smsEnvelope;
        this.mPdu = bArr;
        parseSms();
    }

    private void parsePduFromEfRecord(byte[] bArr) {
        int i;
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(byteArrayInputStream);
        com.android.internal.telephony.cdma.sms.SmsEnvelope smsEnvelope = new com.android.internal.telephony.cdma.sms.SmsEnvelope();
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = new com.android.internal.telephony.cdma.sms.CdmaSmsAddress();
        com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress cdmaSmsSubaddress = new com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress();
        try {
            smsEnvelope.messageType = dataInputStream.readByte();
            while (dataInputStream.available() > 0) {
                byte readByte = dataInputStream.readByte();
                int readUnsignedByte = dataInputStream.readUnsignedByte();
                byte[] bArr2 = new byte[readUnsignedByte];
                int i2 = 0;
                switch (readByte) {
                    case 0:
                        smsEnvelope.teleService = dataInputStream.readUnsignedShort();
                        com.android.telephony.Rlog.i(LOG_TAG, "teleservice = " + smsEnvelope.teleService);
                        break;
                    case 1:
                        smsEnvelope.serviceCategory = dataInputStream.readUnsignedShort();
                        break;
                    case 2:
                    case 4:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bArr2);
                        cdmaSmsAddress.digitMode = bitwiseInputStream.read(1);
                        cdmaSmsAddress.numberMode = bitwiseInputStream.read(1);
                        if (cdmaSmsAddress.digitMode != 1) {
                            i = 0;
                        } else {
                            i = bitwiseInputStream.read(3);
                            cdmaSmsAddress.ton = i;
                            if (cdmaSmsAddress.numberMode == 0) {
                                cdmaSmsAddress.numberPlan = bitwiseInputStream.read(4);
                            }
                        }
                        cdmaSmsAddress.numberOfDigits = bitwiseInputStream.read(8);
                        byte[] bArr3 = new byte[cdmaSmsAddress.numberOfDigits];
                        if (cdmaSmsAddress.digitMode == 0) {
                            while (i2 < cdmaSmsAddress.numberOfDigits) {
                                bArr3[i2] = convertDtmfToAscii((byte) (bitwiseInputStream.read(4) & 15));
                                i2++;
                            }
                        } else if (cdmaSmsAddress.digitMode == 1) {
                            if (cdmaSmsAddress.numberMode == 0) {
                                for (int i3 = 0; i3 < cdmaSmsAddress.numberOfDigits; i3++) {
                                    bArr3[i3] = (byte) (bitwiseInputStream.read(8) & 255);
                                }
                            } else if (cdmaSmsAddress.numberMode == 1) {
                                if (i == 2) {
                                    com.android.telephony.Rlog.e(LOG_TAG, "TODO: Addr is email id");
                                } else {
                                    com.android.telephony.Rlog.e(LOG_TAG, "TODO: Addr is data network address");
                                }
                            } else {
                                com.android.telephony.Rlog.e(LOG_TAG, "Addr is of incorrect type");
                            }
                        } else {
                            com.android.telephony.Rlog.e(LOG_TAG, "Incorrect Digit mode");
                        }
                        cdmaSmsAddress.origBytes = bArr3;
                        com.android.telephony.Rlog.pii(LOG_TAG, "Addr=" + cdmaSmsAddress.toString());
                        if (readByte == 2) {
                            smsEnvelope.origAddress = cdmaSmsAddress;
                            this.mOriginatingAddress = cdmaSmsAddress;
                            break;
                        } else {
                            smsEnvelope.destAddress = cdmaSmsAddress;
                            this.mRecipientAddress = cdmaSmsAddress;
                            break;
                        }
                    case 3:
                    case 5:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        com.android.internal.util.BitwiseInputStream bitwiseInputStream2 = new com.android.internal.util.BitwiseInputStream(bArr2);
                        cdmaSmsSubaddress.type = bitwiseInputStream2.read(3);
                        cdmaSmsSubaddress.odd = bitwiseInputStream2.readByteArray(1)[0];
                        int read = bitwiseInputStream2.read(8);
                        byte[] bArr4 = new byte[read];
                        while (i2 < read) {
                            bArr4[i2] = convertDtmfToAscii((byte) (bitwiseInputStream2.read(4) & 255));
                            i2++;
                        }
                        cdmaSmsSubaddress.origBytes = bArr4;
                        if (readByte == 3) {
                            smsEnvelope.origSubaddress = cdmaSmsSubaddress;
                            break;
                        } else {
                            smsEnvelope.destSubaddress = cdmaSmsSubaddress;
                            break;
                        }
                    case 6:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        smsEnvelope.bearerReply = new com.android.internal.util.BitwiseInputStream(bArr2).read(6);
                        break;
                    case 7:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        com.android.internal.util.BitwiseInputStream bitwiseInputStream3 = new com.android.internal.util.BitwiseInputStream(bArr2);
                        smsEnvelope.replySeqNo = bitwiseInputStream3.readByteArray(6)[0];
                        smsEnvelope.errorClass = bitwiseInputStream3.readByteArray(2)[0];
                        if (smsEnvelope.errorClass != 0) {
                            smsEnvelope.causeCode = bitwiseInputStream3.readByteArray(8)[0];
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        smsEnvelope.bearerData = bArr2;
                        break;
                    default:
                        throw new java.lang.Exception("unsupported parameterId (" + ((int) readByte) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            byteArrayInputStream.close();
            dataInputStream.close();
        } catch (java.lang.Exception e) {
            com.android.telephony.Rlog.e(LOG_TAG, "parsePduFromEfRecord: conversion from pdu to SmsMessage failed" + e);
        }
        this.mEnvelope = smsEnvelope;
        this.mPdu = bArr;
        parseSms();
    }

    public boolean preprocessCdmaFdeaWap() {
        try {
            com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(this.mUserData);
            if (bitwiseInputStream.read(8) != 0) {
                com.android.telephony.Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAMETER_ID");
                return false;
            }
            if (bitwiseInputStream.read(8) != 3) {
                com.android.telephony.Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAM_LEN");
                return false;
            }
            this.mBearerData.messageType = bitwiseInputStream.read(4);
            int read = (bitwiseInputStream.read(8) << 8) | bitwiseInputStream.read(8);
            this.mBearerData.messageId = read;
            this.mMessageRef = read;
            this.mBearerData.hasUserDataHeader = bitwiseInputStream.read(1) == 1;
            if (this.mBearerData.hasUserDataHeader) {
                com.android.telephony.Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier HEADER_IND");
                return false;
            }
            bitwiseInputStream.skip(3);
            if (bitwiseInputStream.read(8) != 1) {
                com.android.telephony.Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data SUBPARAMETER_ID");
                return false;
            }
            int read2 = bitwiseInputStream.read(8) * 8;
            this.mBearerData.userData.msgEncoding = bitwiseInputStream.read(5);
            if (this.mBearerData.userData.msgEncoding != 0) {
                com.android.telephony.Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data MSG_ENCODING");
                return false;
            }
            this.mBearerData.userData.numFields = bitwiseInputStream.read(8);
            int i = read2 - 13;
            int i2 = this.mBearerData.userData.numFields * 8;
            if (i2 < i) {
                i = i2;
            }
            this.mBearerData.userData.payload = bitwiseInputStream.readByteArray(i);
            this.mUserData = this.mBearerData.userData.payload;
            return true;
        } catch (com.android.internal.util.BitwiseInputStream.AccessException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "Fail to preprocess FDEA WAP: " + e);
            return false;
        }
    }

    public void parseSms() {
        if (this.mEnvelope.teleService == 262144) {
            this.mBearerData = new com.android.internal.telephony.cdma.sms.BearerData();
            if (this.mEnvelope.bearerData != null) {
                this.mBearerData.numberOfMessages = this.mEnvelope.bearerData[0] & 255;
                return;
            }
            return;
        }
        this.mBearerData = com.android.internal.telephony.cdma.sms.BearerData.decode(this.mEnvelope.bearerData);
        if (com.android.telephony.Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            com.android.telephony.Rlog.d(LOG_TAG, "MT raw BearerData = '" + com.android.internal.util.HexDump.toHexString(this.mEnvelope.bearerData) + "'");
            com.android.telephony.Rlog.d(LOG_TAG, "MT (decoded) BearerData = " + this.mBearerData);
        }
        this.mMessageRef = this.mBearerData.messageId;
        if (this.mBearerData.userData != null) {
            this.mUserData = this.mBearerData.userData.payload;
            this.mUserDataHeader = this.mBearerData.userData.userDataHeader;
            this.mMessageBody = this.mBearerData.userData.payloadStr;
            this.mReceivedEncodingType = this.mBearerData.userData.msgEncoding;
        }
        if (this.mOriginatingAddress != null) {
            decodeSmsDisplayAddress(this.mOriginatingAddress);
        }
        if (this.mRecipientAddress != null) {
            decodeSmsDisplayAddress(this.mRecipientAddress);
        }
        if (this.mBearerData.msgCenterTimeStamp != null) {
            this.mScTimeMillis = this.mBearerData.msgCenterTimeStamp.toMillis();
        }
        if (this.mBearerData.messageType == 4) {
            if (!this.mBearerData.messageStatusSet) {
                com.android.telephony.Rlog.d(LOG_TAG, "DELIVERY_ACK message without msgStatus (" + (this.mUserData == null ? "also missing" : "does have") + " userData).");
                this.status = 2;
            } else {
                this.status = this.mBearerData.errorClass << 8;
                this.status |= this.mBearerData.messageStatus;
            }
        } else if (this.mBearerData.messageType != 1 && this.mBearerData.messageType != 2) {
            throw new java.lang.RuntimeException("Unsupported message type: " + this.mBearerData.messageType);
        }
        if (this.mMessageBody != null) {
            parseMessageBody();
        } else {
            byte[] bArr = this.mUserData;
        }
    }

    private void decodeSmsDisplayAddress(com.android.internal.telephony.SmsAddress smsAddress) {
        java.lang.String orElse = android.sysprop.TelephonyProperties.operator_idp_string().orElse(null);
        smsAddress.address = new java.lang.String(smsAddress.origBytes);
        if (!android.text.TextUtils.isEmpty(orElse) && smsAddress.address.startsWith(orElse)) {
            smsAddress.address = "+" + smsAddress.address.substring(orElse.length());
        } else if (smsAddress.ton == 1 && smsAddress.address.charAt(0) != '+') {
            smsAddress.address = "+" + smsAddress.address;
        }
        com.android.telephony.Rlog.pii(LOG_TAG, " decodeSmsDisplayAddress = " + smsAddress.address);
    }

    public android.telephony.SmsCbMessage parseBroadcastSms(java.lang.String str, int i, int i2) {
        com.android.internal.telephony.cdma.sms.BearerData decode = com.android.internal.telephony.cdma.sms.BearerData.decode(this.mEnvelope.bearerData, this.mEnvelope.serviceCategory);
        if (decode == null) {
            com.android.telephony.Rlog.w(LOG_TAG, "BearerData.decode() returned null");
            return null;
        }
        if (decode.userData != null) {
            this.mReceivedEncodingType = decode.userData.msgEncoding;
        }
        if (com.android.telephony.Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            com.android.telephony.Rlog.d(LOG_TAG, "MT raw BearerData = " + com.android.internal.util.HexDump.toHexString(this.mEnvelope.bearerData));
        }
        return new android.telephony.SmsCbMessage(2, 1, decode.messageId, new android.telephony.SmsCbLocation(str), this.mEnvelope.serviceCategory, decode.getLanguage(), decode.userData.payloadStr, decode.priority, null, decode.cmasWarningInfo, i, i2);
    }

    public byte[] getEnvelopeBearerData() {
        return this.mEnvelope.bearerData;
    }

    public int getEnvelopeServiceCategory() {
        return this.mEnvelope.serviceCategory;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public com.android.internal.telephony.SmsConstants.MessageClass getMessageClass() {
        if (this.mBearerData.displayMode == 0) {
            return com.android.internal.telephony.SmsConstants.MessageClass.CLASS_0;
        }
        return com.android.internal.telephony.SmsConstants.MessageClass.UNKNOWN;
    }

    public static synchronized int getNextMessageId() {
        int intValue;
        synchronized (com.android.internal.telephony.cdma.SmsMessage.class) {
            intValue = android.sysprop.TelephonyProperties.cdma_msg_id().orElse(1).intValue();
            int i = (intValue % 65535) + 1;
            try {
                android.sysprop.TelephonyProperties.cdma_msg_id(java.lang.Integer.valueOf(i));
                if (com.android.telephony.Rlog.isLoggable(LOGGABLE_TAG, 2)) {
                    com.android.telephony.Rlog.d(LOG_TAG, "next persist.radio.cdma.msgid = " + i);
                    com.android.telephony.Rlog.d(LOG_TAG, "readback gets " + android.sysprop.TelephonyProperties.cdma_msg_id().orElse(1));
                }
            } catch (java.lang.RuntimeException e) {
                com.android.telephony.Rlog.e(LOG_TAG, "set nextMessage ID failed: " + e);
            }
        }
        return intValue;
    }

    private static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu privateGetSubmitPdu(java.lang.String str, boolean z, com.android.internal.telephony.cdma.sms.UserData userData) {
        return privateGetSubmitPdu(str, z, userData, -1);
    }

    private static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu privateGetSubmitPdu(java.lang.String str, boolean z, com.android.internal.telephony.cdma.sms.UserData userData, int i) {
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress parse = com.android.internal.telephony.cdma.sms.CdmaSmsAddress.parse(android.telephony.PhoneNumberUtils.cdmaCheckAndProcessPlusCodeForSms(str));
        if (parse == null) {
            return null;
        }
        com.android.internal.telephony.cdma.sms.BearerData bearerData = new com.android.internal.telephony.cdma.sms.BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = z;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        if (i >= 0 && i <= 3) {
            bearerData.priorityIndicatorSet = true;
            bearerData.priority = i;
        }
        bearerData.userData = userData;
        byte[] encode = com.android.internal.telephony.cdma.sms.BearerData.encode(bearerData);
        if (encode == null) {
            return null;
        }
        if (com.android.telephony.Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            com.android.telephony.Rlog.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            com.android.telephony.Rlog.d(LOG_TAG, "MO raw BearerData = '" + com.android.internal.util.HexDump.toHexString(encode) + "'");
        }
        int i2 = (!bearerData.hasUserDataHeader || userData.msgEncoding == 2) ? 4098 : 4101;
        com.android.internal.telephony.cdma.sms.SmsEnvelope smsEnvelope = new com.android.internal.telephony.cdma.sms.SmsEnvelope();
        smsEnvelope.messageType = 0;
        smsEnvelope.teleService = i2;
        smsEnvelope.destAddress = parse;
        smsEnvelope.bearerReply = 1;
        smsEnvelope.bearerData = encode;
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(100);
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            com.android.internal.telephony.cdma.SmsMessage.SubmitPdu submitPdu = new com.android.internal.telephony.cdma.SmsMessage.SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (java.io.IOException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + e);
            return null;
        }
    }

    public static com.android.internal.telephony.cdma.SmsMessage.SubmitPdu getDeliverPdu(java.lang.String str, java.lang.String str2, long j) {
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress parse;
        if (str == null || str2 == null || (parse = com.android.internal.telephony.cdma.sms.CdmaSmsAddress.parse(str)) == null) {
            return null;
        }
        com.android.internal.telephony.cdma.sms.BearerData bearerData = new com.android.internal.telephony.cdma.sms.BearerData();
        bearerData.messageType = 1;
        bearerData.messageId = 0;
        bearerData.deliveryAckReq = false;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        bearerData.userData = new com.android.internal.telephony.cdma.sms.UserData();
        bearerData.userData.payloadStr = str2;
        bearerData.msgCenterTimeStamp = com.android.internal.telephony.cdma.sms.BearerData.TimeStamp.fromMillis(j);
        byte[] encode = com.android.internal.telephony.cdma.sms.BearerData.encode(bearerData);
        if (encode == null) {
            return null;
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(100);
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(4098);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            com.android.internal.telephony.cdma.SmsMessage.SubmitPdu submitPdu = new com.android.internal.telephony.cdma.SmsMessage.SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (java.io.IOException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "creating Deliver PDU failed: " + e);
            return null;
        }
    }

    public void createPdu() {
        com.android.internal.telephony.cdma.sms.SmsEnvelope smsEnvelope = this.mEnvelope;
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = smsEnvelope.origAddress;
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(100);
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(byteArrayOutputStream));
        try {
            dataOutputStream.writeInt(smsEnvelope.messageType);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(smsEnvelope.serviceCategory);
            dataOutputStream.writeByte(cdmaSmsAddress.digitMode);
            dataOutputStream.writeByte(cdmaSmsAddress.numberMode);
            dataOutputStream.writeByte(cdmaSmsAddress.ton);
            dataOutputStream.writeByte(cdmaSmsAddress.numberPlan);
            dataOutputStream.writeByte(cdmaSmsAddress.numberOfDigits);
            dataOutputStream.write(cdmaSmsAddress.origBytes, 0, cdmaSmsAddress.origBytes.length);
            dataOutputStream.writeInt(smsEnvelope.bearerReply);
            dataOutputStream.writeByte(smsEnvelope.replySeqNo);
            dataOutputStream.writeByte(smsEnvelope.errorClass);
            dataOutputStream.writeByte(smsEnvelope.causeCode);
            dataOutputStream.writeInt(smsEnvelope.bearerData.length);
            dataOutputStream.write(smsEnvelope.bearerData, 0, smsEnvelope.bearerData.length);
            dataOutputStream.close();
            this.mPdu = byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "createPdu: conversion from object to byte array failed: " + e);
        }
    }

    public static byte convertDtmfToAscii(byte b) {
        switch (b) {
            case 0:
                return (byte) 68;
            case 1:
                return (byte) 49;
            case 2:
                return (byte) 50;
            case 3:
                return (byte) 51;
            case 4:
                return (byte) 52;
            case 5:
                return (byte) 53;
            case 6:
                return (byte) 54;
            case 7:
                return (byte) 55;
            case 8:
                return (byte) 56;
            case 9:
                return (byte) 57;
            case 10:
                return (byte) 48;
            case 11:
                return (byte) 42;
            case 12:
                return (byte) 35;
            case 13:
                return (byte) 65;
            case 14:
                return (byte) 66;
            case 15:
                return (byte) 67;
            default:
                return com.android.net.module.util.NetworkStackConstants.TCPHDR_URG;
        }
    }

    public int getNumOfVoicemails() {
        return this.mBearerData.numberOfMessages;
    }

    public byte[] getIncomingSmsFingerprint() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        byteArrayOutputStream.write(this.mEnvelope.serviceCategory);
        byteArrayOutputStream.write(this.mEnvelope.teleService);
        byteArrayOutputStream.write(this.mEnvelope.origAddress.origBytes, 0, this.mEnvelope.origAddress.origBytes.length);
        byteArrayOutputStream.write(this.mEnvelope.bearerData, 0, this.mEnvelope.bearerData.length);
        if (this.mEnvelope.origSubaddress != null && this.mEnvelope.origSubaddress.origBytes != null) {
            byteArrayOutputStream.write(this.mEnvelope.origSubaddress.origBytes, 0, this.mEnvelope.origSubaddress.origBytes.length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public java.util.ArrayList<android.telephony.cdma.CdmaSmsCbProgramData> getSmsCbProgramData() {
        return this.mBearerData.serviceCategoryProgramData;
    }
}
