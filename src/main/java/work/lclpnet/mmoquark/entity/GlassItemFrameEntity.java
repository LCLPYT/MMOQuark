package work.lclpnet.mmoquark.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import work.lclpnet.mmocontent.entity.AdditionalSpawnData;
import work.lclpnet.mmocontent.networking.MMONetworking;
import work.lclpnet.mmoquark.module.ItemFramesModule;

public class GlassItemFrameEntity extends ItemFrameEntity implements AdditionalSpawnData {

    public static final TrackedData<Boolean> IS_SHINY = DataTracker.registerData(GlassItemFrameEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final String TAG_SHINY = "isShiny";

    private boolean didHackery = false;

    public GlassItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

    public GlassItemFrameEntity(World world, BlockPos pos, Direction direction) {
        super(ItemFramesModule.glassFrameEntity, world);
        attachmentPos = pos;
        this.setFacing(direction);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        dataTracker.startTracking(IS_SHINY, false);
    }

    @Override
    public boolean canStayAttached() {
        return super.canStayAttached() || isOnSign();
    }

    public BlockPos getBehindPos() {
        return attachmentPos.offset(facing.getOpposite());
    }

    public boolean isOnSign() {
        BlockState blockstate = world.getBlockState(getBehindPos());
        return blockstate.isIn(BlockTags.STANDING_SIGNS);
    }

    @Override
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        if (stack.getItem() == Items.ITEM_FRAME && !didHackery) {
            stack = new ItemStack(getItem());
            didHackery = true;
        }

        return super.dropStack(stack, yOffset);
    }

    private Item getItem() {
        return dataTracker.get(IS_SHINY) ? ItemFramesModule.glowingGlassFrame : ItemFramesModule.glassFrame;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);

        tag.putBoolean(TAG_SHINY, dataTracker.get(IS_SHINY));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);

        dataTracker.set(IS_SHINY, tag.getBoolean(TAG_SHINY));
    }

    @Override
    public void writeSpawnData(PacketByteBuf buffer) {
        buffer.writeBlockPos(this.attachmentPos);
        buffer.writeVarInt(this.facing.getId());
    }

    @Override
    public void readSpawnData(PacketByteBuf buffer) {
        this.attachmentPos = buffer.readBlockPos();
        this.setFacing(Direction.byId(buffer.readVarInt()));
    }

    @Override
    public Packet<?> createSpawnPacket() {
       return MMONetworking.createMMOSpawnPacket(this);
    }
}
