package com.prollumsloof.glowtorches;

import com.prollumsloof.glowtorches.item.GlowTorchBlock;
import com.prollumsloof.glowtorches.item.GlowWallTorchBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(GlowTorches.MODID)
public class GlowTorches {
    public static final String MODID = "glowtorches";

    public static final int TORCH_LIGHT_LEVEL = 12;

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredBlock<GlowTorchBlock> GLOW_TORCH_BLOCK = BLOCKS.register("glow_torch",
            () -> new GlowTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel(state -> TORCH_LIGHT_LEVEL)));
    public static final DeferredBlock<GlowWallTorchBlock> GLOW_WALL_TORCH_BLOCK = BLOCKS.register("glow_wall_torch",
            () -> new GlowWallTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH).lightLevel(state -> TORCH_LIGHT_LEVEL).lootFrom(GLOW_TORCH_BLOCK)));

    public static final DeferredItem<StandingAndWallBlockItem> GLOW_TORCH = ITEMS.register("glow_torch",
            () -> new StandingAndWallBlockItem(GLOW_TORCH_BLOCK.get(), GLOW_WALL_TORCH_BLOCK.get(), new Item.Properties(), Direction.DOWN)
    );

    public GlowTorches(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertAfter(new ItemStack(Items.REDSTONE_TORCH), new ItemStack(GLOW_TORCH.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
