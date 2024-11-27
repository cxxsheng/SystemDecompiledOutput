package com.android.server.locksettings;

/* loaded from: classes2.dex */
class PasswordSlotManager {
    private static final java.lang.String GSI_RUNNING_PROP = "ro.gsid.image_running";
    private static final java.lang.String SLOT_MAP_DIR = "/metadata/password_slots";
    private static final java.lang.String TAG = "PasswordSlotManager";
    private java.util.Set<java.lang.Integer> mActiveSlots;
    private java.util.Map<java.lang.Integer, java.lang.String> mSlotMap;

    @com.android.internal.annotations.VisibleForTesting
    protected java.lang.String getSlotMapDir() {
        return SLOT_MAP_DIR;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getGsiImageNumber() {
        return android.os.SystemProperties.getInt(GSI_RUNNING_PROP, 0);
    }

    public void refreshActiveSlots(java.util.Set<java.lang.Integer> set) throws java.lang.RuntimeException {
        if (this.mSlotMap == null) {
            this.mActiveSlots = new java.util.HashSet(set);
            return;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : this.mSlotMap.entrySet()) {
            if (entry.getValue().equals(getMode())) {
                hashSet.add(entry.getKey());
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.mSlotMap.remove((java.lang.Integer) it.next());
        }
        java.util.Iterator<java.lang.Integer> it2 = set.iterator();
        while (it2.hasNext()) {
            this.mSlotMap.put(it2.next(), getMode());
        }
        saveSlotMap();
    }

    public void markSlotInUse(int i) throws java.lang.RuntimeException {
        ensureSlotMapLoaded();
        if (this.mSlotMap.containsKey(java.lang.Integer.valueOf(i)) && !this.mSlotMap.get(java.lang.Integer.valueOf(i)).equals(getMode())) {
            throw new java.lang.IllegalStateException("password slot " + i + " is not available");
        }
        this.mSlotMap.put(java.lang.Integer.valueOf(i), getMode());
        saveSlotMap();
    }

    public void markSlotDeleted(int i) throws java.lang.RuntimeException {
        ensureSlotMapLoaded();
        if (this.mSlotMap.containsKey(java.lang.Integer.valueOf(i)) && !this.mSlotMap.get(java.lang.Integer.valueOf(i)).equals(getMode())) {
            throw new java.lang.IllegalStateException("password slot " + i + " cannot be deleted");
        }
        this.mSlotMap.remove(java.lang.Integer.valueOf(i));
        saveSlotMap();
    }

    public java.util.Set<java.lang.Integer> getUsedSlots() {
        ensureSlotMapLoaded();
        return java.util.Collections.unmodifiableSet(this.mSlotMap.keySet());
    }

    private java.io.File getSlotMapFile() {
        return java.nio.file.Paths.get(getSlotMapDir(), "slot_map").toFile();
    }

    private java.lang.String getMode() {
        int gsiImageNumber = getGsiImageNumber();
        if (gsiImageNumber > 0) {
            return "gsi" + gsiImageNumber;
        }
        return "host";
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, java.lang.String> loadSlotMap(java.io.InputStream inputStream) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Properties properties = new java.util.Properties();
        properties.load(inputStream);
        for (java.lang.String str : properties.stringPropertyNames()) {
            int parseInt = java.lang.Integer.parseInt(str);
            hashMap.put(java.lang.Integer.valueOf(parseInt), properties.getProperty(str));
        }
        return hashMap;
    }

    private java.util.Map<java.lang.Integer, java.lang.String> loadSlotMap() {
        java.io.File slotMapFile = getSlotMapFile();
        if (slotMapFile.exists()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(slotMapFile);
                try {
                    java.util.Map<java.lang.Integer, java.lang.String> loadSlotMap = loadSlotMap(fileInputStream);
                    fileInputStream.close();
                    return loadSlotMap;
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Could not load slot map file", e);
            }
        }
        return new java.util.HashMap();
    }

    private void ensureSlotMapLoaded() {
        if (this.mSlotMap == null) {
            this.mSlotMap = loadSlotMap();
            if (this.mActiveSlots != null) {
                refreshActiveSlots(this.mActiveSlots);
                this.mActiveSlots = null;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void saveSlotMap(java.io.OutputStream outputStream) throws java.io.IOException {
        if (this.mSlotMap == null) {
            return;
        }
        java.util.Properties properties = new java.util.Properties();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : this.mSlotMap.entrySet()) {
            properties.setProperty(entry.getKey().toString(), entry.getValue());
        }
        properties.store(outputStream, "");
    }

    private void saveSlotMap() {
        if (this.mSlotMap == null) {
            return;
        }
        if (!getSlotMapFile().getParentFile().exists()) {
            android.util.Slog.w(TAG, "Not saving slot map, " + getSlotMapDir() + " does not exist");
            return;
        }
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(getSlotMapFile());
            try {
                saveSlotMap(fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "failed to save password slot map", e);
        }
    }
}
