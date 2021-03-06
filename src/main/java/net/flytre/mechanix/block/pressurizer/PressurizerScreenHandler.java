package net.flytre.mechanix.block.pressurizer;

import net.flytre.mechanix.api.energy.EnergyScreenHandler;
import net.flytre.mechanix.util.MachineRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PressurizerScreenHandler extends EnergyScreenHandler {
    private final Inventory inventory;
    private final World world;

    public PressurizerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new PressurizerBlockEntity(), new ArrayPropertyDelegate(24));
        this.pos = buf.readBlockPos();
    }

    public PressurizerScreenHandler(int syncId, PlayerInventory playerInventory, PressurizerBlockEntity entity, PropertyDelegate propertyDelegate) {
        super(MachineRegistry.PRESSURIZER.getHandlerType(), syncId, playerInventory, entity, propertyDelegate);

        this.pos = BlockPos.ORIGIN;
        this.addSlot(new Slot(entity, 0, 66, 35));
        this.addSlot(new Slot(entity, 1, 135, 35));
        int o;
        int n;
        for (o = 0; o < 3; ++o) {
            for (n = 0; n < 9; ++n) {
                this.addSlot(new Slot(playerInventory, n + o * 9 + 9, 8 + n * 18, 84 + o * 18));
            }
        }

        for (o = 0; o < 9; ++o) {
            this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 142));
        }

        this.inventory = entity;
        this.world = playerInventory.player.world;
    }


    public double operationProgress() {
        return getPropertyDelegate().get(9) == 0 ? 0 : getPropertyDelegate().get(8) / (double)getPropertyDelegate().get(9);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            stack = slot.getStack();
            if(index <= 1) {
                if(!this.insertItem(stack,4,38,false))
                    return ItemStack.EMPTY;
            } else {
                if(!this.insertItem(stack,0,1,false))
                    return ItemStack.EMPTY;
            }
        }
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

}

