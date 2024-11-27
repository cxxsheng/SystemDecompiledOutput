package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderDeathDispatcher<T extends android.os.IInterface> {
    private static final java.lang.String TAG = "BinderDeathDispatcher";
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.os.IBinder, com.android.internal.os.BinderDeathDispatcher<T>.RecipientsInfo> mTargets = new android.util.ArrayMap<>();

    class RecipientsInfo implements android.os.IBinder.DeathRecipient {
        android.util.ArraySet<android.os.IBinder.DeathRecipient> mRecipients;
        final android.os.IBinder mTarget;

        private RecipientsInfo(android.os.IBinder iBinder) {
            this.mRecipients = new android.util.ArraySet<>();
            this.mTarget = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(android.os.IBinder iBinder) {
            android.util.ArraySet<android.os.IBinder.DeathRecipient> arraySet;
            synchronized (com.android.internal.os.BinderDeathDispatcher.this.mLock) {
                arraySet = this.mRecipients;
                this.mRecipients = null;
                com.android.internal.os.BinderDeathDispatcher.this.mTargets.remove(this.mTarget);
            }
            if (arraySet == null) {
                return;
            }
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                arraySet.valueAt(i).binderDied(iBinder);
            }
        }
    }

    public int linkToDeath(T t, android.os.IBinder.DeathRecipient deathRecipient) {
        int size;
        android.os.IBinder asBinder = t.asBinder();
        synchronized (this.mLock) {
            com.android.internal.os.BinderDeathDispatcher<T>.RecipientsInfo recipientsInfo = this.mTargets.get(asBinder);
            if (recipientsInfo == null) {
                recipientsInfo = new com.android.internal.os.BinderDeathDispatcher.RecipientsInfo(asBinder);
                try {
                    asBinder.linkToDeath(recipientsInfo, 0);
                    this.mTargets.put(asBinder, recipientsInfo);
                } catch (android.os.RemoteException e) {
                    return -1;
                }
            }
            recipientsInfo.mRecipients.add(deathRecipient);
            size = recipientsInfo.mRecipients.size();
        }
        return size;
    }

    public void unlinkToDeath(T t, android.os.IBinder.DeathRecipient deathRecipient) {
        android.os.IBinder asBinder = t.asBinder();
        synchronized (this.mLock) {
            com.android.internal.os.BinderDeathDispatcher<T>.RecipientsInfo recipientsInfo = this.mTargets.get(asBinder);
            if (recipientsInfo == null) {
                return;
            }
            if (recipientsInfo.mRecipients.remove(deathRecipient) && recipientsInfo.mRecipients.size() == 0) {
                recipientsInfo.mTarget.unlinkToDeath(recipientsInfo, 0);
                this.mTargets.remove(recipientsInfo.mTarget);
            }
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.print("# of watched binders: ");
            indentingPrintWriter.println(this.mTargets.size());
            indentingPrintWriter.print("# of death recipients: ");
            java.util.Iterator<com.android.internal.os.BinderDeathDispatcher<T>.RecipientsInfo> it = this.mTargets.values().iterator();
            int i = 0;
            while (it.hasNext()) {
                i += it.next().mRecipients.size();
            }
            indentingPrintWriter.println(i);
        }
    }

    public android.util.ArrayMap<android.os.IBinder, com.android.internal.os.BinderDeathDispatcher<T>.RecipientsInfo> getTargetsForTest() {
        return this.mTargets;
    }
}
