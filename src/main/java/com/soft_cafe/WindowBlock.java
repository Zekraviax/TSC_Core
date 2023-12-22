package com.soft_cafe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.Map;

public class WindowBlock extends Block {
    public static BooleanProperty UP;
    public static BooleanProperty DOWN;
    protected static Map<Direction, BooleanProperty> FACING_PROPERTIES;

    static {
        UP = Properties.UP;
        DOWN = Properties.DOWN;
        FACING_PROPERTIES = ImmutableMap.copyOf((Map) Util.make(Maps.newEnumMap(Direction.class), (directions) -> {
            directions.put(Direction.UP, UP);
            directions.put(Direction.DOWN, DOWN);
        }));
    }

    public WindowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(DOWN, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.up();
        BlockPos blockPos3 = blockPos.down();
        BlockState blockState = blockView.getBlockState(blockPos2);
        BlockState blockState2 = blockView.getBlockState(blockPos3);
        return this.getDefaultState().with(UP, this.connectsTo(blockState, blockState.isSideSolidFullSquare(blockView, blockPos2, Direction.DOWN))).with(DOWN, this.connectsTo(blockState2, blockState2.isSideSolidFullSquare(blockView, blockPos3, Direction.UP)));
    }

    //public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        //return direction.getAxis().isHorizontal() ? state.with(FACING_PROPERTIES.get(direction), this.connectsTo(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, direction.getOpposite()))) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    //}

    //public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        //return VoxelShapes.empty();
    //}

    /*
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            if (!direction.getAxis().isVertical()) {
                return true;
            }

            if ((Boolean)state.get((Property)FACING_PROPERTIES.get(direction)) && (Boolean)stateFrom.get((Property)FACING_PROPERTIES.get(direction.getOpposite()))) {
                return true;
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }
    */

    public final boolean connectsTo(BlockState state, boolean sideSolidFullSquare) {
        return !cannotConnect(state) && sideSolidFullSquare || state.getBlock() instanceof WindowBlock;
    }

    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN);
    }
}