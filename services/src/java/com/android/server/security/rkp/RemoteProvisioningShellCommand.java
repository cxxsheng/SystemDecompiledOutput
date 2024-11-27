package com.android.server.security.rkp;

/* loaded from: classes2.dex */
class RemoteProvisioningShellCommand extends android.os.ShellCommand {
    private static final java.time.Duration BIND_TIMEOUT = java.time.Duration.ofSeconds(10);
    static final java.lang.String EEK_ED25519_BASE64 = "goRDoQEnoFgqpAEBAycgBiFYIJm57t1e5FL2hcZMYtw+YatXSH11NymtdoAy0rPLY1jZWEAeIghLpLekyNdOAw7+uK8UTKc7b6XN3Np5xitk/pk5r3bngPpmAIUNB5gqrJFcpyUUSQY0dcqKJ3rZ41pJ6wIDhEOhASegWE6lAQECWCDQrsEVyirPc65rzMvRlh1l6LHd10oaN7lDOpfVmd+YCAM4GCAEIVggvoXnRsSjQlpA2TY6phXQLFh+PdwzAjLS/F4ehyVfcmBYQJvPkOIuS6vRGLEOjl0gJ0uEWP78MpB+cgWDvNeCvvpkeC1UEEvAMb9r6B414vAtzmwvT/L1T6XUg62WovGHWAQ=";
    static final java.lang.String EEK_P256_BASE64 = "goRDoQEmoFhNpQECAyYgASFYIPcUituX9MxT79JkEcTjdR9mH6RxDGzP+glGgHSHVPKtIlggXn9b9uzk9hnM/xM3/Q+hyJPbGAZ2xF3m12p3hsMtr49YQC+XjkL7vgctlUeFR5NAsB/Um0ekxESp8qEHhxDHn8sR9L+f6Dvg5zRMFfx7w34zBfTRNDztAgRgehXgedOK/ySEQ6EBJqBYcaYBAgJYIDVztz+gioCJsSZn6ct8daGvAmH8bmUDkTvTS30UlD5GAzgYIAEhWCDgQc8vDzQPHDMsQbDP1wwwVTXSHmpHE0su0UiWfiScaCJYIB/ORcX7YbqBIfnlBZubOQ52hoZHuB4vRfHOr9o/gGjbWECMs7p+ID4ysGjfYNEdffCsOI5RvP9s4Wc7Snm8Vnizmdh8igfY2rW1f3H02GvfMyc0e2XRKuuGmZirOrSAqr1Q";
    private static final int ERROR = -1;
    private static final int KEY_ID = 452436;
    private static final int SUCCESS = 0;
    private static final java.lang.String USAGE = "usage: cmd remote_provisioning SUBCOMMAND [ARGS]\nhelp\n  Show this message.\ndump\n  Dump service diagnostics.\nlist\n  List the names of the IRemotelyProvisionedComponent instances.\ncsr [--challenge CHALLENGE] NAME\n  Generate and print a base64-encoded CSR from the named\n  IRemotelyProvisionedComponent. A base64-encoded challenge can be provided,\n  or else it defaults to an empty challenge.\ncertify NAME\n  Output the PEM-encoded certificate chain provisioned for the named\n  IRemotelyProvisionedComponent.\n";
    private final int mCallerUid;
    private final android.content.Context mContext;
    private final com.android.server.security.rkp.RemoteProvisioningShellCommand.Injector mInjector;

    RemoteProvisioningShellCommand(android.content.Context context, int i) {
        this(context, i, new com.android.server.security.rkp.RemoteProvisioningShellCommand.Injector());
    }

    RemoteProvisioningShellCommand(android.content.Context context, int i, com.android.server.security.rkp.RemoteProvisioningShellCommand.Injector injector) {
        this.mContext = context;
        this.mCallerUid = i;
        this.mInjector = injector;
    }

