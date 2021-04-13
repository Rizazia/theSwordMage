package theSwordMage.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import theSwordMage.orbs.EarthenSwordOrb;
import theSwordMage.orbs.FireSwordOrb;
import theSwordMage.orbs.IceSwordOrb;

public class ElementalWall extends AbstractDynamicCard {

    public static final String ID = SwordMageMod.makeID(ElementalWall.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ElementalWall.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheSwordMage.Enums.COLOR_BLUE;

    private static final int COST = 2;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public ElementalWall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int MAX = 3;
        final int MIN = 1;
        int rand = (int) (Math.random() * (MAX - MIN + 1) + MIN);
        String idToCreate;

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
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

                idToCreate = FireSwordOrb.ORB_ID;
        }
        AbstractDungeon.actionManager.addToBottom(new CreateAction(idToCreate, 1));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
