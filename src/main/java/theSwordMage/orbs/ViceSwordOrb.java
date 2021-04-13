package theSwordMage.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theSwordMage.SwordMageMod;
import theSwordMage.powers.MirrorBladePower;

public class ViceSwordOrb extends SwordBase {

    public static final String ORB_ID = SwordMageMod.makeID("ViceSword");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private final AbstractPlayer p;

    public ViceSwordOrb(AbstractCard formBy, boolean isShatter) {

        super(formBy, ORB_ID, orbString.NAME, (int) (formBy.baseDamage * 0.25), Math.floorDiv((int) (formBy.baseDamage * 0.5), 1), DESCRIPTIONS[0], DESCRIPTIONS[2], "viceSword.png");

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
        (AbstractDungeon.getCurrRoom()).monsters.monsters.forEach(m -> {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new WeakPower((AbstractCreature) m, evokeAmount, false), evokeAmount, true));
        });

        returnFormByCard();
    }

    @Override
    public void onStartOfTurn() {
        final int MAX = 2;
        final int MIN = 1;
        int rand;
        AbstractMonster mon;

        int times = 1;
        if (p.getPower(MirrorBladePower.POWER_ID) != null) {
            times += p.getPower(MirrorBladePower.POWER_ID).amount;
        }

        for (int i = times; i > 0; i--) {
            rand = (int) (Math.random() * (MAX - MIN + 1) + MIN);
            mon = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);

            switch (rand) {
                case (1):
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction(mon, (AbstractCreature) p, (AbstractPower) new WeakPower(mon, 1, false), 1));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
                    break;
                case (2):
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction(mon, (AbstractCreature) p, (AbstractPower) new VulnerablePower(mon, 1, false), 1));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));
                    break;
                default:

            }
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ViceSwordOrb(formBy, isShatter);
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new FlameParticleEffect(cX, cY));
    }
}
