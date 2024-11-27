package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class SendConf extends com.google.android.mms.pdu.GenericPdu {
    public SendConf() throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(129);
    }

    SendConf(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getResponseStatus() {
        return this.mPduHeaders.getOctet(146);
    }

    public void setResponseStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 146);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
