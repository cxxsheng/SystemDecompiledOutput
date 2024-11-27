package com.android.server.companion.datatransfer.contextsync;

/* loaded from: classes.dex */
public class CallMetadataSyncInCallService extends android.telecom.InCallService {
    private static final java.lang.String TAG = "CallMetadataIcs";
    private com.android.server.companion.CompanionDeviceManagerServiceInternal mCdmsi;

    @com.android.internal.annotations.VisibleForTesting
    int mNumberOfActiveSyncAssociations;

    @com.android.internal.annotations.VisibleForTesting
    final java.util.Map<android.telecom.Call, com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> mCurrentCalls = new java.util.HashMap();
    final android.telecom.Call.Callback mTelecomCallback = new android.telecom.Call.Callback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.1
        @Override // android.telecom.Call.Callback
        public void onDetailsChanged(android.telecom.Call call, android.telecom.Call.Details details) {
            if (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations > 0) {
                com.android.server.companion.datatransfer.contextsync.CrossDeviceCall crossDeviceCall = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mCurrentCalls.get(call);
                if (crossDeviceCall != null) {
                    crossDeviceCall.updateCallDetails(details);
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.sync(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.getUserId());
                } else {
                    android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Could not update details for nonexistent call");
                }
            }
        }
    };
    final com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback mCrossDeviceSyncControllerCallback = new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.2
        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        void processContextSyncMessage(int i, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData) {
            java.util.Iterator<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallControlRequest> it = callMetadataSyncData.getCallControlRequests().iterator();
            while (it.hasNext()) {
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallControlRequest next = it.next();
                processCallControlAction(next.getId(), next.getControl());
                it.remove();
            }
        }

        private void processCallControlAction(java.lang.String str, int i) {
            com.android.server.companion.datatransfer.contextsync.CrossDeviceCall callForId = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.getCallForId(str, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mCurrentCalls.values());
            switch (i) {
                case 1:
                    if (callForId == null) {
                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Failed to process accept action; no matching call");
                        break;
                    } else {
                        callForId.doAccept();
                        break;
                    }
                case 2:
                    if (callForId == null) {
                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Failed to process reject action; no matching call");
                        break;
                    } else {
                        callForId.doReject();
                        break;
                    }
                case 3:
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.doSilence();
                    break;
                case 4:
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.doMute();
                    break;
                case 5:
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.doUnmute();
                    break;
                case 6:
                    if (callForId == null) {
                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Failed to process end action; no matching call");
                        break;
                    } else {
                        callForId.doEnd();
                        break;
                    }
                case 7:
                    if (callForId == null) {
                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Failed to process hold action; no matching call");
                        break;
                    } else {
                        callForId.doPutOnHold();
                        break;
                    }
                case 8:
                    if (callForId == null) {
                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.TAG, "Failed to process unhold action; no matching call");
                        break;
                    } else {
                        callForId.doTakeOffHold();
                        break;
                    }
            }
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        void requestCrossDeviceSync(android.companion.AssociationInfo associationInfo) {
            if (associationInfo.getUserId() == com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.getUserId()) {
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.sync(associationInfo);
            }
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        void updateNumberOfActiveSyncAssociations(int i, boolean z) {
            if (i == com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.getUserId()) {
                boolean z2 = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations > 0;
                if (z) {
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations++;
                } else {
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations--;
                }
                if (!z2 && com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations > 0) {
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.initializeCalls();
                } else if (z2 && com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mNumberOfActiveSyncAssociations <= 0) {
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.mCurrentCalls.clear();
                }
            }
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
            this.mCdmsi = (com.android.server.companion.CompanionDeviceManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.companion.CompanionDeviceManagerServiceInternal.class);
            this.mCdmsi.registerCallMetadataSyncCallback(this.mCrossDeviceSyncControllerCallback, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeCalls() {
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM) && this.mNumberOfActiveSyncAssociations > 0) {
            this.mCurrentCalls.putAll((java.util.Map) getCalls().stream().collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.telecom.Call lambda$initializeCalls$0;
                    lambda$initializeCalls$0 = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.lambda$initializeCalls$0((android.telecom.Call) obj);
                    return lambda$initializeCalls$0;
                }
            }, new java.util.function.Function() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceCall lambda$initializeCalls$1;
                    lambda$initializeCalls$1 = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.lambda$initializeCalls$1((android.telecom.Call) obj);
                    return lambda$initializeCalls$1;
                }
            })));
            this.mCurrentCalls.keySet().forEach(new java.util.function.Consumer() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.this.lambda$initializeCalls$2((android.telecom.Call) obj);
                }
            });
            sync(getUserId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.telecom.Call lambda$initializeCalls$0(android.telecom.Call call) {
        return call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.companion.datatransfer.contextsync.CrossDeviceCall lambda$initializeCalls$1(android.telecom.Call call) {
        return new com.android.server.companion.datatransfer.contextsync.CrossDeviceCall(this, call, getCallAudioState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeCalls$2(android.telecom.Call call) {
        call.registerCallback(this.mTelecomCallback, getMainThreadHandler());
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.companion.datatransfer.contextsync.CrossDeviceCall getCallForId(java.lang.String str, java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection) {
        if (str == null) {
            return null;
        }
        for (com.android.server.companion.datatransfer.contextsync.CrossDeviceCall crossDeviceCall : collection) {
            if (str.equals(crossDeviceCall.getId())) {
                return crossDeviceCall;
            }
        }
        return null;
    }

    @Override // android.telecom.InCallService
    public void onCallAdded(android.telecom.Call call) {
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM) && this.mNumberOfActiveSyncAssociations > 0) {
            this.mCurrentCalls.put(call, new com.android.server.companion.datatransfer.contextsync.CrossDeviceCall(this, call, getCallAudioState()));
            call.registerCallback(this.mTelecomCallback);
            sync(getUserId());
        }
    }

    @Override // android.telecom.InCallService
    public void onCallRemoved(android.telecom.Call call) {
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM) && this.mNumberOfActiveSyncAssociations > 0) {
            this.mCurrentCalls.remove(call);
            call.unregisterCallback(this.mTelecomCallback);
            this.mCdmsi.removeSelfOwnedCallId(call.getDetails().getExtras().getString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID));
            sync(getUserId());
        }
    }

    @Override // android.telecom.InCallService
    public void onMuteStateChanged(boolean z) {
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM) && this.mNumberOfActiveSyncAssociations > 0) {
            this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(getUserId(), com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallControlMessage(null, z ? 4 : 5));
        }
    }

    @Override // android.telecom.InCallService
    public void onSilenceRinger() {
        if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM) && this.mNumberOfActiveSyncAssociations > 0) {
            this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(getUserId(), com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallControlMessage(null, 3));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doMute() {
        setMuted(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnmute() {
        setMuted(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSilence() {
        android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) getSystemService(android.telecom.TelecomManager.class);
        if (telecomManager != null) {
            telecomManager.silenceRinger();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sync(int i) {
        this.mCdmsi.crossDeviceSync(i, this.mCurrentCalls.values());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sync(android.companion.AssociationInfo associationInfo) {
        this.mCdmsi.crossDeviceSync(associationInfo, this.mCurrentCalls.values());
    }
}
