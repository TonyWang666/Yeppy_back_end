package yeppy.service.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import yeppy.service.core.Restaurant;
import yeppy.service.models.LikeRequestModel;
import yeppy.service.models.RecommendResponseModel;
import yeppy.service.models.RegisterResponseModel;
import yeppy.service.models.SearchRequestModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static yeppy.service.core.Helper.*;
import static yeppy.service.external.YelpClient.*;

@Path("/recommend")
public class Recommend {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response RecommendBusinesses(String jsonText, @Context HttpHeaders headers){
        ObjectMapper mapper = new ObjectMapper();
        SearchRequestModel requestModel;
        RecommendResponseModel responseModel = new RecommendResponseModel();

        try {
            requestModel = mapper.readValue(jsonText, SearchRequestModel.class);

            String userId = requestModel.getUserId();
            float latitude = requestModel.getLatitude();
            float longitude = requestModel.getLongitude();

            List<String> topCategories = getTopCategories(userId);
            if(topCategories == null || topCategories.size() == 0){
                responseModel.setRestaurants(searchMostPopular(latitude, longitude));
                responseModel.setResultCode(110);
                responseModel.setMessage("Most popular businesses near you.");
            } else {
                responseModel.setRestaurants(searchByCategory(latitude, longitude, topCategories));
                responseModel.setResultCode(120);
                responseModel.setMessage("Recommendations for you.");
            }


            return Response.status(Response.Status.OK).entity(responseModel).build();

        } catch (IOException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

