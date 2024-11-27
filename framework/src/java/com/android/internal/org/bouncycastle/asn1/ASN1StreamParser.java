package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1StreamParser {
    private final java.io.InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public ASN1StreamParser(java.io.InputStream inputStream) {
        this(inputStream, com.android.internal.org.bouncycastle.asn1.StreamUtil.findLimit(inputStream));
    }

    public ASN1StreamParser(java.io.InputStream inputStream, int i) {
        this._in = inputStream;
        this._limit = i;
        this.tmpBuffers = new byte[11][];
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new java.io.ByteArrayInputStream(bArr), bArr.length);
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable readIndef(int i) throws java.io.IOException {
        switch (i) {
            case 4:
                return new com.android.internal.org.bouncycastle.asn1.BEROctetStringParser(this);
            case 8:
                return new com.android.internal.org.bouncycastle.asn1.DERExternalParser(this);
            case 16:
                return new com.android.internal.org.bouncycastle.asn1.BERSequenceParser(this);
            case 17:
                return new com.android.internal.org.bouncycastle.asn1.BERSetParser(this);
            default:
                throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("unknown BER object encountered: 0x" + java.lang.Integer.toHexString(i));
        }
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable readImplicit(boolean z, int i) throws java.io.IOException {
        if (this._in instanceof com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) {
            if (!z) {
                throw new java.io.IOException("indefinite-length primitive encoding encountered");
            }
            return readIndef(i);
        }
        if (z) {
            switch (i) {
                case 4:
                    return new com.android.internal.org.bouncycastle.asn1.BEROctetStringParser(this);
                case 16:
                    return new com.android.internal.org.bouncycastle.asn1.DLSequenceParser(this);
                case 17:
                    return new com.android.internal.org.bouncycastle.asn1.DLSetParser(this);
            }
        }
        switch (i) {
            case 4:
                return new com.android.internal.org.bouncycastle.asn1.DEROctetStringParser((com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream) this._in);
            case 16:
                throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
            case 17:
                throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
        }
        throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("implicit tagging not implemented");
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Primitive readTaggedObject(boolean z, int i) throws java.io.IOException {
        if (!z) {
            return new com.android.internal.org.bouncycastle.asn1.DLTaggedObject(false, i, new com.android.internal.org.bouncycastle.asn1.DEROctetString(((com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream) this._in).toByteArray()));
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector readVector = readVector();
        if (this._in instanceof com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) {
            if (readVector.size() == 1) {
                return new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(true, i, readVector.get(0));
            }
            return new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(false, i, com.android.internal.org.bouncycastle.asn1.BERFactory.createSequence(readVector));
        }
        if (readVector.size() == 1) {
            return new com.android.internal.org.bouncycastle.asn1.DLTaggedObject(true, i, readVector.get(0));
        }
        return new com.android.internal.org.bouncycastle.asn1.DLTaggedObject(false, i, com.android.internal.org.bouncycastle.asn1.DLFactory.createSequence(readVector));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
        int read = this._in.read();
        if (read == -1) {
            return null;
        }
        set00Check(false);
        int readTagNumber = com.android.internal.org.bouncycastle.asn1.ASN1InputStream.readTagNumber(this._in, read);
        boolean z = (read & 32) != 0;
        int readLength = com.android.internal.org.bouncycastle.asn1.ASN1InputStream.readLength(this._in, this._limit, readTagNumber == 4 || readTagNumber == 16 || readTagNumber == 17 || readTagNumber == 8);
        if (readLength < 0) {
            if (!z) {
                throw new java.io.IOException("indefinite-length primitive encoding encountered");
            }
            com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser = new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(new com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream(this._in, this._limit), this._limit);
            if ((read & 64) != 0) {
                return new com.android.internal.org.bouncycastle.asn1.BERApplicationSpecificParser(readTagNumber, aSN1StreamParser);
            }
            if ((read & 128) != 0) {
                return new com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser(true, readTagNumber, aSN1StreamParser);
            }
            return aSN1StreamParser.readIndef(readTagNumber);
        }
        com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream = new com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream(this._in, readLength, this._limit);
        if ((read & 64) != 0) {
            return new com.android.internal.org.bouncycastle.asn1.DLApplicationSpecific(z, readTagNumber, definiteLengthInputStream.toByteArray());
        }
        if ((read & 128) != 0) {
            return new com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser(z, readTagNumber, new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream));
        }
        if (z) {
            switch (readTagNumber) {
                case 4:
                    return new com.android.internal.org.bouncycastle.asn1.BEROctetStringParser(new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream));
                case 8:
                    return new com.android.internal.org.bouncycastle.asn1.DERExternalParser(new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream));
                case 16:
                    return new com.android.internal.org.bouncycastle.asn1.DLSequenceParser(new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream));
                case 17:
                    return new com.android.internal.org.bouncycastle.asn1.DLSetParser(new com.android.internal.org.bouncycastle.asn1.ASN1StreamParser(definiteLengthInputStream));
                default:
                    throw new java.io.IOException("unknown tag " + readTagNumber + " encountered");
            }
        }
        switch (readTagNumber) {
            case 4:
                return new com.android.internal.org.bouncycastle.asn1.DEROctetStringParser(definiteLengthInputStream);
            default:
                try {
                    return com.android.internal.org.bouncycastle.asn1.ASN1InputStream.createPrimitiveDERObject(readTagNumber, definiteLengthInputStream, this.tmpBuffers);
                } catch (java.lang.IllegalArgumentException e) {
                    throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("corrupted stream detected", e);
                }
        }
    }

    private void set00Check(boolean z) {
        if (this._in instanceof com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) {
            ((com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) this._in).setEofOn00(z);
        }
    }

    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector readVector() throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject = readObject();
        if (readObject == null) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(0);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        do {
            if (readObject instanceof com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable) {
                aSN1EncodableVector.add(((com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable) readObject).getLoadedObject());
            } else {
                aSN1EncodableVector.add(readObject.toASN1Primitive());
            }
            readObject = readObject();
        } while (readObject != null);
        return aSN1EncodableVector;
    }
}
