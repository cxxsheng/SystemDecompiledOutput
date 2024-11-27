package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RequestProcessor {
    private static final java.lang.String TAG = "RequestProcessor";
    private final android.hardware.camera2.extension.IRequestProcessorImpl mRequestProcessor;
    private final long mVendorId;

    public interface RequestCallback {
        void onCaptureBufferLost(android.hardware.camera2.extension.RequestProcessor.Request request, long j, int i);

        void onCaptureCompleted(android.hardware.camera2.extension.RequestProcessor.Request request, android.hardware.camera2.TotalCaptureResult totalCaptureResult);

        void onCaptureFailed(android.hardware.camera2.extension.RequestProcessor.Request request, android.hardware.camera2.CaptureFailure captureFailure);

        void onCaptureProgressed(android.hardware.camera2.extension.RequestProcessor.Request request, android.hardware.camera2.CaptureResult captureResult);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i, long j);

        void onCaptureStarted(android.hardware.camera2.extension.RequestProcessor.Request request, long j, long j2);
    }

    RequestProcessor(android.hardware.camera2.extension.IRequestProcessorImpl iRequestProcessorImpl, long j) {
        this.mRequestProcessor = iRequestProcessorImpl;
        this.mVendorId = j;
    }

    public static final class Request {
        private final java.util.List<java.lang.Integer> mOutputIds;
        private final java.util.List<android.util.Pair<android.hardware.camera2.CaptureRequest.Key, java.lang.Object>> mParameters;
        private final int mTemplateId;

        public Request(java.util.List<java.lang.Integer> list, java.util.List<android.util.Pair<android.hardware.camera2.CaptureRequest.Key, java.lang.Object>> list2, int i) {
            this.mOutputIds = list;
            this.mParameters = list2;
            this.mTemplateId = i;
        }

        java.util.List<java.lang.Integer> getOutputConfigIds() {
            return this.mOutputIds;
        }

        public java.util.List<android.util.Pair<android.hardware.camera2.CaptureRequest.Key, java.lang.Object>> getParameters() {
            return this.mParameters;
        }

        java.lang.Integer getTemplateId() {
            return java.lang.Integer.valueOf(this.mTemplateId);
        }

        java.util.List<android.hardware.camera2.extension.OutputConfigId> getTargetIds() {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mOutputIds.size());
            int i = 0;
            for (java.lang.Integer num : this.mOutputIds) {
                android.hardware.camera2.extension.OutputConfigId outputConfigId = new android.hardware.camera2.extension.OutputConfigId();
                outputConfigId.id = num.intValue();
                arrayList.add(i, outputConfigId);
                i++;
            }
            return arrayList;
        }

        static android.hardware.camera2.impl.CameraMetadataNative getParametersMetadata(long j, java.util.List<android.util.Pair<android.hardware.camera2.CaptureRequest.Key, java.lang.Object>> list) {
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
            cameraMetadataNative.setVendorId(j);
            for (android.util.Pair<android.hardware.camera2.CaptureRequest.Key, java.lang.Object> pair : list) {
                cameraMetadataNative.set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key>) pair.first, (android.hardware.camera2.CaptureRequest.Key) pair.second);
            }
            return cameraMetadataNative;
        }

        static java.util.List<android.hardware.camera2.extension.Request> initializeParcelable(long j, java.util.List<android.hardware.camera2.extension.RequestProcessor.Request> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
            int i = 0;
            for (android.hardware.camera2.extension.RequestProcessor.Request request : list) {
                android.hardware.camera2.extension.Request request2 = new android.hardware.camera2.extension.Request();
                request2.requestId = i;
                request2.templateId = request.getTemplateId().intValue();
                request2.targetOutputConfigIds = request.getTargetIds();
                request2.parameters = getParametersMetadata(j, request.getParameters());
                arrayList.add(request2.requestId, request2);
                i++;
            }
            return arrayList;
        }
    }

    public int submit(android.hardware.camera2.extension.RequestProcessor.Request request, java.util.concurrent.Executor executor, android.hardware.camera2.extension.RequestProcessor.RequestCallback requestCallback) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        arrayList.add(0, request);
        try {
            int submit = this.mRequestProcessor.submit(android.hardware.camera2.extension.RequestProcessor.Request.initializeParcelable(this.mVendorId, arrayList).get(0), new android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl(arrayList, requestCallback, executor));
            if (submit == -1) {
                throw new android.hardware.camera2.CameraAccessException(3, "Failed to submit capture request");
            }
            return submit;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public int submitBurst(java.util.List<android.hardware.camera2.extension.RequestProcessor.Request> list, java.util.concurrent.Executor executor, android.hardware.camera2.extension.RequestProcessor.RequestCallback requestCallback) throws android.hardware.camera2.CameraAccessException {
        try {
            int submitBurst = this.mRequestProcessor.submitBurst(android.hardware.camera2.extension.RequestProcessor.Request.initializeParcelable(this.mVendorId, list), new android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl(list, requestCallback, executor));
            if (submitBurst == -1) {
                throw new android.hardware.camera2.CameraAccessException(3, "Failed to submit burst request");
            }
            return submitBurst;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public int setRepeating(android.hardware.camera2.extension.RequestProcessor.Request request, java.util.concurrent.Executor executor, android.hardware.camera2.extension.RequestProcessor.RequestCallback requestCallback) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        arrayList.add(0, request);
        try {
            int repeating = this.mRequestProcessor.setRepeating(android.hardware.camera2.extension.RequestProcessor.Request.initializeParcelable(this.mVendorId, arrayList).get(0), new android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl(arrayList, requestCallback, executor));
            if (repeating == -1) {
                throw new android.hardware.camera2.CameraAccessException(3, "Failed to set the repeating request");
            }
            return repeating;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void abortCaptures() {
        try {
            this.mRequestProcessor.abortCaptures();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void stopRepeating() {
        try {
            this.mRequestProcessor.stopRepeating();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RequestCallbackImpl extends android.hardware.camera2.extension.IRequestCallback.Stub {
        private final android.hardware.camera2.extension.RequestProcessor.RequestCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.List<android.hardware.camera2.extension.RequestProcessor.Request> mRequests;

        public RequestCallbackImpl(java.util.List<android.hardware.camera2.extension.RequestProcessor.Request> list, android.hardware.camera2.extension.RequestProcessor.RequestCallback requestCallback, java.util.concurrent.Executor executor) {
            this.mCallback = requestCallback;
            this.mRequests = list;
            this.mExecutor = executor;
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureStarted(final int i, final long j, final long j2) {
            if (this.mRequests.get(i) != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureStarted$0(i, j, j2);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.extension.RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(int i, long j, long j2) {
            this.mCallback.onCaptureStarted(this.mRequests.get(i), j, j2);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureProgressed(final int i, android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult) {
            if (this.mRequests.get(i) != null) {
                final android.hardware.camera2.CaptureResult captureResult = new android.hardware.camera2.CaptureResult(parcelCaptureResult.cameraId, parcelCaptureResult.results, parcelCaptureResult.parent, parcelCaptureResult.sequenceId, parcelCaptureResult.frameNumber);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureProgressed$1(i, captureResult);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.extension.RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProgressed$1(int i, android.hardware.camera2.CaptureResult captureResult) {
            this.mCallback.onCaptureProgressed(this.mRequests.get(i), captureResult);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureCompleted(final int i, android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult) {
            android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr;
            if (this.mRequests.get(i) != null) {
                android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr2 = new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0];
                if (parcelTotalCaptureResult.physicalResult != null && !parcelTotalCaptureResult.physicalResult.isEmpty()) {
                    physicalCaptureResultInfoArr = (android.hardware.camera2.impl.PhysicalCaptureResultInfo[]) parcelTotalCaptureResult.physicalResult.toArray(new android.hardware.camera2.impl.PhysicalCaptureResultInfo[parcelTotalCaptureResult.physicalResult.size()]);
                } else {
                    physicalCaptureResultInfoArr = physicalCaptureResultInfoArr2;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(parcelTotalCaptureResult.partials.size());
                for (android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult : parcelTotalCaptureResult.partials) {
                    arrayList.add(new android.hardware.camera2.CaptureResult(parcelCaptureResult.cameraId, parcelCaptureResult.results, parcelCaptureResult.parent, parcelCaptureResult.sequenceId, parcelCaptureResult.frameNumber));
                }
                final android.hardware.camera2.TotalCaptureResult totalCaptureResult = new android.hardware.camera2.TotalCaptureResult(parcelTotalCaptureResult.logicalCameraId, parcelTotalCaptureResult.results, parcelTotalCaptureResult.parent, parcelTotalCaptureResult.sequenceId, parcelTotalCaptureResult.frameNumber, arrayList, parcelTotalCaptureResult.sessionId, physicalCaptureResultInfoArr);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureCompleted$2(i, totalCaptureResult);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.extension.RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$2(int i, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            this.mCallback.onCaptureCompleted(this.mRequests.get(i), totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureFailed(final int i, android.hardware.camera2.extension.CaptureFailure captureFailure) {
            if (this.mRequests.get(i) != null) {
                final android.hardware.camera2.CaptureFailure captureFailure2 = new android.hardware.camera2.CaptureFailure(captureFailure.request, captureFailure.reason, captureFailure.dropped, captureFailure.sequenceId, captureFailure.frameNumber, captureFailure.errorPhysicalCameraId);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureFailed$3(i, captureFailure2);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.extension.RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3(int i, android.hardware.camera2.CaptureFailure captureFailure) {
            this.mCallback.onCaptureFailed(this.mRequests.get(i), captureFailure);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureBufferLost(final int i, final long j, final int i2) {
            if (this.mRequests.get(i) != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureBufferLost$4(i, j, i2);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.extension.RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureBufferLost$4(int i, long j, int i2) {
            this.mCallback.onCaptureBufferLost(this.mRequests.get(i), j, i2);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceCompleted(final int i, final long j) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureSequenceCompleted$5(i, j);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$5(int i, long j) {
            this.mCallback.onCaptureSequenceCompleted(i, j);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceAborted(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.extension.RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureSequenceAborted$6(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$6(int i) {
            this.mCallback.onCaptureSequenceAborted(i);
        }
    }
}
