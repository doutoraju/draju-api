package br.com.draju.templateapi.controller;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.entity.actiondata.Petitioner;
import br.com.draju.templateapi.facade.DocxGenerator;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/template/download")
public class DownloadResource {
	
    @Inject
    private DocxGenerator docxGenerator;
   
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewDocxMessage(@RequestBody Action action) {
        byte[] result;
        try {
            result = docxGenerator.generateDocxByteFromTemplate(action);
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
    public Response simpleGetTest(	@QueryParam("fullName") String fullName,
    								@QueryParam("fullAddress") String fullAddress) {
    	Petitioner petitioner = new Petitioner();
        Action action = new Action();
        petitioner.setFullName(fullName);
        petitioner.setFullAdress(fullAddress);
        petitioner.setCpf("123456789-09");
    	action.setPetitioner(petitioner);

    	return createNewDocxMessage(action);
    }
}
