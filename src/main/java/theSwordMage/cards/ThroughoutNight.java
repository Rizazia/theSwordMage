package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.characters.TheSwordMage;

public class ThroughoutNight extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(ThroughoutNight.class.getSimpleName());
    public static final String IMG = makeCardPath("ThroughoutNight.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public ThroughoutNight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = moonDropCount(p); i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new MoonDrop(), magicNumber));
    }

    private int moonDropCount(AbstractPlayer p) {
        int count = 0;

        for (int i = 0; i < p.drawPile.size(); i++) {
            if (p.drawPile.getNCardFromTop(i).cardID.equals(MoonDrop.ID)) {
                count++;
            }
        }
        for (int i = 0; i < p.hand.size(); i++) {
            if (p.hand.getNCardFromTop(i).cardID.equals(MoonDrop.ID)) {
                count++;
            }
        }
        for (int i = 0; i < p.discardPile.size(); i++) {
            if (p.hand.getNCardFromTop(i).cardID.equals(MoonDrop.ID)) {
                count++;
            }
        }

        return count;
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
