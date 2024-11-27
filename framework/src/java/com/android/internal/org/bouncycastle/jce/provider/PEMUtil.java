package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PEMUtil {
    private final java.lang.String _footer1;
    private final java.lang.String _footer2;
    private final java.lang.String _header1;
    private final java.lang.String _header2;

    PEMUtil(java.lang.String str) {
        this._header1 = "-----BEGIN " + str + "-----";
        this._header2 = "-----BEGIN X509 " + str + "-----";
        this._footer1 = "-----END " + str + "-----";
        this._footer2 = "-----END X509 " + str + "-----";
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        if (r0.length() == 0) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String readLine(java.io.InputStream inputStream) throws java.io.IOException {
        int read;
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        while (true) {
            read = inputStream.read();
            if (read == 13 || read == 10 || read < 0) {
                break;
            }
            if (read != 13) {
                stringBuffer.append((char) read);
            }
        }
        if (read < 0) {
            return null;
        }
        return stringBuffer.toString();
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Sequence readPEMObject(java.io.InputStream inputStream) throws java.io.IOException {
        java.lang.String readLine;
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        do {
            readLine = readLine(inputStream);
            if (readLine == null || readLine.startsWith(this._header1)) {
                break;
            }
        } while (!readLine.startsWith(this._header2));
        while (true) {
            java.lang.String readLine2 = readLine(inputStream);
            if (readLine2 == null || readLine2.startsWith(this._footer1) || readLine2.startsWith(this._footer2)) {
                break;
            }
            stringBuffer.append(readLine2);
        }
        if (stringBuffer.length() != 0) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(com.android.internal.org.bouncycastle.util.encoders.Base64.decode(stringBuffer.toString())).readObject();
            if (!(readObject instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
                throw new java.io.IOException("malformed PEM data encountered");
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) readObject;
        }
        return null;
    }
}
