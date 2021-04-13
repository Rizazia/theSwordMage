package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.powers.CoreStabilizationPower;

public class PulsatingCore extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(PulsatingCore.class.getSimpleName());
    public static final String IMG = makeCardPath("PulsatingCore.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = -2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public int specialDamage;

    public PulsatingCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

        this.tags.add(TheSwordMage.Enums.CORE);

        cardsToPreview = new Pain();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int toHeal = magicNumber * AbstractDungeon.getCurrRoom().monsters.monsters.size();

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, magicNumber, damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));

        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new HealAction((AbstractCreature) p, (AbstractCreature) p, toHeal));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (AbstractDungeon.player.getPower(CoreStabilizationPower.POWER_ID) != null) {
            for (int i = 0; i < magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new MakeTempCardInDrawPileAction(new Pain(), 1, true, false, false, Settings.WIDTH * 0.65F, Settings.HEIGHT / 2.0F));
            }
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
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
