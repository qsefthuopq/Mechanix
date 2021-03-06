package net.flytre.mechanix.block.item_pipe;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.flytre.mechanix.api.gui.ToggleButton;
import net.flytre.mechanix.util.Packets;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ItemPipeScreen extends HandledScreen<ItemPipeScreenHandler> {

    protected final ItemPipeScreenHandler handler;
    private final Identifier background;
    private static final Identifier WHITELIST_BUTTON = new Identifier("mechanix:textures/gui/button/check_ex.png");
    private static final Identifier MODE_BUTTON = new Identifier("mechanix:textures/gui/button/pipe_mode.png");

    private boolean synced;


    public ItemPipeScreen(ItemPipeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
        this.background = new Identifier("mechanix:textures/gui/container/item_pipe.png");
        synced = false;
    }

    private void onSynced() {

        int startFrame = handler.getFilterType();
        ToggleButton whitelistButton = new ToggleButton(this.x + 177, this.height / 2 - 80, 16, 16, startFrame, WHITELIST_BUTTON, (buttonWidget) -> {

            if(buttonWidget instanceof ToggleButton) {
                ((ToggleButton) buttonWidget).toggleState();
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(handler.getPos());
                passedData.writeInt(((ToggleButton) buttonWidget).getState());
                ClientSidePacketRegistry.INSTANCE.sendToServer(Packets.FILTER_TYPE, passedData);
            }

        } ,"");
        whitelistButton.setTooltips(new TranslatableText("block.mechanix.item_pipe.whitelist"), new TranslatableText("block.mechanix.item_pipe.blacklist"));
        whitelistButton.setTooltipRenderer(this::renderTooltip);

        this.addButton(whitelistButton);

        startFrame = handler.getRoundRobinMode();
        ToggleButton modeButton = new ToggleButton(this.x + 177, this.height / 2 - 60, 16, 16, startFrame, MODE_BUTTON, (buttonWidget) -> {

            if(buttonWidget instanceof ToggleButton) {
                ((ToggleButton) buttonWidget).toggleState();
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(handler.getPos());
                passedData.writeInt(((ToggleButton) buttonWidget).getState());
                ClientSidePacketRegistry.INSTANCE.sendToServer(Packets.PIPE_MODE, passedData);
            }

        } ,"");
        modeButton.setTooltips(new TranslatableText("block.mechanix.item_pipe.closest"), new TranslatableText("block.mechanix.item_pipe.round_robin"));
        modeButton.setTooltipRenderer(this::renderTooltip);
        this.addButton(modeButton);
    }

    @Override
    public void tick() {
        super.tick();

        if(!synced && handler.getSynced()) {
            onSynced();
            synced = true;
        }
    }

    @Override
    protected void init() {
        synced = false;
        super.init();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.client != null;

        this.client.getTextureManager().bindTexture(this.background);
        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
