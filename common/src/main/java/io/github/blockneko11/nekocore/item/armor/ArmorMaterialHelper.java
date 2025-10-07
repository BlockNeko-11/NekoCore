package io.github.blockneko11.nekocore.item.armor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Supplier;

public final class ArmorMaterialHelper {
    public static ArmorMaterial create(String name, int durability, ArmorProtection protection, int enchantability, @NotNull SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        return new ArmorMaterialImpl(name, durability, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, protection.getBoots());
            map.put(ArmorItem.Type.LEGGINGS, protection.getLeggings());
            map.put(ArmorItem.Type.CHESTPLATE, protection.getChestplate());
            map.put(ArmorItem.Type.HELMET, protection.getHelmet());
        }), enchantability, equipSound, toughness, knockbackResistance, repairIngredientSupplier);
    }

    @Getter
    @RequiredArgsConstructor
    public static final class ArmorProtection {
        private final int boots;
        private final int leggings;
        private final int chestplate;
        private final int helmet;
    }

    private static final class ArmorMaterialImpl implements StringIdentifiable, ArmorMaterial {
        private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 13);
            map.put(ArmorItem.Type.LEGGINGS, 15);
            map.put(ArmorItem.Type.CHESTPLATE, 16);
            map.put(ArmorItem.Type.HELMET, 11);
        });

        private final String name;
        private final int durabilityMultiplier;
        private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
        private final int enchantability;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredientSupplier;

        private ArmorMaterialImpl(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.protectionAmounts = protectionAmounts;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredientSupplier = repairIngredientSupplier;
        }

        public int getDurability(ArmorItem.Type type) {
            return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
        }

        public int getProtection(ArmorItem.Type type) {
            return this.protectionAmounts.get(type);
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        public Ingredient getRepairIngredient() {
            return this.repairIngredientSupplier.get();
        }

        public String getName() {
            return this.name;
        }

        public float getToughness() {
            return this.toughness;
        }

        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public String asString() {
            return this.name;
        }
    }
}
