package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import static theSwordMage.actions.SwordCheckActions.playerHasSword;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.FireSwordOrb;

public class BloodMoonSlash extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(BloodMoonSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("BloodMoonSlash.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int MOON_DROP_TO_DISCARD = 1;
    private static final int UPGRADE_PLUS_MOONS = 1;

    public BloodMoonSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = MOON_DROP_TO_DISCARD;

        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new MoonDrop(), defaultSecondMagicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return playerHasSword(FireSwordOrb.ORB_ID);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_MOONS);
            this.initializeDescription();
        }
    }
}
