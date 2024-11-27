package android.content;

/* loaded from: classes.dex */
public class UndoManager {
    public static final int MERGE_MODE_ANY = 2;
    public static final int MERGE_MODE_NONE = 0;
    public static final int MERGE_MODE_UNIQUE = 1;
    private boolean mInUndo;
    private boolean mMerged;
    private int mNextSavedIdx;
    private android.content.UndoOwner[] mStateOwners;
    private int mStateSeq;
    private int mUpdateCount;
    private android.content.UndoManager.UndoState mWorking;
    private final android.util.ArrayMap<java.lang.String, android.content.UndoOwner> mOwners = new android.util.ArrayMap<>(1);
    private final java.util.ArrayList<android.content.UndoManager.UndoState> mUndos = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.content.UndoManager.UndoState> mRedos = new java.util.ArrayList<>();
    private int mHistorySize = 20;
    private int mCommitId = 1;

    public android.content.UndoOwner getOwner(java.lang.String str, java.lang.Object obj) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag can't be null");
        }
        if (obj == null) {
            throw new java.lang.NullPointerException("data can't be null");
        }
        android.content.UndoOwner undoOwner = this.mOwners.get(str);
        if (undoOwner != null) {
            if (undoOwner.mData != obj) {
                if (undoOwner.mData != null) {
                    throw new java.lang.IllegalStateException("Owner " + undoOwner + " already exists with data " + undoOwner.mData + " but giving different data " + obj);
                }
                undoOwner.mData = obj;
            }
            return undoOwner;
        }
        android.content.UndoOwner undoOwner2 = new android.content.UndoOwner(str, this);
        undoOwner2.mData = obj;
        this.mOwners.put(str, undoOwner2);
        return undoOwner2;
    }

    void removeOwner(android.content.UndoOwner undoOwner) {
    }

    public void saveInstanceState(android.os.Parcel parcel) {
        if (this.mUpdateCount > 0) {
            throw new java.lang.IllegalStateException("Can't save state while updating");
        }
        this.mStateSeq++;
        if (this.mStateSeq <= 0) {
            this.mStateSeq = 0;
        }
        this.mNextSavedIdx = 0;
        parcel.writeInt(this.mHistorySize);
        parcel.writeInt(this.mOwners.size());
        int size = this.mUndos.size();
        while (size > 0) {
            parcel.writeInt(1);
            size--;
            this.mUndos.get(size).writeToParcel(parcel);
        }
        int size2 = this.mRedos.size();
        while (size2 > 0) {
            parcel.writeInt(2);
            size2--;
            this.mRedos.get(size2).writeToParcel(parcel);
        }
        parcel.writeInt(0);
    }

    void saveOwner(android.content.UndoOwner undoOwner, android.os.Parcel parcel) {
        if (undoOwner.mStateSeq == this.mStateSeq) {
            parcel.writeInt(undoOwner.mSavedIdx);
            return;
        }
        undoOwner.mStateSeq = this.mStateSeq;
        undoOwner.mSavedIdx = this.mNextSavedIdx;
        parcel.writeInt(undoOwner.mSavedIdx);
        parcel.writeString(undoOwner.mTag);
        parcel.writeInt(undoOwner.mOpCount);
        this.mNextSavedIdx++;
    }

    public void restoreInstanceState(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        if (this.mUpdateCount > 0) {
            throw new java.lang.IllegalStateException("Can't save state while updating");
        }
        forgetUndos(null, -1);
        forgetRedos(null, -1);
        this.mHistorySize = parcel.readInt();
        this.mStateOwners = new android.content.UndoOwner[parcel.readInt()];
        while (true) {
            int readInt = parcel.readInt();
            if (readInt != 0) {
                android.content.UndoManager.UndoState undoState = new android.content.UndoManager.UndoState(this, parcel, classLoader);
                if (readInt == 1) {
                    this.mUndos.add(0, undoState);
                } else {
                    this.mRedos.add(0, undoState);
                }
            } else {
                return;
            }
        }
    }

    android.content.UndoOwner restoreOwner(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.content.UndoOwner undoOwner = this.mStateOwners[readInt];
        if (undoOwner == null) {
            java.lang.String readString = parcel.readString();
            int readInt2 = parcel.readInt();
            android.content.UndoOwner undoOwner2 = new android.content.UndoOwner(readString, this);
            undoOwner2.mOpCount = readInt2;
            this.mStateOwners[readInt] = undoOwner2;
            this.mOwners.put(readString, undoOwner2);
            return undoOwner2;
        }
        return undoOwner;
    }

    public void setHistorySize(int i) {
        this.mHistorySize = i;
        if (this.mHistorySize >= 0 && countUndos(null) > this.mHistorySize) {
            forgetUndos(null, countUndos(null) - this.mHistorySize);
        }
    }

    public int getHistorySize() {
        return this.mHistorySize;
    }

    public int undo(android.content.UndoOwner[] undoOwnerArr, int i) {
        if (this.mWorking != null) {
            throw new java.lang.IllegalStateException("Can't be called during an update");
        }
        this.mInUndo = true;
        android.content.UndoManager.UndoState topUndo = getTopUndo(null);
        if (topUndo != null) {
            topUndo.makeExecuted();
        }
        int i2 = -1;
        int i3 = 0;
        while (i > 0) {
            i2 = findPrevState(this.mUndos, undoOwnerArr, i2);
            if (i2 < 0) {
                break;
            }
            android.content.UndoManager.UndoState remove = this.mUndos.remove(i2);
            remove.undo();
            this.mRedos.add(remove);
            i--;
            i3++;
        }
        this.mInUndo = false;
        return i3;
    }

    public int redo(android.content.UndoOwner[] undoOwnerArr, int i) {
        if (this.mWorking != null) {
            throw new java.lang.IllegalStateException("Can't be called during an update");
        }
        this.mInUndo = true;
        int i2 = -1;
        int i3 = 0;
        while (i > 0) {
            i2 = findPrevState(this.mRedos, undoOwnerArr, i2);
            if (i2 < 0) {
                break;
            }
            android.content.UndoManager.UndoState remove = this.mRedos.remove(i2);
            remove.redo();
            this.mUndos.add(remove);
            i--;
            i3++;
        }
        this.mInUndo = false;
        return i3;
    }

    public boolean isInUndo() {
        return this.mInUndo;
    }

    public int forgetUndos(android.content.UndoOwner[] undoOwnerArr, int i) {
        if (i < 0) {
            i = this.mUndos.size();
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mUndos.size() && i3 < i) {
            android.content.UndoManager.UndoState undoState = this.mUndos.get(i2);
            if (i > 0 && matchOwners(undoState, undoOwnerArr)) {
                undoState.destroy();
                this.mUndos.remove(i2);
                i3++;
            } else {
                i2++;
            }
        }
        return i3;
    }

    public int forgetRedos(android.content.UndoOwner[] undoOwnerArr, int i) {
        if (i < 0) {
            i = this.mRedos.size();
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mRedos.size() && i3 < i) {
            android.content.UndoManager.UndoState undoState = this.mRedos.get(i2);
            if (i > 0 && matchOwners(undoState, undoOwnerArr)) {
                undoState.destroy();
                this.mRedos.remove(i2);
                i3++;
            } else {
                i2++;
            }
        }
        return i3;
    }

    public int countUndos(android.content.UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return this.mUndos.size();
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int findNextState = findNextState(this.mUndos, undoOwnerArr, i);
            if (findNextState >= 0) {
                i2++;
                i = findNextState + 1;
            } else {
                return i2;
            }
        }
    }

    public int countRedos(android.content.UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return this.mRedos.size();
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int findNextState = findNextState(this.mRedos, undoOwnerArr, i);
            if (findNextState >= 0) {
                i2++;
                i = findNextState + 1;
            } else {
                return i2;
            }
        }
    }

    public java.lang.CharSequence getUndoLabel(android.content.UndoOwner[] undoOwnerArr) {
        android.content.UndoManager.UndoState topUndo = getTopUndo(undoOwnerArr);
        if (topUndo != null) {
            return topUndo.getLabel();
        }
        return null;
    }

    public java.lang.CharSequence getRedoLabel(android.content.UndoOwner[] undoOwnerArr) {
        android.content.UndoManager.UndoState topRedo = getTopRedo(undoOwnerArr);
        if (topRedo != null) {
            return topRedo.getLabel();
        }
        return null;
    }

    public void beginUpdate(java.lang.CharSequence charSequence) {
        if (this.mInUndo) {
            throw new java.lang.IllegalStateException("Can't being update while performing undo/redo");
        }
        if (this.mUpdateCount <= 0) {
            createWorkingState();
            this.mMerged = false;
            this.mUpdateCount = 0;
        }
        this.mWorking.updateLabel(charSequence);
        this.mUpdateCount++;
    }

    private void createWorkingState() {
        int i = this.mCommitId;
        this.mCommitId = i + 1;
        this.mWorking = new android.content.UndoManager.UndoState(this, i);
        if (this.mCommitId < 0) {
            this.mCommitId = 1;
        }
    }

    public boolean isInUpdate() {
        return this.mUpdateCount > 0;
    }

    public void setUndoLabel(java.lang.CharSequence charSequence) {
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        this.mWorking.setLabel(charSequence);
    }

    public void suggestUndoLabel(java.lang.CharSequence charSequence) {
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        this.mWorking.updateLabel(charSequence);
    }

    public int getUpdateNestingLevel() {
        return this.mUpdateCount;
    }

    public boolean hasOperation(android.content.UndoOwner undoOwner) {
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        return this.mWorking.hasOperation(undoOwner);
    }

    public android.content.UndoOperation<?> getLastOperation(int i) {
        return getLastOperation(null, null, i);
    }

    public android.content.UndoOperation<?> getLastOperation(android.content.UndoOwner undoOwner, int i) {
        return getLastOperation(null, undoOwner, i);
    }

    public <T extends android.content.UndoOperation> T getLastOperation(java.lang.Class<T> cls, android.content.UndoOwner undoOwner, int i) {
        android.content.UndoManager.UndoState topUndo;
        T t;
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        if (i != 0 && !this.mMerged && !this.mWorking.hasData() && (topUndo = getTopUndo(null)) != null && ((i == 2 || !topUndo.hasMultipleOwners()) && topUndo.canMerge() && (t = (T) topUndo.getLastOperation(cls, undoOwner)) != null && t.allowMerge())) {
            this.mWorking.destroy();
            this.mWorking = topUndo;
            this.mUndos.remove(topUndo);
            this.mMerged = true;
            return t;
        }
        return (T) this.mWorking.getLastOperation(cls, undoOwner);
    }

    public void addOperation(android.content.UndoOperation<?> undoOperation, int i) {
        android.content.UndoManager.UndoState topUndo;
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        if (undoOperation.getOwner().mManager != this) {
            throw new java.lang.IllegalArgumentException("Given operation's owner is not in this undo manager.");
        }
        if (i != 0 && !this.mMerged && !this.mWorking.hasData() && (topUndo = getTopUndo(null)) != null && ((i == 2 || !topUndo.hasMultipleOwners()) && topUndo.canMerge() && topUndo.hasOperation(undoOperation.getOwner()))) {
            this.mWorking.destroy();
            this.mWorking = topUndo;
            this.mUndos.remove(topUndo);
            this.mMerged = true;
        }
        this.mWorking.addOperation(undoOperation);
    }

    public void endUpdate() {
        if (this.mWorking == null) {
            throw new java.lang.IllegalStateException("Must be called during an update");
        }
        this.mUpdateCount--;
        if (this.mUpdateCount == 0) {
            pushWorkingState();
        }
    }

    private void pushWorkingState() {
        int size = this.mUndos.size() + 1;
        if (this.mWorking.hasData()) {
            this.mUndos.add(this.mWorking);
            forgetRedos(null, -1);
            this.mWorking.commit();
            if (size >= 2) {
                this.mUndos.get(size - 2).makeExecuted();
            }
        } else {
            this.mWorking.destroy();
        }
        this.mWorking = null;
        if (this.mHistorySize >= 0 && size > this.mHistorySize) {
            forgetUndos(null, size - this.mHistorySize);
        }
    }

    public int commitState(android.content.UndoOwner undoOwner) {
        if (this.mWorking != null && this.mWorking.hasData()) {
            if (undoOwner == null || this.mWorking.hasOperation(undoOwner)) {
                this.mWorking.setCanMerge(false);
                int commitId = this.mWorking.getCommitId();
                pushWorkingState();
                createWorkingState();
                this.mMerged = true;
                return commitId;
            }
            return -1;
        }
        android.content.UndoManager.UndoState topUndo = getTopUndo(null);
        if (topUndo == null) {
            return -1;
        }
        if (undoOwner == null || topUndo.hasOperation(undoOwner)) {
            topUndo.setCanMerge(false);
            return topUndo.getCommitId();
        }
        return -1;
    }

    public boolean uncommitState(int i, android.content.UndoOwner undoOwner) {
        if (this.mWorking != null && this.mWorking.getCommitId() == i) {
            if (undoOwner == null || this.mWorking.hasOperation(undoOwner)) {
                return this.mWorking.setCanMerge(true);
            }
            return false;
        }
        android.content.UndoManager.UndoState topUndo = getTopUndo(null);
        if (topUndo != null) {
            if ((undoOwner == null || topUndo.hasOperation(undoOwner)) && topUndo.getCommitId() == i) {
                return topUndo.setCanMerge(true);
            }
            return false;
        }
        return false;
    }

    android.content.UndoManager.UndoState getTopUndo(android.content.UndoOwner[] undoOwnerArr) {
        int findPrevState;
        if (this.mUndos.size() > 0 && (findPrevState = findPrevState(this.mUndos, undoOwnerArr, -1)) >= 0) {
            return this.mUndos.get(findPrevState);
        }
        return null;
    }

    android.content.UndoManager.UndoState getTopRedo(android.content.UndoOwner[] undoOwnerArr) {
        int findPrevState;
        if (this.mRedos.size() > 0 && (findPrevState = findPrevState(this.mRedos, undoOwnerArr, -1)) >= 0) {
            return this.mRedos.get(findPrevState);
        }
        return null;
    }

    boolean matchOwners(android.content.UndoManager.UndoState undoState, android.content.UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return true;
        }
        for (android.content.UndoOwner undoOwner : undoOwnerArr) {
            if (undoState.matchOwner(undoOwner)) {
                return true;
            }
        }
        return false;
    }

    int findPrevState(java.util.ArrayList<android.content.UndoManager.UndoState> arrayList, android.content.UndoOwner[] undoOwnerArr, int i) {
        int size = arrayList.size();
        if (i == -1) {
            i = size - 1;
        }
        if (i >= size) {
            return -1;
        }
        if (undoOwnerArr == null) {
            return i;
        }
        while (i >= 0) {
            if (matchOwners(arrayList.get(i), undoOwnerArr)) {
                return i;
            }
            i--;
        }
        return -1;
    }

    int findNextState(java.util.ArrayList<android.content.UndoManager.UndoState> arrayList, android.content.UndoOwner[] undoOwnerArr, int i) {
        int size = arrayList.size();
        if (i < 0) {
            i = 0;
        }
        if (i >= size) {
            return -1;
        }
        if (undoOwnerArr == null) {
            return i;
        }
        while (i < size) {
            if (matchOwners(arrayList.get(i), undoOwnerArr)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    static final class UndoState {
        private boolean mCanMerge;
        private final int mCommitId;
        private boolean mExecuted;
        private java.lang.CharSequence mLabel;
        private final android.content.UndoManager mManager;
        private final java.util.ArrayList<android.content.UndoOperation<?>> mOperations;
        private java.util.ArrayList<android.content.UndoOperation<?>> mRecent;

        UndoState(android.content.UndoManager undoManager, int i) {
            this.mOperations = new java.util.ArrayList<>();
            this.mCanMerge = true;
            this.mManager = undoManager;
            this.mCommitId = i;
        }

        UndoState(android.content.UndoManager undoManager, android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            this.mOperations = new java.util.ArrayList<>();
            this.mCanMerge = true;
            this.mManager = undoManager;
            this.mCommitId = parcel.readInt();
            this.mCanMerge = parcel.readInt() != 0;
            this.mExecuted = parcel.readInt() != 0;
            this.mLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                android.content.UndoOwner restoreOwner = this.mManager.restoreOwner(parcel);
                android.content.UndoOperation<?> undoOperation = (android.content.UndoOperation) parcel.readParcelable(classLoader, android.content.UndoOperation.class);
                undoOperation.mOwner = restoreOwner;
                this.mOperations.add(undoOperation);
            }
        }

        void writeToParcel(android.os.Parcel parcel) {
            if (this.mRecent != null) {
                throw new java.lang.IllegalStateException("Can't save state before committing");
            }
            parcel.writeInt(this.mCommitId);
            parcel.writeInt(this.mCanMerge ? 1 : 0);
            parcel.writeInt(this.mExecuted ? 1 : 0);
            android.text.TextUtils.writeToParcel(this.mLabel, parcel, 0);
            int size = this.mOperations.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                android.content.UndoOperation<?> undoOperation = this.mOperations.get(i);
                this.mManager.saveOwner(undoOperation.mOwner, parcel);
                parcel.writeParcelable(undoOperation, 0);
            }
        }

        int getCommitId() {
            return this.mCommitId;
        }

        void setLabel(java.lang.CharSequence charSequence) {
            this.mLabel = charSequence;
        }

        void updateLabel(java.lang.CharSequence charSequence) {
            if (this.mLabel != null) {
                this.mLabel = charSequence;
            }
        }

        java.lang.CharSequence getLabel() {
            return this.mLabel;
        }

        boolean setCanMerge(boolean z) {
            if (z && this.mExecuted) {
                return false;
            }
            this.mCanMerge = z;
            return true;
        }

        void makeExecuted() {
            this.mExecuted = true;
        }

        boolean canMerge() {
            return this.mCanMerge && !this.mExecuted;
        }

        int countOperations() {
            return this.mOperations.size();
        }

        boolean hasOperation(android.content.UndoOwner undoOwner) {
            int size = this.mOperations.size();
            if (undoOwner == null) {
                return size != 0;
            }
            for (int i = 0; i < size; i++) {
                if (this.mOperations.get(i).getOwner() == undoOwner) {
                    return true;
                }
            }
            return false;
        }

        boolean hasMultipleOwners() {
            int size = this.mOperations.size();
            if (size <= 1) {
                return false;
            }
            android.content.UndoOwner owner = this.mOperations.get(0).getOwner();
            for (int i = 1; i < size; i++) {
                if (this.mOperations.get(i).getOwner() != owner) {
                    return true;
                }
            }
            return false;
        }

        void addOperation(android.content.UndoOperation<?> undoOperation) {
            if (this.mOperations.contains(undoOperation)) {
                throw new java.lang.IllegalStateException("Already holds " + undoOperation);
            }
            this.mOperations.add(undoOperation);
            if (this.mRecent == null) {
                this.mRecent = new java.util.ArrayList<>();
                this.mRecent.add(undoOperation);
            }
            undoOperation.mOwner.mOpCount++;
        }

        <T extends android.content.UndoOperation> T getLastOperation(java.lang.Class<T> cls, android.content.UndoOwner undoOwner) {
            int size = this.mOperations.size();
            if (cls == null && undoOwner == null) {
                if (size > 0) {
                    return this.mOperations.get(size - 1);
                }
                return null;
            }
            for (int i = size - 1; i >= 0; i--) {
                android.content.UndoOperation<?> undoOperation = this.mOperations.get(i);
                if (undoOwner == null || undoOperation.getOwner() == undoOwner) {
                    if (cls != null && undoOperation.getClass() != cls) {
                        return null;
                    }
                    return undoOperation;
                }
            }
            return null;
        }

        boolean matchOwner(android.content.UndoOwner undoOwner) {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                if (this.mOperations.get(size).matchOwner(undoOwner)) {
                    return true;
                }
            }
            return false;
        }

        boolean hasData() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                if (this.mOperations.get(size).hasData()) {
                    return true;
                }
            }
            return false;
        }

        void commit() {
            int size = this.mRecent != null ? this.mRecent.size() : 0;
            for (int i = 0; i < size; i++) {
                this.mRecent.get(i).commit();
            }
            this.mRecent = null;
        }

        void undo() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                this.mOperations.get(size).undo();
            }
        }

        void redo() {
            int size = this.mOperations.size();
            for (int i = 0; i < size; i++) {
                this.mOperations.get(i).redo();
            }
        }

        void destroy() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                android.content.UndoOwner undoOwner = this.mOperations.get(size).mOwner;
                undoOwner.mOpCount--;
                if (undoOwner.mOpCount <= 0) {
                    if (undoOwner.mOpCount < 0) {
                        throw new java.lang.IllegalStateException("Underflow of op count on owner " + undoOwner + " in op " + this.mOperations.get(size));
                    }
                    this.mManager.removeOwner(undoOwner);
                }
            }
        }
    }
}
