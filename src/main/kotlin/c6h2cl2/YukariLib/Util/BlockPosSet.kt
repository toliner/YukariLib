@file:Suppress("UNUSED")
package c6h2cl2.YukariLib.Util

import java.util.TreeSet

/**
 * @author C6H2Cl2
 */
class BlockPosSet(private val target: BlockPos = BlockPos.Empty) : TreeSet<BlockPos>(
        java.util.Comparator {
            o1: @ParameterName(name = "o1") BlockPos, o2: @ParameterName(name = "o2") BlockPos ->
            (o1.getDistance(target) - o2.getDistance(target)).toInt()
        })