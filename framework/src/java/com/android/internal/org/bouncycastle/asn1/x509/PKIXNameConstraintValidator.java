package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class PKIXNameConstraintValidator implements com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator {
    private java.util.Set permittedSubtreesDN;
    private java.util.Set permittedSubtreesDNS;
    private java.util.Set permittedSubtreesEmail;
    private java.util.Set permittedSubtreesIP;
    private java.util.Set permittedSubtreesOtherName;
    private java.util.Set permittedSubtreesURI;
    private java.util.Set excludedSubtreesDN = new java.util.HashSet();
    private java.util.Set excludedSubtreesDNS = new java.util.HashSet();
    private java.util.Set excludedSubtreesEmail = new java.util.HashSet();
    private java.util.Set excludedSubtreesURI = new java.util.HashSet();
    private java.util.Set excludedSubtreesIP = new java.util.HashSet();
    private java.util.Set excludedSubtreesOtherName = new java.util.HashSet();

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void checkPermitted(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        switch (generalName.getTagNo()) {
            case 0:
                checkPermittedOtherName(this.permittedSubtreesOtherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(generalName.getName()));
                break;
            case 1:
                checkPermittedEmail(this.permittedSubtreesEmail, extractNameAsString(generalName));
                break;
            case 2:
                checkPermittedDNS(this.permittedSubtreesDNS, extractNameAsString(generalName));
                break;
            case 4:
                checkPermittedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(generalName.getName()));
                break;
            case 6:
                checkPermittedURI(this.permittedSubtreesURI, extractNameAsString(generalName));
                break;
            case 7:
                checkPermittedIP(this.permittedSubtreesIP, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(generalName.getName()).getOctets());
                break;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void checkExcluded(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        switch (generalName.getTagNo()) {
            case 0:
                checkExcludedOtherName(this.excludedSubtreesOtherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(generalName.getName()));
                break;
            case 1:
                checkExcludedEmail(this.excludedSubtreesEmail, extractNameAsString(generalName));
                break;
            case 2:
                checkExcludedDNS(this.excludedSubtreesDNS, extractNameAsString(generalName));
                break;
            case 4:
                checkExcludedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(generalName.getName()));
                break;
            case 6:
                checkExcludedURI(this.excludedSubtreesURI, extractNameAsString(generalName));
                break;
            case 7:
                checkExcludedIP(this.excludedSubtreesIP, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(generalName.getName()).getOctets());
                break;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree) {
        intersectPermittedSubtree(new com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[]{generalSubtree});
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i = 0; i != generalSubtreeArr.length; i++) {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree = generalSubtreeArr[i];
            java.lang.Integer valueOf = com.android.internal.org.bouncycastle.util.Integers.valueOf(generalSubtree.getBase().getTagNo());
            if (hashMap.get(valueOf) == null) {
                hashMap.put(valueOf, new java.util.HashSet());
            }
            ((java.util.Set) hashMap.get(valueOf)).add(generalSubtree);
        }
        for (java.util.Map.Entry entry : hashMap.entrySet()) {
            int intValue = ((java.lang.Integer) entry.getKey()).intValue();
            switch (intValue) {
                case 0:
                    this.permittedSubtreesOtherName = intersectOtherName(this.permittedSubtreesOtherName, (java.util.Set) entry.getValue());
                    break;
                case 1:
                    this.permittedSubtreesEmail = intersectEmail(this.permittedSubtreesEmail, (java.util.Set) entry.getValue());
                    break;
                case 2:
                    this.permittedSubtreesDNS = intersectDNS(this.permittedSubtreesDNS, (java.util.Set) entry.getValue());
                    break;
                case 3:
                case 5:
                default:
                    throw new java.lang.IllegalStateException("Unknown tag encountered: " + intValue);
                case 4:
                    this.permittedSubtreesDN = intersectDN(this.permittedSubtreesDN, (java.util.Set) entry.getValue());
                    break;
                case 6:
                    this.permittedSubtreesURI = intersectURI(this.permittedSubtreesURI, (java.util.Set) entry.getValue());
                    break;
                case 7:
                    this.permittedSubtreesIP = intersectIP(this.permittedSubtreesIP, (java.util.Set) entry.getValue());
                    break;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void intersectEmptyPermittedSubtree(int i) {
        switch (i) {
            case 0:
                this.permittedSubtreesOtherName = new java.util.HashSet();
                return;
            case 1:
                this.permittedSubtreesEmail = new java.util.HashSet();
                return;
            case 2:
                this.permittedSubtreesDNS = new java.util.HashSet();
                return;
            case 3:
            case 5:
            default:
                throw new java.lang.IllegalStateException("Unknown tag encountered: " + i);
            case 4:
                this.permittedSubtreesDN = new java.util.HashSet();
                return;
            case 6:
                this.permittedSubtreesURI = new java.util.HashSet();
                return;
            case 7:
                this.permittedSubtreesIP = new java.util.HashSet();
                return;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidator
    public void addExcludedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree) {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName base = generalSubtree.getBase();
        switch (base.getTagNo()) {
            case 0:
                this.excludedSubtreesOtherName = unionOtherName(this.excludedSubtreesOtherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(base.getName()));
                return;
            case 1:
                this.excludedSubtreesEmail = unionEmail(this.excludedSubtreesEmail, extractNameAsString(base));
                return;
            case 2:
                this.excludedSubtreesDNS = unionDNS(this.excludedSubtreesDNS, extractNameAsString(base));
                return;
            case 3:
            case 5:
            default:
                throw new java.lang.IllegalStateException("Unknown tag encountered: " + base.getTagNo());
            case 4:
                this.excludedSubtreesDN = unionDN(this.excludedSubtreesDN, (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) base.getName().toASN1Primitive());
                return;
            case 6:
                this.excludedSubtreesURI = unionURI(this.excludedSubtreesURI, extractNameAsString(base));
                return;
            case 7:
                this.excludedSubtreesIP = unionIP(this.excludedSubtreesIP, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(base.getName()).getOctets());
                return;
        }
    }

    public int hashCode() {
        return hashCollection(this.excludedSubtreesDN) + hashCollection(this.excludedSubtreesDNS) + hashCollection(this.excludedSubtreesEmail) + hashCollection(this.excludedSubtreesIP) + hashCollection(this.excludedSubtreesURI) + hashCollection(this.excludedSubtreesOtherName) + hashCollection(this.permittedSubtreesDN) + hashCollection(this.permittedSubtreesDNS) + hashCollection(this.permittedSubtreesEmail) + hashCollection(this.permittedSubtreesIP) + hashCollection(this.permittedSubtreesURI) + hashCollection(this.permittedSubtreesOtherName);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x509.PKIXNameConstraintValidator)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.x509.PKIXNameConstraintValidator pKIXNameConstraintValidator = (com.android.internal.org.bouncycastle.asn1.x509.PKIXNameConstraintValidator) obj;
        return collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesDN, this.excludedSubtreesDN) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesDNS, this.excludedSubtreesDNS) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesEmail, this.excludedSubtreesEmail) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesIP, this.excludedSubtreesIP) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesURI, this.excludedSubtreesURI) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesOtherName, this.excludedSubtreesOtherName) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesDN, this.permittedSubtreesDN) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesDNS, this.permittedSubtreesDNS) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesEmail, this.permittedSubtreesEmail) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesIP, this.permittedSubtreesIP) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesURI, this.permittedSubtreesURI) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesOtherName, this.permittedSubtreesOtherName);
    }

    public void checkPermittedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        checkPermittedDN(this.permittedSubtreesDN, com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(x500Name.toASN1Primitive()));
    }

    public void checkExcludedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        checkExcludedDN(this.excludedSubtreesDN, com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(x500Name));
    }

    private static boolean withinDNSubtree(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2) {
        if (aSN1Sequence2.size() < 1 || aSN1Sequence2.size() > aSN1Sequence.size()) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.x500.RDN rdn = com.android.internal.org.bouncycastle.asn1.x500.RDN.getInstance(aSN1Sequence2.getObjectAt(0));
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= aSN1Sequence.size()) {
                i = i2;
                break;
            }
            if (com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.rDNAreEqual(rdn, com.android.internal.org.bouncycastle.asn1.x500.RDN.getInstance(aSN1Sequence.getObjectAt(i)))) {
                break;
            }
            i2 = i;
            i++;
        }
        if (aSN1Sequence2.size() > aSN1Sequence.size() - i) {
            return false;
        }
        for (int i3 = 0; i3 < aSN1Sequence2.size(); i3++) {
            com.android.internal.org.bouncycastle.asn1.x500.RDN rdn2 = com.android.internal.org.bouncycastle.asn1.x500.RDN.getInstance(aSN1Sequence2.getObjectAt(i3));
            com.android.internal.org.bouncycastle.asn1.x500.RDN rdn3 = com.android.internal.org.bouncycastle.asn1.x500.RDN.getInstance(aSN1Sequence.getObjectAt(i + i3));
            if (rdn2.size() != rdn3.size() || !rdn2.getFirst().getType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) rdn3.getFirst().getType())) {
                return false;
            }
            if (rdn2.size() == 1 && rdn2.getFirst().getType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x500.style.RFC4519Style.serialNumber)) {
                if (!rdn3.getFirst().getValue().toString().startsWith(rdn2.getFirst().getValue().toString())) {
                    return false;
                }
            } else if (!com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.rDNAreEqual(rdn2, rdn3)) {
                return false;
            }
        }
        return true;
    }

    private void checkPermittedDN(java.util.Set set, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        if (set.isEmpty() && aSN1Sequence.size() == 0) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (withinDNSubtree(aSN1Sequence, (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) it.next())) {
                return;
            }
        }
        throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
    }

    private void checkExcludedDN(java.util.Set set, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (withinDNSubtree(aSN1Sequence, (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) it.next())) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
            }
        }
    }

    private java.util.Set intersectDN(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase().getName().toASN1Primitive());
            if (set == null) {
                if (aSN1Sequence != null) {
                    hashSet.add(aSN1Sequence);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) it2.next();
                    if (withinDNSubtree(aSN1Sequence, aSN1Sequence2)) {
                        hashSet.add(aSN1Sequence);
                    } else if (withinDNSubtree(aSN1Sequence2, aSN1Sequence)) {
                        hashSet.add(aSN1Sequence2);
                    }
                }
            }
        }
        return hashSet;
    }

    private java.util.Set unionDN(java.util.Set set, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (set.isEmpty()) {
            if (aSN1Sequence == null) {
                return set;
            }
            set.add(aSN1Sequence);
            return set;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(it.next());
            if (withinDNSubtree(aSN1Sequence, aSN1Sequence2)) {
                hashSet.add(aSN1Sequence2);
            } else if (withinDNSubtree(aSN1Sequence2, aSN1Sequence)) {
                hashSet.add(aSN1Sequence);
            } else {
                hashSet.add(aSN1Sequence2);
                hashSet.add(aSN1Sequence);
            }
        }
        return hashSet;
    }

    private java.util.Set intersectOtherName(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName = com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase().getName());
            if (set == null) {
                if (otherName != null) {
                    hashSet.add(otherName);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    intersectOtherName(otherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(it2.next()), hashSet);
                }
            }
        }
        return hashSet;
    }

    private void intersectOtherName(com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName2, java.util.Set set) {
        if (otherName.equals(otherName2)) {
            set.add(otherName);
        }
    }

    private java.util.Set unionOtherName(java.util.Set set, com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName) {
        java.util.HashSet hashSet = set != null ? new java.util.HashSet(set) : new java.util.HashSet();
        hashSet.add(otherName);
        return hashSet;
    }

    private java.util.Set intersectEmail(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            java.lang.String extractNameAsString = extractNameAsString(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase());
            if (set == null) {
                if (extractNameAsString != null) {
                    hashSet.add(extractNameAsString);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    intersectEmail(extractNameAsString, (java.lang.String) it2.next(), hashSet);
                }
            }
        }
        return hashSet;
    }

    private java.util.Set unionEmail(java.util.Set set, java.lang.String str) {
        if (set.isEmpty()) {
            if (str == null) {
                return set;
            }
            set.add(str);
            return set;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            unionEmail((java.lang.String) it.next(), str, hashSet);
        }
        return hashSet;
    }

    private java.util.Set intersectIP(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            byte[] octets = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase().getName()).getOctets();
            if (set == null) {
                if (octets != null) {
                    hashSet.add(octets);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    hashSet.addAll(intersectIPRange((byte[]) it2.next(), octets));
                }
            }
        }
        return hashSet;
    }

    private java.util.Set unionIP(java.util.Set set, byte[] bArr) {
        if (set.isEmpty()) {
            if (bArr == null) {
                return set;
            }
            set.add(bArr);
            return set;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            hashSet.addAll(unionIPRange((byte[]) it.next(), bArr));
        }
        return hashSet;
    }

    private java.util.Set unionIPRange(byte[] bArr, byte[] bArr2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(bArr, bArr2)) {
            hashSet.add(bArr);
        } else {
            hashSet.add(bArr);
            hashSet.add(bArr2);
        }
        return hashSet;
    }

    private java.util.Set intersectIPRange(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return java.util.Collections.EMPTY_SET;
        }
        byte[][] extractIPsAndSubnetMasks = extractIPsAndSubnetMasks(bArr, bArr2);
        byte[] bArr3 = extractIPsAndSubnetMasks[0];
        byte[] bArr4 = extractIPsAndSubnetMasks[1];
        byte[] bArr5 = extractIPsAndSubnetMasks[2];
        byte[] bArr6 = extractIPsAndSubnetMasks[3];
        byte[][] minMaxIPs = minMaxIPs(bArr3, bArr4, bArr5, bArr6);
        if (compareTo(max(minMaxIPs[0], minMaxIPs[2]), min(minMaxIPs[1], minMaxIPs[3])) == 1) {
            return java.util.Collections.EMPTY_SET;
        }
        return java.util.Collections.singleton(ipWithSubnetMask(or(minMaxIPs[0], minMaxIPs[2]), or(bArr4, bArr6)));
    }

    private byte[] ipWithSubnetMask(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[length * 2];
        java.lang.System.arraycopy(bArr, 0, bArr3, 0, length);
        java.lang.System.arraycopy(bArr2, 0, bArr3, length, length);
        return bArr3;
    }

    private byte[][] extractIPsAndSubnetMasks(byte[] bArr, byte[] bArr2) {
        int length = bArr.length / 2;
        byte[] bArr3 = new byte[length];
        byte[] bArr4 = new byte[length];
        java.lang.System.arraycopy(bArr, 0, bArr3, 0, length);
        java.lang.System.arraycopy(bArr, length, bArr4, 0, length);
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        java.lang.System.arraycopy(bArr2, 0, bArr5, 0, length);
        java.lang.System.arraycopy(bArr2, length, bArr6, 0, length);
        return new byte[][]{bArr3, bArr4, bArr5, bArr6};
    }

    private byte[][] minMaxIPs(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        int length = bArr.length;
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        byte[] bArr7 = new byte[length];
        byte[] bArr8 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr5[i] = (byte) (bArr[i] & bArr2[i]);
            bArr6[i] = (byte) ((bArr[i] & bArr2[i]) | (~bArr2[i]));
            bArr7[i] = (byte) (bArr3[i] & bArr4[i]);
            bArr8[i] = (byte) ((bArr3[i] & bArr4[i]) | (~bArr4[i]));
        }
        return new byte[][]{bArr5, bArr6, bArr7, bArr8};
    }

    private void checkPermittedEmail(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (emailIsConstrained(str, (java.lang.String) it.next())) {
                return;
            }
        }
        if (str.length() == 0 && set.size() == 0) {
        } else {
            throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("Subject email address is not from a permitted subtree.");
        }
    }

    private void checkPermittedOtherName(java.util.Set set, com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (otherNameIsConstrained(otherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(it.next()))) {
                return;
            }
        }
        throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("Subject OtherName is not from a permitted subtree.");
    }

    private void checkExcludedOtherName(java.util.Set set, com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (otherNameIsConstrained(otherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(it.next()))) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("OtherName is from an excluded subtree.");
            }
        }
    }

    private void checkExcludedEmail(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (emailIsConstrained(str, (java.lang.String) it.next())) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("Email address is from an excluded subtree.");
            }
        }
    }

    private void checkPermittedIP(java.util.Set set, byte[] bArr) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isIPConstrained(bArr, (byte[]) it.next())) {
                return;
            }
        }
        if (bArr.length == 0 && set.size() == 0) {
        } else {
            throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("IP is not from a permitted subtree.");
        }
    }

    private void checkExcludedIP(java.util.Set set, byte[] bArr) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isIPConstrained(bArr, (byte[]) it.next())) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("IP is from an excluded subtree.");
            }
        }
    }

    private boolean isIPConstrained(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length != bArr2.length / 2) {
            return false;
        }
        byte[] bArr3 = new byte[length];
        java.lang.System.arraycopy(bArr2, length, bArr3, 0, length);
        byte[] bArr4 = new byte[length];
        byte[] bArr5 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr4[i] = (byte) (bArr2[i] & bArr3[i]);
            bArr5[i] = (byte) (bArr[i] & bArr3[i]);
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(bArr4, bArr5);
    }

    private boolean otherNameIsConstrained(com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName, com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName2) {
        if (otherName2.equals(otherName)) {
            return true;
        }
        return false;
    }

    private boolean emailIsConstrained(java.lang.String str, java.lang.String str2) {
        java.lang.String substring = str.substring(str.indexOf(64) + 1);
        if (str2.indexOf(64) != -1) {
            if (str.equalsIgnoreCase(str2) || substring.equalsIgnoreCase(str2.substring(1))) {
                return true;
            }
        } else if (str2.charAt(0) != '.') {
            if (substring.equalsIgnoreCase(str2)) {
                return true;
            }
        } else if (withinDomain(substring, str2)) {
            return true;
        }
        return false;
    }

    private boolean withinDomain(java.lang.String str, java.lang.String str2) {
        if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            str2 = str2.substring(1);
        }
        java.lang.String[] split = com.android.internal.org.bouncycastle.util.Strings.split(str2, '.');
        java.lang.String[] split2 = com.android.internal.org.bouncycastle.util.Strings.split(str, '.');
        if (split2.length <= split.length) {
            return false;
        }
        int length = split2.length - split.length;
        for (int i = -1; i < split.length; i++) {
            if (i == -1) {
                if (split2[i + length].equals("")) {
                    return false;
                }
            } else if (!split[i].equalsIgnoreCase(split2[i + length])) {
                return false;
            }
        }
        return true;
    }

    private void checkPermittedDNS(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            java.lang.String str2 = (java.lang.String) it.next();
            if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                return;
            }
        }
        if (str.length() == 0 && set.size() == 0) {
        } else {
            throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("DNS is not from a permitted subtree.");
        }
    }

    private void checkExcludedDNS(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            java.lang.String str2 = (java.lang.String) it.next();
            if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("DNS is from an excluded subtree.");
            }
        }
    }

    private void unionEmail(java.lang.String str, java.lang.String str2, java.util.Set set) {
        if (str.indexOf(64) != -1) {
            java.lang.String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (str.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(substring, str2)) {
                    set.add(str2);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (substring.equalsIgnoreCase(str2)) {
                set.add(str2);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (str2.indexOf(64) != -1) {
                if (withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                    set.add(str2);
                    return;
                } else if (withinDomain(str2, str)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (withinDomain(str2, str)) {
                set.add(str);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str2.indexOf(64) != -1) {
            if (str2.substring(str.indexOf(64) + 1).equalsIgnoreCase(str)) {
                set.add(str);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (withinDomain(str, str2)) {
                set.add(str2);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str.equalsIgnoreCase(str2)) {
            set.add(str);
        } else {
            set.add(str);
            set.add(str2);
        }
    }

    private void unionURI(java.lang.String str, java.lang.String str2, java.util.Set set) {
        if (str.indexOf(64) != -1) {
            java.lang.String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (str.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(substring, str2)) {
                    set.add(str2);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (substring.equalsIgnoreCase(str2)) {
                set.add(str2);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (str2.indexOf(64) != -1) {
                if (withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                    set.add(str2);
                    return;
                } else if (withinDomain(str2, str)) {
                    set.add(str);
                    return;
                } else {
                    set.add(str);
                    set.add(str2);
                    return;
                }
            }
            if (withinDomain(str2, str)) {
                set.add(str);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str2.indexOf(64) != -1) {
            if (str2.substring(str.indexOf(64) + 1).equalsIgnoreCase(str)) {
                set.add(str);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (withinDomain(str, str2)) {
                set.add(str2);
                return;
            } else {
                set.add(str);
                set.add(str2);
                return;
            }
        }
        if (str.equalsIgnoreCase(str2)) {
            set.add(str);
        } else {
            set.add(str);
            set.add(str2);
        }
    }

    private java.util.Set intersectDNS(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            java.lang.String extractNameAsString = extractNameAsString(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase());
            if (set == null) {
                if (extractNameAsString != null) {
                    hashSet.add(extractNameAsString);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    java.lang.String str = (java.lang.String) it2.next();
                    if (withinDomain(str, extractNameAsString)) {
                        hashSet.add(str);
                    } else if (withinDomain(extractNameAsString, str)) {
                        hashSet.add(extractNameAsString);
                    }
                }
            }
        }
        return hashSet;
    }

    private java.util.Set unionDNS(java.util.Set set, java.lang.String str) {
        if (set.isEmpty()) {
            if (str == null) {
                return set;
            }
            set.add(str);
            return set;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            java.lang.String str2 = (java.lang.String) it.next();
            if (withinDomain(str2, str)) {
                hashSet.add(str);
            } else if (withinDomain(str, str2)) {
                hashSet.add(str2);
            } else {
                hashSet.add(str2);
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    private void intersectEmail(java.lang.String str, java.lang.String str2, java.util.Set set) {
        if (str.indexOf(64) != -1) {
            java.lang.String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (str.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                }
                return;
            } else if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(substring, str2)) {
                    set.add(str);
                    return;
                }
                return;
            } else {
                if (substring.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                }
                return;
            }
        }
        if (str.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (str2.indexOf(64) != -1) {
                if (withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                    set.add(str2);
                    return;
                }
                return;
            } else {
                if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                    if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                        set.add(str);
                        return;
                    } else {
                        if (withinDomain(str2, str)) {
                            set.add(str2);
                            return;
                        }
                        return;
                    }
                }
                if (withinDomain(str2, str)) {
                    set.add(str2);
                    return;
                }
                return;
            }
        }
        if (str2.indexOf(64) != -1) {
            if (str2.substring(str2.indexOf(64) + 1).equalsIgnoreCase(str)) {
                set.add(str2);
            }
        } else if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (withinDomain(str, str2)) {
                set.add(str);
            }
        } else if (str.equalsIgnoreCase(str2)) {
            set.add(str);
        }
    }

    private void checkExcludedURI(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set.isEmpty()) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isUriConstrained(str, (java.lang.String) it.next())) {
                throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("URI is from an excluded subtree.");
            }
        }
    }

    private java.util.Set intersectURI(java.util.Set set, java.util.Set set2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set2.iterator();
        while (it.hasNext()) {
            java.lang.String extractNameAsString = extractNameAsString(((com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) it.next()).getBase());
            if (set == null) {
                if (extractNameAsString != null) {
                    hashSet.add(extractNameAsString);
                }
            } else {
                java.util.Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    intersectURI((java.lang.String) it2.next(), extractNameAsString, hashSet);
                }
            }
        }
        return hashSet;
    }

    private java.util.Set unionURI(java.util.Set set, java.lang.String str) {
        if (set.isEmpty()) {
            if (str == null) {
                return set;
            }
            set.add(str);
            return set;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            unionURI((java.lang.String) it.next(), str, hashSet);
        }
        return hashSet;
    }

    private void intersectURI(java.lang.String str, java.lang.String str2, java.util.Set set) {
        if (str.indexOf(64) != -1) {
            java.lang.String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (str.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                }
                return;
            } else if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                if (withinDomain(substring, str2)) {
                    set.add(str);
                    return;
                }
                return;
            } else {
                if (substring.equalsIgnoreCase(str2)) {
                    set.add(str);
                    return;
                }
                return;
            }
        }
        if (str.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (str2.indexOf(64) != -1) {
                if (withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                    set.add(str2);
                    return;
                }
                return;
            } else {
                if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
                    if (withinDomain(str, str2) || str.equalsIgnoreCase(str2)) {
                        set.add(str);
                        return;
                    } else {
                        if (withinDomain(str2, str)) {
                            set.add(str2);
                            return;
                        }
                        return;
                    }
                }
                if (withinDomain(str2, str)) {
                    set.add(str2);
                    return;
                }
                return;
            }
        }
        if (str2.indexOf(64) != -1) {
            if (str2.substring(str2.indexOf(64) + 1).equalsIgnoreCase(str)) {
                set.add(str2);
            }
        } else if (str2.startsWith(android.media.MediaMetrics.SEPARATOR)) {
            if (withinDomain(str, str2)) {
                set.add(str);
            }
        } else if (str.equalsIgnoreCase(str2)) {
            set.add(str);
        }
    }

    private void checkPermittedURI(java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isUriConstrained(str, (java.lang.String) it.next())) {
                return;
            }
        }
        if (str.length() == 0 && set.size() == 0) {
        } else {
            throw new com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException("URI is not from a permitted subtree.");
        }
    }

    private boolean isUriConstrained(java.lang.String str, java.lang.String str2) {
        java.lang.String extractHostFromURL = extractHostFromURL(str);
        return !str2.startsWith(android.media.MediaMetrics.SEPARATOR) ? extractHostFromURL.equalsIgnoreCase(str2) : withinDomain(extractHostFromURL, str2);
    }

    private static java.lang.String extractHostFromURL(java.lang.String str) {
        java.lang.String substring = str.substring(str.indexOf(58) + 1);
        if (substring.indexOf("//") != -1) {
            substring = substring.substring(substring.indexOf("//") + 2);
        }
        if (substring.lastIndexOf(58) != -1) {
            substring = substring.substring(0, substring.lastIndexOf(58));
        }
        java.lang.String substring2 = substring.substring(substring.indexOf(58) + 1);
        java.lang.String substring3 = substring2.substring(substring2.indexOf(64) + 1);
        if (substring3.indexOf(47) != -1) {
            return substring3.substring(0, substring3.indexOf(47));
        }
        return substring3;
    }

    private java.lang.String extractNameAsString(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) {
        return com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(generalName.getName()).getString();
    }

    private static byte[] max(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 65535) > (65535 & bArr2[i])) {
                return bArr;
            }
        }
        return bArr2;
    }

    private static byte[] min(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 65535) < (65535 & bArr2[i])) {
                return bArr;
            }
        }
        return bArr2;
    }

    private static int compareTo(byte[] bArr, byte[] bArr2) {
        if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(bArr, bArr2)) {
            return 0;
        }
        if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(max(bArr, bArr2), bArr)) {
            return 1;
        }
        return -1;
    }

    private static byte[] or(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] | bArr2[i]);
        }
        return bArr3;
    }

    private int hashCollection(java.util.Collection collection) {
        int i = 0;
        if (collection == null) {
            return 0;
        }
        for (java.lang.Object obj : collection) {
            if (obj instanceof byte[]) {
                i += com.android.internal.org.bouncycastle.util.Arrays.hashCode((byte[]) obj);
            } else {
                i += obj.hashCode();
            }
        }
        return i;
    }

    private boolean collectionsAreEqual(java.util.Collection collection, java.util.Collection collection2) {
        boolean z;
        if (collection == collection2) {
            return true;
        }
        if (collection == null || collection2 == null || collection.size() != collection2.size()) {
            return false;
        }
        for (java.lang.Object obj : collection) {
            java.util.Iterator it = collection2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (equals(obj, it.next())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    private boolean equals(java.lang.Object obj, java.lang.Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual((byte[]) obj, (byte[]) obj2);
        }
        return obj.equals(obj2);
    }

    private java.lang.String stringifyIP(byte[] bArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < bArr.length / 2; i++) {
            if (sb.length() > 0) {
                sb.append(android.media.MediaMetrics.SEPARATOR);
            }
            sb.append(java.lang.Integer.toString(bArr[i] & 255));
        }
        sb.append("/");
        boolean z = true;
        for (int length = bArr.length / 2; length < bArr.length; length++) {
            if (z) {
                z = false;
            } else {
                sb.append(android.media.MediaMetrics.SEPARATOR);
            }
            sb.append(java.lang.Integer.toString(bArr[length] & 255));
        }
        return sb.toString();
    }

    private java.lang.String stringifyIPCollection(java.util.Set set) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(stringifyIP((byte[]) it.next()));
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private java.lang.String stringifyOtherNameCollection(java.util.Set set) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            com.android.internal.org.bouncycastle.asn1.x509.OtherName otherName = com.android.internal.org.bouncycastle.asn1.x509.OtherName.getInstance(it.next());
            sb.append(otherName.getTypeID().getId());
            sb.append(":");
            try {
                sb.append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(otherName.getValue().toASN1Primitive().getEncoded()));
            } catch (java.io.IOException e) {
                sb.append(e.toString());
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private final void addLine(java.lang.StringBuilder sb, java.lang.String str) {
        sb.append(str).append(com.android.internal.org.bouncycastle.util.Strings.lineSeparator());
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        addLine(sb, "permitted:");
        if (this.permittedSubtreesDN != null) {
            addLine(sb, "DN:");
            addLine(sb, this.permittedSubtreesDN.toString());
        }
        if (this.permittedSubtreesDNS != null) {
            addLine(sb, "DNS:");
            addLine(sb, this.permittedSubtreesDNS.toString());
        }
        if (this.permittedSubtreesEmail != null) {
            addLine(sb, "Email:");
            addLine(sb, this.permittedSubtreesEmail.toString());
        }
        if (this.permittedSubtreesURI != null) {
            addLine(sb, "URI:");
            addLine(sb, this.permittedSubtreesURI.toString());
        }
        if (this.permittedSubtreesIP != null) {
            addLine(sb, "IP:");
            addLine(sb, stringifyIPCollection(this.permittedSubtreesIP));
        }
        if (this.permittedSubtreesOtherName != null) {
            addLine(sb, "OtherName:");
            addLine(sb, stringifyOtherNameCollection(this.permittedSubtreesOtherName));
        }
        addLine(sb, "excluded:");
        if (!this.excludedSubtreesDN.isEmpty()) {
            addLine(sb, "DN:");
            addLine(sb, this.excludedSubtreesDN.toString());
        }
        if (!this.excludedSubtreesDNS.isEmpty()) {
            addLine(sb, "DNS:");
            addLine(sb, this.excludedSubtreesDNS.toString());
        }
        if (!this.excludedSubtreesEmail.isEmpty()) {
            addLine(sb, "Email:");
            addLine(sb, this.excludedSubtreesEmail.toString());
        }
        if (!this.excludedSubtreesURI.isEmpty()) {
            addLine(sb, "URI:");
            addLine(sb, this.excludedSubtreesURI.toString());
        }
        if (!this.excludedSubtreesIP.isEmpty()) {
            addLine(sb, "IP:");
            addLine(sb, stringifyIPCollection(this.excludedSubtreesIP));
        }
        if (!this.excludedSubtreesOtherName.isEmpty()) {
            addLine(sb, "OtherName:");
            addLine(sb, stringifyOtherNameCollection(this.excludedSubtreesOtherName));
        }
        return sb.toString();
    }
}
