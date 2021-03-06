package net.flytre.mechanix.compat.rei;

import net.flytre.mechanix.util.RecipeRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;


public class ReiUtils {

    public static Identifier getId(RecipeType<?> type) {
        if(type == RecipeRegistry.ALLOYING_RECIPE)
            return new Identifier("mechanix:alloying");
        if(type == RecipeRegistry.FOUNDRY_RECIPE)
            return new Identifier("mechanix:casting");
        if(type == RecipeRegistry.LIQUIFIER_RECIPE)
            return new Identifier("mechanix:liquifying");
        if(type == RecipeRegistry.PRESSURIZER_RECIPE)
            return new Identifier("mechanix:compressing");
        return new Identifier("mechanix:null");
    }
}
