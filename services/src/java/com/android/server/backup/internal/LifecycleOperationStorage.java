package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class LifecycleOperationStorage implements com.android.server.backup.OperationStorage {
    private static final java.lang.String TAG = "LifecycleOperationStorage";
    private final int mUserId;
    private final java.lang.Object mOperationsLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mOperationsLock"})
    private final android.util.SparseArray<com.android.server.backup.internal.Operation> mOperations = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mOperationsLock"})
    private final java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> mOpTokensByPackage = new java.util.HashMap();

    public LifecycleOperationStorage(int i) {
        this.mUserId = i;
    }

    @Override // com.android.server.backup.OperationStorage
    public void registerOperation(int i, int i2, com.android.server.backup.BackupRestoreTask backupRestoreTask, int i3) {
        registerOperationForPackages(i, i2, com.google.android.collect.Sets.newHashSet(), backupRestoreTask, i3);
    }

    @Override // com.android.server.backup.OperationStorage
    public void registerOperationForPackages(int i, int i2, java.util.Set<java.lang.String> set, com.android.server.backup.BackupRestoreTask backupRestoreTask, int i3) {
        synchronized (this.mOperationsLock) {
            try {
                this.mOperations.put(i, new com.android.server.backup.internal.Operation(i2, backupRestoreTask, i3));
                for (java.lang.String str : set) {
                    java.util.Set<java.lang.Integer> set2 = this.mOpTokensByPackage.get(str);
                    if (set2 == null) {
                        set2 = new java.util.HashSet<>();
                    }
                    set2.add(java.lang.Integer.valueOf(i));
                    this.mOpTokensByPackage.put(str, set2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.backup.OperationStorage
    public void removeOperation(int i) {
        synchronized (this.mOperationsLock) {
            try {
                this.mOperations.remove(i);
                for (java.lang.String str : this.mOpTokensByPackage.keySet()) {
                    java.util.Set<java.lang.Integer> set = this.mOpTokensByPackage.get(str);
                    if (set != null) {
                        set.remove(java.lang.Integer.valueOf(i));
                        this.mOpTokensByPackage.put(str, set);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.backup.OperationStorage
    public int numOperations() {
        int size;
        synchronized (this.mOperationsLock) {
            size = this.mOperations.size();
        }
        return size;
    }

    @Override // com.android.server.backup.OperationStorage
    public boolean isBackupOperationInProgress() {
        synchronized (this.mOperationsLock) {
            for (int i = 0; i < this.mOperations.size(); i++) {
                try {
                    com.android.server.backup.internal.Operation valueAt = this.mOperations.valueAt(i);
                    if (valueAt.type == 2 && valueAt.state == 0) {
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    @Override // com.android.server.backup.OperationStorage
    public java.util.Set<java.lang.Integer> operationTokensForPackage(java.lang.String str) {
        java.util.HashSet newHashSet;
        synchronized (this.mOperationsLock) {
            try {
                java.util.Set<java.lang.Integer> set = this.mOpTokensByPackage.get(str);
                newHashSet = com.google.android.collect.Sets.newHashSet();
                if (set != null) {
                    newHashSet.addAll(set);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return newHashSet;
    }

    @Override // com.android.server.backup.OperationStorage
    public java.util.Set<java.lang.Integer> operationTokensForOpType(int i) {
        java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet();
        synchronized (this.mOperationsLock) {
            for (int i2 = 0; i2 < this.mOperations.size(); i2++) {
                try {
                    com.android.server.backup.internal.Operation valueAt = this.mOperations.valueAt(i2);
                    int keyAt = this.mOperations.keyAt(i2);
                    if (valueAt.type == i) {
                        newHashSet.add(java.lang.Integer.valueOf(keyAt));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return newHashSet;
    }

    @Override // com.android.server.backup.OperationStorage
    public java.util.Set<java.lang.Integer> operationTokensForOpState(int i) {
        java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet();
        synchronized (this.mOperationsLock) {
            for (int i2 = 0; i2 < this.mOperations.size(); i2++) {
                try {
                    com.android.server.backup.internal.Operation valueAt = this.mOperations.valueAt(i2);
                    int keyAt = this.mOperations.keyAt(i2);
                    if (valueAt.state == i) {
                        newHashSet.add(java.lang.Integer.valueOf(keyAt));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return newHashSet;
    }

    public boolean waitUntilOperationComplete(int i, java.util.function.IntConsumer intConsumer) {
        com.android.server.backup.internal.Operation operation;
        int i2;
        synchronized (this.mOperationsLock) {
            while (true) {
                operation = this.mOperations.get(i);
                if (operation == null) {
                    i2 = 0;
                    break;
                }
                if (operation.state == 0) {
                    try {
                        this.mOperationsLock.wait();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.w(TAG, "Waiting on mOperationsLock: ", e);
                    }
                } else {
                    i2 = operation.state;
                    break;
                }
            }
        }
        removeOperation(i);
        if (operation != null) {
            intConsumer.accept(operation.type);
        }
        if (i2 != 1) {
            return false;
        }
        return true;
    }

    public void onOperationComplete(int i, long j, java.util.function.Consumer<com.android.server.backup.BackupRestoreTask> consumer) {
        com.android.server.backup.internal.Operation operation;
        synchronized (this.mOperationsLock) {
            try {
                operation = this.mOperations.get(i);
                if (operation != null) {
                    if (operation.state == -1) {
                        this.mOperations.remove(i);
                        operation = null;
                    } else if (operation.state == 1) {
                        android.util.Slog.w(TAG, "[UserID:" + this.mUserId + "] Received duplicate ack for token=" + java.lang.Integer.toHexString(i));
                        this.mOperations.remove(i);
                        operation = null;
                    } else if (operation.state == 0) {
                        operation.state = 1;
                    }
                }
                this.mOperationsLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (operation != null && operation.callback != null) {
            consumer.accept(operation.callback);
        }
    }

    public void cancelOperation(int i, boolean z, java.util.function.IntConsumer intConsumer) {
        com.android.server.backup.internal.Operation operation;
        synchronized (this.mOperationsLock) {
            try {
                operation = this.mOperations.get(i);
                int i2 = operation != null ? operation.state : -1;
                if (i2 == 1) {
                    android.util.Slog.w(TAG, "[UserID:" + this.mUserId + "] Operation already got an ack.Should have been removed from mCurrentOperations.");
                    this.mOperations.delete(i);
                    operation = null;
                } else if (i2 == 0) {
                    android.util.Slog.v(TAG, "[UserID:" + this.mUserId + "] Cancel: token=" + java.lang.Integer.toHexString(i));
                    operation.state = -1;
                    intConsumer.accept(operation.type);
                }
                this.mOperationsLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (operation != null && operation.callback != null) {
            operation.callback.handleCancel(z);
        }
    }
}
