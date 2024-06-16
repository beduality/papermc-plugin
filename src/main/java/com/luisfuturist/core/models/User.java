package com.luisfuturist.core.models;

import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class User {
    
    private UUID id;
    private Player player;

}
