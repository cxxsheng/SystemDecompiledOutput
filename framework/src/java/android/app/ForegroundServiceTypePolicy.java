package android.app;

/* loaded from: classes.dex */
public abstract class ForegroundServiceTypePolicy {
    static final boolean DEBUG_FOREGROUND_SERVICE_TYPE_POLICY = false;
    private static final boolean DEFAULT_FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG_VALUE = true;
    private static final java.lang.String FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG = "fgs_type_fg_perm_enforcement_flag";
    public static final long FGS_TYPE_PERMISSION_CHANGE_ID = 254662522;
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_PREFIX = "fgs_type_perm_enforcement_flag_";
    public static final int FGS_TYPE_POLICY_CHECK_DEPRECATED = 2;
    public static final int FGS_TYPE_POLICY_CHECK_DISABLED = 3;
    public static final int FGS_TYPE_POLICY_CHECK_OK = 1;
    public static final int FGS_TYPE_POLICY_CHECK_PERMISSION_DENIED_ENFORCED = 5;
    public static final int FGS_TYPE_POLICY_CHECK_PERMISSION_DENIED_PERMISSIVE = 4;
    public static final int FGS_TYPE_POLICY_CHECK_UNKNOWN = 0;
    static final java.lang.String TAG = "ForegroundServiceTypePolicy";
    public static final long FGS_TYPE_NONE_DEPRECATION_CHANGE_ID = 255042465;
    public static final long FGS_TYPE_NONE_DISABLED_CHANGE_ID = 255038118;
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MANIFEST = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(-1, FGS_TYPE_NONE_DEPRECATION_CHANGE_ID, FGS_TYPE_NONE_DISABLED_CHANGE_ID, null, null, null, false, false);
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_NONE = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(0, FGS_TYPE_NONE_DEPRECATION_CHANGE_ID, FGS_TYPE_NONE_DISABLED_CHANGE_ID, null, null, null, false, false);
    public static final long FGS_TYPE_DATA_SYNC_DEPRECATION_CHANGE_ID = 255039210;
    public static final long FGS_TYPE_DATA_SYNC_DISABLED_CHANGE_ID = 255659651;
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_DATA_SYNC = "fgs_type_perm_enforcement_flag_data_sync";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_DATA_SYNC = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(1, FGS_TYPE_DATA_SYNC_DEPRECATION_CHANGE_ID, FGS_TYPE_DATA_SYNC_DISABLED_CHANGE_ID, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_DATA_SYNC, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PLAYBACK = "fgs_type_perm_enforcement_flag_media_playback";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PLAYBACK = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(2, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PLAYBACK, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_PHONE_CALL = "fgs_type_perm_enforcement_flag_phone_call";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_PHONE_CALL = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(4, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_PHONE_CALL)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.MANAGE_OWN_CALLS), new android.app.ForegroundServiceTypePolicy.RolePermission("android.app.role.DIALER")}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_PHONE_CALL, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_LOCATION = "fgs_type_perm_enforcement_flag_location";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_LOCATION = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(8, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_LOCATION)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_LOCATION, true, true);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_CONNECTED_DEVICE = "fgs_type_perm_enforcement_flag_connected_device";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_CONNECTED_DEVICE = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(16, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.BLUETOOTH_ADVERTISE), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.BLUETOOTH_CONNECT), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.BLUETOOTH_SCAN), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CHANGE_NETWORK_STATE), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CHANGE_WIFI_STATE), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.NFC), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.TRANSMIT_IR), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.UWB_RANGING), new android.app.ForegroundServiceTypePolicy.UsbDevicePermission(), new android.app.ForegroundServiceTypePolicy.UsbAccessoryPermission()}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_CONNECTED_DEVICE, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PROJECTION = "fgs_type_perm_enforcement_flag_media_projection";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PROJECTION = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(32, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_VIDEO_OUTPUT), new android.app.ForegroundServiceTypePolicy.AppOpPermission(46)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PROJECTION, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_CAMERA = "fgs_type_perm_enforcement_flag_camera";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_CAMERA = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(64, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_CAMERA)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAMERA), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.SYSTEM_CAMERA)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_CAMERA, true, true);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MICROPHONE = "fgs_type_perm_enforcement_flag_microphone";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MICROPHONE = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(128, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_AUDIO_HOTWORD), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_AUDIO_OUTPUT), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_MEDIA_OUTPUT), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_TUNER_AUDIO_INPUT), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.RECORD_AUDIO)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_MICROPHONE, true, true);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_HEALTH = "fgs_type_perm_enforcement_flag_health";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_HEALTH = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(256, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_HEALTH)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.ACTIVITY_RECOGNITION), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.BODY_SENSORS), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.HIGH_SAMPLING_RATE_SENSORS)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_HEALTH, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_REMOTE_MESSAGING = "fgs_type_perm_enforcement_flag_remote_messaging";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_REMOTE_MESSAGING = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(512, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_REMOTE_MESSAGING, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_SYSTEM_EXEMPTED = "fgs_type_perm_enforcement_flag_system_exempted";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SYSTEM_EXEMPTED = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(1024, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_SYSTEM_EXEMPTED)}, true), new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM), new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.USE_EXACT_ALARM), new android.app.ForegroundServiceTypePolicy.AppOpPermission(47)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_SYSTEM_EXEMPTED, true, false);
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SHORT_SERVICE = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(2048, 0, 0, null, null, null, false, false);
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_FILE_MANAGEMENT = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(4096, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_FILE_MANAGEMENT)}, true), null, null, false, false);
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PROCESSING = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(8192, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROCESSING)}, true), null, null, true, false);
    private static final java.lang.String FGS_TYPE_PERM_ENFORCEMENT_FLAG_SPECIAL_USE = "fgs_type_perm_enforcement_flag_special_use";
    public static final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SPECIAL_USE = new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo(1073741824, 0, 0, new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions(new android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[]{new android.app.ForegroundServiceTypePolicy.RegularPermission(android.Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_SPECIAL_USE, true, false);
    private static android.app.ForegroundServiceTypePolicy sDefaultForegroundServiceTypePolicy = null;
    private static final java.lang.Object sLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForegroundServicePolicyCheckCode {
    }

    public abstract int checkForegroundServiceTypePolicy(android.content.Context context, java.lang.String str, int i, int i2, boolean z, android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo);

    public abstract android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo getForegroundServiceTypePolicyInfo(int i, int i2);

    public abstract void updatePermissionEnforcementFlagIfNecessary(java.lang.String str);

    public static android.app.ForegroundServiceTypePolicy getDefaultPolicy() {
        android.app.ForegroundServiceTypePolicy foregroundServiceTypePolicy;
        synchronized (sLock) {
            if (sDefaultForegroundServiceTypePolicy == null) {
                sDefaultForegroundServiceTypePolicy = new android.app.ForegroundServiceTypePolicy.DefaultForegroundServiceTypePolicy();
            }
            foregroundServiceTypePolicy = sDefaultForegroundServiceTypePolicy;
        }
        return foregroundServiceTypePolicy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFgsTypeFgPermissionEnforcementEnabled() {
        return android.provider.DeviceConfig.getBoolean("activity_manager", FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG, true);
    }

    public static final class ForegroundServiceTypePolicyInfo {
        private static final long INVALID_CHANGE_ID = 0;
        final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions mAllOfPermissions;
        final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions mAnyOfPermissions;
        android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission mCustomPermission;
        final long mDeprecationChangeId;
        final long mDisabledChangeId;
        final boolean mForegroundOnlyPermission;
        final java.lang.String mPermissionEnforcementFlag;
        final boolean mPermissionEnforcementFlagDefaultValue;
        volatile boolean mPermissionEnforcementFlagValue;
        final int mType;

        private static boolean isValidChangeId(long j) {
            return j != 0;
        }

        public ForegroundServiceTypePolicyInfo(int i, long j, long j2, android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions foregroundServiceTypePermissions, android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermissions foregroundServiceTypePermissions2, java.lang.String str, boolean z, boolean z2) {
            this.mType = i;
            this.mDeprecationChangeId = j;
            this.mDisabledChangeId = j2;
            this.mAllOfPermissions = foregroundServiceTypePermissions;
            this.mAnyOfPermissions = foregroundServiceTypePermissions2;
            this.mPermissionEnforcementFlag = str;
            this.mPermissionEnforcementFlagDefaultValue = z;
            this.mPermissionEnforcementFlagValue = z;
            this.mForegroundOnlyPermission = z2;
        }

        public int getForegroundServiceType() {
            return this.mType;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder permissionString = toPermissionString(new java.lang.StringBuilder());
            permissionString.append("type=0x");
            permissionString.append(java.lang.Integer.toHexString(this.mType));
            permissionString.append(" deprecationChangeId=");
            permissionString.append(this.mDeprecationChangeId);
            permissionString.append(" disabledChangeId=");
            permissionString.append(this.mDisabledChangeId);
            permissionString.append(" customPermission=");
            permissionString.append(this.mCustomPermission);
            return permissionString.toString();
        }

        public java.lang.String toPermissionString() {
            return toPermissionString(new java.lang.StringBuilder()).toString();
        }

        private java.lang.StringBuilder toPermissionString(java.lang.StringBuilder sb) {
            if (this.mAllOfPermissions != null) {
                sb.append("all of the permissions ");
                sb.append(this.mAllOfPermissions.toString());
                sb.append(' ');
            }
            if (this.mAnyOfPermissions != null) {
                sb.append("any of the permissions ");
                sb.append(this.mAnyOfPermissions.toString());
                sb.append(' ');
            }
            return sb;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePermissionEnforcementFlagIfNecessary(java.lang.String str) {
            if (this.mPermissionEnforcementFlag == null || !android.text.TextUtils.equals(str, this.mPermissionEnforcementFlag)) {
                return;
            }
            this.mPermissionEnforcementFlagValue = android.provider.DeviceConfig.getBoolean("activity_manager", this.mPermissionEnforcementFlag, this.mPermissionEnforcementFlagDefaultValue);
        }

        public void setCustomPermission(android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission foregroundServiceTypePermission) {
            this.mCustomPermission = foregroundServiceTypePermission;
        }

        public java.util.Optional<java.lang.String[]> getRequiredAllOfPermissionsForTest(android.content.Context context) {
            if (this.mAllOfPermissions == null) {
                return java.util.Optional.empty();
            }
            return java.util.Optional.of(this.mAllOfPermissions.toStringArray(context));
        }

        public java.util.Optional<java.lang.String[]> getRequiredAnyOfPermissionsForTest(android.content.Context context) {
            if (this.mAnyOfPermissions == null) {
                return java.util.Optional.empty();
            }
            return java.util.Optional.of(this.mAnyOfPermissions.toStringArray(context));
        }

        public boolean isTypeDisabled(int i) {
            return isValidChangeId(this.mDisabledChangeId) && android.app.compat.CompatChanges.isChangeEnabled(this.mDisabledChangeId, i);
        }

        public boolean hasForegroundOnlyPermission() {
            return this.mForegroundOnlyPermission;
        }

        public void setTypeDisabledForTest(boolean z, java.lang.String str) throws android.os.RemoteException {
            overrideChangeIdForTest(this.mDisabledChangeId, z, str);
        }

        public void clearTypeDisabledForTest(java.lang.String str) throws android.os.RemoteException {
            clearOverrideForTest(this.mDisabledChangeId, str);
        }

        boolean isTypeDeprecated(int i) {
            return isValidChangeId(this.mDeprecationChangeId) && android.app.compat.CompatChanges.isChangeEnabled(this.mDeprecationChangeId, i);
        }

        private void overrideChangeIdForTest(long j, boolean z, java.lang.String str) throws android.os.RemoteException {
            if (!isValidChangeId(j)) {
                return;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            if (z) {
                arraySet.add(java.lang.Long.valueOf(j));
            } else {
                arraySet2.add(java.lang.Long.valueOf(j));
            }
            com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE)).setOverridesForTest(new com.android.internal.compat.CompatibilityChangeConfig(new android.compat.Compatibility.ChangeConfig(arraySet, arraySet2)), str);
        }

        private void clearOverrideForTest(long j, java.lang.String str) throws android.os.RemoteException {
            com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE)).clearOverrideForTest(j, str);
        }

        public java.lang.String getPermissionEnforcementFlagForTest() {
            return this.mPermissionEnforcementFlag;
        }
    }

    public static class ForegroundServiceTypePermissions {
        final boolean mAllOf;
        final android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[] mPermissions;

        public ForegroundServiceTypePermissions(android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[] foregroundServiceTypePermissionArr, boolean z) {
            this.mPermissions = foregroundServiceTypePermissionArr;
            this.mAllOf = z;
        }

        public int checkPermissions(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            boolean z2;
            if (this.mAllOf) {
                for (android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission foregroundServiceTypePermission : this.mPermissions) {
                    if (foregroundServiceTypePermission.checkPermission(context, i, i2, str, z) != 0) {
                        return -1;
                    }
                }
                return 0;
            }
            android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission[] foregroundServiceTypePermissionArr = this.mPermissions;
            int length = foregroundServiceTypePermissionArr.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    z2 = false;
                    break;
                }
                if (foregroundServiceTypePermissionArr[i3].checkPermission(context, i, i2, str, z) != 0) {
                    i3++;
                } else {
                    z2 = true;
                    break;
                }
            }
            return z2 ? 0 : -1;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("allOf=");
            sb.append(this.mAllOf);
            sb.append(' ');
            sb.append('[');
            for (int i = 0; i < this.mPermissions.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mPermissions[i].toString());
            }
            sb.append(']');
            return sb.toString();
        }

        java.lang.String[] toStringArray(android.content.Context context) {
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
            for (int i = 0; i < this.mPermissions.length; i++) {
                this.mPermissions[i].addToList(context, arrayList);
            }
            return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
        }
    }

    public static abstract class ForegroundServiceTypePermission {
        protected final java.lang.String mName;

        public abstract int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z);

        public ForegroundServiceTypePermission(java.lang.String str) {
            this.mName = str;
        }

        public java.lang.String toString() {
            return this.mName;
        }

        void addToList(android.content.Context context, java.util.ArrayList<java.lang.String> arrayList) {
            arrayList.add(this.mName);
        }
    }

    static class RegularPermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        RegularPermission(java.lang.String str) {
            super(str);
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            return checkPermission(context, this.mName, i, i2, str, z);
        }

        int checkPermission(android.content.Context context, java.lang.String str, int i, int i2, java.lang.String str2, boolean z) {
            int checkPermissionForPreflight = android.content.PermissionChecker.checkPermissionForPreflight(context, str, i2, i, str2);
            if (checkPermissionForPreflight == 2) {
                return -1;
            }
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str);
            if (permissionToOpCode == -1) {
                return checkPermissionForPreflight == 0 ? 0 : -1;
            }
            switch (((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class)).unsafeCheckOpRawNoThrow(permissionToOpCode, i, str2)) {
                case 0:
                    return 0;
                case 1:
                    return (z && checkPermissionForPreflight == 1) ? 0 : -1;
                case 2:
                default:
                    return -1;
                case 3:
                    return checkPermissionForPreflight == 0 ? 0 : -1;
                case 4:
                    return (!android.app.ForegroundServiceTypePolicy.isFgsTypeFgPermissionEnforcementEnabled() || z) ? 0 : -1;
            }
        }
    }

    static class AppOpPermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        final int mOpCode;

        AppOpPermission(int i) {
            super(android.app.AppOpsManager.opToPublicName(i));
            this.mOpCode = i;
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            int unsafeCheckOpRawNoThrow = ((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class)).unsafeCheckOpRawNoThrow(this.mOpCode, i, str);
            return (unsafeCheckOpRawNoThrow == 0 || (z && unsafeCheckOpRawNoThrow == 4)) ? 0 : -1;
        }
    }

    static class RolePermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        final java.lang.String mRole;

        RolePermission(java.lang.String str) {
            super(str);
            this.mRole = str;
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            java.util.List roleHoldersAsUser = ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getRoleHoldersAsUser(this.mRole, android.os.UserHandle.getUserHandleForUid(i));
            return (roleHoldersAsUser == null || !roleHoldersAsUser.contains(str)) ? -1 : 0;
        }
    }

    static class UsbDevicePermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        UsbDevicePermission() {
            super("USB Device");
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) context.getSystemService(android.hardware.usb.UsbManager.class);
            java.util.HashMap<java.lang.String, android.hardware.usb.UsbDevice> deviceList = usbManager.getDeviceList();
            if (!com.android.internal.util.ArrayUtils.isEmpty(deviceList)) {
                java.util.Iterator<android.hardware.usb.UsbDevice> it = deviceList.values().iterator();
                while (it.hasNext()) {
                    if (usbManager.hasPermission(it.next(), str, i2, i)) {
                        return 0;
                    }
                }
                return -1;
            }
            return -1;
        }
    }

    static class UsbAccessoryPermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        UsbAccessoryPermission() {
            super("USB Accessory");
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(android.content.Context context, int i, int i2, java.lang.String str, boolean z) {
            android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) context.getSystemService(android.hardware.usb.UsbManager.class);
            android.hardware.usb.UsbAccessory[] accessoryList = usbManager.getAccessoryList();
            if (!com.android.internal.util.ArrayUtils.isEmpty(accessoryList)) {
                for (android.hardware.usb.UsbAccessory usbAccessory : accessoryList) {
                    if (usbManager.hasPermission(usbAccessory, i2, i)) {
                        return 0;
                    }
                }
                return -1;
            }
            return -1;
        }
    }

    public static class DefaultForegroundServiceTypePolicy extends android.app.ForegroundServiceTypePolicy {
        private final android.util.SparseArray<android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo> mForegroundServiceTypePolicies = new android.util.SparseArray<>();
        private final android.util.ArrayMap<java.lang.String, android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo> mPermissionEnforcementToPolicyInfoMap = new android.util.ArrayMap<>();

        public DefaultForegroundServiceTypePolicy() {
            this.mForegroundServiceTypePolicies.put(-1, FGS_TYPE_POLICY_MANIFEST);
            this.mForegroundServiceTypePolicies.put(0, FGS_TYPE_POLICY_NONE);
            this.mForegroundServiceTypePolicies.put(1, FGS_TYPE_POLICY_DATA_SYNC);
            this.mForegroundServiceTypePolicies.put(2, FGS_TYPE_POLICY_MEDIA_PLAYBACK);
            this.mForegroundServiceTypePolicies.put(4, FGS_TYPE_POLICY_PHONE_CALL);
            this.mForegroundServiceTypePolicies.put(8, FGS_TYPE_POLICY_LOCATION);
            this.mForegroundServiceTypePolicies.put(16, FGS_TYPE_POLICY_CONNECTED_DEVICE);
            this.mForegroundServiceTypePolicies.put(32, FGS_TYPE_POLICY_MEDIA_PROJECTION);
            this.mForegroundServiceTypePolicies.put(64, FGS_TYPE_POLICY_CAMERA);
            this.mForegroundServiceTypePolicies.put(128, FGS_TYPE_POLICY_MICROPHONE);
            this.mForegroundServiceTypePolicies.put(256, FGS_TYPE_POLICY_HEALTH);
            this.mForegroundServiceTypePolicies.put(512, FGS_TYPE_POLICY_REMOTE_MESSAGING);
            this.mForegroundServiceTypePolicies.put(1024, FGS_TYPE_POLICY_SYSTEM_EXEMPTED);
            this.mForegroundServiceTypePolicies.put(2048, FGS_TYPE_POLICY_SHORT_SERVICE);
            this.mForegroundServiceTypePolicies.put(8192, FGS_TYPE_POLICY_MEDIA_PROCESSING);
            this.mForegroundServiceTypePolicies.put(1073741824, FGS_TYPE_POLICY_SPECIAL_USE);
            int size = this.mForegroundServiceTypePolicies.size();
            for (int i = 0; i < size; i++) {
                android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo valueAt = this.mForegroundServiceTypePolicies.valueAt(i);
                this.mPermissionEnforcementToPolicyInfoMap.put(valueAt.mPermissionEnforcementFlag, valueAt);
            }
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo getForegroundServiceTypePolicyInfo(int i, int i2) {
            android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = this.mForegroundServiceTypePolicies.get(i);
            if (foregroundServiceTypePolicyInfo == null && (foregroundServiceTypePolicyInfo = this.mForegroundServiceTypePolicies.get(i2)) == null) {
                throw new java.lang.IllegalArgumentException("Invalid default fgs type " + i2);
            }
            return foregroundServiceTypePolicyInfo;
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public int checkForegroundServiceTypePolicy(android.content.Context context, java.lang.String str, int i, int i2, boolean z, android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo) {
            int i3;
            if (foregroundServiceTypePolicyInfo.isTypeDisabled(i)) {
                return 3;
            }
            boolean z2 = false;
            if (foregroundServiceTypePolicyInfo.mAllOfPermissions == null) {
                i3 = 0;
            } else {
                i3 = foregroundServiceTypePolicyInfo.mAllOfPermissions.checkPermissions(context, i, i2, str, z);
            }
            if (i3 == 0) {
                if (foregroundServiceTypePolicyInfo.mAnyOfPermissions == null) {
                    z2 = true;
                } else {
                    i3 = foregroundServiceTypePolicyInfo.mAnyOfPermissions.checkPermissions(context, i, i2, str, z);
                    if (i3 != 0) {
                        z2 = true;
                    }
                }
                if (z2 && foregroundServiceTypePolicyInfo.mCustomPermission != null) {
                    i3 = foregroundServiceTypePolicyInfo.mCustomPermission.checkPermission(context, i, i2, str, z);
                }
            }
            if (i3 != 0) {
                if (foregroundServiceTypePolicyInfo.mPermissionEnforcementFlagValue && android.app.compat.CompatChanges.isChangeEnabled(android.app.ForegroundServiceTypePolicy.FGS_TYPE_PERMISSION_CHANGE_ID, i)) {
                    return 5;
                }
                return 4;
            }
            if (!foregroundServiceTypePolicyInfo.isTypeDeprecated(i)) {
                return 1;
            }
            return 2;
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public void updatePermissionEnforcementFlagIfNecessary(java.lang.String str) {
            android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = this.mPermissionEnforcementToPolicyInfoMap.get(str);
            if (foregroundServiceTypePolicyInfo != null) {
                foregroundServiceTypePolicyInfo.updatePermissionEnforcementFlagIfNecessary(str);
            }
        }
    }
}
