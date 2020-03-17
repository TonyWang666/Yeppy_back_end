package yeppy.service.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import yeppy.service.logger.ServiceLogger;
import yeppy.service.models.RegisterRequestModel;
import yeppy.service.models.RegisterResponseModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static yeppy.service.core.Helper.existUser;
import static yeppy.service.core.Helper.insertUserToDb;

@Path("/register")
public class Register {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response RegisterUser(String jsonText, @Context HttpHeaders headers) {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequestModel requestModel;
        RegisterResponseModel responseModel = new RegisterResponseModel();

        try {
            requestModel = mapper.readValue(jsonText, RegisterRequestModel.class);

            String username = requestModel.getUsername();
            String password = requestModel.getPassword();

            if (existUser(username)) {
                responseModel.setResultCode(221);
                responseModel.setMessage("Account already exists.");
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            } else {
                insertUserToDb(requestModel);
                responseModel.setResultCode(220);
                responseModel.setMessage("Account successfully registered.");

                return Response.status(Response.Status.OK).entity(responseModel).build();
            }


        } catch (IOException e) {
            e.printStackTrace();
            responseModel.setResultCode(222);
            responseModel.setMessage("Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
