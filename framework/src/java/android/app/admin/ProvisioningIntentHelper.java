package android.app.admin;

/* loaded from: classes.dex */
final class ProvisioningIntentHelper {
    private static final java.util.Map<java.lang.String, java.lang.Class> EXTRAS_TO_CLASS_MAP = createExtrasToClassMap();
    private static final java.lang.String TAG = "ProvisioningIntentHelper";

    private ProvisioningIntentHelper() {
    }

    public static android.content.Intent createProvisioningIntentFromNfcIntent(android.content.Intent intent) {
        java.util.Objects.requireNonNull(intent);
        if (!"android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction())) {
            android.util.Log.e(TAG, "Wrong Nfc action: " + intent.getAction());
            return null;
        }
        android.nfc.NdefRecord firstNdefRecord = getFirstNdefRecord(intent);
        if (firstNdefRecord != null) {
            return createProvisioningIntentFromNdefRecord(firstNdefRecord);
        }
        return null;
    }

    private static android.content.Intent createProvisioningIntentFromNdefRecord(android.nfc.NdefRecord ndefRecord) {
        java.util.Objects.requireNonNull(ndefRecord);
        java.util.Properties loadPropertiesFromPayload = loadPropertiesFromPayload(ndefRecord.getPayload());
        if (loadPropertiesFromPayload == null) {
            android.util.Log.e(TAG, "Failed to load NdefRecord properties.");
            return null;
        }
        android.os.Bundle createBundleFromProperties = createBundleFromProperties(loadPropertiesFromPayload);
        if (!containsRequiredProvisioningExtras(createBundleFromProperties)) {
            android.util.Log.e(TAG, "Bundle does not contain the required provisioning extras.");
            return null;
        }
        return createProvisioningIntentFromBundle(createBundleFromProperties);
    }

    private static java.util.Properties loadPropertiesFromPayload(byte[] bArr) {
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.load(new java.io.StringReader(new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8)));
            return properties;
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "NFC Intent properties loading failed.");
            return null;
        }
    }

    private static android.os.Bundle createBundleFromProperties(java.util.Properties properties) {
        java.util.Enumeration<?> propertyNames = properties.propertyNames();
        android.os.Bundle bundle = new android.os.Bundle();
        while (propertyNames.hasMoreElements()) {
            addPropertyToBundle((java.lang.String) propertyNames.nextElement(), properties, bundle);
        }
        return bundle;
    }

    private static void addPropertyToBundle(java.lang.String str, java.util.Properties properties, android.os.Bundle bundle) {
        if (EXTRAS_TO_CLASS_MAP.get(str) == android.content.ComponentName.class) {
            bundle.putParcelable(str, android.content.ComponentName.unflattenFromString(properties.getProperty(str)));
            return;
        }
        if (EXTRAS_TO_CLASS_MAP.get(str) == android.os.PersistableBundle.class) {
            try {
                bundle.putParcelable(str, deserializeExtrasBundle(properties, str));
                return;
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Failed to parse " + str + android.media.MediaMetrics.SEPARATOR, e);
                return;
            }
        }
        if (EXTRAS_TO_CLASS_MAP.get(str) == java.lang.Boolean.class) {
            bundle.putBoolean(str, java.lang.Boolean.parseBoolean(properties.getProperty(str)));
            return;
        }
        if (EXTRAS_TO_CLASS_MAP.get(str) == java.lang.Long.class) {
            bundle.putLong(str, java.lang.Long.parseLong(properties.getProperty(str)));
        } else if (EXTRAS_TO_CLASS_MAP.get(str) == java.lang.Integer.class) {
            bundle.putInt(str, java.lang.Integer.parseInt(properties.getProperty(str)));
        } else {
            bundle.putString(str, properties.getProperty(str));
        }
    }

    private static android.os.PersistableBundle deserializeExtrasBundle(java.util.Properties properties, java.lang.String str) throws java.io.IOException {
        java.lang.String property = properties.getProperty(str);
        if (property == null) {
            return null;
        }
        java.util.Properties properties2 = new java.util.Properties();
        properties2.load(new java.io.StringReader(property));
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle(properties2.size());
        for (java.lang.String str2 : properties2.stringPropertyNames()) {
            persistableBundle.putString(str2, properties2.getProperty(str2));
        }
        return persistableBundle;
    }

    private static android.content.Intent createProvisioningIntentFromBundle(android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(bundle);
        android.content.Intent intent = new android.content.Intent(android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE);
        intent.putExtras(bundle);
        intent.putExtra(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_TRIGGER, 5);
        return intent;
    }

    private static boolean containsRequiredProvisioningExtras(android.os.Bundle bundle) {
        return bundle.containsKey(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME) || bundle.containsKey(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME);
    }

    private static android.nfc.NdefRecord getFirstNdefRecord(android.content.Intent intent) {
        android.os.Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
        if (parcelableArrayExtra == null) {
            android.util.Log.i(TAG, "No EXTRA_NDEF_MESSAGES from nfcIntent");
            return null;
        }
        for (android.os.Parcelable parcelable : parcelableArrayExtra) {
            android.nfc.NdefRecord[] records = ((android.nfc.NdefMessage) parcelable).getRecords();
            if (records.length > 0) {
                android.nfc.NdefRecord ndefRecord = records[0];
                if (android.app.admin.DevicePolicyManager.MIME_TYPE_PROVISIONING_NFC.equals(new java.lang.String(ndefRecord.getType(), java.nio.charset.StandardCharsets.UTF_8))) {
                    return ndefRecord;
                }
            }
        }
        android.util.Log.i(TAG, "No compatible records found on nfcIntent");
        return null;
    }

    private static java.util.Map<java.lang.String, java.lang.Class> createExtrasToClassMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator<java.lang.String> it = getBooleanExtras().iterator();
        while (it.hasNext()) {
            hashMap.put(it.next(), java.lang.Boolean.class);
        }
        java.util.Iterator<java.lang.String> it2 = getLongExtras().iterator();
        while (it2.hasNext()) {
            hashMap.put(it2.next(), java.lang.Long.class);
        }
        java.util.Iterator<java.lang.String> it3 = getIntExtras().iterator();
        while (it3.hasNext()) {
            hashMap.put(it3.next(), java.lang.Integer.class);
        }
        java.util.Iterator<java.lang.String> it4 = getComponentNameExtras().iterator();
        while (it4.hasNext()) {
            hashMap.put(it4.next(), android.content.ComponentName.class);
        }
        java.util.Iterator<java.lang.String> it5 = getPersistableBundleExtras().iterator();
        while (it5.hasNext()) {
            hashMap.put(it5.next(), android.os.PersistableBundle.class);
        }
        return hashMap;
    }

    private static java.util.Set<java.lang.String> getPersistableBundleExtras() {
        return java.util.Set.of(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE);
    }

    private static java.util.Set<java.lang.String> getComponentNameExtras() {
        return java.util.Set.of(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME);
    }

    private static java.util.Set<java.lang.String> getIntExtras() {
        return java.util.Set.of(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_WIFI_PROXY_PORT, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SUPPORTED_MODES);
    }

    private static java.util.Set<java.lang.String> getLongExtras() {
        return java.util.Set.of(android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_LOCAL_TIME);
    }

    private static java.util.Set<java.lang.String> getBooleanExtras() {
        return java.util.Set.of((java.lang.Object[]) new java.lang.String[]{android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_ALLOW_OFFLINE, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_WIFI_HIDDEN, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SKIP_ENCRYPTION, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SKIP_EDUCATION_SCREENS, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_USE_MOBILE_DATA, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE, android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_KEEP_SCREEN_ON});
    }
}
