package android.net.http;

/* loaded from: classes2.dex */
public class SslCertificate {
    private static java.lang.String ISO_8601_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    private static final java.lang.String ISSUED_BY = "issued-by";
    private static final java.lang.String ISSUED_TO = "issued-to";
    private static final java.lang.String VALID_NOT_AFTER = "valid-not-after";
    private static final java.lang.String VALID_NOT_BEFORE = "valid-not-before";
    private static final java.lang.String X509_CERTIFICATE = "x509-certificate";
    private final android.net.http.SslCertificate.DName mIssuedBy;
    private final android.net.http.SslCertificate.DName mIssuedTo;
    private final java.util.Date mValidNotAfter;
    private final java.util.Date mValidNotBefore;
    private final java.security.cert.X509Certificate mX509Certificate;

    public static android.os.Bundle saveState(android.net.http.SslCertificate sslCertificate) {
        if (sslCertificate == null) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(ISSUED_TO, sslCertificate.getIssuedTo().getDName());
        bundle.putString(ISSUED_BY, sslCertificate.getIssuedBy().getDName());
        bundle.putString(VALID_NOT_BEFORE, sslCertificate.getValidNotBefore());
        bundle.putString(VALID_NOT_AFTER, sslCertificate.getValidNotAfter());
        java.security.cert.X509Certificate x509Certificate = sslCertificate.mX509Certificate;
        if (x509Certificate != null) {
            try {
                bundle.putByteArray(X509_CERTIFICATE, x509Certificate.getEncoded());
            } catch (java.security.cert.CertificateEncodingException e) {
            }
        }
        return bundle;
    }

    public static android.net.http.SslCertificate restoreState(android.os.Bundle bundle) {
        java.security.cert.X509Certificate x509Certificate;
        if (bundle == null) {
            return null;
        }
        byte[] byteArray = bundle.getByteArray(X509_CERTIFICATE);
        if (byteArray == null) {
            x509Certificate = null;
        } else {
            try {
                x509Certificate = (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(byteArray));
            } catch (java.security.cert.CertificateException e) {
                x509Certificate = null;
            }
        }
        return new android.net.http.SslCertificate(bundle.getString(ISSUED_TO), bundle.getString(ISSUED_BY), parseDate(bundle.getString(VALID_NOT_BEFORE)), parseDate(bundle.getString(VALID_NOT_AFTER)), x509Certificate);
    }

    @java.lang.Deprecated
    public SslCertificate(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this(str, str2, parseDate(str3), parseDate(str4), null);
    }

    @java.lang.Deprecated
    public SslCertificate(java.lang.String str, java.lang.String str2, java.util.Date date, java.util.Date date2) {
        this(str, str2, date, date2, null);
    }

    public SslCertificate(java.security.cert.X509Certificate x509Certificate) {
        this(x509Certificate.getSubjectDN().getName(), x509Certificate.getIssuerDN().getName(), x509Certificate.getNotBefore(), x509Certificate.getNotAfter(), x509Certificate);
    }

    private SslCertificate(java.lang.String str, java.lang.String str2, java.util.Date date, java.util.Date date2, java.security.cert.X509Certificate x509Certificate) {
        this.mIssuedTo = new android.net.http.SslCertificate.DName(str);
        this.mIssuedBy = new android.net.http.SslCertificate.DName(str2);
        this.mValidNotBefore = cloneDate(date);
        this.mValidNotAfter = cloneDate(date2);
        this.mX509Certificate = x509Certificate;
    }

    public java.util.Date getValidNotBeforeDate() {
        return cloneDate(this.mValidNotBefore);
    }

    @java.lang.Deprecated
    public java.lang.String getValidNotBefore() {
        return formatDate(this.mValidNotBefore);
    }

    public java.util.Date getValidNotAfterDate() {
        return cloneDate(this.mValidNotAfter);
    }

    @java.lang.Deprecated
    public java.lang.String getValidNotAfter() {
        return formatDate(this.mValidNotAfter);
    }

    public android.net.http.SslCertificate.DName getIssuedTo() {
        return this.mIssuedTo;
    }

    public android.net.http.SslCertificate.DName getIssuedBy() {
        return this.mIssuedBy;
    }

    public java.security.cert.X509Certificate getX509Certificate() {
        return this.mX509Certificate;
    }

    private static java.lang.String getSerialNumber(java.security.cert.X509Certificate x509Certificate) {
        java.math.BigInteger serialNumber;
        if (x509Certificate == null || (serialNumber = x509Certificate.getSerialNumber()) == null) {
            return "";
        }
        return fingerprint(serialNumber.toByteArray());
    }

