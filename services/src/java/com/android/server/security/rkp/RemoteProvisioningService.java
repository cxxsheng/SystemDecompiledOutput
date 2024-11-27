package com.android.server.security.rkp;

/* loaded from: classes2.dex */
public class RemoteProvisioningService extends com.android.server.SystemService {
    private static final java.time.Duration CREATE_REGISTRATION_TIMEOUT = java.time.Duration.ofSeconds(10);
    public static final java.lang.String TAG = "RemoteProvisionSysSvc";
    private final com.android.server.security.rkp.RemoteProvisioningService.RemoteProvisioningImpl mBinderImpl;

    private static class RegistrationReceiver implements android.os.OutcomeReceiver<android.security.rkp.service.RegistrationProxy, java.lang.Exception> {
        private final android.security.rkp.IGetRegistrationCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        RegistrationReceiver(java.util.concurrent.Executor executor, android.security.rkp.IGetRegistrationCallback iGetRegistrationCallback) {
            this.mExecutor = executor;
            this.mCallback = iGetRegistrationCallback;
        }

        @Override // android.os.OutcomeReceiver
        public void onResult(android.security.rkp.service.RegistrationProxy registrationProxy) {
            try {
                this.mCallback.onSuccess(new com.android.server.security.rkp.RemoteProvisioningRegistration(registrationProxy, this.mExecutor));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.security.rkp.RemoteProvisioningService.TAG, "Error calling success callback " + this.mCallback.asBinder().hashCode(), e);
            }
        }

        @Override // android.os.OutcomeReceiver
        public void onError(java.lang.Exception exc) {
            try {
                this.mCallback.onError(exc.toString());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.security.rkp.RemoteProvisioningService.TAG, "Error calling error callback " + this.mCallback.asBinder().hashCode(), e);
            }
        }
    }

    public RemoteProvisioningService(android.content.Context context) {
        super(context);
        this.mBinderImpl = new com.android.server.security.rkp.RemoteProvisioningService.RemoteProvisioningImpl();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("remote_provisioning", this.mBinderImpl);
    }

    private final class RemoteProvisioningImpl extends android.security.rkp.IRemoteProvisioning.Stub {
        private RemoteProvisioningImpl() {
        }

        public void getRegistration(java.lang.String str, android.security.rkp.IGetRegistrationCallback iGetRegistrationCallback) throws android.os.RemoteException {
            int callingUidOrThrow = android.os.Binder.getCallingUidOrThrow();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            java.util.concurrent.Executor mainExecutor = com.android.server.security.rkp.RemoteProvisioningService.this.getContext().getMainExecutor();
            try {
                android.util.Log.i(com.android.server.security.rkp.RemoteProvisioningService.TAG, "getRegistration(" + str + ")");
                android.security.rkp.service.RegistrationProxy.createAsync(com.android.server.security.rkp.RemoteProvisioningService.this.getContext(), callingUidOrThrow, str, com.android.server.security.rkp.RemoteProvisioningService.CREATE_REGISTRATION_TIMEOUT, mainExecutor, new com.android.server.security.rkp.RemoteProvisioningService.RegistrationReceiver(mainExecutor, iGetRegistrationCallback));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.security.rkp.RemoteProvisioningService.this.getContext(), com.android.server.security.rkp.RemoteProvisioningService.TAG, printWriter)) {
                int callingUidOrThrow = android.os.Binder.getCallingUidOrThrow();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    new com.android.server.security.rkp.RemoteProvisioningShellCommand(com.android.server.security.rkp.RemoteProvisioningService.this.getContext(), callingUidOrThrow).dump(printWriter);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
            int callingUidOrThrow = android.os.Binder.getCallingUidOrThrow();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return new com.android.server.security.rkp.RemoteProvisioningShellCommand(com.android.server.security.rkp.RemoteProvisioningService.this.getContext(), callingUidOrThrow).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
