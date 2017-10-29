package c6h2cl2.yukarilib.util

import com.google.common.collect.AbstractIterator
import net.minecraft.util.math.BlockPos

/**
 * @author C6H2Cl2
*/
class BlockPosRange(
        start: BlockPos, endInclusive: BlockPos,
        val stepX: Int = 1, val stepY: Int = 1, val stepZ: Int = 1
) : ClosedRange<BlockPos>, Iterable<BlockPos> {

    init {
        if (stepX == 0 && stepY == 0 && stepZ == 0) {
            throw IllegalArgumentException("Step must be non-zero")
        }
    }

    val first = start.toBlockPosKt()
    val last = endInclusive.toBlockPosKt()
    override val start: BlockPos
        get() = first
    override val endInclusive: BlockPos
        get() = last

    override fun iterator() = BlockPosIterator(first, last, stepX, stepY, stepZ)

    override fun equals(other: Any?): Boolean {
        return other is BlockPosRange &&
                other.isEmpty() && isEmpty() &&
                other.last == last && other.first == first
    }

    override fun hashCode(): Int {
        return if (isEmpty()) -1 else (31 * (31 * first.toLong() + last.toLong()) + (stepX xor stepY xor stepZ)).toInt()
    }

    override fun toString(): String = "$first..$last"

    class BlockPosIterator(private val first: BlockPos, private val last: BlockPos, val stepX: Int, val stepY: Int, val stepZ: Int) : AbstractIterator<BlockPos>() {
        private var lastReturned: BlockPos? = null
        override fun computeNext(): BlockPos {
            when {
                this.lastReturned == null -> {
                    this.lastReturned = first
                    return this.lastReturned as BlockPos
                }
                this.lastReturned == last -> return this.endOfData() as BlockPos
                else -> {
                    var i = this.lastReturned!!.x
                    var j = this.lastReturned!!.y
                    var k = this.lastReturned!!.z

                    when {
                        i < last.x -> i += stepX
                        j < last.y -> {
                            i = first.x
                            j += stepY
                        }
                        k < last.z -> {
                            i = first.x
                            j = first.y
                            k += stepZ
                        }
                    }
                    this.lastReturned = BlockPos(i, j, k)
                    return this.lastReturned as BlockPos
                }
            }
        }
    }
}