    private static java.lang.String getDigest(java.security.cert.X509Certificate x509Certificate, java.lang.String str) {
        if (x509Certificate == null) {
            return "";
        }
        try {
            return fingerprint(java.security.MessageDigest.getInstance(str).digest(x509Certificate.getEncoded()));
        } catch (java.security.NoSuchAlgorithmException e) {
            return "";
        } catch (java.security.cert.CertificateEncodingException e2) {
            return "";
        }
    }

    private static final java.lang.String fingerprint(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            com.android.internal.util.HexDump.appendByteAsHex(sb, bArr[i], true);
            i++;
            if (i != bArr.length) {
                sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public java.lang.String toString() {
        return "Issued to: " + this.mIssuedTo.getDName() + ";\nIssued by: " + this.mIssuedBy.getDName() + ";\n";
    }

    private static java.util.Date parseDate(java.lang.String str) {
        try {
            return new java.text.SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(str);
        } catch (java.text.ParseException e) {
            return null;
        }
    }

    private static java.lang.String formatDate(java.util.Date date) {
        if (date == null) {
            return "";
        }
        return new java.text.SimpleDateFormat(ISO_8601_DATE_FORMAT).format(date);
    }

    private static java.util.Date cloneDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return (java.util.Date) date.clone();
    }

    public class DName {
        private java.lang.String mCName;
        private java.lang.String mDName;
        private java.lang.String mOName;
        private java.lang.String mUName;

        public DName(java.lang.String str) {
            if (str != null) {
                this.mDName = str;
                try {
                    com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name = new com.android.internal.org.bouncycastle.asn1.x509.X509Name(str);
                    java.util.Vector values = x509Name.getValues();
                    java.util.Vector oIDs = x509Name.getOIDs();
                    for (int i = 0; i < oIDs.size(); i++) {
                        if (oIDs.elementAt(i).equals(com.android.internal.org.bouncycastle.asn1.x509.X509Name.CN)) {
                            if (this.mCName == null) {
                                this.mCName = (java.lang.String) values.elementAt(i);
                            }
                        } else if (oIDs.elementAt(i).equals(com.android.internal.org.bouncycastle.asn1.x509.X509Name.O) && this.mOName == null) {
                            this.mOName = (java.lang.String) values.elementAt(i);
                        } else if (oIDs.elementAt(i).equals(com.android.internal.org.bouncycastle.asn1.x509.X509Name.OU) && this.mUName == null) {
                            this.mUName = (java.lang.String) values.elementAt(i);
                        }
                    }
                } catch (java.lang.IllegalArgumentException e) {
                }
            }
        }

        public java.lang.String getDName() {
            return this.mDName != null ? this.mDName : "";
        }

        public java.lang.String getCName() {
            return this.mCName != null ? this.mCName : "";
        }

        public java.lang.String getOName() {
            return this.mOName != null ? this.mOName : "";
        }

        public java.lang.String getUName() {
            return this.mUName != null ? this.mUName : "";
        }
    }

    public android.view.View inflateCertificateView(android.content.Context context) {
        android.view.View inflate = android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.ssl_certificate, (android.view.ViewGroup) null);
        android.net.http.SslCertificate.DName issuedTo = getIssuedTo();
        if (issuedTo != null) {
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.to_common)).lambda$setTextAsync$0(issuedTo.getCName());
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.to_org)).lambda$setTextAsync$0(issuedTo.getOName());
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.to_org_unit)).lambda$setTextAsync$0(issuedTo.getUName());
        }
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.serial_number)).lambda$setTextAsync$0(getSerialNumber(this.mX509Certificate));
        android.net.http.SslCertificate.DName issuedBy = getIssuedBy();
        if (issuedBy != null) {
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.by_common)).lambda$setTextAsync$0(issuedBy.getCName());
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.by_org)).lambda$setTextAsync$0(issuedBy.getOName());
            ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.by_org_unit)).lambda$setTextAsync$0(issuedBy.getUName());
        }
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.issued_on)).lambda$setTextAsync$0(formatCertificateDate(context, getValidNotBeforeDate()));
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.expires_on)).lambda$setTextAsync$0(formatCertificateDate(context, getValidNotAfterDate()));
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.sha256_fingerprint)).lambda$setTextAsync$0(getDigest(this.mX509Certificate, "SHA256"));
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.sha1_fingerprint)).lambda$setTextAsync$0(getDigest(this.mX509Certificate, "SHA1"));
        return inflate;
    }

    private java.lang.String formatCertificateDate(android.content.Context context, java.util.Date date) {
        if (date == null) {
            return "";
        }
        return android.text.format.DateFormat.getMediumDateFormat(context).format(date);
    }
}
