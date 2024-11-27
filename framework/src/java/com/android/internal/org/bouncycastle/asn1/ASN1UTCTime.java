package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1UTCTime extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    private byte[] time;

    public static com.android.internal.org.bouncycastle.asn1.ASN1UTCTime getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1UTCTime getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime)) {
            return getInstance(object);
        }
        return new com.android.internal.org.bouncycastle.asn1.ASN1UTCTime(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    public ASN1UTCTime(java.lang.String str) {
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(str);
        try {
            getDate();
        } catch (java.text.ParseException e) {
            throw new java.lang.IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1UTCTime(java.util.Date date) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyMMddHHmmss'Z'", java.util.Locale.US);
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1UTCTime(java.util.Date date, java.util.Locale locale) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyMMddHHmmss'Z'", java.util.Locale.US);
        simpleDateFormat.setCalendar(java.util.Calendar.getInstance(locale));
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1UTCTime(byte[] bArr) {
        if (bArr.length < 2) {
            throw new java.lang.IllegalArgumentException("UTCTime string too short");
        }
        this.time = bArr;
        if (!isDigit(0) || !isDigit(1)) {
            throw new java.lang.IllegalArgumentException("illegal characters in UTCTime string");
        }
    }

    public java.util.Date getDate() throws java.text.ParseException {
        return com.android.internal.org.bouncycastle.asn1.DateUtil.epochAdjust(new java.text.SimpleDateFormat("yyMMddHHmmssz", java.util.Locale.US).parse(getTime()));
    }

    public java.util.Date getAdjustedDate() throws java.text.ParseException {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmssz", java.util.Locale.US);
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        return com.android.internal.org.bouncycastle.asn1.DateUtil.epochAdjust(simpleDateFormat.parse(getAdjustedTime()));
    }

    public java.lang.String getTime() {
        java.lang.String fromByteArray = com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.time);
        if (fromByteArray.indexOf(45) < 0 && fromByteArray.indexOf(43) < 0) {
            if (fromByteArray.length() == 11) {
                return fromByteArray.substring(0, 10) + "00GMT+00:00";
            }
            return fromByteArray.substring(0, 12) + "GMT+00:00";
        }
        int indexOf = fromByteArray.indexOf(45);
        if (indexOf < 0) {
            indexOf = fromByteArray.indexOf(43);
        }
        if (indexOf == fromByteArray.length() - 3) {
            fromByteArray = fromByteArray + "00";
        }
        return indexOf == 10 ? fromByteArray.substring(0, 10) + "00GMT" + fromByteArray.substring(10, 13) + ":" + fromByteArray.substring(13, 15) : fromByteArray.substring(0, 12) + "GMT" + fromByteArray.substring(12, 15) + ":" + fromByteArray.substring(15, 17);
    }

    public java.lang.String getAdjustedTime() {
        java.lang.String time = getTime();
        if (time.charAt(0) < '5') {
            return "20" + time;
        }
        return "19" + time;
    }

    private boolean isDigit(int i) {
        return this.time.length > i && this.time[i] >= 48 && this.time[i] <= 57;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        int length = this.time.length;
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 23, this.time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.time, ((com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) aSN1Primitive).time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.time);
    }

    public java.lang.String toString() {
        return com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.time);
    }
}
