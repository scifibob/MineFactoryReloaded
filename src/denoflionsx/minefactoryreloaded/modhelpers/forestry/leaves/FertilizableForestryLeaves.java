package denoflionsx.minefactoryreloaded.modhelpers.forestry.leaves;

import forestry.api.genetics.IFruitBearer;
import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableForestryLeaves implements IFactoryFertilizable {

    private int id;

    public FertilizableForestryLeaves(int id) {
        this.id = id;
    }

    @Override
    public int getFertilizableBlockId() {
        return this.id;
    }

    @Override
    public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType) {
        if (world.getBlockId(x, y, z) == this.getFertilizableBlockId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType) {
        if (world.getBlockId(x, y, z) == this.getFertilizableBlockId()) {
            TileEntity t = world.getBlockTileEntity(x, y, z);
            if (t instanceof IFruitBearer) {
                IFruitBearer f = (IFruitBearer) t;
                if (f.hasFruit() && f.getRipeness() < 1.0f) {
                    BonemealEvent event = new BonemealEvent(null, world, 1, x, y, z);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (event.getResult().equals(BonemealEvent.Result.ALLOW)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