    public void onHelp() {
        getOutPrintWriter().print(USAGE);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            switch (str.hashCode()) {
                case 98818:
                    if (str.equals("csr")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 668936792:
                    if (str.equals("certify")) {
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
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
        return handleDefaultCommands(str);
    }

    void dump(java.io.PrintWriter printWriter) {
        try {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            for (java.lang.String str : this.mInjector.getIrpcNames()) {
                indentingPrintWriter.println(str + ":");
                indentingPrintWriter.increaseIndent();
                dumpRpcInstance(indentingPrintWriter, str);
                indentingPrintWriter.decreaseIndent();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace(printWriter);
        }
    }

    private void dumpRpcInstance(java.io.PrintWriter printWriter, java.lang.String str) throws android.os.RemoteException {
        android.hardware.security.keymint.RpcHardwareInfo hardwareInfo = this.mInjector.getIrpcBinder(str).getHardwareInfo();
        printWriter.println("hwVersion=" + hardwareInfo.versionNumber);
        printWriter.println("rpcAuthorName=" + hardwareInfo.rpcAuthorName);
        if (hardwareInfo.versionNumber < 3) {
            printWriter.println("supportedEekCurve=" + hardwareInfo.supportedEekCurve);
        }
        printWriter.println("uniqueId=" + hardwareInfo.uniqueId);
        if (hardwareInfo.versionNumber >= 3) {
            printWriter.println("supportedNumKeysInCsr=" + hardwareInfo.supportedNumKeysInCsr);
        }
    }

    private int list() throws android.os.RemoteException {
        for (java.lang.String str : this.mInjector.getIrpcNames()) {
            getOutPrintWriter().println(str);
        }
        return 0;
    }

    private int csr() throws android.os.RemoteException, co.nstant.in.cbor.CborException {
        byte[] composeCertificateRequestV1;
        boolean z;
        byte[] bArr = new byte[0];
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1891027651:
                        if (nextOption.equals("--challenge")) {
                            z = false;
                            break;
                        }
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        bArr = java.util.Base64.getDecoder().decode(getNextArgRequired());
                    default:
                        getErrPrintWriter().println("error: unknown option " + nextOption);
                        return -1;
                }
            } else {
                android.hardware.security.keymint.IRemotelyProvisionedComponent irpcBinder = this.mInjector.getIrpcBinder(getNextArgRequired());
                android.hardware.security.keymint.RpcHardwareInfo hardwareInfo = irpcBinder.getHardwareInfo();
                android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr = new android.hardware.security.keymint.MacedPublicKey[0];
                switch (hardwareInfo.versionNumber) {
                    case 1:
                    case 2:
                        android.hardware.security.keymint.DeviceInfo deviceInfo = new android.hardware.security.keymint.DeviceInfo();
                        android.hardware.security.keymint.ProtectedData protectedData = new android.hardware.security.keymint.ProtectedData();
                        composeCertificateRequestV1 = composeCertificateRequestV1(deviceInfo, bArr, protectedData, irpcBinder.generateCertificateRequest(false, macedPublicKeyArr, getEekChain(hardwareInfo.supportedEekCurve), bArr, deviceInfo, protectedData));
                        break;
                    case 3:
                        composeCertificateRequestV1 = irpcBinder.generateCertificateRequestV2(macedPublicKeyArr, bArr);
                        break;
                    default:
                        getErrPrintWriter().println("error: unsupported hwVersion: " + hardwareInfo.versionNumber);
                        return -1;
                }
                getOutPrintWriter().println(java.util.Base64.getEncoder().encodeToString(composeCertificateRequestV1));
                return 0;
            }
        }
    }

    private byte[] getEekChain(int i) {
        switch (i) {
            case 1:
                return java.util.Base64.getDecoder().decode(EEK_P256_BASE64);
            case 2:
                return java.util.Base64.getDecoder().decode(EEK_ED25519_BASE64);
            default:
                throw new java.lang.IllegalArgumentException("unsupported EEK curve: " + i);
        }
    }

    private byte[] composeCertificateRequestV1(android.hardware.security.keymint.DeviceInfo deviceInfo, byte[] bArr, android.hardware.security.keymint.ProtectedData protectedData, byte[] bArr2) throws co.nstant.in.cbor.CborException {
        co.nstant.in.cbor.model.Array add = new co.nstant.in.cbor.model.Array().add(decode(deviceInfo.deviceInfo)).add(new co.nstant.in.cbor.model.Map());
        return encode(new co.nstant.in.cbor.model.Array().add(add).add(new co.nstant.in.cbor.model.ByteString(bArr)).add(decode(protectedData.protectedData)).add(new co.nstant.in.cbor.model.Array().add(new co.nstant.in.cbor.model.ByteString(encode(new co.nstant.in.cbor.model.Map().put(new co.nstant.in.cbor.model.UnsignedInteger(1L), new co.nstant.in.cbor.model.UnsignedInteger(5L))))).add(new co.nstant.in.cbor.model.Map()).add(co.nstant.in.cbor.model.SimpleValue.NULL).add(new co.nstant.in.cbor.model.ByteString(bArr2))));
    }

    private byte[] encode(co.nstant.in.cbor.model.DataItem dataItem) throws co.nstant.in.cbor.CborException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        new co.nstant.in.cbor.CborEncoder(byteArrayOutputStream).encode(dataItem);
        return byteArrayOutputStream.toByteArray();
    }

