package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class Time extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive time;

    public static com.android.internal.org.bouncycastle.asn1.cms.Time getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public Time(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) && !(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime)) {
            throw new java.lang.IllegalArgumentException("unknown object passed to Time");
        }
        this.time = aSN1Primitive;
    }

    public Time(java.util.Date date) {
        java.util.SimpleTimeZone simpleTimeZone = new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z);
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.US);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        java.lang.String str = simpleDateFormat.format(date) + android.hardware.gnss.GnssSignalType.CODE_TYPE_Z;
        int parseInt = java.lang.Integer.parseInt(str.substring(0, 4));
        if (parseInt < 1950 || parseInt > 2049) {
            this.time = new com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime(str);
        } else {
            this.time = new com.android.internal.org.bouncycastle.asn1.DERUTCTime(str.substring(2));
        }
    }

    public Time(java.util.Date date, java.util.Locale locale) {
        java.util.SimpleTimeZone simpleTimeZone = new java.util.SimpleTimeZone(0, android.hardware.gnss.GnssSignalType.CODE_TYPE_Z);
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.US);
        simpleDateFormat.setCalendar(java.util.Calendar.getInstance(locale));
        simpleDateFormat.setTimeZone(simpleTimeZone);
        java.lang.String str = simpleDateFormat.format(date) + android.hardware.gnss.GnssSignalType.CODE_TYPE_Z;
        int parseInt = java.lang.Integer.parseInt(str.substring(0, 4));
        if (parseInt < 1950 || parseInt > 2049) {
            this.time = new com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime(str);
        } else {
            this.time = new com.android.internal.org.bouncycastle.asn1.DERUTCTime(str.substring(2));
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.Time getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.Time)) {
            return (com.android.internal.org.bouncycastle.asn1.cms.Time) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) {
            return new com.android.internal.org.bouncycastle.asn1.cms.Time((com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) {
            return new com.android.internal.org.bouncycastle.asn1.cms.Time((com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) obj);
        }
        throw new java.lang.IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public java.lang.String getTime() {
        if (this.time instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) {
            return ((com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) this.time).getAdjustedTime();
        }
        return ((com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) this.time).getTime();
    }

    public java.util.Date getDate() {
        try {
            if (this.time instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) {
                return ((com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) this.time).getAdjustedDate();
            }
            return ((com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) this.time).getDate();
        } catch (java.text.ParseException e) {
            throw new java.lang.IllegalStateException("invalid date string: " + e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.time;
    }
}
