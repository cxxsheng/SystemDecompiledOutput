package android.net.netstats;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public class NetworkStatsDataMigrationUtils {
    private static final int BUFFER_SIZE = 8192;
    private static final int FILE_MAGIC = 1095648596;
    public static final java.lang.String PREFIX_UID = "uid";
    public static final java.lang.String PREFIX_XT = "xt";
    public static final java.lang.String PREFIX_UID_TAG = "uid_tag";
    private static final java.util.Map<java.lang.String, java.lang.String> sPrefixLegacyFileNameMap = java.util.Map.of(PREFIX_XT, "netstats_xt.bin", "uid", "netstats_uid.bin", PREFIX_UID_TAG, "netstats_uid.bin");

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Prefix {
    }

    private static class CollectionVersion {
        static final int VERSION_NETWORK_INIT = 1;
        static final int VERSION_UID_INIT = 1;
        static final int VERSION_UID_WITH_IDENT = 2;
        static final int VERSION_UID_WITH_SET = 4;
        static final int VERSION_UID_WITH_TAG = 3;
        static final int VERSION_UNIFIED_INIT = 16;

        private CollectionVersion() {
        }
    }

    private static class HistoryVersion {
        static final int VERSION_ADD_ACTIVE = 3;
        static final int VERSION_ADD_PACKETS = 2;
        static final int VERSION_INIT = 1;

        private HistoryVersion() {
        }
    }

    private static class IdentitySetVersion {
        static final int VERSION_ADD_DEFAULT_NETWORK = 5;
        static final int VERSION_ADD_METERED = 4;
        static final int VERSION_ADD_NETWORK_ID = 3;
        static final int VERSION_ADD_OEM_MANAGED_NETWORK = 6;
        static final int VERSION_ADD_ROAMING = 2;
        static final int VERSION_ADD_SUB_ID = 7;
        static final int VERSION_INIT = 1;

        private IdentitySetVersion() {
        }
    }

    private NetworkStatsDataMigrationUtils() {
    }

    private static java.io.File getPlatformSystemDir() {
        return new java.io.File(android.os.Environment.getDataDirectory(), "system");
    }

    private static java.io.File getPlatformBaseDir() {
        java.io.File file = new java.io.File(getPlatformSystemDir(), android.content.Context.NETWORK_STATS_SERVICE);
        file.mkdirs();
        return file;
    }

    private static java.io.File getLegacyBinFileForPrefix(java.lang.String str) {
        return new java.io.File(getPlatformSystemDir(), sPrefixLegacyFileNameMap.get(str));
    }

    private static java.util.ArrayList<java.io.File> getPlatformFileListForPrefix(java.lang.String str) {
        java.util.ArrayList<java.io.File> arrayList = new java.util.ArrayList<>();
        java.io.File platformBaseDir = getPlatformBaseDir();
        if (platformBaseDir.exists()) {
            java.lang.String[] list = platformBaseDir.list();
            if (list == null) {
                return arrayList;
            }
            java.util.Arrays.sort(list);
            for (java.lang.String str2 : list) {
                if (str2.startsWith(str + android.media.MediaMetrics.SEPARATOR)) {
                    arrayList.add(new java.io.File(platformBaseDir, str2));
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.net.NetworkStatsCollection readPlatformCollection(java.lang.String str, long j) throws java.io.IOException {
        char c;
        android.net.NetworkStatsCollection.Builder builder = new android.net.NetworkStatsCollection.Builder(j);
        switch (str.hashCode()) {
            case -434894037:
                if (str.equals(PREFIX_UID_TAG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 115792:
                if (str.equals("uid")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                java.io.File legacyBinFileForPrefix = getLegacyBinFileForPrefix(str);
                if (legacyBinFileForPrefix.exists()) {
                    readLegacyUid(builder, legacyBinFileForPrefix, PREFIX_UID_TAG.equals(str));
                    break;
                }
                break;
        }
        java.util.Iterator<java.io.File> it = getPlatformFileListForPrefix(str).iterator();
        while (it.hasNext()) {
            java.io.File next = it.next();
            if (next.exists()) {
                readPlatformCollection(builder, next);
            }
        }
        return builder.build();
    }

    private static void readPlatformCollection(android.net.NetworkStatsCollection.Builder builder, java.io.File file) throws java.io.IOException {
        com.android.internal.util.ArtFastDataInput artFastDataInput = new com.android.internal.util.ArtFastDataInput(new java.io.FileInputStream(file), 8192);
        try {
            readPlatformCollection(builder, artFastDataInput);
        } finally {
            libcore.io.IoUtils.closeQuietly(artFastDataInput);
        }
    }

    public static void readPlatformCollection(android.net.NetworkStatsCollection.Builder builder, java.io.DataInput dataInput) throws java.io.IOException {
        int readInt = dataInput.readInt();
        if (readInt != FILE_MAGIC) {
            throw new java.net.ProtocolException("unexpected magic: " + readInt);
        }
        int readInt2 = dataInput.readInt();
        switch (readInt2) {
            case 16:
                int readInt3 = dataInput.readInt();
                for (int i = 0; i < readInt3; i++) {
                    java.util.Set<android.net.NetworkIdentity> readPlatformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                    int readInt4 = dataInput.readInt();
                    for (int i2 = 0; i2 < readInt4; i2++) {
                        builder.addEntry(new android.net.NetworkStatsCollection.Key(readPlatformNetworkIdentitySet, dataInput.readInt(), dataInput.readInt(), dataInput.readInt()), readPlatformHistory(dataInput));
                    }
                }
                return;
            default:
                throw new java.net.ProtocolException("unexpected version: " + readInt2);
        }
    }

    private static long[] readFullLongArray(java.io.DataInput dataInput) throws java.io.IOException {
        int readInt = dataInput.readInt();
        if (readInt < 0) {
            throw new java.net.ProtocolException("negative array size");
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = dataInput.readLong();
        }
        return jArr;
    }

    private static long[] readVarLongArray(java.io.DataInput dataInput) throws java.io.IOException {
        int readInt = dataInput.readInt();
        if (readInt == -1) {
            return null;
        }
        if (readInt < 0) {
            throw new java.net.ProtocolException("negative array size");
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readVarLong(dataInput);
        }
        return jArr;
    }

    private static long readVarLong(java.io.DataInput dataInput) throws java.io.IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((dataInput.readByte() & 128) == 0) {
                return j;
            }
        }
        throw new java.net.ProtocolException("malformed var long");
    }

    private static java.lang.String readOptionalString(java.io.DataInput dataInput) throws java.io.IOException {
        if (dataInput.readByte() != 0) {
            return dataInput.readUTF();
        }
        return null;
    }

    private static android.net.NetworkStatsHistory readPlatformHistory(java.io.DataInput dataInput) throws java.io.IOException {
        long readLong;
        long[] readFullLongArray;
        long[] jArr;
        long[] readFullLongArray2;
        long[] jArr2;
        long[] jArr3;
        int length;
        long[] jArr4;
        long[] jArr5;
        long[] jArr6 = new long[0];
        int readInt = dataInput.readInt();
        switch (readInt) {
            case 1:
                readLong = dataInput.readLong();
                long[] readFullLongArray3 = readFullLongArray(dataInput);
                readFullLongArray = readFullLongArray(dataInput);
                jArr = new long[readFullLongArray3.length];
                readFullLongArray2 = readFullLongArray(dataInput);
                jArr2 = new long[readFullLongArray3.length];
                jArr3 = new long[readFullLongArray3.length];
                length = readFullLongArray3.length;
                jArr4 = jArr6;
                jArr5 = readFullLongArray3;
                break;
            case 2:
            case 3:
                readLong = dataInput.readLong();
                jArr5 = readVarLongArray(dataInput);
                if (readInt >= 3) {
                    jArr4 = readVarLongArray(dataInput);
                } else {
                    jArr4 = new long[jArr5.length];
                }
                readFullLongArray = readVarLongArray(dataInput);
                jArr = readVarLongArray(dataInput);
                readFullLongArray2 = readVarLongArray(dataInput);
                jArr2 = readVarLongArray(dataInput);
                jArr3 = readVarLongArray(dataInput);
                length = jArr5.length;
                break;
            default:
                throw new java.net.ProtocolException("unexpected version: " + readInt);
        }
        android.net.NetworkStatsHistory.Builder builder = new android.net.NetworkStatsHistory.Builder(readLong, length);
        for (int i = 0; i < length; i++) {
            builder.addEntry(new android.net.NetworkStatsHistory.Entry(jArr5[i], jArr4[i], readFullLongArray[i], jArr[i], readFullLongArray2[i], jArr2[i], jArr3[i]));
        }
        return builder.build();
    }

    private static java.util.Set<android.net.NetworkIdentity> readPlatformNetworkIdentitySet(java.io.DataInput dataInput) throws java.io.IOException {
        java.lang.String str;
        boolean z;
        boolean z2;
        int i;
        int i2;
        int readInt = dataInput.readInt();
        int readInt2 = dataInput.readInt();
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i3 = 0; i3 < readInt2; i3++) {
            boolean z3 = true;
            if (readInt <= 1) {
                dataInput.readInt();
            }
            int readInt3 = dataInput.readInt();
            int readInt4 = dataInput.readInt();
            java.lang.String readOptionalString = readOptionalString(dataInput);
            if (readInt >= 3) {
                str = readOptionalString(dataInput);
            } else {
                str = null;
            }
            if (readInt >= 2) {
                z = dataInput.readBoolean();
            } else {
                z = false;
            }
            if (readInt >= 4) {
                z2 = dataInput.readBoolean();
            } else {
                z2 = readInt3 == 0;
            }
            if (readInt >= 5) {
                z3 = dataInput.readBoolean();
            }
            if (readInt >= 6) {
                i = dataInput.readInt();
            } else {
                i = 0;
            }
            if (readInt >= 7) {
                i2 = dataInput.readInt();
            } else {
                i2 = -1;
            }
            android.net.NetworkIdentity.Builder subId = new android.net.NetworkIdentity.Builder().setType(getCollapsedLegacyType(readInt3)).setSubscriberId(readOptionalString).setWifiNetworkKey(str).setRoaming(z).setMetered(z2).setDefaultNetwork(z3).setOemManaged(i).setSubId(i2);
            if (readInt3 == 0 && readInt4 != -1) {
                subId.setRatType(readInt4);
            }
            hashSet.add(subId.build());
        }
        return hashSet;
    }

    private static int getCollapsedLegacyType(int i) {
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
                return 0;
            case 1:
            case 6:
            case 7:
            case 8:
            case 9:
            case 13:
            default:
                return i;
        }
    }

    private static void readLegacyUid(android.net.NetworkStatsCollection.Builder builder, java.io.File file, boolean z) throws java.io.IOException {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.BufferedInputStream(new android.util.AtomicFile(file).openRead()));
        try {
            readLegacyUid(builder, dataInputStream, z);
        } finally {
            libcore.io.IoUtils.closeQuietly(dataInputStream);
        }
    }

    public static void readLegacyUid(android.net.NetworkStatsCollection.Builder builder, java.io.DataInput dataInput, boolean z) throws java.io.IOException {
        int i;
        try {
            int readInt = dataInput.readInt();
            if (readInt != FILE_MAGIC) {
                throw new java.net.ProtocolException("unexpected magic: " + readInt);
            }
            int readInt2 = dataInput.readInt();
            switch (readInt2) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                case 4:
                    int readInt3 = dataInput.readInt();
                    for (int i2 = 0; i2 < readInt3; i2++) {
                        java.util.Set<android.net.NetworkIdentity> readPlatformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                        int readInt4 = dataInput.readInt();
                        for (int i3 = 0; i3 < readInt4; i3++) {
                            int readInt5 = dataInput.readInt();
                            if (readInt2 >= 4) {
                                i = dataInput.readInt();
                            } else {
                                i = 0;
                            }
                            int readInt6 = dataInput.readInt();
                            android.net.NetworkStatsCollection.Key key = new android.net.NetworkStatsCollection.Key(readPlatformNetworkIdentitySet, readInt5, i, readInt6);
                            android.net.NetworkStatsHistory readPlatformHistory = readPlatformHistory(dataInput);
                            if ((readInt6 == 0) != z) {
                                builder.addEntry(key, readPlatformHistory);
                            }
                        }
                    }
                    break;
                default:
                    throw new java.net.ProtocolException("unknown version: " + readInt2);
            }
        } catch (java.io.FileNotFoundException | java.net.ProtocolException e) {
        }
    }
}
