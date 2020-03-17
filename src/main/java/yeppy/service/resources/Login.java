package yeppy.service.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import yeppy.service.models.LoginResponseModel;
import yeppy.service.models.RegisterRequestModel;

import java.io.IOException;

import static yeppy.service.core.Helper.*;

@Path("/login")
public class Login {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String jsonText, @Context HttpHeaders headers) {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequestModel requestModel;
        LoginResponseModel lrm = new LoginResponseModel();

        try{

            requestModel = mapper.readValue(jsonText, RegisterRequestModel.class);
            String username=requestModel.getUsername();
            String password = requestModel.getPassword();
            String user_id = verifyLogin(username, password);
            if(user_id != null){
                lrm.setResultCode(200);
                lrm.setMessage("Login Successfully");
                lrm.setUserId(user_id);
                return Response.status(Response.Status.OK).entity(lrm).build();
            } else {
                lrm.setResultCode(14);
                lrm.setMessage("Credentials don’t match.");
                lrm.setUserId("");
                return Response.status(Response.Status.BAD_REQUEST).entity(lrm).build();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        lrm.setResultCode(500);
        lrm.setMessage("Internal Server Error");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}