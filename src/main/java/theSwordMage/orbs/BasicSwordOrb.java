package theSwordMage.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import theSwordMage.SwordMageMod;
import theSwordMage.powers.MirrorBladePower;

public class BasicSwordOrb extends SwordBase {

    public static final String ORB_ID = SwordMageMod.makeID("BasicSword");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private final AbstractPlayer p;

    public BasicSwordOrb(AbstractCard formBy, boolean isShatter) {

        super(formBy, ORB_ID, orbString.NAME, formBy.baseDamage / 2, formBy.baseDamage, DESCRIPTIONS[0], DESCRIPTIONS[2], "basicSword.png");

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
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(evokeAmount, true, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_FAST_1"));

        returnFormByCard();
    }

    @Override
    public void onEndOfTurn() {
        int times = 1;
        if (p.getPower(MirrorBladePower.POWER_ID) != null) {
            times += p.getPower(MirrorBladePower.POWER_ID).amount;
        }

        for (int i = times; i > 0; i--) {
            AbstractMonster mon = null;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if ((!m.isDead) && (!m.isDeadOrEscaped() && !(m.isDying))) {
                    mon = m;
                    break;
                }
            }
            if (mon != null) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, new DamageInfo(p, passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new BasicSwordOrb(formBy, isShatter);
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }
}
