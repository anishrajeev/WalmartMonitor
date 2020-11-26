import net.dv8tion.jda.api.entities.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import net.dv8tion.jda.api.*;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.List;

public class Monitor {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault("NzgxMzkxMDcwMDY3NDI1Mjkw.X789JA.qZP-isEU580NOM_gwbW7RLRpOP0").build();
        jda.awaitReady();
        System.out.println("WALMART MONITOR");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Starting to Monitor");
        eb.setAuthor("Walmart Monitor");
        List<Guild> guilds = jda.getGuilds();
        /*for(Guild g : guilds)
        {
            if(g.getName().equalsIgnoreCase("Walmart Monitor")){
                List<TextChannel> channels = g.getTextChannels();
                for(TextChannel ch:channels){
                    if(ch.getName().contains("general"))ch.sendMessage(eb.build()).queue();
                }
            }
        }*/
        String[] monitor = new String[]{
                //add the item sku  here(String format)
                "493824815",
                "363472942",
                "443574645",
                "606518560",
                "451012823",
                "792898625"
        };
        String[] names = new String[]{
         //add the name of the product here
                "PS5 Digital Edition",
                "PS5 Standard",
                "Xbox Series X",
                "Xbox Series S",
                "Pokemon TCG: XY12 Evolutions Walmart Exclusive Box - Featuring Dragonite-EX and Pidgeot-EX",
                "Pokemon TCG: XY12 Evolutions Walmart Exclusive Kanto Collection Box- Featuring Mewtwo-EX and Slowbro-EX"
        };
        String[] imgurl = new String[]{
                //add the img of the item
                "https://i5.walmartimages.com/asr/f62842fd-263f-46d4-8954-9fbe1a25d636.fefa1d11a99643573cf756f2ce835c05.png?odnHeight=100&odnWidth=100&odnBg=FFFFFF",
                "https://i5.walmartimages.com/asr/fd596ed4-bf03-4ecb-a3b0-7a9c0067df83.bb8f535c7677cebdd4010741c6476d3a.png?odnHeight=100&odnWidth=100&odnBg=FFFFFF",
                "https://i5.walmartimages.com/asr/91de7bfe-8289-4b78-9256-1c006ecceb5d.1b2bfd38b4afcf7e650eb004292aeb6b.jpeg?odnHeight=100&odnWidth=100&odnBg=FFFFFF",
                "https://i5.walmartimages.com/asr/3559c784-6860-4524-a206-1a713c0b2e73.ffb076348e19c4dd9ebb9d590bc635f9.jpeg?odnHeight=100&odnWidth=100&odnBg=FFFFFF",
                "https://i5.walmartimages.com/asr/cebbdf22-06ff-4e42-8b4e-80ef7f1d3717.364cb9ab183480d74480e2b131a5a30f.jpeg?odnHeight=100&odnWidth=100&odnBg=FFFFFF",
                "https://i5.walmartimages.com/asr/4f10912d-0620-4089-a85b-a8ff6ff606c2.5578b62f12b7798f3dec0d8057ec7f17.jpeg?odnHeight=100&odnWidth=100&odnBg=FFFFFF"

        };
        for(int i = 0; i < monitor.length; i++){
            m m = new m(monitor[i], names[i], imgurl[i], jda);
            m.start();
        }

    }
}
class m extends Thread{
    String url;
    boolean instock;
    JDA jda;
    String name;
    String img;
    public m(String u, String n, String i, JDA j) throws LoginException {
        url = "https://affil.walmart.com/cart/buynow?items=" + u;
        instock = false;
        name = n;
        img = i;
        jda = j;
    }
    public void run(){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Walmart Monitor");
        eb.setColor(new Color(157, 204, 252));
        String str = "[ATC]("+url+")";
        eb.addField(name, str, true);
        eb.setThumbnail(img);
        List<Guild> guilds = jda.getGuilds();
        while(true){
            try{
                java.net.Authenticator.setDefault (new PasswordAuthenticator());
                Proxy p = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved("104.129.241.141", 17102));
                Document doc = Jsoup.connect(url).timeout(0).get();
                boolean found = false;
                Elements e = doc.getElementsByTag("h3");
                long ms = 0;
                for (Element el : e) {
                    if(e.text().equalsIgnoreCase("The items cannot be added to cart.")){
                        found = true;
                        break;
                    }
                }
                if(!found && !instock){
                    ms = System.currentTimeMillis();
                    eb.setFooter("TimeStamp: " + ms);
                    System.out.println("[" + ms + "]"+"IN STOCK " + url);
                    guilds = jda.getGuilds();

                    for(Guild g : guilds)
                    {
                        if(g.getName().equalsIgnoreCase("Walmart Monitor")){
                            List<TextChannel> channels = g.getTextChannels();
                            for(TextChannel ch:channels){
                                if(ch.getName().contains("general"))ch.sendMessage(eb.build()).queue();
                            }
                        }
                    }
                    instock = true;
                }
                if(found){
                    instock = false;
                    System.out.println("[" + name + "] OOS");
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