    private co.nstant.in.cbor.model.DataItem decode(byte[] bArr) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.CborDecoder(new java.io.ByteArrayInputStream(bArr)).decodeNext();
    }

    private int certify() throws java.lang.Exception {
        java.lang.String nextArgRequired = getNextArgRequired();
        java.util.concurrent.Executor mainExecutor = this.mContext.getMainExecutor();
        android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
        com.android.server.security.rkp.RemoteProvisioningShellCommand.OutcomeFuture outcomeFuture = new com.android.server.security.rkp.RemoteProvisioningShellCommand.OutcomeFuture();
        this.mInjector.getRegistrationProxy(this.mContext, this.mCallerUid, nextArgRequired, mainExecutor).getKeyAsync(KEY_ID, cancellationSignal, mainExecutor, outcomeFuture);
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(((android.security.rkp.service.RemotelyProvisionedKey) outcomeFuture.join()).getEncodedCertChain());
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.util.Iterator<? extends java.security.cert.Certificate> it = java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(byteArrayInputStream).iterator();
        while (it.hasNext()) {
            java.lang.String encodeToString = java.util.Base64.getEncoder().encodeToString(it.next().getEncoded());
            outPrintWriter.println("-----BEGIN CERTIFICATE-----");
            outPrintWriter.println(encodeToString.replaceAll("(.{64})", "$1\n").stripTrailing());
            outPrintWriter.println("-----END CERTIFICATE-----");
        }
        return 0;
    }

    private static class OutcomeFuture<T> implements android.os.OutcomeReceiver<T, java.lang.Exception> {
        private java.util.concurrent.CompletableFuture<T> mFuture;

        private OutcomeFuture() {
            this.mFuture = new java.util.concurrent.CompletableFuture<>();
        }

        @Override // android.os.OutcomeReceiver
        public void onResult(T t) {
            this.mFuture.complete(t);
        }

        @Override // android.os.OutcomeReceiver
        public void onError(java.lang.Exception exc) {
            this.mFuture.completeExceptionally(exc);
        }

        public T join() {
            return this.mFuture.join();
        }
    }

    static class Injector {
        Injector() {
        }

        java.lang.String[] getIrpcNames() {
            return android.os.ServiceManager.getDeclaredInstances(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
        }

        android.hardware.security.keymint.IRemotelyProvisionedComponent getIrpcBinder(java.lang.String str) {
            java.lang.String str2 = android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
            android.hardware.security.keymint.IRemotelyProvisionedComponent asInterface = android.hardware.security.keymint.IRemotelyProvisionedComponent.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(str2));
            if (asInterface == null) {
                throw new java.lang.IllegalArgumentException("failed to find " + str2);
            }
            return asInterface;
        }

        android.security.rkp.service.RegistrationProxy getRegistrationProxy(android.content.Context context, int i, java.lang.String str, java.util.concurrent.Executor executor) {
            java.lang.String str2 = android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
            com.android.server.security.rkp.RemoteProvisioningShellCommand.OutcomeFuture outcomeFuture = new com.android.server.security.rkp.RemoteProvisioningShellCommand.OutcomeFuture();
            android.security.rkp.service.RegistrationProxy.createAsync(context, i, str2, com.android.server.security.rkp.RemoteProvisioningShellCommand.BIND_TIMEOUT, executor, outcomeFuture);
            return (android.security.rkp.service.RegistrationProxy) outcomeFuture.join();
        }
    }
}
