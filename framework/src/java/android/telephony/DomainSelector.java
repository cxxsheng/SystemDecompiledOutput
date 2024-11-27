package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DomainSelector {
    void finishSelection();

    void reselectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes);
}
