package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pride;
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
import theSwordMage.orbs.GildedSwordOrb;
import theSwordMage.powers.CoreStabilizationPower;

public class GildedCore extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(GildedCore.class.getSimpleName());
    public static final String IMG = makeCardPath("GildedCore.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = -2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;

    public GildedCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;

        this.tags.add(TheSwordMage.Enums.CORE);

        cardsToPreview = new Pride();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard strike = new Strike();
        if (this.upgraded) {
            strike.upgraded = true;
        }

        AbstractDungeon.actionManager.addToBottom(new CreateAction(new MirrorCore(this.baseDamage, ID), GildedSwordOrb.ORB_ID, 1, true));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (!AbstractDungeon.player.hasPower(CoreStabilizationPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Pride(), 1, true, false, false, Settings.WIDTH * 0.65F, Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
