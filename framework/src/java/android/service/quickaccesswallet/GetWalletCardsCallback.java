package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public interface GetWalletCardsCallback {
    void onFailure(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError);

    void onSuccess(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse);
}
