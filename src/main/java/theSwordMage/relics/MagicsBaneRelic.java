package theSwordMage.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSwordMage.SwordMageMod;
import static theSwordMage.SwordMageMod.makeRelicOutlinePath;
import static theSwordMage.SwordMageMod.makeRelicPath;
import theSwordMage.util.TextureLoader;

public class MagicsBaneRelic extends CustomRelic {

    public static final String ID = SwordMageMod.makeID("MagicsBaneRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagicsBaneRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagicsBaneRelic.png"));

    private static final int STRENGTH_AMOUNT = 3;

    public MagicsBaneRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH_AMOUNT), STRENGTH_AMOUNT));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        if (AbstractDungeon.player.orbs.size() > 0) {
            AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(AbstractDungeon.player.orbs.size()));
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        if (AbstractDungeon.player.orbs.size() > 0) {
            flash();
            AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(AbstractDungeon.player.orbs.size()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH_AMOUNT + DESCRIPTIONS[1];
    }
}
