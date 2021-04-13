package theSwordMage.cards;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeCardPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.orbs.GildedSwordOrb;
import theSwordMage.orbs.GreedSwordOrb;
import theSwordMage.orbs.GustoSwordOrb;

public class GCreate extends AbstractDynamicCard implements ModalChoice.Callback {

    public static final String ID = SwordMageMod.makeID(GCreate.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GCreate.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String GREED_ID = GreedSwordOrb.ORB_ID;
    public static final String GUSTO_ID = GustoSwordOrb.ORB_ID;
    public static final String GILDED_ID = GildedSwordOrb.ORB_ID;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 1;

    private final ModalChoice modal;

    public GCreate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        modal = new ModalChoiceBuilder()
                .setCallback(this)
                .setColor(AbstractCard.CardColor.COLORLESS)
                .addOption("Create a Gilded Sword.", AbstractCard.CardTarget.NONE)
                .setColor(AbstractCard.CardColor.COLORLESS)
                .addOption("Create a Greed Sword.", AbstractCard.CardTarget.NONE)
                .setColor(AbstractCard.CardColor.COLORLESS)
                .addOption("Create a Gusto Sword.", AbstractCard.CardTarget.NONE)
                .create();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            final int MAX = 3;
            final int MIN = 1;
            final int rand = (int) (Math.random() * (MAX - MIN + 1) + MIN);

            switch (rand) {
                case (1):
                    AbstractDungeon.actionManager.addToBottom(new CreateAction(GILDED_ID, 1));
                    break;
                case (2):
                    AbstractDungeon.actionManager.addToBottom(new CreateAction(GREED_ID, 1));
                    break;
                case (3):
                    AbstractDungeon.actionManager.addToBottom(new CreateAction(GUSTO_ID, 1));
                    break;
                default:

                    AbstractDungeon.actionManager.addToBottom(new CreateAction(GUSTO_ID, 1));
            }
        } else {
            modal.open();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer ap, AbstractMonster am, int i) {
        switch (i) {
            case (1):
                AbstractDungeon.actionManager.addToBottom(new CreateAction(GILDED_ID, 1));
                break;
            case (2):
                AbstractDungeon.actionManager.addToBottom(new CreateAction(GREED_ID, 1));
                break;
            case (3):
                AbstractDungeon.actionManager.addToBottom(new CreateAction(GUSTO_ID, 1));
                break;
            default:

                AbstractDungeon.actionManager.addToBottom(new CreateAction(GUSTO_ID, 1));
        }
    }
}
