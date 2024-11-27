package com.android.server;

/* loaded from: classes.dex */
public abstract class ExtconUEventObserver extends android.os.UEventObserver {
    private static final boolean LOG = false;
    private static final java.lang.String SELINUX_POLICIES_NEED_TO_BE_CHANGED = "This probably means the selinux policies need to be changed.";
    private static final java.lang.String TAG = "ExtconUEventObserver";
    private final java.util.Map<java.lang.String, com.android.server.ExtconUEventObserver.ExtconInfo> mExtconInfos = new android.util.ArrayMap();

    protected abstract void onUEvent(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, android.os.UEventObserver.UEvent uEvent);

    public final void onUEvent(android.os.UEventObserver.UEvent uEvent) {
        com.android.server.ExtconUEventObserver.ExtconInfo extconInfo = this.mExtconInfos.get(uEvent.get("DEVPATH"));
        if (extconInfo != null) {
            onUEvent(extconInfo, uEvent);
            return;
        }
        android.util.Slog.w(TAG, "No match found for DEVPATH of " + uEvent + " in " + this.mExtconInfos);
    }

    public void startObserving(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo) {
        java.lang.String devicePath = extconInfo.getDevicePath();
        if (devicePath == null) {
            android.util.Slog.wtf(TAG, "Unable to start observing  " + extconInfo.getName() + " because the device path is null. " + SELINUX_POLICIES_NEED_TO_BE_CHANGED);
            return;
        }
        this.mExtconInfos.put(devicePath, extconInfo);
        startObserving("DEVPATH=" + devicePath);
    }

    public static final class ExtconInfo {
        public static final java.lang.String EXTCON_CHARGE_DOWNSTREAM = "CHARGE-DOWNSTREAM";
        public static final java.lang.String EXTCON_DOCK = "DOCK";
        public static final java.lang.String EXTCON_DVI = "DVI";
        public static final java.lang.String EXTCON_FAST_CHARGER = "FAST-CHARGER";
        public static final java.lang.String EXTCON_HDMI = "HDMI";
        public static final java.lang.String EXTCON_HEADPHONE = "HEADPHONE";
        public static final java.lang.String EXTCON_JIG = "JIG";
        public static final java.lang.String EXTCON_LINE_IN = "LINE-IN";
        public static final java.lang.String EXTCON_LINE_OUT = "LINE-OUT";
        public static final java.lang.String EXTCON_MECHANICAL = "MECHANICAL";
        public static final java.lang.String EXTCON_MHL = "MHL";
        public static final java.lang.String EXTCON_MICROPHONE = "MICROPHONE";
        public static final java.lang.String EXTCON_SLOW_CHARGER = "SLOW-CHARGER";
        public static final java.lang.String EXTCON_SPDIF_IN = "SPDIF-IN";
        public static final java.lang.String EXTCON_SPDIF_OUT = "SPDIF-OUT";
        public static final java.lang.String EXTCON_TA = "TA";
        public static final java.lang.String EXTCON_USB = "USB";
        public static final java.lang.String EXTCON_USB_HOST = "USB-HOST";
        public static final java.lang.String EXTCON_VGA = "VGA";
        public static final java.lang.String EXTCON_VIDEO_IN = "VIDEO-IN";
        public static final java.lang.String EXTCON_VIDEO_OUT = "VIDEO-OUT";

        @com.android.server.ExtconUEventObserver.ExtconInfo.ExtconDeviceType
        private final java.util.HashSet<java.lang.String> mDeviceTypes = new java.util.HashSet<>();
        private final java.lang.String mName;
        private static final java.lang.Object sLock = new java.lang.Object();
        private static com.android.server.ExtconUEventObserver.ExtconInfo[] sExtconInfos = null;

        public @interface ExtconDeviceType {
        }

