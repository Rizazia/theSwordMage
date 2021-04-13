package theSwordMage.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theSwordMage.SwordMageMod;
import theSwordMage.powers.MirrorBladePower;

public class EarthenSwordOrb extends SwordBase {

    public static final String ORB_ID = SwordMageMod.makeID("EarthenSword");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private final AbstractPlayer p;

    public EarthenSwordOrb(AbstractCard formBy, boolean isShatter) {

        super(formBy, ORB_ID, orbString.NAME, (int) (formBy.baseDamage * 0.3), Math.floorDiv((int) (formBy.baseDamage * 0.8), 1), DESCRIPTIONS[0], DESCRIPTIONS[3], "earthenSword.png");

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
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + evokeAmount + DESCRIPTIONS[3] + evokeAmount + DESCRIPTIONS[4];
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
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(evokeAmount, true, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("BLOCK_GAIN_3"));

        returnFormByCard();
    }

    @Override
    public void onEndOfTurn() {
        int times = 1;
        if (p.getPower(MirrorBladePower.POWER_ID) != null) {
            times += p.getPower(MirrorBladePower.POWER_ID).amount;
        }

        for (int i = times; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new VFXAction((AbstractGameEffect) new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new GainBlockAction((AbstractCreature) p, (AbstractCreature) AbstractDungeon.player, this.passiveAmount));
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
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
