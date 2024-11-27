package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1GeneralizedTime extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    protected byte[] time;

    public static com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime)) {
            return getInstance(object);
        }
        return new com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    public ASN1GeneralizedTime(java.lang.String str) {
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(str);
        try {
            getDate();
        } catch (java.text.ParseException e) {
            throw new java.lang.IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1GeneralizedTime(java.util.Date date) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss'Z'", java.util.Locale.US);
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(java.util.Date date, java.util.Locale locale) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss'Z'", java.util.Locale.US);
        simpleDateFormat.setCalendar(java.util.Calendar.getInstance(java.util.Locale.US));
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        this.time = com.android.internal.org.bouncycastle.util.Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1GeneralizedTime(byte[] bArr) {
        if (bArr.length < 4) {
            throw new java.lang.IllegalArgumentException("GeneralizedTime string too short");
        }
        this.time = bArr;
        if (!isDigit(0) || !isDigit(1) || !isDigit(2) || !isDigit(3)) {
            throw new java.lang.IllegalArgumentException("illegal characters in GeneralizedTime string");
        }
    }

    public java.lang.String getTimeString() {
        return com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.time);
    }

    public java.lang.String getTime() {
        java.lang.String fromByteArray = com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.time);
        if (fromByteArray.charAt(fromByteArray.length() - 1) == 'Z') {
            return fromByteArray.substring(0, fromByteArray.length() - 1) + "GMT+00:00";
        }
        int length = fromByteArray.length() - 6;
        char charAt = fromByteArray.charAt(length);
        if ((charAt == '-' || charAt == '+') && fromByteArray.indexOf("GMT") == length - 3) {
            return fromByteArray;
        }
        int length2 = fromByteArray.length() - 5;
        char charAt2 = fromByteArray.charAt(length2);
        if (charAt2 == '-' || charAt2 == '+') {
            int i = length2 + 3;
            return fromByteArray.substring(0, length2) + "GMT" + fromByteArray.substring(length2, i) + ":" + fromByteArray.substring(i);
        }
        int length3 = fromByteArray.length() - 3;
        char charAt3 = fromByteArray.charAt(length3);
        if (charAt3 == '-' || charAt3 == '+') {
            return fromByteArray.substring(0, length3) + "GMT" + fromByteArray.substring(length3) + ":00";
        }
        return fromByteArray + calculateGMTOffset(fromByteArray);
    }

    private java.lang.String calculateGMTOffset(java.lang.String str) {
        java.lang.String str2;
        java.util.TimeZone timeZone = java.util.TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset >= 0) {
            str2 = "+";
        } else {
            rawOffset = -rawOffset;
            str2 = com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        int i = rawOffset / 3600000;
        int i2 = (rawOffset - (((i * 60) * 60) * 1000)) / 60000;
        try {
            if (timeZone.useDaylightTime()) {
                if (hasFractionalSeconds()) {
                    str = pruneFractionalSeconds(str);
                }
                if (timeZone.inDaylightTime(calculateGMTDateFormat().parse(str + "GMT" + str2 + convert(i) + ":" + convert(i2)))) {
                    i += str2.equals("+") ? 1 : -1;
                }
            }
        } catch (java.text.ParseException e) {
        }
        return "GMT" + str2 + convert(i) + ":" + convert(i2);
    }

    private java.text.SimpleDateFormat calculateGMTDateFormat() {
        java.text.SimpleDateFormat simpleDateFormat;
        if (hasFractionalSeconds()) {
            simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss.SSSz");
        } else if (hasSeconds()) {
            simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmssz");
        } else if (hasMinutes()) {
            simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmz");
        } else {
            simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHz");
        }
        simpleDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        return simpleDateFormat;
    }

    private java.lang.String pruneFractionalSeconds(java.lang.String str) {
        char charAt;
        java.lang.String substring = str.substring(14);
        int i = 1;
        while (i < substring.length() && '0' <= (charAt = substring.charAt(i)) && charAt <= '9') {
            i++;
        }
        int i2 = i - 1;
        if (i2 > 3) {
            return str.substring(0, 14) + (substring.substring(0, 4) + substring.substring(i));
        }
        if (i2 == 1) {
            return str.substring(0, 14) + (substring.substring(0, i) + "00" + substring.substring(i));
        }
        if (i2 == 2) {
            return str.substring(0, 14) + (substring.substring(0, i) + android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS + substring.substring(i));
        }
        return str;
    }

    private java.lang.String convert(int i) {
        if (i < 10) {
            return android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS + i;
        }
        return java.lang.Integer.toString(i);
    }

    public java.util.Date getDate() throws java.text.ParseException {
        java.text.SimpleDateFormat calculateGMTDateFormat;
        java.lang.String fromByteArray = com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.time);
        if (fromByteArray.endsWith(android.hardware.gnss.GnssSignalType.CODE_TYPE_Z)) {
            if (hasFractionalSeconds()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'", java.util.Locale.US);
            } else if (hasSeconds()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss'Z'", java.util.Locale.US);
            } else if (hasMinutes()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmm'Z'", java.util.Locale.US);
            } else {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHH'Z'", java.util.Locale.US);
            }
            calculateGMTDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z));
        } else if (fromByteArray.indexOf(45) > 0 || fromByteArray.indexOf(43) > 0) {
            fromByteArray = getTime();
            calculateGMTDateFormat = calculateGMTDateFormat();
        } else {
            if (hasFractionalSeconds()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss.SSS", java.util.Locale.US);
            } else if (hasSeconds()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.US);
            } else if (hasMinutes()) {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmm", java.util.Locale.US);
            } else {
                calculateGMTDateFormat = new java.text.SimpleDateFormat("yyyyMMddHH", java.util.Locale.US);
            }
            calculateGMTDateFormat.setTimeZone(new java.util.SimpleTimeZone(0, java.util.TimeZone.getDefault().getID()));
        }
        if (hasFractionalSeconds()) {
            fromByteArray = pruneFractionalSeconds(fromByteArray);
        }
        return com.android.internal.org.bouncycastle.asn1.DateUtil.epochAdjust(calculateGMTDateFormat.parse(fromByteArray));
    }

    protected boolean hasFractionalSeconds() {
        for (int i = 0; i != this.time.length; i++) {
            if (this.time[i] == 46 && i == 14) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasSeconds() {
        return isDigit(12) && isDigit(13);
    }

    protected boolean hasMinutes() {
        return isDigit(10) && isDigit(11);
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
        aSN1OutputStream.writeEncoded(z, 24, this.time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime(this.time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime(this.time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.time, ((com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) aSN1Primitive).time);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.time);
    }
}
