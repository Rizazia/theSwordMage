package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import static theSwordMage.actions.SwordCheckActions.playerHasSword;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.characters.TheSwordMage.Enums;
import theSwordMage.orbs.IceSwordOrb;

public class IcicleSpears extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(IcicleSpears.class.getSimpleName());
    public static final String IMG = makeCardPath("IcicleSpears.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public IcicleSpears() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damageTypeForTurn = DamageInfo.DamageType.THORNS;

        tags.add(Enums.NOT_SWORDABLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.orbs.forEach(o -> {
            if (o instanceof EmptyOrbSlot == false) {
                if (o instanceof IceSwordOrb) {
                    addToBot(new DamageAction(m, new DamageInfo(p, o.passiveAmount, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
            }
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        damage = 0;
        p.orbs.forEach(o -> {
            if (o instanceof EmptyOrbSlot == false) {
                if (upgraded) {
                    block += o.passiveAmount;
                } else if (o instanceof IceSwordOrb == false) {
                    block += o.passiveAmount;
                }
            }
        });

        return playerHasSword(IceSwordOrb.ORB_ID);
    }

    @Override
    public void applyPowers() {
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
