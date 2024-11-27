package android.provider;

/* loaded from: classes3.dex */
public interface DeviceConfigInterface {
    public static final android.provider.DeviceConfigInterface REAL = new android.provider.DeviceConfigInterface() { // from class: android.provider.DeviceConfigInterface.1
        @Override // android.provider.DeviceConfigInterface
        public java.lang.String getProperty(java.lang.String str, java.lang.String str2) {
            return android.provider.DeviceConfig.getProperty(str, str2);
        }

        @Override // android.provider.DeviceConfigInterface
        public android.provider.DeviceConfig.Properties getProperties(java.lang.String str, java.lang.String... strArr) {
            return android.provider.DeviceConfig.getProperties(str, strArr);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean setProperty(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
            return android.provider.DeviceConfig.setProperty(str, str2, str3, z);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean setProperties(android.provider.DeviceConfig.Properties properties) throws android.provider.DeviceConfig.BadConfigException {
            return android.provider.DeviceConfig.setProperties(properties);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean deleteProperty(java.lang.String str, java.lang.String str2) {
            return android.provider.DeviceConfig.deleteProperty(str, str2);
        }

        @Override // android.provider.DeviceConfigInterface
        public void resetToDefaults(int i, java.lang.String str) {
            android.provider.DeviceConfig.resetToDefaults(i, str);
        }

        @Override // android.provider.DeviceConfigInterface
        public java.lang.String getString(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            return android.provider.DeviceConfig.getString(str, str2, str3);
        }

        @Override // android.provider.DeviceConfigInterface
        public int getInt(java.lang.String str, java.lang.String str2, int i) {
            return android.provider.DeviceConfig.getInt(str, str2, i);
        }

        @Override // android.provider.DeviceConfigInterface
        public long getLong(java.lang.String str, java.lang.String str2, long j) {
            return android.provider.DeviceConfig.getLong(str, str2, j);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean getBoolean(java.lang.String str, java.lang.String str2, boolean z) {
            return android.provider.DeviceConfig.getBoolean(str, str2, z);
        }

        @Override // android.provider.DeviceConfigInterface
        public float getFloat(java.lang.String str, java.lang.String str2, float f) {
            return android.provider.DeviceConfig.getFloat(str, str2, f);
        }

        @Override // android.provider.DeviceConfigInterface
        public void addOnPropertiesChangedListener(java.lang.String str, java.util.concurrent.Executor executor, android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(str, executor, onPropertiesChangedListener);
        }

        @Override // android.provider.DeviceConfigInterface
        public void removeOnPropertiesChangedListener(android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            android.provider.DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
        }
    };

    void addOnPropertiesChangedListener(java.lang.String str, java.util.concurrent.Executor executor, android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener);

    boolean deleteProperty(java.lang.String str, java.lang.String str2);

    boolean getBoolean(java.lang.String str, java.lang.String str2, boolean z);

    float getFloat(java.lang.String str, java.lang.String str2, float f);

    int getInt(java.lang.String str, java.lang.String str2, int i);

    long getLong(java.lang.String str, java.lang.String str2, long j);

    android.provider.DeviceConfig.Properties getProperties(java.lang.String str, java.lang.String... strArr);

    java.lang.String getProperty(java.lang.String str, java.lang.String str2);

    java.lang.String getString(java.lang.String str, java.lang.String str2, java.lang.String str3);

    void removeOnPropertiesChangedListener(android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener);

    void resetToDefaults(int i, java.lang.String str);

    boolean setProperties(android.provider.DeviceConfig.Properties properties) throws android.provider.DeviceConfig.BadConfigException;

    boolean setProperty(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z);
}
