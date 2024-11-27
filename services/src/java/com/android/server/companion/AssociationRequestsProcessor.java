package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
class AssociationRequestsProcessor {
    private static final int ASSOCIATE_WITHOUT_PROMPT_MAX_PER_TIME_WINDOW = 5;
    private static final long ASSOCIATE_WITHOUT_PROMPT_WINDOW_MS = 3600000;
    private static final java.lang.String EXTRA_APPLICATION_CALLBACK = "application_callback";
    private static final java.lang.String EXTRA_ASSOCIATION = "association";
    private static final java.lang.String EXTRA_ASSOCIATION_REQUEST = "association_request";
    private static final java.lang.String EXTRA_FORCE_CANCEL_CONFIRMATION = "cancel_confirmation";
    private static final java.lang.String EXTRA_MAC_ADDRESS = "mac_address";
    private static final java.lang.String EXTRA_RESULT_RECEIVER = "result_receiver";
    private static final int RESULT_CODE_ASSOCIATION_APPROVED = 0;
    private static final int RESULT_CODE_ASSOCIATION_CREATED = 0;
    private static final java.lang.String TAG = "CDM_AssociationRequestsProcessor";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStoreImpl mAssociationStore;

    @android.annotation.NonNull
    private final android.content.ComponentName mCompanionDeviceActivity;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final android.os.ResultReceiver mOnRequestConfirmationReceiver = new android.os.ResultReceiver(android.os.Handler.getMain()) { // from class: com.android.server.companion.AssociationRequestsProcessor.1
        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            android.net.MacAddress macAddress;
            if (i != 0) {
                android.util.Slog.w(com.android.server.companion.AssociationRequestsProcessor.TAG, "Unknown result code:" + i);
                return;
            }
            android.companion.AssociationRequest associationRequest = (android.companion.AssociationRequest) bundle.getParcelable(com.android.server.companion.AssociationRequestsProcessor.EXTRA_ASSOCIATION_REQUEST, android.companion.AssociationRequest.class);
            android.companion.IAssociationRequestCallback asInterface = android.companion.IAssociationRequestCallback.Stub.asInterface(bundle.getBinder(com.android.server.companion.AssociationRequestsProcessor.EXTRA_APPLICATION_CALLBACK));
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) bundle.getParcelable(com.android.server.companion.AssociationRequestsProcessor.EXTRA_RESULT_RECEIVER, android.os.ResultReceiver.class);
            java.util.Objects.requireNonNull(associationRequest);
            java.util.Objects.requireNonNull(asInterface);
            java.util.Objects.requireNonNull(resultReceiver);
            if (associationRequest.isSelfManaged()) {
                macAddress = null;
            } else {
                macAddress = (android.net.MacAddress) bundle.getParcelable(com.android.server.companion.AssociationRequestsProcessor.EXTRA_MAC_ADDRESS, android.net.MacAddress.class);
                java.util.Objects.requireNonNull(macAddress);
            }
            com.android.server.companion.AssociationRequestsProcessor.this.processAssociationRequestApproval(associationRequest, asInterface, resultReceiver, macAddress);
        }
    };

    @android.annotation.NonNull
    private final android.content.pm.PackageManagerInternal mPackageManager;

    @android.annotation.NonNull
    private final com.android.server.companion.CompanionDeviceManagerService mService;

    AssociationRequestsProcessor(@android.annotation.NonNull com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService, @android.annotation.NonNull com.android.server.companion.AssociationStoreImpl associationStoreImpl) {
        this.mContext = companionDeviceManagerService.getContext();
        this.mService = companionDeviceManagerService;
        this.mPackageManager = companionDeviceManagerService.mPackageManagerInternal;
        this.mAssociationStore = associationStoreImpl;
        this.mCompanionDeviceActivity = android.content.ComponentName.createRelative(this.mContext.getString(android.R.string.config_cameraLaunchGestureSensorStringType), ".CompanionDeviceActivity");
    }

    void processNewAssociationRequest(@android.annotation.NonNull android.companion.AssociationRequest associationRequest, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.companion.IAssociationRequestCallback iAssociationRequestCallback) {
        java.util.Objects.requireNonNull(associationRequest, "Request MUST NOT be null");
        if (associationRequest.isSelfManaged()) {
            java.util.Objects.requireNonNull(associationRequest.getDisplayName(), "AssociationRequest.displayName MUST NOT be null.");
        }
        java.util.Objects.requireNonNull(str, "Package name MUST NOT be null");
        java.util.Objects.requireNonNull(iAssociationRequestCallback, "Callback MUST NOT be null");
        int packageUid = this.mPackageManager.getPackageUid(str, 0L, i);
        com.android.server.companion.utils.PermissionsUtils.enforcePermissionForCreatingAssociation(this.mContext, associationRequest, packageUid);
        com.android.server.companion.utils.PackageUtils.enforceUsesCompanionDeviceFeature(this.mContext, i, str);
        if (associationRequest.isSelfManaged() && !associationRequest.isForceConfirmation() && !willAddRoleHolder(associationRequest, str, i)) {
            createAssociationAndNotifyApplication(associationRequest, str, i, null, iAssociationRequestCallback, null);
            return;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            android.util.Slog.e(TAG, "3p apps are not allowed to create associations on watch.");
            try {
                iAssociationRequestCallback.onFailure("3p apps are not allowed to create associations on watch.");
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        associationRequest.setPackageName(str);
        associationRequest.setUserId(i);
        associationRequest.setSkipPrompt(mayAssociateWithoutPrompt(str, i));
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(EXTRA_ASSOCIATION_REQUEST, associationRequest);
        bundle.putBinder(EXTRA_APPLICATION_CALLBACK, iAssociationRequestCallback.asBinder());
        bundle.putParcelable(EXTRA_RESULT_RECEIVER, com.android.server.companion.utils.Utils.prepareForIpc(this.mOnRequestConfirmationReceiver));
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(this.mCompanionDeviceActivity);
        intent.putExtras(bundle);
        try {
            iAssociationRequestCallback.onAssociationPending(createPendingIntent(packageUid, intent));
        } catch (android.os.RemoteException e2) {
        }
    }

    android.app.PendingIntent buildAssociationCancellationIntent(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, "Package name MUST NOT be null");
        com.android.server.companion.utils.PackageUtils.enforceUsesCompanionDeviceFeature(this.mContext, i, str);
        int packageUid = this.mPackageManager.getPackageUid(str, 0L, i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(EXTRA_FORCE_CANCEL_CONFIRMATION, true);
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(this.mCompanionDeviceActivity);
        intent.putExtras(bundle);
        return createPendingIntent(packageUid, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processAssociationRequestApproval(@android.annotation.NonNull android.companion.AssociationRequest associationRequest, @android.annotation.NonNull android.companion.IAssociationRequestCallback iAssociationRequestCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver, @android.annotation.Nullable android.net.MacAddress macAddress) {
        java.lang.String packageName = associationRequest.getPackageName();
        int userId = associationRequest.getUserId();
        try {
            com.android.server.companion.utils.PermissionsUtils.enforcePermissionForCreatingAssociation(this.mContext, associationRequest, this.mPackageManager.getPackageUid(packageName, 0L, userId));
            createAssociationAndNotifyApplication(associationRequest, packageName, userId, macAddress, iAssociationRequestCallback, resultReceiver);
        } catch (java.lang.SecurityException e) {
            try {
                iAssociationRequestCallback.onFailure(e.getMessage());
            } catch (android.os.RemoteException e2) {
            }
        }
    }

    private void createAssociationAndNotifyApplication(@android.annotation.NonNull android.companion.AssociationRequest associationRequest, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable android.net.MacAddress macAddress, @android.annotation.NonNull android.companion.IAssociationRequestCallback iAssociationRequestCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            createAssociation(i, str, macAddress, associationRequest.getDisplayName(), associationRequest.getDeviceProfile(), associationRequest.getAssociatedDevice(), associationRequest.isSelfManaged(), iAssociationRequestCallback, resultReceiver);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void createAssociation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.net.MacAddress macAddress, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.companion.AssociatedDevice associatedDevice, boolean z, @android.annotation.Nullable android.companion.IAssociationRequestCallback iAssociationRequestCallback, @android.annotation.Nullable android.os.ResultReceiver resultReceiver) {
        maybeGrantRoleAndStoreAssociation(new android.companion.AssociationInfo(this.mService.getNewAssociationIdForPackage(i, str), i, str, null, macAddress, charSequence, str2, associatedDevice, z, false, false, false, java.lang.System.currentTimeMillis(), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 0), iAssociationRequestCallback, resultReceiver);
    }

    public void maybeGrantRoleAndStoreAssociation(@android.annotation.NonNull final android.companion.AssociationInfo associationInfo, @android.annotation.Nullable final android.companion.IAssociationRequestCallback iAssociationRequestCallback, @android.annotation.Nullable final android.os.ResultReceiver resultReceiver) {
        com.android.server.companion.utils.RolesUtils.addRoleHolderForAssociation(this.mService.getContext(), associationInfo, new java.util.function.Consumer() { // from class: com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.companion.AssociationRequestsProcessor.this.lambda$maybeGrantRoleAndStoreAssociation$0(associationInfo, iAssociationRequestCallback, resultReceiver, (java.lang.Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeGrantRoleAndStoreAssociation$0(android.companion.AssociationInfo associationInfo, android.companion.IAssociationRequestCallback iAssociationRequestCallback, android.os.ResultReceiver resultReceiver, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            android.util.Slog.i(TAG, "Added " + associationInfo.getDeviceProfile() + " role to userId=" + associationInfo.getUserId() + ", packageName=" + associationInfo.getPackageName());
            addAssociationToStore(associationInfo);
            sendCallbackAndFinish(associationInfo, iAssociationRequestCallback, resultReceiver);
            return;
        }
        android.util.Slog.e(TAG, "Failed to add u" + associationInfo.getUserId() + "\\" + associationInfo.getPackageName() + " to the list of " + associationInfo.getDeviceProfile() + " holders.");
        sendCallbackAndFinish(null, iAssociationRequestCallback, resultReceiver);
    }

    public void enableSystemDataSync(int i, int i2) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        this.mAssociationStore.updateAssociation(new android.companion.AssociationInfo.Builder(associationById).setSystemDataSyncFlags(associationById.getSystemDataSyncFlags() | i2).build());
    }

    public void disableSystemDataSync(int i, int i2) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        this.mAssociationStore.updateAssociation(new android.companion.AssociationInfo.Builder(associationById).setSystemDataSyncFlags(associationById.getSystemDataSyncFlags() & (~i2)).build());
    }

    private void addAssociationToStore(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        android.util.Slog.i(TAG, "New CDM association created=" + associationInfo);
        this.mAssociationStore.addAssociation(associationInfo);
        this.mService.updateSpecialAccessPermissionForAssociatedPackage(associationInfo);
        com.android.server.companion.utils.MetricUtils.logCreateAssociation(associationInfo.getDeviceProfile());
    }

    private void sendCallbackAndFinish(@android.annotation.Nullable android.companion.AssociationInfo associationInfo, @android.annotation.Nullable android.companion.IAssociationRequestCallback iAssociationRequestCallback, @android.annotation.Nullable android.os.ResultReceiver resultReceiver) {
        if (associationInfo != null) {
            if (iAssociationRequestCallback != null) {
                try {
                    iAssociationRequestCallback.onAssociationCreated(associationInfo);
                } catch (android.os.RemoteException e) {
                }
            }
            if (resultReceiver != null) {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable(EXTRA_ASSOCIATION, associationInfo);
                resultReceiver.send(0, bundle);
                return;
            }
            return;
        }
        if (iAssociationRequestCallback != null) {
            try {
                iAssociationRequestCallback.onFailure("internal_error");
            } catch (android.os.RemoteException e2) {
            }
        }
        if (resultReceiver != null) {
            resultReceiver.send(3, new android.os.Bundle());
        }
    }

    private boolean willAddRoleHolder(@android.annotation.NonNull android.companion.AssociationRequest associationRequest, @android.annotation.NonNull final java.lang.String str, final int i) {
        if (associationRequest.getDeviceProfile() == null) {
            return false;
        }
        return !((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0019: RETURN 
              (wrap:boolean:NOT 
              (wrap:boolean:0x0013: INVOKE 
              (wrap:java.lang.Boolean:0x0011: CHECK_CAST (java.lang.Boolean) (wrap:java.lang.Object:0x000d: INVOKE 
              (wrap:com.android.internal.util.FunctionalUtils$ThrowingSupplier:0x000a: CONSTRUCTOR 
              (r1v0 'this' com.android.server.companion.AssociationRequestsProcessor A[DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r4v0 'i' int A[DONT_INLINE])
              (r3v0 'str' java.lang.String A[DONT_INLINE])
              (r2 I:java.lang.String A[DONT_INLINE])
             A[DONT_GENERATE, MD:(com.android.server.companion.AssociationRequestsProcessor, int, java.lang.String, java.lang.String):void (m), REMOVE, WRAPPED] (LINE:400) call: com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1.<init>(com.android.server.companion.AssociationRequestsProcessor, int, java.lang.String, java.lang.String):void type: CONSTRUCTOR)
             STATIC call: android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingSupplier):java.lang.Object A[DONT_GENERATE, MD:(com.android.internal.util.FunctionalUtils$ThrowingSupplier):java.lang.Object (s), REMOVE, WRAPPED] (LINE:400)))
             VIRTUAL call: java.lang.Boolean.booleanValue():boolean A[DONT_GENERATE, MD:():boolean (c), REMOVE, WRAPPED] (LINE:400))
             A[WRAPPED])
             (LINE:404) in method: com.android.server.companion.AssociationRequestsProcessor.willAddRoleHolder(android.companion.AssociationRequest, java.lang.String, int):boolean, file: classes.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getCodeVar()" because the return value of "jadx.core.dex.instructions.args.RegisterArg.getSVar()" is null
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:836)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:97)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:878)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.oneArgInsn(InsnGen.java:689)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:362)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 19 more
            */
        /*
            this = this;
            java.lang.String r2 = r2.getDeviceProfile()
            if (r2 != 0) goto L8
            r2 = 0
            return r2
        L8:
            com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1 r0 = new com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1
            r0.<init>()
            java.lang.Object r2 = android.os.Binder.withCleanCallingIdentity(r0)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r2 = r2 ^ 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.companion.AssociationRequestsProcessor.willAddRoleHolder(android.companion.AssociationRequest, java.lang.String, int):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$willAddRoleHolder$1(int i, java.lang.String str, java.lang.String str2) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(com.android.server.companion.utils.RolesUtils.isRoleHolder(this.mContext, i, str, str2));
    }

    private android.app.PendingIntent createPendingIntent(int i, android.content.Intent intent) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.app.PendingIntent.getActivityAsUser(this.mContext, i, intent, 1409286144, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), android.os.UserHandle.CURRENT);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean mayAssociateWithoutPrompt(@android.annotation.NonNull java.lang.String str, int i) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociationsForPackage(i, str).iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if ((currentTimeMillis - it.next().getTimeApprovedMs() < 3600000) && (i2 = i2 + 1) >= 5) {
                android.util.Slog.w(TAG, "Too many associations: " + str + " already associated " + i2 + " devices within the last 3600000ms");
                return false;
            }
        }
        return com.android.server.companion.utils.PackageUtils.isPackageAllowlisted(this.mContext, this.mPackageManager, str);
    }
}
