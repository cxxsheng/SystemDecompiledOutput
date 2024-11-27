package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509Name extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.util.Vector added;
    private com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter converter;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private java.util.Vector ordering;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    private java.util.Vector values;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier C = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.6");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier O = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.10");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier OU = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.11");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier T = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.12");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CN = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SN = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.5");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier STREET = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.9");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SERIALNUMBER = SN;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier L = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.7");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ST = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.8");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SURNAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GIVENNAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.42");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier INITIALS = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.43");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GENERATION = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.44");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.45");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier BUSINESS_CATEGORY = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.15");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier POSTAL_CODE = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.17");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DN_QUALIFIER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.46");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PSEUDONYM = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.65");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DATE_OF_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PLACE_OF_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier GENDER = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NAME_AT_BIRTH = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.36.8.3.14");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier POSTAL_ADDRESS = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.16");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DMD_NAME = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.54");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier TELEPHONE_NUMBER = com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_at_telephoneNumber;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NAME = com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_at_name;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier EmailAddress = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UnstructuredName = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UnstructuredAddress = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier E = EmailAddress;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DC = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier UID = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    public static boolean DefaultReverse = false;
    public static final java.util.Hashtable DefaultSymbols = new java.util.Hashtable();
    public static final java.util.Hashtable RFC2253Symbols = new java.util.Hashtable();
    public static final java.util.Hashtable RFC1779Symbols = new java.util.Hashtable();
    public static final java.util.Hashtable DefaultLookUp = new java.util.Hashtable();
    public static final java.util.Hashtable OIDLookUp = DefaultSymbols;
    public static final java.util.Hashtable SymbolLookUp = DefaultLookUp;
    private static final java.lang.Boolean TRUE = java.lang.Boolean.TRUE;
    private static final java.lang.Boolean FALSE = java.lang.Boolean.FALSE;

    static {
        DefaultSymbols.put(C, android.hardware.gnss.GnssSignalType.CODE_TYPE_C);
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, android.hardware.gnss.GnssSignalType.CODE_TYPE_L);
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SN, "SERIALNUMBER");
        DefaultSymbols.put(EmailAddress, "E");
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(STREET, "STREET");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");
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
        RFC2253Symbols.put(C, android.hardware.gnss.GnssSignalType.CODE_TYPE_C);
        RFC2253Symbols.put(O, "O");
        RFC2253Symbols.put(OU, "OU");
        RFC2253Symbols.put(CN, "CN");
        RFC2253Symbols.put(L, android.hardware.gnss.GnssSignalType.CODE_TYPE_L);
        RFC2253Symbols.put(ST, "ST");
        RFC2253Symbols.put(STREET, "STREET");
        RFC2253Symbols.put(DC, "DC");
        RFC2253Symbols.put(UID, "UID");
        RFC1779Symbols.put(C, android.hardware.gnss.GnssSignalType.CODE_TYPE_C);
        RFC1779Symbols.put(O, "O");
        RFC1779Symbols.put(OU, "OU");
        RFC1779Symbols.put(CN, "CN");
        RFC1779Symbols.put(L, android.hardware.gnss.GnssSignalType.CODE_TYPE_L);
        RFC1779Symbols.put(ST, "ST");
        RFC1779Symbols.put(STREET, "STREET");
        DefaultLookUp.put("c", C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put(android.app.blob.XmlTags.TAG_LEASEE, L);
        DefaultLookUp.put(android.provider.Telephony.BaseMmsColumns.STATUS, ST);
        DefaultLookUp.put("sn", SN);
        DefaultLookUp.put("serialnumber", SN);
        DefaultLookUp.put("street", STREET);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
        DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
        DefaultLookUp.put("unstructuredname", UnstructuredName);
        DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        DefaultLookUp.put("dn", DN_QUALIFIER);
        DefaultLookUp.put("pseudonym", PSEUDONYM);
        DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
        DefaultLookUp.put("nameofbirth", NAME_AT_BIRTH);
        DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        DefaultLookUp.put("gender", GENDER);
        DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
        DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
        DefaultLookUp.put("postalcode", POSTAL_CODE);
        DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
        DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
        DefaultLookUp.put("name", NAME);
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.X509Name getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.X509Name getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Name) {
            return (com.android.internal.org.bouncycastle.asn1.x509.X509Name) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.X500Name) {
            return new com.android.internal.org.bouncycastle.asn1.x509.X509Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(((com.android.internal.org.bouncycastle.asn1.x500.X500Name) obj).toASN1Primitive()));
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.X509Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    protected X509Name() {
        this.converter = null;
        this.ordering = new java.util.Vector();
        this.values = new java.util.Vector();
        this.added = new java.util.Vector();
    }

    public X509Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.converter = null;
        this.ordering = new java.util.Vector();
        this.values = new java.util.Vector();
        this.added = new java.util.Vector();
        this.seq = aSN1Sequence;
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive());
            int i = 0;
            while (i < aSN1Set.size()) {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Set.getObjectAt(i).toASN1Primitive());
                if (aSN1Sequence2.size() == 2) {
                    this.ordering.addElement(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(0)));
                    com.android.internal.org.bouncycastle.asn1.ASN1Encodable objectAt = aSN1Sequence2.getObjectAt(1);
                    if ((objectAt instanceof com.android.internal.org.bouncycastle.asn1.ASN1String) && !(objectAt instanceof com.android.internal.org.bouncycastle.asn1.DERUniversalString)) {
                        java.lang.String string = ((com.android.internal.org.bouncycastle.asn1.ASN1String) objectAt).getString();
                        if (string.length() > 0 && string.charAt(0) == '#') {
                            this.values.addElement("\\" + string);
                        } else {
                            this.values.addElement(string);
                        }
                    } else {
                        try {
                            this.values.addElement("#" + bytesToString(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(objectAt.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER))));
                        } catch (java.io.IOException e) {
                            throw new java.lang.IllegalArgumentException("cannot encode value");
                        }
                    }
                    this.added.addElement(i != 0 ? TRUE : FALSE);
                    i++;
                } else {
                    throw new java.lang.IllegalArgumentException("badly sized pair");
                }
            }
        }
    }

    public X509Name(java.util.Hashtable hashtable) {
        this((java.util.Vector) null, hashtable);
    }

    public X509Name(java.util.Vector vector, java.util.Hashtable hashtable) {
        this(vector, hashtable, new com.android.internal.org.bouncycastle.asn1.x509.X509DefaultEntryConverter());
    }

    public X509Name(java.util.Vector vector, java.util.Hashtable hashtable, com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new java.util.Vector();
        this.values = new java.util.Vector();
        this.added = new java.util.Vector();
        this.converter = x509NameEntryConverter;
        if (vector != null) {
            for (int i = 0; i != vector.size(); i++) {
                this.ordering.addElement(vector.elementAt(i));
                this.added.addElement(FALSE);
            }
        } else {
            java.util.Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                this.ordering.addElement(keys.nextElement());
                this.added.addElement(FALSE);
            }
        }
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i2);
            if (hashtable.get(aSN1ObjectIdentifier) == null) {
                throw new java.lang.IllegalArgumentException("No attribute for object id - " + aSN1ObjectIdentifier.getId() + " - passed to distinguished name");
            }
            this.values.addElement(hashtable.get(aSN1ObjectIdentifier));
        }
    }

    public X509Name(java.util.Vector vector, java.util.Vector vector2) {
        this(vector, vector2, new com.android.internal.org.bouncycastle.asn1.x509.X509DefaultEntryConverter());
    }

    public X509Name(java.util.Vector vector, java.util.Vector vector2, com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new java.util.Vector();
        this.values = new java.util.Vector();
        this.added = new java.util.Vector();
        this.converter = x509NameEntryConverter;
        if (vector.size() != vector2.size()) {
            throw new java.lang.IllegalArgumentException("oids vector must be same length as values.");
        }
        for (int i = 0; i < vector.size(); i++) {
            this.ordering.addElement(vector.elementAt(i));
            this.values.addElement(vector2.elementAt(i));
            this.added.addElement(FALSE);
        }
    }

    public X509Name(java.lang.String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public X509Name(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, java.lang.String str) {
        this(z, DefaultLookUp, str);
    }

    public X509Name(boolean z, java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter x509NameEntryConverter) {
        this(z, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, java.util.Hashtable hashtable, java.lang.String str) {
        this(z, hashtable, str, new com.android.internal.org.bouncycastle.asn1.x509.X509DefaultEntryConverter());
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier decodeOID(java.lang.String str, java.util.Hashtable hashtable) {
        java.lang.String trim = str.trim();
        if (com.android.internal.org.bouncycastle.util.Strings.toUpperCase(trim).startsWith("OID.")) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(trim.substring(4));
        }
        if (trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
            return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(trim);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) hashtable.get(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(trim));
        if (aSN1ObjectIdentifier == null) {
            throw new java.lang.IllegalArgumentException("Unknown object id - " + trim + " - passed to distinguished name");
        }
        return aSN1ObjectIdentifier;
    }

    private java.lang.String unescape(java.lang.String str) {
        int i;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i != charArray.length) {
            char c = charArray[i];
            if (c != ' ') {
                z3 = true;
            }
            if (c == '\"') {
                if (!z) {
                    z2 = !z2;
                } else {
                    stringBuffer.append(c);
                }
                z = false;
            } else if (c == '\\' && !z && !z2) {
                i2 = stringBuffer.length();
                z = true;
            } else if (c != ' ' || z || z3) {
                stringBuffer.append(c);
                z = false;
            }
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i2 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public X509Name(boolean z, java.util.Hashtable hashtable, java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new java.util.Vector();
        this.values = new java.util.Vector();
        this.added = new java.util.Vector();
        this.converter = x509NameEntryConverter;
        com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer x509NameTokenizer = new com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer(str);
        while (x509NameTokenizer.hasMoreTokens()) {
            java.lang.String nextToken = x509NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer x509NameTokenizer2 = new com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer(nextToken, '+');
                addEntry(hashtable, x509NameTokenizer2.nextToken(), FALSE);
                while (x509NameTokenizer2.hasMoreTokens()) {
                    addEntry(hashtable, x509NameTokenizer2.nextToken(), TRUE);
                }
            } else {
                addEntry(hashtable, nextToken, FALSE);
            }
        }
        if (z) {
            java.util.Vector vector = new java.util.Vector();
            java.util.Vector vector2 = new java.util.Vector();
            java.util.Vector vector3 = new java.util.Vector();
            int i = 1;
            for (int i2 = 0; i2 < this.ordering.size(); i2++) {
                if (!((java.lang.Boolean) this.added.elementAt(i2)).booleanValue()) {
                    vector.insertElementAt(this.ordering.elementAt(i2), 0);
                    vector2.insertElementAt(this.values.elementAt(i2), 0);
                    vector3.insertElementAt(this.added.elementAt(i2), 0);
                    i = 1;
                } else {
                    vector.insertElementAt(this.ordering.elementAt(i2), i);
                    vector2.insertElementAt(this.values.elementAt(i2), i);
                    vector3.insertElementAt(this.added.elementAt(i2), i);
                    i++;
                }
            }
            this.ordering = vector;
            this.values = vector2;
            this.added = vector3;
        }
    }

    private void addEntry(java.util.Hashtable hashtable, java.lang.String str, java.lang.Boolean bool) {
        com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer x509NameTokenizer = new com.android.internal.org.bouncycastle.asn1.x509.X509NameTokenizer(str, '=');
        java.lang.String nextToken = x509NameTokenizer.nextToken();
        if (!x509NameTokenizer.hasMoreTokens()) {
            throw new java.lang.IllegalArgumentException("badly formatted directory string");
        }
        java.lang.String nextToken2 = x509NameTokenizer.nextToken();
        this.ordering.addElement(decodeOID(nextToken, hashtable));
        this.values.addElement(unescape(nextToken2));
        this.added.addElement(bool);
    }

    public java.util.Vector getOIDs() {
        java.util.Vector vector = new java.util.Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            vector.addElement(this.ordering.elementAt(i));
        }
        return vector;
    }

    public java.util.Vector getValues() {
        java.util.Vector vector = new java.util.Vector();
        for (int i = 0; i != this.values.size(); i++) {
            vector.addElement(this.values.elementAt(i));
        }
        return vector;
    }

    public java.util.Vector getValues(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.util.Vector vector = new java.util.Vector();
        for (int i = 0; i != this.values.size(); i++) {
            if (this.ordering.elementAt(i).equals(aSN1ObjectIdentifier)) {
                java.lang.String str = (java.lang.String) this.values.elementAt(i);
                if (str.length() > 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
                    vector.addElement(str.substring(1));
                } else {
                    vector.addElement(str);
                }
            }
        }
        return vector;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (this.seq == null) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = null;
            int i = 0;
            while (i != this.ordering.size()) {
                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector3 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i);
                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                aSN1EncodableVector3.add(this.converter.getConvertedValue(aSN1ObjectIdentifier2, (java.lang.String) this.values.elementAt(i)));
                if (aSN1ObjectIdentifier == null || ((java.lang.Boolean) this.added.elementAt(i)).booleanValue()) {
                    aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector3));
                } else {
                    aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector3));
                }
                i++;
                aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
            }
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2));
            this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }

    public boolean equals(java.lang.Object obj, boolean z) {
        if (!z) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Name) && !(obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name = getInstance(obj);
            int size = this.ordering.size();
            if (size != x509Name.ordering.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i)).equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) x509Name.ordering.elementAt(i)) || !equivalentStrings((java.lang.String) this.values.elementAt(i), (java.lang.String) x509Name.values.elementAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i = 0; i != this.ordering.size(); i++) {
            java.lang.String stripInternalSpaces = stripInternalSpaces(canonicalize((java.lang.String) this.values.elementAt(i)));
            this.hashCodeValue ^= this.ordering.elementAt(i).hashCode();
            this.hashCodeValue = stripInternalSpaces.hashCode() ^ this.hashCodeValue;
        }
        return this.hashCodeValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public boolean equals(java.lang.Object obj) {
        int i;
        int i2;
        int i3;
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Name) && !(obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name = getInstance(obj);
            int size = this.ordering.size();
            if (size != x509Name.ordering.size()) {
                return false;
            }
            boolean[] zArr = new boolean[size];
            if (this.ordering.elementAt(0).equals(x509Name.ordering.elementAt(0))) {
                i3 = 1;
                i2 = size;
                i = 0;
            } else {
                i = size - 1;
                i2 = -1;
                i3 = -1;
            }
            while (i != i2) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i);
                java.lang.String str = (java.lang.String) this.values.elementAt(i);
                int i4 = 0;
                while (true) {
                    if (i4 >= size) {
                        z = false;
                        break;
                    }
                    if (zArr[i4] || !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) x509Name.ordering.elementAt(i4)) || !equivalentStrings(str, (java.lang.String) x509Name.values.elementAt(i4))) {
                        i4++;
                    } else {
                        zArr[i4] = true;
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
                i += i3;
            }
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    private boolean equivalentStrings(java.lang.String str, java.lang.String str2) {
        java.lang.String canonicalize = canonicalize(str);
        java.lang.String canonicalize2 = canonicalize(str2);
        if (!canonicalize.equals(canonicalize2) && !stripInternalSpaces(canonicalize).equals(stripInternalSpaces(canonicalize2))) {
            return false;
        }
        return true;
    }

    private java.lang.String canonicalize(java.lang.String str) {
        java.lang.String lowerCase = com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str.trim());
        if (lowerCase.length() > 0 && lowerCase.charAt(0) == '#') {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable decodeObject = decodeObject(lowerCase);
            if (decodeObject instanceof com.android.internal.org.bouncycastle.asn1.ASN1String) {
                return com.android.internal.org.bouncycastle.util.Strings.toLowerCase(((com.android.internal.org.bouncycastle.asn1.ASN1String) decodeObject).getString().trim());
            }
            return lowerCase;
        }
        return lowerCase;
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive decodeObject(java.lang.String str) {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("unknown encoding in name: " + e);
        }
    }

    private java.lang.String stripInternalSpaces(java.lang.String str) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            stringBuffer.append(charAt);
            int i = 1;
            while (i < str.length()) {
                char charAt2 = str.charAt(i);
                if (charAt != ' ' || charAt2 != ' ') {
                    stringBuffer.append(charAt2);
                }
                i++;
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    private void appendValue(java.lang.StringBuffer stringBuffer, java.util.Hashtable hashtable, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        java.lang.String str2 = (java.lang.String) hashtable.get(aSN1ObjectIdentifier);
        if (str2 != null) {
            stringBuffer.append(str2);
        } else {
            stringBuffer.append(aSN1ObjectIdentifier.getId());
        }
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (length < length2 && stringBuffer.charAt(length) == ' ') {
            stringBuffer.insert(length, "\\");
            length += 2;
            length2++;
        }
        while (true) {
            length2--;
            if (length2 <= length || stringBuffer.charAt(length2) != ' ') {
                break;
            } else {
                stringBuffer.insert(length2, '\\');
            }
        }
        while (length <= length2) {
            switch (stringBuffer.charAt(length)) {
                case '\"':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                    stringBuffer.insert(length, "\\");
                    length += 2;
                    length2++;
                    break;
                default:
                    length++;
                    break;
            }
        }
    }

    public java.lang.String toString(boolean z, java.util.Hashtable hashtable) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.util.Vector vector = new java.util.Vector();
        java.lang.StringBuffer stringBuffer2 = null;
        for (int i = 0; i < this.ordering.size(); i++) {
            if (((java.lang.Boolean) this.added.elementAt(i)).booleanValue()) {
                stringBuffer2.append('+');
                appendValue(stringBuffer2, hashtable, (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i), (java.lang.String) this.values.elementAt(i));
            } else {
                stringBuffer2 = new java.lang.StringBuffer();
                appendValue(stringBuffer2, hashtable, (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) this.ordering.elementAt(i), (java.lang.String) this.values.elementAt(i));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z2 = true;
        if (z) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i2 = 0; i2 < vector.size(); i2++) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i2).toString());
            }
        }
        return stringBuffer.toString();
    }

    private java.lang.String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return new java.lang.String(cArr);
    }

    public java.lang.String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }
}
