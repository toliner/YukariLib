package c6h2cl2.YukariLib

/**
 * @author C6H2Cl2
 */
class PlayerNotOfficialPurchasedException : RuntimeException {
    companion object {
        const val template_message = "You must buy Minecraft from Official Site!!!"
        const val template_jp = "Minecraftの正規版を購入しようね！体験版/割れ版ではこのmodは遊べないよ！！！"
    }

    constructor() : super(template_message)
    constructor(message: String) : super(message)
    constructor(exception: Throwable) : super(template_message, exception)
    constructor(message: String, exception: Throwable) : super(message, exception)
}