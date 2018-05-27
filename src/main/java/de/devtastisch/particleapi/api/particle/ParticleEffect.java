package de.devtastisch.particleapi.api.particle;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.awt.*;

/**
 * This Class holds all Types of supported Particles.
 * Not all of them are tested! If some of them don't work as they are supposed to,
 * please report it on the spigot thread.
 */
@Getter
public enum ParticleEffect {

    EXPLOSION_NORMAL(EnumParticle.EXPLOSION_NORMAL),
    EXPLOSION_LARGE(EnumParticle.EXPLOSION_LARGE),
    EXPLOSION_HUGE(EnumParticle.EXPLOSION_HUGE),
    FIREWORKS_SPARK(EnumParticle.FIREWORKS_SPARK),
    WATER_BUBBLE(EnumParticle.WATER_BUBBLE),
    WATER_SPLASH(EnumParticle.WATER_SPLASH),
    WATER_WAKE(EnumParticle.WATER_WAKE),
    SUSPENDED(EnumParticle.SUSPENDED),
    SUSPENDED_DEPTH(EnumParticle.SUSPENDED_DEPTH),
    CRIT(EnumParticle.CRIT),
    CRIT_MAGIC(EnumParticle.CRIT_MAGIC),
    SMOKE_NORMAL(EnumParticle.SMOKE_NORMAL),
    SMOKE_LARGE(EnumParticle.SMOKE_LARGE),
    SPELL(EnumParticle.SPELL),
    SPELL_INSTANT(EnumParticle.SPELL_INSTANT),
    SPELL_MOB(EnumParticle.SPELL_MOB),
    SPELL_MOB_AMBIENT(EnumParticle.SPELL_MOB_AMBIENT),
    SPELL_WITCH(EnumParticle.SPELL_WITCH),
    DRIP_WATER(EnumParticle.DRIP_WATER),
    DRIP_LAVA(EnumParticle.DRIP_LAVA),
    VILLAGER_ANGRY(EnumParticle.VILLAGER_ANGRY),
    VILLAGER_HAPPY(EnumParticle.VILLAGER_HAPPY),
    TOWN_AURA(EnumParticle.TOWN_AURA),
    NOTE(EnumParticle.NOTE),
    PORTAL(EnumParticle.PORTAL),
    ENCHANTMENT_TABLE(EnumParticle.ENCHANTMENT_TABLE),
    FLAME(EnumParticle.FLAME),
    LAVA(EnumParticle.LAVA),
    FOOTSTEP(EnumParticle.FOOTSTEP),
    CLOUD(EnumParticle.CLOUD),
    REDSTONE(EnumParticle.REDSTONE),
    SNOWBALL(EnumParticle.SNOWBALL),
    SNOW_SHOVEL(EnumParticle.SNOW_SHOVEL),
    SLIME(EnumParticle.SLIME),
    HEART(EnumParticle.HEART),
    BARRIER(EnumParticle.BARRIER),
    ITEM_CRACK(EnumParticle.ITEM_CRACK),
    BLOCK_CRACK(EnumParticle.BLOCK_CRACK),
    BLOCK_DUST(EnumParticle.BLOCK_DUST),
    WATER_DROP(EnumParticle.WATER_DROP),
    ITEM_TAKE(EnumParticle.ITEM_TAKE),
    MOB_APPEARANCE(EnumParticle.MOB_APPEARANCE);

    private final EnumParticle enumParticle;
    @Setter
    private float d1;
    @Setter
    private float d2;
    @Setter
    private float d3;

    ParticleEffect(EnumParticle enumParticle) {
        this(enumParticle, 0, 0, 0);
    }

    ParticleEffect(EnumParticle enumParticle, float d1, float d2, float d3) {
        this.enumParticle = enumParticle;
    }


    public void spawn(Location location) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location));
    }

    public void spawn(Player player, Location location) {
        this.spawn(player, location, new Vector(0, 0, 0), 0, 0, false);
    }

    public void spawn(Location location, Vector vector) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, vector));
    }

    public void spawn(Player player, Location location, Vector vector) {
        this.spawn(player, location, vector, 0, 0, false);
    }

    public void spawn(Location location, Vector vector, int speed) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, vector, speed));
    }

    public void spawn(Player player, Location location, Vector vector, int speed) {
        this.spawn(player, location, vector, speed, 0, false);
    }

    public void spawn(Location location, int randomMovement) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, randomMovement));
    }

    public void spawn(Player player, Location location, int randomMovement) {
        this.spawn(player, location, new Vector(0, 0, 0), 0, randomMovement, false);
    }

    public void spawn(Location location, boolean seeWhenFarAway) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, seeWhenFarAway));
    }

    public void spawn(Player player, Location location, boolean seeWhenFarAway) {
        this.spawn(player, location, new Vector(0, 0, 0), 0, 0, seeWhenFarAway);
    }

    public void spawn(Location location, Vector vector, boolean seeWhenFarAway) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, vector, seeWhenFarAway));
    }

    public void spawn(Player player, Location location, Vector vector, boolean seeWhenFarAway) {
        this.spawn(player, location, vector, 1, 0, seeWhenFarAway);
    }

    public void spawn(Location location, boolean seeWhenFarAway, Color color) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, seeWhenFarAway, color));
    }

    public void spawn(Player player, Location location, boolean seeWhenFarAway, Color color) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(
                this.getEnumParticle(),
                true,
                (float) location.getX(),
                (float) location.getY(),
                (float) location.getZ(),
                getFloatRGB(color.getRed()),
                getFloatRGB(color.getGreen()),
                getFloatRGB(color.getBlue()),
                1,
                0,
                new int[0])
        );
    }

    public void spawn(Location location, Vector vector, int speed, boolean seeWhenFarAway) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, vector, speed, seeWhenFarAway));
    }

    public void spawn(Player player, Location location, Vector vector, int speed, boolean seeWhenFarAway) {
        this.spawn(player, location, vector, speed, 0, seeWhenFarAway);
    }

    public void spawn(Location location, int randomMovement, boolean seeWhenFarAway) {
        Bukkit.getOnlinePlayers().forEach(player -> spawn(player, location, randomMovement, seeWhenFarAway));
    }

    public void spawn(Player player, Location location, int randomMovement, boolean seeWhenFarAway) {
        this.spawn(player, location, new Vector(0, 0, 0), 0, randomMovement, seeWhenFarAway);
    }


    public void spawn(Player player, Location location, Vector vector, int speed, int randomMovement, boolean seeWhenFarAway) {
        if (this.getD1() != 0 || this.getD2() != 0 || this.getD3() != 0) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(
                    this.getEnumParticle(),
                    true,
                    (float) location.getX(),
                    (float) location.getY(),
                    (float) location.getZ(),
                    this.getD1(),
                    this.getD2(),
                    this.getD3(),
                    1,
                    0,
                    new int[0])
            );

        } else {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(
                    this.getEnumParticle(),
                    seeWhenFarAway,
                    ((float) location.getX()),
                    ((float) location.getY()),
                    ((float) location.getZ()),
                    ((float) vector.getX()),
                    ((float) vector.getY()),
                    ((float) vector.getZ()),
                    speed,
                    randomMovement

            ));
        }
    }

    private float getFloatRGB(int rgbValue) {
        return (float) (rgbValue / 255.0D);
    }
}
