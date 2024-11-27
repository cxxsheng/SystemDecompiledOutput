package com.android.server.companion.datatransfer.contextsync;

/* loaded from: classes.dex */
public class CrossDeviceSyncController {
    private static final int CURRENT_VERSION = 1;
    static final java.lang.String EXTRA_ASSOCIATION_ID = "com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID";
    static final java.lang.String EXTRA_CALL = "com.android.server.companion.datatransfer.contextsync.extra.CALL";
    static final java.lang.String EXTRA_CALL_FACILITATOR_ID = "com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID";
    public static final java.lang.String EXTRA_CALL_ID = "com.android.companion.datatransfer.contextsync.extra.CALL_ID";
    static final java.lang.String EXTRA_FACILITATOR_ICON = "com.android.companion.datatransfer.contextsync.extra.FACILITATOR_ICON";
    static final java.lang.String EXTRA_IS_REMOTE_ORIGIN = "com.android.companion.datatransfer.contextsync.extra.IS_REMOTE_ORIGIN";
    public static final java.lang.String FACILITATOR_ID_SYSTEM = "system";
    private static final java.lang.String TAG = "CrossDeviceSyncController";
    private static final int VERSION_1 = 1;
    private final com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.CallManager mCallManager;
    private final com.android.server.companion.transport.CompanionTransportManager mCompanionTransportManager;
    private java.lang.ref.WeakReference<com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback> mConnectionServiceCallbackRef;
    private final android.content.Context mContext;
    private java.lang.ref.WeakReference<com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback> mInCallServiceCallbackRef;
    private final com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountManager mPhoneAccountManager;
    private final java.util.List<android.companion.AssociationInfo> mConnectedAssociations = new java.util.ArrayList();
    private final java.util.Set<java.lang.Integer> mBlocklist = new java.util.HashSet();
    private final java.util.List<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator> mCallFacilitators = new java.util.ArrayList();

