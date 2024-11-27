package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1InputStream extends java.io.FilterInputStream implements com.android.internal.org.bouncycastle.asn1.BERTags {
    private final boolean lazyEvaluate;
    private final int limit;
    private final byte[][] tmpBuffers;

    public ASN1InputStream(java.io.InputStream inputStream) {
        this(inputStream, com.android.internal.org.bouncycastle.asn1.StreamUtil.findLimit(inputStream));
    }

    public ASN1InputStream(byte[] bArr) {
        this(new java.io.ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1InputStream(byte[] bArr, boolean z) {
        this(new java.io.ByteArrayInputStream(bArr), bArr.length, z);
    }

    public ASN1InputStream(java.io.InputStream inputStream, int i) {
        this(inputStream, i, false);
    }

    public ASN1InputStream(java.io.InputStream inputStream, boolean z) {
        this(inputStream, com.android.internal.org.bouncycastle.asn1.StreamUtil.findLimit(inputStream), z);
    }

    public ASN1InputStream(java.io.InputStream inputStream, int i, boolean z) {
        super(inputStream);
        this.limit = i;
        this.lazyEvaluate = z;
        this.tmpBuffers = new byte[11][];
    }

    int getLimit() {
        return this.limit;
    }

    protected int readLength() throws java.io.IOException {
        return readLength(this, this.limit, false);
    }

    protected void readFully(byte[] bArr) throws java.io.IOException {
        if (com.android.internal.org.bouncycastle.util.io.Streams.readFully(this, bArr) != bArr.length) {
            throw new java.io.EOFException("EOF encountered in middle of object");
        }
    }

    protected com.android.internal.org.bouncycastle.asn1.ASN1Primitive buildObject(int i, int i2, int i3) throws java.io.IOException {
        boolean z = (i & 32) != 0;
        com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream = new com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream(this, i3, this.limit);
        if ((i & 64) != 0) {
            return new com.android.internal.org.bouncycastle.asn1.DLApplicationSpecific(z, i2, definiteLengthInputStream.toByteArray());
        }
        if ((i & 128) != 0) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream).readTaggedObject(z, i2);
        }
        if (z) {
            switch (i2) {
                case 4:
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector readVector = readVector(definiteLengthInputStream);
                    int size = readVector.size();
                    com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr = new com.android.internal.org.bouncycastle.asn1.ASN1OctetString[size];
                    for (int i4 = 0; i4 != size; i4++) {
                        com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = readVector.get(i4);
                        if (aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
                            aSN1OctetStringArr[i4] = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Encodable;
                        } else {
                            throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("unknown object encountered in constructed OCTET STRING: " + aSN1Encodable.getClass());
                        }
                    }
                    return new com.android.internal.org.bouncycastle.asn1.BEROctetString(aSN1OctetStringArr);
                case 8:
                    return new com.android.internal.org.bouncycastle.asn1.DLExternal(readVector(definiteLengthInputStream));
                case 16:
                    if (this.lazyEvaluate) {
                        return new com.android.internal.org.bouncycastle.asn1.LazyEncodedSequence(definiteLengthInputStream.toByteArray());
                    }
                    return com.android.internal.org.bouncycastle.asn1.DLFactory.createSequence(readVector(definiteLengthInputStream));
                case 17:
                    return com.android.internal.org.bouncycastle.asn1.DLFactory.createSet(readVector(definiteLengthInputStream));
                default:
                    throw new java.io.IOException("unknown tag " + i2 + " encountered");
            }
        }
        return createPrimitiveDERObject(i2, definiteLengthInputStream, this.tmpBuffers);
    }

    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector readVector(com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream) throws java.io.IOException {
        if (definiteLengthInputStream.getRemaining() < 1) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(0);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(definiteLengthInputStream);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        while (true) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject = aSN1InputStream.readObject();
            if (readObject != null) {
                aSN1EncodableVector.add(readObject);
            } else {
                return aSN1EncodableVector;
            }
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject() throws java.io.IOException {
        int read = read();
        if (read <= 0) {
            if (read == 0) {
                throw new java.io.IOException("unexpected end-of-contents marker");
            }
            return null;
        }
        int readTagNumber = readTagNumber(this, read);
        boolean z = (read & 32) != 0;
        int readLength = readLength();
        if (readLength < 0) {
            if (!z) {
                throw new java.io.IOException("indefinite-length primitive encoding encountered");
            }
            com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser = new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(new com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream(this, this.limit), this.limit);
            if ((read & 64) != 0) {
                return new com.android.internal.org.bouncycastle.asn1.BERApplicationSpecificParser(readTagNumber, aSN1StreamParser).getLoadedObject();
            }
            if ((read & 128) != 0) {
                return new com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser(true, readTagNumber, aSN1StreamParser).getLoadedObject();
            }
            switch (readTagNumber) {
                case 4:
                    return new com.android.internal.org.bouncycastle.asn1.BEROctetStringParser(aSN1StreamParser).getLoadedObject();
                case 8:
                    return new com.android.internal.org.bouncycastle.asn1.DERExternalParser(aSN1StreamParser).getLoadedObject();
                case 16:
                    return new com.android.internal.org.bouncycastle.asn1.BERSequenceParser(aSN1StreamParser).getLoadedObject();
                case 17:
                    return new com.android.internal.org.bouncycastle.asn1.BERSetParser(aSN1StreamParser).getLoadedObject();
                default:
                    throw new java.io.IOException("unknown BER object encountered");
            }
        }
        try {
            return buildObject(read, readTagNumber, readLength);
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("corrupted stream detected", e);
        }
    }

    static int readTagNumber(java.io.InputStream inputStream, int i) throws java.io.IOException {
        int i2 = i & 31;
        if (i2 == 31) {
            int read = inputStream.read();
            if ((read & 127) == 0) {
                throw new java.io.IOException("corrupted stream - invalid high tag number found");
            }
            int i3 = 0;
            while (read >= 0 && (read & 128) != 0) {
                i3 = ((read & 127) | i3) << 7;
                read = inputStream.read();
            }
            if (read < 0) {
                throw new java.io.EOFException("EOF found inside tag value.");
            }
            return i3 | (read & 127);
        }
        return i2;
    }

    static int readLength(java.io.InputStream inputStream, int i, boolean z) throws java.io.IOException {
        int read = inputStream.read();
        if (read < 0) {
            throw new java.io.EOFException("EOF found when length expected");
        }
        if (read == 128) {
            return -1;
        }
        if (read > 127) {
            int i2 = read & 127;
            if (i2 > 4) {
                throw new java.io.IOException("DER length more than 4 bytes: " + i2);
            }
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                int read2 = inputStream.read();
                if (read2 < 0) {
                    throw new java.io.EOFException("EOF found reading length");
                }
                i3 = (i3 << 8) + read2;
            }
            if (i3 < 0) {
                throw new java.io.IOException("corrupted stream - negative length found");
            }
            if (i3 >= i && !z) {
                throw new java.io.IOException("corrupted stream - out of bounds length found: " + i3 + " >= " + i);
            }
            return i3;
        }
        return read;
    }

    private static byte[] getBuffer(com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) throws java.io.IOException {
        int remaining = definiteLengthInputStream.getRemaining();
        if (remaining >= bArr.length) {
            return definiteLengthInputStream.toByteArray();
        }
        byte[] bArr2 = bArr[remaining];
        if (bArr2 == null) {
            bArr2 = new byte[remaining];
            bArr[remaining] = bArr2;
        }
        definiteLengthInputStream.readAllIntoByteArray(bArr2);
        return bArr2;
    }

    private static char[] getBMPCharBuffer(com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream) throws java.io.IOException {
        int i;
        int remaining = definiteLengthInputStream.getRemaining();
        if ((remaining & 1) != 0) {
            throw new java.io.IOException("malformed BMPString encoding encountered");
        }
        int i2 = remaining / 2;
        char[] cArr = new char[i2];
        byte[] bArr = new byte[8];
        int i3 = 0;
        int i4 = 0;
        while (remaining >= 8) {
            if (com.android.internal.org.bouncycastle.util.io.Streams.readFully(definiteLengthInputStream, bArr, 0, 8) == 8) {
                cArr[i4] = (char) ((bArr[0] << 8) | (bArr[1] & 255));
                cArr[i4 + 1] = (char) ((bArr[2] << 8) | (bArr[3] & 255));
                cArr[i4 + 2] = (char) ((bArr[4] << 8) | (bArr[5] & 255));
                cArr[i4 + 3] = (char) ((bArr[6] << 8) | (bArr[7] & 255));
                i4 += 4;
                remaining -= 8;
            } else {
                throw new java.io.EOFException("EOF encountered in middle of BMPString");
            }
        }
        if (remaining > 0) {
            if (com.android.internal.org.bouncycastle.util.io.Streams.readFully(definiteLengthInputStream, bArr, 0, remaining) != remaining) {
                throw new java.io.EOFException("EOF encountered in middle of BMPString");
            }
            while (true) {
                int i5 = i3 + 1;
                int i6 = i5 + 1;
                i = i4 + 1;
                cArr[i4] = (char) ((bArr[i3] << 8) | (bArr[i5] & 255));
                if (i6 >= remaining) {
                    break;
                }
                i3 = i6;
                i4 = i;
            }
            i4 = i;
        }
        if (definiteLengthInputStream.getRemaining() != 0 || i2 != i4) {
            throw new java.lang.IllegalStateException();
        }
        return cArr;
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Primitive createPrimitiveDERObject(int i, com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) throws java.io.IOException {
        switch (i) {
            case 1:
                return com.android.internal.org.bouncycastle.asn1.ASN1Boolean.fromOctetString(getBuffer(definiteLengthInputStream, bArr));
            case 2:
                return new com.android.internal.org.bouncycastle.asn1.ASN1Integer(definiteLengthInputStream.toByteArray(), false);
            case 3:
                return com.android.internal.org.bouncycastle.asn1.ASN1BitString.fromInputStream(definiteLengthInputStream.getRemaining(), definiteLengthInputStream);
            case 4:
                return new com.android.internal.org.bouncycastle.asn1.DEROctetString(definiteLengthInputStream.toByteArray());
            case 5:
                return com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
            case 6:
                return com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.fromOctetString(getBuffer(definiteLengthInputStream, bArr));
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 29:
            default:
                throw new java.io.IOException("unknown tag " + i + " encountered");
            case 10:
                return com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.fromOctetString(getBuffer(definiteLengthInputStream, bArr));
            case 12:
                return new com.android.internal.org.bouncycastle.asn1.DERUTF8String(definiteLengthInputStream.toByteArray());
            case 18:
                return new com.android.internal.org.bouncycastle.asn1.DERNumericString(definiteLengthInputStream.toByteArray());
            case 19:
                return new com.android.internal.org.bouncycastle.asn1.DERPrintableString(definiteLengthInputStream.toByteArray());
            case 20:
                return new com.android.internal.org.bouncycastle.asn1.DERT61String(definiteLengthInputStream.toByteArray());
            case 21:
                return new com.android.internal.org.bouncycastle.asn1.DERVideotexString(definiteLengthInputStream.toByteArray());
            case 22:
                return new com.android.internal.org.bouncycastle.asn1.DERIA5String(definiteLengthInputStream.toByteArray());
            case 23:
                return new com.android.internal.org.bouncycastle.asn1.ASN1UTCTime(definiteLengthInputStream.toByteArray());
            case 24:
                return new com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime(definiteLengthInputStream.toByteArray());
            case 25:
                return new com.android.internal.org.bouncycastle.asn1.DERGraphicString(definiteLengthInputStream.toByteArray());
            case 26:
                return new com.android.internal.org.bouncycastle.asn1.DERVisibleString(definiteLengthInputStream.toByteArray());
            case 27:
                return new com.android.internal.org.bouncycastle.asn1.DERGeneralString(definiteLengthInputStream.toByteArray());
            case 28:
                return new com.android.internal.org.bouncycastle.asn1.DERUniversalString(definiteLengthInputStream.toByteArray());
            case 30:
                return new com.android.internal.org.bouncycastle.asn1.DERBMPString(getBMPCharBuffer(definiteLengthInputStream));
        }
    }
}
