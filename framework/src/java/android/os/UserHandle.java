package android.os;

/* loaded from: classes3.dex */
public final class UserHandle implements android.os.Parcelable {
    public static final int AID_APP_END = 19999;
    public static final int AID_APP_START = 10000;
    public static final int AID_CACHE_GID_START = 20000;
    public static final int AID_ROOT = 0;
    public static final int AID_SHARED_GID_START = 50000;
    public static final android.os.Parcelable.Creator<android.os.UserHandle> CREATOR;
    public static final int ERR_GID = -1;
    public static final int MAX_EXTRA_USER_HANDLE_CACHE_SIZE = 32;
    public static final int MAX_SECONDARY_USER_ID = 21473;
    public static final int MIN_SECONDARY_USER_ID = 10;
    public static final boolean MU_ENABLED = true;
    private static final int NUM_CACHED_USERS = 8;
    public static final int PER_USER_RANGE = 100000;
    public static final int USER_ALL = -1;
    public static final int USER_CURRENT = -2;
    public static final int USER_CURRENT_OR_SELF = -3;
    public static final int USER_NULL = -10000;

    @java.lang.Deprecated
    public static final int USER_OWNER = 0;
    public static final int USER_SERIAL_SYSTEM = 0;
    public static final int USER_SYSTEM = 0;
    final int mHandle;

    @android.annotation.SystemApi
    public static final android.os.UserHandle ALL = new android.os.UserHandle(-1);

    @android.annotation.SystemApi
    public static final android.os.UserHandle CURRENT = new android.os.UserHandle(-2);
    public static final android.os.UserHandle CURRENT_OR_SELF = new android.os.UserHandle(-3);
    private static final android.os.UserHandle NULL = new android.os.UserHandle(-10000);

    @java.lang.Deprecated
    public static final android.os.UserHandle OWNER = new android.os.UserHandle(0);

    @android.annotation.SystemApi
    public static final android.os.UserHandle SYSTEM = new android.os.UserHandle(0);
    private static final android.os.UserHandle[] CACHED_USER_HANDLES = new android.os.UserHandle[8];
    public static final android.util.SparseArray<android.os.UserHandle> sExtraUserHandleCache = new android.util.SparseArray<>(0);

    static {
        for (int i = 0; i < CACHED_USER_HANDLES.length; i++) {
            CACHED_USER_HANDLES[i] = new android.os.UserHandle(i + 10);
        }
        CREATOR = new android.os.Parcelable.Creator<android.os.UserHandle>() { // from class: android.os.UserHandle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.UserHandle createFromParcel(android.os.Parcel parcel) {
                return android.os.UserHandle.of(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.UserHandle[] newArray(int i2) {
                return new android.os.UserHandle[i2];
            }
        };
    }

    public static boolean isSameUser(int i, int i2) {
        return getUserId(i) == getUserId(i2);
    }

    public static boolean isSameApp(int i, int i2) {
        return getAppId(i) == getAppId(i2);
    }

    public static boolean isIsolated(int i) {
        if (i > 0) {
            return android.os.Process.isIsolated(i);
        }
        return false;
    }

    public static boolean isApp(int i) {
        int appId;
        return i > 0 && (appId = getAppId(i)) >= 10000 && appId <= 19999;
    }

    public static boolean isCore(int i) {
        return i >= 0 && getAppId(i) < 10000;
    }

    public static boolean isSharedAppGid(int i) {
        return getAppIdFromSharedAppGid(i) != -1;
    }

    public static android.os.UserHandle getUserHandleForUid(int i) {
        return of(getUserId(i));
    }

    public static int getUserId(int i) {
        return i / 100000;
    }

    public static int getCallingUserId() {
        return getUserId(android.os.Binder.getCallingUid());
    }

    public static int getCallingAppId() {
        return getAppId(android.os.Binder.getCallingUid());
    }

    public static int[] fromUserHandles(java.util.List<android.os.UserHandle> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = list.get(i).getIdentifier();
        }
        return iArr;
    }

