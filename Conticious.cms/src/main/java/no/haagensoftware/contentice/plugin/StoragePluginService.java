package no.haagensoftware.contentice.plugin;

import no.haagensoftware.contentice.main.ClassPathUtil;
import no.haagensoftware.contentice.spi.ConticiousPlugin;
import no.haagensoftware.contentice.spi.RouterPlugin;
import no.haagensoftware.contentice.spi.StoragePlugin;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: jhsmbp
 * Date: 11/16/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoragePluginService implements PluginService {
    private static final Logger logger = Logger.getLogger(StoragePluginService.class.getName());
    private static StoragePluginService pluginService = null;
    private ServiceLoader<StoragePlugin> loader;

    public StoragePluginService() {
        ClassPathUtil.addPluginDirectory();
        logger.info("Loading StoragePlugins");
        loader = ServiceLoader.load(StoragePlugin.class);
        for (StoragePlugin plugin : loader) {
            logger.info("Plugin loaded: " + plugin);
        }
        logger.info("Done loading StoragePlugins");
    }

    public static synchronized StoragePluginService getInstance() {
        if (pluginService == null) {
            pluginService = new StoragePluginService();
        }

        return pluginService;
    }

    public StoragePlugin getStoragePluginWithName(String storagePluginName) {
        StoragePlugin returnPlugin = null;

        returnPlugin = getStoragePlugin(storagePluginName, returnPlugin);

        if (returnPlugin == null) {
            throw new IllegalArgumentException("There is no plugin named: " + storagePluginName + ". Storage is unavailable. Please load the plugin JAR file into the plugins directory.");
        }

        return returnPlugin;
    }

    private StoragePlugin getStoragePlugin(String storagePluginName, StoragePlugin returnPlugin) {
        for (StoragePlugin plugin : loader) {
            if (plugin.getPluginName().equals(storagePluginName)) {
                returnPlugin = plugin;
                break;
            }
        }
        return returnPlugin;
    }

    @Override
    public List<ConticiousPlugin> getLoadedPlugins() {
        List<ConticiousPlugin> loadedPlugins = new ArrayList<>();

        for (StoragePlugin plugin: loader) {
            loadedPlugins.add(plugin);
        }

        return loadedPlugins;
    }
}
