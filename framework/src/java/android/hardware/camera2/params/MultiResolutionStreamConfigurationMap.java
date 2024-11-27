package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class MultiResolutionStreamConfigurationMap {
    private final java.util.Map<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> mConfigurations;
    private final java.util.Map<java.lang.Integer, java.util.List<android.hardware.camera2.params.MultiResolutionStreamInfo>> mMultiResolutionOutputConfigs = new java.util.HashMap();
    private final java.util.Map<java.lang.Integer, java.util.List<android.hardware.camera2.params.MultiResolutionStreamInfo>> mMultiResolutionInputConfigs = new java.util.HashMap();

    public MultiResolutionStreamConfigurationMap(java.util.Map<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> map) {
        java.util.Map<java.lang.Integer, java.util.List<android.hardware.camera2.params.MultiResolutionStreamInfo>> map2;
        com.android.internal.util.Preconditions.checkNotNull(map, "multi-resolution configurations must not be null");
        if (map.size() == 0) {
            throw new java.lang.IllegalArgumentException("multi-resolution configurations must not be empty");
        }
        this.mConfigurations = map;
        for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> entry : this.mConfigurations.entrySet()) {
            java.lang.String key = entry.getKey();
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration : entry.getValue()) {
                int format = streamConfiguration.getFormat();
                android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo = new android.hardware.camera2.params.MultiResolutionStreamInfo(streamConfiguration.getWidth(), streamConfiguration.getHeight(), key);
                if (streamConfiguration.isInput()) {
                    map2 = this.mMultiResolutionInputConfigs;
                } else {
                    map2 = this.mMultiResolutionOutputConfigs;
                }
                if (!map2.containsKey(java.lang.Integer.valueOf(format))) {
                    map2.put(java.lang.Integer.valueOf(format), new java.util.ArrayList());
                }
                map2.get(java.lang.Integer.valueOf(format)).add(multiResolutionStreamInfo);
            }
        }
    }

    public static class SizeComparator implements java.util.Comparator<android.hardware.camera2.params.MultiResolutionStreamInfo> {
        @Override // java.util.Comparator
        public int compare(android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo, android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo2) {
            return android.hardware.camera2.params.StreamConfigurationMap.compareSizes(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), multiResolutionStreamInfo2.getWidth(), multiResolutionStreamInfo2.getHeight());
        }
    }

    public int[] getOutputFormats() {
        return getPublicImageFormats(true);
    }

    public int[] getInputFormats() {
        return getPublicImageFormats(false);
    }

    private int[] getPublicImageFormats(boolean z) {
        java.util.Map<java.lang.Integer, java.util.List<android.hardware.camera2.params.MultiResolutionStreamInfo>> map = z ? this.mMultiResolutionOutputConfigs : this.mMultiResolutionInputConfigs;
        int[] iArr = new int[map.size()];
        java.util.Iterator<java.lang.Integer> it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToPublic(it.next().intValue());
            i++;
        }
        return iArr;
    }

    public java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> getOutputInfo(int i) {
        return getInfo(i, true);
    }

    public java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> getInputInfo(int i) {
        return getInfo(i, false);
    }

    private java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> getInfo(int i, boolean z) {
        int imageFormatToInternal = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToInternal(i);
        java.util.Map<java.lang.Integer, java.util.List<android.hardware.camera2.params.MultiResolutionStreamInfo>> map = z ? this.mMultiResolutionOutputConfigs : this.mMultiResolutionInputConfigs;
        if (map.containsKey(java.lang.Integer.valueOf(imageFormatToInternal))) {
            return java.util.Collections.unmodifiableCollection(map.get(java.lang.Integer.valueOf(imageFormatToInternal)));
        }
        return java.util.Collections.emptyList();
    }

    private void appendConfigurationsString(java.lang.StringBuilder sb, boolean z) {
        sb.append(z ? "Outputs(" : "Inputs(");
        int[] publicImageFormats = getPublicImageFormats(z);
        if (publicImageFormats != null) {
            for (int i : publicImageFormats) {
                java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> info = getInfo(i, z);
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + android.hardware.camera2.params.StreamConfigurationMap.formatToString(i) + ":");
                for (android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo : info) {
                    sb.append(java.lang.String.format("[w:%d, h:%d, id:%s], ", java.lang.Integer.valueOf(multiResolutionStreamInfo.getWidth()), java.lang.Integer.valueOf(multiResolutionStreamInfo.getHeight()), multiResolutionStreamInfo.getPhysicalCameraId()));
                }
                if (sb.charAt(sb.length() - 1) == ' ') {
                    sb.delete(sb.length() - 2, sb.length());
                }
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.MultiResolutionStreamConfigurationMap)) {
            return false;
        }
        android.hardware.camera2.params.MultiResolutionStreamConfigurationMap multiResolutionStreamConfigurationMap = (android.hardware.camera2.params.MultiResolutionStreamConfigurationMap) obj;
        if (!this.mConfigurations.keySet().equals(multiResolutionStreamConfigurationMap.mConfigurations.keySet())) {
            return false;
        }
        for (java.lang.String str : this.mConfigurations.keySet()) {
            if (!java.util.Arrays.equals(this.mConfigurations.get(str), multiResolutionStreamConfigurationMap.mConfigurations.get(str))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCodeGeneric(this.mConfigurations, this.mMultiResolutionOutputConfigs, this.mMultiResolutionInputConfigs);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("MultiResolutionStreamConfigurationMap(");
        appendConfigurationsString(sb, true);
        sb.append(",");
        appendConfigurationsString(sb, false);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