    public static java.util.List<android.os.UserHandle> toUserHandles(int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(of(i));
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public static android.os.UserHandle of(int i) {
        if (i == 0) {
            return SYSTEM;
        }
        switch (i) {
            case -3:
                return CURRENT_OR_SELF;
            case -2:
                return CURRENT;
            case -1:
                return ALL;
            default:
                if (i >= 10 && i < CACHED_USER_HANDLES.length + 10) {
                    return CACHED_USER_HANDLES[i - 10];
                }
                if (i == -10000) {
                    return NULL;
                }
                return getUserHandleFromExtraCache(i);
        }
    }

    public static android.os.UserHandle getUserHandleFromExtraCache(int i) {
        synchronized (sExtraUserHandleCache) {
            android.os.UserHandle userHandle = sExtraUserHandleCache.get(i);
            if (userHandle != null) {
                return userHandle;
            }
            if (sExtraUserHandleCache.size() >= 32) {
                sExtraUserHandleCache.removeAt(new java.util.Random().nextInt(32));
            }
            android.os.UserHandle userHandle2 = new android.os.UserHandle(i);
            sExtraUserHandleCache.put(i, userHandle2);
            return userHandle2;
        }
    }

    public static int getUid(int i, int i2) {
        if (i2 >= 0) {
            return (i * 100000) + (i2 % 100000);
        }
        return i2;
    }

    @android.annotation.SystemApi
    public int getUid(int i) {
        return getUid(getIdentifier(), i);
    }

    @android.annotation.SystemApi
    public static int getAppId(int i) {
        return i % 100000;
    }

    public static int getUserGid(int i) {
        return getUid(i, android.os.Process.SHARED_USER_GID);
    }

    @android.annotation.SystemApi
    public static int getSharedAppGid(int i) {
        return getSharedAppGid(getUserId(i), getAppId(i));
    }

    public static int getSharedAppGid(int i, int i2) {
        if (i2 >= 10000 && i2 <= 19999) {
            return (i2 - 10000) + 50000;
        }
        if (i2 >= 0 && i2 <= 10000) {
            return i2;
        }
        return -1;
    }

    public static int getAppIdFromSharedAppGid(int i) {
        int appId = (getAppId(i) + 10000) - 50000;
        if (appId < 0 || appId >= 50000) {
            return -1;
        }
        return appId;
    }

    public static int getCacheAppGid(int i) {
        return getCacheAppGid(getUserId(i), getAppId(i));
    }

    public static int getCacheAppGid(int i, int i2) {
        if (i2 >= 10000 && i2 <= 19999) {
            return getUid(i, (i2 - 10000) + 20000);
        }
        return -1;
    }

    public static void formatUid(java.lang.StringBuilder sb, int i) {
        if (i < 10000) {
            sb.append(i);
            return;
        }
        sb.append('u');
        sb.append(getUserId(i));
        int appId = getAppId(i);
        if (isIsolated(appId)) {
            if (appId > 99000) {
                sb.append('i');
                sb.append(appId - android.os.Process.FIRST_ISOLATED_UID);
                return;
            } else {
                sb.append("ai");
                sb.append(appId - android.os.Process.FIRST_APP_ZYGOTE_ISOLATED_UID);
                return;
            }
        }
        if (appId >= 10000) {
            sb.append(android.text.format.DateFormat.AM_PM);
            sb.append(appId - 10000);
        } else {
            sb.append('s');
            sb.append(appId);
        }
    }

    @android.annotation.SystemApi
    public static java.lang.String formatUid(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        formatUid(sb, i);
        return sb.toString();
    }

    public static void formatUid(java.io.PrintWriter printWriter, int i) {
        if (i < 10000) {
            printWriter.print(i);
            return;
        }
        printWriter.print('u');
        printWriter.print(getUserId(i));
        int appId = getAppId(i);
        if (isIsolated(appId)) {
            if (appId > 99000) {
                printWriter.print('i');
                printWriter.print(appId - android.os.Process.FIRST_ISOLATED_UID);
                return;
            } else {
                printWriter.print("ai");
                printWriter.print(appId - android.os.Process.FIRST_APP_ZYGOTE_ISOLATED_UID);
                return;
            }
        }
        if (appId >= 10000) {
            printWriter.print(android.text.format.DateFormat.AM_PM);
            printWriter.print(appId - 10000);
        } else {
            printWriter.print('s');
            printWriter.print(appId);
        }
    }

    public static int parseUserArg(java.lang.String str) {
        if ("all".equals(str)) {
            return -1;
        }
        if (android.provider.Telephony.Carriers.CURRENT.equals(str) || "cur".equals(str)) {
            return -2;
        }
        try {
            return java.lang.Integer.parseInt(str);
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("Bad user number: " + str);
        }
    }

    @android.annotation.SystemApi
    public static int myUserId() {
        return getUserId(android.os.Process.myUid());
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isOwner() {
        return equals(OWNER);
    }

    @android.annotation.SystemApi
    public boolean isSystem() {
        return equals(SYSTEM);
    }

    public UserHandle(int i) {
        this.mHandle = i;
    }

    @android.annotation.SystemApi
    public int getIdentifier() {
        return this.mHandle;
    }

    public java.lang.String toString() {
        return "UserHandle{" + this.mHandle + "}";
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.os.UserHandle) && this.mHandle == ((android.os.UserHandle) obj).mHandle;
    }

    public int hashCode() {
        return this.mHandle;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
    }

    public static void writeToParcel(android.os.UserHandle userHandle, android.os.Parcel parcel) {
        if (userHandle != null) {
            userHandle.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(-10000);
        }
    }

    public static android.os.UserHandle readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt != -10000) {
            return new android.os.UserHandle(readInt);
        }
        return null;
    }

    public UserHandle(android.os.Parcel parcel) {
        this.mHandle = parcel.readInt();
    }
}
