package theSwordMage.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import java.util.ArrayList;
import static theSwordMage.SwordMageMod.logger;
import static theSwordMage.actions.SwordCheckActions.isCardAllowedToCreate;
import theSwordMage.cards.AbstractDefaultCard;
import theSwordMage.cards.AilingCore;
import theSwordMage.cards.BurningCore;
import theSwordMage.cards.DangerousCore;
import theSwordMage.cards.FrozenCore;
import theSwordMage.cards.GildedCore;
import theSwordMage.cards.GreedyCore;
import theSwordMage.cards.PulsatingCore;
import theSwordMage.cards.StoneCore;
import theSwordMage.cards.StormingCore;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.characters.TheSwordMage.Enums;
import theSwordMage.orbs.*;

public class CreateAction extends AbstractGameAction{
    private static final com.megacrit.cardcrawl.localization.UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    private static final String[] TEXT = UIStrings.TEXT;
    private final ArrayList<AbstractCard> notAtks = new ArrayList<>();
    private final AbstractPlayer p;
    private String swordTypeId;
    private final int createAmount;
    private boolean forceShatter;
    private boolean isCoreDependant;
    
    public AbstractCard choice;
    
    public CreateAction(final String swordTypeId, final int createAmount) {
        p = AbstractDungeon.player;
        this.swordTypeId = swordTypeId;
        this.createAmount = createAmount;
        
        isCoreDependant = false;
        forceShatter = false;
        
        choice = null;
        
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public CreateAction(final AbstractCard card, final String swordTypeId, final int createAmount){
        this(swordTypeId, createAmount);
        
        choiceMade(card);
        isDone = true;
    }
    
    public CreateAction(final AbstractCard card, final String swordTypeId, final int createAmount, final boolean forceShatter){
        this(card, swordTypeId, createAmount);
        
        this.forceShatter = forceShatter;
    }
    
    public CreateAction(final boolean isCoreDependant, final String swordTypeId, final int amount){
        this(swordTypeId, amount);
        
        this.isCoreDependant = isCoreDependant;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            this.p.hand.group.stream().filter(c -> (!isCardAllowedToCreate(c))).forEachOrdered(c -> {
                this.notAtks.add(c);
            }); 
            if (this.notAtks.size() == this.p.hand.group.size()) {
              this.isDone = true;
              return;
            } 
            if (this.p.hand.group.size() - this.notAtks.size() == 1)
              for (AbstractCard c : this.p.hand.group) {
                if (isCardAllowedToCreate(c)) {
                  for (int i = 0; i < this.createAmount; i++){
                    if (isCoreDependant) coreCheck(c);
                    choiceMade(c);
                  }
                  this.isDone = true;
                  return;
                } 
              }  
            this.p.hand.group.removeAll(this.notAtks);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            } 
            if (this.p.hand.group.size() == 1) {
                for (int i = 0; i < this.createAmount; i++){
                  if (isCoreDependant) coreCheck(p.hand.getTopCard());
                  choiceMade(p.hand.getTopCard());
                }
                returnCards();
                this.isDone = true;
            } 
        } 
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(c -> {
                for (int i = 0; i < this.createAmount; i++){
                    if (isCoreDependant) coreCheck(c);
                    choiceMade(c);
                }
            }); 
        returnCards();
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.isDone = true;
    } 
    tickDuration();
}
    
    private void choiceMade(AbstractCard choice){  
        if (choice.tags.contains(Enums.CORE))choice.use(p, AbstractDungeon.getRandomMonster());
        if (choice.tags.contains(Enums.CAUSES_SHATTER)) forceShatter = true;
        p.hand.moveToExhaustPile(choice);
        
        if (swordTypeId.equals(BasicSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new BasicSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(FireSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new FireSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(IceSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new IceSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(EarthenSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new EarthenSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(GildedSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new GildedSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(GreedSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new GreedSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(GustoSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new GustoSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(VampricSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new VampricSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(ViceSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new ViceSwordOrb(choice, forceShatter)));
        else if (swordTypeId.equals(ViciousSwordOrb.ORB_ID)) addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new ViciousSwordOrb(choice, forceShatter)));
        else {
            logger.info("Failed to create a random ID for Sword Mage Create Sword Action.");
            addToTop((AbstractGameAction)new ChannelAction((AbstractOrb)new BasicSwordOrb(choice, forceShatter)));
        }
    }
    
    public void coreCheck(AbstractCard c){
        if (c.hasTag(TheSwordMage.Enums.CORE)){
            if (((AbstractDefaultCard)c).cardID.equals(BurningCore.ID)) swordTypeId = FireSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(FrozenCore.ID)) swordTypeId = IceSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(StoneCore.ID)) swordTypeId = EarthenSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(AilingCore.ID)) swordTypeId = ViceSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(GreedyCore.ID)) swordTypeId = GreedSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(GildedCore.ID)) swordTypeId = GildedSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(StormingCore.ID)) swordTypeId = GustoSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(PulsatingCore.ID)) swordTypeId = VampricSwordOrb.ORB_ID;
            else if (((AbstractDefaultCard)c).cardID.equals(DangerousCore.ID)) swordTypeId = ViciousSwordOrb.ORB_ID;
            //else the expected default value of swordTypeId = basicSwordOrb to create this class is maintained
        }
    }
    
    private void returnCards(){
        notAtks.forEach(c -> {
            p.hand.addToTop(c);
        });
        p.hand.refreshHandLayout();
    }
}
