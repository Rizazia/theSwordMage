package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.GustoSwordOrb;
import theSwordMage.powers.CoreStabilizationPower;

public class StormingCore extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(StormingCore.class.getSimpleName());
    public static final String IMG = makeCardPath("StormingCore.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = -2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;

    public StormingCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

        this.tags.add(TheSwordMage.Enums.CORE);

        cardsToPreview = new Normality();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard strike = new Strike();
        if (this.upgraded) {
            strike.upgraded = true;
        }

        AbstractDungeon.actionManager.addToBottom(new CreateAction(new MirrorCore(this.baseDamage, ID), GustoSwordOrb.ORB_ID, 1, true));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (AbstractDungeon.player.getPower(CoreStabilizationPower.POWER_ID) != null) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new MakeTempCardInDrawPileAction((AbstractCard) new Normality(), magicNumber, true, false, false, Settings.WIDTH * 0.65F, Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void applyPowers() {

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
