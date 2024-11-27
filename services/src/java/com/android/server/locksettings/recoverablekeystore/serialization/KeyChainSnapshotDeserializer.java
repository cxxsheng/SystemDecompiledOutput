package com.android.server.locksettings.recoverablekeystore.serialization;

/* loaded from: classes2.dex */
public class KeyChainSnapshotDeserializer {
    private KeyChainSnapshotDeserializer() {
    }

    public static android.security.keystore.recovery.KeyChainSnapshot deserialize(java.io.InputStream inputStream) throws com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException, java.io.IOException {
        try {
            return deserializeInternal(inputStream);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Malformed KeyChainSnapshot XML", e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0043, code lost:
    
        if (r3.equals("serverParams") != false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.security.keystore.recovery.KeyChainSnapshot deserializeInternal(java.io.InputStream inputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        resolvePullParser.nextTag();
        resolvePullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainSnapshot");
        android.security.keystore.recovery.KeyChainSnapshot.Builder builder = new android.security.keystore.recovery.KeyChainSnapshot.Builder();
        while (true) {
            char c = 3;
            if (resolvePullParser.next() != 3) {
                if (resolvePullParser.getEventType() == 2) {
                    java.lang.String name = resolvePullParser.getName();
                    switch (name.hashCode()) {
                        case -1719931702:
                            if (name.equals("maxAttempts")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1388433662:
                            if (name.equals("backendPublicKey")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1370381871:
                            if (name.equals("recoveryKeyMaterial")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1368437758:
                            if (name.equals("thmCertPath")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 481270388:
                            if (name.equals("snapshotVersion")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1190285858:
                            if (name.equals("applicationKeysList")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 1352257591:
                            if (name.equals("counterId")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1596875199:
                            if (name.equals("keyChainProtectionParamsList")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1806980777:
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            builder.setSnapshotVersion(readIntTag(resolvePullParser, "snapshotVersion"));
                            break;
                        case 1:
                            builder.setEncryptedRecoveryKeyBlob(readBlobTag(resolvePullParser, "recoveryKeyMaterial"));
                            break;
                        case 2:
                            builder.setCounterId(readLongTag(resolvePullParser, "counterId"));
                            break;
                        case 3:
                            builder.setServerParams(readBlobTag(resolvePullParser, "serverParams"));
                            break;
                        case 4:
                            builder.setMaxAttempts(readIntTag(resolvePullParser, "maxAttempts"));
                            break;
                        case 5:
                            try {
                                builder.setTrustedHardwareCertPath(readCertPathTag(resolvePullParser, "thmCertPath"));
                                break;
                            } catch (java.security.cert.CertificateException e) {
                                throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Could not set trustedHardwareCertPath", e);
                            }
                        case 6:
                            break;
                        case 7:
                            builder.setKeyChainProtectionParams(readKeyChainProtectionParamsList(resolvePullParser));
                            break;
                        case '\b':
                            builder.setWrappedApplicationKeys(readWrappedApplicationKeys(resolvePullParser));
                            break;
                        default:
                            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "Unexpected tag %s in keyChainSnapshot", name));
                    }
                }
            } else {
                resolvePullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainSnapshot");
                try {
                    return builder.build();
                } catch (java.lang.NullPointerException e2) {
                    throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Failed to build KeyChainSnapshot", e2);
                }
            }
        }
    }

    private static java.util.List<android.security.keystore.recovery.WrappedApplicationKey> readWrappedApplicationKeys(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKeysList");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                arrayList.add(readWrappedApplicationKey(typedXmlPullParser));
            }
        }
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKeysList");
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.security.keystore.recovery.WrappedApplicationKey readWrappedApplicationKey(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        char c;
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKey");
        android.security.keystore.recovery.WrappedApplicationKey.Builder builder = new android.security.keystore.recovery.WrappedApplicationKey.Builder();
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1712279890:
                        if (name.equals("keyMetadata")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -963209050:
                        if (name.equals("keyMaterial")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 92902992:
                        if (name.equals("alias")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        builder.setAlias(readStringTag(typedXmlPullParser, "alias"));
                        break;
                    case 1:
                        builder.setEncryptedKeyMaterial(readBlobTag(typedXmlPullParser, "keyMaterial"));
                        break;
                    case 2:
                        builder.setMetadata(readBlobTag(typedXmlPullParser, "keyMetadata"));
                        break;
                    default:
                        throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "Unexpected tag %s in wrappedApplicationKey", name));
                }
            }
        }
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKey");
        try {
            return builder.build();
        } catch (java.lang.NullPointerException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Failed to build WrappedApplicationKey", e);
        }
    }

    private static java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> readKeyChainProtectionParamsList(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParamsList");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                arrayList.add(readKeyChainProtectionParams(typedXmlPullParser));
            }
        }
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParamsList");
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.security.keystore.recovery.KeyChainProtectionParams readKeyChainProtectionParams(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        char c;
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParams");
        android.security.keystore.recovery.KeyChainProtectionParams.Builder builder = new android.security.keystore.recovery.KeyChainProtectionParams.Builder();
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -776797115:
                        if (name.equals("lockScreenUiType")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -696958923:
                        if (name.equals("userSecretType")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 912448924:
                        if (name.equals("keyDerivationParams")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        builder.setLockScreenUiFormat(readIntTag(typedXmlPullParser, "lockScreenUiType"));
                        break;
                    case 1:
                        builder.setUserSecretType(readIntTag(typedXmlPullParser, "userSecretType"));
                        break;
                    case 2:
                        builder.setKeyDerivationParams(readKeyDerivationParams(typedXmlPullParser));
                        break;
                    default:
                        throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "Unexpected tag %s in keyChainProtectionParams", name));
                }
            }
        }
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParams");
        try {
            return builder.build();
        } catch (java.lang.NullPointerException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Failed to build KeyChainProtectionParams", e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.security.keystore.recovery.KeyDerivationParams readKeyDerivationParams(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        android.security.keystore.recovery.KeyDerivationParams createSha256Params;
        boolean z;
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyDerivationParams");
        byte[] bArr = null;
        int i = -1;
        int i2 = -1;
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -973274212:
                        if (name.equals("memoryDifficulty")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 3522646:
                        if (name.equals("salt")) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    case 225490031:
                        if (name.equals("algorithm")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        i2 = readIntTag(typedXmlPullParser, "memoryDifficulty");
                        break;
                    case true:
                        i = readIntTag(typedXmlPullParser, "algorithm");
                        break;
                    case true:
                        bArr = readBlobTag(typedXmlPullParser, "salt");
                        break;
                    default:
                        throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "Unexpected tag %s in keyDerivationParams", name));
                }
            }
        }
        if (bArr == null) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("salt was not set in keyDerivationParams");
        }
        switch (i) {
            case 1:
                createSha256Params = android.security.keystore.recovery.KeyDerivationParams.createSha256Params(bArr);
                break;
            case 2:
                createSha256Params = android.security.keystore.recovery.KeyDerivationParams.createScryptParams(bArr, i2);
                break;
            default:
                throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Unknown algorithm in keyDerivationParams");
        }
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyDerivationParams");
        return createSha256Params;
    }