    public CrossDeviceSyncController(android.content.Context context, com.android.server.companion.transport.CompanionTransportManager companionTransportManager) {
        this.mContext = context;
        this.mCompanionTransportManager = companionTransportManager;
        this.mCompanionTransportManager.addListener(new android.companion.IOnTransportsChangedListener.Stub() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.1
            public void onTransportsChanged(java.util.List<android.companion.AssociationInfo> list) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                        return;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    java.util.ArrayList<android.companion.AssociationInfo> arrayList = new java.util.ArrayList(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectedAssociations);
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectedAssociations.clear();
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectedAssociations.addAll(list);
                    java.util.Iterator<android.companion.AssociationInfo> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        android.companion.AssociationInfo next = it.next();
                        if (!arrayList.contains(next)) {
                            if (!com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.isAssociationBlocked(next)) {
                                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback) com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                if (crossDeviceSyncControllerCallback != null) {
                                    crossDeviceSyncControllerCallback.updateNumberOfActiveSyncAssociations(next.getUserId(), true);
                                    crossDeviceSyncControllerCallback.requestCrossDeviceSync(next);
                                } else {
                                    android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "No callback to report new transport");
                                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.syncMessageToDevice(next.getId(), com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.createFacilitatorMessage());
                                }
                            } else {
                                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mBlocklist.add(java.lang.Integer.valueOf(next.getId()));
                                android.util.Slog.i(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "New association was blocked from context syncing");
                            }
                        }
                    }
                    for (android.companion.AssociationInfo associationInfo : arrayList) {
                        if (!list.contains(associationInfo)) {
                            com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mBlocklist.remove(java.lang.Integer.valueOf(associationInfo.getId()));
                            if (!com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.isAssociationBlockedLocal(associationInfo.getId())) {
                                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback2 = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback) com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                if (crossDeviceSyncControllerCallback2 != null) {
                                    crossDeviceSyncControllerCallback2.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), false);
                                } else {
                                    android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "No callback to report removed transport");
                                }
                            }
                            com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.clearInProgressCalls(associationInfo.getId());
                        } else {
                            boolean isAssociationBlocked = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.isAssociationBlocked(associationInfo);
                            if (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.isAssociationBlockedLocal(associationInfo.getId()) != isAssociationBlocked) {
                                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback3 = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback) com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                if (!isAssociationBlocked) {
                                    android.util.Slog.i(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "Unblocking existing association for context sync");
                                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mBlocklist.remove(java.lang.Integer.valueOf(associationInfo.getId()));
                                    if (crossDeviceSyncControllerCallback3 != null) {
                                        crossDeviceSyncControllerCallback3.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), true);
                                        crossDeviceSyncControllerCallback3.requestCrossDeviceSync(associationInfo);
                                    } else {
                                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "No callback to report changed transport");
                                        com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.syncMessageToDevice(associationInfo.getId(), com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.createFacilitatorMessage());
                                    }
                                } else {
                                    android.util.Slog.i(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "Blocking existing association for context sync");
                                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mBlocklist.add(java.lang.Integer.valueOf(associationInfo.getId()));
                                    if (crossDeviceSyncControllerCallback3 != null) {
                                        crossDeviceSyncControllerCallback3.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), false);
                                    } else {
                                        android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "No callback to report changed transport");
                                    }
                                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.syncMessageToDevice(associationInfo.getId(), com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createEmptyMessage());
                                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.clearInProgressCalls(associationInfo.getId());
                                }
                            }
                        }
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        });
        this.mCompanionTransportManager.addListener(1667729539, new android.companion.IOnMessageReceivedListener.Stub() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.2
            public void onMessageReceived(int i, byte[] bArr) {
                if (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.isAssociationBlockedLocal(i)) {
                    return;
                }
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData processTelecomDataFromSync = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.processTelecomDataFromSync(bArr);
                boolean z = (processTelecomDataFromSync.getCallControlRequests().size() == 0 && processTelecomDataFromSync.getCallCreateRequests().size() == 0) ? false : true;
                if (!z) {
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mPhoneAccountManager.updateFacilitators(i, processTelecomDataFromSync);
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mCallManager.updateCalls(i, processTelecomDataFromSync);
                } else {
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.processCallCreateRequests(processTelecomDataFromSync);
                }
                if (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef == null && com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectionServiceCallbackRef == null) {
                    android.util.Slog.w(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.TAG, "No callback to process context sync message");
                    return;
                }
                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback) com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                if (crossDeviceSyncControllerCallback != null) {
                    if (z) {
                        crossDeviceSyncControllerCallback.processContextSyncMessage(i, processTelecomDataFromSync);
                    }
                } else {
                    com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mInCallServiceCallbackRef = null;
                }
                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback2 = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectionServiceCallbackRef != null ? (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback) com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectionServiceCallbackRef.get() : null;
                if (crossDeviceSyncControllerCallback2 != null) {
                    if (!z) {
                        crossDeviceSyncControllerCallback2.processContextSyncMessage(i, processTelecomDataFromSync);
                        return;
                    }
                    return;
                }
                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.this.mConnectionServiceCallbackRef = null;
            }
        });
        this.mPhoneAccountManager = new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountManager(this.mContext);
        this.mCallManager = new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.CallManager(this.mContext, this.mPhoneAccountManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearInProgressCalls(int i) {
        java.util.Set<java.lang.String> clearCallIdsForAssociationId = this.mCallManager.clearCallIdsForAssociationId(i);
        com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = this.mConnectionServiceCallbackRef != null ? this.mConnectionServiceCallbackRef.get() : null;
        if (crossDeviceSyncControllerCallback != null) {
            crossDeviceSyncControllerCallback.cleanUpCallIds(clearCallIdsForAssociationId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAssociationBlocked(android.companion.AssociationInfo associationInfo) {
        return (associationInfo.getSystemDataSyncFlags() & 1) != 1;
    }

    public void onBootCompleted() {
        android.telecom.PhoneAccountHandle defaultOutgoingPhoneAccount;
        android.telecom.PhoneAccount phoneAccount;
        if (!com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
            return;
        }
        this.mPhoneAccountManager.onBootCompleted();
        android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class);
        if (telecomManager != null && telecomManager.getCallCapablePhoneAccounts().size() != 0 && (defaultOutgoingPhoneAccount = telecomManager.getDefaultOutgoingPhoneAccount("tel")) != null && (phoneAccount = telecomManager.getPhoneAccount(defaultOutgoingPhoneAccount)) != null) {
            this.mCallFacilitators.add(new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator(phoneAccount.getLabel().toString(), "system", "system"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processCallCreateRequests(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData) {
        java.util.Iterator<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallCreateRequest> it = callMetadataSyncData.getCallCreateRequests().iterator();
        while (it.hasNext()) {
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallCreateRequest next = it.next();
            if ("system".equals(next.getFacilitator().getIdentifier())) {
                if (next.getAddress() != null && next.getAddress().startsWith("tel")) {
                    this.mCallManager.addSelfOwnedCallId(next.getId());
                    android.net.Uri fromParts = android.net.Uri.fromParts("tel", next.getAddress().replaceAll("\\D+", ""), null);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putString(EXTRA_CALL_ID, next.getId());
                    android.os.Bundle bundle2 = new android.os.Bundle();
                    bundle2.putParcelable("android.telecom.extra.OUTGOING_CALL_EXTRAS", bundle);
                    ((android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class)).placeCall(fromParts, bundle2);
                }
            } else {
                android.util.Slog.e(TAG, "Non-system facilitated calls are not supported yet");
            }
            it.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAssociationBlockedLocal(int i) {
        return this.mBlocklist.contains(java.lang.Integer.valueOf(i));
    }

    public void registerCallMetadataSyncCallback(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback, int i) {
        if (i == 2) {
            this.mInCallServiceCallbackRef = new java.lang.ref.WeakReference<>(crossDeviceSyncControllerCallback);
            for (android.companion.AssociationInfo associationInfo : this.mConnectedAssociations) {
                if (!isAssociationBlocked(associationInfo)) {
                    this.mBlocklist.remove(java.lang.Integer.valueOf(associationInfo.getId()));
                    crossDeviceSyncControllerCallback.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), true);
                    crossDeviceSyncControllerCallback.requestCrossDeviceSync(associationInfo);
                } else {
                    this.mBlocklist.add(java.lang.Integer.valueOf(associationInfo.getId()));
                }
            }
            return;
        }
        if (i == 1) {
            this.mConnectionServiceCallbackRef = new java.lang.ref.WeakReference<>(crossDeviceSyncControllerCallback);
            return;
        }
        android.util.Slog.e(TAG, "Cannot register callback of unknown type: " + i);
    }

    private boolean isAdminBlocked(int i) {
        return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getBluetoothContactSharingDisabled(android.os.UserHandle.of(i));
    }

    public void syncToAllDevicesForUserId(int i, java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.companion.AssociationInfo associationInfo : this.mConnectedAssociations) {
            if (associationInfo.getUserId() == i && !isAssociationBlocked(associationInfo)) {
                hashSet.add(java.lang.Integer.valueOf(associationInfo.getId()));
            }
        }
        if (hashSet.isEmpty()) {
            android.util.Slog.w(TAG, "No eligible devices to sync to");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, createCallUpdateMessage(collection, i), hashSet.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    public void syncToSingleDevice(android.companion.AssociationInfo associationInfo, java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection) {
        if (isAssociationBlocked(associationInfo)) {
            android.util.Slog.e(TAG, "Cannot sync to requested device; connection is blocked");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, createCallUpdateMessage(collection, associationInfo.getUserId()), new int[]{associationInfo.getId()});
        }
    }

    public void syncMessageToDevice(int i, byte[] bArr) {
        if (isAssociationBlockedLocal(i)) {
            android.util.Slog.e(TAG, "Cannot sync to requested device; connection is blocked");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, bArr, new int[]{i});
        }
    }

    public void syncMessageToAllDevicesForUserId(int i, byte[] bArr) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.companion.AssociationInfo associationInfo : this.mConnectedAssociations) {
            if (associationInfo.getUserId() == i && !isAssociationBlocked(associationInfo)) {
                hashSet.add(java.lang.Integer.valueOf(associationInfo.getId()));
            }
        }
        if (hashSet.isEmpty()) {
            android.util.Slog.w(TAG, "No eligible devices to sync to");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, bArr, hashSet.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    public void addSelfOwnedCallId(java.lang.String str) {
        this.mCallManager.addSelfOwnedCallId(str);
    }

    public void removeSelfOwnedCallId(java.lang.String str) {
        if (str != null) {
            this.mCallManager.removeSelfOwnedCallId(str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData processTelecomDataFromSync(byte[] bArr) {
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData();
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(bArr);
        int i = -1;
        while (protoInputStream.nextField() != -1) {
            try {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        i = protoInputStream.readInt(1120986464257L);
                        android.util.Slog.e(TAG, "Processing context sync message version " + i);
                        break;
                    case 4:
                        if (i == 1) {
                            long start = protoInputStream.start(1146756268036L);
                            while (protoInputStream.nextField() != -1) {
                                if (protoInputStream.getFieldNumber() == 1) {
                                    long start2 = protoInputStream.start(2246267895809L);
                                    callMetadataSyncData.addCall(processCallDataFromSync(protoInputStream));
                                    protoInputStream.end(start2);
                                } else if (protoInputStream.getFieldNumber() == 2) {
                                    long start3 = protoInputStream.start(2246267895810L);
                                    while (protoInputStream.nextField() != -1) {
                                        switch (protoInputStream.getFieldNumber()) {
                                            case 1:
                                                long start4 = protoInputStream.start(1146756268033L);
                                                callMetadataSyncData.addCallCreateRequest(processCallCreateRequestDataFromSync(protoInputStream));
                                                protoInputStream.end(start4);
                                                break;
                                            case 2:
                                                long start5 = protoInputStream.start(1146756268034L);
                                                callMetadataSyncData.addCallControlRequest(processCallControlRequestDataFromSync(protoInputStream));
                                                protoInputStream.end(start5);
                                                break;
                                            default:
                                                android.util.Slog.e(TAG, "Unhandled field in Request:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                                                break;
                                        }
                                    }
                                    protoInputStream.end(start3);
                                } else if (protoInputStream.getFieldNumber() == 3) {
                                    long start6 = protoInputStream.start(2246267895811L);
                                    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync = processFacilitatorDataFromSync(protoInputStream);
                                    processFacilitatorDataFromSync.setIsTel(true);
                                    callMetadataSyncData.addFacilitator(processFacilitatorDataFromSync);
                                    protoInputStream.end(start6);
                                } else {
                                    android.util.Slog.e(TAG, "Unhandled field in Telecom:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                                }
                            }
                            protoInputStream.end(start);
                            break;
                        } else {
                            android.util.Slog.e(TAG, "Cannot process unsupported version " + i);
                            break;
                        }
                    default:
                        android.util.Slog.e(TAG, "Unhandled field in ContextSyncMessage:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        break;
                }
            } catch (java.io.IOException | android.util.proto.ProtoParseException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        return callMetadataSyncData;
    }

    public static com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallCreateRequest processCallCreateRequestDataFromSync(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallCreateRequest callCreateRequest = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallCreateRequest();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    callCreateRequest.setId(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    callCreateRequest.setAddress(protoInputStream.readString(1138166333442L));
                    break;
                case 3:
                    long start = protoInputStream.start(1146756268035L);
                    callCreateRequest.setFacilitator(processFacilitatorDataFromSync(protoInputStream));
                    protoInputStream.end(start);
                    break;
                default:
                    android.util.Slog.e(TAG, "Unhandled field in CreateAction:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return callCreateRequest;
    }

    public static com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallControlRequest processCallControlRequestDataFromSync(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallControlRequest callControlRequest = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallControlRequest();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    callControlRequest.setId(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    callControlRequest.setControl(protoInputStream.readInt(1159641169922L));
                    break;
                default:
                    android.util.Slog.e(TAG, "Unhandled field in ControlAction:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return callControlRequest;
    }

    public static com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator callFacilitator = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    callFacilitator.setName(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    callFacilitator.setIdentifier(protoInputStream.readString(1138166333442L));
                    break;
                case 3:
                    callFacilitator.setExtendedIdentifier(protoInputStream.readString(1138166333443L));
                    break;
                default:
                    android.util.Slog.e(TAG, "Unhandled field in Facilitator:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return callFacilitator;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call processCallDataFromSync(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    call.setId(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    long start = protoInputStream.start(1146756268034L);
                    while (protoInputStream.nextField() != -1) {
                        switch (protoInputStream.getFieldNumber()) {
                            case 1:
                                call.setCallerId(protoInputStream.readString(1138166333441L));
                                break;
                            case 2:
                                call.setAppIcon(protoInputStream.readBytes(1151051235330L));
                                break;
                            case 3:
                                long start2 = protoInputStream.start(1146756268035L);
                                call.setFacilitator(processFacilitatorDataFromSync(protoInputStream));
                                protoInputStream.end(start2);
                                break;
                            default:
                                android.util.Slog.e(TAG, "Unhandled field in Origin:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                                break;
                        }
                    }
                    protoInputStream.end(start);
                    break;
                case 3:
                    call.setStatus(protoInputStream.readInt(1159641169923L));
                    break;
                case 4:
                    call.addControl(protoInputStream.readInt(2259152797700L));
                    break;
                case 5:
                    call.setDirection(protoInputStream.readInt(1159641169925L));
                    break;
                default:
                    android.util.Slog.e(TAG, "Unhandled field in Telecom:" + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return call;
    }

    @com.android.internal.annotations.VisibleForTesting
    byte[] createCallUpdateMessage(java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection, int i) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        for (com.android.server.companion.datatransfer.contextsync.CrossDeviceCall crossDeviceCall : collection) {
            if (!crossDeviceCall.isCallPlacedByContextSync() && !this.mCallManager.isExternallyOwned(crossDeviceCall.getId())) {
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getId());
                long start3 = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getReadableCallerId(isAdminBlocked(crossDeviceCall.getUserId())));
                protoOutputStream.write(1151051235330L, crossDeviceCall.getCallingAppIcon());
                long start4 = protoOutputStream.start(1146756268035L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getCallingAppName());
                protoOutputStream.write(1138166333442L, crossDeviceCall.getCallingAppPackageName());
                protoOutputStream.write(1138166333443L, crossDeviceCall.getSerializedPhoneAccountHandle());
                protoOutputStream.end(start4);
                protoOutputStream.end(start3);
                protoOutputStream.write(1159641169923L, crossDeviceCall.getStatus());
                protoOutputStream.write(1159641169925L, crossDeviceCall.getDirection());
                java.util.Iterator<java.lang.Integer> it = crossDeviceCall.getControls().iterator();
                while (it.hasNext()) {
                    protoOutputStream.write(2259152797700L, it.next().intValue());
                }
                protoOutputStream.end(start2);
            }
        }
        for (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator callFacilitator : this.mCallFacilitators) {
            long start5 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, callFacilitator.getName());
            protoOutputStream.write(1138166333442L, callFacilitator.getIdentifier());
            protoOutputStream.write(1138166333443L, callFacilitator.getExtendedIdentifier());
            protoOutputStream.end(start5);
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createCallControlMessage(java.lang.String str, int i) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        long start2 = protoOutputStream.start(2246267895810L);
        long start3 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1159641169922L, i);
        protoOutputStream.end(start3);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createCallCreateMessage(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        long start2 = protoOutputStream.start(2246267895810L);
        long start3 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1138166333442L, str2);
        long start4 = protoOutputStream.start(1146756268035L);
        protoOutputStream.write(1138166333442L, str3);
        protoOutputStream.end(start4);
        protoOutputStream.end(start3);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createEmptyMessage() {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        return protoOutputStream.getBytes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] createFacilitatorMessage() {
        return createCallUpdateMessage(java.util.Collections.emptyList(), -1);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class CallManager {
        private final com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountManager mPhoneAccountManager;
        private final android.telecom.TelecomManager mTelecomManager;

        @com.android.internal.annotations.VisibleForTesting
        final java.util.Set<java.lang.String> mSelfOwnedCalls = new java.util.HashSet();

        @com.android.internal.annotations.VisibleForTesting
        final java.util.Set<java.lang.String> mExternallyOwnedCalls = new java.util.HashSet();

        @com.android.internal.annotations.VisibleForTesting
        final java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> mCallIds = new java.util.HashMap();

        CallManager(android.content.Context context, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountManager phoneAccountManager) {
            this.mTelecomManager = (android.telecom.TelecomManager) context.getSystemService(android.telecom.TelecomManager.class);
            this.mPhoneAccountManager = phoneAccountManager;
        }

        void updateCalls(int i, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData) {
            java.util.Set<java.lang.String> orDefault = this.mCallIds.getOrDefault(java.lang.Integer.valueOf(i), new java.util.HashSet());
            java.util.Set<java.lang.String> set = (java.util.Set) callMetadataSyncData.getCalls().stream().map(new java.util.function.Function() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController$CallManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call) obj).getId();
                }
            }).collect(java.util.stream.Collectors.toSet());
            if (orDefault.equals(set)) {
                return;
            }
            for (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call : callMetadataSyncData.getCalls()) {
                if (!orDefault.contains(call.getId()) && call.getFacilitator() != null && !isSelfOwned(call.getId())) {
                    this.mExternallyOwnedCalls.add(call.getId());
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putInt(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_ASSOCIATION_ID, i);
                    bundle.putBoolean(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_IS_REMOTE_ORIGIN, true);
                    bundle.putBundle(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL, call.writeToBundle());
                    bundle.putString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID, call.getId());
                    bundle.putByteArray(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_FACILITATOR_ICON, call.getAppIcon());
                    android.telecom.PhoneAccountHandle phoneAccountHandle = this.mPhoneAccountManager.getPhoneAccountHandle(i, call.getFacilitator().getIdentifier());
                    if (call.getDirection() == 1) {
                        this.mTelecomManager.addNewIncomingCall(phoneAccountHandle, bundle);
                    } else if (call.getDirection() == 2) {
                        android.os.Bundle bundle2 = new android.os.Bundle();
                        bundle2.putParcelable("android.telecom.extra.OUTGOING_CALL_EXTRAS", bundle);
                        bundle2.putParcelable("android.telecom.extra.PHONE_ACCOUNT_HANDLE", phoneAccountHandle);
                        java.lang.String callerId = call.getCallerId();
                        if (callerId != null) {
                            this.mTelecomManager.placeCall(android.net.Uri.fromParts("sip", callerId, null), bundle2);
                        }
                    }
                }
            }
            this.mCallIds.put(java.lang.Integer.valueOf(i), set);
        }

        java.util.Set<java.lang.String> clearCallIdsForAssociationId(int i) {
            return this.mCallIds.remove(java.lang.Integer.valueOf(i));
        }

        void addSelfOwnedCallId(java.lang.String str) {
            this.mSelfOwnedCalls.add(str);
        }

        void removeSelfOwnedCallId(java.lang.String str) {
            this.mSelfOwnedCalls.remove(str);
        }

        boolean isExternallyOwned(java.lang.String str) {
            return this.mExternallyOwnedCalls.contains(str);
        }

        private boolean isSelfOwned(java.lang.String str) {
            java.util.Iterator<java.lang.String> it = this.mSelfOwnedCalls.iterator();
            while (it.hasNext()) {
                if (str.endsWith(it.next())) {
                    return true;
                }
            }
            return false;
        }
    }

    static class PhoneAccountManager {
        private final android.content.ComponentName mConnectionServiceComponentName;
        private final java.util.Map<com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier, android.telecom.PhoneAccountHandle> mPhoneAccountHandles = new java.util.HashMap();
        private final android.telecom.TelecomManager mTelecomManager;

        PhoneAccountManager(android.content.Context context) {
            this.mTelecomManager = (android.telecom.TelecomManager) context.getSystemService(android.telecom.TelecomManager.class);
            this.mConnectionServiceComponentName = new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.class);
        }

        void onBootCompleted() {
            this.mTelecomManager.clearPhoneAccounts();
        }

        android.telecom.PhoneAccountHandle getPhoneAccountHandle(int i, java.lang.String str) {
            return this.mPhoneAccountHandles.get(new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier(i, str));
        }

        void updateFacilitators(int i, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call> it = callMetadataSyncData.getCalls().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getFacilitator());
            }
            arrayList.addAll(callMetadataSyncData.getFacilitators());
            updateFacilitators(i, arrayList);
        }

        private void updateFacilitators(int i, java.util.List<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator> list) {
            java.util.Iterator<com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier> it = this.mPhoneAccountHandles.keySet().iterator();
            while (it.hasNext()) {
                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier next = it.next();
                final java.lang.String appIdentifier = next.getAppIdentifier();
                if (i == next.getAssociationId() && list.stream().noneMatch(new java.util.function.Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController$PhoneAccountManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$updateFacilitators$0;
                        lambda$updateFacilitators$0 = com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountManager.lambda$updateFacilitators$0(appIdentifier, (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator) obj);
                        return lambda$updateFacilitators$0;
                    }
                })) {
                    unregisterPhoneAccount(this.mPhoneAccountHandles.get(next));
                    it.remove();
                }
            }
            for (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator callFacilitator : list) {
                com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier phoneAccountHandleIdentifier = new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier(i, callFacilitator.getIdentifier());
                if (!this.mPhoneAccountHandles.containsKey(phoneAccountHandleIdentifier)) {
                    registerPhoneAccount(phoneAccountHandleIdentifier, callFacilitator.getName(), callFacilitator.isTel());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$updateFacilitators$0(java.lang.String str, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator callFacilitator) {
            return str != null && str.equals(callFacilitator.getIdentifier());
        }

        private void registerPhoneAccount(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier phoneAccountHandleIdentifier, java.lang.String str, boolean z) {
            if (this.mPhoneAccountHandles.containsKey(phoneAccountHandleIdentifier)) {
                return;
            }
            android.telecom.PhoneAccountHandle phoneAccountHandle = new android.telecom.PhoneAccountHandle(this.mConnectionServiceComponentName, java.util.UUID.randomUUID().toString());
            this.mPhoneAccountHandles.put(phoneAccountHandleIdentifier, phoneAccountHandle);
            this.mTelecomManager.registerPhoneAccount(createPhoneAccount(phoneAccountHandle, str, phoneAccountHandleIdentifier.getAppIdentifier(), phoneAccountHandleIdentifier.getAssociationId(), z));
            this.mTelecomManager.enablePhoneAccount(this.mPhoneAccountHandles.get(phoneAccountHandleIdentifier), true);
        }

        private void unregisterPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
            this.mTelecomManager.unregisterPhoneAccount(phoneAccountHandle);
        }

        @com.android.internal.annotations.VisibleForTesting
        static android.telecom.PhoneAccount createPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2, int i, boolean z) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_FACILITATOR_ID, str2);
            bundle.putInt(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_ASSOCIATION_ID, i);
            return new android.telecom.PhoneAccount.Builder(phoneAccountHandle, str).setExtras(bundle).setSupportedUriSchemes(java.util.List.of(z ? "tel" : "sip")).setCapabilities(3).build();
        }
    }

    static final class PhoneAccountHandleIdentifier {
        private final java.lang.String mAppIdentifier;
        private final int mAssociationId;

        PhoneAccountHandleIdentifier(int i, java.lang.String str) {
            this.mAssociationId = i;
            this.mAppIdentifier = str;
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public java.lang.String getAppIdentifier() {
            return this.mAppIdentifier;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAssociationId), this.mAppIdentifier);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier)) {
                return false;
            }
            com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier phoneAccountHandleIdentifier = (com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.PhoneAccountHandleIdentifier) obj;
            return phoneAccountHandleIdentifier.getAssociationId() == this.mAssociationId && this.mAppIdentifier != null && this.mAppIdentifier.equals(phoneAccountHandleIdentifier.getAppIdentifier());
        }
    }
}
