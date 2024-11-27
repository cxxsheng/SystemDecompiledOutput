package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class SystemLocaleWrapper {
    private static final java.util.concurrent.atomic.AtomicReference<android.os.LocaleList> sSystemLocale = new java.util.concurrent.atomic.AtomicReference<>(new android.os.LocaleList(java.util.Locale.getDefault()));

    interface Callback {
        void onLocaleChanged(@android.annotation.NonNull android.os.LocaleList localeList, @android.annotation.NonNull android.os.LocaleList localeList2);
    }

    private SystemLocaleWrapper() {
    }

    @android.annotation.NonNull
    static android.os.LocaleList get(int i) {
        return sSystemLocale.get();
    }

    static void onStart(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.SystemLocaleWrapper.Callback callback, @android.annotation.NonNull android.os.Handler handler) {
        sSystemLocale.set(context.getResources().getConfiguration().getLocales());
        context.registerReceiver(new com.android.server.inputmethod.SystemLocaleWrapper.LocaleChangeListener(context, callback), new android.content.IntentFilter("android.intent.action.LOCALE_CHANGED"), null, handler);
    }

    private static final class LocaleChangeListener extends android.content.BroadcastReceiver {

        @android.annotation.NonNull
        private final com.android.server.inputmethod.SystemLocaleWrapper.Callback mCallback;

        @android.annotation.NonNull
        private final android.content.Context mContext;

        LocaleChangeListener(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.SystemLocaleWrapper.Callback callback) {
            this.mContext = context;
            this.mCallback = callback;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (!"android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                return;
            }
            android.os.LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
            android.os.LocaleList localeList = (android.os.LocaleList) com.android.server.inputmethod.SystemLocaleWrapper.sSystemLocale.getAndSet(locales);
            if (!java.util.Objects.equals(locales, localeList)) {
                this.mCallback.onLocaleChanged(localeList, locales);
            }
        }
    }
}
