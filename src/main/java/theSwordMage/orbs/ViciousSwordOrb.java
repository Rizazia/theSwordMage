package theSwordMage.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theSwordMage.SwordMageMod;
import theSwordMage.powers.MirrorBladePower;

public class ViciousSwordOrb extends SwordBase {

    public static final String ORB_ID = SwordMageMod.makeID("ViciousSword");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private final AbstractPlayer p;

    public ViciousSwordOrb(AbstractCard formBy, boolean isShatter) {

        super(formBy, ORB_ID, orbString.NAME, (int) (formBy.baseDamage * 0.5), formBy.baseDamage / 2, DESCRIPTIONS[0], DESCRIPTIONS[3], "viciousSword.png");

        this.p = AbstractDungeon.player;
        this.isShatter = isShatter;

        if (basePassiveAmount < 1) {
            basePassiveAmount = 1;
        }
        if (baseEvokeAmount < 2) {
            baseEvokeAmount = 2;
        }

        angle = MathUtils.random(360.0f);
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + evokeAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + passiveAmount + DESCRIPTIONS[4];
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, passiveAmount / 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));

        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new DamageAllEnemiesAction(p, passiveAmount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));
        (AbstractDungeon.getCurrRoom()).monsters.monsters.forEach(m -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, passiveAmount, false), passiveAmount, true));
        });

        returnFormByCard();
    }

    @Override
    public void onEndOfTurn() {
        int times = evokeAmount;
        if (p.getPower(MirrorBladePower.POWER_ID) != null) {
            times += p.getPower(MirrorBladePower.POWER_ID).amount;
        }

        for (int i = 0; i < times; i++) {
            int rand = MathUtils.random(0, 1);

            switch (rand) {
                case (0):
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, Math.floorDiv((int) (passiveAmount / 2), 1), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new StrengthPower((AbstractCreature) p, 1), 1));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
                    break;
                default:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
            }
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ViciousSwordOrb(formBy, isShatter);
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new FlameParticleEffect(cX, cY));
    }
}
