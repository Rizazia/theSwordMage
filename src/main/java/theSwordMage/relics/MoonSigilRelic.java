package theSwordMage.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeRelicOutlinePath;
import static theSwordMage.SwordMageMod.makeRelicPath;
import theSwordMage.cards.MoonDrop;
import theSwordMage.util.TextureLoader;

public class MoonSigilRelic extends CustomRelic {

    public static final String ID = SwordMageMod.makeID("MoonSigilRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MoonSigilRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MoonSigilRelic.png"));

    private static final int AMOUNT = 1;

    public MoonSigilRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new MoonDrop(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
