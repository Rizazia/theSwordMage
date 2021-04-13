package theSwordMage.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makePowerPath;
import theSwordMage.util.TextureLoader;

public class ExperiencedCuttingPower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = SwordMageMod.makeID("ExperiencedCuttingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int hits;
    private static final int HITS_TO_PROC = 5;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ExperiencedCuttingPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ExperiencedCuttingPower32.png"));

    public ExperiencedCuttingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        hits = 0;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onInflictDamage(info, damageAmount, target);

        hits++;
        if (hits >= HITS_TO_PROC) {
            flash();
            addToBot((AbstractGameAction) new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, (AbstractPower) new StrengthPower(AbstractDungeon.player, amount), amount));
            hits = 0;
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);

        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont,
                Integer.toString(HITS_TO_PROC - hits), x, y + 15, this.fontScale, Color.BLUE);
    }

    @Override
    public AbstractPower makeCopy() {
        return new ExperiencedCuttingPower(owner, source, amount);
    }
}
