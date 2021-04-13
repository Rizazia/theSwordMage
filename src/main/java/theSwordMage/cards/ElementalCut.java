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
import theSwordMage.orbs.EarthenSwordOrb;
import theSwordMage.orbs.FireSwordOrb;
import theSwordMage.orbs.IceSwordOrb;

public class ElementalCut extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(ElementalCut.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ElementalCut.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;

    public ElementalCut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int MAX = 3;
        final int MIN = 1;
        int rand = (int) (Math.random() * (MAX - MIN + 1) + MIN);
        String idToCreate;

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        switch (rand) {
            case 1:
                idToCreate = FireSwordOrb.ORB_ID;
                break;
            case 2:
                idToCreate = IceSwordOrb.ORB_ID;
                break;
            case 3:
                idToCreate = EarthenSwordOrb.ORB_ID;
                break;
            default:
                Logger logger = LogManager.getLogger(SwordMageMod.class.getName());
                logger.info("Failed to create a random ID for Sword Mage card Elemental Cut.");
                idToCreate = FireSwordOrb.ORB_ID;
        }
        AbstractDungeon.actionManager.addToBottom(new CreateAction(idToCreate, 1));

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
