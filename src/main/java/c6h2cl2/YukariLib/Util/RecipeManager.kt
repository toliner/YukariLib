@file:JvmName("RecipeManager")
@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.crafting.ShapelessRecipes
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe

/**
 * @author C6H2Cl2
 */
object RecipeManager {
    @JvmStatic
    @JvmOverloads
    fun addRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: ItemStack, vararg recipeComponents: Any): ShapedRecipes {
        var s = ""
        var i = 0
        var j = 0
        var k = 0

        while (recipeComponents[i] is String) {
            val s1 = recipeComponents[i++] as String
            ++k
            j = maxOf(j, s1.length)
            s += s1
        }

        val map: MutableMap<Char, ItemStack>

        map = Maps.newHashMap<Char, ItemStack>()
        while (i < recipeComponents.size) {
            val itemStack = when {
                recipeComponents[i + 1] is Item -> ItemStack(recipeComponents[i + 1] as Item)
                recipeComponents[i + 1] is Block -> ItemStack(recipeComponents[i + 1] as Block, 1, 32767)
                recipeComponents[i + 1] is ItemStack -> recipeComponents[i + 1] as ItemStack
                else -> throw IllegalArgumentException("Arg No.${i + 1} must instance of Item, Block or ItemStack")
            }
            map.put(recipeComponents[i] as? Char ?: throw IllegalArgumentException("Arg No.$i must instance of Char"), itemStack)
            i += 2
        }

        val itemStack = arrayOfNulls<ItemStack>(j * k)

        for (l in 0..j * k - 1) {
            val c0 = s[l]

            if (map.containsKey(Character.valueOf(c0))) {
                itemStack[l] = (map[Character.valueOf(c0)] as ItemStack).copy()
            } else {
                itemStack[l] = ItemStack.EMPTY
            }
        }
        val list = NonNullList.create<Ingredient>()
        itemStack.forEach {
            list.add(Ingredient.func_193369_a(it))
        }

        val recipe = ShapedRecipes(group, j, k, list, output)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapelessRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: ItemStack, vararg recipeComponents: Any): ShapelessRecipes {
        val itemStacks = Lists.newArrayList<ItemStack>()

        for (component in recipeComponents) {
            when (component) {
                is ItemStack -> itemStacks.add(component.copy())
                is Item -> itemStacks.add(ItemStack(component))
                is Block -> itemStacks.add(ItemStack(component))
                else -> throw IllegalArgumentException("Invalid shapeless recipe: unknown type ${component.javaClass.name}!")
            }
        }
        val list = NonNullList.create<Ingredient>()
        itemStacks.forEach {
            list.add(Ingredient.func_193369_a(it))
        }
        val recipe = ShapelessRecipes(group, output, list)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapedOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: ItemStack, vararg recipeComponents: Any): ShapedOreRecipe{
        val recipe = ShapedOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapedOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: Item, vararg recipeComponents: Any): ShapedOreRecipe{
        val recipe = ShapedOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapedOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: Block, vararg recipeComponents: Any): ShapedOreRecipe{
        val recipe = ShapedOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapelessOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: ItemStack, vararg recipeComponents: Any): ShapelessOreRecipe{
        val recipe = ShapelessOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapelessOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: Item, vararg recipeComponents: Any): ShapelessOreRecipe{
        val recipe = ShapelessOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    @JvmOverloads
    fun addShapelessOreRecipe(recipeID: ResourceLocation, group: String = recipeID.resourceDomain, output: Block, vararg recipeComponents: Any): ShapelessOreRecipe{
        val recipe = ShapelessOreRecipe(ResourceLocation(group), output, recipeComponents)
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    fun addRecipe(recipeID: ResourceLocation, recipe: IRecipe): IRecipe{
        CraftingManager.func_193372_a(recipeID, recipe)
        return recipe
    }

    @JvmStatic
    fun unlockRecipe(player: EntityPlayerMP, recipeID: ResourceLocation){
        unlockRecipe(player, listOf(recipeID))
    }

    @JvmStatic
    fun unlockRecipe(player: EntityPlayerMP, vararg recipeID: ResourceLocation){
        unlockRecipe(player, recipeID.toList())
    }

    @JvmStatic
    fun unlockRecipe(player: EntityPlayerMP, recipeIDs: List<ResourceLocation>){
        player.func_193102_a(recipeIDs.toTypedArray())
    }

    @JvmStatic
    fun unlockRecipe(player: EntityPlayerMP, recipe: IRecipe){
        unlockRecipes(player, listOf(recipe))
    }

    @JvmStatic
    fun unlockRecipe(player: EntityPlayerMP, vararg recipe: IRecipe){
        unlockRecipes(player, recipe.toList())
    }

    @JvmStatic
    fun unlockRecipes(player: EntityPlayerMP, recipes: List<IRecipe>){
        player.func_192021_a(recipes)
    }

    @JvmStatic
    fun lockRecipe(player: EntityPlayerMP, recipe: IRecipe){
        lockRecipe(player, listOf(recipe))
    }

    @JvmStatic
    fun lockRecipe(player: EntityPlayerMP, vararg recipe: IRecipe){
        lockRecipe(player, recipe.toList())
    }

    @JvmStatic
    fun lockRecipe(player: EntityPlayerMP, recipes :List<IRecipe>){
        player.func_192022_b(recipes)
    }
}