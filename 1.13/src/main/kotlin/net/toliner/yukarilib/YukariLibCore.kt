/*
 * Copyright 2019 toliner
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.toliner.yukarilib

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

val logger = LogManager.getLogger()

// The value here should match an entry in the META-INF/mods.toml file
@Mod("yukarilib")
class YukariLibCore {
    init {
        FMLJavaModLoadingContext.get().modEventBus.apply {
            addListener<FMLCommonSetupEvent> { setup(it) }
            addListener<InterModProcessEvent> { processIMC(it) }
            addListener<FMLClientSetupEvent> { doClientStuff(it) }
        }
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        // some preinit code
        logger.info("Hello, Kotlin Minecraft World!!")
    }

    private fun doClientStuff(event: FMLClientSetupEvent) {
        event.minecraftSupplier
    }

    private fun processIMC(event: InterModProcessEvent) {

    }
}
