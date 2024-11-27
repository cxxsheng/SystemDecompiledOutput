package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class AdvancedExtender {
    private static final java.lang.String TAG = "AdvancedExtender";
    private final android.hardware.camera2.CameraManager mCameraManager;
    private android.hardware.camera2.extension.CameraUsageTracker mCameraUsageTracker;
    private java.util.HashMap<java.lang.String, java.lang.Long> mMetadataVendorIdMap = new java.util.HashMap<>();

    public abstract java.util.List<android.hardware.camera2.CaptureRequest.Key> getAvailableCaptureRequestKeys(java.lang.String str);

    public abstract java.util.List<android.hardware.camera2.CaptureResult.Key> getAvailableCaptureResultKeys(java.lang.String str);

    public abstract java.util.List<android.util.Pair<android.hardware.camera2.CameraCharacteristics.Key, java.lang.Object>> getAvailableCharacteristicsKeyValues();

    public abstract android.hardware.camera2.extension.SessionProcessor getSessionProcessor();

    public abstract java.util.Map<java.lang.Integer, java.util.List<android.util.Size>> getSupportedCaptureOutputResolutions(java.lang.String str);

    public abstract java.util.Map<java.lang.Integer, java.util.List<android.util.Size>> getSupportedPreviewOutputResolutions(java.lang.String str);

    public abstract void initialize(java.lang.String str, android.hardware.camera2.extension.CharacteristicsMap characteristicsMap);

    public abstract boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.extension.CharacteristicsMap characteristicsMap);

    public AdvancedExtender(android.hardware.camera2.CameraManager cameraManager) {
        this.mCameraManager = cameraManager;
        try {
            java.lang.String[] cameraIdListNoLazy = this.mCameraManager.getCameraIdListNoLazy();
            if (cameraIdListNoLazy != null) {
                for (java.lang.String str : cameraIdListNoLazy) {
                    java.util.ArrayList allVendorKeys = this.mCameraManager.getCameraCharacteristics(str).getNativeMetadata().getAllVendorKeys(android.hardware.camera2.CameraCharacteristics.Key.class);
                    if (allVendorKeys != null && !allVendorKeys.isEmpty()) {
                        this.mMetadataVendorIdMap.put(str, java.lang.Long.valueOf(((android.hardware.camera2.CameraCharacteristics.Key) allVendorKeys.get(0)).getVendorId()));
                    }
                }
            }
        } catch (android.hardware.camera2.CameraAccessException e) {
            android.util.Log.e(TAG, "Failed to query camera characteristics!");
        }
    }

    void setCameraUsageTracker(android.hardware.camera2.extension.CameraUsageTracker cameraUsageTracker) {
        this.mCameraUsageTracker = cameraUsageTracker;
    }

    public long getMetadataVendorId(java.lang.String str) {
        if (this.mMetadataVendorIdMap.containsKey(str)) {
            return this.mMetadataVendorIdMap.get(str).longValue();
        }
        return Long.MAX_VALUE;
    }

    private final class AdvancedExtenderImpl extends android.hardware.camera2.extension.IAdvancedExtenderImpl.Stub {
        private AdvancedExtenderImpl() {
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
            return android.hardware.camera2.extension.AdvancedExtender.this.isExtensionAvailable(str, new android.hardware.camera2.extension.CharacteristicsMap(map));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
            android.hardware.camera2.extension.AdvancedExtender.this.initialize(str, new android.hardware.camera2.extension.CharacteristicsMap(map));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPreviewOutputResolutions(java.lang.String str) {
            return android.hardware.camera2.extension.AdvancedExtender.initializeParcelable(android.hardware.camera2.extension.AdvancedExtender.this.getSupportedPreviewOutputResolutions(str));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedCaptureOutputResolutions(java.lang.String str) {
            return android.hardware.camera2.extension.AdvancedExtender.initializeParcelable(android.hardware.camera2.extension.AdvancedExtender.this.getSupportedCaptureOutputResolutions(str));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(java.lang.String str, android.hardware.camera2.extension.Size size, int i) {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.extension.ISessionProcessorImpl getSessionProcessor() {
            android.hardware.camera2.extension.SessionProcessor sessionProcessor = android.hardware.camera2.extension.AdvancedExtender.this.getSessionProcessor();
            sessionProcessor.setCameraUsageTracker(android.hardware.camera2.extension.AdvancedExtender.this.mCameraUsageTracker);
            return sessionProcessor.getSessionProcessorBinder();
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys(java.lang.String str) {
            java.util.List<android.hardware.camera2.CaptureRequest.Key> availableCaptureRequestKeys = android.hardware.camera2.extension.AdvancedExtender.this.getAvailableCaptureRequestKeys(str);
            if (!availableCaptureRequestKeys.isEmpty()) {
                android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
                long metadataVendorId = android.hardware.camera2.extension.AdvancedExtender.this.getMetadataVendorId(str);
                cameraMetadataNative.setVendorId(metadataVendorId);
                int[] iArr = new int[availableCaptureRequestKeys.size()];
                java.util.Iterator<android.hardware.camera2.CaptureRequest.Key> it = availableCaptureRequestKeys.iterator();
                int i = 0;
                while (it.hasNext()) {
                    iArr[i] = android.hardware.camera2.impl.CameraMetadataNative.getTag(it.next().getName(), metadataVendorId);
                    i++;
                }
                cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<int[]>>) android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS, (android.hardware.camera2.CameraCharacteristics.Key<int[]>) iArr);
                return cameraMetadataNative;
            }
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys(java.lang.String str) {
            java.util.List<android.hardware.camera2.CaptureResult.Key> availableCaptureResultKeys = android.hardware.camera2.extension.AdvancedExtender.this.getAvailableCaptureResultKeys(str);
            if (!availableCaptureResultKeys.isEmpty()) {
                android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
                long metadataVendorId = android.hardware.camera2.extension.AdvancedExtender.this.getMetadataVendorId(str);
                cameraMetadataNative.setVendorId(metadataVendorId);
                int[] iArr = new int[availableCaptureResultKeys.size()];
                java.util.Iterator<android.hardware.camera2.CaptureResult.Key> it = availableCaptureResultKeys.iterator();
                int i = 0;
                while (it.hasNext()) {
                    iArr[i] = android.hardware.camera2.impl.CameraMetadataNative.getTag(it.next().getName(), metadataVendorId);
                    i++;
                }
                cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<int[]>>) android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS, (android.hardware.camera2.CameraCharacteristics.Key<int[]>) iArr);
                return cameraMetadataNative;
            }
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isCaptureProcessProgressAvailable() {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isPostviewAvailable() {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCharacteristicsKeyValues(java.lang.String str) {
            java.util.List<android.util.Pair<android.hardware.camera2.CameraCharacteristics.Key, java.lang.Object>> availableCharacteristicsKeyValues = android.hardware.camera2.extension.AdvancedExtender.this.getAvailableCharacteristicsKeyValues();
            if (availableCharacteristicsKeyValues != null && !availableCharacteristicsKeyValues.isEmpty()) {
                android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
                long longValue = android.hardware.camera2.extension.AdvancedExtender.this.mMetadataVendorIdMap.containsKey(str) ? ((java.lang.Long) android.hardware.camera2.extension.AdvancedExtender.this.mMetadataVendorIdMap.get(str)).longValue() : Long.MAX_VALUE;
                cameraMetadataNative.setVendorId(longValue);
                int[] iArr = new int[availableCharacteristicsKeyValues.size()];
                int i = 0;
                for (android.util.Pair<android.hardware.camera2.CameraCharacteristics.Key, java.lang.Object> pair : availableCharacteristicsKeyValues) {
                    iArr[i] = android.hardware.camera2.impl.CameraMetadataNative.getTag(pair.first.getName(), longValue);
                    cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key>) pair.first, (android.hardware.camera2.CameraCharacteristics.Key) pair.second);
                    i++;
                }
                cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<int[]>>) android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS, (android.hardware.camera2.CameraCharacteristics.Key<int[]>) iArr);
                return cameraMetadataNative;
            }
            return null;
        }
    }

    android.hardware.camera2.extension.IAdvancedExtenderImpl getAdvancedExtenderBinder() {
        return new android.hardware.camera2.extension.AdvancedExtender.AdvancedExtenderImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.hardware.camera2.extension.SizeList> initializeParcelable(java.util.Map<java.lang.Integer, java.util.List<android.util.Size>> map) {
        if (map == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(map.size());
        for (java.util.Map.Entry<java.lang.Integer, java.util.List<android.util.Size>> entry : map.entrySet()) {
            android.hardware.camera2.extension.SizeList sizeList = new android.hardware.camera2.extension.SizeList();
            sizeList.format = entry.getKey().intValue();
            sizeList.sizes = new java.util.ArrayList();
            for (android.util.Size size : entry.getValue()) {
                android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
                size2.width = size.getWidth();
                size2.height = size.getHeight();
                sizeList.sizes.add(size2);
            }
            arrayList.add(sizeList);
        }
        return arrayList;
    }
}
