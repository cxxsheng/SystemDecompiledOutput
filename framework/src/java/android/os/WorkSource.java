package android.os;

/* loaded from: classes3.dex */
public class WorkSource implements android.os.Parcelable {
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "WorkSource";
    static android.os.WorkSource sGoneWork;
    static android.os.WorkSource sNewbWork;
    private java.util.ArrayList<android.os.WorkSource.WorkChain> mChains;
    java.lang.String[] mNames;
    int mNum;
    int[] mUids;
    static final android.os.WorkSource sTmpWorkSource = new android.os.WorkSource(0);
    public static final android.os.Parcelable.Creator<android.os.WorkSource> CREATOR = new android.os.Parcelable.Creator<android.os.WorkSource>() { // from class: android.os.WorkSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WorkSource createFromParcel(android.os.Parcel parcel) {
            return new android.os.WorkSource(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WorkSource[] newArray(int i) {
            return new android.os.WorkSource[i];
        }
    };

    public WorkSource() {
        this.mUids = new int[0];
        this.mNum = 0;
        this.mChains = null;
    }

    public WorkSource(android.os.WorkSource workSource) {
        this.mUids = new int[0];
        if (workSource == null) {
            this.mNum = 0;
            this.mChains = null;
            return;
        }
        this.mNum = workSource.mNum;
        this.mUids = (int[]) workSource.mUids.clone();
        this.mNames = workSource.mNames != null ? (java.lang.String[]) workSource.mNames.clone() : null;
        if (workSource.mChains != null) {
            this.mChains = new java.util.ArrayList<>(workSource.mChains.size());
            java.util.Iterator<android.os.WorkSource.WorkChain> it = workSource.mChains.iterator();
            while (it.hasNext()) {
                this.mChains.add(new android.os.WorkSource.WorkChain(it.next()));
            }
            return;
        }
        this.mChains = null;
    }

    @android.annotation.SystemApi
    public WorkSource(int i) {
        this.mUids = new int[0];
        this.mNum = 1;
        this.mUids = new int[]{i, 0};
        this.mNames = null;
        this.mChains = null;
    }

    @android.annotation.SystemApi
    public WorkSource(int i, java.lang.String str) {
        this.mUids = new int[0];
        java.util.Objects.requireNonNull(str, "packageName can't be null");
        this.mNum = 1;
        this.mUids = new int[]{i, 0};
        this.mNames = new java.lang.String[]{str, null};
        this.mChains = null;
    }

    WorkSource(android.os.Parcel parcel) {
        this.mUids = new int[0];
        this.mNum = parcel.readInt();
        this.mUids = (int[]) java.util.Objects.requireNonNullElse(parcel.createIntArray(), new int[0]);
        this.mNames = parcel.createStringArray();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.mChains = new java.util.ArrayList<>(readInt);
            parcel.readParcelableList(this.mChains, android.os.WorkSource.WorkChain.class.getClassLoader(), android.os.WorkSource.WorkChain.class);
        } else {
            this.mChains = null;
        }
    }

