package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.characters.TheSwordMage.Enums;
import theSwordMage.orbs.BasicSwordOrb;

public class ManaStrike extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(ManaStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("ManaStrike.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final String ID_TO_CREATE = BasicSwordOrb.ORB_ID;

    public ManaStrike() {
        this(0);
    }

    public ManaStrike(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        timesUpgraded = upgrades;

        tags.add(CardTags.STRIKE);
        tags.add(Enums.CAUSES_SHATTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new CreateAction(this.makeCopy(), ID_TO_CREATE, magicNumber, true));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
        timesUpgraded++;
        if (timesUpgraded % 2 == 1) {
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
        }
        upgraded = true;
        name = languagePack.getCardStrings(ID).NAME + " + " + timesUpgraded;
        initializeTitle();
    }
}
