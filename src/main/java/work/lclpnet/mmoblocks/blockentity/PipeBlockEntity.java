package work.lclpnet.mmoblocks.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import work.lclpnet.mmoblocks.module.PipesModule;

public class PipeBlockEntity extends BlockEntity {

    public PipeBlockEntity() {
        super(PipesModule.blockEntityType);
    }

    public static ConnectionType getConnectionTo(BlockView world, BlockPos pos, Direction face) {
        return getConnectionTo(world, pos, face, false);
    }

    private static ConnectionType getConnectionTo(BlockView world, BlockPos pos, Direction face, boolean recursed) {
        BlockPos truePos = pos.offset(face);
        BlockEntity tile = world.getBlockEntity(truePos);

        if (tile != null) {
            if (tile instanceof PipeBlockEntity) return ConnectionType.PIPE;
            else if (tile instanceof Inventory) {
                return tile instanceof ChestBlockEntity ? ConnectionType.TERMINAL_OFFSET : ConnectionType.TERMINAL;
            }
        }

        checkSides: if(!recursed) {
            ConnectionType other = getConnectionTo(world, pos, face.getOpposite(), true);
            if(other.isSolid) {
                for(Direction d : Direction.values())
                    if(d.getAxis() != face.getAxis()) {
                        other = getConnectionTo(world, pos, d, true);
                        if(other.isSolid)
                            break checkSides;
                    }

                return ConnectionType.OPENING;
            }
        }

        return ConnectionType.NONE;
    }

    public enum ConnectionType {

        NONE(false, false, false, 0),
        PIPE(true, true, false, 0),
        OPENING(false, true, true, -0.125),
        TERMINAL(true, true, true, 0.125),
        TERMINAL_OFFSET(true, true, true, 0.1875);

        ConnectionType(boolean isSolid, boolean allowsItems, boolean isFlared, double flareShift) {
            this.isSolid = isSolid;
            this.allowsItems = allowsItems;
            this.isFlared = isFlared;
            this.flareShift = flareShift;
        }

        public final boolean isSolid, allowsItems, isFlared;
        public final double flareShift;

    }
}
