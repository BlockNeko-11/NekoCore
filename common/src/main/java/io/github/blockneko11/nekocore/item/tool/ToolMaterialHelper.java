package io.github.blockneko11.nekocore.item.tool;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public final class ToolMaterialHelper {
    public static ToolMaterial create(int durability, float miningSpeedMultiplier, float attackDamage, int miningLevel, int enchantability, Supplier<Ingredient> repairIngredient) {
        return new ToolMaterialImpl(miningLevel, durability, miningSpeedMultiplier, attackDamage, enchantability, repairIngredient);
    }

    private static final class ToolMaterialImpl implements ToolMaterial {
        private final int miningLevel;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Supplier<Ingredient> repairIngredient;

        private ToolMaterialImpl(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
            this.miningLevel = miningLevel;
            this.itemDurability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairIngredient = repairIngredient;
        }

        public int getDurability() {
            return this.itemDurability;
        }

        public float getMiningSpeedMultiplier() {
            return this.miningSpeed;
        }

        public float getAttackDamage() {
            return this.attackDamage;
        }

        public int getMiningLevel() {
            return this.miningLevel;
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Ingredient getRepairIngredient() {
            return this.repairIngredient.get();
        }
    }
}