        @com.android.internal.annotations.GuardedBy({"sLock"})
        private static void initExtconInfos() {
            if (sExtconInfos != null) {
                return;
            }
            java.io.File file = new java.io.File("/sys/class/extcon");
            java.io.File[] listFiles = file.listFiles();
            if (listFiles == null) {
                android.util.Slog.w(com.android.server.ExtconUEventObserver.TAG, file + " exists " + file.exists() + " isDir " + file.isDirectory() + " but listFiles returns null." + com.android.server.ExtconUEventObserver.SELINUX_POLICIES_NEED_TO_BE_CHANGED);
                sExtconInfos = new com.android.server.ExtconUEventObserver.ExtconInfo[0];
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(listFiles.length);
            for (java.io.File file2 : listFiles) {
                arrayList.add(new com.android.server.ExtconUEventObserver.ExtconInfo(file2.getName()));
            }
            sExtconInfos = (com.android.server.ExtconUEventObserver.ExtconInfo[]) arrayList.toArray(new com.android.server.ExtconUEventObserver.ExtconInfo[0]);
        }

        public static java.util.List<com.android.server.ExtconUEventObserver.ExtconInfo> getExtconInfoForTypes(@com.android.server.ExtconUEventObserver.ExtconInfo.ExtconDeviceType java.lang.String[] strArr) {
            synchronized (sLock) {
                initExtconInfos();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (com.android.server.ExtconUEventObserver.ExtconInfo extconInfo : sExtconInfos) {
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (!extconInfo.hasCableType(strArr[i])) {
                        i++;
                    } else {
                        arrayList.add(extconInfo);
                        break;
                    }
                }
            }
            return arrayList;
        }

        public boolean hasCableType(@com.android.server.ExtconUEventObserver.ExtconInfo.ExtconDeviceType java.lang.String str) {
            return this.mDeviceTypes.contains(str);
        }

        private ExtconInfo(java.lang.String str) {
            this.mName = str;
            java.io.File[] listFilesOrEmpty = android.os.FileUtils.listFilesOrEmpty(new java.io.File("/sys/class/extcon", this.mName), new java.io.FilenameFilter() { // from class: com.android.server.ExtconUEventObserver$ExtconInfo$$ExternalSyntheticLambda0
                @Override // java.io.FilenameFilter
                public final boolean accept(java.io.File file, java.lang.String str2) {
                    boolean lambda$new$0;
                    lambda$new$0 = com.android.server.ExtconUEventObserver.ExtconInfo.lambda$new$0(file, str2);
                    return lambda$new$0;
                }
            });
            if (listFilesOrEmpty.length == 0) {
                android.util.Slog.d(com.android.server.ExtconUEventObserver.TAG, "Unable to list cables in /sys/class/extcon/" + this.mName + ". " + com.android.server.ExtconUEventObserver.SELINUX_POLICIES_NEED_TO_BE_CHANGED);
            }
            for (java.io.File file : listFilesOrEmpty) {
                java.lang.String str2 = null;
                try {
                    java.lang.String canonicalPath = file.getCanonicalPath();
                    try {
                        this.mDeviceTypes.add(android.os.FileUtils.readTextFile(new java.io.File(file, "name"), 0, null).replace("\n", "").replace("\r", ""));
                    } catch (java.io.IOException e) {
                        e = e;
                        str2 = canonicalPath;
                        android.util.Slog.w(com.android.server.ExtconUEventObserver.TAG, "Unable to read " + str2 + "/name. " + com.android.server.ExtconUEventObserver.SELINUX_POLICIES_NEED_TO_BE_CHANGED, e);
                    }
                } catch (java.io.IOException e2) {
                    e = e2;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$0(java.io.File file, java.lang.String str) {
            return str.startsWith("cable.");
        }

        public java.lang.String getName() {
            return this.mName;
        }

        @android.annotation.Nullable
        public java.lang.String getDevicePath() {
            try {
                java.io.File file = new java.io.File(android.text.TextUtils.formatSimple("/sys/class/extcon/%s", new java.lang.Object[]{this.mName}));
                if (!file.exists()) {
                    return null;
                }
                java.lang.String canonicalPath = file.getCanonicalPath();
                return canonicalPath.substring(canonicalPath.indexOf("/devices"));
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.ExtconUEventObserver.TAG, "Could not get the extcon device path for " + this.mName, e);
                return null;
            }
        }

        public java.lang.String getStatePath() {
            return android.text.TextUtils.formatSimple("/sys/class/extcon/%s/state", new java.lang.Object[]{this.mName});
        }
    }

    public static boolean extconExists() {
        java.io.File file = new java.io.File("/sys/class/extcon");
        return file.exists() && file.isDirectory();
    }
}
