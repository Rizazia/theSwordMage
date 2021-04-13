package theSwordMage.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makePowerPath;
import theSwordMage.util.TextureLoader;

public class StrikerFormPower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = SwordMageMod.makeID("StrikerFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    AbstractCard rand;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("StrikerFormPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("StrikerFormPower32.png"));

    public StrikerFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        for (int i = amount; i > 0; i--) {
            rand = randStrike();
            if (rand != null) {
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(rand, AbstractDungeon.getCurrRoom().monsters.getRandomMonster(), true, true));
            }

        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrikerFormPower(owner, source, amount);
    }

    private AbstractCard randStrike() {
        ArrayList<AbstractCard> strikes = new ArrayList<>();
        AbstractCard randStrike = null;

        AbstractDungeon.player.drawPile.group.forEach(c -> {
            if (c.hasTag(AbstractCard.CardTags.STRIKE)) {
                strikes.add(c);
            }
        });

        int max = strikes.size() - 1;
        int min = 0;

        if (strikes.size() > 0) {
            randStrike = strikes.get((int) (Math.random() * (max - min + 1) + min));
        }

        return randStrike;
    }
}
