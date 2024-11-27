package com.android.server.locksettings.recoverablekeystore.serialization;

/* loaded from: classes2.dex */
public class KeyChainSnapshotSerializer {
    private KeyChainSnapshotSerializer() {
    }

    public static void serialize(android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot, java.io.OutputStream outputStream) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((java.lang.String) null, (java.lang.Boolean) null);
        resolveSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainSnapshot");
        writeKeyChainSnapshotProperties(resolveSerializer, keyChainSnapshot);
        writeKeyChainProtectionParams(resolveSerializer, keyChainSnapshot.getKeyChainProtectionParams());
        writeApplicationKeys(resolveSerializer, keyChainSnapshot.getWrappedApplicationKeys());
        resolveSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainSnapshot");
        resolveSerializer.endDocument();
    }

    private static void writeApplicationKeys(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKeysList");
        for (android.security.keystore.recovery.WrappedApplicationKey wrappedApplicationKey : list) {
            typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKey");
            writeApplicationKeyProperties(typedXmlSerializer, wrappedApplicationKey);
            typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKey");
        }
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "applicationKeysList");
    }

    private static void writeApplicationKeyProperties(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.security.keystore.recovery.WrappedApplicationKey wrappedApplicationKey) throws java.io.IOException {
        writePropertyTag(typedXmlSerializer, "alias", wrappedApplicationKey.getAlias());
        writePropertyTag(typedXmlSerializer, "keyMaterial", wrappedApplicationKey.getEncryptedKeyMaterial());
        writePropertyTag(typedXmlSerializer, "keyMetadata", wrappedApplicationKey.getMetadata());
    }

    private static void writeKeyChainProtectionParams(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParamsList");
        for (android.security.keystore.recovery.KeyChainProtectionParams keyChainProtectionParams : list) {
            typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParams");
            writeKeyChainProtectionParamsProperties(typedXmlSerializer, keyChainProtectionParams);
            typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParams");
        }
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyChainProtectionParamsList");
    }

    private static void writeKeyChainProtectionParamsProperties(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.security.keystore.recovery.KeyChainProtectionParams keyChainProtectionParams) throws java.io.IOException {
        writePropertyTag(typedXmlSerializer, "userSecretType", keyChainProtectionParams.getUserSecretType());
        writePropertyTag(typedXmlSerializer, "lockScreenUiType", keyChainProtectionParams.getLockScreenUiFormat());
        writeKeyDerivationParams(typedXmlSerializer, keyChainProtectionParams.getKeyDerivationParams());
    }

    private static void writeKeyDerivationParams(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.security.keystore.recovery.KeyDerivationParams keyDerivationParams) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyDerivationParams");
        writeKeyDerivationParamsProperties(typedXmlSerializer, keyDerivationParams);
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, "keyDerivationParams");
    }

    private static void writeKeyDerivationParamsProperties(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.security.keystore.recovery.KeyDerivationParams keyDerivationParams) throws java.io.IOException {
        writePropertyTag(typedXmlSerializer, "algorithm", keyDerivationParams.getAlgorithm());
        writePropertyTag(typedXmlSerializer, "salt", keyDerivationParams.getSalt());
        writePropertyTag(typedXmlSerializer, "memoryDifficulty", keyDerivationParams.getMemoryDifficulty());
    }

    private static void writeKeyChainSnapshotProperties(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        writePropertyTag(typedXmlSerializer, "snapshotVersion", keyChainSnapshot.getSnapshotVersion());
        writePropertyTag(typedXmlSerializer, "maxAttempts", keyChainSnapshot.getMaxAttempts());
        writePropertyTag(typedXmlSerializer, "counterId", keyChainSnapshot.getCounterId());
        writePropertyTag(typedXmlSerializer, "recoveryKeyMaterial", keyChainSnapshot.getEncryptedRecoveryKeyBlob());
        writePropertyTag(typedXmlSerializer, "serverParams", keyChainSnapshot.getServerParams());
        writePropertyTag(typedXmlSerializer, "thmCertPath", keyChainSnapshot.getTrustedHardwareCertPath());
    }

    private static void writePropertyTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, long j) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        typedXmlSerializer.text(java.lang.Long.toString(j));
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
    }

    private static void writePropertyTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        typedXmlSerializer.text(str2);
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
    }

    private static void writePropertyTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, @android.annotation.Nullable byte[] bArr) throws java.io.IOException {
        if (bArr == null) {
            return;
        }
        typedXmlSerializer.startTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
        typedXmlSerializer.text(android.util.Base64.encodeToString(bArr, 0));
        typedXmlSerializer.endTag(com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSchema.NAMESPACE, str);
    }

    private static void writePropertyTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.security.cert.CertPath certPath) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        writePropertyTag(typedXmlSerializer, str, certPath.getEncoded("PkiPath"));
    }
}
