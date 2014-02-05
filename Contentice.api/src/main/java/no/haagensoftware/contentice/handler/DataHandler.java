package no.haagensoftware.contentice.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import no.haagensoftware.contentice.assembler.DataAssembler;
import no.haagensoftware.contentice.data.SubCategoryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhsmbp on 1/24/14.
 */
public class DataHandler extends ContenticeHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {

        String jsonReturn = "";

        String category = getParameter("category");
        String subcategory = getParameter("subcategory");

        if (category != null && category.equals("pages")) {
            List<String> ids = getQueryStringIds();

            if (isGet(fullHttpRequest) && category != null && subcategory == null && ids.size() == 0) {
                //Get all subcategories for category

                String categoryName = getUrlResolver().getPluralFor(category);

                List<SubCategoryData> subCategoryDataList = getStorage().getSubCategories(category);
                jsonReturn = DataAssembler.buildJsonFromSubCategoryData(categoryName, subCategoryDataList.toArray(new SubCategoryData[subCategoryDataList.size()])).toString();
            } else if (isGet(fullHttpRequest) && category != null && subcategory == null && ids.size() > 0) {
                //Get subcategories with ids for category

                List<SubCategoryData> subCategoryDataList = new ArrayList<>();
                for (String id : ids) {
                    SubCategoryData subCategoryData = getStorage().getSubCategory(category, id);
                    if (subCategoryData != null) {
                        subCategoryDataList.add(subCategoryData);
                    }
                }
                String categoryName = getUrlResolver().getPluralFor(category);
                jsonReturn = DataAssembler.buildJsonFromSubCategoryData(categoryName, subCategoryDataList.toArray(new SubCategoryData[subCategoryDataList.size()])).toString();
            } else if (isGet(fullHttpRequest) && category != null && subcategory != null) {
                //get a single subcategory

                String categoryName = getUrlResolver().getSingularFor(category);

                SubCategoryData subCategoryData = getStorage().getSubCategory(category, subcategory);
                if (subCategoryData != null) {
                    jsonReturn = DataAssembler.buildJsonFromSubCategoryData(categoryName, subCategoryData).toString();
                }
            }
        }


        writeContentsToBuffer(channelHandlerContext, jsonReturn, "application/json; charset=UTF-8");
    }
}