    public static boolean isChainedBatteryAttributionEnabled(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.CHAINED_BATTERY_ATTRIBUTION_ENABLED, 0) == 1;
    }

    public static boolean isChainedBatteryAttributionEnabled$ravenwood(android.content.Context context) {
        return false;
    }

    @android.annotation.SystemApi
    public int size() {
        return this.mNum;
    }

    @java.lang.Deprecated
    public int get(int i) {
        return getUid(i);
    }

    @android.annotation.SystemApi
    public int getUid(int i) {
        return this.mUids[i];
    }

    public int getAttributionUid() {
        if (isEmpty()) {
            return -1;
        }
        return this.mNum > 0 ? this.mUids[0] : this.mChains.get(0).getAttributionUid();
    }

    @java.lang.Deprecated
    public java.lang.String getName(int i) {
        return getPackageName(i);
    }

    @android.annotation.SystemApi
    public java.lang.String getPackageName(int i) {
        if (this.mNames != null) {
            return this.mNames[i];
        }
        return null;
    }

    private void clearNames() {
        if (this.mNames != null) {
            this.mNames = null;
            int i = this.mNum;
            int i2 = 1;
            for (int i3 = 1; i3 < this.mNum; i3++) {
                if (this.mUids[i3] == this.mUids[i3 - 1]) {
                    i--;
                } else {
                    this.mUids[i2] = this.mUids[i3];
                    i2++;
                }
            }
            this.mNum = i;
        }
    }

    public void clear() {
        this.mNum = 0;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.WorkSource)) {
            return false;
        }
        android.os.WorkSource workSource = (android.os.WorkSource) obj;
        if (diff(workSource)) {
            return false;
        }
        if (this.mChains == null || this.mChains.isEmpty()) {
            return workSource.mChains == null || workSource.mChains.isEmpty();
        }
        return this.mChains.equals(workSource.mChains);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this.mNum; i2++) {
            i = ((i >>> 28) | (i << 4)) ^ this.mUids[i2];
        }
        if (this.mNames != null) {
            for (int i3 = 0; i3 < this.mNum; i3++) {
                i = this.mNames[i3].hashCode() ^ ((i << 4) | (i >>> 28));
            }
        }
        if (this.mChains != null) {
            return ((i << 4) | (i >>> 28)) ^ this.mChains.hashCode();
        }
        return i;
    }

    public boolean diff(android.os.WorkSource workSource) {
        int i = this.mNum;
        if (i != workSource.mNum) {
            return true;
        }
        int[] iArr = this.mUids;
        int[] iArr2 = workSource.mUids;
        java.lang.String[] strArr = this.mNames;
        java.lang.String[] strArr2 = workSource.mNames;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return true;
            }
            if (strArr != null && strArr2 != null && !strArr[i2].equals(strArr2[i2])) {
                return true;
            }
        }
        return false;
    }

    public void set(android.os.WorkSource workSource) {
        if (workSource == null) {
            clear();
            return;
        }
        this.mNum = workSource.mNum;
        if (this.mUids.length >= this.mNum) {
            java.lang.System.arraycopy(workSource.mUids, 0, this.mUids, 0, this.mNum);
        } else {
            this.mUids = (int[]) workSource.mUids.clone();
        }
        if (workSource.mNames != null) {
            if (this.mNames != null && this.mNames.length >= this.mNum) {
                java.lang.System.arraycopy(workSource.mNames, 0, this.mNames, 0, this.mNum);
            } else {
                this.mNames = (java.lang.String[]) workSource.mNames.clone();
            }
        } else {
            this.mNames = null;
        }
        if (workSource.mChains != null) {
            if (this.mChains != null) {
                this.mChains.clear();
            } else {
                this.mChains = new java.util.ArrayList<>(workSource.mChains.size());
            }
            java.util.Iterator<android.os.WorkSource.WorkChain> it = workSource.mChains.iterator();
            while (it.hasNext()) {
                this.mChains.add(new android.os.WorkSource.WorkChain(it.next()));
            }
        }
    }

    public void set(int i) {
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
        }
        this.mUids[0] = i;
        this.mNames = null;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    public void set(int i, java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("Name can't be null");
        }
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
            this.mNames = new java.lang.String[2];
        }
        this.mUids[0] = i;
        this.mNames[0] = str;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    @java.lang.Deprecated
    public android.os.WorkSource[] setReturningDiffs(android.os.WorkSource workSource) {
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            sGoneWork = null;
            updateLocked(workSource, true, true);
            if (sNewbWork == null && sGoneWork == null) {
                return null;
            }
            return new android.os.WorkSource[]{sNewbWork, sGoneWork};
        }
    }

    public boolean add(android.os.WorkSource workSource) {
        boolean z;
        boolean z2;
        synchronized (sTmpWorkSource) {
            boolean updateLocked = updateLocked(workSource, false, false);
            if (workSource.mChains == null) {
                z = false;
            } else {
                if (this.mChains == null) {
                    this.mChains = new java.util.ArrayList<>(workSource.mChains.size());
                }
                java.util.Iterator<android.os.WorkSource.WorkChain> it = workSource.mChains.iterator();
                z = false;
                while (it.hasNext()) {
                    android.os.WorkSource.WorkChain next = it.next();
                    if (!this.mChains.contains(next)) {
                        this.mChains.add(new android.os.WorkSource.WorkChain(next));
                        z = true;
                    }
                }
            }
            z2 = updateLocked || z;
        }
        return z2;
    }

    @android.annotation.SystemApi
    public android.os.WorkSource withoutNames() {
        android.os.WorkSource workSource = new android.os.WorkSource(this);
        workSource.clearNames();
        return workSource;
    }

    @java.lang.Deprecated
    public android.os.WorkSource addReturningNewbs(android.os.WorkSource workSource) {
        android.os.WorkSource workSource2;
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            updateLocked(workSource, false, true);
            workSource2 = sNewbWork;
        }
        return workSource2;
    }

    public boolean add(int i) {
        if (this.mNum <= 0) {
            this.mNames = null;
            insert(0, i);
            return true;
        }
        if (this.mNames != null) {
            throw new java.lang.IllegalArgumentException("Adding without name to named " + this);
        }
        int binarySearch = java.util.Arrays.binarySearch(this.mUids, 0, this.mNum, i);
        if (binarySearch >= 0) {
            return false;
        }
        insert((-binarySearch) - 1, i);
        return true;
    }

    public boolean add(int i, java.lang.String str) {
        if (this.mNum <= 0) {
            insert(0, i, str);
            return true;
        }
        if (this.mNames == null) {
            throw new java.lang.IllegalArgumentException("Adding name to unnamed " + this);
        }
        int i2 = 0;
        while (i2 < this.mNum && this.mUids[i2] <= i) {
            if (this.mUids[i2] == i) {
                int compareTo = this.mNames[i2].compareTo(str);
                if (compareTo > 0) {
                    break;
                }
                if (compareTo == 0) {
                    return false;
                }
            }
            i2++;
        }
        insert(i2, i, str);
        return true;
    }

    public boolean remove(android.os.WorkSource workSource) {
        boolean removeUidsAndNames;
        boolean z;
        if (isEmpty() || workSource.isEmpty()) {
            return false;
        }
        if (this.mNames == null && workSource.mNames == null) {
            removeUidsAndNames = removeUids(workSource);
        } else {
            if (this.mNames == null) {
                throw new java.lang.IllegalArgumentException("Other " + workSource + " has names, but target " + this + " does not");
            }
            if (workSource.mNames == null) {
                throw new java.lang.IllegalArgumentException("Target " + this + " has names, but other " + workSource + " does not");
            }
            removeUidsAndNames = removeUidsAndNames(workSource);
        }
        if (workSource.mChains != null && this.mChains != null) {
            z = this.mChains.removeAll(workSource.mChains);
        } else {
            z = false;
        }
        return removeUidsAndNames || z;
    }

    @android.annotation.SystemApi
    public android.os.WorkSource.WorkChain createWorkChain() {
        if (this.mChains == null) {
            this.mChains = new java.util.ArrayList<>(4);
        }
        android.os.WorkSource.WorkChain workChain = new android.os.WorkSource.WorkChain();
        this.mChains.add(workChain);
        return workChain;
    }

    @android.annotation.SystemApi
    public boolean isEmpty() {
        return this.mNum == 0 && (this.mChains == null || this.mChains.isEmpty());
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.WorkSource.WorkChain> getWorkChains() {
        return this.mChains;
    }

    public void transferWorkChains(android.os.WorkSource workSource) {
        if (this.mChains != null) {
            this.mChains.clear();
        }
        if (workSource.mChains == null || workSource.mChains.isEmpty()) {
            return;
        }
        if (this.mChains == null) {
            this.mChains = new java.util.ArrayList<>(4);
        }
        this.mChains.addAll(workSource.mChains);
        workSource.mChains.clear();
    }

    private boolean removeUids(android.os.WorkSource workSource) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        while (i3 < i && i4 < i2) {
            if (iArr2[i4] == iArr[i3]) {
                i--;
                if (i3 < i) {
                    java.lang.System.arraycopy(iArr, i3 + 1, iArr, i3, i - i3);
                }
                i4++;
                z = true;
            } else if (iArr2[i4] > iArr[i3]) {
                i3++;
            } else {
                i4++;
            }
        }
        this.mNum = i;
        return z;
    }

    private boolean removeUidsAndNames(android.os.WorkSource workSource) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        java.lang.String[] strArr = this.mNames;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        java.lang.String[] strArr2 = workSource.mNames;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        while (i3 < i && i4 < i2) {
            if (iArr2[i4] == iArr[i3] && strArr2[i4].equals(strArr[i3])) {
                i--;
                if (i3 < i) {
                    int i5 = i3 + 1;
                    int i6 = i - i3;
                    java.lang.System.arraycopy(iArr, i5, iArr, i3, i6);
                    java.lang.System.arraycopy(strArr, i5, strArr, i3, i6);
                }
                i4++;
                z = true;
            } else if (iArr2[i4] > iArr[i3] || (iArr2[i4] == iArr[i3] && strArr2[i4].compareTo(strArr[i3]) > 0)) {
                i3++;
            } else {
                i4++;
            }
        }
        this.mNum = i;
        return z;
    }

    private boolean updateLocked(android.os.WorkSource workSource, boolean z, boolean z2) {
        if (this.mNames == null && workSource.mNames == null) {
            return updateUidsLocked(workSource, z, z2);
        }
        if (this.mNum > 0 && this.mNames == null) {
            throw new java.lang.IllegalArgumentException("Other " + workSource + " has names, but target " + this + " does not");
        }
        if (workSource.mNum > 0 && workSource.mNames == null) {
            throw new java.lang.IllegalArgumentException("Target " + this + " has names, but other " + workSource + " does not");
        }
        return updateUidsAndNamesLocked(workSource, z, z2);
    }

    private static android.os.WorkSource addWork(android.os.WorkSource workSource, int i) {
        if (workSource == null) {
            return new android.os.WorkSource(i);
        }
        workSource.insert(workSource.mNum, i);
        return workSource;
    }

    private boolean updateUidsLocked(android.os.WorkSource workSource, boolean z, boolean z2) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        int i3 = 0;
        int i4 = 0;
        boolean z3 = false;
        while (true) {
            if (i3 < i || i4 < i2) {
                if (i3 >= i || (i4 < i2 && iArr2[i4] < iArr[i3])) {
                    if (iArr.length == 0) {
                        iArr = new int[4];
                        iArr[0] = iArr2[i4];
                    } else if (i >= iArr.length) {
                        int[] iArr3 = new int[(iArr.length * 3) / 2];
                        if (i3 > 0) {
                            java.lang.System.arraycopy(iArr, 0, iArr3, 0, i3);
                        }
                        if (i3 < i) {
                            java.lang.System.arraycopy(iArr, i3, iArr3, i3 + 1, i - i3);
                        }
                        iArr3[i3] = iArr2[i4];
                        iArr = iArr3;
                    } else {
                        if (i3 < i) {
                            java.lang.System.arraycopy(iArr, i3, iArr, i3 + 1, i - i3);
                        }
                        iArr[i3] = iArr2[i4];
                    }
                    if (z2) {
                        sNewbWork = addWork(sNewbWork, iArr2[i4]);
                    }
                    i++;
                    i3++;
                    i4++;
                    z3 = true;
                } else if (!z) {
                    if (i4 < i2 && iArr2[i4] == iArr[i3]) {
                        i4++;
                    }
                    i3++;
                } else {
                    int i5 = i3;
                    while (i5 < i && (i4 >= i2 || iArr2[i4] > iArr[i5])) {
                        sGoneWork = addWork(sGoneWork, iArr[i5]);
                        i5++;
                    }
                    if (i3 >= i5) {
                        i3 = i5;
                    } else {
                        java.lang.System.arraycopy(iArr, i5, iArr, i3, i - i5);
                        i -= i5 - i3;
                    }
                    if (i3 < i && i4 < i2 && iArr2[i4] == iArr[i3]) {
                        i3++;
                        i4++;
                    }
                }
            } else {
                this.mNum = i;
                this.mUids = iArr;
                return z3;
            }
        }
    }

    private int compare(android.os.WorkSource workSource, int i, int i2) {
        int i3 = this.mUids[i] - workSource.mUids[i2];
        if (i3 != 0) {
            return i3;
        }
        return this.mNames[i].compareTo(workSource.mNames[i2]);
    }

    private static android.os.WorkSource addWork(android.os.WorkSource workSource, int i, java.lang.String str) {
        if (workSource == null) {
            return new android.os.WorkSource(i, str);
        }
        workSource.insert(workSource.mNum, i, str);
        return workSource;
    }

    private boolean updateUidsAndNamesLocked(android.os.WorkSource workSource, boolean z, boolean z2) {
        int i;
        int i2 = workSource.mNum;
        int[] iArr = workSource.mUids;
        java.lang.String[] strArr = workSource.mNames;
        int i3 = 0;
        int i4 = 0;
        boolean z3 = false;
        while (true) {
            if (i3 < this.mNum || i4 < i2) {
                if (i3 < this.mNum) {
                    if (i4 < i2) {
                        i = compare(workSource, i3, i4);
                        if (i > 0) {
                        }
                    } else {
                        i = -1;
                    }
                    if (!z) {
                        if (i4 < i2 && i == 0) {
                            i4++;
                        }
                        i3++;
                    } else {
                        int i5 = i3;
                        while (i < 0) {
                            sGoneWork = addWork(sGoneWork, this.mUids[i5], this.mNames[i5]);
                            i5++;
                            if (i5 >= this.mNum) {
                                break;
                            }
                            i = i4 < i2 ? compare(workSource, i5, i4) : -1;
                        }
                        if (i3 >= i5) {
                            i3 = i5;
                        } else {
                            java.lang.System.arraycopy(this.mUids, i5, this.mUids, i3, this.mNum - i5);
                            java.lang.System.arraycopy(this.mNames, i5, this.mNames, i3, this.mNum - i5);
                            this.mNum -= i5 - i3;
                        }
                        if (i3 < this.mNum && i == 0) {
                            i3++;
                            i4++;
                        }
                    }
                }
                insert(i3, iArr[i4], strArr[i4]);
                if (z2) {
                    sNewbWork = addWork(sNewbWork, iArr[i4], strArr[i4]);
                }
                i3++;
                i4++;
                z3 = true;
            } else {
                return z3;
            }
        }
    }

    private void insert(int i, int i2) {
        if (this.mUids.length == 0) {
            this.mUids = new int[4];
            this.mUids[0] = i2;
            this.mNum = 1;
        } else {
            if (this.mNum >= this.mUids.length) {
                int[] iArr = new int[(this.mNum * 3) / 2];
                if (i > 0) {
                    java.lang.System.arraycopy(this.mUids, 0, iArr, 0, i);
                }
                if (i < this.mNum) {
                    java.lang.System.arraycopy(this.mUids, i, iArr, i + 1, this.mNum - i);
                }
                this.mUids = iArr;
                this.mUids[i] = i2;
                this.mNum++;
                return;
            }
            if (i < this.mNum) {
                java.lang.System.arraycopy(this.mUids, i, this.mUids, i + 1, this.mNum - i);
            }
            this.mUids[i] = i2;
            this.mNum++;
        }
    }

    private void insert(int i, int i2, java.lang.String str) {
        if (this.mNum == 0) {
            this.mUids = new int[4];
            this.mUids[0] = i2;
            this.mNames = new java.lang.String[4];
            this.mNames[0] = str;
            this.mNum = 1;
            return;
        }
        if (this.mNum >= this.mUids.length) {
            int[] iArr = new int[(this.mNum * 3) / 2];
            java.lang.String[] strArr = new java.lang.String[(this.mNum * 3) / 2];
            if (i > 0) {
                java.lang.System.arraycopy(this.mUids, 0, iArr, 0, i);
                java.lang.System.arraycopy(this.mNames, 0, strArr, 0, i);
            }
            if (i < this.mNum) {
                int i3 = i + 1;
                java.lang.System.arraycopy(this.mUids, i, iArr, i3, this.mNum - i);
                java.lang.System.arraycopy(this.mNames, i, strArr, i3, this.mNum - i);
            }
            this.mUids = iArr;
            this.mNames = strArr;
            this.mUids[i] = i2;
            this.mNames[i] = str;
            this.mNum++;
            return;
        }
        if (i < this.mNum) {
            int i4 = i + 1;
            java.lang.System.arraycopy(this.mUids, i, this.mUids, i4, this.mNum - i);
            java.lang.System.arraycopy(this.mNames, i, this.mNames, i4, this.mNum - i);
        }
        this.mUids[i] = i2;
        this.mNames[i] = str;
        this.mNum++;
    }

    @android.annotation.SystemApi
    public static final class WorkChain implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.WorkSource.WorkChain> CREATOR = new android.os.Parcelable.Creator<android.os.WorkSource.WorkChain>() { // from class: android.os.WorkSource.WorkChain.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.WorkSource.WorkChain createFromParcel(android.os.Parcel parcel) {
                return new android.os.WorkSource.WorkChain(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.WorkSource.WorkChain[] newArray(int i) {
                return new android.os.WorkSource.WorkChain[i];
            }
        };
        private int mSize;
        private java.lang.String[] mTags;
        private int[] mUids;

        public WorkChain() {
            this.mSize = 0;
            this.mUids = new int[4];
            this.mTags = new java.lang.String[4];
        }

        public WorkChain(android.os.WorkSource.WorkChain workChain) {
            this.mSize = workChain.mSize;
            this.mUids = (int[]) workChain.mUids.clone();
            this.mTags = (java.lang.String[]) workChain.mTags.clone();
        }

        private WorkChain(android.os.Parcel parcel) {
            this.mSize = parcel.readInt();
            this.mUids = parcel.createIntArray();
            this.mTags = parcel.createStringArray();
        }

        public android.os.WorkSource.WorkChain addNode(int i, java.lang.String str) {
            if (this.mSize == this.mUids.length) {
                resizeArrays();
            }
            this.mUids[this.mSize] = i;
            this.mTags[this.mSize] = str;
            this.mSize++;
            return this;
        }

        public int getAttributionUid() {
            if (this.mSize > 0) {
                return this.mUids[0];
            }
            return -1;
        }

        public java.lang.String getAttributionTag() {
            if (this.mTags.length > 0) {
                return this.mTags[0];
            }
            return null;
        }

        public int[] getUids() {
            int[] iArr = new int[this.mSize];
            java.lang.System.arraycopy(this.mUids, 0, iArr, 0, this.mSize);
            return iArr;
        }

        public java.lang.String[] getTags() {
            java.lang.String[] strArr = new java.lang.String[this.mSize];
            java.lang.System.arraycopy(this.mTags, 0, strArr, 0, this.mSize);
            return strArr;
        }

        public int getSize() {
            return this.mSize;
        }

        private void resizeArrays() {
            int i = this.mSize * 2;
            int[] iArr = new int[i];
            java.lang.String[] strArr = new java.lang.String[i];
            java.lang.System.arraycopy(this.mUids, 0, iArr, 0, this.mSize);
            java.lang.System.arraycopy(this.mTags, 0, strArr, 0, this.mSize);
            this.mUids = iArr;
            this.mTags = strArr;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("WorkChain{");
            for (int i = 0; i < this.mSize; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb.append(this.mUids[i]);
                if (this.mTags[i] != null) {
                    sb.append(", ");
                    sb.append(this.mTags[i]);
                }
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            sb.append("}");
            return sb.toString();
        }

        public int hashCode() {
            return ((this.mSize + (java.util.Arrays.hashCode(this.mUids) * 31)) * 31) + java.util.Arrays.hashCode(this.mTags);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.os.WorkSource.WorkChain)) {
                return false;
            }
            android.os.WorkSource.WorkChain workChain = (android.os.WorkSource.WorkChain) obj;
            return this.mSize == workChain.mSize && java.util.Arrays.equals(this.mUids, workChain.mUids) && java.util.Arrays.equals(this.mTags, workChain.mTags);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mSize);
            parcel.writeIntArray(this.mUids);
            parcel.writeStringArray(this.mTags);
        }
    }

    public static java.util.ArrayList<android.os.WorkSource.WorkChain>[] diffChains(android.os.WorkSource workSource, android.os.WorkSource workSource2) {
        java.util.ArrayList<android.os.WorkSource.WorkChain> arrayList;
        java.util.ArrayList<android.os.WorkSource.WorkChain> arrayList2;
        if (workSource.mChains == null) {
            arrayList = null;
        } else {
            arrayList = null;
            for (int i = 0; i < workSource.mChains.size(); i++) {
                android.os.WorkSource.WorkChain workChain = workSource.mChains.get(i);
                if (workSource2.mChains == null || !workSource2.mChains.contains(workChain)) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList<>(workSource.mChains.size());
                    }
                    arrayList.add(workChain);
                }
            }
        }
        if (workSource2.mChains == null) {
            arrayList2 = null;
        } else {
            arrayList2 = null;
            for (int i2 = 0; i2 < workSource2.mChains.size(); i2++) {
                android.os.WorkSource.WorkChain workChain2 = workSource2.mChains.get(i2);
                if (workSource.mChains == null || !workSource.mChains.contains(workChain2)) {
                    if (arrayList2 == null) {
                        arrayList2 = new java.util.ArrayList<>(workSource2.mChains.size());
                    }
                    arrayList2.add(workChain2);
                }
            }
        }
        if (arrayList2 == null && arrayList == null) {
            return null;
        }
        return new java.util.ArrayList[]{arrayList2, arrayList};
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNum);
        parcel.writeIntArray(this.mUids);
        parcel.writeStringArray(this.mNames);
        if (this.mChains == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(this.mChains.size());
            parcel.writeParcelableList(this.mChains, i);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("WorkSource{");
        for (int i = 0; i < this.mNum; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mUids[i]);
            if (this.mNames != null) {
                sb.append(" ");
                sb.append(this.mNames[i]);
            }
        }
        if (this.mChains != null) {
            sb.append(" chains=");
            for (int i2 = 0; i2 < this.mChains.size(); i2++) {
                if (i2 != 0) {
                    sb.append(", ");
                }
                sb.append(this.mChains.get(i2));
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long j2;
        long j3;
        long start = protoOutputStream.start(j);
        int i = 0;
        while (true) {
            j2 = 1120986464257L;
            j3 = 2246267895809L;
            if (i >= this.mNum) {
                break;
            }
            long start2 = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, this.mUids[i]);
            if (this.mNames != null) {
                protoOutputStream.write(1138166333442L, this.mNames[i]);
            }
            protoOutputStream.end(start2);
            i++;
        }
        if (this.mChains != null) {
            int i2 = 0;
            while (i2 < this.mChains.size()) {
                android.os.WorkSource.WorkChain workChain = this.mChains.get(i2);
                long start3 = protoOutputStream.start(2246267895810L);
                java.lang.String[] tags = workChain.getTags();
                int[] uids = workChain.getUids();
                int i3 = 0;
                while (i3 < tags.length) {
                    long start4 = protoOutputStream.start(j3);
                    protoOutputStream.write(j2, uids[i3]);
                    protoOutputStream.write(1138166333442L, tags[i3]);
                    protoOutputStream.end(start4);
                    i3++;
                    j2 = 1120986464257L;
                    j3 = 2246267895809L;
                }
                protoOutputStream.end(start3);
                i2++;
                j2 = 1120986464257L;
                j3 = 2246267895809L;
            }
        }
        protoOutputStream.end(start);
    }
}
