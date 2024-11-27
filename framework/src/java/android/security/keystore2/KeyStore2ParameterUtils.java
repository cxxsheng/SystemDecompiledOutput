package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class KeyStore2ParameterUtils {
    static android.hardware.security.keymint.KeyParameter makeBool(int i) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1879048192) {
            throw new java.lang.IllegalArgumentException("Not a boolean tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.boolValue(true);
        return keyParameter;
    }

    static android.hardware.security.keymint.KeyParameter makeEnum(int i, int i2) {
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        switch (i) {
            case 268435458:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.algorithm(i2);
                return keyParameter;
            case 268435466:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.ecCurve(i2);
                return keyParameter;
            case android.hardware.security.keymint.Tag.HARDWARE_TYPE /* 268435760 */:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.securityLevel(i2);
                return keyParameter;
            case 268435960:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.hardwareAuthenticatorType(i2);
                return keyParameter;
            case 268436158:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.origin(i2);
                return keyParameter;
            case 536870913:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.keyPurpose(i2);
                return keyParameter;
            case 536870916:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.blockMode(i2);
                return keyParameter;
            case 536870917:
            case 536871115:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.digest(i2);
                return keyParameter;
            case 536870918:
                keyParameter.value = android.hardware.security.keymint.KeyParameterValue.paddingMode(i2);
                return keyParameter;
            default:
                throw new java.lang.IllegalArgumentException("Not an enum or repeatable enum tag: " + i);
        }
    }

    static android.hardware.security.keymint.KeyParameter makeInt(int i, int i2) {
        int tagType = android.security.keymaster.KeymasterDefs.getTagType(i);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new java.lang.IllegalArgumentException("Not an int or repeatable int tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.integer(i2);
        return keyParameter;
    }

    static android.hardware.security.keymint.KeyParameter makeLong(int i, long j) {
        int tagType = android.security.keymaster.KeymasterDefs.getTagType(i);
        if (tagType != 1342177280 && tagType != -1610612736) {
            throw new java.lang.IllegalArgumentException("Not a long or repeatable long tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.longInteger(j);
        return keyParameter;
    }

    static android.hardware.security.keymint.KeyParameter makeBytes(int i, byte[] bArr) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != -1879048192) {
            throw new java.lang.IllegalArgumentException("Not a bytes tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.blob(bArr);
        return keyParameter;
    }

    static android.hardware.security.keymint.KeyParameter makeBignum(int i, java.math.BigInteger bigInteger) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != Integer.MIN_VALUE) {
            throw new java.lang.IllegalArgumentException("Not a bignum tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.blob(bigInteger.toByteArray());
        return keyParameter;
    }

    static android.hardware.security.keymint.KeyParameter makeDate(int i, java.util.Date date) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1610612736) {
            throw new java.lang.IllegalArgumentException("Not a date tag: " + i);
        }
        android.hardware.security.keymint.KeyParameter keyParameter = new android.hardware.security.keymint.KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = android.hardware.security.keymint.KeyParameterValue.dateTime(date.getTime());
        return keyParameter;
    }

    static boolean isSecureHardware(int i) {
        return i == 1 || i == 2;
    }

    static long getUnsignedInt(android.system.keystore2.Authorization authorization) {
        if (android.security.keymaster.KeymasterDefs.getTagType(authorization.keyParameter.tag) != 805306368) {
            throw new java.lang.IllegalArgumentException("Not an int tag: " + authorization.keyParameter.tag);
        }
        return authorization.keyParameter.value.getInteger() & 4294967295L;
    }

    static java.util.Date getDate(android.system.keystore2.Authorization authorization) {
        if (android.security.keymaster.KeymasterDefs.getTagType(authorization.keyParameter.tag) != 1610612736) {
            throw new java.lang.IllegalArgumentException("Not a date tag: " + authorization.keyParameter.tag);
        }
        if (authorization.keyParameter.value.getDateTime() < 0) {
            throw new java.lang.IllegalArgumentException("Date Value too large: " + authorization.keyParameter.value.getDateTime());
        }
        return new java.util.Date(authorization.keyParameter.value.getDateTime());
    }

    static void forEachSetFlag(int i, java.util.function.Consumer<java.lang.Integer> consumer) {
        int i2 = 0;
        while (i != 0) {
            if ((i & 1) == 1) {
                consumer.accept(java.lang.Integer.valueOf(1 << i2));
            }
            i2++;
            i >>>= 1;
        }
    }

    private static long getRootSid() {
        long secureUserId = android.security.GateKeeper.getSecureUserId();
        if (secureUserId == 0) {
            throw new java.lang.IllegalStateException("Secure lock screen must be enabled to create keys requiring user authentication");
        }
        return secureUserId;
    }

    private static void addSids(java.util.List<android.hardware.security.keymint.KeyParameter> list, android.security.keystore.UserAuthArgs userAuthArgs) {
        if (userAuthArgs.getUserAuthenticationType() == 3) {
            if (userAuthArgs.getBoundToSpecificSecureUserId() != 0) {
                list.add(makeLong(-1610612234, userAuthArgs.getBoundToSpecificSecureUserId()));
                return;
            } else {
                list.add(makeLong(-1610612234, getRootSid()));
                return;
            }
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((userAuthArgs.getUserAuthenticationType() & 2) != 0) {
            long[] authenticatorIds = ((android.hardware.biometrics.BiometricManager) android.app.AppGlobals.getInitialApplication().getSystemService(android.hardware.biometrics.BiometricManager.class)).getAuthenticatorIds();
            if (authenticatorIds.length == 0) {
                throw new java.lang.IllegalStateException("At least one biometric must be enrolled to create keys requiring user authentication for every use");
            }
            if (userAuthArgs.getBoundToSpecificSecureUserId() != 0) {
                arrayList.add(java.lang.Long.valueOf(userAuthArgs.getBoundToSpecificSecureUserId()));
            } else if (userAuthArgs.isInvalidatedByBiometricEnrollment()) {
                for (long j : authenticatorIds) {
                    arrayList.add(java.lang.Long.valueOf(j));
                }
            } else {
                arrayList.add(java.lang.Long.valueOf(getRootSid()));
            }
        } else if ((userAuthArgs.getUserAuthenticationType() & 1) != 0) {
            arrayList.add(java.lang.Long.valueOf(getRootSid()));
        } else {
            throw new java.lang.IllegalStateException("Invalid or no authentication type specified.");
        }
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(makeLong(-1610612234, ((java.lang.Long) arrayList.get(i)).longValue()));
        }
    }

    static void addUserAuthArgs(java.util.List<android.hardware.security.keymint.KeyParameter> list, android.security.keystore.UserAuthArgs userAuthArgs) {
        if (userAuthArgs.isUserConfirmationRequired()) {
            list.add(makeBool(1879048700));
        }
        if (userAuthArgs.isUserPresenceRequired()) {
            list.add(makeBool(1879048699));
        }
        if (userAuthArgs.isUnlockedDeviceRequired()) {
            list.add(makeBool(1879048701));
        }
        if (!userAuthArgs.isUserAuthenticationRequired()) {
            list.add(makeBool(1879048695));
            return;
        }
        addSids(list, userAuthArgs);
        list.add(makeEnum(268435960, userAuthArgs.getUserAuthenticationType()));
        if (userAuthArgs.getUserAuthenticationValidityDurationSeconds() != 0) {
            list.add(makeInt(805306873, userAuthArgs.getUserAuthenticationValidityDurationSeconds()));
        }
        if (userAuthArgs.isUserAuthenticationValidWhileOnBody()) {
            if (userAuthArgs.getUserAuthenticationValidityDurationSeconds() == 0) {
                throw new java.security.ProviderException("Key validity extension while device is on-body is not supported for keys requiring fingerprint authentication");
            }
            list.add(makeBool(1879048698));
        }
    }
}
