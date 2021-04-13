package theSwordMage.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makePowerPath;
import theSwordMage.cards.MoonDrop;
import theSwordMage.util.TextureLoader;

public class DanceUnderTheMoonPower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = SwordMageMod.makeID("DanceUnderTheMoonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    AbstractCard rand;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DanceUnderTheMoonPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DanceUnderTheMoonPower32.png"));

    public DanceUnderTheMoonPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + DESCRIPTIONS[4];
        }
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < AbstractDungeon.player.discardPile.size(); j++) {
                if (AbstractDungeon.player.discardPile.getNCardFromTop(j).cardID.equals(MoonDrop.ID)) {
                    AbstractCard moon = AbstractDungeon.player.discardPile.getNCardFromTop(j).makeCopy();
                    AbstractDungeon.player.discardPile.removeCard(AbstractDungeon.player.discardPile.getNCardFromTop(j));
                    AbstractDungeon.player.hand.addToBottom(moon);
                    break;
                }
            }
            for (int j = 0; j < AbstractDungeon.player.drawPile.size(); j++) {
                if (AbstractDungeon.player.drawPile.getNCardFromTop(j).cardID.equals(MoonDrop.ID)) {
                    AbstractCard moon = AbstractDungeon.player.drawPile.getNCardFromTop(j).makeCopy();
                    AbstractDungeon.player.drawPile.removeCard(AbstractDungeon.player.drawPile.getNCardFromTop(j));
                    AbstractDungeon.player.hand.addToBottom(moon);
                    break;
                }
            }
        }

        super.atStartOfTurn();
    }

    @Override
    public AbstractPower makeCopy() {
        return new DanceUnderTheMoonPower(owner, source, amount);
    }
}
