package theSwordMage.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ManaRefinementAction extends AbstractGameAction{
    private static final com.megacrit.cardcrawl.localization.UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");
    public static final String[] TEXT = UIStrings.TEXT;
    private final boolean upgradedAction;
    private CardGroup validCards;
    private final AbstractPlayer p;
    
    public ManaRefinementAction(boolean upgradedAction, int amount){
        p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        
        this.upgradedAction = upgradedAction;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (upgradedAction){
                p.masterDeck.group.forEach(c ->{
                    if (!c.upgraded && c.canUpgrade()){
                        validCards.group.add(c);
                    }
                });
            } else {
                p.hand.group.forEach(c ->{
                    if (!c.upgraded && c.canUpgrade()){
                        validCards.group.add(c);
                    }
                });
            }
            
            AbstractDungeon.gridSelectScreen.open(validCards, this.amount, true, TEXT[0]);
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractDungeon.gridSelectScreen.selectedCards.forEach(c -> {
                    c.upgrade(); 
                });
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            } 
        }        
    }
    
}
