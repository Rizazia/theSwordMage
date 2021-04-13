package theSwordMage.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeRelicOutlinePath;
import static theSwordMage.SwordMageMod.makeRelicPath;
import theSwordMage.actions.CreateAction;
import theSwordMage.cards.ManaCore;
import theSwordMage.orbs.BasicSwordOrb;
import theSwordMage.util.TextureLoader;

public class ManaControllerRelic extends CustomRelic {

    public static final String ID = SwordMageMod.makeID("ManaControllerRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ManaControllerRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ManaControllerRelic.png"));

    private static final String ID_TO_CREATE = BasicSwordOrb.ORB_ID;

    public ManaControllerRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        if (!AbstractDungeon.player.hasRelic(MagicsBaneRelic.ID)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new CreateAction(new ManaCore(), ID_TO_CREATE, 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
