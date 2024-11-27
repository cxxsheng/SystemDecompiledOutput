package com.android.internal.org.bouncycastle.util.io.pem;

/* loaded from: classes4.dex */
public class PemReader extends java.io.BufferedReader {
    private static final java.lang.String BEGIN = "-----BEGIN ";
    private static final java.lang.String END = "-----END ";

    public PemReader(java.io.Reader reader) {
        super(reader);
    }

    public com.android.internal.org.bouncycastle.util.io.pem.PemObject readPemObject() throws java.io.IOException {
        java.lang.String substring;
        int indexOf;
        java.lang.String readLine = readLine();
        while (readLine != null && !readLine.startsWith(BEGIN)) {
            readLine = readLine();
        }
        if (readLine != null && (indexOf = (substring = readLine.substring(BEGIN.length())).indexOf(45)) > 0 && substring.endsWith("-----") && substring.length() - indexOf == 5) {
            return loadObject(substring.substring(0, indexOf));
        }
        return null;
    }

    private com.android.internal.org.bouncycastle.util.io.pem.PemObject loadObject(java.lang.String str) throws java.io.IOException {
        java.lang.String readLine;
        java.lang.String str2 = END + str;
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            readLine = readLine();
            if (readLine == null) {
                break;
            }
            if (readLine.indexOf(":") >= 0) {
                int indexOf = readLine.indexOf(58);
                arrayList.add(new com.android.internal.org.bouncycastle.util.io.pem.PemHeader(readLine.substring(0, indexOf), readLine.substring(indexOf + 1).trim()));
            } else {
                if (readLine.indexOf(str2) != -1) {
                    break;
                }
                stringBuffer.append(readLine.trim());
            }
        }
        if (readLine == null) {
            throw new java.io.IOException(str2 + " not found");
        }
        return new com.android.internal.org.bouncycastle.util.io.pem.PemObject(str, arrayList, com.android.internal.org.bouncycastle.util.encoders.Base64.decode(stringBuffer.toString()));
    }
}
