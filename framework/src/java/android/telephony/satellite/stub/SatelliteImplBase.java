package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public class SatelliteImplBase extends android.telephony.satellite.stub.SatelliteService {
    private static final java.lang.String TAG = "SatelliteImplBase";
    private final android.os.IBinder mBinder = new android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1();
    protected final java.util.concurrent.Executor mExecutor;

    public SatelliteImplBase(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public final android.os.IBinder getBinder() {
        return this.mBinder;
    }

    /* renamed from: android.telephony.satellite.stub.SatelliteImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.satellite.stub.ISatellite.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteListener(final android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$setSatelliteListener$0(iSatelliteListener);
                }
            }, "setSatelliteListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteListener$0(android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) {
            android.telephony.satellite.stub.SatelliteImplBase.this.setSatelliteListener(iSatelliteListener);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteListeningEnabled(final boolean z, final int i, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteListeningEnabled$1(z, i, iIntegerConsumer);
                }
            }, "requestSatelliteListeningEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteListeningEnabled$1(boolean z, int i, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestSatelliteListeningEnabled(z, i, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void enableCellularModemWhileSatelliteModeIsOn(final boolean z, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$enableCellularModemWhileSatelliteModeIsOn$2(z, iIntegerConsumer);
                }
            }, "enableCellularModemWhileSatelliteModeIsOn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enableCellularModemWhileSatelliteModeIsOn$2(boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.enableCellularModemWhileSatelliteModeIsOn(z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteEnabled(final boolean z, final boolean z2, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteEnabled$3(z, z2, iIntegerConsumer);
                }
            }, "requestSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteEnabled$3(boolean z, boolean z2, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestSatelliteEnabled(z, z2, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabled(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteEnabled$4(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabled$4(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestIsSatelliteEnabled(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteSupported(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteSupported$5(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteSupported");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteSupported$5(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestIsSatelliteSupported(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteCapabilities(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteCapabilities$6(iIntegerConsumer, iSatelliteCapabilitiesConsumer);
                }
            }, "requestSatelliteCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteCapabilities$6(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestSatelliteCapabilities(iIntegerConsumer, iSatelliteCapabilitiesConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingSatellitePointingInfo(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$startSendingSatellitePointingInfo$7(iIntegerConsumer);
                }
            }, "startSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingSatellitePointingInfo$7(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.startSendingSatellitePointingInfo(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingSatellitePointingInfo(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$stopSendingSatellitePointingInfo$8(iIntegerConsumer);
                }
            }, "stopSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingSatellitePointingInfo$8(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.stopSendingSatellitePointingInfo(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void provisionSatelliteService(final java.lang.String str, final byte[] bArr, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$provisionSatelliteService$9(str, bArr, iIntegerConsumer);
                }
            }, "provisionSatelliteService");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$provisionSatelliteService$9(java.lang.String str, byte[] bArr, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.provisionSatelliteService(str, bArr, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void deprovisionSatelliteService(final java.lang.String str, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$deprovisionSatelliteService$10(str, iIntegerConsumer);
                }
            }, "deprovisionSatelliteService");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deprovisionSatelliteService$10(java.lang.String str, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.deprovisionSatelliteService(str, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteProvisioned(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteProvisioned$11(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteProvisioned");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteProvisioned$11(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestIsSatelliteProvisioned(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void pollPendingSatelliteDatagrams(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$pollPendingSatelliteDatagrams$12(iIntegerConsumer);
                }
            }, "pollPendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$pollPendingSatelliteDatagrams$12(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.pollPendingSatelliteDatagrams(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void sendSatelliteDatagram(final android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, final boolean z, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$sendSatelliteDatagram$13(satelliteDatagram, z, iIntegerConsumer);
                }
            }, "sendDatagram");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendSatelliteDatagram$13(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.sendSatelliteDatagram(satelliteDatagram, z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteModemState(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteModemState$14(iIntegerConsumer, iIntegerConsumer2);
                }
            }, "requestSatelliteModemState");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteModemState$14(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestSatelliteModemState(iIntegerConsumer, iIntegerConsumer2);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteCommunicationAllowedForCurrentLocation(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteCommunicationAllowedForCurrentLocation$15(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsCommunicationAllowedForCurrentLocation");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteCommunicationAllowedForCurrentLocation$15(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestIsSatelliteCommunicationAllowedForCurrentLocation(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestTimeForNextSatelliteVisibility(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestTimeForNextSatelliteVisibility$16(iIntegerConsumer, iIntegerConsumer2);
                }
            }, "requestTimeForNextSatelliteVisibility");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTimeForNextSatelliteVisibility$16(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestTimeForNextSatelliteVisibility(iIntegerConsumer, iIntegerConsumer2);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatellitePlmn(final int i, final java.util.List<java.lang.String> list, final java.util.List<java.lang.String> list2, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$setSatellitePlmn$17(i, list, list2, iIntegerConsumer);
                }
            }, "setSatellitePlmn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatellitePlmn$17(int i, java.util.List list, java.util.List list2, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.setSatellitePlmn(i, list, list2, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteEnabledForCarrier(final int i, final boolean z, final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$setSatelliteEnabledForCarrier$18(i, z, iIntegerConsumer);
                }
            }, "setSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteEnabledForCarrier$18(int i, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.setSatelliteEnabledForCarrier(i, z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabledForCarrier(final int i, final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteEnabledForCarrier$19(i, iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabledForCarrier$19(int i, android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestIsSatelliteEnabledForCarrier(i, iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSignalStrength(final android.telephony.IIntegerConsumer iIntegerConsumer, final android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$requestSignalStrength$20(iIntegerConsumer, iNtnSignalStrengthConsumer);
                }
            }, "requestSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSignalStrength$20(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.requestSignalStrength(iIntegerConsumer, iNtnSignalStrengthConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingNtnSignalStrength(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$startSendingNtnSignalStrength$21(iIntegerConsumer);
                }
            }, "startSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingNtnSignalStrength$21(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.startSendingNtnSignalStrength(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingNtnSignalStrength(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$stopSendingNtnSignalStrength$22(iIntegerConsumer);
                }
            }, "stopSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingNtnSignalStrength$22(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.stopSendingNtnSignalStrength(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void abortSendingSatelliteDatagrams(final android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.satellite.stub.SatelliteImplBase.AnonymousClass1.this.lambda$abortSendingSatelliteDatagrams$23(iIntegerConsumer);
                }
            }, "abortSendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$abortSendingSatelliteDatagrams$23(android.telephony.IIntegerConsumer iIntegerConsumer) {
            android.telephony.satellite.stub.SatelliteImplBase.this.abortSendingSatelliteDatagrams(iIntegerConsumer);
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.satellite.stub.SatelliteImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.satellite.stub.SatelliteImplBase.TAG, "SatelliteImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }
    }

    public void setSatelliteListener(android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) {
    }

    public void requestSatelliteListeningEnabled(boolean z, int i, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void enableCellularModemWhileSatelliteModeIsOn(boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void requestSatelliteEnabled(boolean z, boolean z2, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void requestIsSatelliteEnabled(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
    }

    public void requestIsSatelliteSupported(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
    }

    public void requestSatelliteCapabilities(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) {
    }

    public void startSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void stopSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void provisionSatelliteService(java.lang.String str, byte[] bArr, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void deprovisionSatelliteService(java.lang.String str, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void requestIsSatelliteProvisioned(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
    }

    public void pollPendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void sendSatelliteDatagram(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void requestSatelliteModemState(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) {
    }

    public void requestIsSatelliteCommunicationAllowedForCurrentLocation(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
    }

    public void requestTimeForNextSatelliteVisibility(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) {
    }

    public void setSatellitePlmn(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void setSatelliteEnabledForCarrier(int i, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void requestIsSatelliteEnabledForCarrier(int i, android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) {
    }

    public void requestSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) {
    }

    public void startSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void stopSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }

    public void abortSendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) {
    }
}
