package android.content.pm;

/* loaded from: classes.dex */
public class PackagePartitions {
    public static final java.lang.String FINGERPRINT = getFingerprint();
    public static final int PARTITION_ODM = 2;
    public static final int PARTITION_OEM = 3;
    public static final int PARTITION_PRODUCT = 4;
    public static final int PARTITION_SYSTEM = 0;
    public static final int PARTITION_SYSTEM_EXT = 5;
    public static final int PARTITION_VENDOR = 1;
    private static final java.util.ArrayList<android.content.pm.PackagePartitions.SystemPartition> SYSTEM_PARTITIONS;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PartitionType {
    }

    static {
        boolean z = true;
        boolean z2 = true;
        SYSTEM_PARTITIONS = new java.util.ArrayList<>(java.util.Arrays.asList(new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getRootDirectory(), 0, "system", true, false), new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getVendorDirectory(), 1, "vendor", true, true), new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getOdmDirectory(), 2, android.os.Build.Partition.PARTITION_NAME_ODM, true, z), new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getOemDirectory(), 3, android.os.Build.Partition.PARTITION_NAME_OEM, false, z), new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getProductDirectory(), 4, "product", z2, z), new android.content.pm.PackagePartitions.SystemPartition(android.os.Environment.getSystemExtDirectory(), 5, android.os.Build.Partition.PARTITION_NAME_SYSTEM_EXT, z2, z)));
    }

    public static <T> java.util.ArrayList<T> getOrderedPartitions(java.util.function.Function<android.content.pm.PackagePartitions.SystemPartition, T> function) {
        java.util.ArrayList<T> arrayList = new java.util.ArrayList<>();
        int size = SYSTEM_PARTITIONS.size();
        for (int i = 0; i < size; i++) {
            T apply = function.apply(SYSTEM_PARTITIONS.get(i));
            if (apply != null) {
                arrayList.add(apply);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.io.File canonicalize(java.io.File file) {
        try {
            return file.getCanonicalFile();
        } catch (java.io.IOException e) {
            return file;
        }
    }

    private static java.lang.String getFingerprint() {
        java.lang.String[] strArr = new java.lang.String[SYSTEM_PARTITIONS.size() + 1];
        for (int i = 0; i < SYSTEM_PARTITIONS.size(); i++) {
            strArr[i] = "ro." + SYSTEM_PARTITIONS.get(i).getName() + ".build.fingerprint";
        }
        strArr[SYSTEM_PARTITIONS.size()] = "ro.build.version.incremental";
        return android.os.SystemProperties.digestOf(strArr);
    }

    public static class SystemPartition {
        private final android.content.pm.PackagePartitions.DeferredCanonicalFile mAppFolder;
        private final android.content.pm.PackagePartitions.DeferredCanonicalFile mFolder;
        private final java.lang.String mName;
        private final java.io.File mNonConicalFolder;
        private final android.content.pm.PackagePartitions.DeferredCanonicalFile mOverlayFolder;
        private final android.content.pm.PackagePartitions.DeferredCanonicalFile mPrivAppFolder;
        public final int type;

        private SystemPartition(java.io.File file, int i, java.lang.String str, boolean z, boolean z2) {
            android.content.pm.PackagePartitions.DeferredCanonicalFile deferredCanonicalFile;
            this.type = i;
            this.mName = str;
            byte b = 0;
            byte b2 = 0;
            this.mFolder = new android.content.pm.PackagePartitions.DeferredCanonicalFile(file);
            this.mAppFolder = new android.content.pm.PackagePartitions.DeferredCanonicalFile(file, "app");
            if (z) {
                deferredCanonicalFile = new android.content.pm.PackagePartitions.DeferredCanonicalFile(file, "priv-app");
            } else {
                deferredCanonicalFile = null;
            }
            this.mPrivAppFolder = deferredCanonicalFile;
            this.mOverlayFolder = z2 ? new android.content.pm.PackagePartitions.DeferredCanonicalFile(file, "overlay") : null;
            this.mNonConicalFolder = file;
        }

        public SystemPartition(android.content.pm.PackagePartitions.SystemPartition systemPartition) {
            this.type = systemPartition.type;
            this.mName = systemPartition.mName;
            this.mFolder = new android.content.pm.PackagePartitions.DeferredCanonicalFile(systemPartition.mFolder.getFile());
            this.mAppFolder = systemPartition.mAppFolder;
            this.mPrivAppFolder = systemPartition.mPrivAppFolder;
            this.mOverlayFolder = systemPartition.mOverlayFolder;
            this.mNonConicalFolder = systemPartition.mNonConicalFolder;
        }

        public SystemPartition(java.io.File file, android.content.pm.PackagePartitions.SystemPartition systemPartition) {
            this(file, systemPartition.type, systemPartition.mName, systemPartition.mPrivAppFolder != null, systemPartition.mOverlayFolder != null);
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.io.File getFolder() {
            return this.mFolder.getFile();
        }

        public java.io.File getNonConicalFolder() {
            return this.mNonConicalFolder;
        }

        public java.io.File getAppFolder() {
            if (this.mAppFolder == null) {
                return null;
            }
            return this.mAppFolder.getFile();
        }

        public java.io.File getPrivAppFolder() {
            if (this.mPrivAppFolder == null) {
                return null;
            }
            return this.mPrivAppFolder.getFile();
        }

        public java.io.File getOverlayFolder() {
            if (this.mOverlayFolder == null) {
                return null;
            }
            return this.mOverlayFolder.getFile();
        }

        public boolean containsPath(java.lang.String str) {
            return containsFile(new java.io.File(str));
        }

        public boolean containsFile(java.io.File file) {
            return android.os.FileUtils.contains(this.mFolder.getFile(), android.content.pm.PackagePartitions.canonicalize(file));
        }

        public boolean containsPrivApp(java.io.File file) {
            return this.mPrivAppFolder != null && android.os.FileUtils.contains(this.mPrivAppFolder.getFile(), android.content.pm.PackagePartitions.canonicalize(file));
        }

        public boolean containsApp(java.io.File file) {
            return this.mAppFolder != null && android.os.FileUtils.contains(this.mAppFolder.getFile(), android.content.pm.PackagePartitions.canonicalize(file));
        }

        public boolean containsOverlay(java.io.File file) {
            return this.mOverlayFolder != null && android.os.FileUtils.contains(this.mOverlayFolder.getFile(), android.content.pm.PackagePartitions.canonicalize(file));
        }
    }

    private static class DeferredCanonicalFile {
        private java.io.File mFile;
        private boolean mIsCanonical;

        private DeferredCanonicalFile(java.io.File file) {
            this.mIsCanonical = false;
            this.mFile = file;
        }

        private DeferredCanonicalFile(java.io.File file, java.lang.String str) {
            this.mIsCanonical = false;
            this.mFile = new java.io.File(file, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.io.File getFile() {
            if (!this.mIsCanonical) {
                this.mFile = android.content.pm.PackagePartitions.canonicalize(this.mFile);
                this.mIsCanonical = true;
            }
            return this.mFile;
        }
    }
}
