package net.flytre.mechanix.util;

import net.flytre.mechanix.recipe.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static AlloyerRecipeSerializer ALLOYING_SERIALIZER;
    public static RecipeType<AlloyingRecipe> ALLOYING_RECIPE;
    public static LiquifierRecipeSerializer LIQUIFIER_SERIALIZER;
    public static RecipeType<LiquifierRecipe> LIQUIFIER_RECIPE;
    public static FoundryRecipeSerializer FOUNDRY_SERIALIZER;
    public static RecipeType<FoundryRecipe> FOUNDRY_RECIPE;
    public static ItemProcessingRecipeSerializer PRESSURIZER_SERIALIZER;
    public static RecipeType<PressurizerRecipe> PRESSURIZER_RECIPE;
    public static ItemProcessingRecipeSerializer CRUSHER_SERIALIZER;
    public static RecipeType<CrusherRecipe> CRUSHER_RECIPE;

    public static void init() {
        ALLOYING_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("mechanix:alloying"), new RecipeType<AlloyingRecipe>() {
            public String toString() {
                return "mechanix:alloying";
            }
        });
        ALLOYING_SERIALIZER = RecipeSerializer.register("mechanix:alloying", new AlloyerRecipeSerializer(AlloyingRecipe::new));

        LIQUIFIER_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("mechanix:liquifying"), new RecipeType<LiquifierRecipe>() {
            public String toString() {
                return "mechanix:liquifying";
            }
        });
        LIQUIFIER_SERIALIZER = RecipeSerializer.register("mechanix:liquifying", new LiquifierRecipeSerializer(LiquifierRecipe::new));

        FOUNDRY_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("mechanix:casting"), new RecipeType<FoundryRecipe>() {
            public String toString() {
                return "mechanix:casting";
            }
        });
        FOUNDRY_SERIALIZER = RecipeSerializer.register("mechanix:casting", new FoundryRecipeSerializer(FoundryRecipe::new));

        PRESSURIZER_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("mechanix:compressing"), new RecipeType<PressurizerRecipe>() {
            public String toString() {
                return "mechanix:compressing";
            }
        });
        PRESSURIZER_SERIALIZER = RecipeSerializer.register("mechanix:compressing", new ItemProcessingRecipeSerializer(PressurizerRecipe::new));

        CRUSHER_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("mechanix:crushing"), new RecipeType<CrusherRecipe>() {
            public String toString() {
                return "mechanix:crushing";
            }
        });
        CRUSHER_SERIALIZER = RecipeSerializer.register("mechanix:crushing", new ItemProcessingRecipeSerializer(CrusherRecipe::new));

    }
}
