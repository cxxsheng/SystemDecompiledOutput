package android.view.textservice;

/* loaded from: classes4.dex */
public final class TextServicesManager {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = android.view.textservice.TextServicesManager.class.getSimpleName();

    @java.lang.Deprecated
    private static android.view.textservice.TextServicesManager sInstance;
    private final android.view.inputmethod.InputMethodManager mInputMethodManager;
    private final com.android.internal.textservice.ITextServicesManager mService = com.android.internal.textservice.ITextServicesManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TEXT_SERVICES_MANAGER_SERVICE));
    private final int mUserId;

    private TextServicesManager(int i, android.view.inputmethod.InputMethodManager inputMethodManager) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mUserId = i;
        this.mInputMethodManager = inputMethodManager;
    }

    public static android.view.textservice.TextServicesManager createInstance(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        return new android.view.textservice.TextServicesManager(context.getUserId(), (android.view.inputmethod.InputMethodManager) context.getSystemService(android.view.inputmethod.InputMethodManager.class));
    }

    public static android.view.textservice.TextServicesManager getInstance() {
        android.view.textservice.TextServicesManager textServicesManager;
        synchronized (android.view.textservice.TextServicesManager.class) {
            if (sInstance == null) {
                try {
                    sInstance = new android.view.textservice.TextServicesManager(android.os.UserHandle.myUserId(), null);
                } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
            textServicesManager = sInstance;
        }
        return textServicesManager;
    }

    public android.view.inputmethod.InputMethodManager getInputMethodManager() {
        return this.mInputMethodManager;
    }

    private static java.lang.String parseLanguageFromLocaleString(java.lang.String str) {
        int indexOf = str.indexOf(95);
        if (indexOf < 0) {
            return str;
        }
        return str.substring(0, indexOf);
    }

    public android.view.textservice.SpellCheckerSession newSpellCheckerSession(android.os.Bundle bundle, java.util.Locale locale, android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener, boolean z) {
        android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder supportedAttributes = new android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder().setLocale(locale).setShouldReferToSpellCheckerLanguageSettings(z).setSupportedAttributes(7);
        if (bundle != null) {
            supportedAttributes.setExtras(bundle);
        }
        return newSpellCheckerSession(supportedAttributes.build(), new android.os.HandlerExecutor(new android.os.Handler()), spellCheckerSessionListener);
    }

    public android.view.textservice.SpellCheckerSession newSpellCheckerSession(android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams spellCheckerSessionParams, java.util.concurrent.Executor executor, android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener) {
        android.view.textservice.SpellCheckerSubtype spellCheckerSubtype;
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(spellCheckerSessionListener);
        java.util.Locale locale = spellCheckerSessionParams.getLocale();
        if (!spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings() && locale == null) {
            throw new java.lang.IllegalArgumentException("Locale should not be null if you don't refer settings.");
        }
        if (spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings() && !isSpellCheckerEnabled()) {
            return null;
        }
        try {
            android.view.textservice.SpellCheckerInfo currentSpellChecker = this.mService.getCurrentSpellChecker(this.mUserId, null);
            if (currentSpellChecker == null) {
                return null;
            }
            if (spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings()) {
                spellCheckerSubtype = getCurrentSpellCheckerSubtype(true);
                if (spellCheckerSubtype == null) {
                    return null;
                }
                if (locale != null) {
                    java.lang.String parseLanguageFromLocaleString = parseLanguageFromLocaleString(spellCheckerSubtype.getLocale());
                    if (parseLanguageFromLocaleString.length() < 2 || !locale.getLanguage().equals(parseLanguageFromLocaleString)) {
                        return null;
                    }
                }
            } else {
                java.lang.String obj = locale.toString();
                int i = 0;
                android.view.textservice.SpellCheckerSubtype spellCheckerSubtype2 = null;
                while (true) {
                    if (i >= currentSpellChecker.getSubtypeCount()) {
                        spellCheckerSubtype = spellCheckerSubtype2;
                        break;
                    }
                    android.view.textservice.SpellCheckerSubtype subtypeAt = currentSpellChecker.getSubtypeAt(i);
                    java.lang.String locale2 = subtypeAt.getLocale();
                    java.lang.String parseLanguageFromLocaleString2 = parseLanguageFromLocaleString(locale2);
                    if (locale2.equals(obj)) {
                        spellCheckerSubtype = subtypeAt;
                        break;
                    }
                    if (parseLanguageFromLocaleString2.length() >= 2 && locale.getLanguage().equals(parseLanguageFromLocaleString2)) {
                        spellCheckerSubtype2 = subtypeAt;
                    }
                    i++;
                }
            }
            if (spellCheckerSubtype == null) {
                return null;
            }
            android.view.textservice.SpellCheckerSession spellCheckerSession = new android.view.textservice.SpellCheckerSession(currentSpellChecker, this, spellCheckerSessionListener, executor);
            try {
                this.mService.getSpellCheckerService(this.mUserId, currentSpellChecker.getId(), spellCheckerSubtype.getLocale(), spellCheckerSession.getTextServicesSessionListener(), spellCheckerSession.getSpellCheckerSessionListener(), spellCheckerSessionParams.getExtras(), spellCheckerSessionParams.getSupportedAttributes());
                return spellCheckerSession;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    public android.view.textservice.SpellCheckerInfo[] getEnabledSpellCheckers() {
        try {
            return this.mService.getEnabledSpellCheckers(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.view.textservice.SpellCheckerInfo> getEnabledSpellCheckerInfos() {
        android.view.textservice.SpellCheckerInfo[] enabledSpellCheckers = getEnabledSpellCheckers();
        return enabledSpellCheckers != null ? java.util.Arrays.asList(enabledSpellCheckers) : java.util.Collections.emptyList();
    }

    public android.view.textservice.SpellCheckerInfo getCurrentSpellCheckerInfo() {
        try {
            return this.mService.getCurrentSpellChecker(this.mUserId, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.textservice.SpellCheckerInfo getCurrentSpellChecker() {
        return getCurrentSpellCheckerInfo();
    }

    public android.view.textservice.SpellCheckerSubtype getCurrentSpellCheckerSubtype(boolean z) {
        try {
            return this.mService.getCurrentSpellCheckerSubtype(this.mUserId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSpellCheckerEnabled() {
        try {
            return this.mService.isSpellCheckerEnabled(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void finishSpellCheckerService(com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) {
        try {
            this.mService.finishSpellCheckerService(this.mUserId, iSpellCheckerSessionListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
