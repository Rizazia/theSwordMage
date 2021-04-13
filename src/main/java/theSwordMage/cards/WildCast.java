package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.*;

public class WildCast extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(WildCast.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WildCast.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    public WildCast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        if (!upgraded) {
            this.exhaustOnUseOnce = true;
        }
        damage = baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String randomId = getRandId();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new CreateAction(randomId, 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }

    private String getRandId() {
        final int MAX = 9;
        final int MIN = 1;
        int rand = (int) (Math.random() * (MAX - MIN + 1) + MIN);

        switch (rand) {
            case (1):
                return BasicSwordOrb.ORB_ID;
            case (2):
                return EarthenSwordOrb.ORB_ID;
            case (3):
                return FireSwordOrb.ORB_ID;
            case (4):
                return GildedSwordOrb.ORB_ID;
            case (5):
                return GreedSwordOrb.ORB_ID;
            case (6):
                return GustoSwordOrb.ORB_ID;
            case (7):
                return IceSwordOrb.ORB_ID;
            case (8):
                return VampricSwordOrb.ORB_ID;
            case (9):
                return ViciousSwordOrb.ORB_ID;
            default:
                Logger logger = LogManager.getLogger(SwordMageMod.class.getName());
                logger.info("Failed to create a random ID for Sword Mage card Wild Cast.");
                return BasicSwordOrb.ORB_ID;
        }
    }
}
