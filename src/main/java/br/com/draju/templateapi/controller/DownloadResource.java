package br.com.draju.templateapi.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.draju.templateapi.data.AuthorBasicData;
import br.com.draju.templateapi.facade.DocxGenerator;

@Path("/template/download")
public class DownloadResource {
	
    @Inject
    private DocxGenerator docxGenerator;
   
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewDocxMessage(AuthorBasicData userInformation) {
        byte[] result;
        try {
            result = docxGenerator.generateDocxByteFromTemplate(userInformation);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok(result, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"message.docx\"")
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response simpleGetTest(	@QueryParam("firstName") String firstName,
    								@QueryParam("lastName") String lastName) {
    	AuthorBasicData userInformation = new AuthorBasicData();
    	userInformation.setFirstName(firstName);
    	userInformation.setLastName(lastName);
    	
    	return createNewDocxMessage(userInformation);
    }
}
