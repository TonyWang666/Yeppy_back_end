package yeppy.service.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import yeppy.service.core.Restaurant;
import yeppy.service.models.LikeRequestModel;
import yeppy.service.models.RegisterResponseModel;
import yeppy.service.models.SearchRequestModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static yeppy.service.core.Helper.UserLikeBusiness;
import static yeppy.service.core.Helper.existLike;
import static yeppy.service.external.YelpClient.search;

@Path("/like")
public class Like {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response LikeBusiness(String jsonText, @Context HttpHeaders headers){
        ObjectMapper mapper = new ObjectMapper();
        LikeRequestModel requestModel;
        RegisterResponseModel responseModel = new RegisterResponseModel();

        try {
            requestModel = mapper.readValue(jsonText, LikeRequestModel.class);

            String userId = requestModel.getUserId();
            String businessId = requestModel.getBusinessId();
            String[] categories = requestModel.getCategories();

            if(!existLike(userId, businessId)){
                UserLikeBusiness(userId, businessId, categories);
                responseModel.setResultCode(1);
                responseModel.setMessage("liked the restaurant successfully");
            } else {
                responseModel.setResultCode(2);
                responseModel.setMessage("liked the restaurant already");
            }

            return Response.status(Response.Status.OK).entity(responseModel).build();

        } catch (IOException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
