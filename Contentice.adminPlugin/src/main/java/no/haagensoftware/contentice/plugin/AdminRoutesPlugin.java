package no.haagensoftware.contentice.plugin;

import io.netty.channel.ChannelHandler;
import no.haagensoftware.contentice.plugin.handler.AdminCategoriesHandler;
import no.haagensoftware.contentice.plugin.handler.AdminCategoryHandler;
import no.haagensoftware.contentice.plugin.handler.AdminSubcategoriesHandler;
import no.haagensoftware.contentice.plugin.handler.AdminSubcategoryHandler;
import no.haagensoftware.contentice.spi.RouterPlugin;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jhsmbp
 * Date: 11/19/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminRoutesPlugin extends RouterPlugin {
    private static final Logger logger = Logger.getLogger(AdminRoutesPlugin.class.getName());

    LinkedHashMap<String, Class<? extends ChannelHandler>> routeMap;

    public AdminRoutesPlugin() {
        logger.info("Initializing AdminRoutesPlugin");
        routeMap = new LinkedHashMap<>();
        routeMap.put("/json/admin/categories", AdminCategoriesHandler.class);
        routeMap.put("/json/admin/categories/{category}", AdminCategoryHandler.class);
        routeMap.put("/json/admin/categories/{category}/subcategories", AdminSubcategoriesHandler.class);
        routeMap.put("/json/admin/categories/{category}/subcategories/{subcategory}", AdminSubcategoryHandler.class);
    }

    @Override
    public LinkedHashMap<String,Class<? extends ChannelHandler>> getRoutes() {
        return routeMap;
    }

    @Override
    public Class<? extends ChannelHandler> getHandlerForRoute(String route) {
        return routeMap.get(route);
    }
}