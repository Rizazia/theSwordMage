package theSwordMage.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theSwordMage.SwordMageMod;
import theSwordMage.powers.MirrorBladePower;

public class FireSwordOrb extends SwordBase {

    public static final String ORB_ID = SwordMageMod.makeID("FireSword");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private final AbstractPlayer p;

    public FireSwordOrb(AbstractCard formBy, boolean isShatter) {

        super(formBy, ORB_ID, orbString.NAME, formBy.baseDamage / 2, formBy.baseDamage, DESCRIPTIONS[0], DESCRIPTIONS[2], "fireSword.png");

        this.p = AbstractDungeon.player;
        this.isShatter = isShatter;

        if (basePassiveAmount < 1) {
            basePassiveAmount = 1;
        }

        angle = MathUtils.random(360.0f);
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + evokeAmount + DESCRIPTIONS[3];
    }

    @Override
    public void applyFocus() {
        passiveAmount = basePassiveAmount;
        evokeAmount = baseEvokeAmount;
        if (formBy != null) {
            description += formBy.name;
        }
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FLAME_BARRIER"));

        returnFormByCard();
    }

    @Override
    public void onEndOfTurn() {
        int times = 1;
        if (p.getPower(MirrorBladePower.POWER_ID) != null) {
            times += p.getPower(MirrorBladePower.POWER_ID).amount;
        }

        for (int i = times; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new VFXAction((AbstractGameEffect) new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new FireSwordOrb(formBy, isShatter);
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new FlameParticleEffect(cX, cY));
    }
}
