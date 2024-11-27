package android.app;

/* loaded from: classes.dex */
final class BackStackRecord extends android.app.FragmentTransaction implements android.app.FragmentManager.BackStackEntry, android.app.FragmentManagerImpl.OpGenerator {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    static final java.lang.String TAG = "FragmentManager";
    boolean mAddToBackStack;
    int mBreadCrumbShortTitleRes;
    java.lang.CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    java.lang.CharSequence mBreadCrumbTitleText;
    java.util.ArrayList<java.lang.Runnable> mCommitRunnables;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    final android.app.FragmentManagerImpl mManager;
    java.lang.String mName;
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed;
    java.util.ArrayList<java.lang.String> mSharedElementSourceNames;
    java.util.ArrayList<java.lang.String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;
    java.util.ArrayList<android.app.BackStackRecord.Op> mOps = new java.util.ArrayList<>();
    boolean mAllowAddToBackStack = true;
    int mIndex = -1;

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        android.app.Fragment fragment;
        int popEnterAnim;
        int popExitAnim;

        Op() {
        }

        Op(int i, android.app.Fragment fragment) {
            this.cmd = i;
            this.fragment = fragment;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mName != null) {
            sb.append(" ");
            sb.append(this.mName);
        }
        sb.append("}");
        return sb.toString();
    }

    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        dump(str, printWriter, true);
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter, boolean z) {
        java.lang.String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(java.lang.Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(java.lang.Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(java.lang.Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(java.lang.Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(java.lang.Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(java.lang.Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(java.lang.Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(java.lang.Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (!this.mOps.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            java.lang.String str3 = str + "    ";
            int size = this.mOps.size();
            for (int i = 0; i < size; i++) {
                android.app.BackStackRecord.Op op = this.mOps.get(i);
                switch (op.cmd) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    default:
                        str2 = "cmd=" + op.cmd;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.println(op.fragment);
                if (z) {
                    if (op.enterAnim != 0 || op.exitAnim != 0) {
                        printWriter.print(str3);
                        printWriter.print("enterAnim=#");
                        printWriter.print(java.lang.Integer.toHexString(op.enterAnim));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(java.lang.Integer.toHexString(op.exitAnim));
                    }
                    if (op.popEnterAnim != 0 || op.popExitAnim != 0) {
                        printWriter.print(str3);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(java.lang.Integer.toHexString(op.popEnterAnim));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(java.lang.Integer.toHexString(op.popExitAnim));
                    }
                }
            }
        }
    }

    public BackStackRecord(android.app.FragmentManagerImpl fragmentManagerImpl) {
        this.mManager = fragmentManagerImpl;
        this.mReorderingAllowed = this.mManager.getTargetSdk() > 25;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public int getId() {
        return this.mIndex;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public java.lang.CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes != 0 && this.mManager.mHost != null) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public java.lang.CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes != 0 && this.mManager.mHost != null) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    void addOp(android.app.BackStackRecord.Op op) {
        this.mOps.add(op);
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction add(android.app.Fragment fragment, java.lang.String str) {
        doAddOp(0, fragment, str, 1);
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction add(int i, android.app.Fragment fragment) {
        doAddOp(i, fragment, null, 1);
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction add(int i, android.app.Fragment fragment, java.lang.String str) {
        doAddOp(i, fragment, str, 1);
        return this;
    }

    private void doAddOp(int i, android.app.Fragment fragment, java.lang.String str, int i2) {
        if (this.mManager.getTargetSdk() > 25) {
            java.lang.Class<?> cls = fragment.getClass();
            int modifiers = cls.getModifiers();
            if (cls.isAnonymousClass() || !java.lang.reflect.Modifier.isPublic(modifiers) || (cls.isMemberClass() && !java.lang.reflect.Modifier.isStatic(modifiers))) {
                throw new java.lang.IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
            }
        }
        fragment.mFragmentManager = this.mManager;
        if (str != null) {
            if (fragment.mTag != null && !str.equals(fragment.mTag)) {
                throw new java.lang.IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str);
            }
            fragment.mTag = str;
        }
        if (i != 0) {
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != i) {
                throw new java.lang.IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i);
            }
            fragment.mFragmentId = i;
            fragment.mContainerId = i;
        }
        addOp(new android.app.BackStackRecord.Op(i2, fragment));
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction replace(int i, android.app.Fragment fragment) {
        return replace(i, fragment, null);
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction replace(int i, android.app.Fragment fragment, java.lang.String str) {
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Must use non-zero containerViewId");
        }
        doAddOp(i, fragment, str, 2);
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction remove(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(3, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction hide(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(4, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction show(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(5, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction detach(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(6, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction attach(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(7, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setPrimaryNavigationFragment(android.app.Fragment fragment) {
        addOp(new android.app.BackStackRecord.Op(8, fragment));
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setCustomAnimations(int i, int i2) {
        return setCustomAnimations(i, i2, 0, 0);
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setCustomAnimations(int i, int i2, int i3, int i4) {
        this.mEnterAnim = i;
        this.mExitAnim = i2;
        this.mPopEnterAnim = i3;
        this.mPopExitAnim = i4;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setTransition(int i) {
        this.mTransition = i;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction addSharedElement(android.view.View view, java.lang.String str) {
        java.lang.String transitionName = view.getTransitionName();
        if (transitionName == null) {
            throw new java.lang.IllegalArgumentException("Unique transitionNames are required for all sharedElements");
        }
        if (this.mSharedElementSourceNames == null) {
            this.mSharedElementSourceNames = new java.util.ArrayList<>();
            this.mSharedElementTargetNames = new java.util.ArrayList<>();
        } else {
            if (this.mSharedElementTargetNames.contains(str)) {
                throw new java.lang.IllegalArgumentException("A shared element with the target name '" + str + "' has already been added to the transaction.");
            }
            if (this.mSharedElementSourceNames.contains(transitionName)) {
                throw new java.lang.IllegalArgumentException("A shared element with the source name '" + transitionName + " has already been added to the transaction.");
            }
        }
        this.mSharedElementSourceNames.add(transitionName);
        this.mSharedElementTargetNames.add(str);
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setTransitionStyle(int i) {
        this.mTransitionStyle = i;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction addToBackStack(java.lang.String str) {
        if (!this.mAllowAddToBackStack) {
            throw new java.lang.IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.mAddToBackStack = true;
        this.mName = str;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction disallowAddToBackStack() {
        if (this.mAddToBackStack) {
            throw new java.lang.IllegalStateException("This transaction is already being added to the back stack");
        }
        this.mAllowAddToBackStack = false;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setBreadCrumbTitle(int i) {
        this.mBreadCrumbTitleRes = i;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setBreadCrumbTitle(java.lang.CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = charSequence;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setBreadCrumbShortTitle(int i) {
        this.mBreadCrumbShortTitleRes = i;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setBreadCrumbShortTitle(java.lang.CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = charSequence;
        return this;
    }

    void bumpBackStackNesting(int i) {
        if (!this.mAddToBackStack) {
            return;
        }
        if (android.app.FragmentManagerImpl.DEBUG) {
            android.util.Log.v(TAG, "Bump nesting in " + this + " by " + i);
        }
        int size = this.mOps.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i2);
            if (op.fragment != null) {
                op.fragment.mBackStackNesting += i;
                if (android.app.FragmentManagerImpl.DEBUG) {
                    android.util.Log.v(TAG, "Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting);
                }
            }
        }
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction runOnCommit(java.lang.Runnable runnable) {
        if (runnable == null) {
            throw new java.lang.IllegalArgumentException("runnable cannot be null");
        }
        disallowAddToBackStack();
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = new java.util.ArrayList<>();
        }
        this.mCommitRunnables.add(runnable);
        return this;
    }

    public void runOnCommitRunnables() {
        if (this.mCommitRunnables != null) {
            int size = this.mCommitRunnables.size();
            for (int i = 0; i < size; i++) {
                this.mCommitRunnables.get(i).run();
            }
            this.mCommitRunnables = null;
        }
    }

    @Override // android.app.FragmentTransaction
    public int commit() {
        return commitInternal(false);
    }

    @Override // android.app.FragmentTransaction
    public int commitAllowingStateLoss() {
        return commitInternal(true);
    }

    @Override // android.app.FragmentTransaction
    public void commitNow() {
        disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override // android.app.FragmentTransaction
    public void commitNowAllowingStateLoss() {
        disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override // android.app.FragmentTransaction
    public android.app.FragmentTransaction setReorderingAllowed(boolean z) {
        this.mReorderingAllowed = z;
        return this;
    }

    int commitInternal(boolean z) {
        if (this.mCommitted) {
            throw new java.lang.IllegalStateException("commit already called");
        }
        if (android.app.FragmentManagerImpl.DEBUG) {
            android.util.Log.v(TAG, "Commit: " + this);
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) new android.util.LogWriter(2, TAG), false, 1024);
            dump("  ", null, fastPrintWriter, null);
            fastPrintWriter.flush();
        }
        this.mCommitted = true;
        if (this.mAddToBackStack) {
            this.mIndex = this.mManager.allocBackStackIndex(this);
        } else {
            this.mIndex = -1;
        }
        this.mManager.enqueueAction(this, z);
        return this.mIndex;
    }

    @Override // android.app.FragmentManagerImpl.OpGenerator
    public boolean generateOps(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2) {
        if (android.app.FragmentManagerImpl.DEBUG) {
            android.util.Log.v(TAG, "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
            return true;
        }
        return true;
    }

    boolean interactsWith(int i) {
        int size = this.mOps.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i2);
            int i3 = op.fragment != null ? op.fragment.mContainerId : 0;
            if (i3 != 0 && i3 == i) {
                return true;
            }
        }
        return false;
    }

    boolean interactsWith(java.util.ArrayList<android.app.BackStackRecord> arrayList, int i, int i2) {
        int i3;
        if (i2 == i) {
            return false;
        }
        int size = this.mOps.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i5);
            int i6 = op.fragment != null ? op.fragment.mContainerId : 0;
            if (i6 != 0 && i6 != i4) {
                for (int i7 = i; i7 < i2; i7++) {
                    android.app.BackStackRecord backStackRecord = arrayList.get(i7);
                    int size2 = backStackRecord.mOps.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        android.app.BackStackRecord.Op op2 = backStackRecord.mOps.get(i8);
                        if (op2.fragment == null) {
                            i3 = 0;
                        } else {
                            i3 = op2.fragment.mContainerId;
                        }
                        if (i3 == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    void executeOps() {
        int size = this.mOps.size();
        for (int i = 0; i < size; i++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i);
            android.app.Fragment fragment = op.fragment;
            if (fragment != null) {
                fragment.setNextTransition(this.mTransition, this.mTransitionStyle);
            }
            switch (op.cmd) {
                case 1:
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.addFragment(fragment, false);
                    break;
                case 2:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown cmd: " + op.cmd);
                case 3:
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.removeFragment(fragment);
                    break;
                case 4:
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.hideFragment(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.showFragment(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.detachFragment(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.attachFragment(fragment);
                    break;
                case 8:
                    this.mManager.setPrimaryNavigationFragment(fragment);
                    break;
                case 9:
                    this.mManager.setPrimaryNavigationFragment(null);
                    break;
            }
            if (!this.mReorderingAllowed && op.cmd != 1 && fragment != null) {
                this.mManager.moveFragmentToExpectedState(fragment);
            }
        }
        if (!this.mReorderingAllowed) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    void executePopOps(boolean z) {
        for (int size = this.mOps.size() - 1; size >= 0; size--) {
            android.app.BackStackRecord.Op op = this.mOps.get(size);
            android.app.Fragment fragment = op.fragment;
            if (fragment != null) {
                fragment.setNextTransition(android.app.FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
            }
            switch (op.cmd) {
                case 1:
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.removeFragment(fragment);
                    break;
                case 2:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown cmd: " + op.cmd);
                case 3:
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.addFragment(fragment, false);
                    break;
                case 4:
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.showFragment(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.hideFragment(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.attachFragment(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.detachFragment(fragment);
                    break;
                case 8:
                    this.mManager.setPrimaryNavigationFragment(null);
                    break;
                case 9:
                    this.mManager.setPrimaryNavigationFragment(fragment);
                    break;
            }
            if (!this.mReorderingAllowed && op.cmd != 3 && fragment != null) {
                this.mManager.moveFragmentToExpectedState(fragment);
            }
        }
        if (!this.mReorderingAllowed && z) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    android.app.Fragment expandOps(java.util.ArrayList<android.app.Fragment> arrayList, android.app.Fragment fragment) {
        int i = 0;
        while (i < this.mOps.size()) {
            android.app.BackStackRecord.Op op = this.mOps.get(i);
            switch (op.cmd) {
                case 1:
                case 7:
                    arrayList.add(op.fragment);
                    break;
                case 2:
                    android.app.Fragment fragment2 = op.fragment;
                    int i2 = fragment2.mContainerId;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        android.app.Fragment fragment3 = arrayList.get(size);
                        if (fragment3.mContainerId == i2) {
                            if (fragment3 == fragment2) {
                                z = true;
                            } else {
                                if (fragment3 == fragment) {
                                    this.mOps.add(i, new android.app.BackStackRecord.Op(9, fragment3));
                                    i++;
                                    fragment = null;
                                }
                                android.app.BackStackRecord.Op op2 = new android.app.BackStackRecord.Op(3, fragment3);
                                op2.enterAnim = op.enterAnim;
                                op2.popEnterAnim = op.popEnterAnim;
                                op2.exitAnim = op.exitAnim;
                                op2.popExitAnim = op.popExitAnim;
                                this.mOps.add(i, op2);
                                arrayList.remove(fragment3);
                                i++;
                            }
                        }
                    }
                    if (z) {
                        this.mOps.remove(i);
                        i--;
                        break;
                    } else {
                        op.cmd = 1;
                        arrayList.add(fragment2);
                        break;
                    }
                case 3:
                case 6:
                    arrayList.remove(op.fragment);
                    if (op.fragment == fragment) {
                        this.mOps.add(i, new android.app.BackStackRecord.Op(9, op.fragment));
                        i++;
                        fragment = null;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    this.mOps.add(i, new android.app.BackStackRecord.Op(9, fragment));
                    i++;
                    fragment = op.fragment;
                    break;
            }
            i++;
        }
        return fragment;
    }

    void trackAddedFragmentsInPop(java.util.ArrayList<android.app.Fragment> arrayList) {
        for (int i = 0; i < this.mOps.size(); i++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i);
            switch (op.cmd) {
                case 1:
                case 7:
                    arrayList.remove(op.fragment);
                    break;
                case 3:
                case 6:
                    arrayList.add(op.fragment);
                    break;
            }
        }
    }

    boolean isPostponed() {
        for (int i = 0; i < this.mOps.size(); i++) {
            if (isFragmentPostponed(this.mOps.get(i))) {
                return true;
            }
        }
        return false;
    }

    void setOnStartPostponedListener(android.app.Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i = 0; i < this.mOps.size(); i++) {
            android.app.BackStackRecord.Op op = this.mOps.get(i);
            if (isFragmentPostponed(op)) {
                op.fragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener);
            }
        }
    }

    private static boolean isFragmentPostponed(android.app.BackStackRecord.Op op) {
        android.app.Fragment fragment = op.fragment;
        return (fragment == null || !fragment.mAdded || fragment.mView == null || fragment.mDetached || fragment.mHidden || !fragment.isPostponed()) ? false : true;
    }

    @Override // android.app.FragmentManager.BackStackEntry
    public java.lang.String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    @Override // android.app.FragmentTransaction
    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }
}
