package com.android.internal.org.bouncycastle.asn1.x500.style;

/* loaded from: classes4.dex */
public class BCStyle extends com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle {
    public static final com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle INSTANCE;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier C = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.6").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier O = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.10").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier OU = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.11").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier T = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.12").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CN = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.3").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SN = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.5").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier STREET = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.9").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SERIALNUMBER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.5").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier L = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.7").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ST = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.8").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SURNAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.4").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GIVENNAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.42").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier INITIALS = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.43").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GENERATION = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.44").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.45").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DESCRIPTION = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.13").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier BUSINESS_CATEGORY = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.15").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier POSTAL_CODE = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.17").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DN_QUALIFIER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.46").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PSEUDONYM = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.65").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ROLE = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.72").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DATE_OF_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PLACE_OF_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GENDER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NAME_AT_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.36.8.3.14").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier POSTAL_ADDRESS = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.16").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DMD_NAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.54").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier TELEPHONE_NUMBER = com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_at_telephoneNumber;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NAME = com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_at_name;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ORGANIZATION_IDENTIFIER = com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_at_organizationIdentifier;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier EmailAddress = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UnstructuredName = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UnstructuredAddress = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier E = EmailAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DC = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UID = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    private static final java.util.Hashtable DefaultSymbols = new java.util.Hashtable();
    private static final java.util.Hashtable DefaultLookUp = new java.util.Hashtable();
    protected final java.util.Hashtable defaultSymbols = copyHashTable(DefaultSymbols);
    protected final java.util.Hashtable defaultLookUp = copyHashTable(DefaultLookUp);

    static {
        DefaultSymbols.put(C, android.hardware.gnss.GnssSignalType.CODE_TYPE_C);
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, android.hardware.gnss.GnssSignalType.CODE_TYPE_L);
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SERIALNUMBER, "SERIALNUMBER");
        DefaultSymbols.put(EmailAddress, "E");
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(STREET, "STREET");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");
        DefaultSymbols.put(DESCRIPTION, "DESCRIPTION");
        DefaultSymbols.put(ROLE, "ROLE");
        DefaultSymbols.put(UnstructuredAddress, "unstructuredAddress");
        DefaultSymbols.put(UnstructuredName, "unstructuredName");
        DefaultSymbols.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
        DefaultSymbols.put(DN_QUALIFIER, "DN");
        DefaultSymbols.put(PSEUDONYM, "Pseudonym");
        DefaultSymbols.put(POSTAL_ADDRESS, "PostalAddress");
        DefaultSymbols.put(NAME_AT_BIRTH, "NameAtBirth");
        DefaultSymbols.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
        DefaultSymbols.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
        DefaultSymbols.put(GENDER, "Gender");
        DefaultSymbols.put(PLACE_OF_BIRTH, "PlaceOfBirth");
        DefaultSymbols.put(DATE_OF_BIRTH, "DateOfBirth");
        DefaultSymbols.put(POSTAL_CODE, "PostalCode");
        DefaultSymbols.put(BUSINESS_CATEGORY, "BusinessCategory");
        DefaultSymbols.put(TELEPHONE_NUMBER, "TelephoneNumber");
        DefaultSymbols.put(NAME, "Name");
        DefaultSymbols.put(ORGANIZATION_IDENTIFIER, "organizationIdentifier");
        DefaultLookUp.put("c", C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put(android.app.blob.XmlTags.TAG_LEASEE, L);
        DefaultLookUp.put(android.provider.Telephony.BaseMmsColumns.STATUS, ST);
        DefaultLookUp.put("sn", SURNAME);
        DefaultLookUp.put("serialnumber", SERIALNUMBER);
        DefaultLookUp.put("street", STREET);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
        DefaultLookUp.put("description", DESCRIPTION);
        DefaultLookUp.put(android.content.Context.ROLE_SERVICE, ROLE);
        DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
        DefaultLookUp.put("unstructuredname", UnstructuredName);
        DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        DefaultLookUp.put("dn", DN_QUALIFIER);
        DefaultLookUp.put("pseudonym", PSEUDONYM);
        DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
        DefaultLookUp.put("nameatbirth", NAME_AT_BIRTH);
        DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        DefaultLookUp.put("gender", GENDER);
        DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
        DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
        DefaultLookUp.put("postalcode", POSTAL_CODE);
        DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
        DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
        DefaultLookUp.put("name", NAME);
        DefaultLookUp.put("organizationidentifier", ORGANIZATION_IDENTIFIER);
        INSTANCE = new com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle();
    }

    protected BCStyle() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    protected com.android.internal.org.bouncycastle.asn1.ASN1Encodable encodeStringValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) EmailAddress) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) DC)) {
            return new com.android.internal.org.bouncycastle.asn1.DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) DATE_OF_BIRTH)) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime(str);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) C) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) SN) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) DN_QUALIFIER) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) TELEPHONE_NUMBER)) {
            return new com.android.internal.org.bouncycastle.asn1.DERPrintableString(str);
        }
        return super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public java.lang.String oidToDisplayName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (java.lang.String) DefaultSymbols.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public java.lang.String[] oidToAttrNames(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier attrNameToOID(java.lang.String str) {
        return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public com.android.internal.org.bouncycastle.asn1.x500.RDN[] fromString(java.lang.String str) {
        return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.rDNsFromString(str, this);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public java.lang.String toString(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        boolean z = true;
        for (com.android.internal.org.bouncycastle.asn1.x500.RDN rdn : x500Name.getRDNs()) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.appendRDN(stringBuffer, rdn, this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
