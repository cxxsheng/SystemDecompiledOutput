package com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc;

/* loaded from: classes4.dex */
public class BcKeyStoreSpi extends java.security.KeyStoreSpi implements com.android.internal.org.bouncycastle.jce.interfaces.BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    private static final java.lang.String KEY_CIPHER = "PBEWithSHAAnd3-KeyTripleDES-CBC";
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    private static final int KEY_SALT_SIZE = 20;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 1024;
    static final int NULL = 0;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final java.lang.String STORE_CIPHER = "PBEWithSHAAndTwofish-CBC";
    private static final int STORE_SALT_SIZE = 20;
    private static final int STORE_VERSION = 2;
    protected int version;
    protected java.util.Hashtable table = new java.util.Hashtable();
    protected java.security.SecureRandom random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();

    public BcKeyStoreSpi(int i) {
        this.version = i;
    }

    private class StoreEntry {
        java.lang.String alias;
        java.security.cert.Certificate[] certChain;
        java.util.Date date;
        java.lang.Object obj;
        int type;

        StoreEntry(java.lang.String str, java.security.cert.Certificate certificate) {
            this.date = new java.util.Date();
            this.type = 1;
            this.alias = str;
            this.obj = certificate;
            this.certChain = null;
        }

        StoreEntry(java.lang.String str, byte[] bArr, java.security.cert.Certificate[] certificateArr) {
            this.date = new java.util.Date();
            this.type = 3;
            this.alias = str;
            this.obj = bArr;
            this.certChain = certificateArr;
        }

        StoreEntry(java.lang.String str, java.security.Key key, char[] cArr, java.security.cert.Certificate[] certificateArr) throws java.lang.Exception {
            this.date = new java.util.Date();
            this.type = 4;
            this.alias = str;
            this.certChain = certificateArr;
            byte[] bArr = new byte[20];
            com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.random.nextBytes(bArr);
            int nextInt = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.random.nextInt() & 1023) + 1024;
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(20);
            dataOutputStream.write(bArr);
            dataOutputStream.writeInt(nextInt);
            java.io.DataOutputStream dataOutputStream2 = new java.io.DataOutputStream(new javax.crypto.CipherOutputStream(dataOutputStream, com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.makePBECipher(com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.KEY_CIPHER, 1, cArr, bArr, nextInt)));
            com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.encodeKey(key, dataOutputStream2);
            dataOutputStream2.close();
            this.obj = byteArrayOutputStream.toByteArray();
        }

        StoreEntry(java.lang.String str, java.util.Date date, int i, java.lang.Object obj) {
            this.date = new java.util.Date();
            this.alias = str;
            this.date = date;
            this.type = i;
            this.obj = obj;
        }

        StoreEntry(java.lang.String str, java.util.Date date, int i, java.lang.Object obj, java.security.cert.Certificate[] certificateArr) {
            this.date = new java.util.Date();
            this.alias = str;
            this.date = date;
            this.type = i;
            this.obj = obj;
            this.certChain = certificateArr;
        }

        int getType() {
            return this.type;
        }

        java.lang.String getAlias() {
            return this.alias;
        }

        java.lang.Object getObject() {
            return this.obj;
        }

        java.lang.Object getObject(char[] cArr) throws java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
            java.security.Key decodeKey;
            byte[] bArr;
            int i;
            if ((cArr == null || cArr.length == 0) && (this.obj instanceof java.security.Key)) {
                return this.obj;
            }
            if (this.type == 4) {
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream((byte[]) this.obj));
                try {
                    byte[] bArr2 = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr2);
                    try {
                        return com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.decodeKey(new java.io.DataInputStream(new javax.crypto.CipherInputStream(dataInputStream, com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.makePBECipher(com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.KEY_CIPHER, 2, cArr, bArr2, dataInputStream.readInt()))));
                    } catch (java.lang.Exception e) {
                        java.io.DataInputStream dataInputStream2 = new java.io.DataInputStream(new java.io.ByteArrayInputStream((byte[]) this.obj));
                        byte[] bArr3 = new byte[dataInputStream2.readInt()];
                        dataInputStream2.readFully(bArr3);
                        int readInt = dataInputStream2.readInt();
                        try {
                            decodeKey = com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.decodeKey(new java.io.DataInputStream(new javax.crypto.CipherInputStream(dataInputStream2, com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.makePBECipher("BrokenPBEWithSHAAnd3-KeyTripleDES-CBC", 2, cArr, bArr3, readInt))));
                            bArr = bArr3;
                            i = readInt;
                        } catch (java.lang.Exception e2) {
                            java.io.DataInputStream dataInputStream3 = new java.io.DataInputStream(new java.io.ByteArrayInputStream((byte[]) this.obj));
                            byte[] bArr4 = new byte[dataInputStream3.readInt()];
                            dataInputStream3.readFully(bArr4);
                            int readInt2 = dataInputStream3.readInt();
                            decodeKey = com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.decodeKey(new java.io.DataInputStream(new javax.crypto.CipherInputStream(dataInputStream3, com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.makePBECipher("OldPBEWithSHAAnd3-KeyTripleDES-CBC", 2, cArr, bArr4, readInt2))));
                            bArr = bArr4;
                            i = readInt2;
                        }
                        if (decodeKey != null) {
                            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
                            dataOutputStream.writeInt(bArr.length);
                            dataOutputStream.write(bArr);
                            dataOutputStream.writeInt(i);
                            java.io.DataOutputStream dataOutputStream2 = new java.io.DataOutputStream(new javax.crypto.CipherOutputStream(dataOutputStream, com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.makePBECipher(com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.KEY_CIPHER, 1, cArr, bArr, i)));
                            com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this.encodeKey(decodeKey, dataOutputStream2);
                            dataOutputStream2.close();
                            this.obj = byteArrayOutputStream.toByteArray();
                            return decodeKey;
                        }
                        throw new java.security.UnrecoverableKeyException("no match");
                    }
                } catch (java.lang.Exception e3) {
                    throw new java.security.UnrecoverableKeyException("no match");
                }
            }
            throw new java.lang.RuntimeException("forget something!");
        }

        java.security.cert.Certificate[] getCertificateChain() {
            return this.certChain;
        }

        java.util.Date getDate() {
            return this.date;
        }
    }

    private void encodeCertificate(java.security.cert.Certificate certificate, java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        try {
            byte[] encoded = certificate.getEncoded();
            dataOutputStream.writeUTF(certificate.getType());
            dataOutputStream.writeInt(encoded.length);
            dataOutputStream.write(encoded);
        } catch (java.security.cert.CertificateEncodingException e) {
            throw new java.io.IOException(e.toString());
        }
    }

    private java.security.cert.Certificate decodeCertificate(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        java.lang.String readUTF = dataInputStream.readUTF();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        try {
            return this.helper.createCertificateFactory(readUTF).generateCertificate(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.NoSuchProviderException e) {
            throw new java.io.IOException(e.toString());
        } catch (java.security.cert.CertificateException e2) {
            throw new java.io.IOException(e2.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void encodeKey(java.security.Key key, java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        byte[] encoded = key.getEncoded();
        if (key instanceof java.security.PrivateKey) {
            dataOutputStream.write(0);
        } else if (key instanceof java.security.PublicKey) {
            dataOutputStream.write(1);
        } else {
            dataOutputStream.write(2);
        }
        dataOutputStream.writeUTF(key.getFormat());
        dataOutputStream.writeUTF(key.getAlgorithm());
        dataOutputStream.writeInt(encoded.length);
        dataOutputStream.write(encoded);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.security.Key decodeKey(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        java.security.spec.KeySpec pKCS8EncodedKeySpec;
        int read = dataInputStream.read();
        java.lang.String readUTF = dataInputStream.readUTF();
        java.lang.String readUTF2 = dataInputStream.readUTF();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        if (readUTF.equals("PKCS#8") || readUTF.equals("PKCS8")) {
            pKCS8EncodedKeySpec = new java.security.spec.PKCS8EncodedKeySpec(bArr);
        } else if (readUTF.equals("X.509") || readUTF.equals("X509")) {
            pKCS8EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(bArr);
        } else {
            if (readUTF.equals("RAW")) {
                return new javax.crypto.spec.SecretKeySpec(bArr, readUTF2);
            }
            throw new java.io.IOException("Key format " + readUTF + " not recognised!");
        }
        try {
            switch (read) {
                case 0:
                    return com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(bArr));
                case 1:
                    return com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(bArr));
                case 2:
                    return this.helper.createSecretKeyFactory(readUTF2).generateSecret(pKCS8EncodedKeySpec);
                default:
                    throw new java.io.IOException("Key type " + read + " not recognised!");
            }
        } catch (java.lang.Exception e) {
            throw new java.io.IOException("Exception creating key: " + e.toString());
        }
    }

    protected javax.crypto.Cipher makePBECipher(java.lang.String str, int i, char[] cArr, byte[] bArr, int i2) throws java.io.IOException {
        try {
            javax.crypto.spec.PBEKeySpec pBEKeySpec = new javax.crypto.spec.PBEKeySpec(cArr);
            javax.crypto.SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(str);
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(bArr, i2);
            javax.crypto.Cipher createCipher = this.helper.createCipher(str);
            createCipher.init(i, createSecretKeyFactory.generateSecret(pBEKeySpec), pBEParameterSpec);
            return createCipher;
        } catch (java.lang.Exception e) {
            throw new java.io.IOException("Error initialising store of key store: " + e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.BCKeyStore
    public void setRandom(java.security.SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Enumeration engineAliases() {
        return this.table.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(java.lang.String str) {
        return this.table.get(str) != null;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(java.lang.String str) throws java.security.KeyStoreException {
        if (this.table.get(str) == null) {
            return;
        }
        this.table.remove(str);
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate engineGetCertificate(java.lang.String str) {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            if (storeEntry.getType() == 1) {
                return (java.security.cert.Certificate) storeEntry.getObject();
            }
            java.security.cert.Certificate[] certificateChain = storeEntry.getCertificateChain();
            if (certificateChain != null) {
                return certificateChain[0];
            }
            return null;
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public java.lang.String engineGetCertificateAlias(java.security.cert.Certificate certificate) {
        java.util.Enumeration elements = this.table.elements();
        while (elements.hasMoreElements()) {
            com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) elements.nextElement();
            if (storeEntry.getObject() instanceof java.security.cert.Certificate) {
                if (((java.security.cert.Certificate) storeEntry.getObject()).equals(certificate)) {
                    return storeEntry.getAlias();
                }
            } else {
                java.security.cert.Certificate[] certificateChain = storeEntry.getCertificateChain();
                if (certificateChain != null && certificateChain[0].equals(certificate)) {
                    return storeEntry.getAlias();
                }
            }
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String str) {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            return storeEntry.getCertificateChain();
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Date engineGetCreationDate(java.lang.String str) {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            return storeEntry.getDate();
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public java.security.Key engineGetKey(java.lang.String str, char[] cArr) throws java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        if (storeEntry == null || storeEntry.getType() == 1) {
            return null;
        }
        return (java.security.Key) storeEntry.getObject(cArr);
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(java.lang.String str) {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        return storeEntry != null && storeEntry.getType() == 1;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(java.lang.String str) {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        return (storeEntry == null || storeEntry.getType() == 1) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(java.lang.String str, java.security.cert.Certificate certificate) throws java.security.KeyStoreException {
        com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) this.table.get(str);
        if (storeEntry != null && storeEntry.getType() != 1) {
            throw new java.security.KeyStoreException("key store already has a key entry with alias " + str);
        }
        this.table.put(str, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(str, certificate));
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, byte[] bArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        this.table.put(str, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(str, bArr, certificateArr));
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, java.security.Key key, char[] cArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        if ((key instanceof java.security.PrivateKey) && certificateArr == null) {
            throw new java.security.KeyStoreException("no certificate chain for private key");
        }
        try {
            this.table.put(str, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(str, key, cArr, certificateArr));
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.BCKeyStoreException(e.toString(), e);
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.table.size();
    }

    protected void loadStore(java.io.InputStream inputStream) throws java.io.IOException {
        java.security.cert.Certificate[] certificateArr;
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        for (int read = dataInputStream.read(); read > 0; read = dataInputStream.read()) {
            java.lang.String readUTF = dataInputStream.readUTF();
            java.util.Date date = new java.util.Date(dataInputStream.readLong());
            int readInt = dataInputStream.readInt();
            if (readInt == 0) {
                certificateArr = null;
            } else {
                java.security.cert.Certificate[] certificateArr2 = new java.security.cert.Certificate[readInt];
                for (int i = 0; i != readInt; i++) {
                    certificateArr2[i] = decodeCertificate(dataInputStream);
                }
                certificateArr = certificateArr2;
            }
            switch (read) {
                case 1:
                    this.table.put(readUTF, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(readUTF, date, 1, decodeCertificate(dataInputStream)));
                    break;
                case 2:
                    this.table.put(readUTF, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(readUTF, date, 2, decodeKey(dataInputStream), certificateArr));
                    break;
                case 3:
                case 4:
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr);
                    this.table.put(readUTF, new com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry(readUTF, date, read, bArr, certificateArr));
                    break;
                default:
                    throw new java.io.IOException("Unknown object type in store.");
            }
        }
    }

    protected void saveStore(java.io.OutputStream outputStream) throws java.io.IOException {
        java.util.Enumeration elements = this.table.elements();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(outputStream);
        while (true) {
            if (elements.hasMoreElements()) {
                com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry storeEntry = (com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry) elements.nextElement();
                dataOutputStream.write(storeEntry.getType());
                dataOutputStream.writeUTF(storeEntry.getAlias());
                dataOutputStream.writeLong(storeEntry.getDate().getTime());
                java.security.cert.Certificate[] certificateChain = storeEntry.getCertificateChain();
                if (certificateChain == null) {
                    dataOutputStream.writeInt(0);
                } else {
                    dataOutputStream.writeInt(certificateChain.length);
                    for (int i = 0; i != certificateChain.length; i++) {
                        encodeCertificate(certificateChain[i], dataOutputStream);
                    }
                }
                switch (storeEntry.getType()) {
                    case 1:
                        encodeCertificate((java.security.cert.Certificate) storeEntry.getObject(), dataOutputStream);
                        break;
                    case 2:
                        encodeKey((java.security.Key) storeEntry.getObject(), dataOutputStream);
                        break;
                    case 3:
                    case 4:
                        byte[] bArr = (byte[]) storeEntry.getObject();
                        dataOutputStream.writeInt(bArr.length);
                        dataOutputStream.write(bArr);
                        break;
                    default:
                        throw new java.io.IOException("Unknown object type in store.");
                }
            } else {
                dataOutputStream.write(0);
                return;
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(java.io.InputStream inputStream, char[] cArr) throws java.io.IOException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters;
        this.table.clear();
        if (inputStream == null) {
            return;
        }
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        int readInt = dataInputStream.readInt();
        if (readInt != 2 && readInt != 0 && readInt != 1) {
            throw new java.io.IOException("Wrong version of key store.");
        }
        int readInt2 = dataInputStream.readInt();
        if (readInt2 <= 0) {
            throw new java.io.IOException("Invalid salt detected");
        }
        byte[] bArr = new byte[readInt2];
        dataInputStream.readFully(bArr);
        int readInt3 = dataInputStream.readInt();
        com.android.internal.org.bouncycastle.crypto.macs.HMac hMac = new com.android.internal.org.bouncycastle.crypto.macs.HMac(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
        if (cArr != null && cArr.length != 0) {
            byte[] PKCS12PasswordToBytes = com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS12PasswordToBytes(cArr);
            com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator pKCS12ParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
            pKCS12ParametersGenerator.init(PKCS12PasswordToBytes, bArr, readInt3);
            if (readInt != 2) {
                generateDerivedMacParameters = pKCS12ParametersGenerator.generateDerivedMacParameters(hMac.getMacSize());
            } else {
                generateDerivedMacParameters = pKCS12ParametersGenerator.generateDerivedMacParameters(hMac.getMacSize() * 8);
            }
            com.android.internal.org.bouncycastle.util.Arrays.fill(PKCS12PasswordToBytes, (byte) 0);
            hMac.init(generateDerivedMacParameters);
            loadStore(new com.android.internal.org.bouncycastle.crypto.io.MacInputStream(dataInputStream, hMac));
            byte[] bArr2 = new byte[hMac.getMacSize()];
            hMac.doFinal(bArr2, 0);
            byte[] bArr3 = new byte[hMac.getMacSize()];
            dataInputStream.readFully(bArr3);
            if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(bArr2, bArr3)) {
                this.table.clear();
                throw new java.io.IOException("KeyStore integrity check failed.");
            }
            return;
        }
        loadStore(dataInputStream);
        dataInputStream.readFully(new byte[hMac.getMacSize()]);
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(java.io.OutputStream outputStream, char[] cArr) throws java.io.IOException {
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(outputStream);
        byte[] bArr = new byte[20];
        int nextInt = (this.random.nextInt() & 1023) + 1024;
        this.random.nextBytes(bArr);
        dataOutputStream.writeInt(this.version);
        dataOutputStream.writeInt(20);
        dataOutputStream.write(bArr);
        dataOutputStream.writeInt(nextInt);
        com.android.internal.org.bouncycastle.crypto.macs.HMac hMac = new com.android.internal.org.bouncycastle.crypto.macs.HMac(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
        com.android.internal.org.bouncycastle.crypto.io.MacOutputStream macOutputStream = new com.android.internal.org.bouncycastle.crypto.io.MacOutputStream(hMac);
        com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator pKCS12ParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
        byte[] PKCS12PasswordToBytes = com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS12PasswordToBytes(cArr);
        pKCS12ParametersGenerator.init(PKCS12PasswordToBytes, bArr, nextInt);
        if (this.version < 2) {
            hMac.init(pKCS12ParametersGenerator.generateDerivedMacParameters(hMac.getMacSize()));
        } else {
            hMac.init(pKCS12ParametersGenerator.generateDerivedMacParameters(hMac.getMacSize() * 8));
        }
        for (int i = 0; i != PKCS12PasswordToBytes.length; i++) {
            PKCS12PasswordToBytes[i] = 0;
        }
        saveStore(new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(dataOutputStream, macOutputStream));
        byte[] bArr2 = new byte[hMac.getMacSize()];
        hMac.doFinal(bArr2, 0);
        dataOutputStream.write(bArr2);
        dataOutputStream.close();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineProbe(java.io.InputStream inputStream) throws java.io.IOException {
        if (inputStream == null) {
            throw new java.lang.NullPointerException("input stream must not be null");
        }
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        int readInt = dataInputStream.readInt();
        if (readInt != 2 && readInt != 0 && readInt != 1) {
            return false;
        }
        int readInt2 = dataInputStream.readInt();
        byte[] bArr = new byte[readInt2];
        return readInt2 == 20;
    }

    public static class BouncyCastleStore extends com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi {
        public BouncyCastleStore() {
            super(1);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi, java.security.KeyStoreSpi
        public void engineLoad(java.io.InputStream inputStream, char[] cArr) throws java.io.IOException {
            java.lang.String str;
            this.table.clear();
            if (inputStream == null) {
                return;
            }
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
            int readInt = dataInputStream.readInt();
            if (readInt != 2 && readInt != 0 && readInt != 1) {
                throw new java.io.IOException("Wrong version of key store.");
            }
            int readInt2 = dataInputStream.readInt();
            byte[] bArr = new byte[readInt2];
            if (readInt2 != 20) {
                throw new java.io.IOException("Key store corrupted.");
            }
            dataInputStream.readFully(bArr);
            int readInt3 = dataInputStream.readInt();
            if (readInt3 < 0 || readInt3 > 65536) {
                throw new java.io.IOException("Key store corrupted.");
            }
            if (readInt == 0) {
                str = "OldPBEWithSHAAndTwofish-CBC";
            } else {
                str = com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.STORE_CIPHER;
            }
            javax.crypto.CipherInputStream cipherInputStream = new javax.crypto.CipherInputStream(dataInputStream, makePBECipher(str, 2, cArr, bArr, readInt3));
            com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest sHA1Digest = new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest();
            loadStore(new com.android.internal.org.bouncycastle.crypto.io.DigestInputStream(cipherInputStream, sHA1Digest));
            byte[] bArr2 = new byte[sHA1Digest.getDigestSize()];
            sHA1Digest.doFinal(bArr2, 0);
            byte[] bArr3 = new byte[sHA1Digest.getDigestSize()];
            com.android.internal.org.bouncycastle.util.io.Streams.readFully(cipherInputStream, bArr3);
            if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(bArr2, bArr3)) {
                this.table.clear();
                throw new java.io.IOException("KeyStore integrity check failed.");
            }
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi, java.security.KeyStoreSpi
        public void engineStore(java.io.OutputStream outputStream, char[] cArr) throws java.io.IOException {
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(outputStream);
            byte[] bArr = new byte[20];
            int nextInt = (this.random.nextInt() & 1023) + 1024;
            this.random.nextBytes(bArr);
            dataOutputStream.writeInt(this.version);
            dataOutputStream.writeInt(20);
            dataOutputStream.write(bArr);
            dataOutputStream.writeInt(nextInt);
            javax.crypto.CipherOutputStream cipherOutputStream = new javax.crypto.CipherOutputStream(dataOutputStream, makePBECipher(com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.STORE_CIPHER, 1, cArr, bArr, nextInt));
            com.android.internal.org.bouncycastle.crypto.io.DigestOutputStream digestOutputStream = new com.android.internal.org.bouncycastle.crypto.io.DigestOutputStream(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
            saveStore(new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(cipherOutputStream, digestOutputStream));
            cipherOutputStream.write(digestOutputStream.getDigest());
            cipherOutputStream.close();
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi, java.security.KeyStoreSpi
        public boolean engineProbe(java.io.InputStream inputStream) throws java.io.IOException {
            if (inputStream == null) {
                throw new java.lang.NullPointerException("input stream must not be null");
            }
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
            int readInt = dataInputStream.readInt();
            if (readInt != 2 && readInt != 0 && readInt != 1) {
                return false;
            }
            int readInt2 = dataInputStream.readInt();
            byte[] bArr = new byte[readInt2];
            return readInt2 == 20;
        }
    }

    public static class Std extends com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi {
        public Std() {
            super(2);
        }
    }

    public static class Version1 extends com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi {
        public Version1() {
            super(1);
        }
    }

    private static class BCKeyStoreException extends java.security.KeyStoreException {
        private final java.lang.Exception cause;

        public BCKeyStoreException(java.lang.String str, java.lang.Exception exc) {
            super(str);
            this.cause = exc;
        }

        @Override // java.lang.Throwable
        public java.lang.Throwable getCause() {
            return this.cause;
        }
    }
}
