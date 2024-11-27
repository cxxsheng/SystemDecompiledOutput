package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
final class GetWalletCardsCallbackImpl implements android.service.quickaccesswallet.GetWalletCardsCallback {
    private static final java.lang.String TAG = "QAWalletCallback";
    private final android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks mCallback;
    private boolean mCalled;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.service.quickaccesswallet.GetWalletCardsRequest mRequest;

    GetWalletCardsCallbackImpl(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks, android.os.Handler handler, android.content.Context context) {
        this.mRequest = getWalletCardsRequest;
        this.mCallback = iQuickAccessWalletServiceCallbacks;
        this.mHandler = handler;
        this.mContext = context;
    }

    @Override // android.service.quickaccesswallet.GetWalletCardsCallback
    public void onSuccess(final android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
        if (isValidResponse(getWalletCardsResponse)) {
            if (!this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WALLET_LOCATION_BASED_SUGGESTIONS)) {
                removeLocationsFromResponse(getWalletCardsResponse);
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.GetWalletCardsCallbackImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.GetWalletCardsCallbackImpl.this.lambda$onSuccess$0(getWalletCardsResponse);
                }
            });
        } else {
            android.util.Log.w(TAG, "Invalid GetWalletCards response");
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.GetWalletCardsCallbackImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.GetWalletCardsCallbackImpl.this.lambda$onSuccess$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$1() {
        lambda$onFailure$2(new android.service.quickaccesswallet.GetWalletCardsError(null, null));
    }

    @Override // android.service.quickaccesswallet.GetWalletCardsCallback
    public void onFailure(final android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.GetWalletCardsCallbackImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.GetWalletCardsCallbackImpl.this.lambda$onFailure$2(getWalletCardsError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSuccessInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onSuccess$0(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
        if (this.mCalled) {
            android.util.Log.w(TAG, "already called");
            return;
        }
        this.mCalled = true;
        try {
            this.mCallback.onGetWalletCardsSuccess(getWalletCardsResponse);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error returning wallet cards", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFailureInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onFailure$2(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) {
        if (this.mCalled) {
            android.util.Log.w(TAG, "already called");
            return;
        }
        this.mCalled = true;
        try {
            this.mCallback.onGetWalletCardsFailure(getWalletCardsError);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error returning failure message", e);
        }
    }

    private boolean isValidResponse(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
        if (getWalletCardsResponse == null) {
            android.util.Log.w(TAG, "Invalid response: response is null");
            return false;
        }
        if (getWalletCardsResponse.getWalletCards() == null) {
            android.util.Log.w(TAG, "Invalid response: walletCards is null");
            return false;
        }
        if (getWalletCardsResponse.getSelectedIndex() < 0) {
            android.util.Log.w(TAG, "Invalid response: selectedIndex is negative");
            return false;
        }
        if (!getWalletCardsResponse.getWalletCards().isEmpty() && getWalletCardsResponse.getSelectedIndex() >= getWalletCardsResponse.getWalletCards().size()) {
            android.util.Log.w(TAG, "Invalid response: selectedIndex out of bounds");
            return false;
        }
        if (getWalletCardsResponse.getWalletCards().size() > this.mRequest.getMaxCards()) {
            android.util.Log.w(TAG, "Invalid response: too many cards");
            return false;
        }
        for (android.service.quickaccesswallet.WalletCard walletCard : getWalletCardsResponse.getWalletCards()) {
            if (walletCard == null) {
                android.util.Log.w(TAG, "Invalid response: card is null");
                return false;
            }
            if (walletCard.getCardId() == null) {
                android.util.Log.w(TAG, "Invalid response: cardId is null");
                return false;
            }
            android.graphics.drawable.Icon cardImage = walletCard.getCardImage();
            if (cardImage == null) {
                android.util.Log.w(TAG, "Invalid response: cardImage is null");
                return false;
            }
            if (cardImage.getType() == 1 && cardImage.getBitmap().getConfig() != android.graphics.Bitmap.Config.HARDWARE) {
                android.util.Log.w(TAG, "Invalid response: cardImage bitmaps must be hardware bitmaps");
                return false;
            }
            if (android.text.TextUtils.isEmpty(walletCard.getContentDescription())) {
                android.util.Log.w(TAG, "Invalid response: contentDescription is null");
                return false;
            }
            if (walletCard.getPendingIntent() == null) {
                android.util.Log.w(TAG, "Invalid response: pendingIntent is null");
                return false;
            }
        }
        return true;
    }

    private void removeLocationsFromResponse(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
        java.util.Iterator<android.service.quickaccesswallet.WalletCard> it = getWalletCardsResponse.getWalletCards().iterator();
        while (it.hasNext()) {
            it.next().removeCardLocations();
        }
    }
}
