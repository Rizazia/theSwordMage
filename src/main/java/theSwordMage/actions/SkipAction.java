package theSwordMage.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import theSwordMage.orbs.SwordBase;

public class SkipAction extends AbstractGameAction{
    private final int ORB_SLOT;
    private final boolean TO_SHATTER;
    
    public SkipAction(boolean toShatter){
        ORB_SLOT = 1;
        TO_SHATTER = toShatter;
    }
    
    public SkipAction(int orbSlot, boolean toShatter){
        ORB_SLOT = orbSlot;
        TO_SHATTER = toShatter;
    }
    
    @Override
    public void update() {
        if (AbstractDungeon.player.orbs.get(ORB_SLOT) instanceof EmptyOrbSlot == false){
            if (TO_SHATTER && AbstractDungeon.player.orbs.get(ORB_SLOT) instanceof SwordBase) ((SwordBase)AbstractDungeon.player.orbs.get(ORB_SLOT)).isShatter = TO_SHATTER;
            AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(AbstractDungeon.player.orbs.get(ORB_SLOT)));
        }
    }
}
