package com.android.server.wm;

/* loaded from: classes3.dex */
class RotationWatcherController {
    private volatile boolean mHasProposedRotationListeners;
    private final com.android.server.wm.WindowManagerService mService;
    private final java.util.ArrayList<com.android.server.wm.RotationWatcherController.DisplayRotationWatcher> mDisplayRotationWatchers = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.RotationWatcherController.ProposedRotationListener> mProposedRotationListeners = new java.util.ArrayList<>();

    RotationWatcherController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    void registerDisplayRotationWatcher(android.view.IRotationWatcher iRotationWatcher, int i) {
        android.os.IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
            if (asBinder == this.mDisplayRotationWatchers.get(size).mWatcher.asBinder()) {
                throw new java.lang.IllegalArgumentException("Registering existed rotation watcher");
            }
        }
        register(asBinder, new com.android.server.wm.RotationWatcherController.DisplayRotationWatcher(this.mService, iRotationWatcher, i), this.mDisplayRotationWatchers);
    }

    void registerProposedRotationListener(android.view.IRotationWatcher iRotationWatcher, android.os.IBinder iBinder) {
        android.os.IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            com.android.server.wm.RotationWatcherController.ProposedRotationListener proposedRotationListener = this.mProposedRotationListeners.get(size);
            if (iBinder == proposedRotationListener.mToken || asBinder == proposedRotationListener.mWatcher.asBinder()) {
                android.util.Slog.w("WindowManager", "Register rotation listener to a registered token, uid=" + android.os.Binder.getCallingUid());
                return;
            }
        }
        register(asBinder, new com.android.server.wm.RotationWatcherController.ProposedRotationListener(this.mService, iRotationWatcher, iBinder), this.mProposedRotationListeners);
        this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
    }

    private static <T extends com.android.server.wm.RotationWatcherController.RotationWatcher> void register(android.os.IBinder iBinder, T t, java.util.ArrayList<T> arrayList) {
        try {
            iBinder.linkToDeath(t, 0);
            arrayList.add(t);
        } catch (android.os.RemoteException e) {
        }
    }

    private static <T extends com.android.server.wm.RotationWatcherController.RotationWatcher> boolean unregister(android.view.IRotationWatcher iRotationWatcher, java.util.ArrayList<T> arrayList) {
        android.os.IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            T t = arrayList.get(size);
            if (asBinder == t.mWatcher.asBinder()) {
                arrayList.remove(size);
                t.unlinkToDeath();
                return true;
            }
        }
        return false;
    }

    void removeRotationWatcher(android.view.IRotationWatcher iRotationWatcher) {
        if (unregister(iRotationWatcher, this.mProposedRotationListeners)) {
            this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
        } else {
            unregister(iRotationWatcher, this.mDisplayRotationWatchers);
        }
    }

    void dispatchDisplayRotationChange(int i, int i2) {
        for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
            com.android.server.wm.RotationWatcherController.DisplayRotationWatcher displayRotationWatcher = this.mDisplayRotationWatchers.get(size);
            if (displayRotationWatcher.mDisplayId == i) {
                displayRotationWatcher.notifyRotation(i2);
            }
        }
    }

    void dispatchProposedRotation(com.android.server.wm.DisplayContent displayContent, int i) {
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            com.android.server.wm.RotationWatcherController.ProposedRotationListener proposedRotationListener = this.mProposedRotationListeners.get(size);
            com.android.server.wm.WindowContainer<?> associatedWindowContainer = getAssociatedWindowContainer(proposedRotationListener.mToken);
            if (associatedWindowContainer != null) {
                if (associatedWindowContainer.mDisplayContent == displayContent) {
                    proposedRotationListener.notifyRotation(i);
                }
            } else {
                this.mProposedRotationListeners.remove(size);
                this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
                proposedRotationListener.unlinkToDeath();
            }
        }
    }

    boolean hasProposedRotationListeners() {
        return this.mHasProposedRotationListeners;
    }

    com.android.server.wm.WindowContainer<?> getAssociatedWindowContainer(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked != null) {
            return forTokenLocked;
        }
        return this.mService.mWindowContextListenerController.getContainer(iBinder);
    }

    void dump(java.io.PrintWriter printWriter) {
        if (!this.mDisplayRotationWatchers.isEmpty()) {
            printWriter.print("  mDisplayRotationWatchers: [");
            for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
                printWriter.print(' ');
                com.android.server.wm.RotationWatcherController.DisplayRotationWatcher displayRotationWatcher = this.mDisplayRotationWatchers.get(size);
                printWriter.print(displayRotationWatcher.mOwnerUid);
                printWriter.print("->");
                printWriter.print(displayRotationWatcher.mDisplayId);
            }
            printWriter.println(']');
        }
        if (!this.mProposedRotationListeners.isEmpty()) {
            printWriter.print("  mProposedRotationListeners: [");
            for (int size2 = this.mProposedRotationListeners.size() - 1; size2 >= 0; size2--) {
                printWriter.print(' ');
                com.android.server.wm.RotationWatcherController.ProposedRotationListener proposedRotationListener = this.mProposedRotationListeners.get(size2);
                printWriter.print(proposedRotationListener.mOwnerUid);
                printWriter.print("->");
                printWriter.print(getAssociatedWindowContainer(proposedRotationListener.mToken));
            }
            printWriter.println(']');
        }
    }

    private static class DisplayRotationWatcher extends com.android.server.wm.RotationWatcherController.RotationWatcher {
        final int mDisplayId;

        DisplayRotationWatcher(com.android.server.wm.WindowManagerService windowManagerService, android.view.IRotationWatcher iRotationWatcher, int i) {
            super(windowManagerService, iRotationWatcher);
            this.mDisplayId = i;
        }
    }

    private static class ProposedRotationListener extends com.android.server.wm.RotationWatcherController.RotationWatcher {
        final android.os.IBinder mToken;

        ProposedRotationListener(com.android.server.wm.WindowManagerService windowManagerService, android.view.IRotationWatcher iRotationWatcher, android.os.IBinder iBinder) {
            super(windowManagerService, iRotationWatcher);
            this.mToken = iBinder;
        }
    }

    private static class RotationWatcher implements android.os.IBinder.DeathRecipient {
        final int mOwnerUid = android.os.Binder.getCallingUid();
        final android.view.IRotationWatcher mWatcher;
        final com.android.server.wm.WindowManagerService mWms;

        RotationWatcher(com.android.server.wm.WindowManagerService windowManagerService, android.view.IRotationWatcher iRotationWatcher) {
            this.mWms = windowManagerService;
            this.mWatcher = iRotationWatcher;
        }

        void notifyRotation(int i) {
            try {
                this.mWatcher.onRotationChanged(i);
            } catch (android.os.RemoteException e) {
            }
        }

        void unlinkToDeath() {
            this.mWatcher.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mWms.removeRotationWatcher(this.mWatcher);
        }
    }
}
