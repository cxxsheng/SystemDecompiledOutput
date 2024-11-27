package com.android.internal.org.bouncycastle.asn1.x500.style;

/* loaded from: classes4.dex */
public class RFC4519Style extends com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle {
    public static final com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle INSTANCE;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier businessCategory = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.15").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier c = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.6").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cn = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.3").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier dc = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier description = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.13").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier destinationIndicator = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.27").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier distinguishedName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.49").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier dnQualifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.46").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier enhancedSearchGuide = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.47").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier facsimileTelephoneNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.23").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier generationQualifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.44").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier givenName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.42").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier houseIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.51").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier initials = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.43").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier internationalISDNNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.25").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier l = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.7").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier member = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.31").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier name = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.41").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier o = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.10").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ou = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.11").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier owner = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.32").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier physicalDeliveryOfficeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.19").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier postalAddress = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.16").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier postalCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.17").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier postOfficeBox = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.18").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier preferredDeliveryMethod = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.28").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier registeredAddress = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.26").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier roleOccupant = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.33").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier searchGuide = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.14").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier seeAlso = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.34").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier serialNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.5").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier sn = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.4").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier st = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.8").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier street = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.9").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier telephoneNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.20").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier teletexTerminalIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.22").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier telexNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.21").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier title = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.12").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier uid = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier uniqueMember = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.50").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier userPassword = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.35").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier x121Address = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.24").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier x500UniqueIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.45").intern();
    private static final java.util.Hashtable DefaultSymbols = new java.util.Hashtable();
    private static final java.util.Hashtable DefaultLookUp = new java.util.Hashtable();
    protected final java.util.Hashtable defaultSymbols = copyHashTable(DefaultSymbols);
    protected final java.util.Hashtable defaultLookUp = copyHashTable(DefaultLookUp);

    static {
        DefaultSymbols.put(businessCategory, "businessCategory");
        DefaultSymbols.put(c, "c");
        DefaultSymbols.put(cn, "cn");
        DefaultSymbols.put(dc, "dc");
        DefaultSymbols.put(description, "description");
        DefaultSymbols.put(destinationIndicator, "destinationIndicator");
        DefaultSymbols.put(distinguishedName, "distinguishedName");
        DefaultSymbols.put(dnQualifier, "dnQualifier");
        DefaultSymbols.put(enhancedSearchGuide, "enhancedSearchGuide");
        DefaultSymbols.put(facsimileTelephoneNumber, "facsimileTelephoneNumber");
        DefaultSymbols.put(generationQualifier, "generationQualifier");
        DefaultSymbols.put(givenName, "givenName");
        DefaultSymbols.put(houseIdentifier, "houseIdentifier");
        DefaultSymbols.put(initials, "initials");
        DefaultSymbols.put(internationalISDNNumber, "internationalISDNNumber");
        DefaultSymbols.put(l, android.app.blob.XmlTags.TAG_LEASEE);
        DefaultSymbols.put(member, "member");
        DefaultSymbols.put(name, "name");
        DefaultSymbols.put(o, "o");
        DefaultSymbols.put(ou, "ou");
        DefaultSymbols.put(owner, "owner");
        DefaultSymbols.put(physicalDeliveryOfficeName, "physicalDeliveryOfficeName");
        DefaultSymbols.put(postalAddress, android.view.View.AUTOFILL_HINT_POSTAL_ADDRESS);
        DefaultSymbols.put(postalCode, android.view.View.AUTOFILL_HINT_POSTAL_CODE);
        DefaultSymbols.put(postOfficeBox, "postOfficeBox");
        DefaultSymbols.put(preferredDeliveryMethod, "preferredDeliveryMethod");
        DefaultSymbols.put(registeredAddress, "registeredAddress");
        DefaultSymbols.put(roleOccupant, "roleOccupant");
        DefaultSymbols.put(searchGuide, "searchGuide");
        DefaultSymbols.put(seeAlso, "seeAlso");
        DefaultSymbols.put(serialNumber, "serialNumber");
        DefaultSymbols.put(sn, "sn");
        DefaultSymbols.put(st, android.provider.Telephony.BaseMmsColumns.STATUS);
        DefaultSymbols.put(street, "street");
        DefaultSymbols.put(telephoneNumber, "telephoneNumber");
        DefaultSymbols.put(teletexTerminalIdentifier, "teletexTerminalIdentifier");
        DefaultSymbols.put(telexNumber, "telexNumber");
        DefaultSymbols.put(title, "title");
        DefaultSymbols.put(uid, "uid");
        DefaultSymbols.put(uniqueMember, "uniqueMember");
        DefaultSymbols.put(userPassword, "userPassword");
        DefaultSymbols.put(x121Address, "x121Address");
        DefaultSymbols.put(x500UniqueIdentifier, "x500UniqueIdentifier");
        DefaultLookUp.put("businesscategory", businessCategory);
        DefaultLookUp.put("c", c);
        DefaultLookUp.put("cn", cn);
        DefaultLookUp.put("dc", dc);
        DefaultLookUp.put("description", description);
        DefaultLookUp.put("destinationindicator", destinationIndicator);
        DefaultLookUp.put("distinguishedname", distinguishedName);
        DefaultLookUp.put("dnqualifier", dnQualifier);
        DefaultLookUp.put("enhancedsearchguide", enhancedSearchGuide);
        DefaultLookUp.put("facsimiletelephonenumber", facsimileTelephoneNumber);
        DefaultLookUp.put("generationqualifier", generationQualifier);
        DefaultLookUp.put("givenname", givenName);
        DefaultLookUp.put("houseidentifier", houseIdentifier);
        DefaultLookUp.put("initials", initials);
        DefaultLookUp.put("internationalisdnnumber", internationalISDNNumber);
        DefaultLookUp.put(android.app.blob.XmlTags.TAG_LEASEE, l);
        DefaultLookUp.put("member", member);
        DefaultLookUp.put("name", name);
        DefaultLookUp.put("o", o);
        DefaultLookUp.put("ou", ou);
        DefaultLookUp.put("owner", owner);
        DefaultLookUp.put("physicaldeliveryofficename", physicalDeliveryOfficeName);
        DefaultLookUp.put("postaladdress", postalAddress);
        DefaultLookUp.put("postalcode", postalCode);
        DefaultLookUp.put("postofficebox", postOfficeBox);
        DefaultLookUp.put("preferreddeliverymethod", preferredDeliveryMethod);
        DefaultLookUp.put("registeredaddress", registeredAddress);
        DefaultLookUp.put("roleoccupant", roleOccupant);
        DefaultLookUp.put("searchguide", searchGuide);
        DefaultLookUp.put("seealso", seeAlso);
        DefaultLookUp.put("serialnumber", serialNumber);
        DefaultLookUp.put("sn", sn);
        DefaultLookUp.put(android.provider.Telephony.BaseMmsColumns.STATUS, st);
        DefaultLookUp.put("street", street);
        DefaultLookUp.put("telephonenumber", telephoneNumber);
        DefaultLookUp.put("teletexterminalidentifier", teletexTerminalIdentifier);
        DefaultLookUp.put("telexnumber", telexNumber);
        DefaultLookUp.put("title", title);
        DefaultLookUp.put("uid", uid);
        DefaultLookUp.put("uniquemember", uniqueMember);
        DefaultLookUp.put("userpassword", userPassword);
        DefaultLookUp.put("x121address", x121Address);
        DefaultLookUp.put("x500uniqueidentifier", x500UniqueIdentifier);
        INSTANCE = new com.android.internal.org.bouncycastle.asn1.x500.style.RFC4519Style();
    }

    protected RFC4519Style() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    protected com.android.internal.org.bouncycastle.asn1.ASN1Encodable encodeStringValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) dc)) {
            return new com.android.internal.org.bouncycastle.asn1.DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) c) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) serialNumber) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) dnQualifier) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) telephoneNumber)) {
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
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNsFromString = com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.rDNsFromString(str, this);
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr = new com.android.internal.org.bouncycastle.asn1.x500.RDN[rDNsFromString.length];
        for (int i = 0; i != rDNsFromString.length; i++) {
            rdnArr[(r0 - i) - 1] = rDNsFromString[i];
        }
        return rdnArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public java.lang.String toString(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs = x500Name.getRDNs();
        boolean z = true;
        for (int length = rDNs.length - 1; length >= 0; length--) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.appendRDN(stringBuffer, rDNs[length], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
