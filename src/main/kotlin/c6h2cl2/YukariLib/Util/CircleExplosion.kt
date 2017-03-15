package c6h2cl2.YukariLib.Util

import c6h2cl2.YukariLib.Render.Cube
import c6h2cl2.YukariLib.Util.Client.Pointer3D
import net.minecraft.block.material.Material
import net.minecraft.enchantment.EnchantmentProtection
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.DamageSource
import net.minecraft.util.MathHelper
import net.minecraft.util.Vec3
import net.minecraft.world.Explosion
import net.minecraft.world.World
import java.util.*
import kotlin.collections.HashMap

/**
 * @author kojin15.
 */
class CircleExplosion : Explosion {
    constructor(world: World, entity: Entity?, explodePoint: Pointer3D, size: Float) :
            super(world, entity, explodePoint.getX(), explodePoint.getY(), explodePoint.getZ(), size) {
        this.worldObj = world
    }

    val worldObj: World
    private val playerMap = HashMap<EntityPlayer, Vec3>()
    var blockList = listOf<BlockPos>().toMutableList()
    private val explosionRNG = Random()

    override fun doExplosionA() {
        val size = this.explosionSize
        val sizeInt = MathHelper.floor_float(this.explosionSize)
        val point = Pointer3D(this.explosionX, this.explosionY, this.explosionZ)

        val targetPos = BlockPos(MathHelper.floor_double(this.explosionX), MathHelper.floor_double(this.explosionY), MathHelper.floor_double(this.explosionZ))
        val blockList = targetPos.getCircle(this.explosionSize.toDouble()).toMutableList()

        blockList.toList().forEach {
            val block = it.getBlockFromPos(this.worldObj)
            val dist = this.explosionSize - (it distance targetPos)
            val resist = if (this.exploder != null) this.exploder.func_145772_a(this, this.worldObj, it.getX(), it.getY(), it.getZ(), block) else block.getExplosionResistance(this.exploder, this.worldObj, it.getX(), it.getY(), it.getZ(), this.explosionX, this.explosionY, this.explosionZ)
            if (dist * 6.0 < resist) blockList.remove(it)
        }


        this.blockList = blockList
        this.explosionSize *= 2.0f

        val entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, Cube(Pointer3D(targetPos.down(sizeInt).north(sizeInt).west(sizeInt)), Pointer3D(targetPos.up(sizeInt).south(sizeInt).east(sizeInt))))
                .toMutableList().filter { it is Entity }.map { it as Entity }
        entityList.filter {
            it.getDistance(this.explosionX, this.explosionY, this.explosionZ) <= this.explosionSize.toDouble()
        }

        entityList.forEach {
            val d4 = it.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize.toDouble()

            if (d4 <= 1.0) {
                var d5 = it.posX - this.explosionX
                var d6 = it.posY + it.eyeHeight - this.explosionY
                var d7 = it.posZ - this.explosionZ
                val d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7).toDouble()

                if (d9 != 0.0) {
                    d5 /= d9
                    d6 /= d9
                    d7 /= d9
                    val d10 = this.worldObj.getBlockDensity(point, it.boundingBox)
                    val d11 = (1.0 - d4) * d10
                    it.attackEntityFrom(DamageSource.setExplosionSource(this), ((d11 * d11 + d11) / 2.0 * 8.0 * explosionSize.toDouble() + 1.0).toInt().toFloat())
                    val d8 = EnchantmentProtection.func_92092_a(it, d11)
                    it.motionX += d5 * d8
                    it.motionY += d6 * d8
                    it.motionZ += d7 * d8

                    if (it is EntityPlayer) {
                        this.playerMap.put(it, Pointer3D(d5 * d11, d6 * d11, d7 * d11))
                    }
                }
            }
        }

        this.explosionSize = size

    }

    override fun doExplosionB(spawnEffect: Boolean) {
        val targetPos = BlockPos(this.explosionX.toInt(), this.explosionY.toInt(), this.explosionZ.toInt())

        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f)

        if (this.explosionSize >= 2.0f && isSmoking) {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0)
        } else {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0)
        }

        if (this.isSmoking) {
            this.blockList.forEach {
                val x = it.getX()
                val y = it.getY()
                val z = it.getZ()
                val block = it.getBlockFromPos(worldObj)

                if (spawnEffect) {
                    val d0 = (x.toFloat() + this.worldObj.rand.nextFloat()).toDouble()
                    val d1 = (y.toFloat() + this.worldObj.rand.nextFloat()).toDouble()
                    val d2 = (z.toFloat() + this.worldObj.rand.nextFloat()).toDouble()
                    var d3 = d0 - this.explosionX
                    var d4 = d1 - this.explosionY
                    var d5 = d2 - this.explosionZ
                    val d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5).toDouble()

                    d3 /= d6
                    d4 /= d6
                    d5 /= d6

                    var d7 = 0.5 / (d6 / this.explosionSize.toDouble() + 0.1)
                    d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f).toDouble()

                    d3 *= d7
                    d4 *= d7
                    d5 *= d7

                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0) / 2.0, (d1 + this.explosionY * 1.0) / 2.0, (d2 + this.explosionZ * 1.0) / 2.0, d3, d4, d5)
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5)

                }


                if (block.material != Material.air) {
                    if (block.canDropFromExplosion(this)) {
                        val dist = this.explosionSize - (it distance targetPos)
                        block.dropBlockAsItemWithChance(this.worldObj, x, y, z, it.getMetaFromPos(this.worldObj), 1.0f / (this.explosionSize + (dist * 2.0).toFloat()), 0)
                    }
                    block.onBlockExploded(this.worldObj, x, y, z, this)
                }
            }
        }

        if (this.isFlaming) {
            this.blockList.forEach {
                val x = it.getX()
                val y = it.getY()
                val z = it.getZ()
                val block = it.getBlockFromPos(this.worldObj)
                val block1 = it.down.getBlockFromPos(this.worldObj)

                if (block.material == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0) {
                    it.setBlockFromPos(this.worldObj, Blocks.fire, 0, 3)
                }
            }
        }
    }

    override fun func_77277_b(): MutableMap<Any?, Any?> = playerMap.toMutableMap()

    companion object {
        fun World.createCircleExplosion(entity: Entity?, position: Pointer3D, size: Float, isSmoking: Boolean, isFlaming: Boolean = false, spawnEffect: Boolean = true): CircleExplosion {
            val explosion = CircleExplosion(this, entity, position, size)
            explosion.isSmoking = isSmoking
            explosion.isFlaming = isFlaming
            if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this, explosion)) return explosion
            explosion.doExplosionA()
            explosion.doExplosionB(spawnEffect)
            return explosion
        }
    }
}