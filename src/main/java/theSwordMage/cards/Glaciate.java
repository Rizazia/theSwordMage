package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import static theSwordMage.actions.SwordCheckActions.playerHasSword;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.IceSwordOrb;

public class Glaciate extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(Glaciate.class.getSimpleName());
    public static final String IMG = makeCardPath("Glaciate.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;

    public Glaciate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        block = 0;
        p.orbs.forEach(o -> {
            if (o instanceof EmptyOrbSlot == false) {
                if (upgraded) {
                    block += o.passiveAmount;
                } else if (o instanceof IceSwordOrb == false) {
                    block += o.passiveAmount;
                }
            }
        });

        rawDescription = rawDescription;
        return playerHasSword(IceSwordOrb.ORB_ID);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
