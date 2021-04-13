package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.SwordBase;

public class ShatterStorm extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(ShatterStorm.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ShatterStorm.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = -1;

    private int focusToLose = 0;
    private int effect = 0;

    public ShatterStorm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        showEvokeValue = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        effect = EnergyPanel.totalCount;

        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        if (!upgraded) {
            effect--;
        }

        focusToLose = 0;

        if (effect > 0) {
            p.orbs.forEach((AbstractOrb orb) -> {
                if (orb instanceof EmptyOrbSlot == false) {
                    if (orb instanceof SwordBase) {
                        ((SwordBase) orb).isShatter = true;
                    }

                    for (int i = 0; i < effect - 1; i++) {
                        addToBot(new EvokeWithoutRemovingOrbAction(1));
                        focusToLose++;
                    }

                    addToBot(new EvokeOrbAction(1));
                    focusToLose++;
                }
            });
        }

        p.energy.use(EnergyPanel.totalCount);
        addToBot((AbstractGameAction) new ApplyPowerAction(p, p, new FocusPower(p, -focusToLose), -focusToLose));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
