package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import static theSwordMage.actions.SwordCheckActions.playerHasSword;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.FireSwordOrb;

public class Cauterize extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(Cauterize.class.getSimpleName());
    public static final String IMG = makeCardPath("Cauterize.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 2;

    private static final int HEAL = 3;
    private static final int UPGRADE_PLUS_HEAL = 2;

    private static final int HP_LOSS = 3;
    private static final int UPGRADE_MINUS_HP_LOSS = -1;

    public Cauterize() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        magicNumber = baseMagicNumber = HEAL;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = HP_LOSS;

        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction) new HealAction((AbstractCreature) p, (AbstractCreature) p, this.magicNumber));
        AbstractDungeon.player.decreaseMaxHealth(defaultSecondMagicNumber);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return playerHasSword(FireSwordOrb.ORB_ID);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_HEAL);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_MINUS_HP_LOSS);
            this.initializeDescription();
        }
    }
}
