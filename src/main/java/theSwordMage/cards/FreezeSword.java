package theSwordMage.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.IceSwordOrb;

public class FreezeSword extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(FreezeSword.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("FreezeSword.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final String ID_TO_CREATE = IceSwordOrb.ORB_ID;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public FreezeSword() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CreateAction(ID_TO_CREATE, 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
