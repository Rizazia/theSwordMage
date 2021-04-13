package theSwordMage.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import theSwordMage.characters.TheSwordMage.Enums;
import theSwordMage.orbs.*;

public class SwordCheckActions {    
    public static final boolean playerHasSword(String find){
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++){
            if ((AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot == false) && AbstractDungeon.player.orbs.get(i).ID.equals(find))
                return true;
        }
        return false;
    }
    
    public static final boolean playerHasAnySword(){
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++){
            if (AbstractDungeon.player.orbs.get(i) instanceof SwordBase) return true;
        }
        return false;
    }
    
    public static final AbstractOrb getFirstSwordOf(String idToFind){
        if (playerHasSword(idToFind)){
            for (AbstractOrb o : AbstractDungeon.player.orbs){
                if(o.ID.equals(idToFind)) return o;
            }
        }
        
        return null;
    }
    
    public static final boolean isCardAllowedToCreate(AbstractCard c){
        return c.type.equals(CardType.ATTACK) && !c.hasTag(Enums.NOT_SWORDABLE);
    }
    
    public static final AbstractOrb getFirstSword(){
        if (playerHasAnySword()){
            for (AbstractOrb o : AbstractDungeon.player.orbs){
                if(o instanceof SwordBase) return o;
            }
        }
        
        return null;
    }
}