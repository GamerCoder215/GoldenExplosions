package me.gamercoder215.goldenexplosions

import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.io.InputStream
import java.util.*

class GoldenExplosions : JavaPlugin() {

    fun print(e: Throwable) {
        logger.severe(e.javaClass.simpleName)
        logger.severe("--------------")
        logger.severe(e.message)
        for (stack: StackTraceElement in e.stackTrace) logger.severe(stack.toString())
    }

    companion object {
        fun getConfig(): FileConfiguration {
            return getPlugin(GoldenExplosions::class.java).config
        }

        fun getLanguage(): String? {
            return getConfig().getString("language", "en")
        }

        fun get(key: String): String {
            val p = Properties()
            val lang: String = if (getLanguage().equals("en", ignoreCase = true)) "" else "_" + getLanguage()

            return try {
                val str: InputStream = GoldenExplosions::class.java.getResourceAsStream("/lang/goldenexplosions$lang.properties") as InputStream

                p.load(str)
                str.close()
                ChatColor.translateAlternateColorCodes('&', p.getProperty(key, "Unknown Value"))
            } catch (e: IOException) {
                print(e)
                "Unknown Value"
            }
        }
    }

    override fun onEnable() {
        saveDefaultConfig()

        logger.info("Finished!")
    }

    override fun onDisable() {}
    
}