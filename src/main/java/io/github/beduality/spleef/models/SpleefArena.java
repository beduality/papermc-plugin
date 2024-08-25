package io.github.beduality.spleef.models;

import io.github.beduality.core.Bed;
import io.github.beduality.core.models.Handler;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SpleefArena implements Handler{

    private int index;
    private final int SIZE = 21;
    private final int LAYERS = 5;
    private final int MARGIN = 150;
    private final int FLOOR_HEIGHT = 6;
    private final Material MATERIAL = Material.SNOW_BLOCK;
    private final Material WALL_MATERIAL = Material.GLASS;
    private Location centerLocation;

    public SpleefArena(int index){
        this.index = index;
    }

    @Override
    public void onEnable() {
        var spleefLocation = Bed.locationManager.getLocation("spleef");

        centerLocation = new Location(spleefLocation.getWorld(), spleefLocation.getX() - (double) SIZE /2, spleefLocation.getY(), spleefLocation.getZ() - (double) SIZE /2);
        createLayers();
        createWalls();
    }

    @Override
    public void onDisable() {

    }

    private void createLayer(Location location, int floor) {

        double baseX = location.getX();
        double baseY = location.getY() + floor * FLOOR_HEIGHT;
        double baseZ = location.getZ();

        for (int x = 0; x < SIZE; x++) {
            for (int z = 0 ; z < SIZE; z++) {
                Block block = location.getWorld().getBlockAt((int) (baseX + x), (int) baseY, (int) baseZ + z);
                block.setType(MATERIAL);
            }
        }
    }

    private void createLayers(){
        for(int i = 0; i < LAYERS; i++){
            createLayer(centerLocation, i);
        }
    }

    private void createWalls() {

        double baseX = centerLocation.getX();
        double baseY = centerLocation.getY();
        double baseZ = centerLocation.getZ();

        for(int y = 0; y < LAYERS * FLOOR_HEIGHT; y++){
            for(int x = 0; x < SIZE; x++){
                centerLocation.getWorld().getBlockAt(
                        (int) (x + baseX),
                        (int) (y + baseY),
                        (int) (baseZ - 1)
                        ).setType(WALL_MATERIAL);

                centerLocation.getWorld().getBlockAt(
                        (int) (x + baseX),
                        (int) (y + baseY),
                        (int) (baseZ + SIZE)
                ).setType(WALL_MATERIAL);
            }

            for(int z = 0; z < SIZE; z++){
                centerLocation.getWorld().getBlockAt(
                        (int) (baseX - 1),
                        (int) (y + baseY),
                        (int) (baseZ + z)
                ).setType(WALL_MATERIAL);

                centerLocation.getWorld().getBlockAt(
                        (int) (baseX + SIZE),
                        (int) (y + baseY),
                        (int) (baseZ + z)
                ).setType(WALL_MATERIAL);
            }
        }
    }

    public Location getPodLocation(){
        var topLayerLocation = (LAYERS * FLOOR_HEIGHT) + 1;

        var y = centerLocation.getY() + topLayerLocation;
        var x = (Math.random() * SIZE) + centerLocation.getX();
        var z = (Math.random() * SIZE) + centerLocation.getZ();

        return new Location(centerLocation.getWorld(), x,y,z);
    }

}
