package c6h2cl2.YukariLib.Block

import c6h2cl2.YukariLib.Render.CustomSelectedBox
import c6h2cl2.YukariLib.Render.ICustomSelectedBox
import c6h2cl2.YukariLib.Util.BlockPos
import c6h2cl2.YukariLib.Util.Client.Pointer3D
import c6h2cl2.YukariLib.Util.Client.RayTracer
import c6h2cl2.YukariLib.Util.Client.RayTracer.CubeIndexed
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.Side.CLIENT
import cpw.mods.fml.relauncher.Side.SERVER
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World

/**
 * @author kojin15.
 */
abstract class CSBoxBlockContainer(material: Material) : BlockContainer(material), ICustomSelectedBox {

    override final fun onBlockActivated(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        world?:return false
        player?:return false
        val blockPos = BlockPos(x, y, z)
        val hitPos = Pointer3D(hitX, hitY, hitZ)
        if (world.isRemote){
            val mop = Minecraft.getMinecraft().objectMouseOver
            return this.onBlockActivatedClient(world, blockPos, player, hitPos, side, mop.subHit)
        }else{
            return this.onBlockActivatedServer(world, blockPos, player, hitPos, side)
        }
    }

    override final fun collisionRayTrace(world: World?, x: Int, y: Int, z: Int, startVec: Vec3?, endVec: Vec3?): MovingObjectPosition? {
        world?:return null
        val blockPos = BlockPos(x, y, z)
        val rayTracer = RayTracer.instance
        val cubeList = ArrayList<CubeIndexed>()
        return rayTracer.rayTraceCubes(Pointer3D(startVec!!), Pointer3D(endVec!!), this.collisionRayTrace(world, blockPos, cubeList, rayTracer), blockPos, this)
    }

    @SideOnly(Side.CLIENT)
    override final fun getSelectedBoundingBoxFromPool(world: World?, x: Int, y: Int, z: Int): AxisAlignedBB? {
        if (this.isVanilaSelectedBox()) {
            return super.getSelectedBoundingBoxFromPool(world, x, y, z)
        } else return null
    }

    /**
     * @return CustomSelectedBoxを使う場合はfalse
     */
    open fun isVanilaSelectedBox(): Boolean = true

    @SideOnly(CLIENT)
    open fun onBlockActivatedClient(world: World, blockPos: BlockPos, player: EntityPlayer, hitPos: Pointer3D, side: Int, subHit: Int): Boolean = false

    @SideOnly(SERVER)
    open fun onBlockActivatedServer(world: World, blockPos: BlockPos, player: EntityPlayer, hitPos: Pointer3D, side: Int): Boolean = false

    open fun collisionRayTrace(world: World, blockPos: BlockPos, cubeList: ArrayList<CubeIndexed>, rayTracer: RayTracer): ArrayList<CubeIndexed> = cubeList

    override fun shouldRenderBox(subHit: Int, player: EntityPlayer): Boolean = false

    override fun getCustomSelectedBox(subHit: Int, player: EntityPlayer): CustomSelectedBox = CustomSelectedBox(0.5, 0.5, 0.5, 1.0, 1.0, 1.0)


}