package android.security;

/* loaded from: classes3.dex */
public class KeyStoreSecurityLevel {
    private static final java.lang.String TAG = "KeyStoreSecurityLevel";
    private final android.system.keystore2.IKeystoreSecurityLevel mSecurityLevel;

    public KeyStoreSecurityLevel(android.system.keystore2.IKeystoreSecurityLevel iKeystoreSecurityLevel) {
        android.os.Binder.allowBlocking(iKeystoreSecurityLevel.asBinder());
        this.mSecurityLevel = iKeystoreSecurityLevel;
    }

    private <R> R handleExceptions(android.security.CheckedRemoteRequest<R> checkedRemoteRequest) throws android.security.KeyStoreException {
        try {
            return checkedRemoteRequest.execute();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not connect to Keystore.", e);
            throw new android.security.KeyStoreException(4, "", e.getMessage());
        } catch (android.os.ServiceSpecificException e2) {
            throw android.security.KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.processExcHandler(ExcHandlersRegionMaker.java:144)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:77)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public android.security.KeyStoreOperation createOperation(android.system.keystore2.KeyDescriptor r6, java.util.Collection<android.hardware.security.keymint.KeyParameter> r7) throws android.security.KeyStoreException {
        /*
            r5 = this;
            android.os.StrictMode.noteDiskWrite()
        L3:
            android.system.keystore2.IKeystoreSecurityLevel r0 = r5.mSecurityLevel     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            int r1 = r7.size()     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            android.hardware.security.keymint.KeyParameter[] r1 = new android.hardware.security.keymint.KeyParameter[r1]     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            java.lang.Object[] r1 = r7.toArray(r1)     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            android.hardware.security.keymint.KeyParameter[] r1 = (android.hardware.security.keymint.KeyParameter[]) r1     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            r2 = 0
            android.system.keystore2.CreateOperationResponse r0 = r0.createOperation(r6, r1, r2)     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            android.system.keystore2.OperationChallenge r1 = r0.operationChallenge     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            r2 = 0
            if (r1 == 0) goto L25
            android.system.keystore2.OperationChallenge r1 = r0.operationChallenge     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            long r3 = r1.challenge     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            java.lang.Long r1 = java.lang.Long.valueOf(r3)     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            goto L26
        L25:
            r1 = r2
        L26:
            android.system.keystore2.KeyParameters r3 = r0.parameters     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            if (r3 == 0) goto L2f
            android.system.keystore2.KeyParameters r2 = r0.parameters     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            android.hardware.security.keymint.KeyParameter[] r2 = r2.keyParameter     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
        L2f:
            android.security.KeyStoreOperation r3 = new android.security.KeyStoreOperation     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            android.system.keystore2.IKeystoreOperation r0 = r0.iOperation     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            r3.<init>(r0, r1, r2)     // Catch: android.os.RemoteException -> L37 android.os.ServiceSpecificException -> L45
            return r3
        L37:
            r6 = move-exception
            java.lang.String r7 = "KeyStoreSecurityLevel"
            java.lang.String r0 = "Cannot connect to keystore"
            android.util.Log.w(r7, r0, r6)
            android.security.keystore.KeyStoreConnectException r6 = new android.security.keystore.KeyStoreConnectException
            r6.<init>()
            throw r6
        L45:
            r0 = move-exception
            int r1 = r0.errorCode
            switch(r1) {
                case 18: goto L56;
                default: goto L4b;
            }
        L4b:
            int r6 = r0.errorCode
            java.lang.String r7 = r0.getMessage()
            android.security.KeyStoreException r6 = android.security.KeyStore2.getKeyStoreException(r6, r7)
            throw r6
        L56:
            double r0 = java.lang.Math.random()
            r2 = 4635329916471083008(0x4054000000000000, double:80.0)
            double r0 = r0 * r2
            r2 = 4626322717216342016(0x4034000000000000, double:20.0)
            double r0 = r0 + r2
            long r0 = (long) r0
            r2 = 169897160(0xa206cc8, double:8.394035E-316)
            boolean r2 = android.app.compat.CompatChanges.isChangeEnabled(r2)
            if (r2 != 0) goto L6f
            interruptedPreservingSleep(r0)
            goto L3
        L6f:
            android.security.keystore.BackendBusyException r6 = new android.security.keystore.BackendBusyException
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.KeyStoreSecurityLevel.createOperation(android.system.keystore2.KeyDescriptor, java.util.Collection):android.security.KeyStoreOperation");
    }

    public android.system.keystore2.KeyMetadata generateKey(final android.system.keystore2.KeyDescriptor keyDescriptor, final android.system.keystore2.KeyDescriptor keyDescriptor2, final java.util.Collection<android.hardware.security.keymint.KeyParameter> collection, final int i, final byte[] bArr) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        return (android.system.keystore2.KeyMetadata) handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda2
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                android.system.keystore2.KeyMetadata lambda$generateKey$0;
                lambda$generateKey$0 = android.security.KeyStoreSecurityLevel.this.lambda$generateKey$0(keyDescriptor, keyDescriptor2, collection, i, bArr);
                return lambda$generateKey$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.system.keystore2.KeyMetadata lambda$generateKey$0(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, java.util.Collection collection, int i, byte[] bArr) throws android.os.RemoteException {
        return this.mSecurityLevel.generateKey(keyDescriptor, keyDescriptor2, (android.hardware.security.keymint.KeyParameter[]) collection.toArray(new android.hardware.security.keymint.KeyParameter[collection.size()]), i, bArr);
    }

    public android.system.keystore2.KeyMetadata importKey(final android.system.keystore2.KeyDescriptor keyDescriptor, final android.system.keystore2.KeyDescriptor keyDescriptor2, final java.util.Collection<android.hardware.security.keymint.KeyParameter> collection, final int i, final byte[] bArr) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        return (android.system.keystore2.KeyMetadata) handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda0
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                android.system.keystore2.KeyMetadata lambda$importKey$1;
                lambda$importKey$1 = android.security.KeyStoreSecurityLevel.this.lambda$importKey$1(keyDescriptor, keyDescriptor2, collection, i, bArr);
                return lambda$importKey$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.system.keystore2.KeyMetadata lambda$importKey$1(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, java.util.Collection collection, int i, byte[] bArr) throws android.os.RemoteException {
        return this.mSecurityLevel.importKey(keyDescriptor, keyDescriptor2, (android.hardware.security.keymint.KeyParameter[]) collection.toArray(new android.hardware.security.keymint.KeyParameter[collection.size()]), i, bArr);
    }

    public android.system.keystore2.KeyMetadata importWrappedKey(android.system.keystore2.KeyDescriptor keyDescriptor, final android.system.keystore2.KeyDescriptor keyDescriptor2, byte[] bArr, final byte[] bArr2, final java.util.Collection<android.hardware.security.keymint.KeyParameter> collection, final android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        final android.system.keystore2.KeyDescriptor keyDescriptor3 = new android.system.keystore2.KeyDescriptor();
        keyDescriptor3.alias = keyDescriptor.alias;
        keyDescriptor3.nspace = keyDescriptor.nspace;
        keyDescriptor3.blob = bArr;
        keyDescriptor3.domain = keyDescriptor.domain;
        return (android.system.keystore2.KeyMetadata) handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda1
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                android.system.keystore2.KeyMetadata lambda$importWrappedKey$2;
                lambda$importWrappedKey$2 = android.security.KeyStoreSecurityLevel.this.lambda$importWrappedKey$2(keyDescriptor3, keyDescriptor2, bArr2, collection, authenticatorSpecArr);
                return lambda$importWrappedKey$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.system.keystore2.KeyMetadata lambda$importWrappedKey$2(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, byte[] bArr, java.util.Collection collection, android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr) throws android.os.RemoteException {
        return this.mSecurityLevel.importWrappedKey(keyDescriptor, keyDescriptor2, bArr, (android.hardware.security.keymint.KeyParameter[]) collection.toArray(new android.hardware.security.keymint.KeyParameter[collection.size()]), authenticatorSpecArr);
    }

    protected static void interruptedPreservingSleep(long j) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis() + j;
        boolean z = false;
        while (true) {
            try {
                java.lang.Thread.sleep(timeInMillis - calendar.getTimeInMillis());
                break;
            } catch (java.lang.IllegalArgumentException e) {
            } catch (java.lang.InterruptedException e2) {
                z = true;
            }
        }
        if (z) {
            java.lang.Thread.currentThread().interrupt();
        }
    }
}
