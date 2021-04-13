package theSwordMage.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.characters.TheSwordMage.Enums;

public class StableCore extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(StableCore.class.getSimpleName());
    public static final String IMG = makeCardPath("StableCore.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = -2;
    private static final int DAMAGE = 16;
    private static final int UPGRADE_PLUS_DMG = 2;

    public StableCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;

        this.selfRetain = true;

        this.tags.add(Enums.CORE);
        this.tags.add(Enums.CAUSES_SHATTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void applyPowers() {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.tags.remove(this.tags.indexOf(Enums.CAUSES_SHATTER));
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
