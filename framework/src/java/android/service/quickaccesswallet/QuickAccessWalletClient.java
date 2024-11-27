package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public interface QuickAccessWalletClient extends java.io.Closeable {

    public interface OnWalletCardsRetrievedCallback {
        void onWalletCardRetrievalError(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError);

        void onWalletCardsRetrieved(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse);
    }

    public interface WalletPendingIntentCallback {
        void onWalletPendingIntentRetrieved(android.app.PendingIntent pendingIntent);
    }

    public interface WalletServiceEventListener {
        void onWalletServiceEvent(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent);
    }

    void addWalletServiceEventListener(android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener);

    void addWalletServiceEventListener(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener);

    android.content.Intent createWalletIntent();

    android.content.Intent createWalletSettingsIntent();

    void disconnect();

    android.graphics.drawable.Drawable getLogo();

    java.lang.CharSequence getServiceLabel();

    java.lang.CharSequence getShortcutLongLabel();

    java.lang.CharSequence getShortcutShortLabel();

    android.graphics.drawable.Drawable getTileIcon();

    void getWalletCards(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback);

    void getWalletCards(java.util.concurrent.Executor executor, android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback);

    void getWalletPendingIntent(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback);

    boolean isWalletFeatureAvailable();

    boolean isWalletFeatureAvailableWhenDeviceLocked();

    boolean isWalletServiceAvailable();

    void notifyWalletDismissed();

    void removeWalletServiceEventListener(android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener);

    void selectWalletCard(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest);

    static android.service.quickaccesswallet.QuickAccessWalletClient create(android.content.Context context) {
        return create(context, null);
    }

    static android.service.quickaccesswallet.QuickAccessWalletClient create(android.content.Context context, java.util.concurrent.Executor executor) {
        return new android.service.quickaccesswallet.QuickAccessWalletClientImpl(context, executor);
    }
}
