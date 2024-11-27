package android.hardware.usb.flags;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.hardware.usb.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING, false), java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API, false), java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API, false), java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING, false), java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING, false), java.util.Map.entry(android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.hardware.usb.flags.Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING, android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API, android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API, android.hardware.usb.flags.Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING, android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING, android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableInputPowerLimitedWarning() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_INPUT_POWER_LIMITED_WARNING);
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableIsModeChangeSupportedApi() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_MODE_CHANGE_SUPPORTED_API);
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableIsPdCompliantApi() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_IS_PD_COMPLIANT_API);
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableReportUsbDataComplianceWarning() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_REPORT_USB_DATA_COMPLIANCE_WARNING);
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataComplianceWarning() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_COMPLIANCE_WARNING);
    }

    @Override // android.hardware.usb.flags.FeatureFlags
    public boolean enableUsbDataSignalStaking() {
        return getValue(android.hardware.usb.flags.Flags.FLAG_ENABLE_USB_DATA_SIGNAL_STAKING);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
