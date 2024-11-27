package android.app.people;

/* loaded from: classes.dex */
public final class PeopleManager {
    private static final java.lang.String LOG_TAG = android.app.people.PeopleManager.class.getSimpleName();
    private android.content.Context mContext;
    public java.util.Map<android.app.people.PeopleManager.ConversationListener, android.util.Pair<java.util.concurrent.Executor, android.app.people.IConversationListener>> mConversationListeners;
    private android.app.people.IPeopleManager mService;

    public PeopleManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mConversationListeners = new java.util.HashMap();
        this.mContext = context;
        this.mService = android.app.people.IPeopleManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.PEOPLE_SERVICE));
    }

    public PeopleManager(android.content.Context context, android.app.people.IPeopleManager iPeopleManager) {
        this.mConversationListeners = new java.util.HashMap();
        this.mContext = context;
        this.mService = iPeopleManager;
    }

    @android.annotation.SystemApi
    public boolean isConversation(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
        try {
            return this.mService.isConversation(str, this.mContext.getUserId(), str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOrUpdateStatus(java.lang.String str, android.app.people.ConversationStatus conversationStatus) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(conversationStatus);
        try {
            this.mService.addOrUpdateStatus(this.mContext.getPackageName(), this.mContext.getUserId(), str, conversationStatus);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearStatus(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
        try {
            this.mService.clearStatus(this.mContext.getPackageName(), this.mContext.getUserId(), str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearStatuses(java.lang.String str) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        try {
            this.mService.clearStatuses(this.mContext.getPackageName(), this.mContext.getUserId(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.people.ConversationStatus> getStatuses(java.lang.String str) {
        try {
            android.content.pm.ParceledListSlice statuses = this.mService.getStatuses(this.mContext.getPackageName(), this.mContext.getUserId(), str);
            if (statuses != null) {
                return statuses.getList();
            }
            return new java.util.ArrayList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public interface ConversationListener {
        default void onConversationUpdate(android.app.people.ConversationChannel conversationChannel) {
        }
    }

    public void registerConversationListener(java.lang.String str, int i, java.lang.String str2, android.app.people.PeopleManager.ConversationListener conversationListener, java.util.concurrent.Executor executor) {
        java.util.Objects.requireNonNull(conversationListener, "Listener cannot be null");
        java.util.Objects.requireNonNull(str, "Package name cannot be null");
        java.util.Objects.requireNonNull(str2, "Shortcut ID cannot be null");
        synchronized (this.mConversationListeners) {
            android.app.people.PeopleManager.ConversationListenerProxy conversationListenerProxy = new android.app.people.PeopleManager.ConversationListenerProxy(executor, conversationListener);
            try {
                this.mService.registerConversationListener(str, i, str2, conversationListenerProxy);
                this.mConversationListeners.put(conversationListener, new android.util.Pair<>(executor, conversationListenerProxy));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterConversationListener(android.app.people.PeopleManager.ConversationListener conversationListener) {
        java.util.Objects.requireNonNull(conversationListener, "Listener cannot be null");
        synchronized (this.mConversationListeners) {
            if (this.mConversationListeners.containsKey(conversationListener)) {
                try {
                    this.mService.unregisterConversationListener(this.mConversationListeners.remove(conversationListener).second);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConversationListenerProxy extends android.app.people.IConversationListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.app.people.PeopleManager.ConversationListener mListener;

        ConversationListenerProxy(java.util.concurrent.Executor executor, android.app.people.PeopleManager.ConversationListener conversationListener) {
            this.mExecutor = executor;
            this.mListener = conversationListener;
        }

        @Override // android.app.people.IConversationListener
        public void onConversationUpdate(final android.app.people.ConversationChannel conversationChannel) {
            if (this.mListener == null || this.mExecutor == null) {
                android.util.Slog.e(android.app.people.PeopleManager.LOG_TAG, "Binder is dead");
            } else {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.people.PeopleManager$ConversationListenerProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.people.PeopleManager.ConversationListenerProxy.this.lambda$onConversationUpdate$0(conversationChannel);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConversationUpdate$0(android.app.people.ConversationChannel conversationChannel) {
            this.mListener.onConversationUpdate(conversationChannel);
        }
    }
}
