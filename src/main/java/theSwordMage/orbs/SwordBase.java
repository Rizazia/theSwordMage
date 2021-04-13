package theSwordMage.orbs;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import static theSwordMage.SwordMageMod.makeOrbPath;
import theSwordMage.characters.TheSwordMage;
import theSwordMage.powers.OverloadPower;

public abstract class SwordBase extends CustomOrb{
    public boolean isShatter;
    public final AbstractCard formBy;
    public SwordBase(AbstractCard formBy, String ORB_ID, String name, int pAmount, int eAmount, String pString, String eString, String path) {
        super(ORB_ID, name, pAmount, eAmount, pString, eString, makeOrbPath(path));
        
        this.formBy = formBy;
        this.isShatter = false;
    }
    
    protected void returnFormByCard(){
        if(!isShatter && //not overloaded
            !(AbstractDungeon.player.hasPower(OverloadPower.POWER_ID) && AbstractDungeon.player.getPower(OverloadPower.POWER_ID).amount >= AbstractDungeon.player.orbs.size())){
            
            if (AbstractDungeon.player.exhaustPile.contains(formBy)){
                AbstractDungeon.player.exhaustPile.removeCard(formBy);
                AbstractCard tmp = formBy.makeCopy();
                if(!formBy.tags.contains(TheSwordMage.Enums.CORE))tmp.setCostForTurn(0);
                AbstractDungeon.player.hand.addToBottom(tmp);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new OverloadPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
            }
        }
    }
    
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, 100));
        sb.draw(img, cX - 50.0f , cY - 40.0f + bobEffect.y, 70.0f , 15.0f , 70.0f, 15.0f, 1, 1, 0, 0, 0, 70, 15, false, false);
        renderText(sb);
        hb.render(sb);
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        if(baseEvokeAmount > 0)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
                Integer.toString(evokeAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + 5.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
        
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
            Integer.toString(passiveAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET - 20.0F * Settings.scale, c, fontScale);
    }

    public void setIsShatter(boolean isShatter){
        this.isShatter = isShatter;
    }
}
