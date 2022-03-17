/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [May 23, 2019, 16:18 AM (EST)]
 */
package work.lclpnet.mmoquark.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import work.lclpnet.mmoquark.MMOQuark;

import java.util.List;

import static work.lclpnet.mmoquark.module.NewStoneTypesModule.*;

public enum StonelingVariant implements EntityData {
	STONE("stone", Blocks.COBBLESTONE, Blocks.STONE),
	ANDESITE("andesite", Blocks.ANDESITE, Blocks.POLISHED_ANDESITE),
	DIORITE("diorite", Blocks.DIORITE, Blocks.POLISHED_DIORITE),
	GRANITE("granite", Blocks.GRANITE, Blocks.POLISHED_GRANITE),
	LIMESTONE("limestone", limestoneBlock, polishedBlocks.get(limestoneBlock)),
	CALCITE("calcite", Blocks.CALCITE),
	JASPER("jasper", jasperBlock, polishedBlocks.get(jasperBlock)),
	DEEPSLATE("deepslate", Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE),
	TUFF("tuff", Blocks.TUFF, polishedBlocks.get(Blocks.TUFF)),
	DRIPSTONE("dripstone", Blocks.DRIPSTONE_BLOCK, polishedBlocks.get(Blocks.DRIPSTONE_BLOCK));

	private final Identifier texture;
	private final List<Block> blocks;

	StonelingVariant(String variantPath, Block... blocks) {
		this.texture = MMOQuark.identifier("textures/model/entity/stoneling/%s.png", variantPath);
		this.blocks = Lists.newArrayList(blocks);
	}

	public static StonelingVariant byIndex(byte index) {
		StonelingVariant[] values = values();
		return values[MathHelper.clamp(index, 0, values.length - 1)];
	}

	public byte getIndex() {
		return (byte) ordinal();
	}

	public Identifier getTexture() {
		return texture;
	}

	public List<Block> getBlocks() {
		return blocks;
	}
}
