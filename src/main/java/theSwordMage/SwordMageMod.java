package theSwordMage;

import theSwordMage.cards.AbstractDefaultCard;
import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.util.IDCheckDontTouchPls;
import theSwordMage.util.TextureLoader;
import theSwordMage.variables.DefaultCustomVariable;
import theSwordMage.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import theSwordMage.relics.*;

@SpireInitializer
public class SwordMageMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(SwordMageMod.class.getName());
    private static String modID;

    public static Properties theSwordMageDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;
    private static final String MODNAME = "The Sword Mage";
    private static final String AUTHOR = "Rizazia";
    private static final String DESCRIPTION = "Adds the Sword Mage as a playable class.";

    public static final Color SWORD_MAGE_BLUE = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f);
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f);
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f);

    private static final String ATTACK_DEFAULT_GRAY = "theSwordMageResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theSwordMageResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theSwordMageResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "theSwordMageResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theSwordMageResources/images/512/card_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theSwordMageResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theSwordMageResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theSwordMageResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theSwordMageResources/images/1024/card_default_gray_orb.png";

    private static final String THE_DEFAULT_BUTTON = "theSwordMageResources/images/charSelect/Button.png";
    private static final String THE_DEFAULT_PORTRAIT = "theSwordMageResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theSwordMageResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theSwordMageResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theSwordMageResources/images/char/defaultCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theSwordMageResources/images/Badge.png";

    public static final String THE_DEFAULT_SKELETON_ATLAS = "theSwordMageResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theSwordMageResources/images/char/defaultCharacter/skeleton.json";

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public SwordMageMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        setModID("theSwordMage");

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheSwordMage.Enums.COLOR_BLUE.toString());

        BaseMod.addColor(TheSwordMage.Enums.COLOR_BLUE, SWORD_MAGE_BLUE, SWORD_MAGE_BLUE, SWORD_MAGE_BLUE,
                SWORD_MAGE_BLUE, SWORD_MAGE_BLUE, SWORD_MAGE_BLUE, SWORD_MAGE_BLUE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

        logger.info("Adding mod settings");
        theSwordMageDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("swordMageMod", "theSwordMageConfig", theSwordMageDefaultSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = SwordMageMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }

    public static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = SwordMageMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = SwordMageMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }

    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        SwordMageMod defaultmod = new SwordMageMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheSwordMage.Enums.THE_SWORD_MAGE.toString());

        BaseMod.addCharacter(new TheSwordMage("the Sword Mage", TheSwordMage.Enums.THE_SWORD_MAGE),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheSwordMage.Enums.THE_SWORD_MAGE);

        receiveEditPotions();
        logger.info("Added " + TheSwordMage.Enums.THE_SWORD_MAGE.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, enablePlaceholder, settingsPanel, (label) -> {
                }, (button) -> {
                    enablePlaceholder = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("swordMageMod", "theSwordMageConfig", theSwordMageDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        logger.info("Done editing potions");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelicToCustomPool(new ManaControllerRelic(), TheSwordMage.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new DaystrikerSwordRelic(), TheSwordMage.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new MadMagesChronicleRelic(), TheSwordMage.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new MagicsBaneRelic(), TheSwordMage.Enums.COLOR_BLUE);

        BaseMod.addRelic(new MoonSigilRelic(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        logger.info("Add variables");
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");

        new AutoAdd("SwordMage").packageFilter(AbstractDefaultCard.class).setDefaultSeen(true)
                .cards();

        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Character-Strings.json");

        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/SwordMageMod-Orb-Strings.json");

        logger.info("Done edittting strings");
    }

    @Override
    public void receiveEditKeywords() {

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/SwordMageMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
