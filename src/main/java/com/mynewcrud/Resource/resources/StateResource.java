package com.mynewcrud.Resource.resources;


import com.mynewcrud.Resource.entities.State;
import com.mynewcrud.Resource.pages.PageRequest;
import com.mynewcrud.Resource.services.StateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/states")
@Produces(MediaType.APPLICATION_JSON)
public class StateResource {

	@Context
	UriInfo uriInfo;

	@Inject
	StateService stateService;

	@GET
	@Path("/count")
	@Transactional
	public long count() {
		return stateService.count();
	}

	@GET
	@Transactional
	public Response getAllPaged(@BeanParam PageRequest pageRequest) {
		return stateService.getAllPaged(pageRequest);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response persist(State state) {
		return stateService.persist(state, uriInfo);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, State state) {
		return stateService.update(id, state);
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Long id) {
		return stateService.delete(id);
	}

}
