package theSwordMage.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeRelicOutlinePath;
import static theSwordMage.SwordMageMod.makeRelicPath;
import theSwordMage.util.TextureLoader;

public class MadMagesChronicleRelic extends CustomRelic {

    public static final String ID = SwordMageMod.makeID("MadMagesChronicleRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MadMagesChronicleRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MadMagesChronicleRelic.png"));

    private static final int STRENGTH_AMOUNT = 10;

    public MadMagesChronicleRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -STRENGTH_AMOUNT), -STRENGTH_AMOUNT));
    }

    @Override
    public void atTurnStart() {
        counter = 0;
        super.atTurnStart();
    }

    @Override
    public void onEvokeOrb(AbstractOrb ammo) {
        if (counter == 0) {
            counter++;
            this.flash();

            super.onEvokeOrb(ammo);

            addToBot(new ChannelAction(ammo));
            addToBot(new EvokeOrbAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH_AMOUNT + DESCRIPTIONS[1];
    }
}
