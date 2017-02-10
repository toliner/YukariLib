package c6h2cl2.YukariLib.Util

import java.util.TreeSet

/**
 * @author C6H2Cl2
 */
class BlockPosSet :TreeSet<BlockPos>(java.util.Comparator(BlockPos::compareTo))