package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class NotifyRespInd extends com.google.android.mms.pdu.GenericPdu {
    public NotifyRespInd(int i, byte[] bArr, int i2) throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(131);
        setMmsVersion(i);
        setTransactionId(bArr);
        setStatus(i2);
    }

    NotifyRespInd(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public int getReportAllowed() {
        return this.mPduHeaders.getOctet(145);
    }

    public void setReportAllowed(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 145);
    }

    public void setStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 149);
    }

    public int getStatus() {
        return this.mPduHeaders.getOctet(149);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
