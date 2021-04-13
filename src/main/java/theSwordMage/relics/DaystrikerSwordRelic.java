package theSwordMage.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeRelicOutlinePath;
import static theSwordMage.SwordMageMod.makeRelicPath;
import theSwordMage.util.TextureLoader;

public class DaystrikerSwordRelic extends CustomRelic {

    public static final String ID = SwordMageMod.makeID("DaystrikerSwordRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DaystrikerSwordRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DaystrikerSwordRelic.png"));

    private static final int BASE_ACTIVISIONS = 1;
    private static final int STR_AMOUNT = 1;
    private int counter = BASE_ACTIVISIONS;

    public DaystrikerSwordRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        flash();
        counter = BASE_ACTIVISIONS;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (counter > 0 && card.type.equals(CardType.ATTACK)) {
            flash();
            counter--;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_AMOUNT), STR_AMOUNT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STR_AMOUNT + DESCRIPTIONS[1];
    }
}
