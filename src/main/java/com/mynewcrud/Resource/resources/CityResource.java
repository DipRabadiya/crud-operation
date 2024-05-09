package com.mynewcrud.Resource.resources;

import com.mynewcrud.Resource.entities.City;
import com.mynewcrud.Resource.pages.PageRequest;
import com.mynewcrud.Resource.services.CityService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {

	@Inject
	CityService cityService;

	@GET
	@Path("/count")
	@Transactional
	public long count() {
		return cityService.count();
	}

	@GET
	@Transactional
	public Response getAllPaged(@BeanParam PageRequest pageRequest) {
		return cityService.getAllPaged(pageRequest);
	}

	@GET
	@Path("{id}")
	@Transactional
	public Response getAllByStateId(@PathParam("id") Long id, @BeanParam PageRequest pageRequest) {
		return cityService.getAllByStateId(id, pageRequest);
	}

	@GET
	@Path("/find/{name}")
	@Transactional
	public Response getAllByCityName(@PathParam("name") String name, @BeanParam PageRequest pageRequest) {
		return cityService.getAllByCityName(name, pageRequest);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response persist(City city) {
		return cityService.persist(city);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, City city) {
		return cityService.update(id, city);
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Long id) {
		return cityService.delete(id);
	}

}
