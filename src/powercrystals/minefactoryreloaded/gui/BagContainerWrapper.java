package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BagContainerWrapper implements IInventory
{
	private ItemStack _stack;
	
	public BagContainerWrapper(ItemStack stack)
	{
		_stack = stack;
	}
	
	public ItemStack getStack()
	{
		_stack.getTagCompound().setLong("code", (_stack.hashCode() << 32) | _stack.getTagCompound().hashCode());
		return _stack;
	}

	@Override
	public int getSizeInventory()
	{
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(_stack.getTagCompound().getCompoundTag("slot" + i) == null || _stack.getTagCompound().getCompoundTag("slot" + i).hasNoTags())
		{
			return null;
		}
		else
		{
			ItemStack s = new ItemStack(0, 0, 0);
			s.readFromNBT(_stack.getTagCompound().getCompoundTag("slot" + i));
			return s;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack s = new ItemStack(0, 0, 0);
		s.readFromNBT(_stack.getTagCompound().getCompoundTag("slot" + i));
		s.stackSize -= j;
		if(s.stackSize <= 0)
		{
			_stack.getTagCompound().setCompoundTag("slot" + i, new NBTTagCompound());
			s = null;
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			s.writeToNBT(t);
			_stack.getTagCompound().setCompoundTag("slot" + i, t);
		}
		return s;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(itemstack == null)
		{
			_stack.getTagCompound().setCompoundTag("slot" + i, new NBTTagCompound());
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			itemstack.writeToNBT(t);
			_stack.getTagCompound().setCompoundTag("slot" + i, t);
		}
	}

	@Override
	public String getInvName()
	{
		return _stack.getDisplayName();
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
}
