package android.service.notification;

/* loaded from: classes3.dex */
public interface INotificationListener extends android.os.IInterface {
    void onActionClicked(java.lang.String str, android.app.Notification.Action action, int i) throws android.os.RemoteException;

    void onAllowedAdjustmentsChanged() throws android.os.RemoteException;

    void onInterruptionFilterChanged(int i) throws android.os.RemoteException;

    void onListenerConnected(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException;

    void onListenerHintsChanged(int i) throws android.os.RemoteException;

    void onNotificationChannelGroupModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) throws android.os.RemoteException;

    void onNotificationChannelModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) throws android.os.RemoteException;

    void onNotificationClicked(java.lang.String str) throws android.os.RemoteException;

    void onNotificationDirectReply(java.lang.String str) throws android.os.RemoteException;

    void onNotificationEnqueuedWithChannel(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException;

    void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.os.Bundle bundle) throws android.os.RemoteException;

    void onNotificationPosted(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException;

    void onNotificationRankingUpdate(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException;

    void onNotificationRemoved(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.service.notification.NotificationStats notificationStats, int i) throws android.os.RemoteException;

    void onNotificationSnoozedUntilContext(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, java.lang.String str) throws android.os.RemoteException;

    void onNotificationVisibilityChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void onNotificationsSeen(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void onPanelHidden() throws android.os.RemoteException;

    void onPanelRevealed(int i) throws android.os.RemoteException;

    void onStatusBarIconsBehaviorChanged(boolean z) throws android.os.RemoteException;

    void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    public static class Default implements android.service.notification.INotificationListener {
        @Override // android.service.notification.INotificationListener
        public void onListenerConnected(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPosted(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onStatusBarIconsBehaviorChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemoved(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.service.notification.NotificationStats notificationStats, int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRankingUpdate(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerHintsChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onInterruptionFilterChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelGroupModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannel(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContext(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationsSeen(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onPanelRevealed(int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onPanelHidden() throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationVisibilityChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationDirectReply(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onActionClicked(java.lang.String str, android.app.Notification.Action action, int i) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationClicked(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onAllowedAdjustmentsChanged() throws android.os.RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.notification.INotificationListener {
        public static final java.lang.String DESCRIPTOR = "android.service.notification.INotificationListener";
        static final int TRANSACTION_onActionClicked = 19;
        static final int TRANSACTION_onAllowedAdjustmentsChanged = 21;
        static final int TRANSACTION_onInterruptionFilterChanged = 7;
        static final int TRANSACTION_onListenerConnected = 1;
        static final int TRANSACTION_onListenerHintsChanged = 6;
        static final int TRANSACTION_onNotificationChannelGroupModification = 9;
        static final int TRANSACTION_onNotificationChannelModification = 8;
        static final int TRANSACTION_onNotificationClicked = 20;
        static final int TRANSACTION_onNotificationDirectReply = 17;
        static final int TRANSACTION_onNotificationEnqueuedWithChannel = 10;
        static final int TRANSACTION_onNotificationExpansionChanged = 16;
        static final int TRANSACTION_onNotificationFeedbackReceived = 22;
        static final int TRANSACTION_onNotificationPosted = 2;
        static final int TRANSACTION_onNotificationRankingUpdate = 5;
        static final int TRANSACTION_onNotificationRemoved = 4;
        static final int TRANSACTION_onNotificationSnoozedUntilContext = 11;
        static final int TRANSACTION_onNotificationVisibilityChanged = 15;
        static final int TRANSACTION_onNotificationsSeen = 12;
        static final int TRANSACTION_onPanelHidden = 14;
        static final int TRANSACTION_onPanelRevealed = 13;
        static final int TRANSACTION_onStatusBarIconsBehaviorChanged = 3;
        static final int TRANSACTION_onSuggestedReplySent = 18;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.notification.INotificationListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.notification.INotificationListener)) {
                return (android.service.notification.INotificationListener) queryLocalInterface;
            }
            return new android.service.notification.INotificationListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onListenerConnected";
                case 2:
                    return "onNotificationPosted";
                case 3:
                    return "onStatusBarIconsBehaviorChanged";
                case 4:
                    return "onNotificationRemoved";
                case 5:
                    return "onNotificationRankingUpdate";
                case 6:
                    return "onListenerHintsChanged";
                case 7:
                    return "onInterruptionFilterChanged";
                case 8:
                    return "onNotificationChannelModification";
                case 9:
                    return "onNotificationChannelGroupModification";
                case 10:
                    return "onNotificationEnqueuedWithChannel";
                case 11:
                    return "onNotificationSnoozedUntilContext";
                case 12:
                    return "onNotificationsSeen";
                case 13:
                    return "onPanelRevealed";
                case 14:
                    return "onPanelHidden";
                case 15:
                    return "onNotificationVisibilityChanged";
                case 16:
                    return "onNotificationExpansionChanged";
                case 17:
                    return "onNotificationDirectReply";
                case 18:
                    return "onSuggestedReplySent";
                case 19:
                    return "onActionClicked";
                case 20:
                    return "onNotificationClicked";
                case 21:
                    return "onAllowedAdjustmentsChanged";
                case 22:
                    return "onNotificationFeedbackReceived";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onListenerConnected(notificationRankingUpdate);
                    return true;
                case 2:
                    android.service.notification.IStatusBarNotificationHolder asInterface = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate2 = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationPosted(asInterface, notificationRankingUpdate2);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStatusBarIconsBehaviorChanged(readBoolean);
                    return true;
                case 4:
                    android.service.notification.IStatusBarNotificationHolder asInterface2 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate3 = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    android.service.notification.NotificationStats notificationStats = (android.service.notification.NotificationStats) parcel.readTypedObject(android.service.notification.NotificationStats.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationRemoved(asInterface2, notificationRankingUpdate3, notificationStats, readInt);
                    return true;
                case 5:
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate4 = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationRankingUpdate(notificationRankingUpdate4);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onListenerHintsChanged(readInt2);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInterruptionFilterChanged(readInt3);
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) parcel.readTypedObject(android.app.NotificationChannel.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationChannelModification(readString, userHandle, notificationChannel, readInt4);
                    return true;
                case 9:
                    java.lang.String readString2 = parcel.readString();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.NotificationChannelGroup notificationChannelGroup = (android.app.NotificationChannelGroup) parcel.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationChannelGroupModification(readString2, userHandle2, notificationChannelGroup, readInt5);
                    return true;
                case 10:
                    android.service.notification.IStatusBarNotificationHolder asInterface3 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    android.app.NotificationChannel notificationChannel2 = (android.app.NotificationChannel) parcel.readTypedObject(android.app.NotificationChannel.CREATOR);
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate5 = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationEnqueuedWithChannel(asInterface3, notificationChannel2, notificationRankingUpdate5);
                    return true;
                case 11:
                    android.service.notification.IStatusBarNotificationHolder asInterface4 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSnoozedUntilContext(asInterface4, readString3);
                    return true;
                case 12:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onNotificationsSeen(createStringArrayList);
                    return true;
                case 13:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPanelRevealed(readInt6);
                    return true;
                case 14:
                    onPanelHidden();
                    return true;
                case 15:
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationVisibilityChanged(readString4, readBoolean2);
                    return true;
                case 16:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationExpansionChanged(readString5, readBoolean3, readBoolean4);
                    return true;
                case 17:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationDirectReply(readString6);
                    return true;
                case 18:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSuggestedReplySent(readString7, charSequence, readInt7);
                    return true;
                case 19:
                    java.lang.String readString8 = parcel.readString();
                    android.app.Notification.Action action = (android.app.Notification.Action) parcel.readTypedObject(android.app.Notification.Action.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActionClicked(readString8, action, readInt8);
                    return true;
                case 20:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationClicked(readString9);
                    return true;
                case 21:
                    onAllowedAdjustmentsChanged();
                    return true;
                case 22:
                    java.lang.String readString10 = parcel.readString();
                    android.service.notification.NotificationRankingUpdate notificationRankingUpdate6 = (android.service.notification.NotificationRankingUpdate) parcel.readTypedObject(android.service.notification.NotificationRankingUpdate.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationFeedbackReceived(readString10, notificationRankingUpdate6, bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.notification.INotificationListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.notification.INotificationListener.Stub.DESCRIPTOR;
            }

            @Override // android.service.notification.INotificationListener
            public void onListenerConnected(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationPosted(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBarNotificationHolder);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onStatusBarIconsBehaviorChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationRemoved(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.service.notification.NotificationStats notificationStats, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBarNotificationHolder);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    obtain.writeTypedObject(notificationStats, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationRankingUpdate(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onListenerHintsChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onInterruptionFilterChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationChannelModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationChannelGroupModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannelGroup, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationEnqueuedWithChannel(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBarNotificationHolder);
                    obtain.writeTypedObject(notificationChannel, 0);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationSnoozedUntilContext(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBarNotificationHolder);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationsSeen(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onPanelRevealed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onPanelHidden() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationVisibilityChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationDirectReply(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onActionClicked(java.lang.String str, android.app.Notification.Action action, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(action, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationClicked(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onAllowedAdjustmentsChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.notification.INotificationListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(notificationRankingUpdate, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
