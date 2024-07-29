package com.luisfuturist.rps.features;

import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.features.InvitableFeature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RpsFeature extends InvitableFeature {

    @Getter @Setter
    private ItemStack itemStack;
    
    @Override
    public void onCreate() {
        setInviteName("Rock-paper-scissors");
        super.onCreate();
    }
}
