package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class FaceService extends com.android.server.SystemService {
    protected static final java.lang.String TAG = "FaceService";

    @android.annotation.NonNull
    private final java.util.function.Supplier<java.lang.String[]> mAidlInstanceNameSupplier;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback<com.android.server.biometrics.sensors.face.ServiceProvider, android.hardware.face.FaceSensorPropertiesInternal> mBiometricStateCallback;

    @android.annotation.NonNull
    private final java.util.function.Function<java.lang.String, com.android.server.biometrics.sensors.face.aidl.FaceProvider> mFaceProvider;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.FaceService.FaceProviderFunction mFaceProviderFunction;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.FaceServiceRegistry mRegistry;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper mServiceWrapper;

    interface FaceProviderFunction {
        com.android.server.biometrics.sensors.face.aidl.FaceProvider getFaceProvider(android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> pair, boolean z);
    }

    public static native android.os.NativeHandle acquireSurfaceHandle(@android.annotation.NonNull android.view.Surface surface);

    public static native void releaseSurfaceHandle(@android.annotation.NonNull android.os.NativeHandle nativeHandle);

    @com.android.internal.annotations.VisibleForTesting
    final class FaceServiceWrapper extends android.hardware.face.IFaceService.Stub {
        FaceServiceWrapper() {
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
            super.createTestSession_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for createTestSession, sensorId: " + i);
                return null;
            }
            return providerForSensor.createTestSession(i, iTestSessionCallback, str);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public byte[] dumpSensorServiceStateProto(int i, boolean z) {
            super.dumpSensorServiceStateProto_enforcePermission();
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor != null) {
                providerForSensor.dumpProtoState(i, protoOutputStream, z);
            }
            protoOutputStream.flush();
            return protoOutputStream.getBytes();
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) {
            super.getSensorPropertiesInternal_enforcePermission();
            return com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getAllProperties();
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i, @android.annotation.NonNull java.lang.String str) {
            super.getSensorProperties_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
                return null;
            }
            return providerForSensor.getSensorProperties(i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
            super.generateChallenge_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "No matching sensor for generateChallenge, sensorId: " + i);
                return;
            }
            providerForSensor.scheduleGenerateChallenge(i, i2, iBinder, iFaceServiceReceiver, str);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) {
            super.revokeChallenge_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "No matching sensor for revokeChallenge, sensorId: " + i);
                return;
            }
            providerForSensor.scheduleRevokeChallenge(i, i2, iBinder, str, j);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public long enroll(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
            super.enroll_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for enroll");
                return -1L;
            }
            return ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleEnroll(((java.lang.Integer) singleProvider.first).intValue(), iBinder, bArr, i, iFaceServiceReceiver, str, iArr, surface, z, faceEnrollOptions);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void scheduleWatchdog() {
            super.scheduleWatchdog_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for scheduling watchdog");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleWatchdog(((java.lang.Integer) singleProvider.first).intValue());
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public long enrollRemotely(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr) {
            super.enrollRemotely_enforcePermission();
            return -1L;
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void cancelEnrollment(android.os.IBinder iBinder, long j) {
            super.cancelEnrollment_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for cancelEnrollment");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).cancelEnrollment(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long authenticate(android.os.IBinder iBinder, long j, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
            int i;
            super.authenticate_enforcePermission();
            java.lang.String opPackageName = faceAuthenticateOptions.getOpPackageName();
            if (com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), opPackageName)) {
                i = 1;
            } else {
                i = 0;
            }
            boolean isKeyguard = com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), opPackageName);
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for authenticate");
                return -1L;
            }
            faceAuthenticateOptions.setSensorId(((java.lang.Integer) singleProvider.first).intValue());
            return ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleAuthenticate(iBinder, j, 0, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), faceAuthenticateOptions, false, i, isKeyguard);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long detectFace(android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
            super.detectFace_enforcePermission();
            java.lang.String opPackageName = faceAuthenticateOptions.getOpPackageName();
            if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), opPackageName)) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "detectFace called from non-sysui package: " + opPackageName);
                return -1L;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for detectFace");
                return -1L;
            }
            faceAuthenticateOptions.setSensorId(((java.lang.Integer) singleProvider.first).intValue());
            return ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleFaceDetect(iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), faceAuthenticateOptions, 1);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) {
            super.prepareForAuthentication_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(faceAuthenticateOptions.getSensorId());
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for prepareForAuthentication");
            } else {
                providerForSensor.scheduleAuthenticate(iBinder, j, i, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iBiometricSensorReceiver), faceAuthenticateOptions, j2, true, 2, z2);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void startPreparedClient(int i, int i2) {
            super.startPreparedClient_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for startPreparedClient");
            } else {
                providerForSensor.startPreparedClient(i, i2);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) {
            super.cancelAuthentication_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for cancelAuthentication");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).cancelAuthentication(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void cancelFaceDetect(android.os.IBinder iBinder, java.lang.String str, long j) {
            super.cancelFaceDetect_enforcePermission();
            if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), str)) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "cancelFaceDetect called from non-sysui package: " + str);
                return;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for cancelFaceDetect");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).cancelFaceDetect(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) {
            super.cancelAuthenticationFromService_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for cancelAuthenticationFromService");
            } else {
                providerForSensor.cancelAuthentication(i, iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
            super.remove_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for remove");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleRemove(((java.lang.Integer) singleProvider.first).intValue(), iBinder, i, i2, iFaceServiceReceiver, str);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void removeAll(android.os.IBinder iBinder, int i, final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
            super.removeAll_enforcePermission();
            android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver2 = new android.hardware.face.FaceServiceReceiver() { // from class: com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.1
                final int numSensors;
                int sensorsFinishedRemoving = 0;

                {
                    this.numSensors = com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.this.getSensorPropertiesInternal(com.android.server.biometrics.sensors.face.FaceService.this.getContext().getOpPackageName()).size();
                }

                public void onRemoved(android.hardware.face.Face face, int i2) throws android.os.RemoteException {
                    if (i2 == 0) {
                        this.sensorsFinishedRemoving++;
                        android.util.Slog.d(com.android.server.biometrics.sensors.face.FaceService.TAG, "sensorsFinishedRemoving: " + this.sensorsFinishedRemoving + ", numSensors: " + this.numSensors);
                        if (this.sensorsFinishedRemoving == this.numSensors) {
                            iFaceServiceReceiver.onRemoved((android.hardware.face.Face) null, 0);
                        }
                    }
                }

                public void onError(int i2, int i3) throws android.os.RemoteException {
                    iFaceServiceReceiver.onError(i2, i3);
                }
            };
            for (com.android.server.biometrics.sensors.face.ServiceProvider serviceProvider : com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders()) {
                java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it = serviceProvider.getSensorProperties().iterator();
                while (it.hasNext()) {
                    serviceProvider.scheduleRemoveAll(it.next().sensorId, iBinder, i, iFaceServiceReceiver2, str);
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) {
            super.addLockoutResetCallback_enforcePermission();
            com.android.server.biometrics.sensors.face.FaceService.this.mLockoutResetDispatcher.addCallback(iBiometricServiceLockoutResetCallback, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.biometrics.sensors.face.FaceShellCommand(com.android.server.biometrics.sensors.face.FaceService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), com.android.server.biometrics.sensors.face.FaceService.TAG, printWriter)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (strArr.length > 1 && "--proto".equals(strArr[0]) && "--state".equals(strArr[1])) {
                    android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
                    for (com.android.server.biometrics.sensors.face.ServiceProvider serviceProvider : com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders()) {
                        java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it = serviceProvider.getSensorProperties().iterator();
                        while (it.hasNext()) {
                            serviceProvider.dumpProtoState(it.next().sensorId, protoOutputStream, false);
                        }
                    }
                    protoOutputStream.flush();
                } else if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    for (com.android.server.biometrics.sensors.face.ServiceProvider serviceProvider2 : com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders()) {
                        java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it2 = serviceProvider2.getSensorProperties().iterator();
                        while (it2.hasNext()) {
                            serviceProvider2.dumpProtoMetrics(it2.next().sensorId, fileDescriptor);
                        }
                    }
                } else if (strArr.length > 1 && "--hal".equals(strArr[0])) {
                    for (com.android.server.biometrics.sensors.face.ServiceProvider serviceProvider3 : com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders()) {
                        java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it3 = serviceProvider3.getSensorProperties().iterator();
                        while (it3.hasNext()) {
                            serviceProvider3.dumpHal(it3.next().sensorId, fileDescriptor, (java.lang.String[]) java.util.Arrays.copyOfRange(strArr, 1, strArr.length, strArr.getClass()));
                        }
                    }
                } else {
                    for (com.android.server.biometrics.sensors.face.ServiceProvider serviceProvider4 : com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders()) {
                        for (android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal : serviceProvider4.getSensorProperties()) {
                            printWriter.println("Dumping for sensorId: " + faceSensorPropertiesInternal.sensorId + ", provider: " + serviceProvider4.getClass().getSimpleName());
                            serviceProvider4.dumpInternal(faceSensorPropertiesInternal.sensorId, printWriter);
                            printWriter.println();
                        }
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public boolean isHardwareDetected(int i, java.lang.String str) {
            super.isHardwareDetected_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
                if (providerForSensor != null) {
                    return providerForSensor.isHardwareDetected(i);
                }
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for isHardwareDetected, caller: " + str);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2, java.lang.String str) {
            super.getEnrolledFaces_enforcePermission();
            if (i2 != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for getEnrolledFaces, caller: " + str);
                return java.util.Collections.emptyList();
            }
            return providerForSensor.getEnrolledFaces(i, i2);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public boolean hasEnrolledFaces(int i, int i2, java.lang.String str) {
            super.hasEnrolledFaces_enforcePermission();
            if (i2 != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor != null) {
                return providerForSensor.getEnrolledFaces(i, i2).size() > 0;
            }
            android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for hasEnrolledFaces, caller: " + str);
            return false;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int getLockoutModeForUser(int i, int i2) {
            super.getLockoutModeForUser_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for getLockoutModeForUser");
                return 0;
            }
            return providerForSensor.getLockoutModeForUser(i, i2);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
            super.invalidateAuthenticatorId_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for invalidateAuthenticatorId");
            } else {
                providerForSensor.scheduleInvalidateAuthenticatorId(i, i2, iInvalidationCallback);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long getAuthenticatorId(int i, int i2) {
            super.getAuthenticatorId_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for getAuthenticatorId");
                return 0L;
            }
            return providerForSensor.getAuthenticatorId(i, i2);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) {
            super.resetLockout_enforcePermission();
            com.android.server.biometrics.sensors.face.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for resetLockout, caller: " + str);
                return;
            }
            providerForSensor.scheduleResetLockout(i, i2, bArr);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void setFeature(android.os.IBinder iBinder, int i, int i2, boolean z, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
            super.setFeature_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for setFeature");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleSetFeature(((java.lang.Integer) singleProvider.first).intValue(), iBinder, i, i2, z, bArr, iFaceServiceReceiver, str);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void getFeature(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
            super.getFeature_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.FaceService.TAG, "Null provider for getFeature");
            } else {
                ((com.android.server.biometrics.sensors.face.ServiceProvider) singleProvider.second).scheduleGetFeature(((java.lang.Integer) singleProvider.first).intValue(), iBinder, i, i2, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), str);
            }
        }

        @android.annotation.NonNull
        private java.util.List<com.android.server.biometrics.sensors.face.ServiceProvider> getHidlProviders(@android.annotation.NonNull java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(com.android.server.biometrics.sensors.face.hidl.Face10.newInstance(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), com.android.server.biometrics.sensors.face.FaceService.this.mBiometricStateCallback, com.android.server.biometrics.sensors.face.FaceService.this.mAuthenticationStateListeners, it.next(), com.android.server.biometrics.sensors.face.FaceService.this.mLockoutResetDispatcher));
            }
            return arrayList;
        }

        @android.annotation.NonNull
        private java.util.List<com.android.server.biometrics.sensors.face.ServiceProvider> getAidlProviders(@android.annotation.NonNull java.util.List<java.lang.String> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : list) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider = (com.android.server.biometrics.sensors.face.aidl.FaceProvider) com.android.server.biometrics.sensors.face.FaceService.this.mFaceProvider.apply(str);
                android.util.Slog.i(com.android.server.biometrics.sensors.face.FaceService.TAG, "Adding AIDL provider: " + str);
                arrayList.add(faceProvider);
            }
            return arrayList;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticators(@android.annotation.NonNull final java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) {
            super.registerAuthenticators_enforcePermission();
            com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.registerAll(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.util.List lambda$registerAuthenticators$0;
                    lambda$registerAuthenticators$0 = com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.this.lambda$registerAuthenticators$0(list);
                    return lambda$registerAuthenticators$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.util.List lambda$registerAuthenticators$0(java.util.List list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.lang.String[] strArr = (java.lang.String[]) com.android.server.biometrics.sensors.face.FaceService.this.mAidlInstanceNameSupplier.get();
            if (strArr != null) {
                arrayList.addAll(com.google.android.collect.Lists.newArrayList(strArr));
            }
            android.util.Pair<java.util.List<android.hardware.face.FaceSensorPropertiesInternal>, java.util.List<java.lang.String>> filterAvailableHalInstances = filterAvailableHalInstances(list, arrayList);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            arrayList2.addAll(getHidlProviders((java.util.List) filterAvailableHalInstances.first));
            arrayList2.addAll(getAidlProviders((java.util.List) filterAvailableHalInstances.second));
            return arrayList2;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticatorsLegacy(final android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) {
            super.registerAuthenticatorsLegacy_enforcePermission();
            if (!faceSensorConfigurations.hasSensorConfigurations()) {
                android.util.Slog.d(com.android.server.biometrics.sensors.face.FaceService.TAG, "No face sensors to register.");
            } else {
                com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.registerAll(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.util.List lambda$registerAuthenticatorsLegacy$1;
                        lambda$registerAuthenticatorsLegacy$1 = com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper.this.lambda$registerAuthenticatorsLegacy$1(faceSensorConfigurations);
                        return lambda$registerAuthenticatorsLegacy$1;
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: getProviders, reason: merged with bridge method [inline-methods] */
        public java.util.List<com.android.server.biometrics.sensors.face.ServiceProvider> lambda$registerAuthenticatorsLegacy$1(android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(com.android.server.biometrics.sensors.face.FaceService.this.mFaceProviderFunction.getFaceProvider(filterAvailableHalInstances(faceSensorConfigurations), faceSensorConfigurations.getResetLockoutRequiresChallenge()));
            return arrayList;
        }

        @android.annotation.NonNull
        private android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> filterAvailableHalInstances(android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) {
            android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> sensorPair = faceSensorConfigurations.getSensorPair();
            if (faceSensorConfigurations.isSingleSensorConfigurationPresent()) {
                return sensorPair;
            }
            android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> sensorPairForInstance = faceSensorConfigurations.getSensorPairForInstance("virtual");
            if (com.android.server.biometrics.Utils.isVirtualEnabled(com.android.server.biometrics.sensors.face.FaceService.this.getContext())) {
                if (sensorPairForInstance != null) {
                    return sensorPairForInstance;
                }
                android.util.Slog.e(com.android.server.biometrics.sensors.face.FaceService.TAG, "Could not find virtual interface while it is enabled");
                return sensorPair;
            }
            if (sensorPairForInstance != null) {
                return faceSensorConfigurations.getSensorPairNotForInstance("virtual");
            }
            return sensorPair;
        }

        private android.util.Pair<java.util.List<android.hardware.face.FaceSensorPropertiesInternal>, java.util.List<java.lang.String>> filterAvailableHalInstances(@android.annotation.NonNull java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list, @android.annotation.NonNull java.util.List<java.lang.String> list2) {
            if (list.size() + list2.size() <= 1) {
                return new android.util.Pair<>(list, list2);
            }
            com.android.server.biometrics.Flags.faceVhalFeature();
            android.util.Slog.i(com.android.server.biometrics.sensors.face.FaceService.TAG, "Face VHAL feature is off");
            int indexOf = list2.indexOf("virtual");
            com.android.server.biometrics.Flags.faceVhalFeature();
            java.util.ArrayList arrayList = new java.util.ArrayList(list2);
            if (indexOf != -1) {
                arrayList.remove(indexOf);
            }
            return new android.util.Pair<>(list, arrayList);
        }

        public void addAuthenticatorsRegisteredCallback(android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) {
            com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.face.FaceService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
            com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.addAllRegisteredCallback(iFaceAuthenticatorsRegisteredCallback);
        }

        public void registerBiometricStateListener(@android.annotation.NonNull android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) {
            com.android.server.biometrics.sensors.face.FaceService.this.mBiometricStateCallback.registerBiometricStateListener(iBiometricStateListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
            super.registerAuthenticationStateListener_enforcePermission();
            com.android.server.biometrics.sensors.face.FaceService.this.mAuthenticationStateListeners.registerAuthenticationStateListener(authenticationStateListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void unregisterAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
            super.unregisterAuthenticationStateListener_enforcePermission();
            com.android.server.biometrics.sensors.face.FaceService.this.mAuthenticationStateListeners.unregisterAuthenticationStateListener(authenticationStateListener);
        }
    }

    public FaceService(android.content.Context context) {
        this(context, null, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.hardware.biometrics.IBiometricService lambda$new$0;
                lambda$new$0 = com.android.server.biometrics.sensors.face.FaceService.lambda$new$0();
                return lambda$new$0;
            }
        }, null, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String[] lambda$new$1;
                lambda$new$1 = com.android.server.biometrics.sensors.face.FaceService.lambda$new$1();
                return lambda$new$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.IBiometricService lambda$new$0() {
        return android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$new$1() {
        return android.os.ServiceManager.getDeclaredInstances(android.hardware.biometrics.face.IFace.DESCRIPTOR);
    }

    @com.android.internal.annotations.VisibleForTesting
    FaceService(android.content.Context context, com.android.server.biometrics.sensors.face.FaceService.FaceProviderFunction faceProviderFunction, java.util.function.Supplier<android.hardware.biometrics.IBiometricService> supplier, java.util.function.Function<java.lang.String, com.android.server.biometrics.sensors.face.aidl.FaceProvider> function, java.util.function.Supplier<java.lang.String[]> supplier2) {
        super(context);
        this.mServiceWrapper = new com.android.server.biometrics.sensors.face.FaceService.FaceServiceWrapper();
        this.mLockoutResetDispatcher = new com.android.server.biometrics.sensors.LockoutResetDispatcher(context);
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(context);
        this.mBiometricStateCallback = new com.android.server.biometrics.sensors.BiometricStateCallback<>(android.os.UserManager.get(context));
        this.mAuthenticationStateListeners = new com.android.server.biometrics.sensors.AuthenticationStateListeners();
        this.mRegistry = new com.android.server.biometrics.sensors.face.FaceServiceRegistry(this.mServiceWrapper, supplier);
        this.mRegistry.addAllRegisteredCallback(new android.hardware.face.IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.biometrics.sensors.face.FaceService.1
            public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) {
                com.android.server.biometrics.sensors.face.FaceService.this.mBiometricStateCallback.start(com.android.server.biometrics.sensors.face.FaceService.this.mRegistry.getProviders());
            }
        });
        this.mAidlInstanceNameSupplier = supplier2;
        this.mFaceProvider = function == null ? new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider lambda$new$2;
                lambda$new$2 = com.android.server.biometrics.sensors.face.FaceService.this.lambda$new$2((java.lang.String) obj);
                return lambda$new$2;
            }
        } : function;
        com.android.server.biometrics.Flags.deHidl();
        this.mFaceProviderFunction = new com.android.server.biometrics.sensors.face.FaceService.FaceProviderFunction() { // from class: com.android.server.biometrics.sensors.face.FaceService$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.face.FaceService.FaceProviderFunction
            public final com.android.server.biometrics.sensors.face.aidl.FaceProvider getFaceProvider(android.util.Pair pair, boolean z) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider lambda$new$4;
                lambda$new$4 = com.android.server.biometrics.sensors.face.FaceService.lambda$new$4(pair, z);
                return lambda$new$4;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.biometrics.sensors.face.aidl.FaceProvider lambda$new$2(java.lang.String str) {
        java.lang.String str2 = android.hardware.biometrics.face.IFace.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
        android.hardware.biometrics.face.IFace asInterface = android.hardware.biometrics.face.IFace.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(str2)));
        if (asInterface == null) {
            android.util.Slog.e(TAG, "Unable to get declared service: " + str2);
            return null;
        }
        try {
            return new com.android.server.biometrics.sensors.face.aidl.FaceProvider(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, asInterface.getSensorProps(), str, this.mLockoutResetDispatcher, com.android.server.biometrics.log.BiometricContext.getInstance(getContext()), false);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception in getSensorProps: " + str2);
            return null;
        }
    }

    private /* synthetic */ com.android.server.biometrics.sensors.face.aidl.FaceProvider lambda$new$3(android.util.Pair pair, boolean z) {
        return new com.android.server.biometrics.sensors.face.aidl.FaceProvider(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, (android.hardware.biometrics.face.SensorProps[]) pair.second, (java.lang.String) pair.first, this.mLockoutResetDispatcher, com.android.server.biometrics.log.BiometricContext.getInstance(getContext()), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.biometrics.sensors.face.aidl.FaceProvider lambda$new$4(android.util.Pair pair, boolean z) {
        return null;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("face", this.mServiceWrapper);
    }

    void syncEnrollmentsNow() {
        com.android.server.biometrics.Utils.checkPermissionOrShell(getContext(), "android.permission.MANAGE_FACE");
        com.android.server.biometrics.Flags.faceVhalFeature();
    }

    void sendFaceReEnrollNotification() {
        com.android.server.biometrics.Utils.checkPermissionOrShell(getContext(), "android.permission.MANAGE_FACE");
        if (android.os.Build.IS_DEBUGGABLE) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.face.ServiceProvider> singleProvider = this.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    ((com.android.server.biometrics.sensors.face.aidl.FaceProvider) singleProvider.second).sendFaceReEnrollNotification();
                } else {
                    android.util.Slog.w(TAG, "Null provider for notification");
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }
}
