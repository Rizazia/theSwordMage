package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.actions.SwordCheckActions;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.IceSwordOrb;

public class NightFreeze extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(NightFreeze.class.getSimpleName());
    public static final String IMG = makeCardPath("NightFreeze.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final String ID_TO_CREATE = IceSwordOrb.ORB_ID;

    public NightFreeze() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;

        cardsToPreview = new MoonDrop();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, damage));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new MoonDrop(), defaultSecondMagicNumber));

        if (SwordCheckActions.playerHasSword(IceSwordOrb.ORB_ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, damage));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new MoonDrop(), defaultSecondMagicNumber));
        } else if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new CreateAction(ID_TO_CREATE, 1));
        }

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
