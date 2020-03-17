package yeppy.service.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import yeppy.service.core.Restaurant;
import yeppy.service.external.YelpClient;
import yeppy.service.models.RegisterRequestModel;
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

import static yeppy.service.core.Helper.existUser;
import static yeppy.service.core.Helper.insertUserToDb;
import static yeppy.service.external.YelpClient.search;

@Path("/search")
public class Search {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Search(String jsonText, @Context HttpHeaders headers){
        ObjectMapper mapper = new ObjectMapper();
        SearchRequestModel requestModel;

        try {
            requestModel = mapper.readValue(jsonText, SearchRequestModel.class);

            String userId = requestModel.getUserId();
            String term = requestModel.getTerm();
            float latitude = requestModel.getLatitude();
            float longitude = requestModel.getLongitude();

            List<Restaurant> list = search(latitude, longitude, term);

            return Response.status(Response.Status.OK).entity(list).build();

        } catch (IOException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
