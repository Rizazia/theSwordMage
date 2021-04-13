package theSwordMage.characters;

import theSwordMage.cards.*;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSwordMage.SwordMageMod;
import java.util.ArrayList;

import static theSwordMage.SwordMageMod.*;
import theSwordMage.relics.*;
import static theSwordMage.characters.TheSwordMage.Enums.COLOR_BLUE;

public class TheSwordMage extends CustomPlayer {

    public static final Logger logger = LogManager.getLogger(SwordMageMod.class.getName());

    public static class Enums {

        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_SWORD_MAGE;
        @SpireEnum(name = "SWORD_MAGE_BLUE_COLOR") //#76b5c5
        public static AbstractCard.CardColor COLOR_BLUE;
        @SpireEnum(name = "SWORD_MAGE_BLUE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        @SpireEnum
        public static AbstractCard.CardTags NOT_SWORDABLE;
        @SpireEnum
        public static AbstractCard.CardTags CORE;
        @SpireEnum
        public static AbstractCard.CardTags CAUSES_SHATTER;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 3;

    private static final String ID = makeID("SwordMage");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public static final String[] orbTextures = {
        "theSwordMageResources/images/char/swordMage/orb/layer1.png",
        "theSwordMageResources/images/char/swordMage/orb/layer2.png",
        "theSwordMageResources/images/char/swordMage/orb/layer3.png",
        "theSwordMageResources/images/char/swordMage/orb/layer4.png",
        "theSwordMageResources/images/char/swordMage/orb/layer5.png",
        "theSwordMageResources/images/char/swordMage/orb/layer6.png",
        "theSwordMageResources/images/char/swordMage/orb/layer1d.png",
        "theSwordMageResources/images/char/swordMage/orb/layer2d.png",
        "theSwordMageResources/images/char/swordMage/orb/layer3d.png",
        "theSwordMageResources/images/char/swordMage/orb/layer4d.png",
        "theSwordMageResources/images/char/swordMage/orb/layer5d.png",};

    public TheSwordMage(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theSwordMageResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theSwordMageResources/images/char/swordMage/spriter/swordMageSCML.scml"));

        initializeClass(null,
                THE_DEFAULT_SHOULDER_2,
                THE_DEFAULT_SHOULDER_1,
                THE_DEFAULT_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);

    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(CreateBasic.ID);
        retVal.add(Cast.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ManaControllerRelic.ID);

        UnlockTracker.markRelicAsSeen(ManaControllerRelic.ID);
        UnlockTracker.markRelicAsSeen(DaystrikerSwordRelic.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_BLUE;
    }

    @Override
    public Color getCardTrailColor() {
        return theSwordMage.SwordMageMod.SWORD_MAGE_BLUE;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheSwordMage(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return theSwordMage.SwordMageMod.SWORD_MAGE_BLUE;
    }

    @Override
    public Color getSlashAttackColor() {
        return theSwordMage.SwordMageMod.SWORD_MAGE_BLUE;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
