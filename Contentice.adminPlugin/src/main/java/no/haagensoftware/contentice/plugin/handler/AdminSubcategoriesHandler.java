package no.haagensoftware.contentice.plugin.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import no.haagensoftware.contentice.assembler.SubCategoryAssembler;
import no.haagensoftware.contentice.data.SubCategoryData;
import no.haagensoftware.contentice.handler.ContenticeHandler;
import no.haagensoftware.contentice.plugin.assembler.AdminSubCategoryAssembler;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jhsmbp
 * Date: 11/19/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminSubcategoriesHandler extends ContenticeHandler {
    private Logger logger = Logger.getLogger(AdminSubcategoryHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        logger.info("reading SubCategoriesHandler and writing contents to buffer");

        String category = getParameter("category");

        List<SubCategoryData> subCategories = getStorage().getSubCategories(category);

        if (subCategories == null) {
            write404ToBuffer(channelHandlerContext);
        } else {
            JsonArray subCategoryArray = new JsonArray();
            for (SubCategoryData subCategory : subCategories) {
                subCategoryArray.add(AdminSubCategoryAssembler.buildAdminJsonFromSubCategoryData(subCategory, category));
            }

            JsonObject topLevelObject = new JsonObject();
            topLevelObject.add("subCategories", subCategoryArray);

            writeContentsToBuffer(channelHandlerContext, topLevelObject.toString(), "application/json; charset=UTF-8");
        }

        channelHandlerContext.fireChannelRead(fullHttpRequest);
    }
}