    private static int readIntTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        java.lang.String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        try {
            return java.lang.Integer.valueOf(readText).intValue();
        } catch (java.lang.NumberFormatException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "%s expected int but got '%s'", str, readText), e);
        }
    }

    private static long readLongTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        java.lang.String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        try {
            return java.lang.Long.valueOf(readText).longValue();
        } catch (java.lang.NumberFormatException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "%s expected long but got '%s'", str, readText), e);
        }
    }

    private static java.lang.String readStringTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        java.lang.String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        return readText;
    }

    private static byte[] readBlobTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        typedXmlPullParser.require(2, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        java.lang.String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        try {
            return android.util.Base64.decode(readText, 0);
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException(java.lang.String.format(java.util.Locale.US, "%s expected base64 encoded bytes but got '%s'", str, readText), e);
        }
    }

    private static java.security.cert.CertPath readCertPathTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        try {
            return java.security.cert.CertificateFactory.getInstance("X.509").generateCertPath(new java.io.ByteArrayInputStream(readBlobTag(typedXmlPullParser, str)));
        } catch (java.security.cert.CertificateException e) {
            throw new com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException("Could not parse CertPath in tag " + str, e);
        }
    }

    private static java.lang.String readText(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (typedXmlPullParser.next() != 4) {
            return "";
        }
        java.lang.String text = typedXmlPullParser.getText();
        typedXmlPullParser.nextTag();
        return text;
    }
}
