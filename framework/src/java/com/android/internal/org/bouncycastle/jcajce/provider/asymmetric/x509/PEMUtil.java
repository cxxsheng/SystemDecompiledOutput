package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class PEMUtil {
    private final com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries[] _supportedBoundaries;

    private class Boundaries {
        private final java.lang.String _footer;
        private final java.lang.String _header;

        private Boundaries(java.lang.String str) {
            this._header = "-----BEGIN " + str + "-----";
            this._footer = "-----END " + str + "-----";
        }

        public boolean isTheExpectedHeader(java.lang.String str) {
            return str.startsWith(this._header);
        }

        public boolean isTheExpectedFooter(java.lang.String str) {
            return str.startsWith(this._footer);
        }
    }

    PEMUtil(java.lang.String str) {
        this._supportedBoundaries = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries[]{new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries(str), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries("X509 " + str), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries("PKCS7")};
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:
    
        if (r0.length() == 0) goto L33;
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
            stringBuffer.append((char) read);
        }
        if (read < 0) {
            if (stringBuffer.length() == 0) {
                return null;
            }
            return stringBuffer.toString();
        }
        if (read == 13) {
            inputStream.mark(1);
            int read2 = inputStream.read();
            if (read2 == 10) {
                inputStream.mark(1);
            }
            if (read2 > 0) {
                inputStream.reset();
            }
        }
        return stringBuffer.toString();
    }

    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries getBoundaries(java.lang.String str) {
        for (int i = 0; i != this._supportedBoundaries.length; i++) {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries boundaries = this._supportedBoundaries[i];
            if (boundaries.isTheExpectedHeader(str) || boundaries.isTheExpectedFooter(str)) {
                return boundaries;
            }
        }
        return null;
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Sequence readPEMObject(java.io.InputStream inputStream) throws java.io.IOException {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries boundaries = null;
        while (boundaries == null) {
            java.lang.String readLine = readLine(inputStream);
            if (readLine == null) {
                break;
            }
            boundaries = getBoundaries(readLine);
            if (boundaries != null && !boundaries.isTheExpectedHeader(readLine)) {
                throw new java.io.IOException("malformed PEM data: found footer where header was expected");
            }
        }
        if (boundaries == null) {
            throw new java.io.IOException("malformed PEM data: no header found");
        }
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.Boundaries boundaries2 = null;
        while (boundaries2 == null) {
            java.lang.String readLine2 = readLine(inputStream);
            if (readLine2 == null) {
                break;
            }
            boundaries2 = getBoundaries(readLine2);
            if (boundaries2 != null) {
                if (!boundaries.isTheExpectedFooter(readLine2)) {
                    throw new java.io.IOException("malformed PEM data: header/footer mismatch");
                }
            } else {
                stringBuffer.append(readLine2);
            }
        }
        if (boundaries2 == null) {
            throw new java.io.IOException("malformed PEM data: no footer found");
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.util.encoders.Base64.decode(stringBuffer.toString()));
        } catch (java.lang.Exception e) {
            throw new java.io.IOException("malformed PEM data encountered");
        }
    }
}
