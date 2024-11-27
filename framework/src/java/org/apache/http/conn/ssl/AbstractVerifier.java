package org.apache.http.conn.ssl;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public abstract class AbstractVerifier implements org.apache.http.conn.ssl.X509HostnameVerifier {
    private static final java.util.regex.Pattern IPV4_PATTERN = java.util.regex.Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final java.lang.String[] BAD_COUNTRY_2LDS = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", android.provider.DocumentsContract.EXTRA_INFO, "lg", "ne", "net", "or", "org"};

    static {
        java.util.Arrays.sort(BAD_COUNTRY_2LDS);
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(java.lang.String str, javax.net.ssl.SSLSocket sSLSocket) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.NullPointerException("host to verify is null");
        }
        verify(str, (java.security.cert.X509Certificate) sSLSocket.getSession().getPeerCertificates()[0]);
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier, javax.net.ssl.HostnameVerifier
    public final boolean verify(java.lang.String str, javax.net.ssl.SSLSession sSLSession) {
        try {
            verify(str, (java.security.cert.X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (javax.net.ssl.SSLException e) {
            return false;
        }
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(java.lang.String str, java.security.cert.X509Certificate x509Certificate) throws javax.net.ssl.SSLException {
        verify(str, getCNs(x509Certificate), getDNSSubjectAlts(x509Certificate));
    }

    public final void verify(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z) throws javax.net.ssl.SSLException {
        java.util.LinkedList linkedList = new java.util.LinkedList();
        if (strArr != null && strArr.length > 0 && strArr[0] != null) {
            linkedList.add(strArr[0]);
        }
        if (strArr2 != null) {
            for (java.lang.String str2 : strArr2) {
                if (str2 != null) {
                    linkedList.add(str2);
                }
            }
        }
        if (linkedList.isEmpty()) {
            throw new javax.net.ssl.SSLException("Certificate for <" + str + "> doesn't contain CN or DNS subjectAlt");
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lowerCase = str.trim().toLowerCase(java.util.Locale.ENGLISH);
        java.util.Iterator it = linkedList.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            java.lang.String lowerCase2 = ((java.lang.String) it.next()).toLowerCase(java.util.Locale.ENGLISH);
            stringBuffer.append(" <");
            stringBuffer.append(lowerCase2);
            stringBuffer.append('>');
            if (it.hasNext()) {
                stringBuffer.append(" OR");
            }
            if (lowerCase2.startsWith("*.") && lowerCase2.indexOf(46, 2) != -1 && acceptableCountryWildcard(lowerCase2) && !isIPv4Address(str)) {
                boolean endsWith = lowerCase.endsWith(lowerCase2.substring(1));
                if (endsWith && z) {
                    z2 = countDots(lowerCase) == countDots(lowerCase2);
                } else {
                    z2 = endsWith;
                }
            } else {
                z2 = lowerCase.equals(lowerCase2);
            }
            if (z2) {
                break;
            }
        }
        if (!z2) {
            throw new javax.net.ssl.SSLException("hostname in certificate didn't match: <" + str + "> !=" + ((java.lang.Object) stringBuffer));
        }
    }

    public static boolean acceptableCountryWildcard(java.lang.String str) {
        int length = str.length();
        if (length >= 7 && length <= 9) {
            int i = length - 3;
            if (str.charAt(i) == '.') {
                return java.util.Arrays.binarySearch(BAD_COUNTRY_2LDS, str.substring(2, i)) < 0;
            }
        }
        return true;
    }

    public static java.lang.String[] getCNs(java.security.cert.X509Certificate x509Certificate) {
        java.util.List<java.lang.String> allMostSpecificFirst = new org.apache.http.conn.ssl.AndroidDistinguishedNameParser(x509Certificate.getSubjectX500Principal()).getAllMostSpecificFirst("cn");
        if (!allMostSpecificFirst.isEmpty()) {
            java.lang.String[] strArr = new java.lang.String[allMostSpecificFirst.size()];
            allMostSpecificFirst.toArray(strArr);
            return strArr;
        }
        return null;
    }

    public static java.lang.String[] getDNSSubjectAlts(java.security.cert.X509Certificate x509Certificate) {
        java.util.Collection<java.util.List<?>> collection;
        java.util.LinkedList linkedList = new java.util.LinkedList();
        try {
            collection = x509Certificate.getSubjectAlternativeNames();
        } catch (java.security.cert.CertificateParsingException e) {
            java.util.logging.Logger.getLogger(org.apache.http.conn.ssl.AbstractVerifier.class.getName()).log(java.util.logging.Level.FINE, "Error parsing certificate.", (java.lang.Throwable) e);
            collection = null;
        }
        if (collection != null) {
            for (java.util.List<?> list : collection) {
                if (((java.lang.Integer) list.get(0)).intValue() == 2) {
                    linkedList.add((java.lang.String) list.get(1));
                }
            }
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        java.lang.String[] strArr = new java.lang.String[linkedList.size()];
        linkedList.toArray(strArr);
        return strArr;
    }

    public static int countDots(java.lang.String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '.') {
                i++;
            }
        }
        return i;
    }

    private static boolean isIPv4Address(java.lang.String str) {
        return IPV4_PATTERN.matcher(str).matches();
    }
}
