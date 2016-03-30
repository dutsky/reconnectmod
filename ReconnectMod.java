package dut.minecraft.reconnectmod;
// Auto-reconnect mod, based on Autojoin mod by Tealnerd(https://github.com/TealNerd/AutoJoin)

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

@Mod(modid = ReconnectMod.MODID, name=ReconnectMod.NAME, version = ReconnectMod.VERSION)
public class ReconnectMod {
    public static final String MODID = "reconnectmod";
    public static final String NAME = "Reconnect Mod";
    public static final String VERSION = "0.0.2";
    public static int WAITTICKS = 1200;
    public static int MAXCONN = 10;
    
    int reconnections = 0;
    int timer = 0;
    ServerData server;
    Minecraft mc = Minecraft.getMinecraft();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
		FMLCommonHandler.instance().bus().register(this);
    }
        
    @SubscribeEvent
    public void onTick(ClientTickEvent event){
    	// func_147104_D() is GetCurrentServerData()
		if(mc.theWorld != null){
			server = mc.func_147104_D();
			reconnections = 0;
		}

    	if(mc.currentScreen instanceof GuiDisconnected){
    		if(timer < WAITTICKS){
    			timer++;
    		}
    	}
    	
    	if((timer >= WAITTICKS) && (reconnections < MAXCONN)){
    		mc.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), mc, server));
    		timer = 0;
    		reconnections++;    		
    	}
    	
    	// func_147104_D() is GetCurrentServerData()
		//if(mc.currentScreen instanceof GuiConnecting && mc.func_147104_D() != null) {
		//	server = mc.func_147104_D();
		//}
    }
}
